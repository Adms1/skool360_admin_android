package anandniketan.com.skool360.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.StudentSubMenuAdapter;
import anandniketan.com.skool360.Model.IconHeaderModel;
import anandniketan.com.skool360.Model.PermissionDataModel;
import anandniketan.com.skool360.Model.Student.StudentAttendanceModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentSmBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

// Antra 29/01/2019
// Change main menu adapter & set Layouts

public class SMSFragment extends Fragment {

    public String[] mThumbIds = {
            AppConfiguration.BASEURL_IMAGES + "SMS/" + "Student%20Absent.png",
            AppConfiguration.BASEURL_IMAGES + "SMS/" + "Student Bulk%20SMS.png",
            AppConfiguration.BASEURL_IMAGES + "SMS/" + "Single%20SMS.png",
            AppConfiguration.BASEURL_IMAGES + "SMS/" + "Staff%20SMS.png",
            AppConfiguration.BASEURL_IMAGES + "SMS/" + "APP%20SMS.png",
            AppConfiguration.BASEURL_IMAGES + "SMS/" + "Student%20Transport.png",
            AppConfiguration.BASEURL_IMAGES + "SMS/" + "Student%20Marks.png",
            AppConfiguration.BASEURL_IMAGES + "SMS/" + "All%20SMS%20Report.png",
    };
    public String[] mThumbNames = {"Student Absent", "Bulk SMS", "Single SMS",
            "Staff SMS", "App SMS", "Student Transport", "Student Marks", "All SMS Report"};
    private FragmentSmBinding fragmentSmBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private Map<String, PermissionDataModel.Detaill> permissionMap;
    private ArrayList<IconHeaderModel> newArr;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public SMSFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentSmBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sm, container, false);

        rootView = fragmentSmBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Utils.callPermissionDetail(getActivity(), PrefUtils.getInstance(getActivity()).getStringValue("StaffID", "0"),
                PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));

        tvHeader = view.findViewById(R.id.home_sname_txt);
        btnBack = view.findViewById(R.id.home_btnBack);
        btnMenu = view.findViewById(R.id.home_btnmenu);

        tvHeader.setText(R.string.m_sms);

        initViews();
        setListners();

    }

    public void initViews() {

        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 3;

        permissionMap = PrefUtils.getInstance(getActivity()).loadMap(getActivity(), "SMS");
        newArr = new ArrayList<>();

        for (int i = 0; i < mThumbNames.length; i++) {
            if (permissionMap.containsKey(mThumbNames[i])) {

                IconHeaderModel iconHeaderModel = new IconHeaderModel();
                iconHeaderModel.setName(mThumbNames[i]);
                iconHeaderModel.setUrl(mThumbIds[i]);
                newArr.add(iconHeaderModel);
            }
        }

        Glide.with(mContext)
                .load(AppConfiguration.BASEURL_IMAGES + "Main/" + "SMS.png")
                .into(fragmentSmBinding.circleImageView);
        fragmentSmBinding.smsSubmenuGridView.setAdapter(new StudentSubMenuAdapter(mContext, newArr));
        callSMSReportDataApi();
    }

    public void setListners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new HomeFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        fragmentSmBinding.smsSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 && permissionMap.get("Student Absent").getStatus().equalsIgnoreCase("true")) {
                    fragment = new StudentAbsentFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 31;

                } else if (position == 1 && permissionMap.get("Bulk SMS").getStatus().equalsIgnoreCase("true")) {
                    fragment = new BullkSmsFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 31;

                } else if (position == 2 && permissionMap.get("Single SMS").getStatus().equalsIgnoreCase("true")) {
                    fragment = new SingleSmsFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 31;

                } else if (position == 3 && permissionMap.get("Staff SMS").getStatus().equalsIgnoreCase("true")) {
                    fragment = new EmployeeSmsFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 31;

                } else if (position == 4 && permissionMap.get("App SMS").getStatus().equalsIgnoreCase("true")) {
                    fragment = new AppSMSFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 31;
                } else if (position == 5) {
                    fragment = new SMSStudentTransportFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 31;

                } else if (position == 6) {
                    fragment = new SMSStudentMarksFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 31;

                } else if (position == 7 && permissionMap.get("All SMS Report").getStatus().equalsIgnoreCase("true")) {
                    fragment = new SMSReportFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("viewstatus", permissionMap.get("All SMS Report").getIsuserview());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 31;
                }
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
                    //  Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();

                    return;
                }
                if (inquiryDataModel.getSuccess().equalsIgnoreCase("True")) {
                    Utils.dismissDialog();
                    fragmentSmBinding.totalSmsCount.setText(inquiryDataModel.getTotal());
                    fragmentSmBinding.totalDeliveredSmsCount.setText(inquiryDataModel.getDelivered());
                    fragmentSmBinding.totalOtherSmsCount.setText(inquiryDataModel.getOther());


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
        Map<String, String> map = new HashMap<>();
        map.put("StartDate", Utils.getTodaysDate());
        map.put("EndDate", Utils.getTodaysDate());
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

}

