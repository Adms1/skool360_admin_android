package anandniketan.com.skool360.Fragment.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.HomeworkAdapter;
import anandniketan.com.skool360.Interface.getEmployeeCheck;
import anandniketan.com.skool360.Model.Staff.FinalArrayStaffModel;
import anandniketan.com.skool360.Model.Staff.HomeWorkModel;
import anandniketan.com.skool360.Model.Staff.StaffAttendaceModel;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiClient;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.Utility.WebServices;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class HomeworkNotDoneFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static String dateFinal;
    String homeworkIdstr;
    String homeworkdetailidstr = "";
    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    private TextView tvHeader, tvNoRecords;
    private Button btnBack, btnMenu, btnSumbit;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    HashMap<Integer, String> spinnerTermMap;
    private Button btnDate, btnSearch;
    private List<FinalArrayStaffModel> finalArrayTeachersModelList, finalArrayGradeModelList = new ArrayList<>(), finalArraySubjectModelList = new ArrayList<>();
    private HashMap<Integer, String> spinnerTeacherMap, spinnerStandardMap, spinnerSubjectMap;
    private DatePickerDialog datePickerDialog;
    private int Year, Month, Day;
    private Calendar calendar;
    private RecyclerView rvList;
    private boolean temp = false, temp1 = false;
    private HomeworkAdapter homeworkAdapter;
    private RadioButton rbDone, rbNotdone;
    private RadioGroup rg;
    private LinearLayout llHeader;
    private ArrayList<HomeWorkModel.HomeworkFinalArray> finalArrays;
    private String finalTermIdStr, FinalTeacherIdStr, FinalStandardIdStr, FinalTeacherid, FinalSubjectIdStr, FinalStandardId, FinalClassId, FinalSubjectId;
    private Spinner teacherSpinner, gradeSpinner, subjectSpinner, termSpinner;

    public HomeworkNotDoneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homework_not_done, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);
        teacherSpinner = view.findViewById(R.id.spTeachers);
        gradeSpinner = view.findViewById(R.id.spGrade);
        subjectSpinner = view.findViewById(R.id.spSubject);
        btnDate = view.findViewById(R.id.btnDate);
        btnSearch = view.findViewById(R.id.btnSearch);
        rvList = view.findViewById(R.id.homework_list);
        rbDone = view.findViewById(R.id.done_txt);
        rbNotdone = view.findViewById(R.id.notdone_txt);
        rg = view.findViewById(R.id.rgHomework);
        llHeader = view.findViewById(R.id.list_header);
        tvNoRecords = view.findViewById(R.id.txtNoRecords);
        btnSumbit = view.findViewById(R.id.btnSubmit);
        termSpinner = view.findViewById(R.id.term_spinner);

        rvList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        tvHeader.setText(R.string.homeworknotdone);

        setListners();
        callTermApi();
        callTeacherApi();

    }

    public void setListners() {

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        try {
            String currentDateTime = Utils.getCurrentDateTime("dd/MM/yyyy");
            btnDate.setText(currentDateTime);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = DatePickerDialog.newInstance(HomeworkNotDoneFragment.this, Year, Month, Day);
                datePickerDialog.setMaxDate(calendar);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "DatePickerDialog");
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                AppConfiguration.position = 55;
                fragment = new StaffFragment();
                fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                AppConfiguration.firsttimeback = true;

            }
        });

        teacherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = teacherSpinner.getSelectedItem().toString();
                String getid = spinnerTeacherMap.get(teacherSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTeacherIdStr = getid;
                FinalTeacherid = finalArrayTeachersModelList.get(position).getPkEmployeeID().toString();
                Log.d("FinalTeacherIdStr", FinalTeacherIdStr);

                callStandardApi(finalArrayTeachersModelList.get(position).getPkEmployeeID().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                finalTermIdStr = getid;
                Log.d("FinalTermIdStr", finalTermIdStr);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = gradeSpinner.getSelectedItem().toString();
                String getid = spinnerStandardMap.get(gradeSpinner.getSelectedItemPosition());


                Log.d("value", name + " " + getid);
                FinalStandardIdStr = getid;
                FinalStandardId = finalArrayGradeModelList.get(position).getStandardId().toString();
                FinalClassId = finalArrayGradeModelList.get(position).getClassid();
                Log.d("FinalTeacherIdStr", FinalStandardIdStr);

                callSubjectApi(FinalTeacherid, finalArrayGradeModelList.get(position).getStandardId().toString(), finalArrayGradeModelList.get(position).getClassid());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = subjectSpinner.getSelectedItem().toString();
                String getid = spinnerSubjectMap.get(subjectSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalSubjectIdStr = getid;
                FinalSubjectId = finalArraySubjectModelList.get(position).getSubjectID().toString();
                Log.d("FinalTeacherIdStr", FinalSubjectIdStr);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHomeworknotdoneApi();
            }
        });

        btnSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSubmitHomeworkApi();
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb = group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    switch (checkedId) {
                        case R.id.done_txt:
//                        if (isChecked) {
                            for (int i = 0; i < finalArrays.size(); i++) {
//                                finalArrays.get(i).setDonecheck("1");
                                finalArrays.get(i).setHomeworkstatus("1");
//                                finalArrays.get(i).setNotdonecheck("0");
                            }
                            homeworkAdapter.notifyDataSetChanged();
                            temp = false;
//                        } else {
//                            if (!temp) {
//                                for (int i = 0; i < finalArrayBulkSMSModelList.size(); i++) {
//                                    finalArrayBulkSMSModelList.get(i).setCheck("0");
//                                }
//                                bulkSMSDetailListAdapter.notifyDataSetChanged();
//                            }
//                        }
                            break;

                        case R.id.notdone_txt:
//                        if (isChecked) {
                            for (int i = 0; i < finalArrays.size(); i++) {
//                                finalArrays.get(i).setNotdonecheck("1");
//                                finalArrays.get(i).setDonecheck("0");
                                finalArrays.get(i).setHomeworkstatus("0");
                            }
                            homeworkAdapter.notifyDataSetChanged();
                            temp = false;
//                        } else {
//                            if (!temp) {
//                                for (int i = 0; i < finalArrayBulkSMSModelList.size(); i++) {
//                                    finalArra yBulkSMSModelList.get(i).setCheck("0");
//                                }
//                                bulkSMSDetailListAdapter.notifyDataSetChanged();
//                            }
//                        }
                            break;
                    }
                }
