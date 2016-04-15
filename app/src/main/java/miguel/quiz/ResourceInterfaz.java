package miguel.quiz;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import parser.ParserCsv;
import question.FillingGaps;
import question.MultipleChoice;
import question.Question;
import question.TrueFalse;


/**
 * Created by migueltorresporta.
 */

// Class who list in one activity all the questions
// DEPRECATED TODO Delete
public class ResourceInterfaz extends ListActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.test_interfaz);

        ArrayList<Question> f = new ArrayList<Question>();
        f = ParserCsv.LeerFicheroCsv(getResources().openRawResource(R.raw.test1));


        //TaskAdapter items = new TaskAdapter(this, R.layout.test_interfaz, f, getLayoutInflater());
        //asignar adaptador a la lista
        //setListAdapter(items);

        //setListAdapter(new ArrayAdapter<ListQuestion>(this, android.R.layout.simple_list_item_multiple_choice, f));
        //setContentView(R.layout.test_interfaz);

    }



    private void showMessage(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration =Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    private class TaskAdapter extends ArrayAdapter<Question>{
        private LayoutInflater mInflater;
        private ArrayList<Question> mObjects;


        private TaskAdapter(Context context, int resource, ArrayList<Question> objects, LayoutInflater mInflater) {
            super(context, resource, objects);
            this.mInflater = mInflater;
            this.mObjects = objects;

        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Question listEntry = mObjects.get(position);
            // obtención de la vista de la línea de la tabla
            View row = null;

            // Utilizar iconos dependiendo de la dificultad
            // dependiendo de la deficultad, se muestran distintos iconos
            //ImageView icon = (ImageView) row.findViewById(R.id.row_importance);
            //icon.setTag(new Integer(listEntry.id));
            switch (listEntry.getTipo()) {
                case "filling the gaps":
                    //row = mInflater.inflate(R.layout.name_question, null);
                    //TextView text1 = (TextView) row.findViewById(R.id.text_name);
                    //text1.setText(listEntry.getTituloPregunta());
                    //icon.setImageResource(R.drawable.ic_green);
                    break;
                case "multiple choice":
                    //row = mInflater.inflate(R.layout.name_question, null);
                    //TextView text2 = (TextView) row.findViewById(R.id.text_name);
                    //text2.setText(listEntry.getTituloPregunta());
                    //icon.setImageResource(R.drawable.ic_yellow);
                    break;
                case "true / false":
                    //row = mInflater.inflate(R.layout.name_question, null);
                    //TextView text3 = (TextView) row.findViewById(R.id.text_name);
                    //text3.setText(listEntry.getTituloPregunta());
                    break;
                default:
                    showMessage("sdf");
                    break;
            }
            return row;
        }

    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
//        ListQuestion o = (ListQuestion) l.getItemAtPosition(position);
        String selection = l.getItemAtPosition(position).toString();
        Intent intent = null;

        switch (selection){
            case "filling the gaps":
                intent = new Intent(this, FillingGaps.class);
                startActivity(intent);
                break;
            case "multiple choice":
                //MultipleChoice o = (MultipleChoice) l.getItemAtPosition(position);
                intent = new Intent(this, MultipleChoice.class);
                //intent.putExtra("salutation", o.getRespuestas());
                startActivity(intent);
                break;
            case "true / false":
                intent = new Intent(this, TrueFalse.class);
                startActivity(intent);
                break;
            default:
                break;

        }


    }



}
