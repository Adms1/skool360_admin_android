package com.skool360admin.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
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

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.ExpandableDailyHrAccountAdapter;
import anandniketan.com.bhadajadmin.Model.HR.DailyAccountModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentHrAccountBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class HrAccountFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    FragmentHrAccountBinding fragmentHrAccountBinding;
    private View rootView;
    private List<String> monthNames;
    private List<String> years;
    private HashMap<Integer, String> spinnerMonthMap;
    private HashMap<Integer, String> spinnerYearMap;
    private String monthId = "",yearId = "";
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private Context mContext;
    private List<DailyAccountModel.FinalArray> finalArrayInOut;
    private ExpandableDailyHrAccountAdapter exapndableInOutSummaryAdapter;
    private List<String> listDataHeader;
    private HashMap<String, ArrayList<DailyAccountModel.FinalArray>> listDataChild;
    private int whichDateClicked = 1 ;
    private DatePickerDialog datePickerDialog;
    private int Year, Month, Day,hour,minute,second;
    private int mYear, mMonth, mDay;
    private static String dateFinal = "";
    private Calendar calendar;
    private String viewstatus;
    private int lastExpandedPosition = -1;
    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public HrAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentHrAccountBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_hr_account,container,false);
        mContext = getActivity().getApplicationContext();


        return fragmentHrAccountBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.account);

        setListners();
        callDailyTransportationApi();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void setListners() {

        Bundle bundle = this.getArguments();
        viewstatus = bundle.getString("viewstatus");

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);

        fragmentHrAccountBinding.fromdateBtn.setText(Utils.getTodaysDate());
        fragmentHrAccountBinding.todateBtn.setText(Utils.getTodaysDate());

        fragmentHrAccountBinding.fromdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichDateClicked = 1;
                datePickerDialog = DatePickerDialog.newInstance(HrAccountFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show(getActivity().getFragmentManager(),"DatePickerDialog");
            }
        });

        fragmentHrAccountBinding.todateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichDateClicked = 2;
                datePickerDialog = DatePickerDialog.newInstance(HrAccountFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show(getActivity().getFragmentManager(),"DatePickerDialog");
            }
        });

        fragmentHrAccountBinding.transportationList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    fragmentHrAccountBinding.transportationList.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new DailyReportFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentHrAccountBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDailyTransportationApi();
            }
        });


    }
    private void callDailyTransportationApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error),getResources().getString(R.string.internet_connection_error),getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getDailyAccount(getInOutParams(),new retrofit.Callback<DailyAccountModel>() {
            @Override
            public void success(DailyAccountModel announcementModel, Response response) {
                Utils.dismissDialog();
                if (announcementModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentHrAccountBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentHrAccountBinding.expHeader.setVisibility(View.GONE);
                    return;
                }
                if (announcementModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentHrAccountBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentHrAccountBinding.expHeader.setVisibility(View.GONE);
                    return;
                }
                if (announcementModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentHrAccountBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentHrAccountBinding.expHeader.setVisibility(View.GONE);

                    return;
                }
                if (announcementModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayInOut = announcementModel.getFinalArray();
                    if (finalArrayInOut != null) {
                        fragmentHrAccountBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentHrAccountBinding.expHeader.setVisibility(View.VISIBLE);
                        fillExpLV();
                        exapndableInOutSummaryAdapter = new ExpandableDailyHrAccountAdapter(getActivity(), listDataHeader, listDataChild, viewstatus);
                        fragmentHrAccountBinding.transportationList.setAdapter(exapndableInOutSummaryAdapter);
                    } else {
                        fragmentHrAccountBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentHrAccountBinding.expHeader.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                fragmentHrAccountBinding.txtNoRecords.setVisibility(View.VISIBLE);
                fragmentHrAccountBinding.expHeader.setVisibility(View.GONE);
                fragmentHrAccountBinding.txtNoRecords.setText(error.getMessage());
                Utils.ping(mContext,getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getInOutParams() {
        Map<String, String> map = new HashMap<>();
        map.put("StartDate",fragmentHrAccountBinding.fromdateBtn.getText().toString());
        map.put("EndDate",fragmentHrAccountBinding.todateBtn.getText().toString());
        return map;
    }

    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, ArrayList<DailyAccountModel.FinalArray>>();
        for (int i = 0; i < finalArrayInOut.size(); i++) {
            listDataHeader.add(finalArrayInOut.get(i).getDate()+"|"+finalArrayInOut.get(i).getEmployeeName());
            Log.d("header", "" + listDataHeader);
            ArrayList<DailyAccountModel.FinalArray> row = new ArrayList<DailyAccountModel.FinalArray>();
            row.add(finalArrayInOut.get(i));
            Log.d("row", "" + row);
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        populateSetDate(year,monthOfYear,dayOfMonth);
    }
    public void populateSetDate(int year, int month, int day) {
        String d, m, y;
        d = Integer.toString(day);
        m = Integer.toString(month + 1);
        y = Integer.toString(year);
        if (day < 10) {
            d = "0" + d;
        }


        if (month < 10) {
            m = "0"+ m;
        }

        dateFinal = d + "/" +m+"/" + y;

        if(whichDateClicked == 1) {
            fragmentHrAccountBinding.fromdateBtn.setText(dateFinal);
        }else if(whichDateClicked == 2){
            fragmentHrAccountBinding.todateBtn.setText(dateFinal);
        }
    }


}
