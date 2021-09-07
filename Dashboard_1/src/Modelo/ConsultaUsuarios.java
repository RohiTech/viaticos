
package Modelo;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import org.apache.commons.codec.binary.Base64;


/**
 *
 * @author AUXINVTIC
 */
public class ConsultaUsuarios extends Conexion {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection con = getConexion();
    
    public ArrayList<User> listUsuarios(){
        ArrayList listaUsuarios = new ArrayList();
        User user;
        PreparedStatement ps = null;
            ResultSet rs = null;
            String sql = "SELECT U.id_usuario, U.nombre, U.direccion, U.telefono, U.correo, U.username, U.password, C.nombre_rol"
                    + " FROM usuarios U INNER JOIN roles C ON U.rol = C.id_rol ORDER BY U.nombre";
        try {           
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                user = new User();
                user.setId(rs.getString(1));
                user.setNombre(rs.getString(2));
                user.setDireccion(rs.getString(3));
                user.setTelefono(rs.getString(4));                
                user.setCorreo(rs.getString(5));
                user.setUsername(rs.getString(6));
                user.setPassword(rs.getString(7));
                user.setCargo(rs.getString(8));
                listaUsuarios.add(user);
            } 
        } catch (Exception e) {
            System.err.println(e);            
        }
        return listaUsuarios;
        
    }
    
    public ArrayList<Empleado> listTecnicos(){
        ArrayList listaTecnicos = new ArrayList();
        Empleado tecnico;
        PreparedStatement ps = null;
            ResultSet rs = null;
            String sql = "SELECT identificacion, nombre, telefono, direccion, correo FROM empleados";
        try {           
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                tecnico = new Empleado();
                tecnico.setId(rs.getString(1));
                tecnico.setNombre_uno(rs.getString(2));
                tecnico.setTelefono_corporativo(rs.getString(3));
                tecnico.setDireccion(rs.getString(4));
                tecnico.setCorreo(rs.getString(5));
                listaTecnicos.add(tecnico);
            }           
            
        } catch (Exception e) {
            System.err.println(e);            
        }
        return listaTecnicos;
        
    }
    
    public boolean regiUsuario(User user){
        String sql = "INSERT INTO usuarios(id_usuario, nombre, direccion, telefono, correo, username, password, rol) VALUES(?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getId());
            ps.setString(2, user.getNombre());
            ps.setString(3, user.getDireccion());
            ps.setString(4, user.getTelefono());            
            ps.setString(5, user.getCorreo());
            ps.setString(6, user.getUsername());
            ps.setString(7, user.getPassword());
            ps.setString(8, user.getCargo());
            ps.execute();
            return true;
            
        } catch (SQLException e) {
            //JOptionPane.showMessageDialog(null, "Error: "+e, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean validarUsuario(String username){
        String sql = "SELECT username FROM usuarios WHERE username = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la base de datos");
            return false;
        }
    }
    
    public boolean validarUserPerfil(String username, String id_user){
        String sql = "SELECT username FROM usuarios WHERE username = ? AND id_usuario <> ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, id_user);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la base de datos");
            return false;
        }
    }
    
    public boolean updateUsuario(User user){
        String sql = "UPDATE usuarios SET nombre=?, direccion=?, telefono=?, correo=?, username=?, password=?, rol=? WHERE id_usuario=?";
        try {
            ps = con.prepareStatement(sql);
            
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getDireccion());
            ps.setString(3, user.getTelefono());            
            ps.setString(4, user.getCorreo());
            ps.setString(5, user.getUsername());
            ps.setString(6, user.getPassword());
            ps.setString(7, user.getCargo());
            ps.setString(8, user.getId());
            ps.execute();
            return true;
        }  catch (SQLException e) {
            System.err.println(e);
            return false;
        }
            
    }

    public Vector<Cargo> listaCargosUsuarios() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Vector<Cargo> datos = new Vector<Cargo>();
        Cargo cargo = null;        
        
        try {       
            String sql = "SELECT id_rol, nombre_rol FROM roles ORDER BY nombre_rol";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            cargo = new Cargo();
            cargo.setIdCargo("0");
            cargo.setNombreCargo("Seleccione:");
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

    public Vector<Cargo> listaCargosUsuariosUpdate(User usu){        
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        ResultSet rst = null;
        Vector<Cargo> datos = new Vector<Cargo>();
        Cargo cargo = null;         
        String c = null;
        try { 
            String sqlt = "SELECT U.rol, C.nombre_rol FROM usuarios U INNER JOIN roles C ON U.rol = C.id_rol WHERE id_usuario=?";
            pst = con.prepareStatement(sqlt);
            pst.setString(1, usu.getId());
            rst = pst.executeQuery();
            
            String sql = "SELECT id_rol, nombre_rol FROM roles ORDER BY nombre_rol";
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

    public ArrayList<Permiso> permiUsuarios(String id_usuario){
        ArrayList<Permiso> permisosUsuario = new ArrayList<Permiso>();
        Permiso permiso;
        String sql = "SELECT id_usuario, id_modulo FROM permisos WHERE id_usuario = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_usuario);
            rs = ps.executeQuery();
            while(rs.next()){
                permiso = new Permiso();
                permiso.setId_usuario(rs.getString(1));
                permiso.setId_modulo(rs.getString(2));
                permisosUsuario.add(permiso);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        return permisosUsuario;
    }
    
    public ArrayList<Permiso> permisosUsuarios(int id_usuario){
        ArrayList<Permiso> permisosUsuario = new ArrayList<Permiso>();
        Permiso permiso;
        String sql = "SELECT M.id_modulo, M.nombre_modulo, "
                + "(SELECT id_usuario FROM permisos WHERE id_modulo = M.id_modulo AND id_usuario = ?) "
                + "FROM modulos M";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_usuario);
            rs = ps.executeQuery();
            while(rs.next()){
                permiso = new Permiso();                
                permiso.setId_modulo(rs.getString(1));
                permiso.setNombre_modulo(rs.getString(2));
                if(rs.getString(3) == null || rs.getString(3).equals("")){
                    permiso.setId_usuario("0"); 
                }else{
                    permiso.setId_usuario(rs.getString(3));
                }                
                permisosUsuario.add(permiso);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        return permisosUsuario;
    }
    
    public boolean deletePermisos(String id_usuario){
        String sql = "DELETE FROM permisos WHERE id_usuario = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_usuario);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public boolean insertPermisos(String id_usuario, String id_modulo){
        String sql = "INSERT INTO permisos(id_usuario, id_modulo) VALUES(?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_usuario);
            ps.setString(2, id_modulo);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    public boolean validarModuloUsuario(String id_usuario, String id_modulo){
        String sql = "SELECT id_usuario FROM permisos WHERE id_usuario = ? AND id_modulo = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_usuario);
            ps.setString(2, id_modulo);
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
     
    public String encriptar(String texto, String llave){

        String secretKey = llave; //llave para encriptar datos
        String base64EncryptedString = "";

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);

        } catch (Exception ex) {
            
        }
        return base64EncryptedString;
}

    public String Desencriptar(String textoEncriptado, String llave){

        String secretKey = llave; //llave para desenciptar datos
        String base64EncryptedString = "";

        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");

        } catch (Exception ex) {
            
        }
        return base64EncryptedString;
    }
    
    public User infoUser(String id_user){
        User user = null;
        String sql = "SELECT U.id_usuario, U.nombre, U.direccion, U.telefono, U.correo, U.username, "
                + "U.password, R.nombre_rol FROM usuarios U "
                + "INNER JOIN roles R ON U.rol = R.id_rol "
                + "WHERE U.id_usuario = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_user);
            rs = ps.executeQuery();
            if(rs.next()){
                user = new User();
                user.setId(rs.getString(1));
                user.setNombre(rs.getString(2));
                user.setDireccion(rs.getString(3));
                user.setTelefono(rs.getString(4));
                user.setCorreo(rs.getString(5));
                user.setUsername(rs.getString(6));
                user.setPassword(rs.getString(7));
                user.setCargo(rs.getString(8));
            }
            return user;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        
    }
    
    public boolean updatePerfil(User user){
        String sql = "UPDATE usuarios SET nombre=?, direccion=?, telefono=?, correo=?, username=?, password=? "
                + "WHERE id_usuario=?";
        try {
            ps = con.prepareStatement(sql);
            
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getDireccion());
            ps.setString(3, user.getTelefono());            
            ps.setString(4, user.getCorreo());
            ps.setString(5, user.getUsername());
            ps.setString(6, user.getPassword());
            ps.setString(7, user.getId());
            ps.execute();
            return true;
        }  catch (SQLException e) {
            System.err.println(e);
            return false;
        }
            
    }
}
