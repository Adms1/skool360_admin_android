package com.skool360admin.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Interface.SuggestionReplyCallback;
import anandniketan.com.bhadajadmin.Model.Student.SuggestionDataModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.DialogUtils;

public class ExpandableSuggestion extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<SuggestionDataModel.FinalArray>> listChildData;
    private SuggestionReplyCallback suggestionReplyCallback;
//    private String type;

    public ExpandableSuggestion(Context context, List<String> listDataHeader, HashMap<String, ArrayList<SuggestionDataModel.FinalArray>> listDataChild, String type, SuggestionReplyCallback suggestionReplyCallback) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.listChildData = listDataChild;
        this.suggestionReplyCallback = suggestionReplyCallback;
//        this.type = type;

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final List<SuggestionDataModel.FinalArray> childData = getChild(groupPosition, 0);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (infalInflater != null) {
                convertView = infalInflater.inflate(R.layout.layout_list_item_child_suggestion, null);
            }
        }

        TextView tvReply, tvComment, tvStudent, tvReplyDate;
        final Button btnReply, btnSend, btnCancel;
        final RelativeLayout llBottom;
        final EditText etMessage;

        tvReply = convertView.findViewById(R.id.sug_tvReply);
        tvComment = convertView.findViewById(R.id.sug_tvComment);
        tvStudent = convertView.findViewById(R.id.sug_tvStudent);
        tvReplyDate = convertView.findViewById(R.id.sug_tvReplydate);
        btnReply = convertView.findViewById(R.id.sug_btnReply);
        btnSend = convertView.findViewById(R.id.sug_btnSend);
        btnCancel = convertView.findViewById(R.id.sug_btnCancel);
        etMessage = convertView.findViewById(R.id.sug_etMessage);
        llBottom = convertView.findViewById(R.id.sug_llLower);

        tvReply.setText(childData.get(childPosition).getReply());
        tvComment.setText(childData.get(childPosition).getComment());
        tvStudent.setText(childData.get(childPosition).getStu_name() + ", " + childData.get(childPosition).getStandard() + " - " + childData.get(childPosition).getClassname());
        tvReplyDate.setText(parseDateToddMMyyyy("yyyy-MM-dd'T'HH:mm:ss.SSSS", "dd MMM yyyy HH:mm a", childData.get(childPosition).getSuggestiondatetime()));

        if (childData.get(childPosition).getStatus().equalsIgnoreCase("Replying")) {

            llBottom.setVisibility(View.GONE);
            btnReply.setVisibility(View.GONE);

        } else {
            tvReply.setVisibility(View.GONE);
            llBottom.setVisibility(View.GONE);
            btnReply.setVisibility(View.VISIBLE);

            btnReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    llBottom.setVisibility(View.VISIBLE);
                    btnReply.setVisibility(View.GONE);
                }
            });

            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!etMessage.getText().toString().equalsIgnoreCase("")) {
                        suggestionReplyCallback.onReply(groupPosition, childPosition, etMessage.getText().toString());
                    } else {
                        DialogUtils.createConfirmDialog((DashboardActivity) _context, "Please enter message", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }

                        }).show();
                    }
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    llBottom.setVisibility(View.GONE);
                    etMessage.getText().clear();
                    btnReply.setVisibility(View.VISIBLE);
                }
            });

        }


        return convertView;
    }


    @Override
    public List<SuggestionDataModel.FinalArray> getChild(int groupPosition, int childPosititon) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition));
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
            convertView = infalInflater.inflate(R.layout.list_item_header_suggestion, null);
        }

        TextView tvHeader, tvDate, tvSDate;
        View viewChange;

        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(Calendar.getInstance().getTime());
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(parseDateToddMMyyyy(headerTitle3));

        int diffYear = startCalendar.get(Calendar.YEAR) - endCalendar.get(Calendar.YEAR);
        int diffMonth = diffYear * 12 + startCalendar.get(Calendar.MONTH) - endCalendar.get(Calendar.MONTH);

        tvHeader = convertView.findViewById(R.id.sug_tvHeader);
        tvDate = convertView.findViewById(R.id.sug_tvDate);
        tvSDate = convertView.findViewById(R.id.sug_tvSuggestDate);
        viewChange = convertView.findViewById(R.id.sug_colorChange);

//        2018-11-26T12:39:13.657
        tvHeader.setText(headerTitle1);
        tvDate.setText(parseDateToddMMyyyy("dd/MM/yyyy", "dd MMM yyyy", headerTitle2));
        tvSDate.setText((diffMonth + " month ago"));

//            }
//        }

        if (isExpanded) {
            viewChange.setBackgroundColor(_context.getResources().getColor(R.color.light_blue));
        } else {
            viewChange.setBackgroundColor(_context.getResources().getColor(R.color.yellow));
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

    public String parseDateToddMMyyyy(String inputpattern, String outputpattern, String time) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputpattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputpattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public Date parseDateToddMMyyyy(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSS");

        Date date = null;

        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}




