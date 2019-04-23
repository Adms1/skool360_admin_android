package anandniketan.com.skool360.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.skool360.Fragment.fragment.staff.StaffInquiryProfileFragment;
import anandniketan.com.skool360.Model.HR.SearchStaffModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.Utils;

public class StaffSearchListItemAdapter extends RecyclerView.Adapter<StaffSearchListItemAdapter.MyViewHolder> {
    SpannableStringBuilder discriptionSpanned;
    String discriptionStr;
    private Context context;
    private List<SearchStaffModel.FinalArray> announcmentModel;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private String viewstatus;

    public StaffSearchListItemAdapter(Context mContext, List<SearchStaffModel.FinalArray> announcmentModel, String viewstatus) {
        this.context = mContext;
        this.announcmentModel = announcmentModel;
        this.viewstatus = viewstatus;
    }


    @Override
    public StaffSearchListItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_staff_search, parent, false);
        return new StaffSearchListItemAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StaffSearchListItemAdapter.MyViewHolder holder, final int position) {
        String sr = String.valueOf(position + 1);
        final SearchStaffModel.FinalArray result = announcmentModel.get(position);
        holder.employee_txt.setText(result.getName() + "(" + result.getEmpCode() + ")");
        holder.department_txt.setText(String.valueOf(result.getDepartment()));

        if (result.getFatherHusbandName().equalsIgnoreCase("")) {
            holder.fh_name_txt.setText("-");
        } else {
            holder.fh_name_txt.setText(result.getFatherHusbandName());
        }

        if (result.getDateOfBirth().equalsIgnoreCase("")) {
            holder.dob_txt.setText("-");
        } else {
            holder.dob_txt.setText(result.getDateOfBirth());
        }

        holder.view_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (viewstatus.equalsIgnoreCase("true")) {

                    try {
                        fragment = new StaffInquiryProfileFragment();
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        Bundle bundle = new Bundle();

                        ArrayList<SearchStaffModel.FinalArray> data = new ArrayList<>();
                        data.add(announcmentModel.get(position));
                        bundle.putParcelableArrayList("dataList", data);
                        fragment.setArguments(bundle);
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).add(R.id.frame_container, fragment).addToBackStack(null).commit();
                        AppConfiguration.firsttimeback = true;
                        AppConfiguration.position = 61;

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    Utils.ping(context, "Access Denied");
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return announcmentModel.size();
    }

    private SpannableStringBuilder trimSpannable(SpannableStringBuilder spannable) {
        int trimStart = 0;
        int trimEnd = 0;
        String text = spannable.toString();

        while (text.length() > 0 && text.startsWith("\n")) {
            text = text.substring(1);
            trimStart += 1;
        }
        while (text.length() > 0 && text.endsWith("\n")) {
            text = text.substring(0, text.length() - 1);
            trimEnd += 1;
        }
        return spannable.delete(0, trimStart).delete(spannable.length() - trimEnd, spannable.length());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView employee_txt, fh_name_txt, dob_txt, department_txt, view_txt;


        public MyViewHolder(View itemView) {
            super(itemView);
            employee_txt = itemView.findViewById(R.id.employee_txt);
            fh_name_txt = itemView.findViewById(R.id.fh_name_txt);
            dob_txt = itemView.findViewById(R.id.dob_txt);
            department_txt = itemView.findViewById(R.id.department_txt);
            view_txt = itemView.findViewById(R.id.view_txt);

        }
    }
}
