package com.skool360admin.Fragment;

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
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
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
import anandniketan.com.bhadajadmin.Model.Account.FinalArrayStandard;
import anandniketan.com.bhadajadmin.Model.Account.GetStandardModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentInquiryModel;
import anandniketan.com.bhadajadmin.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.bhadajadmin.Model.Transport.TermModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.DateUtils;
import anandniketan.com.bhadajadmin.Utility.PrefUtils;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentAddInquiryBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FragmentAddUpdateInquiry extends Fragment implements DatePickerDialog.OnDateSetListener{

    private FragmentAddInquiryBinding fragmentAddInquiryBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private List<FinalArrayStandard> finalArrayStandardsList;
    private HashMap<Integer, String> spinnerStandardMap;
    private DatePickerDialog datePickerDialog;
    private String FinalStandardIdStr, FinalClassIdStr = "",StandardName,FinalTermIdStr, FinalStandardStr, FinalSectionStr,FinalGender = "Male",FinalStatusStr, FinalStatusIdStr;
    private int Year, Month, Day;
    private Calendar calendar;
    private int  whichDatePickerCalled  = 1;
    private String dateFinal = "";
    private List<FinalArrayGetTermModel> finalArrayGetTermModels;
    private HashMap<Integer, String> spinnerTermMap;
    HashMap<Integer, String> spinnerSectionMap;
    private PrefUtils prefUtils;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public FragmentAddUpdateInquiry() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentAddInquiryBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_inquiry,container,false);

        rootView = fragmentAddInquiryBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        prefUtils = PrefUtils.getInstance(getActivity());

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view1 = view.findViewById(R.id.header);
        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        setListener();
        callTermApi();
        callGradeApi();
    }

    private void setListener(){
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        tvHeader.setText(R.string.addinquiry);

//        fragmentAddInquiryBinding.firstnameTxt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
//        fragmentAddInquiryBinding.lastnameTxt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
//        fragmentAddInquiryBinding.middleTxt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

//        fragmentAddInquiryBinding.firstnameTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
//        fragmentAddInquiryBinding.lastnameTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
//        fragmentAddInquiryBinding.middleTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
//        fragmentAddInquiryBinding.dobTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
//        fragmentAddInquiryBinding.previousSchoolTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
//        fragmentAddInquiryBinding.fatherFirstnameTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
//        fragmentAddInquiryBinding.fatherLastnameTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
//        fragmentAddInquiryBinding.fatherMiddlenameTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
       // fragmentAddInquiryBinding.fatherMobilenoTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);


        fragmentAddInquiryBinding.dateTxt.setText(Utils.getTodaysDate());

        fragmentAddInquiryBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm()){
                    callInsertEnquiry();
                }
            }
        });

        fragmentAddInquiryBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFields();
            }
        });

        fragmentAddInquiryBinding.rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rb_male) {
                    FinalGender = "Male";
                    fragmentAddInquiryBinding.rbMale.setChecked(true);
                    fragmentAddInquiryBinding.rbFemale1.setChecked(false);

                }
                else if(checkedId==R.id.rb_female1) {
                    FinalGender = "Female";
                    fragmentAddInquiryBinding.rbMale.setChecked(false);
                    fragmentAddInquiryBinding.rbFemale1.setChecked(true);
                }

            }
        });
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//               if(compoundButton.getId() == R.id.rb_male){
//                   FinalGender = "Male";
//
//               }
//
//
//
//            }
//        });


        fragmentAddInquiryBinding.rbFemale1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                FinalGender = "Female";
            }
        });


        fragmentAddInquiryBinding.gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentAddInquiryBinding.gradeSpinner.getSelectedItem().toString();
                String getid = String.valueOf(spinnerStandardMap.get(fragmentAddInquiryBinding.gradeSpinner.getSelectedItemPosition()));

                Log.d("value", name + " " + getid);
                FinalStandardIdStr = getid;
                Log.d("FinalStandardIdStr", FinalStandardIdStr);
                StandardName = name;
                if (name.equalsIgnoreCase("All")){
                    FinalStandardStr = "0";
                }else{
                    FinalStandardStr = name;
                }
                Log.d("StandardName", FinalStandardStr);
                fillSection();

                //  Log.d("FinalTermDetailIdStr", FinalTermDetailIdStr);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentAddInquiryBinding.sessionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentAddInquiryBinding.sessionSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get( fragmentAddInquiryBinding.sessionSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid.toString();
                Log.d("FinalTermIdStr", FinalTermIdStr);

                AppConfiguration.TermName=name;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentAddInquiryBinding.sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedsectionstr = fragmentAddInquiryBinding.sectionSpinner.getSelectedItem().toString();
                String getid = spinnerSectionMap.get(fragmentAddInquiryBinding.sectionSpinner.getSelectedItemPosition());

                Log.d("value", selectedsectionstr + " " + getid);
                FinalClassIdStr = getid;

                if (selectedsectionstr.equalsIgnoreCase("All") || selectedsectionstr.equalsIgnoreCase("Select")){
                    FinalSectionStr = "0";

                }else{
                    FinalSectionStr = selectedsectionstr;

                }

                Log.d("FinalClassIdStr", FinalSectionStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
            public void onClick(View v) {
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 58;
//                fragment = new StudentViewInquiryFragment();
//                fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction()
//                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
//                        .replace(R.id.frame_container, fragment).commit();
                getActivity().onBackPressed();
            }
        });

        fragmentAddInquiryBinding.dateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichDatePickerCalled = 1;
                datePickerDialog = DatePickerDialog.newInstance(FragmentAddUpdateInquiry.this,Year,Month,Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });

        fragmentAddInquiryBinding.dobTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichDatePickerCalled = 2;
                datePickerDialog = DatePickerDialog.newInstance(FragmentAddUpdateInquiry.this,Year,Month,Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
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
        if (whichDatePickerCalled == 1) {
            fragmentAddInquiryBinding.dateTxt.setText(dateFinal);

        } else if(whichDatePickerCalled == 2) {
            fragmentAddInquiryBinding.dobTxt.setText(dateFinal);
        }
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
        return map;
    }

    public void fillTermSpinner() {

        ArrayList<Integer> TermId = new ArrayList<Integer>();
        for (int i = 0; i < finalArrayGetTermModels.size(); i++) {
            TermId.add(finalArrayGetTermModels.get(i).getTermId());
        }
        ArrayList<String> Term = new ArrayList<String>();
        for (int j = 0; j < finalArrayGetTermModels.size(); j++) {
            Term.add(finalArrayGetTermModels.get(j).getTerm());
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerTermMap = new HashMap<Integer, String>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerTermMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }

//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentAddInquiryBinding.sessionSpinner);
//
//            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentAddInquiryBinding.sessionSpinner.setAdapter(adapterTerm);
        FinalTermIdStr = spinnerTermMap.get(0);
    }


    private void callGradeApi() {

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
        return map;
    }


    public void fillGradeSpinner() {
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("--Select--");

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

        spinnerStandardMap = new HashMap<>();
        for (int i = 0; i < standardId.size(); i++) {
            spinnerStandardMap.put(i, String.valueOf(standardId.get(i)));
            spinnerstandardIdArray[i] = standardname.get(i).trim();
        }

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentAddInquiryBinding.gradeSpinner);

            popupWindow.setHeight(200);
