/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;

import com.ipn.mx.modelo.dao.UsuarioDAO;
import com.ipn.mx.modelo.entidades.Usuario;
import com.ipn.mx.utilerias.EnviarMail;
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
    
    private String errorUsuario;
    private String errorCorreo;

    /**
     * Creates a new instance of UsuarioMB
     */
    public UsuarioMB() {
    }

    public String getErrorCorreo() {
        return errorCorreo;
    }

    public void setErrorCorreo(String errorCorreo) {
        this.errorCorreo = errorCorreo;
    }

    
    public String getErrorUsuario() {
        return errorUsuario;
    }

    public void setErrorUsuario(String errorUsuario) {
        this.errorUsuario = errorUsuario;
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
        init();
        dto = new Usuario();
        return "/usuario/logIn?faces-redirect=true";
    }

    public String prepareIndex() {
        init();
        return "/usuario/listadoUsuarios?faces-redirect=true";
    }

    public Boolean validate() {
        if (!dto.getCorreo().contains("gmail")){
            setErrorCorreo("Correo invalido, solo acepta gmail");
            return false;
        }
        setErrorCorreo(null);
        return true;
    }

    public String add() {
        Boolean valido = validate();
        if (valido) {
            if(!dao.create(dto)){
                setErrorUsuario("El usuario ya existe");
                return prepareAdd();
            }
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

    public String logInMB() {
        try {
            dto = dao.LogIn(dto);
            if (dto != null) {
                EnviarMail email = new EnviarMail();
                email.enviarCorreo(dto.getCorreo(),"Inicio de sesión","Se ha registrado un inicio de sesión");
                return "/usuario/listadoUsuarios?faces-redirect=true";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prepareLogIn();
    }
}
