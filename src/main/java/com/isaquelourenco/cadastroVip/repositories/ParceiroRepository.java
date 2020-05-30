package com.isaquelourenco.cadastroVip.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.isaquelourenco.cadastroVip.domain.Parceiro;

@Repository
public interface ParceiroRepository extends JpaRepository<Parceiro, Integer> {

	@Transactional(readOnly=true)
	Parceiro findByEmail(String email);
}
