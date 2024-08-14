package br.com.fiap.tech_service.tech_service.application.mapper;


import br.com.fiap.tech_service.tech_service.application.dto.UsuariosDTO;
import br.com.fiap.tech_service.tech_service.domain.entities.Usuarios;

public class UsuariosMapper {

    public static UsuariosDTO toDTO(Usuarios usuario) {
        return new UsuariosDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getEndereco()
        );
    }

    public static Usuarios toEntity(UsuariosDTO dto) {
        Usuarios usuario = new Usuarios();
        usuario.setId(dto.id());
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setEndereco(dto.endereco());
        return usuario;
    }
}
