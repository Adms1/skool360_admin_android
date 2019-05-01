package anandniketan.com.skool360.Fragment.fragment.staff;

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

import anandniketan.com.skool360.Model.HR.SearchStaffModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.activity.DashboardActivity;
import anandniketan.com.skool360.adapter.ExpandableStaffInquiryProfileAdapter;
import anandniketan.com.skool360.databinding.FragmentStaffProfileBinding;

public class StaffInquiryProfileFragment extends Fragment {

    List<SearchStaffModel.FinalArray> staffFullDetailArray;
    List<String> listDataHeader;
    HashMap<String, List<SearchStaffModel.FinalArray>> listDataChild;
    ExpandableStaffInquiryProfileAdapter listAdapterStudentFullDetail;
    private FragmentStaffProfileBinding fragmentStudentInquiryProfileBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private int lastExpandedPosition = -1;
    private String viewstatus;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public StaffInquiryProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStudentInquiryProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_staff_profile, container, false);

        rootView = fragmentStudentInquiryProfileBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 58;

        //callStaffApi();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view1 = view.findViewById(R.id.header);
        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.staff_detail);

        setListners();

        try {
            Bundle bundle = getArguments();
            staffFullDetailArray = bundle.getParcelableArrayList("dataList");
            viewstatus = bundle.getString("status");

            if (staffFullDetailArray != null) {
                ArrayList<String> arraystu = new ArrayList<String>();
                arraystu.add("Personal Details");
                //   arraystu.add("Transport Details");

                arraystu.add("Office Details");
                // arraystu.add("Communication Details");
                Log.d("array", "" + arraystu);

                listDataHeader = new ArrayList<>();
                listDataChild = new HashMap<>();

                for (int i = 0; i < arraystu.size(); i++) {
                    Log.d("arraystu", "" + arraystu);
                    listDataHeader.add(arraystu.get(i));
                    Log.d("header", "" + listDataHeader);
                    ArrayList<SearchStaffModel.FinalArray> row = new ArrayList<SearchStaffModel.FinalArray>();
                    for (int j = 0; j < staffFullDetailArray.size(); j++) {
                        row.add(staffFullDetailArray.get(j));
                    }
                    Log.d("row", "" + row);
                    listDataChild.put(listDataHeader.get(i), staffFullDetailArray);
                    Log.d("child", "" + listDataChild);
                }
                listAdapterStudentFullDetail = new ExpandableStaffInquiryProfileAdapter(getActivity(), listDataHeader, listDataChild);
                fragmentStudentInquiryProfileBinding.lvExpStudentDetail.setAdapter(listAdapterStudentFullDetail);
                Utils.dismissDialog();
            }
        } catch (Exception ex) {

            ex.printStackTrace();
        }

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

                getActivity().onBackPressed();


//                fragment = new SearchStaffFragment();
//                fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).add(R.id.frame_container, fragment).addToBackStack(null).commit();

//                fragment = new SearchStaffFragment();
//
//                fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction()
//                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
//                        .replace(R.id.frame_container, fragment).commit();
//                getActivity().onBackPressed();
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

}
