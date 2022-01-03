/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;

import com.ipn.mx.modelo.dao.AlumnoDAO;
import com.ipn.mx.modelo.dao.GraficaDAO;
import com.ipn.mx.modelo.dao.GrupoDAO;
import com.ipn.mx.modelo.entidades.Alumno;
import com.ipn.mx.modelo.entidades.Grupo;
import com.ipn.mx.modelo.entidades.Grafica;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author mauri
 */
@ManagedBean(name = "alumnoMB")
@SessionScoped
public class AlumnoMB extends BaseBean implements Serializable {

    private final AlumnoDAO dao = new AlumnoDAO();
    private final GrupoDAO grupoDAO = new GrupoDAO();
    
    private StreamedContent chartImage;

    private Alumno dto;

    private List<Alumno> listaAlumno;
    private List<Grupo> listaGrupo;

    public AlumnoMB() {
    }

    public StreamedContent getChartImage() {
        return chartImage;
    }

    public void setChartImage(StreamedContent chartImage) {
        this.chartImage = chartImage;
    }
    
    

    public List<Grupo> getListaGrupo() {
        return listaGrupo;
    }

    public void setListaGrupo(List<Grupo> listaGrupo) {
        this.listaGrupo = listaGrupo;
    }

    public Alumno getDto() {
        return dto;
    }

    public void setDto(Alumno dto) {
        this.dto = dto;
    }

    public List<Alumno> getListaAlumno() {
        return listaAlumno;
    }

    public void setListaAlumno(List<Alumno> listaAlumno) {
        this.listaAlumno = listaAlumno;
    }

    @PostConstruct
    public void init() {
        listaAlumno = new ArrayList<>();
        listaGrupo = new ArrayList<>();

        listaGrupo = grupoDAO.readAll();
        listaAlumno = dao.readAll();
        
    }

    public String prepareAdd() {
        dto = new Alumno();
        setAccion(ACC_CREAR);
        return "/alumnos/alumnoForm?faces-redirect=true";
    }

    public String prepareUpdate() {
        setAccion(ACC_ACTUALIZAR);
        return "/alumnos/alumnoForm?faces-redirect=true";
    }

    public String prepareIndex() {
        init();
        return "/alumnos/listadoAlumnos?faces-redirect=true";
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
            Grupo grupo = new Grupo();
            grupo.setIdGrupo(dto.getIdGrupoAux());
            grupo = grupoDAO.read(grupo);
            dto.setIdGrupo(grupo);
            dao.create(dto);
            return prepareIndex();
        }

        return prepareAdd();
    }

    public String update() {
        Boolean valido = validate();
        if (valido) {
            Grupo grupo = new Grupo();
            grupo.setIdGrupo(dto.getIdGrupoAux());
            grupo = grupoDAO.read(grupo);
            dto.setIdGrupo(grupo);
            dao.update(dto);
            return prepareIndex();
        }

        return prepareUpdate();
    }

    public String delete() {
        dao.delete(dto);
        return prepareIndex();
    }

    public void seleccionarAlumno(ActionEvent event) {
        String claveSeleccionada = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("claveUsuario");
        dto = new Alumno();
        dto.setIdAlumno(Integer.parseInt(claveSeleccionada));

        try {
            dto = dao.read(dto);
            dto.setIdGrupoAux(dto.getIdGrupo().getIdGrupo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void graficarCalificacion() {
        String claveSeleccionada = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("claveUsuario");    
        System.out.println("Acaaaa\n\n "+claveSeleccionada);
        JFreeChart grafica = ChartFactory.createPieChart("Calificaiones", obtenerGraficaCalificacionAlumno(Integer.parseInt(claveSeleccionada)), true, true, Locale.getDefault());
        try {
            File chartFile = new File("dynamichart.png");
            ChartUtils.saveChartAsPNG(chartFile, grafica, 500, 500);
            chartImage = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
        } catch (IOException ex) {
            Logger.getLogger(AlumnoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private PieDataset obtenerGraficaCalificacionAlumno (int idAlumno){
        DefaultPieDataset dsPie = new DefaultPieDataset();
        GraficaDAO dao = new GraficaDAO();
        try {
            List datos;
            datos = dao.graficarCalificacionAlumno(idAlumno);
            for (int i = 0; i < datos.size(); i++) {
                Grafica dto = (Grafica) datos.get(i);
                dsPie.setValue(dto.getMateria(),dto.getCalificacion());
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dsPie;
    }
}
