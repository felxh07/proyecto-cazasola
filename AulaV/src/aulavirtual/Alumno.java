package aulavirtual;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Conexion.Conectar;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Blob;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;


/**
 *
 * @author Alex
 */
public class Alumno extends javax.swing.JFrame {

    /**
     * Creates new form Alumno
     */
    public Alumno() {
        initComponents();     
    }
    
    /*creo una variable donde se va a almacenar el dni del estudiante*/
    public String dniEstudiante;
    
    public void obtenerDni(String dni){
        dniEstudiante=dni;
    }
    
    /*variables*/
    private String codCursoEstudianteSemestre;
    private String codAsistencia;
    private String nombreCurso;
    
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

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
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
    
    
    
    
    /*Empieza*/
    public void Mostrar(){
    try{
    Conectar cnx = new Conectar();
    Connection registros = cnx.getConnection();
    String sql="Select  persona.nombre, estudiante.codEstudiante , persona.fechaNacimiento , escuela.nombreEscuela , persona.foto\n" +
    "from estudiante\n" +
    "inner join persona on estudiante.dni=persona.dni\n" +
    "inner join escuela on estudiante.codEscuela=escuela.codEscuela where persona.dni ='"+dniEstudiante+"';";
    PreparedStatement st = registros.prepareStatement(sql);
    ResultSet rs= st.executeQuery();
        while (rs.next()){ 
        jTextFieldAlumno.setText(rs.getString(1));
        jTextFieldCodigo.setText(rs.getString(2));
        jTextFieldEdad.setText(rs.getString(3));
        jTextFieldCarrera.setText(rs.getString(4));
        }
    }catch(SQLException e){
        System.out.println("Error"+e.getMessage());
    }
    }
    
