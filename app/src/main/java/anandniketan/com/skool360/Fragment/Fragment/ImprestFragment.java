package anandniketan.com.skool360.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import anandniketan.com.skool360.Adapter.ExpandbleListAdapterImprest;
import anandniketan.com.skool360.Model.Account.AccountFeesStatusModel;
import anandniketan.com.skool360.Model.Account.FinalArrayAccountFeesModel;
import anandniketan.com.skool360.Model.Account.FinalArrayStandard;
import anandniketan.com.skool360.Model.Account.GetStandardModel;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentImprestBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ImprestFragment extends Fragment {

    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    List<FinalArrayStandard> finalArrayStandardsList;
    HashMap<Integer, String> spinnerTermMap;
    HashMap<Integer, String> spinnerStandardMap;
    String FinalTermIdStr, FinalstandardIdStr = "";
    List<FinalArrayAccountFeesModel> finalArrayImprestDetailModelList;
    List<String> listDataHeader;
    HashMap<String, ArrayList<FinalArrayAccountFeesModel>> listDataChild;
    ExpandbleListAdapterImprest expandbleListAdapterImprest;
    private FragmentImprestBinding fragmentImprestBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private int lastExpandedPosition = -1;

    public ImprestFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentImprestBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_imprest, container, false);

        rootView = fragmentImprestBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setListners();
        callTermApi();
        callStandardApi();

        return rootView;
    }


    public void setListners() {
        fragmentImprestBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentImprestBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new AccountFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentImprestBinding.lvExpstudentimprest.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    fragmentImprestBinding.lvExpstudentimprest.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        fragmentImprestBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentImprestBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentImprestBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid;
                Log.d("FinalTermIdStr", FinalTermIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        fragmentImprestBinding.standardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentImprestBinding.standardSpinner.getSelectedItem().toString();
                String getid = spinnerStandardMap.get(fragmentImprestBinding.standardSpinner.getSelectedItemPosition());
                FinalstandardIdStr = getid;
                Log.d("FinalstandardIdStr", FinalstandardIdStr);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentImprestBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callStudentDiscountDetailApi();
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


    // CALL Standard API HERE
    private void callStandardApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStandardDetail(getStandardDetail(), new retrofit.Callback<GetStandardModel>() {
            @Override
            public void success(GetStandardModel standardModel, Response response) {
                Utils.dismissDialog();
                if (standardModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (standardModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayStandardsList = standardModel.getFinalArray();
                    if (finalArrayStandardsList != null) {
                        fillStandardSpinner();
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

    private Map<String, String> getStandardDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    // CALL Term API HERE
    private void callStudentDiscountDetailApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getImprestDetail(getImprestDetail(), new retrofit.Callback<AccountFeesStatusModel>() {
            @Override
            public void success(AccountFeesStatusModel getImprestDetailModel, Response response) {
//                Utils.dismissDialog();
                if (getImprestDetailModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getImprestDetailModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getImprestDetailModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.dismissDialog();
//                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentImprestBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentImprestBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentImprestBinding.lvExpstudentimprest.setVisibility(View.GONE);
                    return;
                }
                if (getImprestDetailModel.getSuccess().equalsIgnoreCase("True")) {

                    finalArrayImprestDetailModelList = getImprestDetailModel.getFinalArray();
                    if (finalArrayImprestDetailModelList != null) {
                        fragmentImprestBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentImprestBinding.lvExpHeader.setVisibility(View.VISIBLE);
                        fragmentImprestBinding.lvExpstudentimprest.setVisibility(View.VISIBLE);
                        fillExpLV();
                        expandbleListAdapterImprest = new ExpandbleListAdapterImprest(getActivity(), listDataHeader, listDataChild);
                        fragmentImprestBinding.lvExpstudentimprest.setAdapter(expandbleListAdapterImprest);
                        Utils.dismissDialog();
                    } else {
                        fragmentImprestBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentImprestBinding.lvExpHeader.setVisibility(View.GONE);
                        fragmentImprestBinding.lvExpstudentimprest.setVisibility(View.GONE);
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

    private Map<String, String> getImprestDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("TermID", FinalTermIdStr);
        map.put("Standard", FinalstandardIdStr);
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentImprestBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentImprestBinding.termSpinner.setAdapter(adapterTerm);

        FinalTermIdStr = spinnerTermMap.get(0);
    }

    public void fillStandardSpinner() {
        ArrayList<Integer> standardId = new ArrayList<Integer>();
        for (int i = 0; i < finalArrayStandardsList.size(); i++) {
            standardId.add(finalArrayStandardsList.get(i).getStandardID());
        }
        ArrayList<String> Standard = new ArrayList<String>();
        for (int j = 0; j < finalArrayStandardsList.size(); j++) {
            Standard.add(finalArrayStandardsList.get(j).getStandard());
        }

        String[] spinnerstandardIdArray = new String[standardId.size()];

        spinnerStandardMap = new HashMap<Integer, String>();
        for (int i = 0; i < standardId.size(); i++) {
            spinnerStandardMap.put(i, String.valueOf(standardId.get(i)));
            spinnerstandardIdArray[i] = Standard.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentImprestBinding.standardSpinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 500 : spinnerstandardIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        fragmentImprestBinding.standardSpinner.setAdapter(adapterTerm);

        FinalstandardIdStr = spinnerStandardMap.get(0);
        callStudentDiscountDetailApi();
    }

    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, ArrayList<FinalArrayAccountFeesModel>>();

        for (int i = 0; i < finalArrayImprestDetailModelList.size(); i++) {
            listDataHeader.add(finalArrayImprestDetailModelList.get(i).getName() + "|" +
                    finalArrayImprestDetailModelList.get(i).getgRNO() + "|" + finalArrayImprestDetailModelList.get(i).getStandard());
            Log.d("header", "" + listDataHeader);
            ArrayList<FinalArrayAccountFeesModel> row = new ArrayList<FinalArrayAccountFeesModel>();
            row.add(finalArrayImprestDetailModelList.get(i));
            Log.d("row", "" + row);
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }

    }


}

