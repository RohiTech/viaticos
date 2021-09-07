/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cargo;
import Modelo.ConsultaAjustes;
import Modelo.CostoViatico;
import Modelo.Dependencia;
import dialogs.diaEditCargos;
import dialogs.diaEditDependencia;
import dialogs.diaNewCargos;
import dialogs.diaNewDependencia;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import paneles.IconCellRenderer;

/**
 *
 * @author AUXINVTIC
 */
public class CtrlAjustes {
    ConsultaAjustes modAjustes = new ConsultaAjustes();
    
    public CtrlAjustes(){
        
    }
    
    public void llenarTabla(JTable tblDependencias){
        tblDependencias.setDefaultRenderer(Object.class, new IconCellRenderer());
        DefaultTableModel modeloT = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblDependencias.setModel(modeloT);
        tblDependencias.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblDependencias.getTableHeader().setOpaque(false);
        tblDependencias.getTableHeader().setBackground(new Color (204, 0, 0));
        tblDependencias.getTableHeader().setForeground(new Color(255,255,255));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tblDependencias.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        
        
        modeloT.addColumn("DEPENDENCIA");
        modeloT.addColumn("CARGOS");
        modeloT.addColumn("EDITAR");
        modeloT.addColumn("");
        
        TableColumnModel columnModel = tblDependencias.getColumnModel();
        
        columnModel.getColumn(0).setPreferredWidth(160);
        columnModel.getColumn(1).setPreferredWidth(80);
        columnModel.getColumn(2).setPreferredWidth(80);  
        columnModel.getColumn(3).setMaxWidth(0);
        columnModel.getColumn(3).setMinWidth(0);
        columnModel.getColumn(3).setPreferredWidth(0);
        
                        
        if(modAjustes.listDependencia()!= null){
            List<Dependencia> dependencias = new ArrayList<Dependencia>();
            dependencias =  modAjustes.listDependencia();
            Object[] columna = new Object[5];
                        
            JLabel label = new JLabel(new ImageIcon(getClass().getResource("/img/editar.png")));
            JLabel cargos = new JLabel(new ImageIcon(getClass().getResource("/img/cargo.png")));
           
            try {
                for(Dependencia dependencia : dependencias){
                    columna[0] = dependencia.getDependencia().toUpperCase();
                    columna[1] = cargos;
                    columna[2] = label;
                    columna[3] = dependencia.getId_dependencia();                   
                    modeloT.addRow(columna);
                }
            } catch (Exception e) {
            }
             
        }
               
    } 
    
    public void registrarDependencia(diaNewDependencia dia){
        if(dia.txtDependencia.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el nombre de la nueva \ndependencia");
            dia.txtDependencia.requestFocus();
        }else{
            if(modAjustes.insertDependencia(dia.txtDependencia.getText())){
                llenarTabla(dia.tablaDependencias);
                JOptionPane.showMessageDialog(null, "Registro exitoso");
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio un error");
            }
        }
    }

    public void actualizarDependencia(diaEditDependencia dia, diaNewDependencia d) {
        if(dia.txtEdDescripcion.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el nombre del cargo.", "Importante", JOptionPane.WARNING_MESSAGE);
            dia.txtEdDescripcion.requestFocus();
        }else{
            Dependencia dep = new Dependencia();
            dep.setId_dependencia(dia.txtId.getText());
            dep.setDependencia(dia.txtEdDescripcion.getText());
            if(modAjustes.updateDependencia(dep)){
                llenarTabla(d.tablaDependencias);
                dia.setVisible(false);
                dia.dispose();
                JOptionPane.showMessageDialog(null, "Registro exitoso");
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio un error");
            }
        }
        
    }
    
    public void listarCargosXDepd(String id, String dep){
        String c = "Cargos de la dependencia: \n"+dep+"\n**************************************\n";
        List<Cargo> cargos = new ArrayList<Cargo>();
        cargos =  modAjustes.listCargoDependencia(id);
        //int numRegistros = modAjustes.listCargoDependencia(id).size();
        for(Cargo cargo : cargos){
            c += "- "+cargo.getNombreCargo().toUpperCase() +"\n";            
        }    
        JOptionPane.showMessageDialog(null, c);
    }

