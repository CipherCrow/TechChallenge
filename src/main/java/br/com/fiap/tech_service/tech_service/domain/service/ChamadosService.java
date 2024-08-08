package br.com.fiap.tech_service.tech_service.domain.service;

import br.com.fiap.tech_service.tech_service.domain.entities.Chamados;
import br.com.fiap.tech_service.tech_service.domain.entities.enums.Equipe;
import br.com.fiap.tech_service.tech_service.domain.entities.enums.Status;
import br.com.fiap.tech_service.tech_service.domain.repository.ChamadosRepository;
import br.com.fiap.tech_service.tech_service.domain.repository.TecnicosRepository;
import br.com.fiap.tech_service.tech_service.domain.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChamadosService {

    @Autowired
    private ChamadosRepository chamadoRepository;

    @Autowired
    private UsuariosRepository usuarioRepository;

    @Autowired
    private TecnicosRepository tecnicoRepository;

    public Chamados abrirChamado(Long usuarioId, Equipe tipoSolicitacao, String descricao) {
        Chamados chamado = new Chamados();
        chamado.setUsuario(usuarioRepository.findById(usuarioId).orElseThrow());
        chamado.setTipoSolicitacao(tipoSolicitacao);
        chamado.setDescricao(descricao);
        chamado.setStatus(Status.ABERTO);
        chamado.setDataAbertura(LocalDateTime.now());
        return chamadoRepository.save(chamado);
    }

    public Chamados enviarParaArea(Long chamadoId, Equipe equipe) {
        Chamados chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
        chamado.setStatus(Status.ENVIADO_PARA_AREA);
        chamado.setTipoSolicitacao(equipe);
        return chamadoRepository.save(chamado);
    }

    public Chamados visualizarChamado(Long id) {
        Chamados chamado = chamadoRepository.findById(id).orElseThrow();
        chamado.setDataVisualizacao(LocalDateTime.now());
        chamado.setStatus(Status.VISUALIZADO);
        return chamadoRepository.save(chamado);
    }

    public Chamados tratarChamado(Long id,Long tecnicoId) {
        Chamados chamado = chamadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tecnico não encontrado"));
        tecnicoRepository.findById(tecnicoId).orElseThrow(() -> new RuntimeException("Tecnico não encontrado"));;
        chamado.setDataTratamento(LocalDateTime.now());
        chamado.setStatus(Status.EM_TRATAMENTO);
        return chamadoRepository.save(chamado);
    }

    public Chamados solucionarChamado(Long id) {
        Chamados chamado = chamadoRepository.findById(id).orElseThrow();
        chamado.setDataSolucao(LocalDateTime.now());
        chamado.setStatus(Status.SOLUCIONADO);
        return chamadoRepository.save(chamado);
    }

    public Chamados reavaliarChamado(Long id) {
        Chamados chamado = chamadoRepository.findById(id).orElseThrow();
        chamado.setDataReavaliacao(LocalDateTime.now());
        chamado.setStatus(Status.REAVALIADO);
        return chamadoRepository.save(chamado);
    }

    public Chamados encerrarChamado(Long id) {
        Chamados chamado = chamadoRepository.findById(id).orElseThrow();
        chamado.setDataEncerramento(LocalDateTime.now());
        chamado.setStatus(Status.ENCERRADO);
        return chamadoRepository.save(chamado);
    }
}