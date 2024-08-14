package br.com.fiap.tech_service.tech_service.application.controller;

import br.com.fiap.tech_service.tech_service.application.dto.TecnicosDTO;
import br.com.fiap.tech_service.tech_service.domain.entities.Tecnicos;
import br.com.fiap.tech_service.tech_service.application.mapper.TecnicosMapper;
import br.com.fiap.tech_service.tech_service.domain.service.TecnicosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/tecnicos")
public class TecnicosController {

    @Autowired
    private TecnicosService tecnicoService;

    @GetMapping("/buscarTodos")
    public ResponseEntity<Object> buscarTodosTecnicos() {
        try {
            List<Tecnicos> tecnicos = tecnicoService.buscarTodosTecnicos();
            if (tecnicos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(
                    tecnicos.stream()
                            .map(TecnicosMapper::toDTO)
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar todos os técnicos: " + e.getMessage());
        }
    }

    @PostMapping("/criar")
    public ResponseEntity<Object> criarTecnico(@RequestBody TecnicosDTO tecnicosDTO) {
        try {
            Tecnicos tecnico = tecnicoService.criarTecnico(tecnicosDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(TecnicosMapper.toDTO(tecnico));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Erro: " + ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar técnico: " + e.getMessage());
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<Object> buscarTecnico(@RequestParam Long idTecnico) {
        try {
            Tecnicos tecnico = tecnicoService.buscarTecnico(idTecnico);
            return ResponseEntity.ok(TecnicosMapper.toDTO(tecnico));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Erro: " + ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar técnico: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Object> atualizarTecnico(@RequestBody TecnicosDTO tecnicosDTO) {
        try {
            Tecnicos tecnicoAtualizado = tecnicoService.atualizarTecnico(tecnicosDTO);
            return ResponseEntity.ok(TecnicosMapper.toDTO(tecnicoAtualizado));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Erro: " + ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar técnico: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Object> deletarTecnico(@RequestParam Long idTecnico) {
        try {
            tecnicoService.deletarTecnico(idTecnico);
            return ResponseEntity.ok("Técnico deletado com sucesso");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Erro: " + ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar técnico: " + e.getMessage());
        }
    }
}
