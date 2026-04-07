package br.com.fiap.locatech.locatech.dtos;

import java.time.LocalDate;

public record AluguelRequestDTO(
        Long pessoaId,
        Long veiculoId,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
