package iadevelopment.jwt.model;

import org.springframework.security.core.GrantedAuthority;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class Autorizacao implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3427556059812440867L;
	private String authority;
	private String descricao;

}
