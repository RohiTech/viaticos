
package Controlador;

import Modelo.Gasolina;
import Modelo.ModelMunicipio;
import Modelo.Municipio;
import Modelo.Parqueadero;
import Modelo.Peaje;
import dialogs.diaMunicipios;
import dialogs.diaOtrosViaticos;
import dialogs.diaParqueaderos;
import dialogs.diaPeajes;
import dialogs.diaTransporte;
import paneles.IconCellRenderer;
import paneles.pnlAjustes;
import principal.diaEditGasolina;
import principal.diaEditMunicipio;
import principal.diaEditParqueadero;
import principal.diaEditPeaje;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import principal.diaEditOtroViatico;

/**
 *
 * @author AUXINVTIC
 */
public class CtrlMunicipios {
    pnlAjustes mun = new pnlAjustes();
    ModelMunicipio modMun = new ModelMunicipio();
    
    public CtrlMunicipios(pnlAjustes muni){
        this.mun = muni;
        listarMunicipios(this.mun.tablaMunicipios);
        listarPeajes(this.mun.tablaPeajes);
        listarParqueaderos(this.mun.tablaParqueaderos);
        listarGasolina(this.mun.tablaGasolina);
    }

    public CtrlMunicipios() {
        
    }
    
    public void registrarMunicipio(diaMunicipios muni){
        if(muni.txtCodigo.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el código del municipio.", "Importante", JOptionPane.WARNING_MESSAGE);
            muni.txtCodigo.requestFocus(); 
        }else if(muni.txtMunicipio.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el nombre del municipio.", "Importante", JOptionPane.WARNING_MESSAGE);
            muni.txtMunicipio.requestFocus();
        }else if(muni.txtValorHosp.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el valor para el hospedaje correspondiente.", "Importante", JOptionPane.WARNING_MESSAGE);
            muni.txtValorHosp.requestFocus();
        }else if(muni.txtValorAlim.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el valor para la alimentación correspondiente.", "Importante", JOptionPane.WARNING_MESSAGE);
            muni.txtValorAlim.requestFocus();
        }else if(!isNumeric(muni.txtValorHosp.getText()) ){
            JOptionPane.showMessageDialog(null, "El valor ingresado para hospedaje no es valido.", "Importante", JOptionPane.WARNING_MESSAGE);
            muni.txtValorHosp.requestFocus();
        }else if(!isNumeric(muni.txtValorAlim.getText()) ){
            JOptionPane.showMessageDialog(null, "El valor ingresado para alimentación no es valido.", "Importante", JOptionPane.WARNING_MESSAGE);
            muni.txtValorAlim.requestFocus();
        }else{
            Municipio municipio = new Municipio();
            municipio.setCodigo(muni.txtCodigo.getText());
            municipio.setMunicipio(muni.txtMunicipio.getText());        
            municipio.setValor_hospedaje(Integer.parseInt(muni.txtValorHosp.getText()));
            municipio.setValor_alimentacion(Integer.parseInt(muni.txtValorAlim.getText()));

            if(modMun.insertMunicipio(municipio)){
                muni.txtCodigo.setText("");
                muni.txtMunicipio.setText("");
                muni.txtValorHosp.setText("");
                muni.txtValorAlim.setText("");
                listarMunicipios(muni.tablaMunicipios);
                JOptionPane.showMessageDialog(null, "Municipio registrado correctamente.");
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio un error. \nEl código del municipio puede estar repetido.");
            }
        }
        
    }
    
