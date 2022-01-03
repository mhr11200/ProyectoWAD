/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author alexi
 */
@Data
@NoArgsConstructor
@Entity
@Table(name= "Calificaciones")
public class Calificaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCalificacion;
    @ManyToOne()
    @JoinColumn(name = "idMateria")
    private Materia idMateria;
    @ManyToOne()
    @JoinColumn(name = "idAlumno")
    private Alumno idAlumno;
    @Column (name = "periodo", nullable = false)
    private int periodo;
    @Column(name = "calificacion", precision=2, nullable = false)
    private double calificacion;
    
    @Transient
    private int idMateriaAux;
    @Transient
    private int idAlumnoAux;
}
