package com.skool360admin.Fragment;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.ExapandableSchoolResultAdapter;
import anandniketan.com.bhadajadmin.Adapter.MISStudentRangeDetailAdapter;
import anandniketan.com.bhadajadmin.Interface.ResponseCallBack;
import anandniketan.com.bhadajadmin.Model.Account.FinalArrayStandard;
import anandniketan.com.bhadajadmin.Model.Account.GetStandardModel;
import anandniketan.com.bhadajadmin.Model.MIS.MISStudentRange;
import anandniketan.com.bhadajadmin.Model.MIS.MISStudentResultDataModel;
import anandniketan.com.bhadajadmin.Model.MIS.MIStudentWiseResultModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiClient;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.Utility.WebServices;
import anandniketan.com.bhadajadmin.databinding.FragmentClasswiseResultBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Callback;

// changed by Antra 10/01/2019

public class MISClasswiseResultFragment extends Fragment implements ResponseCallBack {

    boolean dispatchMode = false;
    private View rootView;
    private FragmentClasswiseResultBinding fragmentClasswiseResultBinding;
    private List<FinalArrayStandard> finalArrayStandardsList;
    private Context mContext;
    private HashMap<Integer, String> spinnerStandardMap;
    private String FinalTermIdStr, FinalstandardIdStr = "", FinalStandardIdStr = "", StandardName = "", FinalStandardStr = "", FinalClassIdStr = "", FinalSectionStr = "", FinalRangeStr = "", FinalRangeIdStr = "";
    private HashMap<Integer, String> spinnerSectionMap;
    private HashMap<Integer, String> spinnerStatusMap;
    private Fragment fragment = null;
    private RecyclerView rvList;
    private FragmentManager fragmentManager = null;
    private View ll;
    private ProgressBar progressBar;
    private List<String> listDataHeader;
    private HashMap<String, ArrayList<MISStudentResultDataModel.TermDatum>> listDataChild;
    private List<MISStudentResultDataModel.FinalArray> finalArrayAnnouncementFinal;
    private ExapandableSchoolResultAdapter expandableSchoolResultAdapter;
    private ResponseCallBack responseCallBack;
    private int lastExpandedPosition = -1;

    public MISClasswiseResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        responseCallBack = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentClasswiseResultBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_classwise_result, container, false);

        mContext = getActivity();
        rootView = fragmentClasswiseResultBinding.getRoot();

        ll = rootView.findViewById(R.id.lvExp_header_one);
        rvList = rootView.findViewById(R.id.rvStudentList);
        progressBar = rootView.findViewById(R.id.studentlist_progress);

        setListener();

        callStandardApi();

        return rootView;
    }


    private void setListener() {
        progressBar.setVisibility(View.GONE);
        fragmentClasswiseResultBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentClasswiseResultBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppConfiguration.position = 58;
                AppConfiguration.firsttimeback = true;

                getActivity().onBackPressed();


//                getActivity().getSupportFragmentManager().popBackStackImmediate();
//                fragment = new MISFragment();
//                fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentClasswiseResultBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (fragmentClasswiseResultBinding.rangeSpinner.getSelectedItem().toString().equalsIgnoreCase("No")) {
                    callResultClassWise2();
                } else {
                    callStudentRangeDetail(FinalStandardIdStr, FinalClassIdStr);
                }
            }
        });

        fragmentClasswiseResultBinding.gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentClasswiseResultBinding.gradeSpinner.getSelectedItem().toString();
                String getid = spinnerStandardMap.get(fragmentClasswiseResultBinding.gradeSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalStandardIdStr = getid;
                Log.d("FinalStandardIdStr", FinalStandardIdStr);
                StandardName = name;
                if (name.equalsIgnoreCase("All")) {
                    FinalStandardStr = "0";
                } else {
                    FinalStandardStr = name;
                }
                Log.d("StandardName", FinalStandardStr);
                fillSection();
                fillRange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fragmentClasswiseResultBinding.sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedsectionstr = fragmentClasswiseResultBinding.sectionSpinner.getSelectedItem().toString();
                String getid = spinnerSectionMap.get(fragmentClasswiseResultBinding.sectionSpinner.getSelectedItemPosition());

                Log.d("value", selectedsectionstr + " " + getid);
                FinalClassIdStr = getid;
                if (selectedsectionstr.equalsIgnoreCase("All")) {
                    FinalSectionStr = "0";
                } else {
                    FinalSectionStr = selectedsectionstr;
                }

                Log.d("FinalClassIdStr", FinalSectionStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentClasswiseResultBinding.rangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedstatusstr = fragmentClasswiseResultBinding.rangeSpinner.getSelectedItem().toString();
//                String getid = spinnerStatusMap.get(fragmentClasswiseResultBinding.rangeSpinner.getSelectedItemPosition());
//                Log.d("value", selectedstatusstr + "" + getid);
//                FinalRangeStr = selectedstatusstr;
//                Log.d("value", selectedstatusstr + " " + getid);
//                FinalRangeIdStr = getid;

                if (selectedstatusstr.equalsIgnoreCase("No")) {
                    FinalRangeStr = "";

                } else {

                    FinalRangeStr = selectedstatusstr;
                }

                Log.d("FinalStatusStr", FinalRangeStr);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fragmentClasswiseResultBinding.lvExpstudentlist.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                String studentId = String.valueOf(finalArrayAnnouncementFinal.get(i).getStudentID());
                callChildItemAPI(studentId);
                if (expandableListView.isGroupExpanded(i)) {
                    expandableListView.collapseGroup(i);
                } else {
                    expandableListView.expandGroup(i);
                }

                return true;
            }
        });

        fragmentClasswiseResultBinding.lvExpstudentlist.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {

                    fragmentClasswiseResultBinding.lvExpstudentlist.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }

        });
    }

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
        Map<String, String> map = new HashMap<>();
        return map;
    }


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

