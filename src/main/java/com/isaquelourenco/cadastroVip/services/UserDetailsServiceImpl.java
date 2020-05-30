package com.isaquelourenco.cadastroVip.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.isaquelourenco.cadastroVip.domain.Parceiro;
import com.isaquelourenco.cadastroVip.repositories.ParceiroRepository;
import com.isaquelourenco.cadastroVip.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ParceiroRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Parceiro par = repo.findByEmail(email);
		if (par == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSS(par.getId(), par.getEmail(), par.getSenha(), par.getPerfis());
	}
}
