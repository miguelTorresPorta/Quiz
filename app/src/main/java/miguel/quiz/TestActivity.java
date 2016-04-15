package miguel.quiz;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

        switch (question.getTipo()){
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

        if(position < numQuestions) {
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
        }else{
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

        public TrueFalseFragment(){

        }

        public TrueFalseFragment createInstance() {
            TrueFalseFragment trueFalseFragment = new TrueFalseFragment();
            return trueFalseFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.question_true_false, container, false);


            trueFalse = (TrueFalse) question;
            final boolean verdadero = trueFalse.isCorrecto();
            final String respuesta = trueFalse.getRespuesta();

            tip = (TextView)rootView.findViewById(R.id.txtTipo);
            titu = (TextView)rootView.findViewById(R.id.txtTit);
            buttonNext = (Button)rootView.findViewById(R.id.btnSiguienteTrueFalse);
            rFalse = (RadioButton)rootView.findViewById(R.id.radioButtonFalse);
            rTrue = (RadioButton)rootView.findViewById(R.id.radioButtonTrue);


            tip.setText("Tipo: TRUE / FALSE");
            titu.setText(trueFalse.getTituloPregunta());

            // Setear escucha para el fab
            buttonNext.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (rTrue.isChecked()){
                                if (verdadero) {
                                    // En algún lugar de tu actividad
                                    new SimpleDialog("Correcto!", respuesta).show(getSupportFragmentManager(), "SimpleDialog");
                                    //Toast.makeText(getApplicationContext(), "CORRECTO!!", Toast.LENGTH_SHORT).show();
                                }else {
                                    // En algún lugar de tu actividad
                                    new SimpleDialog("Incorrecto!", respuesta).show(getSupportFragmentManager(), "SimpleDialog");
                                    //Toast.makeText(getApplicationContext(), "INCORRECTO!!", Toast.LENGTH_SHORT).show();
                                }

                            }else if(rFalse.isChecked()){
                                if (verdadero) {
                                    // En algún lugar de tu actividad
                                    new SimpleDialog("Incorrecto!", respuesta).show(getSupportFragmentManager(), "SimpleDialog");
                                    //Toast.makeText(getApplicationContext(), "INCORRECTO!!", Toast.LENGTH_SHORT).show();
                                }else {
                                    // En algún lugar de tu actividad
                                    new SimpleDialog("Correcto!", respuesta).show(getSupportFragmentManager(), "SimpleDialog");
                                    //Toast.makeText(getApplicationContext(), "CORRECTO!!", Toast.LENGTH_SHORT).show();
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

        public MultipleChoiceFragment(){

        }

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.question_multiple_choice, container, false);

            tip = (TextView)rootView.findViewById(R.id.tipo_mult_choice);
            titu = (TextView)rootView.findViewById(R.id.titulo_mult_choice);
            buttonNext = (Button)rootView.findViewById(R.id.sig_mult_choice);
            checkBox1 = (CheckBox)rootView.findViewById(R.id.checkBoxMult1);
            checkBox2 = (CheckBox)rootView.findViewById(R.id.checkBoxMult2);
            checkBox3 = (CheckBox)rootView.findViewById(R.id.checkBoxMult3);

            multipleChoice = (MultipleChoice) question;

            tip.setText("Tipo: MULTIPLE CHOICE");
            titu.setText(multipleChoice.getTituloPregunta());
            checkBox1.setText(multipleChoice.getFeebackByPos(0));
            checkBox2.setText(multipleChoice.getFeebackByPos(1));
            checkBox3.setText(multipleChoice.getFeebackByPos(2));

            buttonNext.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Iniciar actividad de actualización
                            siguiente();
                        }
                    }
            );

            return rootView;
        }

        @Override
        public void onStart(){
            super.onStart();
        }

    }

    public class FillingGapsFragment extends Fragment {

        private ViewGroup layout;
        private FillingGaps fillingGaps;
        private TextView tip;
        private Button buttonNext;
        private ArrayList<String> respuestasSeleccionadas;

        public FillingGapsFragment() {

        }


        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.question_filling_gaps, container, false);
            tip = (TextView)rootView.findViewById(R.id.tipo_fill_gaps);
            tip.setText("Tipo: FILLING GAPS");
            buttonNext = (Button) rootView.findViewById(R.id.btn_fill_gaps);
            fillingGaps = (FillingGaps) question;

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.text_edit_filling, fillingGaps.getList());

            //Asociamos el adaptador a la vista.
            ListView lv = (ListView) rootView.findViewById(R.id.listViewFill);
            lv.setAdapter(adapter);
            return rootView;
        }


    }


    /*    @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.question_filling_gaps, container, false);
            tip = (TextView)rootView.findViewById(R.id.tipo_fill_gaps);
            layout = (ViewGroup) rootView.findViewById(R.id.content);
            buttonNext = (Button) rootView.findViewById(R.id.btn_fill_gaps);
            fillingGaps = (FillingGaps) question;
            respuestasSeleccionadas = new ArrayList<String>();
            tip.setText("Tipo: FILLING GAPS");
            // Llenamos todos
            addChild(fillingGaps.getTit(), fillingGaps.getRespuestas());
            buttonNext.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO
                            siguiente();
                        }
                    }
            );
            return rootView;
        }



        private void addChild(ArrayList<String> listTitulo, ArrayList<String> listRespuestas){
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            int id = R.layout.text_edit_filling;

            for(int i=0; i<listRespuestas.size(); i++){
                LinearLayout relativeLayout = (LinearLayout) inflater.inflate(id, null, false);
                TextView textView = (TextView) relativeLayout.findViewById(R.id.textViewDate);
                Button but = (Button) relativeLayout.findViewById(R.id.editTextFill);
                textView.setText(listTitulo.get(i));
                but.setText(listRespuestas.get(i));
                layout.addView(relativeLayout);
            }
        }

    }

    */


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

