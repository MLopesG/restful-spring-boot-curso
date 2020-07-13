package com.api.v1.repository;
import java.util.List;

import com.api.v1.model.VeiculoModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VeiculoRepository extends JpaRepository<VeiculoModel, Integer> {

    @Query(value = "SELECT * FROM Veiculos u WHERE u.nome ilike %:nome% or u.nome_comercial ilike %:nome% or u.marca ilike %:nome% limit 20", nativeQuery = true)
    List<VeiculoModel> findByNomeLike(String nome);

    @Query(value = "SELECT id, nome_comercial, visualizar FROM Veiculos order by visualizar desc limit 4", nativeQuery = true)
    List<Object> findByMax();

    @Query(value = "SELECT id, nome_comercial, visualizar FROM Veiculos order by visualizar asc limit 4", nativeQuery = true)
    List<Object> findByMin();

    @Query(value = "SELECT * FROM Veiculos order by id desc limit :limit", nativeQuery = true)
    List<VeiculoModel> findByLimit(Integer limit);
}
