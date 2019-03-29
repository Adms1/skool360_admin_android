package com.skool360admin.Adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import anandniketan.com.bhadajadmin.Interface.onCheckBoxChnage;
import anandniketan.com.bhadajadmin.Model.Other.StudentDatum;
import anandniketan.com.bhadajadmin.R;


/**
 * Created by admsandroid on 10/25/2017.
 */

public class ListAdapterCreate extends BaseAdapter {
    private Context mContext;
    private ArrayList<StudentDatum> arrayList = new ArrayList<>();
    private ProgressDialog progressDialog = null;
    FragmentManager manager;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    private onCheckBoxChnage listner;

    // Constructor
    public ListAdapterCreate(Context c, ArrayList<StudentDatum> arrayList, FragmentManager manager, onCheckBoxChnage listner) {
        mContext = c;
        this.arrayList = arrayList;
        this.manager = manager;
        this.listner = listner;
    }
    private class ViewHolder {
        TextView student_name_txt, gr_no_txt;
        CheckBox create_Checkbox;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        View view = null;
        convertView = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            final LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_row_displaystudent, null);

            viewHolder.student_name_txt = (TextView) convertView.findViewById(R.id.student_name_txt);
            viewHolder.gr_no_txt = (TextView) convertView.findViewById(R.id.gr_no_txt);
            viewHolder.create_Checkbox = (CheckBox) convertView.findViewById(R.id.create_Checkbox);

            try {
                viewHolder.student_name_txt.setText(arrayList.get(position).getStudentName());
                viewHolder.gr_no_txt.setText(arrayList.get(position).getGRNO());


                viewHolder.create_Checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        String checkvalue;

                        if (isChecked) {
                            arrayList.get(position).setCheck("1");
                            checkvalue = arrayList.get(position).getStudentID().toString();
                            dataCheck.add(checkvalue);
                            Log.d("dataCheck", dataCheck.toString());
                            listner.getChecked();
                        } else {
                            arrayList.get(position).setCheck("0");
                            dataCheck.remove(arrayList.get(position).getStudentID().toString());
                            Log.d("dataUnCheck", dataCheck.toString());
                            listner.getChecked();
                        }
                    }
                });

                if(arrayList.get(position).getCheck().equalsIgnoreCase("1")){
                    viewHolder.create_Checkbox.setChecked(true);
                }else{
                    viewHolder.create_Checkbox.setChecked(false);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }

    public ArrayList<String> getData() {
        return dataCheck;
    }

    public ArrayList<StudentDatum> getDatas(){
        return arrayList;
    }
}


