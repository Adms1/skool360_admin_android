package anandniketan.com.skool360.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.ExapandableListAdapterSMSRepoetData;
import anandniketan.com.skool360.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.skool360.Model.Student.StudentAttendanceModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentSmsreportBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SMSReportFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static String dateFinal;
    private static boolean isFromDate = false;
    int Year, Month, Day;
    Calendar calendar;
    List<StudentAttendanceFinalArray> finalArrayinquiryCountList, totalSMSArray;
    String FinalStartDateStr, FinalEndDateStr;
    ExapandableListAdapterSMSRepoetData exapandableListAdapterSMSRepoetData;
    List<String> listDataHeader;
    HashMap<String, List<StudentAttendanceFinalArray>> listDataChild;
    private FragmentSmsreportBinding fragmentSmsreportBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private DatePickerDialog datePickerDialog;
    private int lastExpandedPosition = -1;
    private String viewstatus;

    private TextView tvHeader, tvTotalSMS, tvDeliveredSMS, tvOtherSMS;
    private Button btnBack, btnMenu;

    public SMSReportFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentSmsreportBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_smsreport, container, false);

        rootView = fragmentSmsreportBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvTotalSMS = view.findViewById(R.id.total_sms_txt);
        tvDeliveredSMS = view.findViewById(R.id.delivered_sms_txt);
        tvOtherSMS = view.findViewById(R.id.other_sms_txt);

        tvHeader.setText(R.string.SMS_report);

        setListners();

    }

    public void setListners() {

        Bundle bundle = this.getArguments();
        viewstatus = bundle.getString("viewstatus");

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        fragmentSmsreportBinding.startdateButton.setText(Utils.getTodaysDate());
        fragmentSmsreportBinding.enddateButton.setText(Utils.getTodaysDate());

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
        fragmentSmsreportBinding.lvExpviewsmsreport.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {


            @Override

            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    fragmentSmsreportBinding.lvExpviewsmsreport.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }

        });
        fragmentSmsreportBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSMSReportDataApi();
            }
        });
        fragmentSmsreportBinding.startdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromDate = true;
                datePickerDialog = DatePickerDialog.newInstance(SMSReportFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });
        fragmentSmsreportBinding.enddateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromDate = false;
                datePickerDialog = DatePickerDialog.newInstance(SMSReportFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });

        tvTotalSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finalArrayinquiryCountList = new ArrayList<>();
                finalArrayinquiryCountList = totalSMSArray;

                fillExpLV();
                exapandableListAdapterSMSRepoetData = new ExapandableListAdapterSMSRepoetData(getActivity(), listDataHeader, listDataChild, viewstatus);
                fragmentSmsreportBinding.lvExpviewsmsreport.setAdapter(exapandableListAdapterSMSRepoetData);

            }
        });

        tvDeliveredSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finalArrayinquiryCountList = new ArrayList<>();

                for (int i = 0; i < totalSMSArray.size(); i++) {
                    if (totalSMSArray.get(i).getStatus().equalsIgnoreCase("delivered")) {
                        finalArrayinquiryCountList.add(totalSMSArray.get(i));
                    }
                }

                fillExpLV();
                exapandableListAdapterSMSRepoetData = new ExapandableListAdapterSMSRepoetData(getActivity(), listDataHeader, listDataChild, viewstatus);
                fragmentSmsreportBinding.lvExpviewsmsreport.setAdapter(exapandableListAdapterSMSRepoetData);

            }
        });

        tvOtherSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finalArrayinquiryCountList = new ArrayList<>();

                for (int i = 0; i < totalSMSArray.size(); i++) {
                    if (totalSMSArray.get(i).getStatus().equalsIgnoreCase("pending")) {
                        finalArrayinquiryCountList.add(totalSMSArray.get(i));
                    }
                }

                fillExpLV();
                exapandableListAdapterSMSRepoetData = new ExapandableListAdapterSMSRepoetData(getActivity(), listDataHeader, listDataChild, viewstatus);
                fragmentSmsreportBinding.lvExpviewsmsreport.setAdapter(exapandableListAdapterSMSRepoetData);

            }
        });

    }

    // CALL SMSReportData API HERE
    private void callSMSReportDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getAllSMSDetail(getSMSReportDetail(), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel inquiryDataModel, Response response) {
                if (inquiryDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (inquiryDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (inquiryDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
                    fragmentSmsreportBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentSmsreportBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentSmsreportBinding.listHeader.setVisibility(View.GONE);
                    fragmentSmsreportBinding.linearCount.setVisibility(View.GONE);
                    return;
                }
                if (inquiryDataModel.getSuccess().equalsIgnoreCase("True")) {
                    fragmentSmsreportBinding.totalSmsTxt.setText("Total SMS : " + inquiryDataModel.getTotal());
                    fragmentSmsreportBinding.deliveredSmsTxt.setText("Delivered SMS : " + inquiryDataModel.getDelivered());
                    fragmentSmsreportBinding.otherSmsTxt.setText("Other SMS : " + inquiryDataModel.getOther());

                    finalArrayinquiryCountList = inquiryDataModel.getFinalArray();

                    totalSMSArray = inquiryDataModel.getFinalArray();

                    if (finalArrayinquiryCountList != null) {
                        fragmentSmsreportBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentSmsreportBinding.lvExpHeader.setVisibility(View.VISIBLE);
                        fragmentSmsreportBinding.listHeader.setVisibility(View.VISIBLE);
                        fragmentSmsreportBinding.linearCount.setVisibility(View.VISIBLE);
                        fillExpLV();
                        exapandableListAdapterSMSRepoetData = new ExapandableListAdapterSMSRepoetData(getActivity(), listDataHeader, listDataChild, viewstatus);
                        fragmentSmsreportBinding.lvExpviewsmsreport.setAdapter(exapandableListAdapterSMSRepoetData);
                        Utils.dismissDialog();
                    } else {
                        fragmentSmsreportBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentSmsreportBinding.lvExpHeader.setVisibility(View.GONE);
                        fragmentSmsreportBinding.listHeader.setVisibility(View.GONE);
                        fragmentSmsreportBinding.linearCount.setVisibility(View.GONE);

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

    private Map<String, String> getSMSReportDetail() {
        getFinalAllId();
        Map<String, String> map = new HashMap<>();
        map.put("StartDate", FinalStartDateStr);
        map.put("EndDate", FinalEndDateStr);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        populateSetDate(year, monthOfYear + 1, dayOfMonth);
    }

    public void populateSetDate(int year, int month, int day) {
        String d, m, y;
        d = Integer.toString(day);
        m = Integer.toString(month);
        y = Integer.toString(year);
        if (day < 10) {
            d = "0" + d;
        }
        if (month < 10) {
            m = "0" + m;
        }

        dateFinal = d + "/" + m + "/" + y;
        if (isFromDate) {
            fragmentSmsreportBinding.startdateButton.setText(dateFinal);
        } else {
            fragmentSmsreportBinding.enddateButton.setText(dateFinal);
        }
    }

    public void getFinalAllId() {
        FinalStartDateStr = fragmentSmsreportBinding.startdateButton.getText().toString();
        FinalEndDateStr = fragmentSmsreportBinding.enddateButton.getText().toString();
    }

    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, List<StudentAttendanceFinalArray>>();

        for (int i = 0; i < finalArrayinquiryCountList.size(); i++) {
            listDataHeader.add(finalArrayinquiryCountList.get(i).getMobileNo() + "|" +
                    finalArrayinquiryCountList.get(i).getSendtime() + "|" +
                    finalArrayinquiryCountList.get(i).getRectime() + "|" +
                    finalArrayinquiryCountList.get(i).getDeliverstatus());
            Log.d("header", "" + listDataHeader);
            ArrayList<StudentAttendanceFinalArray> row = new ArrayList<StudentAttendanceFinalArray>();

            row.add(finalArrayinquiryCountList.get(i));
            Log.d("row", "" + row);

            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }

    }

}

