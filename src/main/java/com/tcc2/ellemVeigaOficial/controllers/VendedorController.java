package com.tcc2.ellemVeigaOficial.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.tcc2.ellemVeigaOficial.models.TipoStatus;
import com.tcc2.ellemVeigaOficial.models.Vendedor;
import com.tcc2.ellemVeigaOficial.services.VendedorService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/vendedor")
public class VendedorController {
    private VendedorService service;

    @PostMapping
    public ResponseEntity<Vendedor> addVendedor(@RequestBody Vendedor Vendedor){
        return ResponseEntity.ok(service.addVendedor(Vendedor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendedor> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Vendedor>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Vendedor> updateVendedor(@PathVariable Long id, @RequestBody Vendedor vendedor) {
        try {
            Vendedor updateVendedor = service.update(id, vendedor);
            return ResponseEntity.ok(updateVendedor);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    /*
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendedor(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }*/

    @GetMapping("/buscar")
    public ResponseEntity<List<Vendedor>> buscarVendedores(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String sobrenome,
            @RequestParam(required = false) TipoStatus status) {
        
        List<Vendedor> vendedores = service.buscarVendedores(id, nome, sobrenome, status);
        return ResponseEntity.ok(vendedores);
    }
}