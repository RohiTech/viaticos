/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cargo;
import Modelo.Conexion;
import Modelo.ConsultaEmpleados;
import Modelo.Dependencia;
import Modelo.Municipio;
import Modelo.TipoDoc;
import principal.diaRegEmpleado;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import java.awt.Component;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import paneles.pnlListaTecnicos;

/**
 *
 * @author AUXINVTIC
 */
public class CtrlHuellaTecnicos extends Component{
    private DPFPCapture LectorTec = DPFPGlobal.getCaptureFactory().createCapture();
    public DPFPEnrollment ReclutadorTec = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    private DPFPVerification VerificadorTec = DPFPGlobal.getVerificationFactory().createVerification();
    private DPFPTemplate templateTec;
    public static String TEMPLATE_PROPERTY = "templateTec";
    
    public DPFPFeatureSet featuresinscripcionTec;
    public DPFPFeatureSet featuresverificacionTec;
    
    private DPFPCapture LectorTec2 = DPFPGlobal.getCaptureFactory().createCapture();
    public DPFPEnrollment ReclutadorTec2 = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    private DPFPVerification VerificadorTec2 = DPFPGlobal.getVerificationFactory().createVerification();
    private DPFPTemplate templateTec2;
    public static String TEMPLATE_PROPERTY2 = "templateTec";
    
    public DPFPFeatureSet featuresinscripcionTec2;
    public DPFPFeatureSet featuresverificacionTec2;
    
    Conexion con = new Conexion();   
    diaRegEmpleado empleado;    
    public int ban = 1;
    
    public CtrlHuellaTecnicos(diaRegEmpleado empleado){
        this.empleado = empleado;            
        Iniciar();  
        start();
    }
    
        
    public void Iniciar(){                 
        LectorTec.addDataListener(new DPFPDataAdapter() {
            @Override 
            public void dataAcquired(final DPFPDataEvent e) {
               SwingUtilities.invokeLater(new Runnable() {   
                   public void run() {
                       if(ban == 1){
                            EnviarTexto("La huella digital ha sido capturada");
                            ProcesarCaptura(e.getSample());  
                       }else{
                            EnviarTexto2("La huella digital ha sido capturada");
                            ProcesarCaptura2(e.getSample()); 
                       }
                                                          
                  }
               });
            }
        });

        LectorTec.addReaderStatusListener(new DPFPReaderStatusAdapter() {
            @Override 
            public void readerConnected(final DPFPReaderStatusEvent e) {
               SwingUtilities.invokeLater(new Runnable() {    
                    public void run() {
                        ImageIcon lector = new ImageIcon(getClass().getResource("/img/hueOn.png"));
                        empleado.lblEstadoLector.setIcon(lector);    
                        if(ban == 1){
                            EnviarTexto("El sensor de huella digital esta activado o conectado");
                        }else{
                            EnviarTexto2("El sensor de huella digital esta activado o conectado");
                        }
                        
                    }
               });
            }
            @Override 
            public void readerDisconnected(final DPFPReaderStatusEvent e) {
               SwingUtilities.invokeLater(new Runnable() {    
                    public void run() {
                        ImageIcon lector = new ImageIcon(getClass().getResource("/img/hueOff.png"));
                        empleado.lblEstadoLector.setIcon(lector);
                        if(ban == 1){
                            EnviarTexto("El sensor de huella digital esta desactivado o no conecatado");
                        }else{
                            EnviarTexto2("El sensor de huella digital esta desactivado o no conecatado");
                        }
                        
                    }
               });
            }
        });

        LectorTec.addSensorListener(new DPFPSensorAdapter() {
            @Override 
            public void fingerTouched(final DPFPSensorEvent e) {
               SwingUtilities.invokeLater(new Runnable() {   
                  public void run() {
                      if(ban == 1){
                          EnviarTexto("El dedo ha sido colocado sobre el lector de huella");
                      }else{
                          EnviarTexto2("El dedo ha sido colocado sobre el lector de huella");
                      }
                      
                  }
               });
            }
            @Override 
            public void fingerGone(final DPFPSensorEvent e) {
               SwingUtilities.invokeLater(new Runnable() {    
                  public void run() {
                      if(ban == 1){
                          EnviarTexto("El dedo ha sido quitado del lector de huella");
                      }else{
                          EnviarTexto2("El dedo ha sido quitado del lector de huella");
                      }                      
                  }
               });
            }
        });

        LectorTec.addErrorListener(new DPFPErrorAdapter(){
            public void errorReader(final DPFPErrorEvent e){
               SwingUtilities.invokeLater(new Runnable() { 
                  public void run() {
                      if(ban == 1){
                          EnviarTexto("Error: "+e.getError());
                      }else{
                          EnviarTexto2("Error: "+e.getError());
                      }
                      
                  }
               });
            }
        });        
        
    }
     
