package anandniketan.com.skool360.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.LoginDetailStatusAdapter;
import anandniketan.com.skool360.Model.Other.FinalArraySMSDataModel;
import anandniketan.com.skool360.Model.Other.GetStaffSMSDataModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentActivityLoggingBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ActivityLoggingFragment extends Fragment {

    //Use for fill Barchart
    List<FinalArraySMSDataModel> monthNumArrayList, datewisemonthArrayList, LoginDetailArrayList;
    ArrayList<Integer> monthNumber = new ArrayList<>();
    ArrayList<String> monthNameArray = new ArrayList<>();
    String FinalSelectedMonth;
    float groupSpace, barSpace, barWidth;
    //Use for fill DateWiseBarChart
    ArrayList<String> DateArray;
    ArrayList<Integer> DateNumber;
    float groupSpace1, barSpace1, barWidth1;
    String FinalSelectedDate;
    LoginDetailStatusAdapter loginDetailStatusAdapter;
    private FragmentActivityLoggingBinding fragmentActivityLoggingBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    //Use for showLoginDetail
    private AlertDialog alertDialogAndroid = null;
    private Button close_btn;
    private LinearLayout header_linear, recycler_linear;
    private RecyclerView login_status_list;
    private TextView txtNoRecordsloginstatus;

    public ActivityLoggingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentActivityLoggingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_activity_logging, container, false);

        rootView = fragmentActivityLoggingBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        init();
        setListners();
        callMonthlyCountApi();

        return rootView;
    }


    public void init() {
        barWidth = 0.3f;
        barSpace = 0f;
        groupSpace = 0.1f;

        barWidth1 = 0.3f;
        barSpace1 = 0f;
        groupSpace1 = 0.1f;
    }

    public void setListners() {

        fragmentActivityLoggingBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentActivityLoggingBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new OtherFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentActivityLoggingBinding.barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String x = fragmentActivityLoggingBinding.barChart.getXAxis().getValueFormatter().getFormattedValue(e.getX(), fragmentActivityLoggingBinding.barChart.getXAxis());
                Log.d("clickValue", x);
                Log.d("secondValue", "" + e.getY());
//                if(!x.equalsIgnoreCase("")) {
                Calendar cal = Calendar.getInstance();
                try {
                    cal.setTime(new SimpleDateFormat("MMM").parse(x));
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                int monthInt = cal.get(Calendar.MONTH) + 1;
                Log.d("Selectedmonth", "" + monthInt);
                Utils.ping(mContext, "Select Month :" + monthInt);
                FinalSelectedMonth = String.valueOf(monthInt);
                if (!FinalSelectedMonth.equalsIgnoreCase("")) {
                    callDateCountPerMonthApi();
                } else {
                    Utils.ping(mContext, "Please Select value");
                }

            }

            @Override
            public void onNothingSelected() {

            }
        });

        fragmentActivityLoggingBinding.barChartDatewise.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String date = fragmentActivityLoggingBinding.barChartDatewise.getXAxis().getValueFormatter().getFormattedValue(e.getX(), fragmentActivityLoggingBinding.barChartDatewise.getXAxis());
                Log.d("SelectedDate", date);
                if (!date.equalsIgnoreCase("")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat output = new SimpleDateFormat("MM/dd/yyyy");
                    Date d = null;
                    try {
                        d = sdf.parse(date);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    String formatedate = output.format(d);

                    FinalSelectedDate = formatedate;
                    if (!FinalSelectedDate.equalsIgnoreCase("")) {
                        callLoginDetailsDatewiseApi();
                    } else {
                        Utils.ping(mContext, "Please Select Date");
                    }
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        fragmentActivityLoggingBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentActivityLoggingBinding.listHeader1.setVisibility(View.GONE);
                fragmentActivityLoggingBinding.back.setVisibility(View.GONE);
                fragmentActivityLoggingBinding.listHeader.setVisibility(View.VISIBLE);

            }
        });
    }

    // CALL MonthlyCount API HERE
    private void callMonthlyCountApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getMonthlyCount(getMonthlyCountDetail(), new retrofit.Callback<GetStaffSMSDataModel>() {
            @Override
            public void success(GetStaffSMSDataModel monthlyCount, Response response) {
                Utils.dismissDialog();
                if (monthlyCount == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (monthlyCount.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (monthlyCount.getSuccess().equalsIgnoreCase("false")) {
                    fragmentActivityLoggingBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentActivityLoggingBinding.listHeader.setVisibility(View.GONE);
                    Utils.dismissDialog();
                    return;
                }
                if (monthlyCount.getSuccess().equalsIgnoreCase("True")) {
                    fragmentActivityLoggingBinding.totalStudentTxt.setText(String.valueOf(monthlyCount.getTotalStudentCount()));
                    fragmentActivityLoggingBinding.activatedStudentTxt.setText(String.valueOf(monthlyCount.getActiveStudentCount()));
                    fragmentActivityLoggingBinding.totalTeacherTxt.setText(String.valueOf(monthlyCount.getTotalTeacherCount()));
                    fragmentActivityLoggingBinding.activatedTeacherTxt.setText(String.valueOf(monthlyCount.getActiveTeacherCount()));
                    fragmentActivityLoggingBinding.totalAdminTxt.setText(String.valueOf(monthlyCount.getTotalAdminCount()));
                    fragmentActivityLoggingBinding.activatedAdminTxt.setText(String.valueOf(monthlyCount.getActiveadminCount()));

                    monthNumArrayList = monthlyCount.getFinalArray();
                    if (monthlyCount.getFinalArray().size() > 0) {
                        fragmentActivityLoggingBinding.listHeader1.setVisibility(View.GONE);
                        fragmentActivityLoggingBinding.back.setVisibility(View.GONE);
                        fragmentActivityLoggingBinding.listHeader.setVisibility(View.VISIBLE);
                        fillBarChartArray();
                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getMonthlyCountDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    // CALL DateCountPerMonth API HERE
    private void callDateCountPerMonthApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getDateCountPerMonth(getDateCountPerMonthDetail(), new retrofit.Callback<GetStaffSMSDataModel>() {
            @Override
            public void success(GetStaffSMSDataModel datePerMonthCount, Response response) {
                Utils.dismissDialog();
                if (datePerMonthCount == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (datePerMonthCount.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (datePerMonthCount.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, "No Record Found");
                    Utils.dismissDialog();
                    return;
                }
                if (datePerMonthCount.getSuccess().equalsIgnoreCase("True")) {
                    datewisemonthArrayList = datePerMonthCount.getFinalArray();
                    if (datePerMonthCount.getFinalArray().size() > 0) {
                        fragmentActivityLoggingBinding.listHeader1.setVisibility(View.VISIBLE);
                        fragmentActivityLoggingBinding.back.setVisibility(View.VISIBLE);
                        fragmentActivityLoggingBinding.listHeader.setVisibility(View.GONE);
                        fillBarChartArrayDateWise();
                    } else {
                        Utils.ping(mContext, "No Record Found");
                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getDateCountPerMonthDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Month", FinalSelectedMonth);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    // CALL LoginDetailsDatewise API HERE
    private void callLoginDetailsDatewiseApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getLoginDetailsDatewise(getLoginDetailsDatewise(), new retrofit.Callback<GetStaffSMSDataModel>() {
            @Override
            public void success(GetStaffSMSDataModel loginDetailModel, Response response) {
                Utils.dismissDialog();
                if (loginDetailModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (loginDetailModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (loginDetailModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, "No Record found");
                    Utils.dismissDialog();
                    return;
                }
                if (loginDetailModel.getSuccess().equalsIgnoreCase("True")) {
                    LoginDetailArrayList = loginDetailModel.getFinalArray();
                    if (loginDetailModel.getFinalArray().size() > 0) {
                        fragmentActivityLoggingBinding.listHeader1.setVisibility(View.VISIBLE);
                        fragmentActivityLoggingBinding.back.setVisibility(View.VISIBLE);
                        fragmentActivityLoggingBinding.listHeader.setVisibility(View.GONE);
                        ShowLoginDetail();
                    } else {
                        Utils.ping(mContext, "No Record Found");
                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getLoginDetailsDatewise() {
        Map<String, String> map = new HashMap<>();
        map.put("date", FinalSelectedDate);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    public void fillBarChartArray() {
        HashMap<Integer, Integer> student = new HashMap<>();
        HashMap<Integer, Integer> teacher = new HashMap<>();
        HashMap<Integer, Integer> admin = new HashMap<>();

        for (int i = 0; i < monthNumArrayList.size(); i++) {
            monthNumber.add(monthNumArrayList.get(i).getMonth());
            if (monthNumArrayList.get(i).getType().equalsIgnoreCase("Student")) {
                student.put(monthNumArrayList.get(i).getMonth(), monthNumArrayList.get(i).getCount());
            } else if (monthNumArrayList.get(i).getType().equalsIgnoreCase("Teacher")) {
                teacher.put(monthNumArrayList.get(i).getMonth(), monthNumArrayList.get(i).getCount());
            } else if (monthNumArrayList.get(i).getType().equalsIgnoreCase("Admin")) {
                admin.put(monthNumArrayList.get(i).getMonth(), monthNumArrayList.get(i).getCount());
            }
        }
        HashSet hs = new HashSet();
        hs.addAll(monthNumber);
        monthNumber.clear();
        monthNumber.addAll(hs);
        Log.d("marks", "" + monthNumber);
        Collections.sort(monthNumber);
        for (int j = 0; j < monthNumber.size(); j++) {
            getMonthFun(monthNumber.get(j));
        }
        Map<Integer, Integer> treeMap = new TreeMap<Integer, Integer>(student);
        Map<Integer, Integer> teacherMap = new TreeMap<Integer, Integer>(teacher);
        Map<Integer, Integer> adminMap = new TreeMap<Integer, Integer>(admin);

        fillBarChartValueNew(treeMap, teacherMap, adminMap);
    }

    public void getMonthFun(int month) {
        SimpleDateFormat monthParse = new SimpleDateFormat("MM");
        SimpleDateFormat monthDisplay = new SimpleDateFormat("MMM");
        try {
            monthNameArray.add(monthDisplay.format(monthParse.parse(String.valueOf(month))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("month", "" + monthNameArray);
    }

    private void fillBarChartValueNew(Map<Integer, Integer> treeMap, Map<Integer, Integer> teacherMap, Map<Integer, Integer> adminMap) {
        fragmentActivityLoggingBinding.barChart.setDescription(null);
        fragmentActivityLoggingBinding.barChart.setPinchZoom(false);
        fragmentActivityLoggingBinding.barChart.setScaleEnabled(true);
        fragmentActivityLoggingBinding.barChart.setDrawBarShadow(false);
        fragmentActivityLoggingBinding.barChart.setDrawGridBackground(false);
//        fragmentActivityLoggingBinding.barChart.animateY(3000);

        int groupCount = 12;

        final ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < monthNameArray.size(); i++) {
            xVals.add(monthNameArray.get(i));
        }
        Log.d("x-Axis", "" + xVals);

        ArrayList<Integer> yVals1 = new ArrayList<Integer>();
        ArrayList<Integer> yVals2 = new ArrayList<Integer>();
        ArrayList<Integer> yVals3 = new ArrayList<Integer>();

        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> entries2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> entries3 = new ArrayList<BarEntry>();

        for (Map.Entry<Integer, Integer> entry : treeMap.entrySet()) {
            yVals1.add(entry.getValue());
        }

        for (Map.Entry<Integer, Integer> entry : teacherMap.entrySet()) {
            yVals2.add(entry.getValue());
        }

        for (Map.Entry<Integer, Integer> entry : adminMap.entrySet()) {
            yVals3.add(entry.getValue());
        }

        for (int i = 0; i < monthNumber.size(); i++) {
            entries1.add(new BarEntry(monthNumber.get(i), yVals1.get(i)));
            entries2.add(new BarEntry(monthNumber.get(i), yVals2.get(i)));
            entries3.add(new BarEntry(monthNumber.get(i), yVals3.get(i)));
        }

        //draw the graph
        BarDataSet set1, set2, set3;
        set1 = new BarDataSet(entries1, "Student");
        set1.setColor(getResources().getColor(R.color.darkblue));
        set2 = new BarDataSet(entries2, "Teacher");
        set2.setColor(getResources().getColor(R.color.orange));
        set3 = new BarDataSet(entries3, "Admin");
        set3.setColor(getResources().getColor(R.color.yellow));

        BarData data = new BarData(set1, set2, set3);//
        data.setValueFormatter(new LargeValueFormatter());
        fragmentActivityLoggingBinding.barChart.setData(data);
        Log.d("getBarData", "" + fragmentActivityLoggingBinding.barChart.getBarData());

        fragmentActivityLoggingBinding.barChart.zoom(1.4f, 0f, 0f, 0f);
        fragmentActivityLoggingBinding.barChart.getBarData().setBarWidth(barWidth);
        fragmentActivityLoggingBinding.barChart.getXAxis().setAxisMinimum(0);
//        fragmentActivityLoggingBinding.barChart.getXAxis().setAxisMaximum(0 + fragmentActivityLoggingBinding.barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        fragmentActivityLoggingBinding.barChart.groupBars(0, groupSpace, barSpace);
        fragmentActivityLoggingBinding.barChart.invalidate();
        Log.d("value", "" + fragmentActivityLoggingBinding.barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);

        //Draw the indicator
        Legend l = fragmentActivityLoggingBinding.barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);


        //Draw the X-Axis and Y-Axis
        //X-axis
        final XAxis xAxis = fragmentActivityLoggingBinding.barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(true);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
        xAxis.setDrawAxisLine(true);

        //Y-axis
        fragmentActivityLoggingBinding.barChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = fragmentActivityLoggingBinding.barChart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(5f);
        leftAxis.setAxisMinimum(0f);


    }

    public void fillBarChartArrayDateWise() {
        DateNumber = new ArrayList<>();
        DateArray = new ArrayList<>();
        fragmentActivityLoggingBinding.barChartDatewise.zoom(0f, 0f, 0f, 0f);
        for (int i = 0; i < datewisemonthArrayList.size(); i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
            Date d = null;
            try {
                d = sdf.parse(datewisemonthArrayList.get(i).getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String formatedate = output.format(d);
            datewisemonthArrayList.get(i).setDate(formatedate);
        }

        HashMap<String, Integer> datewisestudent = new HashMap<>();
        HashMap<String, Integer> datewiseteacher = new HashMap<>();
        HashMap<String, Integer> datewiseadmin = new HashMap<>();

        for (int i = 0; i < datewisemonthArrayList.size(); i++) {
            DateNumber.add(i);
            DateArray.add(datewisemonthArrayList.get(i).getDate());
            datewisestudent.put(datewisemonthArrayList.get(i).getDate(), datewisemonthArrayList.get(i).getStudentCount());
            datewiseteacher.put(datewisemonthArrayList.get(i).getDate(), datewisemonthArrayList.get(i).getTeacherCount());
            datewiseadmin.put(datewisemonthArrayList.get(i).getDate(), datewisemonthArrayList.get(i).getAdminCount());

        }
        Map<String, Integer> datewisetreeMap = new TreeMap<String, Integer>(datewisestudent);
        Map<String, Integer> datewiseteacherMap = new TreeMap<String, Integer>(datewiseteacher);
        Map<String, Integer> datewiseadminMap = new TreeMap<String, Integer>(datewiseadmin);

        fillBarChartValueDateWise(datewisetreeMap, datewiseteacherMap, datewiseadminMap);

    }

    private void fillBarChartValueDateWise(Map<String, Integer> datewisetreeMap, Map<String, Integer> datewiseteacherMap, Map<String, Integer> datewiseadminMap) {
        fragmentActivityLoggingBinding.barChartDatewise.setDescription(null);
        fragmentActivityLoggingBinding.barChartDatewise.setPinchZoom(false);
        fragmentActivityLoggingBinding.barChartDatewise.setScaleEnabled(true);
        fragmentActivityLoggingBinding.barChartDatewise.setDrawBarShadow(false);
        fragmentActivityLoggingBinding.barChartDatewise.setDrawGridBackground(false);
//        fragmentActivityLoggingBinding.barChartDatewise.animateY(3000);

        int groupCount1;

        final ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < DateArray.size(); i++) {
            xVals.add(DateArray.get(i));
        }
        Log.d("x-Axis", "" + xVals);

        groupCount1 = DateNumber.size();
        ArrayList<Integer> yVals1 = new ArrayList<Integer>();
        ArrayList<Integer> yVals2 = new ArrayList<Integer>();
        ArrayList<Integer> yVals3 = new ArrayList<Integer>();

        ArrayList<BarEntry> entriesDatewise1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> entriesDatewise2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> entriesDatewise3 = new ArrayList<BarEntry>();

        for (Map.Entry<String, Integer> entry : datewisetreeMap.entrySet()) {
            yVals1.add(entry.getValue());
        }

        for (Map.Entry<String, Integer> entry : datewiseteacherMap.entrySet()) {
            yVals2.add(entry.getValue());
        }

        for (Map.Entry<String, Integer> entry : datewiseadminMap.entrySet()) {
            yVals3.add(entry.getValue());
        }

        for (int i = 0; i < DateNumber.size(); i++) {
            entriesDatewise1.add(new BarEntry(DateNumber.get(i), yVals1.get(i)));
            entriesDatewise2.add(new BarEntry(DateNumber.get(i), yVals2.get(i)));
            entriesDatewise3.add(new BarEntry(DateNumber.get(i), yVals3.get(i)));
        }

        //draw the graph
        BarDataSet set1, set2, set3;
        set1 = new BarDataSet(entriesDatewise1, "Student");
        set1.setColor(getResources().getColor(R.color.darkblue));
        set2 = new BarDataSet(entriesDatewise2, "Teacher");
        set2.setColor(getResources().getColor(R.color.orange));
        set3 = new BarDataSet(entriesDatewise3, "Admin");
        set3.setColor(getResources().getColor(R.color.yellow));

        BarData data = new BarData(set1, set2, set3);//
        data.setValueFormatter(new LargeValueFormatter());
        fragmentActivityLoggingBinding.barChartDatewise.setData(data);
        Log.d("getBarData", "" + fragmentActivityLoggingBinding.barChartDatewise.getBarData());

        fragmentActivityLoggingBinding.barChartDatewise.zoom(5f, 0f, 0f, 0f);
        fragmentActivityLoggingBinding.barChartDatewise.getBarData().setBarWidth(barWidth1);
        fragmentActivityLoggingBinding.barChartDatewise.getXAxis().setAxisMinimum(0);
//        fragmentActivityLoggingBinding.barChartDatewise.getXAxis().setAxisMaximum(0 + fragmentActivityLoggingBinding.barChartDatewise.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        fragmentActivityLoggingBinding.barChartDatewise.groupBars(0, groupSpace1, barSpace1);
        fragmentActivityLoggingBinding.barChartDatewise.invalidate();
        Log.d("value", "" + fragmentActivityLoggingBinding.barChartDatewise.getBarData().getGroupWidth(groupSpace1, barSpace1) * groupCount1);

        //Draw the indicator
        Legend l = fragmentActivityLoggingBinding.barChartDatewise.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);


        //Draw the X-Axis and Y-Axis
        //X-axis
        final XAxis xAxis = fragmentActivityLoggingBinding.barChartDatewise.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(true);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));


        //Y-axis
        fragmentActivityLoggingBinding.barChartDatewise.getAxisRight().setEnabled(false);
        YAxis leftAxis = fragmentActivityLoggingBinding.barChartDatewise.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(5f);
        leftAxis.setAxisMinimum(0f);


    }

    public void ShowLoginDetail() {
        LayoutInflater lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = lInflater.inflate(R.layout.list_item_login_detail, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
        alertDialogBuilderUserInput.setView(layout);

        alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.setCancelable(false);
        alertDialogAndroid.show();
        Window window = alertDialogAndroid.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(wlp);
        alertDialogAndroid.show();

        close_btn = layout.findViewById(R.id.close_btn);
        header_linear = layout.findViewById(R.id.header_linear);
        login_status_list = layout.findViewById(R.id.login_status_list);
        txtNoRecordsloginstatus = layout.findViewById(R.id.txtNoRecordsloginstatus);
        recycler_linear = layout.findViewById(R.id.recycler_linear);
        FillLoginDetailList();

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogAndroid.dismiss();
            }
        });

    }

    public void FillLoginDetailList() {
        recycler_linear.setVisibility(View.VISIBLE);
        header_linear.setVisibility(View.VISIBLE);
        for (int i = 0; i < LoginDetailArrayList.size(); i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
            Date d = null;
            try {
                d = sdf.parse(LoginDetailArrayList.get(i).getLoginDetails());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String formatedate = output.format(d);
            LoginDetailArrayList.get(i).setLoginDetails(formatedate);
        }
        loginDetailStatusAdapter = new LoginDetailStatusAdapter(mContext, LoginDetailArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        login_status_list.setLayoutManager(mLayoutManager);
        login_status_list.setItemAnimator(new DefaultItemAnimator());
        login_status_list.setAdapter(loginDetailStatusAdapter);
    }
}

