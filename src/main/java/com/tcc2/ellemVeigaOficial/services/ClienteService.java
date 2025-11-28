package com.tcc2.ellemVeigaOficial.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tcc2.ellemVeigaOficial.repositories.UsuarioRepository;
import com.tcc2.ellemVeigaOficial.tratamentoerros.ClienteNaoEncontradoException;
import com.tcc2.ellemVeigaOficial.tratamentoerros.UsuarioNaoEncontradoException;

import lombok.AllArgsConstructor;
import java.util.List;
import com.tcc2.ellemVeigaOficial.models.Cliente;
import com.tcc2.ellemVeigaOficial.models.Usuario;
import com.tcc2.ellemVeigaOficial.repositories.ClienteRepository;


@Service
@AllArgsConstructor
public class ClienteService {
    @Autowired
    private ClienteRepository repository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente findById(Long id) {
                return repository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente com ID " + id + " não encontrado."));
    }

    public Cliente addCliente(Cliente cliente) {
        validarUsuario(cliente);
        return repository.save(cliente);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ClienteNaoEncontradoException("Cliente com ID " + id + " não existe.");
        }
        repository.deleteById(id);
    }

    @Transactional
    public Cliente update(Long id, Cliente cliente) {

        if (!repository.existsById(id)) {
            throw new ClienteNaoEncontradoException("Cliente com ID " + id + " não encontrado.");
        }
        cliente.setId(id);
        validarUsuario(cliente);
        return repository.save(cliente);
    }

    public List<Cliente> buscarClientes(Long id, String nome) {
        return repository.buscarClientes(id, nome);
    }

    private void validarUsuario(Cliente cliente) {
        if (cliente.getUsuario() != null && cliente.getUsuario().getId() != null) {
            Usuario usuario = usuarioRepository.findById(cliente.getUsuario().getId())
                    .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com ID " + cliente.getUsuario().getId() + " não encontrado."));
            cliente.setUsuario(usuario);
        }
    }

}