package anandniketan.com.skool360.adapter;

import android.content.Context;
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

import anandniketan.com.skool360.Fragment.fragment.mis.HeadWiseFeeCollectionDetailFragment;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.AppConfiguration;

public class MISFinanceListAdapter extends RecyclerView.Adapter<MISFinanceListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> dataValues = new ArrayList<String>();
    private ArrayList<String> keyValues = new ArrayList<String>();
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private Bundle bundle;
    private String termid;

    public MISFinanceListAdapter(Context mContext, ArrayList<String> dataValues, ArrayList<String> keyValues, String termid) {
        this.context = mContext;
        this.dataValues = dataValues;
        this.keyValues = keyValues;
        this.termid = termid;

    }

    @Override
    public MISFinanceListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mis_data, parent, false);
        return new MISFinanceListAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MISFinanceListAdapter.MyViewHolder holder, final int position) {
        try {
            String sr = String.valueOf(position + 1);


            String key = keyValues.get(position);

            String value = dataValues.get(position);

            holder.key_text.setText(String.valueOf(key));
            holder.value_txt.setText(String.valueOf(value));


            holder.value_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position == 0) {
                        fragment = new HeadWiseFeeCollectionDetailFragment();
                        bundle = new Bundle();
                        bundle.putString("title", "6 Months Pending Fees");
                        bundle.putString("requestType", holder.key_text.getText().toString());
                        bundle.putString("StndrdID", "0");
                        bundle.putString("TermID", termid);
                        bundle.putString("countdata", holder.value_txt.getText().toString());

//                        bundle.putString("countdata",holder.total_txt.getText().toString());
//                        bundle.putString("Date",AppConfiguration.staffDate);
//                        bundle.putString("deptID",String.valueOf(dataValues.get(position).getDepartmentID()));
                        fragment.setArguments(bundle);
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                        AppConfiguration.firsttimeback = true;
                        AppConfiguration.position = 67;


                    } else {
                        fragment = new HeadWiseFeeCollectionDetailFragment();
                        bundle = new Bundle();
                        bundle.putString("title", "Previous year Pending Fees");
                        bundle.putString("requestType", "Previous year pending fees");
                        bundle.putString("StndrdID", "0");
                        bundle.putString("TermID", termid);
                        bundle.putString("countdata", holder.value_txt.getText().toString());
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
                }
            });


        } catch (Exception ex) {

            ex.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return dataValues == null ? 0 : dataValues.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView key_text, value_txt;


        public MyViewHolder(View itemView) {
            super(itemView);
            key_text = itemView.findViewById(R.id.key_txt);
            value_txt = itemView.findViewById(R.id.value_txt);

        }
    }
}
