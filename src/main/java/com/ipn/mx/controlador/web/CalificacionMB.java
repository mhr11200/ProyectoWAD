/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;

import static com.ipn.mx.controlador.web.BaseBean.ACC_ACTUALIZAR;
import static com.ipn.mx.controlador.web.BaseBean.ACC_CREAR;
import com.ipn.mx.modelo.dao.AlumnoDAO;
import com.ipn.mx.modelo.dao.CalificacionesDAO;
import com.ipn.mx.modelo.dao.MateriaDAO;
import com.ipn.mx.modelo.entidades.Alumno;
import com.ipn.mx.modelo.entidades.Calificaciones;
import com.ipn.mx.modelo.entidades.Materia;
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
@ManagedBean(name = "calificacionMB")
@javax.faces.bean.SessionScoped
public class CalificacionMB extends BaseBean implements Serializable {

    private CalificacionesDAO dao = new CalificacionesDAO();
    private MateriaDAO matDAO = new MateriaDAO();
    private AlumnoDAO alDAO = new AlumnoDAO();
    private Calificaciones dto;

    private List<Calificaciones> listaCalificaciones;
    private List<Materia> listaMaterias;
    private List<Alumno> listaAlumnos;
    
    private String errorPeriodo;
    private String errorCalificacion;

    public CalificacionMB() {
    }

    public String getErrorPeriodo() {
        return errorPeriodo;
    }

    public void setErrorPeriodo(String errorPeriodo) {
        this.errorPeriodo = errorPeriodo;
    }

    public String getErrorCalificacion() {
        return errorCalificacion;
    }

    public void setErrorCalificacion(String errorCalificacion) {
        this.errorCalificacion = errorCalificacion;
    }
    
    

    public CalificacionesDAO getDao() {
        return dao;
    }

    public void setDao(CalificacionesDAO dao) {
        this.dao = dao;
    }

    public MateriaDAO getMatDAO() {
        return matDAO;
    }

    public void setMatDAO(MateriaDAO matDAO) {
        this.matDAO = matDAO;
    }

    public AlumnoDAO getAlDAO() {
        return alDAO;
    }

    public void setAlDAO(AlumnoDAO alDAO) {
        this.alDAO = alDAO;
    }

    public Calificaciones getDto() {
        return dto;
    }

    public void setDto(Calificaciones dto) {
        this.dto = dto;
    }

    public List<Calificaciones> getListaCalificaciones() {
        return listaCalificaciones;
    }

    public void setListaCalificaciones(List<Calificaciones> listaCalificaciones) {
        this.listaCalificaciones = listaCalificaciones;
    }

    public List<Materia> getListaMaterias() {
        return listaMaterias;
    }

    public void setListaMaterias(List<Materia> listaMaterias) {
        this.listaMaterias = listaMaterias;
    }

    public List<Alumno> getListaAlumnos() {
        return listaAlumnos;
    }

    public void setListaAlumnos(List<Alumno> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }

    @PostConstruct
    public void init() {
        listaCalificaciones = new ArrayList<>();
        listaAlumnos = new ArrayList<>();
        listaMaterias = new ArrayList<>();

        listaCalificaciones = dao.readAll();
        listaAlumnos = alDAO.readAll();
        listaMaterias = matDAO.readAll();
    }

    public String prepareAdd() {
        dto = new Calificaciones();
        setAccion(ACC_CREAR);
        return "/calificacion/calificacionForm?faces-redirect=true";
    }

    public String prepareUpdate() {
        setAccion(ACC_ACTUALIZAR);
        return "/calificacion/calificacionForm?faces-redirect=true";
    }

    public String prepareIndex() {
        init();
        return "/calificacion/listadoCalificacion?faces-redirect=true";
    }

    public Boolean validate() {
        if(dto.getPeriodo() < 0 || dto.getPeriodo() > 3){
            setErrorPeriodo("El periodo debe estar dentro de 1 y 3");
            return false;
        }
        if(dto.getCalificacion()< 0 || dto.getCalificacion()> 10){
            setErrorCalificacion("La calificación va de 0 a 10");
            return false;
        }
        
        setErrorCalificacion(null);
        setErrorPeriodo(null);
        return true;
    }

    public String add() {
        Boolean valido = validate();
        if (valido) {
            Alumno al = new Alumno();
            Materia mat = new Materia();
            al.setIdAlumno(dto.getIdAlumnoAux());
            mat.setIdMateria(dto.getIdMateriaAux());
            al = alDAO.read(al);
            mat = matDAO.read(mat);
            dto.setIdAlumno(al);
            dto.setIdMateria(mat);
            dao.create(dto);
            return prepareIndex();
        }

        return prepareAdd();
    }

    public String update() {
        Boolean valido = validate();
        if (valido) {
            Alumno al = new Alumno();
            Materia mat = new Materia();
            al.setIdAlumno(dto.getIdAlumnoAux());
            mat.setIdMateria(dto.getIdMateriaAux());
            al = alDAO.read(al);
            mat = matDAO.read(mat);
            dto.setIdAlumno(al);
            dto.setIdMateria(mat);
            dao.update(dto);
            return prepareIndex();
        }

        return prepareUpdate();
    }

    public String delete() {
        dao.delete(dto);
        return prepareIndex();
    }

    public void seleccionarCalificacion(ActionEvent event) {
        String claveSeleccionada = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("claveUsuario");
        dto = new Calificaciones();
        dto.setIdCalificacion(Integer.parseInt(claveSeleccionada));
        try {
            dto = dao.read(dto);
            dto.setIdAlumnoAux(dto.getIdAlumno().getIdAlumno());
            dto.setIdMateriaAux(dto.getIdMateria().getIdMateria());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
