package anandniketan.com.skool360.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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

import anandniketan.com.skool360.Interface.OnAdapterItemButtonClick;
import anandniketan.com.skool360.Model.HR.LeaveRequestModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.DialogUtils;


public class ExpandableLeaveRequest extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<LeaveRequestModel.FinalArray>> listChildData;
    private OnAdapterItemButtonClick onAdapterItemButtonClick;
    private String type;

    public ExpandableLeaveRequest(Context context, List<String> listDataHeader, HashMap<String, ArrayList<LeaveRequestModel.FinalArray>> listDataChild, OnAdapterItemButtonClick onAdapterItemButtonClick, String type) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.listChildData = listDataChild;
        this.onAdapterItemButtonClick = onAdapterItemButtonClick;
        this.type = type;

    }

    @Override
    public List<LeaveRequestModel.FinalArray> getChild(int groupPosition, int childPosititon) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final List<LeaveRequestModel.FinalArray> childData = getChild(groupPosition, 0);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (infalInflater != null) {
                convertView = infalInflater.inflate(R.layout.layout_list_item_child_leave_request, null);

                TextView txtlabel, txt_leavedate, txt_reason_label, txt_reason, tvComment;
                RelativeLayout linearButtons;
                LinearLayout llComment;
                Button btnApprove, btnReject, btnModify;
                txtlabel = convertView.findViewById(R.id.txt_label);
                txt_leavedate = convertView.findViewById(R.id.txt_leavedate);
                txt_reason_label = convertView.findViewById(R.id.txt_reason_label);
                txt_reason = convertView.findViewById(R.id.txt_reason);
                tvComment = convertView.findViewById(R.id.txt_comment);
                llComment = convertView.findViewById(R.id.ll_comment);
                linearButtons = convertView.findViewById(R.id.RL_buttons);
                btnApprove = convertView.findViewById(R.id.btn_approve);
                btnReject = convertView.findViewById(R.id.btn_reject);
                btnModify = convertView.findViewById(R.id.btn_modify);

                if (type.equalsIgnoreCase("student")) {

                } else {

                }

                tvComment.setText(childData.get(childPosition).getComment());
                txt_reason.setText(childData.get(childPosition).getReason());
                txt_leavedate.setText(childData.get(childPosition).getLeaveDates());

                if (type.equalsIgnoreCase("student")) {

                    llComment.setVisibility(View.VISIBLE);

                    if (childData.get(childPosition).getStatusName().equalsIgnoreCase("Rejected")) {

                        linearButtons.setVisibility(View.GONE);

                    } else if (childData.get(childPosition).getStatusName().equalsIgnoreCase("Approved")) {
                        linearButtons.setVisibility(View.GONE);

                    } else if (childData.get(childPosition).getStatusName().equalsIgnoreCase("Pending")) {
                        linearButtons.setVisibility(View.VISIBLE);
                        btnModify.setVisibility(View.GONE);

                    }
                } else {

                    llComment.setVisibility(View.GONE);

                    if (childData.get(childPosition).getStatusName().equalsIgnoreCase("Rejected")) {

                        linearButtons.setVisibility(View.GONE);

                    } else if (childData.get(childPosition).getStatusName().equalsIgnoreCase("Approved By Admin")) {
                        linearButtons.setVisibility(View.VISIBLE);
                        btnApprove.setVisibility(View.GONE);
                        btnModify.setVisibility(View.GONE);

                    } else if (childData.get(childPosition).getStatusName().equalsIgnoreCase("Pending")) {
                        linearButtons.setVisibility(View.VISIBLE);
                    }
                }


                btnApprove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DialogUtils.createConfirmDialog(_context, R.string.app_name, R.string.approve_confirm_msg, "OK", "Cancel", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                onAdapterItemButtonClick.onItemButtonClick(OnAdapterItemButtonClick.Action.APPROVE, groupPosition);
                            }

                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                    }
                });

                btnReject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DialogUtils.createConfirmDialog(_context, R.string.app_name, R.string.reject_confirm_msg, "OK", "Cancel", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                onAdapterItemButtonClick.onItemButtonClick(OnAdapterItemButtonClick.Action.REJECT, groupPosition);
                            }

                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                    }
                });

                btnModify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onAdapterItemButtonClick.onItemButtonClick(OnAdapterItemButtonClick.Action.MODIFY, groupPosition);

                    }
                });


            }
        }

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

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String[] headerTitle = getGroup(groupPosition).toString().split("\\|");

        String headerTitle1 = headerTitle[0];
        String headerTitle2 = headerTitle[1];
        String headerTitle3 = headerTitle[2];

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            if (infalInflater != null) {
            convertView = infalInflater.inflate(R.layout.list_item_header_leave_request, null);
        }

        TextView txt_emp, appdate_txt, nodays;
        ImageView iv_indicator;

        txt_emp = convertView.findViewById(R.id.emp_txt);
        appdate_txt = convertView.findViewById(R.id.appdate_txt);
        nodays = convertView.findViewById(R.id.noofdays_txt);

        iv_indicator = convertView.findViewById(R.id.iv_indicator);

        txt_emp.setText(headerTitle1);
        appdate_txt.setText(headerTitle2);
        nodays.setText(headerTitle3);

//            }
//        }

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


}




