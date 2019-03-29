package anandniketan.com.bhadajadmin.Fragment.Fragment;

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
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.StudentSubMenuAdapter;
import anandniketan.com.bhadajadmin.Fragment.Fragment.Student.GalleryFragment;
import anandniketan.com.bhadajadmin.Model.IconHeaderModel;
import anandniketan.com.bhadajadmin.Model.PermissionDataModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.PrefUtils;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentStudentBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class StudentFragment extends Fragment {

    public String[] mThumbIds = {
            AppConfiguration.BASEURL_IMAGES + "Student/" + "Search%20Student.png",
            AppConfiguration.BASEURL_IMAGES + "Student/" + "View%20Inquiry.png",
            AppConfiguration.BASEURL_IMAGES + "Student/" + "Student%20Transport.png",
            AppConfiguration.BASEURL_IMAGES + "Student/" + "Permission.png",
            AppConfiguration.BASEURL_IMAGES + "Student/" + "Attendance.png",
            AppConfiguration.BASEURL_IMAGES + "Student/" + "Left_Active.png",
            AppConfiguration.BASEURL_IMAGES + "Student/" + "New%20Register.png",
            AppConfiguration.BASEURL_IMAGES + "Student/" + "Announcement.png",
            AppConfiguration.BASEURL_IMAGES + "Student/" + "Circular.png",
            AppConfiguration.BASEURL_IMAGES + "Student/" + "Planner.png",
            AppConfiguration.BASEURL_IMAGES + "Student/" + "Gallery.png",
            AppConfiguration.BASEURL_IMAGES + "Student/" + "leave%20request.png",
            AppConfiguration.BASEURL_IMAGES + "Student/" + "suggestion.png",
    };
    public String[] mThumbNames = {"Search Student", "View Inquiry", "Student Transport",
            "Permission", "Attendance", "Left/Active", "New Register", "Announcement", "Circular", "Planner", "Gallery", "Leave Request", "Suggestion"};
//
//    public String[] mThumbNames = {"View Inquiry", "Attendance"};

    int Year, Month, Day;
    Calendar calendar;
    private FragmentStudentBinding fragmentStudentBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private String Datestr;
    private Map<String, PermissionDataModel.Detaill> permissionMap;
    private GridView rvList;
    private ArrayList<IconHeaderModel> newArr;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public StudentFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AppConfiguration.position = 1;
        AppConfiguration.firsttimeback = true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStudentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_student, container, false);

        rootView = fragmentStudentBinding.getRoot();
        mContext = Objects.requireNonNull(getActivity()).getApplicationContext();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newArr = new ArrayList<>();

        Utils.callPermissionDetail(getActivity(), PrefUtils.getInstance(getActivity()).getStringValue("StaffID", "0"));

        permissionMap = PrefUtils.getInstance(getActivity()).loadMap(getActivity(), "Student");

        tvHeader = view.findViewById(R.id.home_sname_txt);
        btnBack = view.findViewById(R.id.home_btnBack);
        btnMenu = view.findViewById(R.id.home_btnmenu);

        tvHeader.setText(R.string.student);

        rvList = view.findViewById(R.id.student_submenu_grid_view);

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
        AppConfiguration.position = 1;
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        Datestr = Utils.getTodaysDate();
        Log.d("TodayDate", Datestr);
        Glide.with(mContext)
                .load(AppConfiguration.BASEURL_IMAGES + "Main/" + "Student.png")
                .into(fragmentStudentBinding.circleImageView);
        rvList.setAdapter(new StudentSubMenuAdapter(mContext, newArr));
        callStudentApi();
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
                if (fragmentManager != null) {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                }
            }
        });

        fragmentStudentBinding.viewSummayTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 11;
                fragment = new AttendaceSummaryFragment();
                fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                }
            }
        });

        fragmentStudentBinding.studentSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 && permissionMap.get("Search Student").getStatus().equalsIgnoreCase("true")) {

                    fragment = new SearchStudentFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("status", permissionMap.get("Search Student").getIsuserview());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).addToBackStack(null).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 1 && permissionMap.get("View Inquiry").getStatus().equalsIgnoreCase("true")) {
                    //add fragment in backstack.
                    fragment = new StudentViewInquiryFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("status", permissionMap.get("View Inquiry").getIsuserview());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                .replace(R.id.frame_container, fragment).addToBackStack(null).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                }
                else if (position == 2 && permissionMap.get("Student Transport").getStatus().equalsIgnoreCase("true")) {
                    fragment = new StudentTranspotFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("status", permissionMap.get("Student Transport").getIsuserview());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 3 && permissionMap.get("Permission").getStatus().equalsIgnoreCase("true")) {
                    fragment = new StudentPermissionFragment();
                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                }
                else if (position == 4 && permissionMap.get("Attendance").getStatus().equalsIgnoreCase("true")) {
                    fragment = new StudentAttendaneFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("status", permissionMap.get("Attendance").getIsuserview());
                    bundle.putString("updatestatus", permissionMap.get("Attendance").getIsuserupdate());
                    bundle.putString("deletestatus", permissionMap.get("Attendance").getIsuserdelete());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                }
                else if (position == 5 && permissionMap.get("Left/Active").getStatus().equalsIgnoreCase("true")) {
                    fragment = new LeftDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("status", permissionMap.get("Left/Active").getIsuserview());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 6 && permissionMap.get("New Register").getStatus().equalsIgnoreCase("true")) {
                    fragment = new GRRegisterFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("status", permissionMap.get("New Register").getIsuserview());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 7 && permissionMap.get("Announcement").getStatus().equalsIgnoreCase("true")) {
                    fragment = new AnnoucementListFragment();
                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 8 && permissionMap.get("Circular").getStatus().equalsIgnoreCase("true")) {
                    fragment = new CircularListFragment();
                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 9 && permissionMap.get("Planner").getStatus().equalsIgnoreCase("true")) {
                    fragment = new FragmentPlanner();
                    Bundle bundle = new Bundle();
                    bundle.putString("status", permissionMap.get("Planner").getIsuserview());
                    bundle.putString("updatestatus", permissionMap.get("Planner").getIsuserupdate());
                    bundle.putString("deletestatus", permissionMap.get("Planner").getIsuserdelete());

                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 10 && permissionMap.get("Gallery").getStatus().equalsIgnoreCase("true")) {
                    fragment = new GalleryFragment();

                    Bundle bundle = new Bundle();

                    bundle.putString("gallerydeletestatus", permissionMap.get("Gallery").getIsuserdelete());
                    bundle.putString("galleryupdatestatus", permissionMap.get("Gallery").getIsuserupdate());
                    bundle.putString("galleryviewstatus", permissionMap.get("Gallery").getIsuserview());

                    fragment.setArguments(bundle);

                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 11 && permissionMap.get("Leave Request").getStatus().equalsIgnoreCase("true")) {
                    fragment = new LeaveRequestFragment();

                    Bundle bundle = new Bundle();

                    bundle.putString("type", "student");
                    bundle.putString("leavedeletestatus", permissionMap.get("Leave Request").getIsuserdelete());
                    bundle.putString("leaveupdatestatus", permissionMap.get("Leave Request").getIsuserupdate());
                    bundle.putString("leaveviewstatus", permissionMap.get("Leave Request").getIsuserview());

                    fragment.setArguments(bundle);

                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 12 && permissionMap.get("Suggestion").getStatus().equalsIgnoreCase("true")) {
                    fragment = new SuggestionFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("type", "student");
                    bundle.putString("leavedeletestatus", permissionMap.get("Suggestion").getIsuserdelete());
                    bundle.putString("leaveupdatestatus", permissionMap.get("Suggestion").getIsuserupdate());
                    bundle.putString("leaveviewstatus", permissionMap.get("Suggestion").getIsuserview());

                    fragment.setArguments(bundle);

                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else {
                    Utils.ping(getActivity(), "Access Denied");
                }
            }
        });
    }

    // CALL Student Attendace API HERE
    private void callStudentApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStudentAttendace(getStudentDetail(), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel studentUser, Response response) {
                Utils.dismissDialog();
                if (studentUser == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentUser.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentUser.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (studentUser.getSuccess().equalsIgnoreCase("True")) {
                    List<StudentAttendanceFinalArray> studentArray = studentUser.getFinalArray();
                    for (int i = 0; i < studentArray.size(); i++) {
                        StudentAttendanceFinalArray studentObj = studentArray.get(i);
                        if (studentObj != null) {
                            fragmentStudentBinding.totalStudentCount.setText(studentObj.getTotalStudent());
                            fragmentStudentBinding.totalPresentstudentCount.setText(studentObj.getStudentPresent());
                            fragmentStudentBinding.totalAbsentstudentCount.setText(studentObj.getStudentAbsent());
                            fragmentStudentBinding.totalLeavestudentCount.setText(studentObj.getStudentLeave());
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

    private Map<String, String> getStudentDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Date", Datestr);
        return map;
    }

}

