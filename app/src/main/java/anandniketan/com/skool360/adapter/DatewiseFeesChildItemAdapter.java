package anandniketan.com.skool360.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anandniketan.com.skool360.Model.Account.DateWiseFeesCollectionModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.DialogUtils;

public class DatewiseFeesChildItemAdapter extends RecyclerView.Adapter<DatewiseFeesChildItemAdapter.MyViewHolder> {


    private Context context;
    private List<DateWiseFeesCollectionModel.FeeReceiptDetail> feestModel;
    private SpannableStringBuilder discriptionSpanned;
    private String discriptionStr;

    public DatewiseFeesChildItemAdapter(Context mContext, List<DateWiseFeesCollectionModel.FeeReceiptDetail> feestModel) {
        this.context = mContext;
        this.feestModel = feestModel;
    }


    @Override
    public DatewiseFeesChildItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_datewisefees_list_child_item, parent, false);
        return new DatewiseFeesChildItemAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DatewiseFeesChildItemAdapter.MyViewHolder holder, int position) {
        final DateWiseFeesCollectionModel.FeeReceiptDetail result = feestModel.get(position);

        holder.paymentmode_txt.setText(result.getPaymentMode());
        holder.date_txt.setText(String.valueOf(result.getCreateDate()));
        holder.term_txt.setText(result.getTerm());
        holder.amount_txt.setText("₹" + result.getAmount());

        holder.view_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogUtils.showWebviewDialog(context, AppConfiguration.LIVE_BASE_URL + result.getURL());
            }
        });
    }

    @Override
    public int getItemCount() {
        return feestModel.size();
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
        TextView paymentmode_txt, date_txt, term_txt, amount_txt, view_txt;


        public MyViewHolder(View itemView) {
            super(itemView);
            paymentmode_txt = itemView.findViewById(R.id.paymentmode_txt);
            date_txt = itemView.findViewById(R.id.date_txt);
            term_txt = itemView.findViewById(R.id.term_txt);
            amount_txt = itemView.findViewById(R.id.amount_txt);
            view_txt = itemView.findViewById(R.id.view_txt);


        }
    }

}



