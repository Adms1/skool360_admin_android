package anandniketan.com.skool360.Fragment.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.OnlinePaymentPermissionAdapter;
import anandniketan.com.skool360.Adapter.StandardAdapter;
import anandniketan.com.skool360.Interface.getEditpermission;
import anandniketan.com.skool360.Model.Account.FinalArrayStandard;
import anandniketan.com.skool360.Model.Account.GetStandardModel;
import anandniketan.com.skool360.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.skool360.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.skool360.Model.Student.StudentAttendanceModel;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentOnlinePaymentBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class OnlinePaymentFragment extends Fragment {

    //Use for fill AcademicYear spinner
    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    HashMap<Integer, String> spinnerTermMap;
    HashMap<Integer, String> spinnerTermDetailIdMap;
    //Use for fill Standard
    List<FinalArrayStandard> finalArrayStandardsList;
    StandardAdapter standardAdapter;
    //Use for fill List
    List<StudentAttendanceFinalArray> finalArrayResultPermissionList;
    OnlinePaymentPermissionAdapter onlinePaymentPermissionAdapter;
    String FinalTermIdStr, FinalGradeIsStr = "", FinalTermDetailIdStr = "", FinalStatusStr = "1";
    private FragmentOnlinePaymentBinding fragmentOnlinePaymentBinding;
    private View rootView;
    private Context mContext;
    private String status, updatestatus, deletestatus;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public OnlinePaymentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentOnlinePaymentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_online_payment, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            status = bundle.getString("onlinepayviewstatus");
            updatestatus = bundle.getString("onlinepayupdatestatus");
            deletestatus = bundle.getString("onlinepaydeletestatus");
        }

        rootView = fragmentOnlinePaymentBinding.getRoot();
        mContext = Objects.requireNonNull(getActivity()).getApplicationContext();

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view1 = view.findViewById(R.id.header);
        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.onlinepayment);

        setListners();
        callTermApi();
        callStandardApi();

    }

    public void setListners() {

        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 13;

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
                AppConfiguration.position = 13;

//                fragment = new StudentPermissionFragment();
//                fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction()
//                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
//                        .replace(R.id.frame_container, fragment).commit();
                getActivity().onBackPressed();
            }
        });

        fragmentOnlinePaymentBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentOnlinePaymentBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentOnlinePaymentBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid;
                Log.d("FinalTermIdStr", FinalTermIdStr);
                callOnlinePaymentPermission();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        fragmentOnlinePaymentBinding.termDetailSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentOnlinePaymentBinding.termDetailSpinner.getSelectedItem().toString();
                String getid = spinnerTermDetailIdMap.get(fragmentOnlinePaymentBinding.termDetailSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermDetailIdStr = getid;
                Log.d("FinalTermDetailIdStr", FinalTermDetailIdStr);
                callOnlinePaymentPermission();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentOnlinePaymentBinding.statusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = radioGroup.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    // checkedId is the RadioButton selected
                    switch (checkedId) {
                        case R.id.done_chk:
                            FinalStatusStr = fragmentOnlinePaymentBinding.doneChk.getTag().toString();
                            break;
                        case R.id.pendding_chk:
                            FinalStatusStr = fragmentOnlinePaymentBinding.penddingChk.getTag().toString();
                            break;
                    }
                }
            }
        });

        fragmentOnlinePaymentBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentOnlinePaymentBinding.searchBtn.getText().toString().equalsIgnoreCase("Update")) {

                    if (updatestatus.equalsIgnoreCase("true")) {

                        getFinalIdStr();
                        if (!FinalGradeIsStr.equalsIgnoreCase("")) {
                            callInsertOnlinePaymentPermission();
                        } else {
                            Utils.ping(mContext, "Please Select Grade.");
                        }
                    } else {
                        Utils.ping(getActivity(), "Access Denied");
                    }
                } else {
                    getFinalIdStr();
                    if (!FinalGradeIsStr.equalsIgnoreCase("")) {
                        callInsertOnlinePaymentPermission();
                    } else {
                        Utils.ping(mContext, "Please Select Grade.");
                    }
                }

            }
        });

        fragmentOnlinePaymentBinding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentOnlinePaymentBinding.searchBtn.setText(R.string.Add);

                fragmentOnlinePaymentBinding.termSpinner.setSelection(1);
                fragmentOnlinePaymentBinding.termDetailSpinner.setSelection(0);
                fragmentOnlinePaymentBinding.doneChk.setChecked(true);

                if (finalArrayStandardsList != null) {
                    for (int i = 0; i < finalArrayStandardsList.size(); i++) {
                        finalArrayStandardsList.get(i).setCheckedStatus("0");
                    }
                    standardAdapter.notifyDataSetChanged();
                }

                fragmentOnlinePaymentBinding.cancelBtn.setVisibility(View.GONE);
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
                        fillTermDetailSpinner();

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
                        for (int i = 0; i < finalArrayStandardsList.size(); i++) {
                            finalArrayStandardsList.get(i).setCheckedStatus("0");
                        }
                        standardAdapter = new StandardAdapter(mContext, finalArrayStandardsList);
                        fragmentOnlinePaymentBinding.gradeGridView.setAdapter(standardAdapter);
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

    // CALL OnlinePaymentPermission API HERE
    private void callOnlinePaymentPermission() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getOnlinePaymentPermission(getOnlinePaymentPermission(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel resultPermissionModel, Response response) {
                Utils.dismissDialog();
                if (resultPermissionModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (resultPermissionModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (resultPermissionModel.getSuccess().equalsIgnoreCase("false")) {
//                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentOnlinePaymentBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentOnlinePaymentBinding.lvHeader.setVisibility(View.GONE);
                    fragmentOnlinePaymentBinding.recyclerLinear.setVisibility(View.GONE);
                    return;
                }
                if (resultPermissionModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayResultPermissionList = resultPermissionModel.getFinalArray();
                    if (resultPermissionModel.getFinalArray().size() > 0) {
                        fragmentOnlinePaymentBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentOnlinePaymentBinding.lvHeader.setVisibility(View.VISIBLE);
                        fragmentOnlinePaymentBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        onlinePaymentPermissionAdapter = new OnlinePaymentPermissionAdapter(mContext, resultPermissionModel, new getEditpermission() {
                            @Override
                            public void getEditpermission() {
                                fragmentOnlinePaymentBinding.cancelBtn.setVisibility(View.VISIBLE);
                                UpdatePermission();
                            }
                        }, status);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        fragmentOnlinePaymentBinding.onlinePermissionList.setLayoutManager(mLayoutManager);
                        fragmentOnlinePaymentBinding.onlinePermissionList.setItemAnimator(new DefaultItemAnimator());
                        fragmentOnlinePaymentBinding.onlinePermissionList.setAdapter(onlinePaymentPermissionAdapter);
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

    private Map<String, String> getOnlinePaymentPermission() {
        Map<String, String> map = new HashMap<>();
        map.put("TermId", FinalTermIdStr);
        map.put("TermDetailId", FinalTermDetailIdStr);
        return map;
    }

    // CALL InsertOnlinePaymentPermission
    public void callInsertOnlinePaymentPermission() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().InsertOnlinePaymentPermission(getInsertOnlinePaymentPermission(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<InsertMenuPermissionModel>() {
            @Override
            public void success(InsertMenuPermissionModel permissionModel, Response response) {
                Utils.dismissDialog();
                if (permissionModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (permissionModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (permissionModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (permissionModel.getSuccess().equalsIgnoreCase("True")) {
                    Utils.ping(mContext, "Record update successful");
                    callOnlinePaymentPermission();
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

    private Map<String, String> getInsertOnlinePaymentPermission() {
        Map<String, String> map = new HashMap<>();
        map.put("TermID", FinalTermIdStr);
        map.put("TermDetailID", FinalTermDetailIdStr);
        map.put("GradeID", FinalGradeIsStr);
        map.put("Status", FinalStatusStr);
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
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentOnlinePaymentBinding.termSpinner);
//
//            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentOnlinePaymentBinding.termSpinner.setAdapter(adapterTerm);
        fragmentOnlinePaymentBinding.termSpinner.setSelection(1);
        FinalTermIdStr = spinnerTermMap.get(0);
    }

    public void fillTermDetailSpinner() {
        ArrayList<Integer> termdetailId = new ArrayList<>();
        termdetailId.add(1);
        termdetailId.add(2);


        ArrayList<String> termdetail = new ArrayList<>();
        termdetail.add("Term 1");
        termdetail.add("Term 2");


        String[] spinnertermdetailIdArray = new String[termdetailId.size()];

        spinnerTermDetailIdMap = new HashMap<Integer, String>();
        for (int i = 0; i < termdetailId.size(); i++) {
            spinnerTermDetailIdMap.put(i, String.valueOf(termdetailId.get(i)));
            spinnertermdetailIdArray[i] = termdetail.get(i).trim();
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermdetailIdArray);
        fragmentOnlinePaymentBinding.termDetailSpinner.setAdapter(adapterTerm);

        FinalTermDetailIdStr = spinnerTermDetailIdMap.get(0);
    }

    // Use for get the AllFinalValue for InsertPermission
    public void getFinalIdStr() {
        ArrayList<String> gradeArray = new ArrayList<String>();
        List<FinalArrayStandard> standardArray = standardAdapter.getDatas();
        for (int i = 0; i < standardArray.size(); i++) {
            if (standardArray.get(i).getCheckedStatus().equalsIgnoreCase("1")) {
                gradeArray.add(standardArray.get(i).getStandardID().toString());
            }
        }
        FinalGradeIsStr = String.valueOf(gradeArray);
        FinalGradeIsStr = FinalGradeIsStr.substring(1, FinalGradeIsStr.length() - 1);
        Log.d("FinalGradeIsStr", FinalGradeIsStr);
    }

    // Use For UpdatePermission
    public void UpdatePermission() {
//        List<FinalArrayStandard> standardArray1 = standardAdapter.getDatas();
//        for (int i = 0; i < standardArray1.size(); i++) {
//            standardArray1.get(i).setCheckedStatus("0");
//            standardAdapter.notifyDataSetChanged();
//
//        }


//        2018-19|K2|Inactive|Term2

        fragmentOnlinePaymentBinding.searchBtn.setText("Update");
        ArrayList<String> academicYearArray = new ArrayList<String>();
        String statusArray = "", gradeArray = "", termdetail = "";
//
//        for (int k = 0; k < onlinePaymentPermissionAdapter.getRowValue().size(); k++) {
        String rowValueStr = onlinePaymentPermissionAdapter.getRowValue();
        Log.d("rowValueStr", rowValueStr);
        String[] spiltString = rowValueStr.split("\\|");
        academicYearArray.add(spiltString[0]);
        termdetail = spiltString[3];
        gradeArray = spiltString[1];
        statusArray = spiltString[2];
//            statusArray = statusArray.substring(0, statusArray.length() - 1);

        Log.d("statusArray", statusArray);
//        }

        if (statusArray.equalsIgnoreCase(fragmentOnlinePaymentBinding
                .doneChk.getText().toString())) {
            fragmentOnlinePaymentBinding.doneChk.setChecked(true);
        } else if (statusArray.equalsIgnoreCase(fragmentOnlinePaymentBinding.penddingChk.getText().toString())) {
            fragmentOnlinePaymentBinding.penddingChk.setChecked(true);

        }
        List<FinalArrayStandard> standardArray = standardAdapter.getDatas();
        if (standardArray.size() > 0) {
            for (int i = 0; i < standardArray.size(); i++) {
                if (gradeArray.equalsIgnoreCase(standardArray.get(i).getStandard())) {
                    standardArray.get(i).setCheckedStatus("1");
                } else {
                    standardArray.get(i).setCheckedStatus("0");
                }
            }
            standardAdapter.notifyDataSetChanged();
        }

        if (termdetail.equalsIgnoreCase("term1")) {
            fragmentOnlinePaymentBinding.termDetailSpinner.setSelection(0);
        } else {
            fragmentOnlinePaymentBinding.termDetailSpinner.setSelection(1);
        }

    }
}

