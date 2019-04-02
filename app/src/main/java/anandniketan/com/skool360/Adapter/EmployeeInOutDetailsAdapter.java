package anandniketan.com.skool360.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.skool360.Model.HR.EmployeeInOutDetailsModel;
import anandniketan.com.skool360.R;

public class EmployeeInOutDetailsAdapter extends RecyclerView.Adapter<EmployeeInOutDetailsAdapter.MyViewHolder> {

    List<EmployeeInOutDetailsModel.FinalArray> finalEmpDataModelList;
    String stduentIdStr, mobilenoStr, FinalValue;
    private Context context;
    private ArrayList<String> dataCheck = new ArrayList<String>();

    public EmployeeInOutDetailsAdapter(Context mContext, List<EmployeeInOutDetailsModel.FinalArray> finalEmpDataModelList) {
        this.context = mContext;
        this.finalEmpDataModelList = finalEmpDataModelList;
    }


    @Override
    public EmployeeInOutDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_employee_in_out_summary_details, parent, false);
        return new EmployeeInOutDetailsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EmployeeInOutDetailsAdapter.MyViewHolder holder, final int position) {
        holder.code_txt.setText(finalEmpDataModelList.get(position).getEmployeeCode());
        holder.name_txt.setText(finalEmpDataModelList.get(position).getEmployeeName());
        holder.ondate_txt.setText(finalEmpDataModelList.get(position).getOnDate());
        holder.inoutdetails_txt.setText(finalEmpDataModelList.get(position).getInOutDetails());
        holder.workinghours_txt.setText(finalEmpDataModelList.get(position).getWorkingHours());

    }

    @Override
    public long getItemId(int position) {
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
        TextView code_txt, name_txt, ondate_txt, inoutdetails_txt, workinghours_txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            code_txt = itemView.findViewById(R.id.code_txt);
            name_txt = itemView.findViewById(R.id.name_txt);
            ondate_txt = itemView.findViewById(R.id.ondate_txt);
            inoutdetails_txt = itemView.findViewById(R.id.inoutdetails_txt);
            workinghours_txt = itemView.findViewById(R.id.workinghours_txt);

        }


        public List<EmployeeInOutDetailsModel.FinalArray> getDatas() {
            return finalEmpDataModelList;
        }

        public ArrayList<String> getData() {
            return dataCheck;
        }

    }


}
