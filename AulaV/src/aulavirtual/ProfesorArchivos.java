/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aulavirtual;

import Conexion.Conectar;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.sql.Blob;

/**
 *
 * @author Alex
 */
public class ProfesorArchivos extends javax.swing.JFrame {

    /**
     * Creates new form ProfesorArchivos
     */
    public ProfesorArchivos() {
        initComponents();
    }
    
    
    /*INICIA MI CODIGO*/
    private String codCursoEstudianteSemestre ="MATRI0001";
    private String codAsistencia;
    
    //variables cuando selecciono algo de la tabla
    private String nombreSeleccionado;
    private String descripcionSeleccionado;

    public String getCodCursoEstudianteSemestre() {
        return codCursoEstudianteSemestre;
    }

    public void setCodCursoEstudianteSemestre(String codCursoEstudianteSemestre) {
        this.codCursoEstudianteSemestre = codCursoEstudianteSemestre;
    }

    public String getCodAsistencia() {
        return codAsistencia;
    }

    public void setCodAsistencia(String codAsistencia) {
        this.codAsistencia = codAsistencia;
    }

    public String getNombreSeleccionado() {
        return nombreSeleccionado;
    }

    public void setNombreSeleccionado(String nombreSeleccionado) {
        this.nombreSeleccionado = nombreSeleccionado;
    }

    public String getDescripcionSeleccionado() {
        return descripcionSeleccionado;
    }

    public void setDescripcionSeleccionado(String descripcionSeleccionado) {
        this.descripcionSeleccionado = descripcionSeleccionado;
    }
    
    
    
    //metodos
    public void obtenerCodigoAsistencia(String codigoSemana){
        try{
            Conectar cnx = new Conectar();
            Connection registro = cnx.getConnection();
            String sql="select *from Asistencia\n" +
            "where codCursoEstudianteSemestre=\""+getCodCursoEstudianteSemestre()+"\" and codSemana=\""+codigoSemana+"\"; ";
            PreparedStatement st = registro.prepareStatement(sql);
            ResultSet rs= st.executeQuery();
            if(rs.next()){
                setCodAsistencia(rs.getString(1));
            }else{
                JOptionPane.showInputDialog("No se encontro la asistencia");
            }
                    
        }catch(SQLException e){
            System.out.println("Error"+e.getMessage());
        }
    }
    
    public void seleccionarSemena(){
        switch((String)jComboBoxSemana.getSelectedItem()){
            case "Semana 1":
                obtenerCodigoAsistencia("01");
                break;
            case "Semana 2":
                obtenerCodigoAsistencia("02");
                break;
            case "Semana 3":
                obtenerCodigoAsistencia("03");
                break;
            case "Semana 4":
                obtenerCodigoAsistencia("04");
                break;
            case "Semana 5":
                obtenerCodigoAsistencia("05");
                break;
            case "Semana 6":
                obtenerCodigoAsistencia("06");
                break;
            case "Semana 7":
                obtenerCodigoAsistencia("07");
                break;
            case "Semana 8":
                obtenerCodigoAsistencia("08");
                break;
            case "Semana 9":
                obtenerCodigoAsistencia("09");
                break;
            case "Semana 10":
                obtenerCodigoAsistencia("10");
                break;
            case "Semana 11":
                obtenerCodigoAsistencia("11");
                break;
            case "Semana 12":
                obtenerCodigoAsistencia("12");
                break;
            case "Semana 13":
                obtenerCodigoAsistencia("13");
                break;
            case "Semana 14":
                obtenerCodigoAsistencia("14");
                break;
            case "Semana 15":
                obtenerCodigoAsistencia("15");
                break;
            case "Semana 16":
                obtenerCodigoAsistencia("16");
        } 
    }
    
    public void refrescar(){
        
        DefaultTableModel modelo=(DefaultTableModel) jTableArchivos.getModel();
        modelo.setRowCount(0);//limpiar el modelo
        try{
            Conectar cnx = new Conectar();
            Connection registros = cnx.getConnection();
            String sql="select Archivo.nombreArchivo, Archivo.descripcion from Archivo\n" +
            "where Archivo.codAsistencia='"+getCodAsistencia()+"'";
            PreparedStatement st = registros.prepareStatement(sql);
            ResultSet rs= st.executeQuery();
            while(rs.next()){
                Vector v=new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                modelo.addRow(v);
            }  
            jTableArchivos.setModel(modelo);
        }catch(SQLException e){
            System.out.println("Error"+e.getMessage());
        }
        
    }
    
