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
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.ExpandableStudentInquiryProfileAdapter;
import anandniketan.com.skool360.Model.Student.StudentInquiryProfileModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentStudentInquiryProfileBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class StudentInquiryProfileFragment extends Fragment {

    List<StudentInquiryProfileModel.FinalArray> studentFullDetailArray;
    List<String> listDataHeader;
    HashMap<String, ArrayList<StudentInquiryProfileModel.FinalArray>> listDataChild;
    ExpandableStudentInquiryProfileAdapter listAdapterStudentFullDetail;
    private FragmentStudentInquiryProfileBinding fragmentStudentInquiryProfileBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private int lastExpandedPosition = -1;

    private Button btnMenu, btnBack;
    private TextView tvHeader;

    public StudentInquiryProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStudentInquiryProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_inquiry_profile, container, false);

        rootView = fragmentStudentInquiryProfileBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 58;

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view1 = view.findViewById(R.id.header);
        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.student_profile);

        setListners();
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
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 58;
//                fragment = new StudentViewInquiryFragment();
//                fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction()
//                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
//                        .replace(R.id.frame_container, fragment).commit();
                getActivity().onBackPressed();
            }
        });

        fragmentStudentInquiryProfileBinding.lvExpStudentDetail.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    fragmentStudentInquiryProfileBinding.lvExpStudentDetail.collapseGroup(lastExpandedPosition);
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
        ApiHandler.getApiService().getInquiryDataByID(getStudentFullDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<StudentInquiryProfileModel>() {

            @Override
            public void success(StudentInquiryProfileModel studentFullDetailModel, Response response) {
//                Utils.dismissDialog();
                if (studentFullDetailModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentFullDetailModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentFullDetailModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
                    return;
                }
                if (studentFullDetailModel.getSuccess().equalsIgnoreCase("True")) {
                    studentFullDetailArray = studentFullDetailModel.getFinalArray();
                    if (studentFullDetailArray != null) {
                        ArrayList<String> arraystu = new ArrayList<String>();
                        arraystu.add("Student Details");
                        //   arraystu.add("Transport Details");
                        arraystu.add("Father Details");
                        arraystu.add("Mother Details");
                        arraystu.add("For Office Use");
                        // arraystu.add("Communication Details");
                        Log.d("array", "" + arraystu);

                        listDataHeader = new ArrayList<>();
                        listDataChild = new HashMap<>();

                        for (int i = 0; i < arraystu.size(); i++) {
                            Log.d("arraystu", "" + arraystu);
                            listDataHeader.add(arraystu.get(i));
                            Log.d("header", "" + listDataHeader);
                            ArrayList<StudentInquiryProfileModel.FinalArray> row = new ArrayList<StudentInquiryProfileModel.FinalArray>();
                            for (int j = 0; j < studentFullDetailArray.size(); j++) {
                                row.add(studentFullDetailArray.get(j));
                            }
                            Log.d("row", "" + row);
                            listDataChild.put(listDataHeader.get(i), row);
                            Log.d("child", "" + listDataChild);
                        }
                        listAdapterStudentFullDetail = new ExpandableStudentInquiryProfileAdapter(getActivity(), listDataHeader, listDataChild);
                        fragmentStudentInquiryProfileBinding.lvExpStudentDetail.setAdapter(listAdapterStudentFullDetail);
                        Utils.dismissDialog();
                    }
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

    private Map<String, String> getStudentFullDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("StudentID", AppConfiguration.StudentId);
        return map;
    }

}
