package br.com.fiap.tech_service.tech_service.application.controller;

import br.com.fiap.tech_service.tech_service.application.dto.UsuariosDTO;
import br.com.fiap.tech_service.tech_service.domain.entities.Usuarios;
import br.com.fiap.tech_service.tech_service.application.mapper.UsuariosMapper;
import br.com.fiap.tech_service.tech_service.domain.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosService usuarioService;

    @PostMapping("/criar")
    public UsuariosDTO criarUsuario(@RequestParam String nome, @RequestParam String email) {
        Usuarios usuario = usuarioService.criarUsuario(nome, email);
        return UsuariosMapper.toDTO(usuario);
    }

    @GetMapping("/buscar")
    public UsuariosDTO buscarUsuario(@RequestParam Long idUsuario) {
        Usuarios usuario = usuarioService.buscarUsuario(idUsuario);
        return UsuariosMapper.toDTO(usuario);
    }

    @PutMapping("/atualizar")
    public UsuariosDTO atualizarUsuario(@RequestParam Long idUsuario,@RequestBody UsuariosDTO usuariosDTO) {
        Usuarios usuarioAtualizado = usuarioService.atualizarUsuario(idUsuario, usuariosDTO);
        return UsuariosMapper.toDTO(usuarioAtualizado);
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Object> deletarUsuario(@RequestParam Long idUsuario) {
        ResponseEntity<Object> usuarioDeletado = usuarioService.deletarUsuario(idUsuario);
        return usuarioDeletado;
    }

}