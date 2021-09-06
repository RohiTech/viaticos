/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultaEmpleados;
import Modelo.Empleado;
import paneles.IconCellRenderer;
import paneles.pnlListaTecnicos;
import principal.diaEditTecnico;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
import javax.swing.table.TableColumnModel;

/**
 *
 * @author AUXINVTIC
 */
public class CtrlTecnicos {
    
    ConsultaEmpleados modTecnico = new ConsultaEmpleados();
    pnlListaTecnicos tecnicos;
        
    public CtrlTecnicos (pnlListaTecnicos tecnicos, ConsultaEmpleados modTecnico){
        this.tecnicos = tecnicos;
        this.modTecnico = modTecnico;
        llenarTabla(this.tecnicos.tblTecnicos);
    }

    public CtrlTecnicos() {
        
    }    
    
    public void llenarTabla(JTable tblTecnicos){
        tblTecnicos.setDefaultRenderer(Object.class, new IconCellRenderer());
        DefaultTableModel modeloT = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblTecnicos.setModel(modeloT);
        tblTecnicos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblTecnicos.getTableHeader().setOpaque(false);
        tblTecnicos.getTableHeader().setBackground(new Color (204, 0, 0));
        tblTecnicos.getTableHeader().setForeground(new Color(255,255,255));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tblTecnicos.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        
        
        modeloT.addColumn("CÓDIGO");
        modeloT.addColumn("IDENTIFICACIÓN");
        modeloT.addColumn("NOMBRE");
        modeloT.addColumn("TEL. PERSONAL");
        modeloT.addColumn("TEL. CORPORATIVO");        
        modeloT.addColumn("DIRECCIÓN");
        modeloT.addColumn("CORREO ELECTRÓNICO");
        modeloT.addColumn("MUNICIPIO");
        modeloT.addColumn("CARGO");
        modeloT.addColumn("ESTADO");
        modeloT.addColumn("FOTO");
        modeloT.addColumn("EDITAR");
        
        TableColumnModel columnModel = tblTecnicos.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(1).setPreferredWidth(80);
        columnModel.getColumn(2).setPreferredWidth(160);
        columnModel.getColumn(3).setPreferredWidth(120);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(100);
        columnModel.getColumn(7).setPreferredWidth(100);
        columnModel.getColumn(8).setPreferredWidth(100);
        columnModel.getColumn(9).setPreferredWidth(60);
        columnModel.getColumn(10).setPreferredWidth(72);
        columnModel.getColumn(11).setPreferredWidth(60);
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        
        columnModel.getColumn(0).setCellRenderer(tcr);
        columnModel.getColumn(4).setCellRenderer(tcr);        
        columnModel.getColumn(3).setCellRenderer(tcr); 
        
        if(modTecnico.listTecnicos() != null){
            List<Empleado> empleados = new ArrayList<Empleado>();
            empleados = modTecnico.listTecnicos();
            Object[] columna = new Object[12];
            int numRegistros = modTecnico.listTecnicos().size();
            JLabel label = new JLabel(new ImageIcon(getClass().getResource("/img/editar.png")));
            
            try {
                for(Empleado empleado : empleados){
                    columna[0] = empleado.getCodigo();
                    columna[1] = empleado.getId();
                    columna[2] = empleado.getApellido_uno().toUpperCase() +" "+empleado.getApellido_dos().toUpperCase()+" "+empleado.getNombre_uno().toUpperCase()+" "+empleado.getNombre_dos().toUpperCase();
                    columna[3] = empleado.getTelefono_personal();
                    columna[4] = empleado.getTelefono_corporativo();                    
                    columna[5] = empleado.getDireccion().toUpperCase();
                    columna[6] = empleado.getCorreo();
                    columna[7] = empleado.getMunicipio().toUpperCase();
                    columna[8] = empleado.getCargo().toUpperCase();
                    columna[9] = empleado.getEstado().toUpperCase();
                    Blob blob = empleado.getFoto();
                    byte[] data = blob.getBytes(1, (int)blob.length());
                    BufferedImage img = null;
                    try {
                        img = ImageIO.read(new ByteArrayInputStream(data));
                    } catch (Exception e) {
                    }
                    ImageIcon icono = new ImageIcon(img.getScaledInstance(70, 70, Image.SCALE_SMOOTH));
                    columna[10] = new JLabel(icono);
                    columna[11] = label;
                    modeloT.addRow(columna);
                }
            } catch (Exception e) {
            }
             
        }
               
    } 
    