    public void actualizarMunicipio(diaEditMunicipio muni, diaMunicipios pnl){
        
        if(muni.txtCodigo.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el código del municipio.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else if(muni.txtMunicipio.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el nombre del municipio.", "Importante", JOptionPane.WARNING_MESSAGE);
            muni.txtMunicipio.requestFocus();
        }else if(muni.txtValorHosp.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el valor para el hospedaje correspondiente.", "Importante", JOptionPane.WARNING_MESSAGE);
            muni.txtValorHosp.requestFocus();
        }else if(muni.txtValorAlim.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el valor para la alimentación correspondiente.", "Importante", JOptionPane.WARNING_MESSAGE);
            muni.txtValorAlim.requestFocus();
        }else if(!isNumeric(muni.txtValorHosp.getText()) ){
            JOptionPane.showMessageDialog(null, "El valor ingresado para hospedaje no es valido.", "Importante", JOptionPane.WARNING_MESSAGE);
            muni.txtValorHosp.requestFocus();
        }else if(!isNumeric(muni.txtValorAlim.getText()) ){
            JOptionPane.showMessageDialog(null, "El valor ingresado para alimentación no es valido.", "Importante", JOptionPane.WARNING_MESSAGE);
            muni.txtValorAlim.requestFocus();
        }else{
            Municipio municipio = new Municipio();
            municipio.setCodigo(muni.txtCodigo.getText());
            municipio.setMunicipio(muni.txtMunicipio.getText());        
            municipio.setValor_hospedaje(Integer.parseInt(muni.txtValorHosp.getText()));
            municipio.setValor_alimentacion(Integer.parseInt(muni.txtValorAlim.getText()));

            if(modMun.updateMunicipio(municipio)){ 
                listarMunicipios(pnl.tablaMunicipios);
                muni.setVisible(false);
                muni.dispose();
                JOptionPane.showMessageDialog(null, "Municipio actualizado correctamente.");
                
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio un error.");
            }
        }
    }
    
    public void listarMunicipios(JTable tablaMun){
        //DecimalFormat formato = new DecimalFormat("#,###");
        tablaMun.setDefaultRenderer(Object.class, new IconCellRenderer());
        DefaultTableModel modeloT = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaMun.setModel(modeloT);
        tablaMun.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaMun.getTableHeader().setOpaque(false);
        tablaMun.getTableHeader().setBackground(new Color (204, 0, 0));
        tablaMun.getTableHeader().setForeground(new Color(255,255,255));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tablaMun.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
                
        modeloT.addColumn("CÓDIGO");
        modeloT.addColumn("MUNICIPIO");
        modeloT.addColumn("VALOR HOSPEDAJE");
        modeloT.addColumn("VALOR ALIMENTACIÓN");
        modeloT.addColumn("EDITAR");
        
        TableColumnModel columnModel = tablaMun.getColumnModel();
        
        columnModel.getColumn(0).setPreferredWidth(80);
        columnModel.getColumn(1).setPreferredWidth(120);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(80);
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        
        DefaultTableCellRenderer tcl = new DefaultTableCellRenderer();
        tcl.setHorizontalAlignment(SwingConstants.RIGHT);
        
        columnModel.getColumn(0).setCellRenderer(tcr); 
        columnModel.getColumn(2).setCellRenderer(tcl);
        columnModel.getColumn(3).setCellRenderer(tcl);
        
        if(modMun.listMunicipios() != null){
            List<Municipio> municipios = new ArrayList<Municipio>();
            municipios = modMun.listMunicipios();
            Object[] columna = new Object[5];            
            JLabel label = new JLabel(new ImageIcon(getClass().getResource("/img/editar.png")));
            //label.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            
            for(Municipio municipio : municipios){
                columna[0] = municipio.getCodigo();
                columna[1] = municipio.getMunicipio().toUpperCase();
                columna[2] = municipio.getValor_hospedaje();
                columna[3] = municipio.getValor_alimentacion();           
                columna[4] = label;
                modeloT.addRow(columna);                
            } 
        }
        
        
    }
    
    public void listarPeajes(JTable tablaPeaje){
        //DecimalFormat formato = new DecimalFormat("#,###");
        tablaPeaje.setDefaultRenderer(Object.class, new IconCellRenderer());
        DefaultTableModel modeloT = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaPeaje.setModel(modeloT);
        tablaPeaje.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaPeaje.getTableHeader().setOpaque(false);
        tablaPeaje.getTableHeader().setBackground(new Color (204, 0, 0));
        tablaPeaje.getTableHeader().setForeground(new Color(255,255,255));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tablaPeaje.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
                
        modeloT.addColumn("");
        modeloT.addColumn("DESCRIPCIÓN");
        modeloT.addColumn("VALOR");
        modeloT.addColumn("EDITAR");
        
        TableColumnModel columnModel = tablaPeaje.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(1).setPreferredWidth(120);
        columnModel.getColumn(2).setPreferredWidth(60);
        columnModel.getColumn(3).setPreferredWidth(60);
        
                
        DefaultTableCellRenderer tcl = new DefaultTableCellRenderer();
        tcl.setHorizontalAlignment(SwingConstants.RIGHT);
       
        columnModel.getColumn(2).setCellRenderer(tcl);
               
        if(modMun.listPeajes() != null){
            List<Peaje> peajes = new ArrayList<Peaje>();
            peajes = modMun.listPeajes();
            Object[] columna = new Object[4];
            
            JLabel label = new JLabel(new ImageIcon(getClass().getResource("/img/editar.png")));
            for(Peaje peaje : peajes){                
                columna[0] = peaje.getId_peaje();
                columna[1] = peaje.getDescripcion().toUpperCase();
                columna[2] = peaje.getValor();
                columna[3] = label;  
                modeloT.addRow(columna);                            
            } 
        }
    }

    public void listarOtrosViaticos(JTable tablaPeaje){
        //DecimalFormat formato = new DecimalFormat("#,###");
        tablaPeaje.setDefaultRenderer(Object.class, new IconCellRenderer());
        DefaultTableModel modeloT = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaPeaje.setModel(modeloT);
        tablaPeaje.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaPeaje.getTableHeader().setOpaque(false);
        tablaPeaje.getTableHeader().setBackground(new Color (204, 0, 0));
        tablaPeaje.getTableHeader().setForeground(new Color(255,255,255));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tablaPeaje.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
                
        modeloT.addColumn("");
        modeloT.addColumn("DESCRIPCIÓN");
        modeloT.addColumn("VALOR");
        modeloT.addColumn("EDITAR");
        
        TableColumnModel columnModel = tablaPeaje.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(1).setPreferredWidth(120);
        columnModel.getColumn(2).setPreferredWidth(60);
        columnModel.getColumn(3).setPreferredWidth(60);
        
                
        DefaultTableCellRenderer tcl = new DefaultTableCellRenderer();
        tcl.setHorizontalAlignment(SwingConstants.RIGHT);
       
        columnModel.getColumn(2).setCellRenderer(tcl);
               
        if(modMun.listOtrosViaticos()!= null){
            List<Peaje> peajes = new ArrayList<Peaje>();
            peajes = modMun.listOtrosViaticos();
            Object[] columna = new Object[4];
            
            JLabel label = new JLabel(new ImageIcon(getClass().getResource("/img/editar.png")));
            for(Peaje peaje : peajes){                
                columna[0] = peaje.getId_peaje();
                columna[1] = peaje.getDescripcion().toUpperCase();
                columna[2] = peaje.getValor();
                columna[3] = label;  
                modeloT.addRow(columna);                            
            } 
        }
    }

    public void listarParqueaderos(JTable tablaParqueaderos){
        //DecimalFormat formato = new DecimalFormat("#,###");
        tablaParqueaderos.setDefaultRenderer(Object.class, new IconCellRenderer());
        DefaultTableModel modeloT = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaParqueaderos.setModel(modeloT);
        tablaParqueaderos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaParqueaderos.getTableHeader().setOpaque(false);
        tablaParqueaderos.getTableHeader().setBackground(new Color (204, 0, 0));
        tablaParqueaderos.getTableHeader().setForeground(new Color(255,255,255));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tablaParqueaderos.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        
        modeloT.addColumn("");
        modeloT.addColumn("DESCRIPCIÓN");
        modeloT.addColumn("VALOR");
        modeloT.addColumn("EDITAR");
        
        TableColumnModel columnModel = tablaParqueaderos.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(1).setPreferredWidth(120);
        columnModel.getColumn(2).setPreferredWidth(60);
        columnModel.getColumn(3).setPreferredWidth(60);
        
        DefaultTableCellRenderer tcl = new DefaultTableCellRenderer();
        tcl.setHorizontalAlignment(SwingConstants.RIGHT);
        
        columnModel.getColumn(2).setCellRenderer(tcl);
             
        if(modMun.listParqueaderos()!= null){
            List<Parqueadero> parqueaderos = new ArrayList<Parqueadero>();
            parqueaderos = modMun.listParqueaderos();
            Object[] columna = new Object[4];
            JLabel label = new JLabel(new ImageIcon(getClass().getResource("/img/editar.png")));
            //label.setCursor(new Cursor(Cursor.MOVE_CURSOR));

            for(Parqueadero parqueadero : parqueaderos){                
                columna[0] = parqueadero.getId_parqueadero();
                columna[1] = parqueadero.getDescripcion().toUpperCase();
                columna[2] = parqueadero.getValor();
                columna[3] = label;  
                modeloT.addRow(columna);                              
            } 
        }
    }
    
    public void listarGasolina(JTable tablaGasolina){
        //DecimalFormat formato = new DecimalFormat("#,###");
        tablaGasolina.setDefaultRenderer(Object.class, new IconCellRenderer());
        DefaultTableModel modeloT = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaGasolina.setModel(modeloT);
        tablaGasolina.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaGasolina.getTableHeader().setOpaque(false);
        tablaGasolina.getTableHeader().setBackground(new Color (204, 0, 0));
        tablaGasolina.getTableHeader().setForeground(new Color(255,255,255));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tablaGasolina.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        
        modeloT.addColumn("");
        modeloT.addColumn("DESCRIPCIÓN");
        modeloT.addColumn("VALOR");
        modeloT.addColumn("EDITAR");
        
        TableColumnModel columnModel = tablaGasolina.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(30);
        columnModel.getColumn(3).setPreferredWidth(30);
        
                
        DefaultTableCellRenderer tcl = new DefaultTableCellRenderer();
        tcl.setHorizontalAlignment(SwingConstants.RIGHT);
    
        columnModel.getColumn(2).setCellRenderer(tcl);    
        if(modMun.listGasolina()!= null){
            List<Gasolina> transportes = new ArrayList<Gasolina>();
            transportes = modMun.listGasolina();
            Object[] columna = new Object[4];
            JLabel label = new JLabel(new ImageIcon(getClass().getResource("/img/editar.png")));

            for(Gasolina transporte : transportes){                
                columna[0] = transporte.getId_gasolina();
                columna[1] = transporte.getDescripcion().toUpperCase();
                columna[2] = transporte.getValor();
                columna[3] = label;  
                modeloT.addRow(columna);                                              
            } 
        }
    }

    public void actualizarPeaje(diaEditPeaje pe, diaPeajes dia){
        
        if(pe.txtEdDescripcion.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese la descripcion.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else if(pe.txtEdValor.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el valor.", "Importante", JOptionPane.WARNING_MESSAGE);
            pe.txtEdValor.requestFocus();
        }else if(!isNumeric(pe.txtEdValor.getText()) ){
            JOptionPane.showMessageDialog(null, "El valor ingresado no es valido.", "Importante", JOptionPane.WARNING_MESSAGE);
            pe.txtEdValor.requestFocus();
        }else{
            Peaje peaje = new Peaje();
            peaje.setId_peaje(pe.txtIdPeaje.getText());
            peaje.setDescripcion(pe.txtEdDescripcion.getText());        
            peaje.setValor(Integer.parseInt(pe.txtEdValor.getText()));

            if(modMun.updatePeaje(peaje)){ 
                listarPeajes(dia.tablaPeajes);
                pe.setVisible(false);
                pe.dispose();
                JOptionPane.showMessageDialog(null, "Registro actualizado correctamente.");
                
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio un error.");
            }
        }
    }
    
    public void actualizarOtroViatico(diaEditOtroViatico pe, diaOtrosViaticos dia){
        
        if(pe.txtEdDescripcion.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese la descripcion.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else if(pe.txtEdValor.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el valor.", "Importante", JOptionPane.WARNING_MESSAGE);
            pe.txtEdValor.requestFocus();
        }else if(!isNumeric(pe.txtEdValor.getText()) ){
            JOptionPane.showMessageDialog(null, "El valor ingresado no es valido.", "Importante", JOptionPane.WARNING_MESSAGE);
            pe.txtEdValor.requestFocus();
        }else{
            Peaje peaje = new Peaje();
            peaje.setId_peaje(pe.txtIdPeaje.getText());
            peaje.setDescripcion(pe.txtEdDescripcion.getText());        
            peaje.setValor(Integer.parseInt(pe.txtEdValor.getText()));

            if(modMun.updatePeaje(peaje)){ 
                listarOtrosViaticos(dia.tablaOtrosViaticos);
                pe.setVisible(false);
                pe.dispose();
                JOptionPane.showMessageDialog(null, "Registro actualizado correctamente.");
                
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio un error.");
            }
        }
    }
    
    public void actualizarParqueadero(diaEditParqueadero pe, diaParqueaderos parqueadero){
        
        if(pe.txtEdDescripcion.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese la descripción.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else if(pe.txtEdValor.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el valor.", "Importante", JOptionPane.WARNING_MESSAGE);
            pe.txtEdValor.requestFocus();
        }else if(!isNumeric(pe.txtEdValor.getText()) ){
            JOptionPane.showMessageDialog(null, "El valor ingresado no es valido.", "Importante", JOptionPane.WARNING_MESSAGE);
            pe.txtEdValor.requestFocus();
        }else{
            Parqueadero parq = new Parqueadero();
            parq.setId_parqueadero(pe.txtIdPeaje.getText());
            parq.setDescripcion(pe.txtEdDescripcion.getText());        
            parq.setValor(Integer.parseInt(pe.txtEdValor.getText()));

            if(modMun.updateParqueadero(parq)){ 
                listarParqueaderos(parqueadero.tablaParqueaderos);
                pe.setVisible(false);
                pe.dispose();
                JOptionPane.showMessageDialog(null, "Registro actualizado correctamente.");
                
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio un error.");
            }
        }
    }

    public void actualizarGasolina(diaEditGasolina pe, diaTransporte tr){
        
        if(pe.txtEdDescripcion.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese la descripción.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else if(pe.txtEdValor.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el valor.", "Importante", JOptionPane.WARNING_MESSAGE);
            pe.txtEdValor.requestFocus();
        }else if(!isNumeric(pe.txtEdValor.getText()) ){
            JOptionPane.showMessageDialog(null, "El valor ingresado no es valido.", "Importante", JOptionPane.WARNING_MESSAGE);
            pe.txtEdValor.requestFocus();
        }else{
            Gasolina gas = new Gasolina();
            gas.setId_gasolina(pe.txtIdGasolina.getText());
            gas.setDescripcion(pe.txtEdDescripcion.getText());        
            gas.setValor(Integer.parseInt(pe.txtEdValor.getText()));

            if(modMun.updateGasolina(gas)){ 
                listarGasolina(tr.tablaTransporte);
                pe.setVisible(false);
                pe.dispose();
                JOptionPane.showMessageDialog(null, "Registro actualizado correctamente.");
                
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio un error.");
            }
        }
    }

    public void registrarNuevoPeaje(diaPeajes nuevo){
        if(nuevo.txtEdDescripcion.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese la descripción.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else if(nuevo.txtEdValor.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el valor.", "Importante", JOptionPane.WARNING_MESSAGE);
            nuevo.txtEdValor.requestFocus();
        }else if(!isNumeric(nuevo.txtEdValor.getText()) ){
            JOptionPane.showMessageDialog(null, "El valor ingresado no es valido.", "Importante", JOptionPane.WARNING_MESSAGE);
            nuevo.txtEdValor.requestFocus();
        }else{
            Peaje peaje = new Peaje();
            peaje.setId_peaje("2");
            peaje.setDescripcion(nuevo.txtEdDescripcion.getText());        
            peaje.setValor(Integer.parseInt(nuevo.txtEdValor.getText()));

            if(modMun.insertNuevoCostoViatico(peaje)){
                listarPeajes(nuevo.tablaPeajes);
                nuevo.txtEdDescripcion.setText("");
                nuevo.txtEdValor.setText("");
                JOptionPane.showMessageDialog(null, "Registro agregado correctamente.");                
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio un error.");
            }
        }
    }
    
    public void registrarOtroViatico(diaOtrosViaticos nuevo){
        if(nuevo.txtEdDescripcion.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese la descripción.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else if(nuevo.txtEdValor.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el valor.", "Importante", JOptionPane.WARNING_MESSAGE);
            nuevo.txtEdValor.requestFocus();
        }else if(!isNumeric(nuevo.txtEdValor.getText()) ){
            JOptionPane.showMessageDialog(null, "El valor ingresado no es valido.", "Importante", JOptionPane.WARNING_MESSAGE);
            nuevo.txtEdValor.requestFocus();
        }else{
            Peaje peaje = new Peaje();
            peaje.setId_peaje("7");
            peaje.setDescripcion(nuevo.txtEdDescripcion.getText());        
            peaje.setValor(Integer.parseInt(nuevo.txtEdValor.getText()));

            if(modMun.insertNuevoCostoViatico(peaje)){
                listarOtrosViaticos(nuevo.tablaOtrosViaticos);
                nuevo.txtEdDescripcion.setText("");
                nuevo.txtEdValor.setText("");
                JOptionPane.showMessageDialog(null, "Registro agregado correctamente.");                
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio un error.");
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
    
    public void registrarNuevoParq(diaParqueaderos nuevo){
        if(nuevo.txtEdDescripcion.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese la descripción.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else if(nuevo.txtEdValor.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el valor.", "Importante", JOptionPane.WARNING_MESSAGE);
            nuevo.txtEdValor.requestFocus();
        }else if(!isNumeric(nuevo.txtEdValor.getText()) ){
            JOptionPane.showMessageDialog(null, "El valor ingresado no es valido.", "Importante", JOptionPane.WARNING_MESSAGE);
            nuevo.txtEdValor.requestFocus();
        }else{
            Peaje peaje = new Peaje();
            peaje.setId_peaje("1");
            peaje.setDescripcion(nuevo.txtEdDescripcion.getText());        
            peaje.setValor(Integer.parseInt(nuevo.txtEdValor.getText()));

            if(modMun.insertNuevoCostoViatico(peaje)){
                listarParqueaderos(nuevo.tablaParqueaderos);
                nuevo.txtEdDescripcion.setText("");
                nuevo.txtEdValor.setText("");
                JOptionPane.showMessageDialog(null, "Registro agregado correctamente.");                
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio un error.");
            }
        }
    }
    
    public void registrarNuevoTransporte(diaTransporte nuevo){
        if(nuevo.txtEdDescripcion.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese la descripción.", "Importante", JOptionPane.WARNING_MESSAGE);
        }else if(nuevo.txtEdValor.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el valor.", "Importante", JOptionPane.WARNING_MESSAGE);
            nuevo.txtEdValor.requestFocus();
        }else if(!isNumeric(nuevo.txtEdValor.getText()) ){
            JOptionPane.showMessageDialog(null, "El valor ingresado no es valido.", "Importante", JOptionPane.WARNING_MESSAGE);
            nuevo.txtEdValor.requestFocus();
        }else{
            Peaje peaje = new Peaje();
            peaje.setId_peaje("3");
            peaje.setDescripcion(nuevo.txtEdDescripcion.getText());        
            peaje.setValor(Integer.parseInt(nuevo.txtEdValor.getText()));

            if(modMun.insertNuevoCostoViatico(peaje)){
                listarGasolina(nuevo.tablaTransporte);
                nuevo.txtEdDescripcion.setText("");
                nuevo.txtEdValor.setText("");
                JOptionPane.showMessageDialog(null, "Registro agregado correctamente.");                
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio un error.");
            }
        }
    }
    
    
}
