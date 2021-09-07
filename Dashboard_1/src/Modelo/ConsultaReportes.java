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
public class ConsultaReportes extends Conexion {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection con = getConexion();
    
    public Vector<Dependencia> listaDependencias(){
        Vector<Dependencia> datos = new Vector<Dependencia>();
        Dependencia dependencia;
        String sql = "SELECT id_dependencia, nombre FROM dependencias ORDER BY nombre";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            dependencia = new Dependencia();
            dependencia.setId_dependencia("0");
            dependencia.setDependencia("Seleccione");
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
    
    public Vector<Empleado> listaEmpleados(){
        Vector<Empleado> datos = new Vector<Empleado>();
        Empleado empleado;
        String sql = "SELECT identificacion, codigo, apellido_uno, apellido_dos, nombre_uno, nombre_dos "
                + "FROM empleados ORDER BY apellido_uno, apellido_dos";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            empleado = new Empleado();
            empleado.setId("0");
            empleado.setNombre_uno("Seleccione");
            datos.add(empleado);
            
            while(rs.next()){
                empleado = new Empleado();
                empleado.setId(rs.getString(1));
                empleado.setCodigo(rs.getString(2));
                empleado.setNombre_uno(rs.getString(3).toUpperCase()+" "+rs.getString(4).toUpperCase()+" "+rs.getString(5).toUpperCase()+" "+rs.getString(6).toUpperCase());
                datos.add(empleado);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        return datos;
    }
    
    public ArrayList<Viatico> reporteGeneral(String fechainicial, String fechafinal){
        ArrayList<Viatico> lista = new ArrayList<>();
        Viatico viatico;
        String sql = "SELECT V.id_viatico, E.apellido_uno, E.apellido_dos, E.nombre_uno, E.nombre_dos, M.nombre, "
                + "V.fechaSalida, V.fechaRegreso, V.fecha_entrega, U.nombre, V.observaciones, V.entregado_por, "
                + "(SELECT SUM(valor * pivot) FROM detalle_viatico WHERE id_viatico = V.id_viatico) AS 'valor' , "
                + "(SELECT valor FROM devoluciones WHERE id_viatico = V.id_viatico) AS 'devolucion' "
                + "FROM viaticos V INNER JOIN empleados E ON V.id_empleado = E.identificacion "
                + "INNER JOIN usuarios U ON V.id_usuario = U.id_usuario "
                + "INNER JOIN municipios M ON V.id_municipio = M.codigo "
                + "WHERE V.fecha_entrega >= ? AND V.fecha_entrega <= ?  AND (V.estado = 2 OR V.estado = 4) "
                + "ORDER BY V.fecha_entrega, E.apellido_uno, E.apellido_dos";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fechainicial);
            ps.setString(2, fechafinal);
            rs = ps.executeQuery();
            while(rs.next()){
                viatico = new Viatico();
                viatico.setId_viatico(rs.getInt(1));
                viatico.setTecnico(rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5));
                viatico.setDestino(rs.getString(6));
                viatico.setFechaSalida(rs.getString(7));
                viatico.setFechaRegreso(rs.getString(8));
                viatico.setFechaProgramada(rs.getString(9));
                viatico.setUsuario(rs.getString(10));
                viatico.setObservaciones(rs.getString(11));
                viatico.setEntregadoPor(rs.getString(12));
                viatico.setValor(rs.getInt(13));
                viatico.setValor_devolucion(rs.getInt(14));
                lista.add(viatico);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        return lista;        
    }
    
    public ArrayList<Viatico> reporteGeneralTotal(String fechainicial, String fechafinal){
        ArrayList<Viatico> lista = new ArrayList<>();
        Viatico viatico;
        String sql = "SELECT V.id_viatico, E.apellido_uno, E.apellido_dos, E.nombre_uno, E.nombre_dos, M.nombre, "
                + "V.fechaSalida, V.fechaRegreso, V.fecha_entrega, U.nombre, V.observaciones, V.entregado_por, "
                + "(SELECT SUM(valor * pivot) FROM detalle_viatico WHERE id_viatico = V.id_viatico) AS 'valor' , "
                + "(SELECT valor FROM devoluciones WHERE id_viatico = V.id_viatico) AS 'devolucion' "
                + "FROM viaticos V INNER JOIN empleados E ON V.id_empleado = E.identificacion "
                + "INNER JOIN usuarios U ON V.id_usuario = U.id_usuario "
                + "INNER JOIN municipios M ON V.id_municipio = M.codigo "
                + "WHERE V.fecha_entrega >= ? AND V.fecha_entrega <= ?  AND (V.estado = 2 OR V.estado = 4 OR V.estado = 6) "
                + "ORDER BY V.fecha_entrega, E.apellido_uno, E.apellido_dos";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fechainicial);
            ps.setString(2, fechafinal);
            rs = ps.executeQuery();
            while(rs.next()){
                viatico = new Viatico();
                viatico.setId_viatico(rs.getInt(1));
                viatico.setTecnico(rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5));
                viatico.setDestino(rs.getString(6));
                viatico.setFechaSalida(rs.getString(7));
                viatico.setFechaRegreso(rs.getString(8));
                viatico.setFechaProgramada(rs.getString(9));
                viatico.setUsuario(rs.getString(10));
                viatico.setObservaciones(rs.getString(11));
                viatico.setEntregadoPor(rs.getString(12));
                viatico.setValor(rs.getInt(13));
                viatico.setValor_devolucion(rs.getInt(14));
                lista.add(viatico);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        return lista;        
    }
    
