package br.com.fiap.tech_service.tech_service.domain.service;

import br.com.fiap.tech_service.tech_service.application.dto.TecnicosDTO;
import br.com.fiap.tech_service.tech_service.application.mapper.TecnicosMapper;
import br.com.fiap.tech_service.tech_service.domain.entities.Chamados;
import br.com.fiap.tech_service.tech_service.domain.entities.Tecnicos;
import br.com.fiap.tech_service.tech_service.domain.entities.enums.Status;
import br.com.fiap.tech_service.tech_service.domain.exceptions.TecnicoNotFoundException;
import br.com.fiap.tech_service.tech_service.domain.repository.ChamadosRepository;
import br.com.fiap.tech_service.tech_service.domain.repository.TecnicosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TecnicosService {

    @Autowired
    private TecnicosRepository tecnicoRepository;

    @Autowired
    private ChamadosRepository chamadosRepository;
    @Autowired
    private ChamadosService chamadosService;

    public List<Tecnicos> buscarTodosTecnicos() {
        return tecnicoRepository.findAll();
    }

    public Tecnicos criarTecnico(TecnicosDTO tecnicosDTO) {
        Objects.requireNonNull(tecnicosDTO.id(), "ID não pode ser nulo");
        if (tecnicoRepository.existsById(tecnicosDTO.id())) {
            throw new IllegalArgumentException("ID já existe");
        }
        Tecnicos tecnico = TecnicosMapper.toEntity(tecnicosDTO);
        return tecnicoRepository.save(tecnico);
    }

    public Tecnicos buscarTecnico(Long idTecnico) {
        Objects.requireNonNull(idTecnico, "ID não pode ser nulo");
        return tecnicoRepository.findById(idTecnico)
                .orElseThrow(() -> new TecnicoNotFoundException(idTecnico));
    }

    public Tecnicos atualizarTecnico(TecnicosDTO tecnicosDTO) {
        Objects.requireNonNull(tecnicosDTO.id(), "ID não pode ser nulo");
        Tecnicos tecnico = tecnicoRepository.findById(tecnicosDTO.id())
                .orElseThrow(() -> new TecnicoNotFoundException(tecnicosDTO.id()));

        tecnico.setNome(tecnicosDTO.nome());
        tecnico.setEmail(tecnicosDTO.email());
        tecnico.setEquipe(tecnicosDTO.equipe());
        return tecnicoRepository.save(tecnico);
    }

    public void deletarTecnico(Long idTecnico) {
        Objects.requireNonNull(idTecnico, "ID não pode ser nulo");
        if (!tecnicoRepository.existsById(idTecnico)) {
            throw new TecnicoNotFoundException(idTecnico);
        }
        List<Chamados> chamados = chamadosRepository.findByTecnicoId(idTecnico);
        //chamadosRepository.deleteAll(chamados);

        if(!chamados.isEmpty() && chamados != null){
            try{
                chamadosService.removerAtendenteDosChamados(chamados);
                chamadosService.alterarStatusDosChamados(chamados, Status.ABERTO);
            }catch (Exception e){
                throw new RuntimeException("Houve um problema ao modificar os agendamentos do tecnico.");
            }
        }
        tecnicoRepository.deleteById(idTecnico);
    }
}
