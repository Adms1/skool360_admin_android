package anandniketan.com.skool360.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import anandniketan.com.skool360.Adapter.ClassTeacherDetailListAdapter;
import anandniketan.com.skool360.Adapter.SectionAdapter;
import anandniketan.com.skool360.Interface.OnEditRecordWithPosition;
import anandniketan.com.skool360.Interface.getEditpermission;
import anandniketan.com.skool360.Interface.onDeleteButton;
import anandniketan.com.skool360.Model.Account.FinalArrayStandard;
import anandniketan.com.skool360.Model.Account.GetStandardModel;
import anandniketan.com.skool360.Model.Account.SectionDetailModel;
import anandniketan.com.skool360.Model.Staff.FinalArrayStaffModel;
import anandniketan.com.skool360.Model.Staff.StaffAttendaceModel;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentClassTeacherBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

// change tearcher spinner add permission condition bt Antra 23/01/2019

public class ClassTeacherFragment extends Fragment {

    List<FinalArrayStandard> finalArrayStandardsList;
    HashMap<Integer, String> spinnerStandardMap;
    List<FinalArrayStaffModel> finalArrayTeachersModelList;
    HashMap<Integer, String> spinnerTeacherMap;
    HashMap<Integer, String> spinnerTermMap;
    List<FinalArrayStaffModel> finalArrayClassTeacherDetailModelList;
    List<FinalArrayStaffModel> finalArrayInsertClassTeachersModelList;
    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    String finalStandardIdStr, finalTeacherIdStr, finalClassIdStr = "1", standardName, finalTermIdStr, finalClassTeacherIdStr = "0", editClassteacherStr = "", editGradeStr = "", editSectionStr = "";
    ClassTeacherDetailListAdapter classTeacherDetailListAdapter;
    String getEditValuearray;
    String[] spinnerteacherIdArray;
    String[] spinnerstandardIdArray;
    List<SectionDetailModel> classname;
    private FragmentClassTeacherBinding fragmentClassTeacherBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private RadioGroup radioGroup;
    private String viewstatus, updatestatus, deletestatus;
    private TextView tvHeader;
    private RecyclerView rvList;
    private Button btnBack, btnMenu, btnCancel;
    private Boolean isUpdate = false;

    private SectionAdapter sectionAdapter;

