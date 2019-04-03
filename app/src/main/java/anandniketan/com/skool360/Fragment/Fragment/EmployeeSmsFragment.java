package anandniketan.com.skool360.Fragment.Fragment;

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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.EmployeeSMSDeatilListAdapter;
import anandniketan.com.skool360.Interface.getEmployeeCheck;
import anandniketan.com.skool360.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.skool360.Model.Other.FinalArraySMSDataModel;
import anandniketan.com.skool360.Model.Other.GetStaffSMSDataModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentEmployeeSmsBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EmployeeSmsFragment extends Fragment {

    List<FinalArraySMSDataModel> finalArraySMSDataModelList;
    EmployeeSMSDeatilListAdapter employeeSMSDeatilListAdapter;
    String finalEmployeeIdArray, finalmessageMessageLine, finalDateStr;
    private FragmentEmployeeSmsBinding fragmentEmployeeSmsBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private AlertDialog alertDialogAndroid = null;
    private TextView date_txt, message_edt;
    private Button send_btn, close_btn;
    private boolean temp = false;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public EmployeeSmsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentEmployeeSmsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_sms, container, false);

        rootView = fragmentEmployeeSmsBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.employeesms);

        initView();
        setListners();
        callStaffSMSDataApi();

    }

    public void initView() {

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

        fragmentEmployeeSmsBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage();
            }
        });

        fragmentEmployeeSmsBinding.smsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < finalArraySMSDataModelList.size(); i++) {
                        finalArraySMSDataModelList.get(i).setCheck("1");
                    }
                    employeeSMSDeatilListAdapter.notifyDataSetChanged();
                    temp = false;
                } else {
                    if (!temp) {
                        for (int i = 0; i < finalArraySMSDataModelList.size(); i++) {
                            finalArraySMSDataModelList.get(i).setCheck("0");
                        }
                        employeeSMSDeatilListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    // CALL StaffSMSData API HERE
    private void callStaffSMSDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStaffSMSData(getStaffSMSData(), new retrofit.Callback<GetStaffSMSDataModel>() {
            @Override
            public void success(GetStaffSMSDataModel staffSMSDataModel, Response response) {
                Utils.dismissDialog();
                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    if (staffSMSDataModel.getFinalArray().size() == 0) {
                        fragmentEmployeeSmsBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentEmployeeSmsBinding.employeeSmsDetailList.setVisibility(View.GONE);
                        fragmentEmployeeSmsBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentEmployeeSmsBinding.listHeader.setVisibility(View.GONE);
                    }
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArraySMSDataModelList = staffSMSDataModel.getFinalArray();
                    if (finalArraySMSDataModelList != null) {
                        fillDataList();
                        Utils.dismissDialog();
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

    private Map<String, String> getStaffSMSData() {
        Map<String, String> map = new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    public void fillDataList() {
        fragmentEmployeeSmsBinding.txtNoRecords.setVisibility(View.GONE);
        fragmentEmployeeSmsBinding.employeeSmsDetailList.setVisibility(View.VISIBLE);
        fragmentEmployeeSmsBinding.recyclerLinear.setVisibility(View.VISIBLE);
        fragmentEmployeeSmsBinding.listHeader.setVisibility(View.VISIBLE);
        for (int k = 0; k < finalArraySMSDataModelList.size(); k++) {
            finalArraySMSDataModelList.get(k).setCheck("0");
        }

        employeeSMSDeatilListAdapter = new EmployeeSMSDeatilListAdapter(mContext, finalArraySMSDataModelList, new getEmployeeCheck() {
            @Override
            public void getEmployeeSMSCheck() {
                List<FinalArraySMSDataModel> updatedData = employeeSMSDeatilListAdapter.getDatas();
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
                    fragmentEmployeeSmsBinding.smsCheckbox.setChecked(true);
                } else {
                    temp = true;
                    fragmentEmployeeSmsBinding.smsCheckbox.setChecked(false);
                }
                if (data) {
                    fragmentEmployeeSmsBinding.saveBtn.setEnabled(true);
                    fragmentEmployeeSmsBinding.saveBtn.setAlpha(1);
                } else {
                    fragmentEmployeeSmsBinding.saveBtn.setEnabled(false);
                    fragmentEmployeeSmsBinding.saveBtn.setAlpha((float) 0.5);
                }
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        fragmentEmployeeSmsBinding.employeeSmsDetailList.setLayoutManager(mLayoutManager);
        fragmentEmployeeSmsBinding.employeeSmsDetailList.setItemAnimator(new DefaultItemAnimator());
        fragmentEmployeeSmsBinding.employeeSmsDetailList.setAdapter(employeeSMSDeatilListAdapter);
    }

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
                List<FinalArraySMSDataModel> array = employeeSMSDeatilListAdapter.getDatas();
                int j;
                for (j = 0; j < array.size(); j++) {
                    if (array.get(j).getCheck().equalsIgnoreCase("1")) {
                        id.add(array.get(j).getPKEmployeeID() + "|" + array.get(j).getEmpMobileNo());
                        Log.d("checkid", "" + id.size());
                    } else {
                        id.remove(array.get(j).getPKEmployeeID() + "|" + array.get(j).getEmpMobileNo());
                        Log.d("Uncheckid", "" + id.size());
                    }
                }
                Log.d("id", "" + id);
                finalEmployeeIdArray = String.valueOf(id);
                finalEmployeeIdArray = finalEmployeeIdArray.substring(1, finalEmployeeIdArray.length() - 1);

                finalmessageMessageLine = message_edt.getText().toString();
                finalDateStr = date_txt.getText().toString();
                Log.d("finalEmployeeIdArray", "" + finalEmployeeIdArray);

                if (!Utils.checkNetwork(mContext)) {
                    Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
                    return;
                }
                if (!finalEmployeeIdArray.equalsIgnoreCase("") && !finalmessageMessageLine.equalsIgnoreCase("") && !finalDateStr.equalsIgnoreCase("")) {
                    Utils.showDialog(getActivity());
                    ApiHandler.getApiService().InsertStaffSMSData(InsertSingleSMSDetail(), new retrofit.Callback<InsertMenuPermissionModel>() {
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

    private Map<String, String> InsertSingleSMSDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("SMS", finalmessageMessageLine);
        map.put("Date", finalDateStr);
        map.put("MobileNo", finalEmployeeIdArray);//finalEmployeeIdArray  "1|8672952197"
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

}

