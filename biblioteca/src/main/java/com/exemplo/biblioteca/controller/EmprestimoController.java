package com.exemplo.biblioteca.controller;

import com.exemplo.biblioteca.model.Emprestimo;
import com.exemplo.biblioteca.service.EmprestimoService;
import com.exemplo.biblioteca.service.LivroService;
import com.exemplo.biblioteca.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;
    private final UsuarioService usuarioService;
    private final LivroService livroService;

    public EmprestimoController(EmprestimoService emprestimoService,
                                UsuarioService usuarioService,
                                LivroService livroService) {
        this.emprestimoService = emprestimoService;
        this.usuarioService = usuarioService;
        this.livroService = livroService;
    }

    /**
     * Configura conversão automática de datas do formulário (String -> LocalDate)
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text == null || text.isEmpty()) {
                    setValue(null);
                } else {
                    setValue(LocalDate.parse(text, formatter));
                }
            }

            @Override
            public String getAsText() {
                LocalDate value = (LocalDate) getValue();
                return value != null ? value.format(formatter) : "";
            }
        });
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("emprestimos", emprestimoService.listarAtivos());
        return "emprestimos/list";
    }

    @GetMapping("/novo")
    public String formNovo(Model model) {
        model.addAttribute("emprestimo", new Emprestimo());
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("livros", livroService.listarDisponiveis());
        return "emprestimos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Emprestimo emprestimo,
                         BindingResult br,
                         RedirectAttributes ra,
                         Model model) {

        if (emprestimo.getRetirada() == null || emprestimo.getPrevistoDevolucao() == null) {
            model.addAttribute("usuarios", usuarioService.listar());
            model.addAttribute("livros", livroService.listarDisponiveis());
            model.addAttribute("erro", "Preencha datas de retirada e previsão de devolução");
            return "emprestimos/form";
        }

        if (!emprestimo.getPrevistoDevolucao().isAfter(emprestimo.getRetirada())) {
            model.addAttribute("usuarios", usuarioService.listar());
            model.addAttribute("livros", livroService.listarDisponiveis());
            model.addAttribute("erro", "Data prevista para devolução deve ser posterior à data de retirada");
            return "emprestimos/form";
        }

        try {
            emprestimoService.criar(emprestimo);
            ra.addFlashAttribute("msg", "Empréstimo registrado com sucesso!");
            return "redirect:/emprestimos";
        } catch (RuntimeException e) {
            model.addAttribute("usuarios", usuarioService.listar());
            model.addAttribute("livros", livroService.listarDisponiveis());
            model.addAttribute("erro", e.getMessage());
            return "emprestimos/form";
        }
    }

    @PostMapping("/devolver/{id}")
    public String devolver(@PathVariable Long id, RedirectAttributes ra) {
        try {
            emprestimoService.devolver(id);
            ra.addFlashAttribute("msg", "Livro devolvido com sucesso!");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/emprestimos";
    }
}
