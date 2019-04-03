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
import anandniketan.com.skool360.Adapter.VehicleRouteDetailListAdapter;
import anandniketan.com.skool360.Model.Transport.FinalArrayTransportChargesModel;
import anandniketan.com.skool360.Model.Transport.TransportChargesModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentVehicleRouteBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class VehicleRouteFragment extends Fragment {

    List<FinalArrayTransportChargesModel> finalArrayVehicleRouteModelList;
    VehicleRouteDetailListAdapter vehicleRouteDetailListAdapter;
    private FragmentVehicleRouteBinding fragmentVehicleRouteBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    public VehicleRouteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentVehicleRouteBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_vehicle_route, container, false);

        rootView = fragmentVehicleRouteBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setListner();
        callRouteDetailApi();

        return rootView;
    }


    public void setListner() {
        fragmentVehicleRouteBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentVehicleRouteBinding.btnBack.setOnClickListener(new View.OnClickListener() {
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

    // CALL RoputeDetail API HERE
    private void callRouteDetailApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;

        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getVehicleRouteDetail(getVehicleRouteDetail(), new retrofit.Callback<TransportChargesModel>() {
            @Override
            public void success(TransportChargesModel vehicleRouteDetailModel, Response response) {
//                Utils.dismissDialog();
                if (vehicleRouteDetailModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (vehicleRouteDetailModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (vehicleRouteDetailModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentVehicleRouteBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentVehicleRouteBinding.vehicleRouteList.setVisibility(View.GONE);
                    fragmentVehicleRouteBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentVehicleRouteBinding.listHeader.setVisibility(View.GONE);
                    Utils.dismissDialog();
                    return;
                }
                if (vehicleRouteDetailModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayVehicleRouteModelList = vehicleRouteDetailModel.getFinalArray();
                    if (finalArrayVehicleRouteModelList != null) {
                        fragmentVehicleRouteBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentVehicleRouteBinding.vehicleRouteList.setVisibility(View.VISIBLE);
                        fragmentVehicleRouteBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        fragmentVehicleRouteBinding.listHeader.setVisibility(View.VISIBLE);

                        vehicleRouteDetailListAdapter = new VehicleRouteDetailListAdapter(mContext, finalArrayVehicleRouteModelList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        fragmentVehicleRouteBinding.vehicleRouteList.setLayoutManager(mLayoutManager);
                        fragmentVehicleRouteBinding.vehicleRouteList.setItemAnimator(new DefaultItemAnimator());
                        fragmentVehicleRouteBinding.vehicleRouteList.setAdapter(vehicleRouteDetailListAdapter);
                        Utils.dismissDialog();
                    } else {
                        fragmentVehicleRouteBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentVehicleRouteBinding.vehicleRouteList.setVisibility(View.GONE);
                        fragmentVehicleRouteBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentVehicleRouteBinding.listHeader.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
//                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getVehicleRouteDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

}

