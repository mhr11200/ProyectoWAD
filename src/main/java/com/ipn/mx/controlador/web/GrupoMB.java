/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;

import static com.ipn.mx.controlador.web.BaseBean.ACC_ACTUALIZAR;
import static com.ipn.mx.controlador.web.BaseBean.ACC_CREAR;
import com.ipn.mx.modelo.dao.GraficaDAO;
import com.ipn.mx.modelo.dao.GrupoDAO;
import com.ipn.mx.modelo.dao.MateriaDAO;
import com.ipn.mx.modelo.dao.ProfesorDAO;
import com.ipn.mx.modelo.entidades.Grafica;
import com.ipn.mx.modelo.entidades.Grupo;
import com.ipn.mx.modelo.entidades.Materia;
import com.ipn.mx.modelo.entidades.Profesor;
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
 * @author alexi
 */
@ManagedBean(name = "grupoMB")
@javax.faces.bean.SessionScoped
public class GrupoMB extends BaseBean implements Serializable {

    private final GrupoDAO dao = new GrupoDAO();
    private final ProfesorDAO profDAO = new ProfesorDAO();
    private final MateriaDAO matDAO = new MateriaDAO();
    private Grupo dto;
    private List<Grupo> listaGrupos;
    private List<Profesor> listaProf;
    private List<Materia> listaMat;
    
    private String errorNivel;
    private String errorMateria1;
    private String errorMateria2;
    private String errorMateria3;
    
    private StreamedContent chartImage;

    public GrupoMB() {
    }

    public String getErrorNivel() {
        return errorNivel;
    }

    public void setErrorNivel(String errorNivel) {
        this.errorNivel = errorNivel;
    }
    
    public StreamedContent getChartImage() {
        return chartImage;
    }

    public void setChartImage(StreamedContent chartImage) {
        this.chartImage = chartImage;
    }

    public Grupo getDto() {
        return dto;
    }

    public void setDto(Grupo dto) {
        this.dto = dto;
    }

    public List<Grupo> getListaGrupos() {
        return listaGrupos;
    }

    public void setListaGrupos(List<Grupo> listaGrupos) {
        this.listaGrupos = listaGrupos;
    }

    public List<Profesor> getListaProf() {
        return listaProf;
    }

    public void setListaProf(List<Profesor> listaProf) {
        this.listaProf = listaProf;
    }

    public List<Materia> getListaMat() {
        return listaMat;
    }

    public void setListaMat(List<Materia> listaMat) {
        this.listaMat = listaMat;
    }

    public String getErrorMateria1() {
        return errorMateria1;
    }

    public void setErrorMateria1(String errorMateria1) {
        this.errorMateria1 = errorMateria1;
    }

    public String getErrorMateria2() {
        return errorMateria2;
    }

    public void setErrorMateria2(String errorMateria2) {
        this.errorMateria2 = errorMateria2;
    }

    public String getErrorMateria3() {
        return errorMateria3;
    }

    public void setErrorMateria3(String errorMateria3) {
        this.errorMateria3 = errorMateria3;
    }
    
    @PostConstruct
    public void init() {
        listaGrupos = new ArrayList<>();
        listaGrupos = dao.readAll();
        listaProf = new ArrayList<>();
        listaProf = profDAO.readAll();
        listaMat = new ArrayList<>();
        listaMat = matDAO.readAll();
    }

    public String prepareAdd() {
        dto = new Grupo();
        setAccion(ACC_CREAR);
        return "/grupo/grupoForm?faces-redirect=true";
    }

    public String prepareUpdate() {
        setAccion(ACC_ACTUALIZAR);
        return "/grupo/grupoForm?faces-redirect=true";
    }

    public String prepareIndex() {
        init();
        return "/grupo/listadoGrupo?faces-redirect=true";
    }

