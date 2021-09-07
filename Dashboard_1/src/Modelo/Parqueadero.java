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
public class Parqueadero {
    private String id_parqueadero;
    private String descripcion;
    private int valor;

    public String getId_parqueadero() {
        return id_parqueadero;
    }

    public void setId_parqueadero(String id_parqueadero) {
        this.id_parqueadero = id_parqueadero;
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
