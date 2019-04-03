package anandniketan.com.skool360.Fragment.Fragment;

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
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.StandardwiseCollectionListAdapter;
import anandniketan.com.skool360.Model.Account.AccountFeesCollectionModel;
import anandniketan.com.skool360.Model.Account.AccountFeesStatusModel;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentAccountSummaryBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AccountSummaryFragment extends Fragment {

    String FinalTermIdStr, FinalTermDetailIdStr;
    HashMap<Integer, String> spinnerTermDetailIdMap;
    HashMap<Integer, String> spinnerTermMap;
    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    List<AccountFeesCollectionModel> collectionModelList;
    List<AccountFeesCollectionModel> standardcollectionList;
    private FragmentAccountSummaryBinding fragmentAccountSummaryBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private StandardwiseCollectionListAdapter standardwisecollectionAdapter;

    public AccountSummaryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentAccountSummaryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_summary, container, false);

        rootView = fragmentAccountSummaryBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        initViews();
        setListner();

        return rootView;
    }

    public void initViews() {
        FinalTermIdStr = AppConfiguration.TermId;
        FinalTermDetailIdStr = AppConfiguration.TermDetailId;
        callTermApi();
        callAccountFeesStatusApi();
    }

    public void setListner() {
        fragmentAccountSummaryBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentAccountSummaryBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new AccountFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentAccountSummaryBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentAccountSummaryBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentAccountSummaryBinding.termSpinner.getSelectedItemPosition());

                Log.d("TermValue", name + "" + getid);
                FinalTermIdStr = getid;
                Log.d("FinalTermIdStr", "" + FinalTermIdStr);
                callAccountFeesStatusApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentAccountSummaryBinding.termDetailSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentAccountSummaryBinding.termDetailSpinner.getSelectedItem().toString();
                String getid = spinnerTermDetailIdMap.get(fragmentAccountSummaryBinding.termDetailSpinner.getSelectedItemPosition());

                Log.d("TermDetailValue", name + "" + getid);
                FinalTermDetailIdStr = getid;
                Log.d("FInalTermDetailId", FinalTermDetailIdStr);
                AppConfiguration.ReverseTermDetailId = FinalTermDetailIdStr;
                callAccountFeesStatusApi();
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
                        fillTermDetailSpinner();

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

    // CALL AccountFeesStatus API HERE
    private void callAccountFeesStatusApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getAccountFeesStatusDetail(getAccountDetail(), new retrofit.Callback<AccountFeesStatusModel>() {

            @Override
            public void success(AccountFeesStatusModel accountFeesStatusModel, Response response) {
                Utils.dismissDialog();
                if (accountFeesStatusModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (accountFeesStatusModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));

                    return;
                }
                if (accountFeesStatusModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentAccountSummaryBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentAccountSummaryBinding.mainLinear.setVisibility(View.GONE);
                    fragmentAccountSummaryBinding.standardwiseLinear.setVisibility(View.GONE);
                    return;
                }
                if (accountFeesStatusModel.getSuccess().equalsIgnoreCase("True")) {
                    fragmentAccountSummaryBinding.txtNoRecords.setVisibility(View.GONE);
                    for (int i = 0; i < accountFeesStatusModel.getFinalArray().size(); i++) {
                        collectionModelList = accountFeesStatusModel.getFinalArray().get(i).getCollection();
                        standardcollectionList = accountFeesStatusModel.getFinalArray().get(i).getStandardCollection();
                    }
                    if (collectionModelList != null) {
                        fragmentAccountSummaryBinding.mainLinear.setVisibility(View.VISIBLE);
                        fillData();
                    }
                    if (standardcollectionList != null) {
                        fragmentAccountSummaryBinding.standardwiseLinear.setVisibility(View.VISIBLE);
                        standardwisecollectionAdapter = new StandardwiseCollectionListAdapter(getActivity(), standardcollectionList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        fragmentAccountSummaryBinding.standardwiseStudentCollection.setLayoutManager(mLayoutManager);
                        fragmentAccountSummaryBinding.standardwiseStudentCollection.setItemAnimator(new DefaultItemAnimator());
                        fragmentAccountSummaryBinding.standardwiseStudentCollection.setAdapter(standardwisecollectionAdapter);
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

    private Map<String, String> getAccountDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Term_ID", FinalTermIdStr);
        map.put("TermDetailID", FinalTermDetailIdStr);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    //Use for Fill TermDetail Spinner
    public void fillTermDetailSpinner() {
        ArrayList<Integer> termdetailId = new ArrayList<>();
        termdetailId.add(1);
        termdetailId.add(2);


        ArrayList<String> termdetail = new ArrayList<>();
        termdetail.add("Term 1");
        termdetail.add("Term 2");


        String[] spinnertermdetailIdArray = new String[termdetailId.size()];

        spinnerTermDetailIdMap = new HashMap<Integer, String>();
        for (int i = 0; i < termdetailId.size(); i++) {
            spinnerTermDetailIdMap.put(i, String.valueOf(termdetailId.get(i)));
            spinnertermdetailIdArray[i] = termdetail.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentAccountSummaryBinding.termDetailSpinner);

            popupWindow.setHeight(spinnertermdetailIdArray.length > 4 ? 500 : spinnertermdetailIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTermdetail = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermdetailIdArray);
        fragmentAccountSummaryBinding.termDetailSpinner.setAdapter(adapterTermdetail);
        Log.d("termDetailSpinner", String.valueOf(Arrays.asList(spinnertermdetailIdArray)));
        for (int m = 0; m < spinnertermdetailIdArray.length; m++) {
            if ((AppConfiguration.TermDetailName).equalsIgnoreCase((spinnertermdetailIdArray[m]))) {
                Log.d("spinnerValue", spinnertermdetailIdArray[m]);
                int index = m;
                Log.d("indexOf", String.valueOf(index));
                fragmentAccountSummaryBinding.termDetailSpinner.setSelection(index);
            }
        }
    }

    //Use for Fill Term Spinner
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentAccountSummaryBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentAccountSummaryBinding.termSpinner.setAdapter(adapterTerm);

        Log.d("termspinner", String.valueOf(spinnertermIdArray));
        String TermName = "";
        for (int z = 0; z < finalArrayGetTermModels.size(); z++) {
            if (AppConfiguration.TermId.equalsIgnoreCase(finalArrayGetTermModels.get(z).getTermId().toString())) {
                TermName = finalArrayGetTermModels.get(z).getTerm();
            }
        }
        for (int m = 0; m < spinnertermIdArray.length; m++) {
            if (TermName.equalsIgnoreCase((spinnertermIdArray[m]))) {
                Log.d("spinnerValue", spinnertermIdArray[m]);
                int index = m;
                Log.d("indexOf", String.valueOf(index));
                fragmentAccountSummaryBinding.termSpinner.setSelection(m);
            }
        }


    }

    //Use for Fill FeesStatus Data
    public void fillData() {
        String amount1 = "", amount2 = "", amount3 = "", amount4 = "", amount5 = "", amount6 = "";
        Double longval1 = null, longval2 = null, longval3 = null, longval4 = null, longval5 = null, longval6 = null;
        Format formatter = new DecimalFormat("##,##,###");
        String formattedString1, formattedString2, formattedString3, formattedString4, formattedString5, formattedString6;

        for (int i = 0; i < collectionModelList.size(); i++) {
            amount1 = Double.toString(collectionModelList.get(i).getTotalAmt());
            amount2 = Double.toString(Double.parseDouble(collectionModelList.get(i).getTotalAmtStudent()));
            amount3 = Double.toString(collectionModelList.get(i).getTotalRcv());
            amount4 = Double.toString(Double.parseDouble(collectionModelList.get(i).getTotalRcvStudent()));
            amount5 = Double.toString(collectionModelList.get(i).getTotalDue());
            amount6 = Double.toString(Double.parseDouble(collectionModelList.get(i).getTotalDueStudent()));
            fragmentAccountSummaryBinding.perTotalTxt.setText("(" + collectionModelList.get(i).getTotalDuePer() + "%" + ")");
            fragmentAccountSummaryBinding.perStudentCountTxt.setText("(" + collectionModelList.get(i).getDueStudentPer() + "%" + ")");


        }

        longval1 = Double.parseDouble(amount1);
        longval2 = Double.parseDouble(amount2);
        longval3 = Double.parseDouble(amount3);
        longval4 = Double.parseDouble(amount4);
        longval5 = Double.parseDouble(amount5);
        longval6 = Double.parseDouble(amount6);
        Log.d("decimalValue", formatter.format(longval2));

        formattedString1 = formatter.format(longval1);
        formattedString2 = formatter.format(longval2);
        formattedString3 = formatter.format(longval3);
        formattedString4 = formatter.format(longval4);
        formattedString5 = formatter.format(longval5);
        formattedString6 = formatter.format(longval6);


//        formattedString1 = String.format("%,.1f", longval1);
//        formattedString2 = String.format("%,.1f", longval2);
//        formattedString3 = String.format("%,.1f", longval3);
//        formattedString4 = String.format("%,.1f", longval4);
//        formattedString5 = String.format("%,.1f", longval5);
//        formattedString6 = String.format("%,.1f", longval6);

        fragmentAccountSummaryBinding.totalAmountTxt.setText("₹" + " " + formattedString1);
        fragmentAccountSummaryBinding.totalStudentAmountTxt.setText("₹" + " " + formattedString2);
        fragmentAccountSummaryBinding.totalRcvAmountTxt.setText("₹" + " " + formattedString3);
        fragmentAccountSummaryBinding.totalRcvStudentTxt.setText("₹" + " " + formattedString4);
        fragmentAccountSummaryBinding.totalDueTxt.setText("₹" + " " + formattedString5);
        fragmentAccountSummaryBinding.totalstudentDueTxt.setText("₹" + " " + formattedString6);
    }
}
