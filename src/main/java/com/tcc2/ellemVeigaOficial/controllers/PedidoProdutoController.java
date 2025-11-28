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
import java.util.List;
import com.tcc2.ellemVeigaOficial.models.PedidoProduto;
import com.tcc2.ellemVeigaOficial.services.PedidoProdutoService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/pedidoproduto")
public class PedidoProdutoController {
    private PedidoProdutoService service;
/*
    @PostMapping
    public ResponseEntity<PedidoProduto> addPedidoProduto(@RequestBody PedidoProduto pedidoProduto){
        return ResponseEntity.ok(service.addPedidoProduto(pedidoProduto));
    }*/

    @PostMapping
        public ResponseEntity<List<PedidoProduto>> addPedidoProdutos(@RequestBody List<PedidoProduto> pedidoProdutos){
            return ResponseEntity.ok(service.addPedidoProdutos(pedidoProdutos));
        }


    @GetMapping("/{id}")
    public ResponseEntity<PedidoProduto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PedidoProduto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoProduto> updatePedidoProduto(@PathVariable Long id, @RequestBody PedidoProduto pedidoProduto) {
        try {
            PedidoProduto updatePedidoProduto = service.update(id, pedidoProduto);
            return ResponseEntity.ok(updatePedidoProduto);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<List<PedidoProduto>> updatePedidoProdutos(@RequestBody List<PedidoProduto> pedidoProdutos) {
        try {
            List<PedidoProduto> updatedList = service.updatePedidoProdutos(pedidoProdutos);
            return ResponseEntity.ok(updatedList);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedidoProduto(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<PedidoProduto>> buscarPedidoProdutos(
            @RequestParam(required = false) Long codPedido,
            @RequestParam(required = false) String nomeProduto) {
        List<PedidoProduto> pedidosProdutos = service.buscarPedidoProdutos(codPedido, nomeProduto);
        return ResponseEntity.ok(pedidosProdutos);
    }

    @GetMapping("/pedido/{pedidoId}")
    public List<PedidoProduto> getByPedidoId(@PathVariable Long pedidoId) {
        return service.findByPedidoId(pedidoId);
    }
}