document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('.formLogin');

    form.addEventListener('submit', function (event) {
        event.preventDefault();

        const login = document.getElementById('login').value;
        const password = document.getElementById('password').value;

        if (!login || login.trim().length < 3) {
            M.toast({
                html: 'Por favor, insira um email válido ou um nome de usuário (mínimo 3 caracteres).',
                classes: 'yellow darken-2'
            });
            return;
        }

        if (!password) {
            M.toast({
                html: 'Por favor, insira sua senha.',
                classes: 'yellow darken-2'
            });
            return;
        }

        const data = {
            usuario: login,
            senha: password
        };

        fetch('/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) throw new Error('Erro na autenticação');
                return response.json();
            })
            .then(data => {
                if (data.token) {
                    localStorage.setItem('token', data.token);
                    localStorage.setItem('userId', data.id);

                    localStorage.setItem('usuario', JSON.stringify({
                        id: data.id,
                        nome: data.nome
                    }));

                    window.location.href = "../paginainicial";
                } else {
                    M.toast({ html: 'Erro: Token não recebido.', classes: 'red' });
                }
            })
            .catch(error => {
                console.error('Erro ao se conectar ao servidor:', error);
                M.toast({ html: 'Erro ao se conectar ao servidor.', classes: 'red' });
            });
    });
});
