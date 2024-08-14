package br.com.fiap.tech_service.tech_service.domain.entities.enums;

public enum Status {
    ABERTO,
    ENVIADO_PARA_AREA,
    VISUALIZADO,
    EM_EXECUCAO,
    REAVALIADO,
    VALIDADO,
    AGUARDANDO_VALIDACAO,
    ENCERRADO;

    Status() {
    }

}