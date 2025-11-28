package com.tcc2.ellemVeigaOficial.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.tcc2.ellemVeigaOficial.models.Cliente;
import com.tcc2.ellemVeigaOficial.models.Pedido;
import com.tcc2.ellemVeigaOficial.models.Usuario;
import com.tcc2.ellemVeigaOficial.models.Venda;
import com.tcc2.ellemVeigaOficial.models.Vendedor;
import com.tcc2.ellemVeigaOficial.repositories.ClienteRepository;
import com.tcc2.ellemVeigaOficial.repositories.PedidoRepository;
import com.tcc2.ellemVeigaOficial.repositories.UsuarioRepository;
import com.tcc2.ellemVeigaOficial.repositories.VendaRepository;
import com.tcc2.ellemVeigaOficial.repositories.VendedorRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class VendaService {
    @Autowired
    private VendaRepository repository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private VendedorRepository vendedorRepository;
    

    public Venda addVenda(Venda venda){
        if (venda.getUsuario() != null && venda.getUsuario().getId() != null) {
            Usuario usuario = usuarioRepository.findById(venda.getUsuario().getId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            venda.setUsuario(usuario);
        }

        if (venda.getPedido() != null && venda.getPedido().getId() != null) {
            // Verifica se o pedido existe no banco, caso contrário, lança uma exceção
            Pedido pedido = pedidoRepository.findById(venda.getPedido().getId())
                    .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
            venda.setPedido(pedido);
        }

        if (venda.getCliente() != null && venda.getCliente().getId() != null) {
            // Verifica se o cliente existe no banco, caso contrário, lança uma exceção
            Cliente cliente = clienteRepository.findById(venda.getCliente().getId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            venda.setCliente(cliente);
        }

        if (venda.getVendedor() != null && venda.getVendedor().getId() != null) {
            // Verifica se o vendedor existe no banco, caso contrário, lança uma exceção
            Vendedor vendedor = vendedorRepository.findById(venda.getVendedor().getId())
                    .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));
            venda.setVendedor(vendedor);
        }

        return repository.save(venda);
    }

    public Venda findById(Long id){
        return repository.findById(id).get();
    }

    public List<Venda> findAll(){
        return repository.findAll();
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    @Transactional
    public Venda update(Long id, Venda venda){
        if (!repository.existsById(id)) {
            throw new RuntimeException("Venda não encontrado");}
        venda.setId(id);

        if (venda.getUsuario() != null && venda.getUsuario().getId() != null) {
            Usuario usuario = usuarioRepository.findById(venda.getUsuario().getId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            venda.setUsuario(usuario);
        }
        
        if (venda.getPedido() != null && venda.getPedido().getId() != null) {
            // Verifica se o pedido existe no banco, caso contrário, lança uma exceção
            Pedido pedido = pedidoRepository.findById(venda.getPedido().getId())
                    .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
            venda.setPedido(pedido);
        }
        
        if (venda.getCliente() != null && venda.getCliente().getId() != null) {
            // Verifica se o cliente existe no banco, caso contrário, lança uma exceção
            Cliente cliente = clienteRepository.findById(venda.getCliente().getId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            venda.setCliente(cliente);
        }
        
        if (venda.getVendedor() != null && venda.getVendedor().getId() != null) {
            // Verifica se o vendedor existe no banco, caso contrário, lança uma exceção
            Vendedor vendedor = vendedorRepository.findById(venda.getVendedor().getId())
                    .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));
            venda.setVendedor(vendedor);
        }

        return repository.save(venda);
    }

    public List<Venda> buscarVendas(Long idVenda, String nomeCliente, Long idPedido, Date dataInicial, Date dataFinal) {
        return repository.buscarVendas(idVenda, nomeCliente, idPedido, dataInicial, dataFinal);
    }

}