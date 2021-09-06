
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
public class ConsultaEmpleados extends Conexion {
    Connection con = getConexion();
    
    public ArrayList<Empleado> listTecnicos(){
        ArrayList listaEmpleados = new ArrayList();
        Empleado empleado;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT T.identificacion, T.codigo, T.nombre_uno, T.nombre_dos, T.apellido_uno, T.apellido_dos, "
                + "T.telefono_corporativo, T.telefono_personal, T.direccion, T.correo, T.fecha_registro, "
                + "M.nombre AS 'municipio', T.foto, E.nombre AS 'estado', T.huella, C.nombre_cargo AS 'cargo' " 
                + "FROM empleados T INNER JOIN municipios M ON T.id_municipio = M.codigo "
                + "INNER JOIN estado_empleado E ON T.estado = E.id_estado " 
                + "INNER JOIN cargos C ON T.id_cargo = C.id_cargo " 
                + "ORDER BY apellido_uno";
        
        try {           
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                empleado = new Empleado();
                empleado.setId(rs.getString(1));
                empleado.setCodigo(rs.getString(2));
                empleado.setNombre_uno(rs.getString(3));
                empleado.setNombre_dos(rs.getString(4));
                empleado.setApellido_uno(rs.getString(5));
                empleado.setApellido_dos(rs.getString(6));
                empleado.setTelefono_corporativo(rs.getString(7));
                empleado.setTelefono_personal(rs.getString(8));
                empleado.setDireccion(rs.getString(9));
                empleado.setCorreo(rs.getString(10));
                empleado.setFecha_registro(rs.getString(11));
                empleado.setMunicipio(rs.getString(12));
                empleado.setFoto(rs.getBlob(13));
                empleado.setEstado(rs.getString(14));
                empleado.setHuella(rs.getBlob(15));
                empleado.setCargo(rs.getString(16));
                listaEmpleados.add(empleado);
            }           
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return listaEmpleados;
        
    }
     
    public boolean registroViaticos(Viatico viatico){
        PreparedStatement ps = null;
        String sql = "INSERT INTO viaticos(id_empleado, id_municipio, observaciones, fechaSalida, fechaRegreso, "
                + "fecha_programado, hora_programado, fecha_entrega, hora_entrega, estado, id_usuario, entregado_por) "               
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, viatico.getId_tecnico());
            ps.setString(2, viatico.getDestino());            
            ps.setString(3, viatico.getObservaciones());            
            ps.setString(4, viatico.getFechaSalida());
            ps.setString(5, viatico.getFechaRegreso());
            ps.setString(6, viatico.getFechaProgramada());
            ps.setString(7, viatico.getHoraProgramada());
            ps.setString(8, "1000-01-01");
            ps.setString(9, "00:00:00");
            ps.setString(10, viatico.getEstado());
            ps.setString(11, viatico.getUsuario()); 
            ps.setString(12, ""); 
            ps.execute();
            return true;
            
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public int ultimo_id_viatico(){
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
    }
    
