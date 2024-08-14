package br.com.fiap.tech_service.tech_service.domain.service;

import br.com.fiap.tech_service.tech_service.application.dto.UsuariosDTO;
import br.com.fiap.tech_service.tech_service.application.mapper.UsuariosMapper;
import br.com.fiap.tech_service.tech_service.domain.entities.Chamados;
import br.com.fiap.tech_service.tech_service.domain.entities.Usuarios;
import br.com.fiap.tech_service.tech_service.domain.exceptions.UsuarioNotFoundException;
import br.com.fiap.tech_service.tech_service.domain.repository.ChamadosRepository;
import br.com.fiap.tech_service.tech_service.domain.repository.UsuariosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UsuariosService {
    private static final Logger logger = LoggerFactory.getLogger(UsuariosService.class);

    @Autowired
    private UsuariosRepository usuarioRepository;

    @Autowired
    private ChamadosRepository chamadosRepository;

    public List<Usuarios> buscarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuarios criarUsuario(UsuariosDTO usuariosDTO) {
        logger.info("Criando usuario ID: {}", usuariosDTO.id());
        Objects.requireNonNull(usuariosDTO.id(), "ID não pode ser nulo");
        if (usuarioRepository.existsById(usuariosDTO.id())) {
            logger.info("ID ja existe. ID: {}", usuariosDTO.id());
            throw new IllegalArgumentException("ID já existe");

        }
        Usuarios usuario = UsuariosMapper.toEntity(usuariosDTO);
        return usuarioRepository.save(usuario);
    }

    public Usuarios buscarUsuario(Long idUsuario) {
        Objects.requireNonNull(idUsuario, "ID não pode ser nulo");
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException(idUsuario));
    }

    public Usuarios atualizarUsuario(UsuariosDTO usuariosDTO) {
        Objects.requireNonNull(usuariosDTO.id(), "ID não pode ser nulo");
        Usuarios usuario = usuarioRepository.findById(usuariosDTO.id())
                .orElseThrow(() -> new UsuarioNotFoundException(usuariosDTO.id()));

        usuario.setNome(usuariosDTO.nome());
        usuario.setEmail(usuariosDTO.email());
        usuario.setEndereco(usuariosDTO.endereco());
        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(Long idUsuario) {
        Objects.requireNonNull(idUsuario, "ID não pode ser nulo");
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new UsuarioNotFoundException(idUsuario);
        }
        List<Chamados> chamados = chamadosRepository.findByUsuarioId(idUsuario);
        chamadosRepository.deleteAll(chamados);

        usuarioRepository.deleteById(idUsuario);
    }

}
