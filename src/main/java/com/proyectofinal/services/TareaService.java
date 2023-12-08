package com.proyectofinal.services;

import com.proyectofinal.interfaces.ITarea;
import com.proyectofinal.models.Tarea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TareaService {


    private final ITarea tareaRepository;

    public TareaService(ITarea tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    // Métodos para operaciones CRUD
    public List<Tarea> getAllTareas() {
        return tareaRepository.findAll();
    }

    public Tarea getTareaById(Long id) {
        return tareaRepository.findById(id).orElse(null);
    }

    public void saveTarea(Tarea tarea) {
        tareaRepository.save(tarea);
    }

    public void deleteTarea(Long id) {
        tareaRepository.deleteById(id);
    }

}
