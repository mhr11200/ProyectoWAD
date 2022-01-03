/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.entidades.Grafica;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mauri
 */
public class GraficaDAO {

    private Connection conexion;
    private static final String SQL_GALIFICACION_ALUMNO = "select a.nombre || ' ' || a.apellidopaterno as Nombre, a.idgrupo as grupo, m.nombre as materia, c.calificacion as calificacion, c.periodo as periodo from alumno a,materia m,calificaciones c where m.idmateria = c.idmateria  and a.idalumno = ? and c.idalumno = ?;";
    private static final String SQL_MATERIAUNO = "select g.idmateriauno, m.nombre, avg(c.calificacion) as promedio from grupo g, calificaciones c, materia m where g.idmateriauno = c.idmateria and g.idmateriauno  = m.idmateria and g.idgrupo =? group by g.idmateriauno,m.nombre;";
    private static final String SQL_MATERIADOS = "select g.idmateriados, m.nombre, avg(c.calificacion) as promedio from grupo g, calificaciones c, materia m where g.idmateriados = c.idmateria and g.idmateriados  = m.idmateria and g.idgrupo =? group by g.idmateriados,m.nombre;";
    private static final String SQL_MATERIATRES = "select g.idmateriatres , m.nombre, avg(c.calificacion) as promedio from grupo g, calificaciones c, materia m where g.idmateriatres = c.idmateria and g.idmateriatres  = m.idmateria and g.idgrupo =? group by g.idmateriatres,m.nombre;";

    public Connection conectar() {
        String user = "kdadwslapgtibo";
        String pwd = "7b1586622357fc224313ebe46467db90cfa1de509042d312776844edace9fc20";
        String url = "jdbc:postgresql://ec2-52-70-205-234.compute-1.amazonaws.com:5432/d538ff0nkmb8dh";
        String pgDriver = "org.postgresql.Driver";
        try {
            Class.forName(pgDriver);
            conexion = DriverManager.getConnection(url, user, pwd);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return conexion;
    }
    public List graficarCalificacionAlumno(int idAlummno) throws SQLException {
        conectar();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List lista = new ArrayList();
        try {
            ps = conexion.prepareStatement(SQL_GALIFICACION_ALUMNO);
            ps.setInt(1, idAlummno);
            ps.setInt(2, idAlummno);
            rs = ps.executeQuery();
            while (rs.next()) {
                Grafica dto = new Grafica();
                dto.setNombre(rs.getString("nombre"));
                dto.setGrupo(rs.getInt("grupo"));
                dto.setCalificacion(rs.getInt("calificacion"));
                dto.setMateria(rs.getString("materia"));
                dto.setPeriodo(rs.getInt("periodo"));
                lista.add(dto);
            }
        } finally {
            if (rs != null) {
                rs = null;
            }
            if (ps != null) {
                ps = null;
            }
            if (conexion != null) {
                conexion = null;
            }
        }
        return lista;
    }
    
    public List graficarCalificacionGrupo(int idGrupo) throws SQLException {
        conectar();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List lista = new ArrayList();
        try {
            ps = conexion.prepareStatement(SQL_MATERIAUNO);
            ps.setInt(1, idGrupo);
            rs = ps.executeQuery();
            while (rs.next()) {
                Grafica dto = new Grafica();
                dto.setMateria(rs.getString("nombre"));
                dto.setCalificacion(rs.getInt("promedio"));
                dto.setGrupo(0);
                dto.setPeriodo(0);
                dto.setNombre("1");
                lista.add(dto);
            }
            
            ps = conexion.prepareStatement(SQL_MATERIADOS);
            ps.setInt(1, idGrupo);
            rs = ps.executeQuery();
            while (rs.next()) {
                Grafica dto = new Grafica();
                dto.setMateria(rs.getString("nombre"));
                dto.setCalificacion(rs.getInt("promedio"));
                dto.setGrupo(0);
                dto.setPeriodo(0);
                dto.setNombre("1");
                lista.add(dto);
            }
            
            ps = conexion.prepareStatement(SQL_MATERIATRES);
            ps.setInt(1, idGrupo);
            rs = ps.executeQuery();
            while (rs.next()) {
                Grafica dto = new Grafica();
                dto.setMateria(rs.getString("nombre"));
                dto.setCalificacion(rs.getInt("promedio"));
                dto.setGrupo(0);
                dto.setPeriodo(0);
                dto.setNombre("1");
                lista.add(dto);
            }
        } finally {
            if (rs != null) {
                rs = null;
            }
            if (ps != null) {
                ps = null;
            }
            if (conexion != null) {
                conexion = null;
            }
        }
        return lista;
    }
    
    public static void main(String[] args) {
        GraficaDAO dao = new GraficaDAO();
        try {
            System.out.println(dao.graficarCalificacionAlumno(1));
        } catch (SQLException ex) {
            Logger.getLogger(GraficaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
