package com.skool360admin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anandniketan.com.bhadajadmin.Model.MIS.MISAccountModel;
import anandniketan.com.bhadajadmin.R;

public class MISAccountHeaderAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<T> accountDataModel;
    SpannableStringBuilder discriptionSpanned;
    private String discriptionStr;
    private int type;

    public MISAccountHeaderAdapter(Context mContext, List<T> standardDataModel, int type) {
        this.context = mContext;
        this.accountDataModel = standardDataModel;
        this.type = type;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       viewType = type;
        switch (viewType) {
            case 0:
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mis_account_header_list, parent, false);
                return new MyViewHolder(itemView);
//            case 2:
//                View itemView1 = LayoutInflater.from(parent.getContext()).inflate(R.layout._list_item_mis_absent3day_student, parent, false);
//                return new MISDataListAdapter.MyViewHolder(itemView1);

        }


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String sr = String.valueOf(position + 1);
        switch (type) {
            case 0:
                if(holder instanceof MISAccountHeaderAdapter.MyViewHolder){
                     MISAccountModel.Datum  datum = (MISAccountModel.Datum)accountDataModel.get(position);

                    ((MISAccountHeaderAdapter.MyViewHolder) holder).txt_key.setText("Total to be collect");
                    ((MISAccountHeaderAdapter.MyViewHolder) holder).txt_value.setText(String.valueOf(datum.getTotalAmount()));


                    ((MISAccountHeaderAdapter.MyViewHolder) holder).txt_key1.setText("Total Collection");
                    ((MISAccountHeaderAdapter.MyViewHolder) holder).txt_value1.setText(String.valueOf(datum.getRecievedAmount()));

                    ((MISAccountHeaderAdapter.MyViewHolder) holder).txt_key2.setText("Total Dues");
                    ((MISAccountHeaderAdapter.MyViewHolder) holder).txt_value2.setText(String.valueOf(datum.getDueAmount()));

                    break;

                }

//            case 2:
//
//                break;
        }

    }

    @Override
    public int getItemCount() {
        return accountDataModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_key,txt_value,txt_key1,txt_value1,txt_key2,txt_value2;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_key = (TextView) itemView.findViewById(R.id.txt_key);
            txt_value = (TextView) itemView.findViewById(R.id.txt_value);
            txt_key1 = (TextView) itemView.findViewById(R.id.txt_key1);
            txt_value1 = (TextView) itemView.findViewById(R.id.txt_value1);
            txt_key2 = (TextView) itemView.findViewById(R.id.txt_key2);
            txt_value2 = (TextView) itemView.findViewById(R.id.txt_value2);
        }
    }



}
