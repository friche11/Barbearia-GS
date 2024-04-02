package com.web.BarbeariaGS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.BarbeariaGS.models.Cliente;
import com.web.BarbeariaGS.repository.AdminRepo;
import com.web.BarbeariaGS.repository.ClientesRepo;
import com.web.BarbeariaGS.services.CookieService;

import jakarta.servlet.http.HttpServletRequest;




@Controller
public class ClienteController {

     @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private ClientesRepo clientesRepo;

    @Autowired
    private ClientesRepo funcionariosRepo;

     //Rota para página de cliente
    @GetMapping("/clientes")
    public String index(HttpServletRequest request){
         // Verifica se o cookie de usuário existe e está dentro do prazo de validade
       if (CookieService.getCookie(request, "usuarioId") != null) {
        // Verifica se o usuário autenticado é um administrador
        if (CookieService.getCookie(request, "tipoUsuario").equals("cliente")) {
            return "clientes/index";
        } else {
            // Se não for cliente, redireciona para a página principal
            return "redirect:/";
        }
    } else {
        // Se o cookie não existe ou está expirado, redireciona para a página de login
        return "redirect:/login";
    }
    }

     //Rota para página de cadastro de cliente
     @GetMapping("/clientes/novo")
     public String novo(){
            return "clientes/novo";

     }

     //Rota para metodo POST de cadastro de cliente
    @PostMapping("/clientes/criar")
    public String criar(Cliente cliente, @RequestParam String email, @RequestParam String senha){
         // Verifica se a senha ultrapassa 10 caracteres
         if (senha.length() > 10) {
            return "redirect:/clientes/novo?errorSenhaInvalida=Senha não pode ter mais de 10 caracteres";
        }

        // Verifica se o email ultrapassa 100 caracteres
        if (email.length() > 100) {
            return "redirect:/clientes/novo?errorEmailInvalido=Email não pode ter mais de 100 caracteres";
        }
         // Verifica se o e-mail já está em uso
         if (clientesRepo.existsByEmail(email)) {
            // Se o e-mail já está em uso, redireciona de volta para a página de cadastro com uma mensagem de erro
            return "redirect:/clientes/novo?error=emailInUse";
        }

        if (adminRepo.existsByEmail(email)) {
            // Se o e-mail já está em uso na tabela administradores, redireciona de volta para a página de cadastro com uma mensagem de erro
            return "redirect:/clientes/novo?error=emailInUse";
        }

        if (funcionariosRepo.existsByEmail(email)) {
            // Se o e-mail já está em uso na tabela administradores, redireciona de volta para a página de cadastro com uma mensagem de erro
            return "redirect:/clientes/novo?error=emailInUse";
        }
        
        // Se o e-mail não está em uso, salva o cliente e redireciona para a página de clientes
        clientesRepo.save(cliente);
        return "redirect:/login";
        
    }
}