    public void eliminarArchivo(){
        try{
            Conectar cnx = new Conectar();
            Connection registro = cnx.getConnection();
            String sql="delete from Archivo\n" +
            "where nombreArchivo=\""+getNombreSeleccionado()+"\" and descripcion=\""+getDescripcionSeleccionado()+"\";";
            PreparedStatement st = registro.prepareStatement(sql);
            st.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error"+e.getMessage());
        }
    }
    
    /*codigo para subir archivos*/
    private FileInputStream fis;/*ahi se va almacenar el flujo de datos del archivo*/
    private int longitudBytes;//longitud en bytes del archivo      
    public void selecionarArchivo(){//metodo para poder seleccionar el archivo
        JFileChooser se = new JFileChooser();
        se.setFileSelectionMode(JFileChooser.FILES_ONLY);//para selecionar solo archivos, no carpetas
        int estado = se.showOpenDialog(null);
        if(estado==JFileChooser.APPROVE_OPTION){//si el usuario dio aceptar
            
            try{
                fis = new FileInputStream(se.getSelectedFile());//archivo seleccionado
                this.longitudBytes = (int)se.getSelectedFile().length();//COMVIERTO A UN ENTERO SIMPEL
                
                 /*lo nuevo*/
                jTextFieldNombreArchivo.setText(String.valueOf(se.getSelectedFile().getName()));
                  /*lo nuevo*/
                JOptionPane.showMessageDialog(null, "se seleccionó el archivo");
                /*crear el icono*/
                //Image icono = ImageIO.read( se.getSelectedFile().getScaleInstance() );
                
            } catch (FileNotFoundException ex){
                ex.printStackTrace();
            }
        }
        
    }
    public void subirArchivo(FileInputStream archivo, int bytes){
        try{
            Conectar cnx = new Conectar();
            Connection archivosql = cnx.getConnection();
            String sqlarchivo="insert into Archivo(codAsistencia,nombreArchivo,descripcion,archivo) values(?,?,?,?);";
            PreparedStatement starchivo = archivosql.prepareStatement(sqlarchivo);
            starchivo.setString(1,getCodAsistencia());
            starchivo.setString(2,jTextFieldNombreArchivo.getText());
            starchivo.setString(3,jTextArea1.getText());
            starchivo.setBlob(4, fis, bytes);
            starchivo.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error: "+e.getMessage());
        }
    }
    /**/
    /*codigo para descargar archivo*/
    public void descargarArchivo(){
        
        try{
            Conectar cnx = new Conectar();
            Connection registro = cnx.getConnection();
            String sql="select *from Archivo\n" +
            "where nombreArchivo=\""+getNombreSeleccionado()+"\" and descripcion=\""+getDescripcionSeleccionado()+"\";";
            PreparedStatement st = registro.prepareStatement(sql);
            ResultSet rs= st.executeQuery();
            while(rs.next()){
                Blob blob = rs.getBlob(5);
                InputStream is = blob.getBinaryStream();
                almacenarDiscoDuro(is,getNombreSeleccionado());
            }  
        }catch(SQLException e){
            System.out.println("Error"+e.getMessage());
        }
    }
    public void almacenarDiscoDuro(InputStream x,String nombre){
        JFileChooser de = new JFileChooser();
        de.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//para selecionar solo archivos, no carpetas
        int estado = de.showOpenDialog(null);
        if(estado==JFileChooser.APPROVE_OPTION){//si el usuario dio aceptar
            File directorio=de.getSelectedFile();
            String ruta=directorio.getAbsolutePath();
            File fichero = new File(ruta+"/"+nombre);
           
            //BufferedInputStream in = new BufferedInputStream(x);
            OutputStream out =null;
            try{
                out = new FileOutputStream(fichero);
                byte[] bytes=new byte[8095];
                int len=0;
                while( (len=x.read(bytes))>0 ){
                    out.write(bytes, 0, len);
                }
                //out.flush();
                out.close();
                x.close();
                JOptionPane.showMessageDialog(null, "Se decargó su archivo");
            }catch(FileNotFoundException e){
                System.out.println("Error"+e.getMessage());
            }catch(IOException e){
                System.out.println("Error"+e.getMessage());
            }
        }
        
    }
    
    /*TERMINA MI CODIGO*/
    
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jComboBoxSemana = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableArchivos = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButtonAgregarArchivo = new javax.swing.JButton();
        jPanelCabecera = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnAtraz = new javax.swing.JButton();
        jTextFieldNombreArchivo = new javax.swing.JTextField();
        jButtonDescarga = new javax.swing.JButton();
        jButtonEliminarArchivo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jComboBoxSemana.setBackground(new java.awt.Color(102, 102, 102));
        jComboBoxSemana.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semana 1", "Semana 2", "Semana 3", "Semana 4", "Semana 5", "Semana 6", "Semana 7", "Semana 8", "Semana 9", "Semana 10", "Semana 11", "Semana 12", "Semana 13", "Semana 14", "Semana 15", "Semana 16" }));

