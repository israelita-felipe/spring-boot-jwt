package iadevelopment.jwt.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {

	private String usuario;
	private String senha;

}
