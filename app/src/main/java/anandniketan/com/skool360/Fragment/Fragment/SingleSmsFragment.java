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
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentSingleSmsBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SingleSmsFragment extends Fragment {

    String FinalSMSStr, FinalMobileNoStr;
    private FragmentSingleSmsBinding fragmentSingleSmsBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public SingleSmsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentSingleSmsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_single_sms, container, false);

        rootView = fragmentSingleSmsBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.singlesms);

        setListners();

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
                fragment = new SMSFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentSingleSmsBinding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinalSMSStr = fragmentSingleSmsBinding.messageEdt.getText().toString();
                FinalMobileNoStr = fragmentSingleSmsBinding.mobilenoEdt.getText().toString();

                if (!FinalSMSStr.equalsIgnoreCase("") && !FinalMobileNoStr.equalsIgnoreCase("")) {
                    callInsertSingleSMSDataApi();
                } else {
                    Utils.ping(mContext, getString(R.string.blank));
                }
            }
        });
        fragmentSingleSmsBinding.clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentSingleSmsBinding.mobilenoEdt.setText("");
                fragmentSingleSmsBinding.messageEdt.setText("");
            }
        });
    }


    // CALL InsertSingleSMSData API HERE
    private void callInsertSingleSMSDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().InsertSingleSMSData(InsertSingleSMSDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<InsertMenuPermissionModel>() {
            @Override
            public void success(InsertMenuPermissionModel insertsms, Response response) {
                Utils.dismissDialog();
                if (insertsms == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertsms.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertsms.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (insertsms.getSuccess().equalsIgnoreCase("True")) {
                    Utils.ping(mContext, "Message Sent Successfully");
                    fragmentSingleSmsBinding.mobilenoEdt.setText("");
                    fragmentSingleSmsBinding.messageEdt.setText("");
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

    private Map<String, String> InsertSingleSMSDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("SMS", FinalSMSStr);
        map.put("MobileNo", FinalMobileNoStr);
        return map;
    }
}

