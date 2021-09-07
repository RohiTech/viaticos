/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paneles;

import Controlador.CtrlMunicipios;
import Controlador.CtrlUsuarios;
import Modelo.Gasolina;
import Modelo.Municipio;
import Modelo.Parqueadero;
import Modelo.Peaje;
import principal.diaEditGasolina;
import principal.diaEditMunicipio;
import principal.diaEditParqueadero;
import principal.diaEditPeaje;
import principal.diaNuevoViatico;
import javax.swing.JOptionPane;
import principal.diaEditGasolina;
import principal.diaEditMunicipio;
import principal.diaEditParqueadero;
import principal.diaEditPeaje;
import principal.diaNuevoViatico;

/**
 *
 * @author AUXINVTIC
 */
public class pnlAjustes extends javax.swing.JPanel {

    
    
    public pnlAjustes() {
        initComponents();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtMunicipio = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtValorHosp = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtValorAlim = new javax.swing.JTextField();
        btnGuardarMun = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMunicipios = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaPeajes = new javax.swing.JTable();
        btnNuevoPeaje = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaParqueaderos = new javax.swing.JTable();
        btnNuevoParqueadero = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaGasolina = new javax.swing.JTable();
        btnNuevoGasolina = new javax.swing.JButton();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Agregar nuevo municipio", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Código:");

        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Nombre:");

        txtMunicipio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Costo hospedaje por dia:");

        txtValorHosp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Costo alimentación por dia:");

