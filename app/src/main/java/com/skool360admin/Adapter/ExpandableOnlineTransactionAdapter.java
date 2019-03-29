package com.skool360admin.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.Model.Account.OnlineTransactionModel;
import anandniketan.com.bhadajadmin.R;

public class ExpandableOnlineTransactionAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<OnlineTransactionModel.FinalArray>> listChildData;
    private String standard = "", standardIDS = "";
    private int annousID;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
//    private String viewstatus;

    public ExpandableOnlineTransactionAdapter(Context context, List<String> listDataHeader, HashMap<String, ArrayList<OnlineTransactionModel.FinalArray>> listDataChild) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.listChildData = listDataChild;

    }

    @Override
    public List<OnlineTransactionModel.FinalArray> getChild(int groupPosition, int childPosititon) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final List<OnlineTransactionModel.FinalArray> childData = getChild(groupPosition, 0);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_child_online_transaction_list, null);
        }

        TextView route, drive, parents, vehicle;

        route = convertView.findViewById(R.id.txt_pid);
        drive = convertView.findViewById(R.id.txt_date);
        parents = convertView.findViewById(R.id.txt_stuname);
        vehicle = convertView.findViewById(R.id.txt_grade);

        route.setText(childData.get(childPosition).getPaymentid().toString());
        drive.setText(childData.get(childPosition).getDate());
        parents.setText(childData.get(childPosition).getStudentname());
        vehicle.setText(childData.get(childPosition).getGrade());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        if (viewstatus.equalsIgnoreCase("true")) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition)).size();
//        } else {
//            Utils.ping(_context, "Access Denied");
//            return 0;
//        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String[] headerTitle = getGroup(groupPosition).toString().split("\\|");

        String headerTitle1 = headerTitle[0];
        String headerTitle2 = headerTitle[1];
        String headerTitle3 = headerTitle[2];
        String headerTitle4 = headerTitle[3];

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_header_online_transaction, null);
        }
        TextView tid_txt, date_txt, amount_txt;
        ImageView iv_indicator, status_txt;

        tid_txt = convertView.findViewById(R.id.tid_txt);
        date_txt = convertView.findViewById(R.id.date_txt);
        amount_txt = convertView.findViewById(R.id.amount_txt);
        status_txt = convertView.findViewById(R.id.status_txt);
        iv_indicator = convertView.findViewById(R.id.arrw_txt);

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM");
//        headerTitle2 = simpleDateFormat.format(headerTitle2);

        if (headerTitle4.equalsIgnoreCase("Unsettled")) {

            status_txt.setImageDrawable(_context.getResources().getDrawable(R.drawable.close_check));

        } else {
            status_txt.setImageDrawable(_context.getResources().getDrawable(R.drawable.checked));

        }

        tid_txt.setText(headerTitle1);
        date_txt.setText(headerTitle2);
        amount_txt.setText("₹" + headerTitle3);
//        status_txt.setText(headerTitle4);

//        if (viewstatus.equalsIgnoreCase("true")) {
        if (isExpanded) {
            iv_indicator.setImageResource(R.drawable.arrow_1_42_down);
        } else {
            iv_indicator.setImageResource(R.drawable.arrow_1_42);
        }
//        }
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

