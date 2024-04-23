package com.web.BarbeariaGS.controllers;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.BarbeariaGS.models.Agendamento;
import com.web.BarbeariaGS.models.Funcionario;
import com.web.BarbeariaGS.models.Horario;
import com.web.BarbeariaGS.models.Servico;
import com.web.BarbeariaGS.repository.AgendamentoRepo;
import com.web.BarbeariaGS.repository.FuncionariosRepo;
import com.web.BarbeariaGS.repository.HorarioRepo;
import com.web.BarbeariaGS.repository.ServicoRepo;
import com.web.BarbeariaGS.services.CookieService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AgendamentoController {

    @Autowired
    private AgendamentoRepo agendamentoRepo;

    @Autowired
    private FuncionariosRepo funcionariosRepo;

    @Autowired
    private HorarioRepo horarioRepo;

    @Autowired
    private ServicoRepo servicoRepo;

    @GetMapping("/clientes/novo/agendamento")
    public String index(Model model,Model modelList, HttpServletRequest request,
                        @RequestParam("funcionarioId") int funcionarioId,
                        @RequestParam("servicoId") int servicoId,
                        @RequestParam(name = "dataAgendamento", required = false) Date dataAgendamento) {
        // Verifica se o cookie de usuário existe e está dentro do prazo de validade
        if (CookieService.getCookie(request, "usuarioId") != null) {
            // Verifica se o usuário autenticado é um cliente
            if (CookieService.getCookie(request, "tipoUsuario").equals("cliente")) {
                // Se for cliente, permite o acesso à página de agendamento
                model.addAttribute("logado", true);
                
                // Busca o funcionário pelo ID
                Funcionario funcionario = funcionariosRepo.findById(funcionarioId)
                        .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
    
                // Busca o serviço pelo ID
                Servico servico = servicoRepo.findById(servicoId)
                        .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
    
                // Adiciona o funcionário e serviço ao model para serem utilizados na view
    model.addAttribute("funcionario", funcionario);
    model.addAttribute("servico", servico);
    List<Object[]> horarios = agendamentoRepo.findHorariosVagosByFuncionarioAndData(funcionarioId, dataAgendamento);
    // Verifica se foi fornecida uma data de agendamento
        modelList.addAttribute("horarios", horarios);
        System.out.println(dataAgendamento);
        model.addAttribute("dataAgendamento", dataAgendamento); // Adiciona a data para uso na view
    

    
                return "clientes/agendamento";
            } else {
                // Se não for cliente, redireciona para a página principal
                return "redirect:/";
            }
        } else {
            // Se o cookie não existe ou está expirado, redireciona para a página de login
            return "redirect:/login";
        }
    }

    @GetMapping("/clientes/novo/agendamento/selecao-horarios")
public String abrirModalSelecaoHorarios(Model model, @RequestParam("funcionarioId") int funcionarioId,
                                        @RequestParam("servicoId") int servicoId,
                                        @RequestParam("dataAgendamento") String dataAgendamento) {
    // Busca o funcionário pelo ID
    Funcionario funcionario = funcionariosRepo.findById(funcionarioId)
            .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

    // Busca o serviço pelo ID
    Servico servico = servicoRepo.findById(servicoId)
            .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

    // Converte a data de String para LocalDate
    LocalDate data = LocalDate.parse(dataAgendamento);

    // Busca os horários vagos para o funcionário e data
    List<Object[]> horarios = agendamentoRepo.findHorariosVagosByFuncionarioAndData(funcionarioId, Date.valueOf(data));
    model.addAttribute("funcionario", funcionario);
    model.addAttribute("servico", servico);
    model.addAttribute("horarios", horarios);
    model.addAttribute("dataAgendamento", data);

    return "clientes/selecaoHorarios"; // Nome da sua view do modal de seleção de horários
}




    @PostMapping("/clientes/novo/agendamento/criar")
    public String criarAgendamento(Model model, HttpServletRequest request, @RequestBody Map<String, Object> requestBody) {
        // Verifica se o cookie de usuário existe e está dentro do prazo de validade
        if (CookieService.getCookie(request, "usuarioId") != null) {
            // Verifica se o usuário autenticado é um cliente
            if (CookieService.getCookie(request, "tipoUsuario").equals("cliente")) {
                // Obtém os dados enviados pelo cliente
                int funcionarioId = Integer.parseInt((String) requestBody.get("funcionarioId"));
                int servicoId = Integer.parseInt((String) requestBody.get("servicoId"));
                LocalDate dataAgendamento = LocalDate.parse((String) requestBody.get("dataAgendamento"));
                int horarioId = Integer.parseInt((String) requestBody.get("horarioId"));

                // Busca o funcionário pelo ID
                Funcionario funcionario = funcionariosRepo.findById(funcionarioId)
                        .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

                // Busca o horário pelo ID
                Horario horario = horarioRepo.findById(horarioId)
                        .orElseThrow(() -> new RuntimeException("Horário não encontrado"));

                // Busca o serviço pelo ID
                Servico servico = servicoRepo.findById(servicoId)
                        .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

                // Cria uma instância de Agendamento com os dados
                Agendamento agendamento = new Agendamento();
                agendamento.setFuncionario(funcionario);
                agendamento.setServico(servico);
                agendamento.setData(dataAgendamento);
                agendamento.setHorario(horario);

                // Salva o agendamento no banco de dados
                agendamentoRepo.save(agendamento);

                // Redireciona para outra página após o sucesso
                return "redirect:/clientes/novo/agendamento";
            } else {
                // Se não for cliente, redireciona para a página principal
                return "redirect:/";
            }
        } else {
            // Se o cookie não existe ou está expirado, redireciona para a página de login
            return "redirect:/login";
        }
    }
}
