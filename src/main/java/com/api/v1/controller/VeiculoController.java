package com.api.v1.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.api.v1.model.VeiculoModel;
import com.api.v1.repository.VeiculoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// Marcos Lopes Gonçalves

// {
//     "id": 1,
//     "nome": "DD 1800",
//     "categoria": "Rodoviário",
//     "marca": "Marcopolo",
//     "ano": 2018,
//     "imagem": "https://c1.staticflickr.com/5/4731/24479352687_f0b1c2a82c_b.jpg",
//     "nome_comercial": "Marcopolo Paradiso 1800 DD (G7)",
//     "eixo": "8X4",
//     "geracao": "G7",
//     "descricao": "Marcopolo Paradiso 1800 DD (G7)\",\"8x4\",\"G7\",\"Marcopolo Paradiso é uma carroceria de ônibus rodoviários, apropriados para médias e longas distâncias, fabricada pela empresa gaúcha Marcopolo. Foi produzido desde o lançamento, em 1983, sobre vários chassis d",
//     "visualizar": 15
// } 

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/veiculos")

public class VeiculoController {
    
    @Autowired    
    VeiculoRepository repository;

    @GetMapping("")
    public ResponseEntity<List<VeiculoModel>> getAllVeiculos(){
        List<VeiculoModel> veiculosList = repository.findAll();

        if(veiculosList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<VeiculoModel>> (veiculosList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<VeiculoModel> getOneVeiculo(@PathVariable(value = "id") Integer id){
        Optional<VeiculoModel> veiculo = repository.findById(id);

        // Contador de visualizações

        VeiculoModel Veiculoview = veiculo.get();
        Veiculoview.setVisualizar(Veiculoview.getVisualizar() + 1);
        repository.save(Veiculoview);

        if(!veiculo.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<VeiculoModel> (Veiculoview, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<VeiculoModel> saveVeiculo( @RequestBody  @Valid VeiculoModel veiculo) {
        return new ResponseEntity<VeiculoModel> (repository.save(veiculo), HttpStatus.CREATED); 
    }

    @DeleteMapping("{id}")
    public ResponseEntity<VeiculoModel> deleteVeiculo(@PathVariable(value = "id") Integer id){
        Optional<VeiculoModel> veiculo = repository.findById(id);

        if(!veiculo.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        repository.delete(veiculo.get());
        return new ResponseEntity<> (HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<VeiculoModel> editVeiculo(@PathVariable(value = "id") Integer id, @RequestBody @Validated  VeiculoModel veiculo){
        Optional<VeiculoModel> veiculoEdit = repository.findById(id);

        if(!veiculoEdit.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        veiculo.setId(veiculoEdit.get().getId());
        return new ResponseEntity<VeiculoModel> (repository.save(veiculo),HttpStatus.OK);
    }
}