        jButton1.setBackground(new java.awt.Color(102, 102, 102));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/seleccionar.png"))); // NOI18N
        jButton1.setText("Seleccionar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTableArchivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Descripcion"
            }
        ));
        jTableArchivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableArchivosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableArchivos);

        jButton2.setBackground(new java.awt.Color(102, 102, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/subirArchivo.png"))); // NOI18N
        jButton2.setText("Subir Archivo");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jButtonAgregarArchivo.setBackground(new java.awt.Color(102, 102, 102));
        jButtonAgregarArchivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-add-new-30.png"))); // NOI18N
        jButtonAgregarArchivo.setText("Agregar Archivo");
        jButtonAgregarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarArchivoActionPerformed(evt);
            }
        });

        jPanelCabecera.setBackground(new java.awt.Color(255, 204, 153));
        jPanelCabecera.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo-fiis.png"))); // NOI18N

        javax.swing.GroupLayout jPanelCabeceraLayout = new javax.swing.GroupLayout(jPanelCabecera);
        jPanelCabecera.setLayout(jPanelCabeceraLayout);
        jPanelCabeceraLayout.setHorizontalGroup(
            jPanelCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelCabeceraLayout.setVerticalGroup(
            jPanelCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCabeceraLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnAtraz.setBackground(new java.awt.Color(102, 102, 102));
        btnAtraz.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/retornar.png"))); // NOI18N
        btnAtraz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrazActionPerformed(evt);
            }
        });

        jTextFieldNombreArchivo.setEnabled(false);

        jButtonDescarga.setBackground(new java.awt.Color(102, 102, 102));
        jButtonDescarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/descargar.png"))); // NOI18N
        jButtonDescarga.setText("Descarga");
        jButtonDescarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDescargaActionPerformed(evt);
            }
        });

        jButtonEliminarArchivo.setBackground(new java.awt.Color(102, 102, 102));
        jButtonEliminarArchivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ex.png"))); // NOI18N
        jButtonEliminarArchivo.setText("Eliminar Archivo");
        jButtonEliminarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarArchivoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelCabecera, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonAgregarArchivo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jComboBoxSemana, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(jButton1)
                                .addGap(183, 183, 183)
                                .addComponent(btnAtraz, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButtonDescarga)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButtonEliminarArchivo)
                                        .addGap(3, 3, 3))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextFieldNombreArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))))
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanelCabecera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jComboBoxSemana, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAtraz, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonDescarga)
                            .addComponent(jButtonEliminarArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(jTextFieldNombreArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonAgregarArchivo)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAtrazActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrazActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnAtrazActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        seleccionarSemena();
        refrescar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonDescargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDescargaActionPerformed
        // TODO add your handling code here:
        descargarArchivo();
    }//GEN-LAST:event_jButtonDescargaActionPerformed

    private void jButtonEliminarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarArchivoActionPerformed
        // TODO add your handling code here:
        eliminarArchivo();
        refrescar();
    }//GEN-LAST:event_jButtonEliminarArchivoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        selecionarArchivo();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButtonAgregarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarArchivoActionPerformed
        // TODO add your handling code here:
        subirArchivo(fis,longitudBytes);
        refrescar();
        fis=null;
        longitudBytes=0;
        jTextFieldNombreArchivo.setText("");
        jTextArea1.setText("");
    }//GEN-LAST:event_jButtonAgregarArchivoActionPerformed

    private void jTableArchivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableArchivosMouseClicked
        // TODO add your handling code here:
        int seleccion = jTableArchivos.rowAtPoint(evt.getPoint()); //muestra el numero de la fila selecionada
        
        setNombreSeleccionado(String.valueOf(jTableArchivos.getValueAt(seleccion, 0)));
        setDescripcionSeleccionado(String.valueOf(jTableArchivos.getValueAt(seleccion, 1)));
    }//GEN-LAST:event_jTableArchivosMouseClicked

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
            java.util.logging.Logger.getLogger(ProfesorArchivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProfesorArchivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProfesorArchivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProfesorArchivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProfesorArchivos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtraz;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonAgregarArchivo;
    private javax.swing.JButton jButtonDescarga;
    private javax.swing.JButton jButtonEliminarArchivo;
    private javax.swing.JComboBox<String> jComboBoxSemana;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelCabecera;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableArchivos;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextFieldNombreArchivo;
    // End of variables declaration//GEN-END:variables
}
