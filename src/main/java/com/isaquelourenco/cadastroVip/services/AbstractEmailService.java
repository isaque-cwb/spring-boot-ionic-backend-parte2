package com.isaquelourenco.cadastroVip.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.isaquelourenco.cadastroVip.domain.Parceiro;
import com.isaquelourenco.cadastroVip.domain.DadosCarro;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(DadosCarro obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromDadosCarro(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromDadosCarro(DadosCarro obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getParceiro().getEmail());
		sm.setFrom(sender);
		sm.setSubject("DadosCarro confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
	
	@Override
	public void sendNewPasswordEmail(Parceiro parceiro, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(parceiro, newPass);
		sendEmail(sm);
	}
	
	protected SimpleMailMessage prepareNewPasswordEmail(Parceiro parceiro, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(parceiro.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		return sm;
	}
}
