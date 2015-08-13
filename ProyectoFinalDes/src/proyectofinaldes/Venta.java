/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Venta.java
 *
 * Created on 30-jul-2015, 13:02:21
 */
package proyectofinaldes;

/**
 *
 * @author Andres
 */
import java.awt.Component;
import java.awt.Container;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;


public class Venta extends javax.swing.JInternalFrame {

    /** Creates new form Venta */
    
    
    
    
    DefaultTableModel model;
    String vendedor_id;
    public Venta(String id) {
        
        initComponents();
        vendedor_id=id;
        jTable1.getColumn("Codigo").setCellEditor(new Editor_name(new JCheckBox()));
        jTable1.getColumn("Descripción").setCellEditor(new Editor_name(new JCheckBox()));
        jTable1.getColumn("Precio").setCellEditor(new Editor_name(new JCheckBox()));
        jTable1.getColumn("Subtotal").setCellEditor(new Editor_name(new JCheckBox()));
        
        jTextField2.setEnabled(false);
        jTextField4.setEnabled(false);
        jTextField5.setEnabled(false);
        buttonTask6.setEnabled(false);
        cargarNumeroFactura();
        inicial();
        bloquearPanel(jPanel3, false);
        TableColumnModel columnModel = jTable1.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(75);
        columnModel.getColumn(1).setPreferredWidth(235);
        columnModel.getColumn(2).setPreferredWidth(75);
        columnModel.getColumn(3).setPreferredWidth(75);
        columnModel.getColumn(4).setPreferredWidth(75);
        jComboBox1.setEditable(true);
        AutoCompleteDecorator.decorate(this.jComboBox1);
        cargarCodigoProductos();
        
             
    }
    
    
  public void inicial(){
      bloquearPanel(jPanel1, false);
      bloquearPanel(jPanel2, false);
      buttonTask4.setEnabled(false);
      buttonTask5.setEnabled(false);
      
  }
  
  
  public void desbloquear(){
      bloquearPanel(jPanel1, true);
      bloquearPanel(jPanel2, true);
      buttonTask4.setEnabled(true);
      buttonTask5.setEnabled(true);
  }
    
    
    public static void bloquearPanel(Container panel, boolean bloqueo){
        Component[] allComps = panel.getComponents();
        for(Component com : allComps)
        {
            com.setEnabled(bloqueo);
            if(com instanceof Container)
                bloquearPanel((Container) com, bloqueo);
        }
    }
    
    
    String nombre_cargarEmpleado,apellido_cargarEmpleado;
    public void cargarNombreEmpleado(String dato){
        Conexion cc = new Conexion();
        Connection cn = cc.conexion();
        String sql="";
        sql="select nomcli,apecli from cliente where cicli='"+dato+"'";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            while (rs.next()){
                nombre_cargarEmpleado=rs.getString("nomcli");
                apellido_cargarEmpleado=rs.getString("apecli");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
    }
    
    
    public void cargarNumeroFactura(){
       Conexion cc = new Conexion();
       Connection cn = cc.conexion(); 
       String var="";
       int numer;
       String sql1="";
        sql1="Select  MAX(numven) as var FROM venta";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs=psd.executeQuery(sql1);
            while (rs.next()){
                var=rs.getString("var");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        } 
        numer=Integer.valueOf(var)+1;
        jTextField5.setText(String.valueOf(numer));
    }
    
    
    
    
    String nombre, precio, talla;
    public void cargarNombre(String dato){
        Conexion cc = new Conexion();
        Connection cn = cc.conexion();
        String sql="";
        sql="select nomtex, preunit, taltex from textil where codtex like'%"+dato+"%'";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            while (rs.next()){
                nombre=rs.getString("nomtex");
                precio=String.valueOf((float) Math.rint(rs.getFloat("preunit")*100)/100);
                talla=rs.getString("taltex");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
    }
    
    
    
    public void cargarCodigoProductos(){
        jComboBox1.removeAllItems();
         Conexion cc = new Conexion();
         Connection cn = cc.conexion();
         String sql="";
         sql="select codtex FROM textil";
        try {
            Statement psd= cn.createStatement();
            ResultSet rs= psd.executeQuery(sql);
            jComboBox1.addItem("PRODUCTO");
             while (rs.next()) {
                jComboBox1.addItem(rs.getObject("codtex"));
               // jComboBox1.setModel(modeloCombo);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    
    
    public void guardar(){
        Conexion cc = new Conexion();
        Connection cn = cc.conexion();
        String sql = "";
        sql = "insert into venta(fechorven,totalven, ciencper, cicliper) values(now(),"+0+",'"+vendedor_id+"','"+jTextField1.getText().toString().trim()+"')";
            System.out.println(sql);
        try {
            PreparedStatement psd = cn.prepareStatement(sql);
            int n = psd.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Ingrese detalles");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
       

    
    public void restarStock(int a, String cod){
        Conexion cc = new Conexion();
        Connection cn = cc.conexion();
        String sql="";
                sql="update textil set stockmed=stockmed-"+a+" where codtex='"+cod+"'";
                System.out.println(sql);
        try {
                PreparedStatement psd=cn.prepareStatement(sql);
                int n=psd.executeUpdate();
                if(n>0)
                {
                    //JOptionPane.showMessageDialog(null, "Se actualizo correctamente");
                }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
   
    
    public void sumarStock(int a, String cod){
        Conexion cc = new Conexion();
        Connection cn = cc.conexion();
        String sql="";
                sql="update textil set stockmed=stockmed+"+a+" where codtex="+cod+"";
        try {
                PreparedStatement psd=cn.prepareStatement(sql);
                int n=psd.executeUpdate();
                if(n>0)
                {
                    //JOptionPane.showMessageDialog(null, "Se actualizo correctamente");
                }
        } catch (Exception ex) {
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        buttonTask1 = new org.edisoncor.gui.button.ButtonTask();
        buttonTask3 = new org.edisoncor.gui.button.ButtonTask();
        jTextField3 = new ComponentesPropios.txtEntero();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField1 = new ComponentesPropios.txtEntero();
        buttonTask6 = new org.edisoncor.gui.button.ButtonTask();
        buttonTask2 = new org.edisoncor.gui.button.ButtonTask();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        buttonTask4 = new org.edisoncor.gui.button.ButtonTask();
        buttonTask5 = new org.edisoncor.gui.button.ButtonTask();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Factura");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Articulos Facturados"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripción", "Precio", "Cantidad", "Subtotal"
            }
        ));
        jTable1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTable1PropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel3.setText("Cantidad");

        jLabel1.setText("Codigo");

        jLabel2.setText("Nombre");

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jComboBox1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComboBox1FocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboBox1FocusGained(evt);
            }
        });
        jComboBox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox1KeyPressed(evt);
            }
        });

        buttonTask1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/shopping_bag.png"))); // NOI18N
        buttonTask1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTask1ActionPerformed(evt);
            }
        });

        buttonTask3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/find.png"))); // NOI18N
        buttonTask3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTask3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonTask3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(buttonTask1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(buttonTask3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(buttonTask1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel4.setText("TKS");

        jLabel5.setText("Av. 22 de Julio y Antonio Clavijo, Calle #16");

        jLabel6.setText("RUC: 1802652865001");

        jLabel7.setText("Telefono: 0999236342");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jLabel4))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7))
        );

        jLabel8.setText("CI/RUC:");

        jLabel9.setText("Nombre Cliente");

        jLabel10.setText("N° Venta");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        buttonTask6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/arrow_down.png"))); // NOI18N
        buttonTask6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTask6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(buttonTask6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonTask6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        buttonTask2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/usuario2.png"))); // NOI18N
        buttonTask2.setText("Cliente");
        buttonTask2.setDescription("Ingreso, modificacion");
        buttonTask2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTask2ActionPerformed(evt);
            }
        });

        jLabel11.setText("Subtotal");

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jLabel12.setText("IVA 12%");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel13.setText("TOTAL");

        jTextField8.setFont(new java.awt.Font("Tahoma", 1, 18));
        jTextField8.setForeground(new java.awt.Color(255, 0, 0));
        jTextField8.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel14.setText("$");

        jLabel15.setText("$");

        jLabel16.setText("$");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE))
                        .addGap(64, 64, 64)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14)))
                    .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel11)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel12)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel13)
                    .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
                .addContainerGap())
        );

        buttonTask4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/currency_dollar_green.png"))); // NOI18N
        buttonTask4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTask4ActionPerformed(evt);
            }
        });

        buttonTask5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/remove.png"))); // NOI18N
        buttonTask5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTask5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(buttonTask2, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addGap(42, 42, 42)
                .addComponent(buttonTask4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(209, 209, 209)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(191, 191, 191))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonTask5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(buttonTask5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonTask2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonTask4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jComboBox1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox1FocusGained
// TODO add your handling code here:
     
     
}//GEN-LAST:event_jComboBox1FocusGained

private void jComboBox1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox1FocusLost
// TODO add your handling code here:
   
}//GEN-LAST:event_jComboBox1FocusLost

