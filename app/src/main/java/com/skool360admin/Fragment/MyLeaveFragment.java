package com.skool360admin.Fragment;

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

import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.MyLeaveSubMenuAdapter;
import anandniketan.com.bhadajadmin.Model.PermissionDataModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.PrefUtils;
import anandniketan.com.bhadajadmin.databinding.FragmentMyLeaveBinding;


public class MyLeaveFragment extends Fragment {

    private FragmentMyLeaveBinding fragmentMyLeaveBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private String viewstatus, updatestatus, deletestatus, applyviewstatus, applyupdatestatus, applydeletestatus, balanceviewstatus, balanceupdatestatus, balancedeletestatus;

    private TextView tvHeader;
    private Button btnBack, btnMenu;
    private Map<String, PermissionDataModel.Detaill> permissionMap;

    public MyLeaveFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentMyLeaveBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_leave, container, false);

        rootView = fragmentMyLeaveBinding.getRoot();
        mContext = getActivity().getApplicationContext();


        permissionMap = PrefUtils.getInstance(getActivity()).loadMap(getActivity(), "Staff");

//        Bundle bundle = this.getArguments();
//        viewstatus = bundle.getString("viewstatus");
//        updatestatus = bundle.getString("updatestatus");
//        deletestatus = bundle.getString("deletestatus");
//
//        applyviewstatus = bundle.getString("applyviewstatus");
//        applyupdatestatus = bundle.getString("applyupdatestatus");
//        applydeletestatus = bundle.getString("applydeletestatus");
//
//        balanceviewstatus = bundle.getString("balanceviewstatus");
//        balanceupdatestatus = bundle.getString("balanceupdatestatus");
//        balancedeletestatus = bundle.getString("balancedeletestatus");

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.home_sname_txt);
        btnBack = view.findViewById(R.id.home_btnBack);
        btnMenu = view.findViewById(R.id.home_btnmenu);

        tvHeader.setText(R.string.my_leave);

        initViews();
        setListners();
    }


    public void initViews() {
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 21;
        Glide.with(mContext)
                .load(AppConfiguration.BASEURL_IMAGES + "Main/" + "My%20Leave.png")
                .into(fragmentMyLeaveBinding.circleImageView);
        fragmentMyLeaveBinding.staffMyLeaveSubmenuGridView.setAdapter(new MyLeaveSubMenuAdapter(mContext));

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
                fragment = new StaffFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentMyLeaveBinding.staffMyLeaveSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    fragment = new ApplyLeaveFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                   AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 22;
                } else if (position == 1) {
                    fragment = new MyLeaveBalanceFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 22;
                }
            }
        });
    }
}