//                switch (checkedId) {
//                    case R.id.done_txt:
//                        if (rbDone.isChecked()) {
//                            for (int i = 0; i < finalArrays.size(); i++) {
//
//                                finalArrays.get(i).setHomeworkstatus("1");
//
//                                finalArrays.get(i).setDonecheck("1");
//                                finalArrays.get(i).setNotdonecheck("0");
//                                rbNotdone.setChecked(false);
//                            }
//
//                            rvList.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    homeworkAdapter.notifyDataSetChanged();
//                                }
//                            });
//
//                            temp = false;
//                        } else {
//                            if (!temp) {
//                                for (int i = 0; i < finalArrays.size(); i++) {
//
//                                    finalArrays.get(i).setHomeworkstatus("0");
//
//                                    finalArrays.get(i).setDonecheck("0");
//                                    finalArrays.get(i).setNotdonecheck("0");
//                                }
//                                rvList.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        homeworkAdapter.notifyDataSetChanged();
//                                    }
//                                });
//                            }
//                        }
//                        break;
//
//                    case R.id.notdone_txt:
//                        if (rbNotdone.isChecked()) {
//                            for (int i = 0; i < finalArrays.size(); i++) {
//
//                                finalArrays.get(i).setHomeworkstatus("0");
//
//                                finalArrays.get(i).setDonecheck("0");
//                                finalArrays.get(i).setNotdonecheck("1");
//
//                                rbDone.setChecked(false);
//
//                            }
//                            rvList.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    homeworkAdapter.notifyDataSetChanged();
//                                }
//                            });
//                            temp1 = false;
//                        } else {
//                            if (!temp1) {
//                                for (int i = 0; i < finalArrays.size(); i++) {
//
//                                    finalArrays.get(i).setHomeworkstatus("1");
//
//                                    finalArrays.get(i).setDonecheck("0");
//                                    finalArrays.get(i).setNotdonecheck("0");
//                                }
//                                rvList.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        homeworkAdapter.notifyDataSetChanged();
//                                    }
//                                });
//                            }
//                        }
//                        break;
//                }
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        populateSetDate(year, monthOfYear + 1, dayOfMonth);
    }

    public void populateSetDate(int year, int month, int day) {
        String d, m, y;
        d = Integer.toString(day);
        m = Integer.toString(month);
        y = Integer.toString(year);
        if (day < 10) {
            d = "0" + d;
        }
        if (month < 10) {
            m = "0" + m;
        }

        dateFinal = d + "/" + m + "/" + y;

        btnDate.setText(dateFinal);

    }


    // CALL Teacher API HERE
    private void callTeacherApi() {

        if (!Utils.checkNetwork(Objects.requireNonNull(getActivity()))) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTeachers(getTeacherDetail(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel teachersModel, Response response) {
                Utils.dismissDialog();
                if (teachersModel == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (teachersModel.getSuccess() == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (teachersModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(getActivity(), getString(R.string.false_msg));
                    return;
                }
                if (teachersModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayTeachersModelList = teachersModel.getFinalArray();
                    if (finalArrayTeachersModelList != null) {
                        fillTeacherSpinner();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(getActivity(), getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getTeacherDetail() {
        HashMap<String, String> map = new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    private void callStandardApi(String empid) {
        if (!Utils.checkNetwork(Objects.requireNonNull(getActivity()))) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStandardTeacher(getStandardDetail(empid), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel teachersModel, Response response) {
                Utils.dismissDialog();
                if (teachersModel == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (teachersModel.getSuccess() == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (teachersModel.getSuccess().equalsIgnoreCase("false")) {
                    finalArrayGradeModelList.clear();
                    finalArraySubjectModelList.clear();

                    final ArrayAdapter adb = new ArrayAdapter(getActivity(), R.layout.spinner_layout, finalArrayGradeModelList);
                    gradeSpinner.setAdapter(adb);

                    final ArrayAdapter adb1 = new ArrayAdapter(getActivity(), R.layout.spinner_layout, finalArraySubjectModelList);
                    subjectSpinner.setAdapter(adb1);
                    return;
                }
                if (teachersModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayGradeModelList = teachersModel.getFinalArray();

                    callSubjectApi(FinalTeacherid, finalArrayGradeModelList.get(0).getStandardId().toString(), finalArrayGradeModelList.get(0).getClassid());


                    if (finalArrayGradeModelList != null) {
                        fillGradeSpinner();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(getActivity(), getString(R.string.something_wrong));
            }
        });
    }

    private Map<String, String> getStandardDetail(String empid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("EmployeeID", empid);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    private void callSubjectApi(String empid, String stdid, String classid) {
        if (!Utils.checkNetwork(Objects.requireNonNull(getActivity()))) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getSubjectByTeacher(getSubjectDetail(empid, stdid, classid), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel teachersModel, Response response) {
                Utils.dismissDialog();
                if (teachersModel == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (teachersModel.getSuccess() == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (teachersModel.getSuccess().equalsIgnoreCase("false")) {
                    finalArraySubjectModelList.clear();
                    final ArrayAdapter adb = new ArrayAdapter(getActivity(), R.layout.spinner_layout, finalArraySubjectModelList);
                    subjectSpinner.setAdapter(adb);
                    return;
                }
                if (teachersModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArraySubjectModelList = teachersModel.getFinalArray();
                    if (finalArraySubjectModelList != null) {
                        fillSubjectSpinner();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(getActivity(), getString(R.string.something_wrong));
            }
        });
    }

    private Map<String, String> getSubjectDetail(String empid, String stdid, String classid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("EmployeeID", empid);
        map.put("StandardID", stdid);
        map.put("ClassID", classid);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    public void callHomeworknotdoneApi() {
        if (!Utils.checkNetwork(Objects.requireNonNull(getActivity()))) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getHomeDetail(getHomeworkDetail(), new retrofit.Callback<HomeWorkModel>() {
            @Override
            public void success(HomeWorkModel homeWorkModel, Response response) {
                Utils.dismissDialog();
                if (homeWorkModel == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    llHeader.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                    btnSumbit.setVisibility(View.GONE);
                    tvNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (homeWorkModel.getSuccess() == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    llHeader.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                    btnSumbit.setVisibility(View.GONE);
                    tvNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (homeWorkModel.getSuccess().equalsIgnoreCase("false")) {
                    llHeader.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                    btnSumbit.setVisibility(View.GONE);
                    tvNoRecords.setVisibility(View.VISIBLE);
                    //                    finalArraySubjectModelList.clear();
//                    final ArrayAdapter adb = new ArrayAdapter(getActivity(), R.layout.spinner_layout, finalArraySubjectModelList);
//                    subjectSpinner.setAdapter(adb);
                    return;
                }
                if (homeWorkModel.getSuccess().equalsIgnoreCase("True")) {

                    llHeader.setVisibility(View.VISIBLE);
                    rvList.setVisibility(View.VISIBLE);
                    btnSumbit.setVisibility(View.VISIBLE);
                    tvNoRecords.setVisibility(View.GONE);

                    finalArrays = homeWorkModel.getFinalarray();

//                    for (int k = 0; k < finalArrays.size(); k++) {
//                        finalArrays.get(k).setDonecheck("1");
//                        finalArrays.get(k).setNotdonecheck("0");
//                    }

                    homeworkAdapter = new HomeworkAdapter(getActivity(), homeWorkModel.getFinalarray(), new getEmployeeCheck() {
                        @Override
                        public void getEmployeeSMSCheck() {

//                            List<HomeWorkModel.HomeworkFinalArray> updatedData = homeworkAdapter.getDatas();
//                            Boolean data = false;
//                            int count = 0;
//
//                            for (int i = 0; i < updatedData.size(); i++) {
//                                if (updatedData.get(i).getDonecheck().equalsIgnoreCase("1")) {
//                                    data = true;
//                                    count++;
//                                } else {
//                                    count--;
//                                }
//
//                            }
//
//                            if (count == updatedData.size()) {
//                                rbDone.setChecked(true);
//                            } else {
//                                temp = true;
//                                rbDone.setChecked(false);
//                            }
//
////                            if (data) {
////                                submitBtn.setEnabled(true);
////                                submitBtn.setAlpha(1);
////                            } else {
////                                submitBtn.setEnabled(false);
////                                submitBtn.setAlpha((float) 0.5);
////                            }
                        }
                    },

                            new getEmployeeCheck() {
                                @Override
                                public void getEmployeeSMSCheck() {
//                                    List<HomeWorkModel.HomeworkFinalArray> updatedData = homeworkAdapter.getDatas();
//                                    Boolean data1 = false;
//                                    int count1 = 0;
//
//                                    for (int i = 0; i < updatedData.size(); i++) {
//
//                                        if (updatedData.get(i).getNotdonecheck().equalsIgnoreCase("1")) {
//                                            data1 = true;
//                                            count1++;
//                                        } else {
//                                            count1--;
//                                        }
//                                    }
//
//                                    if (count1 == updatedData.size()) {
//                                        rbNotdone.setChecked(false);
//                                    } else {
//                                        temp1 = true;
//                                        rbNotdone.setChecked(true);
//                                    }
//                            if (data) {
//                                submitBtn.setEnabled(true);
//                                submitBtn.setAlpha(1);
//                            } else {
//                                submitBtn.setEnabled(false);
//                                submitBtn.setAlpha((float) 0.5);
//                            }
                                }
                            });

                    rvList.setAdapter(homeworkAdapter);
                    homeworkAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                llHeader.setVisibility(View.GONE);
                rvList.setVisibility(View.GONE);
                btnSumbit.setVisibility(View.GONE);
                tvNoRecords.setVisibility(View.VISIBLE);
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(getActivity(), getString(R.string.something_wrong));
            }
        });
    }

    private Map<String, String> getHomeworkDetail() {
        HashMap<String, String> map = new HashMap<>();
        map.put("TermID", finalTermIdStr);
        map.put("Date", btnDate.getText().toString());
        map.put("StandardID", FinalStandardId);
        map.put("ClassID", FinalClassId);
        map.put("SubjectID", FinalSubjectId);
//        map.put("TeacherID", FinalTeacherid);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    private void callSubmitHomeworkApi() {

        ArrayList<String> newArray = new ArrayList<>();
        boolean isEnable = false;
        StringBuilder studentString = new StringBuilder();
        for (int j = 0; j < finalArrays.size(); j++) {
            homeworkIdstr = finalArrays.get(0).getHomeworkid();
            if (!isEnable) {
                studentString = new StringBuilder(String.valueOf(finalArrays.get(j).getHomeworkdetailid()) + ","
                        + finalArrays.get(j).getStudentid() + "," +
                        finalArrays.get(j).getHomeworkstatus());
                isEnable = true;
            } else {
                studentString.append("|").append(String.valueOf(finalArrays.get(j).getHomeworkdetailid())).append(",").append(finalArrays.get(j).getStudentid()).append(",").append(finalArrays.get(j).getHomeworkstatus());
            }
        }
        newArray.add(studentString.toString());
        homeworkdetailidstr = "";
        for (String s : newArray) {
            homeworkdetailidstr = homeworkdetailidstr + "," + s;
        }
        homeworkdetailidstr = homeworkdetailidstr.substring(1);


        if (!Utils.checkNetwork(Objects.requireNonNull(getActivity()))) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        Call<JsonObject> call = apiService.teacherStudentHomeworkStatusInsertUpdate(getHomeworkSubmit());
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull retrofit2.Response<JsonObject> response) {

                Utils.dismissDialog();
                if (response == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (response.body() == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (response.body().get("Success").toString().replaceAll("\"", "").equalsIgnoreCase("false")) {
                    Utils.ping(getActivity(), getString(R.string.false_msg));
                    return;
                }
                if (response.body().get("Success").toString().replaceAll("\"", "").equalsIgnoreCase("True")) {
                    Utils.ping(getActivity(), "Save Succesfully");
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Utils.dismissDialog();
                t.printStackTrace();
                t.getMessage();
                Utils.ping(getActivity(), getString(R.string.something_wrong));
            }
        });

    }

    private String getHomeworkSubmit() {

        return AppConfiguration.BASEURL + "TeacherStudentHomeworkStatusInsertUpdate?HomeWorkID=" + homeworkIdstr + "&HomeWorkDetailID=" + homeworkdetailidstr + "&StandardID=" + FinalStandardId + "&ClassID=" + FinalClassId + "&SubjectID=" + FinalSubjectId + "&Date=" + btnDate.getText().toString() + "&LocationID=" +  PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0");
    }

    //Use for fill TeacherNameSpinner
    public void fillTeacherSpinner() {
        ArrayList<Integer> TeacherId = new ArrayList<>();
        for (int i = 0; i < finalArrayTeachersModelList.size(); i++) {
            TeacherId.add(finalArrayTeachersModelList.get(i).getPkEmployeeID());
        }
        ArrayList<String> Teacher = new ArrayList<>();
        for (int j = 0; j < finalArrayTeachersModelList.size(); j++) {
            Teacher.add(finalArrayTeachersModelList.get(j).getTeacher());
        }

        String[] spinnerteacherIdArray = new String[TeacherId.size()];

        spinnerTeacherMap = new HashMap<>();
        for (int i = 0; i < TeacherId.size(); i++) {
            spinnerTeacherMap.put(i, String.valueOf(TeacherId.get(i)));
            spinnerteacherIdArray[i] = Teacher.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(teacherSpinner);

            popupWindow.setHeight(spinnerteacherIdArray.length > 4 ? 500 : spinnerteacherIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_layout, spinnerteacherIdArray);
        teacherSpinner.setAdapter(adapterTerm);
        FinalTeacherIdStr = spinnerTeacherMap.get(0);
    }

    //Use for fill TeacherNameSpinner
    public void fillGradeSpinner() {
        ArrayList<Integer> stdId = new ArrayList<>();
        for (int i = 0; i < finalArrayGradeModelList.size(); i++) {
            stdId.add(finalArrayGradeModelList.get(i).getStandardId());
        }

        ArrayList<String> classId = new ArrayList<>();
        for (int i = 0; i < finalArrayGradeModelList.size(); i++) {
            classId.add(finalArrayGradeModelList.get(i).getClassid());
        }

        ArrayList<String> Standard = new ArrayList<>();
        for (int j = 0; j < finalArrayGradeModelList.size(); j++) {
            Standard.add(finalArrayGradeModelList.get(j).getStandard());
        }

        String[] spinnerstandardIdArray = new String[stdId.size()];

        spinnerStandardMap = new HashMap<>();
        for (int i = 0; i < stdId.size(); i++) {
            spinnerStandardMap.put(i, String.valueOf(stdId.get(i)));
            spinnerstandardIdArray[i] = Standard.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(gradeSpinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 500 : spinnerstandardIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_layout, spinnerstandardIdArray);
        gradeSpinner.setAdapter(adapterTerm);
        FinalStandardIdStr = spinnerStandardMap.get(0);
    }

    //Use for fill TeacherNameSpinner
    public void fillSubjectSpinner() {

        ArrayList<Integer> subId = new ArrayList<>();
        for (int i = 0; i < finalArraySubjectModelList.size(); i++) {
            subId.add(finalArraySubjectModelList.get(i).getSubjectID());
        }

        ArrayList<String> subjectName = new ArrayList<>();
        for (int i = 0; i < finalArraySubjectModelList.size(); i++) {
            subjectName.add(finalArraySubjectModelList.get(i).getSubject());
        }

        String[] spinnersubjectIdArray = new String[subId.size()];

        spinnerSubjectMap = new HashMap<>();

        for (int i = 0; i < subId.size(); i++) {
            spinnerSubjectMap.put(i, String.valueOf(subId.get(i)));
            spinnersubjectIdArray[i] = subjectName.get(i).trim();
        }

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(subjectSpinner);

            popupWindow.setHeight(spinnersubjectIdArray.length > 4 ? 500 : spinnersubjectIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_layout, spinnersubjectIdArray);
        subjectSpinner.setAdapter(adapterTerm);
        FinalSubjectIdStr = spinnerSubjectMap.get(0);
    }

    // CALL Term API HERE
    private void callTermApi() {

        if (!Utils.checkNetwork(getContext())) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTerm(getTermDetail(), new retrofit.Callback<TermModel>() {
            @Override
            public void success(TermModel termModel, Response response) {
                Utils.dismissDialog();
                if (termModel == null) {
                    Utils.ping(getContext(), getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess() == null) {
                    Utils.ping(getContext(), getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(getContext(), getString(R.string.false_msg));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayGetTermModels = termModel.getFinalArray();
                    if (finalArrayGetTermModels != null) {
                        fillTermSpinner();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(getContext(), getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getTermDetail() {
        HashMap<String, String> map = new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    //Use for fill the Term Spinner
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
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, spinnertermIdArray);
        termSpinner.setAdapter(adapterTerm);
        termSpinner.setSelection(1);
        finalTermIdStr = spinnerTermMap.get(0);
    }

}
