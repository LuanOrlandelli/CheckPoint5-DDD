package com.exemplo.biblioteca.service;

import com.exemplo.biblioteca.model.Usuario;
import com.exemplo.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) { this.repo = repo; }

    public Usuario salvar(Usuario u) { return repo.save(u); }
    public List<Usuario> listar() { return repo.findAll(); }
    public Optional<Usuario> buscarPorId(Long id) { return repo.findById(id); }
}
