package com.proyectofinal.services;

import com.proyectofinal.interfaces.IAlumno;
import com.proyectofinal.interfaces.ILaboratorio;
import com.proyectofinal.interfaces.ITarea;
import com.proyectofinal.models.Alumno;
import com.proyectofinal.models.Laboratorio;
import com.proyectofinal.models.Tarea;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AlumnoService {

    private final IAlumno alumnoRepository;

    private final ILaboratorio laboratorioRepository;

    private final ITarea tareaRepository;

    public AlumnoService(IAlumno alumnoRepository, ILaboratorio laboratorioRepository, ITarea tareaRepository) {
        this.alumnoRepository = alumnoRepository;
        this.laboratorioRepository = laboratorioRepository;
        this.tareaRepository = tareaRepository;
    }

    public List<Alumno> getAllAlumnos() {
        return alumnoRepository.findAll();
    }

    public Alumno getAlumnoById(Long id) {
        return alumnoRepository.findById(id).orElse(null);
    }

    public void saveAlumno(Alumno alumno) {
        alumnoRepository.save(alumno);
    }


    public void deleteAlumno(Long id) {
        alumnoRepository.deleteById(id);
    }

    public List<Laboratorio> getLaboratoriosByAlumnoId(Long id) {
        return laboratorioRepository.findLaboratorioByAlumnosId(id);
    }

    public void addLaboratoriosToAlumno(Long alumnoId, Set<Long> laboratorioIds) {
        Alumno alumno = alumnoRepository.findById(alumnoId).orElse(null);

        if (alumno != null) {
            List<Laboratorio> laboratorios = new ArrayList<>(laboratorioRepository.findAllById(laboratorioIds));
            alumno.getLaboratorio().addAll(laboratorios);

            for (Laboratorio laboratorio : laboratorios) {
                laboratorio.getAlumnos().add(alumno);
                laboratorioRepository.save(laboratorio);
            }

            alumnoRepository.save(alumno);
        }
    }

    public void editLaboratoriosDeAlumno(Long alumnoId, Set<Long> nuevoLaboratorioIds) {
        Alumno alumno = alumnoRepository.findById(alumnoId).orElse(null);

        if (alumno != null) {
            List<Laboratorio> laboratoriosActuales = alumno.getLaboratorio();

            // Eliminar laboratorios que ya no están asociados
            laboratoriosActuales.stream()
                    .filter(laboratorio -> !nuevoLaboratorioIds.contains(laboratorio.getId()))
                    .forEach(laboratorio -> {
                        laboratorio.getAlumnos().remove(alumno);
                    });
            laboratoriosActuales.removeIf(laboratorio -> !nuevoLaboratorioIds.contains(laboratorio.getId()));

            // Agregar nuevos laboratorios
            List<Laboratorio> nuevosLaboratorios = laboratorioRepository.findAllById(nuevoLaboratorioIds);
            laboratoriosActuales.addAll(nuevosLaboratorios);

            // Actualizar la asociación en ambas direcciones
            laboratoriosActuales.forEach(laboratorio -> {
                laboratorio.getAlumnos().add(alumno);
            });

            alumnoRepository.save(alumno);
        }
    }

    public void deleteLaboratoriosDeAlumno(Long alumnoId, Set<Long> laboratorioIds) {
        Alumno alumno = alumnoRepository.findById(alumnoId).orElse(null);

        if (alumno != null) {
            List<Laboratorio> laboratoriosAEliminar = laboratorioRepository.findAllById(laboratorioIds);

            laboratoriosAEliminar.forEach(laboratorio -> {
                laboratorio.getAlumnos().remove(alumno);
            });

            laboratoriosAEliminar.forEach(alumno.getLaboratorio()::remove);
            alumnoRepository.save(alumno);
        }
    }

    public void addTareasToAlumno(Long alumnoId, Set<Long> tareaIds) {
        Alumno alumno = alumnoRepository.findById(alumnoId).orElse(null);

        if (alumno != null) {
            List<Tarea> tareas = tareaRepository.findAllById(tareaIds);

            for (Tarea tarea : tareas) {
                tarea.setAlumno(alumno);
                alumno.getTareas().add(tarea);
            }

            alumnoRepository.save(alumno);
        }
    }



    public List<Tarea> getTareasByAlumno(Long alumnoId) {
        return tareaRepository.findAllByAlumno_Id(alumnoId);
    }

    public void editTareasDeAlumno(Long alumnoId, Set<Long> nuevaTareaIds) {
        Alumno alumno = alumnoRepository.findById(alumnoId).orElse(null);

        if (alumno != null) {
            List<Tarea> tareasActuales = alumno.getTareas();

            tareasActuales.removeIf(tarea -> !nuevaTareaIds.contains(tarea.getId()));

            List<Tarea> nuevasTareas = tareaRepository.findAllById(nuevaTareaIds);
            tareasActuales.addAll(nuevasTareas);

            alumnoRepository.save(alumno);
        }
    }

    public void deleteTareasDeAlumno(Long alumnoId, Set<Long> tareaIds) {
        Alumno alumno = alumnoRepository.findById(alumnoId).orElse(null);

        if (alumno != null) {
            List<Tarea> tareasAEliminar = tareaRepository.findAllById(tareaIds);

            tareasAEliminar.forEach(tarea -> {
                tarea.setAlumno(null);
            });

            tareasAEliminar.forEach(alumno.getTareas()::remove);
            alumnoRepository.save(alumno);
        }
    }

}

