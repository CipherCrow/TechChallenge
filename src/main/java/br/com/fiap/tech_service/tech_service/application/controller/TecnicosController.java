package br.com.fiap.tech_service.tech_service.application.controller;

import br.com.fiap.tech_service.tech_service.application.dto.TecnicosDTO;
import br.com.fiap.tech_service.tech_service.domain.entities.Tecnicos;
import br.com.fiap.tech_service.tech_service.application.mapper.TecnicosMapper;
import br.com.fiap.tech_service.tech_service.domain.service.TecnicosService;
import br.com.fiap.tech_service.tech_service.domain.entities.enums.Equipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tecnicos")
public class TecnicosController {

    @Autowired
    private TecnicosService tecnicoService;

    @PostMapping("/criar")
    public TecnicosDTO criarTecnico(@RequestParam String nome, @RequestParam String email, @RequestParam Equipe equipe) {
        Tecnicos tecnico = tecnicoService.criarTecnico(nome, email, equipe);
        return TecnicosMapper.toDTO(tecnico);
    }

    @GetMapping("/buscar")
    public TecnicosDTO buscarTecnico(@RequestParam Long idTecnico) {
        Tecnicos tecnico = tecnicoService.buscarTecnico(idTecnico);
        return TecnicosMapper.toDTO(tecnico);
    }


}