    public void Iniciar2(){                 
        LectorTec2.addDataListener(new DPFPDataAdapter() {
            @Override 
            public void dataAcquired(final DPFPDataEvent e) {
               SwingUtilities.invokeLater(new Runnable() {   
                   public void run() {
                      EnviarTexto2("La huella digital ha sido capturada");
                      ProcesarCaptura2(e.getSample());                                      
                  }
               });
            }
        });

        LectorTec2.addReaderStatusListener(new DPFPReaderStatusAdapter() {
            @Override 
            public void readerConnected(final DPFPReaderStatusEvent e) {
               SwingUtilities.invokeLater(new Runnable() {    
                    public void run() {
                        ImageIcon lector = new ImageIcon(getClass().getResource("/img/hueOn.png"));
                        empleado.lblEstadoLector.setIcon(lector);                        
                        EnviarTexto2("El sensor de huella digital esta activado o conectado");
                    }
               });
            }
            @Override 
            public void readerDisconnected(final DPFPReaderStatusEvent e) {
               SwingUtilities.invokeLater(new Runnable() {    
                    public void run() {
                        ImageIcon lector = new ImageIcon(getClass().getResource("/img/hueOff.png"));
                        empleado.lblEstadoLector.setIcon(lector);
                        EnviarTexto2("El sensor de huella digital esta desactivado o no conecatado");
                    }
               });
            }
        });

        LectorTec2.addSensorListener(new DPFPSensorAdapter() {
            @Override 
            public void fingerTouched(final DPFPSensorEvent e) {
               SwingUtilities.invokeLater(new Runnable() {   
                  public void run() {
                      EnviarTexto2("El dedo ha sido colocado sobre el lector de huella");
                  }
               });
            }
            @Override 
            public void fingerGone(final DPFPSensorEvent e) {
               SwingUtilities.invokeLater(new Runnable() {    
                  public void run() {
                      EnviarTexto2("El dedo ha sido quitado del lector de huella");
                  }
               });
            }
        });

        LectorTec2.addErrorListener(new DPFPErrorAdapter(){
            public void errorReader(final DPFPErrorEvent e){
               SwingUtilities.invokeLater(new Runnable() { 
                  public void run() {
                      EnviarTexto2("Error: "+e.getError());
                  }
               });
            }
        });        
        
    }
     
    
    public  DPFPFeatureSet extraerCaracteristicas(DPFPSample sample, DPFPDataPurpose purpose){
       DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
       try {
            return extractor.createFeatureSet(sample, purpose);
       } catch (DPFPImageQualityException e) {
            return null;
       }
   }
    
    public  DPFPFeatureSet extraerCaracteristicas2(DPFPSample sample, DPFPDataPurpose purpose){
       DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
       try {
            return extractor.createFeatureSet(sample, purpose);
       } catch (DPFPImageQualityException e) {
            return null;
       }
   }
    
    public  Image CrearImagenHuella(DPFPSample sample) {
        return DPFPGlobal.getSampleConversionFactory().createImage(sample);
    }
    
    public  Image CrearImagenHuella2(DPFPSample sample) {
        return DPFPGlobal.getSampleConversionFactory().createImage(sample);
    }
    
    public void DibujarHuella(Image image) {        
        empleado.lblImagenHuella.setIcon(new ImageIcon(
        image.getScaledInstance(empleado.lblImagenHuella .getWidth(), empleado.lblImagenHuella .getHeight(), Image.SCALE_DEFAULT)));
        repaint();
       
    }
    
