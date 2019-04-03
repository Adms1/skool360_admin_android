package anandniketan.com.skool360.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.AttendanceAdapter;
import anandniketan.com.skool360.Model.Account.FinalArrayStandard;
import anandniketan.com.skool360.Model.Account.GetStandardModel;
import anandniketan.com.skool360.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.skool360.Model.Student.StudentAttendanceModel;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentStudentAttendaneBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class StudentAttendaneFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static String dateFinal;
    List<FinalArrayStandard> finalArrayStandardsList;
    HashMap<Integer, String> spinnerStandardMap;
    HashMap<Integer, String> spinnerSectionMap;
    List<StudentAttendanceFinalArray> finalArrayStudentNameModelList;
    String FinalStandardIdStr, FinalClassIdStr, StandardName, FinalStandardStr, FinalSectionStr, FinalDataStr;
    AttendanceAdapter attendanceAdapter;
    int Year, Month, Day;
    int mYear, mMonth, mDay;
    Calendar calendar;
    private FragmentStudentAttendaneBinding fragmentStudentAttendaneBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private DatePickerDialog datePickerDialog;
    private PrefUtils prefUtils;
    private String Attendanceidstr = "", Attendacestatusstr = "", studentidstr = "";
    private String status, updateStatus, update, FinalTermIdStr;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    private List<FinalArrayGetTermModel> finalArrayGetTermModels;
    private HashMap<Integer, String> spinnerTermMap;

    public StudentAttendaneFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStudentAttendaneBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_attendane, container, false);

        Bundle bundle = this.getArguments();
        status = bundle.getString("status");
        updateStatus = bundle.getString("updatestatus");

        prefUtils = PrefUtils.getInstance(getActivity());
        rootView = fragmentStudentAttendaneBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view1 = view.findViewById(R.id.header);
        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.attendace);

        setListners();
        callTermApi();
        callStandardApi();

    }

    public void setListners() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        fragmentStudentAttendaneBinding.dateButton.setText(Utils.getTodaysDate());

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StudentFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentStudentAttendaneBinding.ivAddUpdateAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (update.equalsIgnoreCase("yes")) {
                    if (updateStatus.equalsIgnoreCase("true")) {
                        callInsertAttendanceApi();
                    } else {
                        Utils.ping(getActivity(), "Access Denied");
                    }
                } else {
                    callInsertAttendanceApi();
                }
            }
        });
        fragmentStudentAttendaneBinding.gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentStudentAttendaneBinding.gradeSpinner.getSelectedItem().toString();
                String getid = spinnerStandardMap.get(fragmentStudentAttendaneBinding.gradeSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalStandardIdStr = getid;
                Log.d("FinalStandardIdStr", FinalStandardIdStr);
                StandardName = name;
                FinalStandardStr = name;
                Log.d("StandardName", StandardName);
                fillSection();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentStudentAttendaneBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentStudentAttendaneBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentStudentAttendaneBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid;
                Log.d("FinalTermIdStr", FinalTermIdStr);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        fragmentStudentAttendaneBinding.sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedsectionstr = fragmentStudentAttendaneBinding.sectionSpinner.getSelectedItem().toString();
                String getid = spinnerSectionMap.get(fragmentStudentAttendaneBinding.sectionSpinner.getSelectedItemPosition());

                Log.d("value", selectedsectionstr + " " + getid);
                FinalClassIdStr = getid;
                FinalSectionStr = selectedsectionstr;
                Log.d("FinalClassIdStr", FinalClassIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentStudentAttendaneBinding.dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = DatePickerDialog.newInstance(StudentAttendaneFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });
        fragmentStudentAttendaneBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (status.equalsIgnoreCase("true")) {

                    callAttendence_AdminApi();
                } else {
                    Utils.ping(getActivity(), "Access Denied");
                }
            }
        });
    }

    // CALL Standard API HERE
    private void callStandardApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStandardDetail(getStandardDetail(), new retrofit.Callback<GetStandardModel>() {
            @Override
            public void success(GetStandardModel standardModel, Response response) {
                Utils.dismissDialog();
                if (standardModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (standardModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayStandardsList = standardModel.getFinalArray();
                    if (finalArrayStandardsList != null) {
                        fillGradeSpinner();
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

    private Map<String, String> getStandardDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    // CALL Attendence_Admin API HERE
    private void callAttendence_AdminApi() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }
        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getAttendence_Admin(getAttendence_AdminDetail(), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel attendanceModel, Response response) {
//                Utils.dismissDialog();
                if (attendanceModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentStudentAttendaneBinding.ivAddUpdateAttendance.setVisibility(View.GONE);
                    Utils.dismissDialog();
                    return;
                }
                if (attendanceModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentStudentAttendaneBinding.ivAddUpdateAttendance.setVisibility(View.GONE);
                    Utils.dismissDialog();
                    return;
                }
                if (attendanceModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.dismissDialog();
//                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentStudentAttendaneBinding.ivAddUpdateAttendance.setVisibility(View.GONE);
//                    if (attendanceModel.getFinalArray().size() == 0) {
                    fragmentStudentAttendaneBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentStudentAttendaneBinding.listHeader.setVisibility(View.GONE);
                    fragmentStudentAttendaneBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentStudentAttendaneBinding.totalTxt.setText(Html.fromHtml("Total Students : " + "<font color='#1B88C8'>" + "0"));

//                    }
                    //Utils.dismissDialog();
                    return;
                }
                if (attendanceModel.getSuccess().equalsIgnoreCase("True")) {

                    Utils.dismissDialog();

                    if (attendanceModel.getFinalArray().size() > 0) {
                        fragmentStudentAttendaneBinding.ivAddUpdateAttendance.setVisibility(View.VISIBLE);

                        finalArrayStudentNameModelList = new ArrayList<>();

                        finalArrayStudentNameModelList = attendanceModel.getFinalArray();
                        fragmentStudentAttendaneBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentStudentAttendaneBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        fragmentStudentAttendaneBinding.listHeader.setVisibility(View.VISIBLE);
                        fragmentStudentAttendaneBinding.attendanceIndividualTotal.setVisibility(View.VISIBLE);
                        fragmentStudentAttendaneBinding.totalTxt.setText(Html.fromHtml("Total Students : " + "<font color='#1B88C8'>" + attendanceModel.getFinalArray().get(0).getTotal()));
                        fragmentStudentAttendaneBinding.presentTxt.setText(Html.fromHtml("" + "<font color='#a4c639'>" + attendanceModel.getFinalArray().get(0).getTotalPresent()));
                        fragmentStudentAttendaneBinding.absentTxt.setText(Html.fromHtml("" + "<font color='#ff0000'>" + attendanceModel.getFinalArray().get(0).getTotalAbsent()));
                        fragmentStudentAttendaneBinding.leaveTxt.setText(Html.fromHtml("" + "<font color='#ff9623'>" + attendanceModel.getFinalArray().get(0).getTotalLeave()));
//                        fragmentStudentAttendaneBinding.ondutyTxt.setText(Html.fromHtml("OnDuty : " + "<font color='#d8b834'>" + "<b>" + attendanceModel.getFinalArray().get(0).getTotalOnDuty() + "</b>"));

                        if (!attendanceModel.getFinalArray().get(0).getStudentDetail().get(0).getAttendenceStatus().equalsIgnoreCase("-2")) {

                            update = "yes";
                            fragmentStudentAttendaneBinding.ivAddUpdateAttendance.setImageResource(R.drawable.update_1);
                        } else {
                            update = "no";
                            fragmentStudentAttendaneBinding.ivAddUpdateAttendance.setImageResource(R.drawable.submit);
                        }

                        SetData();
                        attendanceAdapter = new AttendanceAdapter(mContext, attendanceModel);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        fragmentStudentAttendaneBinding.studentAttendanceList.setLayoutManager(mLayoutManager);
                        fragmentStudentAttendaneBinding.studentAttendanceList.setItemAnimator(new DefaultItemAnimator());
                        fragmentStudentAttendaneBinding.studentAttendanceList.setAdapter(attendanceAdapter);
                        //Utils.dismissDialog();
                    } else {
                        fragmentStudentAttendaneBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentStudentAttendaneBinding.ivAddUpdateAttendance.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                fragmentStudentAttendaneBinding.ivAddUpdateAttendance.setVisibility(View.GONE);
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });
    }

    private Map<String, String> getAttendence_AdminDetail() {
        Map<String, String> map = new HashMap<>();
        FinalDataStr = fragmentStudentAttendaneBinding.dateButton.getText().toString();
        map.put("AttDate", FinalDataStr);
        map.put("StdID", FinalStandardIdStr);
        map.put("ClsID", FinalClassIdStr);
        map.put("TermID", FinalTermIdStr);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }


    public void fillGradeSpinner() {
//        ArrayList<String> firstValue = new ArrayList<>();
//        firstValue.add("All");
//
        ArrayList<String> standardname = new ArrayList<>();
//        for (int z = 0; z < firstValue.size(); z++) {
//            standardname.add(firstValue.get(z));
        for (int i = 0; i < finalArrayStandardsList.size(); i++) {
            standardname.add(finalArrayStandardsList.get(i).getStandard());
        }
//        }
//        ArrayList<Integer> firstValueId = new ArrayList<>();
//        firstValueId.add(0);
        ArrayList<Integer> standardId = new ArrayList<>();
//        for (int m = 0; m < firstValueId.size(); m++) {
//            standardId.add(firstValueId.get(m));
        for (int j = 0; j < finalArrayStandardsList.size(); j++) {
            standardId.add(finalArrayStandardsList.get(j).getStandardID());
        }
//        }
        String[] spinnerstandardIdArray = new String[standardId.size()];

        spinnerStandardMap = new HashMap<Integer, String>();
        for (int i = 0; i < standardId.size(); i++) {
            spinnerStandardMap.put(i, String.valueOf(standardId.get(i)));
            spinnerstandardIdArray[i] = standardname.get(i).trim();
        }

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentAttendaneBinding.gradeSpinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 500 : spinnerstandardIdArray.length * 100);
//            popupWindow1.setHeght(200);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }


        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        fragmentStudentAttendaneBinding.gradeSpinner.setAdapter(adapterstandard);

        FinalStandardIdStr = spinnerStandardMap.get(0);
    }

    public void fillSection() {
        ArrayList<String> sectionname = new ArrayList<>();
        ArrayList<Integer> sectionId = new ArrayList<>();
        ArrayList<String> firstSectionValue = new ArrayList<String>();
//        firstSectionValue.add("All");
        ArrayList<Integer> firstSectionId = new ArrayList<>();
//        firstSectionId.add(0);

//        if (StandardName.equalsIgnoreCase("All")) {
//            for (int j = 0; j < firstSectionValue.size(); j++) {
//                sectionname.add(firstSectionValue.get(j));
//            }
//            for (int i = 0; i < firstSectionId.size(); i++) {
//                sectionId.add(firstSectionId.get(i));
//            }
//
//        }
        for (int z = 0; z < finalArrayStandardsList.size(); z++) {
            if (StandardName.equalsIgnoreCase(finalArrayStandardsList.get(z).getStandard())) {
//                for (int j = 0; j < firstSectionValue.size(); j++) {
//                    sectionname.add(firstSectionValue.get(j));
                for (int i = 0; i < finalArrayStandardsList.get(z).getSectionDetail().size(); i++) {
                    sectionname.add(finalArrayStandardsList.get(z).getSectionDetail().get(i).getSection());
//                    }
                }
//                for (int j = 0; j < firstSectionId.size(); j++) {
//                    sectionId.add(firstSectionId.get(j));
                for (int m = 0; m < finalArrayStandardsList.get(z).getSectionDetail().size(); m++) {
                    sectionId.add(finalArrayStandardsList.get(z).getSectionDetail().get(m).getSectionID());
                }
//                }
            }
        }

        String[] spinnersectionIdArray = new String[sectionId.size()];

        spinnerSectionMap = new HashMap<Integer, String>();
        for (int i = 0; i < sectionId.size(); i++) {
            spinnerSectionMap.put(i, String.valueOf(sectionId.get(i)));
            spinnersectionIdArray[i] = sectionname.get(i).trim();
        }
//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentAttendaneBinding.sectionSpinner);
//
//            popupWindow.setHeight(spinnersectionIdArray.length > 4 ? 500 : spinnersectionIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }
        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnersectionIdArray);
        fragmentStudentAttendaneBinding.sectionSpinner.setAdapter(adapterstandard);

        FinalClassIdStr = spinnerSectionMap.get(0);


//        if (status.equalsIgnoreCase("true")) {
//
//            callAttendence_AdminApi();
//        } else {
//            Utils.ping(getActivity(), "Access Denied");
//        }
    }

    public void SetData() {

        for (int j = 0; j < finalArrayStudentNameModelList.size(); j++) {
            for (int k = 0; k < finalArrayStudentNameModelList.get(j).getStudentDetail().size(); k++) {
//                if (finalArrayStudentNameModelList.get(j).getStudentDetail().get(k).getAttendenceStatus().equalsIgnoreCase("-2")) {
//                    finalArrayStudentNameModelList.get(j).getStudentDetail().get(j).setAttendenceStatus("1");
//                }
            }
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "Selected Date : " + Day + "/" + Month + "/" + Year;
        String datestr = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

        mDay = dayOfMonth;
        mMonth = monthOfYear + 1;
        mYear = year;
        String d, m, y;
        d = Integer.toString(mDay);
        m = Integer.toString(mMonth);
        y = Integer.toString(mYear);

        if (mDay < 10) {
            d = "0" + d;
        }
        if (mMonth < 10) {
            m = "0" + m;
        }

        fragmentStudentAttendaneBinding.dateButton.setText(d + "/" + m + "/" + y);


    }

    private void callInsertAttendanceApi() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }
        Utils.showDialog(getActivity());
        ApiHandler.getApiService().insertAttendance(getAttanceParams(), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel attendanceModel, Response response) {
//                Utils.dismissDialog();
                if (attendanceModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    Utils.dismissDialog();
                    return;
                }
                if (attendanceModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    Utils.dismissDialog();
                    return;
                }
                if (attendanceModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
                    return;
                }
                if (attendanceModel.getSuccess().equalsIgnoreCase("True")) {
                    Utils.dismissDialog();
                    Utils.ping(getActivity(), "Attendance updated successfully");
                    if (status.equalsIgnoreCase("true")) {

                        callAttendence_AdminApi();
                    } else {
                        Utils.ping(getActivity(), "Access Denied");
                    }
                    return;
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

    private Map<String, String> getAttanceParams() {
        Map<String, String> map = new HashMap<>();

        try {
            if (finalArrayStudentNameModelList != null) {
                if (finalArrayStudentNameModelList.size() > 0) {
                    final ArrayList<String> Attendanceid = new ArrayList<>();
                    final ArrayList<String> Attendacestatus = new ArrayList<>();
                    final ArrayList<String> studid = new ArrayList<>();

                    for (int j = 0; j < finalArrayStudentNameModelList.get(0).getStudentDetail().size(); j++) {
                        Attendanceid.add(String.valueOf(finalArrayStudentNameModelList.get(0).getStudentDetail().get(j).getAttendanceID()));
                        Attendacestatus.add(String.valueOf(finalArrayStudentNameModelList.get(0).getStudentDetail().get(j).getAttendenceStatus()));
                        studid.add(String.valueOf(finalArrayStudentNameModelList.get(0).getStudentDetail().get(j).getStudentID()));
                    }

                    Log.d("Attendanceid", "" + Attendanceid);
                    Log.d("Attendacestatus", "" + Attendacestatus);
                    Log.d("studid", "" + studid);

                    Attendanceidstr = "";
                    for (String s : Attendanceid) {
                        Attendanceidstr = Attendanceidstr + "," + s;
                    }
                    Log.d("Attendanceidstr", Attendanceidstr);
                    Attendanceidstr = Attendanceidstr.substring(1);
                    Log.d("finalstatusStr", Attendanceidstr);

                    Attendacestatusstr = "";
                    for (String s : Attendacestatus) {
                        Attendacestatusstr = Attendacestatusstr + "," + s;

                    }
                    Log.d("Attendacestatusstr", Attendacestatusstr);

                    Attendacestatusstr = Attendacestatusstr.substring(1);
                    Attendacestatusstr = Attendacestatusstr.replace("-2", "1");
                    Log.d("Attendacestatusstr", Attendacestatusstr);

                    studentidstr = "";
                    for (String s : studid) {
                        studentidstr = studentidstr + "," + s;
                    }
                    Log.d("studentidstr", studentidstr);
                    studentidstr = studentidstr.substring(1);
                    Log.d("studentidstr", studentidstr);

                    FinalDataStr = fragmentStudentAttendaneBinding.dateButton.getText().toString();
                    map.put("StaffID", prefUtils.getStringValue("StaffID", ""));
                    map.put("StandardID", FinalStandardIdStr);
                    map.put("ClassID", FinalClassIdStr);
                    map.put("Date", FinalDataStr);
                    map.put("Comment", "");
                    map.put("AttendacneStatus", Attendacestatusstr);
                    map.put("CurrantDate", Utils.getTodaysDate());
                    map.put("StudentID", studentidstr);
                    map.put("AttendanceID", Attendanceidstr);
                    map.put("TermID", FinalTermIdStr);
                    map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
                }

            }
            return map;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;

    }

    // CALL Term API HERE
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
        HashMap<String, String> map = new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return new HashMap<>();
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
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentViewInquiryBinding.termSpinner);
//
//            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentStudentAttendaneBinding.termSpinner.setAdapter(adapterTerm);

        FinalTermIdStr = spinnerTermMap.get(0);

    }

}

