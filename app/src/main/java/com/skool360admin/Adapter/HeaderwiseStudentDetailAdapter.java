package com.skool360admin.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.bhadajadmin.Model.MIS.HeadwiseStudent;
import anandniketan.com.bhadajadmin.R;

// created by Antra 8/1/2019

public class HeaderwiseStudentDetailAdapter extends RecyclerView.Adapter<HeaderwiseStudentDetailAdapter.MyViewHolder> {

    private Context context;
    private List<HeadwiseStudent.Finalarray> dataValues = new ArrayList<HeadwiseStudent.Finalarray>();

    public HeaderwiseStudentDetailAdapter(Context mContext, List<HeadwiseStudent.Finalarray> dataValues) {
        this.context = mContext;
        this.dataValues = dataValues;
    }

    @Override
    public HeaderwiseStudentDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mis_finance_list, parent, false);
        return new HeaderwiseStudentDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HeaderwiseStudentDetailAdapter.MyViewHolder holder, final int position) {

        try {
            holder.ll.setBackground(context.getResources().getDrawable(R.drawable.greybg_border));

            holder.head_txt.setText(String.valueOf(dataValues.get(position).getHead()));
            holder.term1_credit_txt.setText(String.valueOf(dataValues.get(position).getRecievedFeesTerm1()));
            holder.term1_debit_txt.setText(String.valueOf(dataValues.get(position).getTotalFeesTerm1()));
            holder.term2_credit_txt.setText(String.valueOf(dataValues.get(position).getRecievedFeesTerm2()));
            holder.term2_debit_txt.setText(String.valueOf(dataValues.get(position).getTotalFeesTerm2()));
            holder.term1_due.setText(String.valueOf(dataValues.get(position).getTerm1due()));
            holder.term2_due.setText(String.valueOf(dataValues.get(position).getTerm2due()));

            if (position == (dataValues.size() - 1)) {
                holder.head_txt.setTypeface(holder.head_txt.getTypeface(), Typeface.BOLD);
                holder.term1_credit_txt.setTypeface(holder.term1_credit_txt.getTypeface(), Typeface.BOLD);
                holder.term1_debit_txt.setTypeface(holder.term1_debit_txt.getTypeface(), Typeface.BOLD);
                holder.term2_credit_txt.setTypeface(holder.term2_credit_txt.getTypeface(), Typeface.BOLD);
                holder.term2_debit_txt.setTypeface(holder.term2_debit_txt.getTypeface(), Typeface.BOLD);
                holder.term1_due.setTypeface(holder.term2_credit_txt.getTypeface(), Typeface.BOLD);
                holder.term2_due.setTypeface(holder.term2_debit_txt.getTypeface(), Typeface.BOLD);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataValues == null ? 0 : dataValues.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView head_txt, term1_credit_txt, term1_debit_txt, term2_credit_txt, term2_debit_txt, term1_due, term2_due;
        LinearLayout ll;

        public MyViewHolder(View itemView) {
            super(itemView);
            head_txt = itemView.findViewById(R.id.head_txt);
            term1_credit_txt = itemView.findViewById(R.id.term1_credit_txt);
            term1_debit_txt = itemView.findViewById(R.id.term1_debit_txt);
            term2_credit_txt = itemView.findViewById(R.id.term2_credit_txt);
            term2_debit_txt = itemView.findViewById(R.id.term2_debit_txt);
            term1_due = itemView.findViewById(R.id.term1_due_txt);
            term2_due = itemView.findViewById(R.id.term2_due_txt);
            ll = itemView.findViewById(R.id.llHeader);
        }
    }
}