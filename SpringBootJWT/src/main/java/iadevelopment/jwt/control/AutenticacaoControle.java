package iadevelopment.jwt.control;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import iadevelopment.jwt.model.AuthenticationRequest;
import iadevelopment.jwt.model.AuthenticationResponse;
import iadevelopment.jwt.model.Usuario;
import iadevelopment.jwt.services.ICustomUserDetailService;
import iadevelopment.jwt.services.JWTUtilService;

@Controller
public class AutenticacaoControle {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private ICustomUserDetailService userDetailsService;
	@Autowired
	private JWTUtilService jwtService;

	@PostMapping("/autenticar")
	@ResponseBody
	@ResponseStatus(code = HttpStatus.OK)
	public Callable<AuthenticationResponse> criarTokenDeAutenticacao(@RequestBody AuthenticationRequest request) {
		return () -> {
			try {
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(request.getUsuario(), request.getSenha()));
				final Usuario userDetails = userDetailsService.loadUserByUsername(request.getUsuario());
				Map<String,Object> claims = new HashMap<>();
				claims.put("mensagem", "mensagem");
				final String jwt = jwtService.generateToken(userDetails, claims);

				return AuthenticationResponse.builder().jwt(jwt).message("usuário autenticado").build();
			} catch (Exception e) {
				return AuthenticationResponse.builder().message(e.getMessage()).build();
			}
		};
	}

	@GetMapping("/validar")
	@ResponseBody
	@ResponseStatus(code = HttpStatus.OK)
	public Callable<String> validar() {
		return () -> "autenticado";
	}

}
