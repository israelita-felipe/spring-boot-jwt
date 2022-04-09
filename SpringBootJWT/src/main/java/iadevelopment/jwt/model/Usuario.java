package iadevelopment.jwt.model;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class Usuario implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1097941807962398537L;

	private List<Autorizacao> authorities;
	private String username;
	private String password;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;

}
