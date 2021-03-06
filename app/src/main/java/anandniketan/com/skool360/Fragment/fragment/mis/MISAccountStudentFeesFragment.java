package anandniketan.com.skool360.Fragment.fragment.mis;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Model.MIS.MISAccountModel;
import anandniketan.com.skool360.Model.MIS.MISStaffModel;
import anandniketan.com.skool360.Model.MIS.MISStudentModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.activity.DashboardActivity;
import anandniketan.com.skool360.adapter.MISAccountHeaderAdapter;
import anandniketan.com.skool360.adapter.MISDetailListAdapter;
import anandniketan.com.skool360.adapter.MISStudentAdapter;
import anandniketan.com.skool360.databinding.FragmentMisAccount2Binding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MISAccountStudentFeesFragment extends Fragment {

    private FragmentMisAccount2Binding fragmentMisDataBinding;
    private View rootView;
    private Context mContext;
    private String title = "", requestType = "", date = "", termID = "", subtitle = "", standardID = "", feesType = "", studentId = "";
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private List<MISStudentModel.StandardDatum> misStudentStandardDataList;
    private List<MISStudentModel.StudentDatum> misStudentDataList;
    private List<MISStudentModel.ANT> misStudentANTDataList;
    private List<MISStaffModel.StaffDatum> misStaffDataList;
    private List<MISStaffModel.ANT> misStaffANTDataList;
    private List<MISAccountModel.Datum> misAccountHeaderDataList;
    private List<MISAccountModel.ClassDatum> misAccountDataList;
    private List<MISAccountModel.Student> misAccountStudentDataList;
    private List<MISAccountModel.FinalArray> misAccountStudentFinalDataList;
    private MISStudentAdapter misStudentAdapter;
    private MISDetailListAdapter misDetailListAdapter;
    private TextView mTvInnerTitle, mTvInnerAttendanceStatus, mTvinnerGrno, mTvStudent, mTvDept, mTvCode;
    private View innerTitleView, innerListHeaderView;
    private String countdata = "", requestTitle = "";
    private MISAccountHeaderAdapter misAccountHeaderAdapter;
    private Bundle bundle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentMisDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mis_account2, container, false);

        rootView = fragmentMisDataBinding.getRoot();
        mContext = getActivity().getApplicationContext();


        try {
            bundle = this.getArguments();
            title = bundle.getString("title");
            requestType = bundle.getString("requestType");
            termID = bundle.getString("TermID");
            date = bundle.getString("Date");
            feesType = bundle.getString("feesType");

            try {
                studentId = bundle.getString("StudentID");
            } catch (Exception ex) {

            }


            try {
                standardID = bundle.getString("StandardID");
            } catch (Exception ex) {

            }

            try {
                countdata = bundle.getString("countdata");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                requestTitle = bundle.getString("requestTitle");
            } catch (Exception ex) {

                ex.printStackTrace();
            }

            try {
                subtitle = bundle.getString("subtitle");
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            if (title != null && !TextUtils.isEmpty(title)) {
                fragmentMisDataBinding.textView3.setText(title);
            }

            if (title.equalsIgnoreCase("Fees Details")) {


                fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.list_item_mis_account_student_list_header);
                innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();

                fragmentMisDataBinding.layoutStub1.getViewStub().setVisibility(View.GONE);


            }


        } catch (Exception ex) {
            ex.printStackTrace();

        }

        setListners();
        callAccountStudentMISDataApi();
        return fragmentMisDataBinding.getRoot();
    }

    public void setListners() {

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    bundle.putString("subtitle", AppConfiguration.Subtitle);
                    bundle.putString("countdata", AppConfiguration.Coundata);
                    bundle.putString("requestTitle", AppConfiguration.pageInnerTitle);
                    fragment = new MISAccountFeesDetailFragment();
                    fragment.setArguments(bundle);
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out).replace(R.id.frame_container, fragment).commit();
//                    AppConfiguration.firsttimeback = true;
//                    AppConfiguration.position = 66;
                    return true;
                }
                return false;
            }
        });

        fragmentMisDataBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("subtitle", AppConfiguration.Subtitle);
                bundle.putString("countdata", AppConfiguration.Coundata);
                bundle.putString("requestTitle", AppConfiguration.pageInnerTitle);
                fragment = new MISAccountFeesDetailFragment();
                fragment.setArguments(bundle);
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out).replace(R.id.frame_container, fragment).commit();
//                AppConfiguration.firsttimeback = true;
//                AppConfiguration.position = 66;

