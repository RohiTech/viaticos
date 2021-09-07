/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cargo;
import Modelo.ConsultaUsuarios;
import Modelo.Permiso;
import Modelo.User;
import dialogs.diaPermisos;
import paneles.IconCellRenderer;
import paneles.pnlUsuarios;
import principal.diaRegUsuarios;
import principal.diaEditUsuario;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import principal.diaEditUser;

/**
 *
 * @author AUXINVTIC
 */
public class CtrlUsuarios {
    
    User user = new User();
    ConsultaUsuarios mod = new ConsultaUsuarios();
    pnlUsuarios usuarios;
    
    public CtrlUsuarios(){
        
    }
    
    public CtrlUsuarios(pnlUsuarios usuarios, ConsultaUsuarios mod){
        this.usuarios = usuarios;
        this.mod = mod;
        try {
            llenarTablaUsers(this.usuarios.tablaUsuarios);
        } catch (Exception ex) {
            Logger.getLogger(CtrlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            
    public void registrarUsuario(diaRegUsuarios usuarios, pnlUsuarios pnl) throws Exception{
       Cargo cargo = (Cargo) usuarios.cbCargo.getSelectedItem();
        
        if(usuarios.txtId.getText().equals("")){
           JOptionPane.showMessageDialog(null, "Ingrese el número de identificación del usuario.", "Importante", JOptionPane.WARNING_MESSAGE);
       }else if (usuarios.txtNombre.getText().equals("")){
           JOptionPane.showMessageDialog(null, "Ingrese el nombre completo del usuario.", "Importante", JOptionPane.WARNING_MESSAGE);
       }else if (usuarios.txtTelefono.getText().equals("")){
           JOptionPane.showMessageDialog(null, "Ingrese el teléfono del usuario.", "Importante", JOptionPane.WARNING_MESSAGE);
       }else if (usuarios.txtDireccion.getText().equals("")){
           JOptionPane.showMessageDialog(null, "Ingrese la dirección del usuario.", "Importante", JOptionPane.WARNING_MESSAGE);
       }else if (usuarios.txtCorreo.getText().equals("")){
           JOptionPane.showMessageDialog(null, "Ingrese el correo electrónico del usuario.", "Importante", JOptionPane.WARNING_MESSAGE);
       }else if (usuarios.txtUsername.getText().equals("")){
           JOptionPane.showMessageDialog(null, "Ingrese la cuenta de acceso al sistema del usuario.", "Importante", JOptionPane.WARNING_MESSAGE);
       }else if (usuarios.txtUsername.getText().equals("")){
           JOptionPane.showMessageDialog(null, "Ingrese la cuenta de acceso al sistema del usuario.", "Importante", JOptionPane.WARNING_MESSAGE);
       }else if (usuarios.txtPassword.getText().equals("")){
           JOptionPane.showMessageDialog(null, "Ingrese la contraseña de acceso al sistema del usuario.", "Importante", JOptionPane.WARNING_MESSAGE);
       }else if (mod.validarUsuario(usuarios.txtUsername.getText())){
           JOptionPane.showMessageDialog(null, "El usuario ingresado ya se encuentra \nregistrado en la base de datos.", "Importante", JOptionPane.WARNING_MESSAGE);
       }else{     
            String pass = mod.encriptar(usuarios.txtPassword.getText(), usuarios.txtUsername.getText());
            String pass2 = mod.encriptar(pass, usuarios.txtUsername.getText());
            
            user.setId(usuarios.txtId.getText());
            user.setNombre(usuarios.txtNombre.getText());
            user.setTelefono(usuarios.txtTelefono.getText());
            user.setDireccion(usuarios.txtDireccion.getText());
            user.setCorreo(usuarios.txtCorreo.getText());
            user.setUsername(usuarios.txtUsername.getText());
            user.setPassword(pass2);
            user.setCargo(cargo.getIdCargo());
                        
            if(mod.regiUsuario(user)){
                if(cargo.getIdCargo().equals("1")){
                    for(int n=1; n<8; n++ ){
                        mod.insertPermisos(usuarios.txtId.getText(), String.valueOf(n));
                    }
                }
                if(cargo.getIdCargo().equals("2")){
                    mod.insertPermisos(usuarios.txtId.getText(), "1"); 
                    mod.insertPermisos(usuarios.txtId.getText(), "3");   
                }
                if(cargo.getIdCargo().equals("3")){
                    mod.insertPermisos(usuarios.txtId.getText(), "2"); 
                    mod.insertPermisos(usuarios.txtId.getText(), "3");   
                }
                usuarios.txtId.setText("");
                usuarios.txtNombre.setText("");
                usuarios.txtTelefono.setText("");
                usuarios.txtDireccion.setText("");
                usuarios.txtCorreo.setText("");
                usuarios.cbCargo.setSelectedIndex(0);
                usuarios.txtUsername.setText("");
                usuarios.txtPassword.setText("");
                llenarTablaUsers(pnl.tablaUsuarios);
                usuarios.setVisible(false);
                usuarios.dispose();
                
                
                JOptionPane.showMessageDialog(null, "Usuario registrado");                
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio un error");
            }
       }
    }
    
    public boolean modificarUsuario(diaEditUsuario usuarios, pnlUsuarios pnl) throws Exception{
        Cargo cargo = (Cargo) usuarios.cbCargos.getSelectedItem();
        int ban = 0;
        
        if(usuarios.txtId.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el número de identificación del usuario.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else if (usuarios.txtNombre.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el nombre completo del usuario.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else if (usuarios.txtTelefono.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el teléfono del usuario.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else if (usuarios.txtDireccion.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese la dirección del usuario.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else if (usuarios.txtCorreo.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el correo electrónico del usuario.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else if (usuarios.txtUsername.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese la cuenta de acceso al sistema del usuario.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else if (usuarios.txtUsername.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese la cuenta de acceso al sistema del usuario.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else if (usuarios.txtPassword.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese la contraseña de acceso al sistema del usuario.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else if (mod.validarUserPerfil(usuarios.txtUsername.getText(), usuarios.txtId.getText())){
            JOptionPane.showMessageDialog(null, "El usuario ingresado ya se encuentra \nregistrado en la base de datos.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else{   
            String pass = mod.encriptar(usuarios.txtPassword.getText(), usuarios.txtUsername.getText());
            String pass2 = mod.encriptar(pass, usuarios.txtUsername.getText());
            
            user.setId(usuarios.txtId.getText());
            user.setNombre(usuarios.txtNombre.getText());
            user.setTelefono(usuarios.txtTelefono.getText());
            user.setDireccion(usuarios.txtDireccion.getText());
            user.setCorreo(usuarios.txtCorreo.getText());
            user.setUsername(usuarios.txtUsername.getText());
            user.setPassword(pass2);
            user.setCargo(cargo.getIdCargo());
             
            if(mod.updateUsuario(user)){
                mod.deletePermisos(usuarios.txtId.getText());
                if(cargo.getIdCargo().equals("1")){
                    for(int n=1; n<8; n++ ){
                        mod.insertPermisos(usuarios.txtId.getText(), String.valueOf(n));
                    }
                }
                if(cargo.getIdCargo().equals("2")){
                    mod.insertPermisos(usuarios.txtId.getText(), "1"); 
                    mod.insertPermisos(usuarios.txtId.getText(), "3");   
                }
                if(cargo.getIdCargo().equals("3")){
                    mod.insertPermisos(usuarios.txtId.getText(), "2"); 
                    mod.insertPermisos(usuarios.txtId.getText(), "3");   
                }
                ban = 1;
            }
        }
        if(ban != 0){
            llenarTablaUsers(pnl.tablaUsuarios); 
            return true;
        }else{
            return false;
        }
        
    }
   
   
    public void llenarTablaUsers(JTable tablaUsuarios) throws Exception {
        tablaUsuarios.setDefaultRenderer(Object.class,new IconCellRenderer());
        DefaultTableModel modeloT = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaUsuarios.setModel(modeloT);
        tablaUsuarios.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaUsuarios.getTableHeader().setOpaque(false);
        tablaUsuarios.getTableHeader().setBackground(new Color (204, 0, 0));
        tablaUsuarios.getTableHeader().setForeground(new Color(255,255,255));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tablaUsuarios.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        
        
        modeloT.addColumn("#");
        modeloT.addColumn("IDENTIFICACIÓN");
        modeloT.addColumn("NOMBRE");
        modeloT.addColumn("TELEFONO");
        modeloT.addColumn("DIRECCION");
        modeloT.addColumn("CORREO");
        modeloT.addColumn("ROL");
        modeloT.addColumn("USUARIO");
        modeloT.addColumn("CONTRASEÑA");
        modeloT.addColumn("PERMISOS");
        modeloT.addColumn("EDITAR");
        
        TableColumnModel columnModel = tablaUsuarios.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(120);
        columnModel.getColumn(5).setPreferredWidth(150);
        columnModel.getColumn(6).setPreferredWidth(100);
        columnModel.getColumn(7).setPreferredWidth(100);
        columnModel.getColumn(8).setPreferredWidth(100);
        columnModel.getColumn(9).setPreferredWidth(100);
       
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        
        columnModel.getColumn(0).setCellRenderer(tcr);
        columnModel.getColumn(3).setCellRenderer(tcr); 
         
        List<User> usuarios = new ArrayList<User>();
        usuarios = mod.listUsuarios();
        Object[] columna = new Object[11];
        
        int i = 1;
        JLabel label = new JLabel(new ImageIcon(getClass().getResource("/img/editar.png")));
        JLabel permisos = new JLabel(new ImageIcon(getClass().getResource("/img/lock.png")));
        
        for(User user : usuarios){
            columna[0] = i;
            columna[1] = user.getId();
            columna[2] = user.getNombre().toUpperCase();
            columna[3] = user.getTelefono();
            columna[4] = user.getDireccion().toUpperCase();
            columna[5] = user.getCorreo();
            columna[6] = user.getCargo().toUpperCase();            
            String usu = user.getUsername();
            String contra = user.getPassword();
            String pass = mod.Desencriptar(contra, usu);
            String password = mod.Desencriptar(pass, usu);
            columna[7] = usu;
            columna[8] = password;
            columna[9] = permisos;
            columna[10] = label;
            modeloT.addRow(columna);
            i++;
        } 
    }
   
    public void llenarCargos(JComboBox tipoDocs){
        DefaultComboBoxModel modelDocs = new DefaultComboBoxModel(mod.listaCargosUsuarios());
        tipoDocs.setModel(modelDocs);
    }
    
    public void llenarCargosUpdate(JComboBox tipoDocs, User usu){
        DefaultComboBoxModel modelDocs = new DefaultComboBoxModel(mod.listaCargosUsuariosUpdate(usu));
        tipoDocs.setModel(modelDocs);
    }
    
    
    public void listarPermisos(String id_usuario, diaPermisos dia){
        DefaultTableModel modeloTable = new DefaultTableModel();
        dia.tblPermisos.setModel(modeloTable);
        dia.tblPermisos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        dia.tblPermisos.getTableHeader().setOpaque(false);
        dia.tblPermisos.getTableHeader().setBackground(new Color (204, 0, 0));
        dia.tblPermisos.getTableHeader().setForeground(new Color(255,255,255));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) dia.tblPermisos.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        modeloTable.addColumn("#");
        modeloTable.addColumn("MÓDULO");
        modeloTable.addColumn("PERMISO");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        
        TableColumnModel columnModel = dia.tblPermisos.getColumnModel();
        
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(60);  
        columnModel.getColumn(3).setMaxWidth(0);
        columnModel.getColumn(3).setMinWidth(0);
        columnModel.getColumn(3).setPreferredWidth(0);
        columnModel.getColumn(4).setMaxWidth(0);
        columnModel.getColumn(4).setMinWidth(0);
        columnModel.getColumn(4).setPreferredWidth(0);
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);        
        columnModel.getColumn(0).setCellRenderer(tcr);

        addChecBox(2, dia.tblPermisos);
        int id_user = Integer.parseInt(id_usuario);
        List<Permiso> permisos = new ArrayList<Permiso>();
        permisos = mod.permisosUsuarios(id_user);
        Object[] colum = new Object[5];
        int i = 1;

        for(Permiso permiso : permisos){
            colum[0] = i;
            colum[1] = permiso.getNombre_modulo().toUpperCase();
            if(permiso.getId_usuario().equals("0")){
                colum[2] = Boolean.FALSE;  
            }else{
                colum[2] = Boolean.TRUE;
            }
            colum[3] = permiso.getId_modulo();
            colum[4] = permiso.getId_usuario();
            modeloTable.addRow(colum);
            i++;
        }
    }
    
    public void registrarPermisos(diaPermisos dia){
        if(mod.deletePermisos(dia.txtUsuario.getText())){
            for(int i=0; i<dia.tblPermisos.getRowCount(); i++){
                if(IsSelected(i, 2, dia.tblPermisos)){
                    mod.insertPermisos(dia.txtUsuario.getText(), dia.tblPermisos.getValueAt(i, 3).toString());
                }
            }
            dia.setVisible(false);
            dia.dispose();
            JOptionPane.showMessageDialog(null, "Registro exitoso.");
        }else{
           JOptionPane.showMessageDialog(null, "Ocurrio un error."); 
        }        
    }
    
    public boolean validarUsuarioModulo(String id_usuario, String id_modulo){
        return mod.validarModuloUsuario(id_usuario, id_modulo);
    }
    
    public void addChecBox(int columna, JTable tabla){
        TableColumn tc = tabla.getColumnModel().getColumn(columna);
        tc.setCellEditor(tabla.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(tabla.getDefaultRenderer(Boolean.class));
    }
   
    public boolean IsSelected(int row, int column, JTable tabla){
        Boolean checked = false;
        checked = Boolean.valueOf(tabla.getValueAt(row,column).toString());
        return checked;
        
    }
    
    public void datosMiPerfil(diaEditUser dia, String id_user){
        User user = mod.infoUser(id_user);
        dia.lblIdentificacion.setText(id_user);
        dia.txtNombre.setText(user.getNombre());
        dia.txtDireccion.setText(user.getDireccion());
        dia.txtTelefono.setText(user.getTelefono());
        dia.txtCorreo.setText(user.getCorreo());
        dia.txtRol.setText(user.getCargo());
        dia.txtUsername.setText(user.getUsername());
        String pass = mod.Desencriptar(user.getPassword(), user.getUsername());
        String password = mod.Desencriptar(pass, user.getUsername());
        dia.txtPassword.setText(password);
    }
    
    public void actualizarPerfil(diaEditUser dia){
        User user = new User();
        user.setId(dia.txtId.getText());
        user.setNombre(dia.txtNombre.getText());
        user.setDireccion(dia.txtDireccion.getText());
        user.setTelefono(dia.txtTelefono.getText());
        user.setCorreo(dia.txtCorreo.getText());
        user.setUsername(dia.txtUsername.getText());
        String pass = mod.encriptar(dia.txtPassword.getText(), dia.txtUsername.getText());
        String pass2 = mod.encriptar(pass, dia.txtUsername.getText());        
        user.setPassword(pass2);
        if (mod.validarUserPerfil(dia.txtUsername.getText(), dia.txtId.getText())){
            JOptionPane.showMessageDialog(null, "El usuario ingresado ya se encuentra \nregistrado en la base de datos.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else if(mod.updatePerfil(user)){
            dia.setVisible(false);
            dia.dispose();
            JOptionPane.showMessageDialog(null, "Información actualizada");
        }else{
            JOptionPane.showMessageDialog(null, "Ocurrio un error");
        }
    }
    
}
