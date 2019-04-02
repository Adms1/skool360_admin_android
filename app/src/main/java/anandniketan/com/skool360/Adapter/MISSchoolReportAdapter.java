package anandniketan.com.skool360.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.skool360.Model.MIS.MISSchoolResultModel;
import anandniketan.com.skool360.R;

public class MISSchoolReportAdapter extends RecyclerView.Adapter<MISSchoolReportAdapter.MyViewHolder> {

    private Context context;
    private List<MISSchoolResultModel.FinalArray> dataValues = new ArrayList<MISSchoolResultModel.FinalArray>();
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private Bundle bundle;


    public MISSchoolReportAdapter(Context mContext, List<MISSchoolResultModel.FinalArray> dataValues) {
        this.context = mContext;
        this.dataValues = dataValues;
    }

    @Override
    public MISSchoolReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mis_grid_school_result, parent, false);
        return new MISSchoolReportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MISSchoolReportAdapter.MyViewHolder holder, final int position) {
        try {
//            holder.grade_txt.setText(String.valueOf(dataValues.get(position).getStandard()));
            holder.section_txt.setText(String.valueOf(dataValues.get(position).getStandard()) + "-" + String.valueOf(dataValues.get(position).getClassName()));
            holder.student_txt.setText(String.valueOf(dataValues.get(position).getName()));
            holder.grade1_txt.setText(String.valueOf(dataValues.get(position).getGrade()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return dataValues == null ? 0 : dataValues.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView section_txt, student_txt, percentage_txt, grade1_txt;

        public MyViewHolder(View itemView) {
            super(itemView);
//            grade_txt = (TextView) itemView.findViewById(R.id.grade_txt);
            section_txt = itemView.findViewById(R.id.section_txt);
            student_txt = itemView.findViewById(R.id.student_txt);
            grade1_txt = itemView.findViewById(R.id.grade_txt_1);

        }
    }
}
