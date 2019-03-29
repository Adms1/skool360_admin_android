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
import anandniketan.com.bhadajadmin.Adapter.EmployeeInOutDetailsAdapter;
import anandniketan.com.bhadajadmin.Model.HR.EmployeeInOutDetailsModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentInOutSummaryDetailsBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class InOutSummaryDetailsFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    FragmentInOutSummaryDetailsBinding fragmentEmployeePresentDetailBinding;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private Context mContext;
    private int whichDateClicked = 1 ;
    private DatePickerDialog datePickerDialog;
    private int Year, Month, Day,hour,minute,second;
    private int mYear, mMonth, mDay;
    private Calendar calendar;
    private static String dateFinal = "";
    private List<EmployeeInOutDetailsModel.FinalArray> finalArrayEmlopyee;
    private EmployeeInOutDetailsAdapter employeePresentDetailAdapter;
    private String viewstatus;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        fragmentEmployeePresentDetailBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_in_out_summary_details, container, false);

        Bundle bundle = this.getArguments();
        viewstatus = bundle.getString("");

        return fragmentEmployeePresentDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.inoutsummarydetail);

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
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentEmployeePresentDetailBinding.fromdateBtn1.setText(Utils.getTodaysDate());
        fragmentEmployeePresentDetailBinding.todateBtn1.setText(Utils.getTodaysDate());


        fragmentEmployeePresentDetailBinding.fromdateBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichDateClicked = 1;
                datePickerDialog = DatePickerDialog.newInstance(InOutSummaryDetailsFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show(getActivity().getFragmentManager(),"DatePickerDialog");
            }
        });

        fragmentEmployeePresentDetailBinding.todateBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichDateClicked = 2;
                datePickerDialog = DatePickerDialog.newInstance(InOutSummaryDetailsFragment.this, Year, Month, Day);
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
        ApiHandler.getApiService().getEmployeeInOutDetails(getInOutParams(), new retrofit.Callback<EmployeeInOutDetailsModel>() {
            @Override
            public void success(EmployeeInOutDetailsModel announcementModel,Response response) {
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

                        employeePresentDetailAdapter = new EmployeeInOutDetailsAdapter(getActivity(),finalArrayEmlopyee);
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
                fragmentEmployeePresentDetailBinding.txtNoRecords.setVisibility(View.VISIBLE);
                fragmentEmployeePresentDetailBinding.recylerHeader.setVisibility(View.GONE);
                fragmentEmployeePresentDetailBinding.employeeList.setVisibility(View.GONE);
                fragmentEmployeePresentDetailBinding.txtNoRecords.setText(error.getMessage());
                Utils.ping(mContext,getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getInOutParams() {
        Map<String, String> map = new HashMap<>();
        map.put("StartDate",fragmentEmployeePresentDetailBinding.fromdateBtn1.getText().toString());
        map.put("EndDate",fragmentEmployeePresentDetailBinding.todateBtn1.getText().toString());
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
            fragmentEmployeePresentDetailBinding.fromdateBtn1.setText(dateFinal);

        }else if(whichDateClicked == 2){
            fragmentEmployeePresentDetailBinding.todateBtn1.setText(dateFinal);
        }
    }
}
