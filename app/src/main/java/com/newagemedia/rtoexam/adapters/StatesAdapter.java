package com.newagemedia.rtoexam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.newagemedia.rtoexam.R;
import com.newagemedia.rtoexam.models.States;

import java.util.List;

public class StatesAdapter extends RecyclerView.Adapter<StatesAdapter.ViewHolder> {

    private List<States> dataStates;
    private Context context;
    private LayoutInflater mInflater;
    private StatesAdapter.ItemClickListener mClickListener;
    private int lastSelectedPosition = -1;
    public StatesAdapter(Context context, List<States> dataStates) {
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.dataStates = dataStates;
    }

    public StatesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_states, parent, false);
        return new StatesAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(StatesAdapter.ViewHolder holder, int position) {
        States s = dataStates.get(position);
        holder.textViewStateName.setText(s.getState_name());
        holder.textViewCapitalName.setText(s.getCapital_name());
        holder.radioButtonSelected.setChecked(lastSelectedPosition == position);
    }


    @Override
    public int getItemCount() {
        return dataStates.size();
    }



    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewStateName;
        TextView textViewCapitalName;
        RadioButton radioButtonSelected;

        ViewHolder(View itemView) {
            super(itemView);
            textViewStateName = itemView.findViewById(R.id.text_view_item_state_name);
            textViewCapitalName = itemView.findViewById(R.id.text_view_item_capital_name);
            radioButtonSelected =  itemView.findViewById(R.id.radio_button_state_select);

            itemView.setOnClickListener(this);

            radioButtonSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });



        }

        @Override
        public void onClick(View view) {

            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            //updating radiobutton
            lastSelectedPosition = getAdapterPosition();
            notifyDataSetChanged();
        }
    }

    // convenience method for getting data at click position
    public States getItem(int id) {
        return dataStates.get(id);
    }


    public void setContext(Context context) {
        this.context = context;
    }

    // allows clicks events to be caught
    public void setClickListener(StatesAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}