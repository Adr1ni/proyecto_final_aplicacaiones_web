package com.proyectofinal.controllers;

import com.proyectofinal.models.Alumno;
import com.proyectofinal.models.Laboratorio;
import com.proyectofinal.models.Tarea;
import com.proyectofinal.services.AlumnoService;
import com.proyectofinal.services.LaboratorioService;
import com.proyectofinal.services.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private LaboratorioService laboratorioService;

    // Listar todas las tareas
    @GetMapping
    public String listarTareas(Model model) {
        List<Tarea> tareas = tareaService.getAllTareas();
        model.addAttribute("tareas", tareas);
        return "tareas/listaTareas";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNuevo(Model model) {
        Tarea tarea = new Tarea();
        List<Alumno> alumnos = alumnoService.getAllAlumnos();
        List<Laboratorio> laboratorios = laboratorioService.getAllLaboratorios();
        model.addAttribute("tarea", tarea);
        model.addAttribute("alumnos", alumnos);
        model.addAttribute("laboratorios", laboratorios);
        return "tareas/formularioTarea";
    }


    @PostMapping("/nueva")
    public String guardarTarea(@ModelAttribute("tarea") Tarea tarea,
                               @RequestParam("alumnoId") Long alumnoId,
                               @RequestParam("laboratorioId") Long laboratorioId) {
        // Obtener el alumno y el laboratorio utilizando los IDs
        Alumno alumno = alumnoService.getAlumnoById(alumnoId);
        Laboratorio laboratorio = laboratorioService.getLaboratorioById(laboratorioId);

        tarea.setAlumno(alumno);
        tarea.setLaboratorio(laboratorio);

        tareaService.saveTarea(tarea);

        return "redirect:/tareas";
    }


    // Mostrar formulario para editar una tarea
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Tarea tarea = tareaService.getTareaById(id);
        List<Alumno> alumnos = alumnoService.getAllAlumnos();
        List<Laboratorio> laboratorios = laboratorioService.getAllLaboratorios();
        model.addAttribute("tarea", tarea);
        model.addAttribute("alumnos", alumnos);
        model.addAttribute("laboratorios", laboratorios);
        return "tareas/formularioTarea";
    }

    // Actualizar una tarea existente
    @PostMapping("/editar/{id}")
    public String actualizarTarea(@PathVariable Long id, @ModelAttribute("tarea") Tarea tarea) {
        tareaService.saveTarea(tarea);
        return "redirect:/tareas";
    }

    // Eliminar una tarea
    @GetMapping("/eliminar/{id}")
    public String eliminarTarea(@PathVariable Long id) {
        tareaService.deleteTarea(id);
        return "redirect:/tareas";
    }
}