//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentClasswiseResultBinding.gradeSpinner);
//
//            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 500 : spinnerstandardIdArray.length * 100);
////            popupWindow1.setHeght(200);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }


        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        fragmentClasswiseResultBinding.gradeSpinner.setAdapter(adapterstandard);

        FinalStandardIdStr = spinnerStandardMap.get(0);
    }

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
//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentClasswiseResultBinding.sectionSpinner);
//
//            popupWindow.setHeight(spinnersectionIdArray.length > 4 ? 500 : spinnersectionIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }
        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnersectionIdArray);
        fragmentClasswiseResultBinding.sectionSpinner.setAdapter(adapterstandard);

        FinalSectionStr = spinnerSectionMap.get(0);

    }


    public void fillRange() {

        // RangeID  = 91% - 100%,81% - 90%,71 % - 80%,61% - 70%,51% - 60%,41% - 50%,33% - 40%,32% & Below

        ArrayList<String> statusdetailId = new ArrayList<String>();
//        statusdetailId.add(0);
        statusdetailId.add("All");
        statusdetailId.add("91% - 100%");
        statusdetailId.add("81% - 90%");
        statusdetailId.add("71 % - 80%");
        statusdetailId.add("61% - 70%");
        statusdetailId.add("51% - 60%");
        statusdetailId.add("41% - 50%");
        statusdetailId.add("33% - 40%");
        statusdetailId.add("32% & Below");

        ArrayList<String> statusdetail = new ArrayList<>();

//        statusdetail.add("All");
//        statusdetail.add("91% - 100%");
//        statusdetail.add("81% - 90%");
//        statusdetail.add("71 % - 80%");
//        statusdetail.add("61% - 70%");
//        statusdetail.add("51% - 60%");
//        statusdetail.add("41% - 50%");
//        statusdetail.add("33% - 40%");
//        statusdetail.add("32% & Below");

        statusdetail.add("No");
        statusdetail.add("Yes");

        String[] spinnerstatusdetailIdArray = new String[statusdetailId.size()];

//        spinnerStatusMap = new HashMap<Integer, String>();
//        for (int i = 0; i < statusdetailId.size(); i++) {
//            spinnerStatusMap.put(i, String.valueOf(statusdetailId.get(i)));
//            spinnerstatusdetailIdArray[i] = statusdetail.get(i).trim();
//        }
//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentClasswiseResultBinding.rangeSpinner);
//
//            popupWindow.setHeight(spinnerstatusdetailIdArray.length > 4 ? 500 : spinnerstatusdetailIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, statusdetail);
        fragmentClasswiseResultBinding.rangeSpinner.setAdapter(adapterTerm);
//        FinalRangeStr = spinnerStatusMap.get(0);
    }


