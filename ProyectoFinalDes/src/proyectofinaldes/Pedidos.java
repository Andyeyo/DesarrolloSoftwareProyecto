/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ventas.java
 *
 * Created on 12-ago-2015, 10:03:18
 */
package proyectofinaldes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author PC017
 */
public class Pedidos extends javax.swing.JInternalFrame {

    /** Creates new form ventas */
   
 DefaultTableModel model;
    public Pedidos() {
        
        initComponents();
        inicio();
        TableColumnModel columnModel = jTable1.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(75);
        columnModel.getColumn(1).setPreferredWidth(235);
        columnModel.getColumn(2).setPreferredWidth(75);
        columnModel.getColumn(3).setPreferredWidth(75);
        columnModel.getColumn(4).setPreferredWidth(75);
        boxproveedor.setEditable(true);
        AutoCompleteDecorator.decorate(boxproveedor);
        boxTela.setEditable(true);
        AutoCompleteDecorator.decorate(boxTela);
        cargarCodigoProveedor();
        cargarCodigoTela();
        txtProv.setEditable(false);
        txtTel.setEditable(false);
        txtTotal.setEditable(false);
        txtIva.setEditable(false);
        txtSubtotal.setEditable(false);
//             btnFinal.setEnabled(true);
        
    }
    
    public void inicio(){
        boxTela.setEnabled(false);
        boxproveedor.setEnabled(false);
        btnBuscar.setEnabled(false);
        txtIva.setEnabled(false);
        txtProv.setEnabled(false);
        txtTotal.setEnabled(false);
        txtCantidad.setEnabled(false);
        btnTotal.setEnabled(false);
        btnAñadir.setEnabled(false);
        jTable1.setEnabled(false);
        txtSubtotal.setEnabled(false);
        btnFinal.setEnabled(false);
        txtTel.setEnabled(false);
       btnBorrar.setEnabled(false);
        
    }
    
    public void botonPedido(){
      
        boxTela.setEnabled(true);
        boxproveedor.setEnabled(true);
        btnBuscar.setEnabled(true);
        txtIva.setEnabled(false);
        txtProv.setEnabled(true);
        txtTotal.setEnabled(false);
        txtCantidad.setEnabled(true);
        btnTotal.setEnabled(false);
        btnAñadir.setEnabled(false);
        jTable1.setEnabled(false);
        txtSubtotal.setEnabled(false);
        btnFinal.setEnabled(false);
        txtTel.setEnabled(true);
        
    
    }
    
