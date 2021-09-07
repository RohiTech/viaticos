/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Autorizacion;
import Modelo.ConsultaEmpleados;
import Modelo.CostoViatico;
import Modelo.Detalle_viatico;
import Modelo.Municipio;
import Modelo.Empleado;
import Modelo.Viatico;
import dialogs.diaEditViatico;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import paneles.IconCellRenderer;
import paneles.pnlProgramacion;

/**
 *
 * @author AUXINVTIC
 */
public class CtrlProgramacion{
  
    ConsultaEmpleados modelo = new ConsultaEmpleados();
    pnlProgramacion res = new pnlProgramacion();    
    DefaultTableModel modeloTable;
    String id_usuario;
    String cargo;
    public CtrlProgramacion(pnlProgramacion res, ConsultaEmpleados modTecnico, String id_usuario){        
        this.res = res;
        this.modelo = modTecnico;
        this.id_usuario = id_usuario;
        res.txtIdUsuario.setText(id_usuario);
        res.txtIdUsuario.setVisible(false);
        llenarDependencias();
        
    }
    public CtrlProgramacion(pnlProgramacion res, ConsultaEmpleados modTecnico, String id_usuario, String cargo){        
        this.res = res;
        this.modelo = modTecnico;
        this.id_usuario = id_usuario;
        this.cargo = cargo;
        res.txtIdUsuario.setText(id_usuario);
        res.txtIdUsuario.setVisible(false);
        res.txtCargo.setText(cargo);
        res.txtCargo.setVisible(false);
        llenarDependencias();
        
    }

    public CtrlProgramacion(){
        
    }
         
    public void llenarTablaTecnicos(int numCol, int ColumBoolean, JTable tblResultados){
        if(modelo.listTecnicos() == null){
            JOptionPane.showMessageDialog(null, "No se encontraron datos");
        }else{
            modeloTable = new DefaultTableModel();
            tblResultados.setModel(modeloTable);
            tblResultados.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
            tblResultados.getTableHeader().setOpaque(false);
            tblResultados.getTableHeader().setBackground(new Color (204, 0, 0));
            tblResultados.getTableHeader().setForeground(new Color(255,255,255));

            modeloTable.addColumn("IDENTIFICACION");
            modeloTable.addColumn("NOMBRE");
            modeloTable.addColumn("SELECCIONAR");
          
            addChecBox(2, tblResultados);
            
            Object[] colum = new Object[5];
            int numRegistros = modelo.listTecnicos().size();
            
            for(int i=0; i<numRegistros; i++){
                
                colum[0] = modelo.listTecnicos().get(i).getId();
                colum[1] = modelo.listTecnicos().get(i).getApellido_uno()+" "+modelo.listTecnicos().get(i).getApellido_dos()+" "+modelo.listTecnicos().get(i).getNombre_uno()+" "+modelo.listTecnicos().get(i).getNombre_dos();
                colum[2] = Boolean.FALSE;
                modeloTable.addRow(colum);
                //JOptionPane.showMessageDialog(null, modelo.listTecnicos().get(i).getNombre());
            }             
        }
    }     
    
    
    public void addChecBox(int columna, JTable tabla){
        TableColumn tc = tabla.getColumnModel().getColumn(columna);
        tc.setCellEditor(tabla.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(tabla.getDefaultRenderer(Boolean.class));
    }
  
    public void llenarDependencias(){
        DefaultComboBoxModel modelEst = new DefaultComboBoxModel(modelo.listaDependenciasCargo(this.cargo));
        res.cbDependencia.setModel(modelEst); 
        
    }
    
    public void llenarEmpleados(JComboBox empleados, String id_dependencia){
        DefaultComboBoxModel model = new DefaultComboBoxModel(modelo.listarCargoDep(id_dependencia));
        empleados.setModel(model);         
    }
    
    public void llenarMunicipios(JComboBox combo){
        DefaultComboBoxModel modelEst = new DefaultComboBoxModel(modelo.listarMunicipios());
        combo.setModel(modelEst); 
    }
    
    public void agregarChecks(pnlProgramacion pnl, String id_cargo, String id_empleado){
        modeloTable = new DefaultTableModel();
        pnl.tablaCostoViaticos.setModel(modeloTable);
        pnl.tablaCostoViaticos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        pnl.tablaCostoViaticos.getTableHeader().setOpaque(false);
        pnl.tablaCostoViaticos.getTableHeader().setBackground(new Color (204, 0, 0));
        pnl.tablaCostoViaticos.getTableHeader().setForeground(new Color(255,255,255));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) pnl.tablaCostoViaticos.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        modeloTable.addColumn("");
        modeloTable.addColumn("VIÁTICO");
        modeloTable.addColumn("VALOR");
        modeloTable.addColumn("SELECCIONAR");
        
        TableColumnModel columnModel = pnl.tablaCostoViaticos.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(1).setPreferredWidth(160);
        columnModel.getColumn(2).setPreferredWidth(80);
        columnModel.getColumn(3).setPreferredWidth(80);  
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);        
        columnModel.getColumn(2).setCellRenderer(tcr);        

        addChecBox(3, pnl.tablaCostoViaticos);
        List<CostoViatico> costoviaticos = new ArrayList<CostoViatico>();
        costoviaticos = modelo.listarCostosViaticos(id_cargo);
        Object[] colum = new Object[4];
                
