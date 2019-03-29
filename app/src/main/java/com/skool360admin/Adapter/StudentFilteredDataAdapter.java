package com.skool360admin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import anandniketan.com.bhadajadmin.Interface.onViewClick;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;

/**
 * Created by admsandroid on 11/21/2017.
 */

public class StudentFilteredDataAdapter extends RecyclerView.Adapter<StudentFilteredDataAdapter.MyViewHolder> {
    private Context context;
    private StudentAttendanceModel filteredDataModel;
    private onViewClick onViewClick;
    private String status;

    public StudentFilteredDataAdapter(Context mContext, StudentAttendanceModel filteredDataModel, onViewClick onViewClick, String status) {
        this.context = mContext;
        this.filteredDataModel = filteredDataModel;
        this.onViewClick = onViewClick;
        this.status = status;
    }

    @Override
    public StudentFilteredDataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_student_list, parent, false);
        return new StudentFilteredDataAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StudentFilteredDataAdapter.MyViewHolder holder, int position) {
        final StudentAttendanceFinalArray filter = filteredDataModel.getFinalArray().get(position);
        holder.parentsname_txt.setText(filter.getFatherName());
        holder.studentname_txt.setText(filter.getStudentName());
        holder.grnno_txt.setText(String.valueOf(filter.getGRNO()));
        holder.grade_txt.setText(filter.getGradeSection());

        holder.view_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (status.equalsIgnoreCase("true")) {
                    AppConfiguration.StudentId = filter.getStudentID().toString();
                    AppConfiguration.StudentStatus = filter.getCurrentStatus();
                    onViewClick.getViewClick();
                } else {
                    Utils.ping(context, "Access Denied");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredDataModel.getFinalArray().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView parentsname_txt, studentname_txt, grnno_txt, grade_txt, view_txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            parentsname_txt = itemView.findViewById(R.id.parentsname_txt);
            studentname_txt = itemView.findViewById(R.id.studentname_txt);
            grnno_txt = itemView.findViewById(R.id.grnno_txt);
            grade_txt = itemView.findViewById(R.id.grade_txt);
            view_txt = itemView.findViewById(R.id.view_txt);

        }
    }
}