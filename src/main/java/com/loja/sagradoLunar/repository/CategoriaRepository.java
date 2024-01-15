package com.loja.sagradoLunar.repository;

import com.loja.sagradoLunar.model.Categoria;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
        public List<Categoria> findAllByNomeContainingIgnoreCase(String nome);
}
