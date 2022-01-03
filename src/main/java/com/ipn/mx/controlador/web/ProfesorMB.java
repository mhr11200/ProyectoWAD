/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;

import com.ipn.mx.modelo.dao.ProfesorDAO;
import com.ipn.mx.modelo.entidades.Profesor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author mauri
 */

@ManagedBean(name = "profesorMB")
@SessionScoped
public class ProfesorMB extends BaseBean implements Serializable{
    private final ProfesorDAO dao = new ProfesorDAO();
    
    private Profesor dto;

    private List<Profesor> listaProfesor;

    public ProfesorMB(){
    }

    public Profesor getDto() {
        return dto;
    }

    public void setDto(Profesor dto) {
        this.dto = dto;
    }

    public List<Profesor> getListaProfesor() {
        return listaProfesor;
    }

    public void setListaProfesor(List<Profesor> listaProfesor) {
        this.listaProfesor = listaProfesor;
    }
    
    @PostConstruct
    public void init() {
        listaProfesor = new ArrayList<>();

        listaProfesor = dao.readAll();
    }
    
    public String prepareAdd() {
        dto = new Profesor();
        setAccion(ACC_CREAR);
        return "/profesor/profesorForm?faces-redirect=true";
    }

    public String prepareUpdate() {
        setAccion(ACC_ACTUALIZAR);
        return "/profesor/profesorForm?faces-redirect=true";
    }
    
    public String prepareIndex() {
        init();
        return "/profesor/listadoProfesor?faces-redirect=true";
    }
    
    public Boolean validate() {
        boolean valido = true;
        //if(dto.getNombreUsuario() == null){
        //    valido = false;
        //}
        //aqui van validaciones
        return valido;
    }
    
    public String add() {
        Boolean valido = validate();
        if (valido) {
            dao.create(dto);
            return prepareIndex();
        }

        return prepareAdd();
    }

    public String update() {
        Boolean valido = validate();
        if (valido) {
            dao.update(dto);
            return prepareIndex();
        }

        return prepareUpdate();
    }

    public String delete() {
        dao.delete(dto);
        return prepareIndex();
    }

    public void seleccionarProfesor(ActionEvent event) {
        String claveSeleccionada = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("claveUsuario");
        dto = new Profesor();
        dto.setIdProfesor(Integer.parseInt(claveSeleccionada));

        try {
            dto = dao.read(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
