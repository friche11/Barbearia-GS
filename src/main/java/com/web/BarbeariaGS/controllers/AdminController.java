package com.web.BarbeariaGS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.BarbeariaGS.models.Admin;
import com.web.BarbeariaGS.services.AdminRepo;

@Controller
public class AdminController {
    
    @Autowired
    private AdminRepo repo;

     //Rota para página de admin
    @GetMapping("/administradores")
    public String index(){
        return "administradores/index";
    }

     //Rota para página de cadastro de admin
     @GetMapping("/administradores/novo")
     public String novo(){
         return "administradores/novo";
     }

    //Rota para metodo POST de cadastro de admin
    @PostMapping("/administradores/criar")
    public String criar(Admin admin, @RequestParam String email){
        // Verifica se o e-mail já está em uso
        if (repo.existsByEmail(email)) {
            // Se o e-mail já está em uso, redireciona de volta para a página de cadastro com uma mensagem de erro
            return "redirect:/administradores/novo?error=emailInUse";
        }
        
        // Se o e-mail não está em uso, salva o admin e redireciona para a página de admin
        repo.save(admin);
        return "redirect:/administradores";
    }
}
