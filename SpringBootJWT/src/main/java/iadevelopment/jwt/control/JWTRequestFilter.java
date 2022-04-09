package iadevelopment.jwt.control;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import iadevelopment.jwt.services.ICustomUserDetailService;
import iadevelopment.jwt.services.JWTUtilService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTRequestFilter extends OncePerRequestFilter {

	@Autowired
	private ICustomUserDetailService userDetailService;

	@Autowired
	private JWTUtilService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authorization = request.getHeader("Authorization");
		log.info("{}",authorization);

		String username = null;
		String jwt = null;

		if (authorization != null && authorization.startsWith("Bearer ")) {
			jwt = authorization.substring(7);
			username = jwtService.extractUsername(jwt);
			log.info("{}",username);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
			log.info("{}",userDetails);
			if (jwtService.validateToken(jwt, userDetails)) {
				log.info("válido");
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
