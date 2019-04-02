package anandniketan.com.skool360.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import anandniketan.com.skool360.Interface.getEditpermission;
import anandniketan.com.skool360.Interface.onDeleteButton;
import anandniketan.com.skool360.Model.Staff.FinalArrayStaffModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.Utils;

// Antra 25/01/2019
// Add status, delete & Edit button

/**
 * Created by admsandroid on 12/20/2017.
 */

public class AssignSubjectDetailListAdapter extends RecyclerView.Adapter<AssignSubjectDetailListAdapter.MyViewHolder> {
    List<FinalArrayStaffModel> AssignSubjectModelList;
    onDeleteButton onDeleteButton;
    getEditpermission getEditpermission;
    String deleteId;
    String editId;
    private Context context;
    private String updatestatus, deletestatus;

    public AssignSubjectDetailListAdapter(Context mContext, List<FinalArrayStaffModel> AssignSubjectModelList, onDeleteButton onDeleteButton, getEditpermission getEditpermission, String updatestatus, String deletestatus) {
        this.context = mContext;
        this.AssignSubjectModelList = AssignSubjectModelList;
        this.updatestatus = updatestatus;
        this.deletestatus = deletestatus;
        this.onDeleteButton = onDeleteButton;
        this.getEditpermission = getEditpermission;
    }

    @Override
    public AssignSubjectDetailListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.assign_subject_detail_list_item, parent, false);

        return new AssignSubjectDetailListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AssignSubjectDetailListAdapter.MyViewHolder holder, final int position) {
        String sr = String.valueOf(position + 1);
        holder.index_txt.setText(sr);
        holder.teachername_txt.setText(AssignSubjectModelList.get(position).getTeacherName());
        holder.subject_txt.setText(AssignSubjectModelList.get(position).getSubject());
        holder.status_txt.setText(AssignSubjectModelList.get(position).getStatus());

        holder.edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (updatestatus.equalsIgnoreCase("true")) {
                    editId = String.valueOf(AssignSubjectModelList.get(position).getStatus() + "|" + AssignSubjectModelList.get(position).getPkAssignID()) + "|" +
                            holder.teachername_txt.getText().toString() + "|" + holder.subject_txt.getText().toString();
                    getEditpermission.getEditpermission();
                } else {
                    Utils.ping(context, "Access Denied");
                }
            }
        });

        holder.delete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deletestatus.equalsIgnoreCase("true")) {
                    deleteId = String.valueOf(AssignSubjectModelList.get(position).getPkAssignID());
                    onDeleteButton.deleteSentMessage();
                } else {
                    Utils.ping(context, "Access Denied");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return AssignSubjectModelList.size();
    }

    public String getId() {
        return deleteId;
    }

    public String getEditId() {
        return editId;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView index_txt, teachername_txt, subject_txt, status_txt;
        ImageView edit_img, delete_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            index_txt = itemView.findViewById(R.id.index_txt);
            teachername_txt = itemView.findViewById(R.id.teachername_txt);
            subject_txt = itemView.findViewById(R.id.subject_txt);
            edit_img = itemView.findViewById(R.id.edit_img);
            status_txt = itemView.findViewById(R.id.status_txt);
            delete_img = itemView.findViewById(R.id.delete_img);
        }
    }

}

