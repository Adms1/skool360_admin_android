package anandniketan.com.skool360.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.ExpandableListAdapterLessonPlan;
import anandniketan.com.skool360.Model.Account.FinalArrayStandard;
import anandniketan.com.skool360.Model.Account.GetStandardModel;
import anandniketan.com.skool360.Model.Staff.FinalArrayStaffModel;
import anandniketan.com.skool360.Model.Staff.StaffAttendaceModel;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentViewLessonPlanBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ViewLessonPlanFragment extends Fragment {

    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    List<FinalArrayStandard> finalArrayStandardsList;
    List<FinalArrayStaffModel> finalArrayEmployeeList;
    HashMap<Integer, String> spinnerTermMap;
    HashMap<Integer, String> spinnerStandardMap;
    HashMap<Integer, String> spinnerSubjectMap;
    HashMap<Integer, String> spinneremployeeMap;
    String FinalTermIdStr, FinalStandardIdStr = "", FinalSubjectIdStr = "", FinalEmployeeIdStr = "", StandardName;
    List<FinalArrayStaffModel> finalArraylessonplanList;
    List<String> listDataHeader;
    HashMap<String, ArrayList<FinalArrayStaffModel>> listDataChild;
    ExpandableListAdapterLessonPlan expandableListAdapterLessonPlan;
    private FragmentViewLessonPlanBinding fragmentViewLessonPlanBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private int lastExpandedPosition = -1;

    public ViewLessonPlanFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentViewLessonPlanBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_lesson_plan, container, false);

        rootView = fragmentViewLessonPlanBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setListners();
        callTermApi();


        return rootView;
    }


    public void setListners() {

        fragmentViewLessonPlanBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentViewLessonPlanBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StaffFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentViewLessonPlanBinding.lvExpviewlwssonplan.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    fragmentViewLessonPlanBinding.lvExpviewlwssonplan.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        fragmentViewLessonPlanBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentViewLessonPlanBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentViewLessonPlanBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid;
                Log.d("FinalTermIdStr", FinalTermIdStr);

                fragmentViewLessonPlanBinding.standardSpinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        fragmentViewLessonPlanBinding.standardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentViewLessonPlanBinding.standardSpinner.getSelectedItem().toString();
                String getid = spinnerStandardMap.get(fragmentViewLessonPlanBinding.standardSpinner.getSelectedItemPosition());
                FinalStandardIdStr = getid;
                Log.d("FinalstandardIdStr", FinalStandardIdStr);
                StandardName = name;
                callSubjectApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentViewLessonPlanBinding.subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentViewLessonPlanBinding.subjectSpinner.getSelectedItem().toString();
                String getid = spinnerSubjectMap.get(fragmentViewLessonPlanBinding.subjectSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalSubjectIdStr = getid;
                Log.d("FinalSubjectIdStr", FinalSubjectIdStr);
                callEmployeeApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentViewLessonPlanBinding.employeeNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentViewLessonPlanBinding.employeeNameSpinner.getSelectedItem().toString();
                String getid = spinneremployeeMap.get(fragmentViewLessonPlanBinding.employeeNameSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalEmployeeIdStr = getid;
                Log.d("FinalEmployeeIdStr", FinalEmployeeIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentViewLessonPlanBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callLessonPlanApi();
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
                        fillStandardSpinner();
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

    // CALL Employee API HERE
    private void callEmployeeApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getEmployeeBySubject(getEmployeeDetail(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel employeeModel, Response response) {
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
                    finalArrayEmployeeList = employeeModel.getFinalArray();
                    if (finalArrayEmployeeList != null) {
                        fillEmployeeSpinner();
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
        Map<String, String> map = new HashMap<>();
        map.put("TermId", FinalTermIdStr);
        map.put("StandardID", FinalStandardIdStr);
        map.put("SubjectID", FinalSubjectIdStr);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    // CALL Employee API HERE
    private void callSubjectApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getLessonPlanSubject(getSubjectDetail(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel lessonplanSubjectModel, Response response) {
                Utils.dismissDialog();
                if (lessonplanSubjectModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (lessonplanSubjectModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (lessonplanSubjectModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (lessonplanSubjectModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayEmployeeList = lessonplanSubjectModel.getFinalArray();
                    if (finalArrayEmployeeList != null) {
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
        Map<String, String> map = new HashMap<>();
        map.put("TermId", FinalTermIdStr);
        map.put("StandardID", FinalStandardIdStr);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    // CALL LessonPlan API HERE
    private void callLessonPlanApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getLessonPlan(getLessonPlanDetail(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel lessonPlanModel, Response response) {
//                Utils.dismissDialog();
                if (lessonPlanModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (lessonPlanModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (lessonPlanModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.dismissDialog();
                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentViewLessonPlanBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentViewLessonPlanBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentViewLessonPlanBinding.lvExpviewlwssonplan.setVisibility(View.GONE);
                    return;
                }
                if (lessonPlanModel.getSuccess().equalsIgnoreCase("True")) {

                    finalArraylessonplanList = lessonPlanModel.getFinalArray();
                    if (finalArraylessonplanList != null) {
                        fragmentViewLessonPlanBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentViewLessonPlanBinding.lvExpHeader.setVisibility(View.VISIBLE);
                        fragmentViewLessonPlanBinding.lvExpviewlwssonplan.setVisibility(View.VISIBLE);
                        fillExpLV();
                        expandableListAdapterLessonPlan = new ExpandableListAdapterLessonPlan(getActivity(), listDataHeader, listDataChild);
                        fragmentViewLessonPlanBinding.lvExpviewlwssonplan.setAdapter(expandableListAdapterLessonPlan);
                        Utils.dismissDialog();
                    } else {
                        fragmentViewLessonPlanBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentViewLessonPlanBinding.lvExpHeader.setVisibility(View.GONE);
                        fragmentViewLessonPlanBinding.lvExpviewlwssonplan.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
//                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getLessonPlanDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("TermId", FinalTermIdStr);
        map.put("StandardId", FinalStandardIdStr);
        map.put("SubjectId", FinalSubjectIdStr);
        map.put("EmployeeId", FinalEmployeeIdStr);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentViewLessonPlanBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentViewLessonPlanBinding.termSpinner.setAdapter(adapterTerm);

        FinalTermIdStr = spinnerTermMap.get(0);
        callStandardApi();
    }

    //Use for fill the Standard Spinner
    public void fillStandardSpinner() {
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentViewLessonPlanBinding.standardSpinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 500 : spinnerstandardIdArray.length * 100);
//            popupWindow1.setHeght(200);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }


        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        fragmentViewLessonPlanBinding.standardSpinner.setAdapter(adapterstandard);

        FinalStandardIdStr = spinnerStandardMap.get(0);
    }

    //Use for fill the Subject Spinner
    public void fillSubjectSpinner() {
        ArrayList<Integer> SubjectID = new ArrayList<Integer>();
        for (int i = 0; i < finalArrayEmployeeList.size(); i++) {
            SubjectID.add(finalArrayEmployeeList.get(i).getSubjectID());
        }
        ArrayList<String> Subject = new ArrayList<String>();
        for (int j = 0; j < finalArrayEmployeeList.size(); j++) {
            Subject.add(finalArrayEmployeeList.get(j).getSubject());
        }

        String[] spinnersubjectIdArray = new String[SubjectID.size()];

        spinnerSubjectMap = new HashMap<Integer, String>();
        for (int i = 0; i < SubjectID.size(); i++) {
            spinnerSubjectMap.put(i, String.valueOf(SubjectID.get(i)));
            spinnersubjectIdArray[i] = Subject.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentViewLessonPlanBinding.subjectSpinner);

            popupWindow.setHeight(spinnersubjectIdArray.length > 4 ? 500 : spinnersubjectIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnersubjectIdArray);
        fragmentViewLessonPlanBinding.subjectSpinner.setAdapter(adapterTerm);

        FinalSubjectIdStr = spinnerSubjectMap.get(0);

    }

    //Use for fill the Employee Spinner
    public void fillEmployeeSpinner() {
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("--Select--");
        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        ArrayList<Integer> EmpolyeeId = new ArrayList<Integer>();
        for (int m = 0; m < firstValueId.size(); m++) {
            EmpolyeeId.add(firstValueId.get(m));
            for (int i = 0; i < finalArrayEmployeeList.size(); i++) {
                if (finalArrayEmployeeList.get(i).getEmployeeId().contains(",")) {
                    String[] spiltIdValue = finalArrayEmployeeList.get(i).getEmployeeId().split("\\,");
                    Log.d("id", spiltIdValue[0]);
                    for (int j = 0; j < spiltIdValue.length; j++) {
                        EmpolyeeId.add(Integer.valueOf(spiltIdValue[j]));
                    }
                } else {
                    EmpolyeeId.add(Integer.valueOf(finalArrayEmployeeList.get(i).getEmployeeId()));
                }

            }
        }
        ArrayList<String> Employee = new ArrayList<String>();
        for (int z = 0; z < firstValue.size(); z++) {
            Employee.add(firstValue.get(z));
            for (int j = 0; j < finalArrayEmployeeList.size(); j++) {
                if (finalArrayEmployeeList.get(j).getEmployee().contains(",")) {
                    String[] spiltnameValue = finalArrayEmployeeList.get(j).getEmployee().split("\\,");
                    Log.d("name", spiltnameValue[0]);
                    for (int i = 0; i < spiltnameValue.length; i++) {
                        Employee.add(spiltnameValue[i]);
                    }
                } else {
                    Employee.add(finalArrayEmployeeList.get(j).getEmployee());
                }
            }
        }

        String[] spinnerempolyeeIdArray = new String[EmpolyeeId.size()];

        spinneremployeeMap = new HashMap<Integer, String>();
        for (int i = 0; i < EmpolyeeId.size(); i++) {
            spinneremployeeMap.put(i, String.valueOf(EmpolyeeId.get(i)));
            spinnerempolyeeIdArray[i] = Employee.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentViewLessonPlanBinding.employeeNameSpinner);

            popupWindow.setHeight(spinnerempolyeeIdArray.length > 4 ? 500 : spinnerempolyeeIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerempolyeeIdArray);
        fragmentViewLessonPlanBinding.employeeNameSpinner.setAdapter(adapterTerm);

        FinalEmployeeIdStr = spinneremployeeMap.get(0);
    }

    //Use for fill the LessonList Spinner
    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, ArrayList<FinalArrayStaffModel>>();

        for (int i = 0; i < finalArraylessonplanList.size(); i++) {
            listDataHeader.add(finalArraylessonplanList.get(i).getChapterNo() + "|" +
                    finalArraylessonplanList.get(i).getChapterName() + "|" + finalArraylessonplanList.get(i).getEmployeeName() + "|" + finalArraylessonplanList.get(i).getiD());
            Log.d("header", "" + listDataHeader);
            ArrayList<FinalArrayStaffModel> row = new ArrayList<FinalArrayStaffModel>();
            row.add(finalArraylessonplanList.get(i));
            Log.d("row", "" + row);
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }

    }

}

