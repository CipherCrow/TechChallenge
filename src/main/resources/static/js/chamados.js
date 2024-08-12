document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('formAbrirChamado').addEventListener('submit', function(event) {
        event.preventDefault();
        const usuarioId = document.getElementById('usuarioId').value;
        const tipoSolicitacao = document.getElementById('tipoSolicitacao').value;
        const descricao = document.getElementById('descricao').value;

        fetch('/chamados/abrir', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ usuarioId, tipoSolicitacao, descricao })
        })
        .then(response => response.json())
        .then(data => alert('Chamado aberto com sucesso! ID: ' + data.id))
        .catch(error => alert('Erro ao abrir chamado: ' + error.message));
    });

    document.getElementById('formEnviarParaArea').addEventListener('submit', function(event) {
        event.preventDefault();
        const chamadoId = document.getElementById('chamadoId').value;
        const equipe = document.getElementById('equipe').value;

        fetch(`/chamados/enviar-para-area`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ chamadoId, equipe })
        })
        .then(response => response.json())
        .then(data => alert('Chamado enviado para área com sucesso!'))
        .catch(error => alert('Erro ao enviar chamado para área: ' + error.message));
    });

    document.getElementById('formVisualizarChamado').addEventListener('submit', function(event) {
        event.preventDefault();
        const idChamado = document.getElementById('idChamado').value;

        fetch(`/chamados/${idChamado}`, {
            method: 'GET'
        })
        .then(response => response.json())
        .then(data => {
            document.getElementById('resultadoVisualizarChamado').innerHTML = `
                <p>ID: ${data.id}</p>
                <p>Descrição: ${data.descricao}</p>
                <p>Status: ${data.status}</p>
                <p>Data Abertura: ${data.dataAbertura}</p>
                <p>Data Atualização: ${data.dataAtualizacao}</p>
            `;
        })
        .catch(error => alert('Erro ao visualizar chamado: ' + error.message));
    });

    document.getElementById('formTratarChamado').addEventListener('submit', function(event) {
        event.preventDefault();
        const chamadoId = document.getElementById('chamadoIdTratar').value;
        const observacoes = document.getElementById('observacoes').value;

        fetch(`/chamados/tratar`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ chamadoId, observacoes })
        })
        .then(response => response.json())
        .then(data => alert('Chamado tratado com sucesso!'))
        .catch(error => alert('Erro ao tratar chamado: ' + error.message));
    });

    document.getElementById('formSolucionarChamado').addEventListener('submit', function(event) {
        event.preventDefault();
        const chamadoId = document.getElementById('chamadoIdSolucionar').value;
        const solucao = document.getElementById('solucao').value;

        fetch(`/chamados/solucionar`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ chamadoId, solucao })
        })
        .then(response => response.json())
        .then(data => alert('Chamado solucionado com sucesso!'))
        .catch(error => alert('Erro ao solucionar chamado: ' + error.message));
    });

    document.getElementById('formReavaliarChamado').addEventListener('submit', function(event) {
        event.preventDefault();
        const chamadoId = document.getElementById('chamadoIdReavaliar').value;
        const novaDescricao = document.getElementById('novaDescricao').value;

        fetch(`/chamados/reavaliar`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ chamadoId, novaDescricao })
        })
        .then(response => response.json())
        .then(data => alert('Chamado reavaliado com sucesso!'))
        .catch(error => alert('Erro ao reavaliar chamado: ' + error.message));
    });

    document.getElementById('formValidarChamado').addEventListener('submit', function(event) {
        event.preventDefault();
        const chamadoId = document.getElementById('chamadoIdValidar').value;
        const validado = document.getElementById('validado').value === 'true';

        fetch(`/chamados/validar`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ chamadoId, validado })
        })
        .then(response => response.json())
        .then(data => alert('Chamado validado com sucesso!'))
        .catch(error => alert('Erro ao validar chamado: ' + error.message));
    });

    document.getElementById('formEncerrarChamado').addEventListener('submit', function(event) {
        event.preventDefault();
        const chamadoId = document.getElementById('chamadoIdEncerrar').value;

        fetch(`/chamados/encerrar`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ chamadoId })
        })
        .then(response => response.json())
        .then(data => alert('Chamado encerrado com sucesso!'))
        .catch(error => alert('Erro ao encerrar chamado: ' + error.message));
    });

    document.getElementById('formBuscarStatus').addEventListener('submit', function(event) {
        event.preventDefault();
        const status = document.getElementById('status').value;

        fetch(`/chamados/status/${status}`, {
            method: 'GET'
        })
        .then(response => response.json())
        .then(data => {
            const resultHtml = data.map(chamado => `
                <div>
                    <p>ID: ${chamado.id}</p>
                    <p>Descrição: ${chamado.descricao}</p>
                    <p>Status: ${chamado.status}</p>
                    <p>Data Abertura: ${chamado.dataAbertura}</p>
                </div>
            `).join('');
            document.getElementById('resultadoBuscaStatus').innerHTML = resultHtml;
        })
        .catch(error => alert('Erro ao buscar chamados por status: ' + error.message));
    });

    document.getElementById('formBuscarEquipe').addEventListener('submit', function(event) {
        event.preventDefault();
        const equipe = document.getElementById('equipeBuscar').value;

        fetch(`/chamados/equipe/${equipe}`, {
            method: 'GET'
        })
        .then(response => response.json())
        .then(data => {
            const resultHtml = data.map(chamado => `
                <div>
                    <p>ID: ${chamado.id}</p>
                    <p>Descrição: ${chamado.descricao}</p>
                    <p>Status: ${chamado.status}</p>
                    <p>Data Abertura: ${chamado.dataAbertura}</p>
                </div>
            `).join('');
            document.getElementById('resultadoBuscaEquipe').innerHTML = resultHtml;
        })
        .catch(error => alert('Erro ao buscar chamados por equipe: ' + error.message));
    });
});
