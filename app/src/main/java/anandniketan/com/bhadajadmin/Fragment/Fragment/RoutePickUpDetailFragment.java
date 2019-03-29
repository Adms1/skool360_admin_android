package anandniketan.com.bhadajadmin.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.RoutePickupPointDetailAdapter;
import anandniketan.com.bhadajadmin.Model.Transport.FinalArrayTransportChargesModel;
import anandniketan.com.bhadajadmin.Model.Transport.PickupPointDetailModel;
import anandniketan.com.bhadajadmin.Model.Transport.TransportChargesModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentRoutePickUpDetailBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class RoutePickUpDetailFragment extends Fragment {

    public RoutePickUpDetailFragment() {
    }

    private FragmentRoutePickUpDetailBinding fragmentRoutePickUpDetailBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    List<FinalArrayTransportChargesModel> finalArrayRouteDetailModelList;
    List<PickupPointDetailModel> pickupPointDetailModelList;
    HashMap<Integer, String> spinnerRouteMap;
    String FinalRouteName;
    RoutePickupPointDetailAdapter routePickupPointDetailAdapter;
    List<PickupPointDetailModel> array;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentRoutePickUpDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_route_pick_up_detail, container, false);

        rootView = fragmentRoutePickUpDetailBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setListner();
        callRouteDetailApi();

        return rootView;
    }


    public void setListner() {
        fragmentRoutePickUpDetailBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentRoutePickUpDetailBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new TransportFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentRoutePickUpDetailBinding.routeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentRoutePickUpDetailBinding.routeSpinner.getSelectedItem().toString();
                String getid = spinnerRouteMap.get(fragmentRoutePickUpDetailBinding.routeSpinner.getSelectedItemPosition());

                Log.d("routevalue", name + " " + getid);
//                FinalRouteIdStr = getid.toString();
//                Log.d("FinalRouteIdStr", FinalRouteIdStr);
                FinalRouteName = name;
                Log.d("FinalRouteName", FinalRouteName);
                fillrouteList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        ApiHandler.getApiService().getRouteDetail(getRouteDetail(), new retrofit.Callback<TransportChargesModel>() {
            @Override
            public void success(TransportChargesModel routeDetail, Response response) {
//                Utils.dismissDialog();
                if (routeDetail == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (routeDetail.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (routeDetail.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (routeDetail.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayRouteDetailModelList = routeDetail.getFinalArray();
                    if (finalArrayRouteDetailModelList != null) {
                        pickupPointDetailModelList = finalArrayRouteDetailModelList.get(0).getPickupPointDetail();
                        fillRouteSpinner();
                        Utils.dismissDialog();
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

    private Map<String, String> getRouteDetail() {
        Map<String, String> map = new HashMap<>();

        return map;
    }


    public void fillRouteSpinner() {

        ArrayList<Integer> RouteId = new ArrayList<Integer>();
        for (int i = 0; i < finalArrayRouteDetailModelList.size(); i++) {
            RouteId.add(finalArrayRouteDetailModelList.get(i).getRouteID());
        }
        ArrayList<String> Route = new ArrayList<String>();
        for (int j = 0; j < finalArrayRouteDetailModelList.size(); j++) {
            Route.add(finalArrayRouteDetailModelList.get(j).getRoute());
        }

        String[] spinnerrouteIdArray = new String[RouteId.size()];

        spinnerRouteMap = new HashMap<Integer, String>();
        for (int i = 0; i < RouteId.size(); i++) {
            spinnerRouteMap.put(i, String.valueOf(RouteId.get(i)));
            spinnerrouteIdArray[i] = Route.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentRoutePickUpDetailBinding.routeSpinner);

            popupWindow.setHeight(spinnerrouteIdArray.length > 4 ? 500 : spinnerrouteIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerrouteIdArray);
        fragmentRoutePickUpDetailBinding.routeSpinner.setAdapter(adapterTerm);

    }

    public void fillrouteList() {
        array = new ArrayList<>();
        array.clear();
        for (int i = 0; i < finalArrayRouteDetailModelList.size(); i++) {
            if (FinalRouteName.equalsIgnoreCase(finalArrayRouteDetailModelList.get(i).getRoute())) {
                for (int j = 0; j < finalArrayRouteDetailModelList.get(i).getPickupPointDetail().size(); j++) {
                    array.add(finalArrayRouteDetailModelList.get(i).getPickupPointDetail().get(j));
                }
            }
        }
        if (array.size() != 0) {
            fragmentRoutePickUpDetailBinding.txtNoRecords.setVisibility(View.GONE);
            fragmentRoutePickUpDetailBinding.listHeader.setVisibility(View.VISIBLE);
            fragmentRoutePickUpDetailBinding.routeDetailList.setVisibility(View.VISIBLE);
            fragmentRoutePickUpDetailBinding.recyclerLinear.setVisibility(View.VISIBLE);
            routePickupPointDetailAdapter = new RoutePickupPointDetailAdapter(mContext, array);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            fragmentRoutePickUpDetailBinding.routeDetailList.setLayoutManager(mLayoutManager);
            fragmentRoutePickUpDetailBinding.routeDetailList.setItemAnimator(new DefaultItemAnimator());
            fragmentRoutePickUpDetailBinding.routeDetailList.setAdapter(routePickupPointDetailAdapter);
        } else {
            fragmentRoutePickUpDetailBinding.txtNoRecords.setVisibility(View.VISIBLE);
            fragmentRoutePickUpDetailBinding.listHeader.setVisibility(View.GONE);
            fragmentRoutePickUpDetailBinding.routeDetailList.setVisibility(View.GONE);
fragmentRoutePickUpDetailBinding.recyclerLinear.setVisibility(View.GONE);
        }
    }
}

