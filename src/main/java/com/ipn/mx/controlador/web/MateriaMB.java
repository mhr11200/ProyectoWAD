/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;

import static com.ipn.mx.controlador.web.BaseBean.ACC_ACTUALIZAR;
import static com.ipn.mx.controlador.web.BaseBean.ACC_CREAR;
import com.ipn.mx.modelo.dao.MateriaDAO;
import com.ipn.mx.modelo.entidades.Grupo;
import com.ipn.mx.modelo.entidades.Materia;
import com.ipn.mx.modelo.entidades.Profesor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author alexi
 */
@ManagedBean(name = "materiaMB")
@javax.faces.bean.SessionScoped
public class MateriaMB extends BaseBean implements Serializable{
    private MateriaDAO dao= new MateriaDAO();
    private Materia dto;
    private List<Materia> listaMateria;

    public MateriaDAO getDao() {
        return dao;
    }

    public void setDao(MateriaDAO dao) {
        this.dao = dao;
    }

    public Materia getDto() {
        return dto;
    }

    public void setDto(Materia dto) {
        this.dto = dto;
    }

    public List<Materia> getListaMateria() {
        return listaMateria;
    }

    public void setListaMateria(List<Materia> listaMateria) {
        this.listaMateria = listaMateria;
    }
    @PostConstruct
    public void init() {
        listaMateria= new ArrayList<Materia>();
        listaMateria= dao.readAll();
    }
    public String prepareAdd() {
        dto = new Materia();
        setAccion(ACC_CREAR);
        return "/materia/materiaForm?faces-redirect=true";
    }

    public String prepareUpdate() {
        setAccion(ACC_ACTUALIZAR);
        return "/materia/materiaForm?faces-redirect=true";
    }
    
    public String prepareIndex() {
        init();
        return "/materia/listadoMateria?faces-redirect=true";
    }
    
    public Boolean validate() {
        boolean valido = true;
        //if(dto.getNombreUsuario() == null){
        //    valido = false;
        //}
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

    public void seleccionarMateria(ActionEvent event) {
        String claveSeleccionada = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("claveUsuario");
        dto = new Materia();
        dto.setIdMateria(Integer.parseInt(claveSeleccionada));

        try {
            dto = dao.read(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
