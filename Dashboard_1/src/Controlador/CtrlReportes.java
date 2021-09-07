/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultaReportes;
import Modelo.Empleado;
import Modelo.Mes;
import Modelo.TipoDoc;
import Modelo.Viatico;
import com.csvreader.CsvWriter;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import paneles.IconCellRenderer;
import paneles.pnlReportes;

/**
 *
 * @author AUXINVTIC
 */
public class CtrlReportes {
    
    ConsultaReportes mod = new ConsultaReportes();
    public CtrlReportes(){
        
    }
    
    public void llenarComboBox(JComboBox combobox){
        Vector<TipoDoc> datos = new Vector<TipoDoc>();
        TipoDoc tip;
        tip = new TipoDoc();
        tip.setIdTipoDoc("0");
        tip.setNombreDoc("Seleccione:");
        datos.add(tip);
        
        tip = new TipoDoc();
        tip.setIdTipoDoc("1");
        tip.setNombreDoc("REPORTE GENERAL PASTO");
        datos.add(tip);
        tip = new TipoDoc();
        tip.setIdTipoDoc("3");
        tip.setNombreDoc("REPORTE GENERAL");
        datos.add(tip);
        tip = new TipoDoc();
        tip.setIdTipoDoc("2");
        tip.setNombreDoc("REPORTE POR EMPLEADO:");
        datos.add(tip);
        
        DefaultComboBoxModel modelMun = new DefaultComboBoxModel(datos);
        combobox.setModel(modelMun);
    }
    
//    public void llenarMeses(JComboBox combobox){
//        Vector<Mes> datos = new Vector<Mes>();
//        Mes mes;
//        
//        Calendar cal= Calendar.getInstance();
//        int year= cal.get(Calendar.YEAR);
//        
//        mes = new Mes();
//        mes.setId("0");
//        mes.setMes("Seleccione:");
//        mes.setFecha_inicio(year+"-01-01");
//        mes.setFecha_final(year+"-01-31");
//        datos.add(mes);
//       
//        mes = new Mes();
//        mes.setId("1");
//        mes.setMes("ENERO - " + year);
//        mes.setFecha_inicio(year+"-01-01");
//        mes.setFecha_final(year+"-01-31");
//        datos.add(mes); 
//        
//        mes = new Mes();
//        mes.setId("2");
//        mes.setMes("FEBRERO - " + year);
//        mes.setFecha_inicio(year+"-02-01");
//        mes.setFecha_final(year+"-02-29");
//        datos.add(mes);
//        
//        mes = new Mes();
//        mes.setId("3");
//        mes.setMes("MARZO - " + year);
//        mes.setFecha_inicio(year+"-03-01");
//        mes.setFecha_final(year+"-03-31");
//        datos.add(mes);
//        
//        mes = new Mes();
//        mes.setId("4");
//        mes.setMes("ABRIL - " + year);
//        mes.setFecha_inicio(year+"-04-01");
//        mes.setFecha_final(year+"-04-30");
//        datos.add(mes);
//        
//        mes = new Mes();
//        mes.setId("5");
//        mes.setMes("MAYO - " + year);
//        mes.setFecha_inicio(year+"-05-01");
//        mes.setFecha_final(year+"-05-31");
//        datos.add(mes);
//        
//        mes = new Mes();
//        mes.setId("6");
//        mes.setMes("JUNIO - " + year);
//        mes.setFecha_inicio(year+"-06-01");
//        mes.setFecha_final(year+"-06-30");
//        datos.add(mes);
//        
//        mes = new Mes();
//        mes.setId("7");
//        mes.setMes("JULIO - " + year);
//        mes.setFecha_inicio(year+"-07-01");
//        mes.setFecha_final(year+"-07-31");
//        datos.add(mes);
//        
//        mes = new Mes();
//        mes.setId("8");
//        mes.setMes("AGOSTO - " + year);
//        mes.setFecha_inicio(year+"-08-01");
//        mes.setFecha_final(year+"-08-31");
//        datos.add(mes);
//        
//        mes = new Mes();
//        mes.setId("9");
//        mes.setMes("SEPTIEMBRE - " + year);
//        mes.setFecha_inicio(year+"-09-01");
//        mes.setFecha_final(year+"-09-30");
//        datos.add(mes);
//        
//        mes = new Mes();
//        mes.setId("10");
//        mes.setMes("OCTUMBRE - " + year);
//        mes.setFecha_inicio(year+"-10-01");
//        mes.setFecha_final(year+"-10-31");
//        datos.add(mes);
//        
//        mes = new Mes();
//        mes.setId("11");
//        mes.setMes("NOVIEMBRE - " + year);
//        mes.setFecha_inicio(year+"-11-01");
//        mes.setFecha_final(year+"-11-30");
//        datos.add(mes);
//        
//        mes = new Mes();
//        mes.setId("12");
//        mes.setMes("DICIEMBRE - " + year);
//        mes.setFecha_inicio(year+"-12-01");
//        mes.setFecha_final(year+"-12-31");
//        datos.add(mes);
//        
//        
//        DefaultComboBoxModel modelMun = new DefaultComboBoxModel(datos);
//        combobox.setModel(modelMun);
//    }
//    
    public void listarEmpleados(JComboBox emp){
        DefaultComboBoxModel modelMun = new DefaultComboBoxModel(mod.listaEmpleados());
        emp.setModel(modelMun);
    }
    