    public void llenarTablaCargos(JTable tablaCargos) {
        tablaCargos.setDefaultRenderer(Object.class, new IconCellRenderer());
        DefaultTableModel modeloT = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaCargos.setModel(modeloT);
        tablaCargos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaCargos.getTableHeader().setOpaque(false);
        tablaCargos.getTableHeader().setBackground(new Color (204, 0, 0));
        tablaCargos.getTableHeader().setForeground(new Color(255,255,255));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tablaCargos.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
                
        modeloT.addColumn("DEPENDENCIA");
        modeloT.addColumn("CARGOS");
        modeloT.addColumn("EDITAR");
        modeloT.addColumn("");
        
        TableColumnModel columnModel = tablaCargos.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(110);
        columnModel.getColumn(1).setPreferredWidth(160);
        columnModel.getColumn(2).setPreferredWidth(40);  
        columnModel.getColumn(3).setMaxWidth(0);
        columnModel.getColumn(3).setMinWidth(0);
        columnModel.getColumn(3).setPreferredWidth(0);
        
                        
        if(modAjustes.listCargos()!= null){
            List<Cargo> cargos = new ArrayList<Cargo>();
            cargos =  modAjustes.listCargos();
            Object[] columna = new Object[5];
            JLabel label = new JLabel(new ImageIcon(getClass().getResource("/img/editar.png")));
           
            for(Cargo cargo : cargos){
                columna[0] = cargo.getDependencia().toUpperCase();
                columna[1] = cargo.getNombreCargo().toUpperCase();
                columna[2] = label;
                columna[3] = cargo.getIdCargo();                  
                modeloT.addRow(columna);                
            }
        }
    }
    
    
    public void llenarDependencias(JComboBox dependencias){
        DefaultComboBoxModel modelCargo = new DefaultComboBoxModel(modAjustes.listarDependencias());
        dependencias.setModel(modelCargo);        
    }
    
    public void llenarDependenciasEdit(JComboBox dependencias, Cargo c){
        DefaultComboBoxModel modelCargo = new DefaultComboBoxModel(modAjustes.listDependenciasEdit(c));
        dependencias.setModel(modelCargo);        
    }

    public void registrarCargo(diaNewCargos dia) {
        Dependencia dep = (Dependencia) dia.cbDependencia.getSelectedItem();
        if(dia.txtCargo.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el nombre del cargo.", "Importante", JOptionPane.WARNING_MESSAGE);
            dia.txtCargo.requestFocus();
        }else if(dep.getId_dependencia().equals("0")){
            JOptionPane.showMessageDialog(null, "Seleccione una dependencia.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else{
            Cargo c = new Cargo();
            c.setNombreCargo(dia.txtCargo.getText());
            c.setDependencia(dep.getId_dependencia());
            if(modAjustes.insertCargo(c)){
                registrarViaticosAsignados(dia);
                llenarTablaCostos(dia.tblCostos);
                llenarTablaCargos(dia.tablaCargos);
                dia.txtCargo.setText("");
                dia.cbDependencia.setSelectedIndex(0);
                JOptionPane.showMessageDialog(null, "Registro exitoso");
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio un error");
            }
        }
    }

    public void actualizarCargo(diaEditCargos dcar, diaNewCargos dia) {
       if(dcar.txtEdDescripcion.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el nombre del cargo.", "Importante", JOptionPane.WARNING_MESSAGE);
            dcar.txtEdDescripcion.requestFocus();
       }else{
           Dependencia dep = (Dependencia) dcar.cbDependencia.getSelectedItem();
           Cargo c = new Cargo();
           c.setIdCargo(dcar.txtId.getText());
           c.setNombreCargo(dcar.txtEdDescripcion.getText());
           c.setDependencia(dep.getId_dependencia());
           if(modAjustes.updateCargo(c)){
               if(modAjustes.deleteCostosViaticosCargo(dcar.txtId.getText())){
                   for(int i=0; i<dcar.tablaCostos.getRowCount(); i++){
                        if(IsSelected(i, 3, dcar.tablaCostos)){
                           int id_costo_viatico = Integer.parseInt(dcar.tablaCostos.getValueAt(i, 0).toString());   
                           modAjustes.insertViaticoAsignado(Integer.parseInt(dcar.txtId.getText()), id_costo_viatico);
                        }
                    }
                    llenarTablaCargos(dia.tablaCargos);
                    dcar.setVisible(false);
                    dcar.dispose(); 
                    JOptionPane.showMessageDialog(null, "Registro exitoso");
               }
               
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio un error");
            }
       }
    }
    
       
    public void llenarTablaCostos(JTable tblResultados){        
        DecimalFormat formato = new DecimalFormat("#,###");
        DefaultTableModel modeloTable = new DefaultTableModel();
        tblResultados.setModel(modeloTable);
        tblResultados.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblResultados.getTableHeader().setOpaque(false);
        tblResultados.getTableHeader().setBackground(new Color (204, 0, 0));
        tblResultados.getTableHeader().setForeground(new Color(255,255,255));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tblResultados.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        modeloTable.addColumn("");
        modeloTable.addColumn("NOMBRE VIÁTICO");
        modeloTable.addColumn("VALOR");
        modeloTable.addColumn("");
        
        TableColumnModel columnModel = tblResultados.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(30);
        columnModel.getColumn(3).setPreferredWidth(10);    
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);        
        columnModel.getColumn(2).setCellRenderer(tcr);

        addChecBox(3, tblResultados);

        List<CostoViatico> costoviaticos = new ArrayList<CostoViatico>();
        costoviaticos = modAjustes.listCostoViaticos();
        Object[] colum = new Object[4];
        
        for(CostoViatico costoviatico : costoviaticos){
            colum[0] = costoviatico.getId_viatico();
            colum[1] = costoviatico.getNombre().toUpperCase();
            colum[2] = formato.format(Integer.parseInt(costoviatico.getValor()));
            colum[3] = Boolean.FALSE;
            modeloTable.addRow(colum);
        }
    }   
    
