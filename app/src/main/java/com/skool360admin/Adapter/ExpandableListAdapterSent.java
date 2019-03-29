package com.skool360admin.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.Interface.onDeleteButton;
import anandniketan.com.bhadajadmin.Model.Other.FinalArraySMSDataModel;
import anandniketan.com.bhadajadmin.R;


/**
 * Created by admsandroid on 10/25/2017.
 */

public class ExpandableListAdapterSent extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    private HashMap<String, List<FinalArraySMSDataModel>> listChildData;
    // child data in format of header title, child title
    private ArrayList<String> dataCheck = new ArrayList<String>();
    private onDeleteButton listner;


    public ExpandableListAdapterSent(Context context, List<String> listDataHeader, HashMap<String, List<FinalArraySMSDataModel>> listDataChild, onDeleteButton onDeleteButton) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.listChildData = listDataChild;
        this.listner=onDeleteButton;
    }

    @Override
    public List<FinalArraySMSDataModel> getChild(int groupPosition, int childPosititon) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final List<FinalArraySMSDataModel> childData = getChild(groupPosition, 0);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_sent, null);
        }


        TextView txtSubject;
        final Button delete_btn;
        txtSubject = (TextView) convertView.findViewById(R.id.txtSubject);
        delete_btn = (Button) convertView.findViewById(R.id.delete_btn);

        txtSubject.setText(childData.get(childPosition).getDescription());
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataCheck.add(childData.get(childPosition).getMessageID());
                listner.deleteSentMessage();
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition)).size();
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
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String[] headerTitle = getGroup(groupPosition).toString().split("\\|");

        String headerTitle1 = headerTitle[0];
        String headerTitle2 = headerTitle[1];
        String headerTitle3 = headerTitle[2];
        String headerTitle4 = headerTitle[3];

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_inbox, null);
        }
        TextView Student_name_inbox_txt, date_inbox_txt, subject_inbox_txt, view_inbox_txt;
        Student_name_inbox_txt = (TextView) convertView.findViewById(R.id.Student_name_inbox_txt);
        date_inbox_txt = (TextView) convertView.findViewById(R.id.date_inbox_txt);
        subject_inbox_txt = (TextView) convertView.findViewById(R.id.subject_inbox_txt);
        view_inbox_txt = (TextView) convertView.findViewById(R.id.view_inbox_txt);

        Student_name_inbox_txt.setText(headerTitle1);
        date_inbox_txt.setText(headerTitle2);
        subject_inbox_txt.setText(headerTitle3);

        if (headerTitle4.equalsIgnoreCase("Pending")) {
            Student_name_inbox_txt.setTypeface(null, Typeface.BOLD);
            date_inbox_txt.setTypeface(null, Typeface.BOLD);
            subject_inbox_txt.setTypeface(null, Typeface.BOLD);
            view_inbox_txt.setTypeface(null, Typeface.BOLD);
        } else {
            Student_name_inbox_txt.setTypeface(null, Typeface.NORMAL);
            date_inbox_txt.setTypeface(null, Typeface.NORMAL);
            subject_inbox_txt.setTypeface(null, Typeface.NORMAL);
            view_inbox_txt.setTypeface(null, Typeface.NORMAL);

        }

        if (isExpanded) {
            view_inbox_txt.setTextColor(_context.getResources().getColor(R.color.present));
        } else {
            view_inbox_txt.setTextColor(_context.getResources().getColor(R.color.absent));
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

    public ArrayList<String> getMessageId() {
        return dataCheck;
    }
}



