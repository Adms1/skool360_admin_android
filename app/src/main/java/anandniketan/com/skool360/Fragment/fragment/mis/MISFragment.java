package anandniketan.com.skool360.Fragment.fragment.mis;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import anandniketan.com.skool360.Fragment.fragment.common.HomeFragment;
import anandniketan.com.skool360.Model.MIS.MISFinanaceModel;
import anandniketan.com.skool360.Model.MIS.MISSchoolResultModel;
import anandniketan.com.skool360.Model.MIS.MISStaffNewModel;
import anandniketan.com.skool360.Model.MIS.MISTaskReportModel;
import anandniketan.com.skool360.Model.MISModel;
import anandniketan.com.skool360.Model.MIStudentWiseCalendarModel;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiClient;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.SpacesItemDecoration;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.Utility.WebServices;
import anandniketan.com.skool360.activity.DashboardActivity;
import anandniketan.com.skool360.adapter.MISFinanceListAdapter;
import anandniketan.com.skool360.adapter.MISFinanceReportAdapter;
import anandniketan.com.skool360.adapter.MISSchoolReportAdapter;
import anandniketan.com.skool360.adapter.MISStaffGridAdapter;
import anandniketan.com.skool360.adapter.MISStaffListAdapter;
import anandniketan.com.skool360.adapter.MISTaskReportGridAdapter;
import anandniketan.com.skool360.adapter.ViewPagerAdapter;
import anandniketan.com.skool360.databinding.FragmentMis2Binding;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class MISFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    public static final String inputFormat = "HH:mm";
    private static String dateFinal = "";
    FragmentMis2Binding fragmentMisBinding;
    long specifictime, currenttime;
    SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);
    private Button btnBack, btnMenu;
    private View view;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    //FragmentMisBinding fragmentMisBinding;
    private TextView student_total_txt, student_present_txt, student_absent_txt, student_leave_txt, antstudent_txt, abstudent_txt, student_attendance_less_70_txt, staff_total_txt, staff_present_txt, absent_txt, staffleave_txt, abstaff_txt, antstaff_txt, staff_workplan_txt, staff_hw_submitted_txt, staff_cw_txt, actotaltobecall_txt, acterm1fess_txt, acterm2fess_txt, acterm1_collection, acterm2_collection, acos_txt, accashcollection_txt, acchhqdd_txt, aconine_txt, na_inquiry_txt, na_issueadd_txt, na_rcvform_txt, na_CallForInterview_txt, na_comeforinterview_txt, na_confirmaddmission_txt, na_rejected_txt, na_fees_rec_txt, na_fees_not_rec_txt, smssent_txt, smsdelivered_txt, smspedning_txt, boy_total_txt, girl_total_txt, boy_present_txt, girl_present_txt, boy_absent_txt, girl_absent_txt, boy_leave_txt, girl_leave_txt, antboy_txt, antgirl_txt, abboy_txt, abgirl_txt, boy_attendance_less_70_txt, girl_attendance_less_70_txt, student_transport_detail, ba_txt, baboy_txt, bagirl_txt;
    private List<FinalArrayGetTermModel> finalArrayGetTermModels;
    private HashMap<Integer, String> spinnerTermMap, spinnerSchoolResultMap, spinnerTermMap2, spinnerTermMap3, spinnerTermMap4;
    private String FinalTermIdStr = "", FinalSchoolResultTermID = "1", FinalFinanaceTermId = "3", FinalNATermID = "3", FinalCalendarTermID = "3";
    private Bundle bundle;
    private int whichDateClicked = 1;
    private DatePickerDialog datePickerDialog;
    private int Year, Month, Day, hour, minute, second;
    private int mYear, mMonth, mDay;
    private Calendar calendar;
    private Spinner spCalendar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ProgressBar progressBar;
    private String[] tabsTitles;
    private LinearLayout llCalendar;
    private boolean isRequireCallNestedAPI = true, isFirmtimeLoad = true;
    private List<MISStaffNewModel.FinalArray> dataValues;
    private List<MISTaskReportModel.FinalArray> taskReportdataValues;
    private List<MISSchoolResultModel.FinalArray> topperdataValues;
    private List<MISFinanaceModel.FinalArray> financedataValues;
    private ArrayList<String> dataValues1;
    private ArrayList<String> keyValues;
    private MISTaskReportGridAdapter misTaskReportGridAdapter;
    // private MISStaffListAdapter misStaffListAdapter;
    private MISStaffGridAdapter misStaffGridAdapter;
    private MISStaffListAdapter misStaffListAdapter;
    private MISSchoolReportAdapter misSchoolReportAdapter;
    private MISFinanceReportAdapter misFinanceReportAdapter;
    private MISFinanceListAdapter misFinanceListAdapter;
    private Date date;
    private Date dateCompareOne;
    private String compareStringOne = "4:00";
    private TextView tvTerm1Due, tvTerm2Due;
    private ImageView topperBarChart, rangeBarChart;

    public MISFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        fragmentMisBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mis2, container, false);
        rootView = fragmentMisBinding.getRoot();

        //rootView = fragmentMisBinding.getRoot();
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 5;

        btnBack = rootView.findViewById(R.id.btnBack);
        btnMenu = rootView.findViewById(R.id.btnmenu);
        tvTerm1Due = rootView.findViewById(R.id.term1_due_txt);
        tvTerm2Due = rootView.findViewById(R.id.term2_due_txt);

        spCalendar = rootView.findViewById(R.id.spinner_schoolcalendar);
        tabLayout = rootView.findViewById(R.id.schoolcalendar_tabLayout);
        viewPager = rootView.findViewById(R.id.schoolcalendar_viewPager);
        progressBar = rootView.findViewById(R.id.progress_schoolcalendar);
        llCalendar = rootView.findViewById(R.id.LL_schoolcalendar);
        topperBarChart = rootView.findViewById(R.id.mis_topper_bar_chart);
//        rangeBarChart = rootView.findViewById(R.id.mis_range_pie_chart);

        mContext = getActivity().getApplicationContext();

        tvTerm1Due.setVisibility(View.VISIBLE);
        tvTerm2Due.setVisibility(View.VISIBLE);

        student_transport_detail = rootView.findViewById(R.id.tv_transport);

        student_total_txt = rootView.findViewById(R.id.student_total_txt);
        student_present_txt = rootView.findViewById(R.id.student_present_txt);
        student_absent_txt = rootView.findViewById(R.id.student_absent_txt);
        student_leave_txt = rootView.findViewById(R.id.student_leave_txt);
        antstudent_txt = rootView.findViewById(R.id.antstudent_txt);
        abstudent_txt = rootView.findViewById(R.id.abstudent_txt);
        ba_txt = rootView.findViewById(R.id.ba_txt);
        student_attendance_less_70_txt = rootView.findViewById(R.id.student_attendance_less_70_txt);

        boy_total_txt = rootView.findViewById(R.id.boy_total_txt);
        boy_present_txt = rootView.findViewById(R.id.boy_present_txt);
        boy_absent_txt = rootView.findViewById(R.id.boy_absent_txt);
        boy_leave_txt = rootView.findViewById(R.id.boy_leave_txt);
        antboy_txt = rootView.findViewById(R.id.antboy_txt);
        abboy_txt = rootView.findViewById(R.id.abboy_txt);
        baboy_txt = rootView.findViewById(R.id.baboy_txt);
        boy_attendance_less_70_txt = rootView.findViewById(R.id.boy_attendance_less_70_txt);

        girl_total_txt = rootView.findViewById(R.id.girl_total_txt);
        girl_present_txt = rootView.findViewById(R.id.girl_present_txt);
        girl_absent_txt = rootView.findViewById(R.id.girl_absent_txt);
        girl_leave_txt = rootView.findViewById(R.id.girl_leave_txt);
        antgirl_txt = rootView.findViewById(R.id.antgirl_txt);
        abgirl_txt = rootView.findViewById(R.id.abgirl_txt);
        bagirl_txt = rootView.findViewById(R.id.bagirl_txt);
        girl_attendance_less_70_txt = rootView.findViewById(R.id.girl_attendance_less_70_txt);

        staff_total_txt = rootView.findViewById(R.id.staff_total_txt);
        staff_present_txt = rootView.findViewById(R.id.staff_present_txt);
        absent_txt = rootView.findViewById(R.id.absent_txt);
        staffleave_txt = rootView.findViewById(R.id.staffleave_txt);
        abstaff_txt = rootView.findViewById(R.id.abstaff_txt);
        antstaff_txt = rootView.findViewById(R.id.antstaff_txt);
        staff_workplan_txt = rootView.findViewById(R.id.staff_workplan_txt);
        staff_cw_txt = rootView.findViewById(R.id.staff_cw_txt);
        staff_hw_submitted_txt = rootView.findViewById(R.id.staff_hw_submitted_txt);
        actotaltobecall_txt = rootView.findViewById(R.id.actotaltobecall_txt);
        acterm1fess_txt = rootView.findViewById(R.id.acterm1fess_txt);
        acterm2fess_txt = rootView.findViewById(R.id.acterm2fess_txt);
        acterm1_collection = rootView.findViewById(R.id.acterm1_collection);
        acterm2_collection = rootView.findViewById(R.id.acterm2_collection);
        acos_txt = rootView.findViewById(R.id.acos_txt);
        accashcollection_txt = rootView.findViewById(R.id.accashcollection_txt);
        acchhqdd_txt = rootView.findViewById(R.id.acchhqdd_txt);
        aconine_txt = rootView.findViewById(R.id.aconline_txt);
        na_inquiry_txt = rootView.findViewById(R.id.na_inquiry_txt);
        na_issueadd_txt = rootView.findViewById(R.id.na_issueadd_txt);
        na_rcvform_txt = rootView.findViewById(R.id.na_rcvform_txt);
        na_CallForInterview_txt = rootView.findViewById(R.id.na_CallForInterview_txt);
        na_comeforinterview_txt = rootView.findViewById(R.id.na_comeforinterview_txt);
        na_confirmaddmission_txt = rootView.findViewById(R.id.na_confirmaddmission_txt);
        na_rejected_txt = rootView.findViewById(R.id.na_rejected_txt);
        na_fees_rec_txt = rootView.findViewById(R.id.na_fees_rec_txt);
        na_fees_not_rec_txt = rootView.findViewById(R.id.na_fees_not_rec_txt);

        smssent_txt = rootView.findViewById(R.id.smssent_txt);
        smsdelivered_txt = rootView.findViewById(R.id.smsdelivered_txt);
        smspedning_txt = rootView.findViewById(R.id.smspedning_txt);

        fragmentMisBinding.LLStudentcontainer.setVisibility(View.GONE);
        fragmentMisBinding.LLStaffcontainer.setVisibility(View.GONE);
        fragmentMisBinding.LLAccount.setVisibility(View.GONE);
        fragmentMisBinding.LLNacontainer.setVisibility(View.GONE);
        fragmentMisBinding.LLMsgcontainer.setVisibility(View.GONE);
