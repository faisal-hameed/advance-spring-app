package pk.habsoft.demo.estore.config;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import pk.habsoft.demo.estore.endpoint.Endpoints;
import pk.habsoft.demo.estore.security.AccountAuthenticationProvider;
import pk.habsoft.demo.estore.security.AuthenticationFilter;
import pk.habsoft.demo.estore.security.TokenAuthenticationProvider;
import pk.habsoft.demo.estore.service.TokenService;
import pk.habsoft.demo.estore.service.impl.AppUserDetailsServiceImpl;
import pk.habsoft.demo.estore.service.impl.TokenServiceImpl;

@Component
@EnableWebSecurity()
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final Logger LOGGER = Logger.getLogger(WebSecurityConfiguration.class);

	@Autowired
	AppUserDetailsServiceImpl detailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// auth.userDetailsService(detailsService).passwordEncoder(UserDTO.PASSWORD_ENCODER);

		// Add back-end user auth provider
		LOGGER.info("Adding authentication providers..");
		auth.authenticationProvider(domainUserAuthenticationProvider())
				// Add token auth provider
				.authenticationProvider(tokenUserAuthenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		LOGGER.info("Configure http security");
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()// allow
																						// CORS
																						// option
																						// calls
				.antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
						"/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui")
				.permitAll().antMatchers(Endpoints.UrlAuthorization.BASE_URL + Endpoints.UrlAuthorization.USER_PAGE)
				.hasRole("USER") // Shouln't user (ROLE_USER)
				.antMatchers(Endpoints.UrlAuthorization.BASE_URL + Endpoints.UrlAuthorization.ADMIN_PAGE)
				.hasRole("ADMIN").antMatchers(Endpoints.Test.BASE_URL).permitAll().anyRequest().authenticated()
				// .and()
				// .httpBasic()
				// .anonymous().disable(); This will disable common APIs
				.and().exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());
		// @formatter:on

		// AuthenticationFilter will intercept all requests
		http.addFilterBefore(new AuthenticationFilter(authenticationManager()),
				UsernamePasswordAuthenticationFilter.class);

	}

	@Bean
	public AuthenticationEntryPoint unauthorizedEntryPoint() {
		return (request, response, authException) -> {
			LOGGER.error("Unauthenticated  : " + authException.getMessage());
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		};

	}

	@Bean
	public AccountAuthenticationProvider domainUserAuthenticationProvider() {
		return new AccountAuthenticationProvider(tokenService());
	}

	@Bean
	public TokenAuthenticationProvider tokenUserAuthenticationProvider() {
		return new TokenAuthenticationProvider(tokenService());
	}

	@Bean
	public TokenService tokenService() {
		return new TokenServiceImpl();
	}
}