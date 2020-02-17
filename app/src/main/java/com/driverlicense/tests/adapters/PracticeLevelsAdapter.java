package com.driverlicense.tests.adapters;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.driverlicense.tests.R;
import com.driverlicense.tests.models.Practice;

import java.util.List;


public class PracticeLevelsAdapter extends RecyclerView.Adapter<PracticeLevelsAdapter.ViewHolder> {

    private List<Practice> practiceList;
    private Context context;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public PracticeLevelsAdapter(Context context, List<Practice> practiceList) {
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.practiceList = practiceList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_levels, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Practice p = practiceList.get(position);
        holder.textViewLevelNumber.setText(String.valueOf(p.getLevel_number()));
        holder.textViewLevelName.setText(p.getLevel_name());

        int imageResource = context.getResources().getIdentifier(p.getLevel_color(), null, context.getPackageName());
        Drawable res = context.getResources().getDrawable(imageResource);
        holder.imageViewLevelColor.setImageDrawable(res);

        if(p.getLevel_passed()) {
            holder.imageViewLevelPass.setImageResource(R.drawable.ic_level_pass);
        }
    }


    @Override
    public int getItemCount() {
        return practiceList.size();
    }



    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewLevelNumber;
        TextView textViewLevelName;
        ImageView imageViewLevelColor;
        ImageView imageViewLevelPass;
        ViewHolder(View itemView) {
            super(itemView);
            textViewLevelNumber = itemView.findViewById(R.id.text_view_item_level_number);
            textViewLevelName = itemView.findViewById(R.id.text_view_item_level_name);
            imageViewLevelColor = itemView.findViewById(R.id.image_view_level_color);
            imageViewLevelPass = itemView.findViewById(R.id.image_view_level_pass);

            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Practice getItem(int id) {
        return practiceList.get(id);
    }


    public void setContext(Context context) {
        this.context = context;
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
