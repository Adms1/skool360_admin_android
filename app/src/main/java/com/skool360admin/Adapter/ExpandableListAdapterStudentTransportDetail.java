package com.skool360admin.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.ListGroupStudentRouteTransportDetailBinding;
import anandniketan.com.bhadajadmin.databinding.ListItemStudentRouteTransportDetailBinding;


/**
 * Created by admsandroid on 11/23/2017.
 */

public class ExpandableListAdapterStudentTransportDetail extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<StudentAttendanceFinalArray>> _listDataChild;
    private String status;

    public ExpandableListAdapterStudentTransportDetail(Context context, List<String> listDataHeader,
                                                       HashMap<String, ArrayList<StudentAttendanceFinalArray>> listDataChild, String status) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listDataChild;
        this.status = status;
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ListItemStudentRouteTransportDetailBinding routeTransportDetailBinding;
        ArrayList<StudentAttendanceFinalArray> detail = getChild(groupPosition, 0);
        if (convertView == null) {

        }
        routeTransportDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_student_route_transport_detail, parent, false);
        convertView = routeTransportDetailBinding.getRoot();

        routeTransportDetailBinding.routenameTxt.setText(detail.get(childPosition).getRouteName());
        routeTransportDetailBinding.pickupPointnameTxt.setText(detail.get(childPosition).getPickupPointName());
        routeTransportDetailBinding.kmTxt.setText(detail.get(childPosition).getKM());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (status.equalsIgnoreCase("true")) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        } else {
            Utils.ping(_context, "Access Denied");
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public ArrayList<StudentAttendanceFinalArray> getChild(int groupPosition, int childPosititon) {
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
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ListGroupStudentRouteTransportDetailBinding groupStudentRouteTransportDetailBinding;
        String headerTitle = (String) getGroup(groupPosition);
        String[] spiltValue = headerTitle.split("\\|");
        if (convertView == null) {

        }
        groupStudentRouteTransportDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_group_student_route_transport_detail, parent, false);
        convertView = groupStudentRouteTransportDetailBinding.getRoot();

        String sr = String.valueOf(groupPosition + 1);
//        groupStudentRouteTransportDetailBinding.indexTxt.setText(sr);
        groupStudentRouteTransportDetailBinding.studentnameTxt.setText(spiltValue[0]);
        groupStudentRouteTransportDetailBinding.grnnoTxt.setText(spiltValue[1]);
        groupStudentRouteTransportDetailBinding.gradeTxt.setText(spiltValue[2]);
        Log.d("studentname", spiltValue[0]);
        if (isExpanded) {
            groupStudentRouteTransportDetailBinding.viewTxt.setTextColor(_context.getResources().getColor(R.color.present));
        } else {
            groupStudentRouteTransportDetailBinding.viewTxt.setTextColor(_context.getResources().getColor(R.color.absent));
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

