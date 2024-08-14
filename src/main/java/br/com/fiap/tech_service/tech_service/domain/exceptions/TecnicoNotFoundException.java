package br.com.fiap.tech_service.tech_service.domain.exceptions;

public class TecnicoNotFoundException extends RuntimeException {
    public TecnicoNotFoundException(Long id) {
        super("Técnico não encontrado com ID: " + id);
    }
}

