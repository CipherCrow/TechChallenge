package br.com.fiap.tech_service.tech_service.application.controller;

import br.com.fiap.tech_service.tech_service.application.dto.UsuariosDTO;
import br.com.fiap.tech_service.tech_service.domain.entities.Usuarios;
import br.com.fiap.tech_service.tech_service.application.mapper.UsuariosMapper;
import br.com.fiap.tech_service.tech_service.domain.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Object> buscarTodosUsuarios() {
        try {
            List<Usuarios> usuarios = usuarioService.buscarTodosUsuarios();
            if (usuarios.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(
                    usuarios.stream()
                            .map(UsuariosMapper::toDTO)
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar todos os usuários: " + e.getMessage());
        }
    }

    @PostMapping("/criar")
    public ResponseEntity<Object> criarUsuario(@RequestBody UsuariosDTO usuariosDTO) {
        try {
            Usuarios usuario = usuarioService.criarUsuario(usuariosDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Usuário criado com sucesso: " + UsuariosMapper.toDTO(usuario));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Erro: " + ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar usuário: " + e.getMessage());
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<Object> buscarUsuario(@RequestParam Long idUsuario) {
        try {
            Usuarios usuario = usuarioService.buscarUsuario(idUsuario);
            return ResponseEntity.ok(UsuariosMapper.toDTO(usuario));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Erro: " + ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar usuário: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Object> atualizarUsuario(@RequestBody UsuariosDTO usuariosDTO) {
        try {
            Usuarios usuarioAtualizado = usuarioService.atualizarUsuario(usuariosDTO);
            return ResponseEntity.ok("Usuário atualizado com sucesso: " + UsuariosMapper.toDTO(usuarioAtualizado));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Erro: " + ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Object> deletarUsuario(@RequestParam Long idUsuario) {
        try {
            usuarioService.deletarUsuario(idUsuario);
            return ResponseEntity.ok("Usuário deletado com sucesso");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Erro: " + ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar usuário: " + e.getMessage());
        }
    }
}
