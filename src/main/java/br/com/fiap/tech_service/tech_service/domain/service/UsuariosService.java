package br.com.fiap.tech_service.tech_service.domain.service;

import br.com.fiap.tech_service.tech_service.application.dto.UsuariosDTO;
import br.com.fiap.tech_service.tech_service.application.mapper.UsuariosMapper;
import br.com.fiap.tech_service.tech_service.domain.entities.Chamados;
import br.com.fiap.tech_service.tech_service.domain.entities.Usuarios;
import br.com.fiap.tech_service.tech_service.domain.repository.ChamadosRepository;
import br.com.fiap.tech_service.tech_service.domain.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuarioRepository;

    @Autowired
    private ChamadosRepository chamadosRepository;

    public List<Usuarios> buscarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuarios criarUsuario(UsuariosDTO usuariosDTO) {
        if (usuariosDTO.id() == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        if (usuarioRepository.existsById(usuariosDTO.id())) {
            throw new IllegalArgumentException("ID já existe");
        }
        Usuarios usuario = UsuariosMapper.toEntity(usuariosDTO);
        return usuarioRepository.save(usuario);
    }

    public Usuarios buscarUsuario(Long idUsuario) {
        if (idUsuario == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + idUsuario));
    }

    public Usuarios atualizarUsuario(UsuariosDTO usuariosDTO) {
        if (usuariosDTO.id() == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        Usuarios usuario = usuarioRepository.findById(usuariosDTO.id())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + usuariosDTO.id()));

        usuario.setNome(usuariosDTO.nome());
        usuario.setEmail(usuariosDTO.email());
        usuario.setEndereco(usuariosDTO.endereco());
        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(Long idUsuario) {
        if (idUsuario == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new IllegalArgumentException("Usuário não encontrado com ID: " + idUsuario);
        }
        List<Chamados> chamados = chamadosRepository.findByUsuarioId(idUsuario);
        chamadosRepository.deleteAll(chamados);

        usuarioRepository.deleteById(idUsuario);
    }
}
