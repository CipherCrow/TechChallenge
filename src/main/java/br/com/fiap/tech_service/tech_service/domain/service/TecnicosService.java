package br.com.fiap.tech_service.tech_service.domain.service;

import br.com.fiap.tech_service.tech_service.domain.entities.Tecnicos;
import br.com.fiap.tech_service.tech_service.domain.entities.enums.Equipe;
import br.com.fiap.tech_service.tech_service.domain.repository.TecnicosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TecnicosService {

    @Autowired
    private TecnicosRepository tecnicoRepository;

    public Tecnicos criarTecnico(String nome, String email, Equipe equipe) {
        Tecnicos tecnico = new Tecnicos();
        tecnico.setNome(nome);
        tecnico.setEmail(email);
        tecnico.setEquipe(equipe);
        return tecnicoRepository.save(tecnico);
    }

    public Tecnicos buscarTecnico (Long idTecnico) {
        Tecnicos tecnico = new Tecnicos();
        tecnico = tecnicoRepository.findById(idTecnico).orElseThrow(() -> new RuntimeException("Tecnico não encontrado"));
        return tecnico;
    }
}