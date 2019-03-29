package com.skool360admin.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.StudentAttemndanceSummaryAdapter;
import anandniketan.com.bhadajadmin.Model.Student.ConsistentAbsentStudentModel;
import anandniketan.com.bhadajadmin.Model.Student.StandardWiseAttendanceModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentAttendaceSummaryBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AttendaceSummaryFragment extends Fragment {

    int Year, Month, Day;
    Calendar calendar;
    StudentAttemndanceSummaryAdapter studentAttemndanceSummaryAdapter;
    List<StudentAttendanceFinalArray> studentAttendanceFinalArrayList;
    List<StandardWiseAttendanceModel> standardWiseAttendanceModelList;
    List<ConsistentAbsentStudentModel> consistentAbsentStudentModelList;
    ArrayList<String> standardCount;
    private FragmentAttendaceSummaryBinding fragmentAttendaceSummaryBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private String Datestr;

    public AttendaceSummaryFragment() {
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentAttendaceSummaryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_attendace_summary, container, false);

        rootView = fragmentAttendaceSummaryBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        initViews();
        setListner();

        return rootView;
    }

    public void initViews() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        Datestr = Utils.getTodaysDate();
        Log.d("TodayDate", Datestr);

        callStudentApi();

    }

    public void setListner() {
        fragmentAttendaceSummaryBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentAttendaceSummaryBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 11;

                Fragment fragment = new StudentFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
    }

    // CALL Student Attendace API HERE
    private void callStudentApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStudentAttendace(getStudentDetail(), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel studentUser, Response response) {
                Utils.dismissDialog();
                if (studentUser == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentUser.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentUser.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (studentUser.getSuccess().equalsIgnoreCase("True")) {
                    if (studentUser.getFinalArray() != null) {
                        studentAttendanceFinalArrayList = studentUser.getFinalArray();
                    }
                    if (studentUser.getStandardWiseAttendance() != null) {
                        standardCount = new ArrayList<>();
                        standardCount.add("1");
                        standardWiseAttendanceModelList = studentUser.getStandardWiseAttendance();
                    }
                    if (studentUser.getConsistentAbsent() != null) {
                        consistentAbsentStudentModelList = studentUser.getConsistentAbsent();
                    }
                    studentAttemndanceSummaryAdapter = new StudentAttemndanceSummaryAdapter(mContext, studentAttendanceFinalArrayList, standardWiseAttendanceModelList, consistentAbsentStudentModelList);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false);
                    fragmentAttendaceSummaryBinding.studentAttendanceListRcv.setLayoutManager(mLayoutManager);
                    fragmentAttendaceSummaryBinding.studentAttendanceListRcv.setItemAnimator(new DefaultItemAnimator());
                    fragmentAttendaceSummaryBinding.studentAttendanceListRcv.setAdapter(studentAttemndanceSummaryAdapter);
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

    private Map<String, String> getStudentDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Date", Datestr);
        return map;
    }
}