//        fragmentMisBinding.staffNewInnercontainer.setVisibility(View.GONE);
        fragmentMisBinding.linearRecylerGrid.setVisibility(View.GONE);
        fragmentMisBinding.linearRecylerList.setVisibility(View.GONE);
        fragmentMisBinding.linearStaffDate.setVisibility(View.GONE);
        fragmentMisBinding.taskReportInnercontainer.setVisibility(View.GONE);
        fragmentMisBinding.resultOfSchoolInnercontainer.setVisibility(View.GONE);
        fragmentMisBinding.LLFinance.setVisibility(View.GONE);
        fragmentMisBinding.linearFinance.setVisibility(View.GONE);
        llCalendar.setVisibility(View.GONE);

        fragmentMisBinding.progressStudent.setVisibility(View.VISIBLE);
        fragmentMisBinding.progressStaff.setVisibility(View.VISIBLE);
        fragmentMisBinding.progressAccount.setVisibility(View.VISIBLE);
        fragmentMisBinding.progressNa.setVisibility(View.VISIBLE);
        fragmentMisBinding.progressMsg.setVisibility(View.VISIBLE);
        fragmentMisBinding.progressStaff1.setVisibility(View.VISIBLE);
        fragmentMisBinding.progressTaskReport.setVisibility(View.VISIBLE);
        fragmentMisBinding.progressResultOfSchool.setVisibility(View.VISIBLE);
        fragmentMisBinding.progressFinance.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        topperBarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ChartFragment();
                bundle = new Bundle();
                bundle.putString("charttype", "topper");
//                bundle.putString("requestType", "Total");
//                bundle.putString("TermID", FinalTermIdStr);
//                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());
//                bundle.putString("Gender", "");
//
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
            }
        });

