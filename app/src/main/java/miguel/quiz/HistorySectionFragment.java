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

/**
 * Created by migueltorresporta on 3/4/16.
 */
// Fragment para las historias
public class HistorySectionFragment extends Fragment {

    private static final String TAG = HistorySectionFragment.class.getSimpleName();


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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        static TextView card_title;
        static TextView card_text;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));
            card_title = (TextView)itemView.findViewById(R.id.card_title);
            card_text = (TextView)itemView.findViewById(R.id.card_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, HistoryAuthor.class);

                    intent.putExtra("name", MainActivity.getTestByPosition(getAdapterPosition()).getName());
                    intent.putExtra("description", MainActivity.getTestByPosition(getAdapterPosition()).getDescription());
                    context.startActivity(intent);
                }
            });


        }


    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder>{
        // Set numbers of Card in RecyclerView.

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ViewHolder.card_title.setText(MainActivity.getTestByPosition(position).getName());
            ViewHolder.card_text.setText(MainActivity.getTestByPosition(position).getDescription());
        }

        @Override
        public int getItemCount() {
            return MainActivity.getSize();
        }



    }



}

