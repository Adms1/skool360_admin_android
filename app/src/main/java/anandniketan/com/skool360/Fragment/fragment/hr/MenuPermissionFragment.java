package anandniketan.com.skool360.Fragment.fragment.hr;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Interface.getEmployeeCheck;
import anandniketan.com.skool360.Model.HR.FinalArrayPageListModel;
import anandniketan.com.skool360.Model.HR.GetPageListModel;
import anandniketan.com.skool360.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.skool360.Model.Staff.FinalArrayStaffModel;
import anandniketan.com.skool360.Model.Staff.StaffAttendaceModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.activity.DashboardActivity;
import anandniketan.com.skool360.adapter.OtherPageDeatilListAdapter;
import anandniketan.com.skool360.adapter.PageDeatilListAdapter;
import anandniketan.com.skool360.databinding.FragmentMenuPermissionBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MenuPermissionFragment extends Fragment {

    List<FinalArrayStaffModel> finalArrayTeachersModelList;
    HashMap<Integer, String> spinnerTeacherMap;
    String FinalTeacherIdStr, Finalflag, FinalPageStr;
    List<FinalArrayPageListModel> finalArrayPageListModelList;
    PageDeatilListAdapter pageDeatilListAdapter;
    OtherPageDeatilListAdapter otherPageDeatilListAdapter;
    boolean check = false;
    private FragmentMenuPermissionBinding fragmentMenuPermissionBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private RadioGroup radioGroup;
    private boolean temp = false;
    private boolean temp1 = false;
    private boolean temp2 = false;
    private boolean tempAll = false;
    private boolean temp1All = false;
    private boolean temp2All = false;
    private String viewstatus, updatestatus;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public MenuPermissionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentMenuPermissionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu_permission, container, false);

        rootView = fragmentMenuPermissionBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        Finalflag = "Teacher";

        Bundle bundle = this.getArguments();
        viewstatus = bundle.getString("status");
        updatestatus = bundle.getString("updatestatus");

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inflate the layout for this fragment
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 51;

