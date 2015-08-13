/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Tela.java
 *
 * Created on 01-ago-2015, 10:04:35
 */
package proyectofinaldes;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class Proveedores extends javax.swing.JInternalFrame {
DefaultTableModel model;
    /** Creates new form Tela */
    public Proveedores() {
        initComponents();
        botonesiniciales();
        bloquear();
        cargarTabla("");
        btnNuevo.requestFocus();
        
         jTable2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if(jTable2.getSelectedRow()!=-1)
                {
                    int fila = jTable2.getSelectedRow();
                    txtCodigo.setText(jTable2.getValueAt(fila, 0).toString());
                    txtNombre.setText(jTable2.getValueAt(fila, 1).toString());
                    txtDireccion.setText(jTable2.getValueAt(fila, 2).toString());
                    txtTelefono.setText(jTable2.getValueAt(fila, 3).toString());
                   
                    desbloquear();
                     txtCodigo.setEnabled(false);
                     btnCancelar.setEnabled(true);
                     btnIngresar.setEnabled(false);
                     btnEliminar.setEnabled(true);
                     btnNuevo.setEnabled(false);
                     btnActualizar.setEnabled(true);
                }
//                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    public void limpiar(){
        txtCodigo.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtNombre.setText("");
           
        
    }
    
  
    public void bloquear(){
       txtCodigo.setEnabled(false);
        txtDireccion.setEnabled(false);
        txtTelefono.setEnabled(false);
        txtNombre.setEnabled(false);
    }
    
    public void desbloquear(){
         txtCodigo.setEnabled(true);
        txtDireccion.setEnabled(true);
        txtTelefono.setEnabled(true);
        txtNombre.setEnabled(true);
    
    }
    
    
    public void botonesiniciales(){
        btnNuevo.setEnabled(true);
        btnIngresar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnSalir.setEnabled(true);
        
    }
    
    public void botonnuevo(){
        desbloquear();
        limpiar();
        btnNuevo.setEnabled(false);
        btnIngresar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnEliminar.setEnabled(false);
        btnSalir.setEnabled(true);
    }
    
    public void botoncancelar(){
        botonesiniciales();
        limpiar();
        bloquear();
    }
    
    
    
    public void guardar(){
         if (txtCodigo.getText().isEmpty()) {
             JOptionPane.showMessageDialog(null, "Debe ingresar un Codigo");
            txtCodigo.requestFocus();
        }
        else if (txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un nombre");
            txtNombre.requestFocus();
        }
        else if (txtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar una Dirección");
            txtDireccion.requestFocus();
        }
        else if (txtTelefono.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un Telefono de Contacto");
            txtTelefono.requestFocus();
        }
         else
        {
      try {
            Conexion cc =new Conexion();
          Connection cn=cc.conexion();
           String sql="insert into proveedores(CODPRO,NOMPRO,DIRPRO,TELPRO) values('"+txtCodigo.getText().toUpperCase().trim()+"','"+txtNombre.getText().toUpperCase().trim()+"','"+txtDireccion.getText().toUpperCase().trim()+"','"+txtTelefono.getText().trim()+"') ";
//           System.out.println(sql);
           
           PreparedStatement psd=cn.prepareStatement(sql);
           int n=psd.executeUpdate();
                   if(n>0)
                   {
                     JOptionPane.showMessageDialog(null, "Se inserto Correctamente");  
                       cargarTabla("");
                       botonesiniciales();
                       limpiar();
                   }
        } catch (SQLException ex) {
            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    public void cargarTabla(String dato)
    {
        String[]titulos={"CODIGO", "NOMBRE", "DSCRIPCIÓN", "TELÉFONO"};
        String[]registros=new String[4];
        Conexion cc = new Conexion();
        Connection cn = cc.conexion();
        model=new DefaultTableModel(null, titulos);
        String sql="";
        sql="select * from proveedores where codpro like'%"+dato+"%' order by codpro";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            while (rs.next()){
                registros[0]=rs.getString("codpro");
                registros[1]=rs.getString("nompro");
                registros[2]=rs.getString("dirpro");
                registros[3]=rs.getString("telpro");
                model.addRow(registros);
            }
            jTable2.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
            }
    
     public void actualizar(){
            if (txtCodigo.getText().isEmpty()) {
             JOptionPane.showMessageDialog(null, "Debe ingresar un Codigo");
            txtCodigo.requestFocus();
        }
        else if (txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un nombre");
            txtNombre.requestFocus();
        }
        else if (txtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar una Dirección");
            txtDireccion.requestFocus();
        }
        else if (txtTelefono.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un Telefono de Contacto");
            txtTelefono.requestFocus();
        }
         else
        {
        Conexion cc = new Conexion();
        Connection cn = cc.conexion();
                String sql="";
                sql="update proveedores set nompro='"+txtNombre.getText().trim()+"',"
            + "dirpro='"+txtDireccion.getText().trim()+"',"
            + "telpro="+txtTelefono.getText().trim()+ "where codpro='"+txtCodigo.getText().trim()+"'";
        try {
                PreparedStatement psd=cn.prepareStatement(sql);
                int n=psd.executeUpdate();
                if(n>0)
                {
                    JOptionPane.showMessageDialog(null, "Se actualizo correctamente");
                    cargarTabla("");
                    botonesiniciales();
                       limpiar();
                }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        }
           }
     
     public void borrar(){   
        if(JOptionPane.showConfirmDialog(null, "Estas seguro que deseas borrar el dato", "Borrar Registro", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
    {
    Conexion cc = new Conexion();
    Connection cn = cc.conexion();
    String sql="";
    sql="delete from proveedores where codpro='"+txtCodigo.getText()+"'";
        try {
            PreparedStatement psd=cn.prepareStatement(sql);
            int n=psd.executeUpdate();
            if(n>0)
            {
              //  JOptionPane.showMessageDialog(null, "Se borro correctamente");
                cargarTabla("");
                botonesiniciales();
                       limpiar();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
     }
     
     private void IngersarPorTabla(){
        int tuple=jTable2.getSelectedRow();
        String sql="";
        if (tuple!=-1) {
           
            sql="update proveedores set nompro='"+jTable2.getValueAt(tuple, 1).toString().trim().toUpperCase()+ "',dirpro='"+
                jTable2.getValueAt(tuple, 2).toString().trim().toUpperCase()+"',telpro='"+jTable2.getValueAt(tuple, 3).toString().trim().toUpperCase()+
                    "' where codpro='"+jTable2.getValueAt(tuple, 0).toString().trim()+"'";
            
                
        
        try {
           Conexion cc = new Conexion();
    Connection cn = cc.conexion();
            PreparedStatement psw=cn.prepareStatement(sql);            
            int n=psw.executeUpdate();
            
            if (n>0) {
                JOptionPane.showMessageDialog(this, "Actualizacion correcta");
               cargarTabla ("");
               botonesiniciales();
                       limpiar();
              
               
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        }
    }
     
      public void buscarClaveprimaria(){
         Conexion cc = new Conexion();
    Connection cn = cc.conexion();
             String sql="";
                     sql="SELECT COUNT(*) as contar from proveedores where codpro='"+txtCodigo.getText().toUpperCase()+"'";
             
        try {
            Statement psd= cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
           
           while (rs.next()){
             int contar1=rs.getInt("contar");
             if(contar1>0){
                JOptionPane.showMessageDialog(null, "Codigo no puede ser el mismo");
                txtCodigo.setText("");
                txtCodigo.requestFocus();
               
           }
                    
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        txtNombre = new ComponentesPropios.txtLetrasMayusculas();
        txtDireccion = new ComponentesPropios.txtLetrasMayusculas();
        txtTelefono = new ComponentesPropios.txtEntero();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnNuevo = new org.edisoncor.gui.button.ButtonTask();
        btnIngresar = new org.edisoncor.gui.button.ButtonTask();
        btnActualizar = new org.edisoncor.gui.button.ButtonTask();
        btnEliminar = new org.edisoncor.gui.button.ButtonTask();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnCancelar = new org.edisoncor.gui.button.ButtonTask();
        jLabel10 = new javax.swing.JLabel();
        btnSalir = new org.edisoncor.gui.button.ButtonTask();
        jLabel11 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PROVEEDORES");

        jLabel1.setText("Nombre");

        jLabel3.setText("Código");

        jLabel4.setText("Dirección");

        txtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoFocusLost(evt);
            }
        });

        jLabel5.setText("Teléfono");

        jTextField5.setText("Buscar Proveedores");
        jTextField5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField5FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField5FocusLost(evt);
            }
        });
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField5KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField5KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/new_file.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnIngresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/insertar.png"))); // NOI18N
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar.png"))); // NOI18N
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/remove.png"))); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel6.setText("Nuevo");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel7.setText(" Insertar");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel8.setText("Eliminar");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel9.setText("Actualizar");

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancel.png"))); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel10.setText("Cancelar");

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sali.jpg"))); // NOI18N
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel11.setText("Salir");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10))
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnActualizar, 0, 0, Short.MAX_VALUE)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel11))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
// TODO add your handling code here:
    guardar();  
    botonesiniciales();
    bloquear();
}//GEN-LAST:event_btnIngresarActionPerformed

private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
// TODO add your handling code here:
    actualizar();
    botonesiniciales();
    bloquear();
}//GEN-LAST:event_btnActualizarActionPerformed

private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
// TODO add your handling code here:
    
    borrar();
bloquear();
botonesiniciales();
}//GEN-LAST:event_btnEliminarActionPerformed

private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
// TODO add your handling code here:
    this.dispose();
}//GEN-LAST:event_btnSalirActionPerformed