private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
// TODO add your handling code here:
   // jButton2.requestFocus();
    
}//GEN-LAST:event_jComboBox1ActionPerformed

private void jComboBox1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox1KeyPressed
// TODO add your handling code here:
    
}//GEN-LAST:event_jComboBox1KeyPressed

private void jTable1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
// TODO add your handling code here:
     
     int fila;
     fila=jTable1.getSelectedRow();
     
     if (fila != -1) {
         System.out.println("probando el property change");
        float temp1 = Float.valueOf(jTable1.getValueAt(fila, 2).toString());
        int temp2 = Integer.valueOf(jTable1.getValueAt(fila, 3).toString());
        Object resul = (Object) (Math.rint((temp1 * temp2) * 100) / 100);
        System.out.println(String.valueOf(temp1) + String.valueOf(temp2) + String.valueOf(resul));
        System.out.println(jTable1.getValueAt(fila, 2));
        jTable1.setValueAt(resul, jTable1.getSelectedRow(), 4);

        
        String sql="";
        sql="update detalleventas set  cantven="+jTable1.getValueAt(fila, 3).toString().trim().toUpperCase()
                 + " where codtexven='"+jTable1.getValueAt(fila, 0).toString()+"' and numvenper="+jTextField5.getText().toString()+"";
         System.out.println(sql);
        try {
            Conexion cc = new Conexion();
            Connection cn = cc.conexion();
            PreparedStatement psw=cn.prepareStatement(sql);            
            int n=psw.executeUpdate();
            if (n>0) {
                //JOptionPane.showMessageDialog(null, "Actualizacion correcta");
                //botonesiniciales();
                //limpiar();
                //bloquear();
                
            }
           }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
     }
        
    
     
     //IngresarPorTabla();
    
    
    //}
}//GEN-LAST:event_jTable1PropertyChange

