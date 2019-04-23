package anandniketan.com.skool360.Fragment.fragment.other;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Interface.onInboxRead;
import anandniketan.com.skool360.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.skool360.Model.Other.FinalArraySMSDataModel;
import anandniketan.com.skool360.Model.Other.GetStaffSMSDataModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.adapter.ExpandableListAdapterInbox;
import anandniketan.com.skool360.databinding.FragmentInboxBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class InboxFragment extends Fragment {

    List<FinalArraySMSDataModel> finalArrayBulkSMSModelList;
    ExpandableListAdapterInbox expandableListAdapterInbox;
    List<String> listDataHeader = new ArrayList<>();
    HashMap<String, List<FinalArraySMSDataModel>> listDataChild = new HashMap<>();
    String messageidstr, FromIdstr, ToIdstr, messageDatestr, messageSubjectstr, messageMessageLinestr;
    private FragmentInboxBinding fragmentInboxBinding;
    private View rootView;
    private Context mContext;

    public InboxFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentInboxBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_inbox, container, false);

        rootView = fragmentInboxBinding.getRoot();
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
            callGetInboxDataApi();
        }
        // execute your data loading logic.
    }

    public void setListners() {


    }


    // CALL GetInboxData API HERE
    private void callGetInboxDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getPTMTeacherStudentGetDetail(getInboxDataDetail(), new retrofit.Callback<GetStaffSMSDataModel>() {
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
                    fragmentInboxBinding.txtNoRecordsinbox.setVisibility(View.VISIBLE);
                    fragmentInboxBinding.lvExpinbox.setVisibility(View.GONE);
                    fragmentInboxBinding.inboxHeader.setVisibility(View.GONE);
                    return;
                }
                if (getBulkSMSDataModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayBulkSMSModelList = getBulkSMSDataModel.getFinalArray();
                    if (finalArrayBulkSMSModelList != null) {
                        fragmentInboxBinding.txtNoRecordsinbox.setVisibility(View.GONE);
                        fragmentInboxBinding.lvExpinbox.setVisibility(View.VISIBLE);
                        fragmentInboxBinding.inboxHeader.setVisibility(View.VISIBLE);
                        fillDataList();
                        expandableListAdapterInbox = new ExpandableListAdapterInbox(getActivity(), listDataHeader, listDataChild, new onInboxRead() {
                            @Override
                            public void readMessageStatus() {
                                ArrayList<String> array = expandableListAdapterInbox.getData();
                                Log.d("array", "" + array.size());
                                for (int i = 0; i < array.size(); i++) {
                                    String spiltvalue = array.get(i);
                                    Log.d("spiltvalue", spiltvalue);
                                    String value[] = spiltvalue.trim().split("\\|");

                                    messageidstr = value[0];
                                    FromIdstr = value[1];
                                    ToIdstr = value[2];
                                    messageDatestr = value[3];
                                    messageSubjectstr = value[4];
                                    messageMessageLinestr = value[5];
                                    Log.d("messageid", messageidstr);
                                }
                                callInsertInboxDetailApi();
                            }
                        });
                        fragmentInboxBinding.lvExpinbox.setAdapter(expandableListAdapterInbox);
                        Utils.dismissDialog();
                    } else {
                        fragmentInboxBinding.txtNoRecordsinbox.setVisibility(View.VISIBLE);
                        fragmentInboxBinding.lvExpinbox.setVisibility(View.GONE);
                        fragmentInboxBinding.inboxHeader.setVisibility(View.GONE);
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

    private Map<String, String> getInboxDataDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("UserID", "0");
        map.put("UserType", "Staff");
        map.put("MessgaeType", "Inbox");
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


    // CALL InsertInboxDetail API HERE
    private void callInsertInboxDetailApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().PTMTeacherStudentInsertDetail(getInsertInboxDetail(), new retrofit.Callback<InsertMenuPermissionModel>() {
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
                    callGetInboxDataApi();
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

    private Map<String, String> getInsertInboxDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("MessageID", messageidstr);
        map.put("FromID", FromIdstr);
        map.put("ToID", ToIdstr);
        map.put("MeetingDate", messageDatestr);
        map.put("SubjectLine", messageSubjectstr);
        map.put("Description", messageMessageLinestr);
        map.put("Flag", "Student");
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

}

