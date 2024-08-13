package br.com.fiap.tech_service.tech_service.application.controller;

import br.com.fiap.tech_service.tech_service.application.dto.ChamadosDTO;
import br.com.fiap.tech_service.tech_service.application.mapper.TecnicosMapper;
import br.com.fiap.tech_service.tech_service.domain.entities.Chamados;
import br.com.fiap.tech_service.tech_service.application.mapper.ChamadosMapper;
import br.com.fiap.tech_service.tech_service.domain.entities.enums.Equipe;
import br.com.fiap.tech_service.tech_service.domain.entities.enums.Status;
import br.com.fiap.tech_service.tech_service.domain.service.ChamadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chamados")
public class ChamadosController {

    @Autowired
    private ChamadosService chamadoService;

    @PostMapping("/abrir")
    public ResponseEntity<Object> abrirChamado(
            @RequestParam Long usuarioId,
            @RequestParam Equipe tipoSolicitacao,
            @RequestParam String descricao) {
        try {
            Chamados chamado = chamadoService.abrirChamado(usuarioId, tipoSolicitacao, descricao);
            return ResponseEntity.status(HttpStatus.CREATED).body(ChamadosMapper.toDTO(chamado));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/enviarParaArea/{id}")
    public ResponseEntity<Object> enviarParaArea(@PathVariable Long id, @RequestParam Equipe equipe) {
        try {
            Chamados chamado = chamadoService.enviarParaArea(id, equipe);
            return ResponseEntity.ok(ChamadosMapper.toDTO(chamado));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/visualizar/{id}")
    public ResponseEntity<Object> visualizarChamado(@PathVariable Long id) {
        try {
            Chamados chamado = chamadoService.visualizarChamado(id);
            return ResponseEntity.ok(ChamadosMapper.toDTO(chamado));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/tratar/{idChamado}/{idTecnico}")
    public ResponseEntity<Object> tratarChamado(
            @PathVariable Long idChamado,
            @PathVariable Long idTecnico) {
        try {
            Chamados chamado = chamadoService.tratarChamado(idChamado, idTecnico);
            return ResponseEntity.ok(ChamadosMapper.toDTO(chamado));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/solucionar/{id}")
    public ResponseEntity<Object> solucionarChamado(
            @PathVariable Long id,
            @RequestParam String descricao) {
        try {
            Chamados chamado = chamadoService.solucionarChamado(id, descricao);
            return ResponseEntity.ok(ChamadosMapper.toDTO(chamado));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/reavaliar/{id}")
    public ResponseEntity<Object> reavaliarChamado(@PathVariable Long id) {
        try {
            Chamados chamado = chamadoService.reavaliarChamado(id);
            return ResponseEntity.ok(ChamadosMapper.toDTO(chamado));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/validar/{id}")
    public ResponseEntity<Object> validarChamado(
            @PathVariable Long id,
            @RequestParam boolean isValidado ) {
        try {
            Chamados chamado = chamadoService.validarChamado(id, isValidado);
            return ResponseEntity.ok(ChamadosMapper.toDTO(chamado));
        } catch (RuntimeException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/encerrar/{id}")
    public ResponseEntity<Object> encerrarChamado(@PathVariable Long id) {
        try {
            Chamados chamado = chamadoService.encerrarChamado(id);
            return ResponseEntity.ok(ChamadosMapper.toDTO(chamado));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Object>> buscarChamadosPorStatus(@PathVariable Status status) {
        try {
            List<Chamados> chamados = chamadoService.buscarChamadosPorStatus(status);
            List<ChamadosDTO> chamadosDTO = chamados.stream()
                    .map(ChamadosMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(Collections.singletonList(chamadosDTO));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Collections.singletonList(ex.getMessage()));
        }
    }

    @GetMapping("/equipe")
    public ResponseEntity<Object> buscarChamadosPorEquipe(@RequestParam Equipe equipe) {
        try {
            List<Chamados> chamadosList = chamadoService.buscarChamadosPorEquipe(equipe);
            if (chamadosList.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(
                    chamadosList.stream()
                            .map(ChamadosMapper::toDTO)
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar os chamados: " + e.getMessage());
        }
    }
}
