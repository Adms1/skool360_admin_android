package anandniketan.com.skool360.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.skool360.Interface.getEditpermission;
import anandniketan.com.skool360.Interface.onDeleteWithId;
import anandniketan.com.skool360.Model.LeaveModel;
import anandniketan.com.skool360.R;

public class LeaveBalanceAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<LeaveModel.FinalArray>> listChildData;
    private String getData = "";
    //    private int annousID;
    private onDeleteWithId onDeleteWithIdref;
    private getEditpermission onUpdateRecordRef;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
//    private String status, updatestatus, deletestatus;

    public LeaveBalanceAdapter(Context context, List<String> listDataHeader, HashMap<String, ArrayList<LeaveModel.FinalArray>> listDataChild) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.listChildData = listDataChild;
//        this.status = status;
//        this.updatestatus = updatestatus;
//        this.deletestatus = deletestatus;
    }

    @Override
    public List<LeaveModel.FinalArray> getChild(int groupPosition, int childPosititon) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final List<LeaveModel.FinalArray> childData = getChild(groupPosition, 0);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_child_applyleave, null);
        }

        TextView leavedate, approvedate, approvedays, approveby, reason, tvPL, tvCL;
        LinearLayout llLeaveDate, llApproveDate, llLeaveDays, llApproveby, llReason, llPLCL;
        RelativeLayout llBottom;

        leavedate = convertView.findViewById(R.id.txt_leavedate);
        approvedate = convertView.findViewById(R.id.txt_approvedate);
        approvedays = convertView.findViewById(R.id.txt_approvedays);
        approveby = convertView.findViewById(R.id.txt_approveby);
        reason = convertView.findViewById(R.id.txt_reason);
        llBottom = convertView.findViewById(R.id.llPendingBottom);
        llLeaveDate = convertView.findViewById(R.id.llLeaveDate);
        llApproveDate = convertView.findViewById(R.id.llApproveDate);
        llLeaveDays = convertView.findViewById(R.id.llLeaveDays);
        llApproveby = convertView.findViewById(R.id.llApproveBy);
        llReason = convertView.findViewById(R.id.llReason);
        llPLCL = convertView.findViewById(R.id.llPLCL);
        tvPL = convertView.findViewById(R.id.txt_pl);
        tvCL = convertView.findViewById(R.id.txt_cl);
        Button btnDelete = convertView.findViewById(R.id.applyleave_btn_delete);
        Button btnEdit = convertView.findViewById(R.id.applyleave_btn_edit);

//        standardIDS = childData.get(childPosition).getStandardID();
//        standard = childData.get(childPosition).getStandard();
//        annousID = childData.get(childPosition).getPKAnnouncmentID();

        leavedate.setText(childData.get(childPosition).getLeaveStartDate() + " - " + childData.get(childPosition).getLeaveEndDate());
        approvedate.setText(childData.get(childPosition).getApproveStartDate() + " - " + childData.get(childPosition).getApproveEndDate());
        approvedays.setText(childData.get(childPosition).getApproveDays() + " Days");
        tvCL.setText(childData.get(childPosition).getCL());
        tvPL.setText(childData.get(childPosition).getPL());
        approveby.setText(childData.get(childPosition).getApproveBy());
        reason.setText(childData.get(childPosition).getReason());

        if (childData.get(childPosition).getStatus().equalsIgnoreCase("Pending")) {
            llLeaveDate.setVisibility(View.VISIBLE);
            llApproveDate.setVisibility(View.GONE);
            llLeaveDays.setVisibility(View.GONE);
            llApproveby.setVisibility(View.GONE);
            llReason.setVisibility(View.VISIBLE);
            llBottom.setVisibility(View.GONE);
            llPLCL.setVisibility(View.GONE);
        } else {
            llLeaveDate.setVisibility(View.VISIBLE);
            llApproveDate.setVisibility(View.VISIBLE);
            llLeaveDays.setVisibility(View.VISIBLE);
            llApproveby.setVisibility(View.VISIBLE);
            llReason.setVisibility(View.VISIBLE);
            llBottom.setVisibility(View.GONE);
            llPLCL.setVisibility(View.VISIBLE);
        }


//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (deletestatus.equalsIgnoreCase("true")) {
//                    DialogUtils.createConfirmDialog(_context, R.string.delete, R.string.delete_leave_confirm_msg, new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            onDeleteWithIdref.deleteRecordWithId(String.valueOf(childData.get(childPosition).getLeaveid()));
//                        }
//
//                    }, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                        }
//                    }).show();
//                } else {
//                    Utils.ping(_context, "Access Denied");
//                }
//            }
//        });
//
//        btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (updatestatus.equalsIgnoreCase("true")) {
//
//                    getData = childData.get(childPosition).getLeaveDays() + "|" + childData.get(childPosition).getLeaveStartDate() + "|" +
//                            childData.get(childPosition).getLeaveEndDate() + "|" + childData.get(childPosition).getHeadname() + "|" +
//                            childData.get(childPosition).getReason() + "|" + childData.get(childPosition).getLeaveid();
//
//                    onUpdateRecordRef.getEditpermission();
//
//                } else {
//                    Utils.ping(_context, "Access Denied");
//                }
//
//
//            }
//        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        if (status.equalsIgnoreCase("true")) {
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
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String[] headerTitle = getGroup(groupPosition).toString().split("\\|");

        String headerTitle1 = headerTitle[0];
        String headerTitle2 = headerTitle[1];
        String headerTitle3 = headerTitle[2];

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_header_applyleave, null);
        }
        TextView txt_date, txt_days, txt_status;
        ImageView iv_indicator;

        txt_date = convertView.findViewById(R.id.createdate_txt);
        txt_days = convertView.findViewById(R.id.nodays_txt);
        txt_status = convertView.findViewById(R.id.status_txt);
        iv_indicator = convertView.findViewById(R.id.ivIndicator);

        txt_date.setText(headerTitle1);
        txt_days.setText(headerTitle2);
        txt_status.setText(headerTitle3);

        if (headerTitle3.equalsIgnoreCase("Approved")) {
            txt_status.setTextColor(_context.getResources().getColor(R.color.green));
        } else if (headerTitle3.equalsIgnoreCase("Pending")) {
            txt_status.setTextColor(_context.getResources().getColor(R.color.yellow));
        } else if (headerTitle3.equalsIgnoreCase("Rejected")) {
            txt_status.setTextColor(_context.getResources().getColor(R.color.red));
        } else {
            txt_status.setTextColor(_context.getResources().getColor(R.color.black));
        }

        if (isExpanded) {
            iv_indicator.setImageResource(R.drawable.arrow_1_42_down);
        } else {
            iv_indicator.setImageResource(R.drawable.arrow_1_42);
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

    public String getData() {
        return getData;
    }

}