    public boolean registroDetalleViatico(Detalle_viatico det){
        PreparedStatement ps = null;
        String sql = "INSERT INTO detalle_viatico(detalle, valor, pivot, id_viatico, id_tipo_viatico) VALUES(?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, det.getDetalle());
            ps.setString(2, det.getValor());
            ps.setInt(3, det.getPivot());
            ps.setInt(4, det.getId_viatico());
            ps.setString(5, det.getTipo_viatico());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public Vector<TipoDoc> listTipoDoc(){        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vector<TipoDoc> datos = new Vector<TipoDoc>();
        TipoDoc doc = null;
        
        try {  
            String sql = "SELECT id_tipo_doc, nombre_doc FROM tipo_documento";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            doc = new TipoDoc();
            doc.setIdTipoDoc("0");
            doc.setNombreDoc("Seleccione una opción:");
            datos.add(doc);
            
            while(rs.next()){
                doc = new TipoDoc();
                doc.setIdTipoDoc(rs.getString(1));
                doc.setNombreDoc(rs.getString(2));
                datos.add(doc);                
            }           
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return datos;
        
    }
    
    public Vector<TipoDoc> listTipoDocT(Empleado te){        
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        ResultSet rst = null;
        Vector<TipoDoc> datos = new Vector<TipoDoc>();
        TipoDoc doc = null;
        String c = null;
        try { 
            String sqlt = "SELECT T.id_tipo_doc, D.nombre_doc FROM empleados T "
                    + "INNER JOIN tipo_documento D ON T.id_tipo_doc= D.id_tipo_doc WHERE T.identificacion=?";
            pst = con.prepareStatement(sqlt);
            pst.setString(1, te.getId());
            rst = pst.executeQuery();
            
            String sql = "SELECT id_tipo_doc, nombre_doc FROM tipo_documento";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if(rst.next()){
                doc = new TipoDoc();
                doc.setIdTipoDoc(rst.getString(1));
                c = rst.getString(1);
                doc.setNombreDoc(rst.getString(2));
                datos.add(doc);
            }
            
            while(rs.next()){
                doc = new TipoDoc();
                if(!rs.getString(1).equals(c)){
                    doc.setIdTipoDoc(rs.getString(1));
                    doc.setNombreDoc(rs.getString(2));
                    datos.add(doc);
                }
                                
            }           
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return datos;
        
    }

    public Vector<Municipio> listaMunicipios() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Vector<Municipio> datos = new Vector<Municipio>();
        Municipio municipio = null;        
        
        try {       
            String sql = "SELECT codigo, nombre FROM municipios ORDER BY nombre";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            municipio = new Municipio();
            municipio.setCodigo("0");
            municipio.setMunicipio("Seleccione una opción:");
            datos.add(municipio);
            
            while(rs.next()){
                municipio = new Municipio();
                municipio.setCodigo(rs.getString(1));
                municipio.setMunicipio(rs.getString(2).toUpperCase());
                datos.add(municipio);
            }           
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return datos;
    }
    
    public Vector<Dependencia> listaDependenciasCargo(String cargo) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
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
            
            if(cargo.equals("1")){
                while(rs.next()){
                    dependencia = new Dependencia();
                    dependencia.setId_dependencia(rs.getString(1));
                    dependencia.setDependencia(rs.getString(2).toUpperCase());
                    datos.add(dependencia);                             
                }
            }
            if(cargo.equals("2")){
                while(rs.next()){
                    if(rs.getString(1).equals("2")){
                        dependencia = new Dependencia();
                        dependencia.setId_dependencia(rs.getString(1));
                        dependencia.setDependencia(rs.getString(2).toUpperCase());
                        datos.add(dependencia); 
                    }                                                
                }
            }
            if(cargo.equals("3")){
                while(rs.next()){
                    if(!rs.getString(1).equals("2")){
                        dependencia = new Dependencia();
                        dependencia.setId_dependencia(rs.getString(1));
                        dependencia.setDependencia(rs.getString(2).toUpperCase());
                        datos.add(dependencia); 
                    }                                                
                }
            }
                       
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return datos;
    }
    
    public Vector<Dependencia> listaDependencias() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
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
     
    
    public Vector<Empleado> listarCargoDep(String id_dependencia){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vector<Empleado> datos = new Vector<Empleado>();
        Empleado empleado;
        
        String sql = "SELECT E.identificacion, E.apellido_uno, E.apellido_dos, E.nombre_uno, E.nombre_dos, E.id_municipio, E.id_cargo "
                + "FROM empleados E INNER JOIN cargos C ON E.id_cargo = C.id_cargo "
                + "WHERE C.id_dependencia = ? AND E.estado = 1 ORDER BY E.apellido_uno, E.apellido_dos";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_dependencia);
            rs = ps.executeQuery();
            
            empleado = new Empleado();
            empleado.setId("0");
            empleado.setNombre_uno("Seleccione:");
            empleado.setId_municipio("");
            datos.add(empleado);
            
            while(rs.next()){
                empleado = new Empleado();
                empleado.setId(rs.getString(1));
                String nombre = rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5);
                empleado.setNombre_uno(nombre.toUpperCase());
                empleado.setId_municipio(rs.getString(6));
                empleado.setId_cargo(rs.getString(7));
                datos.add(empleado);
            }
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        
        return datos;
    }
        
    public Vector<Municipio> listaMunicipiosT(Empleado te) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        ResultSet rst = null;
        
