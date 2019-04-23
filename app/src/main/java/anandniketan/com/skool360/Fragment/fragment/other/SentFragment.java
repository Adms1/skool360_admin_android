package anandniketan.com.skool360.Fragment.fragment.other;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Interface.onDeleteButton;
import anandniketan.com.skool360.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.skool360.Model.Other.FinalArraySMSDataModel;
import anandniketan.com.skool360.Model.Other.GetStaffSMSDataModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.adapter.ExpandableListAdapterSent;
import anandniketan.com.skool360.databinding.FragmentSentBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SentFragment extends Fragment {

    List<FinalArraySMSDataModel> finalArrayBulkSMSModelList;
    ExpandableListAdapterSent expandableListAdapterSent;
    List<String> listDataHeader = new ArrayList<>();
    HashMap<String, List<FinalArraySMSDataModel>> listDataChild = new HashMap<>();
    String finalMessageIdArray;
    private FragmentSentBinding fragmentSentBinding;
    private View rootView;
    private Context mContext;

    public SentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentSentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sent, container, false);

        rootView = fragmentSentBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        setUserVisibleHint(true);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            callGetSentDataApi();
        }
        // execute your data loading logic.
    }

    public void setListners() {


    }


    // CALL GetInboxData API HERE
    private void callGetSentDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getPTMTeacherStudentGetDetail(getSentDataDetail(), new retrofit.Callback<GetStaffSMSDataModel>() {
            @Override
            public void success(GetStaffSMSDataModel getBulkSMSDataModel, Response response) {
                Utils.dismissDialog();
                if (getBulkSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getBulkSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getBulkSMSDataModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
//                    if (getBulkSMSDataModel.getFinalArray().size()>0) {
                    fragmentSentBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentSentBinding.lvExpSent.setVisibility(View.GONE);
                    fragmentSentBinding.sentHeader.setVisibility(View.GONE);
//                    }
                    return;
                }
                if (getBulkSMSDataModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayBulkSMSModelList = getBulkSMSDataModel.getFinalArray();
                    if (finalArrayBulkSMSModelList != null) {
                        fragmentSentBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentSentBinding.lvExpSent.setVisibility(View.VISIBLE);
                        fragmentSentBinding.sentHeader.setVisibility(View.VISIBLE);
                        fillDataList();
                        expandableListAdapterSent = new ExpandableListAdapterSent(getActivity(), listDataHeader, listDataChild, new onDeleteButton() {
                            @Override
                            public void deleteSentMessage() {
                                ArrayList<String> id = new ArrayList<>();
                                String StudentArray = null;
                                ArrayList<String> array = expandableListAdapterSent.getMessageId();
                                for (int j = 0; j < array.size(); j++) {
                                    id.add(array.get(j));
                                }
                                finalMessageIdArray = String.valueOf(id);
                                finalMessageIdArray = finalMessageIdArray.substring(1, finalMessageIdArray.length() - 1);
                                if (!finalMessageIdArray.equalsIgnoreCase("")) {
                                    callDeleteSentDataDetailApi();
                                } else {
                                    Utils.ping(mContext, "something went wrong.");
                                }
                            }
                        });
                        fragmentSentBinding.lvExpSent.setAdapter(expandableListAdapterSent);
                        Utils.dismissDialog();
                    } else {
                        fragmentSentBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentSentBinding.lvExpSent.setVisibility(View.GONE);
                        fragmentSentBinding.sentHeader.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getSentDataDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("UserID", "0");
        map.put("UserType", "Staff");
        map.put("MessgaeType", "Sent");
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    //Use for fill the Inbox Detail List
    public void fillDataList() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<FinalArraySMSDataModel>>();

        for (int j = 0; j < finalArrayBulkSMSModelList.size(); j++) {
            listDataHeader.add(finalArrayBulkSMSModelList.get(j).getUserName() + "|" +
                    finalArrayBulkSMSModelList.get(j).getMeetingDate() + "|" +
                    finalArrayBulkSMSModelList.get(j).getSubjectLine() + "|" +
                    finalArrayBulkSMSModelList.get(j).getReadStatus());

            ArrayList<FinalArraySMSDataModel> rows = new ArrayList<FinalArraySMSDataModel>();
            rows.add(finalArrayBulkSMSModelList.get(j));
            listDataChild.put(listDataHeader.get(j), rows);
        }
    }

    // CALL DeleteSentDataDetail API HERE
    private void callDeleteSentDataDetailApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().PTMDeleteMeeting(getDeleteSentDataDetail(), new retrofit.Callback<InsertMenuPermissionModel>() {
            @Override
            public void success(InsertMenuPermissionModel insertinboxModel, Response response) {
                Utils.dismissDialog();
                if (insertinboxModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertinboxModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertinboxModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (insertinboxModel.getSuccess().equalsIgnoreCase("True")) {
                    callGetSentDataApi();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getDeleteSentDataDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("MessageID", finalMessageIdArray.trim());
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

}

