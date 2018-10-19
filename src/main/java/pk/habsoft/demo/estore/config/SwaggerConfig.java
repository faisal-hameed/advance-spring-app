package pk.habsoft.demo.estore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("pk.habsoft.demo.estore.endpoint"))
				.paths(PathSelectors.regex("(?!/error$).*")) // Excluding_implicit_API_(/error)
				.build().apiInfo(apiInfo());

	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Api Documentation", "EPMS Restful Api documentation", "1.0",
				ApiInfo.DEFAULT.getTermsOfServiceUrl(), new Contact("Faisal Hameed", "", "faisal.hameed@afz-sol.com"),
				ApiInfo.DEFAULT.getLicense(), ApiInfo.DEFAULT.getLicenseUrl());
	}
}