package iadevelopment.jwt.repository;

import java.util.Arrays;

import org.springframework.stereotype.Repository;

import iadevelopment.jwt.model.Autorizacao;
import iadevelopment.jwt.model.Usuario;

@Repository
public class UserRepository {

	private static final String USERNAME = "usuario@teste.com";
	private static final String PASSWORD = "$2a$10$.6hXZTOntIGFc50nInQD3e4dDrc1YzUY7zxvixArCVF5h2iYqCeH6";

	public Usuario findByUsername(String username) {
		if (!username.equals(USERNAME)) {
			return null;
		}
		return Usuario.builder()
				// usuário
				.username(USERNAME)
				// senha
				.password(PASSWORD)
				// conta não expirada
				.accountNonExpired(true)
				// conta não bloqueada
				.accountNonLocked(true)
				// senha não expirada
				.credentialsNonExpired(true)
				// habilitado
				.enabled(true)
				// autorização
				.authorities(Arrays.asList(Autorizacao.builder()
						// nome da autorização
						.authority("ROLE_USER")
						// descrição
						.descricao("usuário do sistema").build()))
				.build();
	}
}
