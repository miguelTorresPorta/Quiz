package miguel.quiz;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import question.FillingGaps;
import question.MultipleChoice;
import question.Question;
import question.Test;
import question.TrueFalse;

/**
 * Created by migueltorresporta on 30/3/16.
 */


// Class for one test with different tipes of questions
// Hacer un fragement por cada tipo diferente
public class TestActivity extends AppCompatActivity {


    private Test test;
    private Question question;
    private int position;
    private int numQuestions;
    //private MultipleChoice multipleChoice;
    //private FillingGaps fillingGaps;


    //Test test = new Test();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_interfaz);

        position = 0;

        if (getSupportActionBar() != null) {
            // Dehabilitar titulo de la actividad
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            // Setear ícono "X" como Up button
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_close);
        }


        // GetPossition based in the previous intent
        String pos = getIntent().getStringExtra("Position");
        int p = Integer.parseInt(pos);

        // GetTest bassed in the possition
        test = MainActivity.getTestByPosition(Integer.parseInt(getIntent().getStringExtra("Position")));
        numQuestions = test.getNumQuestions();

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        WindowManager wm = getWindowManager();
        Display d = wm.getDefaultDisplay();

        question = test.getQuestionByPosition(position);

        switch (question.getTipo()) {
            case "TRUE":
                TrueFalseFragment tf = new TrueFalseFragment();
                fragmentTransaction.add(R.id.contain, tf, "editor")
                        .commit();
                break;

        }
        question.getTipo();


    }


    public void siguiente() {

        // Close the fragment
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentByTag("editor");
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(frag);
        ft.commit();

        // Open the new fragment
        position++;

        if (position < numQuestions) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();
            question = test.getQuestionByPosition(position);


            switch (question.getTipo()) {
                case "TRUE":
                    TrueFalse trueFalse = (TrueFalse) question;
                    TrueFalseFragment tf = new TrueFalseFragment();
                    fragmentTransaction.add(R.id.contain, tf, "editor")
                            .commit();
                    break;
                case "FILL":
                    FillingGapsFragment fg = new FillingGapsFragment();
                    fragmentTransaction.add(R.id.contain, fg, "editor")
                            .commit();
                    break;
                case "MULTIPLE":
                    MultipleChoiceFragment mc = new MultipleChoiceFragment();
                    fragmentTransaction.add(R.id.contain, mc, "editor")
                            .commit();
                    break;
            }
        } else {
            finish();
        }

    }

    public class TrueFalseFragment extends Fragment {

        private TextView tip;
        private TextView titu;
        private Button buttonNext;
        private RadioButton rFalse;
        private RadioButton rTrue;
        private TrueFalse trueFalse;

        public TrueFalseFragment() {

        }

        public TrueFalseFragment createInstance() {
            TrueFalseFragment trueFalseFragment = new TrueFalseFragment();
            return trueFalseFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.question_true_false, container, false);


            trueFalse = (TrueFalse) question;
            final boolean verdadero = trueFalse.isCorrecto();
            final String respuesta = trueFalse.getRespuesta();

            tip = (TextView) rootView.findViewById(R.id.txtTipo);
            titu = (TextView) rootView.findViewById(R.id.txtTit);
            buttonNext = (Button) rootView.findViewById(R.id.btnSiguienteTrueFalse);
            rFalse = (RadioButton) rootView.findViewById(R.id.radioButtonFalse);
            rTrue = (RadioButton) rootView.findViewById(R.id.radioButtonTrue);


            tip.setText("Tipo: TRUE / FALSE");
            titu.setText(trueFalse.getTituloPregunta());

            // Setear escucha para el fab
            buttonNext.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (rTrue.isChecked()) {
                                if (verdadero) {
                                    // En algún lugar de tu actividad
                                    //new SimpleDialog("Correcto!", respuesta).show(getSupportFragmentManager(), "SimpleDialog");
                                    //Toast.makeText(getApplicationContext(), "CORRECTO!!", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), "¡CORRECTO!" + respuesta, Toast.LENGTH_LONG).show();
                                    siguiente();
                                } else {
                                    // En algún lugar de tu actividad
                                    new SimpleDialog("Incorrecto!", respuesta).show(getSupportFragmentManager(), "SimpleDialog");
                                    //Toast.makeText(getApplicationContext(), "INCORRECTO!!", Toast.LENGTH_SHORT).show();
                                }

                            } else if (rFalse.isChecked()) {
                                if (verdadero) {
                                    // En algún lugar de tu actividad
                                    new SimpleDialog("Incorrecto!", respuesta).show(getSupportFragmentManager(), "SimpleDialog");
                                    //Toast.makeText(getApplicationContext(), "INCORRECTO!!", Toast.LENGTH_SHORT).show();
                                } else {
                                    // En algún lugar de tu actividad
                                    //new SimpleDialog("Correcto!", respuesta).show(getSupportFragmentManager(), "SimpleDialog");
                                    Toast.makeText(getApplicationContext(), "¡CORRECTO!" + respuesta, Toast.LENGTH_LONG).show();
                                    siguiente();
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Seleciona una opcion...", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
            );


            return rootView;
        }




    }

    public class MultipleChoiceFragment extends Fragment {

        private TextView tip;
        private TextView titu;
        private Button buttonNext;
        private CheckBox checkBox1;
        private CheckBox checkBox2;
        private CheckBox checkBox3;
        private MultipleChoice multipleChoice;
        private ArrayList<Boolean> respuestas;
        private boolean preguntaCorrecta;

        public MultipleChoiceFragment() {

        }

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.question_multiple_choice, container, false);

            tip = (TextView) rootView.findViewById(R.id.tipo_mult_choice);
            titu = (TextView) rootView.findViewById(R.id.titulo_mult_choice);
            buttonNext = (Button) rootView.findViewById(R.id.sig_mult_choice);
            checkBox1 = (CheckBox) rootView.findViewById(R.id.checkBoxMult1);
            checkBox2 = (CheckBox) rootView.findViewById(R.id.checkBoxMult2);
            checkBox3 = (CheckBox) rootView.findViewById(R.id.checkBoxMult3);

            multipleChoice = (MultipleChoice) question;

            tip.setText("Tipo: MULTIPLE CHOICE");
            titu.setText(multipleChoice.getTituloPregunta());
            checkBox1.setText(multipleChoice.getFeebackByPos(0));
            checkBox2.setText(multipleChoice.getFeebackByPos(1));
            checkBox3.setText(multipleChoice.getFeebackByPos(2));
            respuestas = multipleChoice.getRespuestas();
            preguntaCorrecta = true;


            buttonNext.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(checkBox1.isChecked() || checkBox2.isChecked() || checkBox3.isChecked()){

                                if (checkBox1.isChecked() != respuestas.get(0))
                                    preguntaCorrecta = false;
                                if (checkBox2.isChecked() != respuestas.get(1))
                                    preguntaCorrecta = false;
                                if (checkBox3.isChecked() != respuestas.get(2))
                                    preguntaCorrecta = false;

                                if (preguntaCorrecta) {
                                    // En algún lugar de tu actividad
                                    //new SimpleDialog("Correcto!", "").show(getSupportFragmentManager(), "SimpleDialog");
                                    Toast.makeText(getApplicationContext(), "¡CORRECTO!", Toast.LENGTH_SHORT).show();
                                    siguiente();
                                } else {

                                    String s = "";
                                    if (respuestas.get(0))
                                        s = multipleChoice.getFeebackByPos(0) + "\n";
                                    if (respuestas.get(1))
                                        s = multipleChoice.getFeebackByPos(1) + "\n";
                                    if (respuestas.get(2))
                                        s = multipleChoice.getFeebackByPos(2) + "\n";
                                    // En algún lugar de tu actividad
                                    new SimpleDialog("Incorrecto!", s).show(getSupportFragmentManager(), "SimpleDialog");
                                    //Toast.makeText(getApplicationContext(), "INCORRECTO!!", Toast.LENGTH_SHORT).show();
                                }
                                //siguiente();
                            } else {
                                Toast.makeText(getApplicationContext(), "Seleciona una opcion...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );

            return rootView;
        }

        @Override
        public void onStart() {
            super.onStart();
        }

    }

    public class FillingGapsFragment extends ListFragment {

        private FillingGaps fillingGaps;
        private TextView tip;
        private Button buttonNext;
        private ViewGroup rootView;
        private int posSelec;
        private ArrayList<String> contestadas;
        boolean elemNull;

        public FillingGapsFragment() {

        }

        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = (ViewGroup) inflater.inflate(R.layout.question_filling_gaps, container, false);
            tip = (TextView) rootView.findViewById(R.id.tipo_fill_gaps);
            tip.setText("Tipo: FILLING GAPS");
            buttonNext = (Button) rootView.findViewById(R.id.btn_fill_gaps);
            fillingGaps = (FillingGaps) question;
            contestadas = new ArrayList<String>(fillingGaps.getRespuestas().size());
            elemNull = false;

            for (String res: fillingGaps.getRespuestas()){
                contestadas.add(null);
            }

            buttonNext.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (String s : contestadas)
                                if (s == null)
                                    elemNull = true;

                            if (elemNull == true) {
                                Toast.makeText(getApplicationContext(), "Completa todos los espacios...", Toast.LENGTH_SHORT).show();
                            }else {
                                if (contestadas.equals(fillingGaps.getRespuestas())){
                                    //new SimpleDialog("Correcto!", "").show(getSupportFragmentManager(), "SimpleDialog");
                                    Toast.makeText(getApplicationContext(), "¡CORRECTO!", Toast.LENGTH_LONG).show();
                                    siguiente();

                                }else {
                                    new SimpleDialog("Incorrecto!", "").show(getSupportFragmentManager(), "SimpleDialog");

                                }
                            }

                        }
                    }
            );


            makeList();
            return rootView;
        }

        public void makeList(){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.text_edit_filling, fillingGaps.getList());

            //Asociamos el adaptador a la vista.
            ListView lv = (ListView) rootView.findViewById(android.R.id.list);
            lv.setAdapter(adapter);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);

            this.posSelec = position;
            // Hace click sobre un huevo
            if (position % 2 == 1) {
                //Toast.makeText(getApplicationContext(), l.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                ListDialog newFragment = new ListDialog(fillingGaps.getRespuestas());
                newFragment.show(getSupportFragmentManager(), "ListRespuestas");

            }

        }


        public class ListDialog extends DialogFragment {
            private String[] respuestas;

            public ListDialog(ArrayList<String> respuestas){
                this.respuestas = new String[respuestas.size()];
                for (int i = 0; i < respuestas.size(); i++){
                    this.respuestas[i] = respuestas.get(i);
                }
            }
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                ArrayList<String> dd = new ArrayList<>();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Selecciona una opción")
                        .setItems(respuestas, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(getApplicationContext(), String.valueOf(which), Toast.LENGTH_SHORT).show();

                                contestadas.set((posSelec/2) ,respuestas[which]);
                                fillingGaps.setList(posSelec, which);

                                makeList();
                                // The 'which' argument contains the index position
                                // of the selected item
                            }
                        });
                return builder.create();
            }


        }

    }


    public class SimpleDialog extends DialogFragment {

        private String correct;
        private String comment;

        public SimpleDialog(String correct, String comment) {
            this.correct = correct;
            this.comment = comment;

        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return createSimpleDialog();
        }

        /**
         * Crea un diálogo de alerta sencillo
         *
         * @return Nuevo diálogo
         */
        public AlertDialog createSimpleDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle(correct)
                    .setMessage(comment)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    siguiente();
                                }
                            });

            return builder.create();
        }
    }


}
// TODO !!!!!!!!
// Cada vez que se pulsa una opcion se abre un dialogo con las opciones disponibles
// cada vez que se seleciona un una op se llama un metodo que llama al array con con el numero de respuestas seleccionaas!!!!

