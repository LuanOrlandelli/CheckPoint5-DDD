package com.exemplo.biblioteca.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "emprestimos")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "livro_id")
    private Livro livro;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate retirada;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate previstoDevolucao;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataDevolucao;

    public Emprestimo() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public LocalDate getRetirada() { return retirada; }
    public void setRetirada(LocalDate retirada) { this.retirada = retirada; }

    public LocalDate getPrevistoDevolucao() { return previstoDevolucao; }
    public void setPrevistoDevolucao(LocalDate previstoDevolucao) { this.previstoDevolucao = previstoDevolucao; }

    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDate dataDevolucao) { this.dataDevolucao = dataDevolucao; }

    @Transient
    public boolean isAtivo() {
        return this.dataDevolucao == null;
    }
}
