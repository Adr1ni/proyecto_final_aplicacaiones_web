package com.proyectofinal.interfaces;

import com.proyectofinal.models.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ITarea extends JpaRepository<Tarea, Long> {
    List<Tarea> findAllByAlumno_Id(Long id);

    List<Tarea> findAllByLaboratorio_Id(Long id);
}

