package com.newagemedia.rtoexam.adapters;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.newagemedia.rtoexam.R;
import com.newagemedia.rtoexam.models.Levels;

import java.util.List;


public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.ViewHolder> {

    private List<Levels> dataLevels;
    private Context context;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public LevelsAdapter(Context context, List<Levels> dataLevels) {
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.dataLevels = dataLevels;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_levels, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Levels l = dataLevels.get(position);
        holder.textViewLevelNumber.setText(String.valueOf(l.getLevel_number()));
        holder.textViewLevelName.setText(l.getLevel_name());

        //filling image vector with random color
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_rounded_levels_number).mutate();

        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);

        int randomColor = Color.argb(255, r, g, b);

        holder.imageViewLevelColor.setColorFilter(new PorterDuffColorFilter(randomColor, PorterDuff.Mode.SRC_IN));
        holder.imageViewLevelColor.setImageDrawable(drawable);
    }


    @Override
    public int getItemCount() {
        return dataLevels.size();
    }



    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewLevelNumber;
        TextView textViewLevelName;
        ImageView imageViewLevelColor;

        ViewHolder(View itemView) {
            super(itemView);
            textViewLevelNumber = itemView.findViewById(R.id.text_view_item_level_number);
            textViewLevelName = itemView.findViewById(R.id.text_view_item_level_name);
            imageViewLevelColor = itemView.findViewById(R.id.image_view_level_color);

            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Levels getItem(int id) {
        return dataLevels.get(id);
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
