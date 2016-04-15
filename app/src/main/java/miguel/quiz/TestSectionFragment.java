package miguel.quiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * Created by migueltorresporta on 4/4/16.
 */
public class TestSectionFragment extends Fragment {

/*
        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

        }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.quiz_activity, container, false);

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return recyclerView;

        // Create datasource
        //List<String> listAutores = new ArrayList<String>();
        //for (int i = 0; i < MainActivity.getSize(); i++){
        //    listAutores.add(MainActivity.getTestByPosition(i).getName());

        //}


        // Create adapter
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_layout_test, R.id.txtitem, listAutores);

        // Bind adapter to the list fragment
        //setListAdapter(adapter);

        // Retail listFragment instance across configuration changes
        //setRetainInstance(true);

        //return rootView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        static TextView name;
        static TextView numPreguntas;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_quiz, parent, false));

            name = (TextView)itemView.findViewById(R.id.title_author);
            numPreguntas = (TextView)itemView.findViewById(R.id.num_preg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, TestActivity.class);
                    intent.putExtra("Position", Integer.toString(getAdapterPosition()));

                    context.startActivity(intent);
                }
            });
        }
    }

    // Handling item click
    /*public void onListItemClick (ListView l, View view, int position, long id){
        ViewGroup viewGroup = (ViewGroup) view;

        // Pass of the postition Test to make easier to get the object in the other Activity
        Intent intent = new Intent(getActivity(), TestActivity
                .class);


        intent.putExtra("Position", String.valueOf(position));
        startActivity(intent);

    }*/

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ViewHolder.name.setText(MainActivity.getTestByPosition(position).getName());

            String numPreg = "Numero de preguntas " + Integer.toString(MainActivity.getTestByPosition(position).getNumQuestions());
            ViewHolder.numPreguntas.setText(numPreg);

        }

        @Override
        public int getItemCount() {
            return MainActivity.getSize();
        }
    }

}

