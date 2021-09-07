/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Autorizacion;
import Modelo.ConsultaAutorizacion;
import Modelo.Dependencia;
import Modelo.Empleado;
import dialogs.diaEditAutorizacion;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
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
import paneles.IconCellRenderer;
import paneles.pnlAutorizacion;

/**
 *
 * @author AUXINVTIC
 */
public class CtrlAutorizacion {
    
    ConsultaAutorizacion modAuto = new ConsultaAutorizacion();
    public CtrlAutorizacion(){
        
    }
    
    public void llenarDependencias(JComboBox dependencias){
        DefaultComboBoxModel modelCargo = new DefaultComboBoxModel(modAuto.listarDependencias());
        dependencias.setModel(modelCargo);        
    }
    
    public void llenarCargos(JComboBox cargos, String id_dependencia){
        DefaultComboBoxModel modelCargo = new DefaultComboBoxModel(modAuto.listarCargoDep(id_dependencia));
        cargos.setModel(modelCargo);        
    }
    
    public void empleadosAutorizados(JComboBox empleados, String id_autorizacion){
        DefaultComboBoxModel modelCargo = new DefaultComboBoxModel(modAuto.listaEmpleadosAutorizados(id_autorizacion));
        empleados.setModel(modelCargo);        
    }
    
    public void registrarAutorizacion(pnlAutorizacion pnl){
        Dependencia dep = (Dependencia) pnl.cbDependencia.getSelectedItem();
        Empleado emp = (Empleado) pnl.cbEmpleado.getSelectedItem();
        
        if(dep.getId_dependencia().equals("0")){
            JOptionPane.showMessageDialog(null, "Seleccione una dependencia.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else if(emp.getId().equals("0")){
            JOptionPane.showMessageDialog(null, "Seleccione un empleado.", "Importante", JOptionPane.WARNING_MESSAGE);            
        }else if(pnl.txtValor.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el valor a autorizar.", "Importante", JOptionPane.WARNING_MESSAGE);
            pnl.txtValor.requestFocus();
        }else if(!isNumeric(pnl.txtValor.getText()) ){
            JOptionPane.showMessageDialog(null, "El valor ingresado no es valido.", "Importante", JOptionPane.WARNING_MESSAGE);
            pnl.txtValor.requestFocus();
        }else if(pnl.txtJustificacion.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese la justificación.", "Importante", JOptionPane.WARNING_MESSAGE);
            pnl.txtJustificacion.requestFocus();
        }else{
            Autorizacion autorizacion = new Autorizacion();
            autorizacion.setEmpleado(emp.getId());
            autorizacion.setJustificacion(pnl.txtJustificacion.getText());
            autorizacion.setValor(pnl.txtValor.getText());
            autorizacion.setEstado("1");
            autorizacion.setId_usuario(pnl.txtUsuario.getText());
            
            if(modAuto.insertAutorizacion(autorizacion)){
                llenarTablaAutorizaciones(pnl.tablaAutorizacion);
                pnl.cbDependencia.setSelectedIndex(0);
                pnl.txtValor.setText("");
                pnl.txtJustificacion.setText("");
                JOptionPane.showMessageDialog(null, "Registro exitoso.", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio un error.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }       
    }
    
    public void llenarTablaAutorizaciones(JTable tblAutorizacion){
        //DecimalFormat formato = new DecimalFormat("#,###");
        tblAutorizacion.setDefaultRenderer(Object.class, new IconCellRenderer());
        DefaultTableModel modeloT = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblAutorizacion.setModel(modeloT); 
        tblAutorizacion.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblAutorizacion.getTableHeader().setOpaque(false);
        tblAutorizacion.getTableHeader().setBackground(new Color (204, 0, 0));
        tblAutorizacion.getTableHeader().setForeground(new Color(255,255,255));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tblAutorizacion.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        
        modeloT.addColumn("");
        modeloT.addColumn("#");
        modeloT.addColumn("EMPLEADO");
        modeloT.addColumn("JUSTIFICACIÓN");
        modeloT.addColumn("VALOR");
        modeloT.addColumn("EDITAR");
        modeloT.addColumn("ANULAR");
        
        TableColumnModel columnModel = tblAutorizacion.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(1).setPreferredWidth(10);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(270);
        columnModel.getColumn(4).setPreferredWidth(30);
        columnModel.getColumn(5).setPreferredWidth(30);
        columnModel.getColumn(6).setPreferredWidth(30);        
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        
        DefaultTableCellRenderer der = new DefaultTableCellRenderer();
        der.setHorizontalAlignment(SwingConstants.RIGHT);
        
        columnModel.getColumn(1).setCellRenderer(tcr);       
        columnModel.getColumn(4).setCellRenderer(der); 
        
        if(modAuto.listaAutorizaciones()!= null){
            List<Autorizacion> autorizaciones = new ArrayList<Autorizacion>();
            autorizaciones = modAuto.listaAutorizaciones();
            Object[] columna = new Object[7];
            
            JLabel label = new JLabel(new ImageIcon(getClass().getResource("/img/editar.png")));
            JLabel anular = new JLabel(new ImageIcon(getClass().getResource("/img/cancel.png")));
            try {
                int i = 1;
                for(Autorizacion autorizacion : autorizaciones){
                    columna[0] = autorizacion.getId_autorizacion();
                    columna[1] = i;
                    columna[2] = autorizacion.getEmpleado().toUpperCase();
                    columna[3] = autorizacion.getJustificacion().toUpperCase();
                    columna[4] = autorizacion.getValor();                    
                    columna[5] = label;
                    columna[6] = anular;                    
                    modeloT.addRow(columna);
                    i++;
                }
            } catch (Exception e) {
            }
             
        }
    }
    
    public void validarSeleccionAnular(int fila, pnlAutorizacion pnl){
        Object [] opciones ={"Aceptar","Cancelar"};
        int eleccion = JOptionPane.showOptionDialog(null,
        "Desea anular el viático asignado a: \n"
                + pnl.tablaAutorizacion.getValueAt(fila, 2).toString()+"\n"
                + "\nViatico: " +" "+pnl.tablaAutorizacion.getValueAt(fila, 3).toString()+"\nValor: $"+pnl.tablaAutorizacion.getValueAt(fila, 4).toString() ,
        "Sistemas Palacios Ltda",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        opciones,
        "Aceptar");        
        switch(eleccion){
            case JOptionPane.YES_OPTION:
                if(modAuto.anularViaticoAutorizado(Integer.parseInt(pnl.tablaAutorizacion.getValueAt(fila, 0).toString()))){
                    llenarTablaAutorizaciones(pnl.tablaAutorizacion);
                    JOptionPane.showMessageDialog(null, "Registro anulado.");
                }else{
                    JOptionPane.showMessageDialog(null, "Ocurrio un error."); 
                }
            break;
            case JOptionPane.NO_OPTION:   
                
            break;        
        }
    }
    
    public void actualizarAutorizacion(diaEditAutorizacion dia, pnlAutorizacion pnl){
        if(dia.txtJustificacion.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese la justificación.", "Importante", JOptionPane.WARNING_MESSAGE);
            dia.txtJustificacion.requestFocus();
        }else if(dia.txtValor.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el valor a autorizar.", "Importante", JOptionPane.WARNING_MESSAGE);
            dia.txtValor.requestFocus();
        }else if(!isNumeric(dia.txtValor.getText()) ){
            JOptionPane.showMessageDialog(null, "El valor ingresado no es valido.", "Importante", JOptionPane.WARNING_MESSAGE);
            dia.txtValor.requestFocus();
        }else{
            Empleado emp = (Empleado) dia.cbEmpleado.getSelectedItem();
            Autorizacion auto = new Autorizacion();
            auto.setId_autorizacion(dia.txtId.getText());
            auto.setJustificacion(dia.txtJustificacion.getText());
            auto.setValor(dia.txtValor.getText());
            auto.setEmpleado(emp.getId());
            if(modAuto.updateAutorizacion(auto)){
                llenarTablaAutorizaciones(pnl.tablaAutorizacion);
                dia.setVisible(false);
                dia.dispose();
                JOptionPane.showMessageDialog(null, "Registro exitoso.", "Mensaje de confirmación", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio un error.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private static boolean isNumeric(String cadena){
	try {
            Integer.parseInt(cadena);
            return true;
	} catch (NumberFormatException nfe){
            return false;
	}
    }
}
