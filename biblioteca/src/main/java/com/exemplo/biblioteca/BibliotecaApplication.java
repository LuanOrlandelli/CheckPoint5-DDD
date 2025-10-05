package com.exemplo.biblioteca;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.exemplo.biblioteca.model.Livro;
import com.exemplo.biblioteca.model.Usuario;
import com.exemplo.biblioteca.repository.LivroRepository;
import com.exemplo.biblioteca.repository.UsuarioRepository;

@SpringBootApplication
public class BibliotecaApplication {
    public static void main(String[] args) {
        SpringApplication.run(BibliotecaApplication.class, args);
    }

    @Bean
    CommandLineRunner init(LivroRepository lr, UsuarioRepository ur) {
        return args -> {
            if (lr.count() == 0) {
                lr.save(new Livro("Dom Casmurro", "Machado de Assis", 1899));
                lr.save(new Livro("O Alienista", "Machado de Assis", 1882));
                lr.save(new Livro("A Hora da Estrela", "Clarice Lispector", 1977));
            }
            if (ur.count() == 0) {
                ur.save(new Usuario("Ana Silva", "ana@exemplo.com"));
                ur.save(new Usuario("Pedro Souza", "pedro@exemplo.com"));
            }
        };
    }

    // Este método é executado assim que o servidor inicia com sucesso
    @EventListener(ApplicationReadyEvent.class)
    public void mostrarLinkNoConsole() {
        System.out.println("==========================================================");
        System.out.println("🚀 APLICAÇÃO RODANDO COM SUCESSO!");
        System.out.println("👉 Acesse no seu navegador: http://localhost:8080/");
        System.out.println("==========================================================");
    }
}