    public void consultarReporteGeneral(pnlReportes pnl, int tipo){
        DecimalFormat formato = new DecimalFormat("#,###");
        pnl.tblReporte.setDefaultRenderer(Object.class,new IconCellRenderer());
        DefaultTableModel modeloT = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };        
       
        pnl.tblReporte.setModel(modeloT);
        pnl.tblReporte.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        pnl.tblReporte.getTableHeader().setOpaque(false);
        pnl.tblReporte.getTableHeader().setBackground(new Color (204, 0, 0));
        pnl.tblReporte.getTableHeader().setForeground(new Color(255,255,255));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) pnl.tblReporte.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
                                
        modeloT.addColumn("#");
        modeloT.addColumn("FECHA ENTREGA");
        modeloT.addColumn("EMPLEADO");
        modeloT.addColumn("DESTINO");
        modeloT.addColumn("FECHA SALIDA");
        modeloT.addColumn("FECHA REGRESO");
        modeloT.addColumn("OBSERVACIONES");
        modeloT.addColumn("ENTREGADO POR");
        modeloT.addColumn("V. ENTREGADO");        
        modeloT.addColumn("V. DEVOLUCIÓN");
        modeloT.addColumn("TOTAL");
        modeloT.addColumn("");
        
        TableColumnModel columnModel = pnl.tblReporte.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(160);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(180);
        columnModel.getColumn(7).setPreferredWidth(160);
        columnModel.getColumn(8).setPreferredWidth(80);
        columnModel.getColumn(9).setPreferredWidth(80);
        columnModel.getColumn(10).setPreferredWidth(80);
        
        columnModel.getColumn(11).setMaxWidth(0);
        columnModel.getColumn(11).setMinWidth(0);
        columnModel.getColumn(11).setPreferredWidth(0);
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        
        DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
        derecha.setHorizontalAlignment(SwingConstants.RIGHT);
        
        columnModel.getColumn(0).setCellRenderer(tcr);
        columnModel.getColumn(1).setCellRenderer(tcr);
        columnModel.getColumn(4).setCellRenderer(tcr);
        columnModel.getColumn(5).setCellRenderer(tcr);
        columnModel.getColumn(8).setCellRenderer(derecha); 
        columnModel.getColumn(9).setCellRenderer(derecha);
        columnModel.getColumn(10).setCellRenderer(derecha);
        
                 
        String strDateFormat = "yyyy-MM-dd"; // El formato de fecha está especificado  
        SimpleDateFormat f = new SimpleDateFormat(strDateFormat);
        String feInicial = f.format(pnl.fechaInicial.getDate());
        String feFinal= f.format(pnl.fechaFinal.getDate());
        
        List<Viatico> viaticos = new ArrayList<Viatico>();
        if(tipo == 1){
            viaticos = mod.reporteGeneral(feInicial, feFinal);
        }
        if(tipo == 3){
            viaticos = mod.reporteGeneralTotal(feInicial, feFinal);
        }
        Object[] columna = new Object[12];
        int numRegistros = mod.reporteGeneral(feInicial, feFinal).size();
        //JButton btnEditar = new JButton("Editar");
        JLabel label = new JLabel(new ImageIcon(getClass().getResource("/img/previous.png")));
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        SimpleDateFormat fe = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
        int total = 0;
        int dev = 0;
        int suma = 0;
        int i = 1;
        String userId = "";
        String userNombre = "";
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
            total = viatico.getValor();
            dev =  viatico.getValor_devolucion();
            suma += (total-dev);
            columna[0] = i;
            columna[1] = feEnt;
            columna[2] = viatico.getTecnico().toUpperCase();
            columna[3] = viatico.getDestino().toUpperCase();
            columna[4] = feSal;            
            columna[5] = feReg;
            columna[6] = viatico.getObservaciones().replace("\n", " ");
            if(userId.equals(viatico.getEntregadoPor())){
                columna[7] = userNombre;
            }else{
                userId = viatico.getEntregadoPor();
                userNombre = mod.entregadopor(viatico.getEntregadoPor());
                columna[7] = userNombre;
            }
            
            
            columna[8] = formato.format(total);
            columna[9] = formato.format(dev); 
            columna[10] = formato.format(total - dev); 
            columna[11] = viatico.getId_viatico(); 
            modeloT.addRow(columna);
            i++;
        }  
        pnl.lblTotal.setText(formato.format(suma));
        
    }

    public void consultaReporteEmpleado(pnlReportes pnl){
        //JOptionPane.showMessageDialog(null, "ok");
        DecimalFormat formato = new DecimalFormat("#,###");
        pnl.tblReporte.setDefaultRenderer(Object.class,new IconCellRenderer());
        DefaultTableModel modeloT = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };        
       
        pnl.tblReporte.setModel(modeloT);
        pnl.tblReporte.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        pnl.tblReporte.getTableHeader().setOpaque(false);
        pnl.tblReporte.getTableHeader().setBackground(new Color (204, 0, 0));
        pnl.tblReporte.getTableHeader().setForeground(new Color(255,255,255));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) pnl.tblReporte.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
                                
        modeloT.addColumn("#");
        modeloT.addColumn("FECHA ENTREGA");
        modeloT.addColumn("DESTINO");
        modeloT.addColumn("FECHAS PROGRAMADAS");
        modeloT.addColumn("V. ALIMENTACIÓN");
        modeloT.addColumn("V. HOSPEDAJE");               
        modeloT.addColumn("V. PARQUEADEROS");
        modeloT.addColumn("V. PEAJES");
        modeloT.addColumn("V. TRANSPORTE"); 
        modeloT.addColumn("V. OTROS");
        modeloT.addColumn("V. DEVOLUCIÓN");
        modeloT.addColumn("TOTAL");
        modeloT.addColumn("VER");
        modeloT.addColumn("");
        modeloT.addColumn("");
        modeloT.addColumn("");
        
        TableColumnModel columnModel = pnl.tblReporte.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(220);
        columnModel.getColumn(4).setPreferredWidth(90);
        columnModel.getColumn(5).setPreferredWidth(90);
        columnModel.getColumn(6).setPreferredWidth(90);
        columnModel.getColumn(7).setPreferredWidth(90);
        columnModel.getColumn(8).setPreferredWidth(90);
        columnModel.getColumn(9).setPreferredWidth(90);
        columnModel.getColumn(10).setPreferredWidth(90);
        columnModel.getColumn(11).setPreferredWidth(90);
        columnModel.getColumn(12).setPreferredWidth(50);
        columnModel.getColumn(13).setMaxWidth(0);
        columnModel.getColumn(13).setMinWidth(0);
        columnModel.getColumn(13).setPreferredWidth(0);
        
        columnModel.getColumn(14).setMaxWidth(0);
        columnModel.getColumn(14).setMinWidth(0);
        columnModel.getColumn(14).setPreferredWidth(0);
        columnModel.getColumn(15).setMaxWidth(0);
        columnModel.getColumn(15).setMinWidth(0);
        columnModel.getColumn(15).setPreferredWidth(0);
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        
        DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
        derecha.setHorizontalAlignment(SwingConstants.RIGHT);
        
        columnModel.getColumn(0).setCellRenderer(tcr);
        columnModel.getColumn(1).setCellRenderer(tcr);
        columnModel.getColumn(4).setCellRenderer(derecha);
        columnModel.getColumn(5).setCellRenderer(derecha);
        columnModel.getColumn(6).setCellRenderer(derecha);
        columnModel.getColumn(7).setCellRenderer(derecha); 
        columnModel.getColumn(8).setCellRenderer(derecha);
        columnModel.getColumn(9).setCellRenderer(derecha);
        columnModel.getColumn(10).setCellRenderer(derecha);
        columnModel.getColumn(11).setCellRenderer(derecha);
                
        String strDateFormat = "yyyy-MM-dd"; // El formato de fecha está especificado  
        SimpleDateFormat f = new SimpleDateFormat(strDateFormat);
        String feInicial = f.format(pnl.fechaInicial.getDate());
        String feFinal= f.format(pnl.fechaFinal.getDate());
        Empleado empleado = (Empleado) pnl.cbEmpleado.getSelectedItem();
        //JOptionPane.showMessageDialog(null, mes.getFecha_inicio()+" "+mes.getFecha_final());
        List<Viatico> viaticos = new ArrayList<Viatico>();
        viaticos = mod.reporteEmpleado(empleado.getId(), feInicial, feFinal);
        Object[] columna = new Object[16];
        //int numRegistros = mod.reporteEmpleado(empleado.getId(), mes.getFecha_inicio(), mes.getFecha_final()).size();
        //JButton btnEditar = new JButton("Editar");
        JLabel label = new JLabel(new ImageIcon(getClass().getResource("/img/add.png")));
                
        SimpleDateFormat fe = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
        int total = 0;
        int dev = 0;
        int suma = 0;
        int i = 1;
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
            total = ( viatico.getValor_hospedaje() +
                       viatico.getValor_alimentacion() +
                       viatico.getValor_cena() + 
                       viatico.getParqueadero() +
                       viatico.getPeaje() +
                       viatico.getTransporte() + 
                       viatico.getValor()                        
                    );
            dev = viatico.getValor_devolucion();
            suma += (total-dev);
            columna[0] = i;
            columna[1] = feEnt;
            columna[2] = viatico.getDestino().toUpperCase();
            columna[3] = "Salida: "+feSal+" Regreso: "+feReg;
            columna[4] = formato.format(viatico.getValor_alimentacion() + viatico.getValor_cena());
            columna[5] = formato.format(viatico.getValor_hospedaje());
            columna[6] = formato.format(viatico.getParqueadero());
            columna[7] = formato.format(viatico.getPeaje());
            columna[8] = formato.format(viatico.getTransporte()); 
            columna[9] = formato.format(viatico.getValor()); 
            columna[10] = formato.format(viatico.getValor_devolucion()); 
            columna[11] = formato.format(total - dev);
            columna[12] = label; 
            columna[13] = viatico.getId_viatico(); 
            String o = viatico.getObservaciones().toUpperCase();
            
            StringTokenizer cant = new StringTokenizer(o, " ");
            if(o != null && cant.countTokens() > 0){
                o = "OBSERVACIÓN: "+o;                
            }else{
                o = "";
            }
            columna[14] = o; 
            
            String j = viatico.getJust_autorizacion();
            if(j == null){
                j = "";
            }else{
                j = "JUSTIFICACIÓN AUTORIZACIÓN: "+j;
            }
            columna[15] = j.toUpperCase(); 
            modeloT.addRow(columna);
            i++;
        }  
        pnl.lblTotal.setText(formato.format(suma));
        
    }

    public void exportarReporteGeneral(pnlReportes pnl, File archivo, int t){
        String strDateFormat = "yyyy-MM-dd"; // El formato de fecha está especificado  
        SimpleDateFormat f = new SimpleDateFormat(strDateFormat);
        String feInicial = f.format(pnl.fechaInicial.getDate());
        String feFinal= f.format(pnl.fechaFinal.getDate());
        String reporteSalida = archivo.getAbsolutePath();
        boolean existe = new File(reporteSalida).exists();       
        
        if(existe){
            File archivoSalida = new File(reporteSalida);
            archivoSalida.delete();
        }
        
        try {
            CsvWriter salidaCSV = new CsvWriter(new FileWriter(archivo, true), ',');
            
            salidaCSV.write("FECHA ENTREGA");
            salidaCSV.write("EMPLEADO");
            salidaCSV.write("DESTINO");
            salidaCSV.write("FECHA SALIDA");
            salidaCSV.write("FECHA REGRESO");
            salidaCSV.write("OBSERVACIONES");
            salidaCSV.write("V. ENTREGADO");
            salidaCSV.write("V. DEVOLUCION");
            salidaCSV.write("TOTAL");            
            
            salidaCSV.endRecord();
            
            List<Viatico> viaticos = new ArrayList<Viatico>();
            int numRegistros = mod.reporteGeneral(feInicial, feFinal).size();
            SimpleDateFormat fe = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
            int total = 0;
            int dev = 0;
            int suma = 0;
            
            if(t == 1){
               viaticos = mod.reporteGeneral(feInicial, feFinal); 
            }
            if(t == 2){
                viaticos = mod.reporteGeneralTotal(feInicial, feFinal);
            }
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
                total = viatico.getValor();
                dev =  viatico.getValor_devolucion();
                suma += (total-dev);
                String obs = viatico.getObservaciones().replace("\n", " ");
                obs = obs.replace(",", " ");
                obs = obs.replace(";", " ");
                
                salidaCSV.write(feEnt);
                salidaCSV.write(viatico.getTecnico().toUpperCase());
                salidaCSV.write(viatico.getDestino().toUpperCase());
                salidaCSV.write(feSal);
                salidaCSV.write(feReg);
                salidaCSV.write(obs);
                salidaCSV.write(String.valueOf(total));
                salidaCSV.write(String.valueOf(dev));
                salidaCSV.write(String.valueOf(total - dev));
                
                salidaCSV.endRecord();
            }
            
            salidaCSV.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    
    public void exportarReporteEmpleado(pnlReportes pnl, File archivo){
        String strDateFormat = "yyyy-MM-dd"; // El formato de fecha está especificado  
        SimpleDateFormat f = new SimpleDateFormat(strDateFormat);
        String feInicial = f.format(pnl.fechaInicial.getDate());
        String feFinal= f.format(pnl.fechaFinal.getDate());
        Empleado empleado = (Empleado) pnl.cbEmpleado.getSelectedItem();
        String reporteSalida = archivo.getAbsolutePath();
        boolean existe = new File(reporteSalida).exists();       
        
        if(existe){
            File archivoSalida = new File(reporteSalida);
            archivoSalida.delete();
        }
        
        try {
            CsvWriter salidaCSV = new CsvWriter(new FileWriter(archivo, true), ',');
            
            salidaCSV.write("FECHA ENTREGA");
            salidaCSV.write("DESTINO");
            salidaCSV.write("FECHA SALIDA");
            salidaCSV.write("FECHA REGRESO");
            salidaCSV.write("JUSTIFICACION AUTORIZACIÓN");
            salidaCSV.write("OBSERVACIONES");            
            salidaCSV.write("V. ALIMENTACION");
            salidaCSV.write("V. HOSPEDAJE");
            salidaCSV.write("V. PARQUEADEROS");
            salidaCSV.write("V. PEAJES");
            salidaCSV.write("V. TRANSPORTE");
            salidaCSV.write("V. OTROS");
            salidaCSV.write("V. DEVOLUCION");
            
            salidaCSV.write("TOTAL");            
            
            salidaCSV.endRecord();
            
            List<Viatico> viaticos = new ArrayList<Viatico>();
            viaticos = mod.reporteEmpleado(empleado.getId(), feInicial, feFinal);
            SimpleDateFormat fe = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
            int total = 0;
            int dev = 0;
            String obs = "";
            String just = "";
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
                total = ( viatico.getValor_hospedaje() +
                        viatico.getValor_alimentacion() +
                        viatico.getValor_cena() +
                        viatico.getParqueadero() +
                        viatico.getPeaje() +
                        viatico.getTransporte() + 
                        viatico.getValor()                        
                    );
                dev =  viatico.getValor_devolucion(); 
                if(viatico.getObservaciones() != null){
                    obs = viatico.getObservaciones().replace("\n", " ");
                    obs = obs.replace(",", " ");
                    obs = obs.replace(";", " ");
                }
                if(viatico.getJust_autorizacion() != null){
                    just = viatico.getJust_autorizacion().replace("\n", " ");
                    just = just.replace(",", " ");
                    just = just.replace(";", " ");
                }
                
                                
                salidaCSV.write(feEnt); 
                salidaCSV.write(viatico.getDestino().toUpperCase());
                salidaCSV.write(feSal);
                salidaCSV.write(feReg);
                salidaCSV.write(just);
                salidaCSV.write(obs);
                salidaCSV.write(String.valueOf(viatico.getValor_alimentacion()+viatico.getValor_cena()));
                salidaCSV.write(String.valueOf(viatico.getValor_hospedaje()));
                salidaCSV.write(String.valueOf(viatico.getParqueadero()));
                salidaCSV.write(String.valueOf(viatico.getPeaje()));
                salidaCSV.write(String.valueOf(viatico.getTransporte()));
                salidaCSV.write(String.valueOf(viatico.getValor()));
                salidaCSV.write(String.valueOf(dev));
                salidaCSV.write(String.valueOf(total - dev));                
                salidaCSV.endRecord();
                obs = "";
                just = "";
            }
            
            salidaCSV.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
