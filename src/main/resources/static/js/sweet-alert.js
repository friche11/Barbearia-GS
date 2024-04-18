// NAVBAR

// Adicionando evento de clique ao botão "Agendar"
if(document.getElementById("btnAgendar")){
    document.getElementById("btnAgendar").addEventListener("click", function() {
        // Redirecionando para a página de login
        window.location.href = "/login";
        
      });
    }
    
      // Função para redirecionar para a página de clientes
    function redirecionarParaClientes() {
      window.location.href = "/clientes";
    }
    
     // Função para redirecionar para a página de clientes
     function redirecionarParaFuncionarios() {
      window.location.href = "/funcionarios";
    }
    
    // Função para redirecionar para a página de administradores
    function redirecionarParaEscolhaGerenciar() {
      // Exibe uma caixa de diálogo personalizada
      Swal.fire({
            title: 'Quer gerenciar administradores ou funcionários?',
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Funcionários',
            cancelButtonText: 'Administradores',
            iconColor: '#bf8b15'
        }).then((result) => {
    
            if (result.isConfirmed) {
                window.location.href = "/gerenciar/funcionarios";
            } else if(result.dismiss === Swal.DismissReason.cancel){
                window.location.href = "/administradores/novo";
            } else{
              window.location.href = "/administradores";
            }
        });
    }
    
    // Sair da conta logada
    
    function confirmarSair() {
        // Exibe uma caixa de diálogo personalizada
        Swal.fire({
            title: 'Tem certeza que deseja sair?',
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Sim',
            cancelButtonText: 'Cancelar',
            iconColor: '#bf8b15'
        }).then((result) => {
            // Se o usuário clicar em "Sim", exclui o cookie e redireciona para a rota de logout
            if (result.isConfirmed) {
                document.cookie = "usuarioId=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
                window.location.href = "/sair";
            }
        });
    }
    
    // Cadastro de administrador
    
    if(document.getElementById('cadastroForm')){
    document.getElementById('cadastroForm').addEventListener('submit', function(event) {
        event.preventDefault(); // Impede o envio padrão do formulário
    
        // Exibe uma caixa de diálogo personalizada
        Swal.fire({
            title: 'Tem certeza que deseja cadastrar novo admin?',
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Cadastrar',
            cancelButtonText: 'Cancelar',
            iconColor: '#bf8b15'
        }).then((result) => {
            // Se o usuário confirmar, envia o formulário via AJAX
            if (result.isConfirmed) {
                enviarFormularioAdmin(); // Função para enviar o formulário
            }
        });
    });
    }
    
    function enviarFormularioAdmin() {
        var form = document.getElementById('cadastroForm');
        var formData = new FormData(form);
    
        // Envia o formulário via AJAX
        fetch(form.action, {
            method: form.method,
            body: formData
        })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url; // Redireciona se necessário
            }
        })
        .catch(error => console.error('Erro:', error));
    }
    
    // Cadastro de cliente
    
    if(document.getElementById('cadastroFormUser')){
    document.getElementById('cadastroFormUser').addEventListener('submit', function(event) {
        event.preventDefault(); // Impede o envio padrão do formulário
    
        // Exibe uma caixa de diálogo personalizada
        Swal.fire({
            title: 'Tem certeza que deseja se cadastrar?',
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Cadastrar',
            cancelButtonText: 'Cancelar',
            iconColor: '#bf8b15'
        }).then((result) => {
            // Se o usuário confirmar, envia o formulário via AJAX
            if (result.isConfirmed) {
                enviarFormularioCliente(); // Função para enviar o formulário
            }
        });
    });
    }
    
    function enviarFormularioCliente() {
        var form = document.getElementById('cadastroFormUser');
        var formData = new FormData(form);
    
        // Envia o formulário via AJAX
        fetch(form.action, {
            method: form.method,
            body: formData
        })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url; // Redireciona se necessário
            }
        })
        .catch(error => console.error('Erro:', error));
    }
    
    // Editar Funcionario
    
    if(document.getElementById('cadastroFormEdit')){
        document.getElementById('cadastroFormEdit').addEventListener('submit', function(event) {
            event.preventDefault(); // Impede o envio padrão do formulário
        
            // Exibe uma caixa de diálogo personalizada
            Swal.fire({
                title: 'Tem certeza que deseja confirmar a edição desse funcionário?',
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Confirmar',
                cancelButtonText: 'Cancelar',
                iconColor: '#bf8b15'
            }).then((result) => {
                // Se o usuário confirmar, envia o formulário via AJAX
                if (result.isConfirmed) {
                    enviarFormularioEdit(); // Função para enviar o formulário
                }
            });
        });
        }
        
        function enviarFormularioEdit() {
            var form = document.getElementById('cadastroFormEdit');
            var formData = new FormData(form);
        
            // Envia o formulário via AJAX
            fetch(form.action, {
                method: form.method,
                body: formData
            })
            .then(response => {
                if (response.redirected) {
                    window.location.href = response.url; // Redireciona se necessário
                }
            })
            .catch(error => console.error('Erro:', error));
        }
    
        // Novo Funcionário
        if(document.getElementById('cadastroFormNovoFuncionario')){
        document.getElementById('cadastroFormNovoFuncionario').addEventListener('submit', function(event) {
            event.preventDefault(); // Impede o envio padrão do formulário
        
            // Exibe uma caixa de diálogo personalizada
            Swal.fire({
                title: 'Tem certeza que deseja cadastrar novo funcionário?',
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Cadastrar',
                cancelButtonText: 'Cancelar',
                iconColor: '#bf8b15'
            }).then((result) => {
                // Se o usuário confirmar, envia o formulário via AJAX
                if (result.isConfirmed) {
                    enviarFormularioNovoFuncionario(); // Função para enviar o formulário
                }
            });
        });
    }
        
        function enviarFormularioNovoFuncionario() {
            var form = document.getElementById('cadastroFormNovoFuncionario');
            var formData = new FormData(form);
        
            // Envia o formulário via AJAX
            fetch(form.action, {
                method: form.method,
                body: formData
            })
            .then(response => {
                if (response.redirected) {
                    window.location.href = response.url; // Redireciona se necessário
                }
            })
            .catch(error => console.error('Erro:', error));
        
        }
    
        document.addEventListener('DOMContentLoaded', function() {
            var adminId = parseInt(document.getElementById('adminId').value);
            document.getElementById('adminId').value = adminId;
        });
    
    
    // Exibe mensagem de sucesso para login
    $(document).ready(function() {
        var params = new URLSearchParams(window.location.search);
        if(params.has('loginSucesso')){
        var loginSucesso = params.get('loginSucesso');
        
        // Verifica se a mensagem já foi exibida anteriormente
        if (loginSucesso === 'true' && !sessionStorage.getItem('loginSucessoShown')) {
            // Exibe a mensagem de sucesso
            Swal.fire({
                title: 'Login realizado com sucesso!',
                icon: 'success',
                showConfirmButton: false,
                timer: 2000 // Tempo em milissegundos (2 segundos)
            });
            // Remove o indicador de que a mensagem foi exibida
            sessionStorage.removeItem('saidaSucessoShown');
             // Remove o indicador de que a mensagem foi exibida
             sessionStorage.removeItem('cadastroSucessoShown');
            // Define o indicador de que a mensagem foi exibida
            sessionStorage.setItem('loginSucessoShown', 'true');
        }
    } else if (params.has('cadastroSucesso')){
        var cadastroSucesso = params.get('cadastroSucesso');
            
        // Verifica se a mensagem já foi exibida anteriormente
        if (cadastroSucesso === 'true' && !sessionStorage.getItem('cadastroSucessoShown')) {
            // Exibe a mensagem de sucesso para cadastro
            Swal.fire({
                title: 'Cadastro realizado com sucesso!',
                icon: 'success',
                showConfirmButton: false,
                timer: 2000 // Tempo em milissegundos (2 segundos)
            });
    
            // Define o indicador de que a mensagem foi exibida
            sessionStorage.setItem('cadastroSucessoShown', 'true');
            // Remove o indicador de que a mensagem foi exibida
            sessionStorage.removeItem('saidaSucessoShown');
             // Remove o indicador de que a mensagem foi exibida
             sessionStorage.removeItem('loginSucessoShown');
        }
    } else if(params.has('saidaSucesso')){
        var saidaSucesso = params.get('saidaSucesso');
            
            // Verifica se a mensagem já foi exibida anteriormente
            if (saidaSucesso === 'true' && !sessionStorage.getItem('saidaSucessoShown')) {
                Swal.fire({
                    title: 'Você saiu da conta',
                    icon: 'success',
                    showConfirmButton: false,
                    timer: 2000 // Tempo em milissegundos (2 segundos)
                });
                // Define o indicador de que a mensagem foi exibida
                sessionStorage.setItem('saidaSucessoShown', 'true');
                // Remove o indicador de que a mensagem foi exibida
                sessionStorage.removeItem('loginSucessoShown');
    
                // Remove o indicador de que a mensagem foi exibida
                sessionStorage.removeItem('cadastroSucessoShown');
            }
    
    }
    });
    
    
    // Função para exibir o Swal.fire ao clicar no ícone da lixeira
    function ConfirmarExclusao() {
        event.preventDefault(); // Impede o comportamento padrão do link
        const url = event.currentTarget.getAttribute('href'); // Obtém o URL de exclusão do atributo href
        // Exibe uma caixa de diálogo personalizada
        Swal.fire({
            title: 'Tem certeza que deseja excluir este funcionário?',
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Excluir',
            cancelButtonText: 'Cancelar',
            iconColor: '#bf8b15'
        }).then((result) => {
            // Se o usuário confirmar, envia o formulário via AJAX
            if (result.isConfirmed) {
                window.location.href =  url// Redireciona para o URL de exclusão
            }
        });
    }
    
    
    
    