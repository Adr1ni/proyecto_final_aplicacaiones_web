package com.proyectofinal.interfaces;

import com.proyectofinal.models.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IAlumno extends JpaRepository<Alumno, Long> {
    List<Alumno> findAlumnoByLaboratorioId(Long id);
}

