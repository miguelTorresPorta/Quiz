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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return recyclerView;
    }


    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {
        // Set numbers of List in RecyclerView.

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.name.setText(MainActivity.getTestByPosition(position).getName());

            String numPreg = "Numero de preguntas " + Integer.toString(MainActivity.getTestByPosition(position).getNumQuestions());
            holder.numPreguntas.setText(numPreg);

        }

        @Override
        public int getItemCount() {
            return MainActivity.getSize();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView name;
            TextView numPreguntas;

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
    }

}