    public ArrayList<Viatico> reporteEmpleado(String id_empleado, String fechainicial, String fechafinal){
        ArrayList<Viatico> lista = new ArrayList<>();
        Viatico viatico;
        String sql = "SELECT V.id_viatico,  M.nombre, V.fechaSalida, V.fechaRegreso, V.fecha_entrega, U.nombre, V.observaciones, \n" +
                    "(SELECT SUM(valor * pivot) FROM detalle_viatico WHERE id_viatico = V.id_viatico AND id_tipo_viatico = 6) AS 'hospedaje',\n" +
                    "(SELECT SUM(valor * pivot) FROM detalle_viatico WHERE id_viatico = V.id_viatico AND id_tipo_viatico = 5) AS 'alimentacion',\n" +
                    "(SELECT SUM(valor * pivot) FROM detalle_viatico WHERE id_viatico = V.id_viatico AND id_tipo_viatico = 4) AS 'otros',\n" +
                    "(SELECT SUM(valor * pivot) FROM detalle_viatico WHERE id_viatico = V.id_viatico AND id_tipo_viatico = 3) AS 'transporte',\n" +
                    "(SELECT SUM(valor * pivot) FROM detalle_viatico WHERE id_viatico = V.id_viatico AND id_tipo_viatico = 2) AS 'peaje',\n" +
                    "(SELECT SUM(valor * pivot) FROM detalle_viatico WHERE id_viatico = V.id_viatico AND id_tipo_viatico = 1) AS 'parqueadero',\n" +
                    "(SELECT valor FROM devoluciones WHERE id_viatico = V.id_viatico) AS 'devolucion',\n" +
                    "(SELECT SUM(valor * pivot) FROM detalle_viatico WHERE id_viatico = V.id_viatico AND id_tipo_viatico = 7) AS 'al_cena',\n" +
                    "(SELECT detalle FROM detalle_viatico WHERE id_viatico = V.id_viatico AND id_tipo_viatico = 4 LIMIT 1) AS 'justificacion'\n" +
                    "FROM viaticos V \n" +
                    "INNER JOIN usuarios U ON V.id_usuario = U.id_usuario \n" +
                    "INNER JOIN municipios M ON V.id_municipio = M.codigo \n" +
                    "WHERE V.id_empleado = ? AND V.fecha_entrega >= ? AND V.fecha_entrega <= ? "+
                    "AND (V.estado = 2 OR V.estado = 4 OR V.estado = 6) \n" +
                    "ORDER BY V.fecha_entrega";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_empleado);
            ps.setString(2, fechainicial);
            ps.setString(3, fechafinal);
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
                viatico.setValor_hospedaje(rs.getInt(8));
                viatico.setValor_alimentacion(rs.getInt(9));
                viatico.setValor(rs.getInt(10));
                viatico.setTransporte(rs.getInt(11));
                viatico.setPeaje(rs.getInt(12));
                viatico.setParqueadero(rs.getInt(13));
                viatico.setValor_devolucion(rs.getInt(14));
                viatico.setValor_cena(rs.getInt(15));
                viatico.setJust_autorizacion(rs.getString(16));
                lista.add(viatico);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        return lista;        
    }
    
    public String entregadopor(String user){
        String usuario = null;
        String sql = "SELECT nombre FROM usuarios WHERE id_usuario=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user);
            rs = ps.executeQuery();
            if(rs.next()){
                usuario = rs.getString(1);                
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return usuario;
    }
    
}
