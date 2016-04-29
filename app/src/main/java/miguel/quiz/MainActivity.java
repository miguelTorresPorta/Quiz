package miguel.quiz;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import question.FillingGaps;
import question.MultipleChoice;
import question.Test;
import question.TrueFalse;
import tools.Constantes;
import web.VolleySingleton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = AppCompatActivity.class.getSimpleName();
    private static ArrayList<Test> listTest;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // llamamos al layout activity_main.xml
        setContentView(R.layout.activity_main);

        listTest = JDBCHelperTest.getQuestions();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_acerca) {

            Toast.makeText(getApplicationContext(),
                    "...", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


    public static Test getTestByPosition (int position){
        return listTest.get(position);
    }

    public static int getSize(){
        return listTest.size();
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());

        adapter.addFragment(new HistorySectionFragment(), "History");
        adapter.addFragment(new TestSectionFragment(), "Test");

        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void getQuestionary(){

        String newURL = Constantes.GET_AUTHORS;
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
                                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                                Log.d(TAG, "Error Volley: " + error.getMessage());
                            }
                        }
                )
        );
    }
    private void procesarRespuesta(JSONObject response) {

        try {
            // Obtener atributo "mensaje"
            String estado = response.getString("estado");
            String name;
            String description;
            ArrayList<FillingGaps> listFG = new ArrayList<>();
            ArrayList<TrueFalse> listTF = new ArrayList<>();
            ArrayList<MultipleChoice> listMC = new ArrayList<>();


            switch (estado) {
                case "1": // EXITO
                    // Obtener array "Authors" Json
                    JSONArray mensaje = (JSONArray) response.get("authors");
                    //response = response.getJSONObject("authors");

                    for (int i = 0; i < mensaje.length(); i++){
                        listFG.clear();
                        listTF.clear();
                        listMC.clear();

                        JSONObject obj = (JSONObject) mensaje.get(i);
                        name = (String) obj.get("Nombre");
                        description = (String) obj.get("Descripcion");



                        Log.d(TAG, "Error Volley: " + "d");

                    }
/*
                    JSONObject author;
                    String s = "";

                    for(int i = 0; i < mensaje.length(); i++){
                        listFG.clear();
                        listTF.clear();
                        listMC.clear();
                        ArrayList<Boolean> correct = new ArrayList<>();
                        ArrayList<String> coment = new ArrayList<>();

                        author = (JSONObject) mensaje.get(i);
                        //author.get("Nombre");

                        name = author.get("Nombre").toString();
                        description = author.get("Descripcion").toString();

                        listFG.add(new FillingGaps(rsQuestions.getObject(1).toString(), rsQuestions.getObject(2).toString(), rsQuestions.getObject(3).toString()));


                        while (name != author.get("Nombre").toString()){


                            correct.add(author.getBoolean("Correct"));
                            coment.add(author.get("Comentario").toString());
                            author = (JSONObject) mensaje.get(i);
                            i++;

                        }
                        MultipleChoice m = new MultipleChoice(author.get("TituloPregunta").toString(), author.get("Tipo").toString(),
                                correct, coment);
                        listMC.add(m);

                        listTest.add(new Test(name, description, listFG, listTF, listMC));

                        Log.d(TAG, author.get("Nombre").toString());
                    }
*/
                    break;
                case "2": // FALLIDO
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            this,
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    }