private void jTextField5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField5FocusGained
// TODO add your handling code here:
    jTextField5.setText("");
}//GEN-LAST:event_jTextField5FocusGained

private void jTextField5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField5FocusLost
// TODO add your handling code here:
    if(jTextField5.getText().isEmpty()){
        jTextField5.setText("Buscar Proveedor");
    }
}//GEN-LAST:event_jTextField5FocusLost

private void jTextField5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_jTextField5KeyPressed

private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyReleased
// TODO add your handling code here:
        cargarTabla(jTextField5.getText());
}//GEN-LAST:event_jTextField5KeyReleased

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        // TODO add your handling code here:
        IngersarPorTabla();
    }//GEN-LAST:event_jTable2KeyPressed

    private void txtCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFocusLost
        // TODO add your handling code here:
        buscarClaveprimaria();
    }//GEN-LAST:event_txtCodigoFocusLost

private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
// TODO add your handling code here:
    botonnuevo();
}//GEN-LAST:event_btnNuevoActionPerformed

private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
// TODO add your handling code here:
    botoncancelar();
}//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    
     
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Proveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Proveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Proveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Proveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Proveedores().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonTask btnActualizar;
    private org.edisoncor.gui.button.ButtonTask btnCancelar;
    private org.edisoncor.gui.button.ButtonTask btnEliminar;
    private org.edisoncor.gui.button.ButtonTask btnIngresar;
    private org.edisoncor.gui.button.ButtonTask btnNuevo;
    private org.edisoncor.gui.button.ButtonTask btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField txtCodigo;
    private ComponentesPropios.txtLetrasMayusculas txtDireccion;
    private ComponentesPropios.txtLetrasMayusculas txtNombre;
    private ComponentesPropios.txtEntero txtTelefono;
    // End of variables declaration//GEN-END:variables
}
