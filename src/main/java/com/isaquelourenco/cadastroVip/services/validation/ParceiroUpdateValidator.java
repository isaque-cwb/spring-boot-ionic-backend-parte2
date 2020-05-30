package com.isaquelourenco.cadastroVip.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.isaquelourenco.cadastroVip.domain.Parceiro;
import com.isaquelourenco.cadastroVip.dto.ParceiroDTO;
import com.isaquelourenco.cadastroVip.repositories.ParceiroRepository;
import com.isaquelourenco.cadastroVip.resources.exception.FieldMessage;

public class ParceiroUpdateValidator implements ConstraintValidator<ParceiroUpdate, ParceiroDTO> {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ParceiroRepository repo;
	
	@Override
	public void initialize(ParceiroUpdate ann) {
	}

	@Override
	public boolean isValid(ParceiroDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
		
		Parceiro aux = repo.findByEmail(objDto.getEmail());
		if (aux != null && !aux.getId().equals(uriId)) {
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

