/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author AUXINVTIC
 */
public class Conexion {
    
    //private final String base = "viaticos";   
    //private final String user = "sp";
    //private final String password = "sp/*010/";
    //private final String url = "jdbc:mysql://192.168.10.50:3306/" + base;
    
    private final String base = "viaticos";   
    private final String user = "root";
    private final String password = "@#Unicenta@";
    private final String url = "jdbc:mysql://localhost:3306/" + base;
    
    
    private Connection con = null;
    
    public Connection getConexion(){
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(this.url, this.user, this.password);
           
        } catch (SQLException | ClassNotFoundException e) {
            //System.err.println(e);
            JOptionPane.showMessageDialog(null, "Error en la conexión a la \nbase de datos.");
        } 
        return con;
        
    }

    public void desconectar(){
        con = null;
        System.out.println("Desconexion a base de datos listo...");
    }
    
    
}
