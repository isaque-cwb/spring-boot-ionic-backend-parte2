package com.isaquelourenco.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.isaquelourenco.cursomc.domain.Cliente;
import com.isaquelourenco.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Cliente cliente, String newPass);
}
