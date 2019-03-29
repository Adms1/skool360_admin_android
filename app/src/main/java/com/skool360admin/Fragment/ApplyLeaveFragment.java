package com.skool360admin.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.ApplyLeaveAdapter;
import anandniketan.com.bhadajadmin.Interface.getEditpermission;
import anandniketan.com.bhadajadmin.Interface.onDeleteWithId;
import anandniketan.com.bhadajadmin.Model.HR.LeaveDayModel;
import anandniketan.com.bhadajadmin.Model.LeaveModel;
import anandniketan.com.bhadajadmin.Model.Staff.FinalArrayStaffModel;
import anandniketan.com.bhadajadmin.Model.Staff.StaffAttendaceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.PrefUtils;
import anandniketan.com.bhadajadmin.Utility.Utils;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ApplyLeaveFragment extends Fragment implements DatePickerDialog.OnDateSetListener, onDeleteWithId {

    private TextView tvHeader, tvNoRecords;
    private Button btnBack, btnMenu, btnSend, btnSDate, btnEDate, btnCancel;
    private EditText etReason;
    private ExpandableListView rvList;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private DatePickerDialog datePickerDialog;
    private int Year, Month, Day;
    private LinearLayout llHeader;
    private Calendar calendar;
    private int whichclicked;
    private List<LeaveDayModel.FinalArray> finalArrayGetLeaveDays;
    private List<FinalArrayStaffModel> finalArrayGetHead;
    private HashMap<Integer, String> spinnerDaymap, spinnerHeadmap;
    private Spinner spLeaveDays, spHead;
    private String finalDayId = "0", finalHeadId = "0", finalLeaveId = "0";
    private ArrayList<LeaveModel.FinalArray> finalArray;
    private ApplyLeaveAdapter applyLeaveAdapter;
    private List<String> listDataHeader;
    private onDeleteWithId onDeleteWithIdRef;
    private getEditpermission onUpdateRecordRef;
    private int lastExpandedPosition = -1;
    private HashMap<String, ArrayList<LeaveModel.FinalArray>> listDataChild;

    public ApplyLeaveFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onDeleteWithIdRef = this;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apply_leave, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);
        btnSDate = view.findViewById(R.id.applyleave_btnsDate);
        btnEDate = view.findViewById(R.id.applyleave_btneDate);
        btnSend = view.findViewById(R.id.applyleave_btnSend);
        llHeader = view.findViewById(R.id.list_header);
        rvList = view.findViewById(R.id.applyleave_list);
        tvNoRecords = view.findViewById(R.id.txtNoRecords);
        spLeaveDays = view.findViewById(R.id.applyleave_spLeaveDays);
        spHead = view.findViewById(R.id.applyleave_spHead);
        etReason = view.findViewById(R.id.applyleave_etReason);
        btnCancel = view.findViewById(R.id.applyleave_btnCancel);

        tvHeader.setText(R.string.applyleave);

        setListners();
        callLeaveDaysApi();
        callGetApplyLeaveRequest();
        callHeadApi();

    }

    private void setListners() {

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        btnSDate.setText(Utils.getTodaysDate());
        btnEDate.setText(Utils.getTodaysDate());

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApplyLeaveListApi();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCancel.setVisibility(View.GONE);
                finalDayId = "0";
                finalHeadId = "0";
                finalLeaveId = "0";
                btnSDate.setText(Utils.getTodaysDate());
                btnEDate.setText(Utils.getTodaysDate());
                spLeaveDays.setSelection(0);
                spHead.setSelection(0);
                etReason.setText("");
            }
        });

        btnSDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichclicked = 1;
                datePickerDialog = DatePickerDialog.newInstance(ApplyLeaveFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "DatePickerDialog");
            }
        });

        btnEDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichclicked = 2;
                datePickerDialog = DatePickerDialog.newInstance(ApplyLeaveFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date To DatePickerDialog");
                datePickerDialog.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "DatePickerDialog");
            }
        });

        rvList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    rvList.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
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
                fragment = new MyLeaveFragment();
                fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                AppConfiguration.firsttimeback = true;

            }
        });

        spLeaveDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = spLeaveDays.getSelectedItem().toString();
                String getid = spinnerDaymap.get(spLeaveDays.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                finalDayId = finalArrayGetLeaveDays.get(position).getValue();
                Log.d("FinalTermIdStr", finalDayId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spHead.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = spHead.getSelectedItem().toString();
//                String getid = spinnerDaymap.get(spHead.getSelectedItemPosition());
//
//                Log.d("value", name + " " + getid);
                finalHeadId = finalArrayGetHead.get(position).getEmpId().toString();
                Log.d("FinalTermIdStr", finalDayId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

        String dateFinal = d + "/" + m + "/" + y;

        if (whichclicked == 1) {
            btnSDate.setText(dateFinal);
        } else {
            btnEDate.setText(dateFinal);
        }

    }

    // CALL Term API HERE
    private void callLeaveDaysApi() {

        if (!Utils.checkNetwork(getActivity())) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getleaveDays(getLeaveDayParams(), new retrofit.Callback<LeaveDayModel>() {
            @Override
            public void success(LeaveDayModel termModel, Response response) {
                Utils.dismissDialog();
                if (termModel == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess() == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(getActivity(), getString(R.string.false_msg));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayGetLeaveDays = termModel.getFinalArray();
                    if (finalArrayGetLeaveDays != null) {
                        fillDialogDaysSpinner();
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

    private Map<String, String> getLeaveDayParams() {
        Map<String, String> map = new HashMap<>();
        map.put("UserID", PrefUtils.getInstance(getActivity()).getStringValue("StaffID", ""));
        return map;
    }

    public void fillDialogDaysSpinner() {
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spLeaveDays);

            popupWindow.setHeight(600);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout, spinnertermIdArray);
        spLeaveDays.setAdapter(adapterTerm);

        finalDayId = spinnerDaymap.get(0);
        spLeaveDays.setSelection(0);

//        for (int i = 0; i < finalArrayGetLeaveDays.size(); i++) {
//            if (dataItem != null) {
//                if (dataItem.getLeaveDays().equalsIgnoreCase(finalArrayGetLeaveDays.get(i).getValue())) {
//                    spLeaveDays.setSelection(i);
//                    String[] dates = dataItem.getLeaveDates().split("-");
//                    dialogModifyLeaveBinding.fromdateBtn.setText(dates[0]);
//                    dialogModifyLeaveBinding.todateBtn.setText(dates[1]);
//                    updateDateFromDialog = dataItem.getLeaveDays();
//                    break;
//                } else {
//                    dialogModifyLeaveBinding.leavedaySpinner.setSelection(0);
//                    updateDateFromDialog = "0";
//
//                }
//            } else {
//                dialogModifyLeaveBinding.leavedaySpinner.setSelection(0);
//                updateDateFromDialog = "0";
//
//            }
//        }

    }

    // CALL Term API HERE
    private void callHeadApi() {

        if (!Utils.checkNetwork(getActivity())) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getHead(getHeadParams(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel termModel, Response response) {
                Utils.dismissDialog();
                if (termModel == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess() == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(getActivity(), getString(R.string.false_msg));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayGetHead = termModel.getFinalArray();
                    if (finalArrayGetHead != null) {
                        fillDialogHeadSpinner();
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

    private Map<String, String> getHeadParams() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    public void fillDialogHeadSpinner() {
        ArrayList<String> TermId = new ArrayList<>();
        for (int i = 0; i < finalArrayGetHead.size(); i++) {
            TermId.add(String.valueOf(finalArrayGetHead.get(i).getEmpId()));
        }
        ArrayList<String> Term = new ArrayList<>();
        for (int j = 0; j < finalArrayGetHead.size(); j++) {
            Term.add(String.valueOf(finalArrayGetHead.get(j).getEmpName()));
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerHeadmap = new HashMap<>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerHeadmap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spHead);

            popupWindow.setHeight(200);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout, spinnertermIdArray);
        spHead.setAdapter(adapterTerm);

        finalHeadId = spinnerHeadmap.get(0);
        spHead.setSelection(0);
    }

    private void callGetApplyLeaveRequest() {
        if (!Utils.checkNetwork(Objects.requireNonNull(getActivity()))) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStaffLeaveRequest(getApplyLeaveParams(), new retrofit.Callback<LeaveModel>() {
            @Override
            public void success(LeaveModel leaveModel, Response response) {
                Utils.dismissDialog();
                if (leaveModel == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    llHeader.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                    tvNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (leaveModel.getSuccess() == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    llHeader.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                    tvNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (leaveModel.getSuccess().equalsIgnoreCase("false")) {
                    llHeader.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                    tvNoRecords.setVisibility(View.VISIBLE);
                    //                    finalArraySubjectModelList.clear();
//                    final ArrayAdapter adb = new ArrayAdapter(getActivity(), R.layout.spinner_layout, finalArraySubjectModelList);
//                    subjectSpinner.setAdapter(adb);
                    return;
                }
                if (leaveModel.getSuccess().equalsIgnoreCase("True")) {

                    llHeader.setVisibility(View.VISIBLE);
                    rvList.setVisibility(View.VISIBLE);
                    tvNoRecords.setVisibility(View.GONE);

                    finalArray = leaveModel.getFinalArray();
                    if (finalArray != null) {
                        tvNoRecords.setVisibility(View.GONE);
                        rvList.setVisibility(View.VISIBLE);

                        fillExpLV();

                        applyLeaveAdapter = new ApplyLeaveAdapter(getActivity(), listDataHeader, listDataChild, onDeleteWithIdRef,
                                new getEditpermission() {
                                    @Override
                                    public void getEditpermission() {

                                        btnCancel.setVisibility(View.VISIBLE);
                                        btnSend.setText(R.string.update);

                                        String[] dataArr = applyLeaveAdapter.getData().split("\\|");

                                        String edtLeaveDay = dataArr[0];

                                        for (int i = 0; i < finalArrayGetLeaveDays.size(); i++) {
                                            if (finalArrayGetLeaveDays.get(i).getValue().equalsIgnoreCase(edtLeaveDay)) {
                                                spLeaveDays.setSelection(i);
                                            }
                                        }

                                        btnSDate.setText(dataArr[1]);
                                        btnEDate.setText(dataArr[2]);
                                        etReason.setText(dataArr[4]);

                                        finalLeaveId = dataArr[5];
//                                        finalDayId = dataArr[6];
//                                        finalHeadId = dataArr[7];

                                        String edtHead = dataArr[3];
                                        for (int i = 0; i < finalArrayGetHead.size(); i++) {
                                            if (finalArrayGetHead.get(i).getEmpName().equalsIgnoreCase(edtHead)) {
                                                spHead.setSelection(i);
                                            }
                                        }


                                    }
                                }, "true", "true", "true");
                        rvList.setAdapter(applyLeaveAdapter);
                    } else {
                        tvNoRecords.setVisibility(View.VISIBLE);
                        rvList.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                llHeader.setVisibility(View.GONE);
                rvList.setVisibility(View.GONE);
                tvNoRecords.setVisibility(View.VISIBLE);
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(getActivity(), getString(R.string.something_wrong));
            }
        });
    }

    private Map<String, String> getApplyLeaveParams() {
        HashMap<String, String> map = new HashMap<>();
        map.put("StaffID", PrefUtils.getInstance(getActivity()).getStringValue("StaffID", "0"));

        return map;
    }

    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        for (int i = 0; i < finalArray.size(); i++) {
            listDataHeader.add(finalArray.get(i).getCreatedate() + "|" + finalArray.get(i).getLeaveDays() + "|" + finalArray.get(i).getStatus() + "|" + finalArray.get(i).getHeadname() + "|" + finalArray.get(i).getReason());
            Log.d("header", "" + listDataHeader);
            ArrayList<LeaveModel.FinalArray> row = new ArrayList<>();
            row.add(finalArray.get(i));
            Log.d("row", "" + row);
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }

    }

    @Override
    public void deleteRecordWithId(String id) {
        callDeleteRecord(id);
    }

    private void callDeleteRecord(String id) {

        if (!Utils.checkNetwork(getActivity())) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().deleteStaffLeave(getDeleteLeaveParams(id), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel permissionModel, Response response) {
                Utils.dismissDialog();
                if (permissionModel == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (permissionModel.getSuccess() == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (permissionModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(getActivity(), getString(R.string.false_msg));
                    return;
                }
                if (permissionModel.getSuccess().equalsIgnoreCase("True")) {
                    Utils.ping(getActivity(), "Leave Request Deleted Successfully...");
                    btnSend.setText(R.string.send);
                    btnCancel.setVisibility(View.GONE);
                    finalDayId = "0";
                    finalHeadId = "0";
                    finalLeaveId = "0";
                    btnSDate.setText(Utils.getTodaysDate());
                    btnEDate.setText(Utils.getTodaysDate());
                    spLeaveDays.setSelection(0);
                    spHead.setSelection(0);
                    etReason.setText("");
                    callGetApplyLeaveRequest();
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

    private Map<String, String> getDeleteLeaveParams(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("LeaveID", id);
        return map;
    }

    private void callApplyLeaveListApi() {
        if (!Utils.checkNetwork(getActivity())) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().insertStaffLeaveRequest(getLeaveParams(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel permissionModel, Response response) {
                Utils.dismissDialog();
                if (permissionModel == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (permissionModel.getSuccess() == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (permissionModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(getActivity(), permissionModel.getYear());
                    return;
                }
                if (permissionModel.getSuccess().equalsIgnoreCase("True")) {

                    finalLeaveId = "0";
                    finalDayId = "0";
                    finalHeadId = "0";

                    Utils.ping(getActivity(), "Leave Request Inserted Successfully...");
                    callGetApplyLeaveRequest();
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

    private Map<String, String> getLeaveParams() {
        Map<String, String> map = new HashMap<>();
        map.put("LeaveID", finalLeaveId);
        map.put("FromDate", btnSDate.getText().toString());
        map.put("ToDate", btnEDate.getText().toString());
        map.put("StaffID", PrefUtils.getInstance(getActivity()).getStringValue("StaffID", "0"));
        map.put("HeadID", finalHeadId);
        map.put("LeaveDays", finalDayId);
        map.put("Reason", etReason.getText().toString());
        return map;
    }

}
