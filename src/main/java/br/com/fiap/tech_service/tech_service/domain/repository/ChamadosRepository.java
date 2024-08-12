package br.com.fiap.tech_service.tech_service.domain.repository;


import br.com.fiap.tech_service.tech_service.domain.entities.Chamados;
import br.com.fiap.tech_service.tech_service.domain.entities.enums.Equipe;
import br.com.fiap.tech_service.tech_service.domain.entities.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChamadosRepository extends JpaRepository<Chamados, Long> {

    List<Chamados> findByStatus(Status status);
    List<Chamados> findByEquipe(Equipe equipe);
    List<Chamados> findByUsuarioId(Long idUsuario);
}