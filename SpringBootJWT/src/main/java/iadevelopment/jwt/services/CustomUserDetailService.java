package iadevelopment.jwt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import iadevelopment.jwt.model.Usuario;
import iadevelopment.jwt.repository.UserRepository;

@Service
public class CustomUserDetailService implements ICustomUserDetailService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("usuário %s não localizado", username));
		}
		return user;
	}

}
