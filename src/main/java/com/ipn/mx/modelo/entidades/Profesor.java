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
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author alexi
 */
@Data
@NoArgsConstructor
@Entity
@Table(name= "Profesor")
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProfesor;
    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;
    @Column(name = "apellidoPaterno", length = 100, nullable = false)
    private String apellidoPaterno;
    @Column(name = "sueldo",precision=2, nullable = false)
    private double sueldo;
}
