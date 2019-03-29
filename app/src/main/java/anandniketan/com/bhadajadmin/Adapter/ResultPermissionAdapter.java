package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import anandniketan.com.bhadajadmin.Interface.getEditpermission;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.Utils;


/**
 * Created by admsandroid on 1/24/2018.
 */

public class ResultPermissionAdapter extends RecyclerView.Adapter<ResultPermissionAdapter.MyViewHolder> {
    getEditpermission listner;
    private Context context;
    private StudentAttendanceModel resultPermissionModel;
    private ArrayList<String> rowvalue = new ArrayList<>();
    private String status;

    public ResultPermissionAdapter(Context mContext, StudentAttendanceModel resultPermissionModel, getEditpermission listner, String status) {
        this.context = mContext;
        this.resultPermissionModel = resultPermissionModel;
        this.listner = listner;
        this.status = status;
    }

    @NonNull
    @Override
    public ResultPermissionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_permission_list, parent, false);
        return new ResultPermissionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultPermissionAdapter.MyViewHolder holder, int position) {
        String sr = String.valueOf(position + 1);
        final StudentAttendanceFinalArray result = resultPermissionModel.getFinalArray().get(position);
        holder.index_txt.setText(sr);
        holder.academicyear_txt.setText(resultPermissionModel.getYear());
        holder.grade_txt.setText(String.valueOf(result.getStandard()));
        holder.resultstatus_txt.setText(result.getStatus());
        holder.termdetail_txt.setText(result.getTermDetail());

        holder.edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equalsIgnoreCase("true")) {
                    rowvalue.add(resultPermissionModel.getYear() + "|" + result.getTermDetail() + "|" + result.getStandard() + "|" + result.getStatus());
                    listner.getEditpermission();
                } else {
                    Utils.ping(context, "Access Denied");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return resultPermissionModel.getFinalArray().size();
    }

    public ArrayList<String> getRowValue() {
        return rowvalue;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView index_txt, academicyear_txt, grade_txt, resultstatus_txt, termdetail_txt;
        ImageView edit_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            index_txt = itemView.findViewById(R.id.index_txt);
            academicyear_txt = itemView.findViewById(R.id.academicyear_txt);
            grade_txt = itemView.findViewById(R.id.grade_txt);
            resultstatus_txt = itemView.findViewById(R.id.resultstatus_txt);
            edit_img = itemView.findViewById(R.id.edit_img);
            termdetail_txt = itemView.findViewById(R.id.termdetail_txt);

        }
    }
}
