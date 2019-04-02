package anandniketan.com.skool360.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.LeaveBalanceListAdapter;
import anandniketan.com.skool360.Model.LeaveModel;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentLeaveBalanceBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LeaveBalanceFragment extends Fragment {


    private FragmentLeaveBalanceBinding fragmentMyLeaveBalanceBinding;

    private Context mContext;
    private List<FinalArrayGetTermModel> finalArrayGetTermModels;
    private HashMap<Integer, String> spinnerTermMap;
    private List<LeaveModel.FinalArray> dataList;
    private LeaveBalanceListAdapter leaveBalanceListAdapter;
    private String finalTermIdStr = "0";
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public LeaveBalanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();

        fragmentMyLeaveBalanceBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_leave_balance, container, false);

        return fragmentMyLeaveBalanceBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view1 = view.findViewById(R.id.header);
        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.leave_balance);

        setListners();
        callTermApi();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
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
            public void onClick(View v) {
                fragment = new StaffLeaveFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentMyLeaveBalanceBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentMyLeaveBalanceBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentMyLeaveBalanceBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                finalTermIdStr = getid;
                Log.d("FinalTermIdStr", finalTermIdStr);

                callLeaveBalanceApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private void callTermApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTerm(getTermDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<TermModel>() {
            @Override
            public void success(TermModel termModel, Response response) {
                Utils.dismissDialog();
                if (termModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayGetTermModels = termModel.getFinalArray();
                    if (finalArrayGetTermModels != null) {
                        fillTermSpinner();


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

    private Map<String, String> getTermDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    // CALL Term API HERE
    private void callLeaveBalanceApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().leaveBalance(getDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<LeaveModel>() {
            @Override
            public void success(LeaveModel termModel, Response response) {
                Utils.dismissDialog();
                if (termModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMyLeaveBalanceBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentMyLeaveBalanceBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMyLeaveBalanceBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (termModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMyLeaveBalanceBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentMyLeaveBalanceBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMyLeaveBalanceBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentMyLeaveBalanceBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentMyLeaveBalanceBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMyLeaveBalanceBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
                    dataList = termModel.getFinalArray();
                    if (dataList != null && dataList.size() > 0) {
                        fragmentMyLeaveBalanceBinding.lvExpHeader.setVisibility(View.VISIBLE);
                        fragmentMyLeaveBalanceBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        fragmentMyLeaveBalanceBinding.txtNoRecords.setVisibility(View.GONE);
                        leaveBalanceListAdapter = new LeaveBalanceListAdapter(getActivity(), dataList);
                        fragmentMyLeaveBalanceBinding.leaveList.setLayoutManager(new LinearLayoutManager(getActivity()));
                        fragmentMyLeaveBalanceBinding.leaveList.setAdapter(leaveBalanceListAdapter);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                fragmentMyLeaveBalanceBinding.lvExpHeader.setVisibility(View.GONE);
                fragmentMyLeaveBalanceBinding.recyclerLinear.setVisibility(View.GONE);
                fragmentMyLeaveBalanceBinding.txtNoRecords.setVisibility(View.VISIBLE);
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("TermID", finalTermIdStr);
        return map;
    }

    public void fillTermSpinner() {
        ArrayList<Integer> TermId = new ArrayList<Integer>();
        for (int i = 0; i < finalArrayGetTermModels.size(); i++) {
            TermId.add(finalArrayGetTermModels.get(i).getTermId());
        }
        ArrayList<String> Term = new ArrayList<String>();
        for (int j = 0; j < finalArrayGetTermModels.size(); j++) {
            Term.add(finalArrayGetTermModels.get(j).getTerm());
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerTermMap = new HashMap<>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerTermMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentMyLeaveBalanceBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentMyLeaveBalanceBinding.termSpinner.setAdapter(adapterTerm);
        finalTermIdStr = spinnerTermMap.get(0);
        callLeaveBalanceApi();
    }

}
