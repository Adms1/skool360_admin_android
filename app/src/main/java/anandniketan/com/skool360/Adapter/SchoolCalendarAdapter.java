package anandniketan.com.skool360.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.skool360.R;

public class SchoolCalendarAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<String>> child = new HashMap<>();

    public SchoolCalendarAdapter(Context _context, List<String> listDataHeader, HashMap<String, ArrayList<String>> child) {
        this._context = _context;
        this._listDataHeader = listDataHeader;
        this.child = child;

    }

    @Override
    public String getChild(int groupPosition, int childPosititon) {
        return this.child.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childData = getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = infalInflater.inflate(R.layout.list_item_schoolcalendar_child, null);
        }

        TextView name_txt;

        name_txt = convertView.findViewById(R.id.calchild_name_txt);

        name_txt.setText(String.valueOf(childData));

        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return this.child.get(this._listDataHeader.get(groupPosition)).size();
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

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_schoolcalendar_group, null);
        }

//        LinearLayout headerList = convertView.findViewById(R.id.child_header);
//
//        if (isExpanded) {
//            headerList.setVisibility(View.VISIBLE);
//
//        } else {
//            headerList.setVisibility(View.GONE);
//
//        }

        TextView name_txt, date_txt, days_txt;
        ImageView ivArrow;

        name_txt = convertView.findViewById(R.id.calgrp_name_txt);
        date_txt = convertView.findViewById(R.id.calgrp_date_txt);
        days_txt = convertView.findViewById(R.id.calgrp_days_txt);
        ivArrow = convertView.findViewById(R.id.calgrp_iv_view);

        name_txt.setText(String.valueOf(headerTitle1));
        date_txt.setText(String.valueOf(headerTitle2));
        days_txt.setText(String.valueOf(headerTitle3));

        if (isExpanded) {
            ivArrow.setImageResource(R.drawable.arrow_1_42_down);
        } else {
            ivArrow.setImageResource(R.drawable.arrow_1_42);
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