private void buttonTask2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTask2ActionPerformed
// TODO add your handling code here:
    try {
        menu m= new menu("");
      
                Cliente au=new Cliente();
                  JDesktopPane desktopPane = getDesktopPane();
desktopPane.add(au);//add f1 to desktop pane
//au.setVisible(true);
//                m.jDesktopPane1.add(au);
                au.show();
                //au.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    
}//GEN-LAST:event_buttonTask2ActionPerformed

private void buttonTask3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTask3ActionPerformed
// TODO add your handling code here:  
    cargarNombre(jComboBox1.getSelectedItem().toString().trim());
    jTextField2.setText(nombre+"   "+precio+" $ ");
    jTextField3.setEnabled(true);
    jTextField3.requestFocus();
}//GEN-LAST:event_buttonTask3ActionPerformed

private void buttonTask1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTask1ActionPerformed
// TODO add your handling code here:
    //float subtotal=Float.valueOf(precio)*Integer.valueOf(jTextField3.getText().toString().trim());
    if(jTextField3.getText().toString().equals("") || Integer.valueOf(jTextField3.getText())<=0){
         JOptionPane.showMessageDialog(this, "Asegurese de haber ingresado producto y cantidad");       
        
}else{
        String temp=nombre+" talla #"+talla;
    DefaultTableModel model1 = (DefaultTableModel) jTable1.getModel();
    model1.addRow(new Object[]{jComboBox1.getSelectedItem().toString().trim(), temp, precio, jTextField3.getText().toString().trim(),Math.rint((Float.valueOf(precio)*Integer.valueOf(jTextField3.getText().toString().trim()))*100)/100});
    
        Conexion cc = new Conexion();
        Connection cn = cc.conexion();
        
       String var="";
       String sql1="";
        sql1="Select  MAX(numven) as var FROM venta";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs=psd.executeQuery(sql1);
            while (rs.next()){
                var=rs.getString("var");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        } 
        
        
        String sql = "";
        sql = "insert into detalleventas values('"+jComboBox1.getSelectedItem().toString().trim()+"',"+jTextField3.getText().toString().trim()+","+var+")";
            System.out.println(sql);
        try {
            PreparedStatement psd = cn.prepareStatement(sql);
            int n = psd.executeUpdate();
            if (n > 0) {
                //JOptionPane.showMessageDialog(null, "se inserto correctamente");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        buttonTask4.setEnabled(true);
     }
}//GEN-LAST:event_buttonTask1ActionPerformed

private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
// TODO add your handling code here:
    
}//GEN-LAST:event_jTextField6ActionPerformed