    public void guardarPed(){
      try {
            Conexion cc =new Conexion();
          Connection cn=cc.conexion();
//          String fec=new SimpleDateFormat("yyyy-mm-dd").format(jDateChooser1.getDate());
           String sql="insert into pedido(FECHORPED,TOTALPED) values(CURRENT_DATE,0)";
           
           System.out.println(sql);
                    
           PreparedStatement psd=cn.prepareStatement(sql);
           int n=psd.executeUpdate();
                   if(n>0)
                   {
//                     JOptionPane.showMessageDialog(null, "Se inserto Correctamente");  
                   }
        } catch (SQLException ex) {
            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
String num,fec;
    public void recuperardatos(){
       
        Conexion cc = new Conexion();
        Connection cn = cc.conexion();
        String sql="";
        sql="select numped,fechorped from pedido where numped=(select max(numped) from pedido)";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            while (rs.next()){
                num=rs.getString("numped");
                fec=rs.getString("fechorped");
                NPedido.setText(num);
                fecha.setText(fec);
                
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
    
    }
            
    public void pedidosinicio(){
        
    }
            
    
    String nombre,nombre1, precio;
    public void cargarNombreTel(String dato){
        Conexion cc = new Conexion();
        Connection cn = cc.conexion();
        String sql="";
        sql="select nomtel,costmet from tela where codtel like'%"+dato+"%'";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            while (rs.next()){
                nombre1=rs.getString("nomtel");
                precio=String.valueOf((float) Math.rint(rs.getFloat("costmet")*100)/100);
                
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
    }
    
    
    public void cargarNombrePro(String dato){
        Conexion cc = new Conexion();
        Connection cn = cc.conexion();
        String sql="";
        sql="select nompro from proveedores where codpro like'%"+dato+"%'";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            while (rs.next()){
                nombre=rs.getString("nompro");
                
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
    }
    public void cargarCodigoProveedor(){
        boxproveedor.removeAllItems();
          Conexion cc = new Conexion();
        Connection cn = cc.conexion();
         String sql="";
         sql="select codpro from proveedores";
        try {
            Statement psd= cn.createStatement();
            ResultSet rs= psd.executeQuery(sql);
            boxproveedor.addItem("");
             while (rs.next()) {
                boxproveedor.addItem(rs.getObject("codpro"));
               // jComboBox1.setModel(modeloCombo);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    
     public void cargarCodigoTela(){
        boxTela.removeAllItems();
          Conexion cc = new Conexion();
        Connection cn = cc.conexion();
         String sql="";
         sql="select codtel from tela";
        try {
            Statement psd= cn.createStatement();
            ResultSet rs= psd.executeQuery(sql);
            boxTela.addItem("");
             while (rs.next()) {
                boxTela.addItem(rs.getObject("codtel"));
               // jComboBox1.setModel(modeloCombo);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    
    public void guardarDeta(){
        Conexion cc = new Conexion();
        Connection cn = cc.conexion();
        String sql = "";
       sql = "insert into detallepedido(numpedper, cantped, codtel, codpro) values("+NPedido.getText()+",'"+txtCantidad.getText()+"','"+boxTela.getSelectedItem().toString()
               .trim()+"','"+boxproveedor.getSelectedItem().toString().trim()+"')";
            System.out.println(sql);
        try {
            PreparedStatement psd = cn.prepareStatement(sql);
            int n = psd.executeUpdate();
            if (n > 0) {
               float subtotal=Float.valueOf(precio)*Integer.valueOf(txtCantidad.getText().toString().trim());
        DefaultTableModel model1 = (DefaultTableModel) jTable1.getModel();
        model1.addRow(new Object[]{boxproveedor.getSelectedItem().toString().trim(),boxTela.getSelectedItem().toString().trim() , precio, txtCantidad.getText().toString().trim(),Math.rint((Float.valueOf(precio)*Integer.valueOf(txtCantidad.getText().toString().trim()))*100)/100});
        cargarCodigoTela();
        cargarCodigoProveedor();
        txtProv.setText("");
        txtTel.setText("");
        txtCantidad.setText("");
        jTable1.setEnabled(true);
        btnTotal.setEnabled(true);
        txtTotal.setEnabled(true);
        txtIva.setEnabled(true);
        txtSubtotal.setEnabled(true);
        btnFinal.setEnabled(false);
        btnAñadir.setEnabled(false);
        btnBorrar.setEnabled(true);
        
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Ingresar");
            btnAñadir.setEnabled(false);
        }
    }
    
    public void calculatot() {
         float cont=0;
    for (int i = 0; i < jTable1.getRowCount(); i++) {
        cont=cont+Float.valueOf(jTable1.getValueAt(i, 4).toString());
    }
    txtSubtotal.setText(String.valueOf(cont));
    
    txtIva.setText(String.valueOf(Math.rint((cont*0.12)*100)/100));
    txtTotal.setText(String.valueOf(Math.rint((cont+(cont*0.12))*100)/100));
 
    }
      public void botonBuscar(){
          if(boxproveedor.getSelectedItem().toString().equals("")){
              txtProv.setText("Elija un Proveedor");
          }else{
             cargarNombrePro(boxproveedor.getSelectedItem().toString().trim());
        txtProv.setText(nombre);  
          }
           
 if(boxTela.getSelectedItem().toString().equals("")){
     txtTel.setText("Elija Una Tela");
 }else{
     cargarNombreTel(boxTela.getSelectedItem().toString().trim());
        txtTel.setText(nombre1+"   "+precio+"$");
 }
        
        if(txtProv.getText().toString().equals("Elija un Proveedor")||txtTel.getText().toString().equals("Elija Una Tela")){
              btnAñadir.setEnabled(false);
        }else{
            btnAñadir.setEnabled(true);
        }
        
      }      
     private void IngersarPorTabla(){
        int tuple=jTable1.getSelectedRow();
        String sql="";
        if (tuple!=-1) {
           
            sql="update detallepedido set cantped="+jTable1.getValueAt(tuple, 3).toString().trim().toUpperCase()+ "where numpedper="+NPedido.getText().toString()+"and codpro='"+jTable1.getValueAt(tuple, 0).toString().trim()+"'and codtel='"+jTable1.getValueAt(tuple, 1).toString().trim()+"'";
            
            System.out.println(sql);       
        
        try {
           Conexion cc = new Conexion();
    Connection cn = cc.conexion();
            PreparedStatement psw=cn.prepareStatement(sql);            
            int n=psw.executeUpdate();
            
            if (n>0) {
                JOptionPane.showMessageDialog(this, "Actualizacion correcta");
                txtIva.setText("");
                txtSubtotal.setText("");
                txtTotal.setText("");
                btnFinal.setEnabled(false);
               
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        }
    }
    public void cambioTabla(){
        int fila;
            fila=jTable1.getSelectedRow();
            if (fila!=-1){
    System.out.println(fila);
        float temp1=Float.valueOf(jTable1.getValueAt(fila, 2).toString());
        int temp2=Integer.valueOf(jTable1.getValueAt(fila, 3).toString());
        Object resul=(Object) (Math.rint((temp1*temp2)*100)/100);
        System.out.println(String.valueOf(temp1)+ String.valueOf(temp2)+ String.valueOf(resul));
        System.out.println(jTable1.getValueAt(fila, 2));
        jTable1.setValueAt(resul,jTable1.getSelectedRow(),4);
        
  }
    }
    
    public void borrardetalle() {
          if(JOptionPane.showConfirmDialog(null, "Estas seguro que deseas borrar el detalle", "Borrar Registro", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
    {
     int tuple=jTable1.getSelectedRow();
        String sql="";
        if (tuple!=-1) {
           
            sql="delete from detallepedido  where cantped="+jTable1.getValueAt(tuple, 3).toString().trim().toUpperCase()+ " and numpedper="+NPedido.getText().toString()+" and codpro='"+jTable1.getValueAt(tuple, 0).toString().trim()+"' and codtel='"+jTable1.getValueAt(tuple, 1).toString().trim()+"'";
            
            System.out.println(sql);       
        
        try {
           Conexion cc = new Conexion();
    Connection cn = cc.conexion();
            PreparedStatement psw=cn.prepareStatement(sql);            
            int n=psw.executeUpdate();
            
            if (n>0) {
                JOptionPane.showMessageDialog(this, "Eliminacion correcta");
                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel(); //TableProducto es el nombre de mi tabla ;) 
                dtm.removeRow(jTable1.getSelectedRow()); 
//               botonesiniciales();
//                       limpiar();
//              
               
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        }
    }
}
    
     public void buscarClaveprimaria(){
          if(txtCantidad.getText().toString().equals("")||Integer.valueOf(txtCantidad.getText())<=0){
         JOptionPane.showMessageDialog(null, "Debe Ingesar una cantidad mayor a cero");
       }else{
          Conexion cc = new Conexion();
    Connection cn = cc.conexion();
             String sql="";
                     sql="SELECT COUNT(*) as contar from detallepedido where numpedper="+NPedido.getText().toString()+"and codpro='"+boxproveedor.getSelectedItem().toString().trim()+"'and codtel='"+boxTela.getSelectedItem().toString().trim()+"'";
             
        try {
            Statement psd= cn.createStatement();
            ResultSet rs=psd.executeQuery(sql);
           
           while (rs.next()){
             int contar1=rs.getInt("contar");
             if(contar1>0){
                JOptionPane.showMessageDialog(null, "Los datos ya existen si desea puede actualizar las cantidad de metros mediante la tabla");
                             
           }else{
              guardarDeta();      
             }
                  
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }}
        
    }
   
     
     public void actualizarTotal(){
           if(Float.valueOf(txtTotal.getText().toString())<=0){
           JOptionPane.showMessageDialog(null, "El pedido no puede teneer un valor de Cero o menos de Cero");
           }else{
           
        Conexion cc = new Conexion();
        Connection cn = cc.conexion();
                String sql="";
//                sql="update pedido set totalped='"txtTotal.getText().toString()"'where codtel="+NPedido.getText()+"";
                sql="update pedido set totalped="+txtTotal.getText().toString().trim()+" where numped="+NPedido.getText().trim()+" ";
        try {
                PreparedStatement psd=cn.prepareStatement(sql);
                int n=psd.executeUpdate();
                if(n>0)
                {
                  jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Proveedor", "Código Tela", "Precio", "Cantidad", "Subtotal"
            }
        ));
                  btnPedido.setEnabled(true);
                  NPedido.setText("");
                  fecha.setText("");
                inicio();
                txtIva.setText("");
                txtSubtotal.setText("");
                txtTotal.setText("");
                }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
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
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        btnPedido = new javax.swing.JButton();
        fecha = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        NPedido = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtProv = new javax.swing.JTextField();
        boxproveedor = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        boxTela = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        txtTel = new javax.swing.JTextField();
        txtCantidad = new ComponentesPropios.txtEntero();
        btnAñadir = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtIva = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnTotal = new org.edisoncor.gui.button.ButtonTask();
        btnSalir = new javax.swing.JButton();
        btnFinal = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PEDIDO TELA");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Articulos Facturados"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Proveedor", "Código Tela", "Precio", "Cantidad", "Subtotal"
            }
        ));
        jTable1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTable1PropertyChange(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
        );

        jLabel10.setText("N° Venta:");

        btnPedido.setText("Pedido");
        btnPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPedidoActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setText("Fecha:");

        NPedido.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(34, 34, 34)
                .addComponent(NPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(96, 96, 96)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(142, 142, 142)
                .addComponent(btnPedido))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPedido, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(NPedido, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel3.setText("Cantidad Metros");

        jLabel1.setText("Código Proveedor");

        jLabel2.setText("Nombre Proveedor");

        boxproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxproveedorActionPerformed(evt);
            }
        });
        boxproveedor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                boxproveedorFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                boxproveedorFocusLost(evt);
            }
        });
        boxproveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                boxproveedorKeyPressed(evt);
            }
        });

        jLabel17.setText("Código Tela");

        boxTela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxTelaActionPerformed(evt);
            }
        });
        boxTela.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                boxTelaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                boxTelaFocusLost(evt);
            }
        });
        boxTela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                boxTelaKeyPressed(evt);
            }
        });

        jLabel18.setText("Nombre Tela");

        btnAñadir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/comprar.png"))); // NOI18N
        btnAñadir.setText("Añadir");
        btnAñadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirActionPerformed(evt);
            }
        });

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/find.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(75, 75, 75)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(boxTela, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(41, 41, 41)
                                        .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(58, 58, 58)
                                        .addComponent(jLabel18))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(boxproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtProv, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAñadir)))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(boxproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAñadir)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel18))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(boxTela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnBuscar)
                        .addGap(27, 27, 27)))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("TKS");

        jLabel5.setText("Av. 22 de Julio y Antonio Clavijo, Calle #16");

        jLabel6.setText("RUC: 1802652865001");

        jLabel7.setText("Telefono: 0999236342");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(jLabel4))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)))
                .addGap(239, 239, 239))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel11.setText("Subtotal");

        txtSubtotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubtotalActionPerformed(evt);
            }
        });

        jLabel12.setText("IVA 12%");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("TOTAL");

        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(255, 0, 0));
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);

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
                            .addComponent(txtIva, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                            .addComponent(txtSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel12)
                    .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel13)
                    .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
                .addContainerGap())
        );

        btnTotal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/currency_dollar_green.png"))); // NOI18N
        btnTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTotalActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sali.jpg"))); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnFinal.setText("FINALIZAR PEDIDO");
        btnFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalActionPerformed(evt);
            }
        });

        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/remove.png"))); // NOI18N
        btnBorrar.setText("Eliminar");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnFinal))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBorrar)
                        .addGap(39, 39, 39)
                        .addComponent(btnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(btnTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jTable1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
// TODO add your handling code here:  
   cambioTabla();
    //}
}//GEN-LAST:event_jTable1PropertyChange

private void btnPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPedidoActionPerformed
// TODO add your handling code here:
    guardarPed();
    recuperardatos();
    btnPedido.setEnabled(false);
   botonPedido();
    
}//GEN-LAST:event_btnPedidoActionPerformed

