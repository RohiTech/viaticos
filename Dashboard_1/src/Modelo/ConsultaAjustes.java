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
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author AUXINVTIC
 */
public class ConsultaAjustes extends Conexion{
    Connection con = getConexion();
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public ArrayList<Dependencia> listDependencia(){
        Dependencia dependencia;
        ArrayList<Dependencia> listaDependencias = new ArrayList();
        String sql = "SELECT id_dependencia, nombre FROM dependencias ORDER BY nombre";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                dependencia = new Dependencia();
                dependencia.setId_dependencia(rs.getString(1));
                dependencia.setDependencia(rs.getString(2));
                listaDependencias.add(dependencia);
            }
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return listaDependencias;
    }
    
    public boolean insertDependencia(String dependencia){
        String sql = "INSERT INTO dependencias(nombre) VALUES(?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, dependencia);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public boolean updateDependencia(Dependencia dep){
        String sql = "UPDATE dependencias SET nombre = ? WHERE id_dependencia = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, dep.getDependencia());
            ps.setString(2, dep.getId_dependencia());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public boolean updateCargo(Cargo car){
        String sql = "UPDATE cargos SET nombre_cargo = ?, id_dependencia = ? WHERE id_cargo = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, car.getNombreCargo());
            ps.setString(2, car.getDependencia());
            ps.setString(3, car.getIdCargo());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public ArrayList<Cargo> listCargoDependencia(String id){
        Cargo cargo;
        ArrayList<Cargo> listaCargos = new ArrayList();
        String sql = "SELECT id_cargo, nombre_cargo FROM cargos WHERE id_dependencia = ? ORDER BY nombre_cargo";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while(rs.next()){
                cargo = new Cargo();
                cargo.setIdCargo(rs.getString(1));
                cargo.setNombreCargo(rs.getString(2));
                listaCargos.add(cargo);
            }
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return listaCargos;
    }
    
    public ArrayList<Cargo> listCargos(){
        Cargo cargo;
        ArrayList<Cargo> listaCargos = new ArrayList();
        String sql = "SELECT C.id_cargo, C.nombre_cargo, D.nombre FROM cargos C "
                + "INNER JOIN dependencias D ON C.id_dependencia = D.id_dependencia "
                + "ORDER BY D.nombre, C.nombre_cargo";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                cargo = new Cargo();
                cargo.setIdCargo(rs.getString(1));
                cargo.setNombreCargo(rs.getString(2));
                cargo.setDependencia(rs.getString(3));
                listaCargos.add(cargo);
            }
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return listaCargos;
    }
    
    public Vector<Dependencia> listarDependencias(){
        
        PreparedStatement ps = null;
        ResultSet rs = null;
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
    
    public boolean insertCargo(Cargo cargo){
        String sql = "INSERT INTO cargos(nombre_cargo, id_dependencia) VALUES(?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cargo.getNombreCargo());
            ps.setString(2, cargo.getDependencia());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e); 
            return false;
        }
    }
    
    
    public Vector<Dependencia> listDependenciasEdit(Cargo c){        
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        ResultSet rst = null;
        Vector<Dependencia> datos = new Vector<Dependencia>();
        Dependencia doc = null;
        String b = null;
        try { 
            String sqlt = "SELECT C.id_dependencia, D.nombre FROM cargos C "
                    + "INNER JOIN dependencias D ON C.id_dependencia = D.id_dependencia WHERE C.id_cargo=?";
            pst = con.prepareStatement(sqlt);
            pst.setString(1, c.getIdCargo());
            rst = pst.executeQuery();
            
            String sql = "SELECT id_dependencia, nombre FROM dependencias ORDER BY nombre";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if(rst.next()){
                doc = new Dependencia();
                doc.setId_dependencia(rst.getString(1));
                b = rst.getString(1);
                doc.setDependencia(rst.getString(2).toUpperCase());
                datos.add(doc);
            }            
            while(rs.next()){                
                if(!rs.getString(1).equals(b)){
                    doc = new Dependencia();
                    doc.setId_dependencia(rs.getString(1));
                    doc.setDependencia(rs.getString(2).toUpperCase());
                    datos.add(doc);
                }                                
            }             
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return datos;        
    }
    
    
    public ArrayList<CostoViatico> listCostoViaticos(){
        CostoViatico costoviatico;
        ArrayList listaCostoViaticos = new ArrayList();
        String sql = "SELECT id_costo_viatico, nombre, valor FROM costo_viaticos ORDER BY nombre";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                costoviatico = new CostoViatico();
                costoviatico.setId_viatico(rs.getString(1));
                costoviatico.setNombre(rs.getString(2));
                costoviatico.setValor(rs.getString(3));
                listaCostoViaticos.add(costoviatico);
            }
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return listaCostoViaticos;
    }
    
    public boolean verificarCostoViaticoCargo(String v, String id_cargo){
        String sql = "SELECT id_viatico_cargo FROM viaticos_cargo WHERE id_cargo = ? AND id_costo_viatico = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_cargo);
            ps.setString(2, v);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e); 
            return false;
        }        
    }
    
    public int idCargo(){
        int id = 0;
        String sql = "SELECT MAX(id_cargo) FROM cargos";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt(1);                
            }
        } catch (SQLException e) {
            System.err.println(e);             
        }
        return id;
    }
    
    public boolean insertViaticoAsignado(int id_cargo, int id_costo_viatico){
        String sql = "INSERT INTO viaticos_cargo(id_cargo, id_costo_viatico) VALUES(?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_cargo);
            ps.setInt(2, id_costo_viatico);
            ps.execute();
            return true;            
        } catch (SQLException e) {
            System.err.println(e); 
            return false;
        }
    }
    
    public boolean deleteCostosViaticosCargo(String id_cargo){
        String sql = "DELETE FROM viaticos_cargo WHERE id_cargo = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_cargo);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e); 
            return false;
        }
    }
    
    
    
}
