package com.skool360admin.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.ExpandableListAdapterInquiryData;
import anandniketan.com.bhadajadmin.Interface.onViewClick;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentInquiryModel;
import anandniketan.com.bhadajadmin.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.bhadajadmin.Model.Transport.TermModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiClient;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.Utility.WebServices;
import anandniketan.com.bhadajadmin.databinding.FragmentStudentViewInquiryBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Callback;


public class StudentViewInquiryFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static String dateFinal;
    private static boolean isFromDate = false;
    private FragmentStudentViewInquiryBinding fragmentStudentViewInquiryBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private int Year, Month, Day;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private HashMap<Integer, String> spinnerOrderMap;
    private List<FinalArrayGetTermModel> finalArrayGetTermModels;
    private List<FinalArrayGetTermModel> finalArrayGetStatusModels;
    private HashMap<Integer, String> spinnerTermMap;
    private List<StudentAttendanceFinalArray> finalArrayinquiryCountList;
    private List<StudentInquiryModel.FinalArray> finalArrayinquiryCountList1;
    private List<StudentInquiryModel.StausDetail> finalStatusArrayinquiryCountList;
    private int lastExpandedPosition = -1;
    private String FinalStartDateStr, FinalEndDateStr, FinalStatusStr, FinalStatusIdStr, FinalTermIdStr, FinalTermStr, FinalOnlineStatusStr = "All";
    private ExpandableListAdapterInquiryData expandableListAdapterInquiryData;
    private List<StudentInquiryModel.FinalArray> listDataHeader;
    private HashMap<String, List<StudentInquiryModel.StausDetail>> listDataChild;
    private String status;
    private TextView tvHeader;
    private Button btnBack, btnMenu;


    public StudentViewInquiryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStudentViewInquiryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_view_inquiry, container, false);

        rootView = fragmentStudentViewInquiryBinding.getRoot();
        mContext = Objects.requireNonNull(getActivity()).getApplicationContext();

        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 11;

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            status = bundle.getString("status");
        } else {
            status = AppConfiguration.inquiryviewstatus;
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view1 = view.findViewById(R.id.header);
        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        setListners();
        callTermApi();
    }

    public void setListners() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        if (TextUtils.isEmpty(AppConfiguration.fromDate) && TextUtils.isEmpty(AppConfiguration.toDate)) {
            AppConfiguration.fromDate = Utils.getTodaysDate();
            AppConfiguration.toDate = Utils.getTodaysDate();
        }

        fragmentStudentViewInquiryBinding.startdateButton.setText(AppConfiguration.fromDate);
        fragmentStudentViewInquiryBinding.enddateButton.setText(AppConfiguration.toDate);

        tvHeader.setText(R.string.viewinquiry);

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
                if (fragmentManager != null) {
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                }
            }
        });

        fragmentStudentViewInquiryBinding.lvExpviewinquiry.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    fragmentStudentViewInquiryBinding.lvExpviewinquiry.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }

        });

        fragmentStudentViewInquiryBinding.fabAddInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppConfiguration.inquiryviewstatus = status;

                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 58;
                fragment = new FragmentAddUpdateInquiry();
                fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).add(R.id.frame_container, fragment).addToBackStack(null).commit();
                }
            }
        });

        fragmentStudentViewInquiryBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentStudentViewInquiryBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentStudentViewInquiryBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid;
                Log.d("FinalTermIdStr", FinalTermIdStr);
                callInquiryCountApi();
                callInquiryDataApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        fragmentStudentViewInquiryBinding.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentStudentViewInquiryBinding.statusSpinner.getSelectedItem().toString();
                String getid = spinnerOrderMap.get(fragmentStudentViewInquiryBinding.statusSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalStatusIdStr = getid;
                Log.d("FinalStatusIdStr", FinalStatusIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentStudentViewInquiryBinding.statusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = radioGroup.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    // checkedId is the RadioButton selected
                    switch (checkedId) {
                        case R.id.all_chk:
                            FinalOnlineStatusStr = fragmentStudentViewInquiryBinding.allChk.getTag().toString();
                            break;
                        case R.id.online_inquiry_chk:
                            FinalOnlineStatusStr = fragmentStudentViewInquiryBinding.onlineInquiryChk.getTag().toString();
                            break;
                        case R.id.offline_inquiry_chk:
                            FinalOnlineStatusStr = fragmentStudentViewInquiryBinding.offlineInquiryChk.getTag().toString();
                            break;
                    }
                }
            }
        });
        fragmentStudentViewInquiryBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callInquiryDataApi();
            }
        });
        fragmentStudentViewInquiryBinding.startdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromDate = true;
                datePickerDialog = DatePickerDialog.newInstance(StudentViewInquiryFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "DatePickerDialog");
            }
        });
        fragmentStudentViewInquiryBinding.enddateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromDate = false;
                datePickerDialog = DatePickerDialog.newInstance(StudentViewInquiryFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "DatePickerDialog");
            }
        });
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
//                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess() == null) {
//                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("false")) {
//                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayGetTermModels = termModel.getFinalArray();
                    if (finalArrayGetTermModels != null) {
                        fillTermSpinner();
                        callInquiryStatus();
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
        return new HashMap<>();
    }

    // CALL InquiryCount API HERE
    private void callInquiryCountApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getInquiryCount(getInquiryCountDetail(), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel inquiryCountModel, Response response) {
//                Utils.dismissDialog();
                if (inquiryCountModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (inquiryCountModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (inquiryCountModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
//                    Utils.dismissDialog();
                    return;
                }
                if (inquiryCountModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayinquiryCountList = inquiryCountModel.getFinalArray();
                    if (finalArrayinquiryCountList != null) {
                        fillInquiryCount();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
//                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getInquiryCountDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("TermId", FinalTermIdStr);
        return map;
    }


    @Override
    public void onResume() {
        super.onResume();
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 11;
    }

    // CALL InquiryData API HERE
    private void callInquiryDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getInquiryData(getInquiryDetail(), new retrofit.Callback<StudentInquiryModel>() {
            @Override
            public void success(StudentInquiryModel inquiryDataModel, Response response) {

                if (inquiryDataModel == null) {
                    Utils.dismissDialog();
//                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (inquiryDataModel.getSuccess() == null) {
                    Utils.dismissDialog();
//                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (inquiryDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.dismissDialog();
//                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentStudentViewInquiryBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentStudentViewInquiryBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentStudentViewInquiryBinding.listHeader.setVisibility(View.GONE);
                    return;
                }
                if (inquiryDataModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayinquiryCountList1 = inquiryDataModel.getFinalArray();

                    if (finalArrayinquiryCountList != null) {
                        fragmentStudentViewInquiryBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentStudentViewInquiryBinding.lvExpHeader.setVisibility(View.VISIBLE);
                        fragmentStudentViewInquiryBinding.listHeader.setVisibility(View.VISIBLE);
                        fillExpLV();
                        expandableListAdapterInquiryData = new ExpandableListAdapterInquiryData(getActivity(), listDataHeader, listDataChild, new onViewClick() {
                            @Override
                            public void getViewClick() {
                                AppConfiguration.firsttimeback = true;
                                AppConfiguration.position = 58;
                                fragment = new StudentInquiryProfileFragment();
                                fragmentManager = getFragmentManager();
                                if (fragmentManager != null) {
                                    fragmentManager.beginTransaction()
                                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).add(R.id.frame_container, fragment).addToBackStack(null).commit();
                                }
                            }
                        }, status);
                        fragmentStudentViewInquiryBinding.lvExpviewinquiry.setAdapter(expandableListAdapterInquiryData);
                        Utils.dismissDialog();
                    } else {
                        Utils.dismissDialog();
                        fragmentStudentViewInquiryBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentStudentViewInquiryBinding.lvExpHeader.setVisibility(View.GONE);
                        fragmentStudentViewInquiryBinding.listHeader.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
//                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getInquiryDetail() {
        getFinalAllId();
        Map<String, String> map = new HashMap<>();
        map.put("stdt", FinalStartDateStr);
        map.put("enddt", FinalEndDateStr);
        // map.put("onlineStatus",FinalOnlineStatusStr);
        map.put("status", FinalStatusIdStr);
        map.put("TermID", FinalTermIdStr);
        return map;
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
        if (isFromDate) {
            AppConfiguration.fromDate = dateFinal;
            fragmentStudentViewInquiryBinding.startdateButton.setText(dateFinal);

        } else {

            AppConfiguration.toDate = dateFinal;
            fragmentStudentViewInquiryBinding.enddateButton.setText(dateFinal);
        }
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
        fragmentStudentViewInquiryBinding.termSpinner.setAdapter(adapterTerm);

        FinalTermIdStr = spinnerTermMap.get(0);

        callInquiryCountApi();
    }

    public void fillStatusSpinner() {

        ArrayList<String> TermId = new ArrayList<>();
        for (int i = 0; i < finalArrayGetStatusModels.size(); i++) {
            TermId.add(String.valueOf(finalArrayGetStatusModels.get(i).getValue()));
        }
        ArrayList<String> Term = new ArrayList<>();
        for (int j = 0; j < finalArrayGetStatusModels.size(); j++) {
            Term.add(finalArrayGetStatusModels.get(j).getTerm());
        }

        String[] spinnerstatusIdArray = new String[TermId.size()];

        spinnerOrderMap = new HashMap<>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerOrderMap.put(i, String.valueOf(TermId.get(i)));
            spinnerstatusIdArray[i] = Term.get(i).trim();
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_layout, spinnerstatusIdArray);
        fragmentStudentViewInquiryBinding.statusSpinner.setAdapter(adapterTerm);

//        statusIdArray.add("All");
//        statusIdArray.add("Generated");
//        statusIdArray.add("Admission Form Issued");
//        statusIdArray.add("Received Admission Form");
//        statusIdArray.add("Interaction Call");
//        statusIdArray.add("Interview Done");
//        statusIdArray.add("Generate Circular");
//        statusIdArray.add("Fees Paid");
//
//
//        statusdetail.add("All");
//        statusdetail.add("Inquiry Generated");
//        statusdetail.add("Generated Admission Form");
//        statusdetail.add("Received Admission Form");
//        statusdetail.add("Interaction Call");
//        statusdetail.add("Come for Interview");
//        statusdetail.add("Confirm Admission");
//        statusdetail.add("Fees Paid");
//
//        String[] spinnerstatusIdArray = new String[statusIdArray.size()];
//
//        spinnerOrderMap = new HashMap<>();
//        for (int i = 0; i < statusIdArray.size(); i++) {
//            spinnerOrderMap.put(i, String.valueOf(statusIdArray.get(i)));
//            spinnerstatusIdArray[i] = statusdetail.get(i).trim();
//        }
//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentViewInquiryBinding.statusSpinner);
//
//            popupWindow.setHeight(spinnerstatusIdArray.length > 4 ? 500 : spinnerstatusIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }

//        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinnerstatusIdArray);
//        fragmentStudentViewInquiryBinding.statusSpinner.setAdapter(adapterTerm);
//
        FinalStatusIdStr = spinnerOrderMap.get(0);

//        fragmentStudentViewInquiryBinding.statusSpinner.setSelection(0);

    }

    public void fillInquiryCount() {
        for (int i = 0; i < finalArrayinquiryCountList.size(); i++) {
            if (finalArrayinquiryCountList.get(i).getName().equalsIgnoreCase("Total")) {
                fragmentStudentViewInquiryBinding.totalInquiryCount.setText(finalArrayinquiryCountList.get(i).getTotal());
            } else if (finalArrayinquiryCountList.get(i).getName().equalsIgnoreCase("Confirmed")) {
                fragmentStudentViewInquiryBinding.totalConfirmCount.setText(finalArrayinquiryCountList.get(i).getTotal());
            }
        }
    }

    public void getFinalAllId() {
        FinalStartDateStr = AppConfiguration.fromDate;
        FinalEndDateStr = AppConfiguration.toDate;
    }

    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        for (int i = 0; i < finalArrayinquiryCountList1.size(); i++) {
            listDataHeader.add(finalArrayinquiryCountList1.get(i));
            Log.d("header", "" + listDataHeader);
            List<StudentInquiryModel.StausDetail> row = new ArrayList<>();

            String studentId = String.valueOf(listDataHeader.get(i).getStudentID());

            for (int innerCount = 0; innerCount < finalArrayinquiryCountList1.get(i).getStausDetail().size(); innerCount++) {
                finalArrayinquiryCountList1.get(i).getStausDetail().get(innerCount).setStudentId(studentId);
            }

            Log.d("row", "" + row);
            listDataChild.put(String.valueOf(listDataHeader.get(i).getStudentID()), finalArrayinquiryCountList1.get(i).getStausDetail());
            Log.d("child", "" + listDataChild);
        }
    }

    //Standard Filter
    // CALL Standard API HERE
    private void callInquiryStatus() {

        if (!Utils.checkNetwork(Objects.requireNonNull(getActivity()))) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());

        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        Call<TermModel> call = apiService.getInquiryStatus();
        call.enqueue(new Callback<TermModel>() {

            @Override
            public void onResponse(@NonNull Call<TermModel> call, @NonNull retrofit2.Response<TermModel> response) {
                Utils.dismissDialog();
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
                    finalArrayGetStatusModels = response.body().getFinalArray();
                    if (finalArrayGetStatusModels != null) {
                        fillStatusSpinner();

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TermModel> call, @NonNull Throwable t) {
                Utils.dismissDialog();
                t.printStackTrace();
                t.getMessage();
                Utils.ping(getActivity(), getString(R.string.something_wrong));
            }
        });
    }

}

