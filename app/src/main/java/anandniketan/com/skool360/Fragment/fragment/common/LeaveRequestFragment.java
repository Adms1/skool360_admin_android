package anandniketan.com.skool360.Fragment.fragment.common;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import anandniketan.com.skool360.Fragment.fragment.hr.StaffLeaveFragment;
import anandniketan.com.skool360.Fragment.fragment.student.StudentFragment;
import anandniketan.com.skool360.Interface.OnAdapterItemButtonClick;
import anandniketan.com.skool360.Model.HR.LeaveDayModel;
import anandniketan.com.skool360.Model.HR.LeaveRequestModel;
import anandniketan.com.skool360.Model.HR.LeaveStatusModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiClient;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.Utility.WebServices;
import anandniketan.com.skool360.activity.DashboardActivity;
import anandniketan.com.skool360.adapter.ExpandableLeaveRequest;
import anandniketan.com.skool360.databinding.DialogModifyLeaveBinding;
import anandniketan.com.skool360.databinding.FragmentLeaveRequestBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Callback;


public class LeaveRequestFragment extends Fragment implements OnAdapterItemButtonClick, DatePickerDialog.OnDateSetListener {

    private static String dateFinal = "";
    private Context mContext;
    private ExpandableLeaveRequest expandableListCircular;
    private List<LeaveRequestModel.FinalArray> finalArrayAnnouncementFinal;
    private List<String> listDataHeader;
    private HashMap<String, ArrayList<LeaveRequestModel.FinalArray>> listDataChild;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private OnAdapterItemButtonClick onAdapterItemButtonClick;
    private HashMap<Integer, String> spinnerOrderMap;
    private HashMap<Integer, String> spinnerDaymap;
    private Button backBtn;
    private List<LeaveStatusModel.FinalArray> finalArrayGetLeaveStatus;
    private List<LeaveDayModel.FinalArray> finalArrayGetLeaveDays;
    private String FinalStatusIdStr = "", FinalDay = "", FinalTypetext = "Leave Date";
    private FragmentLeaveRequestBinding fragmentLeaveRequestBinding;
    private DatePickerDialog datePickerDialog;
    private int whichDateClicked = 1;
    private int Year, Month, Day, hour, minute, second;
    private int mYear, mMonth, mDay;
    private Calendar calendar;
    private DialogModifyLeaveBinding dialogModifyLeaveBinding;
    private Dialog dialog;
    private int leaveStatus = 0;
    private boolean isRecordInUpdate = false;
    private String updateDateFromDialog = "", type, nid, ndate, nytpe;
    private int lastExpandedPosition = -1;
    private TextView tvHeader, statusTxt, subjectTxt;
    private Button btnBack, btnMenu;

    public LeaveRequestFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        super.onAttach(context);
        onAdapterItemButtonClick = this;

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        fragmentLeaveRequestBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_leave_request, container, false);

        return fragmentLeaveRequestBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        type = bundle.getString("type");

        nytpe = bundle.getString("ntype", "");

        if (nytpe.equalsIgnoreCase("notification")) {
            nid = bundle.getString("stuid");
            ndate = bundle.getString("sdate");
        } else {
            nid = "";
            ndate = "";
        }

