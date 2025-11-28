package com.tcc2.ellemVeigaOficial.controllers;

import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;
import com.tcc2.ellemVeigaOficial.dto.FormaPagamentoDTO;
import com.tcc2.ellemVeigaOficial.models.FormaPagamento;

@RestController
@RequestMapping("/formapagamento")
public class FormaPagamentoController {
    FormaPagamentoDTO formaPagamentoDTO;

    @GetMapping
    public List<FormaPagamentoDTO> getAllFormasPagamento() {
        return Arrays.stream(FormaPagamento.values())
                     .map(forma -> new FormaPagamentoDTO(forma.name(), forma.getDescricao()))
                     .collect(Collectors.toList());
    }
}