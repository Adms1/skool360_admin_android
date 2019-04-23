package anandniketan.com.skool360.Fragment.fragment.mis;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import anandniketan.com.skool360.Interface.ResponseCallBack;
import anandniketan.com.skool360.Model.MIS.MISStudentResultDataModel;
import anandniketan.com.skool360.Model.MIS.MIStudentWiseResultModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.activity.DashboardActivity;
import anandniketan.com.skool360.adapter.ExapandableSchoolResultAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MISStudentRangeDetailFragment extends Fragment implements ResponseCallBack {

    private ExpandableListView expandableListView;
    private TextView tvNoRecords, tvTxt, tvHeader;
    private Button btnBack, btnMenu;
    private List<MISStudentResultDataModel.FinalArray> finalArrayAnnouncementFinal;
    private ExapandableSchoolResultAdapter expandableSchoolResultAdapter;
    private ResponseCallBack responseCallBack;
    private List<String> listDataHeader;
    private View header;
    private HashMap<String, ArrayList<MISStudentResultDataModel.TermDatum>> listDataChild;
    private String stndrdID, classiD, termId, rangeId, count;
    private int lastExpandedPosition = -1;

    public MISStudentRangeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        responseCallBack = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_misstudent_range_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppConfiguration.position = 58;
        AppConfiguration.firsttimeback = true;

        expandableListView = view.findViewById(R.id.range_lvExpstudentlist);
        tvNoRecords = view.findViewById(R.id.range_tv_no_records);
        btnBack = view.findViewById(R.id.range_btnBack);
        btnMenu = view.findViewById(R.id.range_btnmenu);
        tvTxt = view.findViewById(R.id.range_tv_txt);
        header = view.findViewById(R.id.range_lvExp_header);
        tvHeader = view.findViewById(R.id.range_textView3);

        tvHeader.setText("Rangewise Students");

        try {
            Bundle bundle = this.getArguments();
            stndrdID = bundle.getString("StandardID");
            classiD = bundle.getString("ClassID");
            termId = bundle.getString("TermDetailID");
            rangeId = bundle.getString("RangeID");
            count = bundle.getString("count");

        } catch (Exception e) {
            e.printStackTrace();
        }

        tvTxt.setVisibility(View.GONE);
        header.setVisibility(View.GONE);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                String studentId = String.valueOf(finalArrayAnnouncementFinal.get(i).getStudentID());
                callChildItemAPI(studentId);
                if (expandableListView.isGroupExpanded(i)) {
                    expandableListView.collapseGroup(i);
                } else {
                    expandableListView.expandGroup(i);
                }

                return true;
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppConfiguration.position = 58;
                AppConfiguration.firsttimeback = true;

                getActivity().onBackPressed();

//                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {

                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }

        });

        callResultClassWise2();
    }

    private Map<String, String> getStudentwiseResultParams(String studentId) {
        Map<String, String> map = new HashMap<>();
        map.put("StudentID", studentId);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    private void callChildItemAPI(String studentId) {
        ApiHandler.getApiService().getResultStudentWise(getStudentwiseResultParams(studentId), new retrofit.Callback<MIStudentWiseResultModel>() {
            @Override
            public void success(MIStudentWiseResultModel studentFullDetailModel, Response response) {
                Utils.dismissDialog();
                if (studentFullDetailModel == null) {
                    responseCallBack.onFailure(getString(R.string.false_msg));

                    return;
                }
                if (studentFullDetailModel.getSuccess() == null) {

                    responseCallBack.onFailure(getString(R.string.false_msg));

                    return;
                }
                if (studentFullDetailModel.getSuccess().equalsIgnoreCase("False")) {

                    responseCallBack.onFailure(getString(R.string.false_msg));
                    return;
                }
                if (studentFullDetailModel.getSuccess().equalsIgnoreCase("True")) {

                    if (studentFullDetailModel.getFinalArray().size() > 0) {

                        responseCallBack.onResponse(studentFullDetailModel.getFinalArray());

                    } else {

                    }
                }
            }


            @Override
            public void failure(RetrofitError error) {
                error.getMessage();
                responseCallBack.onFailure(error.getMessage());
            }
        });


    }


    private Map<String, String> getClasswiseResultParams() {
        Map<String, String> map = new HashMap<>();
        map.put("TermDetailID", termId);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        if (stndrdID.equalsIgnoreCase("0")) {
            map.put("StandardID", "");
        } else {
            map.put("StandardID", stndrdID);
        }


        if (classiD.equalsIgnoreCase("0")) {
            map.put("ClassID", "");
        } else {
            map.put("ClassID", classiD);
        }

        map.put("RangeID", rangeId);
        return map;
    }

    private void callResultClassWise2() {
        if (!Utils.checkNetwork(getActivity())) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }
        Utils.showDialog(getActivity());
        //progressBar.setVisibility(View.VISIBLE);

        ApiHandler.getApiService().getResultData(getClasswiseResultParams(), new retrofit.Callback<MISStudentResultDataModel>() {
            @Override
            public void success(MISStudentResultDataModel studentFullDetailModel, Response response) {
                Utils.dismissDialog();
                if (studentFullDetailModel == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    tvTxt.setVisibility(View.GONE);
                    header.setVisibility(View.GONE);
                    tvNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (studentFullDetailModel.getSuccess() == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    tvTxt.setVisibility(View.GONE);
                    header.setVisibility(View.GONE);
                    tvNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (studentFullDetailModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(getActivity(), getString(R.string.false_msg));
                    Utils.dismissDialog();
                    tvTxt.setVisibility(View.GONE);
                    header.setVisibility(View.GONE);
                    tvNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (studentFullDetailModel.getSuccess().equalsIgnoreCase("True")) {
                    if (studentFullDetailModel.getFinalArray().size() > 0) {

                        Utils.dismissDialog();

                        tvTxt.setVisibility(View.VISIBLE);
                        tvTxt.setText("Total Students : " + count);
                        header.setVisibility(View.VISIBLE);

                        finalArrayAnnouncementFinal = studentFullDetailModel.getFinalArray();
                        if (finalArrayAnnouncementFinal != null) {
                            tvNoRecords.setVisibility(View.GONE);
                            fillExpLV();
                            expandableSchoolResultAdapter = new ExapandableSchoolResultAdapter(getActivity(), listDataHeader, listDataChild, responseCallBack);
                            expandableListView.setAdapter(expandableSchoolResultAdapter);
                        } else {
                            tvNoRecords.setVisibility(View.VISIBLE);
                        }

                    } else {
                        //   fragmentClasswiseResultBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
//                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                tvNoRecords.setVisibility(View.VISIBLE);
                Utils.ping(getActivity(), getString(R.string.something_wrong));
            }
        });

    }

    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        for (int i = 0; i < finalArrayAnnouncementFinal.size(); i++) {
            listDataHeader.add(finalArrayAnnouncementFinal.get(i).getStandard() + "|" + finalArrayAnnouncementFinal.get(i).getClassName() + "|" + finalArrayAnnouncementFinal.get(i).getName() + "|" + finalArrayAnnouncementFinal.get(i).getPercentage());
            Log.d("header", "" + listDataHeader);
            ArrayList<MISStudentResultDataModel.TermDatum> row = new ArrayList<MISStudentResultDataModel.TermDatum>();
            row.addAll(finalArrayAnnouncementFinal.get(i).getTermData());
            Log.d("row", "" + row);
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }

    }

    @Override
    public void onResponse(List<MIStudentWiseResultModel.FinalArray> data) {
        expandableSchoolResultAdapter.onResponse(data);
    }

    @Override
    public void onFailure(String errorMessage) {
        expandableSchoolResultAdapter.onFailure(errorMessage);
    }
}
