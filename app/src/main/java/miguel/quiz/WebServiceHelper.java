package miguel.quiz;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import question.Author;
import question.FillingGaps;
import question.MultipleChoice;
import question.Test;
import question.TrueFalse;
import tools.Constantes;
import web.VolleySingleton;

/**
 * Created by migueltorresporta on 29/4/16.
 */
public class WebServiceHelper {
    private static final String TAG = "WebServiceHelper";

    private static ArrayList<Test> listTest;
    private Context context;
    JsonObjectRequest jsArrayRequest;

    public WebServiceHelper (Context context){
        this.context = context;
    }

    public ArrayList<Test> getQuestions(){

        ArrayList<Author> authors = getAuthors();
        ArrayList<FillingGaps> listFG = new ArrayList<>();
        ArrayList<TrueFalse> listTF = new ArrayList<>();
        ArrayList<MultipleChoice> listMC = new ArrayList<>();


        for (Author a : authors){
            listFG.clear();
            listTF.clear();
            listMC.clear();

            listFG = getFillGaps(a.getNombre());


            listTest.add(new Test(a.getNombre(), a.getDescripcion(), listFG, listTF, listMC));
        }


        return listTest;
    }

    private ArrayList<Author> getAuthors(){

        String newURL = Constantes.GET_AUTHORS;
        final ArrayList<Author> authors = new ArrayList<>();

        jsArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                newURL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Procesar respuesta Json
                        try {
                            // Obtener atributo "mensaje"
                            String estado = response.getString("estado");

                            switch (estado) {
                                case "1": // EXITO
                                    // Obtener array "Authors" Json
                                    JSONArray mensaje = (JSONArray) response.get("authors");
                                    for (int i = 0; i < mensaje.length(); i++){
                                        JSONObject aut = mensaje.getJSONObject(i);

                                        authors.add(new Author(aut.get("Nombre").toString(), aut.get("Descripcion").toString()));
                                    }

                                    break;
                                case "2": // FALLIDO
                                    String mensaje2 = response.getString("mensaje");
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }                            }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String s = "Error de conexión...";
                        Log.d(TAG, "Error Volley: " + error.getMessage());
                    }
                }
        );

        return authors;
    }

    private ArrayList<FillingGaps> getFillGaps(String nombre) {
        final ArrayList<FillingGaps> fg = new ArrayList<>();
        String newURL = Constantes.GET_FILLING + "?nombreAuthor=" + nombre;


        // Realizar petición GET_BY_ID
        VolleySingleton.getInstance(context).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        newURL,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar respuesta Json
                                try {
                                    // Obtener atributo "mensaje"
                                    String estado = response.getString("estado");

                                    switch (estado) {
                                        case "1": // EXITO
                                            // Obtener array "Authors" Json
                                            JSONArray mensaje = (JSONArray) response.get("pregunta");
                                            for (int i = 0; i < mensaje.length(); i++){
                                                JSONObject ques = mensaje.getJSONObject(i);

                                                fg.add(new FillingGaps(ques.get("TituloPregunta").toString(),
                                                                        ques.get("Tipo").toString(),
                                                                        ques.get("Listado").toString()));
                                                //authors.add(new Author(aut.get("Nombre").toString(), aut.get("Descripcion").toString()));
                                            }

                                            break;
                                        case "2": // FALLIDO
                                            String mensaje2 = response.getString("mensaje");
                                            break;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                String s = "Error de conexión...";
                                Log.d(TAG, "Error Volley: " + error.getMessage());
                            }
                        }
                )
        );

        return fg;
    }

    private ArrayList<TrueFalse> getTrueFalse(String nombre) {
        ArrayList<TrueFalse> tf = new ArrayList<>();

        return tf;
    }

    private ArrayList<MultipleChoice> getMultipleChoice(String nombre) {
        ArrayList<MultipleChoice> mc = new ArrayList<>();

        return mc;
    }
}