    public void llenarTipoDocs(JComboBox tipoDocs){
        DefaultComboBoxModel modelDocs = new DefaultComboBoxModel(modTecnico.listTipoDoc());
        tipoDocs.setModel(modelDocs);
    }
    
    public void llenarMunicipios(JComboBox municipios){
        DefaultComboBoxModel modelMun = new DefaultComboBoxModel(modTecnico.listaMunicipios());
        municipios.setModel(modelMun);
        
    }
    
    public void llenarCargos(JComboBox cargos, String id){
        DefaultComboBoxModel modelCargo = new DefaultComboBoxModel(modTecnico.listaCargos(id));
        cargos.setModel(modelCargo);
        
    }
    
    public void llenarDependencias(JComboBox dependencias){
        DefaultComboBoxModel modelCargo = new DefaultComboBoxModel(modTecnico.listaDependencias());
        dependencias.setModel(modelCargo);
        
    }
    
    public void llenarTipoDocsT(JComboBox tipoDocs, Empleado te){
        DefaultComboBoxModel modelDocs = new DefaultComboBoxModel(modTecnico.listTipoDocT(te));
        tipoDocs.setModel(modelDocs);
    }
    
    public void llenarMunicipiosT(JComboBox municipios, Empleado te){
        DefaultComboBoxModel modelMun = new DefaultComboBoxModel(modTecnico.listaMunicipiosT(te));
        municipios.setModel(modelMun);
        
    }
    
    public void llenarCargosT(JComboBox cargos, Empleado te){
        DefaultComboBoxModel modelCargo = new DefaultComboBoxModel(modTecnico.listaCargosT(te));
        cargos.setModel(modelCargo);
        
    }
    
    public void llenarEstadosT(JComboBox estados, Empleado te){
        DefaultComboBoxModel modelEst = new DefaultComboBoxModel(modTecnico.listaEstadosT(te));
        estados.setModel(modelEst);
        
    }
    
    public void llenarDatosTec(diaEditTecnico editTec, Empleado t){               
        if(modTecnico.buscarTec(t)){
            try {
                editTec.txtId.setText(t.getId());
                editTec.txtNombre.setText(t.getNombre_uno());
                editTec.txtNombreDos.setText(t.getNombre_dos());
                editTec.txtApellidoUno.setText(t.getApellido_uno());
                editTec.txtApellidoDos.setText(t.getApellido_dos());
                editTec.txtTelCorp.setText(t.getTelefono_corporativo());
                editTec.txtTelPersonal.setText(t.getTelefono_personal());
                editTec.txtDireccion.setText(t.getDireccion());
                editTec.txtCorreo.setText(t.getCorreo());
                editTec.lblCodigo.setText(t.getCodigo());
                Blob blob = t.getFoto();
                byte[] data = blob.getBytes(1, (int)blob.length());
                BufferedImage img = null;
                try {
                    img = ImageIO.read(new ByteArrayInputStream(data));
                } catch (Exception e) {
                }
                ImageIcon icono = new ImageIcon(img.getScaledInstance(160, 170, Image.SCALE_SMOOTH));
                editTec.lblFoto.setIcon(icono);
                                
            } catch (SQLException ex) {
                Logger.getLogger(CtrlTecnicos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }


    public void updateTecnico(Empleado t, pnlListaTecnicos pnlTec, diaEditTecnico editTec){
        if(modTecnico.actualizarTecnico(t)){
            llenarTabla(pnlTec.tblTecnicos);
            editTec.setVisible(false);
            editTec.dispose();
            JOptionPane.showMessageDialog(null, "Datos actualizados correctamente.");
        }else{
            JOptionPane.showMessageDialog(null, "Ocurrio un error.");
        }
    }
    
    public void updateTecnicoFoto(Empleado t, pnlListaTecnicos pnlTec, diaEditTecnico editTec){
        File file = new File(editTec.txtFoto.getText());
        if(modTecnico.actualizarTecnicoFoto(t, file)){
            llenarTabla(pnlTec.tblTecnicos);
            editTec.setVisible(false);
            editTec.dispose();
            JOptionPane.showMessageDialog(null, "Datos actualizados correctamente.");
        }else{
            JOptionPane.showMessageDialog(null, "Ocurrio un error.");
        }
    }

}
