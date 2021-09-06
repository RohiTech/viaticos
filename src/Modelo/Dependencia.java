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
public class Dependencia {
    String id_dependencia;
    String dependencia;

    public String getId_dependencia() {
        return id_dependencia;
    }

    public void setId_dependencia(String id_dependencia) {
        this.id_dependencia = id_dependencia;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }
    
    public String toString(){
        return this.dependencia;
    }
}
