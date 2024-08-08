package br.com.fiap.tech_service.tech_service.application.mapper;


import br.com.fiap.tech_service.tech_service.application.dto.ChamadosDTO;
import br.com.fiap.tech_service.tech_service.domain.entities.Chamados;

public class ChamadosMapper {

    public static ChamadosDTO toDTO(Chamados chamado) {
        return new ChamadosDTO(
                chamado.getId(),
                chamado.getUsuario() != null ? chamado.getUsuario().getId() : null,
                chamado.getTecnico() != null ? chamado.getTecnico().getId() : null,
                chamado.getTipoSolicitacao(),
                chamado.getDescricao(),
                chamado.getStatus(),
                chamado.getDataAbertura(),
                chamado.getDataVisualizacao(),
                chamado.getDataTratamento(),
                chamado.getDataSolucao(),
                chamado.getDataReavaliacao(),
                chamado.getDataEncerramento()
        );
    }

    public static Chamados toEntity(ChamadosDTO dto) {
        Chamados chamado = new Chamados();
        chamado.setId(dto.id());
        chamado.setTipoSolicitacao(dto.tipoSolicitacao());
        chamado.setDescricao(dto.descricao());
        chamado.setStatus(dto.status());
        chamado.setDataAbertura(dto.dataAbertura());
        chamado.setDataVisualizacao(dto.dataVisualizacao());
        chamado.setDataTratamento(dto.dataTratamento());
        chamado.setDataSolucao(dto.dataSolucao());
        chamado.setDataReavaliacao(dto.dataReavaliacao());
        chamado.setDataEncerramento(dto.dataEncerramento());
        return chamado;
    }
}

