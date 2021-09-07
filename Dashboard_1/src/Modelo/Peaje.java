/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author AUXINVTIC
 */
public class Peaje {
    private String id_peaje;
    private String descripcion;
    private int valor;

    public String getId_peaje() {
        return id_peaje;
    }

    public void setId_peaje(String id_peaje) {
        this.id_peaje = id_peaje;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
    
    public String toString(){
        return this.descripcion;
    }
}
