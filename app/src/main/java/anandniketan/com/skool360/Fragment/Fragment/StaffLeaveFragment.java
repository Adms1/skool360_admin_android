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
import java.util.Map;
import java.util.Objects;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.StudentSubMenuAdapter;
import anandniketan.com.skool360.Model.IconHeaderModel;
import anandniketan.com.skool360.Model.PermissionDataModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.databinding.FragmentStaffLeaveBinding;

// Antra 29/01/2019
// Change main menu adpater

public class StaffLeaveFragment extends Fragment {

    public String[] mThumbIds = {AppConfiguration.BASEURL_IMAGES + "Staff%20Leave/" + "Leave%20Request.png", AppConfiguration.BASEURL_IMAGES + "Staff%20Leave/" + "Leave%20Balance.png",};
    public String[] mThumbNames = {"Leave Request", "Leave Balance"};
    private FragmentStaffLeaveBinding fragmentstaffleaveBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private Map<String, PermissionDataModel.Detaill> permissionMap;
    private ArrayList<IconHeaderModel> newArr;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public StaffLeaveFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentstaffleaveBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_staff_leave, container, false);

        rootView = fragmentstaffleaveBinding.getRoot();
        mContext = Objects.requireNonNull(getActivity()).getApplicationContext();

//        Bundle bundle = this.getArguments();
//        viewstatus = bundle.getString("viewstatus");
//        updatestatus = bundle.getString("updatestatus");
//        deletestatus = bundle.getString("deletestatus");
//
//        requeststatus = bundle.getString("requeststatus");
//        requestupdatestatus = bundle.getString("requestupdatestatus");
//        requestdeletestatus = bundle.getString("requestdeletestatus");
//
//        balancestatus = bundle.getString("balancestatus");
//        balanceupdatestatus = bundle.getString("balanceupdatestatus");
//        balancedeletestatus = bundle.getString("balancedeletestatus");


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        tvHeader = view.findViewById(R.id.home_sname_txt);
        btnBack = view.findViewById(R.id.home_btnBack);
        btnMenu = view.findViewById(R.id.home_btnmenu);

        tvHeader.setText(R.string.staffleave);

//        fragmentStudentViewInquiryBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_search_staff,container,false);
//        rootView =  fragmentStudentViewInquiryBinding.getRoot();

        initViews();
        setListners();
    }

    public void initViews() {
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 51;
        Glide.with(mContext).load(AppConfiguration.BASEURL_IMAGES + "HR/" + "Staff%20Leave.png").into(fragmentstaffleaveBinding.circleImageView);
        fragmentstaffleaveBinding.staffLeaveSubmenuGridView.setAdapter(new StudentSubMenuAdapter(mContext, newArr));

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
                if (fragmentManager != null) {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                }
            }
        });

        fragmentstaffleaveBinding.staffLeaveSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    fragment = new LeaveRequestFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "staff");
//                    bundle.putString("requeststatus", requeststatus);
//                    bundle.putString("requestupdatestatus", requestupdatestatus);
//                    bundle.putString("requestdeletestatus", requestdeletestatus);
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 52;

                } else if (position == 1) {
                    fragment = new LeaveBalanceFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("balancestatus", balancestatus);
//                    bundle.putString("balanceupdatestatus", balanceupdatestatus);
//                    bundle.putString("balancedeletestatus", balancedeletestatus);
//                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 52;
                }
            }
        });

    }


}