//        view1 = view.findViewById(R.id.header);
        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        statusTxt = view.findViewById(R.id.status_txt);
        subjectTxt = view.findViewById(R.id.leave_user_name);

        tvHeader.setText(R.string.leave_req);

        setListners();
        callLeaveStatusApi();

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        populateSetDate(year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onItemButtonClick(Action target, int posID) {
        switch (target) {
            case ADD:
                break;

            case UPDATE:
                break;

            case DELETE:
                break;

            case MODIFY:
                leaveStatus = 3;
                List<LeaveRequestModel.FinalArray> dataItem1 = expandableListCircular.getChild(posID, posID);
                if (dataItem1 != null && dataItem1.size() > 0) {
                    modifyLeaveDialog(dataItem1.get(0));
                }

                break;

            case APPROVE:
                leaveStatus = 3;
                if (expandableListCircular != null) {
                    List<LeaveRequestModel.FinalArray> dataItem2 = expandableListCircular.getChild(posID, posID);
                    if (dataItem2 != null && dataItem2.size() > 0) {
                        callUpdateLeaveStatusApi(dataItem2.get(0), "Approved");
                    }
                }
                break;

            case REJECT:
                leaveStatus = 4;
                if (expandableListCircular != null) {
                    List<LeaveRequestModel.FinalArray> dataItem3 = expandableListCircular.getChild(posID, posID);
                    if (dataItem3 != null && dataItem3.size() > 0) {
                        callUpdateLeaveStatusApi(dataItem3.get(0), "Rejected");
                    }
                }
                break;
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

        if (nytpe.equalsIgnoreCase("notification")) {
            fragmentLeaveRequestBinding.fromdateBtn.setText(ndate);
            fragmentLeaveRequestBinding.todateBtn.setText(Utils.getTodaysDate());

            fragmentLeaveRequestBinding.typeGroup.setEnabled(false);
            fragmentLeaveRequestBinding.rbLeavedate.setChecked(false);
            fragmentLeaveRequestBinding.rbAppdate.setChecked(true);

        }

//        fragmentLeaveRequestBinding.fromdateBtn.setText(Utils.getTodaysDate());
//        fragmentLeaveRequestBinding.todateBtn.setText(Utils.getTodaysDate());

        fragmentLeaveRequestBinding.fromdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichDateClicked = 1;
                datePickerDialog = DatePickerDialog.newInstance(LeaveRequestFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "DatePickerDialog");

            }
        });
        fragmentLeaveRequestBinding.todateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichDateClicked = 2;
                datePickerDialog = DatePickerDialog.newInstance(LeaveRequestFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "DatePickerDialog");
            }
        });

        fragmentLeaveRequestBinding.typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_leavedate) {
                    FinalTypetext = fragmentLeaveRequestBinding.rbLeavedate.getText().toString();
                    fragmentLeaveRequestBinding.rbLeavedate.setChecked(true);
                    fragmentLeaveRequestBinding.rbAppdate.setChecked(false);

                } else if (i == R.id.rb_appdate) {
                    FinalTypetext = fragmentLeaveRequestBinding.rbAppdate.getText().toString();
                    fragmentLeaveRequestBinding.rbLeavedate.setChecked(false);
                    fragmentLeaveRequestBinding.rbAppdate.setChecked(true);
                }
            }
        });

        fragmentLeaveRequestBinding.leavereqList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    fragmentLeaveRequestBinding.leavereqList.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DashboardActivity.onLeft();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                AppConfiguration.position = 58;
