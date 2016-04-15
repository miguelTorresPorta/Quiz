package login;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import miguel.quiz.R;
import tools.Constantes;
import web.VolleySingleton;

/**
 * Created by migueltorresporta on 27/1/16.
 */
public class RegistroActivity extends AppCompatActivity {

    private static final String TAG = "RegistroActivity";

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText passwordAgainEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        usernameEditText = (EditText) findViewById(R.id.userNewTxt);
        passwordEditText = (EditText) findViewById(R.id.passNewTxt);
        passwordAgainEditText = (EditText) findViewById(R.id.passRepNewTxt);

        Button logBtn = (Button) findViewById(R.id.regNewUserBtn);
        logBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                registrar();
            }
        });
        Button atrasBtn = (Button) findViewById(R.id.atrasBtn);
        atrasBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                finish();
            }
        });


    }

    private void registrar(){
        boolean correcto = false;
        // Cogemos el nombre y la pass introducida
        final String username = usernameEditText.getText().toString();
        final String password = passwordEditText.getText().toString();
        final String passwordAgain = passwordAgainEditText.getText().toString();



        boolean validationError = false;
        StringBuilder validationErrorMessage = new StringBuilder(getString(R.string.error_intro));

        if (username.length() == 0) {
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_username));
        }
        if (password.length() == 0 || passwordAgain.length() == 0) {
            /*if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }*/
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_password));
        } else if(!password.equals(passwordAgain)){
            validationError = true;
            validationErrorMessage.append(getString(R.string.diferentPass));

        }
        //validationErrorMessage.append(getString(R.string.error_end));

        // If there is a validation error, display the error
        if (validationError) {
            Toast.makeText(RegistroActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                    .show();
            return;
        } else {
            // Comprobamos que el usuario esta registrado
            newUser(username, password);

            /*if (correcto == true){
                Toast toast = Toast.makeText(getApplicationContext(), R.string.registroCorrecto, Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.userPassWrong, Toast.LENGTH_SHORT);
                toast.show();
            }*/
            // else Mensaje de error
        }

    }

    // Primero comprobamos si ya existe el usuario
    // Si no existe lo añadimos (insert)
    public void newUser(String username, String password){

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("name", username);
        map.put("pass", password);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        // Depurando objeto Json...
        Log.d(TAG, jobject.toString());

        // Actualizar datos en el servidor
        VolleySingleton.getInstance(this).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Constantes.INSERT_USER,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta del servidor
                                procesarRespuesta(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley: " + error.getMessage());
                                Toast.makeText(RegistroActivity.this, "Error de creacion", Toast.LENGTH_LONG)
                                        .show();
                            }
                        }

                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("Accept", "application/json");
                        return headers;
                    }

                    @Override
                    public String getBodyContentType() {
                        String s = "application/json; charset=utf-8" + getParamsEncoding();
                        return s;
                    }
                }
        );

    }

    /**
     * Procesa la respuesta obtenida desde el sevidor
     *
     * @param response Objeto Json
     */
    private void procesarRespuesta(JSONObject response) {

        try {
            // Obtener estado
            String estado = response.getString("estado");
            // Obtener mensaje
            String mensaje = response.getString("mensaje");

            switch (estado) {
                case "1":
                    // Mostrar mensaje
                    Toast.makeText(
                            this,
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    // Enviar código de éxito
                    this.setResult(Activity.RESULT_OK);
                    // Terminar actividad
                    this.finish();
                    break;

                case "2":
                    // Mostrar mensaje
                    Toast.makeText(
                            this,
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    // Enviar código de falla
                    this.setResult(Activity.RESULT_CANCELED);
                    // Terminar actividad
                    this.finish();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
