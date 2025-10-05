package com.exemplo.biblioteca.service;

import com.exemplo.biblioteca.model.Emprestimo;
import com.exemplo.biblioteca.model.Livro;
import com.exemplo.biblioteca.model.StatusLivro;
import com.exemplo.biblioteca.repository.EmprestimoRepository;
import com.exemplo.biblioteca.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {
    private final EmprestimoRepository emprestimoRepo;
    private final LivroRepository livroRepo;

    public EmprestimoService(EmprestimoRepository emprestimoRepo, LivroRepository livroRepo) {
        this.emprestimoRepo = emprestimoRepo;
        this.livroRepo = livroRepo;
    }

    @Transactional
    public Emprestimo criar(Emprestimo e) {
        Livro l = livroRepo.findById(e.getLivro().getId())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        if (l.getStatus() == StatusLivro.EMPRESTADO) {
            throw new RuntimeException("Livro já está emprestado");
        }
        l.setStatus(StatusLivro.EMPRESTADO);
        livroRepo.save(l);

        e.setLivro(l);
        return emprestimoRepo.save(e);
    }

    public List<Emprestimo> listarAtivos() {
        return emprestimoRepo.findByDataDevolucaoIsNull();
    }

    public List<Emprestimo> listarTodos() { return emprestimoRepo.findAll(); }

    @Transactional
    public void devolver(Long emprestimoId) {
        Emprestimo e = emprestimoRepo.findById(emprestimoId)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
        if (e.getDataDevolucao() != null) throw new RuntimeException("Já devolvido");

        e.setDataDevolucao(LocalDate.now());
        emprestimoRepo.save(e);

        Livro l = e.getLivro();
        l.setStatus(StatusLivro.DISPONIVEL);
        livroRepo.save(l);
    }
}
