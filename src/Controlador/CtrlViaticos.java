/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultaEmpleados;
import Modelo.ConsultaViaticos;
import Modelo.Detalle_viatico;
import Modelo.Devolucion;
import Modelo.Empleado;
import Modelo.Viatico;
import dialogs.diaDevolucionViatico;
import paneles.IconCellRenderer;
import principal.diaEntregaViaticos;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.apache.poi.ss.usermodel.Workbook;
import paneles.pnlViatico;
import principal.diaViaticosMun;


public final class CtrlViaticos {
    
    diaEntregaViaticos viaticos ;
    pnlViatico reportes;
    ConsultaViaticos modViatico = new ConsultaViaticos();
    ConsultaEmpleados modTec = new ConsultaEmpleados();
    JFileChooser selecArchivo = new JFileChooser();
    File archivo ;
    Workbook wb;
    int contAccion = 0;
    //private final String ruta = System.getProperties().getProperty("user.dir");
       
    public CtrlViaticos (diaEntregaViaticos viaticos, ConsultaViaticos modViatico){
        this.viaticos = viaticos;
        this.modViatico = modViatico;
        llenarTabla(this.viaticos.tablaViaticos);
    }


    public CtrlViaticos() {
       
    }
    //TABLA DE VIATICOS A ENTREGAR
    public void llenarTabla(JTable tblTecnicos){
        DecimalFormat formato = new DecimalFormat("#,###");
        tblTecnicos.setDefaultRenderer(Object.class,new IconCellRenderer());
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
        renderer.setHorizontalAlignment(SwingConstants.CENTER);  
                                
        modeloT.addColumn("#");
        modeloT.addColumn("EMPLEADO");
        modeloT.addColumn("DESTINO");
        modeloT.addColumn("FECHA SALIDA");
        modeloT.addColumn("FECHA REGRESO");
        modeloT.addColumn("FECHA PROGRAMACIÓN");
        modeloT.addColumn("PROGRAMADO POR");        
        modeloT.addColumn("OBSERVACIONES");
        modeloT.addColumn("VALOR");
        modeloT.addColumn("ENTREGAR");
        modeloT.addColumn("");
        modeloT.addColumn("");
        modeloT.addColumn("");
        
        TableColumnModel columnModel = tblTecnicos.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30);
        columnModel.getColumn(1).setPreferredWidth(250);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(120);
        columnModel.getColumn(7).setPreferredWidth(150);
        columnModel.getColumn(8).setPreferredWidth(90);
        columnModel.getColumn(9).setPreferredWidth(110);
        
        columnModel.getColumn(10).setMaxWidth(0);
        columnModel.getColumn(10).setMinWidth(0);
        columnModel.getColumn(10).setPreferredWidth(0);
        
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
        viaticos = modViatico.listarViaticos();
        Object[] columna = new Object[13];
        int i = 1;
        JLabel label = new JLabel(new ImageIcon(getClass().getResource("/img/checked.png")));
        
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
            columna[8] = viatico.getValor();            
            if(viatico.getEstado().equals("1") ){
                columna[9] = label;
            }else if(viatico.getEstado().equals("4") ){
                columna[9] = "Pendiente huella";
            }else{
                columna[9] = "";
            }
            
