package br.com.fiap.locatech.locatech.services;

import br.com.fiap.locatech.locatech.dtos.AluguelRequestDTO;
import br.com.fiap.locatech.locatech.entities.Aluguel;
import br.com.fiap.locatech.locatech.repositories.AluguelRepository;
import br.com.fiap.locatech.locatech.repositories.VeiculoRepository;
import br.com.fiap.locatech.locatech.services.exceptions.ResourceNotFoundExceptions;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AluguelService {

    private final AluguelRepository aluguelRepository;
    private final VeiculoRepository veiculoRepository;

    public AluguelService(AluguelRepository aluguelRepository, VeiculoRepository veiculoRepository) {
        this.aluguelRepository = aluguelRepository;
        this.veiculoRepository = veiculoRepository;
    }

    public List<Aluguel> findAllAlugueis(int page, int size) {
        int offset = (page - 1) * size;
        return aluguelRepository.findAll(size, offset);
    }

    public Optional<Aluguel> findAluguelId(Long id) {
        return Optional.ofNullable(aluguelRepository.findByid(id).orElseThrow(() -> new ResourceNotFoundExceptions("Aluguel não encontrado")));
    }

    public void saveAluguel(AluguelRequestDTO aluguel) {
        var aluguelEntity = calculaAluguel(aluguel);
        var save = aluguelRepository.save(aluguelEntity);
        Assert.state(save == 1, "Error ao salvar pessoa" + aluguel.pessoaId());
    }

    public void updateAluguel(Aluguel aluguel, Long id) {
        var updade = aluguelRepository.update(aluguel, id);
        if (updade == 0) {
            throw new RuntimeException("Pessoa não encontrado");
        }
    }

    public void delete(Long id) {
        var delete = aluguelRepository.delete(id);
        if (delete == 0) {
            throw new RuntimeException("Pessoa não encontrada");
        }
    }

    private Aluguel calculaAluguel(AluguelRequestDTO aluguelRequestDTO) {
        var veiculo = veiculoRepository.findByid(aluguelRequestDTO.veiculoId())
                .orElseThrow(() -> new RuntimeException(("Veiculo não encontrado")));
        var quantidaDeDias = BigDecimal.valueOf(aluguelRequestDTO.dataFim().getDayOfYear() - aluguelRequestDTO.dataInicio().getDayOfYear());
        var valor = veiculo.getValorDiaria().multiply(quantidaDeDias);
        return new Aluguel(aluguelRequestDTO, valor);
    }

}
