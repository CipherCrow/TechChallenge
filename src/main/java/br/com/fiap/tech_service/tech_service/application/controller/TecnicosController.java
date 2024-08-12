package br.com.fiap.tech_service.tech_service.application.controller;

import br.com.fiap.tech_service.tech_service.application.dto.TecnicosDTO;
import br.com.fiap.tech_service.tech_service.domain.entities.Tecnicos;
import br.com.fiap.tech_service.tech_service.application.mapper.TecnicosMapper;
import br.com.fiap.tech_service.tech_service.domain.service.TecnicosService;
import br.com.fiap.tech_service.tech_service.domain.entities.enums.Equipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tecnicos")
public class TecnicosController {

    @Autowired
    private TecnicosService tecnicoService;

    @GetMapping("/buscarTodos")
    public ResponseEntity<List<TecnicosDTO>> buscarTodosTecnicos() {
        List<Tecnicos> tecnicos = tecnicoService.buscarTodosTecnicos();
        if (tecnicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(
                tecnicos.stream()
                        .map(TecnicosMapper::toDTO)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("/criar")
    public ResponseEntity<Object> criarTecnico(@RequestBody TecnicosDTO tecnicosDTO) {
        try {
            Tecnicos tecnico = tecnicoService.criarTecnico(tecnicosDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(TecnicosMapper.toDTO(tecnico));
        } catch (ResponseStatusException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<Object> buscarTecnico(@RequestParam Long idTecnico) {
        try {
            Tecnicos tecnico = tecnicoService.buscarTecnico(idTecnico);
            return ResponseEntity.ok(TecnicosMapper.toDTO(tecnico));
        } catch (ResponseStatusException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Object> atualizarTecnico(@RequestParam Long idTecnico, @RequestBody TecnicosDTO tecnicosDTO) {
        try {
            Tecnicos tecnicoAtualizado = tecnicoService.atualizarTecnico(idTecnico, tecnicosDTO);
            return ResponseEntity.ok(TecnicosMapper.toDTO(tecnicoAtualizado));
        } catch (ResponseStatusException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Object> deletarTecnico(@RequestParam Long idTecnico) {
        try {
            tecnicoService.deletarTecnico(idTecnico);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