public void actualizarTotal(String dato,String ven){
        Conexion cc = new Conexion();
        Connection cn = cc.conexion();
        String sql="";
                sql="update venta set totalven="+dato+" where numven="+ven+"";
        try {
                PreparedStatement psd=cn.prepareStatement(sql);
                int n=psd.executeUpdate();
                if(n>0)
                {
                  //  JOptionPane.showMessageDialog(null, "Se actualizo correctamente");
                }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
}



public void borrarDetalle(){   
        if(JOptionPane.showConfirmDialog(null, "Estas seguro que deseas borrar el dato", "Borrar Registro", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
    {
     int filadetalle;
     filadetalle=jTable1.getSelectedRow();
    Conexion cc = new Conexion();
    Connection cn = cc.conexion();
    String sql="";
    if(filadetalle!=-1){
    sql="delete from detalleventas where numvenper="+jTextField5.getText()+" and codtexven='"+jTable1.getValueAt(filadetalle, 0)+"'";
        System.out.println(sql);
        try {
            PreparedStatement psd=cn.prepareStatement(sql);
            int n=psd.executeUpdate();
            if(n>0)
            {
                
                ((DefaultTableModel)jTable1.getModel()).removeRow(filadetalle);
                //jTable1.setModel(model);
                //JOptionPane.showMessageDialog(null, "Se borro correctamente");
               // cargarTabla("");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
   }
}


private void buttonTask4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTask4ActionPerformed
// TODO add your handling code here:
    float cont=0;
    for (int i = 0; i < jTable1.getRowCount(); i++) {
        cont=cont+Float.valueOf(jTable1.getValueAt(i, 4).toString());
        restarStock(Integer.valueOf(jTable1.getValueAt(i, 3).toString()), jTable1.getValueAt(i, 0).toString());
    }
    jTextField6.setText(String.valueOf(Math.rint((cont)*100)/100));
    jTextField7.setText(String.valueOf(Math.rint((cont*0.12)*100)/100));
    jTextField8.setText(String.valueOf(Math.rint((cont+(cont*0.12))*100)/100));
    String numero_venta,total_ven;
    numero_venta=jTextField5.getText().toString();
    total_ven=jTextField8.getText().toString();
    actualizarTotal(total_ven,numero_venta);

    JOptionPane.showMessageDialog(null, "TOTAL:  "+jTextField8.getText().toString());
    this.dispose();
   Venta v= new Venta(vendedor_id);
   v.show();
}//GEN-LAST:event_buttonTask4ActionPerformed

private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
// TODO add your handling code here:
     cargarNombreEmpleado(jTextField1.getText().toString().trim());
    jTextField4.setText(nombre_cargarEmpleado+" "+apellido_cargarEmpleado);
    
    if(jTextField1.getText().toString().trim().isEmpty()){
        JOptionPane.showMessageDialog(null, "Ingrese un RUC/CI");
        jTextField1.requestFocus();
        //buttonTask6.setEnabled(false);
    }else{
        if(jTextField4.getText().toString().trim().equals("null null")) {
            JOptionPane.showMessageDialog(null, "Ingrese cliente");
            jTextField1.requestFocus();
        //buttonTask6.setEnabled(false);
        } else{
    buttonTask6.requestFocus();
    buttonTask6.setEnabled(true);
    }
  }
    
    System.out.println(jTextField4.getText().toString());
    
    
    
}//GEN-LAST:event_jTextField1ActionPerformed

private void buttonTask5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTask5ActionPerformed
// TODO add your handling code here:
    borrarDetalle();
}//GEN-LAST:event_buttonTask5ActionPerformed

private void buttonTask6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTask6ActionPerformed
// TODO add your handling code here:
    bloquearPanel(jPanel5, false);
    desbloquear();
    buttonTask4.setEnabled(false);
    jPanel2.setEnabled(true);
    guardar();
    
}//GEN-LAST:event_buttonTask6ActionPerformed

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
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Venta("").setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonTask buttonTask1;
    private org.edisoncor.gui.button.ButtonTask buttonTask2;
    private org.edisoncor.gui.button.ButtonTask buttonTask3;
    private org.edisoncor.gui.button.ButtonTask buttonTask4;
    private org.edisoncor.gui.button.ButtonTask buttonTask5;
    private org.edisoncor.gui.button.ButtonTask buttonTask6;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private ComponentesPropios.txtEntero jTextField1;
    private javax.swing.JTextField jTextField2;
    private ComponentesPropios.txtEntero jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables
}
