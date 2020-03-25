package com.driverlicense.tests.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.driverlicense.tests.R;

import com.driverlicense.tests.models.Reading;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ReadingAdapter extends RecyclerView.Adapter<ReadingAdapter.ViewHolder> implements Filterable {

    Context context;
    private List<Reading> readingList;
    private List<Reading> readingListFiltered;

    public ReadingAdapter(Context context, List readingList) {
        this.context = context;
        this.readingList = readingList;
        this.readingListFiltered = readingList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_reading, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Reading reading = readingListFiltered.get(position);
        holder.textViewQuestion.setText(reading.getQuestion());
        holder.textViewCorrectAnswer.setText(reading.getCorrectAnswer());
        Picasso.get()
                .load(reading.getImageUrl())
                .into(holder.imageViewUrl);
    }

    @Override
    public int getItemCount() {
        return readingListFiltered.size();
    }

    // Getting filtered data at click position
    public Reading getFilteredItem(int id) {
        return readingListFiltered.get(id);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    readingListFiltered = readingList;
                } else {
                    List filteredList = new ArrayList<>();
                    String filterPattern = charSequence.toString().toLowerCase().trim();

                    for (Reading reading : readingList) {
                        if (reading.getCorrectAnswer().toLowerCase().contains(filterPattern)) {
                            filteredList.add(reading);
                        }
                    }
                    readingListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = readingListFiltered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                readingListFiltered = (ArrayList) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewQuestion;
        private TextView textViewCorrectAnswer;
        private ImageView imageViewUrl;

        public ViewHolder(View itemView) {

            super(itemView);
            textViewQuestion = itemView.findViewById(R.id.text_view_reading_question);
            textViewCorrectAnswer = itemView.findViewById(R.id.text_view_reading_correct_answer);
            imageViewUrl = itemView.findViewById(R.id.image_view_reading_image_url);
        }
    }
}