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
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.ExpandableListAdapterGRstudentdetail;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentGrnoStudentDetailBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class GRNoStudentDetailFragment extends Fragment {

    private FragmentGrnoStudentDetailBinding fragmentGrnoStudentDetailBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    List<StudentAttendanceFinalArray> studentFullDetailArray;
    List<String> listDataHeader;
    private String flag;
    private int stuid;
    HashMap<String, ArrayList<StudentAttendanceFinalArray>> listDataChild;

    ExpandableListAdapterGRstudentdetail listAdapterGRstudentdetail;

    private int lastExpandedPosition = -1;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public GRNoStudentDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentGrnoStudentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_grno_student_detail, container, false);

        rootView = fragmentGrnoStudentDetailBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        flag = getArguments().getString("flag");
        stuid = getArguments().getInt("stuid");

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view1 = view.findViewById(R.id.header);
        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.studentdetail);

        initViews();
        setListners();
        callStaffApi();
    }

    public void initViews() {

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
//                if (flag.equalsIgnoreCase("1")) {

                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 58;

                getActivity().onBackPressed();
//                    fragment = new LeftDetailFragment();
//                    fragmentManager = getFragmentManager();
//                    fragmentManager.beginTransaction()
//                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
//                            .replace(R.id.frame_container, fragment).commit();
//                } else {
//                    fragment = new GRRegisterFragment();
//                    fragmentManager = getFragmentManager();
//                    fragmentManager.beginTransaction()
//                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
//                            .replace(R.id.frame_container, fragment).commit();
//                }
            }
        });
        fragmentGrnoStudentDetailBinding.lvExpStudentDetail.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    fragmentGrnoStudentDetailBinding.lvExpStudentDetail.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }

        });


    }

    // CALL Stuednt Full Detail API HERE
    private void callStaffApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStudentFullDetail(getGRRegisterDetail(), new retrofit.Callback<StudentAttendanceModel>() {

            @Override
            public void success(StudentAttendanceModel studentFullDetailModel, Response response) {
//                Utils.dismissDialog();
                if (studentFullDetailModel == null) {
                    Utils.dismissDialog();
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentFullDetailModel.getSuccess() == null) {
                    Utils.dismissDialog();
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentFullDetailModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.dismissDialog();
                    Utils.ping(mContext, getString(R.string.false_msg));
//                    if (studentFullDetailModel.getFinalArray().size() == 0) {
                        fragmentGrnoStudentDetailBinding.lvExpStudentDetail.setVisibility(View.GONE);
                        fragmentGrnoStudentDetailBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentGrnoStudentDetailBinding.txtNoRecords.setVisibility(View.VISIBLE);
//                    }
                    return;
                }
                if (studentFullDetailModel.getSuccess().equalsIgnoreCase("True")) {
                    if (studentFullDetailModel.getFinalArray().size() > 0) {
                        studentFullDetailArray = studentFullDetailModel.getFinalArray();
                        if (studentFullDetailArray != null) {
                            ArrayList<String> arraystu = new ArrayList<String>();
                            arraystu.add("Student Details");
                            arraystu.add("Transport Details");
                            arraystu.add("Father Details");
                            arraystu.add("Mother Details");
                            arraystu.add("Communication Details");
                            Log.d("array", "" + arraystu);

                            listDataHeader = new ArrayList<>();
                            listDataChild = new HashMap<>();

                            for (int i = 0; i < arraystu.size(); i++) {
                                Log.d("arraystu", "" + arraystu);
                                listDataHeader.add(arraystu.get(i));
                                Log.d("header", "" + listDataHeader);
                                ArrayList<StudentAttendanceFinalArray> row = new ArrayList<>();
                                for (int j = 0; j < studentFullDetailArray.size(); j++) {
//                                    if (String.valueOf(studentFullDetailArray.get(j).getStudent_ID()).equalsIgnoreCase(AppConfiguration.CheckStudentId)) {
                                        row.add(studentFullDetailArray.get(j));
//                                    }
                                }
                                Log.d("row", "" + row);
                                listDataChild.put(listDataHeader.get(i), row);
                                Log.d("child", "" + listDataChild);
                            }
                            listAdapterGRstudentdetail = new ExpandableListAdapterGRstudentdetail(getActivity(), listDataHeader, listDataChild);
                            fragmentGrnoStudentDetailBinding.lvExpStudentDetail.setAdapter(listAdapterGRstudentdetail);
                            Utils.dismissDialog();
                        }
                    } else {
                        Utils.dismissDialog();
                        fragmentGrnoStudentDetailBinding.txtNoRecords.setVisibility(View.VISIBLE);
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

//    private Map<String, String> getGRRegisterDetail() {
//        Map<String, String> map = new HashMap<>();
//        map.put("Year", AppConfiguration.FinalTermIdStr);
//        map.put("Grade", AppConfiguration.FinalStandardIdStr);
//        map.put("Section", AppConfiguration.FinalClassIdStr);
//        map.put("Status", AppConfiguration.FinalStatusStr);
//        return map;
//    }

    private Map<String, String> getGRRegisterDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("StudentID", String.valueOf(stuid));

        return map;
    }
}

