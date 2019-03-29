package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import anandniketan.com.bhadajadmin.Model.Student.ConsistentAbsentStudentModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.databinding.ConsistentAbsentTeacherListBinding;


/**
 * Created by admsandroid on 11/21/2017.
 */

public class ConsistentAbsentTeacherAdapter extends RecyclerView.Adapter<ConsistentAbsentTeacherAdapter.ViewHolder> {
    List<ConsistentAbsentStudentModel> consistentAbsentStudentModelList;
    ConsistentAbsentTeacherListBinding consistentAbsentTeacherListBinding;
    private Context context;

    public ConsistentAbsentTeacherAdapter(Context context, List<ConsistentAbsentStudentModel> consistentAbsentStudentModelList) {
        this.context = context;
        this.consistentAbsentStudentModelList = consistentAbsentStudentModelList;
    }

    @Override
    public ConsistentAbsentTeacherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        consistentAbsentTeacherListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.consistent_absent_teacher_list, parent, false);
        view = consistentAbsentTeacherListBinding.getRoot();
        return new ConsistentAbsentTeacherAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ConsistentAbsentTeacherAdapter.ViewHolder holder, int position) {
        consistentAbsentTeacherListBinding.studentnameTxt.setText(consistentAbsentStudentModelList.get(position).getStudentName());
        consistentAbsentTeacherListBinding.standardTxt.setText(consistentAbsentStudentModelList.get(position).getStandard() + " - " + consistentAbsentStudentModelList.get(position).getClass_());
//        consistentAbsentTeacherListBinding.sectionTxt.setText(consistentAbsentStudentModelList.get(position).getClass_());
        consistentAbsentTeacherListBinding.daysTxt.setText(String.valueOf(consistentAbsentStudentModelList.get(position).getDays()));
    }

    @Override
    public int getItemCount() {
        return consistentAbsentStudentModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}