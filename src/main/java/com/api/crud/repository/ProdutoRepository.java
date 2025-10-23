package com.api.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;\nimport com.api.crud.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
