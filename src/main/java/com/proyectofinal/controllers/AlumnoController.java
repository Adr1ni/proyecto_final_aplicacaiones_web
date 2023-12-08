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
import java.util.Set;

@Controller
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private LaboratorioService laboratorioService;


    @GetMapping
    public String listarAlumnos(Model model) {
        List<Alumno> alumnos = alumnoService.getAllAlumnos();
        model.addAttribute("alumnos", alumnos);
        return "alumnos/listaAlumnos";
    }


    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        Alumno alumno = new Alumno();
        model.addAttribute("alumno", alumno);
        return "alumnos/formularioAlumno";
    }


    @PostMapping("/nuevo")
    public String guardarAlumno(@ModelAttribute("alumno") Alumno alumno) {
        alumnoService.saveAlumno(alumno);
        return "redirect:/alumnos";
    }


    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Alumno alumno = alumnoService.getAlumnoById(id);
        List<Laboratorio> laboratorios = laboratorioService.getAllLaboratorios();
        model.addAttribute("alumno", alumno);
        model.addAttribute("laboratorios", laboratorios);
        return "alumnos/formularioAlumno";
    }

    @PostMapping("/editar/{id}")
    public String actualizarAlumno(@PathVariable Long id, @ModelAttribute("alumno") Alumno alumno) {
        alumnoService.saveAlumno(alumno);
        return "redirect:/alumnos";
    }

    // Eliminar un alumno
    @GetMapping("/eliminar/{id}")
    public String eliminarAlumno(@PathVariable Long id) {
        alumnoService.deleteAlumno(id);
        return "redirect:/alumnos";
    }


    @GetMapping("/{id}/tareas")
    public String mostrarTareasDeAlumno(@PathVariable Long id, Model model) {
        List<Tarea> tareas = alumnoService.getTareasByAlumno(id);
        model.addAttribute("tareas", tareas);
        return "tareas/listaTareas";
    }


    @PostMapping("/{alumnoId}/asociar-laboratorios")
    public String asociarLaboratorios(@PathVariable Long alumnoId, @RequestParam Set<Long> laboratorioIds) {
        alumnoService.addLaboratoriosToAlumno(alumnoId, laboratorioIds);
        return "redirect:/alumnos";
    }


    @PostMapping("/{alumnoId}/desasociar-laboratorios")
    public String desasociarLaboratorios(@PathVariable Long alumnoId, @RequestParam Set<Long> laboratorioIds) {
        alumnoService.deleteLaboratoriosDeAlumno(alumnoId, laboratorioIds);
        return "redirect:/alumnos";
    }

    // Asociar tareas a un alumno
    @PostMapping("/{alumnoId}/asociar-tareas")
    public String asociarTareas(@PathVariable Long alumnoId, @RequestParam Set<Long> tareaIds) {
        alumnoService.addTareasToAlumno(alumnoId, tareaIds);
        return "redirect:/alumnos";
    }

    // Desasociar tareas de un alumno
    @PostMapping("/{alumnoId}/desasociar-tareas")
    public String desasociarTareas(@PathVariable Long alumnoId, @RequestParam Set<Long> tareaIds) {
        alumnoService.deleteTareasDeAlumno(alumnoId, tareaIds);
        return "redirect:/alumnos";
    }
}

