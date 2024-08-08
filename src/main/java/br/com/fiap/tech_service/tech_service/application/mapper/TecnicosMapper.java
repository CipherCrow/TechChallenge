package br.com.fiap.tech_service.tech_service.application.mapper;

import br.com.fiap.tech_service.tech_service.application.dto.TecnicosDTO;
import br.com.fiap.tech_service.tech_service.domain.entities.Tecnicos;

public class TecnicosMapper {

    public static TecnicosDTO toDTO(Tecnicos tecnico) {
        return new TecnicosDTO(
                tecnico.getId(),
                tecnico.getNome(),
                tecnico.getEmail(),
                tecnico.getEquipe()
        );
    }

    public static Tecnicos toEntity(TecnicosDTO dto) {
        Tecnicos tecnico = new Tecnicos();
        tecnico.setId(dto.id());
        tecnico.setNome(dto.nome());
        tecnico.setEmail(dto.email());
        tecnico.setEquipe(dto.equipe());
        return tecnico;
    }


}