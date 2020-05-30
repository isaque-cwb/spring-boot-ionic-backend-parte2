package com.isaquelourenco.cadastroVip.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.isaquelourenco.cadastroVip.domain.Cidade;
import com.isaquelourenco.cadastroVip.domain.Endereco;
import com.isaquelourenco.cadastroVip.domain.Parceiro;
import com.isaquelourenco.cadastroVip.domain.enums.Perfil;
import com.isaquelourenco.cadastroVip.dto.ParceiroDTO;
import com.isaquelourenco.cadastroVip.dto.ParceiroNewDTO;
import com.isaquelourenco.cadastroVip.repositories.EnderecoRepository;
import com.isaquelourenco.cadastroVip.repositories.ParceiroRepository;
import com.isaquelourenco.cadastroVip.security.UserSS;
import com.isaquelourenco.cadastroVip.services.exceptions.AuthorizationException;
import com.isaquelourenco.cadastroVip.services.exceptions.DataIntegrityException;
import com.isaquelourenco.cadastroVip.services.exceptions.ObjectNotFoundException;

@Service
public class ParceiroService {
	
	@Autowired
	private ParceiroRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
		
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	public Parceiro find(Integer id) {
		
		UserSS user = UserService.authenticated();
		if (user==null || !user.hasRole(Perfil.ADM) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Parceiro> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Parceiro.class.getName()));
	}
	
	@Transactional
	public Parceiro insert(Parceiro obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Parceiro update(Parceiro obj) {
		Parceiro newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
		}
	}
	
	public List<Parceiro> findAll() {
		return repo.findAll();
	}
	
	public Parceiro findByEmail(String email) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADM) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
	
		Parceiro obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Parceiro.class.getName());
		}
		return obj;
	}
	
	public Page<Parceiro> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Parceiro fromDTO(ParceiroDTO objDto) {
		return new Parceiro(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null, null);
	}
	
	public Parceiro fromDTO(ParceiroNewDTO objDto) {
		Parceiro par = new Parceiro(null, objDto.getNome(), objDto.getEmail(), objDto.getTelefone(), objDto.getIndicadoPor(), objDto.getIndicadoTel(), pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), par, cid);
		par.getEnderecos().add(end);
		par.getTelefones().add(objDto.getTelefone());
		if (objDto.getTelefone2()!=null) {
			par.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			par.getTelefones().add(objDto.getTelefone3());
		}
		return par;
	}
	
	private void updateData(Parceiro newObj, Parceiro obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
}
