package br.com.fiap.locatech.locatech.controllers;

import br.com.fiap.locatech.locatech.entities.Veiculo;
import br.com.fiap.locatech.locatech.services.VeiculoServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private static final Logger logger = LoggerFactory.getLogger(VeiculoController.class);

    private final VeiculoServices veiculoService;

    public VeiculoController(VeiculoServices veiculoService) {
        this.veiculoService = veiculoService;
    }

    // http://localhost:8080/veiculos?page=1&size=10
    @GetMapping
    public ResponseEntity<List<Veiculo>> findAllVeiculos(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
      logger.info("foi acessado o endpoint de veículos /veiculos");
      var veiculos = veiculoService.findAllVeiculos(page, size);
      return ResponseEntity.ok(veiculos);
    }

    // http://localhost:8080/veiculos/1
    @GetMapping("/{id}")
    public ResponseEntity <Optional<Veiculo>> findVeiculo(
            @PathVariable("id") long id
    ){
        logger.info("/veiculos/" + id);
        var veiculo = veiculoService.findVeiculoById(id);
        return ResponseEntity.ok(veiculo);
    }

    @PostMapping
    public ResponseEntity<Void> saveVeiculo(
            @RequestBody Veiculo veiculo
    ) {
        logger.info("POST -> /Veiculo");
        veiculoService.saveVeiculo(veiculo);
        return ResponseEntity.status(201).build();
    }

    // http://localhost:8080/veiculos/1
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateVeiculo(
            @PathVariable("id") long id,
            @RequestBody Veiculo veiculo
    ){
        logger.info("PUT -> /veiculo/" + id);
        veiculoService.updateVeiculo(veiculo, id);
        var status = HttpStatus.NO_CONTENT;
        return ResponseEntity.status(status.value()).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeiculo(
            @PathVariable("id") long id
    ){
        logger.info("DELETE -> /veiculo/" + id);
        veiculoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
