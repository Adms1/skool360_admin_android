package anandniketan.com.bhadajadmin.Fragment.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.HeaderwiseStudentDetailAdapter;
import anandniketan.com.bhadajadmin.Model.MIS.HeadwiseStudent;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiClient;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.SpacesItemDecoration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.Utility.WebServices;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */

//created by Antra 8/1/2019

public class HeadCollectionStudentFragment extends Fragment {

    RecyclerView rvList;
    Context mContext;
    ProgressBar progressBar;
    LinearLayout llFinance;
    private HeaderwiseStudentDetailAdapter misFinanceReportAdapter;
    private TextView tvNoRecord;
    private String studentID, termId, stndrdID;
    private Button btnBack, btnMenu;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    private ArrayList<HeadwiseStudent.Finalarray> financedataValues;

    public HeadCollectionStudentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_head_collection_student, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContext = getActivity().getApplicationContext();
        rvList = view.findViewById(R.id.headwisestudent_rv_finance_list);
        progressBar = view.findViewById(R.id.headwisestudent_progress_finance);
        tvNoRecord = view.findViewById(R.id.tv_no_records_of_finance);
        llFinance = view.findViewById(R.id.headwisestudent_linear_finance_recyler_grid);
        btnBack = view.findViewById(R.id.headwisestudent_btnBack);
        btnMenu = view.findViewById(R.id.headwisestudent_btnmenu);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new HeadWiseFeeCollectionDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", "Term 2 Total Fees");
                bundle.putString("requestType", "TotalFeesTerm2Count");
                bundle.putString("StndrdID", stndrdID);
                bundle.putString("TermID", termId);
//                        bundle.putString("countdata",holder.total_txt.getText().toString());
//                        bundle.putString("Date",AppConfiguration.staffDate);
//                        bundle.putString("deptID",String.valueOf(dataValues.get(position).getDepartmentID()));
                fragment.setArguments(bundle);
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 67;

                AppConfiguration.ReverseTermDetailId = "";
                fragment = new HeadWiseFeeCollectionDetailFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });


        try {
            Bundle bundle = this.getArguments();
            stndrdID = bundle.getString("StandardID");
            studentID = bundle.getString("StudentID");
            termId = bundle.getString("TermID");
        } catch (Exception e) {
            e.printStackTrace();
        }

        callFinanceMISDataApi(studentID, termId);
    }

    private void callFinanceMISDataApi(String studentId, String termId) {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
//        fragmentMisBinding.LLFinance.setVisibility(View.GONE);
        // Utils.showDialog(getActivity());
//        ApiHandler.getApiService().getHeadWiseFeesCollectionStudent(getFinanceListParams(studentId, term),new retrofit.Callback<MISFinanaceModel>() {
        WebServices apiService =
                ApiClient.getClient().create(WebServices.class);
        Call<HeadwiseStudent> call = apiService.getHeadWiseFeesCollectionStudent("http://192.168.1.8:8086/MobileApp_Service.asmx/GetHeadDetailByStudentID?StudentID=" + studentId + "&Term=" + termId);

        call.enqueue(new Callback<HeadwiseStudent>() {

            @Override
            public void onResponse(Call<HeadwiseStudent> call, retrofit2.Response<HeadwiseStudent> response) {
                //Utils.dismissDialog();
                progressBar.setVisibility(View.GONE);
                HeadwiseStudent staffSMSDataModel = response.body();
                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    progressBar.setVisibility(View.GONE);
                    llFinance.setVisibility(View.GONE);
                    tvNoRecord.setVisibility(View.VISIBLE);
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {

                    try {

                        progressBar.setVisibility(View.GONE);
                        llFinance.setVisibility(View.VISIBLE);
                        tvNoRecord.setVisibility(View.GONE);


//                        financedataValues = staffSMSDataModel.getFinalArray();

                        misFinanceReportAdapter = new HeaderwiseStudentDetailAdapter(getActivity(), financedataValues);
                        rvList.setLayoutManager(new GridLayoutManager(getActivity(), financedataValues.size(), OrientationHelper.HORIZONTAL, false));
                        rvList.addItemDecoration(new SpacesItemDecoration(0));
                        rvList.setAdapter(misFinanceReportAdapter);

                        try {
                            Gson gson = new Gson();
                            String json = gson.toJson(staffSMSDataModel);

                            JSONObject dataObject = new JSONObject(json);

                            JSONArray dataArray = dataObject.getJSONArray("CountData");

                            JSONObject innerdataObject = null;

                            for (int countArray = 0; countArray < dataArray.length(); countArray++) {

                                innerdataObject = dataArray.getJSONObject(countArray);

                                Iterator<String> dataKeys = innerdataObject.keys();

//                                dataValues1 = new ArrayList<String>();
//                                keyValues = new ArrayList<String>();
//
//
//                                while (dataKeys.hasNext()) {
//                                    String key = dataKeys.next();
//                                    keyValues.add(key);
//                                }
                            }

//                            for(int count  = 0; count<keyValues.size();count++){
//                                String value = innerdataObject.optString(keyValues.get(count));
//                                dataValues1.add(value);
//                            }
//
//                            misStaffListAdapter = new MISStaffListAdapter(getActivity(),dataValues1,keyValues);
//                            AppConfiguration.TAG = "Finance";
//                            fragmentMisBinding.rvFinanceList2.setLayoutManager(new LinearLayoutManager(getActivity()));
//                            fragmentMisBinding.rvFinanceList2.setAdapter(misStaffListAdapter);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

//                        try {
//                            if(isAdded()) {
//                                if(isRequireCallNestedAPI || isFirmtimeLoad) {
//                                    // callAccounttMISDataApi("Account");
//                                    //callNAMISDataApi("New Addmission");
//
//                                    callStudentMISDataApi("Student",fragmentMisBinding.studentDateBtn.getText().toString());
//
//                                }
//                            }
//                        }catch (Exception ex){
//                            ex.printStackTrace();
//                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<HeadwiseStudent> call, Throwable t) {
                t.printStackTrace();
                t.getMessage();
                progressBar.setVisibility(View.GONE);
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });
    }

}
