package anandniketan.com.skool360.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.CompoundButton;
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

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.BulkSMSDetailListAdapter;
import anandniketan.com.skool360.Interface.getEmployeeCheck;
import anandniketan.com.skool360.Model.Account.FinalArrayStandard;
import anandniketan.com.skool360.Model.Account.GetStandardModel;
import anandniketan.com.skool360.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.skool360.Model.Other.FinalArraySMSDataModel;
import anandniketan.com.skool360.Model.Other.GetStaffSMSDataModel;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentStudentAbsentBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class StudentAbsentFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    HashMap<Integer, String> spinnerTermMap;
    List<FinalArrayStandard> finalArrayStandardsList;
    HashMap<Integer, String> spinnerStandardMap;
    HashMap<Integer, String> spinnerSectionMap;
    String FinalStandardIdStr, FinalClassIdStr, StandardName, FinalDateStr, FinalStandardStr, FinalSectionStr;
    List<FinalArraySMSDataModel> finalArrayBulkSMSModelList;
    BulkSMSDetailListAdapter bulkSMSDetailListAdapter;
    String finalstudentAbsentIdArray, finalmessageMessageLine, finalDateStr;
    int Year, Month, Day;
    Calendar calendar;
    int mYear, mMonth, mDay;
    private FragmentStudentAbsentBinding fragmentStudentAbsentBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private boolean temp = false;
    private TextView message_edt;
    private Button send_btn, close_btn;
    private AlertDialog alertDialogAndroid = null;
    private DatePickerDialog datePickerDialog;
    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public StudentAbsentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStudentAbsentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_absent, container, false);

        rootView = fragmentStudentAbsentBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.studentabsent);

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        fragmentStudentAbsentBinding.dateBtn.setText(Utils.getTodaysDate());
        setListners();
