package com.tcc2.ellemVeigaOficial.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import com.tcc2.ellemVeigaOficial.models.Pedido;
import com.tcc2.ellemVeigaOficial.models.PedidoProduto;
import com.tcc2.ellemVeigaOficial.models.Produto;
import com.tcc2.ellemVeigaOficial.repositories.PedidoProdutoRepository;
import com.tcc2.ellemVeigaOficial.repositories.PedidoRepository;
import com.tcc2.ellemVeigaOficial.repositories.ProdutoRepository;


@Service
public class PedidoProdutoService {
    @Autowired
    private PedidoProdutoRepository repository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    public List<PedidoProduto> addPedidoProdutos(List<PedidoProduto> pedidoProdutos) {
        List<PedidoProduto> savedList = new ArrayList<>();
        for (PedidoProduto pp : pedidoProdutos) {
            if (pp.getPedido() != null && pp.getPedido().getId() != null) {
                Pedido pedido = pedidoRepository.findById(pp.getPedido().getId())
                    .orElseThrow(() -> new RuntimeException("Pedido com ID " + pp.getPedido().getId() + " não encontrado"));
                pp.setPedido(pedido);
            }
            if (pp.getProduto() != null && pp.getProduto().getId() != null) {
                Produto produto = produtoRepository.findById(pp.getProduto().getId())
                    .orElseThrow(() -> new RuntimeException("Produto com ID " + pp.getProduto().getId() + " não encontrado"));
                pp.setProduto(produto);
            }
            savedList.add(repository.save(pp));
        }
        return savedList;
    }

    public List<PedidoProduto> findByPedidoId(Long pedidoId) {
        return repository.findByPedidoId(pedidoId);
    }

    public PedidoProduto findById(Long id){
        return repository.findById(id).get();
    }

    public List<PedidoProduto> findAll(){
        return repository.findAll();
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    @Transactional
    public PedidoProduto update(Long id, PedidoProduto pedidoProduto){
        if (!repository.existsById(id)) {
            throw new RuntimeException("Relação pedido X produto não encontrada");}
        pedidoProduto.setId(id);

        if (pedidoProduto.getPedido() != null && pedidoProduto.getPedido().getId() != null) {
            Pedido pedido = pedidoRepository.findById(pedidoProduto.getPedido().getId()).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
            pedidoProduto.setPedido(pedido);
        }
        
        if (pedidoProduto.getProduto() != null && pedidoProduto.getProduto().getId() != null) {
            Produto produto = produtoRepository.findById(pedidoProduto.getProduto().getId()).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            pedidoProduto.setProduto(produto);
        }

        return repository.save(pedidoProduto);
    }

    @Transactional
    public List<PedidoProduto> updatePedidoProdutos(List<PedidoProduto> pedidoProdutos) {
        List<PedidoProduto> updatedList = new ArrayList<>();

        for (PedidoProduto pp : pedidoProdutos) {
            Long id = pp.getId();

            if (id == null || !repository.existsById(id)) {
                throw new RuntimeException("Relação pedido x produto com ID " + id + " não encontrada");
            }

            if (pp.getPedido() != null && pp.getPedido().getId() != null) {
                Pedido pedido = pedidoRepository.findById(pp.getPedido().getId())
                    .orElseThrow(() -> new RuntimeException("Pedido com ID " + pp.getPedido().getId() + " não encontrado"));
                pp.setPedido(pedido);
            }

            if (pp.getProduto() != null && pp.getProduto().getId() != null) {
                Produto produto = produtoRepository.findById(pp.getProduto().getId())
                    .orElseThrow(() -> new RuntimeException("Produto com ID " + pp.getProduto().getId() + " não encontrado"));
                pp.setProduto(produto);
            }

            pp.setId(id); // garantir que está setando corretamente o ID para update
            updatedList.add(repository.save(pp));
        }

        return updatedList;
    }


    public List<PedidoProduto> buscarPedidoProdutos(Long codPedido, String nomeProduto) {
        return repository.buscarPedidoProdutos(codPedido, nomeProduto);
    }
}