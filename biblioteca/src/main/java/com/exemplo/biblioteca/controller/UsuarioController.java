package com.exemplo.biblioteca.controller;

import com.exemplo.biblioteca.model.Usuario;
import com.exemplo.biblioteca.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) { this.service = service; }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", service.listar());
        return "usuarios/list";
    }

    @GetMapping("/novo")
    public String formNovo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Usuario usuario, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            return "usuarios/form";
        }
        service.salvar(usuario);
        ra.addFlashAttribute("msg", "Usuário salvo");
        return "redirect:/usuarios";
    }
}