    public ClassTeacherFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentClassTeacherBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_class_teacher, container, false);

        rootView = fragmentClassTeacherBinding.getRoot();
        mContext = Objects.requireNonNull(getActivity()).getApplicationContext();

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
        rvList = view.findViewById(R.id.radio_list);
        btnCancel = view.findViewById(R.id.classteacher_cancel_btn);

        tvHeader.setText(R.string.classTeacher);

        callTermApi();
        setListners();

    }

    public void setListners() {

        rvList.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
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
        fragmentClassTeacherBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentClassTeacherBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentClassTeacherBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                finalTermIdStr = getid;
                Log.d("FinalTermIdStr", finalTermIdStr);

                if (viewstatus.equalsIgnoreCase("true")) {
                    fragmentClassTeacherBinding.listHeader.setVisibility(View.VISIBLE);
                    callTeacherApi(getid);
                    callClassTeacherApi();
                } else {
                    fragmentClassTeacherBinding.listHeader.setVisibility(View.GONE);
                    Utils.ping(getActivity(), "Access Denied");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentClassTeacherBinding.gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentClassTeacherBinding.gradeSpinner.getSelectedItem().toString();
                String getid = spinnerStandardMap.get(fragmentClassTeacherBinding.gradeSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                finalStandardIdStr = getid;
                Log.d("FinalStandardIdStr", finalStandardIdStr);
                standardName = name;
                Log.d("StandardName", standardName);

                if (!isUpdate) {
                    for (int i = 0; i < finalArrayStandardsList.size(); i++) {
                        classname = new ArrayList<>();
                        classname.addAll(finalArrayStandardsList.get(i).getSectionDetail());

                        if (finalArrayStandardsList.get(i).getSectionDetail().size() == 1) {
                            finalArrayStandardsList.get(i).getSectionDetail().get(0).setCheckstatus("1");
                        }

                        if (standardName.equalsIgnoreCase(finalArrayStandardsList.get(i).getStandard())) {

                            fillSection1(classname, name);
                            break;
                        }
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        fragmentClassTeacherBinding.gradeSpinner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isUpdate = false;
//            }
//        });
//
        fragmentClassTeacherBinding.teacherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentClassTeacherBinding.teacherSpinner.getSelectedItem().toString();
                String getid = spinnerTeacherMap.get(fragmentClassTeacherBinding.teacherSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                finalTeacherIdStr = getid;
                Log.d("FinalTeacherIdStr", finalTeacherIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentClassTeacherBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updatestatus.equalsIgnoreCase("true")) {
                    callInsertClassTeacherApi();
                } else {
                    Utils.ping(getActivity(), "Access Denied");
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentClassTeacherBinding.termSpinner.setSelection(1);
                fragmentClassTeacherBinding.gradeSpinner.setSelection(0);
                fragmentClassTeacherBinding.teacherSpinner.setSelection(0);

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
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayGetTermModels = termModel.getFinalArray();
                    if (finalArrayGetTermModels != null) {
                        fillTermSpinner();
                        callStandardApi();
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

    // CALL Standard API HERE
    private void callStandardApi() {

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

                    finalArrayTeachersModelList = teachersModel.getFinalArray();
                    fillTeacherSpinner();

//                    Utils.ping(mContext, getString(R.string.false_msg));
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

    // CALL ClassTeacher API HERE
    private void callClassTeacherApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getClassTeacherDetail(getClassTeacherDetail(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel getClassTeacherDetailModel, Response response) {
                Utils.dismissDialog();
                if (getClassTeacherDetailModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getClassTeacherDetailModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getClassTeacherDetailModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    if (getClassTeacherDetailModel.getFinalArray().size() == 0) {
                        fragmentClassTeacherBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentClassTeacherBinding.classTeacherDetailList.setVisibility(View.GONE);
                        fragmentClassTeacherBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentClassTeacherBinding.listHeader.setVisibility(View.GONE);
                    }
                    return;
                }
                if (getClassTeacherDetailModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayClassTeacherDetailModelList = getClassTeacherDetailModel.getFinalArray();
                    if (finalArrayClassTeacherDetailModelList != null) {
                        fillDataList();
                        Utils.dismissDialog();
                    } else {
                        fragmentClassTeacherBinding.txtNoRecords.setVisibility(View.VISIBLE);
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

    private Map<String, String> getClassTeacherDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("TermID", finalTermIdStr);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    // CALL InsertClassTeacher API HERE
    private void callInsertClassTeacherApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().InsertClassTeachers(getInsertClassTeacherDetail(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel insertClassTeachersModel, Response response) {
                Utils.dismissDialog();
                if (insertClassTeachersModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertClassTeachersModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertClassTeachersModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, "Already Assigned Class Teacher.");

                    return;
                }
                if (insertClassTeachersModel.getSuccess().equalsIgnoreCase("True")) {
//                    Utils.ping(mContext, "Record Inserted Successfully...!!");
                    Utils.ping(mContext, insertClassTeachersModel.getYear());

                    fragmentClassTeacherBinding.termSpinner.setSelection(1);
                    fragmentClassTeacherBinding.gradeSpinner.setSelection(0);
                    fragmentClassTeacherBinding.teacherSpinner.setSelection(0);

                    btnCancel.setVisibility(View.GONE);

                    finalArrayInsertClassTeachersModelList = insertClassTeachersModel.getFinalArray();
                    if (finalArrayInsertClassTeachersModelList != null) {

                        if (viewstatus.equalsIgnoreCase("true")) {

                            fragmentClassTeacherBinding.listHeader.setVisibility(View.VISIBLE);
                            callClassTeacherApi();
                            Utils.dismissDialog();
                        } else {
                            fragmentClassTeacherBinding.listHeader.setVisibility(View.GONE);
                            Utils.ping(getActivity(), "Access Denied");
                        }
                    } else {
                        fragmentClassTeacherBinding.txtNoRecords.setVisibility(View.VISIBLE);
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

    private Map<String, String> getInsertClassTeacherDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("StandardID", finalStandardIdStr);
        map.put("ClassId", finalClassIdStr);
        map.put("Pk_EmployeID", finalTeacherIdStr);
        map.put("TermID", finalTermIdStr);
        map.put("Pk_ClsTeacherID", finalClassTeacherIdStr);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    //Use for fill the TeachetList with subject
    public void fillDataList() {
        fragmentClassTeacherBinding.txtNoRecords.setVisibility(View.GONE);
        fragmentClassTeacherBinding.classTeacherDetailList.setVisibility(View.VISIBLE);
        fragmentClassTeacherBinding.recyclerLinear.setVisibility(View.VISIBLE);
        fragmentClassTeacherBinding.listHeader.setVisibility(View.VISIBLE);

        classTeacherDetailListAdapter = new ClassTeacherDetailListAdapter(mContext, finalArrayClassTeacherDetailModelList, new onDeleteButton() {
            @Override
            public void deleteSentMessage() {
                finalClassTeacherIdStr = String.valueOf(classTeacherDetailListAdapter.getId());
                finalClassTeacherIdStr = finalClassTeacherIdStr.substring(1, finalClassTeacherIdStr.length() - 1);

                if (!finalClassTeacherIdStr.equalsIgnoreCase("")) {
                    callDeleteClassTeacherApi();
                }

            }
        }, new getEditpermission() {
            @Override
            public void getEditpermission() {
                getEditValuearray = classTeacherDetailListAdapter.getEditId();
                updateTeacher();


            }
        }, deletestatus, updatestatus);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        fragmentClassTeacherBinding.classTeacherDetailList.setLayoutManager(mLayoutManager);
        fragmentClassTeacherBinding.classTeacherDetailList.setItemAnimator(new DefaultItemAnimator());
        fragmentClassTeacherBinding.classTeacherDetailList.setAdapter(classTeacherDetailListAdapter);
    }

    //Use for fill the Standard Spinner
    public void fillGradeSpinner() {
        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        ArrayList<Integer> StandardId = new ArrayList<>();
        for (int m = 0; m < firstValueId.size(); m++) {
            StandardId.add(firstValueId.get(m));
            for (int i = 0; i < finalArrayStandardsList.size(); i++) {
                StandardId.add(finalArrayStandardsList.get(i).getStandardID());
            }
        }
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("--Select--");
        ArrayList<String> Standard = new ArrayList<>();
        for (int z = 0; z < firstValue.size(); z++) {
            Standard.add(firstValue.get(z));

            for (int j = 0; j < finalArrayStandardsList.size(); j++) {
                Standard.add(finalArrayStandardsList.get(j).getStandard());
            }
        }

        spinnerstandardIdArray = new String[StandardId.size()];

        spinnerStandardMap = new HashMap<>();
        for (int i = 0; i < StandardId.size(); i++) {
            spinnerStandardMap.put(i, String.valueOf(StandardId.get(i)));
            spinnerstandardIdArray[i] = Standard.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentClassTeacherBinding.gradeSpinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 500 : spinnerstandardIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        fragmentClassTeacherBinding.gradeSpinner.setAdapter(adapterTerm);

        fragmentClassTeacherBinding.gradeSpinner.setSelection(1);

    }

    //Use for fill the TeacherName Spinner
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentClassTeacherBinding.teacherSpinner);

            popupWindow.setHeight(spinnerteacherIdArray.length > 4 ? 500 : spinnerteacherIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinnerteacherIdArray);
        fragmentClassTeacherBinding.teacherSpinner.setAdapter(adapterTerm);

        fragmentClassTeacherBinding.teacherSpinner.setSelection(0);

    }

    public void fillSection1(final List<SectionDetailModel> classname1, String standardName) {

        if (!standardName.equalsIgnoreCase("--Select--")) {
//            for (int i = 0; i < finalArrayStandardsList.size(); i++) {
//                classname = new ArrayList<>();
//                classname.addAll(finalArrayStandardsList.get(i).getSectionDetail());
////                finalArrayStandardsList.get(i).getSectionDetail().get(0).setCheckstatus("1");
//
//                if (standardName.equalsIgnoreCase(finalArrayStandardsList.get(i).getStandard())) {

            sectionAdapter = new SectionAdapter(getActivity(), new OnEditRecordWithPosition() {
                @Override
                public void getEditpermission(int pos) {
                    finalClassIdStr = String.valueOf(classname1.get(pos).getSectionID());
                }
            }, classname1);
            rvList.setAdapter(sectionAdapter);
//                    break;
//                }
//            }
        }
    }

//    //Use for fill the Class Spinner
//    public void fillSection() {
//        ArrayList<String> classname = new ArrayList<>();
//
//        if (!standardName.equalsIgnoreCase("--Select--")) {
//            for (int i = 0; i < finalArrayStandardsList.size(); i++) {
//                if (standardName.equalsIgnoreCase(finalArrayStandardsList.get(i).getStandard())) {
//                    for (int j = 0; j < finalArrayStandardsList.get(i).getSectionDetail().size(); j++) {
//                        classname.add(finalArrayStandardsList.get(i).getSectionDetail().get(j).getSection() + "|"
//                                + String.valueOf(finalArrayStandardsList.get(i).getSectionDetail().get(j).getSectionID()));
//                    }
//                }
//            }
//        }
//
//        if (fragmentClassTeacherBinding.sectionLinear.getChildCount() > 0) {
//            fragmentClassTeacherBinding.sectionLinear.removeAllViews();
//        }
//        try {
//            for (int i = 0; i < 1; i++) {
//                @SuppressLint("InflateParams") View convertView = LayoutInflater.from(mContext).inflate(R.layout.list_checkbox, null);
//                radioGroup = convertView.findViewById(R.id.radiogroup);
//
//                for (int k = 0; k < classname.size(); k++) {
//                    RadioButton radioButton = new RadioButton(mContext);
//                    ColorStateList colorStateList = new ColorStateList(
//                            new int[][]{
//                                    new int[]{-android.R.attr.state_checked},
//                                    new int[]{android.R.attr.state_checked}
//                            },
//                            new int[]{
//
//                                    Color.rgb(23,145,216)
//                                    , Color.rgb(23,145,216),
//                            }
//                    );
//                    radioButton.setButtonTintList(colorStateList);
////                    radioButton.setButtonDrawable(R.drawable.check_uncheck);
////                    radioButton.setPadding(10, 0, 10, 10);
//                    radioButton.setTextColor(getResources().getColor(R.color.black));
//
//                    String[] splitValue = classname.get(k).split("\\|");
//                    radioButton.setText(splitValue[0]);
//                    radioButton.setTag(splitValue[1]);
//                    if (editSectionStr.equalsIgnoreCase(radioButton.getText().toString())) {
//                        radioButton.setChecked(true);
//                    }
//                    else {
//                        radioButton.setChecked(false);
//                    }
//                    radioGroup.addView(radioButton);
//                }
//                radioGroup.setOnCheckedChangeListener(mCheckedListner);
//
//
//                fragmentClassTeacherBinding.sectionLinear.addView(convertView);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentClassTeacherBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentClassTeacherBinding.termSpinner.setAdapter(adapterTerm);
        fragmentClassTeacherBinding.termSpinner.setSelection(1);
        finalTermIdStr = spinnerTermMap.get(0);
    }

    // CALL DeleteClassTeacher API HERE
    private void callDeleteClassTeacherApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().DeleteClassTeacher(getDeleteClassTeacherDetail(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel insertClassTeachersModel, Response response) {
                Utils.dismissDialog();
                if (insertClassTeachersModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertClassTeachersModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertClassTeachersModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));

                    return;
                }
                if (insertClassTeachersModel.getSuccess().equalsIgnoreCase("True")) {
                    Utils.ping(mContext, "Record Deleted Successfully...!!");
                    finalArrayInsertClassTeachersModelList = insertClassTeachersModel.getFinalArray();
                    if (finalArrayInsertClassTeachersModelList != null) {
                        if (viewstatus.equalsIgnoreCase("true")) {
                            fragmentClassTeacherBinding.listHeader.setVisibility(View.VISIBLE);
                            callClassTeacherApi();
                            Utils.dismissDialog();
                        } else {
                            fragmentClassTeacherBinding.listHeader.setVisibility(View.GONE);
                            Utils.ping(getActivity(), "Access Denied");
                        }
                    } else {
                        fragmentClassTeacherBinding.txtNoRecords.setVisibility(View.VISIBLE);
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

    private Map<String, String> getDeleteClassTeacherDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Pk_ClsTeacherID", finalClassTeacherIdStr);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    public void updateTeacher() {

        isUpdate = true;
        btnCancel.setVisibility(View.VISIBLE);

//        for (int i = 0; i < getEditValuearray.size(); i++) {
        String[] spiltValue = getEditValuearray.split("\\|");
        finalClassTeacherIdStr = spiltValue[0];
        editClassteacherStr = spiltValue[1];
        editGradeStr = spiltValue[2];
        editSectionStr = spiltValue[3];
//        }

        if (!editClassteacherStr.equalsIgnoreCase("")) {
            for (int i = 0; i < spinnerteacherIdArray.length; i++) {
                if (spinnerteacherIdArray[i].trim().equalsIgnoreCase(editClassteacherStr.trim())) {
                    fragmentClassTeacherBinding.teacherSpinner.setSelection(i);
                }
            }
        }

        if (!editGradeStr.equalsIgnoreCase("")) {
            for (int i = 0; i < spinnerstandardIdArray.length; i++) {
                if (spinnerstandardIdArray[i].trim().equalsIgnoreCase(editGradeStr.trim())) {
                    fragmentClassTeacherBinding.gradeSpinner.setSelection(i);
                }
            }
        }

        if (!editSectionStr.equalsIgnoreCase("")) {

//            classname = new ArrayList<>();

//            classname = finalArrayStandardsList.get()

            for (int i = 0; i < finalArrayStandardsList.size(); i++) {

                classname = new ArrayList<>();

                if (editGradeStr.trim().equalsIgnoreCase(finalArrayStandardsList.get(i).getStandard().trim())) {
                    classname = finalArrayStandardsList.get(i).getSectionDetail();

                    for (int j = 0; j < classname.size(); j++) {

                        if (editSectionStr.trim().equalsIgnoreCase(classname.get(j).getSection().trim())) {

                            classname.get(j).setCheckstatus("1");

                        } else {
                            classname.get(j).setCheckstatus("0");

                        }
                    }

                    break;

                }
            }

            if (!editSectionStr.equalsIgnoreCase("--select--")) {
                sectionAdapter = new SectionAdapter(getActivity(), new OnEditRecordWithPosition() {
                    @Override
                    public void getEditpermission(int pos) {
                        finalClassIdStr = String.valueOf(classname.get(pos).getSectionID());
                    }
                }, classname);
                rvList.setAdapter(sectionAdapter);
                sectionAdapter.notifyDataSetChanged();
            }

            isUpdate = false;
//            fillSection1(classname, editSectionStr);
        }
    }
}

