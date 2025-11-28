package com.tcc2.ellemVeigaOficial.controllers;

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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import com.tcc2.ellemVeigaOficial.dto.VendasPorProdutoDTO;
import com.tcc2.ellemVeigaOficial.models.ProdutoVenda;
import com.tcc2.ellemVeigaOficial.services.ProdutoVendaService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/produtovenda")
public class ProdutoVendaController {
    private ProdutoVendaService service;

    /*
    @PostMapping
    public ResponseEntity<List<ProdutoVenda>> addProdutoVendas(@RequestBody List<ProdutoVenda> produtoVendas){
        return ResponseEntity.ok(service.addProdutoVendas(produtoVendas));
    }*/

    @PostMapping
    public ResponseEntity<?> addProdutoVendas(@RequestBody List<ProdutoVenda> produtosVenda) {
        List<ProdutoVenda> salvos = service.addProdutoVendas(produtosVenda);
        return ResponseEntity.ok(salvos); // ou uma mensagem se preferir
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoVenda> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoVenda>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoVenda> updateProdutoVenda(@PathVariable Long id, @RequestBody ProdutoVenda produtoVenda) {
        try {
            ProdutoVenda updateProdutoVenda = service.update(id, produtoVenda);
            return ResponseEntity.ok(updateProdutoVenda);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<List<ProdutoVenda>> updateProdutoVendas(@RequestBody List<ProdutoVenda> produtosVenda) {
        try {
            List<ProdutoVenda> atualizados = service.updateProdutosVenda(produtosVenda);
            return ResponseEntity.ok(atualizados);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProdutoVenda(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProdutoVenda>> buscarProdutoVendas(
            @RequestParam(required = false) Long idVenda,
            @RequestParam(required = false) String nomeProduto) {
        List<ProdutoVenda> produtoVendas = service.buscarProdutoVendas(idVenda, nomeProduto);
        return ResponseEntity.ok(produtoVendas);
    }

    @GetMapping("/vendasmes")
    public ResponseEntity<List<VendasPorProdutoDTO>> vendasDoMes() {
        ZoneId zoneBrasilia = ZoneId.of("America/Sao_Paulo");

        LocalDate hoje = LocalDate.now(zoneBrasilia);
        LocalDate primeiroDia = hoje.withDayOfMonth(1);
        LocalDate ultimoDia = hoje.withDayOfMonth(hoje.lengthOfMonth());

        ZonedDateTime inicio = primeiroDia.atStartOfDay(zoneBrasilia);
        ZonedDateTime fim = ultimoDia.atTime(LocalTime.MAX).atZone(zoneBrasilia);

        Date dataInicial = Date.from(inicio.toInstant());
        Date dataFinal = Date.from(fim.toInstant());

        List<VendasPorProdutoDTO> vendas = service.buscarVendasPorProdutoNoPeriodo(dataInicial, dataFinal);
        return ResponseEntity.ok(vendas);
    }


}