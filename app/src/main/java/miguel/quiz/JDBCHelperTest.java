package miguel.quiz;

/**
 * Created by migueltorresporta on 3/2/16.
 */

import android.os.StrictMode;

//import com.mysql.jdbc.Statement;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import question.FillingGaps;
import question.MultipleChoice;
import question.Test;
import question.TrueFalse;

public class JDBCHelperTest {

    private static final String url = "jdbc:mysql://sql2.freemysqlhosting.net:3306/sql2106171";
    private static final String user = "sql2106171";
    private static final String pass = "tP7%aN9%";
    private static Connection con = null;
    private static ResultSet rsAutores = null;
    private static ResultSet rsQuestions = null;
    private static ResultSet rsMultiple = null;

    private static Statement st = null;
    private static PreparedStatement stmt = null;

    // Devuelve un arraylist con todos la informacion de los autores
  /*  public static ArrayList<Autores> getAutores() {
        ArrayList<Autores> listAut = new ArrayList<>();
        try {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);

            st = con.createStatement();

            rsAutores = st.executeQuery("SELECT * FROM Autores");

            while (rsAutores.next()) {
                listAut.add(new Autores(rsAutores.getObject(1).toString(), rsAutores.getObject(2).toString()));
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    return listAut;
    }
*/
    public static ArrayList<Test> getQuestions(){

        ArrayList<Test> listTest = new ArrayList<>();

        String name = "";
        String description;
        ArrayList<FillingGaps> listFG = new ArrayList<>();
        ArrayList<TrueFalse> listTF = new ArrayList<>();
        ArrayList<MultipleChoice> listMC = new ArrayList<>();


        try {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);

            st = con.createStatement();

            rsAutores = st.executeQuery("SELECT * FROM Autores");

            while (rsAutores.next()) {
                listFG.clear();
                listTF.clear();
                listMC.clear();

                name = rsAutores.getObject(1).toString();
                description = rsAutores.getObject(2).toString();


                // Fix query WHERE NombreAutor = name
                // Hacer consultas FillingGaps
                stmt = con.prepareStatement("SELECT TituloPregunta, Tipo, Listado FROM FillingGaps where NombreAutor = ?");
                stmt.setString(1, name);
                rsQuestions = stmt.executeQuery();
                //rsQuestions = st.executeQuery("SELECT TituloPregunta, Tipo, Listado FROM FillingGaps");
                while (rsQuestions.next()){
                    listFG.add(new FillingGaps(rsQuestions.getObject(1).toString(), rsQuestions.getObject(2).toString(), rsQuestions.getObject(3).toString()));
                }

                // Query MultipleChoice
                stmt = con.prepareStatement("SELECT id, TituloPregunta, Tipo FROM MultipleChoice WHERE NombreAutor = ?");
                stmt.setString(1, name);
                rsQuestions = stmt.executeQuery();
                while(rsQuestions.next()){
                    int id = rsQuestions.getInt(1);
                    //MultipleChoice m = new MultipleChoice(rsQuestions.getObject(2).toString(), rsQuestions.getObject(3).toString(), null, null);
                    //listMC.add(m);
                    // TODO hacer consulta
                    stmt = con.prepareStatement("SELECT Id, IdMultipleChoice, Titulo, Correct, Comentario FROM PreguntasMult WHERE IdMultipleChoice = ?");
                    stmt.setInt(1, id);
                    rsMultiple = stmt.executeQuery();

                    ArrayList<Boolean> correct = new ArrayList<>();
                    ArrayList<String> coment = new ArrayList<>();
                    while (rsMultiple.next()) {
                        correct.add(rsMultiple.getBoolean(4));
                        coment.add(rsMultiple.getString(5));
                    }
                    MultipleChoice m = new MultipleChoice(rsQuestions.getObject(2).toString(), rsQuestions.getObject(3).toString(),
                            correct, coment);
                    listMC.add(m);
                }

                // Query TrueFalse
                stmt = con.prepareStatement("SELECT TituloPregunta, Tipo, Correct, Comentario FROM TrueFalse WHERE NombreAutor = ?");
                stmt.setString(1, name);
                rsQuestions = stmt.executeQuery();
                while(rsQuestions.next()){
                    listTF.add(new TrueFalse(rsQuestions.getObject(1).toString(), rsQuestions.getObject(2).toString(), rsQuestions.getBoolean(3), rsQuestions.getObject(4).toString()));
                }
                listTest.add(new Test(name, description, listFG, listTF, listMC));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listTest;

    }
}