//        rangeBarChart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fragment = new ChartFragment();
//                bundle = new Bundle();
//                bundle.putString("charttype", "range");
////                bundle.putString("requestType", "Total");
////                bundle.putString("TermID", FinalTermIdStr);
////                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());
////                bundle.putString("Gender", "");
////
//                fragment.setArguments(bundle);
//                fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
//                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
//                AppConfiguration.firsttimeback = true;
//                AppConfiguration.position = 67;
//            }
//        });

        student_transport_detail.setOnClickListener(this);

        student_total_txt.setOnClickListener(this);
        student_present_txt.setOnClickListener(this);
        student_leave_txt.setOnClickListener(this);
        student_absent_txt.setOnClickListener(this);
        antstudent_txt.setOnClickListener(this);
        abstudent_txt.setOnClickListener(this);
        ba_txt.setOnClickListener(this);
        student_attendance_less_70_txt.setOnClickListener(this);

        boy_total_txt.setOnClickListener(this);
        boy_present_txt.setOnClickListener(this);
        boy_leave_txt.setOnClickListener(this);
        boy_absent_txt.setOnClickListener(this);
        antboy_txt.setOnClickListener(this);
        abboy_txt.setOnClickListener(this);
        baboy_txt.setOnClickListener(this);
        boy_attendance_less_70_txt.setOnClickListener(this);

        girl_total_txt.setOnClickListener(this);
        girl_present_txt.setOnClickListener(this);
        girl_leave_txt.setOnClickListener(this);
        girl_absent_txt.setOnClickListener(this);
        antgirl_txt.setOnClickListener(this);
        abgirl_txt.setOnClickListener(this);
        bagirl_txt.setOnClickListener(this);
        girl_attendance_less_70_txt.setOnClickListener(this);

        staff_total_txt.setOnClickListener(this);
        staff_present_txt.setOnClickListener(this);
        absent_txt.setOnClickListener(this);
        staffleave_txt.setOnClickListener(this);
        abstaff_txt.setOnClickListener(this);
        antstaff_txt.setOnClickListener(this);
        staff_workplan_txt.setOnClickListener(this);
        staff_cw_txt.setOnClickListener(this);
        staff_hw_submitted_txt.setOnClickListener(this);

        actotaltobecall_txt.setOnClickListener(this);
        acterm1fess_txt.setOnClickListener(this);
        acterm2fess_txt.setOnClickListener(this);
        acterm1_collection.setOnClickListener(this);
        acterm2_collection.setOnClickListener(this);
        acos_txt.setOnClickListener(this);
        accashcollection_txt.setOnClickListener(this);
        acchhqdd_txt.setOnClickListener(this);
        aconine_txt.setOnClickListener(this);

        na_inquiry_txt.setOnClickListener(this);
        na_issueadd_txt.setOnClickListener(this);
        na_rcvform_txt.setOnClickListener(this);
        na_CallForInterview_txt.setOnClickListener(this);
        na_comeforinterview_txt.setOnClickListener(this);
        na_confirmaddmission_txt.setOnClickListener(this);
        na_rejected_txt.setOnClickListener(this);
        na_fees_rec_txt.setOnClickListener(this);
        na_fees_not_rec_txt.setOnClickListener(this);

        smssent_txt.setOnClickListener(this);
        smsdelivered_txt.setOnClickListener(this);
        smspedning_txt.setOnClickListener(this);

        setListners();

        try {
            callTermApi();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

//        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        AppConfiguration.ReverseTermDetailId = "";
        AppConfiguration.position = 5;
        AppConfiguration.firsttimeback = true;
    }

    private void compareDates() {

        Date date2 = parseDate("16:00"); //The date to compare with the current date
        Date nowdate = new Date();//Get the current date
        SimpleDateFormat sdff = new SimpleDateFormat("HH:mm");//This is the time and minute to remove
        String str1 = sdff.format(date2);
        String str2 = sdff.format(nowdate);
        Date d;
        Date dd;//current date
        try {
            d = sdff.parse(str1);
            dd = sdff.parse(str2);

//This date is executed before today...

            if (d.getTime() < dd.getTime()) {
                //yada yada
                fragmentMisBinding.studentDateBtn.setText(Utils.getTodaysDate());
                fragmentMisBinding.staffDateBtn.setText(Utils.getTodaysDate());
                fragmentMisBinding.taskreportDateBtn.setText(Utils.getTodaysDate());
            } else {
                String sDate = Utils.getTodaysDate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date date1 = null;
                try {
                    date1 = dateFormat.parse(sDate);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date1);
                    calendar.add(Calendar.DATE, -1);
                    String yesterdayAsString = dateFormat.format(calendar.getTime());

                    fragmentMisBinding.studentDateBtn.setText(yesterdayAsString);
                    fragmentMisBinding.staffDateBtn.setText(yesterdayAsString);
                    fragmentMisBinding.taskreportDateBtn.setText(yesterdayAsString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Date parseDate(String date) {
        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }

    public void setListners() {

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfiguration.ReverseTermDetailId = "";
                fragment = new HomeFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        compareDates();
//        SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
////        String date = df.format(Calendar.getInstance().getTime());
//        Date CurrentTime = null;
//        Date EndTime = null;
//        try {
//
//            String str_date= Utils.getTodaysDate()+" "+"16:00:00";
//            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
//            Date date = (Date)formatter.parse(str_date);
//
//            System.out.println("Today is " +date.getTime());
//
//             specifictime = date.getTime();
//
//
//            String current_date = Utils.getTodaysDate();
//            DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
//            Date date1 = (Date)formatter1.parse(current_date);
//
//            Calendar current_time = Calendar.getInstance ();
//            current_time.add(Calendar.YEAR, 0);
//            current_time.add(Calendar.DAY_OF_YEAR, 0);
//            current_time.set(Calendar.HOUR_OF_DAY,0);
//            current_time.set(Calendar.MINUTE, 0);
//            current_time.set(Calendar.SECOND, 0);
//
//
//            CurrentTime = df.parse(df.format(currenttime));
//            currenttime = CurrentTime.getTime();
//            //EndTime = df.parse("04:00 PM");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        Log.d("time",String.valueOf(specifictime) + " " + String.valueOf(currenttime));
//
//        //if (CurrentTime.after(EndTime))
//        if (specifictime <  currenttime) {
//            fragmentMisBinding.studentDateBtn.setText(Utils.getTodaysDate());
//            fragmentMisBinding.staffDateBtn.setText(Utils.getTodaysDate());
//            fragmentMisBinding.taskreportDateBtn.setText(Utils.getTodaysDate());
//        }
//        else {
//
//            String sDate = Utils.getTodaysDate();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//            Date date = null;
//            try {
//                date = dateFormat.parse(sDate);
//
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(date);
//                calendar.add(Calendar.DATE, -1);
//                String yesterdayAsString = dateFormat.format(calendar.getTime());
//
//                fragmentMisBinding.studentDateBtn.setText(yesterdayAsString);
//                fragmentMisBinding.staffDateBtn.setText(yesterdayAsString);
//                fragmentMisBinding.taskreportDateBtn.setText(yesterdayAsString);
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }


//        fragmentMisBinding.studentDateBtn.setText(Utils.getTodaysDate());
//        fragmentMisBinding.staffDateBtn.setText(Utils.getTodaysDate());
//        fragmentMisBinding.taskreportDateBtn.setText(Utils.getTodaysDate());

        AppConfiguration.staffDate = fragmentMisBinding.staffDateBtn.getText().toString();
        AppConfiguration.taskReportDate = fragmentMisBinding.taskreportDateBtn.getText().toString();

        fragmentMisBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentMisBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentMisBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid;
                AppConfiguration.TermId = FinalTermIdStr;

                Log.d("FinalTermIdStr", FinalTermIdStr);
                AppConfiguration.TermName = name;

                fragmentMisBinding.LLStudentcontainer.setVisibility(View.GONE);
                fragmentMisBinding.LLStaffcontainer.setVisibility(View.GONE);
                fragmentMisBinding.LLAccount.setVisibility(View.GONE);
                fragmentMisBinding.LLNacontainer.setVisibility(View.GONE);
                fragmentMisBinding.LLMsgcontainer.setVisibility(View.GONE);

                fragmentMisBinding.progressStudent.setVisibility(View.VISIBLE);
                fragmentMisBinding.progressStaff.setVisibility(View.VISIBLE);
                fragmentMisBinding.progressStaff1.setVisibility(View.VISIBLE);
                fragmentMisBinding.progressAccount.setVisibility(View.VISIBLE);
                fragmentMisBinding.progressNa.setVisibility(View.VISIBLE);
                fragmentMisBinding.progressMsg.setVisibility(View.VISIBLE);

                try {
                    if (isAdded()) {
//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                              //  callStudentMISDataApi("Student",fragmentMisBinding.studentDateBtn.getText().toString());
//                                //callFinanceMISDataApi(FinalFinanaceTermId);
//                                fillFinanceTermSpinner();
//                            }
//                        }, 2000);

                        fillFinanceTermSpinner();

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentMisBinding.spinnerFinance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentMisBinding.spinnerFinance.getSelectedItem().toString();
                String getid = spinnerTermMap2.get(fragmentMisBinding.spinnerFinance.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalFinanaceTermId = getid;
                AppConfiguration.financeTermId = FinalFinanaceTermId;

                Log.d("FinalFinanaceTermId", FinalFinanaceTermId);
                //AppConfiguration.TermName = name;

                fragmentMisBinding.LLFinance.setVisibility(View.GONE);
                fragmentMisBinding.linearFinance.setVisibility(View.GONE);
                fragmentMisBinding.tvNoRecordsOfFinance.setVisibility(View.GONE);
                fragmentMisBinding.progressFinance.setVisibility(View.VISIBLE);

                try {
                    if (isAdded()) {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                callFinanceMISDataApi(FinalFinanaceTermId);

                                //fillFinanceTermSpinner();
                            }
                        }, 2000);

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCalendar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = spCalendar.getSelectedItem().toString();
                String getid = spinnerTermMap4.get(spCalendar.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalCalendarTermID = getid;
                AppConfiguration.calendarTermId = FinalCalendarTermID;

                Log.d("FinalCAlendarId", FinalCalendarTermID);
                //AppConfiguration.TermName = name;

                llCalendar.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                try {
                    if (isAdded()) {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                callSchoolCalendar(FinalCalendarTermID);

                                //fillFinanceTermSpinner();
                            }
                        }, 2000);

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentMisBinding.spinnerNa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentMisBinding.spinnerNa.getSelectedItem().toString();
                String getid = spinnerTermMap3.get(fragmentMisBinding.spinnerNa.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalNATermID = getid;
                AppConfiguration.NA_TERM_ID = FinalNATermID;

                Log.d("FinalNATermID", FinalNATermID);
                //AppConfiguration.TermName = name;

                fragmentMisBinding.progressNa.setVisibility(View.VISIBLE);
                fragmentMisBinding.LLNacontainer.setVisibility(View.GONE);

                try {
                    if (isAdded()) {

                        try {
                            callNAMISDataApi("New Addmission");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //fillFinanceTermSpinner();

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        fragmentMisBinding.resultOfSchoolTermSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentMisBinding.resultOfSchoolTermSpinner.getSelectedItem().toString();
                String getid = spinnerSchoolResultMap.get(fragmentMisBinding.resultOfSchoolTermSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalSchoolResultTermID = getid;
                AppConfiguration.schoolResultTermID = FinalSchoolResultTermID;

                Log.d("FinalTermIdStr", FinalSchoolResultTermID);

                isRequireCallNestedAPI = false;

                try {
                    if (isAdded()) {
                        callSchoolTopperListMISDataApi();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        fragmentMisBinding.studentDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichDateClicked = 1;
                datePickerDialog = DatePickerDialog.newInstance(MISFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.setMaxDate(calendar);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });

        fragmentMisBinding.staffDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichDateClicked = 2;
                datePickerDialog = DatePickerDialog.newInstance(MISFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.setMaxDate(calendar);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });

        fragmentMisBinding.taskreportDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichDateClicked = 3;
                datePickerDialog = DatePickerDialog.newInstance(MISFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.setMaxDate(calendar);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });
        fragmentMisBinding.tvGradewise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new MISClasswiseResultFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out).add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
            }
        });

    }

    private void callTermApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTerm(getTermDetail(), new retrofit.Callback<TermModel>() {
            @Override
            public void success(TermModel termModel, Response response) {
                Utils.dismissDialog();
                if (termModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayGetTermModels = termModel.getFinalArray();
                    if (finalArrayGetTermModels != null) {
                        fillTermSpinner();
                        fillFinanceTermSpinner();
//                        fillNATermSpinner();
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

    private Map<String, String> getTermDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }


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


//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentMisBinding.termSpinner);
//
//            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray) {

            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                //((TextView) v).setTextSize(16);
                ((TextView) v).setGravity(Gravity.CENTER_HORIZONTAL);

                return v;

            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getDropDownView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.LEFT);

                return v;

            }

        };

        fragmentMisBinding.termSpinner.setAdapter(adapter);
        FinalTermIdStr = spinnerTermMap.get(1);
        AppConfiguration.TermId = FinalTermIdStr;
        fragmentMisBinding.termSpinner.setSelection(1);


    }

    public void fillFinanceTermSpinner() {

        ArrayList<Integer> TermId = new ArrayList<>();
        for (int i = 0; i < finalArrayGetTermModels.size(); i++) {
            TermId.add(finalArrayGetTermModels.get(i).getTermId());
        }
        ArrayList<String> Term = new ArrayList<>();
        for (int j = 0; j < finalArrayGetTermModels.size(); j++) {
            Term.add(finalArrayGetTermModels.get(j).getTerm());
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerTermMap2 = new HashMap<>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerTermMap2.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }


//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentMisBinding.spinnerFinance);
//
//            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray) {

            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                //((TextView) v).setTextSize(16);
                ((TextView) v).setGravity(Gravity.CENTER_HORIZONTAL);

                return v;

            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getDropDownView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.LEFT);

                return v;

            }

        };


        fragmentMisBinding.spinnerFinance.setAdapter(adapter);
        FinalFinanaceTermId = spinnerTermMap2.get(0);
        AppConfiguration.financeTermId = FinalFinanaceTermId;
        fragmentMisBinding.spinnerFinance.setSelection(0, false);


    }

    public void fillSchoolCalendarSpinner() {

        ArrayList<Integer> calId = new ArrayList<>();
        for (int i = 0; i < finalArrayGetTermModels.size(); i++) {
            calId.add(finalArrayGetTermModels.get(i).getTermId());
        }
        ArrayList<String> Cal = new ArrayList<>();
        for (int j = 0; j < finalArrayGetTermModels.size(); j++) {
            Cal.add(finalArrayGetTermModels.get(j).getTerm());
        }

        String[] spinnertermIdArray = new String[calId.size()];

        spinnerTermMap4 = new HashMap<>();
        for (int i = 0; i < calId.size(); i++) {
            spinnerTermMap4.put(i, String.valueOf(calId.get(i)));
            spinnertermIdArray[i] = Cal.get(i).trim();
        }

//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spCalendar);
//
//            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray) {

            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                //((TextView) v).setTextSize(16);
                ((TextView) v).setGravity(Gravity.CENTER_HORIZONTAL);

                return v;

            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getDropDownView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.LEFT);

                return v;
            }
        };

        spCalendar.setAdapter(adapter);
        FinalCalendarTermID = spinnerTermMap4.get(1);
        AppConfiguration.calendarTermId = FinalCalendarTermID;
        spCalendar.setSelection(1, false);

    }

    public void fillNATermSpinner() {

        ArrayList<Integer> TermId = new ArrayList<>();
        for (int i = 0; i < finalArrayGetTermModels.size(); i++) {
            TermId.add(finalArrayGetTermModels.get(i).getTermId());
        }
        ArrayList<String> Term = new ArrayList<>();
        for (int j = 0; j < finalArrayGetTermModels.size(); j++) {
            Term.add(finalArrayGetTermModels.get(j).getTerm());
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerTermMap3 = new HashMap<>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerTermMap3.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }


//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentMisBinding.spinnerNa);
//
//            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray) {

            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                //((TextView) v).setTextSize(16);
                ((TextView) v).setGravity(Gravity.CENTER_HORIZONTAL);

                return v;

            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getDropDownView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.LEFT);

                return v;

            }
        };

        fragmentMisBinding.spinnerNa.setAdapter(adapter);
        FinalNATermID = spinnerTermMap3.get(0);
        AppConfiguration.NA_TERM_ID = FinalNATermID;
        fragmentMisBinding.spinnerNa.setSelection(0, false);

    }


    public void fillSchoolResultTermSpinner() {

        ArrayList<Integer> TermId = new ArrayList<>();

        TermId.add(1);
        TermId.add(2);

//        for (int i = 0; i < finalArrayGetTermModels.size(); i++) {
//            TermId.add(finalArrayGetTermModels.get(i).getTermId());
//        }
        ArrayList<String> Term = new ArrayList<>();
//        for (int j = 0; j < finalArrayGetTermModels.size(); j++) {
//            Term.add(finalArrayGetTermModels.get(j).getTerm());
//        }

        Term.add("Term 1");
        Term.add("Term 2");

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerSchoolResultMap = new HashMap<>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerSchoolResultMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }

