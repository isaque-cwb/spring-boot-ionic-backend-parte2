package com.isaquelourenco.cadastroVip.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.isaquelourenco.cadastroVip.domain.DadosCarro;
import com.isaquelourenco.cadastroVip.domain.Parceiro;
import com.isaquelourenco.cadastroVip.repositories.DadosCarroRepository;
import com.isaquelourenco.cadastroVip.security.UserSS;
import com.isaquelourenco.cadastroVip.services.exceptions.AuthorizationException;
import com.isaquelourenco.cadastroVip.services.exceptions.ObjectNotFoundException;

@Service
public class DadosCarroService {
	
	@Autowired
	private DadosCarroRepository repo;
	
		
	
	@Autowired
	private ParceiroService parceiroService;
	
	@Autowired
	private EmailService emailService;
	
	public DadosCarro find(Integer id) {
		Optional<DadosCarro> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + DadosCarro.class.getName()));
	}
	
	public DadosCarro insert(DadosCarro obj) {
		obj.setId(null);
		
		obj.setParceiro(parceiroService.find(obj.getParceiro().getId()));
		
		obj = repo.save(obj);
		
		emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}
	
	public Page<DadosCarro> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Parceiro parceiro =  parceiroService.find(user.getId());
		return repo.findByParceiro(parceiro, pageRequest);
	}
}
