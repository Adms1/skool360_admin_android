package anandniketan.com.skool360.Fragment.Fragment;

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
import android.widget.ExpandableListView;
import android.widget.Spinner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.FeesStructureExpandableListAdapter;
import anandniketan.com.skool360.Model.Account.AccountFeesStatusModel;
import anandniketan.com.skool360.Model.Account.FinalArrayAccountFeesModel;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentFeeStructureBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class FeeStructureFragment extends Fragment {

    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    HashMap<Integer, String> spinnerTermMap;
    String FinalTermIdStr;
    List<FinalArrayAccountFeesModel> finalArrayFeesStructureModelList;
    FeesStructureExpandableListAdapter feesStructureExpandableListAdapter;
    List<String> listDataHeader;
    HashMap<String, ArrayList<FinalArrayAccountFeesModel>> listDataChild;
    private FragmentFeeStructureBinding fragmentFeeStructureBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private int lastExpandedPosition = -1;

    public FeeStructureFragment() {
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentFeeStructureBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fee_structure, container, false);

        rootView = fragmentFeeStructureBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        callTermApi();
        setListner();


        return rootView;
    }


    public void setListner() {
        fragmentFeeStructureBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentFeeStructureBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new AccountFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentFeeStructureBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentFeeStructureBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentFeeStructureBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid;
                Log.d("FinalTermIdStr", FinalTermIdStr);
                callAccountFeesStructureApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentFeeStructureBinding.lvExpfeesstructure.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {


            @Override

            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    fragmentFeeStructureBinding.lvExpfeesstructure.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
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
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
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

        spinnerTermMap = new HashMap<Integer, String>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerTermMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentFeeStructureBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentFeeStructureBinding.termSpinner.setAdapter(adapterTerm);

    }

    // CALL AccountFeesStructure API HERE
    private void callAccountFeesStructureApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }
//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getAccountFeesStructureDetail(geFeesStructureDetail(), new retrofit.Callback<AccountFeesStatusModel>() {
            @Override
            public void success(AccountFeesStatusModel accountFeesStructureModel, Response response) {
//                Utils.dismissDialog();
                if (accountFeesStructureModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (accountFeesStructureModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (accountFeesStructureModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    if (accountFeesStructureModel.getFinalArray().size() == 0) {
                        fragmentFeeStructureBinding.lvExpfeesstructure.setVisibility(View.GONE);
                        fragmentFeeStructureBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentFeeStructureBinding.expHeader.setVisibility(View.GONE);
                    }
                    return;
                }
                if (accountFeesStructureModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayFeesStructureModelList = accountFeesStructureModel.getFinalArray();
                    if (finalArrayFeesStructureModelList.size() > 0) {
                        fragmentFeeStructureBinding.lvExpfeesstructure.setVisibility(View.VISIBLE);
                        fragmentFeeStructureBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentFeeStructureBinding.expHeader.setVisibility(View.VISIBLE);
                        fillExpLV();
                        feesStructureExpandableListAdapter = new FeesStructureExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
                        fragmentFeeStructureBinding.lvExpfeesstructure.setAdapter(feesStructureExpandableListAdapter);
                        Utils.dismissDialog();
                    } else {
                        fragmentFeeStructureBinding.txtNoRecords.setVisibility(View.VISIBLE);
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

    private Map<String, String> geFeesStructureDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Term_ID", FinalTermIdStr);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }


    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, ArrayList<FinalArrayAccountFeesModel>>();

        for (int i = 0; i < finalArrayFeesStructureModelList.size(); i++) {
            listDataHeader.add(finalArrayFeesStructureModelList.get(i).getStandard());
            Log.d("header", "" + listDataHeader);
            ArrayList<FinalArrayAccountFeesModel> row = new ArrayList<FinalArrayAccountFeesModel>();

            row.add(finalArrayFeesStructureModelList.get(i));
            Log.d("row", "" + row);
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }

    }
}