    public void DibujarHuella2(Image image) {        
        empleado.lblImagenHuella2.setIcon(new ImageIcon(
        image.getScaledInstance(empleado.lblImagenHuella2 .getWidth(), empleado.lblImagenHuella2 .getHeight(), Image.SCALE_DEFAULT)));
        repaint();
       
    }
    
    public void EstadoHuellas(){       
        ImageIcon imagen = new ImageIcon(getClass().getResource("/img/huellaVerde.png"));        
        if(ReclutadorTec.getFeaturesNeeded()==3){
            empleado.lblhuellad1.setIcon(imagen);
        }
        if(ReclutadorTec.getFeaturesNeeded()==2){
            empleado.lblhuellad2.setIcon(imagen);
        }
        if(ReclutadorTec.getFeaturesNeeded()==1){
            empleado.lblhuellad3.setIcon(imagen);
        }
        if(ReclutadorTec.getFeaturesNeeded()==0){
            empleado.lblhuellad4.setIcon(imagen);
        } 
        EnviarTexto("Número de muestras de huella necesarias para guardar: "+ ReclutadorTec.getFeaturesNeeded());
       
    }
    
    public void EstadoHuellas2(){       
        ImageIcon imagen = new ImageIcon(getClass().getResource("/img/huellaVerde.png"));        
        if(ReclutadorTec2.getFeaturesNeeded()==3){
            empleado.lblhuella5.setIcon(imagen);
        }
        if(ReclutadorTec2.getFeaturesNeeded()==2){
            empleado.lblhuella6.setIcon(imagen);
        }
        if(ReclutadorTec2.getFeaturesNeeded()==1){
            empleado.lblhuella7.setIcon(imagen);
        }
        if(ReclutadorTec2.getFeaturesNeeded()==0){
            empleado.lblhuella8.setIcon(imagen);
        } 
        EnviarTexto2("Número de muestras de huella necesarias para guardar: "+ ReclutadorTec2.getFeaturesNeeded());
       
    }
    
    public boolean numeroHuellas(){
        int nh = ReclutadorTec.getFeaturesNeeded();
        if(nh != 0){
            return false;
        }else{
            return true;
        }        
    }
    
    public boolean numeroHuellas2(){
        int nh = ReclutadorTec2.getFeaturesNeeded();
        if(nh != 0){
            return false;
        }else{
            return true;
        }        
    }
    
    public void start(){        
        LectorTec.startCapture();
        //EnviarTexto("Utilizando el lector de huella dactilar ");
    }

        
    public  void stop(){
        LectorTec.stopCapture();
        EnviarTexto("No se está usando el lector de huella dactilar ");
    } 
    
    public  void stop2(){
        LectorTec2.stopCapture();
        EnviarTexto("No se está usando el lector de huella dactilar ");
    }
    
    public void EnviarTexto(String string) {
        //tecnicos.txtArea.append("Holaa");  
        empleado.txtArea.append(string +"\n");
    }
    
    public void EnviarTexto2(String string) {
        //tecnicos.txtArea.append("Holaa");  
        empleado.txtArea1.append(string +"\n");
    }
    
    public DPFPTemplate getTemplate() {
       return templateTec;
    }
    
    public DPFPTemplate getTemplate2() {
       return templateTec2;
    }

    public void setTemplate(DPFPTemplate template) {
        DPFPTemplate old = this.templateTec;
        this.templateTec = template;
        firePropertyChange(TEMPLATE_PROPERTY, old, template);
    }
    
    public void setTemplate2(DPFPTemplate template) {
        DPFPTemplate old = this.templateTec2;
        this.templateTec2 = template;
        firePropertyChange(TEMPLATE_PROPERTY2, old, template);
    }
    
