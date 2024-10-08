package br.com.fiap.tech_service.tech_service.domain.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "tb_usuarios")
public class Usuarios {

    @Id
    private Long id;
    private String nome;
    private String email;
    private String endereco;

   public Usuarios() {}

    public Usuarios(Long id, String nome, String email, String endereco) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
    }

    public String getEndereco() { return endereco; }

    public void setEndereco(String endereco) { this.endereco = endereco; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {return nome;}

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}