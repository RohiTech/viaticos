/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author AUXINVTIC
 */
public class ConsultaAutorizacion extends Conexion{
    
    PreparedStatement ps;
    ResultSet rs;
    Connection con = getConexion();
    
    public Vector<Dependencia> listarDependencias(){
        Vector<Dependencia> datos = new Vector<Dependencia>();
        Dependencia doc = null;        
        try {  
            String sql = "SELECT id_dependencia, nombre FROM dependencias ORDER BY nombre";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            doc = new Dependencia();
            doc.setId_dependencia("0");
            doc.setDependencia("Seleccione:");
            datos.add(doc);
            
            while(rs.next()){
                doc = new Dependencia();
                doc.setId_dependencia(rs.getString(1));
                doc.setDependencia(rs.getString(2).toUpperCase());
                datos.add(doc);                
            }      
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return datos;        
    }
    
    public Vector<Empleado> listarCargoDep(String id_dependencia){
        Vector<Empleado> datos = new Vector<Empleado>();
        Empleado empleado;
        
        String sql = "SELECT E.identificacion, E.apellido_uno, E.apellido_dos, E.nombre_uno, E.nombre_dos "
                + "FROM empleados E INNER JOIN cargos C ON E.id_cargo = C.id_cargo "
                + "WHERE C.id_dependencia = ? AND estado = 1 ORDER BY E.apellido_uno, E.apellido_dos";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_dependencia);
            rs = ps.executeQuery();
            
            empleado = new Empleado();
            empleado.setId("0");
            empleado.setNombre_uno("Seleccione:");            
            datos.add(empleado);
            
            while(rs.next()){
                empleado = new Empleado();
                empleado.setId(rs.getString(1));
                String nombre = rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5);
                empleado.setNombre_uno(nombre.toUpperCase());            
                datos.add(empleado);
            }
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        
        return datos;
    }
    
    public boolean insertAutorizacion(Autorizacion autorizacion){
        Date fecha = new Date();            
        String strDateFormat = "yyyy-MM-dd"; // El formato de fecha está especificado  
        SimpleDateFormat f = new SimpleDateFormat(strDateFormat);
        String feActual = f.format(fecha);

        Date hora = new Date();
        String strHoraFormat = "hh:mm:ss"; // El formato de fecha está especificado  
        SimpleDateFormat h = new SimpleDateFormat(strHoraFormat);
        String hoActual = h.format(hora);
        String sql = "INSERT INTO autorizaciones(justificacion, valor, id_empleado, estado, autorizado_por, fecha, hora) "
                + "VALUES(?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, autorizacion.getJustificacion());
            ps.setString(2, autorizacion.getValor());
            ps.setString(3, autorizacion.getEmpleado());
            ps.setString(4, autorizacion.getEstado());
            ps.setString(5, autorizacion.getId_usuario());
            ps.setString(6, feActual);
            ps.setString(7, hoActual);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public ArrayList<Autorizacion> listaAutorizaciones(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Autorizacion> datos = new ArrayList<Autorizacion>();
        Autorizacion autorizacion;
        String sql = "SELECT A.id_autorizacion, A.justificacion, A.valor, E.apellido_uno, E.apellido_dos, E.nombre_uno, E.nombre_dos "
                + " FROM autorizaciones A INNER JOIN empleados E ON A.id_empleado = E.identificacion WHERE A.estado = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "1");
            rs = ps.executeQuery();
            while(rs.next()){
                autorizacion = new Autorizacion();
                autorizacion.setId_autorizacion(rs.getString(1));
                autorizacion.setJustificacion(rs.getString(2));
                autorizacion.setValor(rs.getString(3));
                autorizacion.setEmpleado(rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7));
                datos.add(autorizacion);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        return datos;
    }
    
    public boolean anularViaticoAutorizado(int id){
        String sql = "UPDATE autorizaciones SET estado = ? WHERE id_autorizacion = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "3");
            ps.setInt(2, id);            
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public Vector<Empleado> listaEmpleadosAutorizados(String id_autorizacion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        ResultSet rst = null;
        
        Vector<Empleado> datos = new Vector<Empleado>();
        Empleado empleado = null;        
        String c = null;
        try {   
            String sqlt = "SELECT E.identificacion, E.apellido_uno, E.apellido_dos, E.nombre_uno, E.nombre_dos "
                + "FROM autorizaciones A INNER JOIN empleados E ON A.id_empleado = E.identificacion WHERE A.id_autorizacion = ?";
            pst = con.prepareStatement(sqlt);
            pst.setString(1, id_autorizacion);
            rst = pst.executeQuery();
            
            String sql = "SELECT identificacion, apellido_uno, apellido_dos, nombre_uno, nombre_dos"
                    + " FROM empleados ORDER BY apellido_uno, apellido_dos";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rst.next()){
                empleado = new Empleado();
                empleado.setId(rst.getString(1));
                c = rst.getString(1);
                empleado.setNombre_uno(rst.getString(2).toUpperCase()+" "+rst.getString(3).toUpperCase()+" "+rst.getString(4).toUpperCase()+" "+rst.getString(5).toUpperCase());
                datos.add(empleado);
            }           
            
            while(rs.next()){
                empleado = new Empleado();
                if(!rs.getString(1).equals(c)){
                    empleado.setId(rs.getString(1));
                    empleado.setNombre_uno(rs.getString(2).toUpperCase()+" "+rs.getString(3).toUpperCase()+" "+rs.getString(4).toUpperCase()+" "+rs.getString(5).toUpperCase());
                    datos.add(empleado);
                }
            }    
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return datos;
    }

    public boolean updateAutorizacion(Autorizacion autorizacion){
        String sql = "UPDATE autorizaciones SET justificacion = ?, valor = ?, id_empleado = ? WHERE id_autorizacion = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, autorizacion.getJustificacion());
            ps.setString(2, autorizacion.getValor());
            ps.setString(3, autorizacion.getEmpleado());
            ps.setString(4, autorizacion.getId_autorizacion());
            ps.execute();
            return true;            
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
}
