package com.tcc2.ellemVeigaOficial.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import com.tcc2.ellemVeigaOficial.dto.VendasPorProdutoDTO;
import com.tcc2.ellemVeigaOficial.models.Produto;
import com.tcc2.ellemVeigaOficial.models.ProdutoVenda;
import com.tcc2.ellemVeigaOficial.models.Venda;
import com.tcc2.ellemVeigaOficial.repositories.ProdutoRepository;
import com.tcc2.ellemVeigaOficial.repositories.ProdutoVendaRepository;
import com.tcc2.ellemVeigaOficial.repositories.VendaRepository;

@Service
public class ProdutoVendaService {
    @Autowired
    private ProdutoVendaRepository repository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private VendaRepository vendaRepository;

    @Transactional
    public List<ProdutoVenda> addProdutoVendas(List<ProdutoVenda> produtoVendas) {
        List<ProdutoVenda> savedList = new ArrayList<>();

        for (ProdutoVenda pv : produtoVendas) {
            // 🔍 Validar Produto
            if (pv.getProduto() == null || pv.getProduto().getId() == null) {
                throw new IllegalArgumentException("Produto é obrigatório e deve conter um ID válido.");
            }

            Produto produto = produtoRepository.findById(pv.getProduto().getId())
                .orElseThrow(() -> new RuntimeException("Produto com ID " + pv.getProduto().getId() + " não encontrado"));

            // 🔍 Validar Venda
            if (pv.getVenda() == null || pv.getVenda().getId() == null) {
                throw new IllegalArgumentException("Venda é obrigatória e deve conter um ID válido.");
            }

            Venda venda = vendaRepository.findById(pv.getVenda().getId())
                .orElseThrow(() -> new RuntimeException("Venda com ID " + pv.getVenda().getId() + " não encontrada"));

            // ✅ Associar entidades e salvar
            pv.setProduto(produto);
            pv.setVenda(venda);
            savedList.add(repository.save(pv));
        }

        return savedList;
    }



    public ProdutoVenda findById(Long id){
        return repository.findById(id).get();
    }

    public List<ProdutoVenda> findAll(){
        return repository.findAll();
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    @Transactional
    public ProdutoVenda update(Long id, ProdutoVenda produtoVenda){
        if (!repository.existsById(id)) {
            throw new RuntimeException("Relação produto X venda não encontrada");}
        produtoVenda.setId(id);

        if (produtoVenda.getProduto() != null && produtoVenda.getProduto().getId() != null) {
            Produto produto = produtoRepository.findById(produtoVenda.getProduto().getId()).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            produtoVenda.setProduto(produto);
        }
        
        if (produtoVenda.getVenda() != null && produtoVenda.getVenda().getId() != null) {
            Venda venda = vendaRepository.findById(produtoVenda.getVenda().getId()).orElseThrow(() -> new RuntimeException("Venda não encontrada"));
            produtoVenda.setVenda(venda);
        }
        
        return repository.save(produtoVenda);
    }

    @Transactional
    public List<ProdutoVenda> updateProdutosVenda(List<ProdutoVenda> produtosVenda) {
        List<ProdutoVenda> atualizados = new ArrayList<>();

        for (ProdutoVenda pv : produtosVenda) {
            Long id = pv.getId();
            if (id == null || !repository.existsById(id)) {
                throw new RuntimeException("Relação produto x venda com ID " + id + " não encontrada");
            }

            if (pv.getProduto() != null && pv.getProduto().getId() != null) {
                Produto produto = produtoRepository.findById(pv.getProduto().getId())
                    .orElseThrow(() -> new RuntimeException("Produto com ID " + pv.getProduto().getId() + " não encontrado"));
                pv.setProduto(produto);
            }

            if (pv.getVenda() != null && pv.getVenda().getId() != null) {
                Venda venda = vendaRepository.findById(pv.getVenda().getId())
                    .orElseThrow(() -> new RuntimeException("Venda com ID " + pv.getVenda().getId() + " não encontrada"));
                pv.setVenda(venda);
            }

            atualizados.add(repository.save(pv));
        }

        return atualizados;
    }


    public List<ProdutoVenda> buscarProdutoVendas(Long idVenda, String nomeProduto) {
        return repository.buscarProdutoVendas(idVenda, nomeProduto);
    }

    public List<VendasPorProdutoDTO> buscarVendasPorProdutoNoPeriodo(Date dataInicial, Date dataFinal) {
        return repository.agruparVendasPorNomeProduto(dataInicial, dataFinal);
    }
}