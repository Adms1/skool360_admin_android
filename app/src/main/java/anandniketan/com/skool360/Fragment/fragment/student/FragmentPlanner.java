package anandniketan.com.skool360.Fragment.fragment.student;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Interface.OnEditRecordWithPosition;
import anandniketan.com.skool360.Interface.onDeleteWithId;
import anandniketan.com.skool360.Model.Account.FinalArrayStandard;
import anandniketan.com.skool360.Model.Account.GetStandardModel;
import anandniketan.com.skool360.Model.Student.PlannerModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.activity.DashboardActivity;
import anandniketan.com.skool360.adapter.PlannerAdapter;
import anandniketan.com.skool360.adapter.StandardAdapter;
import anandniketan.com.skool360.databinding.FragmentPlannerBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FragmentPlanner extends Fragment implements OnEditRecordWithPosition, onDeleteWithId, DatePickerDialog.OnDateSetListener {

    private static String dateFinal;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private FragmentPlannerBinding fragmentPlannerBinding;
    private List<FinalArrayStandard> finalArrayStandardsList;
    private StandardAdapter standardAdapter;
    private PlannerModel finalArrayPlannerList1;
    private PlannerAdapter plannerAdapter;
    private OnEditRecordWithPosition onUpdateRecordRef;
    private onDeleteWithId onDeleteWithIdRef;
    private String FinalType = "Holiday", FinalTitle = "", FinalStartDate = "", FinalEnddate = "", FinalGradeIds = "", FinalID = "", PK_ID = "0";
    private DatePickerDialog datePickerDialog;
    private int whichdateViewClick = 1;
    private Calendar calendar;
    private int Year, Month, Day, stdsize;
    private boolean isRecordInUpdate = false;
    private String status, updateStatus, deletestatus;

    private TextView tvHeader;
    private Button btnBack, btnMenu, btnCancel;


    public FragmentPlanner() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AppConfiguration.position = 11;
        AppConfiguration.firsttimeback = true;
        onDeleteWithIdRef = this;
        onUpdateRecordRef = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentPlannerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_planner, container, false);

        Bundle bundle = this.getArguments();
        status = bundle.getString("status");
        updateStatus = bundle.getString("updatestatus");
        deletestatus = bundle.getString("deletestatus");

        rootView = fragmentPlannerBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        //Set Thread Policy
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().build());

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view1 = view.findViewById(R.id.header);
        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);
        btnCancel = view.findViewById(R.id.btn_cancel);

        tvHeader.setText(R.string.planner);

        setListner();
        callStandardApi();
    }

    private void setListner() {

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        fragmentPlannerBinding.fromDate1Edt.setText(Utils.getTodaysDate());
        fragmentPlannerBinding.toDate2Edt.setText(Utils.getTodaysDate());

        fragmentPlannerBinding.rbHoliday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    FinalType = compoundButton.getText().toString();
                }
            }
        });

        fragmentPlannerBinding.rbEvent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    FinalType = compoundButton.getText().toString();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new StudentFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentPlannerBinding.rbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (!isRecordInUpdate) {
                    if (finalArrayStandardsList != null) {
                        if (finalArrayStandardsList.size() > 0) {

                            for (int listsize = 0; listsize < finalArrayStandardsList.size(); listsize++) {
                                finalArrayStandardsList.get(listsize).setCheckedStatus("1");
                            }
                            if (standardAdapter != null) {
                                standardAdapter.notifyDataSetChanged();
                                //standardAdapter.disableSelection();
                            }
                        }
                    }
                    }
                    isRecordInUpdate = false;
                }
            }
        });

        fragmentPlannerBinding.rbIndividual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (!isRecordInUpdate) {
                    if (finalArrayStandardsList != null) {
                        if (finalArrayStandardsList.size() > 0) {

                            for (int listsize = 0; listsize < finalArrayStandardsList.size(); listsize++) {
                                finalArrayStandardsList.get(listsize).setCheckedStatus("0");
                            }
                            if (standardAdapter != null) {
                                standardAdapter.notifyDataSetChanged();
                                //  standardAdapter.enableSelection();
                            }
                        }
                    }
                    }
                }
            }
        });
        fragmentPlannerBinding.fromDate1Edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                whichdateViewClick = 1;
                datePickerDialog = DatePickerDialog.newInstance(FragmentPlanner.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });
        fragmentPlannerBinding.toDate2Edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichdateViewClick = 2;
                datePickerDialog = DatePickerDialog.newInstance(FragmentPlanner.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });

        fragmentPlannerBinding.btnAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fragmentPlannerBinding.btnAddUpdate.getText().toString().equalsIgnoreCase("Update")) {

                    if (updateStatus.equalsIgnoreCase("true")) {
                        if (validate()) {
                            callInsertUpdatePlannerDataApi();
                        }
                    } else {
                        Utils.ping(getActivity(), "Access Denied");
                    }
                } else {
                    if (validate()) {
                        callInsertUpdatePlannerDataApi();
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentPlannerBinding.btnAddUpdate.setText(R.string.Add);

                fragmentPlannerBinding.fromDate1Edt.setText(Utils.getTodaysDate());
                fragmentPlannerBinding.toDate2Edt.setText(Utils.getTodaysDate());
                fragmentPlannerBinding.titleEdt.setText("");
                fragmentPlannerBinding.rbAll.setChecked(true);
                fragmentPlannerBinding.rbHoliday.setChecked(true);

                if (finalArrayStandardsList != null) {
                    if (finalArrayStandardsList.size() > 0) {

                        for (int listsize = 0; listsize < finalArrayStandardsList.size(); listsize++) {
                            finalArrayStandardsList.get(listsize).setCheckedStatus("1");
                        }
                        if (standardAdapter != null) {
                            standardAdapter.notifyDataSetChanged();
                            //standardAdapter.disableSelection();
                        }
                    }
                }

                btnCancel.setVisibility(View.GONE);
            }
        });

    }

    private void callStandardApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }
        Utils.showDialog(getActivity());

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStandardDetail(getStandardDetail(), new retrofit.Callback<GetStandardModel>() {
            @Override
            public void success(GetStandardModel standardModel, Response response) {
                Utils.dismissDialog();
                if (standardModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    Utils.dismissDialog();
                    return;
                }
                if (standardModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    Utils.dismissDialog();
                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("True")) {
                    Utils.dismissDialog();
                    finalArrayStandardsList = standardModel.getFinalArray();
                    if (finalArrayStandardsList != null) {
                        for (int i = 0; i < finalArrayStandardsList.size(); i++) {
                            finalArrayStandardsList.get(i).setCheckedStatus("1");
                        }
                        standardAdapter = new StandardAdapter(mContext, finalArrayStandardsList);
                        stdsize = finalArrayStandardsList.size();
                        fragmentPlannerBinding.standardGridView.setAdapter(standardAdapter);
                    }

                    callPlannerDataApi();

                    //onUpdateRecordRef.onUpdateRecord();
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


    private void callPlannerDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }
        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getPlanner(getPlannerParams(), new retrofit.Callback<PlannerModel>() {
            @Override
            public void success(PlannerModel inquiryDataModel, Response response) {
                if (inquiryDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    Utils.dismissDialog();
                    return;
                }
                if (inquiryDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    Utils.dismissDialog();
                    return;
                }
                if (inquiryDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
                    fragmentPlannerBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentPlannerBinding.listHeader.setVisibility(View.GONE);
                    fragmentPlannerBinding.recyclerLinear.setVisibility(View.GONE);
                    return;
                }
                if (inquiryDataModel.getSuccess().equalsIgnoreCase("True")) {
                    Utils.dismissDialog();
                    finalArrayPlannerList1 = inquiryDataModel;

                    if (finalArrayPlannerList1 != null) {
                        fragmentPlannerBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentPlannerBinding.listHeader.setVisibility(View.VISIBLE);
                        fragmentPlannerBinding.recyclerLinear.setVisibility(View.VISIBLE);

                        plannerAdapter = new PlannerAdapter(getActivity(), finalArrayPlannerList1, onUpdateRecordRef, onDeleteWithIdRef, status, updateStatus, deletestatus);
                        fragmentPlannerBinding.plannerList.setLayoutManager(new LinearLayoutManager(getActivity()));
                        fragmentPlannerBinding.plannerList.setAdapter(plannerAdapter);


                    } else {
                        fragmentPlannerBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentPlannerBinding.listHeader.setVisibility(View.GONE);
                        fragmentPlannerBinding.recyclerLinear.setVisibility(View.GONE);
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

    private Map<String, String> getPlannerParams() {
        Map<String, String> map = new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }


    private void callInsertUpdatePlannerDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }
        Utils.showDialog(getActivity());
        ApiHandler.getApiService().insertPlanner(getInsertUpdatePlannerParams(), new retrofit.Callback<PlannerModel>() {
            @Override
            public void success(PlannerModel inquiryDataModel, Response response) {
                if (inquiryDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    Utils.dismissDialog();
                    return;
                }
                if (inquiryDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    Utils.dismissDialog();
                    return;
                }
                if (inquiryDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
                    return;
                }
                if (inquiryDataModel.getSuccess().equalsIgnoreCase("True")) {
                    Utils.dismissDialog();
                    if (!isRecordInUpdate) {
                        Utils.ping(getActivity(), "Added Successfully.");
                    } else {
                        Utils.ping(getActivity(), "Updated Successfully.");
                    }

                    PK_ID = "0";
                    isRecordInUpdate = false;
                    FinalType = "Holiday";
                    fragmentPlannerBinding.btnAddUpdate.setText("Add");
                    fragmentPlannerBinding.rbHoliday.setChecked(true);
                    fragmentPlannerBinding.fromDate1Edt.setText(Utils.getTodaysDate());
                    fragmentPlannerBinding.toDate2Edt.setText(Utils.getTodaysDate());
                    fragmentPlannerBinding.titleEdt.setText("");
                    fragmentPlannerBinding.rbAll.setChecked(true);
                    fragmentPlannerBinding.rbIndividual.setChecked(false);

                    callPlannerDataApi();
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

    private Map<String, String> getInsertUpdatePlannerParams() {
        Map<String, String> map = new HashMap<>();
        map.put("Type", FinalType);
        map.put("Title", fragmentPlannerBinding.titleEdt.getText().toString());
        map.put("StartDate", fragmentPlannerBinding.fromDate1Edt.getText().toString());
        map.put("EndDate", fragmentPlannerBinding.toDate2Edt.getText().toString());
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        if (fragmentPlannerBinding.rbAll.isChecked()) {
            // map.put("GradeID", "0");
            if (standardAdapter != null) {
                String selectedGradeIds = TextUtils.join(",", standardAdapter.getCheckedStandards());
                map.put("GradeIds", selectedGradeIds);
            }
        } else {
            if (standardAdapter != null) {
                String selectedGradeIds = TextUtils.join(",", standardAdapter.getCheckedStandards());
                map.put("GradeIds", selectedGradeIds);
            }
        }

        map.put("ID", PK_ID);

        return map;
    }


    @Override
    public void deleteRecordWithId(String id) {
        callDeleteePlannerDataApi(id);
    }


    private void callDeleteePlannerDataApi(String id) {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }
        Utils.showDialog(getActivity());
        ApiHandler.getApiService().deletePlanner(getDeletePlannerParams(id), new retrofit.Callback<PlannerModel>() {
            @Override
            public void success(PlannerModel inquiryDataModel, Response response) {
                if (inquiryDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    Utils.dismissDialog();
                    return;
                }
                if (inquiryDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    Utils.dismissDialog();
                    return;
                }
                if (inquiryDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
                    return;
                }
                if (inquiryDataModel.getSuccess().equalsIgnoreCase("True")) {
                    Utils.dismissDialog();
                    Utils.ping(getActivity(), "Deleted Successfully.");
                    callPlannerDataApi();
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

    private Map<String, String> getDeletePlannerParams(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("ID", id);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
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
        if (month < 10) {
            m = "0" + m;
        }

        dateFinal = d + "/" + m + "/" + y;
        if (whichdateViewClick == 1) {
            fragmentPlannerBinding.fromDate1Edt.setText(dateFinal);
            FinalStartDate = dateFinal;

        } else {
            if (whichdateViewClick == 2) {
                fragmentPlannerBinding.toDate2Edt.setText(dateFinal);
                FinalEnddate = dateFinal;
            }

        }
    }

    private boolean validate() {
        try {
            if (TextUtils.isEmpty(fragmentPlannerBinding.titleEdt.getText().toString().trim())) {
                Utils.ping(getActivity(), "Please enter title");
                return false;
            }
            if (fragmentPlannerBinding.rbIndividual.isChecked()) {
                if (standardAdapter != null) {
                    if (standardAdapter.getCheckedStandards() != null) {
                        if (standardAdapter.getCheckedStandards().size() <= 0) {
                            Utils.ping(getActivity(), "Please select standard");
                            return false;
                        }
                    }
                }
            }
            if (fragmentPlannerBinding.rbAll.isChecked()) {
                if (standardAdapter != null) {
                    if (standardAdapter.getCheckedStandards() != null) {
                        if (standardAdapter.getCheckedStandards().size() <= 0) {
                            Utils.ping(getActivity(), "Please select standard");
                            return false;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;

    }

    @Override
    public void getEditpermission(int pos) {
        isRecordInUpdate = true;
        fragmentPlannerBinding.btnAddUpdate.setText("Update");

        btnCancel.setVisibility(View.VISIBLE);

        try {
            if (finalArrayPlannerList1 != null) {
                if (finalArrayPlannerList1.getFinalArray().size() > 0) {

                    PlannerModel.FinalArray dataList = finalArrayPlannerList1.getFinalArray().get(pos);

                    if (dataList != null) {

                        PK_ID = String.valueOf(dataList.getID());

                        if (!TextUtils.isEmpty(dataList.getType())) {
                            if (dataList.getType().equalsIgnoreCase("Holiday")) {
                                fragmentPlannerBinding.rbHoliday.setChecked(true);
                                fragmentPlannerBinding.rbEvent.setChecked(false);
                            } else if (dataList.getType().equalsIgnoreCase("Event")) {
                                fragmentPlannerBinding.rbHoliday.setChecked(false);
                                fragmentPlannerBinding.rbEvent.setChecked(true);
                            }
                        }

                        if (!TextUtils.isEmpty(dataList.getStartDate())) {
                            fragmentPlannerBinding.fromDate1Edt.setText(dataList.getStartDate());
                        }
                        if (!TextUtils.isEmpty(dataList.getEndDate())) {
                            fragmentPlannerBinding.toDate2Edt.setText(dataList.getEndDate());
                        }
                        if (!TextUtils.isEmpty(dataList.getName())) {
                            fragmentPlannerBinding.titleEdt.setText(dataList.getName());
                        }

                        if (standardAdapter != null) {


                            String[] standards = dataList.getGradeID().split(",");

                            // standardAdapter.setCheckedStandards(items);

                            if (finalArrayStandardsList != null) {
                                if (finalArrayStandardsList.size() > 0) {

                                    for (int count2 = 0; count2 < finalArrayStandardsList.size(); count2++) {
                                        finalArrayStandardsList.get(count2).setCheckedStatus("0");
                                    }

                                    for (int count3 = 0; count3 < finalArrayStandardsList.size(); count3++) {
                                        for (int count1 = 0; count1 < standards.length; count1++) {
                                            if (standards[count1].equalsIgnoreCase(String.valueOf(finalArrayStandardsList.get(count3).getStandardID()))) {
                                                finalArrayStandardsList.get(count3).setCheckedStatus("1");
                                            }
                                        }
                                    }

                                    standardAdapter.notifyDataSetChanged();

                                }
                            }
                            //  standardAdapter.notifyDataSetChanged();
                            if (standards.length == stdsize) {
                                fragmentPlannerBinding.rbAll.setChecked(true);
                                fragmentPlannerBinding.rbIndividual.setChecked(false);
                            } else {
                                fragmentPlannerBinding.rbAll.setChecked(false);
                                fragmentPlannerBinding.rbIndividual.setChecked(true);
                            }

                        }
                        isRecordInUpdate = true;

                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
