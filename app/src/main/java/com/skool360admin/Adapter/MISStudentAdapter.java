package com.skool360admin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anandniketan.com.bhadajadmin.Model.MIS.MISStudentModel;
import anandniketan.com.bhadajadmin.R;

public class MISStudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<MISStudentModel.StandardDatum> standardDataModel;
    SpannableStringBuilder discriptionSpanned;
    private String discriptionStr;
    private int type;

    public MISStudentAdapter(Context mContext, List<MISStudentModel.StandardDatum> standardDataModel, int type) {
        this.context = mContext;
        this.standardDataModel = standardDataModel;
        this.type = type;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mis_totlal_student, parent, false);
                return new MISStudentAdapter.MyViewHolder(itemView);
//            case 2:
//                View itemView1 = LayoutInflater.from(parent.getContext()).inflate(R.layout._list_item_mis_absent3day_student, parent, false);
//                return new MISDataListAdapter.MyViewHolder(itemView1);

        }


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String sr = String.valueOf(position + 1);
        switch (holder.getItemViewType()) {
            case 0:
                if(holder instanceof MyViewHolder){
                    MISStudentModel.StandardDatum  datum= standardDataModel.get(position);
                    ((MyViewHolder) holder).txt_NoOfStudents.setText(String.valueOf(datum.getTotalStudent()));
                    ((MyViewHolder) holder).txt_standard.setText(String.valueOf(datum.getStandard()));

                }
                break;

//            case 2:
//
//                break;
        }

    }

    @Override
    public int getItemCount() {
        return standardDataModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_standard,txt_NoOfStudents;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_standard = (TextView) itemView.findViewById(R.id.txt_grade);
            txt_NoOfStudents = (TextView) itemView.findViewById(R.id.txt_no);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

}