    public void CargaCursos(){
        DefaultTableModel modelo=(DefaultTableModel) jTableCursos.getModel();
        modelo.setRowCount(0);//limpiar el modelo
        try{
            Conectar cnx = new Conectar();
            Connection registros = cnx.getConnection();
            String sql="select curso.nombre_curso, ciclo.nombreciclo\n" +
            "from curso_estudiantesemestre\n" +
            "inner join curso on  curso_estudiantesemestre.codCurso=curso.codCurso\n" +
            "inner join ciclo on curso.numCiclo=ciclo.numCiclo\n" +
            "where curso_estudiantesemestre.codSemestre='2019A' and curso_estudiantesemestre.dni='"+dniEstudiante+"'";
            PreparedStatement st = registros.prepareStatement(sql);
            ResultSet rs= st.executeQuery();
            while(rs.next()){
                Vector v=new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                modelo.addRow(v);
            }  
            jTableCursos.setModel(modelo);
        }catch(SQLException e){
            System.out.println("Error"+e.getMessage());
        }
        
    }
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
        
        
    public void refrescarCodigo(){
        try{
            Conectar cnx = new Conectar();
            Connection registros = cnx.getConnection();
            String sql="select curso_estudiantesemestre.codCursoEstudianteSemestre,curso.nombre_curso, ciclo.nombreciclo\n" +
"from curso_estudiantesemestre\n" +
"inner join curso on  curso_estudiantesemestre.codCurso=curso.codCurso\n" +
"inner join ciclo on curso.numCiclo=ciclo.numCiclo\n" +
"where curso_estudiantesemestre.codSemestre=\"2019A\" and curso_estudiantesemestre.dni=\""+dniEstudiante+"\"\n" +
"and curso.nombre_curso= \""+getNombreCurso()+"\"";
            PreparedStatement st = registros.prepareStatement(sql);
            ResultSet rs= st.executeQuery();
            while(rs.next()){
                setCodCursoEstudianteSemestre(rs.getString(1));
            }  
        }catch(SQLException e){
            System.out.println("Error"+e.getMessage());
        }
    }
    public void refrescar(){
        
        DefaultTableModel modelo=(DefaultTableModel) jTable2.getModel();
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
            jTable2.setModel(modelo);
        }catch(SQLException e){
            System.out.println("Error"+e.getMessage());
        }
        
    }
    
    
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
    public void descargarSilabu(){
        try{
            Conectar cnx = new Conectar();
            Connection registro = cnx.getConnection();
            String sql="select *from Silabu where codCursoEstudianteSemestre=\""+getCodCursoEstudianteSemestre()+"\";";
            PreparedStatement st = registro.prepareStatement(sql);
            ResultSet rs= st.executeQuery();
            while(rs.next()){
                String nombresilabu =rs.getString(2);
                Blob blob = rs.getBlob(3);
                InputStream is = blob.getBinaryStream();
                almacenarDiscoDuro(is,nombresilabu);
            }  
        }catch(SQLException e){
            System.out.println("Error"+e.getMessage());
        }
        
    }
    
    
    
    

    /*Termina
    */
    private FileInputStream fis;/*ahi se va almacenar el flujo de datos del archivo*/
    private int longitudBytes;//longitud en bytes del archivo
    
    /*Codigo de seleccionar foto*/
     public void seleccionarFoto(){//metodo para poder seleccionar foto
        JFileChooser se = new JFileChooser();
        se.setFileSelectionMode(JFileChooser.FILES_ONLY);//para selecionar foto
        int estado = se.showOpenDialog(null);
        if(estado==JFileChooser.APPROVE_OPTION){//
            
            try{
                fis = new FileInputStream(se.getSelectedFile());//foto seleccionada
                this.longitudBytes = (int)se.getSelectedFile().length();//longitud
                
                Image icono = ImageIO.read(se.getSelectedFile()).getScaledInstance(jLabelFoto.getWidth(),jLabelFoto.getHeight(), Image.SCALE_DEFAULT);
                jLabelFoto.setIcon(new ImageIcon(icono));
                jLabelFoto.updateUI();
                
            } catch (FileNotFoundException ex){ ex.printStackTrace();} 
            catch (IOException ex) { ex.printStackTrace();}
            }
    } 
     /*Fin de Codigo de seleccionar foto*/
     
     /*Codigo de subir foto*/
     public void subirFoto(FileInputStream foto, int longitud){
        try{
            Conectar cnx = new Conectar();
            Connection fotosql = cnx.getConnection();
            String sqlfoto="UPDATE persona  SET foto = ?" +  "WHERE dni = " + dniEstudiante;
            PreparedStatement stfoto = fotosql.prepareStatement(sqlfoto);
            stfoto.setBlob(1, fis, longitud);
            stfoto.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error: "+e.getMessage());
        }
    }
     /*Fin de codigo de subir foto*/
     
    //Mostrar foto
    public void mostrarFoto(){
        try{
            Conectar cnx = new Conectar();
            Connection registro = cnx.getConnection();
            String sql="select foto from persona where dni=\""+dniEstudiante+"\";";
            PreparedStatement st = registro.prepareStatement(sql);
            ResultSet rs= st.executeQuery();
            if(rs.next()){
                byte[] img = rs.getBytes(1);
                try{
                    
                    Image imagen = getImage(img, false);//este es el malo
                    JOptionPane.showMessageDialog(null,"llego al final");
                    if(imagen!=null){
                        Icon icon = new ImageIcon(imagen);
                        jLabelFoto.setIcon(icon);
                    }else{
                        JOptionPane.showMessageDialog(null,"No encontro foto");
                    }
                    jLabelFoto.updateUI();
                    
                }catch (IOException ex){
                    System.out.println("Error:" + ex);
                }
            }  
        }catch(SQLException e){
            System.out.println("Error"+e.getMessage());
        }
    }
    
    private Image getImage(byte[] bytes, boolean isThumbnail) throws IOException {

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator readers = ImageIO.getImageReadersByFormatName("png");
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis; // File or InputStream
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        if (isThumbnail) {
            param.setSourceSubsampling(4, 4, 0, 0);
        }
        return reader.read(0, param);

    }
    
    
    //para que me cargue la foto de la base de datos ESTE ES EL VERDADERO
    public ImageIcon getFoto(String id){//leer un flujo de imagen de una base de datos
        String sql="select foto from persona where dni=\""+id+"\";";
        ImageIcon li=null;
        InputStream is =null;
        try{
            Conectar cnx = new Conectar();
            Connection registro = cnx.getConnection();
            PreparedStatement st = registro.prepareStatement(sql);
            ResultSet rs= st.executeQuery();
            
            if(rs.next()){
                is = rs.getBinaryStream(1);
                BufferedImage bi=ImageIO.read(is);
                li=new ImageIcon(bi.getScaledInstance(jLabelFoto.getWidth(),jLabelFoto.getHeight(), Image.SCALE_DEFAULT));
            }
        }catch(SQLException e){
            
        }catch (IOException ex){
                    System.out.println("Error:" + ex);
        }
        
        
        return li;
    }
    
    
    
     
     /*fin de mostrar foto*/
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelInfoProf = new javax.swing.JPanel();
        jLabelFoto = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldAlumno = new javax.swing.JTextField();
        jTextFieldCodigo = new javax.swing.JTextField();
        jTextFieldEdad = new javax.swing.JTextField();
        jTextFieldCarrera = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanelActividad = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCursos = new javax.swing.JTable();
        jPanelCurso = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jComboBoxSemana = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTextFieldNomCurso = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanelInfoProf.setBackground(new java.awt.Color(255, 255, 204));
        jPanelInfoProf.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelFoto.setText("foto");
        jLabelFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelFotoMouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("ALUMNO :");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("CODIGO :");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("FECHA DE NACIMIENTO:");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("CARRERA:");

        jLabel5.setText("2019-A");

        jTextFieldAlumno.setEnabled(false);
        jTextFieldAlumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAlumnoActionPerformed(evt);
            }
        });

        jTextFieldCodigo.setEnabled(false);

        jTextFieldEdad.setEnabled(false);

        jTextFieldCarrera.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addGap(97, 97, 97)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldEdad, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                            .addComponent(jTextFieldCodigo)
                            .addComponent(jTextFieldAlumno)
                            .addComponent(jTextFieldCarrera))
                        .addGap(75, 75, 75)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        jPanel3.setBackground(new java.awt.Color(153, 255, 153));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo-fiis.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/unac_opt.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(38, 38, 38))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addContainerGap())
        );

        jPanelActividad.setBackground(new java.awt.Color(255, 255, 204));
        jPanelActividad.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registros de actividad", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 204));

        jTableCursos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cursos", "Ciclo"
            }
        ));
        jTableCursos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableCursosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableCursos);

        jPanelCurso.setBackground(new java.awt.Color(255, 255, 204));

        jButton2.setBackground(new java.awt.Color(102, 102, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/descargar.png"))); // NOI18N
        jButton2.setText("Descargar Silabu");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jComboBoxSemana.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semana 1", "Semana 2", "Semana 3", "Semana 4", "Semana 5", "Semana 6", "Semana 7", "Semana 8", "Semana 9", "Semana 10", "Semana 11", "Semana 12", "Semana 13", "Semana 14", "Semana 15", "Semana 16" }));

        jButton1.setBackground(new java.awt.Color(102, 102, 102));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/seleccionar.png"))); // NOI18N
        jButton1.setText("Seleccionar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Descripcion"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jTextFieldNomCurso.setEnabled(false);

        jButton4.setBackground(new java.awt.Color(102, 102, 102));
        jButton4.setForeground(new java.awt.Color(102, 102, 102));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/dsc_archivo.png"))); // NOI18N
        jButton4.setText("Descargar Archivo");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCursoLayout = new javax.swing.GroupLayout(jPanelCurso);
        jPanelCurso.setLayout(jPanelCursoLayout);
        jPanelCursoLayout.setHorizontalGroup(
            jPanelCursoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCursoLayout.createSequentialGroup()
                .addGroup(jPanelCursoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCursoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelCursoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCursoLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jTextFieldNomCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelCursoLayout.createSequentialGroup()
                                .addComponent(jComboBoxSemana, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelCursoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(139, Short.MAX_VALUE))
        );
        jPanelCursoLayout.setVerticalGroup(
            jPanelCursoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCursoLayout.createSequentialGroup()
                .addGroup(jPanelCursoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCursoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextFieldNomCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanelCursoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCursoLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBoxSemana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51))
                            .addGroup(jPanelCursoLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanelCursoLayout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(11, 11, 11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        jLabel6.setText("Nombre del curso");

        jButton3.setBackground(new java.awt.Color(102, 102, 102));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrarsesion.png"))); // NOI18N
        jButton3.setText("Cerrar Sesion");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelActividadLayout = new javax.swing.GroupLayout(jPanelActividad);
        jPanelActividad.setLayout(jPanelActividadLayout);
        jPanelActividadLayout.setHorizontalGroup(
            jPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelActividadLayout.createSequentialGroup()
                .addGroup(jPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelActividadLayout.createSequentialGroup()
                        .addGroup(jPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelActividadLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelActividadLayout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(55, 55, 55)
                        .addComponent(jPanelCurso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelActividadLayout.createSequentialGroup()
                        .addGap(286, 286, 286)
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelActividadLayout.setVerticalGroup(
            jPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelActividadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelActividadLayout.createSequentialGroup()
                        .addComponent(jPanelCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(22, Short.MAX_VALUE))
                    .addGroup(jPanelActividadLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(43, 43, 43))))
        );

        javax.swing.GroupLayout jPanelInfoProfLayout = new javax.swing.GroupLayout(jPanelInfoProf);
        jPanelInfoProf.setLayout(jPanelInfoProfLayout);
        jPanelInfoProfLayout.setHorizontalGroup(
            jPanelInfoProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInfoProfLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelInfoProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelInfoProfLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanelInfoProfLayout.createSequentialGroup()
                .addComponent(jPanelActividad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelInfoProfLayout.setVerticalGroup(
            jPanelInfoProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelInfoProfLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelInfoProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(11, 11, 11)
                .addComponent(jPanelActividad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanelInfoProf, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldAlumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAlumnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAlumnoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        Mostrar();
        //mostrarFoto();
        CargaCursos();
        
        /*lLO NUEVO QUE AGREGUE*/
        ImageIcon foto= getFoto(dniEstudiante);
        if(foto!=null){
            jLabelFoto.setIcon(foto);
        }else{
            jLabelFoto.setIcon(null);
        }
        jLabelFoto.updateUI();
        
    }//GEN-LAST:event_formWindowOpened


    
    private void jLabelFotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelFotoMouseClicked
        // TODO add your handling code here:
        seleccionarFoto(); 
        subirFoto(fis,longitudBytes);
        refrescar();
    }//GEN-LAST:event_jLabelFotoMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        descargarArchivo();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        int seleccion = jTable2.rowAtPoint(evt.getPoint()); //muestra el numero de la fila selecionada

        setNombreSeleccionado(String.valueOf(jTable2.getValueAt(seleccion, 0)));
        setDescripcionSeleccionado(String.valueOf(jTable2.getValueAt(seleccion, 1)));
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        refrescarCodigo();
        seleccionarSemena();
        refrescar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        refrescarCodigo();
        descargarSilabu();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTableCursosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCursosMouseClicked
        // TODO add your handling code here:
        int seleccion=jTableCursos.rowAtPoint(evt.getPoint());
        setNombreCurso(String.valueOf(jTableCursos.getValueAt(seleccion,0)));
        jTextFieldNomCurso.setText(getNombreCurso());
    }//GEN-LAST:event_jTableCursosMouseClicked


    
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
            java.util.logging.Logger.getLogger(Alumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Alumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Alumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Alumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Alumno().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBoxSemana;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelFoto;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelActividad;
    private javax.swing.JPanel jPanelCurso;
    private javax.swing.JPanel jPanelInfoProf;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTableCursos;
    private javax.swing.JTextField jTextFieldAlumno;
    private javax.swing.JTextField jTextFieldCarrera;
    private javax.swing.JTextField jTextFieldCodigo;
    private javax.swing.JTextField jTextFieldEdad;
    private javax.swing.JTextField jTextFieldNomCurso;
    // End of variables declaration//GEN-END:variables
}
