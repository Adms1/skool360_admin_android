package anandniketan.com.skool360.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.skool360.Interface.getEmployeeCheck;
import anandniketan.com.skool360.Model.Account.TallyTranscationModel;
import anandniketan.com.skool360.R;

public class TallyTransactionAdapter extends RecyclerView.Adapter<TallyTransactionAdapter.MyViewHolder> {
    private Context context;
    private List<TallyTranscationModel.FinalArray> finalArrayBulkSMSModelList;
    private String stduentIdStr, mobilenoStr, FinalValue;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    private getEmployeeCheck listner;
    private String viewstatus;

    public TallyTransactionAdapter(Context mContext, List<TallyTranscationModel.FinalArray> finalArrayBulkSMSModelList, String viewstatus) {
        this.context = mContext;
        this.finalArrayBulkSMSModelList = finalArrayBulkSMSModelList;
        this.listner = listner;
        this.viewstatus = viewstatus;
    }


    @Override
    public TallyTransactionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tally_transaction_list_item, parent, false);
        return new TallyTransactionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TallyTransactionAdapter.MyViewHolder holder, final int position) {
        final String sr = String.valueOf(position + 1);

        TallyTranscationModel.FinalArray data = finalArrayBulkSMSModelList.get(position);

        holder.vno_txt.setText(String.valueOf(data.getVoucherNo()));
        holder.studentname_txt.setText(finalArrayBulkSMSModelList.get(position).getStudentName());
        holder.grno_txt.setText(finalArrayBulkSMSModelList.get(position).getGRNO());
        holder.amount_txt.setText("₹" + String.valueOf(finalArrayBulkSMSModelList.get(position).getAmount()));
        holder.grade_txt.setText(String.valueOf(finalArrayBulkSMSModelList.get(position).getStandard()));

        if (data.getStatus().equalsIgnoreCase("Pending Receipt")) {
            holder.iv_status.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);

        } else {
            holder.iv_status.setColorFilter(ContextCompat.getColor(context, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);

        }
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
        return finalArrayBulkSMSModelList.size();
    }

    public ArrayList<String> getData() {
        return dataCheck;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView vno_txt, studentname_txt, grno_txt, amount_txt, grade_txt;
        ImageView iv_status;

        public MyViewHolder(View itemView) {
            super(itemView);
            vno_txt = itemView.findViewById(R.id.vno_txt);
            studentname_txt = itemView.findViewById(R.id.studentname_txt);
            grno_txt = itemView.findViewById(R.id.grno_txt);
            amount_txt = itemView.findViewById(R.id.amount_txt);
            iv_status = itemView.findViewById(R.id.iv_status);
            grade_txt = itemView.findViewById(R.id.grade_txt);

        }
    }
}
