package com.web.BarbeariaGS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.web.BarbeariaGS.models.Cliente;
import com.web.BarbeariaGS.services.ClientesRepo;



@Controller
public class ClienteController {

     @Autowired
    private ClientesRepo repo;

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
    public String criar(Cliente cliente){
        repo.save(cliente);
        return "redirect:/clientes";
    }
}
