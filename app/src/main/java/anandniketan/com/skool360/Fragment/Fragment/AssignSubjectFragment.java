package anandniketan.com.skool360.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.AssignSubjectDetailListAdapter;
import anandniketan.com.skool360.Interface.getEditpermission;
import anandniketan.com.skool360.Interface.onDeleteButton;
import anandniketan.com.skool360.Model.Staff.FinalArrayStaffModel;
import anandniketan.com.skool360.Model.Staff.StaffAttendaceModel;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentAssignSubjectBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AssignSubjectFragment extends Fragment {

    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    HashMap<Integer, String> spinnerTermMap;
    List<FinalArrayStaffModel> finalArrayTeachersModelList;
    HashMap<Integer, String> spinnerTeacherMap;
    List<FinalArrayStaffModel> finalArraySubjectModelList;
    HashMap<Integer, String> spinnerSubjectMap;
    List<FinalArrayStaffModel> finalArrayAssignSubjectModelList;
    List<FinalArrayStaffModel> finalArrayInsertAssignSubjectModelList;
    String FinalTermIdStr, FinalTeacherIdStr, FinalSubjectIdStr;
    String[] spinnerteacherIdArray;
    AssignSubjectDetailListAdapter assignSubjectDetailListAdapter;
    private FragmentAssignSubjectBinding fragmentAssignSubjectBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private String getEditValuearray;
    private String[] spinnersubjectIdArray;
    private RadioButton rbActive, rbInactive;
    private String viewstatus, updatestatus, deletestatus, assignID = "0", statusstr = "1", finalStatusStr = "";

    private String editClassteacherStr, editGradeStr;

    private TextView tvHeader;
    private Button btnBack, btnMenu, btnCancel;

    public AssignSubjectFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentAssignSubjectBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_assign_subject, container, false);

        rootView = fragmentAssignSubjectBinding.getRoot();
        mContext = Objects.requireNonNull(getActivity()).getApplicationContext();

        rbActive = rootView.findViewById(R.id.radio_active);
        rbInactive = rootView.findViewById(R.id.radio_inactive);
        btnCancel = rootView.findViewById(R.id.cancel_btn);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            viewstatus = bundle.getString("viewstatus");
            updatestatus = bundle.getString("updatestatus");
            deletestatus = bundle.getString("deletestatus");

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

        tvHeader.setText(R.string.assignsubject);

        setListners();
        callTermApi();
        callSubjectApi();

    }

    public void setListners() {

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        fragmentAssignSubjectBinding.statusLinear.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_active)
                    statusstr = "1";
                else if (checkedId == R.id.radio_inactive)
                    statusstr = "0";
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StaffFragment();
                fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                }
            }
        });

        fragmentAssignSubjectBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentAssignSubjectBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentAssignSubjectBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid;
                Log.d("FinalTermIdStr", FinalTermIdStr);

                if (viewstatus.equalsIgnoreCase("true")) {
                    callAssignSubjectApi();
                    callTeacherApi(getid);
                } else {
                    Utils.ping(getActivity(), "Access Denied");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentAssignSubjectBinding.teacherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentAssignSubjectBinding.teacherSpinner.getSelectedItem().toString();
                String getid = spinnerTeacherMap.get(fragmentAssignSubjectBinding.teacherSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTeacherIdStr = getid;
                Log.d("FinalTeacherIdStr", FinalTeacherIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentAssignSubjectBinding.subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentAssignSubjectBinding.subjectSpinner.getSelectedItem().toString();
                String getid = spinnerSubjectMap.get(fragmentAssignSubjectBinding.subjectSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalSubjectIdStr = getid;
                Log.d("FinalSubjectIdStr", FinalSubjectIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentAssignSubjectBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!fragmentAssignSubjectBinding.teacherSpinner.getSelectedItem().toString().equalsIgnoreCase("--select--")) {

                    if (updatestatus.equalsIgnoreCase("true")) {
                        callInsertAssignSubjectApi();
                    } else {
                        Utils.ping(getActivity(), "Access Denied");
                    }
                } else {
                    Utils.ping(getContext(), "Please Select teacher");
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentAssignSubjectBinding.saveBtn.setText(R.string.save);
                fragmentAssignSubjectBinding.termSpinner.setSelection(1);
                fragmentAssignSubjectBinding.teacherSpinner.setSelection(0);
                fragmentAssignSubjectBinding.subjectSpinner.setSelection(0);
                fragmentAssignSubjectBinding.radioActive.setChecked(true);
                btnCancel.setVisibility(View.GONE);

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
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
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
        HashMap<String, String> map = new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    // CALL Teacher API HERE
    private void callTeacherApi(String id) {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTeachersbyTerm(getTeacherDetail(id), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel teachersModel, Response response) {
                Utils.dismissDialog();
                if (teachersModel == null) {
//                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (teachersModel.getSuccess() == null) {
//                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (teachersModel.getSuccess().equalsIgnoreCase("false")) {
                    finalArrayTeachersModelList.clear();
                    ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinnerteacherIdArray);
                    fragmentAssignSubjectBinding.teacherSpinner.setAdapter(adapterTerm);

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
//                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });
    }

    private Map<String, String> getTeacherDetail(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("TermID", id);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    // CALL Subject API HERE
    private void callSubjectApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getSubject(getSubjectDetail(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel subjectModel, Response response) {
                Utils.dismissDialog();
                if (subjectModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (subjectModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (subjectModel.getSuccess().equalsIgnoreCase("false")) {
//                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (subjectModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArraySubjectModelList = subjectModel.getFinalArray();
                    if (finalArraySubjectModelList != null) {
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

    private Map<String, String> getSubjectDetail() {
        HashMap<String, String> map= new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    // CALL AssignSubject API HERE
    private void callAssignSubjectApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getSubjectAssgin(getAssignSubjectDetail(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel getSubjectAssginModel, Response response) {
                Utils.dismissDialog();
                if (getSubjectAssginModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getSubjectAssginModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getSubjectAssginModel.getSuccess().equalsIgnoreCase("false")) {
//                    Utils.ping(mContext, getString(R.string.false_msg));
                    if (getSubjectAssginModel.getFinalArray().size() == 0) {
                        fragmentAssignSubjectBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentAssignSubjectBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentAssignSubjectBinding.lvExpHeader.setVisibility(View.GONE);
                    }
                    return;
                }
                if (getSubjectAssginModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayAssignSubjectModelList = getSubjectAssginModel.getFinalArray();
                    if (finalArrayAssignSubjectModelList != null) {
                        fragmentAssignSubjectBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentAssignSubjectBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        fragmentAssignSubjectBinding.lvExpHeader.setVisibility(View.VISIBLE);
                        fillDataList();
                        Utils.dismissDialog();
                    } else {
                        fragmentAssignSubjectBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentAssignSubjectBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentAssignSubjectBinding.lvExpHeader.setVisibility(View.GONE);
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

    private Map<String, String> getAssignSubjectDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Term", FinalTermIdStr);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    // CALL InsertAssignSubject API HERE
    private void callInsertAssignSubjectApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().InsertAssignSubject(getInsertAssignSubjectDetail(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel insertAssignSubjectModel, Response response) {
                Utils.dismissDialog();
                if (insertAssignSubjectModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertAssignSubjectModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertAssignSubjectModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, insertAssignSubjectModel.getYear());
                    return;
                }
                if (insertAssignSubjectModel.getSuccess().equalsIgnoreCase("True")) {

                    fragmentAssignSubjectBinding.subjectSpinner.setSelection(0);
                    fragmentAssignSubjectBinding.teacherSpinner.setSelection(0);
                    rbActive.setChecked(false);
                    rbInactive.setChecked(false);
                    btnCancel.setVisibility(View.GONE);

                    finalArrayInsertAssignSubjectModelList = insertAssignSubjectModel.getFinalArray();
                    if (finalArrayInsertAssignSubjectModelList != null) {

                        if (viewstatus.equalsIgnoreCase("true")) {

                            callAssignSubjectApi();
                        } else {
                            Utils.ping(getActivity(), "Access Denied");
                        }
                        Utils.dismissDialog();
                    } else {
                        fragmentAssignSubjectBinding.txtNoRecords.setVisibility(View.VISIBLE);
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

    private Map<String, String> getInsertAssignSubjectDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Term", FinalTermIdStr);
        map.put("SubjectID", FinalSubjectIdStr);
        map.put("Pk_EmployeID", FinalTeacherIdStr);
        map.put("Status", statusstr);
        map.put("Pk_AssignID", assignID);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    //Use for fill the Term Spinner
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
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentAssignSubjectBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentAssignSubjectBinding.termSpinner.setAdapter(adapterTerm);
        fragmentAssignSubjectBinding.termSpinner.setSelection(1);
        FinalTermIdStr = spinnerTermMap.get(0);
        callAssignSubjectApi();
    }

    //Use for fill the Teacher Name Spinner
    public void fillTeacherSpinner() {
        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        ArrayList<Integer> TeacherId = new ArrayList<>();
        for (int m = 0; m < firstValueId.size(); m++) {
            TeacherId.add(firstValueId.get(m));
            for (int i = 0; i < finalArrayTeachersModelList.size(); i++) {
                TeacherId.add(finalArrayTeachersModelList.get(i).getEmpId());
            }
        }

        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("--Select--");

        ArrayList<String> Teacher = new ArrayList<>();
        for (int z = 0; z < firstValue.size(); z++) {
            Teacher.add(firstValue.get(z));
            for (int j = 0; j < finalArrayTeachersModelList.size(); j++) {
                Teacher.add(finalArrayTeachersModelList.get(j).getEmpName());
            }
        }
        spinnerteacherIdArray = new String[TeacherId.size()];

        spinnerTeacherMap = new HashMap<>();
        for (int i = 0; i < TeacherId.size(); i++) {
            spinnerTeacherMap.put(i, String.valueOf(TeacherId.get(i)));
            spinnerteacherIdArray[i] = Teacher.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentAssignSubjectBinding.teacherSpinner);

            popupWindow.setHeight(spinnerteacherIdArray.length > 4 ? 500 : spinnerteacherIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinnerteacherIdArray);
        fragmentAssignSubjectBinding.teacherSpinner.setAdapter(adapterTerm);
        fragmentAssignSubjectBinding.teacherSpinner.setSelection(0);
    }

    //Use for fill the Subject Spinner
    public void fillSubjectSpinner() {
        ArrayList<Integer> SubjectId = new ArrayList<>();
        for (int i = 0; i < finalArraySubjectModelList.size(); i++) {
            SubjectId.add(finalArraySubjectModelList.get(i).getPkSubjectID());
        }
        ArrayList<String> Subject = new ArrayList<>();
        for (int j = 0; j < finalArraySubjectModelList.size(); j++) {
            Subject.add(finalArraySubjectModelList.get(j).getSubject());
        }

        spinnersubjectIdArray = new String[SubjectId.size()];

        spinnerSubjectMap = new HashMap<>();
        for (int i = 0; i < SubjectId.size(); i++) {
            spinnerSubjectMap.put(i, String.valueOf(SubjectId.get(i)));
            spinnersubjectIdArray[i] = Subject.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentAssignSubjectBinding.subjectSpinner);

            popupWindow.setHeight(spinnersubjectIdArray.length > 4 ? 500 : spinnersubjectIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinnersubjectIdArray);
        fragmentAssignSubjectBinding.subjectSpinner.setAdapter(adapterTerm);

    }

    //Use for fill the Subject with Teacher data
    public void fillDataList() {
        fragmentAssignSubjectBinding.txtNoRecords.setVisibility(View.GONE);
        fragmentAssignSubjectBinding.assignSubjectDetailList.setVisibility(View.VISIBLE);
        fragmentAssignSubjectBinding.recyclerLinear.setVisibility(View.VISIBLE);
        fragmentAssignSubjectBinding.lvExpHeader.setVisibility(View.VISIBLE);

        assignSubjectDetailListAdapter = new AssignSubjectDetailListAdapter(mContext, finalArrayAssignSubjectModelList, new onDeleteButton() {
            @Override
            public void deleteSentMessage() {
                assignID = String.valueOf(assignSubjectDetailListAdapter.getId());
//                finalClassTeacherIdStr = finalClassTeacherIdStr.substring(1, finalClassTeacherIdStr.length() - 1);

                if (!assignID.equalsIgnoreCase("")) {
                    callDeleteAssinSubjectApi(assignID);
                }

            }
        }, new getEditpermission() {
            @Override
            public void getEditpermission() {
                getEditValuearray = assignSubjectDetailListAdapter.getEditId();
                updateTeacher();

            }
        }, updatestatus, deletestatus);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        fragmentAssignSubjectBinding.assignSubjectDetailList.setLayoutManager(mLayoutManager);
        fragmentAssignSubjectBinding.assignSubjectDetailList.setItemAnimator(new DefaultItemAnimator());
        fragmentAssignSubjectBinding.assignSubjectDetailList.setAdapter(assignSubjectDetailListAdapter);
    }

    public void updateTeacher() {

        btnCancel.setVisibility(View.VISIBLE);

//        for (int i = 0; i < getEditValuearray.size(); i++) {
        String[] spiltValue = getEditValuearray.split("\\|");
        statusstr = spiltValue[0];
        assignID = spiltValue[1];
        editClassteacherStr = spiltValue[2];
        editGradeStr = spiltValue[3];
//        }

        if (statusstr.equalsIgnoreCase("active")) {
            finalStatusStr = "1";
            rbActive.setChecked(true);
        } else {
            finalStatusStr = "0";
            rbInactive.setChecked(true);
        }

        if (!editClassteacherStr.equalsIgnoreCase("")) {
            for (int i = 0; i < spinnerteacherIdArray.length; i++) {
                if (spinnerteacherIdArray[i].trim().equalsIgnoreCase(editClassteacherStr.trim())) {
                    fragmentAssignSubjectBinding.teacherSpinner.setSelection(i);
                }
            }
        }
        if (!editGradeStr.equalsIgnoreCase("")) {
            for (int i = 0; i < spinnersubjectIdArray.length; i++) {
                if (spinnersubjectIdArray[i].trim().equalsIgnoreCase(editGradeStr.trim())) {
                    fragmentAssignSubjectBinding.subjectSpinner.setSelection(i);
                }
            }
        }
    }

    private void callDeleteAssinSubjectApi(String did) {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().DeleteAssignSubject(getDeleteAssignSubjectDetail(did), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel insertAssignSubjectModel, Response response) {
                Utils.dismissDialog();
                if (insertAssignSubjectModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertAssignSubjectModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertAssignSubjectModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (insertAssignSubjectModel.getSuccess().equalsIgnoreCase("True")) {

                    fragmentAssignSubjectBinding.subjectSpinner.setSelection(0);
                    fragmentAssignSubjectBinding.teacherSpinner.setSelection(0);
                    rbActive.setChecked(false);
                    rbInactive.setChecked(false);

                    finalArrayInsertAssignSubjectModelList = insertAssignSubjectModel.getFinalArray();
                    if (finalArrayInsertAssignSubjectModelList != null) {

                        if (viewstatus.equalsIgnoreCase("true")) {

                            callAssignSubjectApi();
                        } else {
                            Utils.ping(getActivity(), "Access Denied");
                        }
                        Utils.dismissDialog();
                    } else {
                        fragmentAssignSubjectBinding.txtNoRecords.setVisibility(View.VISIBLE);
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

    private Map<String, String> getDeleteAssignSubjectDetail(String did) {
        Map<String, String> map = new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        map.put("Pk_AssignID", did);
        return map;
    }

}

