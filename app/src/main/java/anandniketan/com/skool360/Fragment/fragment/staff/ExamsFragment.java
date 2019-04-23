package anandniketan.com.skool360.Fragment.fragment.staff;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.Objects;

import anandniketan.com.skool360.Model.Staff.FinalArrayStaffModel;
import anandniketan.com.skool360.Model.Staff.StaffAttendaceModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.activity.DashboardActivity;
import anandniketan.com.skool360.adapter.ExamListAdapter;
import anandniketan.com.skool360.databinding.FragmentExamsBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ExamsFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static String dateFinal;
    private static boolean isFromDate = false;
    int Year, Month, Day;
    int mYear, mMonth, mDay;
    Calendar calendar;
    String FinalStartDateStr, FinalEndDateStr;
    ExamListAdapter examListAdapter;
    List<FinalArrayStaffModel> finalArrayExamsModel;
    private FragmentExamsBinding fragmentExamsBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private DatePickerDialog datePickerDialog;
    private String viewstatus;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public ExamsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentExamsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_exams, container, false);

        rootView = fragmentExamsBinding.getRoot();
        mContext = Objects.requireNonNull(getActivity()).getApplicationContext();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            viewstatus = bundle.getString("viewstatus");
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

        tvHeader.setText(R.string.exams);

        setListners();
        callExamsApi();


    }

    public void setListners() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        fragmentExamsBinding.startDateBtn.setText(Utils.getTodaysDate());
        fragmentExamsBinding.endDateBtn.setText(Utils.getTodaysDate());

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StaffFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentExamsBinding.startDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromDate = true;
                datePickerDialog = DatePickerDialog.newInstance(ExamsFragment.this, Year, Month, Day);
                datePickerDialog.setMaxDate(calendar);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });
        fragmentExamsBinding.endDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromDate = false;
                datePickerDialog = DatePickerDialog.newInstance(ExamsFragment.this, Year, Month, Day);
                datePickerDialog.setMaxDate(calendar);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });
        fragmentExamsBinding.filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewstatus.equalsIgnoreCase("true")) {

                    callExamsApi();
                } else {
                    Utils.ping(getActivity(), "Access Denied");
                }
            }
        });
    }

    // CALL Exams API HERE
    private void callExamsApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getExams(getExamsDetail(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel examsModel, Response response) {
                Utils.dismissDialog();
                if (examsModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (examsModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (examsModel.getSuccess().equalsIgnoreCase("false")) {
                    fragmentExamsBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentExamsBinding.examsHeaderLinear.setVisibility(View.GONE);
                    fragmentExamsBinding.listExamLinear.setVisibility(View.GONE);
//                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (examsModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayExamsModel = examsModel.getFinalArray();
                    if (finalArrayExamsModel != null) {
                        fragmentExamsBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentExamsBinding.examsHeaderLinear.setVisibility(View.VISIBLE);
                        fragmentExamsBinding.listExamLinear.setVisibility(View.VISIBLE);
                        examListAdapter = new ExamListAdapter(getActivity(), finalArrayExamsModel);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        fragmentExamsBinding.examList.setLayoutManager(mLayoutManager);
                        fragmentExamsBinding.examList.setItemAnimator(new DefaultItemAnimator());
                        fragmentExamsBinding.examList.setAdapter(examListAdapter);
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

    private Map<String, String> getExamsDetail() {
        FinalStartDateStr = fragmentExamsBinding.startDateBtn.getText().toString();
        FinalEndDateStr = fragmentExamsBinding.endDateBtn.getText().toString();

        Map<String, String> map = new HashMap<>();
        map.put("Stdt", FinalStartDateStr);
        map.put("EndDt", FinalEndDateStr);
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
            fragmentExamsBinding.startDateBtn.setText(dateFinal);
        } else {
            fragmentExamsBinding.endDateBtn.setText(dateFinal);
        }
    }
}

