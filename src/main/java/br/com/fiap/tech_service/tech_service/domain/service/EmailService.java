package br.com.fiap.tech_service.tech_service.domain.service;

import br.com.fiap.tech_service.tech_service.domain.entities.enums.Equipe;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private static final String EMAIL_REMETENTE = "techservice@fiap.com";

    public void enviarEmail(String destinatario, String assunto, String conteudoHtml) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(EMAIL_REMETENTE);
            messageHelper.setTo(destinatario);
            messageHelper.setSubject(assunto);
            messageHelper.setText(conteudoHtml, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Falha ao enviar e-mail", e);
        }
    }

    public String gerarEmailAberturaChamado(Long chamadoId, LocalDateTime dataAbertura) {
        return "<html><body>" +
                "<h1>Chamado Aberto</h1>" +
                "<p>Seu chamado foi aberto com sucesso.</p>" +
                "<p>Número do chamado: " + chamadoId + "</p>" +
                "<p>Data de abertura: " + dataAbertura + "</p>" +
                "<p>Obrigado por entrar em contato!</p>" +
                "</body></html>";
    }

    public String gerarEmailNovoChamado(Long chamadoId, String descricao) {
        return "<html><body>" +
                "<h1>Novo Chamado Recebido</h1>" +
                "<p>Um novo chamado foi aberto.</p>" +
                "<p>Número do chamado: " + chamadoId + "</p>" +
                "<p>Descrição: " + descricao + "</p>" +
                "<p>Por favor, verifique o sistema para mais detalhes.</p>" +
                "</body></html>";
    }

    public String gerarEmailChamadoEnviado(Long chamadoId, Equipe equipe) {
        return "<html><body>" +
                "<h1>Chamado Enviado para sua Área</h1>" +
                "<p>Um chamado foi enviado para a sua equipe.</p>" +
                "<p>Número do chamado: " + chamadoId + "</p>" +
                "<p>Equipe responsável: " + equipe + "</p>" +
                "<p>Por favor, verifique o sistema para mais detalhes.</p>" +
                "</body></html>";
    }

    public String gerarEmailTratamentoChamado(Long chamadoId, LocalDateTime dataTratamento, String nomeTecnico, String nomeEquipe) {
        return String.format(
                "<html><body><p>Seu chamado está em tratamento.</p>" +
                        "<p>Id do chamado: %d</p><p>Data de tratamento: %s</p>" +
                        "<p>Técnico responsável: %s</p><p>Equipe: %s</p></body></html>",
                chamadoId, dataTratamento, nomeTecnico, nomeEquipe
        );
    }

    public String gerarEmailSolucaoChamado(Long chamadoId, String descricaoSolucao, LocalDateTime dataSolucao, String nomeTecnico) {
        return String.format(
                "<html><body><p>Seu chamado foi solucionado.</p>" +
                        "<p>Id do chamado: %d</p><p>Descrição da solução: %s</p>" +
                        "<p>Data da solução: %s</p><p>Técnico responsável: %s</p></body></html>",
                chamadoId, descricaoSolucao, dataSolucao, nomeTecnico
        );
    }

    public String gerarEmailValidacaoChamado(Long chamadoId,String usuario, Equipe equipe, String status) {
        return "<html>" +
                "<body>" +
                "<h1>Chamado Validado com status:"+status+"</h1>" +
                "<p>O usuário <strong>" + usuario + "</strong> validou o chamado de ID: <strong>" + chamadoId + "</strong>.</p>" +
                "<p>O chamado está pronto para ser analisado pelo tecnico.</p>" +
                "</body>" +
                "</html>";
    }

    public String gerarEmailEncerramentoChamado(Long chamadoId, LocalDateTime dataEncerramento, String descricao, String nomeTecnico) {
        return "<html>" +
                "<body>" +
                "<h1>Chamado Encerrado</h1>" +
                "<p>O chamado de ID: <strong>" + chamadoId + "</strong> foi encerrado com sucesso.</p>" +
                "<p><strong>Data de Encerramento:</strong> " + dataEncerramento + "</p>" +
                "<p><strong>Descrição:</strong> " + descricao + "</p>" +
                "<p><strong>Técnico Responsável:</strong> " + nomeTecnico + "</p>" +
                "</body>" +
                "</html>";
    }

    public String obterEmailEquipe(Equipe equipe) {
        switch (equipe) {
            case ATENDIMENTO:
                return "techservice_atendimento@fiap.com";
            case N3:
                return "techservice_n3@fiap.com";
            case PRESENCIAL:
                return "techservice_presencial@fiap.com";
            default:
                throw new RuntimeException("Equipe nao encontrada");
        }
    }
}
