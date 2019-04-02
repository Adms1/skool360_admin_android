package anandniketan.com.skool360.Adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.skool360.Model.Student.TestModel;
import anandniketan.com.skool360.R;

public class MarkSyllabusExamListAdapter extends RecyclerView.Adapter<MarkSyllabusExamListAdapter.MyViewHolder> {
    private Context context;
    private List<TestModel.FinalArray> mData;
    private List<String> mCheckedTestIds = new ArrayList<String>();
    private boolean[] chkArray;


    public MarkSyllabusExamListAdapter(Context mContext, List<TestModel.FinalArray> LoginDetailArrayList) {
        this.context = mContext;
        this.mData = LoginDetailArrayList;
        chkArray = new boolean[mData.size()];
    }


    @Override
    public MarkSyllabusExamListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mark_syllabus_exams, parent, false);
        return new MarkSyllabusExamListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MarkSyllabusExamListAdapter.MyViewHolder holder, final int position) {

        TestModel.FinalArray finalArray = mData.get(position);

        String status = mData.get(position).getCheckedStatus();
        holder.cbCheckValue.setText(finalArray.getTestName());
        if (status.equalsIgnoreCase("0")) {
            chkArray[position] = false;
            // holder.cbCheckValue.setChecked(false);
            //mData.get(position).setCheckedStatus("0");
            if (mCheckedTestIds != null && mCheckedTestIds.size() > 0) {
                mCheckedTestIds.remove(String.valueOf(mData.get(position).getTestName()));
            }

        } else {
            if (status.equalsIgnoreCase("1")) {
                chkArray[position] = true;
                //  holder.cbCheckValue.setChecked(true);
                mCheckedTestIds.add(String.valueOf(mData.get(position).getTestName()));
                //mData.get(position).setCheckedStatus("1");
            }
        }

        holder.cbCheckValue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.cbCheckValue.isChecked()) {

                    chkArray[position] = true;
                    mCheckedTestIds.add(String.valueOf(mData.get(position).getTestName()));
                    mData.get(position).setCheckedStatus("1");

                } else {
                    chkArray[position] = false;
                    mCheckedTestIds.remove(String.valueOf(mData.get(position).getTestName()));
                    mData.get(position).setCheckedStatus("0");
                }


//                else{
//                    mCheckedTestIds.remove(String.valueOf(mData.get(position).getTestID()));
//                    mData.get(position).setCheckedStatus("0");
//                }
            }
        });

        if (chkArray.length > 0) {
            for (int i = 0; i < chkArray.length; i++) {
                if (chkArray != null && chkArray[position]) {
                    holder.cbCheckValue.setChecked(true);
                } else {
                    holder.cbCheckValue.setChecked(false);
                }
            }
        }


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<String> getCheckedItems() {
        return mCheckedTestIds;
    }

    public List<TestModel.FinalArray> getDatas() {
        return mData;
    }

    public void updateData(int pos) {
        mData.get(pos).setCheckedStatus("1");
        notifyDataSetChanged();
    }

    public void clearCheckedStatus() {
        for (int count = 0; count < mData.size(); count++) {
            mData.get(count).setCheckedStatus("0");
        }
        if (mCheckedTestIds != null && mCheckedTestIds.size() > 0) {
            mCheckedTestIds.clear();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AppCompatCheckBox cbCheckValue;

        public MyViewHolder(View itemView) {
            super(itemView);
            cbCheckValue = itemView.findViewById(R.id.cb_exam_list_item);

        }
    }

}
