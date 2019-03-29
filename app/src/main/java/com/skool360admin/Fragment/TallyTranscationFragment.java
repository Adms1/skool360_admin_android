package com.skool360admin.Fragment;

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

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.TallyTransactionAdapter;
import anandniketan.com.bhadajadmin.Model.Account.FinalArrayStandard;
import anandniketan.com.bhadajadmin.Model.Account.GetStandardModel;
import anandniketan.com.bhadajadmin.Model.Account.TallyTranscationModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentTallyTranscationBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TallyTranscationFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    private FragmentTallyTranscationBinding fragmentTallyTranscationBinding;
    private View rootView;
    private Context mContext;
    private List<FinalArrayStandard> finalArrayStandardsList;
    private HashMap<Integer, String> spinnerStandardMap;
    private String FinalClassIdStr = "",FinalStatusIdStr = "";
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private DatePickerDialog datePickerDialog;
    private int whichdateViewClick = 1 ;
    private Calendar calendar;
    private static String dateFinal;
    private int Year, Month, Day;
    private HashMap<Integer, String> spinnerOrderMap;
    private List<TallyTranscationModel.FinalArray> dailyCollectionsList;
    private TallyTransactionAdapter tallyTransactionAdapter;
    private String viewstatus;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        AppConfiguration.position = 41;
        AppConfiguration.firsttimeback = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentTallyTranscationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tally_transcation,container, false);

        rootView = fragmentTallyTranscationBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        Bundle bundle = this.getArguments();
        viewstatus = bundle.getString("viewstatus");

        //Set Thread Policy
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().build());

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.tally_transcation);

        setListner();
        callStandardApi();

    }

    public void setListner() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        fragmentTallyTranscationBinding.fromDate1Edt.setText(Utils.getTodaysDate());
        fragmentTallyTranscationBinding.toDate2Edt.setText(Utils.getTodaysDate());

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new AccountFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentTallyTranscationBinding.gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentTallyTranscationBinding.gradeSpinner.getSelectedItem().toString();
                String getid = spinnerStandardMap.get(fragmentTallyTranscationBinding.gradeSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalClassIdStr = getid;
                Log.d("FinalStandardIdStr", FinalClassIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentTallyTranscationBinding.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentTallyTranscationBinding.statusSpinner.getSelectedItem().toString();
                String getid = spinnerOrderMap.get(fragmentTallyTranscationBinding.statusSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalStatusIdStr = getid;
                Log.d("FinalStatusIdStr", FinalStatusIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentTallyTranscationBinding.fromDate1Edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichdateViewClick = 1;
                datePickerDialog = DatePickerDialog.newInstance(TallyTranscationFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });

        fragmentTallyTranscationBinding.toDate2Edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichdateViewClick = 2;
                datePickerDialog = DatePickerDialog.newInstance(TallyTranscationFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");

            }
        });

        fragmentTallyTranscationBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callTallyTranscationListApi();
            }
        });
    }

    private void callTallyTranscationListApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTallyTransactionList(getTallyDetail(),new retrofit.Callback<TallyTranscationModel>() {
            @Override
            public void success(TallyTranscationModel standardModel, Response response) {
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
                    fragmentTallyTranscationBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentTallyTranscationBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentTallyTranscationBinding.lvExpstudentfeescollection.setVisibility(View.GONE);
                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("True")) {
                    dailyCollectionsList = standardModel.getFinalArray();
                    if (dailyCollectionsList != null) {
                        fragmentTallyTranscationBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentTallyTranscationBinding.lvExpHeader.setVisibility(View.VISIBLE);
                        fragmentTallyTranscationBinding.lvExpstudentfeescollection.setVisibility(View.VISIBLE);
                        //fillExpLV();
                        fragmentTallyTranscationBinding.lvExpstudentfeescollection.setLayoutManager(new LinearLayoutManager(getActivity()));
                        tallyTransactionAdapter = new TallyTransactionAdapter(getActivity(), dailyCollectionsList, viewstatus);
                        fragmentTallyTranscationBinding.lvExpstudentfeescollection.setAdapter(tallyTransactionAdapter);
                        Utils.dismissDialog();
                    } else {
                        fragmentTallyTranscationBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentTallyTranscationBinding.lvExpHeader.setVisibility(View.GONE);
                        fragmentTallyTranscationBinding.lvExpstudentfeescollection.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                fragmentTallyTranscationBinding.txtNoRecords.setVisibility(View.VISIBLE);
                fragmentTallyTranscationBinding.txtNoRecords.setText(error.getMessage());
                fragmentTallyTranscationBinding.lvExpHeader.setVisibility(View.GONE);
                fragmentTallyTranscationBinding.lvExpstudentfeescollection.setVisibility(View.GONE);
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getTallyDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("StartDate",fragmentTallyTranscationBinding.fromDate1Edt.getText().toString());
        map.put("EndDate",fragmentTallyTranscationBinding.toDate2Edt.getText().toString());
        map.put("Status",FinalStatusIdStr);
        map.put("StandardID",FinalClassIdStr);
        return map;
    }

    public void fillStatusSpinner() {
        ArrayList<String> statusIdArray = new ArrayList<String>();
        statusIdArray.add("All");
        statusIdArray.add("0");
        statusIdArray.add("1");



        ArrayList<String> statusdetail = new ArrayList<>();
        statusdetail.add("All");
        statusdetail.add("Pending Receipt");
        statusdetail.add("Receipt Generated");

        String[] spinnerstatusIdArray = new String[statusIdArray.size()];

        spinnerOrderMap = new HashMap<Integer, String>();
        for (int i = 0; i < statusIdArray.size(); i++) {
            spinnerOrderMap.put(i, String.valueOf(statusIdArray.get(i)));
            spinnerstatusIdArray[i] = statusdetail.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentTallyTranscationBinding.statusSpinner);

            popupWindow.setHeight(spinnerstatusIdArray.length > 4 ? 500 : spinnerstatusIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstatusIdArray);
        fragmentTallyTranscationBinding.statusSpinner.setAdapter(adapterTerm);

        FinalStatusIdStr = spinnerOrderMap.get(0);

        callTallyTranscationListApi();

    }



    private void callStandardApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

       Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStandardSectionCombine(getStandardDetail(),new retrofit.Callback<GetStandardModel>() {
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

    private Map<String, String> getStandardDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
    }


    public void fillGradeSpinner() {
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("All");
//
        ArrayList<String> standardname = new ArrayList<>();
        for (int z = 0; z < firstValue.size(); z++) {
            standardname.add(firstValue.get(z));
            for (int i = 0; i < finalArrayStandardsList.size(); i++) {
                standardname.add(finalArrayStandardsList.get(i).getStandardClass());
            }
        }
        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        ArrayList<Integer> standardId = new ArrayList<>();
        for (int m = 0; m < firstValueId.size(); m++) {
            standardId.add(firstValueId.get(m));
            for (int j = 0; j < finalArrayStandardsList.size(); j++) {
                standardId.add(finalArrayStandardsList.get(j).getClassID());
            }
        }
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentTallyTranscationBinding.gradeSpinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 500 : spinnerstandardIdArray.length * 100);
//            popupWindow1.setHeght(200);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }


        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        fragmentTallyTranscationBinding.gradeSpinner.setAdapter(adapterstandard);

        FinalClassIdStr = spinnerStandardMap.get(0);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        populateSetDate(year,monthOfYear,dayOfMonth);
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
        if (whichdateViewClick  == 1) {
            fragmentTallyTranscationBinding.fromDate1Edt.setText(dateFinal);

        } else {
            fragmentTallyTranscationBinding.toDate2Edt.setText(dateFinal);
        }
    }
}
