document.addEventListener('DOMContentLoaded', function () {
    const baseUrl = 'http://localhost:8080/tecnicos'; // Altere para a URL da sua API

    document.getElementById('formCriarTecnico').addEventListener('submit', async function (e) {
        e.preventDefault();
        const id = document.getElementById('idCriar').value;
        const nome = document.getElementById('nomeCriar').value;
        const email = document.getElementById('emailCriar').value;
        const equipe = document.getElementById('equipeCriar').value;
        const response = await fetch(`${baseUrl}/criar`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ id, nome, email, equipe }),
        });
        const result = await response.json();
        document.getElementById('resultadoCriar').innerText = JSON.stringify(result, null, 2);
    });

    document.getElementById('formBuscarTecnico').addEventListener('submit', async function (e) {
        e.preventDefault();
        const id = document.getElementById('idBuscar').value;
        const response = await fetch(`${baseUrl}/buscar?idTecnico=${id}`);
        const result = await response.json();
        document.getElementById('resultadoBuscar').innerText = JSON.stringify(result, null, 2);
    });

    document.getElementById('formAtualizarTecnico').addEventListener('submit', async function (e) {
        e.preventDefault();
        const id = document.getElementById('idAtualizar').value;
        const nome = document.getElementById('nomeAtualizar').value;
        const email = document.getElementById('emailAtualizar').value;
        const equipe = document.getElementById('equipeAtualizar').value;
        const response = await fetch(`${baseUrl}/atualizar?idTecnico=${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ nome, email, equipe }),
        });
        const result = await response.json();
        document.getElementById('resultadoAtualizar').innerText = JSON.stringify(result, null, 2);
    });

    document.getElementById('formDeletarTecnico').addEventListener('submit', async function (e) {
        e.preventDefault();
        const id = document.getElementById('idDeletar').value;
        const response = await fetch(`${baseUrl}/deletar?idTecnico=${id}`, {
            method: 'DELETE',
        });
        if (response.ok) {
            document.getElementById('resultadoDeletar').innerText = 'Técnico deletado com sucesso.';
        } else {
            const result = await response.json();
            document.getElementById('resultadoDeletar').innerText = JSON.stringify(result, null, 2);
        }
    });

    document.getElementById('btnListarTecnicos').addEventListener('click', async function () {
        const response = await fetch(`${baseUrl}/buscarTodos`);
        const tecnicos = await response.json();
        const lista = document.getElementById('listaTecnicos');
        lista.innerHTML = '';
        tecnicos.forEach(tecnico => {
            const item = document.createElement('li');
            item.textContent = `ID: ${tecnico.id}, Nome: ${tecnico.nome}, Email: ${tecnico.email}, Equipe: ${tecnico.equipe}`;
            lista.appendChild(item);
        });
    });
});
