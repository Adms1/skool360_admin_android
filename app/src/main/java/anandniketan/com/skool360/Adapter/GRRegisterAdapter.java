package anandniketan.com.skool360.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import anandniketan.com.skool360.Interface.OnEditRecordWithPosition;
import anandniketan.com.skool360.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.skool360.Model.Student.StudentAttendanceModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.AppConfiguration;


/**
 * Created by admsandroid on 1/25/2018.
 */

public class GRRegisterAdapter extends RecyclerView.Adapter<GRRegisterAdapter.MyViewHolder> {
    private Context context;
    private StudentAttendanceModel filteredDataModel;
    private OnEditRecordWithPosition onViewClick;
    private String status;
    private String type;

    public GRRegisterAdapter(Context mContext, StudentAttendanceModel studentFullDetailModel, OnEditRecordWithPosition onViewClick, String status, String type) {
        this.context = mContext;
        this.filteredDataModel = studentFullDetailModel;
        this.onViewClick = onViewClick;
        this.status = status;
        this.type = type;
    }

    @NonNull
    @Override
    public GRRegisterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grregister_list, parent, false);
        return new GRRegisterAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GRRegisterAdapter.MyViewHolder holder, final int position) {

        final StudentAttendanceFinalArray filter = filteredDataModel.getFinalArray().get(position);
        String sr = String.valueOf(position + 1);
        holder.index_txt.setText(sr);

        if (type.equalsIgnoreCase("left/active")) {
            holder.stu_name.setVisibility(View.GONE);
            holder.firstname_txt.setVisibility(View.VISIBLE);
            holder.lastname_txt.setVisibility(View.VISIBLE);
        } else if (type.equalsIgnoreCase("new register")) {
            holder.stu_name.setVisibility(View.VISIBLE);
            holder.firstname_txt.setVisibility(View.GONE);
            holder.lastname_txt.setVisibility(View.GONE);
        } else {
            holder.stu_name.setVisibility(View.VISIBLE);
            holder.firstname_txt.setVisibility(View.GONE);
            holder.lastname_txt.setVisibility(View.GONE);
        }

        holder.firstname_txt.setText(filter.getFirstName());
        holder.lastname_txt.setText(filter.getLastName());
        holder.stu_name.setText(filter.getFirstName() + " " + filter.getLastName());
        holder.grnno_txt.setText(String.valueOf(filter.getGRNO()));
        holder.grade_txt.setText(filter.getGrade() + "-" + filter.getSection());

        holder.view_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (status.equalsIgnoreCase("true")) {
                AppConfiguration.CheckStudentId = String.valueOf(filter.getStudent_ID().toString());
                Log.d("CheckStudentId", AppConfiguration.CheckStudentId);
                onViewClick.getEditpermission(position);
//                } else {
//                    Utils.ping(context, "Access Denied");
//                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredDataModel.getFinalArray().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView index_txt, firstname_txt, lastname_txt, grnno_txt, grade_txt, view_txt, stu_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            index_txt = itemView.findViewById(R.id.index_txt);
            firstname_txt = itemView.findViewById(R.id.firstname_txt);
            lastname_txt = itemView.findViewById(R.id.lastname_txt);
            grnno_txt = itemView.findViewById(R.id.grnno_txt);
            grade_txt = itemView.findViewById(R.id.grade_txt);
            view_txt = itemView.findViewById(R.id.view_txt);
            stu_name = itemView.findViewById(R.id.firstname1_txt);

        }
    }

}
