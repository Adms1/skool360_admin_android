package com.skool360admin.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.Interface.onViewClick;
import anandniketan.com.bhadajadmin.Model.Student.StudentInquiryModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.ListGroupStudentInquiryDataDetailBinding;
import anandniketan.com.bhadajadmin.databinding.ListItemHeaderBinding;
import anandniketan.com.bhadajadmin.databinding.ListItemInquiryDataBinding;


/**
 * Created by admsandroid on 1/30/2018.
 */

public class ExpandableListAdapterInquiryData extends BaseExpandableListAdapter {

    private Context _context;
    private List<StudentInquiryModel.FinalArray> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<StudentInquiryModel.StausDetail>> listChildData;
    private HashMap<String, String> listfooterDate;
    private onViewClick onViewClickRef;
    private String status;

    public ExpandableListAdapterInquiryData(Context context, List<StudentInquiryModel.FinalArray> listDataHeader, HashMap<String, List<StudentInquiryModel.StausDetail>> listDataChild, onViewClick onViewClickRef, String status) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.listChildData = listDataChild;
        this.onViewClickRef = onViewClickRef;
        this.status = status;
    }

    @Override
    public StudentInquiryModel.StausDetail getChild(int groupPosition, int childPosititon) {
        return this.listChildData.get(String.valueOf(this._listDataHeader.get(groupPosition).getStudentID())).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ListItemHeaderBinding headerBinding;
        ListItemInquiryDataBinding rowBinding;
//        LayoutInflater infalInflater = (LayoutInflater) this._context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (childPosition > 0 && childPosition < getChildrenCount(groupPosition)) {

            final StudentInquiryModel.StausDetail currentchild = getChild(groupPosition, childPosition - 1);
            rowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_inquiry_data, parent, false);


            convertView = rowBinding.getRoot();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
            Date d = null;
            try {
                d = sdf.parse(currentchild.getDate());
                String formatedate = output.format(d);
                rowBinding.dateTxt.setText(formatedate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            rowBinding.statusTxt.setText(currentchild.getStatus1());

            if (childPosition == listChildData.get(String.valueOf(_listDataHeader.get(groupPosition).getStudentID())).size()) {
                rowBinding.profileTxt.setVisibility(View.VISIBLE);
            } else {
                rowBinding.profileTxt.setVisibility(View.GONE);
            }

            rowBinding.profileTxt.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    AppConfiguration.StudentId = String.valueOf(currentchild.getStudentId());
                    //AppConfiguration.StudentStatus = String.valueOf(currentchild. get);
                    onViewClickRef.getViewClick();
                }
            });

        } else if (childPosition == 0) {
            headerBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_header, parent, false);
            convertView = headerBinding.getRoot();
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (status.equalsIgnoreCase("true")) {
            return this.listChildData.get(String.valueOf(this._listDataHeader.get(groupPosition).getStudentID())).size() + 1;
        } else {
            Utils.ping(_context, "Access Denied");
            return 0;
        }
    }

    @Override
    public StudentInquiryModel.FinalArray getGroup(int groupPosition) {
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
        ListGroupStudentInquiryDataDetailBinding groupBinding;


        final StudentInquiryModel.FinalArray currentGroup = getGroup(groupPosition);

//        String[] headerTitle = getGroup(groupPosition).toString().split("\\|");
//
//        String headerTitle1 = headerTitle[0];
//        String headerTitle2 = headerTitle[1];
//        String headerTitle3 = headerTitle[2];
//        String headerTitle4 = headerTitle[3];
        //String headertitle5 = headerTitle[4];

        if (convertView == null) {

        }

        groupBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_group_student_inquiry_data_detail, parent, false);
        convertView = groupBinding.getRoot();
        String str = String.valueOf(groupPosition + 1);

        groupBinding.indexTxt.setText(str);
        groupBinding.studentnameTxt.setText(currentGroup.getName());
        groupBinding.StandardTxt.setText(currentGroup.getGrade());
        groupBinding.genderTxt.setText(currentGroup.getGender());
        groupBinding.statusTxt.setText(currentGroup.getCurrentStatus());

        if (isExpanded) {
            groupBinding.viewTxt.setTextColor(_context.getResources().getColor(R.color.present));
        } else {
            groupBinding.viewTxt.setTextColor(_context.getResources().getColor(R.color.absent));
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

