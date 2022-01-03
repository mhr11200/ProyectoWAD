/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.entidades;

import java.io.Serializable;
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
@Table(name= "Alumno")
public class Alumno implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAlumno;
    @Column(name = "nombre",length = 50, nullable = false)
    private String nombre;
    @Column(name = "apellidoPaterno",length = 100, nullable = false)
    private String apellidoPaterno;
    @ManyToOne()
    @JoinColumn(name = "idGrupo")
    private Grupo idGrupo;
    
    @Transient
    private int idGrupoAux;
}
