package anandniketan.com.skool360.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.skool360.Interface.EditTimetableWithId;
import anandniketan.com.skool360.Interface.editTimetableData;
import anandniketan.com.skool360.Interface.onDeleteWithId;
import anandniketan.com.skool360.Model.Staff.Datum;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.databinding.ListGroupTimetableBinding;

/**
 * Created by admsandroid on 11/27/2017.
 */

//1. change layout and set data according to it.
// Antra 21/02/2019

public class ExpandableListAdapterTimeTable extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<Datum>> _listDataChild;
    private Dialog dialog;
    private onDeleteWithId onDeleteWithId;
    private EditTimetableWithId editTimetableWithId;
    private editTimetableData onEditRecordWithPosition;

    public ExpandableListAdapterTimeTable(Context context, List<String> listDataHeader,
                                          HashMap<String, ArrayList<Datum>> listDataChild,
                                          EditTimetableWithId editTimetableWithId,
                                          editTimetableData onEditRecordWithPosition,
                                          onDeleteWithId onDeleteWithId) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listDataChild;
        this.editTimetableWithId = editTimetableWithId;
        this.onEditRecordWithPosition = onEditRecordWithPosition;
        this.onDeleteWithId = onDeleteWithId;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final List<Datum> childData = getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_time_table, null);
        }

//        LayoutInflater infalInflater = (LayoutInflater) this._context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        Datum detail = getChild(groupPosition, childPosition);
//        convertView = infalInflater.inflate(R.layout.list_item_time_table, null);
        TextView txtLecture, txtSubject, txtTeacher;
        ImageView ivEdit, ivDelete, ivAdd;

        txtLecture = convertView.findViewById(R.id.timetable_txtLecture);
        txtSubject = convertView.findViewById(R.id.timetable_tvSubject);
        txtTeacher = convertView.findViewById(R.id.timetable_tvTeacher);
        ivEdit = convertView.findViewById(R.id.timetable_ivEdit);
        ivDelete = convertView.findViewById(R.id.timetable_ivDelete);
        ivAdd = convertView.findViewById(R.id.timetable_ivAdd);

        txtLecture.setText(childData.get(childPosition).getLecture().toString());
        txtSubject.setText(childData.get(childPosition).getSubject());
        txtTeacher.setText(childData.get(childPosition).getTeacherName());
        Picasso.get().load("http://192.168.1.22:8086/SKOOL360-Design-Icons/Admin/Delete.png").resize(100, 100).into(ivDelete);

        if (childData.get(childPosition).getSubject().equalsIgnoreCase("") && childData.get(childPosition).getTeacherName().equalsIgnoreCase("")) {
            ivEdit.setVisibility(View.GONE);
            ivDelete.setVisibility(View.GONE);
        } else {
            ivEdit.setVisibility(View.VISIBLE);
            ivDelete.setVisibility(View.VISIBLE);
        }

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteWithId.deleteRecordWithId(childData.get(childPosition).getTimetableID());
            }
        });

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditRecordWithPosition.editTimetable(groupPosition, childPosition, childData.get(childPosition).getSubject(), childData.get(childPosition).getTeacherName());
            }
        });

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTimetableWithId.editTimetablewithID(groupPosition, childPosition);

            }
        });

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
    public List<Datum> getChild(int groupPosition, int childPosititon) {
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
        ListGroupTimetableBinding groupbinding;

        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {

        }
        groupbinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_group_timetable, parent, false);
        convertView = groupbinding.getRoot();

        LinearLayout ll = convertView.findViewById(R.id.timetable_child_header);

        groupbinding.lblListHeader.setText(headerTitle);


        if (isExpanded) {

            ll.setVisibility(View.VISIBLE);

            groupbinding.lblListHeader.setTextColor(_context.getResources().getColor(R.color.present));
        } else {

            ll.setVisibility(View.GONE);

            groupbinding.lblListHeader.setTextColor(_context.getResources().getColor(R.color.orange));
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





