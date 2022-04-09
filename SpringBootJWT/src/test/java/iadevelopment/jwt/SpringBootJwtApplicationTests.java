package iadevelopment.jwt;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class SpringBootJwtApplicationTests {

	@Autowired
	BCryptPasswordEncoder encoder;

	@Test
	void contextLoads() {
		String password = "usuario@teste.com";
		String encoded = encoder.encode(password);
		log.info("{}", encoded);
		assertTrue(encoder.matches(password, encoded));
	}

}
