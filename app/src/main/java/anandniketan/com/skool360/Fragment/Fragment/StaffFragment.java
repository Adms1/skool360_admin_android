package anandniketan.com.skool360.Fragment.Fragment;

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
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.StudentSubMenuAdapter;
import anandniketan.com.skool360.Model.IconHeaderModel;
import anandniketan.com.skool360.Model.PermissionDataModel;
import anandniketan.com.skool360.Model.Staff.FinalArrayStaffModel;
import anandniketan.com.skool360.Model.Staff.StaffAttendaceModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentStaffBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

//antra
//1. set image on top
//2. set permission
//3. Change main menu adapter

public class StaffFragment extends Fragment {

    public String[] mThumbIds = {
            AppConfiguration.BASEURL_IMAGES + "Staff/" + "Class%20Teacher.png",
            AppConfiguration.BASEURL_IMAGES + "Staff/" + "Assign%20Subject.png",
            AppConfiguration.BASEURL_IMAGES + "Staff/" + "Time%20Table.png",
            AppConfiguration.BASEURL_IMAGES + "Staff/" + "Add%20Exam_Syllabus.png",
            AppConfiguration.BASEURL_IMAGES + "Staff/" + "Home%20Work%20Not%20Done.png",
            AppConfiguration.BASEURL_IMAGES + "Staff/" + "My%20Leave.png",
            AppConfiguration.BASEURL_IMAGES + "Staff/" + "View%20Marks.png",
    };
    public String[] mThumbNames = {"Class Teacher", "Assign Subject", "Time Table",
            "Exam", "Home Work Not Done", "My Leave", "View Marks"};
    int Year, Month, Day;
    Calendar calendar;
    private FragmentStaffBinding fragmentStaffBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private String Datestr;
    private Map<String, PermissionDataModel.Detaill> permissionMap;
    private ArrayList<IconHeaderModel> newArr;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public StaffFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStaffBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_staff, container, false);

        rootView = fragmentStaffBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Utils.callPermissionDetail(getActivity(), PrefUtils.getInstance(getActivity()).getStringValue("StaffID", "0"),
                PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));

        newArr = new ArrayList<>();
        permissionMap = PrefUtils.getInstance(getActivity()).loadMap(getActivity(), "Student");

        tvHeader = view.findViewById(R.id.home_sname_txt);
        btnBack = view.findViewById(R.id.home_btnBack);
        btnMenu = view.findViewById(R.id.home_btnmenu);

        tvHeader.setText(R.string.staff);

        for (int i = 0; i < mThumbNames.length; i++) {
            if (permissionMap.containsKey(mThumbNames[i]) && permissionMap.get(mThumbNames[i]).getStatus().equalsIgnoreCase("true")) {

                IconHeaderModel iconHeaderModel = new IconHeaderModel();
                iconHeaderModel.setName(mThumbNames[i]);
                iconHeaderModel.setUrl(mThumbIds[i]);
                newArr.add(iconHeaderModel);
            }
        }

        initViews();
        setListners();
    }

    public void initViews() {
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 2;

        newArr = new ArrayList<>();
        permissionMap = PrefUtils.getInstance(getActivity()).loadMap(getActivity(), "Staff");

        for (int i = 0; i < mThumbNames.length; i++) {
//            if (permissionMap.containsKey(mThumbNames[i]) && permissionMap.get(mThumbNames[i]).getStatus().equalsIgnoreCase("true")) {
            if (permissionMap.containsKey(mThumbNames[i])) {

                IconHeaderModel iconHeaderModel = new IconHeaderModel();
                iconHeaderModel.setName(mThumbNames[i]);
                iconHeaderModel.setUrl(mThumbIds[i]);
                newArr.add(iconHeaderModel);
            }
        }

        fragmentStaffBinding.staffSubmenuGridView.setAdapter(new StudentSubMenuAdapter(mContext, newArr));
        Glide.with(mContext)
                .load(AppConfiguration.BASEURL_IMAGES + "Main/" + "Staff.png")
                .into(fragmentStaffBinding.circleImageView);
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        Datestr = Utils.getTodaysDate();
        Log.d("TodayDate", Datestr);
        callStaffApi();
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
                fragment = new HomeFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentStaffBinding.staffSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0 && permissionMap.get("Class Teacher").getStatus().equalsIgnoreCase("true")) {
                    fragment = new ClassTeacherFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("viewstatus", permissionMap.get("Class Teacher").getIsuserview());
                    bundle.putString("updatestatus", permissionMap.get("Class Teacher").getIsuserupdate());
                    bundle.putString("deletestatus", permissionMap.get("Class Teacher").getIsuserdelete());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 21;

                } else if (position == 1 && permissionMap.get("Assign Subject").getStatus().equalsIgnoreCase("true")) {
                    fragment = new AssignSubjectFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("viewstatus", permissionMap.get("Assign Subject").getIsuserview());
                    bundle.putString("updatestatus", permissionMap.get("Assign Subject").getIsuserupdate());
                    bundle.putString("deletestatus", permissionMap.get("Assign Subject").getIsuserdelete());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 21;

                } else if (position == 2 && permissionMap.get("Time Table").getStatus().equalsIgnoreCase("true")) {
                    fragment = new TimeTableFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("viewstatus", permissionMap.get("Time Table").getIsuserview());
                    bundle.putString("updatestatus", permissionMap.get("Time Table").getIsuserupdate());
                    bundle.putString("deletestatus", permissionMap.get("Time Table").getIsuserdelete());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 21;

                } else if (position == 3 && permissionMap.get("Exam").getStatus().equalsIgnoreCase("true")) {
                    fragment = new ExamsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("viewstatus", permissionMap.get("Exam").getIsuserview());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 21;

                } else if (position == 4 && permissionMap.get("Home Work Not Done").getStatus().equalsIgnoreCase("true")) {
                    fragment = new HomeworkNotDoneFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("viewstatus", permissionMap.get("Home Work Not Done").getIsuserview());
                    bundle.putString("updatestatus", permissionMap.get("Home Work Not Done").getIsuserupdate());
                    bundle.putString("deletestatus", permissionMap.get("Home Work Not Done").getIsuserdelete());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 21;
                } else if (position == 5 && permissionMap.get("My Leave").getStatus().equalsIgnoreCase("true")) {
                    fragment = new MyLeaveFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("viewstatus", permissionMap.get("My Leave").getIsuserview());
                    bundle.putString("updatestatus", permissionMap.get("My Leave").getIsuserupdate());
                    bundle.putString("deletestatus", permissionMap.get("My Leave").getIsuserdelete());

                    bundle.putString("applyviewstatus", permissionMap.get("Apply Leave").getIsuserview());
                    bundle.putString("applyupdatestatus", permissionMap.get("Apply Leave").getIsuserupdate());
                    bundle.putString("applydeletestatus", permissionMap.get("Apply Leave").getIsuserdelete());

                    bundle.putString("balanceviewstatus", permissionMap.get("Leave Balance").getIsuserview());
                    bundle.putString("balanceupdatestatus", permissionMap.get("Leave Balance").getIsuserupdate());
                    bundle.putString("balancedeletestatus", permissionMap.get("Leave Balance").getIsuserdelete());

                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 21;

                } else if (position == 6) {
//                     && permissionMap.get("View Marks").getStatus().equalsIgnoreCase("true")
                    fragment = new ViewMarksFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 21;
                } else {
                    Utils.ping(getActivity(), "Access Denied");
                }

            }
        });
    }


    // CALL Staff Attendace API HERE
    private void callStaffApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStaffAttendace(getStaffDetail(), new retrofit.Callback<StaffAttendaceModel>() {

            @Override
            public void success(StaffAttendaceModel staffUser, Response response) {
                Utils.dismissDialog();
                if (staffUser == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffUser.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffUser.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (staffUser.getSuccess().equalsIgnoreCase("True")) {
                    List<FinalArrayStaffModel> staffArray = staffUser.getFinalArray();
                    for (int i = 0; i < staffArray.size(); i++) {
                        FinalArrayStaffModel studentObj = staffArray.get(i);
                        if (studentObj != null) {
                            fragmentStaffBinding.totalAbsentstaffCount.setText(studentObj.getStaffAbsent());
                            fragmentStaffBinding.totalLeavestaffCount.setText(studentObj.getStaffLeave());
                            fragmentStaffBinding.totalPresentstaffCount.setText(studentObj.getStaffPresent());
                            fragmentStaffBinding.totalStaffCount.setText(studentObj.getTotalStaff());
                        }
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

    private Map<String, String> getStaffDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Date", Datestr);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }
}

