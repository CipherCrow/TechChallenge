package br.com.fiap.tech_service.tech_service.domain.service;

import br.com.fiap.tech_service.tech_service.domain.entities.Chamados;
import br.com.fiap.tech_service.tech_service.domain.entities.Tecnicos;
import br.com.fiap.tech_service.tech_service.domain.entities.enums.Equipe;
import br.com.fiap.tech_service.tech_service.domain.entities.enums.Status;
import br.com.fiap.tech_service.tech_service.domain.repository.ChamadosRepository;
import br.com.fiap.tech_service.tech_service.domain.repository.TecnicosRepository;
import br.com.fiap.tech_service.tech_service.domain.repository.UsuariosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChamadosService {

    private static final Logger logger = LoggerFactory.getLogger(ChamadosService.class);

    @Autowired
    private ChamadosRepository chamadoRepository;

    @Autowired
    private UsuariosRepository usuarioRepository;

    @Autowired
    private TecnicosRepository tecnicoRepository;

    @Autowired
    private EmailService emailService;

    public Chamados abrirChamado(Long usuarioId, Equipe tipoSolicitacao, String descricao) {
        try {
            logger.info("Abrindo chamado para o usuário ID: {}, tipo de solicitação: {}, descrição: {}", usuarioId, tipoSolicitacao, descricao);

            Chamados chamado = new Chamados();
            chamado.setUsuario(usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado")));
            chamado.setEquipe(tipoSolicitacao);
            chamado.setDescricao(descricao);
            chamado.setStatus(Status.ABERTO);
            chamado.setDataAbertura(LocalDateTime.now());
            chamado.setPriorizado(false);

            Chamados salvoChamado = chamadoRepository.save(chamado);
            logger.info("Chamado aberto com sucesso. ID: {}", salvoChamado.getId());

            String emailUsuario = salvoChamado.getUsuario().getEmail();
            String emailAbertura = emailService.gerarEmailAberturaChamado(salvoChamado.getId(), salvoChamado.getDataAbertura());
            emailService.enviarEmail(emailUsuario, "Seu chamado foi aberto", emailAbertura);
            logger.info("Notificação de abertura enviada para o usuário: {}", emailUsuario);

            String emailEquipe = emailService.obterEmailEquipe(tipoSolicitacao);
            String emailNovoChamado = emailService.gerarEmailNovoChamado(salvoChamado.getId(), descricao);
            emailService.enviarEmail(emailEquipe, "Novo chamado recebido", emailNovoChamado);
            logger.info("Notificação de novo chamado enviada para a equipe: {}", emailEquipe);

            return salvoChamado;
        } catch (Exception e) {
            logger.error("Erro ao abrir chamado: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao abrir chamado: " + e.getMessage(), e);
        }
    }

    public Chamados enviarParaArea(Long chamadoId, Equipe equipe) {
        try {
            logger.info("Enviando chamado ID: {} para a área: {}", chamadoId, equipe);

            Chamados chamado = chamadoRepository.findById(chamadoId)
                    .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
            chamado.setStatus(Status.ENVIADO_PARA_AREA);
            chamado.setEquipe(equipe);

            String emailEquipe = emailService.obterEmailEquipe(equipe);
            String emailChamadoEnviado = emailService.gerarEmailChamadoEnviado(chamado.getId(), equipe);
            emailService.enviarEmail(emailEquipe, "Chamado enviado para a área", emailChamadoEnviado);
            logger.info("Notificação de chamado enviado para a área enviada para a equipe: {}", emailEquipe);

            return chamadoRepository.save(chamado);
        } catch (Exception e) {
            logger.error("Erro ao enviar chamado para a área: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao enviar chamado para a área: " + e.getMessage(), e);
        }
    }

    public Chamados visualizarChamado(Long id) {
        try {
            logger.info("Visualizando chamado ID: {}", id);

            Chamados chamado = chamadoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
            chamado.setDataVisualizacao(LocalDateTime.now());
            chamado.setStatus(Status.VISUALIZADO);

            return chamadoRepository.save(chamado);
        } catch (Exception e) {
            logger.error("Erro ao visualizar chamado: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao visualizar chamado: " + e.getMessage(), e);
        }
    }

    public Chamados tratarChamado(Long id, Long tecnicoId) {
        Chamados chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
        Tecnicos tecnico = tecnicoRepository.findById(tecnicoId)
                .orElseThrow(() -> new RuntimeException("Técnico não encontrado"));

        if (!tecnico.getEquipe().equals(chamado.getEquipe())) {
            throw new RuntimeException("O técnico não pode tratar chamados de outra equipe");
        }

        chamado.setDataTratamento(LocalDateTime.now());
        chamado.setStatus(Status.EM_EXECUCAO);
        chamado.setTecnico(tecnico);

        try {
            String emailUsuario = chamado.getUsuario().getEmail();
            String nomeTecnico = tecnico.getNome();
            String nomeEquipe = tecnico.getEquipe().name();
            String emailTratamento = emailService.gerarEmailTratamentoChamado(chamado.getId(), chamado.getDataTratamento(), nomeTecnico, nomeEquipe);
            emailService.enviarEmail(emailUsuario, "Seu chamado está em tratamento", emailTratamento);
        } catch (Exception e) {
            logger.error("Erro ao enviar e-mail: {}", e.getMessage());
        }

        return chamadoRepository.save(chamado);
    }

    public Chamados solucionarChamado(Long id, String descricaoSolucao) {
        Chamados chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));

        if (!chamado.getStatus().equals(Status.EM_EXECUCAO)) {
            throw new RuntimeException("O chamado deve estar em execucao para ser solucionado");
        }

        chamado.setDescricao(descricaoSolucao);
        chamado.setDataSolucao(LocalDateTime.now());
        chamado.setStatus(Status.AGUARDANDO_VALIDACAO);

        Tecnicos tecnico = tecnicoRepository.findById(chamado.getTecnico().getId())
                .orElseThrow(() -> new RuntimeException("Técnico não encontrado"));

        try {
            String emailUsuario = chamado.getUsuario().getEmail();
            String nomeTecnico = tecnico.getNome();
            String emailSolucao = emailService.gerarEmailSolucaoChamado(chamado.getId(), descricaoSolucao, chamado.getDataSolucao(), nomeTecnico);
            emailService.enviarEmail(emailUsuario, "Seu chamado foi solucionado", emailSolucao);
        } catch (Exception e) {
            logger.error("Erro ao enviar e-mail: {}", e.getMessage());
        }

        return chamadoRepository.save(chamado);
    }

    public Chamados reavaliarChamado(Long id) {
        try {
            logger.info("Reavaliando chamado ID: {}", id);

            Chamados chamado = chamadoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));

            chamado.setDataReavaliacao(LocalDateTime.now());
            chamado.setStatus(Status.REAVALIADO);

            return chamadoRepository.save(chamado);
        } catch (Exception e) {
            logger.error("Erro ao reavaliar chamado: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao reavaliar chamado: " + e.getMessage(), e);
        }
    }

    public Chamados validarChamado(Long chamadoId, boolean isValidado) {
        logger.info("Iniciando validação do chamado com ID: {}", chamadoId);
        try {
            Chamados chamado = chamadoRepository.findById(chamadoId)
                    .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));

            if (isValidado) {
                chamado.setStatus(Status.VALIDADO);
                String emailEquipe = emailService.obterEmailEquipe(chamado.getEquipe());
                String emailChamadoValidado = emailService.gerarEmailValidacaoChamado(chamadoId,chamado.getUsuario().getNome());
                emailService.enviarEmail(emailEquipe, "Chamado validado", emailChamadoValidado);
                logger.info("Chamado ID {} validado com sucesso. Enviando e-mail para a equipe.", chamadoId);
            } else {
                chamado.setStatus(Status.ABERTO);
                chamado.setPriorizado(true);
                String emailEquipe = emailService.obterEmailEquipe(chamado.getEquipe());
                String emailChamadoInsuficiente = emailService.gerarEmailValidacaoChamado(chamadoId,chamado.getUsuario().getNome());
                emailService.enviarEmail(emailEquipe, "Chamado reaberto com prioridade", emailChamadoInsuficiente);
                logger.info("Chamado ID {} não validado. Marcando como priorizado e reabrindo o chamado.", chamadoId);
            }

            return chamadoRepository.save(chamado);
        } catch (Exception e) {
            logger.error("Erro ao valiar chamado: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao validar chamado: " + e.getMessage(), e);

        }
    }


    public Chamados encerrarChamado(Long id) {
        Chamados chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));

        if (!chamado.getStatus().equals(Status.VALIDADO)) {
            throw new RuntimeException("O chamado deve estar validado para ser encerrado");
        }

        chamado.setDataEncerramento(LocalDateTime.now());
        chamado.setStatus(Status.ENCERRADO);

        String emailUsuario = chamado.getUsuario().getEmail();
        String emailEncerramento = emailService.gerarEmailEncerramentoChamado(
                chamado.getId(),
                chamado.getDataEncerramento(),
                chamado.getDescricao(),
                chamado.getTecnico().getNome()
        );
        emailService.enviarEmail(emailUsuario, "Chamado encerrado com sucesso", emailEncerramento);

        return chamadoRepository.save(chamado);
    }

    public List<Chamados> buscarChamadosPorStatus(Status status) {
        try {
            List<Chamados> chamados = chamadoRepository.findByStatus(status);
            logger.info("Busca de chamados por status: " + status + " realizada com sucesso.");
            return chamados;
        } catch (Exception e) {
            logger.error("Erro ao buscar chamados por status: ", e);
            throw new RuntimeException("Não foi possível buscar chamados por status. Tente novamente.");
        }
    }

    public List<Chamados> buscarChamadosPorEquipe(Equipe equipe) {
        try {
            List<Chamados> chamados = chamadoRepository.findByEquipe(equipe);
            logger.info("Busca de chamados por equipe: " + equipe + " realizada com sucesso.");
            return chamados;
        } catch (Exception e) {
            logger.error("Erro ao buscar chamados por equipe: ", e);
            throw new RuntimeException("Não foi possível buscar chamados por equipe. Tente novamente.");
        }
    }
}
