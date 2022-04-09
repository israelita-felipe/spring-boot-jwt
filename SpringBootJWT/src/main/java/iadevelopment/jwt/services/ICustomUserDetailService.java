package iadevelopment.jwt.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import iadevelopment.jwt.model.Usuario;

public interface ICustomUserDetailService extends UserDetailsService {

	@Override
	Usuario loadUserByUsername(String username) throws UsernameNotFoundException;

}
