package com.algaworks.algafood.api.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.api.domain.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado,Long> {


}