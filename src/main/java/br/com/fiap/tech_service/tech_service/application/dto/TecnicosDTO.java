package br.com.fiap.tech_service.tech_service.application.dto;

public record TecnicosDTO(
        Long id,
        String nome,
        String email,
        br.com.fiap.tech_service.tech_service.domain.entities.enums.Equipe equipe) {}