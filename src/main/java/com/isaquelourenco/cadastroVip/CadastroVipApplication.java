package com.isaquelourenco.cadastroVip;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.isaquelourenco.cadastroVip.domain.Cidade;
import com.isaquelourenco.cadastroVip.domain.DadosCarro;
import com.isaquelourenco.cadastroVip.domain.Endereco;
import com.isaquelourenco.cadastroVip.domain.Estado;
import com.isaquelourenco.cadastroVip.domain.Parceiro;
import com.isaquelourenco.cadastroVip.domain.enums.Perfil;
import com.isaquelourenco.cadastroVip.repositories.CidadeRepository;
import com.isaquelourenco.cadastroVip.repositories.DadosCarroRepository;
import com.isaquelourenco.cadastroVip.repositories.EnderecoRepository;
import com.isaquelourenco.cadastroVip.repositories.EstadoRepository;
import com.isaquelourenco.cadastroVip.repositories.ParceiroRepository;

@SpringBootApplication
public class CadastroVipApplication implements CommandLineRunner {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ParceiroRepository parceiroRepository;
	
	@Autowired
	private DadosCarroRepository dadosCarroRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CadastroVipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Estado est1 = new Estado(null, "Paraná");

		Cidade c1 = new Cidade(null, "Curitiba", est1);
		Cidade c2 = new Cidade(null, "Pinhais", est1);
		Cidade c3 = new Cidade(null, "Colombo", est1);
		Cidade c4 = new Cidade(null, "Araucária", est1);
		Cidade c5 = new Cidade(null, "São José dos Pinhais", est1);
		Cidade c6 = new Cidade(null, "Campina Grande do Sul", est1);
		Cidade c7 = new Cidade(null, "Quatro Barras", est1);
		Cidade c8 = new Cidade(null, "Piraquara", est1);
		Cidade c9 = new Cidade(null, "Campo Largo", est1);
		Cidade c10 = new Cidade(null, "Fazenda Rio Grande", est1);
		
		estadoRepository.saveAll(Arrays.asList(est1));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10));
		
		
		
		Parceiro par1 = new Parceiro(null, "Isaque Lourenço", "isaque.lourenco2@gmail.com", "36378912377", "Marcos", "995655887", pe.encode("123"));
		par1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		par1.addPerfil(Perfil.ADM);
		
		Parceiro par2 = new Parceiro(null, "Gustavo Lourenço", "gustavo@gmail.com", "31628382740", "Isaque", "956525632", pe.encode("123"));
		par2.getTelefones().addAll(Arrays.asList("93883321", "34252625"));
		par2.addPerfil(Perfil.PARCEIRO);
		
		Endereco e1 = new Endereco(null, "Rua: Epaminondas Santos", "2723", "casa 04", "Bairro Alto", "82820-090", par1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", par2, c3);
		
		par1.getEnderecos().addAll(Arrays.asList(e1));
		par2.getEnderecos().addAll(Arrays.asList(e2));
		
				
		DadosCarro dc1 = new DadosCarro("AWL-8708", "Preto", 2013, "Fiat", "Palio", par1);
		DadosCarro dc2 = new DadosCarro("ADS-6532", "Branco", 2018, "Chevrolet", "Onix", par2);
		
		parceiroRepository.saveAll(Arrays.asList(par1, par2));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		dadosCarroRepository.saveAll(Arrays.asList(dc1, dc2));
		
		
		
	}	
}
