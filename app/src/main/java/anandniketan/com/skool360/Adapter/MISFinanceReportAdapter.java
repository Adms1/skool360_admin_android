package anandniketan.com.skool360.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.skool360.Fragment.Fragment.HeadWiseFeeCollectionDetailFragment;
import anandniketan.com.skool360.Model.MIS.MISFinanaceModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.AppConfiguration;

//Changed by Antra 7/1/2019

public class MISFinanceReportAdapter extends RecyclerView.Adapter<MISFinanceReportAdapter.MyViewHolder> {

    private Context context;
    private List<MISFinanaceModel.FinalArray> dataValues = new ArrayList<MISFinanaceModel.FinalArray>();
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private Bundle bundle;
    private String termid;

    public MISFinanceReportAdapter(Context mContext, List<MISFinanaceModel.FinalArray> dataValues, String termid) {
        this.context = mContext;
        this.dataValues = dataValues;
        this.termid = termid;
    }

    @Override
    public MISFinanceReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mis_finance_list, parent, false);
        return new MISFinanceReportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MISFinanceReportAdapter.MyViewHolder holder, final int position) {
        try {

            holder.term1_due.setText(dataValues.get(position).getDueterm1());
            holder.term2_due.setText(dataValues.get(position).getDueterm2());

            holder.head_txt.setText(String.valueOf(dataValues.get(position).getHead()));
            holder.term1_credit_txt.setText(String.valueOf(dataValues.get(position).getRecievedFeesTerm1()));
            holder.term1_debit_txt.setText(String.valueOf(dataValues.get(position).getTotalFeesTerm1()));
            holder.term2_credit_txt.setText(String.valueOf(dataValues.get(position).getRecievedFeesTerm2()));
            holder.term2_debit_txt.setText(String.valueOf(dataValues.get(position).getTotalFeesTerm2()));

            if (position == (dataValues.size() - 1)) {
                holder.term1_credit_txt.setVisibility(View.GONE);
                holder.term1_due.setVisibility(View.GONE);
                holder.term2_credit_txt.setVisibility(View.GONE);
                holder.term2_due.setVisibility(View.GONE);
            }

            if (position == (dataValues.size() - 2)) {
                holder.head_txt.setTypeface(holder.head_txt.getTypeface(), Typeface.BOLD);
                holder.term1_credit_txt.setTypeface(holder.term1_credit_txt.getTypeface(), Typeface.BOLD);
                holder.term1_debit_txt.setTypeface(holder.term1_debit_txt.getTypeface(), Typeface.BOLD);
                holder.term2_credit_txt.setTypeface(holder.term2_credit_txt.getTypeface(), Typeface.BOLD);
                holder.term2_debit_txt.setTypeface(holder.term2_debit_txt.getTypeface(), Typeface.BOLD);
                holder.term1_due.setTypeface(holder.term2_debit_txt.getTypeface(), Typeface.BOLD);
                holder.term2_due.setTypeface(holder.term2_debit_txt.getTypeface(), Typeface.BOLD);

                holder.term1_credit_txt.setTextColor(context.getResources().getColor(R.color.blue));
                holder.term1_debit_txt.setTextColor(context.getResources().getColor(R.color.blue));
                holder.term2_credit_txt.setTextColor(context.getResources().getColor(R.color.blue));
                holder.term2_debit_txt.setTextColor(context.getResources().getColor(R.color.blue));
                holder.term1_due.setTextColor(context.getResources().getColor(R.color.blue));
                holder.term2_due.setTextColor(context.getResources().getColor(R.color.blue));

                holder.term1_debit_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment = new HeadWiseFeeCollectionDetailFragment();
                        bundle = new Bundle();
                        bundle.putString("title", "Term 1 Total Debits");
                        bundle.putString("requestType", "TotalFeesTerm1Count");
                        bundle.putString("StndrdID", "0");
                        bundle.putString("TermID", termid);
//                        bundle.putString("countdata",holder.total_txt.getText().toString());
//                        bundle.putString("Date",AppConfiguration.staffDate);
//                        bundle.putString("deptID",String.valueOf(dataValues.get(position).getDepartmentID()));
                        fragment.setArguments(bundle);
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                        AppConfiguration.firsttimeback = true;
                        AppConfiguration.position = 67;
                    }
                });

                holder.term1_credit_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment = new HeadWiseFeeCollectionDetailFragment();
                        bundle = new Bundle();
                        bundle.putString("title", "Term 1 Received Credits");
                        bundle.putString("requestType", "RecievedFeesTerm1Count");
                        bundle.putString("StndrdID", "0");
                        bundle.putString("TermID", termid);
