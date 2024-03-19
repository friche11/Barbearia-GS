package com.web.BarbeariaGS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.BarbeariaGS.models.Admin;
import com.web.BarbeariaGS.repository.AdminRepo;
import com.web.BarbeariaGS.repository.ClientesRepo;

@Controller
public class AdminController {
    
    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private ClientesRepo clienteRepo;

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
        if (adminRepo.existsByEmail(email)) {
            // Se o e-mail já está em uso, redireciona de volta para a página de cadastro com uma mensagem de erro
            return "redirect:/administradores/novo?error=emailInUse";
        }
        if(clienteRepo.existsByEmail(email)){
            // Se o e-mail já está em uso na tabela cliente, redireciona de volta para a página de cadastro com uma mensagem de erro
            return "redirect:/administradores/novo?error=emailInUse";
        }
        
        // Se o e-mail não está em uso, salva o admin e redireciona para a página de admin
        adminRepo.save(admin);
        return "redirect:/administradores";
    }
}
