package com.proyectofinal.interfaces;

import com.proyectofinal.models.Alumno;
import com.proyectofinal.models.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ILaboratorio extends JpaRepository<Laboratorio,Long> {
    List<Laboratorio> findLaboratorioByAlumnosId(Long id);
}