//                AppConfiguration.position = 65;
//                AppConfiguration.firsttimeback = true;
                //   getActivity().getSupportFragmentManager().popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);

//                AppConfiguration.ReverseTermDetailId = "";
//                AppConfiguration.firsttimeback = true;
//                AppConfiguration.position = 65;
//
//                Bundle bundle = new Bundle();
//                bundle.putString("title","Account");
//                bundle.putString("requestType",requestType);
//                bundle.putString("requestTitle",requestTitle);
//                bundle.putString("TermID",termID);
//                bundle.putString("Date",Utils.getTodaysDate());
//                bundle.putString("countdata",countdata);
//                fragment = new MISDataListFragment();
//                fragment.setArguments(bundle);
//                fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();


            }
        });
        fragmentMisDataBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
    }


    private void callAccountMISDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getMISAccountByStandard(getAccountParams(), new retrofit.Callback<MISAccountModel>() {
            @Override
            public void success(MISAccountModel staffSMSDataModel, Response response) {
                Utils.dismissDialog();

                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);

                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {


                    try {

                        misAccountStudentDataList = staffSMSDataModel.getStudent();
                        misAccountHeaderDataList = staffSMSDataModel.getData();

                        fragmentMisDataBinding.lvHeader2.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.lvHeader.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.recyclerLinear1.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.txtNoRecords.setVisibility(View.GONE);

                        if (countdata != null) {
                            fragmentMisDataBinding.tvTxt.setText(requestTitle + ": " + countdata);
                        }


//                            misAccountHeaderAdapter = new MISAccountHeaderAdapter(getActivity(),misAccountHeaderDataList,0);
//                            fragmentMisDataBinding.rvMisdataList1.setLayoutManager(new LinearLayoutManager(mContext));
//                            fragmentMisDataBinding.rvMisdataList1.setAdapter(misAccountHeaderAdapter);


                        misAccountHeaderAdapter = new MISAccountHeaderAdapter(getActivity(), misAccountHeaderDataList, 0);
                        fragmentMisDataBinding.rvMisdataList1.setLayoutManager(new LinearLayoutManager(mContext));
                        fragmentMisDataBinding.rvMisdataList1.setAdapter(misAccountHeaderAdapter);


                        misDetailListAdapter = new MISDetailListAdapter(getActivity(), misAccountStudentDataList, 4, requestType);
                        fragmentMisDataBinding.rvMisdataList.setLayoutManager(new LinearLayoutManager(mContext));
                        fragmentMisDataBinding.rvMisdataList.setAdapter(misDetailListAdapter);


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                fragmentMisDataBinding.txtNoRecords.setText(error.getMessage());
                //Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }


    private Map<String, String> getAccountParams() {
        Map<String, String> map = new HashMap<>();
        map.put("TermID", termID);
        map.put("StandardID", standardID);
        map.put("RequestType", requestType);
        map.put("FeesType", feesType);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }


    private void callAccountStudentMISDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getMISAccountByStudent(getAccountStudentParams(), new retrofit.Callback<MISAccountModel>() {

            @Override
            public void success(MISAccountModel staffSMSDataModel, Response response) {
                Utils.dismissDialog();

                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);

                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {

                    try {

                        misAccountStudentFinalDataList = staffSMSDataModel.getFinalArray();
                        misAccountHeaderDataList = staffSMSDataModel.getData();

                        fragmentMisDataBinding.lvHeader2.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.lvHeader.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.recyclerLinear1.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.txtNoRecords.setVisibility(View.GONE);


                        if (requestTitle != null) {
                            fragmentMisDataBinding.tvTxt.setText(requestTitle);
                        }


                        fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                        fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);

                        misDetailListAdapter = new MISDetailListAdapter(mContext, misAccountStudentFinalDataList, 5, requestType);
                        fragmentMisDataBinding.rvMisdataList.setLayoutManager(new LinearLayoutManager(mContext));
                        fragmentMisDataBinding.rvMisdataList.setAdapter(misDetailListAdapter);


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                fragmentMisDataBinding.txtNoRecords.setText(error.getMessage());
                //Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }


    private Map<String, String> getAccountStudentParams() {
        Map<String, String> map = new HashMap<>();
        map.put("StudentID", studentId);
        map.put("Term", termID);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }
}
