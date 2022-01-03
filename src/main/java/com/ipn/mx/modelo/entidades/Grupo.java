/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name= "Grupo")
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idGrupo;
    @ManyToOne()
    @JoinColumn(name = "idProfesor")
    private Profesor idProfesor;
    @ManyToOne()
    @JoinColumn(name = "idMateriaUno")
    private Materia idMateriaUno;
    @ManyToOne()
    @JoinColumn(name = "idMateriaDos")
    private Materia idMateriaDos;
    @ManyToOne()
    @JoinColumn(name = "idMateriaTres")
    private Materia idMateriaTres;
    @Column(name = "nivel", nullable = false)
    private int nivel;
    @Transient
    private int idProfesorAux;
    @Transient
    private int idMateriaUnoaux;
    @Transient
    private int idMateriaDosaux;
    @Transient
    private int idMateriaTresaux;
    
    
}
