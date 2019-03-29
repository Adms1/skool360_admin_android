package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anandniketan.com.bhadajadmin.Model.Student.StandardWiseAttendanceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.databinding.StandardwiseAbsentListBinding;


/**
 * Created by admsandroid on 11/20/2017.
 */

public class StandardwiseStudentAttendaceAdapter extends RecyclerView.Adapter<StandardwiseStudentAttendaceAdapter.ViewHolder> {
    StandardwiseAbsentListBinding standardwiseAbsentListBinding;
    private Context context;
    private List<StandardWiseAttendanceModel> studentAttendanceModel;

    public StandardwiseStudentAttendaceAdapter(Context context, List<StandardWiseAttendanceModel> standardWiseAttendanceModelList) {
        this.context = context;
        this.studentAttendanceModel = standardWiseAttendanceModelList;
    }


    @Override
    public StandardwiseStudentAttendaceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        standardwiseAbsentListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.standardwise_absent_list, parent, false);
        view = standardwiseAbsentListBinding.getRoot();
        return new StandardwiseStudentAttendaceAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(StandardwiseStudentAttendaceAdapter.ViewHolder holder, int position) {
        standardwiseAbsentListBinding.standardTxt.setText(studentAttendanceModel.get(position).getStandard() +
                " - " + studentAttendanceModel.get(position).getClass_() + " ( " + studentAttendanceModel.get(position).getTotalStudent() + " ) ");

        standardwiseAbsentListBinding.presentTxt.setText(studentAttendanceModel.get(position).getPresent());
        standardwiseAbsentListBinding.absentTxt.setText(studentAttendanceModel.get(position).getAbsent());
        standardwiseAbsentListBinding.leaveTxt.setText(studentAttendanceModel.get(position).getLeave());
    }

    @Override
    public int getItemCount() {
        return studentAttendanceModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView standard_txt, status_txt;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}