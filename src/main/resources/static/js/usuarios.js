
        const apiUrl = 'http://localhost:8080/usuarios';

function buscarTodosUsuarios() {
    fetch(`${apiUrl}/buscarTodos`)
        .then(response => {
            if (!response.ok) {
                return response.text().then(error => Promise.reject(new Error(error)));
            }
            return response.json();
        })
        .then(data => {
            const lista = data.map(usuario => `
                <tr>
                    <td>${usuario.id}</td>
                    <td>${usuario.nome}</td>
                    <td>${usuario.email}</td>
                    <td>${usuario.endereco}</td>
                </tr>
            `).join('');
            document.getElementById('todos-usuarios').innerHTML = `
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>Email</th>
                            <th>Endereço</th>
                        </tr>
                    </thead>
                    <tbody>${lista}</tbody>
                </table>
            `;
        })
        .catch(error => document.getElementById('todos-usuarios').innerText = `Erro: ${error.message}`);
}

function criarUsuario() {
    const nome = document.getElementById('nome').value;
    const email = document.getElementById('email').value;
    const endereco = document.getElementById('endereco').value;
    const id = document.getElementById('id').value;

    fetch(`${apiUrl}/criar`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ nome, email, endereco, id })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(error => Promise.reject(new Error(error)));
            }
            return response.json();
        })
        .then(data => document.getElementById('resultado-criar').innerText = `Usuário criado: ${JSON.stringify(data)}`)
        .catch(error => document.getElementById('resultado-criar').innerText = `Erro: ${error.message}`);
}

function buscarUsuario() {
    const idUsuario = document.getElementById('id-buscar').value;

    fetch(`${apiUrl}/buscar?idUsuario=${idUsuario}`)
        .then(response => {
            if (!response.ok) {
                return response.text().then(error => Promise.reject(new Error(error)));
            }
            return response.json();
        })
        .then(data => document.getElementById('resultado-buscar').innerText = `Usuário encontrado: ${JSON.stringify(data)}`)
        .catch(error => document.getElementById('resultado-buscar').innerText = `Erro: ${error.message}`);
}

function atualizarUsuario() {
    const id = document.getElementById('id-atualizar').value;
    const nome = document.getElementById('nome-atualizar').value;
    const email = document.getElementById('email-atualizar').value;
    const endereco = document.getElementById('endereco-atualizar').value;

    fetch(`${apiUrl}/atualizar`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ id, nome, email, endereco })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(error => Promise.reject(new Error(error)));
            }
            return response.json();
        })
        .then(data => document.getElementById('resultado-atualizar').innerText = `Usuário atualizado: ${JSON.stringify(data)}`)
        .catch(error => document.getElementById('resultado-atualizar').innerText = `Erro: ${error.message}`);
}

function deletarUsuario() {
    const idUsuario = document.getElementById('id-deletar').value;

    fetch(`${apiUrl}/deletar?idUsuario=${idUsuario}`, { method: 'DELETE' })
        .then(response => {
            if (!response.ok) {
                return response.text().then(error => Promise.reject(new Error(error)));
            }
            return response.text();
        })
        .then(message => document.getElementById('resultado-deletar').innerText = message)
        .catch(error => document.getElementById('resultado-deletar').innerText = `Erro: ${error.message}`);
}