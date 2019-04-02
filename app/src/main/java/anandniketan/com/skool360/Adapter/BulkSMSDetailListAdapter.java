package anandniketan.com.skool360.Adapter;

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

import anandniketan.com.skool360.Interface.getEmployeeCheck;
import anandniketan.com.skool360.Model.Other.FinalArraySMSDataModel;
import anandniketan.com.skool360.R;


/**
 * Created by admsandroid on 12/21/2017.
 */

public class BulkSMSDetailListAdapter extends RecyclerView.Adapter<BulkSMSDetailListAdapter.MyViewHolder> {
    List<FinalArraySMSDataModel> finalArrayBulkSMSModelList;
    String stduentIdStr, mobilenoStr, FinalValue;
    getEmployeeCheck listner;
    private Context context;
    private ArrayList<String> dataCheck = new ArrayList<String>();


    public BulkSMSDetailListAdapter(Context mContext, List<FinalArraySMSDataModel> finalArrayBulkSMSModelList, getEmployeeCheck listner) {
        this.context = mContext;
        this.finalArrayBulkSMSModelList = finalArrayBulkSMSModelList;
        this.listner = listner;
    }


    @Override
    public BulkSMSDetailListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bulk_sms_detail_list_item, parent, false);

        return new BulkSMSDetailListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BulkSMSDetailListAdapter.MyViewHolder holder, final int position) {
        final String sr = String.valueOf(position + 1);

        holder.index_txt.setText(sr);
        holder.student_name_txt.setText(finalArrayBulkSMSModelList.get(position).getStudentName());
        holder.family_name_txt.setText(finalArrayBulkSMSModelList.get(position).getFamilyName());
        holder.grade_txt.setText(finalArrayBulkSMSModelList.get(position).getStandard());
        holder.mobileno_txt.setText(String.valueOf(finalArrayBulkSMSModelList.get(position).getSmsNo()));

        holder.mobileno_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 10) {
                    finalArrayBulkSMSModelList.get(position).setSmsNo(editable.toString());
                }
            }
        });
        holder.sms_chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                stduentIdStr = "";
                if (isChecked) {
                    finalArrayBulkSMSModelList.get(position).setCheck("1");
                    stduentIdStr = finalArrayBulkSMSModelList.get(position).getFkStudentID().toString();
                    mobilenoStr = finalArrayBulkSMSModelList.get(position).getSmsNo();
                    FinalValue = stduentIdStr + "|" + mobilenoStr;
                    dataCheck.add(FinalValue);
                    Log.d("dataCheck", dataCheck.toString());
                    listner.getEmployeeSMSCheck();
                } else {
                    finalArrayBulkSMSModelList.get(position).setCheck("0");
                    dataCheck.remove(finalArrayBulkSMSModelList.get(position).getFkStudentID() + "|" +
                            finalArrayBulkSMSModelList.get(position).getSmsNo());
                    Log.d("dataUnCheck", dataCheck.toString());
                    listner.getEmployeeSMSCheck();
                }
            }
        });
        if (finalArrayBulkSMSModelList.get(position).getCheck().equalsIgnoreCase("1")) {
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
        return finalArrayBulkSMSModelList.size();
    }

    public List<FinalArraySMSDataModel> getDatas() {
        return finalArrayBulkSMSModelList;
    }

    public ArrayList<String> getData() {
        return dataCheck;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView index_txt, student_name_txt, family_name_txt, grade_txt;
        EditText mobileno_txt;
        CheckBox sms_chk;

        public MyViewHolder(View itemView) {
            super(itemView);
            index_txt = itemView.findViewById(R.id.index_txt);
            student_name_txt = itemView.findViewById(R.id.student_name_txt);
            family_name_txt = itemView.findViewById(R.id.family_name_txt);
            grade_txt = itemView.findViewById(R.id.grade_txt);
            mobileno_txt = itemView.findViewById(R.id.mobileno_txt);
            sms_chk = itemView.findViewById(R.id.sms_chk);
        }
    }
}


