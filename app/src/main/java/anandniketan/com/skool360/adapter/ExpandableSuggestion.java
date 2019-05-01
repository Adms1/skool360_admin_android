package anandniketan.com.skool360.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
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
import java.util.HashMap;
import java.util.List;

import anandniketan.com.skool360.Interface.SuggestionReplyCallback;
import anandniketan.com.skool360.Model.Student.SuggestionDataModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.DialogUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.activity.DashboardActivity;

public class ExpandableSuggestion extends BaseExpandableListAdapter {
    int SessionHour = 0;
    Integer SessionMinit = 0;
    String SessionDurationminit = "", SessionDurationHours = "";
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

        if (childData.get(childPosition).getSuggestiondatetime() != "") {
            tvReplyDate.setText(parseDateToddMMyyyy("yyyy-MM-dd'T'HH:mm:ss.SSSS", "dd MMM yyyy hh:mm a", childData.get(childPosition).getSuggestiondatetime()));
        }

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

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_header_suggestion, null);
        }

        TextView tvHeader, tvDate, tvSDate;
        View viewChange;

        String headerTitle1 = " ", headerTitle3 = " ", headerTitle2 = " ";

        tvHeader = convertView.findViewById(R.id.sug_tvHeader);
        tvDate = convertView.findViewById(R.id.sug_tvDate);
        tvSDate = convertView.findViewById(R.id.sug_tvSuggestDate);
        viewChange = convertView.findViewById(R.id.sug_colorChange);

        if (headerTitle.length >= 2) {
            headerTitle2 = headerTitle[1];
        }
        if (headerTitle.length >= 1) {
            headerTitle1 = headerTitle[0];
        }
        if (headerTitle.length >= 3) {
            headerTitle3 = headerTitle[2];
        }

//        Calendar startCalendar = new GregorianCalendar();
//        startCalendar.setTime(Calendar.getInstance().getTime());
//        Calendar endCalendar = new GregorianCalendar();
//        endCalendar.setTime(parseDateToddMMyyyy(headerTitle3));

//        int diffYear = startCalendar.get(Calendar.YEAR) - endCalendar.get(Calendar.YEAR);
//        int diffMonth = diffYear * 12 + startCalendar.get(Calendar.MONTH) - endCalendar.get(Calendar.MONTH);

//        2018-11-26T12:39:13.657

        String CurrentTime = "";

        Date currentTimeDate = Calendar.getInstance().getTime();
        CurrentTime = String.valueOf(currentTimeDate);

        tvHeader.setText(headerTitle1);
        tvDate.setText(parseDateToddMMyyyy("dd/MM/yyyy", "dd MMM yyyy", headerTitle2));


        if (!headerTitle3.equalsIgnoreCase("") && headerTitle3 != null && headerTitle.length >= 3) {
            String[] dateHours = headerTitle3.split("\\t");
            String inputPattern = "yyyy-MM-dd";
            String outputPattern1 = "dd MMM yyyy";
            String inputdayPattern = "dd/MM/yyyy";
            String inputtimePattern = "yyyy-MM-dd'T'hh:mm:ss.SSS";
            String outputtimePattern = "hh:mm aa";
            String inputCurrentTimePattern = "EEE MMM d HH:mm:ss zzz yyyy";

            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern1);
            SimpleDateFormat inputdayFormat = new SimpleDateFormat(inputdayPattern);
            SimpleDateFormat inputtimeFormat = new SimpleDateFormat(inputtimePattern);
            SimpleDateFormat outputtimeFormat = new SimpleDateFormat(outputtimePattern);
            SimpleDateFormat inputCurrentTimeFormat = new SimpleDateFormat(inputCurrentTimePattern);
            SimpleDateFormat outputCurrentTimeFormat = new SimpleDateFormat(outputtimePattern);

            String dateAfterString = Utils.getTodaysDate();

            Date startdateTime = null, ResponseTimeStr = null, CurrentTimeStr = null;
            String str = null, StartTimeStr = null, ConvertResponseTimeStr = null, ConvertCurrentTimeStr = null;

            try {
                startdateTime = inputFormat.parse(dateHours[0]);
                StartTimeStr = outputFormat.format(startdateTime);

                str = inputdayFormat.format(startdateTime);
                Log.i("mini", "Converted Date Today:" + StartTimeStr);

                Date dateBefore = inputdayFormat.parse(str);

                Date dateAfter = inputdayFormat.parse(dateAfterString);
                long difference = dateAfter.getTime() - dateBefore.getTime();
                String daysBetween = String.valueOf((difference / (1000 * 60 * 60 * 24)));
                tvSDate.setText(StartTimeStr);

                ResponseTimeStr = inputtimeFormat.parse(dateHours[0]);
                ConvertResponseTimeStr = outputtimeFormat.format(ResponseTimeStr);
                Log.d("Time", ConvertResponseTimeStr);
                CurrentTimeStr = inputCurrentTimeFormat.parse(CurrentTime);
                ConvertCurrentTimeStr = outputCurrentTimeFormat.format(CurrentTimeStr);
                Log.d("CurrentTimeCOnvert", ConvertCurrentTimeStr);
                calculateHours(ConvertCurrentTimeStr, ConvertResponseTimeStr);

                if (daysBetween.equals("0")) {

//                    calculateHours(ConvertCurrentTimeStr, ConvertResponseTimeStr);
                    SessionDurationHours = SessionDurationHours.replace("-", "");
                    tvSDate.setText(SessionDurationHours + " ago");
                } else if (daysBetween.equals("1")) {
                    if (SessionHour > 24) {
                        tvSDate.setText(daysBetween + " day ago");
                    } else {
                        SessionDurationHours = SessionDurationHours.replace("-", "");
                        tvSDate.setText(SessionDurationHours + " ago");
                    }
                } else {
                    tvSDate.setText(daysBetween + " days ago");
                }
                System.out.println("Number of Days between dates: " + daysBetween);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            tvSDate.setText("");
//            tvSDate.setTextColor(_context.getResources().getColor(R.color.white));
        }

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

    public void calculateHours(String time1, String time2) {
        Date date1, date2;
        int days, hours, min;
        String hourstr, minstr;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        try {

            date2 = simpleDateFormat.parse(time2);
            date1 = simpleDateFormat.parse(time1);

            long difference = date2.getTime() - date1.getTime();
            days = (int) (difference / (1000 * 60 * 60 * 24));
            hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            hours = (hours < 0 ? -hours : hours);
            SessionHour = hours;
            SessionMinit = min;
            Log.i("======= Hours", " :: " + hours + ":" + min);

            if (SessionHour > 0) {
                if (SessionHour > 1) {
                    SessionDurationHours = SessionHour + " hours";
                } else {
                    SessionDurationHours = SessionHour + " hour";
                }
            } else {
                if (SessionMinit > 1) {
                    SessionDurationHours = SessionMinit + " minutes";
                } else {
                    SessionDurationHours = SessionMinit + " minute";
                }
            }

            SessionDurationHours = SessionDurationHours.replace("-", "");

            Log.d("SessionTIme", SessionDurationHours);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}




