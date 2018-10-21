package pk.habsoft.demo.estore.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import pk.habsoft.demo.estore.db.UserRepository;
import pk.habsoft.demo.estore.dto.UserDTO;

// TODO: Auto-generated Javadoc
/**
 * The Class AppUserDetailsService.
 */
@Component
public class AppUserDetailsService implements UserDetailsService {

	/** The users. */
	@Autowired
	UserRepository users;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("DetailsService::loadUserByUsername() : " + username);
		Optional<UserDTO> userOptional = users.findByUsername(username);
		if (!userOptional.isPresent()) {
			throw new UsernameNotFoundException(username + " was not found");
		}
		return userOptional.get();
	}

	/**
	 * Authenticate.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return the authentication
	 */
	public Authentication authenticate(String username, String password) {
		Optional<UserDTO> userOptional = users.authenticate(username, password);
		if (userOptional.isPresent()) {
			return new UsernamePasswordAuthenticationToken(username, password, userOptional.get().getAuthorities());
		} else {
			throw new BadCredentialsException("Invalid user/password.");
		}
	}

}