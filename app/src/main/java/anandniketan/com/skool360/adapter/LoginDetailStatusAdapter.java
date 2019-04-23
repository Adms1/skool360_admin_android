package anandniketan.com.skool360.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anandniketan.com.skool360.Model.Other.FinalArraySMSDataModel;
import anandniketan.com.skool360.R;


/**
 * Created by admsandroid on 2/14/2018.
 */

public class LoginDetailStatusAdapter extends RecyclerView.Adapter<LoginDetailStatusAdapter.MyViewHolder> {
    private Context context;
    private List<FinalArraySMSDataModel> loginData;


    public LoginDetailStatusAdapter(Context mContext, List<FinalArraySMSDataModel> LoginDetailArrayList) {
        this.context = mContext;
        this.loginData = LoginDetailArrayList;
    }


    @Override
    public LoginDetailStatusAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detail_login_status, parent, false);
        return new LoginDetailStatusAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LoginDetailStatusAdapter.MyViewHolder holder, int position) {
        String sr = String.valueOf(position + 1);
        holder.name.setText(loginData.get(position).getEmployeeName());
        holder.date.setText(loginData.get(position).getLoginDetails());
        holder.type.setText(loginData.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return loginData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, type;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_txt);
            date = itemView.findViewById(R.id.date);
            type = itemView.findViewById(R.id.type);
        }
    }
}


