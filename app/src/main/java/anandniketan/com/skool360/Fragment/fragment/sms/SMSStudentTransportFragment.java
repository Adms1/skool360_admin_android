package anandniketan.com.skool360.Fragment.fragment.sms;

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

import anandniketan.com.skool360.Interface.getEmployeeCheck;
import anandniketan.com.skool360.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.skool360.Model.Other.FinalArraySMSDataModel;
import anandniketan.com.skool360.Model.Other.GetStaffSMSDataModel;
import anandniketan.com.skool360.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.Model.Transport.FinalArrayTransportChargesModel;
import anandniketan.com.skool360.Model.Transport.PickupPointDetailModel;
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.Model.Transport.TransportChargesModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.activity.DashboardActivity;
import anandniketan.com.skool360.adapter.BulkSMSDetailListAdapter;
import anandniketan.com.skool360.databinding.FragmentSmsstudentTransportBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SMSStudentTransportFragment extends Fragment {

    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    HashMap<Integer, String> spinnerTermMap;
    List<FinalArrayTransportChargesModel> finalArrayRouteDetailModelList;
    List<PickupPointDetailModel> pickupPointDetailModelList;
    HashMap<Integer, String> spinnerRouteMap;
    HashMap<Integer, String> spinnerPickupMap;
    String FinalTermIdStr, FinalRouteIdStr = "", FinalPickupIdStr = "", RouteName;
    //    List<FinalArraySMSDataModel> finalArrayBulkSMSModelList;
    BulkSMSDetailListAdapter bulkSMSDetailListAdapter;
    String finalBulkIdArray, finalmessageMessageLine, finalDateStr;
    List<FinalArraySMSDataModel> finalArrayStudentTransportModelList;
    List<String> listDataHeader;
    HashMap<String, ArrayList<StudentAttendanceFinalArray>> listDataChild;
    private FragmentSmsstudentTransportBinding fragmentSmsstudentTransportBinding;
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

    public SMSStudentTransportFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentSmsstudentTransportBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_smsstudent_transport, container, false);

        rootView = fragmentSmsstudentTransportBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.studenttransport);

        setListners();
        callTermApi();

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

        fragmentSmsstudentTransportBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentSmsstudentTransportBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentSmsstudentTransportBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid;
                Log.d("FinalTermIdStr", FinalTermIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentSmsstudentTransportBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callStudentTransportDetailApi();
            }
        });
        fragmentSmsstudentTransportBinding.smsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < finalArrayStudentTransportModelList.size(); i++) {
                        finalArrayStudentTransportModelList.get(i).setCheck("1");
                    }
                    bulkSMSDetailListAdapter.notifyDataSetChanged();
                    temp = false;
                } else {
                    if (!temp) {
                        for (int i = 0; i < finalArrayStudentTransportModelList.size(); i++) {
                            finalArrayStudentTransportModelList.get(i).setCheck("0");
                        }
                        bulkSMSDetailListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        fragmentSmsstudentTransportBinding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage();
            }
        });
        fragmentSmsstudentTransportBinding.routeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentSmsstudentTransportBinding.routeSpinner.getSelectedItem().toString();
                String getid = spinnerRouteMap.get(fragmentSmsstudentTransportBinding.routeSpinner.getSelectedItemPosition());

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
        fragmentSmsstudentTransportBinding.pickupPointSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentSmsstudentTransportBinding.pickupPointSpinner.getSelectedItem().toString();
                String getid = spinnerPickupMap.get(fragmentSmsstudentTransportBinding.pickupPointSpinner.getSelectedItemPosition());

                Log.d("pickupvalue", name + " " + getid);
                FinalPickupIdStr = getid;
                Log.d("FinalPickupIdStr", FinalPickupIdStr);
