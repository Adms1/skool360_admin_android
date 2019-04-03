package anandniketan.com.skool360.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.VehicleDetailListAdapter;
import anandniketan.com.skool360.Model.Transport.FinalArrayTransportChargesModel;
import anandniketan.com.skool360.Model.Transport.TransportChargesModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentVehicleDetailBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class VehicleDetailFragment extends Fragment {

    List<FinalArrayTransportChargesModel> vehicleDetailList;
    VehicleDetailListAdapter vehicleDetailListAdapter;
    private FragmentVehicleDetailBinding fragmentVehicleDetailBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    public VehicleDetailFragment() {
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentVehicleDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_vehicle_detail, container, false);

        rootView = fragmentVehicleDetailBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        callVehicleDetailApi();
        setListner();


        return rootView;
    }


    public void setListner() {
        fragmentVehicleDetailBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentVehicleDetailBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new TransportFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

    }


    // CALL VehicleDetail API HERE
    private void callVehicleDetailApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getVehicleDetail(getVehicleDetail(), new retrofit.Callback<TransportChargesModel>() {
            @Override
            public void success(TransportChargesModel vehicleDetailModel, Response response) {
                Utils.dismissDialog();
                if (vehicleDetailModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (vehicleDetailModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (vehicleDetailModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentVehicleDetailBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentVehicleDetailBinding.vehicleDetailList.setVisibility(View.GONE);
                    fragmentVehicleDetailBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentVehicleDetailBinding.listHeader.setVisibility(View.GONE);

                    return;
                }
                if (vehicleDetailModel.getSuccess().equalsIgnoreCase("True")) {
                    vehicleDetailList = vehicleDetailModel.getFinalArray();
                    if (vehicleDetailList.size() != 0) {
                        fragmentVehicleDetailBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentVehicleDetailBinding.vehicleDetailList.setVisibility(View.VISIBLE);
                        fragmentVehicleDetailBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        fragmentVehicleDetailBinding.listHeader.setVisibility(View.VISIBLE);

                        vehicleDetailListAdapter = new VehicleDetailListAdapter(mContext, vehicleDetailList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        fragmentVehicleDetailBinding.vehicleDetailList.setLayoutManager(mLayoutManager);
                        fragmentVehicleDetailBinding.vehicleDetailList.setItemAnimator(new DefaultItemAnimator());
                        fragmentVehicleDetailBinding.vehicleDetailList.setAdapter(vehicleDetailListAdapter);

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

    private Map<String, String> getVehicleDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

}

