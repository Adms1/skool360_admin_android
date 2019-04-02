package anandniketan.com.skool360.Fragment.Fragment;

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
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.SuggestionPermissionAdapter;
import anandniketan.com.skool360.Interface.onDeleteButton;
import anandniketan.com.skool360.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.skool360.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.skool360.Model.Student.StudentAttendanceModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentSuggestionPermissionBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.content.ContentValues.TAG;


public class SuggestionPermissionFragment extends Fragment {

    protected ArrayList<CharSequence> selectedmonthForString = new ArrayList<>();
    protected ArrayList<CharSequence> selectedmonth = new ArrayList<>();
    //Use for employee spinner
    List<StudentAttendanceFinalArray> finalArrayStandardsList;
    HashMap<Integer, String> spinnerEmployeeMap;
    //Use for fill SuggestionPermission List
    SuggestionPermissionAdapter suggestionPermissionAdapter;
    String FinalEmployeeIdStr, FinalEmployeeStr, FinaltypeStr, FinalDeleteIdStr;
    CharSequence[] m_months;
    ArrayList<String> month_no = new ArrayList<>();
    ArrayList<String> MonthID_Arr;
    private FragmentSuggestionPermissionBinding fragmentsuggestionBinding;
    private View rootView;
    private Context mContext;
    private String status, updatestatus, deletestatus;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public SuggestionPermissionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentsuggestionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_suggestion_permission, container, false);

        rootView = fragmentsuggestionBinding.getRoot();
        mContext = Objects.requireNonNull(getActivity()).getApplicationContext();

        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 13;

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            status = bundle.getString("suggestionviewstatus");
            updatestatus = bundle.getString("suggestionupdatestatus");
            deletestatus = bundle.getString("suggestiondeletestatus");
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

        tvHeader.setText(R.string.suggestionpermission);

        setListners();
        fillType();
        callEmployeeApi();

//        if(status.equalsIgnoreCase("true")) {
        callSuggestionPermissionApi();
//        }else {
//            Utils.ping(getActivity(), "Access Denied");
//        }
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
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 13;

//                fragment = new StudentPermissionFragment();
//                fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction()
//                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
//                        .replace(R.id.frame_container, fragment).commit();
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });

        fragmentsuggestionBinding.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentsuggestionBinding.typeSpinner.getSelectedItem().toString();


                Log.d("value", name);
                //  FinaltypeIdStr = getid.toString();


                FinaltypeStr = name;
                Log.d("FinaltypeStr", FinaltypeStr);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentsuggestionBinding.assignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedsectionstr = fragmentsuggestionBinding.assignSpinner.getSelectedItem().toString();
                String getid = spinnerEmployeeMap.get(fragmentsuggestionBinding.assignSpinner.getSelectedItemPosition());

                Log.d("value", selectedsectionstr + " " + getid);
                FinalEmployeeIdStr = getid;
                Log.d("FinalEmployeeIdStr", FinalEmployeeIdStr);

                FinalEmployeeStr = selectedsectionstr;
                Log.d("FinalEmployeeStr", FinalEmployeeStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        fragmentsuggestionBinding.assignSpinner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                selectedmonthForString.clear();
//                boolean[] checkedMOnths = new boolean[m_months.length];
//                int count = m_months.length;
//
//                for (int i = 0; i < count; i++)
//                    checkedMOnths[i] = selectedmonth.contains(m_months[i]);
//
//                DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//                        if (isChecked) {
//                            selectedmonth.add(m_months[which]);
//                            month_no.add(MonthID_Arr.get(which));
//                        } else {
//                            selectedmonth.remove(m_months[which]);
//                            month_no.remove(MonthID_Arr.get(which));
//                            fragmentsuggestionBinding.assignSpinner.setText("");
//                        }
//                    }
//                };
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Select Employee");
//                builder.setMultiChoiceItems(m_months, checkedMOnths, coloursDialogListener);
//                builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        selectedmonthForString.clear();
//                        for (int i = 0; i < m_months.length; i++) {
//                            for (int j = 0; j < selectedmonth.size(); j++) {
//                                if (m_months[i].equals(selectedmonth.get(j))) {
//                                    selectedmonthForString.add(m_months[i]);
//                                    onChangeSelectedMonth(selectedmonthForString);
//                                }
//                            }
//                        }
//
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });

        fragmentsuggestionBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (updatestatus.equalsIgnoreCase("true")) {

                    ArrayList<String> employeeId = new ArrayList<>();
                    FinalEmployeeIdStr = "";
                    if (fragmentsuggestionBinding.assignSpinner.getSelectedItem().toString().contains(",")) {
                        String[] split = fragmentsuggestionBinding.assignSpinner.getSelectedItem().toString().split("\\,");

                        for (int i = 0; i < split.length; i++) {
                            for (int j = 0; j < finalArrayStandardsList.size(); j++) {
                                if (finalArrayStandardsList.get(j).getEmployeeName().equalsIgnoreCase(split[i])) {
                                    employeeId.add(String.valueOf(finalArrayStandardsList.get(j).getEmployeeID()));
                                }
                            }
                        }
                    } else {
                        for (int j = 0; j < finalArrayStandardsList.size(); j++) {
                            if (finalArrayStandardsList.get(j).getEmployeeName().equalsIgnoreCase(fragmentsuggestionBinding.assignSpinner.getSelectedItem().toString())) {
                                employeeId.add(String.valueOf(finalArrayStandardsList.get(j).getEmployeeID()));
                            }
                        }
                    }
                    StringBuilder stringBuilder1 = new StringBuilder();
                    for (CharSequence empId : employeeId) {
                        Log.i(TAG, "size = " + employeeId.size());
                        if (employeeId.size() <= 1) {
                            stringBuilder1.append(empId);
                        } else {
                            stringBuilder1.append(empId + ",");
                        }
                    }
                    if (employeeId.size() <= 1) {
                        FinalEmployeeIdStr = stringBuilder1.toString().trim();
                    } else {
                        FinalEmployeeIdStr = stringBuilder1.toString().trim().substring(0, stringBuilder1.length() - 1);
                    }

                    Log.d(" FinalEmployeeIdStr", FinalEmployeeIdStr);
                    if (!FinaltypeStr.equalsIgnoreCase("-Please Select-")) {
                        if (!FinalEmployeeIdStr.equalsIgnoreCase("")) {
                            callInsertSuggestionPermission();
                        } else {
                            Utils.ping(mContext, "Please select assign to");
                        }
                    } else {
                        Utils.ping(mContext, "Please select type");
                    }
                } else {
                    Utils.ping(getActivity(), "Access Denied");
                }
            }
        });
    }

