package com.api.v1.repository;
import java.util.List;

import com.api.v1.model.VeiculoModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VeiculoRepository extends JpaRepository<VeiculoModel, Integer> {

    @Query(value = "SELECT * FROM Veiculos u WHERE u.nome like %:nome%", nativeQuery = true)
    List<VeiculoModel> findByNomeLike(String nome);

    @Query(value = "SELECT id, nome_comercial, visualizar FROM Veiculos order by visualizar desc limit 4", nativeQuery = true)
    List<Object> findByViews();

}