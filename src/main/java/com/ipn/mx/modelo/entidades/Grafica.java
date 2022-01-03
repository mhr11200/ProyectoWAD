/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.entidades;

import java.io.Serializable;
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
public class Grafica implements Serializable{
    private String nombre;
    private int grupo;
    private String materia;
    private int calificacion;
    private int periodo;
}