//        callTermApi();
        callStandardApi();

    }

    public void setListners() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new SMSFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentStudentAbsentBinding.dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = DatePickerDialog.newInstance(StudentAbsentFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });

        fragmentStudentAbsentBinding.gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentStudentAbsentBinding.gradeSpinner.getSelectedItem().toString();
                String getid = spinnerStandardMap.get(fragmentStudentAbsentBinding.gradeSpinner.getSelectedItemPosition());

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

        fragmentStudentAbsentBinding.sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedsectionstr = fragmentStudentAbsentBinding.sectionSpinner.getSelectedItem().toString();
                String getid = spinnerSectionMap.get(fragmentStudentAbsentBinding.sectionSpinner.getSelectedItemPosition());

                Log.d("value", selectedsectionstr + " " + getid);
                FinalClassIdStr = getid;
                FinalSectionStr = selectedsectionstr;
                Log.d("FinalClassIdStr", FinalClassIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentStudentAbsentBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callGetAbsentTodayApi();
            }
        });
        fragmentStudentAbsentBinding.smsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < finalArrayBulkSMSModelList.size(); i++) {
                        finalArrayBulkSMSModelList.get(i).setCheck("1");
                    }
                    bulkSMSDetailListAdapter.notifyDataSetChanged();
                    temp = false;
                } else {
                    if (!temp) {
                        for (int i = 0; i < finalArrayBulkSMSModelList.size(); i++) {
                            finalArrayBulkSMSModelList.get(i).setCheck("0");
                        }
                        bulkSMSDetailListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        fragmentStudentAbsentBinding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage();
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
        ApiHandler.getApiService().getStandardDetail(getStandardDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<GetStandardModel>() {
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
        return map;
    }

    // CALL GetBulkSMSData API HERE
    private void callGetAbsentTodayApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getAbsentToday(getAbsentTodayDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<GetStaffSMSDataModel>() {
            @Override
            public void success(GetStaffSMSDataModel getBulkSMSDataModel, Response response) {
                Utils.dismissDialog();
                if (getBulkSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getBulkSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getBulkSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    if (getBulkSMSDataModel.getFinalArray().size() == 0) {
                        fragmentStudentAbsentBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentStudentAbsentBinding.studentAbsentSmsDetailList.setVisibility(View.GONE);
                        fragmentStudentAbsentBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentStudentAbsentBinding.listHeader.setVisibility(View.GONE);
                        fragmentStudentAbsentBinding.submitBtn.setVisibility(View.GONE);
                    }
                    return;
                }
                if (getBulkSMSDataModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayBulkSMSModelList = getBulkSMSDataModel.getFinalArray();
                    if (finalArrayBulkSMSModelList != null) {
                        fillDataList();
                        Utils.dismissDialog();
                    } else {
                        fragmentStudentAbsentBinding.txtNoRecords.setVisibility(View.VISIBLE);
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

    private Map<String, String> getAbsentTodayDetail() {
        if (FinalStandardIdStr.equalsIgnoreCase("0")) {
            FinalStandardIdStr = "";
        }
        if (FinalClassIdStr.equalsIgnoreCase("0")) {
            FinalClassIdStr = "";
        }
        FinalDateStr = fragmentStudentAbsentBinding.dateBtn.getText().toString();
        Map<String, String> map = new HashMap<>();
        map.put("dt", FinalDateStr);
        map.put("grade", FinalStandardIdStr);
        map.put("section", FinalClassIdStr);
        return map;
    }

    public void fillGradeSpinner() {
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("All");

        ArrayList<String> standardname = new ArrayList<>();
        for (int z = 0; z < firstValue.size(); z++) {
            standardname.add(firstValue.get(z));
            for (int i = 0; i < finalArrayStandardsList.size(); i++) {
                standardname.add(finalArrayStandardsList.get(i).getStandard());
            }
        }
        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        ArrayList<Integer> standardId = new ArrayList<>();
        for (int m = 0; m < firstValueId.size(); m++) {
            standardId.add(firstValueId.get(m));
            for (int j = 0; j < finalArrayStandardsList.size(); j++) {
                standardId.add(finalArrayStandardsList.get(j).getStandardID());
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentAbsentBinding.gradeSpinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 500 : spinnerstandardIdArray.length * 100);
//            popupWindow1.setHeght(200);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }


        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        fragmentStudentAbsentBinding.gradeSpinner.setAdapter(adapterstandard);

        FinalStandardIdStr = spinnerStandardMap.get(0);
    }

    public void fillSection() {
        ArrayList<String> sectionname = new ArrayList<>();
        ArrayList<Integer> sectionId = new ArrayList<>();
        ArrayList<String> firstSectionValue = new ArrayList<String>();
        firstSectionValue.add("All");
        ArrayList<Integer> firstSectionId = new ArrayList<>();
        firstSectionId.add(0);

        if (StandardName.equalsIgnoreCase("All")) {
            for (int j = 0; j < firstSectionValue.size(); j++) {
                sectionname.add(firstSectionValue.get(j));
            }
            for (int i = 0; i < firstSectionId.size(); i++) {
                sectionId.add(firstSectionId.get(i));
            }

        }
        for (int z = 0; z < finalArrayStandardsList.size(); z++) {
            if (StandardName.equalsIgnoreCase(finalArrayStandardsList.get(z).getStandard())) {
                for (int j = 0; j < firstSectionValue.size(); j++) {
                    sectionname.add(firstSectionValue.get(j));
                    for (int i = 0; i < finalArrayStandardsList.get(z).getSectionDetail().size(); i++) {
                        sectionname.add(finalArrayStandardsList.get(z).getSectionDetail().get(i).getSection());
                    }
                }
                for (int j = 0; j < firstSectionId.size(); j++) {
                    sectionId.add(firstSectionId.get(j));
                    for (int m = 0; m < finalArrayStandardsList.get(z).getSectionDetail().size(); m++) {
                        sectionId.add(finalArrayStandardsList.get(z).getSectionDetail().get(m).getSectionID());
                    }
                }
            }
        }

        String[] spinnersectionIdArray = new String[sectionId.size()];

        spinnerSectionMap = new HashMap<Integer, String>();
        for (int i = 0; i < sectionId.size(); i++) {
            spinnerSectionMap.put(i, String.valueOf(sectionId.get(i)));
            spinnersectionIdArray[i] = sectionname.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentAbsentBinding.sectionSpinner);

            popupWindow.setHeight(spinnersectionIdArray.length > 4 ? 500 : spinnersectionIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnersectionIdArray);
        fragmentStudentAbsentBinding.sectionSpinner.setAdapter(adapterstandard);

        FinalClassIdStr = spinnerSectionMap.get(0);
        callGetAbsentTodayApi();
    }

    public void fillDataList() {
        fragmentStudentAbsentBinding.txtNoRecords.setVisibility(View.GONE);
        fragmentStudentAbsentBinding.studentAbsentSmsDetailList.setVisibility(View.VISIBLE);
        fragmentStudentAbsentBinding.recyclerLinear.setVisibility(View.VISIBLE);
        fragmentStudentAbsentBinding.listHeader.setVisibility(View.VISIBLE);
        fragmentStudentAbsentBinding.submitBtn.setVisibility(View.VISIBLE);

        for (int k = 0; k < finalArrayBulkSMSModelList.size(); k++) {
            finalArrayBulkSMSModelList.get(k).setCheck("0");
        }
        bulkSMSDetailListAdapter = new BulkSMSDetailListAdapter(mContext, finalArrayBulkSMSModelList, new getEmployeeCheck() {
            @Override
            public void getEmployeeSMSCheck() {
                List<FinalArraySMSDataModel> updatedData = bulkSMSDetailListAdapter.getDatas();
                Boolean data = false;
                int count = 0;

                for (int i = 0; i < updatedData.size(); i++) {
                    if (updatedData.get(i).getCheck().equalsIgnoreCase("1")) {
                        data = true;
                        count++;
                    } else {
                        count--;
                    }
                }

                if (count == updatedData.size()) {
                    fragmentStudentAbsentBinding.smsCheckbox.setChecked(true);
                } else {
                    temp = true;
                    fragmentStudentAbsentBinding.smsCheckbox.setChecked(false);
                }
                if (data) {
                    fragmentStudentAbsentBinding.submitBtn.setEnabled(true);
                    fragmentStudentAbsentBinding.submitBtn.setAlpha(1);
                } else {
                    fragmentStudentAbsentBinding.submitBtn.setEnabled(false);
                    fragmentStudentAbsentBinding.submitBtn.setAlpha((float) 0.5);
                }
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        fragmentStudentAbsentBinding.studentAbsentSmsDetailList.setLayoutManager(mLayoutManager);
        fragmentStudentAbsentBinding.studentAbsentSmsDetailList.setItemAnimator(new DefaultItemAnimator());
        fragmentStudentAbsentBinding.studentAbsentSmsDetailList.setAdapter(bulkSMSDetailListAdapter);
    }

    public void SendMessage() {
        LayoutInflater lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = lInflater.inflate(R.layout.insert_student_absent_message_item, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
        alertDialogBuilderUserInput.setView(layout);

        alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.setCancelable(false);
        alertDialogAndroid.show();
        Window window = alertDialogAndroid.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER_HORIZONTAL;
        wlp.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(wlp);
        alertDialogAndroid.show();

//        date_txt = (TextView) layout.findViewById(R.id.date_txt);
        message_edt = layout.findViewById(R.id.message_edt);
        send_btn = layout.findViewById(R.id.send_btn);
        close_btn = layout.findViewById(R.id.close_btn);


        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogAndroid.dismiss();
            }
        });

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> id = new ArrayList<>();
                List<FinalArraySMSDataModel> array = bulkSMSDetailListAdapter.getDatas();
                int j;
                for (j = 0; j < array.size(); j++) {
                    if (array.get(j).getCheck().equalsIgnoreCase("1")) {
                        id.add(array.get(j).getFkStudentID() + "|" + array.get(j).getSmsNo());
                        Log.d("checkid", "" + id.size());
                    } else {
                        id.remove(array.get(j).getFkStudentID() + "|" + array.get(j).getSmsNo());
                        Log.d("Uncheckid", "" + id.size());
                    }
                }
                Log.d("id", "" + id);
                finalstudentAbsentIdArray = String.valueOf(id);
                finalstudentAbsentIdArray = finalstudentAbsentIdArray.substring(1, finalstudentAbsentIdArray.length() - 1);

                finalmessageMessageLine = message_edt.getText().toString();
                Log.d("finalBulkIdArray", "" + finalstudentAbsentIdArray);
                finalDateStr = fragmentStudentAbsentBinding.dateBtn.getText().toString();
                Log.d("finalDateStr", "" + finalDateStr);

                if (!Utils.checkNetwork(mContext)) {
                    Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
                    return;
                }
                if (!finalstudentAbsentIdArray.equalsIgnoreCase("") && !finalmessageMessageLine.equalsIgnoreCase("") && !finalDateStr.equalsIgnoreCase("")) {
                    Utils.showDialog(getActivity());
                    ApiHandler.getApiService().InsertAbsentTodaySMS(InsertAbsentTodaySMS(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<InsertMenuPermissionModel>() {
                        @Override
                        public void success(InsertMenuPermissionModel insertMenuPermissionModel, Response response) {
                            Utils.dismissDialog();
                            if (insertMenuPermissionModel == null) {
                                Utils.ping(mContext, getString(R.string.something_wrong));
                                return;
                            }
                            if (insertMenuPermissionModel.getSuccess() == null) {
                                Utils.ping(mContext, getString(R.string.something_wrong));
                                return;
                            }
                            if (insertMenuPermissionModel.getSuccess().equalsIgnoreCase("false")) {
                                Utils.ping(mContext, getString(R.string.false_msg));

                                return;
                            }
                            if (insertMenuPermissionModel.getSuccess().equalsIgnoreCase("True")) {
                                Utils.ping(mContext, getString(R.string.true_msg));
                                alertDialogAndroid.dismiss();
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
                } else {
                    Utils.ping(mContext, getString(R.string.blank));
                }
            }
        });

    }

    private Map<String, String> InsertAbsentTodaySMS() {
        Map<String, String> map = new HashMap<>();
        map.put("SMS", finalmessageMessageLine);
        map.put("Date", finalDateStr);
        map.put("MobileNo", finalstudentAbsentIdArray);//finalstudentAbsentIdArray  "1|8672952197"
        return map;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
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

        fragmentStudentAbsentBinding.dateBtn.setText(d + "/" + m + "/" + y);
    }
}