    public void llenarTablaCostosEdit(JTable tblResultados, String id_cargo){        
        DecimalFormat formato = new DecimalFormat("#,###");
        DefaultTableModel modeloTable = new DefaultTableModel();
        tblResultados.setModel(modeloTable);
        tblResultados.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblResultados.getTableHeader().setOpaque(false);
        tblResultados.getTableHeader().setBackground(new Color (204, 0, 0));
        tblResultados.getTableHeader().setForeground(new Color(255,255,255));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tblResultados.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        modeloTable.addColumn("");
        modeloTable.addColumn("NOMBRE");
        modeloTable.addColumn("VALOR");
        modeloTable.addColumn("SELECCIONAR");
        
        TableColumnModel columnModel = tblResultados.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(40);
        columnModel.getColumn(3).setPreferredWidth(60);    
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);        
        columnModel.getColumn(2).setCellRenderer(tcr);

        addChecBox(3, tblResultados);
        List<CostoViatico> costoviaticos = new ArrayList<CostoViatico>();
        costoviaticos = modAjustes.listCostoViaticos();
        Object[] colum = new Object[4];
       
        for(CostoViatico costoviatico : costoviaticos){
            colum[0] = costoviatico.getId_viatico();
            colum[1] = costoviatico.getNombre().toUpperCase();
            colum[2] = formato.format(Integer.parseInt(costoviatico.getValor()));
            if(listaViaticosAsignados(costoviatico.getId_viatico(), id_cargo)){
                colum[3] = Boolean.TRUE;
            }else{
                colum[3] = Boolean.FALSE;
            }            
            modeloTable.addRow(colum);
        }
    } 
    
    public boolean listaViaticosAsignados(String v, String id_cargo){
        return modAjustes.verificarCostoViaticoCargo(v, id_cargo);
    }
    
    
    public void addChecBox(int columna, JTable tabla){
        TableColumn tc = tabla.getColumnModel().getColumn(columna);
        tc.setCellEditor(tabla.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(tabla.getDefaultRenderer(Boolean.class));
    }
    
    
    public void registrarViaticosAsignados(diaNewCargos dia){
        int id = modAjustes.idCargo();
                
        for(int i=0; i<dia.tblCostos.getRowCount(); i++){
            if(IsSelected(i, 3, dia.tblCostos)){
               int id_costo_viatico = Integer.parseInt(dia.tblCostos.getValueAt(i, 0).toString());   
               modAjustes.insertViaticoAsignado(id, id_costo_viatico);
            }
        }
        
    }
    
   
    
    public boolean IsSelected(int row, int column, JTable tabla){
        Boolean checked = false;
        checked = Boolean.valueOf(tabla.getValueAt(row,column).toString());
        return checked;        
    }
    
}
