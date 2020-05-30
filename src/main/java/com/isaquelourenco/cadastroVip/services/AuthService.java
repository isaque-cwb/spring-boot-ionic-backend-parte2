package com.isaquelourenco.cadastroVip.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.isaquelourenco.cadastroVip.domain.Parceiro;
import com.isaquelourenco.cadastroVip.repositories.ParceiroRepository;
import com.isaquelourenco.cadastroVip.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ParceiroRepository parceiroRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		
		Parceiro parceiro = parceiroRepository.findByEmail(email);
		if (parceiro == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		parceiro.setSenha(pe.encode(newPass));
		
		parceiroRepository.save(parceiro);
		emailService.sendNewPasswordEmail(parceiro, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48);
		}
		else if (opt == 1) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		}
		else { // gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