    public void ProcesarCaptura(DPFPSample sample){
        // Procesar la muestra de la huella y crear un conjunto de caracterÃ­sticas con el propÃ³sito de inscripciÃ³n.
        featuresinscripcionTec = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
     
        // Procesar la muestra de la huella y crear un conjunto de caracterÃ­sticas con el propÃ³sito de verificacion.
        featuresverificacionTec = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

        // Comprobar la calidad de la muestra de la huella y lo aÃ±ade a su reclutador si es bueno
        if (featuresinscripcionTec != null)
            try{
                System.out.println("Las caracteristicas de la huella han sido creadas");
                ReclutadorTec.addFeatures(featuresinscripcionTec);// Agregar las caracteristicas de la huella a la plantilla a crear

                // Dibuja la huella dactilar capturada.
                Image image=CrearImagenHuella(sample);
                DibujarHuella(image);

    //            btnVerificar.setEnabled(true);
    //            btnIdentificar.setEnabled(true);

            }catch (DPFPImageQualityException ex) {
                System.err.println("Error: "+ex.getMessage());
            }

            finally {
                EstadoHuellas();
                // Comprueba si la plantilla se ha creado.
                switch(ReclutadorTec.getTemplateStatus())
                {
                    case TEMPLATE_STATUS_READY: // informe de Ã©xito y detiene la captura de huellas
                    stop();
                    ban = 2;
                    start();
                    setTemplate(ReclutadorTec.getTemplate());
                    EnviarTexto("La plantilla de la huella ha sido creada, ya puede ser registrada");

                    break;

                    case TEMPLATE_STATUS_FAILED: // informe de fallas y reiniciar la captura de huellas
                    ReclutadorTec.clear();
                    stop();
                    EstadoHuellas();
                    setTemplate(null);
                    empleado.txtArea.setText("");
                    ImageIcon imagen = new ImageIcon(getClass().getResource("/img/huellagris.png"));
                    empleado.lblhuellad1.setIcon(imagen);
                    empleado.lblhuellad2.setIcon(imagen);
                    empleado.lblhuellad3.setIcon(imagen);
                    empleado.lblhuellad4.setIcon(imagen);
                    empleado.lblImagenHuella.setIcon(null); 
                    JOptionPane.showMessageDialog(this, "La plantilla de la huella no pudo ser creada. \nPor favor, repita el proceso.", "Inscripcion de huellas dactilares", JOptionPane.ERROR_MESSAGE);
                    start();
                    break;
                }
            }
    }
    
