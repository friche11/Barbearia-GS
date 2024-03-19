package com.web.BarbeariaGS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.BarbeariaGS.models.Cliente;
import com.web.BarbeariaGS.repository.AdminRepo;
import com.web.BarbeariaGS.repository.ClientesRepo;



@Controller
public class ClienteController {

     @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private ClientesRepo clienteRepo;

     //Rota para página de cliente
    @GetMapping("/clientes")
    public String index(){
        return "clientes/index";
    }

     //Rota para página de cadastro de cliente
     @GetMapping("/clientes/novo")
     public String novo(){
         return "clientes/novo";
     }

     //Rota para metodo POST de cadastro de cliente
    @PostMapping("/clientes/criar")
    public String criar(Cliente cliente, @RequestParam String email){
         // Verifica se o e-mail já está em uso
         if (clienteRepo.existsByEmail(email)) {
            // Se o e-mail já está em uso, redireciona de volta para a página de cadastro com uma mensagem de erro
            return "redirect:/clientes/novo?error=emailInUse";
        }

        if (adminRepo.existsByEmail(email)) {
            // Se o e-mail já está em uso na tabela administradores, redireciona de volta para a página de cadastro com uma mensagem de erro
            return "redirect:/administradores/novo?error=emailInUse";
        }
        
        // Se o e-mail não está em uso, salva o cliente e redireciona para a página de clientes
        clienteRepo.save(cliente);
        return "redirect:/clientes";
    }
}
