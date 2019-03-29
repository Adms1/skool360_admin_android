package com.skool360admin.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.Model.Account.DateWiseFeesCollectionModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.databinding.ListGroupStudentDiscountDetailBinding;
import anandniketan.com.bhadajadmin.databinding.ListItemDailyCollectionBinding;


/**
 * Created by admsandroid on 11/24/2017.
 */

public class ExpandbleListAdapterDailyCollection extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<DateWiseFeesCollectionModel.FinalArray>> _listDataChild;


    public ExpandbleListAdapterDailyCollection(Context context, List<String> listDataHeader, HashMap<String, ArrayList<DateWiseFeesCollectionModel.FinalArray>> listDataChild) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listDataChild;
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
         ListItemDailyCollectionBinding itembinding;
        ArrayList<DateWiseFeesCollectionModel.FinalArray> detail = getChild(groupPosition, 0);
        if (convertView == null) {

        }
        itembinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_daily_collection, parent, false);
        convertView = itembinding.getRoot();

//        itembinding.totalFeesTxt.setText("₹" + " " +detail.get(childPosition).getFeeReceiptDetail().get(0).getAmount());
//        itembinding.revFeesTxt.setText("₹" + " " +detail.get(childPosition).getReceivedFees());
//        itembinding.penddingFeesTxt.setText("₹" + " " +detail.get(childPosition).getPendingFees());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public ArrayList<DateWiseFeesCollectionModel.FinalArray> getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ListGroupStudentDiscountDetailBinding groupbinding;
        String headerTitle = (String) getGroup(groupPosition);
        final String[] spiltValue = headerTitle.split("\\|");
        if (convertView == null) {

        }

        groupbinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_group_student_discount_detail, parent, false);
        convertView = groupbinding.getRoot();
        convertView.setEnabled(false);
        String sr = String.valueOf(groupPosition + 1);
        groupbinding.indexTxt.setText(sr);
        groupbinding.studentnameTxt.setText(spiltValue[0]);
        groupbinding.grnnoTxt.setText(spiltValue[1]);
        groupbinding.sectionTxt.setText(spiltValue[2]);
        groupbinding.totalPaidTxt.setText(spiltValue[3]);

        Log.d("studentname", spiltValue[0]);
        if (isExpanded) {
            groupbinding.viewTxt.setTextColor(_context.getResources().getColor(R.color.present));
        } else {
            groupbinding.viewTxt.setTextColor(_context.getResources().getColor(R.color.absent));
        }

        try {


            groupbinding.viewTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Dialog dialog = new Dialog(_context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_datewise_collection_list_child_item);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


                    TextView tvUserName = (TextView) dialog.findViewById(R.id.tv_name);

                    tvUserName.setText(spiltValue[0]+"("+spiltValue[4]+")");

                    RecyclerView recyclerView = (RecyclerView)dialog.findViewById(R.id.rv_datewisechildlist);

                    recyclerView.setLayoutManager(new LinearLayoutManager(_context));

                   List<DateWiseFeesCollectionModel.FinalArray> dataList =  _listDataChild.get(_listDataHeader.get(groupPosition));

                   DatewiseFeesChildItemAdapter datewiseFeesChildItemAdapter  = new DatewiseFeesChildItemAdapter(_context,dataList.get(groupPosition).getFeeReceiptDetail());
                    recyclerView.setAdapter(datewiseFeesChildItemAdapter);

                   Button btnClose = (Button) dialog.findViewById(R.id.close_btn);
                    btnClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
            });
        }catch (Exception ex){

        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}