//                        bundle.putString("countdata",holder.total_txt.getText().toString());
//                        bundle.putString("Date",AppConfiguration.staffDate);
//                        bundle.putString("deptID",String.valueOf(dataValues.get(position).getDepartmentID()));
                        fragment.setArguments(bundle);
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                        AppConfiguration.firsttimeback = true;
                        AppConfiguration.position = 67;
                    }
                });

                holder.term2_debit_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment = new HeadWiseFeeCollectionDetailFragment();
                        bundle = new Bundle();
                        bundle.putString("title", "Term 2 Total Debits");
                        bundle.putString("requestType", "TotalFeesTerm2Count");
                        bundle.putString("StndrdID", "0");
                        bundle.putString("TermID", termid);
//                        bundle.putString("countdata",holder.total_txt.getText().toString());
//                        bundle.putString("Date",AppConfiguration.staffDate);
//                        bundle.putString("deptID",String.valueOf(dataValues.get(position).getDepartmentID()));
                        fragment.setArguments(bundle);
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                        AppConfiguration.firsttimeback = true;
                        AppConfiguration.position = 67;
                    }
                });

                holder.term2_credit_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment = new HeadWiseFeeCollectionDetailFragment();
                        bundle = new Bundle();
                        bundle.putString("title", "Term 2 Received Credits");
                        bundle.putString("requestType", "RecievedFeesTerm2Count");
                        bundle.putString("StndrdID", "0");
                        bundle.putString("TermID", termid);
//                        bundle.putString("countdata",holder.total_txt.getText().toString());
//                        bundle.putString("Date",AppConfiguration.staffDate);
//                        bundle.putString("deptID",String.valueOf(dataValues.get(position).getDepartmentID()));
                        fragment.setArguments(bundle);
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                        AppConfiguration.firsttimeback = true;
                        AppConfiguration.position = 67;
                    }
                });

                holder.term1_due.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment = new HeadWiseFeeCollectionDetailFragment();
                        bundle = new Bundle();
                        bundle.putString("title", "Term 1 Total Due");
                        bundle.putString("requestType", "DueTerm1");
                        bundle.putString("StndrdID", "0");
                        bundle.putString("TermID", termid);
//                        bundle.putString("countdata",holder.total_txt.getText().toString());
//                        bundle.putString("Date",AppConfiguration.staffDate);
//                        bundle.putString("deptID",String.valueOf(dataValues.get(position).getDepartmentID()));
                        fragment.setArguments(bundle);
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                        AppConfiguration.firsttimeback = true;
                        AppConfiguration.position = 67;
                    }
                });

                holder.term2_due.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment = new HeadWiseFeeCollectionDetailFragment();
                        bundle = new Bundle();
                        bundle.putString("title", "Term 2 Total Due");
                        bundle.putString("requestType", "DueTerm2");
                        bundle.putString("StndrdID", "0");
                        bundle.putString("TermID", termid);
//                        bundle.putString("countdata",holder.total_txt.getText().toString());
//                        bundle.putString("Date",AppConfiguration.staffDate);
//                        bundle.putString("deptID",String.valueOf(dataValues.get(position).getDepartmentID()));
                        fragment.setArguments(bundle);
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                        AppConfiguration.firsttimeback = true;
                        AppConfiguration.position = 67;
                    }
                });

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataValues == null ? 0 : dataValues.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView head_txt, term1_credit_txt, term1_debit_txt, term2_credit_txt, term2_debit_txt, term1_due, term2_due;

        public MyViewHolder(final View itemView) {
            super(itemView);
            head_txt = itemView.findViewById(R.id.head_txt);
            term1_credit_txt = itemView.findViewById(R.id.term1_credit_txt);
            term1_debit_txt = itemView.findViewById(R.id.term1_debit_txt);
            term2_credit_txt = itemView.findViewById(R.id.term2_credit_txt);
            term2_debit_txt = itemView.findViewById(R.id.term2_debit_txt);
            term1_due = itemView.findViewById(R.id.term1_due_txt);
            term2_due = itemView.findViewById(R.id.term2_due_txt);

        }
    }

}
