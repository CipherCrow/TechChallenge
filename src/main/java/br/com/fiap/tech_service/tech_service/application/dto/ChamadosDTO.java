package br.com.fiap.tech_service.tech_service.application.dto;

import br.com.fiap.tech_service.tech_service.domain.entities.enums.Equipe;
import br.com.fiap.tech_service.tech_service.domain.entities.enums.Status;

import java.time.LocalDateTime;

public record ChamadosDTO(
        Long id,
        Long usuarioId,
        Long tecnicoId,
        Equipe tipoSolicitacao,
        String descricao,
        Status status,
        LocalDateTime dataAbertura,
        LocalDateTime dataVisualizacao,
        LocalDateTime dataTratamento,
        LocalDateTime dataSolucao,
        LocalDateTime dataReavaliacao,
        LocalDateTime dataEncerramento
) {}
