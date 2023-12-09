package com.proyectofinal.controllers;

import com.proyectofinal.models.Alumno;
import com.proyectofinal.models.Laboratorio;
import com.proyectofinal.models.Tarea;
import com.proyectofinal.services.AlumnoService;
import com.proyectofinal.services.LaboratorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/laboratorios")
public class LaboratorioController {

    @Autowired
    private LaboratorioService laboratorioService;

    @Autowired
    private AlumnoService alumnoService;


    @GetMapping
    public String listarLaboratorios(Model model) {
        List<Laboratorio> laboratorios = laboratorioService.getAllLaboratorios();
        model.addAttribute("laboratorios", laboratorios);
        return "laboratorios/listaLaboratorio";
    }

    // Mostrar formulario para agregar un nuevo laboratorio
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        Laboratorio laboratorio = new Laboratorio();
        List<Alumno> alumnos = alumnoService.getAllAlumnos();
        model.addAttribute("laboratorio", laboratorio);
        model.addAttribute("alumnos", alumnos);
        return "laboratorios/formularioLaboratorio";
    }


    @PostMapping("/nuevo")
    public String guardarLaboratorio(@ModelAttribute("laboratorio") Laboratorio laboratorio, @RequestParam("alumnoIds") List<Long> alumnoIds) {
        laboratorioService.saveLaboratorio(laboratorio);
        laboratorioService.addAlummnosdToLaboratorio(laboratorio.getId(), alumnoIds);
        return "redirect:/laboratorios";
    }


    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Laboratorio laboratorio = laboratorioService.getLaboratorioById(id);
        List<Alumno> alumnos = alumnoService.getAllAlumnos();
        model.addAttribute("laboratorio", laboratorio);
        model.addAttribute("alumnos", alumnos);
        return "laboratorios/formularioLaboratorio";
    }


    @PostMapping("/editar/{id}")
    public String actualizarLaboratorio(@PathVariable Long id, @ModelAttribute("laboratorio") Laboratorio laboratorio, @RequestParam("alumnoIds") List<Long> alumnoIds) {
        laboratorioService.saveLaboratorio(laboratorio);
        laboratorioService.editAlumnosDeLaboratorio(laboratorio.getId(), alumnoIds);
        return "redirect:/laboratorios";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarLaboratorio(@PathVariable Long id) {
        laboratorioService.deleteLaboratorio(id);
        return "redirect:/laboratorios";
    }


    @GetMapping("/{id}/alumnos")
    public String mostrarAlumnosDeLaboratorio(@PathVariable Long id, Model model) {
        List<Alumno> alumnos = laboratorioService.getAlumnosByLaboratorio(id);
        model.addAttribute("alumnos", alumnos);
        return "alumnos/listaAlumnos";
    }

    @GetMapping("/{id}/tareas")
    public String mostrarTareasDeLaboratorio(@PathVariable Long id, Model model) {
        List<Tarea> tareas = laboratorioService.getTareasByLaboratorio(id);
        model.addAttribute("tareas", tareas);
        return "tareas/listaTareas";
    }

}
