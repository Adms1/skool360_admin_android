package anandniketan.com.skool360.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import anandniketan.com.skool360.Model.Student.ConsistentAbsentStudentModel;
import anandniketan.com.skool360.Model.Student.StandardWiseAttendanceModel;
import anandniketan.com.skool360.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.databinding.TotalAttendanceItemBinding;

public class StudentAttemndanceSummaryAdapter extends RecyclerView.Adapter<StudentAttemndanceSummaryAdapter.ViewHolder> {
    TotalAttendanceItemBinding totalAttendanceItemBinding;
    List<StudentAttendanceFinalArray> studentAttendanceFinalArrayList;
    List<StandardWiseAttendanceModel> standardWiseAttendanceModelList;
    List<ConsistentAbsentStudentModel> consistentAbsentStudentModelList;
    StandardwiseStudentAttendaceAdapter standardwiseStudentAttendaceAdapter;
    ConsistentAbsentTeacherAdapter consistentAbsentTeacherAdapter;
    private Context context;

    public StudentAttemndanceSummaryAdapter(Context mContext, List<StudentAttendanceFinalArray> studentAttendanceFinalArrayList, List<StandardWiseAttendanceModel> standardWiseAttendanceModelList, List<ConsistentAbsentStudentModel> consistentAbsentStudentModelList) {
        this.context = mContext;
        this.studentAttendanceFinalArrayList = studentAttendanceFinalArrayList;
        this.standardWiseAttendanceModelList = standardWiseAttendanceModelList;
        this.consistentAbsentStudentModelList = consistentAbsentStudentModelList;
    }

    @Override
    public StudentAttemndanceSummaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        totalAttendanceItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.total_attendance_item, parent, false);
        view = totalAttendanceItemBinding.getRoot();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentAttemndanceSummaryAdapter.ViewHolder holder, int position) {
        totalAttendanceItemBinding.absentstudentCountTxt.setText(studentAttendanceFinalArrayList.get(position).getStudentAbsent());
        totalAttendanceItemBinding.presentstudentCountTxt.setText(studentAttendanceFinalArrayList.get(position).getStudentPresent());
        totalAttendanceItemBinding.leavestudentCountTxt.setText(studentAttendanceFinalArrayList.get(position).getStudentLeave());
        totalAttendanceItemBinding.totalstudentCountTxt.setText(studentAttendanceFinalArrayList.get(position).getTotalStudent());

        if (standardWiseAttendanceModelList.size() > 0) {

            totalAttendanceItemBinding.standardwiseRcv.setVisibility(View.VISIBLE);
            holder.llHeader1.setVisibility(View.VISIBLE);
            totalAttendanceItemBinding.txtNoRecords1.setVisibility(View.GONE);
            standardwiseStudentAttendaceAdapter = new StandardwiseStudentAttendaceAdapter(context, standardWiseAttendanceModelList);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, OrientationHelper.VERTICAL, false);
            totalAttendanceItemBinding.standardwiseRcv.setLayoutManager(mLayoutManager);
            totalAttendanceItemBinding.standardwiseRcv.setItemAnimator(new DefaultItemAnimator());
            totalAttendanceItemBinding.standardwiseRcv.setAdapter(standardwiseStudentAttendaceAdapter);
        } else {
            totalAttendanceItemBinding.txtNoRecords1.setVisibility(View.VISIBLE);
            totalAttendanceItemBinding.standardwiseRcv.setVisibility(View.GONE);
            holder.llHeader1.setVisibility(View.GONE);
        }

        if (consistentAbsentStudentModelList.size() > 0) {

            totalAttendanceItemBinding.consistentAbsentRcv.setVisibility(View.VISIBLE);
            holder.llHeader2.setVisibility(View.VISIBLE);
            totalAttendanceItemBinding.txtNoRecords2.setVisibility(View.GONE);
            consistentAbsentTeacherAdapter = new ConsistentAbsentTeacherAdapter(context, consistentAbsentStudentModelList);
            LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(context, OrientationHelper.VERTICAL, false);
            totalAttendanceItemBinding.consistentAbsentRcv.setLayoutManager(mLayoutManager1);
            totalAttendanceItemBinding.consistentAbsentRcv.setItemAnimator(new DefaultItemAnimator());
            totalAttendanceItemBinding.consistentAbsentRcv.setAdapter(consistentAbsentTeacherAdapter);
        } else {
            holder.llHeader2.setVisibility(View.GONE);
            totalAttendanceItemBinding.txtNoRecords2.setVisibility(View.VISIBLE);
            totalAttendanceItemBinding.consistentAbsentRcv.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return studentAttendanceFinalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llHeader1, llHeader2;

        public ViewHolder(View itemView) {
            super(itemView);

            llHeader1 = itemView.findViewById(R.id.llHeader1);
            llHeader2 = itemView.findViewById(R.id.llHeader2);

        }
    }

}