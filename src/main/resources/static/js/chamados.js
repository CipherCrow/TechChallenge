    const apiUrl = 'http://localhost:8080/chamados';

    function abrirChamado() {
        const usuarioId = document.getElementById('usuario-id').value;
        const tipoSolicitacao = document.getElementById('tipo-solicitacao').value;
        const descricao = document.getElementById('descricao').value;

        fetch(`${apiUrl}/abrir`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ usuarioId, tipoSolicitacao, descricao })
        })
        .then(response => response.json())
        .then(data => document.getElementById('resultado-abrir').innerText = `Chamado aberto: ${JSON.stringify(data)}`)
        .catch(error => document.getElementById('resultado-abrir').innerText = `Erro: ${error.message}`);
    }

    function enviarParaArea() {
        const id = document.getElementById('id-enviar').value;
        const equipe = document.getElementById('equipe-enviar').value;

        fetch(`${apiUrl}/enviarParaArea/${id}?equipe=${equipe}`, { method: 'PUT' })
        .then(response => response.json())
        .then(data => document.getElementById('resultado-enviar').innerText = `Chamado enviado: ${JSON.stringify(data)}`)
        .catch(error => document.getElementById('resultado-enviar').innerText = `Erro: ${error.message}`);
    }

    function visualizarChamado() {
        const id = document.getElementById('id-visualizar').value;

        fetch(`${apiUrl}/visualizar/${id}`, { method: 'PUT' })
        .then(response => response.json())
        .then(data => document.getElementById('resultado-visualizar').innerText = `Chamado visualizado: ${JSON.stringify(data)}`)
        .catch(error => document.getElementById('resultado-visualizar').innerText = `Erro: ${error.message}`);
    }

    function tratarChamado() {
        const idChamado = document.getElementById('id-tratar').value;
        const idTecnico = document.getElementById('id-tecnico-tratar').value;

        fetch(`${apiUrl}/tratar/${idChamado}/${idTecnico}`, { method: 'PUT' })
        .then(response => response.json())
        .then(data => document.getElementById('resultado-tratar').innerText = `Chamado tratado: ${JSON.stringify(data)}`)
        .catch(error => document.getElementById('resultado-tratar').innerText = `Erro: ${error.message}`);
    }

    function solucionarChamado() {
        const id = document.getElementById('id-solucionar').value;
        const descricao = document.getElementById('descricao-solucionar').value;

        fetch(`${apiUrl}/solucionar/${id}?descricao=${descricao}`, { method: 'PUT' })
        .then(response => response.json())
        .then(data => document.getElementById('resultado-solucionar').innerText = `Chamado solucionado: ${JSON.stringify(data)}`)
        .catch(error => document.getElementById('resultado-solucionar').innerText = `Erro: ${error.message}`);
    }

    function reavaliarChamado() {
        const id = document.getElementById('id-reavaliar').value;

        fetch(`${apiUrl}/reavaliar/${id}`, { method: 'PUT' })
        .then(response => response.json())
        .then(data => document.getElementById('resultado-reavaliar').innerText = `Chamado reavaliado: ${JSON.stringify(data)}`)
        .catch(error => document.getElementById('resultado-reavaliar').innerText = `Erro: ${error.message}`);
    }

    function validarChamado() {
        const id = document.getElementById('id-validar').value;
        const isValidado = document.getElementById('is-validado').value;

        fetch(`${apiUrl}/validar/${id}?isValidado=${isValidado}`, { method: 'PUT' })
        .then(response => response.json())
        .then(data => document.getElementById('resultado-validar').innerText = `Chamado validado: ${JSON.stringify(data)}`)
        .catch(error => document.getElementById('resultado-validar').innerText = `Erro: ${error.message}`);
    }

    function encerrarChamado() {
        const id = document.getElementById('id-encerrar').value;

        fetch(`${apiUrl}/encerrar/${id}`, { method: 'PUT' })
         .then(response => {
            if (!response.ok) {
                return response.text().then(error => Promise.reject(new Error(error)));
            }
            return response.text();
        })
        .then(message => document.getElementById('resultado-encerrar').innerText = message)
        .catch(error => document.getElementById('resultado-encerrar').innerText = `Erro: ${error.message}`);
    }

    function buscarChamadosPorStatus() {
        const status = document.getElementById('status-buscar').value;

        fetch(`${apiUrl}/status/${status}`, { method: 'GET' })
        .then(response => response.json())
        .then(data => document.getElementById('resultado-buscar-status').innerText = `Chamados encontrados: ${JSON.stringify(data)}`)
        .catch(error => document.getElementById('resultado-buscar-status').innerText = `Erro: ${error.message}`);
    }

    function buscarChamadosPorEquipe() {
        const equipe = document.getElementById('equipe-buscar').value;

        fetch(`${apiUrl}/equipe?equipe=${equipe}`, { method: 'GET' })
        .then(response => response.json())
        .then(data => document.getElementById('resultado-buscar-equipe').innerText = `Chamados encontrados: ${JSON.stringify(data)}`)
        .catch(error => document.getElementById('resultado-buscar-equipe').innerText = `Erro: ${error.message}`);
    }