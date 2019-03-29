package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anandniketan.com.bhadajadmin.Model.Staff.FinalArrayStaffModel;
import anandniketan.com.bhadajadmin.R;


/**
 * Created by admsandroid on 11/28/2017.
 */

public class ExamListAdapter extends RecyclerView.Adapter<ExamListAdapter.ViewHolder> {
    private Context context;
    private List<FinalArrayStaffModel> examsModelList;

    public ExamListAdapter(Context mContext, List<FinalArrayStaffModel> examsModelList) {
        this.context = mContext;
        this.examsModelList = examsModelList;
    }


    @Override
    public ExamListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_exams, parent, false);
        return new ExamListAdapter.ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ExamListAdapter.ViewHolder holder, int position) {
        String sr = String.valueOf(position + 1);
        holder.srno_txt.setText(sr);
        holder.test_name_txt.setText(examsModelList.get(position).getTestName());
        holder.grade_txt.setText(examsModelList.get(position).getGrade());
        holder.subject_txt.setText(examsModelList.get(position).getSubject());
        holder.testDate_txt.setText(examsModelList.get(position).getTestDate());
    }

    @Override
    public int getItemCount() {
        return examsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView testDate_txt,subject_txt,grade_txt,test_name_txt,srno_txt;

        public ViewHolder(View itemView) {
            super(itemView);
            testDate_txt=(TextView)itemView.findViewById(R.id.testDate_txt);
            subject_txt=(TextView)itemView.findViewById(R.id.subject_txt);
            grade_txt=(TextView)itemView.findViewById(R.id.grade_txt);
            test_name_txt=(TextView)itemView.findViewById(R.id.test_name_txt);
            srno_txt=(TextView)itemView.findViewById(R.id.srno_txt);
        }
    }
}

