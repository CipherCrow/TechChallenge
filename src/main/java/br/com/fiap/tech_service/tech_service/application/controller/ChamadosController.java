package br.com.fiap.tech_service.tech_service.application.controller;


import br.com.fiap.tech_service.tech_service.application.dto.ChamadosDTO;
import br.com.fiap.tech_service.tech_service.domain.entities.Chamados;
import br.com.fiap.tech_service.tech_service.application.mapper.ChamadosMapper;
import br.com.fiap.tech_service.tech_service.domain.entities.enums.Equipe;
import br.com.fiap.tech_service.tech_service.domain.service.ChamadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chamados")
public class ChamadosController {

    @Autowired
    private ChamadosService chamadoService;

    @PostMapping("/abrir")
    public ChamadosDTO abrirChamado(@RequestParam Long usuarioId, @RequestParam Equipe tipoSolicitacao, @RequestParam String descricao) {
        Chamados chamado = chamadoService.abrirChamado(usuarioId, tipoSolicitacao, descricao);
        return ChamadosMapper.toDTO(chamado);
    }

    @PutMapping("/enviarParaArea/{id}")
    public ChamadosDTO enviarParaArea(@PathVariable Long id, @PathVariable Equipe equipe) {
        Chamados chamado = chamadoService.enviarParaArea(id, equipe);
        return ChamadosMapper.toDTO(chamado);
    }

    @PutMapping("/visualizar/{id}")
    public ChamadosDTO visualizarChamado(@PathVariable Long id) {
        Chamados chamado = chamadoService.visualizarChamado(id);
        return ChamadosMapper.toDTO(chamado);
    }

    @PutMapping("/tratar/{id}")
    public ChamadosDTO tratarChamado(@PathVariable Long idChamado, @PathVariable Long idTecnico) {
        Chamados chamado = chamadoService.tratarChamado(idChamado,idTecnico );
        return ChamadosMapper.toDTO(chamado);
    }

    @PutMapping("/solucionar/{id}")
    public ChamadosDTO solucionarChamado(@PathVariable Long id) {
        Chamados chamado = chamadoService.solucionarChamado(id);
        return ChamadosMapper.toDTO(chamado);
    }

    @PutMapping("/reavaliar/{id}")
    public ChamadosDTO reavaliarChamado(@PathVariable Long id) {
        Chamados chamado = chamadoService.reavaliarChamado(id);
        return ChamadosMapper.toDTO(chamado);
    }

    @PutMapping("/encerrar/{id}")
    public ChamadosDTO encerrarChamado(@PathVariable Long id) {
        Chamados chamado = chamadoService.encerrarChamado(id);
        return ChamadosMapper.toDTO(chamado);
    }
}
