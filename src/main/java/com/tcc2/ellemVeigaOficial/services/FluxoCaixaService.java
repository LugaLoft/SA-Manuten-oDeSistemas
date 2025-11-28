package com.tcc2.ellemVeigaOficial.services;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import com.tcc2.ellemVeigaOficial.models.FluxoCaixa;
import com.tcc2.ellemVeigaOficial.models.FormaPagamento;
import com.tcc2.ellemVeigaOficial.models.Produto;
import com.tcc2.ellemVeigaOficial.models.ProdutoVenda;
import com.tcc2.ellemVeigaOficial.models.Venda;
import com.tcc2.ellemVeigaOficial.repositories.ProdutoVendaRepository;
import com.tcc2.ellemVeigaOficial.repositories.VendaRepository;

@Service
public class FluxoCaixaService {
    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private ProdutoVendaRepository produtovendaRepository;

    public List<FluxoCaixa> gerarRelatorioFluxoCaixa(Date dataInicio, Date dataFim, FormaPagamento formaPagamento) {
        List<FluxoCaixa> relatorio = new ArrayList<>();
        List<Venda> vendas = vendaRepository.findByDataBetweenAndFormaPagamento(dataInicio, dataFim, formaPagamento);

        Float totalEntradas = 0f;
        Float totalSaidas = 0f;

        for (Venda venda : vendas) {
            Float valorVenda = venda.getValorTotal();
            Float valorSemComissao = valorVenda;

            if (venda.getVendedor() != null && venda.getComissao() != null) {
                valorSemComissao = valorVenda - venda.getComissao();
            }

            List<ProdutoVenda> produtosVenda = produtovendaRepository.findByVenda(venda);

            String nomeProdutoEntrada;
            if (produtosVenda.size() == 1) {
                nomeProdutoEntrada = produtosVenda.get(0).getProduto().getNome();
            } else {
                nomeProdutoEntrada = produtosVenda.stream()
                        .map(pv -> String.valueOf(pv.getProduto().getId()))
                        .collect(Collectors.joining(", "));
            }

            relatorio.add(FluxoCaixa.builder()
                    .data(venda.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                    .idVenda(venda.getId())
                    .valor(valorVenda)
                    .valorSemComissao(valorSemComissao)
                    .nomeCliente(venda.getCliente() != null ? venda.getCliente().getNome() : "Desconhecido")
                    .formaPagamento(venda.getFormaPagamento())
                    .tipo("entrada")
                    .nomeProduto(nomeProdutoEntrada)
                    .build());

            totalEntradas += valorVenda;

            Float custoTotalVenda = 0f;
            for (ProdutoVenda produtoVenda : produtosVenda) {
                Produto produto = produtoVenda.getProduto();
                if (produto != null && produto.getCustoProduto() != null) {
                    custoTotalVenda += produto.getCustoProduto() * produtoVenda.getQuantidade();
                }
            }

            relatorio.add(FluxoCaixa.builder()
                    .data(venda.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                    .idVenda(venda.getId())
                    .nomeProduto(nomeProdutoEntrada)
                    .nomeCliente(venda.getCliente() != null ? venda.getCliente().getNome() : "Desconhecido")
                    .valor(custoTotalVenda)
                    .formaPagamento(venda.getFormaPagamento())
                    .tipo("saida")
                    .build());

            totalSaidas += custoTotalVenda;
        }

        relatorio.add(FluxoCaixa.builder().valor(totalEntradas).tipo("totalEntrada").build());
        relatorio.add(FluxoCaixa.builder().valor(totalSaidas).tipo("totalSaida").build());
        return relatorio;
    }
}