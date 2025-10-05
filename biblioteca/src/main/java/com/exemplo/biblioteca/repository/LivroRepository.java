package com.exemplo.biblioteca.repository;

import com.exemplo.biblioteca.model.Livro;
import com.exemplo.biblioteca.model.StatusLivro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByStatus(StatusLivro status);
}
