package com.web.BarbeariaGS.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.web.BarbeariaGS.models.Funcionario;

import com.web.BarbeariaGS.repository.FuncionariosRepo;
import com.web.BarbeariaGS.services.CookieService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class FuncionarioController {
    
    @Autowired
    private FuncionariosRepo funcionariosRepo;

    //Rota para página de funcionario
     @GetMapping("/gerenciar/funcionarios")
     public String index(HttpServletRequest request, Model model, Model modelList){
         // Verifica se o cookie de usuário existe e está dentro do prazo de validade
         if (CookieService.getCookie(request, "usuarioId") != null) {
            // Se o cookie existe e está dentro do prazo de validade, redireciona para a página principal
            model.addAttribute("logado", true);
            List<Funcionario> funcionarios = (List<Funcionario>)funcionariosRepo.findAll();
            modelList.addAttribute("funcionarios", funcionarios);
            return "funcionarios/index";
        } else {
            // Se o cookie não existe ou está expirado, redireciona para a página de login
            return "redirect:/login";
        }
     }

      //Rota para página de cadastro de funcionario
      @GetMapping("/gerenciar/funcionarios/novo")
      public String novo(HttpServletRequest request, Model model){
         // Verifica se o cookie de usuário existe e está dentro do prazo de validade
         if (CookieService.getCookie(request, "usuarioId") != null) {
             // Se o cookie existe e está dentro do prazo de validade, redireciona para a página principal
             model.addAttribute("logado", true);
             return "funcionarios/novo";
         } else {
             // Se o cookie não existe ou está expirado, redireciona para a página de login
             return "redirect:/login";
         }
      }

      //Rota para metodo POST de cadastro de funcionario
    @PostMapping("/gerenciar/funcionarios/criar")
    public String criar(Funcionario funcionario, @RequestParam String email){
        // Verifica se o e-mail já está em uso
        if (funcionariosRepo.existsByEmail(email)) {
            // Se o e-mail já está em uso, redireciona de volta para a página de cadastro com uma mensagem de erro
            return "redirect:/gerenciar/funcionarios/novo?error=emailInUse";
        }
        if(funcionariosRepo.existsByEmail(email)){
            // Se o e-mail já está em uso na tabela cliente, redireciona de volta para a página de cadastro com uma mensagem de erro
            return "redirect:/gerenciar/funcionarios/novo?error=emailInUse";
        }
        
        // Se o e-mail não está em uso, salva o admin e redireciona para a página de admin
        funcionariosRepo.save(funcionario);
        return "redirect:/gerenciar/funcionarios";
    }

     //Rota para alterar cadastro
    @GetMapping("/gerenciar/funcionarios/{id}")
    public String buscar(@PathVariable int id, Model model){
        Optional<Funcionario> funcionario = funcionariosRepo.findById(id);
        try{
        model.addAttribute("funcionario", funcionario.get());
        }catch(Exception err){
            return "redirect:/gerenciar/funcionarios";
        }
        return "funcionarios/editar";
    }

     //Rota para alterar cadastro no banco
     @PostMapping("/gerenciar/funcionarios/{id}/atualizar")
     public String atualizar(@PathVariable int id, Funcionario funcionario){
        if(!funcionariosRepo.exist(id)){
         return "redirect:/gerenciar/funcionarios";
        }
        funcionariosRepo.save(funcionario);
         return "redirect:/gerenciar/funcionarios";
     }

      //Rota para excluir cadastro
    @GetMapping("/gerenciar/funcionarios/{id}/excluir")
    public String excluir(@PathVariable int id){
        funcionariosRepo.deleteById(id);
        return "redirect:/gerenciar/funcionarios";
    }
}