//                RouteName = name;
//                fillPickUpSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                        callRouteDetailApi();
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentSmsstudentTransportBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentSmsstudentTransportBinding.termSpinner.setAdapter(adapterTerm);
        FinalTermIdStr = spinnerTermMap.get(0);
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
        Map<String, String> map = new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    public void fillRouteSpinner() {
        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        ArrayList<Integer> RouteId = new ArrayList<Integer>();
        for (int m = 0; m < firstValueId.size(); m++) {
            RouteId.add(firstValueId.get(m));
            for (int i = 0; i < finalArrayRouteDetailModelList.size(); i++) {
                RouteId.add(finalArrayRouteDetailModelList.get(i).getRouteID());
            }
        }
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("All");
        ArrayList<String> Route = new ArrayList<String>();
        for (int z = 0; z < firstValue.size(); z++) {
            Route.add(firstValue.get(z));
            for (int j = 0; j < finalArrayRouteDetailModelList.size(); j++) {
                Route.add(finalArrayRouteDetailModelList.get(j).getRoute());
            }
        }
        String[] spinnerrouteIdArray = new String[RouteId.size()];

        spinnerRouteMap = new HashMap<Integer, String>();
        for (int i = 0; i < RouteId.size(); i++) {
            spinnerRouteMap.put(i, String.valueOf(RouteId.get(i)));
            spinnerrouteIdArray[i] = Route.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentSmsstudentTransportBinding.routeSpinner);

            popupWindow.setHeight(spinnerrouteIdArray.length > 4 ? 500 : spinnerrouteIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerrouteIdArray);
        fragmentSmsstudentTransportBinding.routeSpinner.setAdapter(adapterTerm);

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
            ArrayList<Integer> PickupId = new ArrayList<Integer>();
            ArrayList<Integer> firstValuePickId = new ArrayList<>();
            firstValuePickId.add(0);
            for (int a = 0; a < firstValuePickId.size(); a++) {
                PickupId.add(firstValuePickId.get(a));

                for (int i = 0; i < pickupArray.size(); i++) {
                    PickupId.add(pickupArray.get(i).getPickupPointID());
                }
            }
            ArrayList<String> PickupPoint = new ArrayList<String>();
            ArrayList<String> firstValuePickPoint = new ArrayList<String>();
            firstValuePickPoint.add("All");
            for (int r = 0; r < firstValuePickPoint.size(); r++) {
                PickupPoint.add(firstValuePickPoint.get(r));

                for (int j = 0; j < pickupArray.size(); j++) {
                    PickupPoint.add(pickupArray.get(j).getPickupPoint());
                }
            }
            String[] spinnerpickupIdArray = new String[PickupId.size()];

            spinnerPickupMap = new HashMap<Integer, String>();
            for (int i = 0; i < PickupId.size(); i++) {
                spinnerPickupMap.put(i, String.valueOf(PickupId.get(i)));
                spinnerpickupIdArray[i] = PickupPoint.get(i).trim();
            }
            try {
                Field popup = Spinner.class.getDeclaredField("mPopup");
                popup.setAccessible(true);

                // Get private mPopup member variable and try cast to ListPopupWindow
                android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentSmsstudentTransportBinding.pickupPointSpinner);

                popupWindow.setHeight(spinnerpickupIdArray.length > 4 ? 500 : spinnerpickupIdArray.length * 100);
            } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
                // silently fail...
            }

            ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerpickupIdArray);
            fragmentSmsstudentTransportBinding.pickupPointSpinner.setAdapter(adapterTerm);
        } else {
            ArrayList<String> pick = new ArrayList<String>();
            pick.add("All");
            ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, pick);
            fragmentSmsstudentTransportBinding.pickupPointSpinner.setAdapter(adapterTerm);
        }
    }

    // CALL Term API HERE
    private void callStudentTransportDetailApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStudentTransportDetail(getStudentTransportDetail(), new retrofit.Callback<GetStaffSMSDataModel>() {
            @Override
            public void success(GetStaffSMSDataModel studentTransportDetailModel, Response response) {
//                Utils.dismissDialog();
                if (studentTransportDetailModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentTransportDetailModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentTransportDetailModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.dismissDialog();
//                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentSmsstudentTransportBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentSmsstudentTransportBinding.listHeader.setVisibility(View.GONE);
                    fragmentSmsstudentTransportBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentSmsstudentTransportBinding.submitBtn.setVisibility(View.GONE);
                    return;
                }
                if (studentTransportDetailModel.getSuccess().equalsIgnoreCase("True")) {

                    finalArrayStudentTransportModelList = studentTransportDetailModel.getFinalArray();
                    if (finalArrayStudentTransportModelList != null) {
                        fragmentSmsstudentTransportBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentSmsstudentTransportBinding.listHeader.setVisibility(View.VISIBLE);
                        fragmentSmsstudentTransportBinding.recyclerLinear.setVisibility(View.VISIBLE);

                        if (finalArrayStudentTransportModelList.size() > 0) {
                            fillDataList();
                        }

                        Utils.dismissDialog();
                    } else {
                        fragmentSmsstudentTransportBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentSmsstudentTransportBinding.listHeader.setVisibility(View.GONE);
                        fragmentSmsstudentTransportBinding.recyclerLinear.setVisibility(View.GONE);
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
        map.put("GRNO", "");
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    //Use for fill the SMS Detail List
    public void fillDataList() {
        fragmentSmsstudentTransportBinding.txtNoRecords.setVisibility(View.GONE);
        fragmentSmsstudentTransportBinding.studentTrasportSmsDetailList.setVisibility(View.VISIBLE);
        fragmentSmsstudentTransportBinding.recyclerLinear.setVisibility(View.VISIBLE);
        fragmentSmsstudentTransportBinding.listHeader.setVisibility(View.VISIBLE);
        fragmentSmsstudentTransportBinding.submitBtn.setVisibility(View.VISIBLE);

        for (int k = 0; k < finalArrayStudentTransportModelList.size(); k++) {
            finalArrayStudentTransportModelList.get(k).setCheck("0");
        }
        bulkSMSDetailListAdapter = new BulkSMSDetailListAdapter(mContext, finalArrayStudentTransportModelList, new getEmployeeCheck() {
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
                    fragmentSmsstudentTransportBinding.smsCheckbox.setChecked(true);
                } else {
                    temp = true;
                    fragmentSmsstudentTransportBinding.smsCheckbox.setChecked(false);
                }
                if (data) {
                    fragmentSmsstudentTransportBinding.submitBtn.setEnabled(true);
                    fragmentSmsstudentTransportBinding.submitBtn.setAlpha(1);
                } else {
                    fragmentSmsstudentTransportBinding.submitBtn.setEnabled(false);
                    fragmentSmsstudentTransportBinding.submitBtn.setAlpha((float) 0.5);
                }
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        fragmentSmsstudentTransportBinding.studentTrasportSmsDetailList.setLayoutManager(mLayoutManager);
        fragmentSmsstudentTransportBinding.studentTrasportSmsDetailList.setItemAnimator(new DefaultItemAnimator());
        fragmentSmsstudentTransportBinding.studentTrasportSmsDetailList.setAdapter(bulkSMSDetailListAdapter);
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
                    ApiHandler.getApiService().InsertBulkSMSData(InsertBulkSMSData(), new retrofit.Callback<InsertMenuPermissionModel>() {
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
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

}

