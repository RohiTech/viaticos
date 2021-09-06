/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import dialogs.diaBackup;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author AUXINVTIC
 */
public class CtrlBackups {
    
    public void seleccionarDirectorio(diaBackup dia){
        JFileChooser fc = new JFileChooser();
        //Mostrar la ventana para abrir archivo y recoger la respuesta
        //En el parámetro del showOpenDialog se indica la ventana
        //  al que estará asociado. Con el valor this se asocia a la
        //  ventana que la abre.
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int respuesta = fc.showOpenDialog(null);
        //Comprobar si se ha pulsado Aceptar
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            //Crear un objeto File con el archivo elegido
            File archivoElegido = fc.getSelectedFile();
            //Mostrar el nombre del archvivo en un campo de texto
            dia.txtRutaBackup.setText(archivoElegido.getAbsolutePath());
        }
    }
    
    public void adjuntarArchivoSQL(diaBackup dia){
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter fil = new FileNameExtensionFilter("SQL", "sql");
        fc.setFileFilter(fil);
        int respuesta = fc.showOpenDialog(null);
        //Comprobar si se ha pulsado Aceptar
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            //Crear un objeto File con el archivo elegido
            File archivoElegido = fc.getSelectedFile();
            //Mostrar el nombre del archvivo en un campo de texto
            dia.txtAdjuntarSql.setText(archivoElegido.getAbsolutePath());
        }
    }
    
    public void generarBackup(diaBackup dia){
        if(dia.txtRutaBackup.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Selecicone un directorio donde \nguardar la copia de seguridad");
        }else{
            try {
                Date fecha = new Date();
                String strDateFormat = "dd-MM-yyyy_hh-mm-ss"; // El formato de fecha está especificado  
                SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
                String ruta = dia.txtRutaBackup.getText();
                String nombre = "\\copia_seguridad_"+objSDF.format(fecha)+".sql";
                String backus = "";
               
                backus = "mysqldump --opt -h192.168.10.50 -usp -psp/*010/ -B viaticos -r "+ruta+nombre;   
                //backus = "mysqldump --opt -hlocalhost -uroot -B viaticos -r "+ruta+nombre;  
                Runtime p = Runtime.getRuntime();
                p.exec(backus);
                JOptionPane.showMessageDialog(null, "Copia de seguridad generada con exito");
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error: "+e.getMessage());
            }
            
        }
    }
    
    public void restaurarDB(diaBackup dia){
        if(dia.txtAdjuntarSql.getText().equals("")){
            JOptionPane.showMessageDialog(null, "No se ha adjuntado el archivo de tipo SQL");
        }else{
            try {
                String ruta = dia.txtAdjuntarSql.getText();
                String backus = "";
               
                backus = "cmd /c mysql -h192.168.10.50 -usp -psp/*010/ viaticos < "+ruta;
                //backus = "cmd /c mysql -hlocalhost -uroot viaticos < "+ruta; 
                Runtime p = Runtime.getRuntime();
                p.exec(backus);
                JOptionPane.showMessageDialog(null, "Copia de seguridad restaurada con exito");
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error: "+e.getMessage());
            }
            
        }
    }
}
