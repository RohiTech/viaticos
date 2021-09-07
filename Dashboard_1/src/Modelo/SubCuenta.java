/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author jfrancisco
 */
public class SubCuenta
{
    int idSubCuenta;
    String codigo;
    String nombre;
    int idCuenta;

    public int getIdSubCuenta() {
        return idSubCuenta;
    }

    public void setIdSubCuenta(int idSubCuenta) {
        this.idSubCuenta = idSubCuenta;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setNombre(String nombreCuenta) {
        this.nombre = nombreCuenta;
    }

    public String getNombre() {
        return nombre;
    }
    
    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }
}