//        viewstatus = AppConfiguration.HRstaffseachviewstatus;

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.menupermission);
        setListners();
        callTeacherApi();

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
                fragment = new HRFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentMenuPermissionBinding.teacherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentMenuPermissionBinding.teacherSpinner.getSelectedItem().toString();
                String getid = spinnerTeacherMap.get(fragmentMenuPermissionBinding.teacherSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
//                FinalTeacherIdStr = getid;
                FinalTeacherIdStr = finalArrayTeachersModelList.get(position).getEmpId().toString();
                Log.d("FinalTeacherIdStr", FinalTeacherIdStr);
                callPageListApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentMenuPermissionBinding.usertypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {

                    // checkedId is the RadioButton selected
                    switch (checkedId) {
                        case R.id.teacher_radiobutton:
                            Finalflag = rb.getText().toString();
                            Log.d("FInalflag", Finalflag);
                            fragmentMenuPermissionBinding.otherAddallChk.setChecked(false);
                            fragmentMenuPermissionBinding.otherUpdateChk.setChecked(false);
                            fragmentMenuPermissionBinding.otherDeleteChk.setChecked(false);
                            callPageListApi();
                            break;

                        case R.id.other_radiobutton:
                            Finalflag = rb.getText().toString();
                            Log.d("FInalflag", Finalflag);
                            fragmentMenuPermissionBinding.addallChk.setChecked(false);
                            fragmentMenuPermissionBinding.updateChk.setChecked(false);
                            fragmentMenuPermissionBinding.deleteChk.setChecked(false);
                            callPageListApi();
                            break;
                        default:
                            break;
                    }

                }
            }
        });

        fragmentMenuPermissionBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FatchInsertPermissionData();

                if (viewstatus.equalsIgnoreCase("true")) {
                    callInsertMenuPermissionApi();
                } else {
                    Utils.ping(getActivity(), "Access Denied");
                }
            }
        });

        fragmentMenuPermissionBinding.addallChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < finalArrayPageListModelList.size(); i++) {
                        finalArrayPageListModelList.get(i).setStatus(true);
                    }
                    pageDeatilListAdapter.notifyDataSetChanged();
                    temp = false;
                } else {
                    if (!temp) {
                        for (int i = 0; i < finalArrayPageListModelList.size(); i++) {
                            finalArrayPageListModelList.get(i).setStatus(false);
                        }
                        pageDeatilListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        fragmentMenuPermissionBinding.updateChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < finalArrayPageListModelList.size(); i++) {
                        finalArrayPageListModelList.get(i).setIsUserUpdate(true);
                    }
                    pageDeatilListAdapter.notifyDataSetChanged();
                    temp1 = false;
                } else {
                    if (!temp1) {
                        for (int i = 0; i < finalArrayPageListModelList.size(); i++) {
                            finalArrayPageListModelList.get(i).setIsUserUpdate(false);
                        }
                        pageDeatilListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        fragmentMenuPermissionBinding.deleteChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < finalArrayPageListModelList.size(); i++) {
                        finalArrayPageListModelList.get(i).setIsUserDelete(true);
                    }
                    pageDeatilListAdapter.notifyDataSetChanged();
                    temp2 = false;
                } else {
                    if (!temp2) {
                        for (int i = 0; i < finalArrayPageListModelList.size(); i++) {
                            finalArrayPageListModelList.get(i).setIsUserDelete(false);
                        }
                        pageDeatilListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        fragmentMenuPermissionBinding.otherAddallChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < finalArrayPageListModelList.size(); i++) {
                        finalArrayPageListModelList.get(i).setStatus(true);
                    }
                    otherPageDeatilListAdapter.notifyDataSetChanged();
                    temp = false;
                } else {
                    if (!temp) {
                        for (int i = 0; i < finalArrayPageListModelList.size(); i++) {
                            finalArrayPageListModelList.get(i).setStatus(false);
                        }
                        otherPageDeatilListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        fragmentMenuPermissionBinding.otherUpdateChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < finalArrayPageListModelList.size(); i++) {
                        finalArrayPageListModelList.get(i).setIsUserUpdate(true);
                    }
                    otherPageDeatilListAdapter.notifyDataSetChanged();
                    temp1 = false;
                } else {
                    if (!temp1) {
                        for (int i = 0; i < finalArrayPageListModelList.size(); i++) {
                            finalArrayPageListModelList.get(i).setIsUserUpdate(false);
                        }
                        otherPageDeatilListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        fragmentMenuPermissionBinding.otherDeleteChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < finalArrayPageListModelList.size(); i++) {
                        finalArrayPageListModelList.get(i).setIsUserDelete(true);
                    }
                    otherPageDeatilListAdapter.notifyDataSetChanged();
                    temp2 = false;
                } else {
                    if (!temp2) {
                        for (int i = 0; i < finalArrayPageListModelList.size(); i++) {
                            finalArrayPageListModelList.get(i).setIsUserDelete(false);
                        }
                        otherPageDeatilListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }

    // CALL Teacher API HERE
    private void callTeacherApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTeachers(getTeacherDetail(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel teachersModel, Response response) {
                Utils.dismissDialog();
                if (teachersModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (teachersModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (teachersModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (teachersModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayTeachersModelList = teachersModel.getFinalArray();
                    if (finalArrayTeachersModelList != null) {
                        fillTeacherSpinner();
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

    private Map<String, String> getTeacherDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    // CALL PageList API HERE
    private void callPageListApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getPageList(getPageListDetail(), new retrofit.Callback<GetPageListModel>() {
            @Override
            public void success(GetPageListModel getPageListModel, Response response) {
                Utils.dismissDialog();
                if (getPageListModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getPageListModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getPageListModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    if (getPageListModel.getFinalArray().size() == 0) {
                        fragmentMenuPermissionBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentMenuPermissionBinding.pageListDetailList.setVisibility(View.GONE);
                        fragmentMenuPermissionBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentMenuPermissionBinding.listHeader.setVisibility(View.GONE);
                    }
                    return;
                }
                if (getPageListModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayPageListModelList = getPageListModel.getFinalArray();
                    if (finalArrayPageListModelList != null) {

                        fillDataList();
                        Utils.dismissDialog();
//
                    } else {
                        fragmentMenuPermissionBinding.txtNoRecords.setVisibility(View.VISIBLE);
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

    private Map<String, String> getPageListDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("EmployeeID", FinalTeacherIdStr);
        map.put("flag", Finalflag);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    // CALL InsertMenuPermission API HERE
    private void callInsertMenuPermissionApi() {
        FatchInsertPermissionData();
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().InsertMenuPermission(getInsertMenuPermissionDetail(), new retrofit.Callback<InsertMenuPermissionModel>() {
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
                    if (insertMenuPermissionModel.getFinalArray().size() == 0) {
                        Utils.ping(mContext, getString(R.string.false_msg));
                    }
                    return;
                }
                if (insertMenuPermissionModel.getSuccess().equalsIgnoreCase("True")) {
                    Utils.ping(mContext, "Information Successfully saved..!");
                    callPageListApi();
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

    private Map<String, String> getInsertMenuPermissionDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Pk_EmployeID", FinalTeacherIdStr);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        map.put("Pages", FinalPageStr);//FinalPageStr
        return map;
    }

    //Use for fill TeacherNameSpinner
    public void fillTeacherSpinner() {
        ArrayList<Integer> TeacherId = new ArrayList<Integer>();
        for (int i = 0; i < finalArrayTeachersModelList.size(); i++) {
            TeacherId.add(finalArrayTeachersModelList.get(i).getEmpCode());
        }
        ArrayList<String> Teacher = new ArrayList<String>();
        for (int j = 0; j < finalArrayTeachersModelList.size(); j++) {
            Teacher.add(finalArrayTeachersModelList.get(j).getEmpName());
        }

        String[] spinnerteacherIdArray = new String[TeacherId.size()];

        spinnerTeacherMap = new HashMap<Integer, String>();
        for (int i = 0; i < TeacherId.size(); i++) {
            spinnerTeacherMap.put(i, String.valueOf(TeacherId.get(i)));
            spinnerteacherIdArray[i] = Teacher.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentMenuPermissionBinding.teacherSpinner);

            popupWindow.setHeight(spinnerteacherIdArray.length > 4 ? 500 : spinnerteacherIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerteacherIdArray);
        fragmentMenuPermissionBinding.teacherSpinner.setAdapter(adapterTerm);
        FinalTeacherIdStr = spinnerTeacherMap.get(0);
        callPageListApi();
    }

    //Use for fillPermissionList
    public void fillDataList() {
        fragmentMenuPermissionBinding.txtNoRecords.setVisibility(View.GONE);
        fragmentMenuPermissionBinding.pageListDetailList.setVisibility(View.VISIBLE);
        fragmentMenuPermissionBinding.recyclerLinear.setVisibility(View.VISIBLE);
        fragmentMenuPermissionBinding.listHeader.setVisibility(View.VISIBLE);

        if (Finalflag.equalsIgnoreCase("Teacher")) {
            fragmentMenuPermissionBinding.pageListDetailList.setVisibility(View.VISIBLE);
            fragmentMenuPermissionBinding.recyclerLinear.setVisibility(View.VISIBLE);
            fragmentMenuPermissionBinding.listHeader.setVisibility(View.VISIBLE);

            fragmentMenuPermissionBinding.pageListDetailList1.setVisibility(View.GONE);
            fragmentMenuPermissionBinding.listHeader1.setVisibility(View.GONE);
            fragmentMenuPermissionBinding.recyclerLinear1.setVisibility(View.GONE);


            pageDeatilListAdapter = new PageDeatilListAdapter(mContext, finalArrayPageListModelList, new getEmployeeCheck() {
                @Override
                public void getEmployeeSMSCheck() {
                    List<FinalArrayPageListModel> updatedData = pageDeatilListAdapter.getDatas();
                    Boolean data = false;
                    int count = 0, count1 = 0, count2 = 0;

                    for (int i = 0; i < updatedData.size(); i++) {
                        if (updatedData.get(i).getStatus().equals(true)) {
                            data = true;
                            count++;
                        } else {
                            count--;
                        }
                        if (updatedData.get(i).getIsUserUpdate().equals(true)) {
                            data = true;
                            count1++;
                        } else {
                            count1--;
                        }
                        if (updatedData.get(i).getIsUserDelete().equals(true)) {
                            data = true;
                            count2++;
                        } else {
                            count2--;
                        }
                    }

                    if (count == updatedData.size()) {
                        fragmentMenuPermissionBinding.addallChk.setChecked(true);
                    } else {
                        temp = true;
                        fragmentMenuPermissionBinding.addallChk.setChecked(false);
                    }
                    if (count1 == updatedData.size()) {
                        fragmentMenuPermissionBinding.updateChk.setChecked(true);
                    } else {
                        temp1 = true;
                        fragmentMenuPermissionBinding.updateChk.setChecked(false);
                    }
                    if (count2 == updatedData.size()) {
                        fragmentMenuPermissionBinding.deleteChk.setChecked(true);
                    } else {
                        temp2 = true;
                        fragmentMenuPermissionBinding.deleteChk.setChecked(false);
                    }
                    if (data) {
                        fragmentMenuPermissionBinding.saveBtn.setEnabled(true);
                        fragmentMenuPermissionBinding.saveBtn.setAlpha(1);
                    } else {
                        fragmentMenuPermissionBinding.saveBtn.setEnabled(false);
                        fragmentMenuPermissionBinding.saveBtn.setAlpha((float) 0.5);
                    }
                }
            });
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            fragmentMenuPermissionBinding.pageListDetailList.setLayoutManager(mLayoutManager);
            fragmentMenuPermissionBinding.pageListDetailList.setItemAnimator(new DefaultItemAnimator());
            fragmentMenuPermissionBinding.pageListDetailList.setAdapter(pageDeatilListAdapter);

            ArrayList<String> list = new ArrayList<>();
            ArrayList<String> list1 = new ArrayList<>();
            ArrayList<String> list2 = new ArrayList<>();

            int checkAll = 0, checkAll1 = 0, checkAll2 = 0;
            for (int i = 0; i < finalArrayPageListModelList.size(); i++) {
                if (finalArrayPageListModelList.get(i).getStatus().equals(true)) {
                    list.add(finalArrayPageListModelList.get(i).getStatus().toString());
                    checkAll++;
                } else {
                    checkAll--;
                }
                if (finalArrayPageListModelList.get(i).getIsUserUpdate().equals(true)) {
                    list1.add(finalArrayPageListModelList.get(i).getIsUserUpdate().toString());
                    checkAll1++;
                } else {
                    checkAll1--;
                }
                if (finalArrayPageListModelList.get(i).getIsUserDelete().equals(true)) {
                    list2.add(finalArrayPageListModelList.get(i).getIsUserDelete().toString());
                    checkAll2++;
                } else {
                    checkAll2--;
                }
            }
            if (checkAll == list.size()) {
                if (list.size() > 0 && checkAll > 0) {
                    check = true;
                    fragmentMenuPermissionBinding.addallChk.setChecked(true);
                } else {
                    fragmentMenuPermissionBinding.addallChk.setChecked(false);
                }
            }
            if (checkAll1 == list1.size()) {
                if (list1.size() > 0 && checkAll1 > 0) {
                    check = true;
                    fragmentMenuPermissionBinding.updateChk.setChecked(true);
                } else {
                    fragmentMenuPermissionBinding.updateChk.setChecked(false);
                }
            }
            if (checkAll2 == list2.size()) {
                if (list2.size() > 0 && checkAll2 > 0) {
                    check = true;
                    fragmentMenuPermissionBinding.deleteChk.setChecked(true);
                } else {
                    fragmentMenuPermissionBinding.deleteChk.setChecked(false);
                }
            }


        } else if (Finalflag.equalsIgnoreCase("Other")) {
            fragmentMenuPermissionBinding.pageListDetailList.setVisibility(View.GONE);
            fragmentMenuPermissionBinding.recyclerLinear.setVisibility(View.GONE);
            fragmentMenuPermissionBinding.listHeader.setVisibility(View.GONE);

            fragmentMenuPermissionBinding.pageListDetailList1.setVisibility(View.VISIBLE);
            fragmentMenuPermissionBinding.listHeader1.setVisibility(View.VISIBLE);
            fragmentMenuPermissionBinding.recyclerLinear1.setVisibility(View.VISIBLE);


            otherPageDeatilListAdapter = new OtherPageDeatilListAdapter(mContext, finalArrayPageListModelList, new getEmployeeCheck() {
                @Override
                public void getEmployeeSMSCheck() {
                    List<FinalArrayPageListModel> updatedData = otherPageDeatilListAdapter.getDatas();
                    Boolean data = false;
                    int count = 0, count1 = 0, count2 = 0;

                    for (int i = 0; i < updatedData.size(); i++) {
                        if (updatedData.get(i).getStatus().equals(true)) {
                            data = true;
                            count++;
                        } else {
                            count--;
                        }
                        if (updatedData.get(i).getIsUserUpdate().equals(true)) {
                            data = true;
                            count1++;
                        } else {
                            count1--;
                        }
                        if (updatedData.get(i).getIsUserDelete().equals(true)) {
                            data = true;
                            count2++;
                        } else {
                            count2--;
                        }
                    }

                    if (count == updatedData.size()) {
                        fragmentMenuPermissionBinding.otherAddallChk.setChecked(true);
                    } else {
                        temp = true;
                        fragmentMenuPermissionBinding.otherAddallChk.setChecked(false);
                    }
                    if (count1 == updatedData.size()) {
                        fragmentMenuPermissionBinding.otherUpdateChk.setChecked(true);
                    } else {
                        temp1 = true;
                        fragmentMenuPermissionBinding.otherUpdateChk.setChecked(false);
                    }
                    if (count2 == updatedData.size()) {
                        fragmentMenuPermissionBinding.otherDeleteChk.setChecked(true);
                    } else {
                        temp2 = true;
                        fragmentMenuPermissionBinding.otherDeleteChk.setChecked(false);
                    }
                    if (data) {
                        fragmentMenuPermissionBinding.saveBtn.setEnabled(true);
                        fragmentMenuPermissionBinding.saveBtn.setAlpha(1);
                    } else {
                        fragmentMenuPermissionBinding.saveBtn.setEnabled(false);
                        fragmentMenuPermissionBinding.saveBtn.setAlpha((float) 0.5);
                    }
                }
            });
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            fragmentMenuPermissionBinding.pageListDetailList1.setLayoutManager(mLayoutManager);
            fragmentMenuPermissionBinding.pageListDetailList1.setItemAnimator(new DefaultItemAnimator());
            fragmentMenuPermissionBinding.pageListDetailList1.setAdapter(otherPageDeatilListAdapter);
            ArrayList<String> list = new ArrayList<>();
            ArrayList<String> list1 = new ArrayList<>();
            ArrayList<String> list2 = new ArrayList<>();

            int checkAll = 0, checkAll1 = 0, checkAll2 = 0;
            for (int i = 0; i < finalArrayPageListModelList.size(); i++) {
                if (finalArrayPageListModelList.get(i).getStatus().equals(true)) {
                    list.add(finalArrayPageListModelList.get(i).getStatus().toString());
                    checkAll++;
                } else {
                    checkAll--;
                }
                if (finalArrayPageListModelList.get(i).getIsUserUpdate().equals(true)) {
                    list1.add(finalArrayPageListModelList.get(i).getIsUserUpdate().toString());
                    checkAll1++;
                } else {
                    checkAll1--;
                }
                if (finalArrayPageListModelList.get(i).getIsUserDelete().equals(true)) {
                    list2.add(finalArrayPageListModelList.get(i).getIsUserDelete().toString());
                    checkAll2++;
                } else {
                    checkAll2--;
                }
            }
            if (checkAll == list.size()) {
                if (list.size() > 0 && checkAll > 0) {
                    check = true;
                    fragmentMenuPermissionBinding.otherAddallChk.setChecked(true);
                } else {
                    fragmentMenuPermissionBinding.otherAddallChk.setChecked(false);
                }
            }
            if (checkAll1 == list1.size()) {
                if (list1.size() > 0 && checkAll1 > 0) {
                    check = true;
                    fragmentMenuPermissionBinding.otherUpdateChk.setChecked(true);
                } else {
                    fragmentMenuPermissionBinding.otherUpdateChk.setChecked(false);
                }
            }
            if (checkAll2 == list2.size()) {
                if (list2.size() > 0 && checkAll2 > 0) {
                    check = true;
                    fragmentMenuPermissionBinding.otherDeleteChk.setChecked(true);
                } else {
                    fragmentMenuPermissionBinding.otherDeleteChk.setChecked(false);
                }
            }


        }
    }

    //Use for FetchInsertPermissionData
    public void FatchInsertPermissionData() {
        if (Finalflag.equalsIgnoreCase("Teacher")) {
            ArrayList<String> id = new ArrayList<>();
            List<FinalArrayPageListModel> array = pageDeatilListAdapter.getDatas();
            int j;
            for (j = 0; j < array.size(); j++) {
                if (array.get(j).getStatus().equals(true) || array.get(j).getIsUserUpdate().equals(true) || array.get(j).getIsUserDelete().equals(true)) {
                    id.add(array.get(j).getPKPageID() + "|" + array.get(j).getPageURL() + "|" + array.get(j).getStatus()
                            + "|" + array.get(j).getIsUserUpdate() + "|" + array.get(j).getIsUserDelete());
                    Log.d("Statuscheckid", "" + id.size());
                } else {
                    id.remove(array.get(j).getPKPageID() + "|" + array.get(j).getPageURL() + "|" + array.get(j).getStatus()
                            + "|" + array.get(j).getIsUserUpdate() + "|" + array.get(j).getIsUserDelete());
                    Log.d("StatusUncheckid", "" + id.size());
                }
            }
            Log.d("id", "" + id);
            FinalPageStr = String.valueOf(id);
            FinalPageStr = FinalPageStr.substring(1, FinalPageStr.length() - 1);
        } else if (Finalflag.equalsIgnoreCase("Other")) {
            ArrayList<String> id = new ArrayList<>();
            List<FinalArrayPageListModel> array = otherPageDeatilListAdapter.getDatas();
            int j;
            for (j = 0; j < array.size(); j++) {
                if (array.get(j).getStatus().equals(true) || array.get(j).getIsUserUpdate().equals(true) || array.get(j).getIsUserDelete().equals(true)) {
                    id.add(array.get(j).getPKPageID() + "|" + array.get(j).getPageURL() + "|" + array.get(j).getStatus()
                            + "|" + array.get(j).getIsUserUpdate() + "|" + array.get(j).getIsUserDelete());
                    Log.d("Statuscheckid", "" + id.size());
                } else {
                    id.remove(array.get(j).getPKPageID() + "|" + array.get(j).getPageURL() + "|" + array.get(j).getStatus()
                            + "|" + array.get(j).getIsUserUpdate() + "|" + array.get(j).getIsUserDelete());
                    Log.d("StatusUncheckid", "" + id.size());
                }
            }
            Log.d("id", "" + id);
            FinalPageStr = String.valueOf(id);
            FinalPageStr = FinalPageStr.substring(1, FinalPageStr.length() - 1);
        }

    }
}

