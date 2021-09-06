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
public class Gasolina {
    private String id_gasolina;
    private String descripcion;
    private int valor;

    public String getId_gasolina() {
        return id_gasolina;
    }

    public void setId_gasolina(String id_gasolina) {
        this.id_gasolina = id_gasolina;
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
