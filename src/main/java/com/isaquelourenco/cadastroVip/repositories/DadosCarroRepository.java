package com.isaquelourenco.cadastroVip.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.isaquelourenco.cadastroVip.domain.DadosCarro;
import com.isaquelourenco.cadastroVip.domain.Parceiro;

@Repository
public interface DadosCarroRepository extends JpaRepository<DadosCarro, Integer> {

	@Transactional(readOnly=true)
	Page<DadosCarro> findByParceiro(Parceiro parceiro, Pageable pageRequest);
}
