package anandniketan.com.skool360.Fragment.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.MarkSyllabusExamListAdapter;
import anandniketan.com.skool360.Adapter.MarkSyllabusPermissionAdapter;
import anandniketan.com.skool360.Interface.OnEditRecordWithPosition;
import anandniketan.com.skool360.Model.Account.FinalArrayStandard;
import anandniketan.com.skool360.Model.Account.GetStandardModel;
import anandniketan.com.skool360.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.skool360.Model.Student.MarkSyllabusModel;
import anandniketan.com.skool360.Model.Student.TestModel;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MarkSyllabusPermission extends Fragment {

    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    //Use for fill TermSpinner

    private List<FinalArrayGetTermModel> finalArrayGetTermModels;

    private HashMap<Integer, String> spinnerStandardMap;

    private HashMap<Integer, String> spinnerTermMap;
    private HashMap<Integer, String> spinnerTermDetailIdMap;
    //Use for fill List
    private List<MarkSyllabusModel.FinalArray> finalArrayResultPermissionList;
    private MarkSyllabusPermissionAdapter resultPermissionAdapter;

    //Use for fill section
    private List<FinalArrayStandard> finalArrayStandardsList;
    private List<TestModel.FinalArray> finalArrayTestList;

    private MarkSyllabusExamListAdapter standardAdapter;
    private String FinalTermIdStr, FinalGradeIsStr = "", FinalStatusStr = "1", FinalTermDetailIdStr;
    private String markCheckText = "", syllabusCheckText = "";
    private Spinner sp_term, sp_grade;
    private AppCompatCheckBox markCheckBox, mSyllabusCheckBox;
    private Button mBtnAddUpdate, btnCancel;
    private LinearLayout mLayoutHeader;
    private TextView txNoRecords;
    private RecyclerView rvListData, rvMainListData;
    private RadioButton rbActive, rbInActive;
    private TextView fLEmptyView;
    private boolean isExamAdded = false;
    private String PID = "", testName = "";
    private boolean isRefreshRequired = true;
    private String status, updatestatus, deletestatus;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public MarkSyllabusPermission() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_mark_syallbus, container, false);
        mContext = getActivity().getApplicationContext();

        Bundle bundle = this.getArguments();
        status = bundle.getString("markviewstatus");
        updatestatus = bundle.getString("markupdatestatus");
        deletestatus = bundle.getString("markdeletestatus");

        sp_grade = rootView.findViewById(R.id.sp_grade);
        sp_term = rootView.findViewById(R.id.term_spinner);
        mBtnAddUpdate = rootView.findViewById(R.id.add_btn);
        markCheckBox = rootView.findViewById(R.id.cb_marks);
        mSyllabusCheckBox = rootView.findViewById(R.id.cb_syllabus);
        mLayoutHeader = rootView.findViewById(R.id.lv_header);
        txNoRecords = rootView.findViewById(R.id.txtNoRecords);
        rvListData = rootView.findViewById(R.id.rv_exams);
        rvListData.setHasFixedSize(true);
        rbActive = rootView.findViewById(R.id.done_chk);
        rbInActive = rootView.findViewById(R.id.pendding_chk);
        fLEmptyView = rootView.findViewById(R.id.tv_empty_exam_view);
        rvMainListData = rootView.findViewById(R.id.rv_marks_syallabus_list);
        btnCancel = rootView.findViewById(R.id.cancel_btn);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view1 = view.findViewById(R.id.header);
        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.marks_syllabus_label);

        setListners();
        callTermApi();
        callGradeApi();

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

        sp_term.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = sp_term.getSelectedItem().toString();
                String getid = spinnerTermMap.get(sp_term.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid;
                Log.d("FinalTermIdStr", FinalTermIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mBtnAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getFinalIdStr();

                if (mBtnAddUpdate.getText().toString().equalsIgnoreCase("update")) {
                    if (updatestatus.equalsIgnoreCase("true")) {
                        if ((markCheckText != null && !TextUtils.isEmpty(markCheckText)) || (syllabusCheckText != null && !TextUtils.isEmpty(syllabusCheckText))) {

                            if (!FinalGradeIsStr.equalsIgnoreCase("")) {
                                if (standardAdapter != null) {
                                    if (standardAdapter.getCheckedItems() != null) {
                                        if (standardAdapter.getCheckedItems().size() > 0) {
                                            callInsertResultPermission();
                                        } else {
                                            Utils.ping(mContext, "Please select any one Test/Exam");
                                        }
                                    } else {
                                        Utils.ping(mContext, "Please select any one Test/Exam");
                                    }

                                } else {
                                    Utils.ping(mContext, "Please select any one Test/Exam");
                                }
                            } else {
                                Utils.ping(mContext, "Please select grade.");
                            }

                        } else {
                            Utils.ping(mContext, "Please select any one Type.");

                        }
                    } else {
                        Utils.ping(getActivity(), "Access Denied");
                    }
                } else {
                    if ((markCheckText != null && !TextUtils.isEmpty(markCheckText)) || (syllabusCheckText != null && !TextUtils.isEmpty(syllabusCheckText))) {

                        if (!FinalGradeIsStr.equalsIgnoreCase("")) {
                            if (standardAdapter != null) {
                                if (standardAdapter.getCheckedItems() != null) {
                                    if (standardAdapter.getCheckedItems().size() > 0) {
                                        callInsertResultPermission();
                                    } else {
                                        Utils.ping(mContext, "Please select any one Test/Exam");
                                    }
                                } else {
                                    Utils.ping(mContext, "Please select any one Test/Exam");
                                }

                            } else {
                                Utils.ping(mContext, "Please select any one Test/Exam");
                            }
                        } else {
                            Utils.ping(mContext, "Please select grade.");
                        }

                    } else {
                        Utils.ping(mContext, "Please select any one Type.");

                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnAddUpdate.setText(R.string.Add);

                sp_term.setSelection(1);
                markCheckBox.setChecked(true);
                mSyllabusCheckBox.setChecked(false);
                sp_grade.setSelection(0);
                rbActive.setChecked(true);

                if (finalArrayTestList != null) {
                    for (int i = 0; i < finalArrayTestList.size(); i++) {
                        finalArrayTestList.get(i).setCheckedStatus("0");
                    }
                    standardAdapter.notifyDataSetChanged();
                }

                btnCancel.setVisibility(View.GONE);
            }
        });

        markCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                    @Override
                                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                        if (isChecked) {
                                                            markCheckText = "R";
                                                        }
                                                    }
                                                }
        );

        mSyllabusCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                         @Override
                                                         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                             if (isChecked) {
                                                                 syllabusCheckText = "S";
                                                             }
                                                         }

                                                     }
        );
        rbActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    FinalStatusStr = "1";
                }
            }
        });

        rbInActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    FinalStatusStr = "0";
                }
            }
        });


        sp_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = sp_grade.getSelectedItem().toString();
                String getid = String.valueOf(spinnerStandardMap.get(sp_grade.getSelectedItemPosition()));

                Log.d("value", name + " " + getid);
                FinalGradeIsStr = getid;

                callTestNameForMarksSyllabusPermissionApi(FinalTermIdStr, FinalGradeIsStr);

                if (isRefreshRequired) {
                    callResultPermission();
                }


                //  Log.d("FinalTermDetailIdStr", FinalTermDetailIdStr);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
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
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(sp_term);
//
//            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        sp_term.setAdapter(adapterTerm);
    }


    // CALL Standard API HERE
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
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
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

        spinnerStandardMap = new HashMap<Integer, String>();
        for (int i = 0; i < standardId.size(); i++) {
            spinnerStandardMap.put(i, String.valueOf(standardId.get(i)));
            spinnerstandardIdArray[i] = standardname.get(i).trim();
        }

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(sp_grade);

            popupWindow.setHeight(400);
