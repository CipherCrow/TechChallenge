package br.com.fiap.tech_service.tech_service.domain.service;

import br.com.fiap.tech_service.tech_service.application.dto.TecnicosDTO;
import br.com.fiap.tech_service.tech_service.application.mapper.TecnicosMapper;
import br.com.fiap.tech_service.tech_service.domain.entities.Chamados;
import br.com.fiap.tech_service.tech_service.domain.entities.Tecnicos;
import br.com.fiap.tech_service.tech_service.domain.repository.ChamadosRepository;
import br.com.fiap.tech_service.tech_service.domain.repository.TecnicosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TecnicosService {

    @Autowired
    private TecnicosRepository tecnicoRepository;

    @Autowired
    private ChamadosRepository chamadosRepository;

    public List<Tecnicos> buscarTodosTecnicos() {
        return tecnicoRepository.findAll();
    }

    public Tecnicos criarTecnico(TecnicosDTO tecnicosDTO) {
        if (tecnicosDTO.id() == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        if (tecnicoRepository.existsById(tecnicosDTO.id())) {
            throw new IllegalArgumentException("ID já existe");
        }
        Tecnicos tecnico = TecnicosMapper.toEntity(tecnicosDTO);
        return tecnicoRepository.save(tecnico);
    }

    public Tecnicos buscarTecnico(Long idTecnico) {
        if (idTecnico == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return tecnicoRepository.findById(idTecnico)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + idTecnico));
    }

    public Tecnicos atualizarTecnico(TecnicosDTO tecnicosDTO) {
        if (tecnicosDTO.id() == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        Tecnicos tecnico = tecnicoRepository.findById(tecnicosDTO.id())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + tecnicosDTO.id()));

        tecnico.setNome(tecnicosDTO.nome());
        tecnico.setEmail(tecnicosDTO.email());
        return tecnicoRepository.save(tecnico);
    }

    public void deletarTecnico(Long idTecnico) {
        if (idTecnico == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        if (!tecnicoRepository.existsById(idTecnico)) {
            throw new IllegalArgumentException("Técnico não encontrado com ID: " + idTecnico);
        }
        List<Chamados> chamados = chamadosRepository.findByTecnicoId(idTecnico);
        chamadosRepository.deleteAll(chamados);

        tecnicoRepository.deleteById(idTecnico);
    }
}