        txtValorAlim.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnGuardarMun.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnGuardarMun.setForeground(new java.awt.Color(102, 102, 102));
        btnGuardarMun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/diskette.png"))); // NOI18N
        btnGuardarMun.setText("Guardar");
        btnGuardarMun.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarMun.setIconTextGap(6);
        btnGuardarMun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarMunActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMunicipio, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigo)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel4)
                                        .addComponent(txtValorAlim, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                                        .addComponent(txtValorHosp))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel1)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(38, 38, 38))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(btnGuardarMun)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtValorHosp, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtValorAlim, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnGuardarMun)
                .addContainerGap())
        );

        tablaMunicipios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaMunicipios.setRowHeight(30);
        tablaMunicipios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMunicipiosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaMunicipios);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información de viáticos para peajes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(51, 51, 51))); // NOI18N

        tablaPeajes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "#", "NOMBRE", "VALOR", "EDITAR"
            }
        ));
        tablaPeajes.setRowHeight(25);
        tablaPeajes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaPeajesMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaPeajes);

        btnNuevoPeaje.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNuevoPeaje.setForeground(new java.awt.Color(102, 102, 102));
        btnNuevoPeaje.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/peaje.png"))); // NOI18N
        btnNuevoPeaje.setText("Nuevo");
        btnNuevoPeaje.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoPeaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoPeajeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(btnNuevoPeaje)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNuevoPeaje)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información de viáticos para parqueaderos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(51, 51, 51))); // NOI18N

        tablaParqueaderos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaParqueaderos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "#", "NOMBRE", "VALOR", "EDITAR"
            }
        ));
        tablaParqueaderos.setRowHeight(25);
        tablaParqueaderos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaParqueaderosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaParqueaderos);

        btnNuevoParqueadero.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNuevoParqueadero.setForeground(new java.awt.Color(102, 102, 102));
        btnNuevoParqueadero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/parking.png"))); // NOI18N
        btnNuevoParqueadero.setText("Nuevo");
        btnNuevoParqueadero.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoParqueadero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoParqueaderoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNuevoParqueadero)
                .addGap(115, 115, 115))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNuevoParqueadero)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información de viáticos para gasolina", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(51, 51, 51))); // NOI18N

        tablaGasolina.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaGasolina.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "#", "NOMBRE", "VALOR", "EDITAR"
            }
        ));
        tablaGasolina.setRowHeight(25);
        tablaGasolina.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaGasolinaMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablaGasolina);

        btnNuevoGasolina.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNuevoGasolina.setForeground(new java.awt.Color(102, 102, 102));
        btnNuevoGasolina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/gas.png"))); // NOI18N
        btnNuevoGasolina.setText("Nuevo");
        btnNuevoGasolina.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoGasolina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoGasolinaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNuevoGasolina)
                .addGap(112, 112, 112))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNuevoGasolina)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(71, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tablaMunicipiosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMunicipiosMouseClicked
        int fila = tablaMunicipios.rowAtPoint(evt.getPoint());
        int columna = tablaMunicipios.columnAtPoint(evt.getPoint());
        
        if(columna == 5){
            validarSeleccion(fila);
        }
    }//GEN-LAST:event_tablaMunicipiosMouseClicked

    private void tablaPeajesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPeajesMouseClicked
        int fila = tablaPeajes.rowAtPoint(evt.getPoint());
        int columna = tablaPeajes.columnAtPoint(evt.getPoint());
        
        if(columna == 4){
            validarSeleccionPeaje(fila);
        }
    }//GEN-LAST:event_tablaPeajesMouseClicked

    private void tablaParqueaderosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaParqueaderosMouseClicked
        int fila = tablaParqueaderos.rowAtPoint(evt.getPoint());
        int columna = tablaParqueaderos.columnAtPoint(evt.getPoint());
        
        if(columna == 4){
            validarSeleccionParqueadero(fila);
        }
    }//GEN-LAST:event_tablaParqueaderosMouseClicked

    private void tablaGasolinaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaGasolinaMouseClicked
        int fila = tablaGasolina.rowAtPoint(evt.getPoint());
        int columna = tablaGasolina.columnAtPoint(evt.getPoint());
        
        if(columna == 4){
            validarSeleccionGasolina(fila);
        }
    }//GEN-LAST:event_tablaGasolinaMouseClicked

    private void btnGuardarMunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarMunActionPerformed
        guardarMunicipio();
    }//GEN-LAST:event_btnGuardarMunActionPerformed

    private void btnNuevoPeajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoPeajeActionPerformed
        String id = "2";
        diaNuevoViatico dialog = new diaNuevoViatico(new java.awt.Frame(), true, id, this );
        dialog.setVisible(true);
    }//GEN-LAST:event_btnNuevoPeajeActionPerformed

    private void btnNuevoParqueaderoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoParqueaderoActionPerformed
        String id = "1";
        diaNuevoViatico dialog = new diaNuevoViatico(new java.awt.Frame(), true, id, this );
        dialog.setVisible(true);
    }//GEN-LAST:event_btnNuevoParqueaderoActionPerformed

    private void btnNuevoGasolinaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoGasolinaActionPerformed
        String id = "3";
        diaNuevoViatico dialog = new diaNuevoViatico(new java.awt.Frame(), true, id, this );
        dialog.setVisible(true);
    }//GEN-LAST:event_btnNuevoGasolinaActionPerformed

    private void guardarMunicipio(){ 
        CtrlMunicipios ctrl = new CtrlMunicipios();
        //ctrl.registrarMunicipio(this);
    }
    
       
    private void validarSeleccion(int fila) {
        Municipio municipio = new Municipio();
        
        municipio.setCodigo(tablaMunicipios.getValueAt(fila, 1).toString());
        municipio.setMunicipio(tablaMunicipios.getValueAt(fila, 2).toString());
        municipio.setValor_hospedaje(Integer.parseInt(tablaMunicipios.getValueAt(fila, 3).toString()));
        municipio.setValor_alimentacion(Integer.parseInt(tablaMunicipios.getValueAt(fila, 4).toString()));
        
//        diaEditMunicipio dialog = new diaEditMunicipio(new java.awt.Frame(), true, municipio, this);
//        dialog.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardarMun;
    private javax.swing.JButton btnNuevoGasolina;
    private javax.swing.JButton btnNuevoParqueadero;
    private javax.swing.JButton btnNuevoPeaje;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    public javax.swing.JTable tablaGasolina;
    public javax.swing.JTable tablaMunicipios;
    public javax.swing.JTable tablaParqueaderos;
    public javax.swing.JTable tablaPeajes;
    public javax.swing.JTextField txtCodigo;
    public javax.swing.JTextField txtMunicipio;
    public javax.swing.JTextField txtValorAlim;
    public javax.swing.JTextField txtValorHosp;
    // End of variables declaration//GEN-END:variables

    private void validarSeleccionPeaje(int fila) {
        Peaje peaje = new Peaje();
        
        peaje.setId_peaje(tablaPeajes.getValueAt(fila, 0).toString());
        peaje.setDescripcion(tablaPeajes.getValueAt(fila, 2).toString());
        peaje.setValor(Integer.parseInt(tablaPeajes.getValueAt(fila, 3).toString()));
        
//        diaEditPeaje dialog = new diaEditPeaje(new java.awt.Frame(), true, peaje, this);
//        dialog.setVisible(true);
    }

    private void validarSeleccionParqueadero(int fila) {
        Parqueadero parq = new Parqueadero();
        
        parq.setId_parqueadero(tablaParqueaderos.getValueAt(fila, 0).toString());
        parq.setDescripcion(tablaParqueaderos.getValueAt(fila, 2).toString());
        parq.setValor(Integer.parseInt(tablaParqueaderos.getValueAt(fila, 3).toString()));
        
//        diaEditParqueadero dialog = new diaEditParqueadero(new java.awt.Frame(), true, parq, this);
//        dialog.setVisible(true);
    }
    
    private void validarSeleccionGasolina(int fila) {
        Gasolina gas = new Gasolina();
        
        gas.setId_gasolina(tablaGasolina.getValueAt(fila, 0).toString());
        gas.setDescripcion(tablaGasolina.getValueAt(fila, 2).toString());
        gas.setValor(Integer.parseInt(tablaGasolina.getValueAt(fila, 3).toString()));
        
//        diaEditGasolina dialog = new diaEditGasolina(new java.awt.Frame(), true, gas, this);
//        dialog.setVisible(true);
    }
}
