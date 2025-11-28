package com.tcc2.ellemVeigaOficial.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.tcc2.ellemVeigaOficial.config.authentication.JwtTokenService;

@Controller
public class PageController {
    @Autowired
    private JwtTokenService jwtTokenService;
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/paginainicial")
    public String paginainicial (){
        return "paginainicial";  // Retorna a página de sucesso
    }

    @GetMapping("/relatorio")
    public String relatorio() {
        return "relatorio"; 
    }

    @GetMapping("/fluxocaixa")
    public String fluxocaixa() {
        return "fluxocaixa";
    }

    @GetMapping("/alterarcliente")
    public String alterarcliente() {
        return "alterarcliente"; 
    }

    @GetMapping("/alterarpagamento")
    public String alterarpagamento() {
        return "alterarpagamento"; 
    }

    @GetMapping("/alterarpedido")
    public String alterarpedido() {
        return "alterarpedido";
    }

    @GetMapping("/alterarproduto")
    public String alterarproduto() {
        return "alterarproduto"; 
    }

    @GetMapping("/alterarusuario")
    public String alterarusuario() {
        return "alterarusuario"; 
    }


    @GetMapping("/alterarvenda")
    public String alterarvenda() {
        return "alterarvenda";
    }

    @GetMapping("/alterarvendedor")
    public String alterarvendedor() {
        return "alterarvendedor"; 
    }

    @GetMapping("/buscarcliente")
    public String buscarcliente() {
        return "buscarcliente"; 
    }
    
    @GetMapping("/buscarpagamento")
    public String buscarpagamento() {
        return "buscarpagamento"; 
    }

    @GetMapping("/buscarpedido")
    public String buscarpedido() {
        return "buscarpedido";
    }

    @GetMapping("/buscarproduto")
    public String buscarproduto() {
        return "buscarproduto"; 
    }

    @GetMapping("/buscarusuario")
    public String buscarusuario() {
        return "buscarusuario"; 
    }

    @GetMapping("/buscarvenda")
    public String buscarvenda() {
        return "buscarvenda";
    }

    @GetMapping("/buscarvendedor")
    public String buscarvendedor() {
        return "buscarvendedor"; 
    }
    

    @GetMapping("/cadastrocliente")
    public String cadastrocliente() {
        return "cadastrocliente"; 
    }
    
    @GetMapping("/cadastropagamento")
    public String cadastropagamento() {
        return "cadastropagamento"; 
    }

    @GetMapping("/cadastropedido")
    public String cadastropedido() {
        return "cadastropedido";
    }

    @GetMapping("/cadastroproduto")
    public String cadastroproduto() {
        return "cadastroproduto"; 
    }

    @GetMapping("/cadastrousuario")
    public String cadastrousuario() {
        return "cadastrousuario"; 
    }

    @GetMapping("/cadastrovenda")
    public String cadastrovenda() {
        return "cadastrovenda";
    }

    @GetMapping("/cadastrovendedor")
    public String cadastrovendedor() {
        return "cadastrovendedor"; 
    }
}



