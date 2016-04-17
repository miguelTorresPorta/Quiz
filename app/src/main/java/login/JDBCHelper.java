package login;

/**
 * Created by migueltorresporta on 3/2/16.
 */

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/* DEPRECATED
 * Clase para la implementacion del servidor MySQL
 * mediante JDBC
 */
public class JDBCHelper {

    //private static final String url = "jdbc:mysql://sql2.freemysqlhosting.net:3306/sql2106171";
    //private static final String user = "sql2106171";
    //private static final String pass = "tP7%aN9%";
        private static final String url = "jdbc:mysql://10.0.2.2:3306/quiz";
    private static final String user = "root";
    private static final String pass = "";
    private static Connection con = null;
    private static ResultSet rs = null;
    private static ResultSetMetaData rand = null;
    private static PreparedStatement stmt = null;


    public static boolean checkUser(String nombre, String password){
        String result = null;
        boolean correcto = false;
        try {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);

            stmt = con.prepareStatement("SELECT * FROM usuarios where nombreUsuario = ? and password = ?");
            stmt.setString(1, nombre);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            rand = rs.getMetaData();

           // while (rs.next()){
           //     result += rand.getColumnName(1) + "; " + rs.getString(1) + "\n";
           // }

            if (rs.next())
                correcto = true;
            else
                correcto = false;

        }catch (Exception e){
            e.printStackTrace();

        }finally {
            try{
                if (con != null){
                    con.close();
                    //st.close();
                    //rs.close();
                }
            }catch (SQLException e){

            }
        }
        return correcto;
    }


    // Primero comprobamos si ya existe el usuario
    // Si no existe lo añadimos (insert)
    public static boolean newUser(String nombre, String password){
        boolean correcto = false;
        try {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);

            stmt = con.prepareStatement("SELECT * FROM usuarios where nombreUsuario = ? and password = ?");
            stmt.setString(1, nombre);
            stmt.setString(2, password);
            rs = stmt.executeQuery();


            rand = rs.getMetaData();

            if (!rs.next()){

                stmt = con.prepareStatement("INSERT INTO usuarios VALUES (?, ?)");
                stmt.setString(1, nombre);
                stmt.setString(2, password);

                if (stmt.executeUpdate() == 1){
                    correcto = true;
                }
                correcto = true;

            }


        }catch (Exception e){
            e.printStackTrace();

        }finally {
            try{
                if (con != null){
                    con.close();
                    //st.close();
                    //rs.close();
                }
            }catch (SQLException e){

            }
        }

        return correcto;
    }


}