////    private void callResultClassWise() {
////        if (!Utils.checkNetwork(mContext)) {
////            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error),getActivity());
////            return;
////        }
////        Utils.showDialog(getActivity());
////        ApiHandler.getApiService().getResultClassWise(getClasswiseResultParams(), new retrofit.Callback<MISClassWiseResultModel>() {
////            @Override
////            public void success(MISClassWiseResultModel studentFullDetailModel, Response response) {
////                Utils.dismissDialog();
////                if (studentFullDetailModel == null) {
////                    Utils.ping(mContext, getString(R.string.something_wrong));
////                    fragmentClasswiseResultBinding.tvNoRecords.setVisibility(View.VISIBLE);
////                    fragmentClasswiseResultBinding.lvExpHeader.setVisibility(View.GONE);
////                    fragmentClasswiseResultBinding.listHeader.setVisibility(View.GONE);
////                    return;
////                }
////                if (studentFullDetailModel.getSuccess() == null) {
////                    Utils.ping(mContext, getString(R.string.something_wrong));
////                    fragmentClasswiseResultBinding.tvNoRecords.setVisibility(View.VISIBLE);
////                    fragmentClasswiseResultBinding.lvExpHeader.setVisibility(View.GONE);
////                    fragmentClasswiseResultBinding.listHeader.setVisibility(View.GONE);
////                    return;
////                }
////                if (studentFullDetailModel.getSuccess().equalsIgnoreCase("False")) {
////                    Utils.ping(mContext, getString(R.string.false_msg));
////                    Utils.dismissDialog();
////                    fragmentClasswiseResultBinding.tvNoRecords.setVisibility(View.VISIBLE);
////                    fragmentClasswiseResultBinding.lvExpHeader.setVisibility(View.GONE);
////                    fragmentClasswiseResultBinding.listHeader.setVisibility(View.GONE);
////                    return;
////                }
////                if (studentFullDetailModel.getSuccess().equalsIgnoreCase("True")) {
////                    if (studentFullDetailModel.getFinalArray().size() > 0) {
////
////                        Utils.dismissDialog();
////
////                        finalArrayAnnouncementFinal = studentFullDetailModel.getFinalArray();
////                        if (finalArrayAnnouncementFinal != null) {
////                            fragmentClasswiseResultBinding.tvNoRecords.setVisibility(View.GONE);
////                            fragmentClasswiseResultBinding.lvExpHeader.setVisibility(View.VISIBLE);
////                            fragmentClasswiseResultBinding.listHeader.setVisibility(View.VISIBLE);
////                            fillExpLV();
////                            expandableSchoolResultAdapter = new ExapandableSchoolResultAdapter(getActivity(),listDataHeader,listDataChild,responseCallBack);
////                            fragmentClasswiseResultBinding.lvExpstudentlist.setAdapter(expandableSchoolResultAdapter);
////                        } else {
////                            fragmentClasswiseResultBinding.tvNoRecords.setVisibility(View.VISIBLE);
////                            fragmentClasswiseResultBinding.lvExpHeader.setVisibility(View.GONE);
////                            fragmentClasswiseResultBinding.listHeader.setVisibility(View.GONE);
////                        }
////
////
////                    } else {
////                     //   fragmentClasswiseResultBinding.txtNoRecords.setVisibility(View.VISIBLE);
////                    }
////                }
////            }
////
////            @Override
////            public void failure(RetrofitError error) {
//////                Utils.dismissDialog();
////                error.printStackTrace();
////                error.getMessage();
////                fragmentClasswiseResultBinding.tvNoRecords.setVisibility(View.VISIBLE);
////                fragmentClasswiseResultBinding.lvExpHeader.setVisibility(View.GONE);
////                fragmentClasswiseResultBinding.listHeader.setVisibility(View.GONE);
////                Utils.ping(mContext, getString(R.string.something_wrong));
////            }
////        });
//
//    }

    private void callResultClassWise2() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }
        Utils.showDialog(getActivity());
        //progressBar.setVisibility(View.VISIBLE);

        ApiHandler.getApiService().getResultData(getClasswiseResultParams(), new retrofit.Callback<MISStudentResultDataModel>() {
            @Override
            public void success(MISStudentResultDataModel studentFullDetailModel, Response response) {
                Utils.dismissDialog();
                if (studentFullDetailModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentClasswiseResultBinding.tvNoRecords.setVisibility(View.VISIBLE);
                    fragmentClasswiseResultBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentClasswiseResultBinding.listHeader.setVisibility(View.GONE);
                    ll.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (studentFullDetailModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentClasswiseResultBinding.tvNoRecords.setVisibility(View.VISIBLE);
                    fragmentClasswiseResultBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentClasswiseResultBinding.listHeader.setVisibility(View.GONE);
                    ll.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (studentFullDetailModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
                    fragmentClasswiseResultBinding.tvNoRecords.setVisibility(View.VISIBLE);
                    fragmentClasswiseResultBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentClasswiseResultBinding.listHeader.setVisibility(View.GONE);
                    ll.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (studentFullDetailModel.getSuccess().equalsIgnoreCase("True")) {
                    if (studentFullDetailModel.getFinalArray().size() > 0) {

                        Utils.dismissDialog();

                        finalArrayAnnouncementFinal = studentFullDetailModel.getFinalArray();
                        if (finalArrayAnnouncementFinal != null) {
                            fragmentClasswiseResultBinding.tvNoRecords.setVisibility(View.GONE);
                            fragmentClasswiseResultBinding.lvExpHeader.setVisibility(View.VISIBLE);
                            fragmentClasswiseResultBinding.listHeader.setVisibility(View.VISIBLE);
                            ll.setVisibility(View.GONE);
                            rvList.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);
                            fillExpLV();
                            expandableSchoolResultAdapter = new ExapandableSchoolResultAdapter(getActivity(), listDataHeader, listDataChild, responseCallBack);
                            fragmentClasswiseResultBinding.lvExpstudentlist.setAdapter(expandableSchoolResultAdapter);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            fragmentClasswiseResultBinding.tvNoRecords.setVisibility(View.VISIBLE);
                            fragmentClasswiseResultBinding.lvExpHeader.setVisibility(View.GONE);
                            fragmentClasswiseResultBinding.listHeader.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                        }

                    } else {
                        //   fragmentClasswiseResultBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
//                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                fragmentClasswiseResultBinding.tvNoRecords.setVisibility(View.VISIBLE);
                fragmentClasswiseResultBinding.lvExpHeader.setVisibility(View.GONE);
                fragmentClasswiseResultBinding.listHeader.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });
    }

    private Map<String, String> getClasswiseResultParams() {
        Map<String, String> map = new HashMap<>();
        map.put("TermDetailID", AppConfiguration.schoolResultTermID);

        if (FinalStandardStr.equalsIgnoreCase("0")) {
            map.put("StandardID", "");
        } else {
            map.put("StandardID", FinalStandardIdStr);
        }

        if (FinalSectionStr.equalsIgnoreCase("0")) {
            map.put("ClassID", "");
        } else {
            map.put("ClassID", FinalClassIdStr);
        }

        map.put("RangeID", FinalRangeStr);
        return map;
    }

    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, ArrayList<MISStudentResultDataModel.TermDatum>>();
        for (int i = 0; i < finalArrayAnnouncementFinal.size(); i++) {
            listDataHeader.add(finalArrayAnnouncementFinal.get(i).getStandard() + "|" + finalArrayAnnouncementFinal.get(i).getClassName() + "|" + finalArrayAnnouncementFinal.get(i).getName() + "|" + finalArrayAnnouncementFinal.get(i).getPercentage());
            Log.d("header", "" + listDataHeader);
            ArrayList<MISStudentResultDataModel.TermDatum> row = new ArrayList<MISStudentResultDataModel.TermDatum>();
            row.addAll(finalArrayAnnouncementFinal.get(i).getTermData());
            Log.d("row", "" + row);
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }

    }

    private void callChildItemAPI(String studentId) {
        ApiHandler.getApiService().getResultStudentWise(getStudentwiseResultParams(studentId), new retrofit.Callback<MIStudentWiseResultModel>() {
            @Override
            public void success(MIStudentWiseResultModel studentFullDetailModel, Response response) {
                Utils.dismissDialog();
                if (studentFullDetailModel == null) {
                    responseCallBack.onFailure(getString(R.string.false_msg));

                    return;
                }
                if (studentFullDetailModel.getSuccess() == null) {
                    responseCallBack.onFailure(getString(R.string.false_msg));

                    return;
                }
                if (studentFullDetailModel.getSuccess().equalsIgnoreCase("False")) {
                    responseCallBack.onFailure(getString(R.string.false_msg));
                    return;
                }
                if (studentFullDetailModel.getSuccess().equalsIgnoreCase("True")) {
                    if (studentFullDetailModel.getFinalArray().size() > 0) {

                        responseCallBack.onResponse(studentFullDetailModel.getFinalArray());

                    } else {

                    }
                }
            }


            @Override
            public void failure(RetrofitError error) {
                error.getMessage();
                responseCallBack.onFailure(error.getMessage());
            }
        });


    }

    private Map<String, String> getStudentwiseResultParams(String studentId) {
        Map<String, String> map = new HashMap<>();
        map.put("StudentID", studentId);
        return map;
    }


    @Override
    public void onResponse(List<MIStudentWiseResultModel.FinalArray> data) {
        expandableSchoolResultAdapter.onResponse(data);
    }

    @Override
    public void onFailure(String errorMessage) {
        expandableSchoolResultAdapter.onFailure(errorMessage);

    }

    private void callStudentRangeDetail(String standardid, String classid) {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
//        fragmentMisBinding.LLFinance.setVisibility(View.GONE);
        // Utils.showDialog(getActivity());
//        ApiHandler.getApiService().getHeadWiseFeesCollectionStudent(getFinanceListParams(studentId, term),new retrofit.Callback<MISFinanaceModel>() {
        WebServices apiService =
                ApiClient.getClient().create(WebServices.class);
        Call<MISStudentRange> call = apiService.getStudentRange(AppConfiguration.BASEURL + "GetResultRangeWise?StandardID=" + standardid + "&ClassID=" + classid);

        call.enqueue(new Callback<MISStudentRange>() {

            @Override
            public void onResponse(Call<MISStudentRange> call, retrofit2.Response<MISStudentRange> response) {
                //Utils.dismissDialog();
                progressBar.setVisibility(View.GONE);
                MISStudentRange staffSMSDataModel = response.body();
                if (staffSMSDataModel == null) {
                    rvList.setVisibility(View.GONE);
                    ll.setVisibility(View.GONE);
                    fragmentClasswiseResultBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentClasswiseResultBinding.listHeader.setVisibility(View.GONE);
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    rvList.setVisibility(View.GONE);
                    ll.setVisibility(View.GONE);
                    fragmentClasswiseResultBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentClasswiseResultBinding.listHeader.setVisibility(View.GONE);
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    progressBar.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                    ll.setVisibility(View.GONE);
                    fragmentClasswiseResultBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentClasswiseResultBinding.listHeader.setVisibility(View.GONE);
                    fragmentClasswiseResultBinding.tvNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {

                    progressBar.setVisibility(View.GONE);
                    ll.setVisibility(View.VISIBLE);
                    fragmentClasswiseResultBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentClasswiseResultBinding.listHeader.setVisibility(View.GONE);
                    rvList.setVisibility(View.VISIBLE);
                    fragmentClasswiseResultBinding.tvNoRecords.setVisibility(View.GONE);

                    rvList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    rvList.setAdapter(new MISStudentRangeDetailAdapter(getContext(), staffSMSDataModel.getFinalArray(), FinalClassIdStr, FinalStandardIdStr));

                }
            }

            @Override
            public void onFailure(Call<MISStudentRange> call, Throwable t) {
                t.printStackTrace();
                t.getMessage();
                progressBar.setVisibility(View.GONE);
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });
    }

}
