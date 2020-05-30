package com.isaquelourenco.cadastroVip.services;

import org.springframework.mail.SimpleMailMessage;

import com.isaquelourenco.cadastroVip.domain.DadosCarro;
import com.isaquelourenco.cadastroVip.domain.Parceiro;

public interface EmailService {

	void sendOrderConfirmationEmail(DadosCarro obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	
	void sendNewPasswordEmail(Parceiro parceiro, String newPass);

	
}
