/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;

import java.io.Serializable;

/**
 *
 * @author mauri
 */
public class BaseBean implements Serializable {
    protected static final String ACC_CREAR = "CREAR";
    protected static final String ACC_ACTUALIZAR = "ACTUALIZAR";
    protected static final String ACC_LOGIN = "LOGIN";
    
    protected String accion;
    
    public boolean isModoCrear(){
        if (accion != null){
            return accion.equals(ACC_CREAR);
        }
        
        return false;
    }
    
    public boolean isModoActualizar(){
        if (accion != null){
            return accion.equals(ACC_ACTUALIZAR);
        }
        
        return false;
    }
    
    public boolean isModoLogIn(){
        if (accion != null){
            return accion.equals(ACC_LOGIN);
        }
        
        return false;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
    
    
}
