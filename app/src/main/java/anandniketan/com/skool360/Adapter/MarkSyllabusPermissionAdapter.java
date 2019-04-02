package anandniketan.com.skool360.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.skool360.Interface.OnEditRecordWithPosition;
import anandniketan.com.skool360.Model.Student.MarkSyllabusModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.Utils;

public class MarkSyllabusPermissionAdapter extends RecyclerView.Adapter<MarkSyllabusPermissionAdapter.MyViewHolder> {
    private Context context;
    private MarkSyllabusModel profilePermissionModel;
    private List<MarkSyllabusModel.FinalArray> mDataList = new ArrayList<>();
    private OnEditRecordWithPosition listner;
    private String rowvalue;
    private String status;

    public MarkSyllabusPermissionAdapter(Context mContext, MarkSyllabusModel profilePermissionModel, OnEditRecordWithPosition listner, String status) {
        this.context = mContext;
        this.profilePermissionModel = profilePermissionModel;
        this.mDataList = profilePermissionModel.getFinalArray();
        this.listner = listner;
        this.status = status;
    }

    @Override
    public MarkSyllabusPermissionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mark_syllabus, parent, false);
        return new MarkSyllabusPermissionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MarkSyllabusPermissionAdapter.MyViewHolder holder, final int position) {
//        final MarkSyllabusModel.FinalArray result = mDataList.get(position);

        holder.term_txt.setText(mDataList.get(position).getTerm());
        holder.type_txt.setText(String.valueOf(mDataList.get(position).getType()));
        holder.grade_txt.setText(mDataList.get(position).getStandard());
        holder.testname_txt.setText(mDataList.get(position).getTestName());
        holder.result_status_txt.setText(mDataList.get(position).getStatus());

        holder.mIvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (status.equalsIgnoreCase("true")) {

                    rowvalue = mDataList.get(position).getTerm() + "|" + mDataList.get(position).getType() + "|" + mDataList.get(position).getStandard() + "|" + mDataList.get(position).getTestName() + "|" + mDataList.get(position).getStatus() + "|" + mDataList.get(position).getPermissionID();


                    listner.getEditpermission(position);
                } else {
                    Utils.ping(context, "Access Denied");
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public String getRowValue() {
        return rowvalue;
    }

    public List<MarkSyllabusModel.FinalArray> getDatas() {
        return mDataList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView term_txt, type_txt, grade_txt, testname_txt, result_status_txt;
        private ImageView mIvEdit;

        public MyViewHolder(View itemView) {
            super(itemView);
            term_txt = itemView.findViewById(R.id.term_txt);
            type_txt = itemView.findViewById(R.id.type_txt);
            grade_txt = itemView.findViewById(R.id.grade_txt);
            testname_txt = itemView.findViewById(R.id.testname_txt);
            result_status_txt = itemView.findViewById(R.id.result_status_txt);
            mIvEdit = itemView.findViewById(R.id.iv_edt);
        }
    }
}
