/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;

import com.ipn.mx.modelo.dao.UsuarioDAO;
import com.ipn.mx.modelo.entidades.Usuario;
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
@ManagedBean(name = "usuarioMB")
@SessionScoped
public class UsuarioMB extends BaseBean implements Serializable {

    private final UsuarioDAO dao = new UsuarioDAO();
    private Usuario dto;
    private List<Usuario> listaUsuario;

    /**
     * Creates a new instance of UsuarioMB
     */
    public UsuarioMB() {
    }

    public Usuario getDto() {
        return dto;
    }

    public void setDto(Usuario dto) {
        this.dto = dto;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    @PostConstruct
    public void init() {
        listaUsuario = new ArrayList<>();

        listaUsuario = dao.readAll();
        System.out.println("LISTA\n\n");
        System.out.println(listaUsuario);
    }

    public String prepareAdd() {
        dto = new Usuario();
        setAccion(ACC_CREAR);
        return "/usuario/usuarioForm?faces-redirect=true";
    }

    public String prepareUpdate() {
        setAccion(ACC_ACTUALIZAR);
        return "/usuario/usuarioForm?faces-redirect=true";
    }

    public String prepareLogIn() {
        dto = new Usuario();
        setAccion(ACC_LOGIN);
        return "/usuario/LogIn?faces-redirect=true";
    }

    public String prepareIndex() {
        init();
        return "/usuario/listadoUsuarios?faces-redirect=true";
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
            System.out.println("\n\nACAAAAAAA\n\n");
            System.out.println("VALORES");
            System.out.println(dto);
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

    public void seleccionarUsuario(ActionEvent event) {
        String claveSeleccionada = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("claveUsuario");
        dto = new Usuario();
        dto.setIdUsuario(Integer.parseInt(claveSeleccionada));

        try {
            dto = dao.read(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String LogInMB() {
        System.out.println("\n\nACAAAAAAA\n\n");
        System.out.println("VALORES");
        System.out.println(dto);
        try {
            if (dao.LogIn(dto)) {
                return prepareIndex();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/index.xhtml";
    }
}