    public Boolean validate() {
        if(dto.getNivel() < 1 || dto.getNivel() > 3){
            setErrorNivel("El nivel debe estar dentro de 1 y 3");
            return false;
        }
        if(dto.getIdMateriaUnoaux() == dto.getIdMateriaDosaux()) {
            setErrorMateria1("No se puede repetir");
            setErrorMateria2("No se puede repetir");
            if (dto.getIdMateriaUnoaux() == dto.getIdMateriaTresaux()){
                setErrorMateria3("No se puede repetir");
            }
            return false;
        }
        if (dto.getIdMateriaUnoaux() == dto.getIdMateriaTresaux()){
            setErrorMateria1("No se puede repetir");
            setErrorMateria3("No se puede repetir");
            return false;
        }
        if (dto.getIdMateriaDosaux() == dto.getIdMateriaTresaux()){
            setErrorMateria2("No se puede repetir");
            setErrorMateria3("No se puede repetir");
            return false;
        }
        setErrorMateria1(null);
        setErrorMateria2(null);
        setErrorMateria3(null);
        setErrorNivel(null);
        return true;
    }

    public String add() {
        Boolean valido = validate();
        if (valido) {
            Profesor prof = new Profesor();
            Materia mat1 = new Materia();
            Materia mat2 = new Materia();
            Materia mat3 = new Materia();
            prof.setIdProfesor(dto.getIdProfesorAux());
            mat1.setIdMateria(dto.getIdMateriaUnoaux());
            mat2.setIdMateria(dto.getIdMateriaDosaux());
            mat3.setIdMateria(dto.getIdMateriaTresaux());
            prof = profDAO.read(prof);
            mat1 = matDAO.read(mat1);
            mat2 = matDAO.read(mat2);
            mat3 = matDAO.read(mat3);
            dto.setIdProfesor(prof);
            dto.setIdMateriaUno(mat1);
            dto.setIdMateriaDos(mat2);
            dto.setIdMateriaTres(mat3);
            dao.create(dto);
            return prepareIndex();
        }

        return prepareAdd();
    }

    public String update() {
        Boolean valido = validate();
        if (valido) {
            Profesor prof = new Profesor();
            Materia mat1 = new Materia();
            Materia mat2 = new Materia();
            Materia mat3 = new Materia();
            prof.setIdProfesor(dto.getIdProfesorAux());
            mat1.setIdMateria(dto.getIdMateriaUnoaux());
            mat2.setIdMateria(dto.getIdMateriaDosaux());
            mat3.setIdMateria(dto.getIdMateriaTresaux());
            prof = profDAO.read(prof);
            mat1 = matDAO.read(mat1);
            mat2 = matDAO.read(mat2);
            mat3 = matDAO.read(mat3);
            dto.setIdProfesor(prof);
            dto.setIdMateriaUno(mat1);
            dto.setIdMateriaDos(mat2);
            dto.setIdMateriaTres(mat3);
            dao.update(dto);
            return prepareIndex();
        }

        return prepareUpdate();
    }

    public String delete() {
        dao.delete(dto);
        return prepareIndex();
    }

    public void seleccionarGrupo(ActionEvent event) {
        String claveSeleccionada = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("claveUsuario");
        dto = new Grupo();
        dto.setIdGrupo(Integer.parseInt(claveSeleccionada));
        try {
            dto = dao.read(dto);
            dto.setIdProfesorAux(dto.getIdProfesor().getIdProfesor());
            dto.setIdMateriaUnoaux(dto.getIdMateriaUno().getIdMateria());
            dto.setIdMateriaDosaux(dto.getIdMateriaDos().getIdMateria());
            dto.setIdMateriaTresaux(dto.getIdMateriaTres().getIdMateria());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void graficarCalificacion() {
        String claveSeleccionada = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("claeGrupo");    
        System.out.println("Acaaaa\n\n "+claveSeleccionada);
        JFreeChart grafica = ChartFactory.createPieChart("Calificaciones", obtenerGraficaCalificacionAlumno(Integer.parseInt(claveSeleccionada)), true, true, Locale.getDefault());
        try {
            File chartFile = new File("grupoCalificaciones.png");
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
            datos = dao.graficarCalificacionGrupo(idAlumno);
            System.out.println("\n\nDatos\n\n");
            System.out.println(datos);
            for (int i = 0; i < datos.size(); i++) {
                Grafica dto = (Grafica) datos.get(i);
                System.out.println(dto.getMateria());
                System.out.println(dto.getCalificacion());
                dsPie.setValue(dto.getMateria(),dto.getCalificacion());
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dsPie;
    }
}
