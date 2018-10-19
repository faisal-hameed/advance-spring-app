package pk.habsoft.demo.estore.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import pk.habsoft.demo.estore.db.entity.UserEntity;

public class AuthenticationWithToken extends PreAuthenticatedAuthenticationToken {

	private static final long serialVersionUID = 1L;

	private pk.habsoft.demo.estore.db.entity.UserEntity jwtUser;

	public AuthenticationWithToken(Object aPrincipal, Object aCredentials,
			Collection<? extends GrantedAuthority> anAuthorities) {
		super(aPrincipal, aCredentials, anAuthorities);
	}

	public void setJwtUser(UserEntity user) {
		this.jwtUser = user;
	}

	public UserEntity getJwtUser() {
		return jwtUser;
	}
}