package com.isaquelourenco.cadastroVip.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class DadosCarro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String placa;
	private String cor;
	private Integer ano;
	private String marca;
	private String modelo;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "parceiro_id")
	private Parceiro parceiro;

	
	
	public DadosCarro() {
	}

	

public DadosCarro(String placa, String cor, Integer ano, String marca, String modelo, Parceiro parceiro) {
	super();
	this.placa = placa;
	this.cor = cor;
	this.ano = ano;
	this.marca = marca;
	this.modelo = modelo;
	this.parceiro = parceiro;
	
}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	

	public Parceiro getParceiro() {
		return parceiro;
	}

	public void setParceiro(Parceiro parceiro) {
		this.parceiro = parceiro;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DadosCarro other = (DadosCarro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DadosCarro id: ");
		builder.append(getId());
		builder.append(", placa: ");
		builder.append(getPlaca());
		builder.append(", cor: ");
		builder.append(getCor());
		builder.append(", ano: ");
		builder.append(ano);
		builder.append(", marca: ");
		builder.append(getMarca());
		builder.append(", modelo: ");
		builder.append(getModelo());
		builder.append(", parceiro: ");
		builder.append(getParceiro().getNome());
		builder.append("");
		return builder.toString();
	}



	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
}
