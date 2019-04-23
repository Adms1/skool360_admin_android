package anandniketan.com.skool360.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import anandniketan.com.skool360.Interface.OnEditRecordWithPosition;
import anandniketan.com.skool360.Interface.onDeleteWithId;
import anandniketan.com.skool360.Model.Student.PlannerModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.DialogUtils;
import anandniketan.com.skool360.Utility.Utils;

public class PlannerAdapter extends RecyclerView.Adapter<PlannerAdapter.MyViewHolder> {
    private Context context;
    private PlannerModel plannerList;
    private OnEditRecordWithPosition onUpdateRecordRef;
    private onDeleteWithId onDeleteWithIdRef;
    private String status, updatestatus, deletestatus;

    public PlannerAdapter(Context mContext, PlannerModel plannerList, OnEditRecordWithPosition onUpdateRecordRef, onDeleteWithId onDeleteWithIdRef, String status, String updatestatus, String deletestatus) {
        this.context=mContext;
        this.plannerList=plannerList;
        this.onDeleteWithIdRef = onDeleteWithIdRef;
        this.onUpdateRecordRef = onUpdateRecordRef;
        this.status = status;
        this.deletestatus = deletestatus;
        this.updatestatus = updatestatus;
    }


    @Override
    public PlannerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_planner, parent, false);
        return new PlannerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlannerAdapter.MyViewHolder holder, final int position) {
        String sr = String.valueOf(position + 1);
        final PlannerModel.FinalArray result = plannerList.getFinalArray().get(position);

        holder.type_txt.setText(result.getType());
        holder.name_txt.setText(result.getName());
        holder.startdate_txt.setText(result.getStartDate());
        holder.endate_txt.setText(result.getEndDate());

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (deletestatus.equalsIgnoreCase("true")) {

                    DialogUtils.createConfirmDialog(context, R.string.app_name, R.string.delete_confirm_msg, "OK", "Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    onDeleteWithIdRef.deleteRecordWithId(String.valueOf(result.getID()));
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }
                    ).show();
                } else {
                    Utils.ping(context, "Access Denied");
                }
            }
        });

        holder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (status.equalsIgnoreCase("true")) {

                    onUpdateRecordRef.getEditpermission(position);
                } else {
                    Utils.ping(context, "Access Denied");
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return plannerList.getFinalArray().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView type_txt,name_txt,startdate_txt,endate_txt;
        ImageView iv_edit,iv_delete;

        public MyViewHolder(View itemView) {
            super(itemView);
            type_txt = itemView.findViewById(R.id.type_txt);
            name_txt = itemView.findViewById(R.id.name_txt);
            startdate_txt = itemView.findViewById(R.id.startdate_txt);
            endate_txt = itemView.findViewById(R.id.endate_txt);
            iv_edit = itemView.findViewById(R.id.iv_edit);
            iv_delete = itemView.findViewById(R.id.iv_delete);
        }
    }
}
