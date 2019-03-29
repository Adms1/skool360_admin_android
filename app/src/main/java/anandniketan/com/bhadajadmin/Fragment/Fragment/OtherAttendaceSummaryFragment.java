package anandniketan.com.bhadajadmin.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Adapter.ConsistentAbsentTeacherAdapter;
import anandniketan.com.bhadajadmin.Adapter.StandardwiseStudentAttendaceAdapter;
import anandniketan.com.bhadajadmin.Model.Staff.FinalArrayStaffModel;
import anandniketan.com.bhadajadmin.Model.Staff.StaffAttendaceModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentOtherAttendaceSummaryBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class OtherAttendaceSummaryFragment extends Fragment {

    public OtherAttendaceSummaryFragment() {
    }

    private FragmentOtherAttendaceSummaryBinding fragmentOtherAttendaceSummaryBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    int Year, Month, Day;
    Calendar calendar;
    private String Datestr;
    private StandardwiseStudentAttendaceAdapter standardwiseStudentAttendaceAdapter;
    private ConsistentAbsentTeacherAdapter consistentAbsentTeacherAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentOtherAttendaceSummaryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_other_attendace_summary, container, false);

        rootView = fragmentOtherAttendaceSummaryBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        initViews();

        return rootView;
    }

    public void initViews() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        Datestr = Utils.getTodaysDate();
        Log.d("TodayDate", Datestr);
        setUserVisibleHint(true);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            callStudentApi();
            callStaffApi();
        }
        // execute your data loading logic.
    }

    // CALL Staff Attendace API HERE
    private void callStaffApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStaffAttendace(getStaffDetail(), new retrofit.Callback<StaffAttendaceModel>() {


            @Override
            public void success(StaffAttendaceModel staffUser, Response response) {
//                Utils.dismissDialog();
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
                            fragmentOtherAttendaceSummaryBinding.absentstaffCountTxt.setText(studentObj.getStaffAbsent());
                            fragmentOtherAttendaceSummaryBinding.leavestaffCountTxt.setText(studentObj.getStaffLeave());
                            fragmentOtherAttendaceSummaryBinding.presentstaffCountTxt.setText(studentObj.getStaffPresent());
                            fragmentOtherAttendaceSummaryBinding.totalstaffCountTxt.setText(studentObj.getTotalStaff());
                        }
                    }
//                    if (staffUser.getFinalArray().size() > 0) {
//                        fragmentOtherAttendaceSummaryBinding.txtNoRecords.setVisibility(View.GONE);
//
//                        consistentAbsentTeacherAdapter = new ConsistentAbsentTeacherAdapter(mContext, staffUser);
//                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//                        fragmentOtherAttendaceSummaryBinding.consistentAbsentTeacherList.setLayoutManager(mLayoutManager);
//                        fragmentOtherAttendaceSummaryBinding.consistentAbsentTeacherList.setItemAnimator(new DefaultItemAnimator());
//                        fragmentOtherAttendaceSummaryBinding.consistentAbsentTeacherList.setAdapter(consistentAbsentTeacherAdapter);
//                    } else {
//                        fragmentOtherAttendaceSummaryBinding.txtNoRecords.setVisibility(View.VISIBLE);
//                    }
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

    private Map<String, String> getStaffDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Date", Datestr);
        return map;
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
                if (studentUser.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (studentUser.getSuccess().equalsIgnoreCase("True")) {

//                    List<FinalArrayStudentModel> studentArray = studentUser.getFinalArray();
//                    for (int i = 0; i < studentArray.size(); i++) {
//                        FinalArrayStudentModel studentObj = studentArray.get(i);
//                        if (studentObj != null) {
//                            fragmentOtherAttendaceSummaryBinding.absentstudentCountTxt.setText(studentObj.getStudentAbsent());
//                            fragmentOtherAttendaceSummaryBinding.leavestudentCountTxt.setText(studentObj.getStudentLeave());
//                            fragmentOtherAttendaceSummaryBinding.presentstudentCountTxt.setText(studentObj.getStudentPresent());
//                            fragmentOtherAttendaceSummaryBinding.totalstudentCountTxt.setText(studentObj.getTotalStudent());
//                        }
//                    }
//                    if (studentUser.getFinalArray().size() > 0) {
//                        fragmentOtherAttendaceSummaryBinding.txtNoRecords.setVisibility(View.GONE);
//                        standardwiseStudentAttendaceAdapter = new StandardwiseStudentAttendaceAdapter(mContext, studentUser);
//                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//                        fragmentOtherAttendaceSummaryBinding.standardwiseStudentAttendacelist.setLayoutManager(mLayoutManager);
//                        fragmentOtherAttendaceSummaryBinding.standardwiseStudentAttendacelist.setItemAnimator(new DefaultItemAnimator());
//                        fragmentOtherAttendaceSummaryBinding.standardwiseStudentAttendacelist.setAdapter(standardwiseStudentAttendaceAdapter);
//                    } else {
//                        fragmentOtherAttendaceSummaryBinding.txtNoRecords.setVisibility(View.VISIBLE);
//                    }
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
