package anandniketan.com.skool360.Fragment.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.SMSStudentMarksAdapter;
import anandniketan.com.skool360.Interface.getEmployeeCheck;
import anandniketan.com.skool360.Model.Account.FinalArrayStandard;
import anandniketan.com.skool360.Model.Account.GetStandardModel;
import anandniketan.com.skool360.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.skool360.Model.Other.FinalArraySMSDataModel;
import anandniketan.com.skool360.Model.Other.GetStaffSMSDataModel;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiClient;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.Utility.WebServices;
import anandniketan.com.skool360.databinding.FragmentSmsstudentMarksBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class SMSStudentMarksFragment extends Fragment {

    String finalBulkIdArray, finalmessageMessageLine, finalDateStr;
    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    HashMap<Integer, String> spinnerStandardMap, spinnerTermMap;
    HashMap<String, String> spinnerSubjectMap;
    List<FinalArrayStandard> finalArrayStandardsList;
    List<FinalArraySMSDataModel> finalArraySMSDataModelList, finalGetMarks;
    String FinalStandardIdStr, FinalTermIdStr, FinalStandardStr, StandardName, FinalSubjectStr, SubjectName, FinalSubjectIdStr;
    private FragmentSmsstudentMarksBinding fragmentSmsstudentMarksBinding;
    private View rootView;
    private Context mContext;
    private FragmentManager fragmentManager = null;
    private Fragment fragment = null;
    private AlertDialog alertDialogAndroid = null;
    private TextView date_txt, message_edt;
    private Button send_btn, close_btn;
    private SMSStudentMarksAdapter smsStudentMarksAdapter;
    private boolean temp = false;
    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public SMSStudentMarksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSmsstudentMarksBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_smsstudent_marks, container, false);

        rootView = fragmentSmsstudentMarksBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText("Student Marks");

        setListners();
        callTermApi();
        callStandardApi();
    }

    private void setListners() {
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

        fragmentSmsstudentMarksBinding.smsMarksTermSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentSmsstudentMarksBinding.smsMarksTermSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentSmsstudentMarksBinding.smsMarksTermSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid;
                Log.d("FinalTermIdStr", FinalTermIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentSmsstudentMarksBinding.smsMarksGradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentSmsstudentMarksBinding.smsMarksGradeSpinner.getSelectedItem().toString();
                String getid = spinnerStandardMap.get(fragmentSmsstudentMarksBinding.smsMarksGradeSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalStandardIdStr = getid;
                Log.d("FinalStandardIdStr", FinalStandardIdStr);
                StandardName = name;
                FinalStandardStr = name;
                Log.d("StandardName", StandardName);

                callSubjectApi();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentSmsstudentMarksBinding.smsMarksSubjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentSmsstudentMarksBinding.smsMarksSubjectSpinner.getSelectedItem().toString();
                String getid = finalArraySMSDataModelList.get(position).getSubjectid();

                Log.d("value", name + " " + getid);
                FinalSubjectIdStr = getid;
                Log.d("FinalStandardIdStr", FinalSubjectIdStr);
                SubjectName = name;
                FinalSubjectStr = name;
                Log.d("StandardName", SubjectName);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentSmsstudentMarksBinding.smsMarksSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callViewMarks();
            }
        });

        fragmentSmsstudentMarksBinding.smsMarksSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSendSMSViewMarks();
            }
        });

        fragmentSmsstudentMarksBinding.smsMarksSmsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < finalGetMarks.size(); i++) {
                        finalGetMarks.get(i).setCheck("1");
                    }
                    smsStudentMarksAdapter.notifyDataSetChanged();
                    temp = false;
                } else {
                    if (!temp) {
                        for (int i = 0; i < finalGetMarks.size(); i++) {
                            finalGetMarks.get(i).setCheck("0");
                        }
                        smsStudentMarksAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        fragmentSmsstudentMarksBinding.smsMarksSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage();
            }
        });
    }

    //     CALL Term API HERE
    private void callTermApi() {

        if (!Utils.checkNetwork(Objects.requireNonNull(getActivity()))) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());

        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        Call<TermModel> call = apiService.getTerm();
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
                    finalArrayGetTermModels = response.body().getFinalArray();
                    if (finalArrayGetTermModels != null) {
                        fillTermSpinner();

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

    @SuppressLint("UseSparseArrays")
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

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_layout, spinnertermIdArray);
        fragmentSmsstudentMarksBinding.smsMarksTermSpinner.setAdapter(adapterTerm);
        fragmentSmsstudentMarksBinding.smsMarksTermSpinner.setSelection(1);

    }

    // CALL Standard API HERE
    private void callStandardApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStandardSectionCombine(getStandardDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<GetStandardModel>() {
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

    //Use for fill the Standard Spinner
    public void fillGradeSpinner() {
//        ArrayList<String> firstValue = new ArrayList<>();
//        firstValue.add("All");
//
//        ArrayList<String> standardname = new ArrayList<>();
        ArrayList<String> standardname = new ArrayList<>();
//        for (int z = 0; z < firstValue.size(); z++) {
//            standardname.add(firstValue.get(z));
        for (int i = 0; i < finalArrayStandardsList.size(); i++) {
            standardname.add(finalArrayStandardsList.get(i).getStandardClass());
        }
//        }
//        ArrayList<Integer> firstValueId = new ArrayList<>();
//        firstValueId.add(0);
        ArrayList<Integer> standardId = new ArrayList<>();
//        for (int m = 0; m < firstValueId.size(); m++) {
//            standardId.add(firstValueId.get(m));
        for (int j = 0; j < finalArrayStandardsList.size(); j++) {
            standardId.add(finalArrayStandardsList.get(j).getClassID());
        }
//        }
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentSmsstudentMarksBinding.smsMarksGradeSpinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 500 : spinnerstandardIdArray.length * 100);
//            popupWindow1.setHeght(200);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<String> adapterstandard = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        fragmentSmsstudentMarksBinding.smsMarksGradeSpinner.setAdapter(adapterstandard);

        FinalStandardIdStr = spinnerStandardMap.get(0);
    }

    private void callSubjectApi() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTestForMarks(getTestformarks(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<GetStaffSMSDataModel>() {
            @Override
            public void success(GetStaffSMSDataModel standardModel, Response response) {
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
                    ArrayList<String> newarr = new ArrayList<>();
                    ArrayAdapter<String> adapterstandard = new ArrayAdapter<>(mContext, R.layout.spinner_layout, newarr);
                    fragmentSmsstudentMarksBinding.smsMarksSubjectSpinner.setAdapter(adapterstandard);
                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArraySMSDataModelList = standardModel.getFinalArray();
                    if (finalArraySMSDataModelList != null) {
                        fillSubjectSpinner();
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

    private Map<String, String> getTestformarks() {
        Map<String, String> map = new HashMap<>();

        map.put("TermID", FinalTermIdStr);
        map.put("StaffID", "0");
        map.put("ClassID", FinalStandardIdStr);

        return map;
    }

    //Use for fill the Standard Spinner
    public void fillSubjectSpinner() {
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("All");
//
//        ArrayList<String> standardname = new ArrayList<>();
        ArrayList<String> subjectname = new ArrayList<>();
        for (int z = 0; z < firstValue.size(); z++) {
            subjectname.add(firstValue.get(z));
            for (int i = 0; i < finalArraySMSDataModelList.size(); i++) {
                subjectname.add(finalArraySMSDataModelList.get(i).getSubject());
            }
        }
        ArrayList<String> firstValueId = new ArrayList<>();
        firstValueId.add("0");
        ArrayList<String> subjectId = new ArrayList<>();
        for (int m = 0; m < firstValueId.size(); m++) {
            subjectId.add(firstValueId.get(m));
            for (int j = 0; j < finalArraySMSDataModelList.size(); j++) {
                subjectId.add(finalArraySMSDataModelList.get(j).getSubjectid());
            }
        }
        String[] spinnersubjectIdArray = new String[subjectId.size()];

        spinnerSubjectMap = new HashMap<>();
        for (int i = 0; i < subjectId.size(); i++) {
            spinnerSubjectMap.put(subjectId.get(i), String.valueOf(subjectname.get(i)));
            spinnersubjectIdArray[i] = subjectname.get(i).trim();
        }

        ArrayAdapter<String> adapterstandard = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinnersubjectIdArray);
        fragmentSmsstudentMarksBinding.smsMarksSubjectSpinner.setAdapter(adapterstandard);

        FinalSubjectStr = spinnerSubjectMap.get(0);
    }

    //Use for fill the Selected student send the message
    public void SendMessage() {
        ArrayList<String> id = new ArrayList<>();
        List<FinalArraySMSDataModel> array = smsStudentMarksAdapter.getDatas();
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
        finalBulkIdArray = String.valueOf(id);
        finalBulkIdArray = finalBulkIdArray.substring(1, finalBulkIdArray.length() - 1);

        Log.d("finalBulkIdArray", "" + finalBulkIdArray);
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }
        if (!finalBulkIdArray.equalsIgnoreCase("")) {
            Utils.showDialog(getActivity());
            ApiHandler.getApiService().SendAppSMS(InsertAppSMSData(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<InsertMenuPermissionModel>() {
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
                        fragmentSmsstudentMarksBinding.smsMarksSmsCheckbox.setChecked(false);
                        callViewMarks();
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

    private Map<String, String> InsertAppSMSData() {
        Map<String, String> map = new HashMap<>();
        map.put("TermID", FinalTermIdStr);
        map.put("StuIDMobile", finalBulkIdArray);
        return map;
    }


    // CALL GetBulkSMSData API HERE
    private void callViewMarks() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getMarks(getGetBulkSMSDataDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<GetStaffSMSDataModel>() {
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
                        fragmentSmsstudentMarksBinding.smsMarksTxtNoRecords.setVisibility(View.VISIBLE);
                        fragmentSmsstudentMarksBinding.smsMarksSmsDetailList.setVisibility(View.GONE);
                        fragmentSmsstudentMarksBinding.smsMarksRecyclerLinear.setVisibility(View.GONE);
                        fragmentSmsstudentMarksBinding.smsMarksListHeader.setVisibility(View.GONE);
                        fragmentSmsstudentMarksBinding.smsMarksSubmitBtn.setVisibility(View.GONE);
                    }
                    return;
                }
                if (getBulkSMSDataModel.getSuccess().equalsIgnoreCase("True")) {
                    finalGetMarks = getBulkSMSDataModel.getFinalArray();
                    if (finalGetMarks != null) {
                        fillDataList();
                        Utils.dismissDialog();
                    } else {
                        fragmentSmsstudentMarksBinding.smsMarksTxtNoRecords.setVisibility(View.VISIBLE);
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

    private Map<String, String> getGetBulkSMSDataDetail() {
        if (FinalStandardIdStr.equalsIgnoreCase("0")) {
            FinalStandardIdStr = "";
        }

        Map<String, String> map = new HashMap<>();
        map.put("TermID", FinalTermIdStr);
        map.put("ClassID", FinalStandardIdStr);
//        map.put("StuIDMobile", finalBulkIdArray);
        map.put("SubjectID", FinalSubjectIdStr);
//        map.put("SubjectName", FinalSubjectStr);
        return map;
    }

    //Use for fill the SMS Detail List
    public void fillDataList() {
        fragmentSmsstudentMarksBinding.smsMarksTxtNoRecords.setVisibility(View.GONE);
        fragmentSmsstudentMarksBinding.smsMarksSmsDetailList.setVisibility(View.VISIBLE);
        fragmentSmsstudentMarksBinding.smsMarksRecyclerLinear.setVisibility(View.VISIBLE);
        fragmentSmsstudentMarksBinding.smsMarksListHeader.setVisibility(View.VISIBLE);
        fragmentSmsstudentMarksBinding.smsMarksSubmitBtn.setVisibility(View.VISIBLE);

        for (int k = 0; k < finalGetMarks.size(); k++) {
            finalGetMarks.get(k).setCheck("0");
        }

        smsStudentMarksAdapter = new SMSStudentMarksAdapter(mContext, finalGetMarks, new getEmployeeCheck() {
            @Override
            public void getEmployeeSMSCheck() {
                List<FinalArraySMSDataModel> updatedData = smsStudentMarksAdapter.getDatas();
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
                    fragmentSmsstudentMarksBinding.smsMarksSmsCheckbox.setChecked(true);
                } else {
                    temp = true;
                    fragmentSmsstudentMarksBinding.smsMarksSmsCheckbox.setChecked(false);
                }
                if (data) {
                    fragmentSmsstudentMarksBinding.smsMarksSubmitBtn.setEnabled(true);
                    fragmentSmsstudentMarksBinding.smsMarksSubmitBtn.setAlpha(1);
                } else {
                    fragmentSmsstudentMarksBinding.smsMarksSubmitBtn.setEnabled(false);
                    fragmentSmsstudentMarksBinding.smsMarksSubmitBtn.setAlpha((float) 0.5);
                }
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        fragmentSmsstudentMarksBinding.smsMarksSmsDetailList.setLayoutManager(mLayoutManager);
        fragmentSmsstudentMarksBinding.smsMarksSmsDetailList.setItemAnimator(new DefaultItemAnimator());
        fragmentSmsstudentMarksBinding.smsMarksSmsDetailList.setAdapter(smsStudentMarksAdapter);
    }

    private void callSendSMSViewMarks() {

    }

}
