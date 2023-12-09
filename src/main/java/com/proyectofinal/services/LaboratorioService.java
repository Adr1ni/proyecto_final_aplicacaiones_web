package com.proyectofinal.services;

import com.proyectofinal.interfaces.IAlumno;
import com.proyectofinal.interfaces.ILaboratorio;
import com.proyectofinal.interfaces.ITarea;
import com.proyectofinal.models.Alumno;
import com.proyectofinal.models.Laboratorio;
import com.proyectofinal.models.Tarea;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class LaboratorioService {

    private final ILaboratorio laboratorioRepository;

    private final IAlumno alumnoRepository;

    private final ITarea tareaRepository;

    public LaboratorioService(ILaboratorio laboratorioRepository, IAlumno alumnoRepository, ITarea tareaRepository) {
        this.laboratorioRepository = laboratorioRepository;
        this.alumnoRepository = alumnoRepository;
        this.tareaRepository = tareaRepository;
    }

    public List<Laboratorio> getAllLaboratorios() {
        return laboratorioRepository.findAll();
    }

    public Laboratorio getLaboratorioById(Long id) {
        return laboratorioRepository.findById(id).orElse(null);
    }

    public void saveLaboratorio(Laboratorio laboratorio) {
        laboratorioRepository.save(laboratorio);
    }

    public void deleteLaboratorio(Long id) {
        laboratorioRepository.deleteById(id);
    }

    public List<Alumno> getAlumnosByLaboratorio(Long laboratorioId) {
        return alumnoRepository.findAlumnoByLaboratorioId(laboratorioId);
    }

    public List<Tarea> getTareasByLaboratorio(Long id){
        return tareaRepository.findAllByLaboratorio_Id(id);
    }

    @Transactional
    public void addAlummnosdToLaboratorio(Long laboratorioId, List<Long> alumnosIds) {
        Laboratorio laboratorio = laboratorioRepository.findById(laboratorioId).orElse(null);

        if (laboratorio != null) {
            List<Alumno> alumnos = alumnoRepository.findAllById(alumnosIds);

            // Agrega los alumnos al laboratorio
            laboratorio.setAlumnos(alumnos);

            laboratorioRepository.save(laboratorio);
        }
    }

    @Transactional
    public void editAlumnosDeLaboratorio(Long laboratorioId, List<Long> nuevosAlumnoIds) {
        Laboratorio laboratorio = laboratorioRepository.findById(laboratorioId).orElse(null);

        if (laboratorio != null) {
            List<Alumno> alumnosActuales = laboratorio.getAlumnos();

            // Eliminar alumnos que ya no están asociados
            alumnosActuales.stream()
                    .filter(alumno -> !nuevosAlumnoIds.contains(alumno.getId()))
                    .forEach(alumno -> {
                        alumno.getLaboratorio().remove(laboratorio);
                    });
            alumnosActuales.removeIf(alumno -> !nuevosAlumnoIds.contains(alumno.getId()));

            // Agregar nuevos alumnos
            List<Alumno> nuevosAlumnos = alumnoRepository.findAllById(nuevosAlumnoIds);
            alumnosActuales.addAll(nuevosAlumnos);

            // Actualizar la asociación en ambas direcciones
            alumnosActuales.forEach(alumno -> {
                alumno.getLaboratorio().add(laboratorio);
            });

            laboratorioRepository.save(laboratorio);
        }
    }

    // Eliminar alumnos asociados a un laboratorio
    public void deleteAlumnosDeLaboratorio(Long laboratorioId, Set<Long> alumnoIds) {
        Laboratorio laboratorio = laboratorioRepository.findById(laboratorioId).orElse(null);

        if (laboratorio != null) {
            List<Alumno> alumnosAEliminar = alumnoRepository.findAllById(alumnoIds);

            // Eliminar la asociación en ambas direcciones
            alumnosAEliminar.forEach(alumno -> {
                alumno.getLaboratorio().remove(laboratorio);
            });

            alumnosAEliminar.forEach(laboratorio.getAlumnos()::remove);
            laboratorioRepository.save(laboratorio);
        }
    }

}
