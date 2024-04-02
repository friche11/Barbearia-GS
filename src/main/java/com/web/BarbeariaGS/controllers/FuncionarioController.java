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

import com.web.BarbeariaGS.models.Admin;
import com.web.BarbeariaGS.models.Funcionario;
import com.web.BarbeariaGS.repository.AdminRepo;
import com.web.BarbeariaGS.repository.ClientesRepo;
import com.web.BarbeariaGS.repository.FuncionariosRepo;
import com.web.BarbeariaGS.services.CookieService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class FuncionarioController {
    
    @Autowired
    private FuncionariosRepo funcionariosRepo;

     @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private ClientesRepo clientesRepo;

    //Rota para página de gerencia funcionario
    @GetMapping("/funcionarios")
    public String index(HttpServletRequest request){
          // Verifica se o cookie de usuário existe e está dentro do prazo de validade
       if (CookieService.getCookie(request, "usuarioId") != null) {
        // Verifica se o usuário autenticado é um administrador
        if (CookieService.getCookie(request, "tipoUsuario").equals("funcionario")) {
            
            return "funcionarios/index";
    
        } else {
            // Se não for administrador, redireciona para a página principal
            return "redirect:/";
        }
    } else {
        // Se o cookie não existe ou está expirado, redireciona para a página de login
        return "redirect:/login";
    }
    }

    //Rota para página de gerencia funcionario
     @GetMapping("/gerenciar/funcionarios")
     public String gerenciar(HttpServletRequest request, Model model, Model modelList){
         // Verifica se o cookie de usuário existe e está dentro do prazo de validade
       if (CookieService.getCookie(request, "usuarioId") != null) {
        // Verifica se o usuário autenticado é um administrador
        if (CookieService.getCookie(request, "tipoUsuario").equals("admin")) {
            // Se o cookie existe e está dentro do prazo de validade, redireciona para a página principal
            model.addAttribute("logado", true);
            List<Funcionario> funcionarios = (List<Funcionario>)funcionariosRepo.findAll();
            modelList.addAttribute("funcionarios", funcionarios);
            return "funcionarios/gerenciar";
    
        } else {
            // Se não for administrador, redireciona para a página principal
            return "redirect:/";
        }
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
        // Verifica se o usuário autenticado é um administrador
        if (CookieService.getCookie(request, "tipoUsuario").equals("admin")) {
            // Se o cookie existe e está dentro do prazo de validade, redireciona para a página principal
            model.addAttribute("logado", true);
            return "funcionarios/novo";
    
        } else {
            // Se não for administrador, redireciona para a página principal
            return "redirect:/";
        }
    } else {
        // Se o cookie não existe ou está expirado, redireciona para a página de login
        return "redirect:/login";
    }

      }

      //Rota para metodo POST de cadastro de funcionario
    @PostMapping("/gerenciar/funcionarios/criar")
    public String criar(Funcionario funcionario, @RequestParam String email,  @RequestParam String senha, HttpServletRequest request){
         // Verifica se a senha ultrapassa 10 caracteres
         if (senha.length() > 10) {
            return "redirect:/gerenciar/funcionarios/novo?errorSenhaInvalida=Senha não pode ter mais de 10 caracteres";
        }

        // Verifica se o email ultrapassa 100 caracteres
        if (email.length() > 100) {
            return "redirect:/gerenciar/funcionarios/novo?errorEmailInvalido=Email não pode ter mais de 100 caracteres";
        }
        // Verifica se o e-mail já está em uso
        if (adminRepo.existsByEmail(email)) {
            // Se o e-mail já está em uso, redireciona de volta para a página de cadastro com uma mensagem de erro
            return "redirect:/gerenciar/funcionarios/novo?error=emailInUse";
        }
        if(clientesRepo.existsByEmail(email)){
            // Se o e-mail já está em uso na tabela cliente, redireciona de volta para a página de cadastro com uma mensagem de erro
            return "redirect:/gerenciar/funcionarios/novo?error=emailInUse";
        }

        if(funcionariosRepo.existsByEmail(email)){
            // Se o e-mail já está em uso na tabela cliente, redireciona de volta para a página de cadastro com uma mensagem de erro
            return "redirect:/gerenciar/funcionarios/novo?error=emailInUse";
        }
        
        
          // Verifica se o cookie de usuário existe e está dentro do prazo de validade
if (CookieService.getCookie(request, "usuarioId") != null) {
        // Verifica se o usuário autenticado é um administrador
    if (CookieService.getCookie(request, "tipoUsuario").equals("admin")) {
            // Se o cookie existe e está dentro do prazo de validade, redireciona para a página principal
          
            // Obter o valor do cookie "usuarioId" e convertê-lo para um número inteiro
        String adminIdStr = CookieService.getCookie(request, "usuarioId");
        // Verifica se o adminIdStr não é nulo nem vazio antes de tentar converter para inteiro
        if (adminIdStr == null || adminIdStr.isEmpty()) {
            // Se o cookie não foi encontrado ou está vazio, redirecionar para a página de login
            return "redirect:/login";
        }

        // Converte a string do cookie para um número inteiro
        int adminId;
        try {
            adminId = Integer.parseInt(adminIdStr);
        } catch (NumberFormatException e) {
            // Se ocorrer uma exceção ao converter a string para inteiro, trata o erro de alguma forma apropriada
            // Aqui você pode redirecionar para uma página de erro ou fazer outra ação adequada
            e.printStackTrace();
            return "redirect:/login";
        }

            // Verifica se o admin com o ID fornecido existe
        Optional<Admin> adminOptional = adminRepo.findById(adminId);
        if (adminOptional.isPresent()) {
            funcionario.setAdmin(adminOptional.get());
            funcionariosRepo.save(funcionario);
            return "redirect:/gerenciar/funcionarios";
        } else {
            return "redirect:/gerenciar/funcionarios/novo?error=adminNotFound";
        }
    
        } else {
            // Se não for administrador, redireciona para a página principal
            return "redirect:/";
        }
    } else {
        // Se o cookie não existe ou está expirado, redireciona para a página de login
        return "redirect:/login";
    }
      
    }

     //Rota para alterar cadastro
    @GetMapping("/gerenciar/funcionarios/{id}")
    public String buscar(@PathVariable int id, Model model, HttpServletRequest request){

        
          // Verifica se o cookie de usuário existe e está dentro do prazo de validade
       if (CookieService.getCookie(request, "usuarioId") != null) {
        // Verifica se o usuário autenticado é um administrador
        if (CookieService.getCookie(request, "tipoUsuario").equals("admin")) {
            // Se o cookie existe e está dentro do prazo de validade, redireciona para a página principal
            
        Optional<Funcionario> funcionario = funcionariosRepo.findById(id);
        try{
        model.addAttribute("funcionario", funcionario.get());
        }catch(Exception err){
            return "redirect:/gerenciar/funcionarios";
        }
        return "funcionarios/editar";
    
        } else {
            // Se não for administrador, redireciona para a página principal
            return "redirect:/";
        }
    } else {
        // Se o cookie não existe ou está expirado, redireciona para a página de login
        return "redirect:/login";
    }

    }

     //Rota para alterar cadastro no banco
     @PostMapping("/gerenciar/funcionarios/{id}/atualizar")
     public String atualizar(@PathVariable int id, @RequestParam String email,  @RequestParam String senha, Funcionario funcionario, HttpServletRequest request){

 // Verifica se a senha ultrapassa 10 caracteres
 if (senha.length() > 10) {
    return "redirect:/gerenciar/funcionarios/{id}?errorSenhaInvalida=Senha não pode ter mais de 10 caracteres";
}

// Verifica se o email ultrapassa 100 caracteres
if (email.length() > 100) {
    return "redirect:/gerenciar/funcionarios/{id}?errorEmailInvalido=Email não pode ter mais de 100 caracteres";
}
// Verifica se o e-mail já está em uso
if (adminRepo.existsByEmail(email)) {
    // Se o e-mail já está em uso, redireciona de volta para a página de cadastro com uma mensagem de erro
    return "redirect:/gerenciar/funcionarios/{id}?error=emailInUse";
}
if(clientesRepo.existsByEmail(email)){
    // Se o e-mail já está em uso na tabela cliente, redireciona de volta para a página de cadastro com uma mensagem de erro
    return "redirect:/gerenciar/funcionarios/{id}?error=emailInUse";
}

if(funcionariosRepo.existsByEmail(email)){
    // Se o e-mail já está em uso na tabela cliente, redireciona de volta para a página de cadastro com uma mensagem de erro
    return "redirect:/gerenciar/funcionarios/{id}?error=emailInUse";
}


          // Verifica se o cookie de usuário existe e está dentro do prazo de validade
          if (CookieService.getCookie(request, "usuarioId") != null) {
            // Verifica se o usuário autenticado é um administrador
            if (CookieService.getCookie(request, "tipoUsuario").equals("admin")) {
                // Se o cookie existe e está dentro do prazo de validade, redireciona para a página principal
            // Obter o valor do cookie "usuarioId" e convertê-lo para um número inteiro
    String adminIdStr = CookieService.getCookie(request, "usuarioId");
    // Verifica se o adminIdStr não é nulo nem vazio antes de tentar converter para inteiro
    if (adminIdStr == null || adminIdStr.isEmpty()) {
        // Se o cookie não foi encontrado ou está vazio, redirecionar para a página de login
        return "redirect:/login";
    }
     // Converte a string do cookie para um número inteiro
     int adminId;
     try {
         adminId = Integer.parseInt(adminIdStr);
     } catch (NumberFormatException e) {
         // Se ocorrer uma exceção ao converter a string para inteiro, trata o erro de alguma forma apropriada
         // Aqui você pode redirecionar para uma página de erro ou fazer outra ação adequada
         e.printStackTrace();
         return "redirect:/login";
     }

        if(!funcionariosRepo.exist(id)){
         return "redirect:/gerenciar/funcionarios";
        }
        
       // Verifica se o admin com o ID fornecido existe
Optional<Admin> adminOptional = adminRepo.findById(adminId);
if (adminOptional.isPresent()) {
    funcionario.setAdmin(adminOptional.get());
    funcionariosRepo.save(funcionario);
    return "redirect:/gerenciar/funcionarios";
} else {
    return "redirect:/gerenciar/funcionarios/novo?error=adminNotFound";
}
        
            } else {
                // Se não for administrador, redireciona para a página principal
                return "redirect:/";
            }
        } else {
            // Se o cookie não existe ou está expirado, redireciona para a página de login
            return "redirect:/login";
        }

          
     }

      //Rota para excluir cadastro
    @GetMapping("/gerenciar/funcionarios/{id}/excluir")
    public String excluir(@PathVariable int id){
        funcionariosRepo.deleteById(id);
        return "redirect:/gerenciar/funcionarios";
    }
}
