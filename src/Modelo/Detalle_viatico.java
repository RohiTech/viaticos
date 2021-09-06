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
public class Detalle_viatico {
    private String id_detalle_viatico;
    private String detalle;
    private String valor;
    private int id_viatico;
    private int pivot;
    private String tipo_viatico;

    public String getId_detalle_viatico() {
        return id_detalle_viatico;
    }

    public void setId_detalle_viatico(String id_detalle_viatico) {
        this.id_detalle_viatico = id_detalle_viatico;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getId_viatico() {
        return id_viatico;
    }

    public void setId_viatico(int id_viatico) {
        this.id_viatico = id_viatico;
    }

    public int getPivot() {
        return pivot;
    }

    public void setPivot(int pivot) {
        this.pivot = pivot;
    }

    public String getTipo_viatico() {
        return tipo_viatico;
    }

    public void setTipo_viatico(String tipo_viatico) {
        this.tipo_viatico = tipo_viatico;
    }
    
    
}
