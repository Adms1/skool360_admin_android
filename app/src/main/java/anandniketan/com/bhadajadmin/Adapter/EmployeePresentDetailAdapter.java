package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.bhadajadmin.Model.HR.EmployeePresentDetailsModel;
import anandniketan.com.bhadajadmin.R;

public class EmployeePresentDetailAdapter extends RecyclerView.Adapter<EmployeePresentDetailAdapter.MyViewHolder> {
    private Context context;
    List<EmployeePresentDetailsModel.FinalArray> finalEmpDataModelList;
    String stduentIdStr, mobilenoStr, FinalValue;
    private ArrayList<String> dataCheck = new ArrayList<String>();

    public EmployeePresentDetailAdapter(Context mContext, List<EmployeePresentDetailsModel.FinalArray> finalEmpDataModelList) {
        this.context = mContext;
        this.finalEmpDataModelList = finalEmpDataModelList;
    }


    @Override
    public EmployeePresentDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_employee_present_details, parent, false);
        return new EmployeePresentDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EmployeePresentDetailAdapter.MyViewHolder holder, final int position) {
        holder.date_txt.setText(finalEmpDataModelList.get(position).getDate());
        holder.name_txt.setText(finalEmpDataModelList.get(position).getEmployeeName());
        holder.dept_txt.setText(finalEmpDataModelList.get(position).getDepartment());
        holder.designation_txt.setText(finalEmpDataModelList.get(position).getDesignation());
    }

    @Override
    public long getItemId(int position) {
// return specific item's id here
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return finalEmpDataModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date_txt,name_txt,dept_txt,designation_txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            date_txt = (TextView) itemView.findViewById(R.id.date_txt);
            name_txt = (TextView) itemView.findViewById(R.id.name_txt);
            dept_txt = (TextView) itemView.findViewById(R.id.dept_txt);
            designation_txt = (TextView) itemView.findViewById(R.id.designation_txt);
        }
    }

    public List<EmployeePresentDetailsModel.FinalArray> getDatas() {
        return finalEmpDataModelList;
    }

    public ArrayList<String> getData() {
        return dataCheck;
    }
}