    public void ProcesarCaptura2(DPFPSample sample){
        // Procesar la muestra de la huella y crear un conjunto de caracterÃ­sticas con el propÃ³sito de inscripciÃ³n.
        featuresinscripcionTec2 = extraerCaracteristicas2(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
     
        // Procesar la muestra de la huella y crear un conjunto de caracterÃ­sticas con el propÃ³sito de verificacion.
        featuresverificacionTec2 = extraerCaracteristicas2(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

        // Comprobar la calidad de la muestra de la huella y lo aÃ±ade a su reclutador si es bueno
        if (featuresinscripcionTec2 != null)
            try{
                System.out.println("Las caracteristicas de la huella han sido creadas");
                ReclutadorTec2.addFeatures(featuresinscripcionTec2);// Agregar las caracteristicas de la huella a la plantilla a crear

                // Dibuja la huella dactilar capturada.
                Image image=CrearImagenHuella2(sample);
                DibujarHuella2(image);

    //            btnVerificar.setEnabled(true);
    //            btnIdentificar.setEnabled(true);

            }catch (DPFPImageQualityException ex) {
                System.err.println("Error: "+ex.getMessage());
            }

            finally {
                EstadoHuellas2();
                // Comprueba si la plantilla se ha creado.
                switch(ReclutadorTec2.getTemplateStatus())
                {
                    case TEMPLATE_STATUS_READY: // informe de Ã©xito y detiene la captura de huellas
                    stop();
                    setTemplate2(ReclutadorTec2.getTemplate());
                    EnviarTexto2("La plantilla de la huella ha sido creada, ya puede ser registrada");

                    break;

                    case TEMPLATE_STATUS_FAILED: // informe de fallas y reiniciar la captura de huellas
                    ReclutadorTec2.clear();
                    stop();
                    EstadoHuellas2();
                    setTemplate2(null);
                    empleado.txtArea1.setText("");
                    ImageIcon imagen = new ImageIcon(getClass().getResource("/img/huellagris.png"));
                    empleado.lblhuella5.setIcon(imagen);
                    empleado.lblhuella6.setIcon(imagen);
                    empleado.lblhuella7.setIcon(imagen);
                    empleado.lblhuella8.setIcon(imagen);
                    empleado.lblImagenHuella2.setIcon(null); 
                    JOptionPane.showMessageDialog(this, "La plantilla de la huella dos no pudo ser creada. \nPor favor, repita el proceso.", "Inscripcion de huellas dactilares", JOptionPane.ERROR_MESSAGE);
                    start();
                    break;
                }
            }
    }
    
//    public void verificarHuella() {
//        try {
//            //Establece los valores para la sentencia SQL
//            Connection c=con.getConexion();
//            //Obtiene la plantilla correspondiente a la persona indicada
//            PreparedStatement verificarStmt = c.prepareStatement("SELECT huella FROM tecnicos");
//            PreparedStatement verif = c.prepareStatement("SELECT huella FROM tecnicos");
//
//            ResultSet rs = verificarStmt.executeQuery();
//            ResultSet rst = verif.executeQuery();
//            int ban = 1;
//
//            while(rst.next() && ban == 1){                
//                //Lee la plantilla de la base de datos
//                byte templateBuffer[] = rst.getBytes("huella");
//                //Crea una nueva plantilla a partir de la guardada en la base de datos
//                DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
//                //Envia la plantilla creada al objeto contendor de Template del componente de huella digital
//                setTemplate(referenceTemplate);
//                // Compara las caracteriticas de la huella recientemente capturda con la
//                // plantilla guardada al usuario especifico en la base de datos
//                DPFPVerificationResult result = VerificadorTec.verify(featuresverificacionTec, getTemplate());
//
//                //compara las plantilas (actual vs bd)
//                if (result.isVerified()){
//                    ban = 2;                    
//                }
//            //Si no encuentra alguna huella correspondiente al nombre lo indica con un mensaje
//            } 
//            if(ban == 1){
//                guardarHuella();
//            }else{
//                ImageIcon imagen = new ImageIcon(getClass().getResource("/img/huellagris.png"));
//                tecnicos.lblhuella1.setIcon(imagen);
//                tecnicos.lblhuella2.setIcon(imagen);
//                tecnicos.lblhuella3.setIcon(imagen);
//                tecnicos.lblhuella4.setIcon(imagen);
//                ReclutadorTec.clear();
//                stop();
//                EstadoHuellas();
//                setTemplate(null);
//                tecnicos.lblImagenHuella.setIcon(null); 
//                tecnicos.txtArea.setText(""); 
//                JOptionPane.showMessageDialog(null, "La huella capturada ya se encuentra \nregistrada.");
//                start();
//            }
//
//        } catch (SQLException e) {
//            //Si ocurre un error lo indica en la consola
//            System.err.println("Error al verificar los datos de la huella. \n" + e);
//        }
//    }  
        
   
   
    public void guardarHuella(pnlListaTecnicos pnl) {  
        TipoDoc tipodoc = (TipoDoc) empleado.txtTipoDoc.getSelectedItem();
        Municipio munici = (Municipio) empleado.txtMunicipios.getSelectedItem();
        Cargo cargo = (Cargo) empleado.txtCargo.getSelectedItem();
        Dependencia dep = (Dependencia) empleado.txtDependencias.getSelectedItem();
        //JOptionPane.showMessageDialog(null, munici.getCodigo());
        if(tipodoc.getIdTipoDoc().equals("0")){
            JOptionPane.showMessageDialog(null, "Seleccione un tipo de documento", "Importante", JOptionPane.WARNING_MESSAGE);
        }else if(empleado.txtId.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Ingrese el número de identificación del empleado", "Importante", JOptionPane.WARNING_MESSAGE);
             empleado.txtId.requestFocus();
         }else if(empleado.txtNombre.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Ingrese el nombre del empleado", "Importante", JOptionPane.WARNING_MESSAGE);
             empleado.txtNombre.requestFocus();
         }else if(empleado.txtApellidoUno.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Ingrese el apellido del empleado", "Importante", JOptionPane.WARNING_MESSAGE);
             empleado.txtNombre.requestFocus();
         }else if(empleado.txtTelPersonal.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Ingrese el teléfono del empleado", "Importante", JOptionPane.WARNING_MESSAGE);
             empleado.txtTelPersonal.requestFocus();
         }else if(empleado.txtDireccion.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Ingrese la dirección de residencia del empleado", "Importante", JOptionPane.WARNING_MESSAGE);
             empleado.txtDireccion.requestFocus();
         }else if(empleado.txtCorreo.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Ingrese el correo electrónico del empleado", "Importante", JOptionPane.WARNING_MESSAGE);
             empleado.txtCorreo.requestFocus();
         }else if(dep.getId_dependencia().equals("0")){
            JOptionPane.showMessageDialog(null, "Seleccione una dependencia", "Importante", JOptionPane.WARNING_MESSAGE);
         }else if(cargo.getIdCargo().equals("0")){
            JOptionPane.showMessageDialog(null, "Seleccione un cargo", "Importante", JOptionPane.WARNING_MESSAGE);
         }else if(munici.getCodigo().equals("0")){
            JOptionPane.showMessageDialog(null, "Seleccione un municipio", "Importante", JOptionPane.WARNING_MESSAGE);
         }else if(empleado.txtCodigo.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Ingrese el código del empleado", "Importante", JOptionPane.WARNING_MESSAGE);
             empleado.txtCodigo.requestFocus();
         }else if(featuresinscripcionTec == null){
            JOptionPane.showMessageDialog(null, "No ha registrado la huella", "Importante", JOptionPane.WARNING_MESSAGE);
         }else if(empleado.txtFoto.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Agregue una foto del empleado.", "Importante", JOptionPane.WARNING_MESSAGE);
         }else if(validarCodigo(empleado.txtCodigo.getText())){
            JOptionPane.showMessageDialog(null, "El código ingresado ya está asignado.", "Importante", JOptionPane.WARNING_MESSAGE);
            empleado.txtCodigo.requestFocus();
         }else{
             
             //Obtiene los datos del template de la huella actual
            ByteArrayInputStream datosHuella = new ByteArrayInputStream(templateTec.serialize());
            Integer tamañoHuella=templateTec.serialize().length;
            
            ByteArrayInputStream datosHuella2 = new ByteArrayInputStream(templateTec2.serialize());
            Integer tamañoHuella2=templateTec2.serialize().length;
            
            Date fecha = new Date();            
            String strDateFormat = "yyyy-MM-dd"; // El formato de fecha está especificado  
            SimpleDateFormat f = new SimpleDateFormat(strDateFormat);
            String feActual = f.format(fecha);
            
//            String codigotec = "52"+munici.getCodigo();
//            ConsultaEmpleados modCon = new ConsultaEmpleados();
//            String codigo = modCon.codigoTecnico(codigotec);
//            if(codigo == null){
//                codigo = codigotec+"01";
//            }else{
//                int c = Integer.parseInt(codigo) + 1;
//                codigo = String.valueOf(c);
//            }
            
            FileInputStream fl = null;
            File file = new File(empleado.txtFoto.getText());
            try {
                //FileInputStream fis = new FileInputStream(fichero);
                fl = new FileInputStream(file);
                try {
                    //Establece los valores para la sentencia SQL
                    String sql = "INSERT INTO empleados(identificacion, id_tipo_doc, codigo, nombre_uno, nombre_dos, apellido_uno, "
                            + "apellido_dos, telefono_corporativo, telefono_personal, direccion, correo, huella, huella_dos, "
                            + "fecha_registro, id_municipio, id_cargo, estado, foto) "
                            + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    Connection c=con.getConexion();

                    try (PreparedStatement guardarStmt = c.prepareStatement(sql)) {                    
                        guardarStmt.setString(1,empleado.txtId.getText());
                        guardarStmt.setString(2, tipodoc.getIdTipoDoc());
                        guardarStmt.setString(3, empleado.txtCodigo.getText());
                        guardarStmt.setString(4,empleado.txtNombre.getText());
                        guardarStmt.setString(5,empleado.txtNombreDos.getText());
                        guardarStmt.setString(6,empleado.txtApellidoUno.getText());
                        guardarStmt.setString(7,empleado.txtApellidoDos.getText());
                        guardarStmt.setString(8,empleado.txtTelCorp.getText());
                        guardarStmt.setString(9,empleado.txtTelPersonal.getText());
                        guardarStmt.setString(10,empleado.txtDireccion.getText());
                        guardarStmt.setString(11,empleado.txtCorreo.getText());
                        guardarStmt.setBinaryStream(12, datosHuella,tamañoHuella);
                        guardarStmt.setBinaryStream(13, datosHuella2,tamañoHuella2);
                        guardarStmt.setString(14,feActual);
                        guardarStmt.setString(15, munici.getCodigo());
                        guardarStmt.setString(16, cargo.getIdCargo());
                        guardarStmt.setString(17, "1");
                        guardarStmt.setBinaryStream(18, fl);
                        //Ejecuta la sentencia
                        guardarStmt.execute();
                    }
                    //JOptionPane.showMessageDialog(null,"Información registrada correctamente");
                    CtrlTecnicos ctrlT = new CtrlTecnicos();
                    ReclutadorTec.clear();
                    ReclutadorTec2.clear();
                    stop();
                    EstadoHuellas();
                    setTemplate(null);
                    setTemplate2(null);    
                    ctrlT.llenarTabla(pnl.tblTecnicos);
                    empleado.txtTipoDoc.setSelectedIndex(0);
                    empleado.txtId.setText("");
                    empleado.txtNombre.setText("");
                    empleado.txtNombreDos.setText("");
                    empleado.txtApellidoUno.setText("");
                    empleado.txtApellidoDos.setText("");
                    empleado.txtTelCorp.setText("");
                    empleado.txtTelPersonal.setText("");
                    empleado.txtDireccion.setText("");
                    empleado.txtCorreo.setText("");
                    empleado.txtCodigo.setText("");
                    empleado.txtMunicipios.setSelectedIndex(0);
                    empleado.txtCargo.setSelectedIndex(0);
                    empleado.txtDependencias.setSelectedIndex(0);
                    ImageIcon imagen = new ImageIcon(getClass().getResource("/img/huellagris.png"));
                    empleado.lblhuellad1.setIcon(imagen);
                    empleado.lblhuellad2.setIcon(imagen);
                    empleado.lblhuellad3.setIcon(imagen);
                    empleado.lblhuellad4.setIcon(imagen);
                    empleado.lblhuella5.setIcon(imagen);
                    empleado.lblhuella6.setIcon(imagen);
                    empleado.lblhuella7.setIcon(imagen);
                    empleado.lblhuella8.setIcon(imagen);
                    empleado.lblImagenHuella.setIcon(null);
                    empleado.lblImagenHuella2.setIcon(null);
                    empleado.lblFoto.setIcon(null);
                    empleado.txtFoto.setText("");
                    empleado.txtArea.setText(""); 
                    empleado.txtArea1.setText("");
                    ban = 1;
                    con.desconectar();
                    JOptionPane.showMessageDialog(null,"Información registrada correctamente");
                    start();
                    //btnVerificar.grabFocus();
                } catch (SQLException ex) {
                    //Si ocurre un error lo indica en la consola
                    System.err.println("Error al guardar los datos de la huella.");
                    JOptionPane.showMessageDialog(null,"Error al registrar la información");
                }finally{
                    con.desconectar();
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CtrlHuellaTecnicos.class.getName()).log(Level.SEVERE, null, ex);
            }
             
            
         }

    }
    
    
    public boolean validarCodigo(String codigo){
        String sql = "SELECT codigo FROM empleados WHERE codigo = ?";
        Connection c=con.getConexion();
        ResultSet rs = null;
        
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
        
    }
        
    
    
}//Cierre clase
