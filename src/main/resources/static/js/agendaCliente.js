$(document).ready(function () {
  // Função para abrir o modal de seleção de profissional
  function abrirModalSelecaoProfissional() {
    $('#modalSelecaoProfissional').modal('show');
  }

 // Função para abrir o modal de seleção de horários
function abrirModalSelecaoHorarios(data, funcionarioId) {
  // Converter a data para o formato desejado: DD/MM/YYYY
  var dataFormatada = new Date(data + 'T00:00:00').toLocaleDateString('pt-br', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric'
  });
  document.getElementById('dataEscolhida').innerText = dataFormatada;

  // Obter o funcionarioId e servicoId da URL
  const urlParams = new URLSearchParams(window.location.search);
  const funcionarioIdUrl = urlParams.get('funcionarioId');
  const servicoId = urlParams.get('servicoId');

  // Verificar se funcionarioId e servicoId estão presentes
  if (!funcionarioIdUrl || !servicoId) {
    console.error('funcionarioId ou servicoId não encontrados.');
    return;
  }

  // Formatar a data para o formato yyyy-MM-dd
  var dataFormatadaISO = new Date(data + 'T00:00:00').toISOString().split('T')[0];

  // Construir a nova URL com o parâmetro 'modal'
  var newUrl = '/clientes/novo/agendamento/selecao-horarios?funcionarioId=' + funcionarioIdUrl + '&servicoId=' + servicoId + '&dataAgendamento=' + dataFormatadaISO + '&modal=true';

  // Redirecionar para a nova URL
  window.location.href = newUrl;
}

// Verificar se o parâmetro 'modal' está presente na URL
const urlParams = new URLSearchParams(window.location.search);
const abrirModal = urlParams.get('modal');

// Se 'modal' estiver presente, abrir o modal
if (abrirModal === 'true') {
  $('#modalHorarios').modal('show');
}

  // Função para marcar o profissional selecionado
  function marcarProfissional() {
    profissionalSelecionado = document.getElementById("selectProfissional").value;
    $('#modalSelecaoProfissional').modal('hide');
    $('#modalSelecaoServico').modal('show');
  }

  // Função para marcar o serviço selecionado e redirecionar para a segunda view
  function marcarServico() {
    servicoSelecionado = document.getElementById("selectServico").value;

    if (profissionalSelecionado && servicoSelecionado) {
      // Redirecionar para a segunda página com os parâmetros na URL
      window.location.href = '/clientes/novo/agendamento?funcionarioId=' + profissionalSelecionado + '&servicoId=' + servicoSelecionado;
    } else {
      console.error('Dados incompletos para criar o agendamento.');
    }
  }

  // Função para marcar o horário
  function marcarHorario(dataEscolhida, horarioSelecionado) {
    // Obter os parâmetros da URL
    const urlParams = new URLSearchParams(window.location.search);
    const funcionarioId = urlParams.get('funcionarioId');
    const servicoId = urlParams.get('servicoId');

    // Verificar se todos os dados estão disponíveis
    if (funcionarioId && servicoId && dataEscolhida && horarioSelecionado) {
      // Aqui você faria a requisição AJAX para salvar o agendamento no banco de dados

      $.ajax({
        url: '/clientes/novo/agendamento/criar',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
          funcionarioId: funcionarioId,
          servicoId: servicoId,
          dataAgendamento: dataEscolhida,
          horarioId: horarioSelecionado
        }),
        success: function (response) {
          // Redirecionar para outra página após o sucesso
          window.location.href = '/clientes/novo/agendamento';
        },
        error: function (error) {
          console.error('Erro ao enviar dados para o servidor:', error);
          // Exibir mensagem de erro para o usuário, se necessário
        }
      });

      // Exemplo de redirecionamento (não envia realmente o agendamento)
      window.location.href = '/clientes/novo/agendamento';
    } else {
      console.error('Dados incompletos para criar o agendamento.');
    }
  }

  // Função para habilitar botões conforme seleção
  function habilitarBotao() {
    var selectProfissional = document.getElementById("selectProfissional");
    var selectServico = document.getElementById("selectServico");
    var selectHorario = document.getElementById("selectHorario");
    var btnConfirmarMarcarProfissional = document.querySelector("#modalSelecaoProfissional .btn-confirmar");
    var btnConfirmarMarcarServico = document.querySelector("#modalSelecaoServico .btn-confirmar");
    var btnConfirmarMarcarHorario = document.querySelector("#modalSelecaoHorarios .btn-confirmar");

    // Habilitar o botão se um profissional foi selecionado
    if (selectProfissional.value !== "") {
      btnConfirmarMarcarProfissional.disabled = false;
    } else {
      btnConfirmarMarcarProfissional.disabled = true;
    }

    // Habilitar o botão se um serviço foi selecionado
    if (selectServico.value !== "") {
      btnConfirmarMarcarServico.disabled = false;
    } else {
      btnConfirmarMarcarServico.disabled = true;
    }

    // Habilitar o botão se um horário foi selecionado
    if (selectHorario.value !== "") {
      btnConfirmarMarcarHorario.disabled = false;
    } else {
      btnConfirmarMarcarHorario.disabled = true;
    }
  }

  // Event listener para abrir o modal de seleção de profissional
  $('#btnAbrirModalProfissional').on('click', abrirModalSelecaoProfissional);

  // Event listener para marcar o profissional
  $('#btnMarcarProfissional').on('click', marcarProfissional);

  // Event listener para marcar o serviço
  $('#btnMarcarServico').on('click', marcarServico);

  // Event listener para marcar o horário
  $('#btnMarcarHorario').on('click', function () {
    var dataEscolhida = $('#dataEscolhida').text();
    var horarioSelecionado = $('#selectHorario').val();
    marcarHorario(dataEscolhida, horarioSelecionado);
  });

  // Inicializar o calendário FullCalendar
  var calendarEl = document.getElementById('calendar');
  var calendar = new FullCalendar.Calendar(calendarEl, {
    initialView: 'dayGridMonth',
    locale: 'pt-br',
    dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'],
    dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'],
    buttonText: {
      today: 'Hoje',
      month: 'Mês',
      week: 'Semana',
      day: 'Dia',
      list: 'Lista'
    },
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
    },
    slotLabelFormat: { hour: '2-digit', minute: '2-digit', hour12: false },
    dateClick: function (info) {
      var profissionalId = $('#selectProfissional').val();
      abrirModalSelecaoHorarios(info.dateStr, profissionalId);
    },
    eventClick: function (info) {
      abrirModalSelecaoHorarios(info.event.startStr, info.event.extendedProps.profissionalId);
    }
  });
  calendar.render();
});
