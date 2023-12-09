package com.proyectofinal.models;

import lombok.Data;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Data
@Entity
public class Laboratorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String Curso;

    private String Docente;

    @ManyToMany
    @JoinTable(
            name = "lab_alumnos",
            joinColumns = @JoinColumn(name = "lab_id"),
            inverseJoinColumns = @JoinColumn(name = "alumno_id")
    )
    private List<Alumno> alumnos;


    @OneToMany(mappedBy = "laboratorio")
    private List<Tarea> tarea;

}
