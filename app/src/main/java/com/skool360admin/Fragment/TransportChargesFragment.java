package com.skool360admin.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.TransportChargesListAdapter;
import anandniketan.com.bhadajadmin.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.bhadajadmin.Model.Transport.FinalArrayTransportChargesModel;
import anandniketan.com.bhadajadmin.Model.Transport.TermModel;
import anandniketan.com.bhadajadmin.Model.Transport.TransportChargesModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentTransportChargesBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TransportChargesFragment extends Fragment {

    public TransportChargesFragment() {
    }

    private FragmentTransportChargesBinding fragmentTransportChargesBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    HashMap<Integer, String> spinnerTermMap;
    String FinalTermIdStr;
    List<FinalArrayTransportChargesModel> transportChargesModelsList;
    TransportChargesListAdapter transportChargesListAdapter;
    Calendar calendar;
    int Year;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentTransportChargesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_transport_charges, container, false);

        rootView = fragmentTransportChargesBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        callTermApi();
        setListner();


        return rootView;
    }


    public void setListner() {
        fragmentTransportChargesBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentTransportChargesBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new TransportFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentTransportChargesBinding.yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentTransportChargesBinding.yearSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentTransportChargesBinding.yearSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid.toString();
                Log.d("FinalTermIdStr", FinalTermIdStr);
                callTransportChargesApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // CALL Term API HERE
    private void callTermApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTerm(getTermDetail(), new retrofit.Callback<TermModel>() {
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

    // CALL Transport charges API HERE
    private void callTransportChargesApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }
//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTransportChargesDetail(getTransportChargesDetail(), new retrofit.Callback<TransportChargesModel>() {
            @Override
            public void success(TransportChargesModel transportChargesModel, Response response) {
//                Utils.dismissDialog();
                if (transportChargesModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (transportChargesModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (transportChargesModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    if (transportChargesModel.getFinalArray().size() == 0) {
                        fragmentTransportChargesBinding.transportChargesList.setVisibility(View.GONE);
                        fragmentTransportChargesBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentTransportChargesBinding.listHeader.setVisibility(View.GONE);
                        fragmentTransportChargesBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    }
                    return;
                }
                if (transportChargesModel.getSuccess().equalsIgnoreCase("True")) {
                    transportChargesModelsList = transportChargesModel.getFinalArray();
                    if (transportChargesModelsList.size() > 0) {
                        fragmentTransportChargesBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentTransportChargesBinding.transportChargesList.setVisibility(View.VISIBLE);
                        fragmentTransportChargesBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        fragmentTransportChargesBinding.listHeader.setVisibility(View.VISIBLE);
                        transportChargesListAdapter = new TransportChargesListAdapter(mContext, transportChargesModelsList);
                        fragmentTransportChargesBinding.transportChargesList.setAdapter(transportChargesListAdapter);
                    } else {
                        fragmentTransportChargesBinding.txtNoRecords.setVisibility(View.VISIBLE);
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

    private Map<String, String> getTransportChargesDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Term_ID", FinalTermIdStr);
        return map;
    }

    //Use for fill TermSpinner
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

        spinnerTermMap = new HashMap<Integer, String>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerTermMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentTransportChargesBinding.yearSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentTransportChargesBinding.yearSpinner.setAdapter(adapterTerm);

    }
}

