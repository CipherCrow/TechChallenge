package br.com.fiap.tech_service.tech_service.domain.service;

import br.com.fiap.tech_service.tech_service.application.dto.TecnicosDTO;
import br.com.fiap.tech_service.tech_service.application.mapper.TecnicosMapper;
import br.com.fiap.tech_service.tech_service.domain.entities.Tecnicos;
import br.com.fiap.tech_service.tech_service.domain.repository.TecnicosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TecnicosService {

    @Autowired
    private TecnicosRepository tecnicoRepository;

    public List<Tecnicos> buscarTodosTecnicos() {
        return tecnicoRepository.findAll();
    }

    public Tecnicos criarTecnico(TecnicosDTO tecnicosDTO) {
        if (tecnicoRepository.existsById(tecnicosDTO.id())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "ID fornecido já existe");
        }
        Tecnicos tecnico = TecnicosMapper.toEntity(tecnicosDTO);
        return tecnicoRepository.save(tecnico);
    }

    public Tecnicos buscarTecnico(Long idTecnico) {
        return tecnicoRepository.findById(idTecnico)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Técnico não encontrado com ID: " + idTecnico));
    }

    public Tecnicos atualizarTecnico(Long idTecnico, TecnicosDTO tecnicosDTO) {
        if (tecnicosDTO.id() == null || !idTecnico.equals(tecnicosDTO.id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id INCORRETO: " + idTecnico);
        }
        if (!tecnicoRepository.existsById(idTecnico)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Técnico não encontrado com ID: " + idTecnico);
        }
        Tecnicos tecnico = tecnicoRepository.findById(idTecnico).get();
        tecnico.setNome(tecnicosDTO.nome());
        tecnico.setEmail(tecnicosDTO.email());
        tecnico.setEquipe(tecnicosDTO.equipe());
        return tecnicoRepository.save(tecnico);
    }

    public void deletarTecnico(Long idTecnico) {
        if (!tecnicoRepository.existsById(idTecnico)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Técnico não encontrado com ID: " + idTecnico);
        }
        tecnicoRepository.deleteById(idTecnico);
    }
}
