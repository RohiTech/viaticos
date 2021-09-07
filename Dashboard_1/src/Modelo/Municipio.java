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
public class Municipio {

    private String codigo;
    private String municipio;
    private int valor_hospedaje;
    private int valor_alimentacion;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public int getValor_hospedaje() {
        return valor_hospedaje;
    }

    public void setValor_hospedaje(int valor_hospedaje) {
        this.valor_hospedaje = valor_hospedaje;
    }

    public int getValor_alimentacion() {
        return valor_alimentacion;
    }

    public void setValor_alimentacion(int valor_alimentacion) {
        this.valor_alimentacion = valor_alimentacion;
    }

    public String toString(){
        return this.municipio;
    }
   
    
    
    
}
