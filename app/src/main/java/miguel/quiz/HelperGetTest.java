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
import java.util.Arrays;

import tools.Constantes;
import web.VolleySingleton;

/**
 * Created by migueltorresporta on 18/4/16.
 */
public class HelperGetTest {
    private static final String TAG = AppCompatActivity.class.getSimpleName();
    ArrayList <String> listNomAuthors;
    ArrayList <String> listDesAuthors;


    public static void cargarAutores(Context cnt) {
        // Petición GET
        VolleySingleton.
                getInstance(cnt).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET_AUTHORS,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta Json
                                        procesarRespuesta(response);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(TAG, "Error Volley: " + error.toString());
                                    }
                                }

                        )
                );
    }

    private static void procesarRespuesta(JSONObject response) {

        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");

            switch (estado) {
                case "1": // EXITO
                    // Obtener array "metas" Json
                    JSONArray mensaje = response.getJSONArray("metas");
                    // Parsear con Gson
                  //  Meta[] metas = gson.fromJson(mensaje.toString(), Meta[].class);
                    // Inicializar adaptador
                  //  adapter = new MetaAdapter(Arrays.asList(metas), getActivity());
                    // Setear adaptador a la lista
                 //   lista.setAdapter(adapter);
                    break;
                case "2": // FALLIDO
                    String mensaje2 = response.getString("mensaje");
                    //Toast.makeText(getActivity(), mensaje2, Toast.LENGTH_LONG).show();
                    break;
            }

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
    }
}