        //DecimalFormat formato = new DecimalFormat("#,###");
        for(CostoViatico costoviatico : costoviaticos){
            colum[0] = costoviatico.getId_viatico();
            colum[1] = costoviatico.getNombre().toUpperCase();
            colum[2] = costoviatico.getValor();
            colum[3] = Boolean.FALSE;
            modeloTable.addRow(colum);
        }        
    }
    
    public void agregarChecksModificar(diaEditViatico pnl, String id_cargo, String id_empleado){
        modeloTable = new DefaultTableModel();
        pnl.tablaCostoViaticos.setModel(modeloTable);
        pnl.tablaCostoViaticos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        pnl.tablaCostoViaticos.getTableHeader().setOpaque(false);
        pnl.tablaCostoViaticos.getTableHeader().setBackground(new Color (204, 0, 0));
        pnl.tablaCostoViaticos.getTableHeader().setForeground(new Color(255,255,255));

        modeloTable.addColumn("");
        modeloTable.addColumn("VIÁTICO");
        modeloTable.addColumn("VALOR");
        modeloTable.addColumn("SELECCIONAR");
        
        TableColumnModel columnModel = pnl.tablaCostoViaticos.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(1).setPreferredWidth(160);
        columnModel.getColumn(2).setPreferredWidth(80);
        columnModel.getColumn(3).setPreferredWidth(80);  
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);        
        columnModel.getColumn(2).setCellRenderer(tcr);        

        addChecBox(3, pnl.tablaCostoViaticos);
        List<CostoViatico> costoviaticos = new ArrayList<CostoViatico>();
        costoviaticos = modelo.listarCostosViaticos(id_cargo);
        Object[] colum = new Object[4];
                
        //DecimalFormat formato = new DecimalFormat("#,###");
        for(CostoViatico costoviatico : costoviaticos){
            colum[0] = costoviatico.getId_viatico();
            colum[1] = costoviatico.getNombre().toUpperCase();
            colum[2] = costoviatico.getValor();
            colum[3] = Boolean.FALSE;
            modeloTable.addRow(colum);
        }        
    }
    

    public void agregarChecksMunicipio(pnlProgramacion pnl, String id_municipio){
        modeloTable = new DefaultTableModel();
        pnl.tablaCostoViaticos_dos.setModel(modeloTable);
        
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        JTableHeader th; 
        th = pnl.tablaCostoViaticos_dos.getTableHeader(); 
        th.setVisible(false);
        
        TableColumnModel columnModel = pnl.tablaCostoViaticos_dos.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(1).setPreferredWidth(160);
        columnModel.getColumn(2).setPreferredWidth(80);
        columnModel.getColumn(3).setPreferredWidth(80);  
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);        
        columnModel.getColumn(2).setCellRenderer(tcr);   
        addChecBox(3, pnl.tablaCostoViaticos_dos);
        
        Object[] colum = new Object[4];       
        int numRegistrosAuto = modelo.listarCostosMunicipio(id_municipio).size();
        if(numRegistrosAuto > 0){
            colum[0] = "5";
            colum[1] = "ALIMENTACIÓN";
            colum[2] = modelo.listarCostosMunicipio(id_municipio).get(0).getValor_alimentacion();
            colum[3] = Boolean.FALSE;
            modeloTable.addRow(colum);
            
            colum[0] = "6";
            colum[1] = "HOSPEDAJE";
            colum[2] = modelo.listarCostosMunicipio(id_municipio).get(0).getValor_hospedaje();
            colum[3] = Boolean.FALSE;
            modeloTable.addRow(colum);            
        }        
        
    }
    
    public void agregarChecksMunicipioModificar(diaEditViatico pnl, String id_municipio){
        modeloTable = new DefaultTableModel();
        pnl.tablaCostoViaticos_dos.setModel(modeloTable);
        
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        JTableHeader th; 
        th = pnl.tablaCostoViaticos_dos.getTableHeader(); 
        th.setVisible(false);
        
        TableColumnModel columnModel = pnl.tablaCostoViaticos_dos.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(1).setPreferredWidth(160);
        columnModel.getColumn(2).setPreferredWidth(80);
        columnModel.getColumn(3).setPreferredWidth(80);  
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);        
        columnModel.getColumn(2).setCellRenderer(tcr);   
        addChecBox(3, pnl.tablaCostoViaticos_dos);
        
        Object[] colum = new Object[4];       
        int numRegistrosAuto = modelo.listarCostosMunicipio(id_municipio).size();
        if(numRegistrosAuto > 0){
            colum[0] = "5";
            colum[1] = "ALIMENTACIÓN";
            colum[2] = modelo.listarCostosMunicipio(id_municipio).get(0).getValor_alimentacion();
            colum[3] = Boolean.FALSE;
            modeloTable.addRow(colum);
            
            colum[0] = "6";
            colum[1] = "HOSPEDAJE";
            colum[2] = modelo.listarCostosMunicipio(id_municipio).get(0).getValor_hospedaje();
            colum[3] = Boolean.FALSE;
            modeloTable.addRow(colum);            
        }        
        
    }
    
    public void agregarChecksOtros(pnlProgramacion pnl, String id_empleado){
        modeloTable = new DefaultTableModel();
        pnl.tablaCostoViaticos_otros.setModel(modeloTable);
        
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        JTableHeader th; 
        th = pnl.tablaCostoViaticos_otros.getTableHeader(); 
        th.setVisible(false);
        
        TableColumnModel columnModel = pnl.tablaCostoViaticos_otros.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(1).setPreferredWidth(160);
        columnModel.getColumn(2).setPreferredWidth(80);
        columnModel.getColumn(3).setPreferredWidth(80);  
        columnModel.getColumn(4).setMaxWidth(0);
        columnModel.getColumn(4).setMinWidth(0);
        columnModel.getColumn(4).setPreferredWidth(0);
        columnModel.getColumn(5).setMaxWidth(0);
        columnModel.getColumn(5).setMinWidth(0);
        columnModel.getColumn(5).setPreferredWidth(0);
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT); 
        DefaultTableCellRenderer cent = new DefaultTableCellRenderer();
        cent.setHorizontalAlignment(SwingConstants.CENTER);
        
        columnModel.getColumn(2).setCellRenderer(tcr); 
        columnModel.getColumn(3).setCellRenderer(cent);
        addChecBox(5, pnl.tablaCostoViaticos_otros);
        
        List<Autorizacion> otrosviaticos = new ArrayList<Autorizacion>();
        String estado = "1";
        otrosviaticos = modelo.obtenerAutorizacion(id_empleado, estado);
        int numregistros = modelo.obtenerAutorizacion(id_empleado, estado).size();
        Object[] colum = new Object[6];       
        
        if(numregistros > 0){
            pnl.ScrollTabla.setVisible(true);
            pnl.lblOtros.setVisible(true);
            for(Autorizacion otroviatico : otrosviaticos){
                colum[0] = "4";
                colum[1] = otroviatico.getJustificacion().toUpperCase();
                colum[2] = otroviatico.getValor();
                colum[3] = "Autorizado";
                colum[4] = otroviatico.getId_autorizacion();
                colum[5] = Boolean.TRUE;
                modeloTable.addRow(colum);
            }            
        }else{
            pnl.lblOtros.setVisible(false);
            pnl.ScrollTabla.setVisible(false);
        }
    }
    
    
    public void registrarViatico(pnlProgramacion pnl){ 
        Empleado tecnico = (Empleado) pnl.cbEmpleado.getSelectedItem();
        Municipio munici = (Municipio) pnl.cbDestino.getSelectedItem();
                        
        Date fecha = new Date();            
        String strDateFormat = "yyyy-MM-dd"; // El formato de fecha está especificado  
        SimpleDateFormat f = new SimpleDateFormat(strDateFormat);
        String feActual = f.format(fecha);

        Date hora = new Date();
        String strHoraFormat = "hh:mm:ss"; // El formato de fecha está especificado  
        SimpleDateFormat h = new SimpleDateFormat(strHoraFormat);
        String hoActual = h.format(hora);

        Date fechaInicial = pnl.jdFechaSalida.getDate();
        Date fechaFinal = pnl.jdFechaRegreso.getDate();

        SimpleDateFormat fi = new SimpleDateFormat(strDateFormat);
        String f_inicial = fi.format(fechaInicial);

        SimpleDateFormat ff = new SimpleDateFormat(strDateFormat);
        String f_final = ff.format(fechaFinal);
        
        int dias=(int) ((fechaFinal.getTime()-fechaInicial.getTime())/86400000);       
        Viatico viatico = new Viatico();
        viatico.setId_tecnico(tecnico.getId());
        viatico.setDestino(munici.getCodigo());
        viatico.setObservaciones(pnl.txtObservaciones.getText());        
        viatico.setFechaSalida(f_inicial);
        viatico.setFechaRegreso(f_final);
        viatico.setFechaProgramada(feActual);
        viatico.setHoraProgramada(hoActual);
        if(tecnico.getId_municipio().equals("01")){
            viatico.setEstado("1");
        }else{
            viatico.setEstado("5");
        }
        
        viatico.setUsuario(pnl.txtIdUsuario.getText());        
        //JOptionPane.showMessageDialog(null, dias);

        if(modelo.registroViaticos(viatico)){
            int id_viatico = modelo.ultimo_id_viatico();            
            for(int i=0; i<pnl.tablaCostoViaticos.getRowCount(); i++){
                if(IsSelected(i, 3, pnl.tablaCostoViaticos)){
                    Detalle_viatico det = new Detalle_viatico();
                    det.setDetalle(pnl.tablaCostoViaticos.getValueAt(i, 1).toString());
                    det.setValor(pnl.tablaCostoViaticos.getValueAt(i, 2).toString());
                    if((pnl.tablaCostoViaticos.getValueAt(i, 0).toString()).equals("1")){
                        if(pnl.checkDiaNoche.isSelected()){
                            det.setPivot(dias+1);
                        }else{
                            det.setPivot(dias);
                        }
                    }else if((pnl.tablaCostoViaticos.getValueAt(i, 0).toString()).equals("7")){
                        if(pnl.checkDiaNoche.isSelected()){
                            det.setPivot(dias+1);
                        }else{
                            det.setPivot(dias);
                        }
                    }else{
                        det.setPivot(1);
                    }                    
                    det.setId_viatico(id_viatico);
                    det.setTipo_viatico(pnl.tablaCostoViaticos.getValueAt(i, 0).toString());
                    modelo.registroDetalleViatico(det);
                }
            }
            for(int i=0; i<pnl.tablaCostoViaticos_dos.getRowCount(); i++){
                if(IsSelected(i, 3, pnl.tablaCostoViaticos_dos)){
                    Detalle_viatico det = new Detalle_viatico();
                    det.setDetalle(pnl.tablaCostoViaticos_dos.getValueAt(i, 1).toString());
                    det.setValor(pnl.tablaCostoViaticos_dos.getValueAt(i, 2).toString());
                    if(pnl.tablaCostoViaticos_dos.getValueAt(i, 1).toString().equals("HOSPEDAJE")){
                        if(pnl.checkDiaNoche.isSelected()){
                            det.setPivot(dias+1);
                        }else{
                            det.setPivot(dias);
                        }
                        
                    }else{
                        det.setPivot(dias+1);
                    }
                    
                    det.setId_viatico(id_viatico);
                    det.setTipo_viatico(pnl.tablaCostoViaticos_dos.getValueAt(i, 0).toString());
                    modelo.registroDetalleViatico(det);
                }
            }
            for(int i=0; i<pnl.tablaCostoViaticos_otros.getRowCount(); i++){
                if(IsSelected(i, 5, pnl.tablaCostoViaticos_otros)){
                    Detalle_viatico det = new Detalle_viatico();
                    det.setDetalle(pnl.tablaCostoViaticos_otros.getValueAt(i, 1).toString());
                    det.setValor(pnl.tablaCostoViaticos_otros.getValueAt(i, 2).toString());
                    det.setId_viatico(id_viatico);
                    det.setPivot(1);
                    det.setTipo_viatico(pnl.tablaCostoViaticos_otros.getValueAt(i, 0).toString());
                    modelo.registroDetalleViatico(det);
                    modelo.updateViaticoAutorizado(Integer.parseInt(pnl.tablaCostoViaticos_otros.getValueAt(i, 4).toString()));
                }
            }
            pnl.cbDependencia.setSelectedIndex(0);
            pnl.cbEmpleado.setSelectedIndex(0);
            pnl.cbDestino.setSelectedIndex(0);
            pnl.jdFechaSalida.setDate(null);
            pnl.jdFechaRegreso.setDate(null);
            pnl.txtObservaciones.setText("");
            pnl.checkDiaNoche.setSelected(false);
            
            DefaultTableModel modeloT = (DefaultTableModel) pnl.tablaCostoViaticos_dos.getModel();
            while(modeloT.getRowCount()>0)modeloT.removeRow(0);
            DefaultTableModel modeloTo = (DefaultTableModel) pnl.tablaCostoViaticos_otros.getModel();
            while(modeloTo.getRowCount()>0)modeloTo.removeRow(0);
            try {
                DefaultTableModel modelo=(DefaultTableModel) pnl.tablaCostoViaticos.getModel();
                int filas=pnl.tablaCostoViaticos.getRowCount();
                for (int i = 0;filas>i; i++) {
                    modelo.removeRow(0);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
            }
            JOptionPane.showMessageDialog(null, "Registro exitoso.");
        }else{
            JOptionPane.showMessageDialog(null, "Ocurrio un error.");
        } 
        
        
    }
    
    public void actualizarViaticoRegistrado(diaEditViatico dia, pnlProgramacion pnl){
        Municipio munici = (Municipio) dia.cbDestino.getSelectedItem();
        String strDateFormat = "yyyy-MM-dd"; 
        Date fechaInicial = dia.fechaSalida.getDate();
        Date fechaFinal = dia.fechaRegreso.getDate();

        SimpleDateFormat fi = new SimpleDateFormat(strDateFormat);
        String f_inicial = fi.format(fechaInicial);
        String f_final = fi.format(fechaFinal);
        int dias=(int) ((fechaFinal.getTime()-fechaInicial.getTime())/86400000);
        Viatico viatico = new Viatico();
        viatico.setId_viatico(Integer.parseInt(dia.txtIdViatico.getText()));
        viatico.setId_destino(munici.getCodigo());
        viatico.setObservaciones(dia.txtObservaciones.getText());
        viatico.setFechaSalida(f_inicial);
        viatico.setFechaRegreso(f_final);
        
        if(modelo.updateViaticoRegistrado(viatico)){
            //borrar detalle viaticos
            modelo.deleteDetalleViatico(Integer.parseInt(dia.txtIdViatico.getText()));
            for(int i=0; i<dia.tablaCostoViaticos.getRowCount(); i++){
                if(IsSelected(i, 3, dia.tablaCostoViaticos)){
                    Detalle_viatico det = new Detalle_viatico();
                    det.setDetalle(dia.tablaCostoViaticos.getValueAt(i, 1).toString());
                    det.setValor(dia.tablaCostoViaticos.getValueAt(i, 2).toString());
                    if((dia.tablaCostoViaticos.getValueAt(i, 0).toString()).equals("1")){
                        det.setPivot(dias);
                    }else if((dia.tablaCostoViaticos.getValueAt(i, 0).toString()).equals("7")){
                        if(dia.checkDiaNoche.isSelected()){
                            det.setPivot(dias+1);
                        }else{
                            det.setPivot(dias);
                        }
                    }else{
                        det.setPivot(1);
                    }                    
                    det.setId_viatico(Integer.parseInt(dia.txtIdViatico.getText()));
                    det.setTipo_viatico(dia.tablaCostoViaticos.getValueAt(i, 0).toString());
                    modelo.registroDetalleViatico(det);
                }
            }
            for(int i=0; i<dia.tablaCostoViaticos_dos.getRowCount(); i++){
                if(IsSelected(i, 3, dia.tablaCostoViaticos_dos)){
                    Detalle_viatico det = new Detalle_viatico();
                    det.setDetalle(dia.tablaCostoViaticos_dos.getValueAt(i, 1).toString());
                    det.setValor(dia.tablaCostoViaticos_dos.getValueAt(i, 2).toString());
                    if(dia.tablaCostoViaticos_dos.getValueAt(i, 1).toString().equals("HOSPEDAJE")){
                        if(dia.checkDiaNoche.isSelected()){
                            det.setPivot(dias+1);
                        }else{
                            det.setPivot(dias);
                        }
                    }else{
                        det.setPivot(dias+1);
                    }
                    det.setId_viatico(Integer.parseInt(dia.txtIdViatico.getText()));
                    det.setTipo_viatico(dia.tablaCostoViaticos_dos.getValueAt(i, 0).toString());
                    modelo.registroDetalleViatico(det);
                }
            }
            listarProgramaciones(pnl);
            dia.setVisible(false);
            dia.dispose();
            JOptionPane.showMessageDialog(null, "Acturalización de información exitosa.");
        }else{
            JOptionPane.showMessageDialog(null, "Ocurrio un error.");
        }
        
    }
   
    public boolean IsSelected(int row, int column, JTable tabla){
        Boolean checked = false;
        checked = Boolean.valueOf(tabla.getValueAt(row,column).toString());
        return checked;        
    }
    
    //SE MUESTRAN TODAS LAS PROGRAMACIONES DE TODAS LAS DEPENDENCIAS
    public void listarProgramaciones(pnlProgramacion pnl){
        DecimalFormat formato = new DecimalFormat("#,###");
        pnl.tblProgramaciones.setDefaultRenderer(Object.class,new IconCellRenderer());
        DefaultTableModel modeloT = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };        
       
        pnl.tblProgramaciones.setModel(modeloT);
        pnl.tblProgramaciones.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        pnl.tblProgramaciones.getTableHeader().setOpaque(false);
        pnl.tblProgramaciones.getTableHeader().setBackground(new Color (204, 0, 0));
        pnl.tblProgramaciones.getTableHeader().setForeground(new Color(255,255,255));
        
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) pnl.tblProgramaciones.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);   
//        
//        pnl.tblProgramaciones.getTableHeader().getColumnModel().getColumn(9).setCellRenderer(renderer);
          
        modeloT.addColumn("#");
        modeloT.addColumn("EMPLEADO");
        modeloT.addColumn("DESTINO");
        modeloT.addColumn("FECHA SALIDA");
        modeloT.addColumn("FECHA REGRESO");
        modeloT.addColumn("FECHA PROGRAMACIÓN");
        modeloT.addColumn("PROGRAMADO POR");
        modeloT.addColumn("OBSERVACIONES");
        modeloT.addColumn("VALOR");        
        modeloT.addColumn("EDITAR");
        modeloT.addColumn("ANULAR");
        modeloT.addColumn("");
        modeloT.addColumn("");
        
        TableColumnModel columnModel = pnl.tblProgramaciones.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(90);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(150);
        columnModel.getColumn(7).setPreferredWidth(180);
        columnModel.getColumn(8).setPreferredWidth(90);
        columnModel.getColumn(9).setPreferredWidth(100);
        columnModel.getColumn(10).setPreferredWidth(100);
        columnModel.getColumn(11).setMaxWidth(0);
        columnModel.getColumn(11).setMinWidth(0);
        columnModel.getColumn(11).setPreferredWidth(0);
        columnModel.getColumn(12).setMaxWidth(0);
        columnModel.getColumn(12).setMinWidth(0);
        columnModel.getColumn(12).setPreferredWidth(0);
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        
        DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
        derecha.setHorizontalAlignment(SwingConstants.RIGHT);
                        
        columnModel.getColumn(0).setCellRenderer(tcr);
        columnModel.getColumn(3).setCellRenderer(tcr);
        columnModel.getColumn(4).setCellRenderer(tcr);
        columnModel.getColumn(5).setCellRenderer(tcr);
        columnModel.getColumn(8).setCellRenderer(derecha); 
        
        List<Viatico> viaticos = new ArrayList<Viatico>();
        viaticos = modelo.listarViaticos();
        Object[] columna = new Object[13];
        int i = 1;
        JLabel label = new JLabel(new ImageIcon(getClass().getResource("/img/editar.png")));
        JLabel anular = new JLabel(new ImageIcon(getClass().getResource("/img/cancel.png")));
        SimpleDateFormat fe = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
        
        for(Viatico viatico : viaticos){
            String fechaEnt = viatico.getFechaProgramada();
            String fechaSal = viatico.getFechaSalida();
            String fechaReg = viatico.getFechaRegreso();
            Date fEnt = null;
            Date fSal = null;
            Date fReg = null;
            try {
                fEnt = fs.parse(fechaEnt);
                fSal = fs.parse(fechaSal);
                fReg = fs.parse(fechaReg);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }            
           
            String feSal = fe.format(fSal);
            String feReg = fe.format(fReg);
            String feEnt = fe.format(fEnt); 
            
            columna[0] = i;
            columna[1] = viatico.getTecnico().toUpperCase();
            columna[2] = viatico.getDestino().toUpperCase();
            columna[3] = feSal;
            columna[4] = feReg;
            columna[5] = feEnt;
            columna[6] = viatico.getUsuario().toUpperCase();
            columna[7] = viatico.getObservaciones();
            columna[8] = formato.format(viatico.getValor());            
            columna[9] = label;
            columna[10] = anular;
            columna[11] = viatico.getId_viatico(); 
            columna[12] = viatico.getId_tecnico(); 
            modeloT.addRow(columna); 
            
            i++;
        } 
    }
       
    
    //SE MUESTRAN SOLO PROGRAMACIONES DE LA DEPENDENCIA DE GERENCIA DE OPERACIONES
    public void listarProgramacionesDos(pnlProgramacion pnl){
        DecimalFormat formato = new DecimalFormat("#,###");
        pnl.tblProgramaciones.setDefaultRenderer(Object.class,new IconCellRenderer());
        DefaultTableModel modeloT = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };        
       
        pnl.tblProgramaciones.setModel(modeloT);
        pnl.tblProgramaciones.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        pnl.tblProgramaciones.getTableHeader().setOpaque(false);
        pnl.tblProgramaciones.getTableHeader().setBackground(new Color (204, 0, 0));
        pnl.tblProgramaciones.getTableHeader().setForeground(new Color(255,255,255));
        
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) pnl.tblProgramaciones.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER); 
                                
        modeloT.addColumn("#");
        modeloT.addColumn("EMPLEADO");
        modeloT.addColumn("DESTINO");
        modeloT.addColumn("FECHA SALIDA");
        modeloT.addColumn("FECHA REGRESO");
        modeloT.addColumn("FECHA PROGRAMACIÓN");
        modeloT.addColumn("PROGRAMADO POR");
        modeloT.addColumn("OBSERVACIONES");
        modeloT.addColumn("VALOR");        
        modeloT.addColumn("EDITAR");
        modeloT.addColumn("ANULAR");
        modeloT.addColumn("");
        modeloT.addColumn("");
        
        TableColumnModel columnModel = pnl.tblProgramaciones.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(90);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(150);
        columnModel.getColumn(7).setPreferredWidth(180);
        columnModel.getColumn(8).setPreferredWidth(90);
        columnModel.getColumn(9).setPreferredWidth(100);
        columnModel.getColumn(10).setPreferredWidth(100);
        columnModel.getColumn(11).setMaxWidth(0);
        columnModel.getColumn(11).setMinWidth(0);
        columnModel.getColumn(11).setPreferredWidth(0);
        columnModel.getColumn(12).setMaxWidth(0);
        columnModel.getColumn(12).setMinWidth(0);
        columnModel.getColumn(12).setPreferredWidth(0);
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        
        DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
        derecha.setHorizontalAlignment(SwingConstants.RIGHT);
        
        columnModel.getColumn(0).setCellRenderer(tcr);
        columnModel.getColumn(3).setCellRenderer(tcr);
        columnModel.getColumn(4).setCellRenderer(tcr);
        columnModel.getColumn(5).setCellRenderer(tcr);
        columnModel.getColumn(8).setCellRenderer(derecha); 
        
        List<Viatico> viaticos = new ArrayList<Viatico>();
        viaticos = modelo.listarViaticos();
        Object[] columna = new Object[13];
        int i = 1;
        JLabel label = new JLabel(new ImageIcon(getClass().getResource("/img/editar.png")));
        JLabel anular = new JLabel(new ImageIcon(getClass().getResource("/img/cancel.png")));
        SimpleDateFormat fe = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
        
        for(Viatico viatico : viaticos){
            String fechaEnt = viatico.getFechaProgramada();
            String fechaSal = viatico.getFechaSalida();
            String fechaReg = viatico.getFechaRegreso();
            Date fEnt = null;
            Date fSal = null;
            Date fReg = null;
            try {
                fEnt = fs.parse(fechaEnt);
                fSal = fs.parse(fechaSal);
                fReg = fs.parse(fechaReg);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }            
           
            String feSal = fe.format(fSal);
            String feReg = fe.format(fReg);
            String feEnt = fe.format(fEnt); 
            
            if(viatico.getDependenciaEmpleado().equals("2")){
                columna[0] = i;
                columna[1] = viatico.getTecnico().toUpperCase();
                columna[2] = viatico.getDestino().toUpperCase();
                columna[3] = feSal;
                columna[4] = feReg;
                columna[5] = feEnt;
                columna[6] = viatico.getUsuario().toUpperCase();
                columna[7] = viatico.getObservaciones();
                columna[8] = formato.format(viatico.getValor());                
                columna[9] = label;
                columna[10] = anular;
                columna[11] = viatico.getId_viatico(); 
                columna[12] = viatico.getId_tecnico(); 
                modeloT.addRow(columna);
                i++;
            }
        } 
    }
    
    //SE MUESTRAN SOLO PROGRAMACIONES DE LAS DEPENDENCIAS ADMINISTRATIVAS
    public void listarProgramacionesTres(pnlProgramacion pnl){
        DecimalFormat formato = new DecimalFormat("#,###");
        pnl.tblProgramaciones.setDefaultRenderer(Object.class,new IconCellRenderer());
        DefaultTableModel modeloT = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };        
       
        pnl.tblProgramaciones.setModel(modeloT);
        pnl.tblProgramaciones.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        pnl.tblProgramaciones.getTableHeader().setOpaque(false);
        pnl.tblProgramaciones.getTableHeader().setBackground(new Color (204, 0, 0));
        pnl.tblProgramaciones.getTableHeader().setForeground(new Color(255,255,255));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) pnl.tblProgramaciones.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER); 
                                
        modeloT.addColumn("#");
        modeloT.addColumn("EMPLEADO");
        modeloT.addColumn("DESTINO");
        modeloT.addColumn("FECHA SALIDA");
        modeloT.addColumn("FECHA REGRESO");
        modeloT.addColumn("FECHA PROGRAMACIÓN");
        modeloT.addColumn("PROGRAMADO POR");
        modeloT.addColumn("OBSERVACIONES");
        modeloT.addColumn("VALOR");        
        modeloT.addColumn("EDITAR");
        modeloT.addColumn("ANULAR");
        modeloT.addColumn("");
        modeloT.addColumn("");
        
        TableColumnModel columnModel = pnl.tblProgramaciones.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(90);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(150);
        columnModel.getColumn(7).setPreferredWidth(180);
        columnModel.getColumn(8).setPreferredWidth(90);
        columnModel.getColumn(9).setPreferredWidth(100);
        columnModel.getColumn(10).setPreferredWidth(100);
        columnModel.getColumn(11).setMaxWidth(0);
        columnModel.getColumn(11).setMinWidth(0);
        columnModel.getColumn(11).setPreferredWidth(0);
        columnModel.getColumn(12).setMaxWidth(0);
        columnModel.getColumn(12).setMinWidth(0);
        columnModel.getColumn(12).setPreferredWidth(0);
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        
        DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
        derecha.setHorizontalAlignment(SwingConstants.RIGHT);
        
        columnModel.getColumn(0).setCellRenderer(tcr);
        columnModel.getColumn(3).setCellRenderer(tcr);
        columnModel.getColumn(4).setCellRenderer(tcr);
        columnModel.getColumn(5).setCellRenderer(tcr);
        columnModel.getColumn(8).setCellRenderer(derecha); 
        
        List<Viatico> viaticos = new ArrayList<Viatico>();
        viaticos = modelo.listarViaticos();
        Object[] columna = new Object[13];
        int i = 1;
        JLabel label = new JLabel(new ImageIcon(getClass().getResource("/img/editar.png")));
        JLabel anular = new JLabel(new ImageIcon(getClass().getResource("/img/cancel.png")));
        SimpleDateFormat fe = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
        
        for(Viatico viatico : viaticos){
            String fechaEnt = viatico.getFechaProgramada();
            String fechaSal = viatico.getFechaSalida();
            String fechaReg = viatico.getFechaRegreso();
            Date fEnt = null;
            Date fSal = null;
            Date fReg = null;
            try {
                fEnt = fs.parse(fechaEnt);
                fSal = fs.parse(fechaSal);
                fReg = fs.parse(fechaReg);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }            
           
            String feSal = fe.format(fSal);
            String feReg = fe.format(fReg);
            String feEnt = fe.format(fEnt); 
            
            if(!viatico.getDependenciaEmpleado().equals("2")){
                columna[0] = i;
                columna[1] = viatico.getTecnico().toUpperCase();
                columna[2] = viatico.getDestino().toUpperCase();
                columna[3] = feSal;
                columna[4] = feReg;
                columna[5] = feEnt;
                columna[6] = viatico.getUsuario().toUpperCase();
                columna[7] = viatico.getObservaciones();
                columna[8] = formato.format(viatico.getValor());                
                columna[9] = label;
                columna[10] = anular;
                columna[11] = viatico.getId_viatico(); 
                columna[12] = viatico.getId_tecnico(); 
                modeloT.addRow(columna);
                i++;
            }
        } 
    }
    
    //SE MUESTRAN SOLO PROGRAMACIONES DE LOS TECNICOS DE MUNICIPIOS
    public void listarProgramacionesTecMunicipios(pnlProgramacion pnl){
        DecimalFormat formato = new DecimalFormat("#,###");
        pnl.tblProgramacionesMun.setDefaultRenderer(Object.class,new IconCellRenderer());
        DefaultTableModel modeloT = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };        
       
        pnl.tblProgramacionesMun.setModel(modeloT);
        pnl.tblProgramacionesMun.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        pnl.tblProgramacionesMun.getTableHeader().setOpaque(false);
        pnl.tblProgramacionesMun.getTableHeader().setBackground(new Color (204, 0, 0));
        pnl.tblProgramacionesMun.getTableHeader().setForeground(new Color(255,255,255));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) pnl.tblProgramacionesMun.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER); 
                                
        modeloT.addColumn("#");
        modeloT.addColumn("EMPLEADO");
        modeloT.addColumn("DESTINO");
        modeloT.addColumn("FECHA SALIDA");
        modeloT.addColumn("FECHA REGRESO");
        modeloT.addColumn("FECHA PROGRAMACIÓN");
        modeloT.addColumn("PROGRAMADO POR");
        modeloT.addColumn("OBSERVACIONES");
        modeloT.addColumn("VALOR");        
        modeloT.addColumn("EDITAR");
        modeloT.addColumn("ANULAR");
        modeloT.addColumn("");
        modeloT.addColumn("");
        
        TableColumnModel columnModel = pnl.tblProgramacionesMun.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(90);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(150);
        columnModel.getColumn(7).setPreferredWidth(180);
        columnModel.getColumn(8).setPreferredWidth(90);
        columnModel.getColumn(9).setPreferredWidth(100);
        columnModel.getColumn(10).setPreferredWidth(100);
        columnModel.getColumn(11).setMaxWidth(0);
        columnModel.getColumn(11).setMinWidth(0);
        columnModel.getColumn(11).setPreferredWidth(0);
        columnModel.getColumn(12).setMaxWidth(0);
        columnModel.getColumn(12).setMinWidth(0);
        columnModel.getColumn(12).setPreferredWidth(0);
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        
        DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
        derecha.setHorizontalAlignment(SwingConstants.RIGHT);
        
        columnModel.getColumn(0).setCellRenderer(tcr);
        columnModel.getColumn(3).setCellRenderer(tcr);
        columnModel.getColumn(4).setCellRenderer(tcr);
        columnModel.getColumn(5).setCellRenderer(tcr);
        columnModel.getColumn(8).setCellRenderer(derecha); 
        
        List<Viatico> viaticos = new ArrayList<Viatico>();
        viaticos = modelo.listarViaticosMunicipios();
        Object[] columna = new Object[13];
        int i = 1;
        JLabel label = new JLabel(new ImageIcon(getClass().getResource("/img/editar.png")));
        JLabel anular = new JLabel(new ImageIcon(getClass().getResource("/img/cancel.png")));
        SimpleDateFormat fe = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
        
        for(Viatico viatico : viaticos){
            String fechaEnt = viatico.getFechaProgramada();
            String fechaSal = viatico.getFechaSalida();
            String fechaReg = viatico.getFechaRegreso();
            Date fEnt = null;
            Date fSal = null;
            Date fReg = null;
            try {
                fEnt = fs.parse(fechaEnt);
                fSal = fs.parse(fechaSal);
                fReg = fs.parse(fechaReg);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }            
           
            String feSal = fe.format(fSal);
            String feReg = fe.format(fReg);
            String feEnt = fe.format(fEnt);             
            
            columna[0] = i;
            columna[1] = viatico.getTecnico().toUpperCase();
            columna[2] = viatico.getDestino().toUpperCase();
            columna[3] = feSal;
            columna[4] = feReg;
            columna[5] = feEnt;
            columna[6] = viatico.getUsuario().toUpperCase();
            columna[7] = viatico.getObservaciones();
            columna[8] = formato.format(viatico.getValor());                
            columna[9] = label;
            columna[10] = anular;
            columna[11] = viatico.getId_viatico(); 
            columna[12] = viatico.getId_tecnico(); 
            modeloT.addRow(columna);
            i++;
            
        } 
    }
    
    //VER INFORMACION DEL VIATICO PROGRAMADO AL EMPLEADO
    public void verInfoViatico(diaEditViatico dia, String id_viatico){
        Viatico viatico = modelo.viaticoSeleccionado(id_viatico);
        SimpleDateFormat fe = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
        
        String fechaEnt = viatico.getFechaProgramada();
        String fechaSal = viatico.getFechaSalida();
        String fechaReg = viatico.getFechaRegreso();
        
        Date fEnt = null;
        Date fSal = null;
        Date fReg = null;
        try {
            fEnt = fs.parse(fechaEnt);
            fSal = fs.parse(fechaSal);
            fReg = fs.parse(fechaReg);
        } 
        catch (ParseException ex) 
        {
            System.out.println(ex);
        }
        dia.lblIdentificacion.setText(viatico.getId_tecnico());
        dia.lblEmpleado.setText(viatico.getTecnico());
        dia.fechaSalida.setDate(fSal);
        dia.fechaRegreso.setDate(fReg);
        dia.txtObservaciones.setText(viatico.getObservaciones());
        dia.txtIdMunicipio.setText(viatico.getId_destino());
        dia.txtIdMunLabora.setText(viatico.getEstado());
        dia.txtCargo.setText(viatico.getId_cargo());
        dia.txtIdViatico.setText(id_viatico);
        //Agregar destino a combobox
        DefaultComboBoxModel model = new DefaultComboBoxModel(modelo.listarMunicipiosEditar(viatico.getId_destino(), viatico.getDestino(), viatico.getValor_hospedaje(), viatico.getValor_alimentacion()));
        dia.cbDestino.setModel(model); 
        List<Detalle_viatico> dets = new ArrayList<Detalle_viatico>();
        dets =  modelo.verDetalleViatico(id_viatico); 
        int day = modelo.obtenerPivot(id_viatico);
        int dayAli = modelo.obtenerPivotAlimentacion(id_viatico);
        if(day == dayAli && day > 0){
            dia.checkDiaNoche.setSelected(true);            
        }
        agregarChecksEditar(dia, viatico.getId_cargo(), dets);
        agregarChecksMunicipioEditar(dia, viatico.getId_destino(), dets);
        agregarChecksOtrosEditar(dia, viatico.getId_tecnico(), id_viatico);
    }
    
    public void agregarChecksEditar(diaEditViatico dia, String id_cargo, List<Detalle_viatico> dets){
        modeloTable = new DefaultTableModel();
        dia.tablaCostoViaticos.setModel(modeloTable);
        dia.tablaCostoViaticos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        dia.tablaCostoViaticos.getTableHeader().setOpaque(false);
        dia.tablaCostoViaticos.getTableHeader().setBackground(new Color (204, 0, 0));
        dia.tablaCostoViaticos.getTableHeader().setForeground(new Color(255,255,255));

        modeloTable.addColumn("");
        modeloTable.addColumn("VIÁTICO");
        modeloTable.addColumn("VALOR");
        modeloTable.addColumn("SELECCIONAR");
        
        TableColumnModel columnModel = dia.tablaCostoViaticos.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(1).setPreferredWidth(180);
        columnModel.getColumn(2).setPreferredWidth(40);
        columnModel.getColumn(3).setPreferredWidth(90);  
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);        
        columnModel.getColumn(2).setCellRenderer(tcr);        

        addChecBox(3, dia.tablaCostoViaticos);
        List<CostoViatico> costoviaticos = new ArrayList<CostoViatico>();
        costoviaticos = modelo.listarCostosViaticos(id_cargo);
        Object[] colum = new Object[4];
        int ban = 1; 
        //DecimalFormat formato = new DecimalFormat("#,###");
        for(CostoViatico costoviatico : costoviaticos){             
            colum[0] = costoviatico.getId_viatico();
            colum[1] = costoviatico.getNombre().toUpperCase();
            colum[2] = costoviatico.getValor();
            for(Detalle_viatico det : dets){
                if(det.getTipo_viatico().equals(costoviatico.getId_viatico()) && costoviatico.getNombre().toUpperCase().equals(det.getDetalle())){
                    colum[3] = Boolean.TRUE;
                    ban = 2;
                }
            }
            if(ban == 1){
                colum[3] = Boolean.FALSE;
            }
            
            modeloTable.addRow(colum);
            ban = 1;
        }        
    }
    
    public void agregarChecksMunicipioEditar(diaEditViatico dia, String id_municipio, List<Detalle_viatico> dets){
        modeloTable = new DefaultTableModel();
        dia.tablaCostoViaticos_dos.setModel(modeloTable);
        
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        JTableHeader th; 
        th = dia.tablaCostoViaticos_dos.getTableHeader(); 
        th.setVisible(false);
        
        TableColumnModel columnModel = dia.tablaCostoViaticos_dos.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(1).setPreferredWidth(180);
        columnModel.getColumn(2).setPreferredWidth(40);
        columnModel.getColumn(3).setPreferredWidth(90);  
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);        
        columnModel.getColumn(2).setCellRenderer(tcr);   
        addChecBox(3, dia.tablaCostoViaticos_dos);
        
        Object[] colum = new Object[4];       
        int numRegistrosAuto = modelo.listarCostosMunicipio(id_municipio).size();
        int ban = 1;
        if(numRegistrosAuto > 0){
            
            colum[0] = "5";
            colum[1] = "ALIMENTACIÓN";
            colum[2] = modelo.listarCostosMunicipio(id_municipio).get(0).getValor_alimentacion();
            for(Detalle_viatico det : dets){
                if(det.getTipo_viatico().equals("5") && det.getDetalle().toUpperCase().equals("ALIMENTACIÓN")){
                    colum[3] = Boolean.TRUE;
                    ban = 2;
                }
            }
            if(ban == 1){
                colum[3] = Boolean.FALSE;
            }            
            modeloTable.addRow(colum);
            ban = 1;
            colum[0] = "6";
            colum[1] = "HOSPEDAJE";
            colum[2] = modelo.listarCostosMunicipio(id_municipio).get(0).getValor_hospedaje();
            for(Detalle_viatico det : dets){
                if(det.getTipo_viatico().equals("6") && det.getDetalle().toUpperCase().equals("HOSPEDAJE")){
                    colum[3] = Boolean.TRUE;
                    ban = 2;
                }
            }
            if(ban == 1){
                colum[3] = Boolean.FALSE;
            }
            modeloTable.addRow(colum);            
        }        
        
    }
    
    public void agregarChecksOtrosEditar(diaEditViatico dia, String id_empleado, String id_viatico){
        modeloTable = new DefaultTableModel();
        dia.tablaCostoViaticos_otros.setModel(modeloTable);
        
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        JTableHeader th; 
        th = dia.tablaCostoViaticos_otros.getTableHeader(); 
        th.setVisible(false);
        
        TableColumnModel columnModel = dia.tablaCostoViaticos_otros.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(1).setPreferredWidth(180);
        columnModel.getColumn(2).setPreferredWidth(40);
        columnModel.getColumn(3).setPreferredWidth(90);  
        columnModel.getColumn(4).setMaxWidth(0);
        columnModel.getColumn(4).setMinWidth(0);
        columnModel.getColumn(4).setPreferredWidth(0);
        columnModel.getColumn(5).setMaxWidth(0);
        columnModel.getColumn(5).setMinWidth(0);
        columnModel.getColumn(5).setPreferredWidth(0);
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT); 
        DefaultTableCellRenderer cent = new DefaultTableCellRenderer();
        cent.setHorizontalAlignment(SwingConstants.CENTER);
        
        columnModel.getColumn(2).setCellRenderer(tcr); 
        columnModel.getColumn(3).setCellRenderer(cent);
        addChecBox(5, dia.tablaCostoViaticos_otros);
        
        List<CostoViatico> otrosviaticos = new ArrayList<CostoViatico>();
        String estado = "2";
        otrosviaticos = modelo.listarCostosAutorizaciones(id_viatico);
        int numregistros = modelo.listarCostosAutorizaciones(id_viatico).size();
        Object[] colum = new Object[6];       
        
        if(numregistros > 0){
            dia.ScrollTabla.setVisible(true);
            dia.lblOtros.setVisible(true);
            for(CostoViatico otroviatico : otrosviaticos){
                colum[0] = "4";
                colum[1] = otroviatico.getNombre().toUpperCase();
                colum[2] = otroviatico.getValor();
                colum[3] = "Autorizado";
                colum[4] = otroviatico.getId_viatico();
                colum[5] = Boolean.TRUE;
                modeloTable.addRow(colum);
            }            
        }else{
            dia.lblOtros.setVisible(false);
            dia.ScrollTabla.setVisible(false);
        }
    }
    
    public void cancelarViatico(int viatico, int estado, pnlProgramacion pnl, String id_empleado){
        if(modelo.updateViatico(viatico, estado)){
            modelo.cancelarViaticoAutorizado(id_empleado);
            listarProgramaciones(pnl);
            JOptionPane.showMessageDialog(null, "El registro seleccionado fue ANULADO");            
        }else{
            JOptionPane.showMessageDialog(null, "Ocurrió un error al anular \nel registro seleccionado");
        }
    }
    
    public void cancelarViaticoMunicipio(int viatico, int estado, pnlProgramacion pnl, String id_empleado){
        if(modelo.updateViatico(viatico, estado)){
            modelo.cancelarViaticoAutorizado(id_empleado);
            listarProgramacionesTecMunicipios(pnl);
            JOptionPane.showMessageDialog(null, "El registro seleccionado fue ANULADO");            
        }else{
            JOptionPane.showMessageDialog(null, "Ocurrió un error al anular \nel registro seleccionado");
        }
    }
        
    
}
