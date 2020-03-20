package com.driverlicense.tests.adapters;


import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.driverlicense.tests.R;
import com.driverlicense.tests.models.Sheet;

import org.w3c.dom.Text;

import java.util.List;


public class AnswerSheetAdapter extends RecyclerView.Adapter<AnswerSheetAdapter.ViewHolder> {

    private List<Sheet> sheetList;
    private Context context;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public AnswerSheetAdapter(Context context, List<Sheet> sheetList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.sheetList = sheetList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_answer_sheet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Sheet a = sheetList.get(position);

        int questionNumber = a.getSavedQuestionNumber();
        String scorePercentage = "Q -"+ questionNumber;
        holder.TextViewAnswerSheetQuestionNumber.setText(scorePercentage);

        holder.TextViewAnswerSheetQuestion.setText(String.valueOf(a.getSavedQuestion()));
        holder.textViewAnswerSheetCorrectAnswer.setText(String.valueOf(a.getSavedCorrectAnswer()));

       if(a.getSavedIncorrectAnswer()==null) {
           holder.textViewAnswerSheetIncorrectAnswer.setVisibility(View.GONE);

        }
       else{
           holder.textViewAnswerSheetIncorrectAnswer.setVisibility(View.VISIBLE);
           holder.textViewAnswerSheetIncorrectAnswer.setPaintFlags(holder.textViewAnswerSheetIncorrectAnswer.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
           holder.textViewAnswerSheetIncorrectAnswer.setText(a.getSavedIncorrectAnswer());
       }
    }

    @Override
    public int getItemCount() {
        return sheetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView TextViewAnswerSheetQuestionNumber;
        TextView TextViewAnswerSheetQuestion;
        TextView textViewAnswerSheetCorrectAnswer;
        TextView textViewAnswerSheetIncorrectAnswer;


        ViewHolder(View itemView) {
            super(itemView);
            TextViewAnswerSheetQuestionNumber = itemView.findViewById(R.id.text_view_answer_sheet_question_number);
            TextViewAnswerSheetQuestion = itemView.findViewById(R.id.text_view_answer_sheet_question);
            textViewAnswerSheetCorrectAnswer = itemView.findViewById(R.id.text_view_answer_sheet_correct_answer);
            textViewAnswerSheetCorrectAnswer = itemView.findViewById(R.id.text_view_answer_sheet_correct_answer);
            textViewAnswerSheetIncorrectAnswer = itemView.findViewById(R.id.text_view_answer_sheet_incorrect_answer);

            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Sheet getItem(int id) {
        return sheetList.get(id);
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
