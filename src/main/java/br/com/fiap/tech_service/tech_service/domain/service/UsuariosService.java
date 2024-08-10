package br.com.fiap.tech_service.tech_service.domain.service;

import br.com.fiap.tech_service.tech_service.application.dto.UsuariosDTO;
import br.com.fiap.tech_service.tech_service.application.mapper.UsuariosMapper;
import br.com.fiap.tech_service.tech_service.domain.entities.Usuarios;
import br.com.fiap.tech_service.tech_service.domain.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuarioRepository;

    public List<Usuarios> buscarTodosUsuarios() {
        List<Usuarios> usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    public Usuarios criarUsuario(String nome, String email) {
        Usuarios usuario = new Usuarios();
        usuario.setNome(nome);
        usuario.setEmail(email);
        return usuarioRepository.save(usuario);
    }

    public Usuarios buscarUsuario(long idUsuario) {
        Usuarios usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return usuario;
    }

    public Usuarios atualizarUsuario(long idUsuario, UsuariosDTO usuariosDTO) {
        Usuarios usuario = buscarUsuario(idUsuario);
        usuario.setNome(usuariosDTO.nome());
        usuario.setEmail(usuariosDTO.email());
        usuario = usuarioRepository.save(usuario);
        return usuario;
    }
    public ResponseEntity<Object> deletarUsuario(long idUsuario) {
        Usuarios usuario =  buscarUsuario(idUsuario);
        usuarioRepository.delete(usuario);
        return ResponseEntity.accepted().build();
    }
}