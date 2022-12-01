package com.example.recrutement_microservice.repository;

import com.example.recrutement_microservice.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface EmployeRepository extends JpaRepository<Employe, Long>{
    List<Employe> findByChercheurId(Long chercheurid);
}

