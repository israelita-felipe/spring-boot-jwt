package iadevelopment.jwt.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {

	private final String jwt;

	private final String message;

}
