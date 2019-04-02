package anandniketan.com.skool360.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import anandniketan.com.skool360.Interface.getEmployeeCheck;
import anandniketan.com.skool360.Model.HR.FinalArrayPageListModel;
import anandniketan.com.skool360.R;


/**
 * Created by admsandroid on 12/20/2017.
 */

public class PageDeatilListAdapter extends RecyclerView.Adapter<PageDeatilListAdapter.MyViewHolder> {
    List<FinalArrayPageListModel> pageListmodel;
    String addAllStr, updateStr, deleteStr;
    private Context context;
    private getEmployeeCheck listner;

    public PageDeatilListAdapter(Context mContext, List<FinalArrayPageListModel> pageListmodel, getEmployeeCheck listner) {
        this.context = mContext;
        this.pageListmodel = pageListmodel;
        this.listner = listner;
    }


    @Override
    public PageDeatilListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_detail_list_item, parent, false);

        return new PageDeatilListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PageDeatilListAdapter.MyViewHolder holder, final int position) {
        String sr = String.valueOf(position + 1);

        holder.index_txt.setText(sr);
        holder.pagename_txt.setText(pageListmodel.get(position).getPageNam());
        addAllStr = pageListmodel.get(position).getStatus().toString();
        updateStr = pageListmodel.get(position).getIsUserUpdate().toString();
        deleteStr = pageListmodel.get(position).getIsUserDelete().toString();

        if (addAllStr.equalsIgnoreCase("true")) {
            holder.addall_chk.setChecked(true);
        } else {
            holder.addall_chk.setChecked(false);
        }

        if (updateStr.equalsIgnoreCase("true")) {
            holder.update_chk.setChecked(true);
        } else {
            holder.update_chk.setChecked(false);
        }

        if (deleteStr.equalsIgnoreCase("true")) {
            holder.delete_chk.setChecked(true);
        } else {
            holder.delete_chk.setChecked(false);
        }

        holder.addall_chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pageListmodel.get(position).setStatus(true);
                    listner.getEmployeeSMSCheck();
                } else {
                    pageListmodel.get(position).setStatus(false);
                    listner.getEmployeeSMSCheck();
                }
            }
        });
        holder.update_chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pageListmodel.get(position).setIsUserUpdate(true);
                    listner.getEmployeeSMSCheck();
                } else {
                    pageListmodel.get(position).setIsUserUpdate(false);
                    listner.getEmployeeSMSCheck();
                }
            }
        });
        holder.delete_chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pageListmodel.get(position).setIsUserDelete(true);
                    listner.getEmployeeSMSCheck();
                } else {
                    pageListmodel.get(position).setIsUserDelete(false);
                    listner.getEmployeeSMSCheck();
                }
            }
        });

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
        return pageListmodel.size();
    }

    public List<FinalArrayPageListModel> getDatas() {
        return pageListmodel;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView index_txt, pagename_txt;
        CheckBox addall_chk, update_chk, delete_chk;

        public MyViewHolder(View itemView) {
            super(itemView);
            index_txt = itemView.findViewById(R.id.index_txt);
            pagename_txt = itemView.findViewById(R.id.pagename_txt);
            addall_chk = itemView.findViewById(R.id.addall_chk);
            update_chk = itemView.findViewById(R.id.update_chk);
            delete_chk = itemView.findViewById(R.id.delete_chk);
        }
    }

}

