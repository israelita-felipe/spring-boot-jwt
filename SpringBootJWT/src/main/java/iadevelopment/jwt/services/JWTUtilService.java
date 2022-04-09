package iadevelopment.jwt.services;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JWTUtilService {

	private final long MILLIS = 1000;
	private final long MINUTE = MILLIS * 60;
	private final long HOUR = MINUTE * 60;

	private final String SECRET_KEY = "RrLiHe9OsVdPtlnRqA12HbHRoLEeVwxx";

	private String createToken(Map<String, Object> claims, String subject) {
		// key from secret key string
		Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				// expiration in 30 minutes
				.setExpiration(new Date(System.currentTimeMillis() + (HOUR / 2)))
				.signWith(key, SignatureAlgorithm.HS256).compact();
	}

	private Claims extractAllClaims(String token) {
		Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		return Jwts.parserBuilder()
				// add secret key to decode
				.setSigningKey(key)
				// building paerser
				.build()
				// parse token to extract claims
				.parseClaimsJws(token)
				// get body object
				.getBody();
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public String generateToken(UserDetails userDetail, Map<String, Object> claims) {
		return createToken(claims, userDetail.getUsername());
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());

	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
