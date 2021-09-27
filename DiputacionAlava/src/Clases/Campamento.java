/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author iBaD
 */
public class Campamento {
        
    private int id;
    private String nombre;
    private String lugar;
    private LocalDate fechaI;
    private LocalDate fechaF;
    private int edadMin;
    private int edadMax;
    private int capacidad;
    
    ArrayList<Persona> personas = new ArrayList();

    public Campamento() {
    }

    public Campamento(int id, String nombre, String lugar, LocalDate fechaI, LocalDate fechaF, int edadMin, int edadMax, int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.lugar = lugar;
        this.fechaI = fechaI;
        this.fechaF = fechaF;
        this.edadMin = edadMin;
        this.edadMax = edadMax;
        this.capacidad = capacidad;
    }
    
    public Campamento(String nombre, String lugar, LocalDate fechaI, LocalDate fechaF, int edadMin, int edadMax, int capacidad) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.fechaI = fechaI;
        this.fechaF = fechaF;
        this.edadMin = edadMin;
        this.edadMax = edadMax;
        this.capacidad = capacidad;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public LocalDate getFechaI() {
        return fechaI;
    }

    public void setFechaI(LocalDate fechaI) {
        this.fechaI = fechaI;
    }

    public LocalDate getFechaF() {
        return fechaF;
    }

    public void setFechaF(LocalDate fechaF) {
        this.fechaF = fechaF;
    }

    public int getEdadMin() {
        return edadMin;
    }

    public void setEdadMin(int edadMin) {
        this.edadMin = edadMin;
    }

    public int getEdadMax() {
        return edadMax;
    }

    public void setEdadMax(int edadMax) {
        this.edadMax = edadMax;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public ArrayList<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(Persona p) {
        this.personas.add(p);
    }
    
    
    
}
