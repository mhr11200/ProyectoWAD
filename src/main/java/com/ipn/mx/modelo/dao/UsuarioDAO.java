/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.entidades.Usuario;
import com.ipn.mx.utilerias.HibernateUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author alexi
 */
public class UsuarioDAO {

    public Boolean create(Usuario dto) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.getTransaction();
        try {
            tx.begin();
            s.save(dto);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            return false;
        }
    }

    public void update(Usuario dto) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.getTransaction();
        try {
            tx.begin();
            s.update(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void delete(Usuario dto) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.getTransaction();
        try {
            tx.begin();
            s.delete(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Usuario read(Usuario dto) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.getTransaction();
        try {
            tx.begin();
            dto = s.get(dto.getClass(), dto.getIdUsuario());
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Error: " + e.getMessage());
        }
        return dto;
    }

    public List readAll() {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.getTransaction();
        List<Usuario> lista = new ArrayList<>();
        try {
            tx.begin();
            Query q = s.createQuery("from Usuario p order by p.idUsuario");
            lista = q.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Error: " + e.getMessage());
        }
        return lista;
    }

    public Usuario LogIn(Usuario dto) {
        GraficaDAO conectar = new GraficaDAO();

        Connection conexion = conectar.conectar();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement("Select * from usuario where nombreusuario = ? and contrasena = ?;");
            ps.setString(1, dto.getNombreUsuario());
            ps.setString(2, dto.getContrasena());
            rs = ps.executeQuery();
            while (rs.next()) {
                dto.setNombre(rs.getString("nombre"));
                dto.setApellidoPaterno(rs.getString("apellidoPaterno"));
                dto.setIdUsuario(rs.getInt("idUsuario"));
                dto.setCorreo(rs.getString("correo"));
                dto.setNombreUsuario(rs.getString("nombreUsuario"));
                dto.setContrasena("");
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
                return dto;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> lista = dao.readAll();
        for (Usuario categoriaDTO : lista) {
            System.out.println("Elemento: " + categoriaDTO);
        }
    }
}