        Vector<Municipio> datos = new Vector<Municipio>();
        Municipio municipio = null;        
        String c = null;
        try {     
            String sqlt = "SELECT T.id_municipio, M.nombre FROM empleados T "
                    + "INNER JOIN municipios M ON T.id_municipio= M.codigo WHERE T.identificacion=?";
            pst = con.prepareStatement(sqlt);
            pst.setString(1, te.getId());
            rst = pst.executeQuery();
            
            String sql = "SELECT codigo, nombre FROM municipios ORDER BY nombre";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if(rst.next()){
                municipio = new Municipio();
                municipio.setCodigo(rst.getString(1));
                c = rst.getString(1);
                municipio.setMunicipio(rst.getString(2).toUpperCase());
                datos.add(municipio);
            }
                        
            while(rs.next()){
                municipio = new Municipio();
                if(!rs.getString(1).equals(c)){
                    municipio.setCodigo(rs.getString(1));
                    municipio.setMunicipio(rs.getString(2).toUpperCase());
                    datos.add(municipio);
                }                
            }           
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return datos;
    }
    
    public Vector<EstadoEmpleado> listaEstadosT(Empleado te) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        ResultSet rst = null;
        
        Vector<EstadoEmpleado> datos = new Vector<EstadoEmpleado>();
        EstadoEmpleado estado = null;        
        String c = null;
        try {     
            String sqlt = "SELECT T.estado, E.nombre FROM empleados T "
                    + "INNER JOIN estado_empleado E ON T.estado= E.id_estado WHERE T.identificacion=?";
            pst = con.prepareStatement(sqlt);
            pst.setString(1, te.getId());
            rst = pst.executeQuery();
            
            String sql = "SELECT id_estado, nombre FROM estado_empleado ORDER BY nombre";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if(rst.next()){
                estado = new EstadoEmpleado();
                estado.setId_estado(rst.getString(1));
                c = rst.getString(1);
                estado.setNombre_estado(rst.getString(2).toUpperCase());
                datos.add(estado);
            }
                        
            while(rs.next()){
                estado = new EstadoEmpleado();
                if(!rs.getString(1).equals(c)){
                    estado.setId_estado(rs.getString(1));
                    estado.setNombre_estado(rs.getString(2).toUpperCase());
                    datos.add(estado);
                }                
            }           
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return datos;
    }
    
    public Vector<Cargo> listaCargos(String id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Vector<Cargo> datos = new Vector<Cargo>();
        Cargo cargo = null;        
        
        try {       
            String sql = "SELECT id_cargo, nombre_cargo FROM cargos WHERE id_dependencia = ? ORDER BY nombre_cargo";
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            cargo = new Cargo();
            cargo.setIdCargo("0");
            cargo.setNombreCargo("Seleccione una opción:");
            datos.add(cargo);
            
            while(rs.next()){
                cargo = new Cargo();
                cargo.setIdCargo(rs.getString(1));
                cargo.setNombreCargo(rs.getString(2).toUpperCase());
                datos.add(cargo);
            }           
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return datos;
    }
    
    public Vector<Cargo> listaCargosT(Empleado t) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        ResultSet rst = null;
        
        Vector<Cargo> datos = new Vector<Cargo>();
        Cargo cargo = null;        
        String c = null;
        try {   
            String sqlt = "SELECT T.id_cargo, D.nombre_cargo FROM empleados T "
                    + "INNER JOIN cargos D ON T.id_cargo= D.id_cargo WHERE T.identificacion=?";
            pst = con.prepareStatement(sqlt);
            pst.setString(1, t.getId());
            rst = pst.executeQuery();
            
            String sql = "SELECT id_cargo, nombre_cargo FROM cargos ORDER BY nombre_cargo";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rst.next()){
                cargo = new Cargo();
                cargo.setIdCargo(rst.getString(1));
                c = rst.getString(1);
                cargo.setNombreCargo(rst.getString(2).toUpperCase());
                datos.add(cargo);
            }           
            
            while(rs.next()){
                cargo = new Cargo();
                if(!rs.getString(1).equals(c)){
                    cargo.setIdCargo(rs.getString(1));
                    cargo.setNombreCargo(rs.getString(2).toUpperCase());
                    datos.add(cargo);
                }
            }           
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return datos;
    }
    
    public String codigoTecnico(String codigo){        
        PreparedStatement ps = null;
        ResultSet rs = null;
        String cod = null;
        try {
            String sql = "SELECT MAX(codigo) FROM empleados WHERE codigo LIKE ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, "%"+codigo+"%");
            rs = ps.executeQuery();
            if(rs.next()){
                cod = rs.getString(1);
            }
            return cod;
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }        
    }
    
