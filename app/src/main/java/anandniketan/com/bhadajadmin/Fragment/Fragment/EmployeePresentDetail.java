package anandniketan.com.bhadajadmin.Fragment.Fragment;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.EmployeePresentDetailAdapter;
import anandniketan.com.bhadajadmin.Model.HR.EmployeePresentDetailsModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentEmployeePresentDetailBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class EmployeePresentDetail extends Fragment implements DatePickerDialog.OnDateSetListener{

    FragmentEmployeePresentDetailBinding fragmentEmployeePresentDetailBinding;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private Context mContext;
    private int whichDateClicked = 1 ;
    private DatePickerDialog datePickerDialog;
    private int Year, Month, Day,hour,minute,second;
    private int mYear, mMonth, mDay;
    private Calendar calendar;
    private static String dateFinal = "";
    private List<EmployeePresentDetailsModel.FinalArray> finalArrayEmlopyee;
    private EmployeePresentDetailAdapter employeePresentDetailAdapter;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public EmployeePresentDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getActivity();
        fragmentEmployeePresentDetailBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_employee_present_detail, container, false);


        return fragmentEmployeePresentDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.employeepresentdetail);

        setListners();
        callEmployeePresentListApi();
    }


    private void setListners() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new AttendenceReportFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentEmployeePresentDetailBinding.fromdateBtn.setText(Utils.getTodaysDate());
        fragmentEmployeePresentDetailBinding.todateBtn.setText(Utils.getTodaysDate());


        fragmentEmployeePresentDetailBinding.fromdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichDateClicked = 1;
                datePickerDialog = DatePickerDialog.newInstance(EmployeePresentDetail.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show(getActivity().getFragmentManager(),"DatePickerDialog");
            }
        });

        fragmentEmployeePresentDetailBinding.todateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichDateClicked = 2;
                datePickerDialog = DatePickerDialog.newInstance(EmployeePresentDetail.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show(getActivity().getFragmentManager(),"DatePickerDialog");
            }
        });
        fragmentEmployeePresentDetailBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callEmployeePresentListApi();            }
        });
    }


    private void callEmployeePresentListApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getEmployeePresentDetails(getInOutParams(), new retrofit.Callback<EmployeePresentDetailsModel>() {
            @Override
            public void success(EmployeePresentDetailsModel announcementModel, Response response) {
                Utils.dismissDialog();
                if (announcementModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentEmployeePresentDetailBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentEmployeePresentDetailBinding.recylerHeader.setVisibility(View.GONE);
                    return;
                }
                if (announcementModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentEmployeePresentDetailBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentEmployeePresentDetailBinding.recylerHeader.setVisibility(View.GONE);
                    return;
                }
                if (announcementModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentEmployeePresentDetailBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentEmployeePresentDetailBinding.recylerHeader.setVisibility(View.GONE);

                    return;
                }
                if (announcementModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayEmlopyee = announcementModel.getFinalArray();
                    if (finalArrayEmlopyee != null && finalArrayEmlopyee.size() > 0) {
                        fragmentEmployeePresentDetailBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentEmployeePresentDetailBinding.recylerHeader.setVisibility(View.VISIBLE);

                        employeePresentDetailAdapter = new EmployeePresentDetailAdapter(getActivity(),finalArrayEmlopyee);
                        fragmentEmployeePresentDetailBinding.employeeList.setLayoutManager(new LinearLayoutManager(getActivity()));
                        fragmentEmployeePresentDetailBinding.employeeList.setAdapter(employeePresentDetailAdapter);
                    } else {
                        fragmentEmployeePresentDetailBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentEmployeePresentDetailBinding.recylerHeader.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
//                fragmentInOutSummaryBinding.txtNoRecords.setVisibility(View.VISIBLE);
//                fragmentInOutSummaryBinding.expHeader.setVisibility(View.GONE);
//                fragmentInOutSummaryBinding.txtNoRecords.setText(error.getMessage());
                Utils.ping(mContext,getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getInOutParams() {
        Map<String, String> map = new HashMap<>();
        map.put("StartDate",fragmentEmployeePresentDetailBinding.fromdateBtn.getText().toString());
        map.put("EndDate",fragmentEmployeePresentDetailBinding.todateBtn.getText().toString());
        return map;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        populateSetDate(year,monthOfYear,dayOfMonth);
    }

    public void populateSetDate(int year, int month, int day) {
        String d, m, y;
        d = Integer.toString(day);
        m = Integer.toString(month);
        y = Integer.toString(year);
        if (day < 10) {
            d = "0" + d;
        }
        if (month <= 9) {
            m = "0" + m;
        }
        dateFinal = d + "/" + String.valueOf(Integer.parseInt(m) + 1) + "/" + y;

        if(whichDateClicked == 1) {
            fragmentEmployeePresentDetailBinding.fromdateBtn.setText(dateFinal);
        }else if(whichDateClicked == 2){
            fragmentEmployeePresentDetailBinding.todateBtn.setText(dateFinal);
        }
    }
}
