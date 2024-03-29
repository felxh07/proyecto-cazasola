/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aulavirtual;

import Conexion.Conectar;
import aulavirtual.Alumno;
import aulavirtual.Profesor;
import aulavirtual.Profesor;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Alex
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    /*variable para almacenar el dni de la persona*/
    public String dni;
    
    /*Metodo para ingresar usuario*/
    public void Login(){
        try{
            Conectar cnx = new Conectar();
            Connection registros = cnx.getConnection();
            String sql="select *from Login where dni='"+jTextFieldUsu.getText()+"' and contraseña='"+jPasswordFieldContra.getText()+"'";
            String sqlProfesor="select *from Profesor where dni='"+jTextFieldUsu.getText()+"' ;";
            String sqlEstudiante="select *from Estudiante where dni='"+jTextFieldUsu.getText()+"' ;";
            PreparedStatement st = registros.prepareStatement(sql);
            ResultSet rs= st.executeQuery();
            
            if(rs.next()){
                /*ahora va a buscar si es profesor o estudiante*/
                PreparedStatement stP = registros.prepareStatement(sqlProfesor);
                ResultSet rsP = stP.executeQuery();
                if(rsP.next()){
                    dni=rsP.getString("dni");
                    Profesor p=new Profesor();
                    p.obtenerDni(dni);
                    p.setVisible(true);
                    dispose();
                }else{
                    PreparedStatement stE = registros.prepareStatement(sqlEstudiante);
                    ResultSet rsE= stE.executeQuery();
                    if(rsE.next()){
                        dni=rsE.getString("dni");
                        Alumno a=new Alumno();
                        a.obtenerDni(dni);
                        a.setVisible(true);
                        dispose();
                    }else{
                        JOptionPane.showInputDialog("No aparece en el sistema universitario");
                    }
                }
            }else{
                JOptionPane.showInputDialog("Valores incorrectos");
            }
                    
        }catch(SQLException e){
            System.out.println("Error"+e.getMessage());
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldUsu = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButtonLogin = new javax.swing.JButton();
        jPasswordFieldContra = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(new java.awt.Dimension(600, 400));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(null);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo_opt.png"))); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(20, 20, 346, 100);

        jTextFieldUsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldUsuActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldUsu);
        jTextFieldUsu.setBounds(130, 150, 168, 39);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-grupo-de-usuarios-hombre-hombre-filled-50.png"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(60, 130, 70, 70);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-candado-2-52.png"))); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(50, 210, 52, 71);

        jButtonLogin.setBackground(new java.awt.Color(204, 255, 204));
        jButtonLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/check.png"))); // NOI18N
        jButtonLogin.setText("INGRESAR");
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonLogin);
        jButtonLogin.setBounds(20, 310, 134, 39);
        jPanel1.add(jPasswordFieldContra);
        jPasswordFieldContra.setBounds(130, 230, 169, 37);

        jButton1.setBackground(new java.awt.Color(255, 204, 204));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ex.png"))); // NOI18N
        jButton1.setText("CANCELAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(190, 310, 134, 39);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/universidad_opt_opt_opt_opt.jpg"))); // NOI18N
        jPanel1.add(jLabel4);
        jLabel4.setBounds(0, 0, 380, 400);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        // TODO add your handling code here:
        Login();
    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void jTextFieldUsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldUsuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldUsuActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordFieldContra;
    private javax.swing.JTextField jTextFieldUsu;
    // End of variables declaration//GEN-END:variables
}