private void txtSubtotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubtotalActionPerformed
// TODO add your handling code here:
    
}//GEN-LAST:event_txtSubtotalActionPerformed

private void btnTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTotalActionPerformed
// TODO add your handling code here:
   calculatot();
   btnFinal.setEnabled(true);
    
}//GEN-LAST:event_btnTotalActionPerformed

private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
// TODO add your handling code here:
    this.dispose();
}//GEN-LAST:event_btnSalirActionPerformed

    private void boxTelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_boxTelaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxTelaKeyPressed

    private void boxTelaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_boxTelaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_boxTelaFocusLost

    private void boxTelaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_boxTelaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_boxTelaFocusGained

    private void boxTelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxTelaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxTelaActionPerformed

    private void boxproveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_boxproveedorKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_boxproveedorKeyPressed

    private void boxproveedorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_boxproveedorFocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_boxproveedorFocusLost

    private void boxproveedorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_boxproveedorFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_boxproveedorFocusGained

    private void boxproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxproveedorActionPerformed
        // TODO add your handling code here:
        // jButton2.requestFocus();
        //    System.out.println("ya esta");
    }//GEN-LAST:event_boxproveedorActionPerformed

    private void btnAñadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirActionPerformed
        // TODO add your handling code here:
             
           buscarClaveprimaria();
       
       
    }//GEN-LAST:event_btnAñadirActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
      botonBuscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalActionPerformed
        // TODO add your handling code here:
        actualizarTotal();
        
        
        
    }//GEN-LAST:event_btnFinalActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
       IngersarPorTabla();
    }//GEN-LAST:event_jTable1KeyPressed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:
        borrardetalle();
    }//GEN-LAST:event_btnBorrarActionPerformed

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
            java.util.logging.Logger.getLogger(Pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Pedidos().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel NPedido;
    private javax.swing.JComboBox boxTela;
    private javax.swing.JComboBox boxproveedor;
    private javax.swing.JButton btnAñadir;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnFinal;
    private javax.swing.JButton btnPedido;
    private javax.swing.JButton btnSalir;
    private org.edisoncor.gui.button.ButtonTask btnTotal;
    private javax.swing.JLabel fecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private ComponentesPropios.txtEntero txtCantidad;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtProv;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtTel;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
