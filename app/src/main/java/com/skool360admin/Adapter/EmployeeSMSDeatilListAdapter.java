package com.skool360admin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.bhadajadmin.Interface.getEmployeeCheck;
import anandniketan.com.bhadajadmin.Model.Other.FinalArraySMSDataModel;
import anandniketan.com.bhadajadmin.R;


/**
 * Created by admsandroid on 12/21/2017.
 */

public class EmployeeSMSDeatilListAdapter extends RecyclerView.Adapter<EmployeeSMSDeatilListAdapter.MyViewHolder> {
    private Context context;
    List<FinalArraySMSDataModel> finalArraySMSDataModelList;
    String stduentIdStr, mobilenoStr, FinalValue;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    getEmployeeCheck listner;

    public EmployeeSMSDeatilListAdapter(Context mContext, List<FinalArraySMSDataModel> finalArraySMSDataModelList, getEmployeeCheck listner) {
        this.context = mContext;
        this.finalArraySMSDataModelList = finalArraySMSDataModelList;
        this.listner = listner;
    }


    @Override
    public EmployeeSMSDeatilListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_sms_detail_list_item, parent, false);

        return new EmployeeSMSDeatilListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EmployeeSMSDeatilListAdapter.MyViewHolder holder, final int position) {
        String sr = String.valueOf(position + 1);

        holder.index_txt.setText(sr);
        holder.employee_name_txt.setText(finalArraySMSDataModelList.get(position).getEmpName());
        holder.mobileno_txt.setText(String.valueOf(finalArraySMSDataModelList.get(position).getEmpMobileNo()));

        holder.mobileno_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                finalArraySMSDataModelList.get(position).setEmpMobileNo(holder.mobileno_txt.getText().toString());

            }
        });

        holder.sms_chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                stduentIdStr = "";
                if (isChecked) {
                    finalArraySMSDataModelList.get(position).setCheck("1");
                    stduentIdStr = finalArraySMSDataModelList.get(position).getPKEmployeeID().toString();
                    mobilenoStr = finalArraySMSDataModelList.get(position).getEmpMobileNo().toString();
                    FinalValue = stduentIdStr + "|" + mobilenoStr;
                    dataCheck.add(FinalValue);
                    Log.d("dataCheck", dataCheck.toString());
                    listner.getEmployeeSMSCheck();
                } else {
                    finalArraySMSDataModelList.get(position).setCheck("0");
                    dataCheck.remove(finalArraySMSDataModelList.get(position).getPKEmployeeID() + "|" +
                            finalArraySMSDataModelList.get(position).getEmpMobileNo());
                    Log.d("dataUnCheck", dataCheck.toString());
                    listner.getEmployeeSMSCheck();
                }
            }
        });

        if (finalArraySMSDataModelList.get(position).getCheck().equalsIgnoreCase("1")) {
            holder.sms_chk.setChecked(true);
        } else {
            holder.sms_chk.setChecked(false);
        }

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
        return finalArraySMSDataModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView index_txt, employee_name_txt;
        EditText mobileno_txt;
        CheckBox sms_chk;

        public MyViewHolder(View itemView) {
            super(itemView);
            index_txt = (TextView) itemView.findViewById(R.id.index_txt);
            employee_name_txt = (TextView) itemView.findViewById(R.id.employee_name_txt);
            mobileno_txt = (EditText) itemView.findViewById(R.id.mobileno_txt);
            sms_chk = (CheckBox) itemView.findViewById(R.id.sms_chk);
        }
    }

    public List<FinalArraySMSDataModel> getDatas() {
        return finalArraySMSDataModelList;
    }

    public ArrayList<String> getData() {
        return dataCheck;
    }
}


