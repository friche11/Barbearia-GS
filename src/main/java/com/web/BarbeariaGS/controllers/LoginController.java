package com.web.BarbeariaGS.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.web.BarbeariaGS.models.Admin;
import com.web.BarbeariaGS.models.Cliente;
import com.web.BarbeariaGS.repository.AdminRepo;
import com.web.BarbeariaGS.repository.ClientesRepo;
import com.web.BarbeariaGS.services.CookieService;

@Controller
public class LoginController {

     @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private ClientesRepo clienteRepo;

    //Rota para página de login
    @GetMapping("/login")
    public String index(){
        return "login/index";
    }
    
     // Rota para realizar login
    @PostMapping("/logar")
    public String logar(Model model, String email, String senha, Admin administrador, Cliente cliente, String lembrar, HttpServletResponse response) throws UnsupportedEncodingException {
        Admin adm = this.adminRepo.login(administrador.getEmail(), administrador.getSenha());
        Cliente client = this.clienteRepo.login(cliente.getEmail(), cliente.getSenha());
        int tempoLogado = (60 * 60); // 1 hora logado por padrão
        if (adm != null) {
            if (lembrar != null) {
                tempoLogado = (60 * 60 * 24 * 365); // 1 ano logado
            }
            CookieService.setCookie(response, "usuarioId", String.valueOf(adm.getId()), tempoLogado);
            CookieService.setCookie(response, "usuarioNome", URLEncoder.encode(adm.getNome(), "UTF-8"), tempoLogado);
            return "redirect:/administradores";
        } else if (client != null) {
            if (lembrar != null) {
                tempoLogado = (60 * 60 * 24 * 365); // 1 ano logado
            }
            CookieService.setCookie(response, "usuarioId", String.valueOf(client.getId()), tempoLogado);
            CookieService.setCookie(response, "usuarioNome", URLEncoder.encode(client.getNome(), "UTF-8"), tempoLogado);
            return "redirect:/clientes";
        } else {
            model.addAttribute("erro", "Usuário ou senha inválidos");
            return "login/index";
        }
    }

}
