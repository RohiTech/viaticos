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

/**
 *
 * @author AUXINVTIC
 */
public class ModelMunicipio extends Conexion {
    Connection con = getConexion();
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    
    public boolean insertMunicipio(Municipio municipio){
        String sql = "INSERT INTO municipios(codigo, nombre, valor_hospedaje, valor_alimentacion) VALUES(?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, municipio.getCodigo());
            ps.setString(2, municipio.getMunicipio());
            ps.setInt(3, municipio.getValor_hospedaje());
            ps.setInt(4, municipio.getValor_alimentacion());
                        
            ps.execute();
            return true;
            
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public ArrayList<Municipio> listMunicipios(){
        ArrayList listaMunicipios = new ArrayList();
        Municipio municipio;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT codigo, nombre, valor_hospedaje, valor_alimentacion FROM municipios ORDER BY codigo";
        
        try {           
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                municipio = new Municipio();
                municipio.setCodigo(rs.getString(1));
                municipio.setMunicipio(rs.getString(2));
                municipio.setValor_hospedaje(rs.getInt(3));
                municipio.setValor_alimentacion(rs.getInt(4));                
                listaMunicipios.add(municipio);
            }           
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return listaMunicipios;        
    }
    
    public boolean updateMunicipio(Municipio municipio){
        String sql = "UPDATE municipios SET nombre=?, valor_hospedaje=?, valor_alimentacion=? WHERE codigo=?";
        try {
            ps = con.prepareStatement(sql);
            
            ps.setString(1, municipio.getMunicipio());
            ps.setInt(2, municipio.getValor_hospedaje());
            ps.setInt(3, municipio.getValor_alimentacion());            
            ps.setString(4, municipio.getCodigo());
            
            ps.execute();
            return true;
        }  catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public ArrayList<Peaje> listPeajes(){
        ArrayList listaPeajes = new ArrayList();
        Peaje peaje;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT id_costo_viatico, nombre, valor FROM costo_viaticos WHERE tipo_viatico = 2 ORDER BY nombre";
        
        try {           
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                peaje = new Peaje();
                peaje.setId_peaje(rs.getString(1));
                peaje.setDescripcion(rs.getString(2));
                peaje.setValor(rs.getInt(3));              
                listaPeajes.add(peaje);
            }           
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return listaPeajes;        
    }
    
    public ArrayList<Peaje> listOtrosViaticos(){
        ArrayList lista = new ArrayList();
        Peaje peaje;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT id_costo_viatico, nombre, valor FROM costo_viaticos "
                + "WHERE tipo_viatico = 5 OR tipo_viatico = 7 OR tipo_viatico = 6 ORDER BY nombre";
        
        try {           
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                peaje = new Peaje();
                peaje.setId_peaje(rs.getString(1));
                peaje.setDescripcion(rs.getString(2));
                peaje.setValor(rs.getInt(3));              
                lista.add(peaje);
            }           
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return lista;        
    }
    
    public ArrayList<Parqueadero> listParqueaderos(){
        ArrayList listaParqueadero = new ArrayList();
        Parqueadero parqueadero;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT id_costo_viatico, nombre, valor FROM costo_viaticos WHERE tipo_viatico = 1 ORDER BY nombre";
        
        try {           
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                parqueadero = new Parqueadero();
                parqueadero.setId_parqueadero(rs.getString(1));
                parqueadero.setDescripcion(rs.getString(2));
                parqueadero.setValor(rs.getInt(3));              
                listaParqueadero.add(parqueadero);
            }           
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return listaParqueadero;        
    }
    
    public ArrayList<Gasolina> listGasolina(){
        ArrayList listaGasolina = new ArrayList();
        Gasolina gasolina;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT id_costo_viatico, nombre, valor FROM costo_viaticos WHERE tipo_viatico = 3 ORDER BY nombre";
        
        try {           
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                gasolina = new Gasolina();
                gasolina.setId_gasolina(rs.getString(1));
                gasolina.setDescripcion(rs.getString(2));
                gasolina.setValor(rs.getInt(3));              
                listaGasolina.add(gasolina);
            }           
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return listaGasolina;        
    }
    
    public boolean updatePeaje(Peaje peaje){
        String sql = "UPDATE costo_viaticos SET nombre=?, valor=? WHERE id_costo_viatico=?";
        try {
            ps = con.prepareStatement(sql);
            
            ps.setString(1, peaje.getDescripcion());
            ps.setInt(2, peaje.getValor());
            ps.setString(3, peaje.getId_peaje());            
                       
            ps.execute();
            return true;
        }  catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public boolean updateParqueadero(Parqueadero parqueadero){
        String sql = "UPDATE costo_viaticos SET nombre=?, valor=? WHERE id_costo_viatico=?";
        try {
            ps = con.prepareStatement(sql);
            
            ps.setString(1, parqueadero.getDescripcion());
            ps.setInt(2, parqueadero.getValor());
            ps.setString(3, parqueadero.getId_parqueadero());            
                       
            ps.execute();
            return true;
        }  catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public boolean updateGasolina(Gasolina gasolina){
        String sql = "UPDATE costo_viaticos SET nombre=?, valor=? WHERE id_costo_viatico=?";
        try {
            ps = con.prepareStatement(sql);            
            ps.setString(1, gasolina.getDescripcion());
            ps.setInt(2, gasolina.getValor());
            ps.setString(3, gasolina.getId_gasolina());            
                       
            ps.execute();
            return true;
        }  catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public boolean insertNuevoCostoViatico(Peaje p){
        String sql = "INSERT INTO costo_viaticos(nombre, valor, tipo_viatico) VALUES(?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getDescripcion());
            ps.setInt(2, p.getValor());
            ps.setString(3, p.getId_peaje());
                        
            ps.execute();
            return true;
            
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
}
