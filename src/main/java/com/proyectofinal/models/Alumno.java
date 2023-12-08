package com.proyectofinal.models;

import lombok.Data;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Data
@Entity
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombres;
    private String apellidos;
    private int edad;
    private String dni;
    private String correo;
    private String carrera;
    private String semestre;

    @ManyToMany(mappedBy = "alumnos")
    private List<Laboratorio> laboratorio;

    @OneToMany(mappedBy = "alumno")
    private List<Tarea> tareas;

}
