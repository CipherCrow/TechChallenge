package br.com.fiap.tech_service.tech_service.domain.entities;

import br.com.fiap.tech_service.tech_service.domain.entities.enums.Equipe;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_tecnicos")
public class Tecnicos {

    @Id
    private Long id;
    private String nome;
    private String email;

    @Enumerated(EnumType.STRING)
    private Equipe equipe;

    public Tecnicos() {}

    public Tecnicos(Long id, String nome, String email, Equipe equipe) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.equipe = equipe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Equipe getEquipe() {
        return equipe;
    }
    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }
}

