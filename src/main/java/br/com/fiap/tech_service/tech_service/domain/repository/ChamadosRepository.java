package br.com.fiap.tech_service.tech_service.domain.repository;


import br.com.fiap.tech_service.tech_service.domain.entities.Chamados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChamadosRepository extends JpaRepository<Chamados, Long> {
}