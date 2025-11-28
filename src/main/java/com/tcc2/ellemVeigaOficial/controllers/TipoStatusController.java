package com.tcc2.ellemVeigaOficial.controllers;

import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tcc2.ellemVeigaOficial.dto.TipoStatusDTO;
import com.tcc2.ellemVeigaOficial.models.TipoStatus;

@RestController
@RequestMapping("/status")
public class TipoStatusController {
    TipoStatusDTO tipoStatusDTO;

    @GetMapping
    public List<TipoStatusDTO> getAllStatus() {
        return Arrays.stream(TipoStatus.values())
                     .map(status -> new TipoStatusDTO(status.name(), status.getDescricao()))
                     .collect(Collectors.toList());
    }
}
