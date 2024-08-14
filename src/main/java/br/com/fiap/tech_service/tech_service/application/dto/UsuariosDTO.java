package br.com.fiap.tech_service.tech_service.application.dto;

public record UsuariosDTO(
        Long id,
        String nome,
        String email,
        String endereco
) {}