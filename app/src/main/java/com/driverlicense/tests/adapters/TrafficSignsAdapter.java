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
import com.driverlicense.tests.models.TrafficSigns;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TrafficSignsAdapter extends RecyclerView.Adapter<TrafficSignsAdapter.ViewHolder> implements Filterable {

    Context context;
    private List<TrafficSigns> trafficSignsList;
    private List<TrafficSigns> trafficSignsListFiltered;
    private ItemClickListener itemClickListener;

    public TrafficSignsAdapter(Context context, List trafficSignsList, ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.trafficSignsList = trafficSignsList;
        this.trafficSignsListFiltered = trafficSignsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_traffic_signs, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TrafficSigns trafficSigns = trafficSignsListFiltered.get(position);
        holder.textViewName.setText(trafficSigns.getTrafficSignName());
        Picasso.get()
                .load(trafficSigns.getTrafficSignImageUrl())
                .into(holder.imageViewUrl);
    }

    @Override
    public int getItemCount() {

        return trafficSignsListFiltered.size();

    }

    // Getting filtered data at click position
    public TrafficSigns getFilteredItem(int id) {
        return trafficSignsListFiltered.get(id);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    trafficSignsListFiltered = trafficSignsList;
                } else {
                    List filteredList = new ArrayList<>();
                    String filterPattern = charSequence.toString().toLowerCase().trim();

                    for (TrafficSigns trafficSigns : trafficSignsList) {
                        if (trafficSigns.getTrafficSignName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(trafficSigns);
                        }
                    }
                    trafficSignsListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = trafficSignsListFiltered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                trafficSignsListFiltered = (ArrayList) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textViewName;
        private ImageView imageViewUrl;

        public ViewHolder(View itemView) {

            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_traffic_sign_name);
            imageViewUrl = itemView.findViewById(R.id.image_view_traffic_signs);
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    // Parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int adapterPosition);
    }

}