//            popupWindow1.setHeght(200);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        sp_grade.setAdapter(adapterstandard);
        sp_grade.setSelection(1);
        FinalGradeIsStr = spinnerStandardMap.get(0);


    }


    private void callResultPermission() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }
        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getMarksSyllabusPermission(getResultPermission(), new retrofit.Callback<MarkSyllabusModel>() {
            @Override
            public void success(MarkSyllabusModel resultPermissionModel, Response response) {
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
                    txNoRecords.setVisibility(View.VISIBLE);
                    mLayoutHeader.setVisibility(View.GONE);
                    rvMainListData.setVisibility(View.GONE);
                    return;
                }
                if (resultPermissionModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayResultPermissionList = new ArrayList<MarkSyllabusModel.FinalArray>();
                    finalArrayResultPermissionList = resultPermissionModel.getFinalArray();
                    if (resultPermissionModel.getFinalArray().size() > 0) {
                        txNoRecords.setVisibility(View.GONE);
                        mLayoutHeader.setVisibility(View.VISIBLE);
                        rvMainListData.setVisibility(View.VISIBLE);
                        resultPermissionAdapter = new MarkSyllabusPermissionAdapter(mContext, resultPermissionModel, new OnEditRecordWithPosition() {

                            @Override
                            public void getEditpermission(int pos) {
                                btnCancel.setVisibility(View.VISIBLE);
                                UpdatePermission(pos);
                            }
                        }, status);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        rvMainListData.setLayoutManager(mLayoutManager);
                        rvMainListData.setAdapter(resultPermissionAdapter);
                        rvMainListData.setItemAnimator(new DefaultItemAnimator());

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

    private Map<String, String> getResultPermission() {
        Map<String, String> map = new HashMap<>();
        map.put("TermId", FinalTermIdStr);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    // CALL Standard API HERE
    private void callTestNameForMarksSyllabusPermissionApi(String termId, String gradeId) {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTestNameForMarksSyllabusPermission(getStandardDetail(termId, gradeId), new retrofit.Callback<TestModel>() {
            @Override
            public void success(TestModel standardModel, Response response) {
                Utils.dismissDialog();
                if (standardModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fLEmptyView.setVisibility(View.VISIBLE);
                    rvListData.setVisibility(View.GONE);
                    isExamAdded = false;
                    return;
                }
                if (standardModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fLEmptyView.setVisibility(View.VISIBLE);
                    rvListData.setVisibility(View.GONE);
                    isExamAdded = false;

                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("false")) {
                    // Utils.ping(mContext, getString(R.string.false_msg));
                    fLEmptyView.setVisibility(View.VISIBLE);
                    rvListData.setVisibility(View.GONE);
                    isExamAdded = false;

                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("True")) {
                    isExamAdded = true;

                    fLEmptyView.setVisibility(View.GONE);
                    rvListData.setVisibility(View.VISIBLE);
                    finalArrayTestList = standardModel.getFinalArray();
                    if (finalArrayTestList != null) {
                        for (int i = 0; i < finalArrayTestList.size(); i++) {
                            finalArrayTestList.get(i).setCheckedStatus("0");
                        }
                        standardAdapter = new MarkSyllabusExamListAdapter(mContext, finalArrayTestList);
                        rvListData.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rvListData.setAdapter(standardAdapter);
                    }

                    if (!isRefreshRequired) {
                        if (standardAdapter != null) {
                            for (int i = 0; i < standardAdapter.getDatas().size(); i++) {
                                if (testName.equalsIgnoreCase(standardAdapter.getDatas().get(i).getTestName())) {
                                    standardAdapter.clearCheckedStatus();
                                    standardAdapter.updateData(i);
                                    break;
                                }
                            }
                            testName = "";
                            standardAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                fLEmptyView.setVisibility(View.VISIBLE);
                isExamAdded = false;
                rvListData.setVisibility(View.GONE);
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getStandardDetail(String termId, String gradeId) {
        Map<String, String> map = new HashMap<>();
        map.put("TermID", termId);
        map.put("GradeID", gradeId);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    // CALL InsertResultPermission
    public void callInsertResultPermission() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().insertMarksSyllabusPermission(getInsertResultPermission(), new retrofit.Callback<InsertMenuPermissionModel>() {
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
                    // Utils.ping(mContext, getString(R.string.true_msg));

                    markCheckBox.setChecked(false);
                    mSyllabusCheckBox.setChecked(false);
                    rbActive.setChecked(false);
                    rbInActive.setChecked(false);
                    mBtnAddUpdate.setText("Add");
                    callTermApi();
                    isRefreshRequired = true;
                    callGradeApi();
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

    private Map<String, String> getInsertResultPermission() {
        Map<String, String> map = new HashMap<>();
        map.put("TermID", FinalTermIdStr);

        if (markCheckText != null && !TextUtils.isEmpty(markCheckText)) {
            map.put("Type", markCheckText);
        }
        if (syllabusCheckText != null && !TextUtils.isEmpty(syllabusCheckText)) {
            map.put("Type", syllabusCheckText);

        }
        if ((markCheckText != null && !TextUtils.isEmpty(markCheckText)) && (syllabusCheckText != null && !TextUtils.isEmpty(syllabusCheckText))) {
            map.put("Type", markCheckText + "," + syllabusCheckText);
        }


        markCheckText = "";
        syllabusCheckText = "";

        map.put("GradeID", FinalGradeIsStr);
        if (standardAdapter.getCheckedItems() != null) {
            if (standardAdapter.getCheckedItems().size() > 0) {
                String idList = standardAdapter.getCheckedItems().toString();
                String csv = idList.substring(1, idList.length() - 1).replace(", ", ",");
                map.put("Exams", csv);
            } else {
                map.put("Exams", "");
            }
        } else {
            map.put("Exams", "");
        }


        map.put("Status", FinalStatusStr);
        if (PID != null && !TextUtils.isEmpty(PID)) {
            map.put("PermissionID", PID);
            isRefreshRequired = false;
            PID = "";

        } else {
            isRefreshRequired = true;
            map.put("PermissionID", "0");

        }
        return map;
    }

    // Use for get the AllFinalValue for InsertPermission

    // Use For UpdatePermission
    public void UpdatePermission(int pos) {
        try {


            mBtnAddUpdate.setText("Update");
            ArrayList<String> academicYearArray = new ArrayList<String>();
            String grade = "", term = "", type = "", testName = "", status = "", PID = "";

            String rowValueStr = resultPermissionAdapter.getRowValue();
            Log.d("rowValueStr", rowValueStr);
            String[] spiltString = rowValueStr.split("\\|");
            term = spiltString[0];
            type = spiltString[1];
            grade = spiltString[2];
            testName = spiltString[3];
            this.testName = testName;
            status = spiltString[4];
            PID = spiltString[5];
            this.PID = PID;

            Log.e("mark iddddddd", PID);
//            statusArray = statusArray.substring(0, statusArray.length() - 1);

            markCheckBox.setChecked(false);
            mSyllabusCheckBox.setChecked(false);

            if (type.equalsIgnoreCase("marks")) {
                markCheckBox.setChecked(true);
            } else if (type.equalsIgnoreCase("result")) {
                markCheckBox.setChecked(true);
            } else if (type.equalsIgnoreCase("syllabus")) {
                mSyllabusCheckBox.setChecked(true);
            } else if (type.equalsIgnoreCase("marks,syllabus")) {
                markCheckBox.setChecked(true);
                mSyllabusCheckBox.setChecked(true);
            }

            if (finalArrayGetTermModels != null) {

                for (int termPos = 0; termPos < finalArrayGetTermModels.size(); termPos++) {
                    if (term.equalsIgnoreCase(finalArrayGetTermModels.get(termPos).getTerm())) {
                        sp_term.setSelection(termPos);
                        break;
                    }
                }
            }

            if (finalArrayStandardsList != null) {
                for (int termPos = 0; termPos < finalArrayStandardsList.size(); termPos++) {
                    if (grade.equalsIgnoreCase(finalArrayStandardsList.get(termPos).getStandard())) {
                        sp_grade.setSelection(termPos + 1);
                        isRefreshRequired = false;
                        break;
                    }
                }
            }

            if (status.equalsIgnoreCase(rbActive.getText().toString())) {
                rbActive.setChecked(true);
            } else if (status.equalsIgnoreCase(rbInActive.getText().toString())) {
                rbInActive.setChecked(true);
            }

            if (standardAdapter != null) {
                List<TestModel.FinalArray> standardArray1 = standardAdapter.getDatas();
                for (int i = 0; i < standardArray1.size(); i++) {
                    if (testName.equalsIgnoreCase(standardArray1.get(i).getTestName())) {
                        standardArray1.get(i).setCheckedStatus("1");
                        break;
                    }
                }
                standardAdapter.notifyDataSetChanged();
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
