package br.com.fiap.tech_service.tech_service.domain.exceptions;

public class ChamadoNotFoundException extends RuntimeException {
    public ChamadoNotFoundException(String message) {
        super(message);
    }
}
