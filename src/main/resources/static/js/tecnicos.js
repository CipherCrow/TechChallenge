
        const apiUrl = 'http://localhost:8080/tecnicos';

function buscarTodosTecnicos() {
    fetch(`${apiUrl}/buscarTodos`)
        .then(response => {
            if (!response.ok) {
                return response.text().then(error => Promise.reject(new Error(error)));
            }
            return response.json();
        })
        .then(data => {
            const lista = data.map(tecnico => `
                <tr>
                    <td>${tecnico.id}</td>
                    <td>${tecnico.nome}</td>
                    <td>${tecnico.email}</td>
                </tr>
            `).join('');
            document.getElementById('todos-tecnicos').innerHTML = `
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>Email</th>
                        </tr>
                    </thead>
                    <tbody>${lista}</tbody>
                </table>
            `;
        })
        .catch(error => document.getElementById('todos-tecnicos').innerText = `Erro: ${error.message}`);
}

function criarTecnico() {
    const nome = document.getElementById('nome').value;
    const email = document.getElementById('email').value;
    const id = document.getElementById('id').value;

    fetch(`${apiUrl}/criar`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ nome, email, id })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(error => Promise.reject(new Error(error)));
            }
            return response.json();
        })
        .then(data => document.getElementById('resultado-criar').innerText = `Técnico criado: ${JSON.stringify(data)}`)
        .catch(error => document.getElementById('resultado-criar').innerText = `Erro: ${error.message}`);
}

function buscarTecnico() {
    const idTecnico = document.getElementById('id-buscar').value;

    fetch(`${apiUrl}/buscar?idTecnico=${idTecnico}`)
        .then(response => {
            if (!response.ok) {
                return response.text().then(error => Promise.reject(new Error(error)));
            }
            return response.json();
        })
        .then(data => document.getElementById('resultado-buscar').innerText = `Técnico encontrado: ${JSON.stringify(data)}`)
        .catch(error => document.getElementById('resultado-buscar').innerText = `Erro: ${error.message}`);
}

function atualizarTecnico() {
    const id = document.getElementById('id-atualizar').value;
    const nome = document.getElementById('nome-atualizar').value;
    const email = document.getElementById('email-atualizar').value;
    const equipe = document.getElementById('equipe-atualizar').value;

    fetch(`${apiUrl}/atualizar`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ id, nome, email,equipe })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(error => Promise.reject(new Error(error)));
            }
            return response.json();
        })
        .then(data => document.getElementById('resultado-atualizar').innerText = `Técnico atualizado: ${JSON.stringify(data)}`)
        .catch(error => document.getElementById('resultado-atualizar').innerText = `Erro: ${error.message}`);
}

function deletarTecnico() {
    const idTecnico = document.getElementById('id-deletar').value;

    fetch(`${apiUrl}/deletar?idTecnico=${idTecnico}`, { method: 'DELETE' })
        .then(response => {
            if (!response.ok) {
                return response.text().then(error => Promise.reject(new Error(error)));
            }
            return response.text();
        })
        .then(message => document.getElementById('resultado-deletar').innerText = message)
        .catch(error => document.getElementById('resultado-deletar').innerText = `Erro: ${error.message}`);
}