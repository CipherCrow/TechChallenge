package br.com.fiap.tech_service.tech_service.application.controller;

import br.com.fiap.tech_service.tech_service.application.dto.UsuariosDTO;
import br.com.fiap.tech_service.tech_service.domain.entities.Usuarios;
import br.com.fiap.tech_service.tech_service.application.mapper.UsuariosMapper;
import br.com.fiap.tech_service.tech_service.domain.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosService usuarioService;

    @GetMapping("/buscarTodos")
    public ResponseEntity<List<UsuariosDTO>> buscarTodosUsuarios() {
        List<Usuarios> usuario = usuarioService.buscarTodosUsuarios();
        /*Transforma cada usuário da lista em um DTO*/
        return ResponseEntity.ok().body(
                usuario.stream()
                        .map(usuarios -> UsuariosMapper.toDTO(usuarios))
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("/criar")
    public ResponseEntity<UsuariosDTO> criarUsuario(@RequestBody UsuariosDTO usuariosDTO) {
        Usuarios usuario = usuarioService.criarUsuario(usuariosDTO);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(UsuariosMapper.toDTO(usuario));
    }

    @GetMapping("/buscar")
    public ResponseEntity<UsuariosDTO> buscarUsuario(@RequestParam Long idUsuario) {
        Usuarios usuario = usuarioService.buscarUsuario(idUsuario);
        return ResponseEntity.ok(UsuariosMapper.toDTO(usuario));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<UsuariosDTO> atualizarUsuario(@RequestParam Long idUsuario,@RequestBody UsuariosDTO usuariosDTO) {
        Usuarios usuarioAtualizado = usuarioService.atualizarUsuario(idUsuario, usuariosDTO);
        return ResponseEntity.ok(UsuariosMapper.toDTO(usuarioAtualizado));
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Object> deletarUsuario(@RequestParam Long idUsuario) {
        ResponseEntity<Object> usuarioDeletado = usuarioService.deletarUsuario(idUsuario);
        return usuarioDeletado;
    }

}