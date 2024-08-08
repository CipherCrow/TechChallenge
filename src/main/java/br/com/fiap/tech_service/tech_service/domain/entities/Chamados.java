package br.com.fiap.tech_service.tech_service.domain.entities;

import br.com.fiap.tech_service.tech_service.domain.entities.enums.Equipe;
import br.com.fiap.tech_service.tech_service.domain.entities.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_chamados")
public class Chamados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tb_usuarios_id")
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "tb_tecnicos_id")
    private Tecnicos tecnico;

    @Enumerated(EnumType.STRING)
    private Equipe tipoSolicitacao;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime dataAbertura;
    private LocalDateTime dataVisualizacao;
    private LocalDateTime dataTratamento;
    private LocalDateTime dataSolucao;
    private LocalDateTime dataReavaliacao;
    private LocalDateTime dataEncerramento;

    public Chamados(Long id, Usuarios usuario, Tecnicos tecnico, Equipe tipoSolicitacao, String descricao,
                    Status status, LocalDateTime dataAbertura, LocalDateTime dataVisualizacao, LocalDateTime dataTratamento,
                    LocalDateTime dataSolucao, LocalDateTime dataReavaliacao, LocalDateTime dataEncerramento) {
        this.id = id;
        this.usuario = usuario;
        this.tecnico = tecnico;
        this.tipoSolicitacao = tipoSolicitacao;
        this.descricao = descricao;
        this.status = status;
        this.dataAbertura = dataAbertura;
        this.dataVisualizacao = dataVisualizacao;
        this.dataTratamento = dataTratamento;
        this.dataSolucao = dataSolucao;
        this.dataReavaliacao = dataReavaliacao;
        this.dataEncerramento = dataEncerramento;
    }

    public Chamados() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Tecnicos getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnicos tecnico) {
        this.tecnico = tecnico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDateTime getDataVisualizacao() {
        return dataVisualizacao;
    }

    public void setDataVisualizacao(LocalDateTime dataVisualizacao) {
        this.dataVisualizacao = dataVisualizacao;
    }

    public LocalDateTime getDataTratamento() {
        return dataTratamento;
    }

    public void setDataTratamento(LocalDateTime dataTratamento) {
        this.dataTratamento = dataTratamento;
    }

    public LocalDateTime getDataSolucao() {
        return dataSolucao;
    }

    public void setDataSolucao(LocalDateTime dataSolucao) {
        this.dataSolucao = dataSolucao;
    }

    public LocalDateTime getDataReavaliacao() {
        return dataReavaliacao;
    }

    public void setDataReavaliacao(LocalDateTime dataReavaliacao) {
        this.dataReavaliacao = dataReavaliacao;
    }

    public LocalDateTime getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(LocalDateTime dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public Equipe getTipoSolicitacao() {
        return tipoSolicitacao;
    }

    public void setTipoSolicitacao(Equipe tipoSolicitacao) {
        this.tipoSolicitacao = tipoSolicitacao;
    }
}