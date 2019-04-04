package anandniketan.com.skool360.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import anandniketan.com.skool360.Model.HR.EmployeeInOutSummaryModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.DateUtils;
import anandniketan.com.skool360.calendarview.CalendarListener;
import anandniketan.com.skool360.calendarview.CustomCalendarView;

public class ExapndableInOutSummaryAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<EmployeeInOutSummaryModel.FinalArray>> listChildData;
    private String standard = "", standardIDS = "";
    private int annousID;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
//    private String viewstatus;

    public ExapndableInOutSummaryAdapter(Context context, List<String> listDataHeader, HashMap<String, ArrayList<EmployeeInOutSummaryModel.FinalArray>> listDataChild) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.listChildData = listDataChild;
//        this.viewstatus = viewstatus;

    }

    @Override
    public List<EmployeeInOutSummaryModel.FinalArray> getChild(int groupPosition, int childPosititon) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final List<EmployeeInOutSummaryModel.FinalArray> childData = getChild(groupPosition, 0);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_child_in_out_summary, null);
        }
        final CustomCalendarView customCalendarView = convertView.findViewById(R.id.calendar_view);

        customCalendarView.setShowOverflowDate(false);

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(calendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, Integer.parseInt(AppConfiguration.year));
        calendar.set(Calendar.MONTH, Integer.parseInt(AppConfiguration.month) - 1);

        calendar.setFirstDayOfWeek(DateUtils.getFirstDayOfMonth(1, Integer.parseInt(AppConfiguration.month), Integer.parseInt(AppConfiguration.year)));

        customCalendarView.refreshCalendar(calendar);

        customCalendarView.setDataInCalendar(DateUtils.getFirstDayOfMonth(1, Integer.parseInt(AppConfiguration.month) - 1, Integer.parseInt(AppConfiguration.year)), _context, childData);

        customCalendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {

            }

            @Override
            public void onMonthChanged(Date time) {

            }
        });
        //  customCalendarView.refreshCalendar(currentCalendar);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        if (viewstatus.equalsIgnoreCase("true")) {
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String[] headerTitle = getGroup(groupPosition).toString().split("\\|");

        String headerTitle1 = headerTitle[0];
        String headerTitle2 = headerTitle[1];

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_in_out_summary_header, null);
        }
        TextView txt_index, txt_teachername, txt_dept;
        ImageView iv_indicator;

        txt_index = convertView.findViewById(R.id.index_txt);
        txt_teachername = convertView.findViewById(R.id.teachername_txt);
        txt_dept = convertView.findViewById(R.id.dept_txt);
        iv_indicator = convertView.findViewById(R.id.iv_indicator);

        String index = String.valueOf(groupPosition + 1);

        txt_index.setText(index);
        txt_teachername.setText(headerTitle1);
        txt_dept.setText(headerTitle2);

//        if (viewstatus.equalsIgnoreCase("true")) {
            if (isExpanded) {
                iv_indicator.setImageResource(R.drawable.arrow_1_42_down);
            } else {
                iv_indicator.setImageResource(R.drawable.arrow_1_42);
            }
//        }
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




