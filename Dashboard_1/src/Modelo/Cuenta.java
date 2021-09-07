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
public class Cuenta
{
    int idCuenta;
    String codigo;
    String nombre;

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String cod_cuenta) {
        this.codigo = cod_cuenta;
    }

    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombreCuenta) {
        this.nombre = nombreCuenta;
    }
}
