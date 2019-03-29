package com.skool360admin.Fragment;

import android.os.Bundle;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.TransportDetailAdpapter;
import anandniketan.com.bhadajadmin.Adapter.TransportExDetailAdpapter;
import anandniketan.com.bhadajadmin.Model.MIS.TransportMainModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiClient;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.Utility.WebServices;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */

//created by Antra 11/01/2019

public class MISStudentTransportDetailFragment extends Fragment {

    private Button btnMenu, btnBack;
    private TextView viewhead, tvTotal;
    private TextView tvHeader, tvNorecord;
    private String title, stdid, termid, requestType, count;
    private ProgressBar progressBar;
    private RecyclerView rvList;
    private LinearLayout llHeader, llHeader2;
    private TransportDetailAdpapter transportDetailAdpapter;
    private TransportExDetailAdpapter transportExDetailAdpapter;
    private ExpandableListView rvExp;
    private List<String> lvHeader;
    private int lastExpandedPosition = -1;
    private Fragment fragment;
    private FragmentManager fragmentManager = null;
    private HashMap<String, ArrayList<TransportMainModel.StudentDatum>> lvChild;
    private ArrayList<TransportMainModel.StudentDatum> transportMainArray;

    public MISStudentTransportDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transport_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppConfiguration.position = 66;
        AppConfiguration.firsttimeback = true;

        tvHeader = view.findViewById(R.id.transport_detail_textView3);
        btnBack = view.findViewById(R.id.transport_detail_btnBack);
        btnMenu = view.findViewById(R.id.transport_detail_btnmenu);
        rvList = view.findViewById(R.id.transDetail_rvList);
        progressBar = view.findViewById(R.id.transport_detail_loader);
        tvNorecord = view.findViewById(R.id.transport_detail_txtNoRecords);
        llHeader = view.findViewById(R.id.transport_misstudent_llHeader);
        llHeader2 = view.findViewById(R.id.transport_misstudent_llHeader2);
        viewhead = view.findViewById(R.id.transportd_view_header);
        rvExp = view.findViewById(R.id.transport_detail_expandlist);
        tvTotal = view.findViewById(R.id.trans_tv_txt);

        Bundle bundle = this.getArguments();
        title = bundle.getString("title");
        stdid = bundle.getString("stdid");
        requestType = bundle.getString("requestType");
        termid = bundle.getString("TermID");
        count = bundle.getString("count");

        if (!count.equalsIgnoreCase("0")) {
            tvTotal.setText("Total Students : " + count);
        } else {
            tvTotal.setVisibility(View.GONE);
        }

        rvExp.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    rvExp.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        if (requestType.equalsIgnoreCase("Personal")) {
            viewhead.setVisibility(View.GONE);
        } else {
            viewhead.setVisibility(View.VISIBLE);
        }

        tvHeader.setText(title);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppConfiguration.position = 58;
                AppConfiguration.firsttimeback = true;

                getActivity().onBackPressed();


//                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                fragment = new MISStudentTransportFragment();
//                fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
            }
        });

        rvList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        callTransportDetail();
    }

    private void callTransportDetail() {
        WebServices apiService = ApiClient.getClient().create(WebServices.class);
//
        Call<TransportMainModel> call = apiService.getTransportDetail(AppConfiguration.BASEURL + "GetMISTransport?RequestType=" + requestType + "&TermID=" + termid + "&StandardID=" + stdid);
        call.enqueue(new Callback<TransportMainModel>() {
            //
            @Override
            public void onResponse(Call<TransportMainModel> call, retrofit2.Response<TransportMainModel> response) {
                Utils.dismissDialog();
                if (response.body() == null) {
                    progressBar.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                    llHeader.setVisibility(View.GONE);
                    llHeader2.setVisibility(View.GONE);
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    tvNorecord.setVisibility(View.VISIBLE);
                    return;
                }
//
                if (response.body().getSuccess().equalsIgnoreCase("false")) {
                    progressBar.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                    Utils.ping(getActivity(), getString(R.string.false_msg));
                    llHeader.setVisibility(View.GONE);
                    llHeader2.setVisibility(View.GONE);
                    tvNorecord.setVisibility(View.VISIBLE);
                    return;
                }

                if (response.body().getFinalArray().get(0).getStudentdata() == null || !(response.body().getFinalArray().get(0).getStudentdata().size() > 0)) {
                    progressBar.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                    Utils.ping(getActivity(), getString(R.string.false_msg));
                    llHeader.setVisibility(View.GONE);
                    llHeader2.setVisibility(View.GONE);
                    tvNorecord.setVisibility(View.VISIBLE);
                    return;
                }

                if (response.body().getSuccess().equalsIgnoreCase("True")) {
//
                    progressBar.setVisibility(View.GONE);
                    rvList.setVisibility(View.VISIBLE);

                    lvHeader = new ArrayList<>();
                    lvChild = new HashMap<>();
//
                    if (response.body().getFinalArray().size() > 0) {

                        transportMainArray = response.body().getFinalArray().get(0).getStudentdata();

                        List<TransportMainModel.StudentDatum> newArr = transportMainArray;
//                        List<TransportMainModel.StudentDatum> newArr1 = staffSMSDataModel.getFinalArray().get(0).getStudentData();

//                        ArrayList<String> header = new ArrayList<>();
//                        HashMap<String, ArrayList<TransportMainModel.StudentDatum>> child = new HashMap<>();

                        for (int i = 0; i < newArr.size(); i++) {

                            lvHeader.add(newArr.get(i).getGrade() + "-" + newArr.get(i).getSection()
                                    + "|" + newArr.get(i).getStudentname()
                                    + "|" + newArr.get(i).getGrno() + "|" + newArr.get(i).getPhoneno());

                            ArrayList<TransportMainModel.StudentDatum> child_child = new ArrayList();
                            for (int j = 0; j < newArr.size(); j++) {

                                if (newArr.get(i).getGrno().equalsIgnoreCase(newArr.get(j).getGrno())) {

                                    child_child.add(newArr.get(j));
                                }

                                lvChild.put(lvHeader.get(i), child_child);
                            }
                        }

                        if (transportMainArray != null) {
                            tvNorecord.setVisibility(View.GONE);
                            if (requestType.equalsIgnoreCase("Personal")) {

                                llHeader.setVisibility(View.VISIBLE);
                                llHeader2.setVisibility(View.GONE);

                                rvExp.setVisibility(View.GONE);
                                rvList.setVisibility(View.VISIBLE);
                                transportDetailAdpapter = new TransportDetailAdpapter(getActivity(), transportMainArray);
                                rvList.setAdapter(transportDetailAdpapter);
                            } else {

                                llHeader.setVisibility(View.GONE);
                                llHeader2.setVisibility(View.VISIBLE);

                                rvExp.setVisibility(View.VISIBLE);
                                rvList.setVisibility(View.GONE);
                                transportExDetailAdpapter = new TransportExDetailAdpapter(getActivity(), lvHeader, lvChild);
                                rvExp.setAdapter(transportExDetailAdpapter);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TransportMainModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("headfee", t.toString());
            }
        });
    }
}
