package anandniketan.com.skool360.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import anandniketan.com.skool360.Interface.getEditpermission;
import anandniketan.com.skool360.Model.Other.FinalArraySMSDataModel;
import anandniketan.com.skool360.Model.Other.GetStaffSMSDataModel;
import anandniketan.com.skool360.R;


/**
 * Created by admsandroid on 2/9/2018.
 */

public class HolidayAdapter extends RecyclerView.Adapter<HolidayAdapter.MyViewHolder> {
    private Context context;
    private GetStaffSMSDataModel holidayModel;
    private ArrayList<String> rowvalue = new ArrayList<String>();
    getEditpermission listner;

    public HolidayAdapter(Context mContext, GetStaffSMSDataModel resultPermissionModel, getEditpermission listner) {
        this.context = mContext;
        this.holidayModel = resultPermissionModel;
        this.listner = listner;
    }


    @Override
    public HolidayAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_permission_list, parent, false);
        return new HolidayAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HolidayAdapter.MyViewHolder holder, int position) {
        String sr = String.valueOf(position + 1);
        final FinalArraySMSDataModel result = holidayModel.getFinalArray().get(position);
        holder.index_txt.setText(sr);
        holder.holiday_name.setText(result.getCategory());
        holder.start_date.setText(result.getStartDT());
        holder.end_date.setText(result.getEndDT());

        holder.edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rowvalue.add(result.getCategory() + "|" + result.getpKHolidayID() + "|" + result.getStartDT() + "|" + result.getEndDT() + "|" + result.getDescription() + "|" + result.getpKCategoryID());
                listner.getEditpermission();
            }
        });

    }

    @Override
    public int getItemCount() {
        return holidayModel.getFinalArray().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView index_txt, holiday_name, start_date, end_date;
        ImageView edit_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            index_txt = itemView.findViewById(R.id.index_txt);
            holiday_name = itemView.findViewById(R.id.academicyear_txt);
            start_date = itemView.findViewById(R.id.grade_txt);
            end_date = itemView.findViewById(R.id.resultstatus_txt);
            edit_img = itemView.findViewById(R.id.edit_img);

        }
    }

    public ArrayList<String> getRowValue() {
        return rowvalue;
    }
}

