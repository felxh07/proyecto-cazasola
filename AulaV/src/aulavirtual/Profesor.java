/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aulavirtual;

import Conexion.Conectar;
import aulavirtual.ProfesorAlumnos;
import aulavirtual.ProfesorAlumnos;
import aulavirtual.ProfesorArchivos;
import aulavirtual.ProfesorArchivos;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alex
 */
public class Profesor extends javax.swing.JFrame {

    /**
     * Creates new form Profesor
     */
    public Profesor() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    /*creo una variable donde se va a almacenar el dni del profesor*/
    public String dniProfesor;
    
    public void obtenerDni(String dni){
        dniProfesor=dni;
    }
    private int indiceSeleccionado = -1;
    //creo variable para almacenar el codigo del curso
    private String codigoCurso;
    private String nombreCurso;
    private String codCursoEstudianteSemestre;
    public String getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(String codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public String getCodCursoEstudianteSemestre() {
        return codCursoEstudianteSemestre;
    }

    public void setCodCursoEstudianteSemestre(String codCursoEstudianteSemestre) {
        this.codCursoEstudianteSemestre = codCursoEstudianteSemestre;
    }
    
      public void CargaAlumnos(DefaultTableModel modeloTablaAlumnos) {
        System.out.println("Indice seleccionado : " + indiceSeleccionado);

        DefaultTableModel modelo = (DefaultTableModel) jTableCursos.getModel();
         String cursoABuscar = String.valueOf(jTableCursos.getValueAt(indiceSeleccionado, 0));
         
         

        modeloTablaAlumnos.setRowCount(0);//limpiar el modelo
        try {
            Conectar cnx = new Conectar();
            Connection registros = cnx.getConnection();
            String sql = "select persona.nombre, estudiante.codEstudiante from estudiante join persona on persona.dni=estudiante.dni\n"
                    + "join curso_estudiantesemestre on estudiante.dni=curso_estudiantesemestre.dni\n"
                    + "join curso on curso_estudiantesemestre.codCurso=curso.codCurso where nombre_curso=\"" + cursoABuscar + "\"";
            PreparedStatement st = registros.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));

                modeloTablaAlumnos.addRow(v);

            }
            jTableCursos.setModel(modelo);
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }

    }
    
    /*Empieza*/
    public void Mostrar(){
    try{
    Conectar cnx = new Conectar();
    Connection registros = cnx.getConnection();
    String sql="select  persona.nombre, persona.dni , persona.fechaNacimiento , escuela.nombreEscuela , persona.foto\n" +
    "from profesor\n" +
    "inner join persona on profesor.dni=persona.dni\n" +
    "inner join escuela on profesor.codEscuela=escuela.codEscuela where persona.dni ='"+dniProfesor+"';";
    PreparedStatement st = registros.prepareStatement(sql);
    ResultSet rs= st.executeQuery();
        while (rs.next()){ 
        jTextFieldNombreProfesor.setText(rs.getString(1));
        jTextFieldDniProfesor.setText(rs.getString(2));
        jTextFieldEdadProfesor.setText(rs.getString(3));
        jTextFieldEscuelaProfesor.setText(rs.getString(4));
        }
    }catch(SQLException e){
        System.out.println("Error"+e.getMessage());
    }
     deshabilitar();
    }
   
    public void CargaCursos(){
        DefaultTableModel modelo=(DefaultTableModel) jTableCursos.getModel();
        modelo.setRowCount(0);//limpiar el modelo
        try{
            Conectar cnx = new Conectar();
            Connection registros = cnx.getConnection();
            String sql="select curso.codCurso,curso.nombre_curso, ciclo.nombreciclo\n" +
            "from curso\n" +
            "inner join ciclo on curso.numCiclo=ciclo.numCiclo\n" +
            "where curso.dni=\""+dniProfesor+"\";";
            PreparedStatement st = registros.prepareStatement(sql);
            ResultSet rs= st.executeQuery();
            while(rs.next()){
                Vector v=new Vector();
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                modelo.addRow(v);
            }  
            jTableCursos.setModel(modelo);
        }catch(SQLException e){
            System.out.println("Error"+e.getMessage());
        }
        
    }
    /*agregar silabu*/
    /*codigo para subir silabu*/
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
                
            } catch (FileNotFoundException ex){
                ex.printStackTrace();
            }
        }
        
    }
    public void subirSilabu(FileInputStream archivo, int bytes,String codigosSemestre){
        try{
            Conectar cnx = new Conectar();
            Connection fotosql = cnx.getConnection();
            String sqlsilabu="UPDATE silabu  SET nombreSilabu = ?, archivoPDF = ? " +  "WHERE codCursoEstudianteSemestre =\"" + codigosSemestre+"\";";
            PreparedStatement stsilabu = fotosql.prepareStatement(sqlsilabu);
            stsilabu.setString(1,jTextFieldNombreArchivo.getText());
            stsilabu.setBlob(2, fis, bytes);
            stsilabu.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error: "+e.getMessage());
        }
    }
    
    /**/
    public void obtenerCodigoCurso(){
        try{
    Conectar cnx = new Conectar();
    Connection registros = cnx.getConnection();
    String sql="select *from curso where nombre_curso = \""+getNombreCurso()+"\";";
    PreparedStatement st = registros.prepareStatement(sql);
    ResultSet rs= st.executeQuery();
        while (rs.next()){ 
            setCodigoCurso(rs.getString(1));
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

        jPanelActividad = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldNombreProfesor = new javax.swing.JTextField();
        jTextFieldDniProfesor = new javax.swing.JTextField();
        jTextFieldEdadProfesor = new javax.swing.JTextField();
        jTextFieldEscuelaProfesor = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanelInfoProf = new javax.swing.JPanel();
        jLabelFoto = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCursos = new javax.swing.JTable();
        jPanelCurso = new javax.swing.JPanel();
        jLabelNombreCurso = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldNombreArchivo = new javax.swing.JTextField();
        jButtonSeleccionarSilabu = new javax.swing.JButton();
        jButtonAgregarSilabu = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SISTEMA PROFESOR");
        setBackground(new java.awt.Color(0, 153, 153));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanelActividad.setBackground(new java.awt.Color(0, 153, 153));
        jPanelActividad.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelActividad.setForeground(new java.awt.Color(51, 153, 255));

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(0, 204, 204));
        jPanel2.setForeground(new java.awt.Color(0, 153, 153));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("PROFESOR :");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("DNI:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("EDAD :");

        jTextFieldNombreProfesor.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jTextFieldNombreProfesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNombreProfesorActionPerformed(evt);
            }
        });

        jTextFieldDniProfesor.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jTextFieldEdadProfesor.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("ESCUELA :");

        jPanelInfoProf.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanelInfoProfLayout = new javax.swing.GroupLayout(jPanelInfoProf);
        jPanelInfoProf.setLayout(jPanelInfoProfLayout);
        jPanelInfoProfLayout.setHorizontalGroup(
            jPanelInfoProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelFoto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
        );
        jPanelInfoProfLayout.setVerticalGroup(
            jPanelInfoProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelFoto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelInfoProf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldEdadProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldEscuelaProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldDniProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldNombreProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(3687, 3687, 3687))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jPanelInfoProf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jTextFieldNombreProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldDniProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldEdadProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldEscuelaProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(15, 15, 15)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLayeredPane1.setLayer(jPanel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 914, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Relacion de cursos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jTableCursos.setBackground(new java.awt.Color(204, 204, 255));
        jTableCursos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTableCursos.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jTableCursos.setForeground(new java.awt.Color(102, 102, 102));
        jTableCursos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CURSOS", "CICLO"
            }
        ));
        jTableCursos.setGridColor(new java.awt.Color(204, 204, 255));
        jTableCursos.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTableCursos.setSelectionForeground(new java.awt.Color(255, 0, 0));
        jTableCursos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableCursosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableCursos);

        jPanelCurso.setBackground(new java.awt.Color(0, 102, 102));
        jPanelCurso.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Subir Archivo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanelCurso.setMaximumSize(new java.awt.Dimension(500, 500));

        jLabelNombreCurso.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton3.setBackground(new java.awt.Color(102, 102, 102));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/archivos.png"))); // NOI18N
        jButton3.setText("ARCHIVOS");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("NOMBRE DE SILABUS");

        jTextFieldNombreArchivo.setEnabled(false);

        jButtonSeleccionarSilabu.setBackground(new java.awt.Color(102, 102, 102));
        jButtonSeleccionarSilabu.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonSeleccionarSilabu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/seleccionar.png"))); // NOI18N
        jButtonSeleccionarSilabu.setText("SELECCIONAR SILABUS");
        jButtonSeleccionarSilabu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSeleccionarSilabuActionPerformed(evt);
            }
        });

        jButtonAgregarSilabu.setBackground(new java.awt.Color(102, 102, 102));
        jButtonAgregarSilabu.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonAgregarSilabu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/subirArchivo.png"))); // NOI18N
        jButtonAgregarSilabu.setText("SUBIR SILABUS");
        jButtonAgregarSilabu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarSilabuActionPerformed(evt);
            }
        });

        jButton1.setText("alumno");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCursoLayout = new javax.swing.GroupLayout(jPanelCurso);
        jPanelCurso.setLayout(jPanelCursoLayout);
        jPanelCursoLayout.setHorizontalGroup(
            jPanelCursoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCursoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelCursoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCursoLayout.createSequentialGroup()
                        .addComponent(jLabelNombreCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCursoLayout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGroup(jPanelCursoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanelCursoLayout.createSequentialGroup()
                                .addGap(118, 118, 118)
                                .addComponent(jTextFieldNombreArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelCursoLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(148, 148, 148))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCursoLayout.createSequentialGroup()
                        .addComponent(jButtonAgregarSilabu)
                        .addGap(84, 84, 84)
                        .addComponent(jButtonSeleccionarSilabu)
                        .addGap(116, 116, 116))))
        );
        jPanelCursoLayout.setVerticalGroup(
            jPanelCursoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCursoLayout.createSequentialGroup()
                .addGroup(jPanelCursoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCursoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelNombreCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelCursoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(jLabel5)))
                    .addGroup(jPanelCursoLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jButton1)))
                .addGroup(jPanelCursoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCursoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNombreArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonSeleccionarSilabu, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(32, Short.MAX_VALUE))
                    .addGroup(jPanelCursoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonAgregarSilabu)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo-fiis.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/unac_opt.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel7))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jButton2.setBackground(new java.awt.Color(102, 102, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrarsesion.png"))); // NOI18N
        jButton2.setText("Cerrar Sesion");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelActividadLayout = new javax.swing.GroupLayout(jPanelActividad);
        jPanelActividad.setLayout(jPanelActividadLayout);
        jPanelActividadLayout.setHorizontalGroup(
            jPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelActividadLayout.createSequentialGroup()
                .addGroup(jPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelActividadLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanelActividadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelActividadLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(jPanelCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        jPanelActividadLayout.setVerticalGroup(
            jPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelActividadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelActividadLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(jPanelActividadLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        getContentPane().add(jPanelActividad, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        Mostrar();
        CargaCursos();
        ImageIcon profesor = new ImageIcon(getClass().getResource("/imagenes/profesor.jpg"));
        ImageIcon icono = new ImageIcon(profesor.getImage().getScaledInstance(jLabelFoto.getWidth(),jLabelFoto.getHeight(),Image.SCALE_DEFAULT));
        jLabelFoto.setIcon(icono); 
    }//GEN-LAST:event_formWindowOpened

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButtonAgregarSilabuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarSilabuActionPerformed
        // TODO add your handling code here:
        //se agregar a todas las matriculas del curso
        try{
            Conectar cnx = new Conectar();
            Connection registros = cnx.getConnection();
            String sql="select *from curso_estudiantesemestre\n" +
            "where codSemestre=\"2019A\" and codCurso=\""+getCodigoCurso()+"\"";
            PreparedStatement st = registros.prepareStatement(sql);
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                subirSilabu(fis, longitudBytes,rs.getString(4));
            }
            JOptionPane.showMessageDialog(null, "Se agregaron a todas las matriculas");
        }catch(SQLException e){
            System.out.println("Error"+e.getMessage());
        }
    }//GEN-LAST:event_jButtonAgregarSilabuActionPerformed

    private void jButtonSeleccionarSilabuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSeleccionarSilabuActionPerformed
        // TODO add your handling code here:
        selecionarArchivo();
    }//GEN-LAST:event_jButtonSeleccionarSilabuActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        ProfesorArchivos pA=new ProfesorArchivos();
        pA.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed
   
    
    private void jTableCursosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCursosMouseClicked
        // TODO add your handling code here:
        int seleccion=jTableCursos.rowAtPoint(evt.getPoint());
        
        jLabelNombreCurso.setText(getNombreCurso());
        indiceSeleccionado = jTableCursos.getSelectedRow();
        jLabelNombreCurso.setText(String.valueOf(jTableCursos.getValueAt(seleccion, 0)));
        obtenerCodigoCurso();
    }//GEN-LAST:event_jTableCursosMouseClicked

    private void jTextFieldNombreProfesorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNombreProfesorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNombreProfesorActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
if (indiceSeleccionado != -1) {
           
            ProfesorAlumnos.getInstance().setVisible(true);
            CargaAlumnos(ProfesorAlumnos.getInstance().getModeloTabla());
            
                    
            
        }else{
            ProfesorAlumnos.getInstance().dispose();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Profesor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Profesor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Profesor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Profesor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Profesor().setVisible(true);
            }
        });
    }
private void deshabilitar(){
jTextFieldNombreProfesor.setEnabled(false);
jTextFieldDniProfesor.setEnabled(false);
jTextFieldEdadProfesor.setEnabled(false);
jTextFieldEscuelaProfesor.setEnabled(false);
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonAgregarSilabu;
    private javax.swing.JButton jButtonSeleccionarSilabu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelFoto;
    private javax.swing.JLabel jLabelNombreCurso;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelActividad;
    private javax.swing.JPanel jPanelCurso;
    private javax.swing.JPanel jPanelInfoProf;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableCursos;
    private javax.swing.JTextField jTextFieldDniProfesor;
    private javax.swing.JTextField jTextFieldEdadProfesor;
    private javax.swing.JTextField jTextFieldEscuelaProfesor;
    private javax.swing.JTextField jTextFieldNombreArchivo;
    private javax.swing.JTextField jTextFieldNombreProfesor;
    // End of variables declaration//GEN-END:variables
}
