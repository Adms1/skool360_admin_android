package anandniketan.com.skool360.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import anandniketan.com.skool360.Model.LeaveModel;
import anandniketan.com.skool360.R;

public class LeaveBalanceDetailAdapter extends RecyclerView.Adapter<LeaveBalanceDetailAdapter.viewholder> {

    private Context context;
    private ArrayList<LeaveModel.FinalArray> finalArrays;

    public LeaveBalanceDetailAdapter(Context context, ArrayList<LeaveModel.FinalArray> finalArrays) {
        this.context = context;
        this.finalArrays = finalArrays;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_leave_balance_item_list, viewGroup, false);
        return new viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, int i) {

        if (i == 0) {

            viewholder.category.setTextColor(context.getResources().getColor(R.color.light_blue));
            viewholder.total.setTextColor(context.getResources().getColor(R.color.light_blue));
            viewholder.used.setTextColor(context.getResources().getColor(R.color.light_blue));
            viewholder.remaining.setTextColor(context.getResources().getColor(R.color.light_blue));
        }

        viewholder.category.setText(finalArrays.get(i).getCategory());
        viewholder.total.setText(finalArrays.get(i).getTotal());
        viewholder.used.setText(finalArrays.get(i).getUsed());
        viewholder.remaining.setText(finalArrays.get(i).getRemaining());

    }

    @Override
    public int getItemCount() {
        return finalArrays.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView category, total, used, remaining;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.cateory);
            total = itemView.findViewById(R.id.total);
            used = itemView.findViewById(R.id.paid);
            remaining = itemView.findViewById(R.id.remaining);

        }
    }

}

