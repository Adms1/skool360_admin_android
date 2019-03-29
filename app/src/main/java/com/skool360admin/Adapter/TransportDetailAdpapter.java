package com.skool360admin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import anandniketan.com.bhadajadmin.Model.MIS.TransportMainModel;
import anandniketan.com.bhadajadmin.R;

public class TransportDetailAdpapter extends RecyclerView.Adapter<TransportDetailAdpapter.MyViewHolder> {
    private Context context;
    private ArrayList<TransportMainModel.StudentDatum> transportDetailModelList;
//    private String reqType;

    public TransportDetailAdpapter(Context mContext, ArrayList<TransportMainModel.StudentDatum> transportDetailModelList) {
        this.context = mContext;
        this.transportDetailModelList = transportDetailModelList;
//        this.reqType = reqType;
    }

    @Override
    public TransportDetailAdpapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_detail_transport, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.ll.setVisibility(View.VISIBLE);
        holder.ll1.setVisibility(View.GONE);

        holder.class_txt.setTextColor(context.getResources().getColor(R.color.black));
        holder.stuname.setTextColor(context.getResources().getColor(R.color.black));
        holder.grno.setTextColor(context.getResources().getColor(R.color.black));
        holder.phno.setTextColor(context.getResources().getColor(R.color.black));


        holder.class_txt.setText(transportDetailModelList.get(position).getGrade() + "-" + transportDetailModelList.get(position).getSection());
        holder.stuname.setText(transportDetailModelList.get(position).getStudentname());
        holder.grno.setText(transportDetailModelList.get(position).getGrno());
        holder.phno.setText(transportDetailModelList.get(position).getPhoneno());

    }

    @Override
    public long getItemId(int position) {
// return specific item's id here
        return position;
    }

    @Override
    public int getItemCount() {
        return transportDetailModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView class_txt, stuname, grno, phno;

        LinearLayout ll, ll1;

        public MyViewHolder(View itemView) {
            super(itemView);
            class_txt = itemView.findViewById(R.id.transportd_class_txt);
            stuname = itemView.findViewById(R.id.transportd_stuname_txt);
            grno = itemView.findViewById(R.id.transportd_grno_txt);
            phno = itemView.findViewById(R.id.transportd_phno_txt);

            ll = itemView.findViewById(R.id.vieww);
            ll1 = itemView.findViewById(R.id.vieww1);

        }
    }

}