    public boolean buscarTec(Empleado tecnico){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT T.identificacion, T.codigo, T.nombre_uno, T.nombre_dos, T.apellido_uno, "
                    + "T.apellido_dos, T.telefono_corporativo, T.telefono_personal, T.direccion, T.correo, T.id_municipio, "
                    + "M.nombre AS 'municipio', T.id_cargo, C.nombre_cargo, T.estado, E.nombre AS 'estado', T.foto "
                    + "FROM empleados T INNER JOIN municipios M ON T.id_municipio = M.codigo "
                    + "INNER JOIN cargos C ON T.id_cargo = C.id_cargo "
                    + "INNER JOIN estado_empleado E ON T.estado=E.id_estado  WHERE identificacion=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, tecnico.getId());
            rs = ps.executeQuery();
            if(rs.next()){
                
                tecnico.setId(rs.getString(1));
                tecnico.setCodigo(rs.getString(2));
                tecnico.setNombre_uno(rs.getString(3));
                tecnico.setNombre_dos(rs.getString(4));
                tecnico.setApellido_uno(rs.getString(5));
                tecnico.setApellido_dos(rs.getString(6));
                tecnico.setTelefono_corporativo(rs.getString(7));
                tecnico.setTelefono_personal(rs.getString(8));
                tecnico.setDireccion(rs.getString(9));
                tecnico.setCorreo(rs.getString(10));
                tecnico.setId_municipio(rs.getString(11));
                tecnico.setMunicipio(rs.getString(12));
                tecnico.setId_cargo(rs.getString(13));
                tecnico.setCargo(rs.getString(14));
                tecnico.setId_estado(rs.getString(15));
                tecnico.setEstado(rs.getString(16));
                tecnico.setFoto(rs.getBlob(17));
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
        
    }
    
    public boolean actualizarTecnico(Empleado tec){
        PreparedStatement ps = null;        
        String sql = "UPDATE empleados SET id_tipo_doc=?, nombre_uno=?, nombre_dos=?, apellido_uno=?, apellido_dos=?,"
                + " telefono_corporativo=?, telefono_personal=?, direccion=?, correo=?, id_municipio=?, id_cargo=?, estado=? "
                + "WHERE identificacion=?";
        try {
            ps = con.prepareStatement(sql);
            
            ps.setString(1, tec.getId_tipo_doc());
            ps.setString(2, tec.getNombre_uno());
            ps.setString(3, tec.getNombre_dos());            
            ps.setString(4, tec.getApellido_uno());
            ps.setString(5, tec.getApellido_dos());
            ps.setString(6, tec.getTelefono_corporativo());
            ps.setString(7, tec.getTelefono_personal());
            ps.setString(8, tec.getDireccion());
            ps.setString(9, tec.getCorreo());
            ps.setString(10, tec.getId_municipio());
            ps.setString(11, tec.getId_cargo());
            ps.setString(12, tec.getId_estado());
            ps.setString(13, tec.getId());
            ps.execute();
            return true;
        }  catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public boolean actualizarTecnicoFoto(Empleado tec, File file){
        FileInputStream fl = null;
        
        PreparedStatement ps = null;        
        String sql = "UPDATE empleados SET id_tipo_doc=?, nombre_uno=?, nombre_dos=?, apellido_uno=?, apellido_dos=?,"
                + " telefono_corporativo=?, telefono_personal=?, direccion=?, correo=?, "
                + "id_municipio=?, id_cargo=?, estado=?, foto=? "
                + "WHERE identificacion=?";
        int ban = 0;
        try {
            fl = new FileInputStream(file);
            try {
            
                ps = con.prepareStatement(sql);

                ps.setString(1, tec.getId_tipo_doc());
                ps.setString(2, tec.getNombre_uno());
                ps.setString(3, tec.getNombre_dos());            
                ps.setString(4, tec.getApellido_uno());
                ps.setString(5, tec.getApellido_dos());
                ps.setString(6, tec.getTelefono_corporativo());
                ps.setString(7, tec.getTelefono_personal());
                ps.setString(8, tec.getDireccion());
                ps.setString(9, tec.getCorreo());
                ps.setString(10, tec.getId_municipio());
                ps.setString(11, tec.getId_cargo());
                ps.setString(12, tec.getId_estado());
                ps.setBinaryStream(13, fl);
                ps.setString(14, tec.getId());
                
                ps.execute();
                ban = 1;
            }  catch (SQLException e) {
                System.err.println(e);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsultaEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(ban != 0){
            return true;
        }else{
            return false;
        }
    }
       
    public Vector<Empleado> listarTecnicosRadio(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vector<Empleado> datos = new Vector<Empleado>();
        Empleado tec = null;
        
        try {  
            String sql = "SELECT identificacion, nombre_uno, nombre_dos, apellido_uno, apellido_dos "
                    + "FROM empleados WHERE estado = 1 ORDER BY apellido_uno, apellido_dos";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();            
            tec = new Empleado();
            tec.setId("0");
            tec.setNombre_uno("Seleccione:");
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

    public Vector<Municipio> listarMunicipios() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vector<Municipio> datos = new Vector<Municipio>();
        Municipio mun = null;        
        try {  
            String sql = "SELECT codigo, nombre, valor_hospedaje, valor_alimentacion FROM municipios ORDER BY nombre";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            mun = new Municipio();
            mun.setCodigo("0");
            mun.setMunicipio("Seleccione:");
            mun.setValor_alimentacion(0);
            mun.setValor_hospedaje(0);
            datos.add(mun);
            
            while(rs.next()){
                mun = new Municipio();
                mun.setCodigo(rs.getString(1));
                mun.setMunicipio(rs.getString(2).toUpperCase());
                mun.setValor_alimentacion(Integer.parseInt(rs.getString(4)));
                mun.setValor_hospedaje(Integer.parseInt(rs.getString(3)));
                datos.add(mun);                
            } 
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return datos;
    }
    
    public Vector<Parqueadero> listarParqueaderos() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vector<Parqueadero> datos = new Vector<Parqueadero>();
        Parqueadero parq = null;
        
        try {  
            String sql = "SELECT id_costo_viatico, nombre, valor FROM costo_viaticos WHERE tipo_viatico = 1 ORDER BY nombre";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            parq = new Parqueadero();
            parq.setId_parqueadero("0");
            parq.setDescripcion("Seleccione:");
            parq.setValor(0);
            datos.add(parq);
            
            while(rs.next()){                
                parq = new Parqueadero();
                parq.setId_parqueadero(rs.getString(1));
                parq.setDescripcion(rs.getString(2).toUpperCase() +" --> $"+rs.getString(3) );
                parq.setValor(Integer.parseInt(rs.getString(3)));
                datos.add(parq);                                              
            }           
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return datos;
    }
    
    public Vector<Peaje> listarPeajes() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vector<Peaje> datos = new Vector<Peaje>();
        Peaje peaje = null;
        
        try {  
            String sql = "SELECT id_costo_viatico, nombre, valor FROM costo_viaticos WHERE tipo_viatico = 2 ORDER BY nombre";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            peaje = new Peaje();
            peaje.setId_peaje("0");
            peaje.setDescripcion("Seleccione:");
            peaje.setValor(0);
            datos.add(peaje);
            
            while(rs.next()){                
                peaje = new Peaje();
                peaje.setId_peaje(rs.getString(1));
                peaje.setDescripcion(rs.getString(2).toUpperCase() +" --> $"+rs.getString(3) );
                peaje.setValor(Integer.parseInt(rs.getString(3)));
                datos.add(peaje);                                               
            }           
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return datos;
    }
    
    public Vector<Gasolina> listarGasolina() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vector<Gasolina> datos = new Vector<Gasolina>();
        Gasolina gasolina = null;
        
        try {  
            String sql = "SELECT id_costo_viatico, nombre, valor FROM costo_viaticos WHERE tipo_viatico = 3 ORDER BY nombre";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            gasolina = new Gasolina();
            gasolina.setId_gasolina("0");
            gasolina.setDescripcion("Seleccione:");
            gasolina.setValor(0);
            datos.add(gasolina);
            
            while(rs.next()){                
                gasolina = new Gasolina();
                gasolina.setId_gasolina(rs.getString(1));
                gasolina.setDescripcion(rs.getString(2).toUpperCase() +" --> $"+rs.getString(3) );
                gasolina.setValor(Integer.parseInt(rs.getString(3)));
                datos.add(gasolina);                                                
            }           
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return datos;
    }
    
    public ArrayList<CostoViatico> listarCostosViaticos(String id_cargo){
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList listaCostosViaticos = new ArrayList();
        CostoViatico costoViatico;
        String sql = "SELECT V.id_costo_viatico, C.nombre, C.valor, C.tipo_viatico FROM viaticos_cargo V "
                + "INNER JOIN costo_viaticos C ON V.id_costo_viatico = C.id_costo_viatico "
                + "WHERE V.id_cargo = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_cargo);
            rs = ps.executeQuery();
            while(rs.next()){
                costoViatico = new CostoViatico();                
                costoViatico.setNombre(rs.getString(2));
                costoViatico.setValor(rs.getString(3));
                costoViatico.setId_viatico(rs.getString(4));
                listaCostosViaticos.add(costoViatico);
            }
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        
        return listaCostosViaticos;
        
    }
    
    public ArrayList<CostoViatico> listarCostosAutorizaciones(String id_viatico){
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList listaCostosViaticos = new ArrayList();
        CostoViatico costoViatico;
        String sql = "SELECT id_detalle_viatico, detalle, valor FROM detalle_viatico "
                + "WHERE id_viatico = ? AND id_tipo_viatico = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_viatico);
            ps.setInt(2, 4);
            rs = ps.executeQuery();
            while(rs.next()){
                costoViatico = new CostoViatico();                
                costoViatico.setNombre(rs.getString(2));
                costoViatico.setValor(rs.getString(3));
                costoViatico.setId_viatico(rs.getString(1));
                listaCostosViaticos.add(costoViatico);
            }
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        
        return listaCostosViaticos;
        
    }
    
    public ArrayList<Autorizacion> obtenerAutorizacion(String id_empleado, String estado){
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Autorizacion> datos = new ArrayList<Autorizacion>();
        Autorizacion autorizacion;
        String sql = "SELECT id_autorizacion, justificacion, valor FROM autorizaciones WHERE id_empleado = ? AND estado = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_empleado);
            ps.setString(2, estado);
            rs = ps.executeQuery();
            while(rs.next()){
                autorizacion = new Autorizacion();
                autorizacion.setId_autorizacion(rs.getString(1));
                autorizacion.setJustificacion(rs.getString(2));
                autorizacion.setValor(rs.getString(3));
                datos.add(autorizacion);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        return datos;
    }

    public ArrayList<Municipio> listarCostosMunicipio(String id_municipio) {
        ArrayList<Municipio> municipio = new ArrayList<Municipio>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Municipio mun;
        String sql = "SELECT codigo, nombre, valor_hospedaje, valor_alimentacion FROM municipios WHERE codigo = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_municipio);
            rs = ps.executeQuery();
            if(rs.next()){
                mun = new Municipio();
                mun.setCodigo(rs.getString(1));
                mun.setMunicipio(rs.getString(2));
                mun.setValor_hospedaje(rs.getInt(3));
                mun.setValor_alimentacion(rs.getInt(4));
                municipio.add(mun);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        
        return municipio;
    }

    public boolean updateViaticoAutorizado(int id){
        PreparedStatement ps = null;
        String sql = "UPDATE autorizaciones SET estado = ? WHERE id_autorizacion = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "2");
            ps.setInt(2, id);            
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public boolean cancelarViaticoAutorizado(String id_empleado){
        PreparedStatement ps = null;
        String sql = "UPDATE autorizaciones SET estado = ? WHERE id_empleado = ? AND estado = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "3");
            ps.setString(2, id_empleado);  
            ps.setString(3, "2");
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    public ArrayList<Viatico> listarViaticos(){
        ArrayList listaViaticos = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Viatico viatico;
        String sql = "SELECT V.id_viatico, V.id_empleado, E.nombre_uno, E.nombre_dos, E.apellido_uno, E.apellido_dos, "
                + "M.nombre, V.fechaSalida, V.fechaRegreso, V.fecha_programado, U.nombre, V.observaciones, "
                + "(SELECT SUM(valor * pivot) FROM detalle_viatico WHERE id_viatico = V.id_viatico) AS 'valor', D.id_dependencia "
                + "FROM viaticos V INNER JOIN empleados E ON V.id_empleado = E.identificacion "
                + "INNER JOIN usuarios U ON V.id_usuario = U.id_usuario "
                + "INNER JOIN municipios M ON V.id_municipio = M.codigo "
                + "INNER JOIN cargos C ON E.id_cargo = C.id_cargo "
                + "INNER JOIN dependencias D ON C.id_dependencia = D.id_dependencia "
                + "WHERE V.estado = 1 ORDER BY E.apellido_uno, E.apellido_dos ";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                viatico = new Viatico();
                viatico.setId_viatico(rs.getInt(1));
                viatico.setId_tecnico(rs.getString(2));
                viatico.setTecnico(rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(3)+" "+rs.getString(4));
                viatico.setDestino(rs.getString(7));
                viatico.setFechaSalida(rs.getString(8));
                viatico.setFechaRegreso(rs.getString(9));
                viatico.setFechaProgramada(rs.getString(10));
                viatico.setUsuario(rs.getString(11));
                viatico.setObservaciones(rs.getString(12)); 
                viatico.setValor(rs.getInt(13));
                viatico.setDependenciaEmpleado(rs.getString(14));
                listaViaticos.add(viatico);                
            }    
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }        
        return listaViaticos;
    }
    
    public ArrayList<Viatico> listarViaticosMunicipios(){
        ArrayList listaViaticos = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Viatico viatico;
        String sql = "SELECT V.id_viatico, V.id_empleado, E.nombre_uno, E.nombre_dos, E.apellido_uno, E.apellido_dos, "
                + "M.nombre, V.fechaSalida, V.fechaRegreso, V.fecha_programado, U.nombre, V.observaciones, "
                + "(SELECT SUM(valor * pivot) FROM detalle_viatico WHERE id_viatico = V.id_viatico) AS 'valor', D.id_dependencia "
                + "FROM viaticos V INNER JOIN empleados E ON V.id_empleado = E.identificacion "
                + "INNER JOIN usuarios U ON V.id_usuario = U.id_usuario "
                + "INNER JOIN municipios M ON V.id_municipio = M.codigo "
                + "INNER JOIN cargos C ON E.id_cargo = C.id_cargo "
                + "INNER JOIN dependencias D ON C.id_dependencia = D.id_dependencia "
                + "WHERE V.estado = 5 ORDER BY E.apellido_uno, E.apellido_dos ";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                viatico = new Viatico();
                viatico.setId_viatico(rs.getInt(1));
                viatico.setId_tecnico(rs.getString(2));
                viatico.setTecnico(rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(3)+" "+rs.getString(4));
                viatico.setDestino(rs.getString(7));
                viatico.setFechaSalida(rs.getString(8));
                viatico.setFechaRegreso(rs.getString(9));
                viatico.setFechaProgramada(rs.getString(10));
                viatico.setUsuario(rs.getString(11));
                viatico.setObservaciones(rs.getString(12)); 
                viatico.setValor(rs.getInt(13));
                viatico.setDependenciaEmpleado(rs.getString(14));
                listaViaticos.add(viatico);                
            }    
            
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }        
        return listaViaticos;
    }

    public Viatico viaticoSeleccionado(String id_viatico){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Viatico viatico = null;
        String sql = "SELECT V.id_empleado, E.apellido_uno, E.apellido_dos, E.nombre_uno, E.nombre_dos, "
                + "V.id_municipio, M.nombre, M.valor_hospedaje, M.valor_alimentacion, "
                + "V.observaciones, V.fechaSalida, V.fechaRegreso, V.fecha_programado, E.id_cargo, E.id_municipio "
                + "FROM viaticos V INNER JOIN empleados E ON V.id_empleado = E.identificacion "
                + "INNER JOIN municipios M ON V.id_municipio = M.codigo "
                + "WHERE V.id_viatico = ? ORDER BY E.apellido_uno, E.apellido_dos";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_viatico);
            rs = ps.executeQuery();
            if(rs.next()){
                viatico = new Viatico();
                viatico.setId_tecnico(rs.getString(1));
                viatico.setTecnico(rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5));
                viatico.setId_destino(rs.getString(6));
                viatico.setDestino(rs.getString(7));
                viatico.setValor_hospedaje(rs.getInt(8));
                viatico.setValor_alimentacion(rs.getInt(9));
                viatico.setObservaciones(rs.getString(10));
                viatico.setFechaSalida(rs.getString(11));
                viatico.setFechaRegreso(rs.getString(12));
                viatico.setFechaProgramada(rs.getString(13));
                viatico.setId_cargo(rs.getString(14));
                viatico.setEstado(rs.getString(15));
            }
            return viatico;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }        
    }
    
    public Vector<Municipio> listarMunicipiosEditar(String id, String municipio, int v_hos, int v_ali) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vector<Municipio> datos = new Vector<Municipio>();
        Municipio mun = null;        
        try {  
            String sql = "SELECT codigo, nombre, valor_hospedaje, valor_alimentacion FROM municipios ORDER BY nombre";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            mun = new Municipio();
            mun.setCodigo(id);
            mun.setMunicipio(municipio.toUpperCase());
            mun.setValor_alimentacion(v_hos);
            mun.setValor_hospedaje(v_ali);
            datos.add(mun);
            
            while(rs.next()){
                if(!rs.getString(1).equals(id)){
                    mun = new Municipio();
                    mun.setCodigo(rs.getString(1));
                    mun.setMunicipio(rs.getString(2).toUpperCase());
                    mun.setValor_alimentacion(Integer.parseInt(rs.getString(4)));
                    mun.setValor_hospedaje(Integer.parseInt(rs.getString(3)));
                    datos.add(mun);  
                }
            } 
        } catch (SQLException e) {
            System.err.println(e); 
            return null;
        }
        return datos;
    }
    
    public ArrayList<Detalle_viatico> verDetalleViatico(String id_viatico){
        ArrayList lista = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Detalle_viatico det;
        
        String sql = "SELECT detalle, id_tipo_viatico, pivot FROM detalle_viatico WHERE id_viatico = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_viatico);
            rs = ps.executeQuery();
            while(rs.next()){
                det = new Detalle_viatico();
                det.setDetalle(rs.getString(1));
                det.setTipo_viatico(rs.getString(2));
                det.setPivot(rs.getInt(3));
                lista.add(det);
            }
            return lista;
        } catch (SQLException e) {
            return null;
        }
    }
    
    public int obtenerPivot(String id_viatico){
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT  pivot FROM detalle_viatico WHERE id_viatico = ? AND id_tipo_viatico = 6 LIMIT 1";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_viatico);
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
    
    public int obtenerPivotOtros(String id_viatico){
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT  pivot FROM detalle_viatico WHERE id_viatico = ? AND id_tipo_viatico = 6 LIMIT 1";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_viatico);
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
    
    public int obtenerPivotAlimentacion(String id_viatico){
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT  pivot FROM detalle_viatico WHERE id_viatico = ? AND id_tipo_viatico = 5 LIMIT 1";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_viatico);
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
    
    public boolean updateViatico(int id, int estado){
        PreparedStatement ps = null;        
        Date fecha = new Date();            
        String strDateFormat = "yyyy-MM-dd"; // El formato de fecha está especificado  
        SimpleDateFormat f = new SimpleDateFormat(strDateFormat);
        String feActual = f.format(fecha);

        Date hora = new Date();
        String strHoraFormat = "hh:mm:ss"; // El formato de fecha está especificado  
        SimpleDateFormat h = new SimpleDateFormat(strHoraFormat);
        String hoActual = h.format(hora);
        
        String sql = "UPDATE viaticos SET fecha_entrega=?, hora_entrega=?, estado=? WHERE id_viatico=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, feActual);
            ps.setString(2, hoActual);
            ps.setInt(3, estado);
            ps.setInt(4, id);
            
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }        
    }
    
    public boolean updateViaticoRegistrado(Viatico viatico){
        PreparedStatement ps = null;
        Date fecha = new Date();            
        String strDateFormat = "yyyy-MM-dd"; // El formato de fecha está especificado  
        SimpleDateFormat f = new SimpleDateFormat(strDateFormat);
        String feActual = f.format(fecha);

        Date hora = new Date();
        String strHoraFormat = "hh:mm:ss"; // El formato de fecha está especificado  
        SimpleDateFormat h = new SimpleDateFormat(strHoraFormat);
        String hoActual = h.format(hora);
        
        String sql = "UPDATE viaticos SET id_municipio=?, observaciones=?, fechaSalida=?, fechaRegreso=?, fecha_programado=?, "
                + "hora_programado=? WHERE id_viatico=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, viatico.getId_destino());
            ps.setString(2, viatico.getObservaciones());
            ps.setString(3, viatico.getFechaSalida());
            ps.setString(4, viatico.getFechaRegreso());
            ps.setString(5, feActual);
            ps.setString(6, hoActual);           
            ps.setInt(7, viatico.getId_viatico());
            
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } 
    }

    public boolean deleteDetalleViatico(int id_viatico){
        PreparedStatement ps = null;  
        String sql = "DELETE FROM detalle_viatico WHERE id_viatico=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_viatico);
            ps.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