//    protected void onChangeSelectedMonth(ArrayList<CharSequence> selectedmonth) {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (CharSequence month : selectedmonth) {
//            Log.i(TAG, "size = " + selectedmonth.size());
//            if (selectedmonth.size() <= 1) {
//                stringBuilder.append(month);
//            } else {
//                stringBuilder.append(month + ",");
//            }
//        }
//        if (selectedmonth.size() <= 1) {
//            fragmentsuggestionBinding.assignSpinner.setText(stringBuilder.toString().trim());
//        } else {
//            fragmentsuggestionBinding.assignSpinner.setText(stringBuilder.toString().trim().substring(0, stringBuilder.length() - 1));
//        }
//
//    }

    // CALL Employee API HERE
    private void callEmployeeApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getEmployeeForSuggestionPermission(getEmployeeDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel employeeModel, Response response) {
                Utils.dismissDialog();
                if (employeeModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (employeeModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (employeeModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (employeeModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayStandardsList = employeeModel.getFinalArray();
                    if (finalArrayStandardsList != null) {
                        MonthID_Arr = new ArrayList<>();
                        m_months = new String[finalArrayStandardsList.size()];
                        for (int i = 0; i < finalArrayStandardsList.size(); i++) {
                            m_months[i] = finalArrayStandardsList.get(i).getEmployeeName();
                            Log.d("months", m_months[i].toString());
                            MonthID_Arr.add(String.valueOf(finalArrayStandardsList.get(i).getEmployeeID()));
                        }

                        fillAssignSpinner();
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

    private Map<String, String> getEmployeeDetail() {
        return new HashMap<>();
    }

    // CALL SuggestionPermission API HERE
    private void callSuggestionPermissionApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getSuggestionPermission(getSuggestionPermissionDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel suggestionPermissionModel, Response response) {
                Utils.dismissDialog();
                if (suggestionPermissionModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (suggestionPermissionModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (suggestionPermissionModel.getSuccess().equalsIgnoreCase("false")) {
//                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentsuggestionBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentsuggestionBinding.lvHeader.setVisibility(View.GONE);
                    fragmentsuggestionBinding.recyclerLinear.setVisibility(View.GONE);
                    return;
                }
                if (suggestionPermissionModel.getSuccess().equalsIgnoreCase("True")) {
                    if (suggestionPermissionModel.getFinalArray().size() > 0) {
                        fragmentsuggestionBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentsuggestionBinding.lvHeader.setVisibility(View.VISIBLE);
                        fragmentsuggestionBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        suggestionPermissionAdapter = new SuggestionPermissionAdapter(mContext, suggestionPermissionModel, new onDeleteButton() {
                            @Override
                            public void deleteSentMessage() {
                                FinalDeleteIdStr = String.valueOf(suggestionPermissionAdapter.getId());

                                FinalDeleteIdStr = FinalDeleteIdStr.substring(1, FinalDeleteIdStr.length() - 1);
                                Log.d("FinalDeleteId", FinalDeleteIdStr);
                                if (!FinalDeleteIdStr.equalsIgnoreCase("")) {
                                    callDeleteSuggestionPermission();
                                }
                            }
                        }, deletestatus);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        fragmentsuggestionBinding.suggestionList.setLayoutManager(mLayoutManager);
                        fragmentsuggestionBinding.suggestionList.setItemAnimator(new DefaultItemAnimator());
                        fragmentsuggestionBinding.suggestionList.setAdapter(suggestionPermissionAdapter);
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

    private Map<String, String> getSuggestionPermissionDetail() {
        return new HashMap<>();
    }


    // CALL InsertSuggestionPermission
    public void callInsertSuggestionPermission() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().InsertSuggestionPermission(getInsertSuggestionPermission(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<InsertMenuPermissionModel>() {
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
//                    Utils.ping(mContext, getString(R.string.true_msg));
//                    if (status.equalsIgnoreCase("true")) {
                    callSuggestionPermissionApi();
//                    } else {
//                        Utils.ping(getActivity(), "Access Denied");
//                    }
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

    private Map<String, String> getInsertSuggestionPermission() {
        Map<String, String> map = new HashMap<>();
        map.put("Type", FinaltypeStr);
        map.put("StaffIDs", FinalEmployeeIdStr);
        return map;
    }


    // CALL DeleteSuggestionPermission
    public void callDeleteSuggestionPermission() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().deleteSuggestionPermission(getDeleteSuggestionPermission(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<InsertMenuPermissionModel>() {
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
//                    Utils.ping(mContext, getString(R.string.true_msg));
//                    if (status.equalsIgnoreCase("true")) {
                    callSuggestionPermissionApi();
//                    } else {
//                        Utils.ping(getActivity(), "Access Denied");
//                    }
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

    private Map<String, String> getDeleteSuggestionPermission() {
        Map<String, String> map = new HashMap<>();
        map.put("AssignPermissionID", FinalDeleteIdStr);

        return map;
    }


    //use for fill assign spinner
//    public void fillAssignSpinner() {
//
//    }
//    //use for fill assign spinner
    public void fillAssignSpinner() {
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("-Please Select-");

        ArrayList<String> employeename = new ArrayList<>();
        for (int z = 0; z < firstValue.size(); z++) {
            employeename.add(firstValue.get(z));
            for (int i = 0; i < finalArrayStandardsList.size(); i++) {
                employeename.add(finalArrayStandardsList.get(i).getEmployeeName());
            }
        }
        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        ArrayList<Integer> employeeId = new ArrayList<>();
        for (int m = 0; m < firstValueId.size(); m++) {
            employeeId.add(firstValueId.get(m));
            for (int j = 0; j < finalArrayStandardsList.size(); j++) {
                employeeId.add(finalArrayStandardsList.get(j).getEmployeeID());
            }
        }
        String[] spinneremployeeIdArray = new String[employeeId.size()];

        spinnerEmployeeMap = new HashMap<>();
        for (int i = 0; i < employeeId.size(); i++) {
            spinnerEmployeeMap.put(i, String.valueOf(employeeId.get(i)));
            spinneremployeeIdArray[i] = employeename.get(i).trim();
        }

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentsuggestionBinding.assignSpinner);

            popupWindow.setHeight(spinneremployeeIdArray.length > 4 ? 500 : spinneremployeeIdArray.length * 100);
//            popupWindow1.setHeght(200);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }


        ArrayAdapter<String> adapterstandard = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinneremployeeIdArray);
        fragmentsuggestionBinding.assignSpinner.setAdapter(adapterstandard);

        FinalEmployeeIdStr = spinnerEmployeeMap.get(0);
    }

    //use for fill Type spinner
    public void fillType() {
        ArrayList<String> selectedArray = new ArrayList<>();
        selectedArray.add("-Please Select-");
        selectedArray.add("Academic");
        selectedArray.add("Admin");
        selectedArray.add("Other");


        //Collections.sort(selectedArray);
        System.out.println("Sorted ArrayList in Java - Ascending order : " + selectedArray);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentsuggestionBinding.typeSpinner);

            popupWindow.setHeight(selectedArray.size() > 1 ? 200 : selectedArray.size() * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<String> adapterSpinYear = new ArrayAdapter<>(mContext, R.layout.spinner_layout, selectedArray);
        fragmentsuggestionBinding.typeSpinner.setAdapter(adapterSpinYear);

        fragmentsuggestionBinding.typeSpinner.setSelection(0);
    }


}

