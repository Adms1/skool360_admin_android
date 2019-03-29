package com.skool360admin.Adapter;

import android.content.Context;
import android.graphics.Typeface;
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

import anandniketan.com.bhadajadmin.Fragment.Fragment.MISStudentTransportDetailFragment;
import anandniketan.com.bhadajadmin.Model.MIS.TransportMainModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;

// created by Antra 11/01/2019

public class TransportAdapter extends RecyclerView.Adapter<TransportAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<TransportMainModel.StandardDatum> transportMainModelList;
    private Bundle bundle;
    private String termid;
    private FragmentManager fragmentManager = null;
    private Fragment fragment = null;

    public TransportAdapter(Context mContext, ArrayList<TransportMainModel.StandardDatum> transportMainModelList, String termid) {
        this.context = mContext;
        this.transportMainModelList = transportMainModelList;
        this.termid = termid;
    }

    @Override
    public TransportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_transport, parent, false);

        return new TransportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TransportAdapter.MyViewHolder holder, final int position) {

        holder.class_txt.setText(transportMainModelList.get(position).getStandard());
        holder.transport_txt.setText(transportMainModelList.get(position).getTransport());
        holder.personal_txt.setText(transportMainModelList.get(position).getPersonal());

        if (position == (transportMainModelList.size() - 1)) {
            holder.class_txt.setTypeface(holder.class_txt.getTypeface(), Typeface.BOLD);
            holder.transport_txt.setTypeface(holder.transport_txt.getTypeface(), Typeface.BOLD);
            holder.personal_txt.setTypeface(holder.personal_txt.getTypeface(), Typeface.BOLD);

//            holder.class_txt.setTextColor(context.getResources().getColor(R.color.blue));
            holder.transport_txt.setTextColor(context.getResources().getColor(R.color.blue));
            holder.personal_txt.setTextColor(context.getResources().getColor(R.color.blue));

        }

        holder.transport_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new MISStudentTransportDetailFragment();
                bundle = new Bundle();
                bundle.putString("title", "Standard Transport");
                bundle.putString("requestType", "");
                bundle.putString("count", transportMainModelList.get(position).getTransport());
                bundle.putString("TermID", termid);

                if (position == (transportMainModelList.size() - 1)) {
                    bundle.putString("stdid", "0");
                } else {
                    bundle.putString("stdid", transportMainModelList.get(position).getStandardid());
                }

//                        bundle.putString("countdata",holder.total_txt.getText().toString());
//                        bundle.putString("Date", AppConfiguration.staffDate);
//                        bundle.putString("deptID",String.valueOf(dataValues.get(position).getDepartmentID()));
                fragment.setArguments(bundle);
                fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
            }

        });

        holder.personal_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new MISStudentTransportDetailFragment();
                bundle = new Bundle();
                bundle.putString("title", "Personal Transport");
                bundle.putString("requestType", "Personal");
                bundle.putString("count", transportMainModelList.get(position).getPersonal());
                bundle.putString("TermID", termid);

                if (position == (transportMainModelList.size() - 1)) {
                    bundle.putString("stdid", "0");
                } else {
                    bundle.putString("stdid", transportMainModelList.get(position).getStandardid());
                }

//                        bundle.putString("countdata",holder.total_txt.getText().toString());
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
    public long getItemId(int position) {
// return specific item's id here
        return position;
    }

    @Override
    public int getItemCount() {
        return transportMainModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView class_txt, transport_txt, personal_txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            class_txt = itemView.findViewById(R.id.transport_class_txt);
            transport_txt = itemView.findViewById(R.id.transport_transport_txt);
            personal_txt = itemView.findViewById(R.id.transport_personal_txt);

        }
    }

}