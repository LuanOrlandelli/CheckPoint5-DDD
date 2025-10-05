package com.exemplo.biblioteca.service;

import com.exemplo.biblioteca.model.Livro;
import com.exemplo.biblioteca.model.StatusLivro;
import com.exemplo.biblioteca.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    private final LivroRepository repo;

    public LivroService(LivroRepository repo) { this.repo = repo; }

    public Livro salvar(Livro livro) { 
        if (livro.getStatus() == null) livro.setStatus(StatusLivro.DISPONIVEL);
        return repo.save(livro); 
    }

    public List<Livro> listar() { return repo.findAll(); }

    public Optional<Livro> buscarPorId(Long id) { return repo.findById(id); }

    public void deletar(Long id) {
        Livro l = repo.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        if (l.getStatus() == StatusLivro.EMPRESTADO) {
            throw new RuntimeException("Não é possível excluir um livro emprestado");
        }
        repo.deleteById(id);
    }

    public List<Livro> listarDisponiveis() { return repo.findByStatus(StatusLivro.DISPONIVEL); }
}