//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentMisBinding.resultOfSchoolTermSpinner);
//
//            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray) {

            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                //((TextView) v).setTextSize(16);
                ((TextView) v).setGravity(Gravity.CENTER_HORIZONTAL);

                return v;

            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getDropDownView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.LEFT);

                return v;

            }

        };


        fragmentMisBinding.resultOfSchoolTermSpinner.setAdapter(adapter);
        FinalSchoolResultTermID = spinnerSchoolResultMap.get(0);
        AppConfiguration.schoolResultTermID = FinalSchoolResultTermID;
        fragmentMisBinding.resultOfSchoolTermSpinner.setSelection(0, false);


    }


    private void callStudentMISDataApi(String requestType, String date, final String termid) {


        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        fragmentMisBinding.progressStudent.setVisibility(View.VISIBLE);
        fragmentMisBinding.LLStudentcontainer.setVisibility(View.GONE);
        // Utils.showDialog(getActivity());
        ApiHandler.getApiService().getMISdata(getStudentParams(requestType, date), new retrofit.Callback<MISModel>() {

            @Override
            public void success(MISModel staffSMSDataModel, Response response) {
                //Utils.dismissDialog();
                fragmentMisBinding.progressStudent.setVisibility(View.GONE);

                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {

                    try {

                        student_total_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(0).getData().get(0).getTotal()));
                        student_present_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(0).getData().get(0).getTotalPresent()));
                        student_absent_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(0).getData().get(0).getTotalAbsent()));
                        student_leave_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(0).getData().get(0).getTotalLeave()));
                        antstudent_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(0).getData().get(0).getTotalStudentANT()));
                        abstudent_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(0).getData().get(0).getTotalConsistanceAbsent()));
                        ba_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(0).getData().get(0).getBetAlumini()));
                        student_attendance_less_70_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(0).getData().get(0).getAttendance()));

                        boy_total_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(1).getData().get(0).getTotal()));
                        boy_present_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(1).getData().get(0).getTotalPresent()));
                        boy_absent_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(1).getData().get(0).getTotalAbsent()));
                        boy_leave_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(1).getData().get(0).getTotalLeave()));
                        antboy_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(1).getData().get(0).getTotalStudentANT()));
                        abboy_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(1).getData().get(0).getTotalConsistanceAbsent()));
                        baboy_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(1).getData().get(0).getBetAlumini()));
                        boy_attendance_less_70_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(1).getData().get(0).getAttendance()));

                        girl_total_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(2).getData().get(0).getTotal()));
                        girl_present_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(2).getData().get(0).getTotalPresent()));
                        girl_absent_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(2).getData().get(0).getTotalAbsent()));
                        girl_leave_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(2).getData().get(0).getTotalLeave()));
                        antgirl_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(2).getData().get(0).getTotalStudentANT()));
                        abgirl_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(2).getData().get(0).getTotalConsistanceAbsent()));
                        bagirl_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(2).getData().get(0).getBetAlumini()));
                        girl_attendance_less_70_txt.setText(String.valueOf(staffSMSDataModel.getDetailArray().get(2).getData().get(0).getAttendance()));

                        fragmentMisBinding.progressStudent.setVisibility(View.GONE);
                        fragmentMisBinding.LLStudentcontainer.setVisibility(View.VISIBLE);
                        fragmentMisBinding.linearStaffDate.setVisibility(View.GONE);


                        try {
                            if (isAdded()) {
                                if (isRequireCallNestedAPI || isFirmtimeLoad) {
                                    //callStafftMISDataApi("Staff");
                                    callNewStafftMISDataApi(fragmentMisBinding.staffDateBtn.getText().toString(), termid);

                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                // Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                fragmentMisBinding.progressStudent.setVisibility(View.GONE);
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }


//    private void callStafftMISDataApi(String requestType) {
//
//        if (!Utils.checkNetwork(mContext)) {
//            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
//            return;
//        }
//
//        // Utils.showDialog(getActivity());
//        ApiHandler.getApiService().getMISdata(getParams(requestType), new retrofit.Callback<MISModel>() {
//            @Override
//            public void success(MISModel staffSMSDataModel, Response response) {
//                //  Utils.dismissDialog();
//                fragmentMisBinding.progressStaff.setVisibility(View.GONE);
//
//                if (staffSMSDataModel == null) {
//                    Utils.ping(mContext, getString(R.string.something_wrong));
//                    return;
//                }
//                if (staffSMSDataModel.getSuccess() == null) {
//                    Utils.ping(mContext, getString(R.string.something_wrong));
//                    return;
//                }
//                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
//                    Utils.ping(mContext, getString(R.string.false_msg));
//                    return;
//                }
//                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {
//                    try {
//
//                        staff_total_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getTotal()));
//                        staff_present_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getTotalPresent()));
//                        absent_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getTotalAbsent()));
//                        staffleave_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getTotalLeave()));
//                        abstaff_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getTotalAbsent()));
//                        antstaff_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getTotalStudentANT()));
//                        staff_workplan_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getDailyEntryDone() + "/" + staffSMSDataModel.getFinalArray().get(0).getDailyEntryTotal()));
//                        staff_cw_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getCWDone() + "/" + staffSMSDataModel.getFinalArray().get(0).getCWTotal()));
//                        staff_hw_submitted_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getHWDone() + "/" + staffSMSDataModel.getFinalArray().get(0).getHWTotal()));
//
//                        fragmentMisBinding.progressStaff.setVisibility(View.GONE);
//                        fragmentMisBinding.LLStaffcontainer.setVisibility(View.VISIBLE);
//
//
//                        try {
//                            if (isAdded()) {
////                              callNewStafftMISDataApi(fragmentMisBinding.staffDateBtn.getText().toString());
//                            }
//                        } catch (Exception ex) {
//
//                        }
//
//
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                //Utils.dismissDialog();
//                fragmentMisBinding.progressStaff.setVisibility(View.GONE);
//
//                error.printStackTrace();
//                error.getMessage();
//                Utils.ping(mContext, error.getMessage());
//            }
//        });
//    }


    private void callNewStafftMISDataApi(String date, final String termid) {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }
        fragmentMisBinding.progressStaff1.setVisibility(View.VISIBLE);
        fragmentMisBinding.linearStaffDate.setVisibility(View.GONE);

        // Utils.showDialog(getActivity());
        ApiHandler.getApiService().getMISStaffAttendance(getStaffNewParams(date), new retrofit.Callback<MISStaffNewModel>() {
            @Override
            public void success(MISStaffNewModel staffSMSDataModel, Response response) {
                //  Utils.dismissDialog();
                fragmentMisBinding.progressStaff1.setVisibility(View.GONE);

                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMisBinding.progressStaff.setVisibility(View.GONE);
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    fragmentMisBinding.progressStaff1.setVisibility(View.GONE);
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    fragmentMisBinding.progressStaff1.setVisibility(View.GONE);
                    fragmentMisBinding.linearStaffDate.setVisibility(View.VISIBLE);

//                    try {
//                        if (isAdded()) {
//                            callTaskReportMISDataApi(fragmentMisBinding.taskreportDateBtn.getText().toString());
//                        }
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }


                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {
//                  else {
                    try {

                        fragmentMisBinding.progressStaff1.setVisibility(View.GONE);
                        fragmentMisBinding.linearStaffDate.setVisibility(View.VISIBLE);

//                        fragmentMisBinding.staffNewInnercontainer.setVisibility(View.VISIBLE);
                        fragmentMisBinding.linearRecylerGrid.setVisibility(View.VISIBLE);
                        fragmentMisBinding.linearRecylerList.setVisibility(View.VISIBLE);

                        dataValues = staffSMSDataModel.getFinalArray();

                        misStaffGridAdapter = new MISStaffGridAdapter(getActivity(), dataValues);
                        fragmentMisBinding.rvStaffGridList.setLayoutManager(new GridLayoutManager(getActivity(), dataValues.size(), OrientationHelper.HORIZONTAL, false));
                        fragmentMisBinding.rvStaffGridList.addItemDecoration(new SpacesItemDecoration(0));
                        fragmentMisBinding.rvStaffGridList.setAdapter(misStaffGridAdapter);


                        try {
                            Gson gson = new Gson();
                            String json = gson.toJson(staffSMSDataModel);

                            JSONObject dataObject = new JSONObject(json);

                            JSONArray dataArray = dataObject.getJSONArray("Detail");

                            JSONObject innerdataObject = null;

                            for (int countArray = 0; countArray < dataArray.length(); countArray++) {

                                innerdataObject = dataArray.getJSONObject(countArray);

                                Iterator<String> dataKeys = innerdataObject.keys();

                                dataValues1 = new ArrayList<>();
                                keyValues = new ArrayList<>();


                                while (dataKeys.hasNext()) {
                                    String key = dataKeys.next();
                                    keyValues.add(key);
                                }
                            }

                            for (int count = 0; count < keyValues.size(); count++) {
                                String value = innerdataObject.optString(keyValues.get(count));
                                dataValues1.add(value);
                            }
                            AppConfiguration.TAG = "Staff";

                            misStaffListAdapter = new MISStaffListAdapter(getActivity(), dataValues1, keyValues, termid);
                            fragmentMisBinding.rvStaffList.setLayoutManager(new LinearLayoutManager(getActivity()));
                            fragmentMisBinding.rvStaffList.setAdapter(misStaffListAdapter);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }


                        try {
                            if (isAdded()) {
                                callTaskReportMISDataApi(fragmentMisBinding.taskreportDateBtn.getText().toString());
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                //Utils.dismissDialog();
                fragmentMisBinding.progressStaff.setVisibility(View.GONE);

                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, error.getMessage());
            }
        });
    }

    private void callTaskReportMISDataApi(String date) {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }


        // Utils.showDialog(getActivity());
        ApiHandler.getApiService().getMISTask(getTaskReportParams(date), new retrofit.Callback<MISTaskReportModel>() {
            @Override
            public void success(MISTaskReportModel staffSMSDataModel, Response response) {
                //  Utils.dismissDialog();
                fragmentMisBinding.progressTaskReport.setVisibility(View.GONE);

                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMisBinding.progressTaskReport.setVisibility(View.GONE);
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    fragmentMisBinding.progressTaskReport.setVisibility(View.GONE);
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    fragmentMisBinding.progressTaskReport.setVisibility(View.GONE);
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {
                    try {

                        fragmentMisBinding.progressTaskReport.setVisibility(View.GONE);

                        fragmentMisBinding.taskReportInnercontainer.setVisibility(View.VISIBLE);

                        taskReportdataValues = staffSMSDataModel.getFinalArray();
                        misTaskReportGridAdapter = new MISTaskReportGridAdapter(getActivity(), taskReportdataValues, FinalFinanaceTermId);
                        fragmentMisBinding.rvTaskreportGridList.setLayoutManager(new GridLayoutManager(getActivity(), taskReportdataValues.size(), OrientationHelper.HORIZONTAL, false));
                        fragmentMisBinding.rvTaskreportGridList.addItemDecoration(new SpacesItemDecoration(0));
                        fragmentMisBinding.rvTaskreportGridList.setAdapter(misTaskReportGridAdapter);

                        fragmentMisBinding.LLNacontainer.setVisibility(View.GONE);

                        try {
                            if (isAdded()) {
                                callNAMISDataApi("New Addmission");
                                fillSchoolResultTermSpinner();
                                fillNATermSpinner();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                //Utils.dismissDialog();
                fragmentMisBinding.progressTaskReport.setVisibility(View.GONE);

                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, error.getMessage());
            }
        });

    }

    private void callSchoolTopperListMISDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        fragmentMisBinding.progressResultOfSchool.setVisibility(View.VISIBLE);
        fragmentMisBinding.resultOfSchoolInnercontainer.setVisibility(View.GONE);
        // Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTopperList(getTopperListParams(), new retrofit.Callback<MISSchoolResultModel>() {

            @Override
            public void success(MISSchoolResultModel staffSMSDataModel, Response response) {
                //Utils.dismissDialog();
                fragmentMisBinding.progressResultOfSchool.setVisibility(View.GONE);

                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentMisBinding.progressResultOfSchool.setVisibility(View.GONE);
                    fragmentMisBinding.resultOfSchoolInnercontainer.setVisibility(View.VISIBLE);
                    fragmentMisBinding.linearResultOfSchoolDate.setVisibility(View.VISIBLE);
                    fragmentMisBinding.tvNoRecordsOfSchoolResult.setVisibility(View.VISIBLE);
                    fragmentMisBinding.linearResultOfSchoolRecylerGrid.setVisibility(View.GONE);
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {

                    try {

                        fragmentMisBinding.progressResultOfSchool.setVisibility(View.GONE);
                        fragmentMisBinding.resultOfSchoolInnercontainer.setVisibility(View.VISIBLE);

                        fragmentMisBinding.linearResultOfSchoolDate.setVisibility(View.VISIBLE);
                        fragmentMisBinding.tvNoRecordsOfSchoolResult.setVisibility(View.GONE);
                        fragmentMisBinding.linearResultOfSchoolRecylerGrid.setVisibility(View.VISIBLE);

                        topperdataValues = staffSMSDataModel.getFinalArray();
                        misSchoolReportAdapter = new MISSchoolReportAdapter(getActivity(), topperdataValues);
                        fragmentMisBinding.rvResultOfSchoolGridList.setLayoutManager(new GridLayoutManager(getActivity(), topperdataValues.size(), OrientationHelper.HORIZONTAL, false));
                        fragmentMisBinding.rvResultOfSchoolGridList.addItemDecoration(new SpacesItemDecoration(0));
                        fragmentMisBinding.rvResultOfSchoolGridList.setAdapter(misSchoolReportAdapter);


                        try {
                            if (isAdded()) {
                                if (isRequireCallNestedAPI || isFirmtimeLoad) {
                                    // fillFinanceTermSpinner();
                                    fillNATermSpinner();
                                    //callNAMISDataApi("New Addmission");


                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                // Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                fragmentMisBinding.progressResultOfSchool.setVisibility(View.GONE);
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });
    }

    private void callFinanceMISDataApi(final String termId) {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        fragmentMisBinding.progressFinance.setVisibility(View.VISIBLE);
        fragmentMisBinding.LLFinance.setVisibility(View.GONE);
        fragmentMisBinding.linearFinance.setVisibility(View.GONE);
        fragmentMisBinding.tvNoRecordsOfFinance.setVisibility(View.GONE);
        // Utils.showDialog(getActivity());
        ApiHandler.getApiService().getHeadWiseFeesCollection(getFinanceListParams(termId), new retrofit.Callback<MISFinanaceModel>() {

            @Override
            public void success(MISFinanaceModel staffSMSDataModel, Response response) {
                //Utils.dismissDialog();
                fragmentMisBinding.progressFinance.setVisibility(View.GONE);

                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentMisBinding.progressFinance.setVisibility(View.GONE);
                    fragmentMisBinding.LLFinance.setVisibility(View.GONE);
                    fragmentMisBinding.linearFinance.setVisibility(View.VISIBLE);
                    fragmentMisBinding.tvNoRecordsOfFinance.setVisibility(View.VISIBLE);
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {

                    try {

                        fragmentMisBinding.progressFinance.setVisibility(View.GONE);
                        fragmentMisBinding.LLFinance.setVisibility(View.VISIBLE);
                        fragmentMisBinding.tvNoRecordsOfFinance.setVisibility(View.GONE);
                        fragmentMisBinding.linearFinance.setVisibility(View.VISIBLE);

                        financedataValues = staffSMSDataModel.getFinalArray();

                        misFinanceReportAdapter = new MISFinanceReportAdapter(getActivity(), financedataValues, termId);
                        fragmentMisBinding.rvFinanceList.setLayoutManager(new GridLayoutManager(getActivity(), financedataValues.size(), OrientationHelper.HORIZONTAL, false));
                        fragmentMisBinding.rvFinanceList.addItemDecoration(new SpacesItemDecoration(0));
                        fragmentMisBinding.rvFinanceList.setAdapter(misFinanceReportAdapter);

                        try {
                            Gson gson = new Gson();
                            String json = gson.toJson(staffSMSDataModel);

                            JSONObject dataObject = new JSONObject(json);

                            JSONArray dataArray = dataObject.getJSONArray("CountData");

                            JSONObject innerdataObject = null;

                            for (int countArray = 0; countArray < dataArray.length(); countArray++) {

                                innerdataObject = dataArray.getJSONObject(countArray);

                                Iterator<String> dataKeys = innerdataObject.keys();

                                dataValues1 = new ArrayList<>();
                                keyValues = new ArrayList<>();


                                while (dataKeys.hasNext()) {
                                    String key = dataKeys.next();
                                    keyValues.add(key);
                                }
                            }

                            for (int count = 0; count < keyValues.size(); count++) {
                                String value = innerdataObject.optString(keyValues.get(count));
                                dataValues1.add(value);
                            }


                            AppConfiguration.TAG = "Finance";

                            misFinanceListAdapter = new MISFinanceListAdapter(getActivity(), dataValues1, keyValues, termId);

                            fragmentMisBinding.rvFinanceList2.setLayoutManager(new LinearLayoutManager(getActivity()));
                            fragmentMisBinding.rvFinanceList2.setAdapter(misFinanceListAdapter);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        try {
                            if (isAdded()) {
                                if (isRequireCallNestedAPI || isFirmtimeLoad) {
                                    // callAccounttMISDataApi("Account");
                                    //callNAMISDataApi("New Addmission");

                                    callStudentMISDataApi("Student", fragmentMisBinding.studentDateBtn.getText().toString(), "");

                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                // Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                fragmentMisBinding.progressResultOfSchool.setVisibility(View.GONE);
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

//    private void callAccounttMISDataApi(String requestType) {
//
//        if (!Utils.checkNetwork(mContext)) {
//            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
//            return;
//        }
//
//        // Utils.showDialog(getActivity());
//        ApiHandler.getApiService().getMISdata(getParams(requestType), new retrofit.Callback<MISModel>() {
//
//            @Override
//            public void success(MISModel staffSMSDataModel, Response response) {
//                // Utils.dismissDialog();
//                fragmentMisBinding.progressAccount.setVisibility(View.GONE);
//
//                if (staffSMSDataModel == null) {
//                    Utils.ping(mContext, getString(R.string.something_wrong));
//                    return;
//                }
//                if (staffSMSDataModel.getSuccess() == null) {
//                    Utils.ping(mContext, getString(R.string.something_wrong));
//                    return;
//                }
//                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
//                    Utils.ping(mContext, getString(R.string.false_msg));
//                    return;
//                }
//                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {
//
//                    try {
//                        actotaltobecall_txt.setText(String.valueOf("₹" + staffSMSDataModel.getFinalArray().get(0).getTotalToBeCollected()));
//                        acterm1fess_txt.setText(String.valueOf("₹" + staffSMSDataModel.getFinalArray().get(0).getTerm1Fees()));
//                        acterm2fess_txt.setText(String.valueOf("₹" + staffSMSDataModel.getFinalArray().get(0).getTerm2Fees()));
//                        acterm1_collection.setText(String.valueOf("₹" + staffSMSDataModel.getFinalArray().get(0).getTerm1FeesCollection()));
//                        acterm2_collection.setText(String.valueOf("₹" + staffSMSDataModel.getFinalArray().get(0).getTerm2FeesCollection()));
//                        acos_txt.setText(String.valueOf("₹" + staffSMSDataModel.getFinalArray().get(0).getTotalOS()));
//                        accashcollection_txt.setText(String.valueOf("₹" + staffSMSDataModel.getFinalArray().get(0).getCashCollection()));
//                        acchhqdd_txt.setText(String.valueOf("₹" + staffSMSDataModel.getFinalArray().get(0).getChequeDD()));
//                        aconine_txt.setText(String.valueOf("₹" + staffSMSDataModel.getFinalArray().get(0).getOnline()));
//
//
//                        fragmentMisBinding.progressAccount.setVisibility(View.GONE);
//                        fragmentMisBinding.LLAccount.setVisibility(View.VISIBLE);
//                        try {
//                            if (isAdded()) {
//                                fillNATermSpinner();
//                            }
//                        } catch (Exception ex) {
//
//                        }
//
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                // Utils.dismissDialog();
//                fragmentMisBinding.progressAccount.setVisibility(View.GONE);
//                error.printStackTrace();
//                error.getMessage();
//                Utils.ping(mContext, getString(R.string.something_wrong));
//            }
//        });
//
//    }

    private void callNAMISDataApi(String requestType) {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        //  Utils.showDialog(getActivity());
        fragmentMisBinding.progressNa.setVisibility(View.VISIBLE);

        ApiHandler.getApiService().getMISdata(getNAParams(requestType), new retrofit.Callback<MISModel>() {
            @Override
            public void success(MISModel staffSMSDataModel, Response response) {
                //Utils.dismissDialog();
                fragmentMisBinding.progressNa.setVisibility(View.GONE);

                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {

                    try {
                        if (isAdded()) {

                            fillSchoolCalendarSpinner();
                            //callMessgeMISDataApi("Message");
                        }
                    } catch (Exception ex) {

                    }

                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {


                    try {

                        na_inquiry_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getTotalInquiry()));
                        na_issueadd_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getIssueAddmissionForm()));
                        na_rcvform_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getRcvAddmissionForm()));
                        na_CallForInterview_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getCallForInterview()));
                        na_comeforinterview_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getComeForInterview()));
                        na_confirmaddmission_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getConfirmAddmission()));
                        na_rejected_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getRejectedInquiry()));
                        na_fees_rec_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getInquiryFeesRcvd()));
                        na_fees_not_rec_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getFeesNotPaid()));

                        fragmentMisBinding.progressNa.setVisibility(View.GONE);
                        fragmentMisBinding.LLNacontainer.setVisibility(View.VISIBLE);
                        isFirmtimeLoad = false;

                        try {
                            if (isAdded()) {

                                fillSchoolCalendarSpinner();
                                //callMessgeMISDataApi("Message");
                            }
                        } catch (Exception ex) {

                        }


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                // Utils.dismissDialog();
                fragmentMisBinding.progressNa.setVisibility(View.GONE);

                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }


//    private void callMessgeMISDataApi(String requestType) {
//
//        if (!Utils.checkNetwork(mContext)) {
//            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
//            return;
//        }
//
//        // Utils.showDialog(getActivity());
//        ApiHandler.getApiService().getMISdata(getParams(requestType), new retrofit.Callback<MISModel>() {
//            @Override
//            public void success(MISModel staffSMSDataModel, Response response) {
//                // Utils.dismissDialog();
//                fragmentMisBinding.progressMsg.setVisibility(View.GONE);
//
//                if (staffSMSDataModel == null) {
//                    if (isAdded()) {
//                        Utils.ping(mContext, getString(R.string.something_wrong));
//                    }
//                    return;
//                }
//                if (staffSMSDataModel.getSuccess() == null) {
//                    if (isAdded()) {
//                        Utils.ping(mContext, getString(R.string.something_wrong));
//                    }
//                    return;
//                }
//                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
//                    if (isAdded()) {
//                        Utils.ping(mContext, getString(R.string.false_msg));
//                    }
//                    return;
//                }
//                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {
//
//                    if (isAdded()) {
//                        fragmentMisBinding.progressMsg.setVisibility(View.GONE);
//                        fragmentMisBinding.LLMsgcontainer.setVisibility(View.VISIBLE);
//
//                        try {
//                            smsdelivered_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getSMSDelivered()));
//                            smspedning_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getSMSPending()));
//                            smssent_txt.setText(String.valueOf(staffSMSDataModel.getFinalArray().get(0).getSMSSent()));
//                            isFirmtimeLoad = false;
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                // Utils.dismissDialog();
//                fragmentMisBinding.progressMsg.setVisibility(View.GONE);
//
//                error.printStackTrace();
//                error.getMessage();
//                Utils.ping(mContext, getString(R.string.something_wrong));
//            }
//        });
//
//    }

    private Map<String, String> getFinanceListParams(String termId) {
        Map<String, String> map = new HashMap<>();
        map.put("TermID", String.valueOf(termId));
        map.put("stop", "");
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    private Map<String, String> getTopperListParams() {
        Map<String, String> map = new HashMap<>();
        map.put("TermDetailID", String.valueOf(FinalSchoolResultTermID));
        map.put("stop", "");
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    private Map<String, String> getStudentParams(String reqType, String date) {
        Map<String, String> map = new HashMap<>();
        map.put("Date", date);
        map.put("TermID", FinalFinanaceTermId);
        map.put("RequestType", reqType);
        map.put("stop", "");
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));

        Log.i("student request", "SomeText: " + new Gson().toJson(map));

        return map;
    }


//    private Map<String, String> getParams(String params) {
//        Map<String, String> map = new HashMap<>();
//        map.put("Date", Utils.getTodaysDate());
//        map.put("TermID", FinalTermIdStr);
//        map.put("RequestType", params);
//        return map;
//    }

    private Map<String, String> getNAParams(String params) {
        Map<String, String> map = new HashMap<>();
        map.put("Date", Utils.getTodaysDate());
        map.put("TermID", FinalNATermID);
        map.put("RequestType", params);
        map.put("stop", "");
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    private Map<String, String> getTaskReportParams(String date) {
        Map<String, String> map = new HashMap<>();
        map.put("Date", date);
        map.put("TermID", FinalFinanaceTermId);
        map.put("stop", "");
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    private Map<String, String> getStaffNewParams(String date) {
        Map<String, String> map = new HashMap<>();
        map.put("Date", date);
        map.put("TermID", FinalFinanaceTermId);
        map.put("stop", "");
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tv_transport:

                fragment = new MISStudentTransportFragment();
                bundle = new Bundle();
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("stdID", "0");

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out).add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;

//                fragment = new MISStudentTransportFragment();
//                bundle = new Bundle();
//                bundle.putString("TermID", FinalTermIdStr);
//
//                fragment.setArguments(bundle);
//                fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
//                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
//                AppConfiguration.firsttimeback = true;
//                AppConfiguration.position = 67
//;

                break;

            case R.id.student_total_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "All Student");
                bundle.putString("requestType", "Total");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());
                bundle.putString("Gender", "");

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;

                break;

            case R.id.boy_total_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Boys");
                bundle.putString("requestType", "Total");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());
                bundle.putString("Gender", "boys");

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;

                break;

            case R.id.girl_total_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Girls");
                bundle.putString("requestType", "Total");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());
                bundle.putString("Gender", "girls");

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;

                break;

            case R.id.student_present_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "All Student");
                bundle.putString("requestType", "Present");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());
                bundle.putString("Gender", "");

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.boy_present_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Boys");
                bundle.putString("requestType", "Present");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());
                bundle.putString("Gender", "boys");

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.girl_present_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Girls");
                bundle.putString("requestType", "Present");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());
                bundle.putString("Gender", "girls");

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.student_leave_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "All Student");
                bundle.putString("requestType", "Leave");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());
                bundle.putString("Gender", "");

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.boy_leave_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Boys");
                bundle.putString("requestType", "Leave");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());
                bundle.putString("Gender", "boys");

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.girl_leave_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Girls");
                bundle.putString("requestType", "Leave");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());
                bundle.putString("Gender", "girls");

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.student_absent_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "All Student");
                bundle.putString("requestType", "Absent");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());
                bundle.putString("Gender", "");

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.boy_absent_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Boys");
                bundle.putString("requestType", "Absent");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());
                bundle.putString("Gender", "boys");

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.girl_absent_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Girls");
                bundle.putString("requestType", "Absent");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());
                bundle.putString("Gender", "girls");

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.student_attendance_less_70_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "All Student");
                bundle.putString("requestType", "Attendance less then 70%");
                bundle.putString("requestTitle", "Attendance less then 70%");
                bundle.putString("countdata", student_attendance_less_70_txt.getText().toString());
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Gender", "");
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.boy_attendance_less_70_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Boys");
                bundle.putString("requestType", "Attendance less then 70%");
                bundle.putString("requestTitle", "Attendance less then 70%");
                bundle.putString("countdata", student_attendance_less_70_txt.getText().toString());
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Gender", "boys");
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.girl_attendance_less_70_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Girls");
                bundle.putString("requestType", "Attendance less then 70%");
                bundle.putString("requestTitle", "Attendance less then 70%");
                bundle.putString("countdata", student_attendance_less_70_txt.getText().toString());
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Gender", "girls");
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.antstudent_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "All Student");
                bundle.putString("requestType", "ANT");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Gender", "");
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.antboy_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Boys");
                bundle.putString("requestType", "ANT");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Gender", "boys");
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.antgirl_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Girls");
                bundle.putString("requestType", "ANT");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Gender", "girls");
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.abstudent_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "All Student");
                bundle.putString("requestType", "ConsistentAbsent");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Gender", "");
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.ba_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "All Student");
                bundle.putString("requestType", "Between alumini left");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Gender", "");
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.abboy_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Boys");
                bundle.putString("requestType", "ConsistentAbsent");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Gender", "boys");
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.baboy_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Boys");
                bundle.putString("requestType", "Between alumini left");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Gender", "boys");
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.abgirl_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Girls");
                bundle.putString("requestType", "ConsistentAbsent");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Gender", "girls");
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.bagirl_txt:
                fragment = new MISStudentListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Girls");
                bundle.putString("requestType", "Between alumini left");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Gender", "girls");
                bundle.putString("Date", fragmentMisBinding.studentDateBtn.getText().toString());

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.staff_total_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Staff");
                bundle.putString("requestType", "Total");
                bundle.putString("requestTitle", "Total Staff");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", Utils.getTodaysDate());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.staff_present_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Staff");
                bundle.putString("requestType", "Present");
                bundle.putString("requestTitle", "Present");

                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", staff_present_txt.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;


            case R.id.absent_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Staff");
                bundle.putString("requestType", "Absent");
                bundle.putString("requestTitle", "Absent");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", absent_txt.getText().toString());

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;


            case R.id.staffleave_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Staff");
                bundle.putString("requestType", "Leave");
                bundle.putString("requestTitle", "Leave");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", staffleave_txt.getText().toString());

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;


            case R.id.antstaff_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Staff");
                bundle.putString("requestType", "A.N.T.");
                bundle.putString("requestTitle", "A.N.T. Class Teacher");

                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", antstaff_txt.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.staff_workplan_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Staff");
                bundle.putString("requestType", "Work Plan");
                bundle.putString("requestTitle", "Work Plan Submitted");

                bundle.putString("countdata", staff_workplan_txt.getText().toString());
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", Utils.getTodaysDate());

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;


            case R.id.staff_cw_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Staff");
                bundle.putString("requestType", "CW Submitted");
                bundle.putString("requestTitle", "Classwork Submitted");

                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", staff_cw_txt.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.staff_hw_submitted_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Staff");
                bundle.putString("requestType", "HW Submitted");
                bundle.putString("requestTitle1", "HomeWork Submitted");
                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", staff_hw_submitted_txt.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.actotaltobecall_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Account");
                bundle.putString("requestType", "Total To Be Collect");
                bundle.putString("requestTitle", "Total To Be Collect");

                AppConfiguration.acReqTitle = "Total To Be Collect";

                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", actotaltobecall_txt.getText().toString());
                AppConfiguration.CountdataAccount = actotaltobecall_txt.getText().toString();

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;


            case R.id.acterm1fess_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Account");
                bundle.putString("requestType", "Term 1 Fees");
                bundle.putString("requestTitle", "Term 1 Fees");

                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", acterm1fess_txt.getText().toString());
                AppConfiguration.CountdataAccount = acterm1fess_txt.getText().toString();
                AppConfiguration.acReqTitle = "Term 1 Fees";

                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;


            case R.id.acterm2fess_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Account");
                bundle.putString("requestType", "Term 2 Fees");
                bundle.putString("requestTitle", "Term 2 Fees");
                AppConfiguration.acReqTitle = "Term 2 Fees";

                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", acterm2fess_txt.getText().toString());
                AppConfiguration.CountdataAccount = acterm2fess_txt.getText().toString();
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.acterm1_collection:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Account");
                bundle.putString("requestType", "Term 1 Collection");
                bundle.putString("requestTitle", "Term 1 Collection");
                AppConfiguration.acReqTitle = "Term 1 Collection";

                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", acterm1_collection.getText().toString());
                AppConfiguration.CountdataAccount = acterm1_collection.getText().toString();
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;


            case R.id.acterm2_collection:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Account");
                bundle.putString("requestType", "Term 2 Collection");
                bundle.putString("requestTitle", "Term 2 Collection");
                AppConfiguration.acReqTitle = "Term 2 Collection";

                bundle.putString("TermID", FinalFinanaceTermId);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", acterm2_collection.getText().toString());
                AppConfiguration.CountdataAccount = acterm2_collection.getText().toString();
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.na_inquiry_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "New Addmission");
                bundle.putString("requestType", "Inquiry");
                bundle.putString("requestTitle", "Inquiry");
                bundle.putString("TermID", FinalNATermID);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", na_inquiry_txt.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;
            case R.id.na_issueadd_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "New Addmission");
                bundle.putString("requestType", "Issue Addmission Form");
                bundle.putString("requestTitle", "Issue Addmission Form");
                bundle.putString("TermID", FinalNATermID);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", na_issueadd_txt.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.na_rcvform_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "New Addmission");
                bundle.putString("requestType", "Rcv Form");
                bundle.putString("requestTitle", "Recieved Form");
                bundle.putString("TermID", FinalNATermID);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", na_rcvform_txt.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.na_CallForInterview_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "New Addmission");
                bundle.putString("requestType", "Call for Interview");
                bundle.putString("requestTitle", "Call for Interview");
                bundle.putString("TermID", FinalNATermID);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", na_CallForInterview_txt.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.na_comeforinterview_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "New Addmission");
                bundle.putString("requestType", "Come for Interview");
                bundle.putString("requestTitle", "Come for Interview");
                bundle.putString("TermID", FinalNATermID);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", na_comeforinterview_txt.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.na_confirmaddmission_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "New Addmission");
                bundle.putString("requestType", "Confirm Addmission");
                bundle.putString("requestTitle", "Confirm Addmission");
                bundle.putString("TermID", FinalNATermID);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", na_confirmaddmission_txt.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.na_rejected_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "New Addmission");
                bundle.putString("requestType", "Rejected");
                bundle.putString("requestTitle", "Rejected");
                bundle.putString("TermID", FinalNATermID);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", na_rejected_txt.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;
            case R.id.na_fees_rec_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "New Addmission");
                bundle.putString("requestType", "Fees Received");
                bundle.putString("requestTitle", "Fees Received");
                bundle.putString("TermID", FinalNATermID);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", na_fees_rec_txt.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.na_fees_not_rec_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "New Addmission");
                bundle.putString("requestType", "FeesNotPaid");
                bundle.putString("requestTitle", "Fees Not Received");
                bundle.putString("TermID", FinalNATermID);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", na_fees_not_rec_txt.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.smssent_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Message");
                bundle.putString("requestType", "Sent");
                bundle.putString("requestTitle", "SMS Sent");
                bundle.putString("TermID", FinalTermIdStr);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", smssent_txt.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.smsdelivered_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Message");
                bundle.putString("requestType", "Delivered");
                bundle.putString("requestTitle", "SMS Sent");
                bundle.putString("TermID", FinalTermIdStr);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", smsdelivered_txt.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

            case R.id.smspedning_txt:
                fragment = new MISDataListFragment();
                bundle = new Bundle();
                bundle.putString("title", "Message");
                bundle.putString("requestType", "Pending");
                bundle.putString("requestTitle", "SMS Pending");
                bundle.putString("TermID", FinalTermIdStr);
                bundle.putString("Date", Utils.getTodaysDate());
                bundle.putString("countdata", smspedning_txt.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;
                break;

        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        populateSetDate(year, monthOfYear, dayOfMonth);
    }

    public void populateSetDate(int year, int month, int day) {
        String d, m, y;
        d = Integer.toString(day);
        m = Integer.toString(month + 1);
        y = Integer.toString(year);
        if (day < 10) {
            d = "0" + d;
        }


        if (month < 9) {
            m = "0" + m;
        }


        dateFinal = d + "/" + m + "/" + y;

        if (whichDateClicked == 1) {
            isRequireCallNestedAPI = false;
            fragmentMisBinding.studentDateBtn.setText(dateFinal);
            fragmentMisBinding.progressStudent.setVisibility(View.VISIBLE);
            fragmentMisBinding.LLStudentcontainer.setVisibility(View.GONE);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after  2 secs
                    try {
                        callStudentMISDataApi("Student", fragmentMisBinding.studentDateBtn.getText().toString(), FinalFinanaceTermId);
                    } catch (Exception ex) {

                    }
                }
            }, 2000);

        } else if (whichDateClicked == 2) {
            isRequireCallNestedAPI = false;
            fragmentMisBinding.staffDateBtn.setText(dateFinal);
            AppConfiguration.staffDate = fragmentMisBinding.staffDateBtn.getText().toString();

            fragmentMisBinding.progressStaff1.setVisibility(View.VISIBLE);
//            fragmentMisBinding.staffNewInnercontainer.setVisibility(View.GONE);
            fragmentMisBinding.linearRecylerGrid.setVisibility(View.GONE);
            fragmentMisBinding.linearRecylerList.setVisibility(View.GONE);
            fragmentMisBinding.linearStaffDate.setVisibility(View.GONE);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after  2 secs
                    try {
                        callNewStafftMISDataApi(fragmentMisBinding.staffDateBtn.getText().toString(), "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 2000);
        } else if (whichDateClicked == 3) {
            isRequireCallNestedAPI = false;
            fragmentMisBinding.taskreportDateBtn.setText(dateFinal);
            AppConfiguration.taskReportDate = fragmentMisBinding.taskreportDateBtn.getText().toString();

            fragmentMisBinding.progressTaskReport.setVisibility(View.VISIBLE);
            fragmentMisBinding.taskReportInnercontainer.setVisibility(View.GONE);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after  2 secs
                    try {
                        callTaskReportMISDataApi(fragmentMisBinding.taskreportDateBtn.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 2000);
        }

    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener(final ViewPager pager) {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

    private void callSchoolCalendar(String term) {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        llCalendar.setVisibility(View.GONE);

        WebServices apiService = ApiClient.getClient().create(WebServices.class);
        Call<MIStudentWiseCalendarModel> call = apiService.getSchoolCalendarDetail(AppConfiguration.BASEURL + "GetMISCalender?TermID=" + term + "&stop=" + "&LocationID=" + PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        call.enqueue(new Callback<MIStudentWiseCalendarModel>() {
            //
            @Override
            public void onResponse(Call<MIStudentWiseCalendarModel> call, retrofit2.Response<MIStudentWiseCalendarModel> response) {
                Utils.dismissDialog();
//                if (response.body() == null) {
//                    progressBar.setVisibility(View.GONE);
//                    llCalendar.setVisibility(View.VISIBLE);
//                    Utils.ping(mContext, getString(R.string.something_wrong));
//                    return;
//                }
//                if (response.body().getSuccess() == null) {
//                    llCalendar.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
//                    Utils.ping(mContext, getString(R.string.something_wrong));
//                    return;
//                }
//                if (response.body().getSuccess().equalsIgnoreCase("false")) {
//                    llCalendar.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
//                    Utils.ping(mContext, getString(R.string.false_msg));
//                    return;
//                }
//                if (response.body().getSuccess().equalsIgnoreCase("True")) {

                llCalendar.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

                if (tabLayout != null && viewPager != null && progressBar != null) {

                    try {

                        List<MIStudentWiseCalendarModel.FinalArray> data1 = response.body().getFinalarray();

//                            for (int i = 0; i < termModel.getFinalarray().size(); i++) {
//                                data1.add(termModel.getFinalarray().get(i).getType());
//                            }

                        tabsTitles = new String[data1.size()];

                        if (tabLayout.getTabCount() > 0) {
                            tabLayout.removeAllTabs();
                        }

                        for (int count = 0; count < data1.size(); count++) {
                            tabLayout.addTab(tabLayout.newTab().setText(data1.get(count).getType()));
                            tabsTitles[count] = data1.get(count).getType();
                        }

                        if (data1.size() > 0) {
                            ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), data1.size(), data1, tabsTitles);
                            viewPager.setAdapter(adapter);
                            viewPager.setCurrentItem(0);
                            tabLayout.setupWithViewPager(viewPager);

                            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                }

                                @Override
                                public void onPageSelected(int position) {
                                    viewPager.setCurrentItem(position);
                                    //  tabLayout.getTabAt(position).select();

                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
//                }
            }

            @Override
            public void onFailure(Call<MIStudentWiseCalendarModel> call, Throwable t) {
                Utils.dismissDialog();
                tabLayout.setVisibility(View.GONE);
                viewPager.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });
    }
}
