package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import anandniketan.com.bhadajadmin.R;

public class MISListAdapter extends RecyclerView.Adapter<MISListAdapter.MyViewHolder> {
    private Context context;


    private ArrayList<String> dataValues = new ArrayList<String>();
    private ArrayList<String> keyValues = new ArrayList<String>();

    public MISListAdapter(Context mContext, ArrayList<String> dataValues, ArrayList<String> keyValues) {
        this.context = mContext;
        this.dataValues = dataValues;
        this.keyValues = keyValues;

    }

    @Override
    public MISListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mis_data, parent, false);
        return new MISListAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MISListAdapter.MyViewHolder holder, int position) {
        try {


            String sr = String.valueOf(position + 1);


            if(keyValues.get(position).equalsIgnoreCase("TotalPresentStudent")){
                keyValues.set(position,"Present");
            }
            if(keyValues.get(position).equalsIgnoreCase("TotalLeaveStudent")){
                keyValues.set(position,"Leave");
            }
            if(keyValues.get(position).equalsIgnoreCase("TotalAbsentStudent")){
                keyValues.set(position,"Absent");
            }
            if(keyValues.get(position).equalsIgnoreCase("TotalConsistanceAbsentStudent")){
                keyValues.set(position,"Ab > 3");
            }

            if(keyValues.get(position).equalsIgnoreCase("TotalStudentANTStudent")){
                keyValues.set(position,"A.N.T");
            }

            if(keyValues.get(position).equalsIgnoreCase("TotalStudent")){
                keyValues.set(position,"Total");
            }



            if(keyValues.get(position).equalsIgnoreCase("TotalStaff")){
                keyValues.set(position,"Total");
            }

            if(keyValues.get(position).equalsIgnoreCase("TotalPresentStaff")){
                keyValues.set(position,"Present");
            }
            if(keyValues.get(position).equalsIgnoreCase("TotalLeaveStaff")){
                keyValues.set(position,"Leave");
            }
            if(keyValues.get(position).equalsIgnoreCase("TotalAbsentStaff")){
                keyValues.set(position,"Absent");
            }
            if(keyValues.get(position).equalsIgnoreCase("TotalStudentANTStaff")){
                keyValues.set(position,"A.N.T Class Teacher");
            }


            if(keyValues.get(position).equalsIgnoreCase("CWDoneStaff")){

            }




            String key = keyValues.get(position);

            String value = dataValues.get(position);

            holder.key_text.setText(String.valueOf(key));
            holder.value_txt.setText(String.valueOf(value));
        }catch (Exception ex){

        }



    }

    @Override
    public int getItemCount() {
        return dataValues == null ? 0 :dataValues.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView key_text,value_txt;


       public MyViewHolder(View itemView) {
            super(itemView);
            key_text = (TextView) itemView.findViewById(R.id.key_txt);
            value_txt = (TextView) itemView.findViewById(R.id.value_txt);

        }
    }

}
