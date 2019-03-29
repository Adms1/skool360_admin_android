package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.ListGroupSmsReportBinding;
import anandniketan.com.bhadajadmin.databinding.ListItemSmsReportBinding;

public class ExapandableListAdapterSMSRepoetData extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<StudentAttendanceFinalArray>> listChildData;
    private HashMap<String, String> listfooterDate;
    private String viewstatus;

    public ExapandableListAdapterSMSRepoetData(Context context, List<String> listDataHeader, HashMap<String, List<StudentAttendanceFinalArray>> listDataChild, String viewstatus) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.listChildData = listDataChild;
        this.viewstatus = viewstatus;
    }

    @Override
    public StudentAttendanceFinalArray getChild(int groupPosition, int childPosititon) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ListItemSmsReportBinding listItemSmsReportBinding;
        StudentAttendanceFinalArray currentchild = getChild(groupPosition, childPosition);
        listItemSmsReportBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_sms_report, parent, false);
        convertView = listItemSmsReportBinding.getRoot();


        listItemSmsReportBinding.messageTxt.setText(currentchild.getMessage());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (viewstatus.equalsIgnoreCase("true")) {
            return this.listChildData.get(this._listDataHeader.get(groupPosition)).size();
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
        ListGroupSmsReportBinding groupBinding;
        String[] headerTitle = getGroup(groupPosition).toString().split("\\|");

        String headerTitle1 = headerTitle[0];
        String headerTitle2 = headerTitle[1];
        String headerTitle3 = headerTitle[2];
        String headerTitle4 = headerTitle[3];

        if (convertView == null) {

        }
        groupBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_group_sms_report, parent, false);
        convertView = groupBinding.getRoot();
        String str = String.valueOf(groupPosition + 1);

        groupBinding.indexTxt.setText(str);
        groupBinding.mobileTxt.setText(headerTitle1);
        groupBinding.SendTimeTxt.setText(headerTitle2);
        groupBinding.receiveTimeTxt.setText(headerTitle3);
        groupBinding.statusTxt.setText(headerTitle4);

        if (headerTitle4.equalsIgnoreCase("expired") || headerTitle4.equalsIgnoreCase("Failed")) {
            groupBinding.llHeader.setBackgroundColor(_context.getResources().getColor(R.color.red));
            groupBinding.indexTxt.setTextColor(_context.getResources().getColor(R.color.white1));
            groupBinding.mobileTxt.setTextColor(_context.getResources().getColor(R.color.white1));
            groupBinding.SendTimeTxt.setTextColor(_context.getResources().getColor(R.color.white1));
            groupBinding.receiveTimeTxt.setTextColor(_context.getResources().getColor(R.color.white1));
            groupBinding.statusTxt.setTextColor(_context.getResources().getColor(R.color.white1));
            groupBinding.viewTxt.setTextColor(_context.getResources().getColor(R.color.white1));
        } else {

            if (isExpanded) {
                groupBinding.viewTxt.setTextColor(_context.getResources().getColor(R.color.present));
            } else {
                groupBinding.viewTxt.setTextColor(_context.getResources().getColor(R.color.absent));
            }
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

