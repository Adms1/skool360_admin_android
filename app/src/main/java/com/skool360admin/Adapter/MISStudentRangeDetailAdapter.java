package com.skool360admin.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import anandniketan.com.bhadajadmin.Fragment.Fragment.MISStudentRangeDetailFragment;
import anandniketan.com.bhadajadmin.Model.MIS.MISStudentRange;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;

//created by Antra 10/01/2019

public class MISStudentRangeDetailAdapter extends RecyclerView.Adapter<MISStudentRangeDetailAdapter.MyViewHolder> {
    private Context context;
    private List<MISStudentRange.FinalArray> standardDataModel;
    private Fragment fragment = null;
    private String classid, stdid;
    private FragmentManager fragmentManager = null;

    public MISStudentRangeDetailAdapter(Context mContext, List<MISStudentRange.FinalArray> standardDataModel,
                                        String classid, String stdid) {
        this.context = mContext;
        this.standardDataModel = standardDataModel;
        this.classid = classid;
        this.stdid = stdid;
    }

    @Override
    public MISStudentRangeDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mis_totlal_range_student, parent, false);

        return new MISStudentRangeDetailAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MISStudentRangeDetailAdapter.MyViewHolder holder, final int position) {
        holder.txt_range.setText(standardDataModel.get(position).getRange());
        holder.txt_NoOfStudents.setText(standardDataModel.get(position).getCount());

        holder.ivIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new MISStudentRangeDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("TermDetailID", AppConfiguration.schoolResultTermID);
                bundle.putString("StandardID", stdid);
                bundle.putString("ClassID", classid);
                bundle.putString("RangeID", standardDataModel.get(position).getRange());
                bundle.putString("count", standardDataModel.get(position).getCount());
//                        bundle.putString("Date",AppConfiguration.staffDate);
//                        bundle.putString("deptID",String.valueOf(dataValues.get(position).getDepartmentID()));
                fragment.setArguments(bundle);
                fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
            }
        });
    }

    @Override
    public int getItemCount() {
        return standardDataModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_range, txt_NoOfStudents;
        ImageView ivIndicator;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_range = itemView.findViewById(R.id.range_txt_range);
            txt_NoOfStudents = itemView.findViewById(R.id.range_txt_no);
            ivIndicator = itemView.findViewById(R.id.range_iv_indicatior);

        }
    }

}

