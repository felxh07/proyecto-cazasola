
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Alex
 */
public class Conectar {
    public static Connection conn;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String password = "0912cisneros";
    private static final String url ="jdbc:mysql://localhost:3306/aulavirtual?useSSL=false";

    public Conectar() {
        conn = null;
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url,user,password);
            if(conn != null){
                System.out.println("Conexion establecida");
            }
        } catch (ClassNotFoundException | SQLException e){
            System.out.println("Error al conectar " + e);
        }
    }
    // este metodo nos retorna la conexion
    public Connection getConnection(){
        return conn;
    }
    // este metodo nos desconectamos de la bbdd
    public void desconectar(){
        conn = null;
        if(conn == null){
            System.out.println("Conexion terminada");
        }
    }
    
}
