package com.isaquelourenco.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.isaquelourenco.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
