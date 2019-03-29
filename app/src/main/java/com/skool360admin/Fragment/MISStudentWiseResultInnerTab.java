package com.skool360admin.Fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.bhadajadmin.Adapter.MISStudentWiseTermWiseResultAdapter;
import anandniketan.com.bhadajadmin.Model.MIS.MIStudentWiseResultModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.databinding.FragmentMisstudentWiseResultInnerTabBinding;

public class MISStudentWiseResultInnerTab extends Fragment {

    private FragmentMisstudentWiseResultInnerTabBinding fragmentMISStudentWiseResultInnerTab;

    private static List<MIStudentWiseResultModel.Datum> dataList;
    private MISStudentWiseTermWiseResultAdapter misStudentWiseTermWiseResultAdapter;
    public MISStudentWiseResultInnerTab() {
        // Required empty public constructor
    }

    public static MISStudentWiseResultInnerTab newInstance(List<MIStudentWiseResultModel.Datum> data) {
        MISStudentWiseResultInnerTab fragment = new MISStudentWiseResultInnerTab();
        Bundle args = new Bundle();
        dataList = new ArrayList<>();
        dataList = data;
        args.putParcelableArrayList("dataList", (ArrayList<? extends Parcelable>) dataList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMISStudentWiseResultInnerTab = DataBindingUtil.inflate(inflater,R.layout.fragment_misstudent_wise_result_inner_tab,container,false);

        try {
            if (getArguments() != null) {
                dataList = getArguments().getParcelableArrayList("dataList");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        if(dataList != null){
            if(dataList.size() > 0){
                fragmentMISStudentWiseResultInnerTab.LLListHeader.setVisibility(View.VISIBLE);
                fragmentMISStudentWiseResultInnerTab.tvNoRecords.setVisibility(View.GONE);

                misStudentWiseTermWiseResultAdapter = new MISStudentWiseTermWiseResultAdapter(getActivity(),dataList,1);
                fragmentMISStudentWiseResultInnerTab.rvTermwiselist.setLayoutManager(new LinearLayoutManager(getActivity()));
                fragmentMISStudentWiseResultInnerTab.rvTermwiselist.setAdapter(misStudentWiseTermWiseResultAdapter);

            }else{
                fragmentMISStudentWiseResultInnerTab.LLListHeader.setVisibility(View.GONE);
                fragmentMISStudentWiseResultInnerTab.tvNoRecords.setVisibility(View.VISIBLE);
            }
        }

        return fragmentMISStudentWiseResultInnerTab.getRoot();

    }




}
