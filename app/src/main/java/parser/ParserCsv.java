package parser;

/**
 * Created by migueltorresporta.
 */
import android.content.res.Resources;

import java.io.*;
import java.util.ArrayList;

import miguel.quiz.R;
import question.FillingGaps;
import question.MultipleChoice;
import question.Question;
import question.TrueFalse;

public class ParserCsv {

    public static ArrayList<Question> LeerFicheroCsv(InputStream flujo) {
        String numPregunta = "";
        String titulo = "";
        String tipoPregunta = "";
        ArrayList <Question> quiz = new ArrayList<Question>();
        ArrayList <String> respuesta = new ArrayList<String>();
        ArrayList <String> feedback = new ArrayList<String>();

        //para ficheros con símbolos propios del español,
        //utilizar la codificación "ISO-8859-1"
        BufferedReader lector = null;


        try{

            //flujo = getResources().openRawResource(R.raw.prueba_int);
            lector = new BufferedReader(new InputStreamReader(flujo));

            // Leemos primera pregunta
            String linea=lector.readLine();

            // Separamos los valores de la primera linea
            String[] parts = linea.split(";");

            while(linea!=null){

                numPregunta = parts[0];
                titulo = parts[1];
                tipoPregunta = parts[2].toLowerCase();

                // Reconocemos el tipo de pregunta
                switch (tipoPregunta){
                    case "multiple choice":
                        respuesta.clear();
                        // Terminamos de leer la primera linea de la pregunta
                        respuesta.add(parts[3]);
                        feedback.add(parts[4]);

                        // Leemos la suguiente
                        linea=lector.readLine();
                        if (linea != null)
                            parts = linea.split(";");

                        // Mientras en la primera posicion no haya nada seguimos con la misma pregunta
                        while (parts[0].isEmpty() && linea!=null){
                            respuesta.add(parts[3]);
                            feedback.add(parts[4]);

                            // Leemos la siguiente linea
                            linea=lector.readLine();
                            if (linea != null)
                                parts = linea.split(";");
                        }

                        // Creamos el objeto de tipo pregunta MultipleChoice y
                        // Lo añadimos al array list de preguntas
                        //quiz.add(new MultipleChoice(numPregunta, titulo, tipoPregunta, respuesta, feedback));

                        break;
                    case "true / false":

                        // Como solo tiene una respuesta creamos directamente el objeto
                        // Y lo añadimos al quiz
                        //quiz.add(new TrueFalse(numPregunta, titulo, tipoPregunta, parts[3]));

                        // Leemos la suguiente
                        linea=lector.readLine();
                        if (linea != null)
                            parts = linea.split(";");

                        break;
                    case "filling the gaps":
                        respuesta.clear();

                        // Terminamos de leer la primera linea de la pregunta
                        respuesta.add(parts[3]);

                        // Leemos la suguiente
                        linea=lector.readLine();
                        if (linea != null)
                            parts = linea.split(";");

                        // Mientras en la primera posicion no haya nada seguimos con la misma pregunta
                        while (parts[0].isEmpty() && linea!=null){
                            respuesta.add(parts[3]);

                            // Leemos la siguiente linea
                            linea=lector.readLine();
                            if (linea != null)
                                parts = linea.split(";");
                        }

                        // Creamos el objeto de tipo pregunta MultipleChoice y
                        // Lo añadimos al array list de preguntas
                        //quiz.add(new FillingGaps(numPregunta, titulo, tipoPregunta, respuesta));

                        break;
                    default:
                        linea=lector.readLine();
                        if (linea != null)
                            parts = linea.split(";");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (lector != null) {
                try {
                    lector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return quiz;

    }



    public void EscribirFicheroCsv(String fichero_a_escribir, ArrayList<String[]> d)
            throws UnsupportedEncodingException, FileNotFoundException, IOException{

        OutputStream fout = new FileOutputStream(fichero_a_escribir);
        //para ficheros con símbolos propios del español,
        //utilizar la codificación "ISO-8859-1"
        OutputStreamWriter out = new OutputStreamWriter(fout, "UTF8");

        for(int i=0; i<d.size(); i++){

            String[] fila=d.get(i);

            for(int j=0; j<fila.length; j++){
                out.write(fila[j]+",");
            }

            out.write("\n");
        }
        out.close();
        fout.close();
    }


}
