package com.exemplo.biblioteca.controller;

import com.exemplo.biblioteca.model.Livro;
import com.exemplo.biblioteca.service.LivroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/livros")
public class LivroController {
    private final LivroService service;

    public LivroController(LivroService service) { this.service = service; }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("livros", service.listar());
        return "livros/list";
    }

    @GetMapping("/disponiveis")
    public String listarDisponiveis(Model model) {
        model.addAttribute("livros", service.listarDisponiveis());
        return "livros/list";
    }

    @GetMapping("/novo")
    public String formNovo(Model model) {
        model.addAttribute("livro", new Livro());
        return "livros/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Livro livro, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            return "livros/form";
        }
        service.salvar(livro);
        ra.addFlashAttribute("msg", "Livro salvo com sucesso");
        return "redirect:/livros";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        var opt = service.buscarPorId(id);
        if (opt.isEmpty()) {
            ra.addFlashAttribute("erro", "Livro não encontrado");
            return "redirect:/livros";
        }
        model.addAttribute("livro", opt.get());
        return "livros/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.deletar(id);
            ra.addFlashAttribute("msg", "Livro excluído");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/livros";
    }
}
