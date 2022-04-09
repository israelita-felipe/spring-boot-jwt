package iadevelopment.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import iadevelopment.jwt.control.JWTRequestFilter;
import iadevelopment.jwt.services.ICustomUserDetailService;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private ICustomUserDetailService customUserDetailService;
	@Autowired
	private JWTRequestFilter jwtFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
				// aponta para custom user detail
				.userDetailsService(customUserDetailService)
				// coloca o encoder do password
				.passwordEncoder(defaultPassEnconder());
		super.configure(auth);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				// autorizações
				.authorizeRequests()
				// todos podem autenticar
				.antMatchers("/autenticar").permitAll()
				// outras
				.antMatchers("/validar").hasAnyRole("USER")
				// autenticaçao
				.anyRequest().authenticated().and()
				// gestor de sessão
				.sessionManagement()
				// criação de sessão sem estado
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// adicionando o filtro para autenticação jwt
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		// habilitando http security on headers
		http.headers().httpStrictTransportSecurity().disable();
		http.requiresChannel().requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null).requiresSecure();
	}

	@Bean
	public PasswordEncoder defaultPassEnconder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
