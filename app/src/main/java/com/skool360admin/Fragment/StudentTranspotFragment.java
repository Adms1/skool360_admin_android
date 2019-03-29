package com.skool360admin.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.ExpandableListAdapterStudentTransportDetail;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.bhadajadmin.Model.Transport.FinalArrayTransportChargesModel;
import anandniketan.com.bhadajadmin.Model.Transport.PickupPointDetailModel;
import anandniketan.com.bhadajadmin.Model.Transport.TermModel;
import anandniketan.com.bhadajadmin.Model.Transport.TransportChargesModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentStudentTranspotBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class StudentTranspotFragment extends Fragment {

    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    HashMap<Integer, String> spinnerTermMap;
    List<FinalArrayTransportChargesModel> finalArrayRouteDetailModelList;
    List<PickupPointDetailModel> pickupPointDetailModelList;
    HashMap<Integer, String> spinnerRouteMap;
    HashMap<Integer, String> spinnerPickupMap;
    String FinalTermIdStr, FinalRouteIdStr = "", FinalPickupIdStr = "", FinalGrnoStr = "", RouteName;
    List<StudentAttendanceFinalArray> finalArrayStudentTransportModelList;
    List<String> listDataHeader;
    HashMap<String, ArrayList<StudentAttendanceFinalArray>> listDataChild;
    ExpandableListAdapterStudentTransportDetail expandableListAdapterStudentTransportDetail;
    private FragmentStudentTranspotBinding fragmentStudentTranspotBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private int lastExpandedPosition = -1;
    private String status;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public StudentTranspotFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStudentTranspotBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_transpot, container, false);

        rootView = fragmentStudentTranspotBinding.getRoot();
        mContext = Objects.requireNonNull(getActivity()).getApplicationContext();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            status = bundle.getString("status");
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

        tvHeader.setText(R.string.studenttransport);

        setListners();
        callTermApi();
        callRouteDetailApi();

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
                fragment = new StudentFragment();
                fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                }
            }
        });

        fragmentStudentTranspotBinding.lvExpStudenttransport.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {


            @Override

            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    fragmentStudentTranspotBinding.lvExpStudenttransport.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }

        });

        fragmentStudentTranspotBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentStudentTranspotBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentStudentTranspotBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid;
                Log.d("FinalTermIdStr", FinalTermIdStr);
                callStudentTransportDetailApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentStudentTranspotBinding.routeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentStudentTranspotBinding.routeSpinner.getSelectedItem().toString();
                String getid = spinnerRouteMap.get(fragmentStudentTranspotBinding.routeSpinner.getSelectedItemPosition());

                Log.d("routevalue", name + " " + getid);
                FinalRouteIdStr = getid;
                Log.d("FinalRouteIdStr", FinalRouteIdStr);
                RouteName = name;
                fillPickUpSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentStudentTranspotBinding.pickupPointSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentStudentTranspotBinding.pickupPointSpinner.getSelectedItem().toString();
                if (!name.equalsIgnoreCase("-Please Select-")) {
                    String getid = spinnerPickupMap.get(fragmentStudentTranspotBinding.pickupPointSpinner.getSelectedItemPosition());

                    Log.d("pickvalue", name + " " + getid);
                    FinalPickupIdStr = getid;
                    Log.d("FinalPickupIdStr", FinalPickupIdStr);
                } else {
                    FinalPickupIdStr = "";
                    Log.d("NotPickupIdStr", FinalPickupIdStr);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentStudentTranspotBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callStudentTransportDetailApi();
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
        return new HashMap<>();
    }

    // CALL RoputeDetail API HERE
    private void callRouteDetailApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getRouteDetail(getRouteDetail(), new retrofit.Callback<TransportChargesModel>() {
            @Override
            public void success(TransportChargesModel routeDetail, Response response) {
//                Utils.dismissDialog();
                if (routeDetail == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (routeDetail.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (routeDetail.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (routeDetail.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayRouteDetailModelList = routeDetail.getFinalArray();
                    if (finalArrayRouteDetailModelList != null) {
                        pickupPointDetailModelList = finalArrayRouteDetailModelList.get(0).getPickupPointDetail();
                        fillRouteSpinner();
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

    private Map<String, String> getRouteDetail() {

        return new HashMap<>();
    }

    // CALL Term API HERE
    private void callStudentTransportDetailApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStudentTransportDetail1(getStudentTransportDetail(), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel studentTransportDetailModel, Response response) {
//                Utils.dismissDialog();
                if (studentTransportDetailModel == null) {
                    Utils.dismissDialog();
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentTransportDetailModel.getSuccess() == null) {
                    Utils.dismissDialog();
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentTransportDetailModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.dismissDialog();
//                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentStudentTranspotBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentStudentTranspotBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentStudentTranspotBinding.lvExpStudenttransport.setVisibility(View.GONE);
                    return;
                }
                if (studentTransportDetailModel.getSuccess().equalsIgnoreCase("True")) {

                    finalArrayStudentTransportModelList = studentTransportDetailModel.getFinalArray();
                    if (finalArrayStudentTransportModelList != null) {
                        fragmentStudentTranspotBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentStudentTranspotBinding.lvExpStudenttransport.setVisibility(View.VISIBLE);
                        fillExpLV();
                        expandableListAdapterStudentTransportDetail = new ExpandableListAdapterStudentTransportDetail(getActivity(), listDataHeader, listDataChild, status);
                        fragmentStudentTranspotBinding.lvExpStudenttransport.setAdapter(expandableListAdapterStudentTransportDetail);
                        fragmentStudentTranspotBinding.lvExpHeader.setVisibility(View.VISIBLE);
                        Utils.dismissDialog();

                    } else {
                        fragmentStudentTranspotBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentStudentTranspotBinding.lvExpHeader.setVisibility(View.GONE);
                        fragmentStudentTranspotBinding.lvExpStudenttransport.setVisibility(View.GONE);
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

    private Map<String, String> getStudentTransportDetail() {
        FinalGrnoStr = fragmentStudentTranspotBinding.grnoTxt.getText().toString();
        Map<String, String> map = new HashMap<>();
        map.put("Term", FinalTermIdStr);
        if (FinalRouteIdStr.equalsIgnoreCase("0")) {
            FinalRouteIdStr = "All";
        }
        map.put("RouteID", FinalRouteIdStr);
        if (FinalPickupIdStr.equalsIgnoreCase("0")) {
            FinalPickupIdStr = "All";
        }
        map.put("PickupPoint", FinalPickupIdStr);
        map.put("GRNO", FinalGrnoStr);
        return map;
    }

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
//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentTranspotBinding.termSpinner);
//
//            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentStudentTranspotBinding.termSpinner.setAdapter(adapterTerm);
        fragmentStudentTranspotBinding.termSpinner.setSelection(1);

    }

    public void fillRouteSpinner() {
        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        ArrayList<Integer> RouteId = new ArrayList<>();
        for (int m = 0; m < firstValueId.size(); m++) {
            RouteId.add(firstValueId.get(m));
            for (int i = 0; i < finalArrayRouteDetailModelList.size(); i++) {
                RouteId.add(finalArrayRouteDetailModelList.get(i).getRouteID());
            }
        }
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("All");
        ArrayList<String> Route = new ArrayList<>();
        for (int z = 0; z < firstValue.size(); z++) {
            Route.add(firstValue.get(z));
            for (int j = 0; j < finalArrayRouteDetailModelList.size(); j++) {
                Route.add(finalArrayRouteDetailModelList.get(j).getRoute());
            }
        }
        String[] spinnerrouteIdArray = new String[RouteId.size()];

        spinnerRouteMap = new HashMap<>();
        for (int i = 0; i < RouteId.size(); i++) {
            spinnerRouteMap.put(i, String.valueOf(RouteId.get(i)));
            spinnerrouteIdArray[i] = Route.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentTranspotBinding.routeSpinner);

            popupWindow.setHeight(spinnerrouteIdArray.length > 4 ? 500 : spinnerrouteIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinnerrouteIdArray);
        fragmentStudentTranspotBinding.routeSpinner.setAdapter(adapterTerm);

    }

    public void fillPickUpSpinner() {
        List<PickupPointDetailModel> pickupArray = new ArrayList<>();


        for (int k = 0; k < finalArrayRouteDetailModelList.size(); k++) {
            for (int m = 0; m < finalArrayRouteDetailModelList.get(k).getPickupPointDetail().size(); m++) {
                if (RouteName.equalsIgnoreCase("All")) {
                    pickupArray.add(finalArrayRouteDetailModelList.get(k).getPickupPointDetail().get(m));
                } else if ((RouteName.equalsIgnoreCase(finalArrayRouteDetailModelList.get(k).getRoute()))) {
                    pickupArray.add(finalArrayRouteDetailModelList.get(k).getPickupPointDetail().get(m));
                }
            }
        }

        if (pickupArray.size() > 0) {
            ArrayList<Integer> PickupId = new ArrayList<>();
            ArrayList<Integer> firstValuePickId = new ArrayList<>();
            firstValuePickId.add(0);
            for (int a = 0; a < firstValuePickId.size(); a++) {
                PickupId.add(firstValuePickId.get(a));

                for (int i = 0; i < pickupArray.size(); i++) {
                    PickupId.add(pickupArray.get(i).getPickupPointID());
                }
            }
            ArrayList<String> PickupPoint = new ArrayList<>();
            ArrayList<String> firstValuePickPoint = new ArrayList<>();
            firstValuePickPoint.add("All");
            for (int r = 0; r < firstValuePickPoint.size(); r++) {
                PickupPoint.add(firstValuePickPoint.get(r));

                for (int j = 0; j < pickupArray.size(); j++) {
                    PickupPoint.add(pickupArray.get(j).getPickupPoint());
                }
            }
            String[] spinnerpickupIdArray = new String[PickupId.size()];

            spinnerPickupMap = new HashMap<>();
            for (int i = 0; i < PickupId.size(); i++) {
                spinnerPickupMap.put(i, String.valueOf(PickupId.get(i)));
                spinnerpickupIdArray[i] = PickupPoint.get(i).trim();
            }
            try {
                Field popup = Spinner.class.getDeclaredField("mPopup");
                popup.setAccessible(true);

                // Get private mPopup member variable and try cast to ListPopupWindow
                android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentTranspotBinding.pickupPointSpinner);

                popupWindow.setHeight(spinnerpickupIdArray.length > 4 ? 500 : spinnerpickupIdArray.length * 100);
            } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
                // silently fail...
            }

            ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinnerpickupIdArray);
            fragmentStudentTranspotBinding.pickupPointSpinner.setAdapter(adapterTerm);
        } else {
            ArrayList<String> pick = new ArrayList<>();
            pick.add("-Please Select-");
            ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(mContext, R.layout.spinner_layout, pick);
            fragmentStudentTranspotBinding.pickupPointSpinner.setAdapter(adapterTerm);
        }
    }

    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        for (int i = 0; i < finalArrayStudentTransportModelList.size(); i++) {
            listDataHeader.add(finalArrayStudentTransportModelList.get(i).getStudentName() + "|" +
                    finalArrayStudentTransportModelList.get(i).getGRNO() + "|" + finalArrayStudentTransportModelList.get(i).getStandard());
            Log.d("header", "" + listDataHeader);
            ArrayList<StudentAttendanceFinalArray> row = new ArrayList<>();
//            for (int j = 0; j < finalArrayStudentTransportModelList.size(); j++) {
            row.add(finalArrayStudentTransportModelList.get(i));
//            }
            Log.d("row", "" + row);
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }

    }
}

