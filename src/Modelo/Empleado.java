/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Blob;

/**
 *
 * @author AUXINVTIC
 */
public class Empleado {
    
    private String id;
    private String codigo;
    private String nombre_uno;
    private String nombre_dos;
    private String apellido_uno;
    private String apellido_dos;
    private String telefono_corporativo;
    private String telefono_personal;
    private String direccion;
    private String correo;
    private String fecha_registro;
    private String municipio;
    private Blob foto;
    private String estado;
    private Blob huella;
    private String id_municipio;
    private String id_cargo;
    private String id_estado;
    private String cargo;
    private String id_tipo_doc;
    private String documento;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre_uno() {
        return nombre_uno;
    }

    public void setNombre_uno(String nombre_uno) {
        this.nombre_uno = nombre_uno;
    }

    public String getNombre_dos() {
        return nombre_dos;
    }

    public void setNombre_dos(String nombre_dos) {
        this.nombre_dos = nombre_dos;
    }

    public String getApellido_uno() {
        return apellido_uno;
    }

    public void setApellido_uno(String apellido_uno) {
        this.apellido_uno = apellido_uno;
    }

    public String getApellido_dos() {
        return apellido_dos;
    }

    public void setApellido_dos(String apellido_dos) {
        this.apellido_dos = apellido_dos;
    }

    public String getTelefono_corporativo() {
        return telefono_corporativo;
    }

    public void setTelefono_corporativo(String telefono_corporativo) {
        this.telefono_corporativo = telefono_corporativo;
    }

    public String getTelefono_personal() {
        return telefono_personal;
    }

    public void setTelefono_personal(String telefono_personal) {
        this.telefono_personal = telefono_personal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Blob getFoto() {
        return foto;
    }

    public void setFoto(Blob foto) {
        this.foto = foto;
    }

    public Blob getHuella() {
        return huella;
    }

    public void setHuella(Blob huella) {
        this.huella = huella;
    }

    public String getId_municipio() {
        return id_municipio;
    }

    public void setId_municipio(String id_municipio) {
        this.id_municipio = id_municipio;
    }

    public String getId_cargo() {
        return id_cargo;
    }

    public void setId_cargo(String id_cargo) {
        this.id_cargo = id_cargo;
    }

    public String getId_estado() {
        return id_estado;
    }

    public void setId_estado(String id_estado) {
        this.id_estado = id_estado;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getId_tipo_doc() {
        return id_tipo_doc;
    }

    public void setId_tipo_doc(String id_tipo_doc) {
        this.id_tipo_doc = id_tipo_doc;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
    
    public String toString(){
        return this.nombre_uno;
    }
    
    
    
}