//                AppConfiguration.firsttimeback = true;
//                getActivity().onBackPressed();

                if (nytpe.equalsIgnoreCase("")) {
                    if (type.equalsIgnoreCase("student")) {
                        fragment = new StudentFragment();
                        fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                    } else {
                        fragment = new StaffLeaveFragment();
                        fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();

                    }
                } else {

//                AppConfiguration.position = 58;
//                AppConfiguration.firsttimeback = true;
//                getActivity().onBackPressed();

                    fragment = new NotificationFragment();
                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                }
            }
        });

        fragmentLeaveRequestBinding.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentLeaveRequestBinding.statusSpinner.getSelectedItem().toString();
                String getid = spinnerOrderMap.get(fragmentLeaveRequestBinding.statusSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalStatusIdStr = getid;
                callLeaveRequestListApi();
                Log.d("FinalStatusIdStr", FinalStatusIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentLeaveRequestBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callLeaveRequestListApi();
            }
        });
    }

    private void callLeaveRequestListApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        Call<LeaveRequestModel> call = null;

        if (type.equalsIgnoreCase("student")) {

            statusTxt.setText("Grade");
            subjectTxt.setText("Student Name");

            call = apiService.getAllStudentLeaveRequest(getDetail());
        } else if (type.equalsIgnoreCase("staff") || type.equalsIgnoreCase("Employee")) {

            statusTxt.setText("No. of Days");
            subjectTxt.setText("Employee");

            call = apiService.getAllStaffLeaveRequest(getDetail());
        }

        call.enqueue(new Callback<LeaveRequestModel>() {

//        ApiHandler.getApiService().getAllStaffLeaveRequest(getDetail(), new retrofit.Callback<LeaveRequestModel>() {

            @Override
            public void onResponse(Call<LeaveRequestModel> call, retrofit2.Response<LeaveRequestModel> response) {
                Utils.dismissDialog();
                if (response.body() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentLeaveRequestBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentLeaveRequestBinding.explinear.setVisibility(View.GONE);
                    fragmentLeaveRequestBinding.lvExpHeader.setVisibility(View.GONE);
                    return;
                }
                if (response.body().getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentLeaveRequestBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentLeaveRequestBinding.explinear.setVisibility(View.GONE);
                    fragmentLeaveRequestBinding.lvExpHeader.setVisibility(View.GONE);
                    return;
                }
                if (response.body().getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));

                    fragmentLeaveRequestBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentLeaveRequestBinding.explinear.setVisibility(View.GONE);
                    fragmentLeaveRequestBinding.lvExpHeader.setVisibility(View.GONE);
                    return;
                }
                if (response.body().getSuccess().equalsIgnoreCase("True")) {
                    finalArrayAnnouncementFinal = response.body().getFinalArray();
                    if (finalArrayAnnouncementFinal != null) {
                        fragmentLeaveRequestBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentLeaveRequestBinding.explinear.setVisibility(View.VISIBLE);
                        fragmentLeaveRequestBinding.lvExpHeader.setVisibility(View.VISIBLE);

                        fillExpLV();

                        expandableListCircular = new ExpandableLeaveRequest(getActivity(), listDataHeader, listDataChild, onAdapterItemButtonClick, type);
                        fragmentLeaveRequestBinding.leavereqList.setAdapter(expandableListCircular);
                    } else {
                        fragmentLeaveRequestBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentLeaveRequestBinding.explinear.setVisibility(View.GONE);
                        fragmentLeaveRequestBinding.lvExpHeader.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<LeaveRequestModel> call, Throwable t) {
                Utils.dismissDialog();
                t.printStackTrace();
                t.getMessage();
                fragmentLeaveRequestBinding.txtNoRecords.setText(t.getMessage());
                fragmentLeaveRequestBinding.txtNoRecords.setVisibility(View.VISIBLE);
                fragmentLeaveRequestBinding.explinear.setVisibility(View.GONE);
                fragmentLeaveRequestBinding.lvExpHeader.setVisibility(View.GONE);

                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });
    }

    private Map<String, String> getDetail() {
        Map<String, String> map = new HashMap<>();

        if (!nytpe.equalsIgnoreCase("notification")) {
            map.put("Type", FinalTypetext);
        } else {
            map.put("Type", "Application Date");
        }
        map.put("FromDate", fragmentLeaveRequestBinding.fromdateBtn.getText().toString());
        map.put("ToDate", fragmentLeaveRequestBinding.todateBtn.getText().toString());

        if (type.equalsIgnoreCase("student")) {
            map.put("Status", fragmentLeaveRequestBinding.statusSpinner.getSelectedItem().toString());

        } else if (type.equalsIgnoreCase("staff") || type.equalsIgnoreCase("Employee")) {
            map.put("LeaveStatusID", FinalStatusIdStr);
            map.put("UserID", PrefUtils.getInstance(getActivity()).getStringValue("StaffID", ""));
        }

        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    private void callUpdateLeaveStatusApi(LeaveRequestModel.FinalArray dataItem, String stustatus) {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());

        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        Call<LeaveRequestModel> call = null;

        if (type.equalsIgnoreCase("student")) {

//            statusTxt.setText("Grade");
//            subjectTxt.setText("Student Name");

            call = apiService.geUpdateStudentLeaveRequest(getUpdateDetail(dataItem, stustatus));
        } else if (type.equalsIgnoreCase("staff") || type.equalsIgnoreCase("Employee")) {

//            statusTxt.setText("No. of Days");
//            subjectTxt.setText("Employee");

            call = apiService.geUpdateStaffLeaveRequest(getUpdateDetail(dataItem, ""));
        }

        call.enqueue(new Callback<LeaveRequestModel>() {

            @Override
            public void onResponse(Call<LeaveRequestModel> call, retrofit2.Response<LeaveRequestModel> response) {
                Utils.dismissDialog();
                if (response.body() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (response.body().getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (response.body().getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (response.body().getSuccess().equalsIgnoreCase("True")) {
                    Utils.ping(getActivity(), "Leave updated successfully");
                    callLeaveRequestListApi();
                }
            }

            @Override
            public void onFailure(Call<LeaveRequestModel> call, Throwable t) {
                Utils.dismissDialog();
                t.printStackTrace();
                t.getMessage();
                fragmentLeaveRequestBinding.txtNoRecords.setText(t.getMessage());
                fragmentLeaveRequestBinding.txtNoRecords.setVisibility(View.VISIBLE);
                fragmentLeaveRequestBinding.explinear.setVisibility(View.GONE);
                fragmentLeaveRequestBinding.lvExpHeader.setVisibility(View.GONE);
                Utils.ping(mContext, getString(R.string.something_wrong));
            }

        });

    }

    private Map<String, String> getUpdateDetail(LeaveRequestModel.FinalArray dataItem, String stustatus) {
        Map<String, String> map = new HashMap<>();
        try {

            if (type.equalsIgnoreCase("student")) {
                map.put("PK_LeaveID", String.valueOf(dataItem.getPKLeaveApproveID()));
                map.put("Status", stustatus);

            } else {
                map.put("PK_LeaveApproveID", String.valueOf(dataItem.getPKLeaveApproveID()));
                if (leaveStatus == 1) {
                    map.put("LeaveStatusID", String.valueOf(finalArrayGetLeaveStatus.get(0).getLeaveStatusID()));

                } else if (leaveStatus == 3) {
                    map.put("LeaveStatusID", String.valueOf(finalArrayGetLeaveStatus.get(1).getLeaveStatusID()));

                } else if (leaveStatus == 4) {
                    map.put("LeaveStatusID", String.valueOf(finalArrayGetLeaveStatus.get(2).getLeaveStatusID()));

                }

                String[] dates;
                try {
                    dates = dataItem.getLeaveDates().split("-");
                    String fromDate = dates[0];
                    String toDate = dates[1];

                    map.put("FromDate", fromDate);
                    map.put("ToDate", toDate);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                map.put("ApproveDays", String.valueOf(dataItem.getApproveDays()));
                map.put("EmployeeID", String.valueOf(dataItem.getEmployeeID()));
            }

            map.put("UserID", PrefUtils.getInstance(getActivity()).getStringValue("StaffID", ""));
            map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));

            return map;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }


    // CALL Term API HERE
    private void callLeaveStatusApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getLeaveStatus(getTermDetail(), new retrofit.Callback<LeaveStatusModel>() {
            @Override
            public void success(LeaveStatusModel termModel, Response response) {
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
                    finalArrayGetLeaveStatus = termModel.getFinalArray();
                    if (finalArrayGetLeaveStatus != null) {
                        fillStatusSpinner();
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
        return map;
    }


    // CALL Term API HERE
    private void callLeaveDaysApi(final LeaveRequestModel.FinalArray dataItem) {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getleaveDays(getLeaveDayParams(), new retrofit.Callback<LeaveDayModel>() {
            @Override
            public void success(LeaveDayModel termModel, Response response) {
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
                    finalArrayGetLeaveDays = termModel.getFinalArray();
                    if (finalArrayGetLeaveDays != null) {
                        fillDialogDaysSpinner(dataItem);
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

    private Map<String, String> getLeaveDayParams() {
        Map<String, String> map = new HashMap<>();
        map.put("UserID", PrefUtils.getInstance(getActivity()).getStringValue("StaffID", ""));
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }


    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        for (int i = 0; i < finalArrayAnnouncementFinal.size(); i++) {

            String classname;
            if (type.equalsIgnoreCase("student")) {
                classname = finalArrayAnnouncementFinal.get(i).getStandard() + "-" + finalArrayAnnouncementFinal.get(i).getClassname();
            } else {
                classname = finalArrayAnnouncementFinal.get(i).getLeaveDays();
            }

            if (!nid.equalsIgnoreCase("")) {
                if (finalArrayAnnouncementFinal.get(i).getPKLeaveApproveID().toString().equalsIgnoreCase(nid)) {
                    listDataHeader.add(finalArrayAnnouncementFinal.get(i).getEmployeeName() + "|" + finalArrayAnnouncementFinal.get(i).getApplicationDate() + "|" + classname);

                    Log.d("header", "" + listDataHeader);
                    ArrayList<LeaveRequestModel.FinalArray> row = new ArrayList<>();
                    row.add(finalArrayAnnouncementFinal.get(i));
                    Log.d("row", "" + row);
                    listDataChild.put(listDataHeader.get(0), row);
                    Log.d("child", "" + listDataChild);

                }
            } else {
                listDataHeader.add(finalArrayAnnouncementFinal.get(i).getEmployeeName() + "|" + finalArrayAnnouncementFinal.get(i).getApplicationDate() + "|" + classname);

                Log.d("header", "" + listDataHeader);
                ArrayList<LeaveRequestModel.FinalArray> row = new ArrayList<>();
                row.add(finalArrayAnnouncementFinal.get(i));
                Log.d("row", "" + row);
                listDataChild.put(listDataHeader.get(i), row);
                Log.d("child", "" + listDataChild);
            }
        }
    }

    public void fillStatusSpinner() {
        ArrayList<Integer> TermId = new ArrayList<>();
        for (int i = 0; i < finalArrayGetLeaveStatus.size(); i++) {
            TermId.add(finalArrayGetLeaveStatus.get(i).getLeaveStatusID());
        }
        ArrayList<String> Term = new ArrayList<>();
        for (int j = 0; j < finalArrayGetLeaveStatus.size(); j++) {
            if (type.equalsIgnoreCase("student"))
                if (finalArrayGetLeaveStatus.get(j).getStatusName().equalsIgnoreCase("Approved By Admin")) {
                    Term.add("Approved");
                } else {
                    Term.add(finalArrayGetLeaveStatus.get(j).getStatusName());
                }

            else {
                Term.add(finalArrayGetLeaveStatus.get(j).getStatusName());
            }
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerOrderMap = new HashMap<>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerOrderMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentLeaveRequestBinding.statusSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentLeaveRequestBinding.statusSpinner.setAdapter(adapterTerm);

        FinalStatusIdStr = spinnerOrderMap.get(0);

    }


    public void fillDialogDaysSpinner(LeaveRequestModel.FinalArray dataItem) {
        ArrayList<String> TermId = new ArrayList<>();
        for (int i = 0; i < finalArrayGetLeaveDays.size(); i++) {
            TermId.add(String.valueOf(finalArrayGetLeaveDays.get(i).getValue()));
        }
        ArrayList<String> Term = new ArrayList<>();
        for (int j = 0; j < finalArrayGetLeaveDays.size(); j++) {
            Term.add(String.valueOf(finalArrayGetLeaveDays.get(j).getDays()));
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerDaymap = new HashMap<>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerDaymap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(dialogModifyLeaveBinding.leavedaySpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        dialogModifyLeaveBinding.leavedaySpinner.setAdapter(adapterTerm);


        FinalDay = spinnerDaymap.get(0);


        for (int i = 0; i < finalArrayGetLeaveDays.size(); i++) {
            if (dataItem != null) {
                if (dataItem.getLeaveDays().equalsIgnoreCase(finalArrayGetLeaveDays.get(i).getValue())) {
                    dialogModifyLeaveBinding.leavedaySpinner.setSelection(i);
                    String[] dates = dataItem.getLeaveDates().split("-");
                    dialogModifyLeaveBinding.fromdateBtn.setText(dates[0]);
                    dialogModifyLeaveBinding.todateBtn.setText(dates[1]);
                    updateDateFromDialog = dataItem.getLeaveDays();
                    break;
                } else {
                    dialogModifyLeaveBinding.leavedaySpinner.setSelection(0);
                    updateDateFromDialog = "0";

                }
            } else {
                dialogModifyLeaveBinding.leavedaySpinner.setSelection(0);
                updateDateFromDialog = "0";

            }
        }
    }


    public void modifyLeaveDialog(final LeaveRequestModel.FinalArray dataItem) {
        dialogModifyLeaveBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_modify_leave, (ViewGroup) fragmentLeaveRequestBinding.getRoot(), false);
//
        dialog = new Dialog(mContext);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = null;
        if (window != null) {
            wlp = window.getAttributes();

            wlp.gravity = Gravity.CENTER;
            window.setAttributes(wlp);

        }

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(dialogModifyLeaveBinding.getRoot());


        callLeaveDaysApi(dataItem);
        dialogModifyLeaveBinding.todateBtn.setAlpha(0.5f);

        dialogModifyLeaveBinding.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDialog();
            }
        });

        dialogModifyLeaveBinding.fromdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichDateClicked = 3;
                datePickerDialog = DatePickerDialog.newInstance(LeaveRequestFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setMinDate(calendar);
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "DatePickerDialog");
            }
        });


        dialogModifyLeaveBinding.leavedaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = dialogModifyLeaveBinding.leavedaySpinner.getSelectedItem().toString();
                String getid = spinnerDaymap.get(dialogModifyLeaveBinding.leavedaySpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalDay = getid;

                int finalDays = (int) Math.floor(Double.parseDouble(FinalDay));
                if (FinalDay.equalsIgnoreCase(updateDateFromDialog)) {

                } else {

                    String approveDay = dataItem.getApproveDays();

                    int leaveDayInt = (int) Math.floor(Double.parseDouble(approveDay));
                    if (finalDays > leaveDayInt) {
                        Utils.ping(getActivity(), "Approve days more than apply days leave");

                        for (int i = 0; i < finalArrayGetLeaveDays.size(); i++) {
                            if (dataItem != null) {
                                if (dataItem.getLeaveDays().equalsIgnoreCase(finalArrayGetLeaveDays.get(i).getValue())) {
                                    dialogModifyLeaveBinding.leavedaySpinner.setSelection(i);
                                    String[] dates = dataItem.getLeaveDates().split("-");
                                    dialogModifyLeaveBinding.fromdateBtn.setText(dates[0]);
                                    dialogModifyLeaveBinding.todateBtn.setText(dates[1]);
                                    updateDateFromDialog = dataItem.getLeaveDays();
                                    break;
                                } else {
                                    dialogModifyLeaveBinding.leavedaySpinner.setSelection(0);
                                    updateDateFromDialog = "0";

                                }
                            } else {
                                dialogModifyLeaveBinding.leavedaySpinner.setSelection(0);
                                updateDateFromDialog = "0";

                            }
                        }


                    } else {
                        String days = dialogModifyLeaveBinding.leavedaySpinner.getSelectedItem().toString();
                        String appDate = dialogModifyLeaveBinding.fromdateBtn.getText().toString();
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Calendar c = Calendar.getInstance();
                            c.setTime(sdf.parse(appDate));

                            int numOfDays = (int) Math.floor(Double.parseDouble(days));
                            c.add(Calendar.DATE, numOfDays);

                            sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date resultdate = new Date(c.getTimeInMillis());
                            String toDate = sdf.format(resultdate);

                            dialogModifyLeaveBinding.todateBtn.setText(toDate);
                            dataItem.setApproveDays(days);
                            dataItem.setLeaveDates(dialogModifyLeaveBinding.fromdateBtn.getText().toString() + "-" + dialogModifyLeaveBinding.todateBtn.getText().toString());

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    //dialogModifyLeaveBinding.fromdateBtn.setText("");

                    // dialogModifyLeaveBinding.todateBtn.setText("");
                }

//                dialogModifyLeaveBinding.fromdateBtn.setText("");
//                dialogModifyLeaveBinding.todateBtn.setText("");

//                for (int i = 0; i < finalArrayGetLeaveDays.size(); i++) {
//                    if (dataItem != null) {
//                        if (!dataItem.getLeaveDays().equalsIgnoreCase(finalArrayGetLeaveDays.get(i).getValue())) {
//                            dialogModifyLeaveBinding.leavedaySpinner.setSelection(i);
//                            String [] dates  = dataItem.getLeaveDates().split("-");
//                            dialogModifyLeaveBinding.fromdateBtn.setText(dates[0]);
//                            dialogModifyLeaveBinding.todateBtn.setText(dates[1]);
//                            break;
//                        } else {
//                            dialogModifyLeaveBinding.leavedaySpinner.setSelection(0);
//                            String [] dates  = dataItem.getLeaveDates().split("-");
//                            dialogModifyLeaveBinding.fromdateBtn.setText(dates[0]);
//                            dialogModifyLeaveBinding.todateBtn.setText(dates[1]);
//                        }
//                    }else{
//                        dialogModifyLeaveBinding.leavedaySpinner.setSelection(0);
//
//                    }
//                 }

                Log.d("FinalStatusIdStr", FinalStatusIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialogModifyLeaveBinding.fromdateBtn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String leaveStartDateStr = dialogModifyLeaveBinding.fromdateBtn.getText().toString();
                if (!leaveStartDateStr.equalsIgnoreCase("")) {
                    String days = dialogModifyLeaveBinding.leavedaySpinner.getSelectedItem().toString();
                    String appDate = dialogModifyLeaveBinding.fromdateBtn.getText().toString();
                    String toDate = "";

                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar c = Calendar.getInstance();
                        c.setTime(sdf.parse(appDate));

                        int numOfDays = (int) Math.floor(Double.parseDouble(days));
                        c.add(Calendar.DATE, numOfDays);

                        sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date resultdate = new Date(c.getTimeInMillis());
                        toDate = sdf.format(resultdate);

                        dialogModifyLeaveBinding.todateBtn.setText(toDate);
                        dataItem.setApproveDays(days);
                        dataItem.setLeaveDates(dialogModifyLeaveBinding.fromdateBtn.getText().toString() + "-" + dialogModifyLeaveBinding.todateBtn.getText().toString());


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        dialogModifyLeaveBinding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateModifyLeaveDialog()) {
                    dialog.dismiss();
                    callUpdateLeaveStatusApi(dataItem, "");
                }

            }

        });
        dialog.show();
    }

    public void closeDialog() {
        dialog.dismiss();
    }

    private boolean validateModifyLeaveDialog() {
        if (dialogModifyLeaveBinding.leavedaySpinner.getSelectedItemPosition() < 0) {
            Utils.ping(getActivity(), "Please select leave days");
            return false;
        } else if (TextUtils.isEmpty(dialogModifyLeaveBinding.fromdateBtn.getText().toString())) {
            Utils.ping(getActivity(), "Please select start date");
            return false;
        } else if (TextUtils.isEmpty(dialogModifyLeaveBinding.todateBtn.getText().toString())) {
            Utils.ping(getActivity(), "Please select end date");
            return false;
        }

        return true;
    }


    public void populateSetDate(int year, int month, int day) {
        String d, m, y;
        d = Integer.toString(day);
        m = Integer.toString(month + 1);
        y = Integer.toString(year);
        if (day < 10) {
            d = "0" + d;
        }
        if (month < 10) {
            m = "0" + m;
        }

        dateFinal = d + "/" + m + "/" + y;

        if (whichDateClicked == 1) {
            fragmentLeaveRequestBinding.fromdateBtn.setText(dateFinal);

        } else if (whichDateClicked == 2) {
            fragmentLeaveRequestBinding.todateBtn.setText(dateFinal);

        } else if (whichDateClicked == 3) {
            dialogModifyLeaveBinding.fromdateBtn.setText(dateFinal);
        }
    }
}
