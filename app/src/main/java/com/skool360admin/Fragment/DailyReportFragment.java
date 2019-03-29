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

import java.util.ArrayList;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.StudentSubMenuAdapter;
import anandniketan.com.bhadajadmin.Model.IconHeaderModel;
import anandniketan.com.bhadajadmin.Model.PermissionDataModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.PrefUtils;
import anandniketan.com.bhadajadmin.databinding.FragmentDailyReportBinding;


public class DailyReportFragment extends Fragment {

    public String[] mThumbIds = {
            AppConfiguration.BASEURL_IMAGES + "Daily%20Report/" + "Transportation.png",
            AppConfiguration.BASEURL_IMAGES + "Daily%20Report/" + "Account.png",
            AppConfiguration.BASEURL_IMAGES + "Daily%20Report/" + "Admin.png",
            AppConfiguration.BASEURL_IMAGES + "Daily%20Report/" + "House%20Keeping.png",
            AppConfiguration.BASEURL_IMAGES + "Daily%20Report/" + "Information%20Technology.png",
            AppConfiguration.BASEURL_IMAGES + "Daily%20Report/" + "Hr%20Head.png",

    };
    public String[] mThumbNames = {"Transportation", "Account", "Admin", "House Keeping", "Information Technology", "HR Head"};

    private FragmentDailyReportBinding fragmentDailyReportBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private Map<String, PermissionDataModel.Detaill> permissionMap;
    private ArrayList<IconHeaderModel> newArr;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public DailyReportFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentDailyReportBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_daily_report, container, false);

        rootView = fragmentDailyReportBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.home_sname_txt);
        btnBack = view.findViewById(R.id.home_btnBack);
        btnMenu = view.findViewById(R.id.home_btnmenu);

        tvHeader.setText(R.string.dailyreport);

        newArr = new ArrayList<>();
        permissionMap = PrefUtils.getInstance(getActivity()).loadMap(getActivity(), "HR");

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
        AppConfiguration.position = 51;
        Glide.with(mContext)
                .load(AppConfiguration.BASEURL_IMAGES + "HR/" + "Daily%20Report.png")
                .into(fragmentDailyReportBinding.circleImageView);
        fragmentDailyReportBinding.dailyReportSubmenuGridView.setAdapter(new StudentSubMenuAdapter(mContext, newArr));

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
                fragment = new HRFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentDailyReportBinding.dailyReportSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 && permissionMap.get("Transportation").getStatus().equalsIgnoreCase("true")) {
                    fragment = new HrTrasportationFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("viewstatus", permissionMap.get("Transportation").getIsuserview());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 54;

                } else if (position == 1 && permissionMap.get("Account").getStatus().equalsIgnoreCase("true")) {
                    fragment = new HrAccountFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("viewstatus", permissionMap.get("Account").getIsuserview());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 54;

                } else if (position == 2 && permissionMap.get("Admin").getStatus().equalsIgnoreCase("true")) {
                    fragment = new HrAdminFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("viewstatus", permissionMap.get("Admin").getIsuserview());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 54;

                } else if (position == 3 && permissionMap.get("House Keeping").getStatus().equalsIgnoreCase("true")) {
                    fragment = new HrHouseKeepingFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("viewstatus", permissionMap.get("House Keeping").getIsuserview());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 54;

                }
                else if (position == 4 && permissionMap.get("Information Technology").getStatus().equalsIgnoreCase("true")) {
                    fragment = new HrInformationtechnologyFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("viewstatus", permissionMap.get("Information Technology").getIsuserview());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 54;

                } else if (position == 5 && permissionMap.get("HR Head").getStatus().equalsIgnoreCase("true")) {
                    fragment = new HrHeadFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("viewstatus", permissionMap.get("HR Head").getIsuserview());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 54;
                }
            }
        });
    }
}

