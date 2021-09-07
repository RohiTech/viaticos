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
public class ConsultaViaticos extends Conexion{
    Connection con = getConexion();
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public ArrayList<Viatico> listarViaticos(){
        ArrayList listaViaticos = new ArrayList();
        Viatico viatico;
        String sql = "SELECT V.id_viatico, V.id_empleado, E.nombre_uno, E.nombre_dos, E.apellido_uno, E.apellido_dos, "
                + "M.nombre, V.fechaSalida, V.fechaRegreso,V.fecha_programado, U.nombre, V.observaciones, "
                + "(SELECT SUM(valor * pivot) FROM detalle_viatico WHERE id_viatico = V.id_viatico) AS 'valor', V.estado, E.id_municipio "
                + "FROM viaticos V INNER JOIN empleados E ON V.id_empleado = E.identificacion "
                + "INNER JOIN usuarios U ON V.id_usuario = U.id_usuario "
                + "INNER JOIN municipios M ON V.id_municipio = M.codigo "
                + "WHERE V.estado = 1 OR V.estado = 4 ORDER BY V.fecha_programado DESC, E.nombre_uno, E.nombre_dos ";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                viatico = new Viatico();
                viatico.setId_viatico(rs.getInt(1));
                viatico.setId_tecnico(rs.getString(2));
                viatico.setTecnico(rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6));
                viatico.setDestino(rs.getString(7));
                viatico.setFechaSalida(rs.getString(8));
                viatico.setFechaRegreso(rs.getString(9));
                viatico.setFechaProgramada(rs.getString(10));
                viatico.setUsuario(rs.getString(11));
                viatico.setObservaciones(rs.getString(12)); 
                viatico.setValor(rs.getInt(13));
                viatico.setEstado(rs.getString(14));
                viatico.setId_destino(rs.getString(15));
                listaViaticos.add(viatico);                
            }    
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }        
        return listaViaticos;
    }
    
    public ArrayList<Viatico> listarViaticosMunicipios(String fecha, String id_empleado){
        ArrayList listaViaticos = new ArrayList();
        Viatico viatico;
        String sql = "SELECT V.id_viatico, V.id_empleado, E.nombre_uno, E.nombre_dos, E.apellido_uno, E.apellido_dos, "
                + "M.nombre, V.fechaSalida, V.fechaRegreso,V.fecha_programado, U.nombre, V.observaciones, "
                + "(SELECT SUM(valor * pivot) FROM detalle_viatico WHERE id_viatico = V.id_viatico) AS 'valor', V.estado, E.id_municipio "
                + "FROM viaticos V INNER JOIN empleados E ON V.id_empleado = E.identificacion "
                + "INNER JOIN usuarios U ON V.id_usuario = U.id_usuario "
                + "INNER JOIN municipios M ON V.id_municipio = M.codigo "
                + "WHERE V.id_empleado = ? AND V.fechaSalida >= ? AND V.estado = 5 "
                + "ORDER BY V.fechaSalida, E.nombre_uno, E.nombre_dos ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_empleado);
            ps.setString(2, fecha);
            rs = ps.executeQuery();
            while(rs.next()){
                viatico = new Viatico();
                viatico.setId_viatico(rs.getInt(1));
                viatico.setId_tecnico(rs.getString(2));
                viatico.setTecnico(rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6));
                viatico.setDestino(rs.getString(7));
                viatico.setFechaSalida(rs.getString(8));
                viatico.setFechaRegreso(rs.getString(9));
                viatico.setFechaProgramada(rs.getString(10));
                viatico.setUsuario(rs.getString(11));
                viatico.setObservaciones(rs.getString(12)); 
                viatico.setValor(rs.getInt(13));
                viatico.setEstado(rs.getString(14));
                viatico.setId_destino(rs.getString(15));
                listaViaticos.add(viatico);                
            }    
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }        
        return listaViaticos;
    }
    
    public boolean updateViatico(int id, int estado, String usuario){
        Date fecha = new Date();            
        String strDateFormat = "yyyy-MM-dd"; // El formato de fecha está especificado  
        SimpleDateFormat f = new SimpleDateFormat(strDateFormat);
        String feActual = f.format(fecha);

        Date hora = new Date();
        String strHoraFormat = "hh:mm:ss"; // El formato de fecha está especificado  
        SimpleDateFormat h = new SimpleDateFormat(strHoraFormat);
        String hoActual = h.format(hora);
        
        String sql = "UPDATE viaticos SET fecha_entrega=?, hora_entrega=?, estado=?, entregado_por=? WHERE id_viatico=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, feActual);
            ps.setString(2, hoActual);
            ps.setInt(3, estado);
            ps.setString(4, usuario);
            ps.setInt(5, id);
            
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }        
    }
    public boolean updateViaticoMunicipio(int id, int estado, String usuario, String fecha){
        
        Date hora = new Date();
        String strHoraFormat = "hh:mm:ss"; // El formato de fecha está especificado  
        SimpleDateFormat h = new SimpleDateFormat(strHoraFormat);
        String hoActual = h.format(hora);
        
        String sql = "UPDATE viaticos SET fecha_entrega=?, hora_entrega=?, estado=?, entregado_por=? WHERE id_viatico=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            ps.setString(2, hoActual);
            ps.setInt(3, estado);
            ps.setString(4, usuario);
            ps.setInt(5, id);
            
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }        
    }
    
    public boolean updateViaticoEntregado(int id, int estado){
        String sql = "UPDATE viaticos SET estado=? WHERE id_viatico=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, estado);
            ps.setInt(2, id);            
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }        
    }
    
    
    public ArrayList<Viatico> viaticosEntregados(String fechaInicial, String fechaFinal){
        ArrayList listaViaticos = new ArrayList();
        Viatico viatico;
        String sql = "SELECT T.nombre_uno, T.nombre_dos, T.apellido_uno, T.apellido_dos, M.nombre, V.observaciones, "
                + "V.fechaSalida, V.fechaRegreso, V.fecha_entrega, V.hora_entrega, V.valor, V.parqueadero, V.peajes, "
                + "V.hospedaje, V.alimentacion, V.gasolina, V.id_viatico, "
                + "(SELECT valor FROM devoluciones WHERE id_viatico = V.id_viatico LIMIT 1) AS 'valor_devolucion', "
                + "(SELECT observacion FROM devoluciones WHERE id_viatico = V.id_viatico LIMIT 1) AS 'obs_devolucion', "
                + "(SELECT fecha FROM devoluciones WHERE id_viatico = V.id_viatico LIMIT 1) AS 'fecha_devolucion' "
                + "FROM viaticos V INNER JOIN tecnicos T ON V.id_tecnico = T.identificacion "
                + "INNER JOIN municipios M ON V.id_municipio = M.codigo "
                + "WHERE V.fecha_entrega >= ? AND V.fecha_entrega <= ? AND V.estado = 2 "
                + "ORDER BY V.fecha_entrega ASC, T.apellido_uno, T.apellido_dos ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fechaInicial);
            ps.setString(2, fechaFinal);
            rs = ps.executeQuery();
            
            while(rs.next()){
                viatico = new Viatico();
                viatico.setTecnico(rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(1)+" "+rs.getString(2));
                viatico.setDestino(rs.getString(5));
                viatico.setObservaciones(rs.getString(6));
                viatico.setFechaSalida(rs.getString(7));
                viatico.setFechaRegreso(rs.getString(8));
                viatico.setFechaProgramada(rs.getString(9));
                viatico.setValor(rs.getInt(11));
                viatico.setParqueadero(rs.getInt(12));
                viatico.setPeaje(rs.getInt(13));
                viatico.setValor_hospedaje(rs.getInt(14));
                viatico.setValor_alimentacion(rs.getInt(15));
                viatico.setTransporte(rs.getInt(16));
                viatico.setId_viatico(rs.getInt(17));
                viatico.setValor_devolucion(rs.getInt(18));
                viatico.setObs_devolucion(rs.getString(19));
                viatico.setFecha_devolucion(rs.getString(20));                
                listaViaticos.add(viatico);                
            }               
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }        
        return listaViaticos;        
    }
    
    public ArrayList<Viatico> viaticosEntregadosTecnicos(String id_tecnico, String fechaInicial, String fechaFinal){
        ArrayList listaViaticos = new ArrayList();
        Viatico viatico;
        
        String sql = "SELECT T.nombre_uno, T.nombre_dos, T.apellido_uno, T.apellido_dos, M.nombre, V.observaciones, "
                + "V.fechaSalida, V.fechaRegreso, V.fecha_entrega, V.hora_entrega, V.valor, V.parqueadero, V.peajes, "
                + "V.hospedaje, V.alimentacion, V.gasolina, V.id_viatico, "
                + "(SELECT valor FROM devoluciones WHERE id_viatico = V.id_viatico LIMIT 1) AS 'valor_devolucion', "
                + "(SELECT observacion FROM devoluciones WHERE id_viatico = V.id_viatico LIMIT 1) AS 'obs_devolucion', "
                + "(SELECT fecha FROM devoluciones WHERE id_viatico = V.id_viatico LIMIT 1) AS 'fecha_devolucion' "
                + "FROM viaticos V INNER JOIN tecnicos T ON V.id_tecnico = T.identificacion "
                + "INNER JOIN municipios M ON V.id_municipio = M.codigo "
                + "WHERE V.id_tecnico = ? AND V.fecha_entrega >= ? AND V.fecha_entrega <= ? AND V.estado = 2 "
                + "ORDER BY V.fecha_entrega ASC, T.apellido_uno, T.apellido_dos ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_tecnico);
            ps.setString(2, fechaInicial);
            ps.setString(3, fechaFinal);
            rs = ps.executeQuery();
            
            while(rs.next()){
                viatico = new Viatico();
                viatico.setTecnico(rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(1)+" "+rs.getString(2));
                viatico.setDestino(rs.getString(5));
                viatico.setObservaciones(rs.getString(6));
                viatico.setFechaSalida(rs.getString(7));
                viatico.setFechaRegreso(rs.getString(8));
                viatico.setFechaProgramada(rs.getString(9));
                viatico.setValor(rs.getInt(11));
                viatico.setParqueadero(rs.getInt(12));
                viatico.setPeaje(rs.getInt(13));
                viatico.setValor_hospedaje(rs.getInt(14));
                viatico.setValor_alimentacion(rs.getInt(15));
                viatico.setTransporte(rs.getInt(16));
                viatico.setId_viatico(rs.getInt(17));
                viatico.setValor_devolucion(rs.getInt(18));
                viatico.setObs_devolucion(rs.getString(19));
                viatico.setFecha_devolucion(rs.getString(20));                
                listaViaticos.add(viatico);                
            }               
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }        
        return listaViaticos;        
    }
    
    public Vector<Empleado> listarTecnicos(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vector<Empleado> datos = new Vector<Empleado>();
        Empleado tec = null;
        
        try {  
            String sql = "SELECT identificacion, nombre_uno, nombre_dos, apellido_uno, apellido_dos "
                    + "FROM tecnicos WHERE estado = 1 ORDER BY apellido_uno, apellido_dos";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();            
            tec = new Empleado();
            tec.setId("0");
            tec.setNombre_uno("Todos");
            datos.add(tec);
            
            while(rs.next()){
                tec = new Empleado();
                tec.setId(rs.getString(1));
                tec.setNombre_uno(rs.getString(4).toUpperCase()+" "+rs.getString(5).toUpperCase()+" "+rs.getString(2).toUpperCase()+" "+rs.getString(3).toUpperCase());
                datos.add(tec);                
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
    
    public boolean insertDevolucion(Devolucion dev){
        String sql = "INSERT INTO devoluciones(valor, fecha, id_viatico, observacion) VALUES(?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, dev.getValor());
            ps.setString(2, dev.getFecha());
            ps.setString(3, dev.getId_viatico());
            ps.setString(4, dev.getObservacion());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    
    public Vector<Dependencia> listaDependencias() {
        Vector<Dependencia> datos = new Vector<Dependencia>();
        Dependencia dependencia = null;        
        
        try {       
            String sql = "SELECT id_dependencia, nombre FROM dependencias ORDER BY nombre";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            dependencia = new Dependencia();
            dependencia.setId_dependencia("0");
            dependencia.setDependencia("Seleccione una opción:");
            datos.add(dependencia);
            
            while(rs.next()){
                dependencia = new Dependencia();
                dependencia.setId_dependencia(rs.getString(1));
                dependencia.setDependencia(rs.getString(2).toUpperCase());
                datos.add(dependencia);
            }   
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return datos;
    }
    
    public ArrayList<Viatico> listarViaticosEntregados(String id_empleado, String fecha){
        ArrayList listaViaticos = new ArrayList();
        Viatico viatico;
        String sql = "SELECT V.id_viatico, M.nombre, V.fechaSalida, V.fechaRegreso, V.fecha_entrega, U.nombre, V.observaciones, "
                + "(SELECT SUM(valor * pivot) FROM detalle_viatico WHERE id_viatico = V.id_viatico) AS 'valor' , "
                + "(SELECT valor FROM devoluciones WHERE id_viatico = V.id_viatico) AS 'devolucion' "
                + "FROM viaticos V INNER JOIN usuarios U ON V.id_usuario = U.id_usuario "
                + "INNER JOIN municipios M ON V.id_municipio = M.codigo "
                + "WHERE V.id_empleado = ? AND (V.estado = ? OR V.estado = ?) AND V.fecha_entrega >= ? ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_empleado);
            ps.setString(2, "2");
            ps.setString(3, "4");
            ps.setString(4, fecha);
            rs = ps.executeQuery();
            while(rs.next()){
                viatico = new Viatico();
                viatico.setId_viatico(rs.getInt(1));
                viatico.setDestino(rs.getString(2));
                viatico.setFechaSalida(rs.getString(3));
                viatico.setFechaRegreso(rs.getString(4));
                viatico.setFechaProgramada(rs.getString(5));
                viatico.setUsuario(rs.getString(6));
                viatico.setObservaciones(rs.getString(7)); 
                viatico.setValor(rs.getInt(8));
                viatico.setValor_devolucion(rs.getInt(9));
                listaViaticos.add(viatico);                
            }    
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }        
        return listaViaticos;
    }
    
    public String verificarDevolucionRegistrada(String id_viatico){
        String sql = "SELECT valor FROM devoluciones WHERE id_viatico = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_viatico);
            rs = ps.executeQuery();
            if(rs.next()){
                String valor = rs.getString(1);
                return valor;
            }else{
                return null;
            }            
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }
    
    
    public ArrayList<Detalle_viatico> viaticosAlimentacion(String id_viatico){
        ArrayList<Detalle_viatico> lista = new ArrayList<Detalle_viatico>();
        Detalle_viatico al;        
        String sql = "SELECT id_detalle_viatico, detalle, valor, pivot FROM detalle_viatico WHERE id_viatico = ? AND id_tipo_viatico = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_viatico);
            ps.setString(2, "5");
            rs = ps.executeQuery();
            if(rs.next()){
                al = new Detalle_viatico();
                al.setId_detalle_viatico(rs.getString(1));
                al.setDetalle(rs.getString(2));
                al.setValor(rs.getString(3));
                al.setPivot(rs.getInt(4));
                lista.add(al);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        return lista;
    }

    public ArrayList<Detalle_viatico> viaticosHospedaje(String id_viatico){
        ArrayList<Detalle_viatico> lista = new ArrayList<Detalle_viatico>();
        Detalle_viatico al;        
        String sql = "SELECT id_detalle_viatico, detalle, valor, pivot FROM detalle_viatico WHERE id_viatico = ? AND id_tipo_viatico = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_viatico);
            ps.setString(2, "6");
            rs = ps.executeQuery();
            if(rs.next()){
                al = new Detalle_viatico();
                al.setId_detalle_viatico(rs.getString(1));
                al.setDetalle(rs.getString(2));
                al.setValor(rs.getString(3));
                al.setPivot(rs.getInt(4));
                lista.add(al);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        return lista;
    }
    
    public ArrayList<Detalle_viatico> viaticosParqueaderos(String id_viatico){
        ArrayList<Detalle_viatico> lista = new ArrayList<Detalle_viatico>();
        Detalle_viatico al;        
        String sql = "SELECT id_detalle_viatico, detalle, valor, pivot FROM detalle_viatico WHERE id_viatico = ? AND id_tipo_viatico = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_viatico);
            ps.setString(2, "1");
            rs = ps.executeQuery();
            if(rs.next()){
                al = new Detalle_viatico();
                al.setId_detalle_viatico(rs.getString(1));
                al.setDetalle(rs.getString(2));
                al.setValor(rs.getString(3));
                al.setPivot(rs.getInt(4));
                lista.add(al);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        return lista;
    }
    
    public ArrayList<Detalle_viatico> viaticosPeajes(String id_viatico){
        ArrayList<Detalle_viatico> lista = new ArrayList<Detalle_viatico>();
        Detalle_viatico al;        
        String sql = "SELECT id_detalle_viatico, detalle, valor, pivot FROM detalle_viatico WHERE id_viatico = ? AND id_tipo_viatico = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_viatico);
            ps.setString(2, "2");
            rs = ps.executeQuery();
            while(rs.next()){
                al = new Detalle_viatico();
                al.setId_detalle_viatico(rs.getString(1));
                al.setDetalle(rs.getString(2));
                al.setValor(rs.getString(3));
                al.setPivot(rs.getInt(4));
                lista.add(al);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        return lista;
    }
    
    public ArrayList<Detalle_viatico> viaticosTransporte(String id_viatico){
        ArrayList<Detalle_viatico> lista = new ArrayList<Detalle_viatico>();
        Detalle_viatico al;        
        String sql = "SELECT id_detalle_viatico, detalle, valor, pivot FROM detalle_viatico WHERE id_viatico = ? AND id_tipo_viatico = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_viatico);
            ps.setString(2, "3");
            rs = ps.executeQuery();
            while(rs.next()){
                al = new Detalle_viatico();
                al.setId_detalle_viatico(rs.getString(1));
                al.setDetalle(rs.getString(2));
                al.setValor(rs.getString(3));
                al.setPivot(rs.getInt(4));
                lista.add(al);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        return lista;
    }
    
    public ArrayList<Detalle_viatico> viaticosOtros(String id_viatico){
        ArrayList<Detalle_viatico> lista = new ArrayList<Detalle_viatico>();
        Detalle_viatico al;        
        String sql = "SELECT id_detalle_viatico, detalle, valor, pivot FROM detalle_viatico WHERE id_viatico = ? AND id_tipo_viatico = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_viatico);
            ps.setString(2, "4");
            rs = ps.executeQuery();
            while(rs.next()){
                al = new Detalle_viatico();
                al.setId_detalle_viatico(rs.getString(1));
                al.setDetalle(rs.getString(2));
                al.setValor(rs.getString(3));
                al.setPivot(rs.getInt(4));
                lista.add(al);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        return lista;
    }
    
    public ArrayList<Viatico> viaticosAHP(String id_viatico){
        ArrayList<Viatico> lista = new ArrayList<Viatico>();
        Viatico v;        
        String sql = "SELECT DISTINCT "
                + "(SELECT valor FROM detalle_viatico WHERE id_tipo_viatico = 5 and id_viatico = ?) AS 'alimentacion', "
                + "(SELECT valor FROM detalle_viatico WHERE id_tipo_viatico = 6 and id_viatico = ?) AS 'hospedaje', "
                + "(SELECT valor FROM detalle_viatico WHERE id_tipo_viatico = 1 and id_viatico = ?) AS 'parqueadero', "
                +"(SELECT valor FROM detalle_viatico WHERE id_tipo_viatico = 7 and id_viatico = ?) AS 'ali_noche' "
                + "FROM detalle_viatico";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_viatico);
            ps.setString(2, id_viatico);
            ps.setString(3, id_viatico);
            ps.setString(4, id_viatico);
            rs = ps.executeQuery();
            while(rs.next()){
                v = new Viatico();                
                v.setValor_alimentacion(rs.getInt(1));
                v.setValor_hospedaje(rs.getInt(2));
                v.setParqueadero(rs.getInt(3));
                v.setValor_cena(rs.getInt(4));
                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        return lista;
    }
    
    public int diasViatico(String id_viatico){
        String sql = "SELECT MAX(pivot) FROM detalle_viatico WHERE id_viatico = ?";
        int c = 0;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_viatico);
            rs = ps.executeQuery();
            if(rs.next()){
                c = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return 0;
        }
        return c;
    }
    
    public boolean updateAutorizacion(String empleado, String estado){
        String sql = "UPDATE autorizaciones SET estado=? WHERE id_empleado=? AND estado=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setString(2, empleado);
            ps.setString(3, "2");
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public int obtenerPivot(String id_viatico, String tipo){
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT  pivot FROM detalle_viatico WHERE id_viatico = ? AND id_tipo_viatico = ? LIMIT 1";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_viatico);
            ps.setString(2, tipo);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }else{
                return 0;
            }
        } catch (SQLException e) {
            return 0;
        }
    }
    
    public ArrayList<Detalle_viatico> getDetalleViatico(String id_viatico){
        ArrayList<Detalle_viatico> lista = new ArrayList<Detalle_viatico>();
        Detalle_viatico v; 
        String sql = "SELECT detalle, (valor * pivot) AS 'valor_detalle' FROM detalle_viatico WHERE id_viatico = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_viatico);
            rs = ps.executeQuery();
            while(rs.next()){
                v = new Detalle_viatico();                
                v.setDetalle(rs.getString(1));
                v.setValor(rs.getString(2));
                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        return lista;
    }
}
