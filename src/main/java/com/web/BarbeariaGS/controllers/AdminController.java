package com.web.BarbeariaGS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.BarbeariaGS.models.Admin;
import com.web.BarbeariaGS.repository.AdminRepo;
import com.web.BarbeariaGS.repository.ClientesRepo;
import com.web.BarbeariaGS.repository.FuncionariosRepo;
import com.web.BarbeariaGS.services.CookieService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminController {
    
    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private ClientesRepo clienteRepo;

    @Autowired
    private FuncionariosRepo funcionariosRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

   
   
     
     //Rota para página de cadastro de admin
     @GetMapping("/administradores/novo")
     public String novo(HttpServletRequest request, Model model){
         // Verifica se o cookie de usuário existe e está dentro do prazo de validade
         if (CookieService.getCookie(request, "usuarioId") != null) {
            // Verifica se o usuário autenticado é um administrador
            if (CookieService.getCookie(request, "tipoUsuario").equals("adminCookie")) {
                // Se for administrador, permite o acesso à página de cadastro de administradores
                model.addAttribute("logado", true);
                model.addAttribute("adminCookie", true);
                return "administradores/novo";
            } else {
                // Se não for administrador, redireciona para a página principal
                return "redirect:/";
            }
        } else {
            // Se o cookie não existe ou está expirado, redireciona para a página de login
            return "redirect:/login";
        }
     }

    //Rota para metodo POST de cadastro de admin
    @PostMapping("/administradores/criar")
    public String criar(Admin admin, @RequestParam String email, @RequestParam String senha, @RequestParam String nome, HttpServletRequest request){
        // Verifica se a senha contém pelo menos 1 número
        boolean temNumero = senha.matches(".*[0-9].*");

        // Verifica se a senha contém pelo menos 1 letra
        boolean temLetra = senha.matches(".*[a-zA-ZçÇ].*");

        // Verifica se a senha contém pelo menos 1 caractere especial
        boolean temCaractereEspecial = senha.matches(".*[@#$%^&+=?!].*");

        // Verifica se o nome contém apenas letras e espaços
        boolean contemApenasLetras = nome.matches("[a-zA-ZçÇ\\s]+");

        // Verifica se o nome contém apenas letras
        if (!contemApenasLetras) {
            return "redirect:/administradores/novo?errorNomeInvalido=O nome deve conter apenas letras";
        }

        // Verifica se a senha atende a todos os critérios
         if (!temNumero || !temLetra || !temCaractereEspecial) {
        return "redirect:/administradores/novo?errorSenhaInsegura=A senha deve conter pelo menos 1 número, 1 letra e 1 caractere especial";
        }
         // Verifica se a senha ultrapassa 10 caracteres
         if (senha.length() > 10) {
            return "redirect:/administradores/novo?errorSenhaInvalida=Senha deve ter entre 3 e 10 caracteres";
        }

         // Verifica se a senha ultrapassa 10 caracteres
         if (senha.length() < 3) {
            return "redirect:/administradores/novo?errorSenhaInvalida=Senha deve ter entre 3 e 10 caracteres";
        }
        // Verifica se o email ultrapassa 100 caracteres
        if (email.length() > 100) {
            return "redirect:/administradores/novo?errorEmailInvalido=Email não pode ter mais de 100 caracteres";
        }
        // Verifica se o e-mail já está em uso
        if (adminRepo.existsByEmail(email)) {
            // Se o e-mail já está em uso, redireciona de volta para a página de cadastro com uma mensagem de erro
            return "redirect:/administradores/novo?error=emailInUse";
        }
        if(clienteRepo.existsByEmail(email)){
            // Se o e-mail já está em uso na tabela cliente, redireciona de volta para a página de cadastro com uma mensagem de erro
            return "redirect:/administradores/novo?error=emailInUse";
        }

        if(funcionariosRepo.existsByEmail(email)){
            // Se o e-mail já está em uso na tabela cliente, redireciona de volta para a página de cadastro com uma mensagem de erro
            return "redirect:/administradores/novo?error=emailInUse";
        }
       // Verifica se o cookie de usuário existe e está dentro do prazo de validade
       if (CookieService.getCookie(request, "usuarioId") != null) {
        // Verifica se o usuário autenticado é um administrador
        if (CookieService.getCookie(request, "tipoUsuario").equals("admin")) {
            // Se for administrador, permite a criação de um novo administrador
              // Configura a senha do cliente como o hash gerado
            admin.setSenha(bCryptPasswordEncoder.encode(senha));
            adminRepo.save(admin);
            return "redirect:/administradores?cadastroSucesso=true";
        } else {
            // Se não for administrador, redireciona para a página principal
            return "redirect:/";
        }
    } else {
        // Se o cookie não existe ou está expirado, redireciona para a página de login
        return "redirect:/login";
    }

    }
}