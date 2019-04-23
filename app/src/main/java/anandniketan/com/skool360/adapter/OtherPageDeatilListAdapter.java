package anandniketan.com.skool360.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import anandniketan.com.skool360.Interface.getEmployeeCheck;
import anandniketan.com.skool360.Model.HR.FinalArrayPageListModel;
import anandniketan.com.skool360.R;


/**
 * Created by admsandroid on 12/21/2017.
 */

public class OtherPageDeatilListAdapter extends RecyclerView.Adapter<OtherPageDeatilListAdapter.MyViewHolder> {
    List<FinalArrayPageListModel> pageListmodel;
    String addAllStr, updateStr, deleteStr;
    private Context context;
    private getEmployeeCheck listner;

    public OtherPageDeatilListAdapter(Context mContext, List<FinalArrayPageListModel> pageListmodel, getEmployeeCheck listner) {
        this.context = mContext;
        this.pageListmodel = pageListmodel;
        this.listner = listner;
    }


    @Override
    public OtherPageDeatilListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.other_page_detail_list_item, parent, false);

        return new OtherPageDeatilListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OtherPageDeatilListAdapter.MyViewHolder holder, final int position) {
        String sr = String.valueOf(position + 1);

        holder.other_index_txt.setText(sr);
        holder.other_pagename_txt.setText(pageListmodel.get(position).getPageNam());
        holder.other_underpagename_txt.setText(pageListmodel.get(position).getPageUnderName());
        addAllStr = pageListmodel.get(position).getStatus().toString();
        updateStr = pageListmodel.get(position).getIsUserUpdate().toString();
        deleteStr = pageListmodel.get(position).getIsUserDelete().toString();

        if (addAllStr.equalsIgnoreCase("true")) {
            holder.other_addall_chk.setChecked(true);
        } else {
            holder.other_addall_chk.setChecked(false);
        }

        if (updateStr.equalsIgnoreCase("true")) {
            holder.other_update_chk.setChecked(true);
        } else {
            holder.other_update_chk.setChecked(false);
        }

        if (deleteStr.equalsIgnoreCase("true")) {
            holder.other_delete_chk.setChecked(true);
        } else {
            holder.other_delete_chk.setChecked(false);
        }

        holder.other_addall_chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pageListmodel.get(position).setStatus(true);
                    listner.getEmployeeSMSCheck();
                } else {
                    pageListmodel.get(position).setStatus(false);
                    listner.getEmployeeSMSCheck();
                }
            }
        });
        holder.other_update_chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pageListmodel.get(position).setIsUserUpdate(true);
                    listner.getEmployeeSMSCheck();
                } else {
                    pageListmodel.get(position).setIsUserUpdate(false);
                    listner.getEmployeeSMSCheck();
                }
            }
        });
        holder.other_delete_chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pageListmodel.get(position).setIsUserDelete(true);
                    listner.getEmployeeSMSCheck();
                } else {
                    pageListmodel.get(position).setIsUserDelete(false);
                    listner.getEmployeeSMSCheck();
                }
            }
        });

    }

    @Override
    public long getItemId(int position) {
// return specific item's id here
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return pageListmodel.size();
    }

    public List<FinalArrayPageListModel> getDatas() {
        return pageListmodel;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView other_index_txt, other_pagename_txt, other_underpagename_txt;
        CheckBox other_addall_chk, other_update_chk, other_delete_chk;

        public MyViewHolder(View itemView) {
            super(itemView);
            other_index_txt = itemView.findViewById(R.id.other_index_txt);
            other_pagename_txt = itemView.findViewById(R.id.other_pagename_txt);
            other_underpagename_txt = itemView.findViewById(R.id.other_underpagename_txt);
            other_addall_chk = itemView.findViewById(R.id.other_addall_chk);
            other_update_chk = itemView.findViewById(R.id.other_update_chk);
            other_delete_chk = itemView.findViewById(R.id.other_delete_chk);
        }
    }
}


