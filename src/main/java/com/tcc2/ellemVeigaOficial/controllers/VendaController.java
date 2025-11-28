package com.tcc2.ellemVeigaOficial.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.tcc2.ellemVeigaOficial.models.FluxoCaixa;
import com.tcc2.ellemVeigaOficial.models.FormaPagamento;
import com.tcc2.ellemVeigaOficial.models.Venda;
import com.tcc2.ellemVeigaOficial.services.FluxoCaixaService;
import com.tcc2.ellemVeigaOficial.services.VendaService;
import java.util.Date;

@RestController
@RequestMapping("/venda")
public class VendaController {
    @Autowired
    private VendaService service;
    @Autowired
    private FluxoCaixaService fluxoservice;

    @PostMapping
    public ResponseEntity<Venda> addVenda(@RequestBody Venda venda){
       return ResponseEntity.status(HttpStatus.CREATED).body(service.addVenda(venda));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Venda>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Venda> updateVenda(@PathVariable Long id, @RequestBody Venda venda) {
        try {
            Venda updateVenda = service.update(id, venda);
            return ResponseEntity.ok(updateVenda);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenda(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Venda>> buscarVendas(
            @RequestParam(required = false) Long idVenda,
            @RequestParam(required = false) String nomeCliente,
            @RequestParam(required = false) Long idPedido,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataInicial,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataFinal) {
        
        List<Venda> vendas = service.buscarVendas(idVenda, nomeCliente, idPedido, dataInicial, dataFinal);
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/fluxo-caixa")
    public ResponseEntity<List<FluxoCaixa>> relatorioFluxoCaixa(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataFim,
            @RequestParam(required = false) FormaPagamento formaPagamento) {
                
        List<FluxoCaixa> relatorio = fluxoservice.gerarRelatorioFluxoCaixa(dataInicio, dataFim, formaPagamento);
        return ResponseEntity.ok(relatorio);
    }
    
}