//            popupWindow1.setHeght(200);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        fragmentAddInquiryBinding.gradeSpinner.setAdapter(adapterstandard);
        fragmentAddInquiryBinding.gradeSpinner.setSelection(1);
        FinalStandardIdStr = spinnerStandardMap.get(0);


    }

    public void fillSection() {
        ArrayList<String> sectionname = new ArrayList<>();
        ArrayList<Integer> sectionId = new ArrayList<>();
        ArrayList<String> firstSectionValue = new ArrayList<String>();
        firstSectionValue.add("--Select--");
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

        spinnerSectionMap = new HashMap<>();
        for (int i = 0; i < sectionId.size(); i++) {
            spinnerSectionMap.put(i, String.valueOf(sectionId.get(i)));
            spinnersectionIdArray[i] = sectionname.get(i).trim();
        }
//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentAddInquiryBinding.sectionSpinner);
//
//            popupWindow.setHeight(spinnersectionIdArray.length > 4 ? 500 : spinnersectionIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }
        ArrayAdapter<String> adapterstandard = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinnersectionIdArray);
        fragmentAddInquiryBinding.sectionSpinner.setAdapter(adapterstandard);

        FinalSectionStr = spinnerSectionMap.get(0);
        FinalClassIdStr = spinnerSectionMap.get(0);

    }

    private void callInsertEnquiry() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().insertInquiryData(getEnquiryParams(),new retrofit.Callback<StudentInquiryModel>() {
            @Override
            public void success(StudentInquiryModel termModel, Response response) {
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
                    Utils.ping(getActivity(),"Enquiry Added Successfully");
                    //replace fragment.
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.fromDate = Utils.getTodaysDate();
                    AppConfiguration.toDate = Utils.getTodaysDate();
                    fragment = new StudentViewInquiryFragment();
                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
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

    private Map<String, String> getEnquiryParams() {
        Map<String, String> map = new HashMap<>();
        map.put("UserID",prefUtils.getStringValue("StaffID",""));
        map.put("StudentFName",fragmentAddInquiryBinding.firstnameTxt.getText().toString());
        map.put("StudentLName",fragmentAddInquiryBinding.lastnameTxt.getText().toString());
        map.put("StudentMName",fragmentAddInquiryBinding.middleTxt.getText().toString() ==  null || fragmentAddInquiryBinding.middleTxt.getText().toString().length() <= 0 ? "":fragmentAddInquiryBinding.middleTxt.getText().toString());
        map.put("Gender",FinalGender);
        map.put("GradeID",FinalStandardIdStr);
        map.put("SectionID",FinalClassIdStr);
        map.put("TermID",FinalTermIdStr);
        map.put("Date",fragmentAddInquiryBinding.dateTxt.getText().toString());
        map.put("StudentDOB",fragmentAddInquiryBinding.dobTxt.getText().toString());
        map.put("PreviousSchool",fragmentAddInquiryBinding.previousSchoolTxt.getText().toString());
        map.put("FatherFName",fragmentAddInquiryBinding.fatherFirstnameTxt.getText().toString());
        map.put("FatherLName",fragmentAddInquiryBinding.fatherLastnameTxt.getText().toString());
        map.put("FatherMName",fragmentAddInquiryBinding.fatherMiddlenameTxt.getText().toString() == null || fragmentAddInquiryBinding.fatherMiddlenameTxt.getText().toString().length() <= 0 ? "" :fragmentAddInquiryBinding.fatherMiddlenameTxt.getText().toString());
        map.put("FatherContactMobile",fragmentAddInquiryBinding.fatherMobilenoTxt.getText().toString());
        return map;
    }

    private void clearFields(){
        fragmentAddInquiryBinding.firstnameTxt.setText("");
        fragmentAddInquiryBinding.lastnameTxt.setText("");
        fragmentAddInquiryBinding.middleTxt.setText("");
        fragmentAddInquiryBinding.dateTxt.setText("");
        fragmentAddInquiryBinding.dobTxt.setText("");
        fragmentAddInquiryBinding.previousSchoolTxt.setText("");
        fragmentAddInquiryBinding.fatherMiddlenameTxt.setText("");
        fragmentAddInquiryBinding.fatherLastnameTxt.setText("");
        fragmentAddInquiryBinding.fatherFirstnameTxt.setText("");
        fragmentAddInquiryBinding.fatherMobilenoTxt.setText("");

        fragmentAddInquiryBinding.mainScrollview.fullScroll(ScrollView.FOCUS_UP);

    }

    private boolean validateForm(){
        if(TextUtils.isEmpty(fragmentAddInquiryBinding.firstnameTxt.getText().toString())){
            Utils.ping(getActivity(),"Please enter student firstname");
            fragmentAddInquiryBinding.firstnameTxt.requestFocus();
            return false;
        }else if(TextUtils.isEmpty(fragmentAddInquiryBinding.lastnameTxt.getText().toString())){
            Utils.ping(getActivity(),"Please enter student lastname");
            fragmentAddInquiryBinding.lastnameTxt.requestFocus();
            return false;
        }else if(FinalStandardIdStr == null || TextUtils.isEmpty(FinalStandardIdStr)){
            Utils.ping(getActivity(),"Please select grade");
            fragmentAddInquiryBinding.gradeSpinner.requestFocus();
            return false;
        }else if(FinalTermIdStr == null || TextUtils.isEmpty(FinalTermIdStr)){
            Utils.ping(getActivity(),"Please select session");
            fragmentAddInquiryBinding.sessionSpinner.requestFocus();
            return false;
        }
//        else if(FinalSectionStr == null || TextUtils.isEmpty(FinalSectionStr) || FinalSectionStr.equalsIgnoreCase("0")){
//            Utils.ping(getActivity(),"Please select section");
//            fragmentAddInquiryBinding.sessionSpinner.requestFocus();
//            return false;
//        }

        else if(TextUtils.isEmpty(fragmentAddInquiryBinding.dateTxt.getText().toString())){
            Utils.ping(getActivity(),"Please select date");
            fragmentAddInquiryBinding.dateTxt.requestFocus();
            return false;
        }else if(TextUtils.isEmpty(fragmentAddInquiryBinding.dobTxt.getText().toString())){
            Utils.ping(getActivity(),"Please select date of birth");
            fragmentAddInquiryBinding.dobTxt.requestFocus();
            return false;
        }else if(TextUtils.isEmpty(fragmentAddInquiryBinding.previousSchoolTxt.getText().toString())) {
            Utils.ping(getActivity(), "Please enter student previous school");
            fragmentAddInquiryBinding.previousSchoolTxt.requestFocus();
            return false;
//        }else if(TextUtils.isEmpty(fragmentAddInquiryBinding.fatherFirstnameTxt.getText().toString())){
//            Utils.ping(getActivity(),"Please enter father firstname");
//            fragmentAddInquiryBinding.fatherFirstnameTxt.requestFocus();
//
//            return false;
//        }else if(TextUtils.isEmpty(fragmentAddInquiryBinding.fatherLastnameTxt.getText().toString())){
//            Utils.ping(getActivity(),"Please enter father lastname");
//            fragmentAddInquiryBinding.fatherLastnameTxt.requestFocus();
//            return false;
//        }
        }else if(TextUtils.isEmpty(fragmentAddInquiryBinding.fatherMobilenoTxt.getText().toString())){
            Utils.ping(getActivity(),"Please enter father mobileno");
            fragmentAddInquiryBinding.fatherMobilenoTxt.requestFocus();
            return false;
        }else if(fragmentAddInquiryBinding.fatherMobilenoTxt.getText().toString().length() < 10){
            Utils.ping(getActivity(),"Mobileno must be 10 digits");
            fragmentAddInquiryBinding.fatherMobilenoTxt.requestFocus();
            return false;
        }else if(!DateUtils.checkCurrentDateValidation(fragmentAddInquiryBinding.dateTxt.getText().toString())){
            Utils.ping(getActivity(),"Please select valid date");
            fragmentAddInquiryBinding.fatherMobilenoTxt.requestFocus();
            return false;
        }else if(!DateUtils.checkCurrentDateValidation(fragmentAddInquiryBinding.dobTxt.getText().toString())){
            Utils.ping(getActivity(),"Please select valid Birthdate");
            fragmentAddInquiryBinding.fatherMobilenoTxt.requestFocus();
            return false;
        }
        return  true;
    }


}
