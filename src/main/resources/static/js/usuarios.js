<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciamento de Usuários</title>
    <style>
        body { font-family: Arial, sans-serif; }
        .container { width: 80%; margin: auto; padding: 20px; }
        h1 { text-align: center; }
        .form-section, .result-section { margin-bottom: 20px; }
        .form-section form { margin-bottom: 10px; }
        input, button { padding: 10px; margin: 5px; }
        table { width: 100%; border-collapse: collapse; }
        table, th, td { border: 1px solid black; }
        th, td { padding: 10px; text-align: left; }
        th { background-color: #f4f4f4; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Gerenciamento de Usuários</h1>

        <!-- Buscar Todos os Usuários -->
        <div class="form-section">
            <h2>Buscar Todos os Usuários</h2>
            <button onclick="buscarTodosUsuarios()">Buscar Todos</button>
            <div id="todos-usuarios"></div>
        </div>

        <!-- Criar Usuário -->
        <div class="form-section">
            <h2>Criar Usuário</h2>
            <form id="criar-usuario-form">
                <input type="text" id="nome" placeholder="Nome" required>
                <input type="email" id="email" placeholder="Email" required>
                <input type="text" id="endereco" placeholder="Endereço" required>
                <input type="text" id="id" placeholder="ID" required>
                <button type="button" onclick="criarUsuario()">Criar</button>
            </form>
            <div id="resultado-criar"></div>
        </div>

        <!-- Buscar Usuário -->
        <div class="form-section">
            <h2>Buscar Usuário</h2>
            <input type="number" id="id-buscar" placeholder="ID do Usuário" required>
            <button onclick="buscarUsuario()">Buscar</button>
            <div id="resultado-buscar"></div>
        </div>

        <!-- Atualizar Usuário -->
        <div class="form-section">
            <h2>Atualizar Usuário</h2>
            <input type="number" id="id-atualizar" placeholder="ID do Usuário" required>
            <input type="text" id="nome-atualizar" placeholder="Nome">
            <input type="email" id="email-atualizar" placeholder="Email">
            <input type="text" id="endereco-atualizar" placeholder="Endereço">
            <button onclick="atualizarUsuario()">Atualizar</button>
            <div id="resultado-atualizar"></div>
        </div>

        <!-- Deletar Usuário -->
        <div class="form-section">
            <h2>Deletar Usuário</h2>
            <input type="number" id="id-deletar" placeholder="ID do Usuário" required>
            <button onclick="deletarUsuario()">Deletar</button>
            <div id="resultado-deletar"></div>
        </div>
    </div>

    <script>
        const apiUrl = 'http://localhost:8080/usuarios'; // Atualize com o URL correto

        function buscarTodosUsuarios() {
            fetch(`${apiUrl}/buscarTodos`)
                .then(response => response.json())
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
                .catch(error => console.error('Erro ao buscar todos os usuários:', error));
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
            .then(response => response.json())
            .then(data => {
                if (data.id) {
                    document.getElementById('resultado-criar').innerText = `Usuário ${data.nome} ID ${data.id} foi criado com sucesso`;
                } else {
                    document.getElementById('resultado-criar').innerText = JSON.stringify(data);
                }
            })
            .catch(error => {
                console.error('Erro ao criar usuário:', error);
                document.getElementById('resultado-criar').innerText = 'Erro ao criar usuário.';
            });
        }

        function buscarUsuario() {
            const id = document.getElementById('id-buscar').value;

            fetch(`${apiUrl}/buscar?idUsuario=${id}`)
                .then(response => response.json())
                .then(data => document.getElementById('resultado-buscar').innerText = JSON.stringify(data))
                .catch(error => console.error('Erro ao buscar usuário:', error));
        }

        function atualizarUsuario() {
            const id = document.getElementById('id-atualizar').value;
            const nome = document.getElementById('nome-atualizar').value;
            const email = document.getElementById('email-atualizar').value;
            const endereco = document.getElementById('endereco-atualizar').value;

            fetch(`${apiUrl}/atualizar`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ nome, email, endereco, id})
            })
            .then(response => response.json())
            .then(data => document.getElementById('resultado-atualizar').innerText = JSON.stringify(data))
            .catch(error => console.error('Erro ao atualizar usuário:', error));
        }

        function deletarUsuario() {
            const id = document.getElementById('id-deletar').value;

            fetch(`${apiUrl}/deletar?idUsuario=${id}`, {
                method: 'DELETE'
            })
            .then(() => document.getElementById('resultado-deletar').innerText = 'Usuário deletado com sucesso')
            .catch(error => {
                console.error('Erro ao deletar usuário:', error);
                document.getElementById('resultado-deletar').innerText = 'Erro ao deletar usuário.';
            });
        }
    </script>
</body>
</html>
