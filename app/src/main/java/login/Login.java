package login;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.gson.Gson;

import miguel.quiz.MainActivity;
import miguel.quiz.R;
import tools.Constantes;
import web.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

/*
 * Activity encargada de hacer el login de usuarios
 */
public class Login extends AppCompatActivity {

    private static final String TAG = AppCompatActivity.class.getSimpleName();
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the login form.
        usernameEditText = (EditText) findViewById(R.id.nameTxt);
        passwordEditText = (EditText) findViewById(R.id.passTxt);

        // Set up the submit button click handler
        Button logBtn = (Button) findViewById(R.id.saveBtn);
        logBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Llamar a clase que compruebe si esta logeado
                login();
            }
        });

        Button regBtn = (Button) findViewById(R.id.registroBtn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Llamar a clase que para iniciar registro
                registro();
            }
        });

    }

    private void login(){

        // Cogemos el nombre y la pass introducida
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate the log in data
        boolean validationError = false;
        StringBuilder validationErrorMessage = new StringBuilder(getString(R.string.error_intro));

        if (username.length() == 0) {
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_username));
        }
        if (password.length() == 0) {
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_password));
        }

        // If there is a validation error, display the error
        if (validationError) {
            Toast.makeText(Login.this, validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
        } else {
            // Comprobamos que el usuario esta registrado
            checkUser(username, password);


        }

    }

    private void registro(){
        // Llamamos a la activity registro
        Intent intent = new Intent(Login.this, RegistroActivity.class);
        startActivity(intent);
    }


    public void checkUser(String name, String pass) {

        // Añadir parámetro a la URL del web service
        String newURL = Constantes.GET_CHECK + "?name=" + name + "&pass=" + pass;

        // Realizar petición GET_BY_ID
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        newURL,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar respuesta Json
                                procesarRespuesta(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                String s = "Error de conexión...";
                                Toast.makeText(Login.this, s, Toast.LENGTH_LONG).show();
                                Log.d(TAG, "Error Volley: " + error.getMessage());
                            }
                        }
                )
        );


    }

    private void procesarRespuesta(JSONObject response) {

        try {
            // Obtener atributo "mensaje"
            String mensaje = response.getString("estado");

            switch (mensaje) {
                case "1":

                    String mensaje1 = "Login correcto";
                    Toast.makeText(this, mensaje1, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                    break;

                case "2":
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(this, mensaje2, Toast.LENGTH_LONG).show();
                    break;
                case "3":
                    String mensaje3 = response.getString("mensaje");
                    Toast.makeText(this, mensaje3, Toast.LENGTH_LONG).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
