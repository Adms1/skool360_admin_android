package anandniketan.com.skool360.Fragment.fragment.staff;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import anandniketan.com.skool360.Model.HR.LeaveDayModel;
import anandniketan.com.skool360.Model.LeaveModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.activity.DashboardActivity;
import anandniketan.com.skool360.adapter.LeaveBalanceAdapter;
import anandniketan.com.skool360.adapter.LeaveBalanceDetailAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyLeaveBalanceFragment extends Fragment {

    private TextView tvHeader, tvNoRecords;
    private Button btnBack, btnMenu;
    private ExpandableListView rvList;
    private RecyclerView rvTop;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private LinearLayout llHeader;
    private int whichclicked;
    private List<LeaveDayModel.FinalArray> finalArrayGetLeaveDays;
    private ArrayList<LeaveModel.FinalArray> finalArray;
    private ArrayList<LeaveModel.FinalArray> detailArrayList;
    private LeaveBalanceAdapter applyLeaveAdapter;
    private LeaveBalanceDetailAdapter leaveBalanceDetailAdapter;
    private List<String> listDataHeader;
    private int lastExpandedPosition = -1;
    private HashMap<String, ArrayList<LeaveModel.FinalArray>> listDataChild;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_leave_balance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);
        llHeader = view.findViewById(R.id.list_header);
        rvList = view.findViewById(R.id.leavebalance_list);
        tvNoRecords = view.findViewById(R.id.txtNoRecords);
        rvTop = view.findViewById(R.id.leavebalance_rvList);

        rvTop.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        tvHeader.setText(R.string.leave_balance);

        setListners();
        callGetApplyLeaveRequest();

    }

    private void setListners() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                AppConfiguration.position = 55;
                fragment = new MyLeaveFragment();
                fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                AppConfiguration.firsttimeback = true;

            }
        });

        rvList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    rvList.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
    }

    private void callGetApplyLeaveRequest() {
        if (!Utils.checkNetwork(Objects.requireNonNull(getActivity()))) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStaffLeaveRequest(getApplyLeaveParams(), new retrofit.Callback<LeaveModel>() {
            @Override
            public void success(LeaveModel leaveModel, Response response) {
                Utils.dismissDialog();
                if (leaveModel == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    llHeader.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                    tvNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (leaveModel.getSuccess() == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    llHeader.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                    tvNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (leaveModel.getSuccess().equalsIgnoreCase("false")) {
                    llHeader.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                    tvNoRecords.setVisibility(View.VISIBLE);
                    //                    finalArraySubjectModelList.clear();
//                    final ArrayAdapter adb = new ArrayAdapter(getActivity(), R.layout.spinner_layout, finalArraySubjectModelList);
//                    subjectSpinner.setAdapter(adb);
                    return;
                }
                if (leaveModel.getSuccess().equalsIgnoreCase("True")) {

                    llHeader.setVisibility(View.VISIBLE);
                    rvList.setVisibility(View.VISIBLE);
                    tvNoRecords.setVisibility(View.GONE);

                    detailArrayList = new ArrayList<>();

                    LeaveModel.FinalArray finalArray1 = new LeaveModel.FinalArray();
                    finalArray1.setCategory("Category");
                    finalArray1.setTotal("Total");
                    finalArray1.setUsed("Used");
                    finalArray1.setRemaining("Remaining");
                    detailArrayList.add(finalArray1);

                    finalArray = leaveModel.getFinalArray();
                    detailArrayList.addAll(leaveModel.getLeavedetails());

                    if (finalArray != null) {
                        tvNoRecords.setVisibility(View.GONE);
                        rvList.setVisibility(View.VISIBLE);

                        fillExpLV();
                        fillRvTop();

                        applyLeaveAdapter = new LeaveBalanceAdapter(getActivity(), listDataHeader, listDataChild);
                        rvList.setAdapter(applyLeaveAdapter);
                    } else {
                        tvNoRecords.setVisibility(View.VISIBLE);
                        rvList.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                llHeader.setVisibility(View.GONE);
                rvList.setVisibility(View.GONE);
                tvNoRecords.setVisibility(View.VISIBLE);
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(getActivity(), getString(R.string.something_wrong));
            }
        });
    }

    private Map<String, String> getApplyLeaveParams() {
        HashMap<String, String> map = new HashMap<>();
        map.put("StaffID", PrefUtils.getInstance(getActivity()).getStringValue("StaffID", "0"));
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        for (int i = 0; i < finalArray.size(); i++) {
            listDataHeader.add(finalArray.get(i).getCreatedate() + "|" + finalArray.get(i).getLeaveDays() + "|" + finalArray.get(i).getStatus() + "|" + finalArray.get(i).getHeadname() + "|" + finalArray.get(i).getReason());
            Log.d("header", "" + listDataHeader);
            ArrayList<LeaveModel.FinalArray> row = new ArrayList<>();
            row.add(finalArray.get(i));
            Log.d("row", "" + row);
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }
    }

    private void fillRvTop() {

        leaveBalanceDetailAdapter = new LeaveBalanceDetailAdapter(getActivity(), detailArrayList);
        rvTop.setAdapter(leaveBalanceDetailAdapter);
    }

}
