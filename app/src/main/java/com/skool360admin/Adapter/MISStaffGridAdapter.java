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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.bhadajadmin.Fragment.Fragment.MISDataListFragment;
import anandniketan.com.bhadajadmin.Model.MIS.MISStaffNewModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;

public class MISStaffGridAdapter extends RecyclerView.Adapter<MISStaffGridAdapter.MyViewHolder> {

    private Context context;
    private List<MISStaffNewModel.FinalArray> dataValues = new ArrayList<MISStaffNewModel.FinalArray>();
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private Bundle bundle;


    public MISStaffGridAdapter(Context mContext, List<MISStaffNewModel.FinalArray> dataValues) {
        this.context = mContext;
        this.dataValues = dataValues;


    }

    @Override
    public MISStaffGridAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_staff_grid_iten, parent, false);
        return new MISStaffGridAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MISStaffGridAdapter.MyViewHolder holder, final int position) {
        try {
            String sr = String.valueOf(position + 1);

            holder.dept_txt.setText(dataValues.get(position).getDepartment());
            holder.total_txt.setText(String.valueOf(dataValues.get(position).getTotal()));
            holder.absent_txt.setText(String.valueOf(dataValues.get(position).getTotalAbsent()));
            holder.leave_txt.setText(String.valueOf(dataValues.get(position).getTotalLeave()));

            holder.total_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragment = new MISDataListFragment();
                    bundle = new Bundle();
                    bundle.putString("title","Staff New");
                    bundle.putString("requestType","Total");
                    bundle.putString("requestTitle",holder.dept_txt.getText().toString());
                    bundle.putString("TermID",AppConfiguration.TermId);
                    bundle.putString("countdata",holder.total_txt.getText().toString());
                    bundle.putString("Date",AppConfiguration.staffDate);
                    bundle.putString("deptID", String.valueOf(dataValues.get(position).getDepartmentID()));
                    fragment.setArguments(bundle);
                    fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in,R.anim.zoom_out)
                            .add(R.id.frame_container,fragment).addToBackStack(null).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 65;
                }
            });

            holder.absent_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragment = new MISDataListFragment();
                    bundle = new Bundle();
                    bundle.putString("title","Staff New");
                    bundle.putString("requestType","Absent");
                    bundle.putString("requestTitle",holder.dept_txt.getText().toString());
                    bundle.putString("TermID",AppConfiguration.TermId);
                    bundle.putString("countdata", holder.absent_txt.getText().toString());
                    bundle.putString("Date",AppConfiguration.staffDate);
                    bundle.putString("deptID", String.valueOf(dataValues.get(position).getDepartmentID()));
                    fragment.setArguments(bundle);
                    fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in,R.anim.zoom_out)
                            .add(R.id.frame_container,fragment).addToBackStack(null).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 65;
                }
            });

            holder.leave_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragment = new MISDataListFragment();
                    bundle = new Bundle();
                    bundle.putString("title","Staff New");
                    bundle.putString("requestType","Leave");
                    bundle.putString("requestTitle",holder.dept_txt.getText().toString());
                    bundle.putString("TermID",AppConfiguration.TermId);
                    bundle.putString("countdata",holder.leave_txt.getText().toString());
                    bundle.putString("Date",AppConfiguration.staffDate);
                    bundle.putString("deptID", String.valueOf(dataValues.get(position).getDepartmentID()));
                    fragment.setArguments(bundle);
                    fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in,R.anim.zoom_out)
                            .add(R.id.frame_container,fragment).addToBackStack(null).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 65;
                }
            });


        }catch (Exception ex){

            ex.printStackTrace();
        }



    }

    @Override
    public int getItemCount() {
        return dataValues == null ? 0 :dataValues.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dept_txt,total_txt,absent_txt,leave_txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            dept_txt = itemView.findViewById(R.id.dept_txt);
            total_txt = itemView.findViewById(R.id.total_txt);
            absent_txt = itemView.findViewById(R.id.absent_txt);
            leave_txt = itemView.findViewById(R.id.leave_txt);

        }
    }
}
