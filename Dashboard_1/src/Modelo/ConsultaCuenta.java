
package Modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AUXINVTIC
 */
public class ConsultaCuenta extends Conexion {
    Connection con = getConexion();
    
    public ArrayList<Cuenta> listarCuentas(){
        ArrayList listaEmpleados = new ArrayList();
        Cuenta cuenta;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT T.codigo, T.name "
                + "FROM Cuenta T ";
        
        try {           
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                cuenta = new Cuenta();
                cuenta.setCodigo(rs.getString(1));
                cuenta.setNombre(rs.getString(2));
            }           
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return listaEmpleados;
        
    }
    
    /*public int ultimo_id_viatico(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = 0;
        String sql = "SELECT MAX(id_viatico) FROM viaticos";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt(1);
            }
            return id;
        } catch (SQLException e) {
            System.err.println(e);
            return id;
        }
    }*/
}
