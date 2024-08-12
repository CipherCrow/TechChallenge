package br.com.fiap.tech_service.tech_service.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class WebController {

    @GetMapping("/tecnicos")
    public String tecnicos() {
        return "tecnicos";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/chamados")
    public String chamados() {
        return "chamados";
    }

    @GetMapping("/usuarios")
    public String usuarios() {
        return "usuarios";
    }
}
