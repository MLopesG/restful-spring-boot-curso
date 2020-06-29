package com.api.v1.repository;
import com.api.v1.model.VeiculoModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends JpaRepository<VeiculoModel,Integer> {
    
}