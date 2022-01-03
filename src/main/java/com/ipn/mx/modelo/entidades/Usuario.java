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
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author mauri
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Usuario")
public class Usuario implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;
    
    @Column(name = "nombre",length = 50, nullable = false)
    private String nombre;
    
    @Column(name = "apellidoPaterno",length = 100, nullable = false)
    private String apellidoPaterno;
    @Column(name = "nombreUsuario",length = 50, nullable = false)
    private String nombreUsuario;
    @Column(name = "contrasena",length = 50, nullable = false)
    private String contrasena;
}
