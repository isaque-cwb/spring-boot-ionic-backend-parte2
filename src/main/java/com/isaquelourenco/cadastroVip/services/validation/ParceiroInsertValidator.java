package com.isaquelourenco.cadastroVip.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.isaquelourenco.cadastroVip.domain.Parceiro;
import com.isaquelourenco.cadastroVip.dto.ParceiroNewDTO;
import com.isaquelourenco.cadastroVip.repositories.ParceiroRepository;
import com.isaquelourenco.cadastroVip.resources.exception.FieldMessage;

public class ParceiroInsertValidator implements ConstraintValidator<ParceiroInsert, ParceiroNewDTO> {

	@Autowired
	private ParceiroRepository repo;
	
	@Override
	public void initialize(ParceiroInsert ann) {
	}

	@Override
	public boolean isValid(ParceiroNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		
		Parceiro aux = repo.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}

