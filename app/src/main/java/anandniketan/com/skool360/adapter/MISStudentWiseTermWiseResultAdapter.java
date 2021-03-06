package anandniketan.com.skool360.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.skool360.Interface.getEmployeeCheck;
import anandniketan.com.skool360.Model.MIS.MIStudentWiseResultModel;
import anandniketan.com.skool360.R;

public class MISStudentWiseTermWiseResultAdapter extends RecyclerView.Adapter<MISStudentWiseTermWiseResultAdapter.MyViewHolder> {
    List<MIStudentWiseResultModel.Datum> finalArrayModelList;
    String stduentIdStr, mobilenoStr, FinalValue;
    getEmployeeCheck listner;
    private Context context;
    private ArrayList<String> dataCheck = new ArrayList<String>();
    private int tag = 1;


    public MISStudentWiseTermWiseResultAdapter(Context mContext, List<MIStudentWiseResultModel.Datum> finalArrayBulkSMSModelList, int tag) {
        this.context = mContext;
        this.finalArrayModelList = finalArrayBulkSMSModelList;
        this.listner = listner;
        this.tag = tag;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mis_student_term_wise_result_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MISStudentWiseTermWiseResultAdapter.MyViewHolder holder, final int position) {
        final String sr = String.valueOf(position + 1);
        holder.subject_txt.setText(String.valueOf(finalArrayModelList.get(position).getSubject()));
        holder.term1_marks_txt.setText(String.valueOf(finalArrayModelList.get(position).getTerm1Marks()));
        holder.term2_marks_txt.setText(String.valueOf(finalArrayModelList.get(position).getTerm2Marks()));

        if (position == (finalArrayModelList.size() - 1)) {
            holder.subject_txt.setTypeface(holder.subject_txt.getTypeface(), Typeface.BOLD);
            holder.term1_marks_txt.setTypeface(holder.subject_txt.getTypeface(), Typeface.BOLD);
            holder.term2_marks_txt.setTypeface(holder.subject_txt.getTypeface(), Typeface.BOLD);
        }

    }

    @Override
    public long getItemId(int position) {
// return specific item's id here
        return position;
    }


    @Override
    public int getItemCount() {
        return finalArrayModelList.size();
    }

    public List<MIStudentWiseResultModel.Datum> getDatas() {
        return finalArrayModelList;
    }

    public ArrayList<String> getData() {
        return dataCheck;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView subject_txt, term1_marks_txt, term2_marks_txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            subject_txt = itemView.findViewById(R.id.subject_txt);
            term1_marks_txt = itemView.findViewById(R.id.term1_marks_txt);
            term2_marks_txt = itemView.findViewById(R.id.term2_marks_txt);
        }
    }
}
