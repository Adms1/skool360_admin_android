package anandniketan.com.skool360.Fragment.Fragment;

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

import java.lang.reflect.Field;
import java.util.ArrayList;
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
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentBullkSmsBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BullkSmsFragment extends Fragment {

    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    HashMap<Integer, String> spinnerTermMap;
    List<FinalArrayStandard> finalArrayStandardsList;
    HashMap<Integer, String> spinnerStandardMap;
    HashMap<Integer, String> spinnerSectionMap;
    String FinalStandardIdStr, FinalClassIdStr, StandardName, FinalTermIdStr, FinalStandardStr, FinalSectionStr;
    List<FinalArraySMSDataModel> finalArrayBulkSMSModelList;
    BulkSMSDetailListAdapter bulkSMSDetailListAdapter;
    String finalBulkIdArray, finalmessageMessageLine, finalDateStr;
    private FragmentBullkSmsBinding fragmentBullkSmsBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private boolean temp = false;
    private TextView date_txt, message_edt;
    private Button send_btn, close_btn;
    private AlertDialog alertDialogAndroid = null;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public BullkSmsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBullkSmsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bullk_sms, container, false);

        rootView = fragmentBullkSmsBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.bulksms);

        setListners();
        callTermApi();
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

        fragmentBullkSmsBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentBullkSmsBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentBullkSmsBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid;
                Log.d("FinalTermIdStr", FinalTermIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentBullkSmsBinding.gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentBullkSmsBinding.gradeSpinner.getSelectedItem().toString();
                String getid = spinnerStandardMap.get(fragmentBullkSmsBinding.gradeSpinner.getSelectedItemPosition());

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

        fragmentBullkSmsBinding.sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedsectionstr = fragmentBullkSmsBinding.sectionSpinner.getSelectedItem().toString();
                String getid = spinnerSectionMap.get(fragmentBullkSmsBinding.sectionSpinner.getSelectedItemPosition());

                Log.d("value", selectedsectionstr + " " + getid);
                FinalClassIdStr = getid;
                FinalSectionStr = selectedsectionstr;
                Log.d("FinalClassIdStr", FinalClassIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentBullkSmsBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callGetBulkSMSDataApi();
            }
        });
        fragmentBullkSmsBinding.smsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

        fragmentBullkSmsBinding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage();
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
        ApiHandler.getApiService().getTerm(getTermDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<TermModel>() {
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
    private void callGetBulkSMSDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getBulkSMSData(getGetBulkSMSDataDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<GetStaffSMSDataModel>() {
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
                        fragmentBullkSmsBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentBullkSmsBinding.bulkSmsDetailList.setVisibility(View.GONE);
                        fragmentBullkSmsBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentBullkSmsBinding.listHeader.setVisibility(View.GONE);
                        fragmentBullkSmsBinding.submitBtn.setVisibility(View.GONE);
                    }
                    return;
                }
                if (getBulkSMSDataModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayBulkSMSModelList = getBulkSMSDataModel.getFinalArray();
                    if (finalArrayBulkSMSModelList != null) {
                        fillDataList();
                        Utils.dismissDialog();
                    } else {
                        fragmentBullkSmsBinding.txtNoRecords.setVisibility(View.VISIBLE);
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
        if (FinalClassIdStr.equalsIgnoreCase("0")) {
            FinalClassIdStr = "";
        }

        Map<String, String> map = new HashMap<>();
        map.put("Term", FinalTermIdStr);
        map.put("grade", FinalStandardIdStr);
        map.put("section", FinalClassIdStr);
        return map;
    }

    //Use for fill the Term Spinner
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
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentBullkSmsBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentBullkSmsBinding.termSpinner.setAdapter(adapterTerm);
        FinalTermIdStr = spinnerTermMap.get(0);
    }

    //Use for fill the Standard Spinner
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentBullkSmsBinding.gradeSpinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 500 : spinnerstandardIdArray.length * 100);
//            popupWindow1.setHeght(200);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }


        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        fragmentBullkSmsBinding.gradeSpinner.setAdapter(adapterstandard);

        FinalStandardIdStr = spinnerStandardMap.get(0);
    }

    //Use for fill the Class Spinner
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentBullkSmsBinding.sectionSpinner);

            popupWindow.setHeight(spinnersectionIdArray.length > 4 ? 500 : spinnersectionIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnersectionIdArray);
        fragmentBullkSmsBinding.sectionSpinner.setAdapter(adapterstandard);

        FinalClassIdStr = spinnerSectionMap.get(0);
        callGetBulkSMSDataApi();
    }

    //Use for fill the SMS Detail List
    public void fillDataList() {
        fragmentBullkSmsBinding.txtNoRecords.setVisibility(View.GONE);
        fragmentBullkSmsBinding.bulkSmsDetailList.setVisibility(View.VISIBLE);
        fragmentBullkSmsBinding.recyclerLinear.setVisibility(View.VISIBLE);
        fragmentBullkSmsBinding.listHeader.setVisibility(View.VISIBLE);
        fragmentBullkSmsBinding.submitBtn.setVisibility(View.VISIBLE);

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
                    fragmentBullkSmsBinding.smsCheckbox.setChecked(true);
                } else {
                    temp = true;
                    fragmentBullkSmsBinding.smsCheckbox.setChecked(false);
                }
                if (data) {
                    fragmentBullkSmsBinding.submitBtn.setEnabled(true);
                    fragmentBullkSmsBinding.submitBtn.setAlpha(1);
                } else {
                    fragmentBullkSmsBinding.submitBtn.setEnabled(false);
                    fragmentBullkSmsBinding.submitBtn.setAlpha((float) 0.5);
                }
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        fragmentBullkSmsBinding.bulkSmsDetailList.setLayoutManager(mLayoutManager);
        fragmentBullkSmsBinding.bulkSmsDetailList.setItemAnimator(new DefaultItemAnimator());
        fragmentBullkSmsBinding.bulkSmsDetailList.setAdapter(bulkSMSDetailListAdapter);
    }

    //Use for fill the Selected student send the message
    public void SendMessage() {
        LayoutInflater lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = lInflater.inflate(R.layout.insert_message_item, null);

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

        date_txt = layout.findViewById(R.id.insert_message_date_txt);
        message_edt = layout.findViewById(R.id.insert_message_Message_txt);
        send_btn = layout.findViewById(R.id.send_message_btn);
        close_btn = layout.findViewById(R.id.close_btn);

        date_txt.setText(Utils.getTodaysDate());
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
                finalBulkIdArray = String.valueOf(id);
                finalBulkIdArray = finalBulkIdArray.substring(1, finalBulkIdArray.length() - 1);

                finalmessageMessageLine = message_edt.getText().toString();
                finalDateStr = date_txt.getText().toString();
                Log.d("finalBulkIdArray", "" + finalBulkIdArray);

                if (!Utils.checkNetwork(mContext)) {
                    Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
                    return;
                }
                if (!finalBulkIdArray.equalsIgnoreCase("") && !finalmessageMessageLine.equalsIgnoreCase("") && !finalDateStr.equalsIgnoreCase("")) {
                    Utils.showDialog(getActivity());
                    ApiHandler.getApiService().InsertBulkSMSData(InsertBulkSMSData(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<InsertMenuPermissionModel>() {
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

    private Map<String, String> InsertBulkSMSData() {
        Map<String, String> map = new HashMap<>();
        map.put("SMS", finalmessageMessageLine);
        map.put("Date", finalDateStr);
        map.put("MobileNo", finalBulkIdArray);//finalBulkIdArray  "1|8672952197"
        return map;
    }

}

