package anandniketan.com.skool360.Fragment.Fragment;


import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Model.MIS.MISClassWiseResultModel;
import anandniketan.com.skool360.Model.MIS.RangeChartModel;
import anandniketan.com.skool360.Model.MIS.TopperChartModel;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiClient;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.Utility.WebServices;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends Fragment {

    private List<FinalArrayGetTermModel> finalArrayGetTermModels;
    private HashMap<Integer, String> spinnerTermMap;

    private Spinner spTermDetail, spTerm;
    private BarChart chart;
    private HashMap<Integer, String> spinnerSchoolResultMap;
    private String FinalSchoolResultTermID = "1";
    private TextView noRecords, tvHeader;
    private String FinalTermIdStr;
    private ImageView btnBack, btnBack1;
    private Button btnMenu;
    private PieChart piechart;
    private Legend l;
    private ScrollView scrollView;
    private Handler timerHandler1, timerHandler;

    public ChartFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        return inflater.inflate(R.layout.fragment_chart, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppConfiguration.position = 58;
        AppConfiguration.firsttimeback = true;

//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            chartType = bundle.getString("charttype");
//        }

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.chart_btnBack);
        btnBack1 = view.findViewById(R.id.chart_btnBack1);
        btnMenu = view.findViewById(R.id.btnmenu);

        spTermDetail = view.findViewById(R.id.chart_spTermdetail);
        spTerm = view.findViewById(R.id.chart_spTerm);
        chart = view.findViewById(R.id.chart_topperchart);
        noRecords = view.findViewById(R.id.chart_tv_no_records);
        piechart = view.findViewById(R.id.chart_rangechart);
        scrollView = view.findViewById(R.id.chart_scrollview);

        chart.setNoDataText("");
        piechart.setNoDataText("");

        timerHandler = new Handler();
        timerHandler1 = new Handler();

//        if (chartType.equalsIgnoreCase("topper")) {

        tvHeader.setText("Class Topper Result");

//            spTermDetail.setVisibility(View.VISIBLE);
//            spTerm.setVisibility(View.GONE);

//            piechart.setVisibility(View.GONE);
//            chart.setVisibility(View.VISIBLE);

        chart.getDescription().setEnabled(false);
        chart.setPinchZoom(false);
        chart.setDescription(null);
        chart.setNoDataText("No data to display");
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        fillSchoolResultTermSpinner();

        Legend ll = chart.getLegend();
        ll.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        ll.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        ll.setOrientation(Legend.LegendOrientation.VERTICAL);
        ll.setDrawInside(false);
        ll.setYOffset(5f);
        ll.setXOffset(10f);
        ll.setYEntrySpace(10f);
        ll.setTextSize(10f);

        callTopperListChartApi();

//        } else if (chartType.equalsIgnoreCase("range")) {

        tvHeader.setText("Range Wise Student Result");

//            spTermDetail.setVisibility(View.GONE);
//            spTerm.setVisibility(View.VISIBLE);

//            piechart.setVisibility(View.VISIBLE);
//            chart.setVisibility(View.GONE);

        piechart.setUsePercentValues(false);
        piechart.getDescription().setEnabled(false);
//            piechart.setExtraOffsets(5, 10, 5, 5);

//            piechart.setDragDecelerationFrictionCoef(0.95f);

        piechart.setDrawHoleEnabled(false);
//            piechart.setHoleColor(Color.WHITE);

//            piechart.setTransparentCircleColor(Color.WHITE);
//            piechart.setTransparentCircleAlpha(110);

//            piechart.setHoleRadius(58f);
//            piechart.setTransparentCircleRadius(61f);

//            piechart.setDrawCenterText(true);

        piechart.setRotationAngle(0);
        // enable rotation of the chart by touch
//            piechart.setRotationEnabled(true);
        piechart.setHighlightPerTapEnabled(true);

        l = piechart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        callTermApi();

        // entry label styling
        piechart.setEntryLabelColor(Color.WHITE);
        piechart.setEntryLabelTextSize(12f);
//        }

        setListener();
    }

    private void setListener() {

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppConfiguration.position = 58;
                AppConfiguration.firsttimeback = true;

                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });

        btnBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppConfiguration.position = 58;
                AppConfiguration.firsttimeback = true;

                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });


//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch(event.getAction()){
//                    case MotionEvent.ACTION_DOWN:{
//                        scrollView.fullScroll(scrollView.FOCUS_DOWN);         // 5 is how many pixels you want it to scroll vertically by
//                        return true;
//                    }
//                    case MotionEvent.ACTION_UP:{
//                        scrollView.fullScroll(scrollView.FOCUS_UP);         // 5 is how many pixels you want it to scroll vertically by
//                        return true;
//                    }
//                }
//
//                return false;
//            }
//        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY > oldScrollY) {

                        scrollView.setSmoothScrollingEnabled(true);
                        //                    scrollView.smoothScrollBy(0,v.getBottom());
                        scrollView.fullScroll(View.FOCUS_DOWN);

                    } else {

                        scrollView.setSmoothScrollingEnabled(true);
                        //                    scrollView.smoothScrollBy(0,v.getTop());
                        scrollView.fullScroll(View.FOCUS_UP);

                    }
                }
            });
        }

        spTermDetail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = spTermDetail.getSelectedItem().toString();
                String getid = spinnerSchoolResultMap.get(spTermDetail.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalSchoolResultTermID = getid;
                AppConfiguration.schoolResultTermID = FinalSchoolResultTermID;

                Log.d("FinalTermIdStr", FinalSchoolResultTermID);

                try {
//                    if (chartType.equalsIgnoreCase("topper")) {
                    callTopperListChartApi();
//                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spTerm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = spTerm.getSelectedItem().toString();
                String getid = spinnerTermMap.get(spTerm.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid;
                Log.d("FinalTermIdStr", FinalTermIdStr);

//                if (chartType.equalsIgnoreCase("range")) {
                callRangeChartApi();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //     CALL Term API HERE
    private void callTermApi() {

        if (!Utils.checkNetwork(Objects.requireNonNull(getActivity()))) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());

        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        Call<TermModel> call = apiService.getTerm();
        call.enqueue(new Callback<TermModel>() {

            @Override
            public void onResponse(@NonNull Call<TermModel> call, @NonNull retrofit2.Response<TermModel> response) {
//                Utils.dismissDialog();
                if (response.body() == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (response.body().getSuccess() == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (response.body().getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(getActivity(), getString(R.string.false_msg));
                    return;
                }
                if (response.body().getSuccess().equalsIgnoreCase("True")) {
                    finalArrayGetTermModels = response.body().getFinalArray();
                    if (finalArrayGetTermModels != null) {
                        fillTermSpinner();

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TermModel> call, @NonNull Throwable t) {
//                Utils.dismissDialog();
                t.printStackTrace();
                t.getMessage();
                Utils.ping(getActivity(), getString(R.string.something_wrong));
            }
        });
    }

    @SuppressLint("UseSparseArrays")
    public void fillTermSpinner() {
        ArrayList<Integer> TermId = new ArrayList<>();
        for (int i = 0; i < finalArrayGetTermModels.size(); i++) {
            TermId.add(finalArrayGetTermModels.get(i).getTermId());
        }
        ArrayList<String> Term = new ArrayList<>();
        for (int j = 0; j < finalArrayGetTermModels.size(); j++) {
            Term.add(finalArrayGetTermModels.get(j).getTerm());
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerTermMap = new HashMap<>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerTermMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_layout, spinnertermIdArray);
        spTerm.setAdapter(adapterTerm);
        spTerm.setSelection(1);

    }

    @SuppressLint("UseSparseArrays")
    public void fillSchoolResultTermSpinner() {

        ArrayList<Integer> TermId = new ArrayList<>();

        TermId.add(1);
        TermId.add(2);

        ArrayList<String> Term = new ArrayList<>();

        Term.add("Term 1");
        Term.add("Term 2");

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerSchoolResultMap = new HashMap<>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerSchoolResultMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()), R.layout.spinner_layout, spinnertermIdArray) {

            @NonNull
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.CENTER_HORIZONTAL);

                return v;

            }

            @SuppressLint("RtlHardcoded")
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {

                View v = super.getDropDownView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.LEFT);

                return v;

            }

        };

        spTermDetail.setAdapter(adapter);
        FinalSchoolResultTermID = spinnerSchoolResultMap.get(0);
        AppConfiguration.schoolResultTermID = FinalSchoolResultTermID;
        spTermDetail.setSelection(0, false);

    }

    private void callTopperListChartApi() {

        if (!Utils.checkNetwork(Objects.requireNonNull(getActivity()))) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());

        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        Call<TopperChartModel> call = apiService.getTopperChart(FinalSchoolResultTermID, PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));

        call.enqueue(new Callback<TopperChartModel>() {

            @Override
            public void onResponse(@NonNull Call<TopperChartModel> call, @NonNull final retrofit2.Response<TopperChartModel> response) {

                if (response.body() != null) {

                    if (response.body().getSuccess().equalsIgnoreCase("false")) {
                        noRecords.setVisibility(View.VISIBLE);
                        Utils.dismissDialog();
                        chart.setVisibility(View.GONE);
//                        llHeader.setVisibility(View.GONE);
                        return;
                    }

                    if (response.body().getSuccess().equalsIgnoreCase("True")) {
                        noRecords.setVisibility(View.GONE);

                        if (response.body().getFinalarray() != null) {

                            chart.setVisibility(View.VISIBLE);

                            float groupSpace = 0.6f;
                            float barSpace = 0f; // x4 DataSet
                            float barWidth = 0.2f; // x4 DataSet

                            int groupCount = response.body().getFinalarray().get(1).getData().size();

                            ArrayList<String> xVals = new ArrayList<>();
                            final ArrayList<String> yVals = new ArrayList<>();

                            for (int i = 0; i < groupCount; i++) {
                                String newstr = response.body().getFinalarray().get(1).getData().get(i).getStandard() + "-" + response.body().getFinalarray().get(1).getData().get(i).getClassName();
                                xVals.add(newstr);
                            }

                            for (int i = 0; i < response.body().getGradedata().size(); i++) {

                                yVals.add(response.body().getGradedata().get(i).getGrade());

                            }

                            yVals.add("0");
                            Collections.reverse(yVals);
//                            Collections.reverse(xVals);

                            //X-axis
                            XAxis xAxis = chart.getXAxis();
                            xAxis.setGranularity(1f);
                            xAxis.setGranularityEnabled(true);
                            xAxis.setCenterAxisLabels(true);
                            xAxis.setDrawGridLines(false);
                            xAxis.setAxisMinimum(0f);
//                            xAxis.setLabelRotationAngle(90);
                            xAxis.setAxisMaximum(groupCount);
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

                            xAxis.setLabelCount(groupCount, false);

                            //Y-axis
                            YAxis leftAxis = chart.getAxisLeft();
                            leftAxis.setGranularityEnabled(true);
                            leftAxis.setDrawGridLines(true);
                            leftAxis.setAxisMinimum(0f);
                            leftAxis.setValueFormatter(new IndexAxisValueFormatter(yVals));
//
                            chart.getAxisRight().setEnabled(false);
                            chart.getAxisLeft().setAxisMinimum(0f);
                            leftAxis.setLabelCount(yVals.size(), false);

                            final ArrayList<MISClassWiseResultModel.FinalArray> values1 = new ArrayList<>(response.body().getFinalarray().get(1).getData());

                            final ArrayList<MISClassWiseResultModel.FinalArray> values2 = new ArrayList<>(response.body().getFinalarray().get(2).getData());

                            ArrayList<BarEntry> yVals1 = new ArrayList<>();
                            ArrayList<BarEntry> yVals2 = new ArrayList<>();

                            // fill the lists
                            for (int i = 0; i < values1.size(); i++) {
                                yVals1.add(new BarEntry((i + 1), Float.valueOf(values1.get(i).getGrade())));
                                yVals2.add(new BarEntry((i + 1), Float.valueOf(values2.get(i).getGrade())));
                            }

                            BarDataSet set1, set2;

                            set1 = new BarDataSet(yVals1, response.body().getFinalarray().get(1).getTerm());
                            set1.setColor(Color.rgb(192, 80, 77));
                            set2 = new BarDataSet(yVals2, response.body().getFinalarray().get(2).getTerm());
                            set2.setColor(Color.rgb(79, 129, 189));

                            final BarData data = new BarData(set1, set2);
                            set1.setValueFormatter(new IValueFormatter() {
                                @Override
                                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

                                    return "";

//                                    if (values1.size() > 0) {
//                                        for (int i = 0; i < values1.size(); i++) {
//                                            val1 = values1.get(i).getName();
//                                        }
//                                    } else {
//                                        return val1;
//                                    }
//
//                                    return val1;
                                }
                            });

                            set2.setValueFormatter(new IValueFormatter() {
                                @Override
                                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

                                    String val2 = "";

                                    if (values2.size() > 0) {
                                        for (int i = 0; i < values2.size(); i++) {
                                            val2 = values2.get(i).getName();
                                        }
                                    } else {
                                        return val2;
                                    }

                                    return val2;
                                }
                            });

                            chart.setData(data);
                            chart.animateY(2500);

                            chart.fitScreen();
                            chart.getBarData().setBarWidth(barWidth);
                            chart.getXAxis().setAxisMinimum(0);
                            chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
                            chart.setVisibleXRange(0, 7);
                            chart.groupBars(0, groupSpace, barSpace);
                            chart.getData().setHighlightEnabled(false);
//                            chart.setNoDataText("");
                            chart.invalidate();

//                            Utils.dismissDialog();
                        }
                    }
                } else {
                    Utils.dismissDialog();
                    chart.setVisibility(View.GONE);
                    noRecords.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopperChartModel> call, @NonNull Throwable t) {
                // Log error here since request failed
                Utils.dismissDialog();
                noRecords.setVisibility(View.VISIBLE);
                chart.setVisibility(View.GONE);
                Log.e("gallery", t.toString());

//                callRangeChartApi();
            }
        });
    }

    private void callRangeChartApi() {

        if (!Utils.checkNetwork(Objects.requireNonNull(getActivity()))) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        Call<RangeChartModel> call = apiService.getRangeChart(FinalTermIdStr, PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        call.enqueue(new Callback<RangeChartModel>() {

            @Override
            public void onResponse(@NonNull Call<RangeChartModel> call, @NonNull retrofit2.Response<RangeChartModel> response) {

                Utils.showDialog(getActivity());
                if (response.body() != null) {

                    if (response.body().getSuccess().equalsIgnoreCase("false")) {
                        noRecords.setVisibility(View.VISIBLE);
                        piechart.setVisibility(View.GONE);
                        Utils.dismissDialog();
                        return;
                    }

                    if (response.body().getSuccess().equalsIgnoreCase("True")) {
                        noRecords.setVisibility(View.GONE);
                        Utils.dismissDialog();

                        if (response.body().getFinalarray() != null) {

                            piechart.setVisibility(View.VISIBLE);

                            ArrayList<String> xVals = new ArrayList<>();
                            ArrayList<PieEntry> entries = new ArrayList<>();

                            for (int i = 0; i < response.body().getFinalarray().size(); i++) {
                                xVals.add(response.body().getFinalarray().get(i).getRange());
                            }

                            for (int i = 0; i < response.body().getFinalarray().size(); i++) {

                                entries.add(new PieEntry(Float.valueOf(response.body().getFinalarray().get(i).getCount()), i));
                            }

                            PieDataSet dataSet = new PieDataSet(entries, "Rangewise Students");

                            dataSet.setDrawIcons(false);

                            dataSet.setSliceSpace(0f);
                            dataSet.setIconsOffset(new MPPointF(0, 40));
                            dataSet.setSelectionShift(5f);

                            ArrayList<Integer> colors = new ArrayList<>();

                            colors.add(Color.rgb(69, 114, 167));
                            colors.add(Color.rgb(170, 70, 67));
                            colors.add(Color.rgb(137, 165, 78));
                            colors.add(Color.rgb(113, 88, 143));
                            colors.add(Color.rgb(101, 172, 190));
                            colors.add(Color.rgb(219, 132, 61));
                            colors.add(Color.rgb(147, 169, 207));
                            colors.add(Color.rgb(185, 122, 87));

                            dataSet.setColors(colors);

                            List<LegendEntry> legendEntries = new ArrayList<>();

                            for (int i = 0; i < xVals.size(); i++) {
                                LegendEntry legendEntry = new LegendEntry();
                                legendEntry.formColor = colors.get(i);
                                legendEntry.label = xVals.get(i);
                                legendEntries.add(legendEntry);
                            }

                            l.setCustom(legendEntries);

                            PieData data = new PieData(dataSet);
                            data.setValueTextSize(11f);
                            data.setValueTextColor(Color.WHITE);
                            piechart.setData(data);

                            piechart.setTouchEnabled(false);

//                            piechart.setExtraOffsets(-10,-10,-10,-10);
                            // undo all highlights
                            piechart.highlightValues(null);
                            piechart.invalidate();

                        }
                    }
                } else {
                    Utils.dismissDialog();
                    piechart.setVisibility(View.GONE);
                    noRecords.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RangeChartModel> call, @NonNull Throwable t) {
                // Log error here since request failed
                Utils.dismissDialog();
                Log.e("gallery", t.toString());
            }
        });
    }
}