            columna[10] = viatico.getId_viatico();
            columna[11] = viatico.getId_tecnico(); 
            columna[12] = viatico.getEstado(); 
            modeloT.addRow(columna);
            i++;
        }  
        
    } 
    
    
    //TABLA DE VIATICOS PROGRAMADOS PARA TECNICOS EN MUNICIPIOS
    public void viaticosMunicipios(JTable tabla, String fechaInicial, String id_empleado, diaViaticosMun dia){
        DecimalFormat formato = new DecimalFormat("#,###");
//        tabla.setDefaultRenderer(Object.class,new IconCellRenderer());
//        DefaultTableModel modeloT = new DefaultTableModel(){
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };        
        DefaultTableModel modeloT = new DefaultTableModel();
        tabla.setModel(modeloT);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.getTableHeader().setOpaque(false);
        tabla.getTableHeader().setBackground(new Color (204, 0, 0));
        tabla.getTableHeader().setForeground(new Color(255,255,255));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tabla.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);  
                                
        modeloT.addColumn("#");
        modeloT.addColumn("EMPLEADO");
        modeloT.addColumn("DESTINO");
        modeloT.addColumn("FECHA SALIDA");
        modeloT.addColumn("FECHA REGRESO");
        modeloT.addColumn("FECHA PROGRAMACIÓN");
        modeloT.addColumn("PROGRAMADO POR");        
        modeloT.addColumn("OBSERVACIONES");
        modeloT.addColumn("VALOR");
        modeloT.addColumn("SELECCIONAR");
        modeloT.addColumn("");
        modeloT.addColumn("");
        modeloT.addColumn("");
        
        TableColumnModel columnModel = tabla.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30);
        columnModel.getColumn(1).setPreferredWidth(250);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(120);
        columnModel.getColumn(7).setPreferredWidth(150);
        columnModel.getColumn(8).setPreferredWidth(90);
        columnModel.getColumn(9).setPreferredWidth(110);
        
        columnModel.getColumn(10).setMaxWidth(0);
        columnModel.getColumn(10).setMinWidth(0);
        columnModel.getColumn(10).setPreferredWidth(0);
        
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
        
        
        
        addChecBox(9, tabla);
        List<Viatico> viaticos = new ArrayList<Viatico>();
        viaticos = modViatico.listarViaticosMunicipios(fechaInicial, id_empleado);
        Object[] columna = new Object[13];
        int i = 1, suma = 0;
        JLabel label = new JLabel(new ImageIcon(getClass().getResource("/img/checked.png")));
        
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
            columna[8] = viatico.getValor();            
            if(dia.checkTodos.isSelected()){
                columna[9] = Boolean.TRUE;
            }else{
               columna[9] = Boolean.FALSE; 
            }            
            
            columna[10] = viatico.getId_viatico();
            columna[11] = viatico.getId_tecnico(); 
            columna[12] = viatico.getEstado(); 
            modeloT.addRow(columna);
            i++;
            suma = suma + viatico.getValor();
        }  
        dia.lblTotal.setText(formato.format(suma));
    } 
    
    
    public void cancelarViatico(int viatico, int estado, diaEntregaViaticos viaticos, ConsultaViaticos modViatico, String estadoViatico){
        if(estadoViatico.equals("4")){
            if(modViatico.updateViaticoEntregado(viatico, estado)){
                CtrlViaticos ctrl = new CtrlViaticos(viaticos,  modViatico);
                JOptionPane.showMessageDialog(null, "Viático actualizado como legalizado.");
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrió un error con \nel registro seleccionado");
            }
            
        }else{
            if(modViatico.updateViatico(viatico, estado, viaticos.txtUser.getText())){
                CtrlViaticos ctrl = new CtrlViaticos(viaticos,  modViatico);
                if(estado == 2){
                    JOptionPane.showMessageDialog(null, "El registro seleccionado fue ENTREGADO");
                }else if(estado == 4){
                    JOptionPane.showMessageDialog(null, "Viático registrado como entregado \nPendiente registrar huella.");
                }else{
                    JOptionPane.showMessageDialog(null, "El registro seleccionado fue ANULADO");            
                }            
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrió un error al anular \nel registro seleccionado");
            } 
        }
               
    }
       
    
    public void cambiarEstadoAutorizacion(String id_empleado, String estado){
        modViatico.updateAutorizacion(id_empleado, estado);
    }
 
    
    public void llenarTecnicos(JComboBox tencicos){
        DefaultComboBoxModel modelCargo = new DefaultComboBoxModel(modViatico.listarTecnicos());
        tencicos.setModel(modelCargo);        
    } 
    
    public void llenarEmpleados(JComboBox tencicos, String dep){
        DefaultComboBoxModel modelCargo = new DefaultComboBoxModel(modViatico.listarCargoDep(dep));
        tencicos.setModel(modelCargo);        
    }
 
    public void openFile(String file){
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void registrarDevolucion(diaDevolucionViatico dia, pnlViatico pnl){
        int alimentacion = 0, hospedaje = 0, parqueadero = 0, peaje = 0, transporte = 0, otros = 0, suma = 0, imprevisto = 0;
        
        if(dia.valorImprevisto.getText().equals("")){
            imprevisto = 0;
        }
        
        if(dia.tblViaticos.getRowCount() > 0){
            for(int i=0; i<dia.tblViaticos.getRowCount(); i++){
                if(IsSelected(i, 4, dia.tblViaticos)){
                    alimentacion += Integer.parseInt(dia.tblViaticos.getValueAt(i, 1).toString());
                    hospedaje += Integer.parseInt(dia.tblViaticos.getValueAt(i, 2).toString());
                    parqueadero += Integer.parseInt(dia.tblViaticos.getValueAt(i, 3).toString());
                }
            }
        }
        
        if(dia.tblPeaje.getRowCount() > 0){
            for(int i=0; i<dia.tblPeaje.getRowCount(); i++){
                if(IsSelected(i, 3, dia.tblPeaje)){
                    peaje += Integer.parseInt(dia.tblPeaje.getValueAt(i, 2).toString());
                }
            }
        }
        if(dia.tblTransporte.getRowCount() > 0){
            for(int i=0; i<dia.tblTransporte.getRowCount(); i++){
                if(IsSelected(i, 3, dia.tblTransporte)){
                    transporte += Integer.parseInt(dia.tblTransporte.getValueAt(i, 2).toString());
                }
            }
        }
        if(dia.tblOtros.getRowCount() > 0){
            for(int i=0; i<dia.tblOtros.getRowCount(); i++){
                if(IsSelected(i, 3, dia.tblOtros)){
                    otros += Integer.parseInt(dia.tblOtros.getValueAt(i, 2).toString());
                }
            }
        }
        if(!isNumeric(dia.valorImprevisto.getText()) ){
            JOptionPane.showMessageDialog(null, "El valor ingresado no es valido.", "Importante", JOptionPane.WARNING_MESSAGE);
            dia.valorImprevisto.requestFocus();
        }else{
            imprevisto = Integer.parseInt(dia.valorImprevisto.getText());
            String mensaje = "Valor a devolver: \n";
            if(alimentacion > 0){
                mensaje += "Alimentción: $"+alimentacion+"\n";
            }
            if(hospedaje > 0){
                mensaje += "Hospedaje: $"+hospedaje+"\n";
            }
            if(parqueadero > 0){
                mensaje += "Parqueaderos: $"+parqueadero+"\n";
            }
            if(peaje > 0){
                mensaje += "Peaje: $"+peaje+"\n";
            }
            if(transporte > 0){
                mensaje += "Transporte: $"+transporte+"\n";
            }
            if(otros > 0){
                mensaje += "Otros: $"+otros+"\n";
            }
            if(imprevisto > 0){
                mensaje += "Imprevistos: $"+imprevisto+"\n";
            }
            suma = alimentacion+hospedaje+parqueadero+peaje+transporte+otros+imprevisto;
            mensaje += "___________________________\n";
            mensaje += "Total: $"+suma;
            if(dia.txtObservacion.getText().equals("")){
                JOptionPane.showMessageDialog(null, "No ha ingresado una \nobservación para guardar");
            }
            else if(suma > 0){
                Object [] opciones ={"Aceptar","Cancelar"};

                JOptionPane mens = new JOptionPane();

                int eleccion = JOptionPane.showOptionDialog(null,
                "¿Desea guardar los siguientes valores?\n"+mensaje,
                "Devoluciones",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                "Aceptar");        
                switch(eleccion){
                    case JOptionPane.YES_OPTION:
                       Date fecha = new Date();
                       String strDateFormat = "yyyy-MM-dd"; // El formato de fecha está especificado  
                       SimpleDateFormat f = new SimpleDateFormat(strDateFormat);
                       String feActual = f.format(fecha);
                       Devolucion dev = new Devolucion();
                       dev.setValor(String.valueOf(suma));
                       dev.setFecha(feActual);
                       dev.setId_viatico(dia.txtIdViatico.getText());
                       dev.setObservacion(dia.txtObservacion.getText());
                       if(modViatico.insertDevolucion(dev)){
                           listarViaticosEntregados(pnl.tblDevoluciones, dia.txtIdEmpleado.getText(), dia.txtFecha.getText());
                           dia.setVisible(false);
                           dia.dispose();
                           JOptionPane.showMessageDialog(null, "Registro exitoso.");
                       }else{
                           JOptionPane.showMessageDialog(null, "Ocurrio un error.");
                       }
                    break;
                    case JOptionPane.NO_OPTION:   

                    break;        
                }
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado o registrado ningún \nvalor para guardar");
            }
        }
        
               
             
    }
    
    
    public void llenarDependencias(JComboBox dep){
        DefaultComboBoxModel modelEst = new DefaultComboBoxModel(modViatico.listaDependencias());
        dep.setModel(modelEst);        
    }
    
    public String verificarRegistroDevolucion(String id_viatico){
        String valor = modViatico.verificarDevolucionRegistrada(id_viatico);
        return valor;
    }
    
    public void listarViaticosEntregados(JTable tabla, String id_empleado, String fecha){
        DecimalFormat formato = new DecimalFormat("#,###");
        tabla.setDefaultRenderer(Object.class,new IconCellRenderer());
        DefaultTableModel modeloT = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };        
       
        tabla.setModel(modeloT);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.getTableHeader().setOpaque(false);
        tabla.getTableHeader().setBackground(new Color (204, 0, 0));
        tabla.getTableHeader().setForeground(new Color(255,255,255));
        //tblDevoluciones.setRowHeight(25);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tabla.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);  
                                        
        modeloT.addColumn("#");
        modeloT.addColumn("FECHA ENTREGA");
        modeloT.addColumn("DESTINO");
        modeloT.addColumn("FECHA SALIDA");
        modeloT.addColumn("FECHA REGRESO");
        modeloT.addColumn("OBSERVACIONES");
        modeloT.addColumn("VALOR ENTREGADO");
        modeloT.addColumn("DEVOLUCIÓN");
        modeloT.addColumn("");
        
        TableColumnModel columnModel = tabla.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(90);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(90);
        columnModel.getColumn(4).setPreferredWidth(90);        
        columnModel.getColumn(5).setPreferredWidth(250);
        columnModel.getColumn(6).setPreferredWidth(90);
        columnModel.getColumn(7).setPreferredWidth(80);
        
        columnModel.getColumn(8).setMaxWidth(0);
        columnModel.getColumn(8).setMinWidth(0);
        columnModel.getColumn(8).setPreferredWidth(0);
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        
        DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
        derecha.setHorizontalAlignment(SwingConstants.RIGHT);
        
        columnModel.getColumn(0).setCellRenderer(tcr);
        columnModel.getColumn(1).setCellRenderer(tcr);
        columnModel.getColumn(3).setCellRenderer(tcr);
        columnModel.getColumn(4).setCellRenderer(tcr);
        columnModel.getColumn(6).setCellRenderer(derecha); 
        
        List<Viatico> viaticos = new ArrayList<Viatico>();
        viaticos = modViatico.listarViaticosEntregados(id_empleado, fecha);
        Object[] columna = new Object[9];
        int i = 1;
        //JButton btnEditar = new JButton("Editar");
        JLabel label = new JLabel(new ImageIcon(getClass().getResource("/img/previous.png")));
        label.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        
        SimpleDateFormat fe = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
        int total = 0;
        int dev = 0;
        
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
            columna[1] = feEnt;
            columna[2] = viatico.getDestino().toUpperCase();
            columna[3] = feSal;
            columna[4] = feReg;            
            total = viatico.getValor();
            dev =  viatico.getValor_devolucion();
            columna[5] = viatico.getObservaciones();
            columna[6] = (total - dev);            
            columna[7] = label;
            columna[8] = viatico.getId_viatico();            
            modeloT.addRow(columna);
            i++;
        }  
    }
    
    public void detalleViaticos(diaDevolucionViatico dia, JTable tabla, String id_viatico){
        DefaultTableModel modeloTable = new DefaultTableModel();
        tabla.setModel(modeloTable);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.getTableHeader().setOpaque(false);
        tabla.getTableHeader().setBackground(new Color (204, 0, 0));
        tabla.getTableHeader().setForeground(new Color(255,255,255));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tabla.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        modeloTable.addColumn("");
        modeloTable.addColumn("ALIMENTACIÓN");
        modeloTable.addColumn("HOSPEDAJE");        
        modeloTable.addColumn("PARQUEADERO");
        modeloTable.addColumn("SELECCIONAR");
        
               
        TableColumnModel columnModel = tabla.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(100);  
        columnModel.getColumn(4).setPreferredWidth(100);  
                
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);        
        columnModel.getColumn(1).setCellRenderer(tcr);
        columnModel.getColumn(2).setCellRenderer(tcr); 
        columnModel.getColumn(3).setCellRenderer(tcr); 
        addChecBox(4, tabla);
                
        Object[] colum = new Object[5]; 
        int numRegistros = modViatico.diasViatico(id_viatico);
        int al = modViatico.viaticosAHP(id_viatico).get(0).getValor_alimentacion();
        int ho = modViatico.viaticosAHP(id_viatico).get(0).getValor_hospedaje();
        int p = modViatico.viaticosAHP(id_viatico).get(0).getParqueadero();
        int cena = modViatico.viaticosAHP(id_viatico).get(0).getValor_cena();
        
        int dayHos = modViatico.obtenerPivot(id_viatico, "6");
        int dayAli = modViatico.obtenerPivot(id_viatico, "5");
        int dayCen = modViatico.obtenerPivot(id_viatico, "7");
        
        ///JOptionPane.showMessageDialog(null, numRegistros);
        if ((al+cena) > 0 || ho > 0 || p > 0){
            int d = 0;
            for(int i=0; i< (numRegistros-1); i++){
                colum[0] = "Día "+ (i+1);
                colum[1] = al+cena;
                colum[2] = ho;
                colum[3] = p;
                colum[4] = Boolean.FALSE;
                modeloTable.addRow(colum);
                d = i+1;
            }
            if((dayHos == dayAli && dayHos > 0) && (dayHos == dayCen && dayHos > 0)){
                colum[0] = "Día "+ (d+1);
                colum[1] = al+cena;
                colum[2] = ho;
                colum[3] = p;
                colum[4] = Boolean.FALSE;
                modeloTable.addRow(colum);
            }else if(dayAli == 0 && dayCen == 0 && dayHos > 0){
                colum[0] = "Día "+ (d+1);
                colum[1] = "0";
                colum[2] = ho;
                colum[3] = p;
                colum[4] = Boolean.FALSE;
                modeloTable.addRow(colum);
            }else if(dayAli == 0 && dayCen > 0){
                colum[0] = "Día "+ (d+1);
                colum[1] = cena;
                colum[2] = ho;
                colum[3] = p;
                colum[4] = Boolean.FALSE;
                modeloTable.addRow(colum);
            }else if(dayAli == 0 && dayCen == 0 && dayHos == 0){
                colum[0] = "Día "+ (d+1);
                colum[1] = cena;
                colum[2] = ho;
                colum[3] = p;
                colum[4] = Boolean.FALSE;
                modeloTable.addRow(colum);
            }else{
                colum[0] = "Día "+ (d+1);
                colum[1] = al;
                colum[2] = "0";
                colum[3] = "0";
                colum[4] = Boolean.FALSE;
                modeloTable.addRow(colum);
            }
            
        }
    }
    
    
    public void detalleViaticosPeajes(diaDevolucionViatico dia, JTable tabla, String id_viatico){
        DefaultTableModel modeloTable = new DefaultTableModel();
        tabla.setModel(modeloTable);
        
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        JTableHeader th; 
        th = tabla.getTableHeader(); 
        th.setVisible(false);
        
        TableColumnModel columnModel = tabla.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(180);
        columnModel.getColumn(2).setPreferredWidth(50);
        columnModel.getColumn(3).setPreferredWidth(30);  
        columnModel.getColumn(4).setMaxWidth(0);
        columnModel.getColumn(4).setMinWidth(0);
        columnModel.getColumn(4).setPreferredWidth(0);
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);        
        columnModel.getColumn(2).setCellRenderer(tcr);   
        addChecBox(3, tabla);
        
        List<Detalle_viatico> detViaticos = new ArrayList<Detalle_viatico>();
        detViaticos = modViatico.viaticosPeajes(id_viatico);
        Object[] colum = new Object[5]; 
        int numRegistros = modViatico.viaticosPeajes(id_viatico).size();
        int i = 1;
        if (numRegistros > 0){
            for(Detalle_viatico detViatico : detViaticos){
                colum[0] = i;
                colum[1] = detViatico.getDetalle();
                colum[2] = detViatico.getValor();
                colum[3] = Boolean.FALSE;
                colum[4] = detViatico.getId_detalle_viatico();
                modeloTable.addRow(colum);
                i++;
            }
        }
    }
    
    public void detalleViaticosTransporte(diaDevolucionViatico dia, JTable tabla, String id_viatico){
        DefaultTableModel modeloTable = new DefaultTableModel();
        tabla.setModel(modeloTable);
        
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        modeloTable.addColumn("");
        JTableHeader th; 
        th = tabla.getTableHeader(); 
        th.setVisible(false);
        
        TableColumnModel columnModel = tabla.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(180);
        columnModel.getColumn(2).setPreferredWidth(50);
        columnModel.getColumn(3).setPreferredWidth(30);  
        columnModel.getColumn(4).setMaxWidth(0);
        columnModel.getColumn(4).setMinWidth(0);
        columnModel.getColumn(4).setPreferredWidth(0);
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);        
        columnModel.getColumn(2).setCellRenderer(tcr);   
        addChecBox(3, tabla);
        
        List<Detalle_viatico> detViaticos = new ArrayList<Detalle_viatico>();
        detViaticos = modViatico.viaticosTransporte(id_viatico);
        Object[] colum = new Object[5]; 
        int numRegistros = modViatico.viaticosTransporte(id_viatico).size();
        int i = 1;
        if (numRegistros > 0){
            for(Detalle_viatico detViatico : detViaticos){
                colum[0] = i;
                colum[1] = detViatico.getDetalle();
                colum[2] = detViatico.getValor();
                colum[3] = Boolean.FALSE;
                colum[4] = detViatico.getId_detalle_viatico();
                modeloTable.addRow(colum);
                i++;
            }
        }
    }
    
    
    public void detalleViaticosOtros(diaDevolucionViatico dia, JTable tabla, String id_viatico){
        DefaultTableModel modeloTable = new DefaultTableModel();
        tabla.setModel(modeloTable);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.getTableHeader().setOpaque(false);
        tabla.getTableHeader().setBackground(new Color (204, 0, 0));
        tabla.getTableHeader().setForeground(new Color(255,255,255));
        //tblDevoluciones.setRowHeight(25);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tabla.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        modeloTable.addColumn("#");
        modeloTable.addColumn("JUSTIFICACIÓN");
        modeloTable.addColumn("VALOR");
        modeloTable.addColumn("SELECCIONAR");
        modeloTable.addColumn("");
                
        TableColumnModel columnModel = tabla.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(400);
        columnModel.getColumn(2).setPreferredWidth(50);
        columnModel.getColumn(3).setPreferredWidth(50);  
        columnModel.getColumn(4).setMaxWidth(0);
        columnModel.getColumn(4).setMinWidth(0);
        columnModel.getColumn(4).setPreferredWidth(0);
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);        
        columnModel.getColumn(2).setCellRenderer(tcr);   
        addChecBox(3, tabla);

        List<Detalle_viatico> detViaticos = new ArrayList<Detalle_viatico>();
        detViaticos = modViatico.viaticosOtros(id_viatico);
        Object[] colum = new Object[5]; 
        int numRegistros = modViatico.viaticosOtros(id_viatico).size();
        int i = 1;
        if (numRegistros > 0){
            for(Detalle_viatico detViatico : detViaticos){
                colum[0] = i;
                colum[1] = detViatico.getDetalle();
                colum[2] = detViatico.getValor();
                colum[3] = Boolean.FALSE;
                colum[4] = detViatico.getId_detalle_viatico();
                modeloTable.addRow(colum);
                i++;
            }
        }
    }
    
    
    public void addChecBox(int columna, JTable tabla){
        TableColumn tc = tabla.getColumnModel().getColumn(columna);
        tc.setCellEditor(tabla.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(tabla.getDefaultRenderer(Boolean.class));
    }
    
    public void cambiarEstadoViatico(diaViaticosMun dia){
        String strDateFormat = "yyyy-MM-dd"; // El formato de fecha está especificado  
        SimpleDateFormat f = new SimpleDateFormat(strDateFormat);
        String feRegistro = f.format(dia.fechaRegistro.getDate());
        int ban = 0;
        for(int i=0; i<dia.tblResultados.getRowCount(); i++){
            if(IsSelected(i, 9, dia.tblResultados)){
                 modViatico.updateViaticoMunicipio(Integer.parseInt(dia.tblResultados.getValueAt(i, 10).toString()), 6, dia.txtUsuario.getText(), feRegistro);
                 ban ++;
            }
        }
        if(ban != 0){
            String feInicial = f.format(dia.fechaInicial.getDate());
            Empleado emp = (Empleado) dia.cbEmpleado.getSelectedItem();
            dia.checkTodos.setSelected(false);
            dia.fechaRegistro.setDate(null);
            viaticosMunicipios(dia.tblResultados, feInicial, emp.getId(), dia);
            JOptionPane.showMessageDialog(null, "Los registros seleccionados se \nguardaron correctamente.");
        }else{
            JOptionPane.showMessageDialog(null, "No hay registros seleccionados.");
        }
        
        
    }
    
    public void consultarDetalleViatico(String id_viatico){
        DecimalFormat formato = new DecimalFormat("#,###");
        List<Detalle_viatico> detViaticos = new ArrayList<Detalle_viatico>();
        detViaticos = modViatico.getDetalleViatico(id_viatico);
            
        int suma = 0;
        String mensaje = "DETALLES DEL VIÁTICO SELECCIONADO. \n";
        mensaje += "**************************************************\n";
        for(Detalle_viatico detViatico : detViaticos){
            mensaje += detViatico.getDetalle()+": $"+formato.format(Integer.parseInt(detViatico.getValor()))+"\n"; 
            suma += Integer.parseInt(detViatico.getValor());
        }
        mensaje += "__________________________________________________\n";
        mensaje += "TOTAL ASIGNADO: $"+formato.format(suma);
        JOptionPane.showMessageDialog(null, mensaje);
        
        
    }
    
    public boolean IsSelected(int row, int column, JTable tabla){
        Boolean checked = false;
        checked = Boolean.valueOf(tabla.getValueAt(row,column).toString());
        return checked;
        
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
