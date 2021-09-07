/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Modelo.ConsultaEmpleados;
import Modelo.ConsultaViaticos;
import paneles.pnlUsuarios;
import principal.diaEntregaViaticos;
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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author AUXINVTIC
 */
public class CtrlHuella extends Component{
    
    public  DPFPCapture Lector = DPFPGlobal.getCaptureFactory().createCapture();
    //private DPFPCapture Lector = DPFPGlobal.getCaptureFactory().createCapture();
    public DPFPEnrollment Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    private DPFPVerification Verificador = DPFPGlobal.getVerificationFactory().createVerification();
    private DPFPTemplate template;
    public static String TEMPLATE_PROPERTY = "template";
    
    public DPFPFeatureSet featuresinscripcion;
    public DPFPFeatureSet featuresverificacion;
    
    Conexion con = new Conexion();
    diaEntregaViaticos viaticos;
          
    public CtrlHuella(diaEntregaViaticos viaticos){
        this.viaticos = viaticos;  
        Iniciar();  
        start();
    }
    
            
    public void Iniciar(){                 
        Lector.addDataListener(new DPFPDataAdapter() {
            @Override 
            public void dataAcquired(final DPFPDataEvent e) {
               SwingUtilities.invokeLater(new Runnable() {   
                   public void run() {
                      EnviarTexto("La huella digital ha sido capturada");
                      ProcesarCaptura(e.getSample());
                      verificarHuella();                      
                  }
               });
            }
        });

        Lector.addReaderStatusListener(new DPFPReaderStatusAdapter() {
            @Override 
            public void readerConnected(final DPFPReaderStatusEvent e) {
               SwingUtilities.invokeLater(new Runnable() {    
                    public void run() {
                        ImageIcon lector = new ImageIcon(getClass().getResource("/img/hueOn.png"));
                        viaticos.lblEstadoLector.setIcon(lector);                        
                        EnviarTexto("El sensor de huella digital esta activado o conectado");
                    }
               });
            }
            @Override 
            public void readerDisconnected(final DPFPReaderStatusEvent e) {
               SwingUtilities.invokeLater(new Runnable() {    
                    public void run() {
                        ImageIcon lector = new ImageIcon(getClass().getResource("/img/hueOff.png"));
                        viaticos.lblEstadoLector.setIcon(lector);                         
                        EnviarTexto("El sensor de huella digital esta desactivado o no conecatado");
                    }
               });
            }
        });

        Lector.addSensorListener(new DPFPSensorAdapter() {
            @Override 
            public void fingerTouched(final DPFPSensorEvent e) {
               SwingUtilities.invokeLater(new Runnable() {   
                  public void run() {
                      EnviarTexto("El dedo ha sido colocado sobre el lector de huella");
                  }
               });
            }
            @Override 
            public void fingerGone(final DPFPSensorEvent e) {
               SwingUtilities.invokeLater(new Runnable() {    
                  public void run() {
                      EnviarTexto("El dedo ha sido quitado del lector de huella");
                  }
               });
            }
        });

        Lector.addErrorListener(new DPFPErrorAdapter(){
            public void errorReader(final DPFPErrorEvent e){
               SwingUtilities.invokeLater(new Runnable() { 
                  public void run() {
                      EnviarTexto("Error: "+e.getError());
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
    
    public  Image CrearImagenHuella(DPFPSample sample) {
        return DPFPGlobal.getSampleConversionFactory().createImage(sample);
    }
    public void DibujarHuella(Image image) {
//        tecnicos.lblImagenHuella.setIcon(new ImageIcon(
//        image.getScaledInstance(tecnicos.lblImagenHuella .getWidth(), tecnicos.lblImagenHuella .getHeight(), Image.SCALE_DEFAULT)));
//        repaint();
    }
    
    public void EstadoHuellas(){ 
//            EnviarTexto("Número de muestras de huella necesarias para guardar: "+ Reclutador.getFeaturesNeeded());
    }
    
    public void EnviarTexto(String string) {
        viaticos.txtAreaViaticos.append(string + "\n");        
    }
    
    public  void start(){        
        Lector.startCapture();
        //EnviarTexto("Utilizando el lector de huella dactilar ");
    }

    public  void stop(){
        Lector.stopCapture();
        EnviarTexto("No se está usando el lector de huella dactilar ");
    } 
    
    public DPFPTemplate getTemplate() {
       return template;
    }

    public void setTemplate(DPFPTemplate template) {
        DPFPTemplate old = this.template;
        this.template = template;
        firePropertyChange(TEMPLATE_PROPERTY, old, template);
    }
    
    public void ProcesarCaptura(DPFPSample sample){
        // Procesar la muestra de la huella y crear un conjunto de caracterÃ­sticas con el propÃ³sito de inscripciÃ³n.
        featuresinscripcion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

        // Procesar la muestra de la huella y crear un conjunto de caracterÃ­sticas con el propÃ³sito de verificacion.
        featuresverificacion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

        // Comprobar la calidad de la muestra de la huella y lo aÃ±ade a su reclutador si es bueno
        if (featuresinscripcion != null)
            try{
                System.out.println("Las caracteristicas de la huella han sido creadas");
                Reclutador.addFeatures(featuresinscripcion);// Agregar las caracteristicas de la huella a la plantilla a crear

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
                switch(Reclutador.getTemplateStatus())
                {
                    case TEMPLATE_STATUS_READY: // informe de Ã©xito y detiene la captura de huellas
                    stop();
                    setTemplate(Reclutador.getTemplate());
                    EnviarTexto("La plantilla de la huella ha sido creada, ya puede ser registrada");
                    break;

                    case TEMPLATE_STATUS_FAILED: // informe de fallas y reiniciar la captura de huellas
                    Reclutador.clear();
                    stop();
                    EstadoHuellas();
                    setTemplate(null);
                    //JOptionPane.showMessageDialog(pnlRegTecnicos.this, "La Plantilla de la Huella no pudo ser creada, Repita el Proceso", "Inscripcion de Huellas Dactilares", JOptionPane.ERROR_MESSAGE);
                    start();
                    break;
                }
            }
    }
    
   
//    public void guardarHuella(){         
//        if(tecnicos.txtId.getText().equals("")){
//             JOptionPane.showMessageDialog(null, "Ingrese el número de identificación del técnico", "Importante", JOptionPane.WARNING_MESSAGE);
//             tecnicos.txtId.requestFocus();
//         }else if(tecnicos.txtNombre.getText().equals("")){
//             JOptionPane.showMessageDialog(null, "Ingrese el nombre del técnico", "Importante", JOptionPane.WARNING_MESSAGE);
//             tecnicos.txtNombre.requestFocus();
//         }else if(tecnicos.txtTelefono.getText().equals("")){
//             JOptionPane.showMessageDialog(null, "Ingrese el teléfono del técnico", "Importante", JOptionPane.WARNING_MESSAGE);
//             tecnicos.txtTelefono.requestFocus();
//         }else if(tecnicos.txtDireccion.getText().equals("")){
//             JOptionPane.showMessageDialog(null, "Ingrese la dirección de residencia del técnico", "Importante", JOptionPane.WARNING_MESSAGE);
//             tecnicos.txtDireccion.requestFocus();
//         }else if(tecnicos.txtCorreo.getText().equals("")){
//             JOptionPane.showMessageDialog(null, "Ingrese el correo electrónico del técnico", "Importante", JOptionPane.WARNING_MESSAGE);
//             tecnicos.txtCorreo.requestFocus();
//         }else if(featuresinscripcion == null){
//            JOptionPane.showMessageDialog(null, "No ha registrado la huella", "Importante", JOptionPane.WARNING_MESSAGE);
//         }else{
//             //Obtiene los datos del template de la huella actual
//            ByteArrayInputStream datosHuella = new ByteArrayInputStream(template.serialize());
//            Integer tamañoHuella=template.serialize().length;
//
//             //Pregunta el nombre de la persona a la cual corresponde dicha huella
//             ///String nombre = JOptionPane.showInputDialog("Nombre:");
//             try {
//                //Establece los valores para la sentencia SQL
//                Connection c=con.getConexion();
//                try (PreparedStatement guardarStmt = c.prepareStatement("INSERT INTO tecnicos(identificacion, nombre, telefono, direccion, correo, huella) values(?,?,?,?,?,?)")) {
//                    guardarStmt.setString(1,tecnicos.txtId.getText());
//                    guardarStmt.setString(2,tecnicos.txtNombre.getText());
//                    guardarStmt.setString(3,tecnicos.txtTelefono.getText());
//                    guardarStmt.setString(4,tecnicos.txtDireccion.getText());
//                    guardarStmt.setString(5,tecnicos.txtCorreo.getText());
//                    guardarStmt.setBinaryStream(6, datosHuella,tamañoHuella);
//                    //Ejecuta la sentencia
//                    guardarStmt.execute();
//                }
//                JOptionPane.showMessageDialog(null,"Información registrada correctamente");
//                con.desconectar();
//                tecnicos.btnGuardar.setEnabled(false);
//                tecnicos.txtId.setText("");
//                tecnicos.txtNombre.setText("");
//                tecnicos.txtTelefono.setText("");
//                tecnicos.txtDireccion.setText("");
//                tecnicos.txtCorreo.setText("");
//
//                //btnVerificar.grabFocus();
//            } catch (SQLException ex) {
//                //Si ocurre un error lo indica en la consola
//                System.err.println("Error al guardar los datos de la huella.");
//                JOptionPane.showMessageDialog(null,"Error al registrar la información");
//            }finally{
//                con.desconectar();
//            }
//            
//         }
//
//    }

    
    /**
* Verifica la huella digital actual contra otra en la base de datos
*/
    public void verificarHuella() {
        DecimalFormat formato = new DecimalFormat("#,###");
    try {
        //Establece los valores para la sentencia SQL
        Connection c=con.getConexion();
        //Obtiene la plantilla correspondiente a la persona indicada
        PreparedStatement verificarStmt = c.prepareStatement("SELECT V.id_viatico, E.nombre_uno, E.nombre_dos, E.apellido_uno, "
                + "E.apellido_dos, V.id_municipio, M.nombre, E.huella, E.huella_dos, E.identificacion, V.estado "
                + "FROM viaticos V INNER JOIN empleados E ON V.id_empleado = E.identificacion "
                + "INNER JOIN municipios M ON V.id_municipio = M.codigo "
                + "WHERE V.estado = 1 OR V.estado = 4 ");
        PreparedStatement verif = c.prepareStatement("SELECT V.id_viatico, E.nombre_uno, E.nombre_dos, E.apellido_uno, "
                + "E.apellido_dos, V.id_municipio, M.nombre, E.huella, E.huella_dos, E.identificacion, V.estado "
                + "FROM viaticos V INNER JOIN empleados E ON V.id_empleado = E.identificacion "
                + "INNER JOIN municipios M ON V.id_municipio = M.codigo "
                + "WHERE V.estado = 1 OR V.estado = 4 ");
                
        ResultSet rs = verificarStmt.executeQuery();
        ResultSet rst = verif.executeQuery();        
        int ban = 1;
        if(rs.next()){
            //Si se encuentra el nombre en la base de datos
            while(rst.next() && ban == 1){                
                //Lee la plantilla de la base de datos
                byte templateBuffer[] = rst.getBytes("huella");
                //Crea una nueva plantilla a partir de la guardada en la base de datos
                DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
                //Envia la plantilla creada al objeto contendor de Template del componente de huella digital
                setTemplate(referenceTemplate);
                // Compara las caracteriticas de la huella recientemente capturda con la
                // plantilla guardada al usuario especifico en la base de datos
                DPFPVerificationResult result = Verificador.verify(featuresverificacion, getTemplate());
                
                //Lee la plantilla de la base de datos
                byte templateBuffer2[] = rst.getBytes("huella_dos");
                //Crea una nueva plantilla a partir de la guardada en la base de datos
                DPFPTemplate referenceTemplate2 = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer2);
                //Envia la plantilla creada al objeto contendor de Template del componente de huella digital
                setTemplate(referenceTemplate2);
                // Compara las caracteriticas de la huella recientemente capturda con la
                // plantilla guardada al usuario especifico en la base de datos
                DPFPVerificationResult result2 = Verificador.verify(featuresverificacion, getTemplate());

                //compara las plantilas (actual vs bd)
                if (result.isVerified() || result2.isVerified()){
 
                    int id = rst.getInt(1);

                    PreparedStatement ps = c.prepareStatement("SELECT detalle, valor, pivot, id_tipo_viatico FROM detalle_viatico "
                            + "WHERE id_viatico = ?");
                    ps.setInt(1, id);
                    ResultSet r = ps.executeQuery();
                    String mensaje = "";
                    if(rst.getString(11).equals("1")){
                        mensaje = "Desea entregar los viáticos de: \n";
                    }
                    if(rst.getString(11).equals("4")){
                        mensaje = "Desea legalizar los viáticos de: \n";
                    }
                    mensaje += rst.getString(2).toUpperCase()+" "+rst.getString(3).toUpperCase()+" "+rst.getString(4).toUpperCase()+" "+rst.getString(5).toUpperCase()+"\n";
                    mensaje += "Con destino: "+rst.getString(7).toUpperCase()+"\n";
                    int suma = 0, bandera = 0;
                    while(r.next()){
                        String det = organizarFrase(r.getString(1));
                        mensaje += det+": $"+formato.format(r.getInt(2) * r.getInt(3))+"\n";
                        suma += (r.getInt(2)*r.getInt(3));
                        if(r.getString(4).equals("4")){
                            bandera = 1;
                        }
                    }
                    mensaje += "____________________________________\n";
                    mensaje += "TOTAL: $"+formato.format(suma);
                    ban = 2;
                    CtrlViaticos ctrl = new CtrlViaticos();
                    ConsultaViaticos modViatico = new ConsultaViaticos();                   

                    
                    int estado = 2;
                    stop();                    
                    
                    int resp = JOptionPane.showConfirmDialog(null, mensaje, "Confirmación de viáticos", JOptionPane.YES_NO_OPTION);
                    switch(resp){
                        case JOptionPane.YES_OPTION:
                            ctrl.cancelarViatico(rst.getInt(1), estado, this.viaticos, modViatico, rst.getString(11));
                            if(bandera == 1){
                               ctrl.cambiarEstadoAutorizacion(rst.getString(10), "4"); 
                            }                            
                            Reclutador.clear();
                            setTemplate(null);
                            this.viaticos.txtAreaViaticos.setText(""); 
                            start();
                        break;
                        case JOptionPane.NO_OPTION:
                            Reclutador.clear();
                            setTemplate(null);
                            this.viaticos.txtAreaViaticos.setText(""); 
                            start();
                        break; 
                        case JOptionPane.CLOSED_OPTION:
                            Reclutador.clear();
                            setTemplate(null);
                            this.viaticos.txtAreaViaticos.setText(""); 
                            start();
                        break;
                    }
                }
            //Si no encuentra alguna huella correspondiente al nombre lo indica con un mensaje
            } 
            if(ban == 1){
                stop();
                JOptionPane.showMessageDialog(null, "No se encontraron resultados para \nla huella ingresada.", "Verificacion de huella", JOptionPane.ERROR_MESSAGE);
                stop();
                setTemplate(null);
                start();
            }
        }else{
            stop();
            JOptionPane.showMessageDialog(null, "No existen registros programados en la lista", "Verificacion de huella", JOptionPane.INFORMATION_MESSAGE);
            Reclutador.clear();
            
            setTemplate(null);
            //txtArea.setText(""); 
            start();
        }
    } catch (SQLException e) {
        //Si ocurre un error lo indica en la consola
        System.err.println("Error al verificar los datos de la huella. \n" + e);
    }
   }  
    
    public String organizarFrase(String cadena){
        String words[] = cadena.split(" ");
        int numPal = words.length;
        String cad = "";
        int c = 5;
        if(numPal > 0){
           for(int i = 0; i<numPal; i++){
               if(i == c){
                   cad += words[i]+"\n ";
                   c += 5;
               }else{
                   cad += words[i]+" ";
               }
           }
        }
       return cad;
    }
        
  
}
