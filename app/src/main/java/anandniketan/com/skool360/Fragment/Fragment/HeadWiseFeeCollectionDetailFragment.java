package anandniketan.com.skool360.Fragment.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.HeadwiseFeeAdapter;
import anandniketan.com.skool360.Interface.RecyclerItemClick;
import anandniketan.com.skool360.Model.Account.FinalArrayStandard;
import anandniketan.com.skool360.Model.Account.GetStandardModel;
import anandniketan.com.skool360.Model.MIS.MISHeadwiseFee;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiClient;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.Utility.WebServices;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */

//Created by Antra 7/1/2019

public class HeadWiseFeeCollectionDetailFragment extends Fragment implements RecyclerItemClick {

    private RecyclerView rvList;
    private String title = "", requestType = "", termID = "", stndrdID = "";
    private Spinner spAcademic, spStndrd;
    private Context mContext;
    private ProgressBar progressBar;
    private TextView tvTitle, tvNorecord, tvPhone, ivPhone, ivSMS;
    private View rootView;
    private String FinalStandardStr, StandardName, FinalStandardIdStr, FinalFinanaceTermId;
    private ArrayList<MISHeadwiseFee.FinalArray> finalArrayGetFeeModels;
    private HeadwiseFeeAdapter headwiseFeeAdapter;
    private HashMap<Integer, String> spinnerTermMap2, spinnerStandardMap;
    private List<FinalArrayGetTermModel> finalArrayGetTermModels;
    private List<FinalArrayStandard> finalArrayStandardsList;
    private Button btnBack, btnMenu;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private LinearLayoutManager linearlayoutmanager;
    private LinearLayout llHeader;
    private int lastExpandedPosition = -1;
    private RecyclerItemClick recyclerItemClick;
    private List<String> listDataHeader;
    private HashMap<String, ArrayList<MISHeadwiseFee.TermDatum>> listDataChild;
    private List<MISHeadwiseFee.FinalArray> finalArrayAnnouncementFinal;

    public HeadWiseFeeCollectionDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        recyclerItemClick = this;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_headwise_fee, container, false);
        mContext = getActivity();

        AppConfiguration.position = 66;
        AppConfiguration.firsttimeback = true;

//        rootView = fragmentMisDataBinding.getRoot();
        progressBar = rootView.findViewById(R.id.headwisefee_loader);

        try {
            Bundle bundle = this.getArguments();
            title = bundle.getString("title");
            requestType = bundle.getString("requestType");
            termID = bundle.getString("TermID");
            stndrdID = bundle.getString("StndrdID");
        } catch (Exception e) {
            e.printStackTrace();
        }

        progressBar.setVisibility(View.VISIBLE);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvList = view.findViewById(R.id.headwisefee_rv_students);

        spinnerTermMap2 = new HashMap<Integer, String>();
        tvTitle = view.findViewById(R.id.headwisefee_textView3);
        spAcademic = view.findViewById(R.id.headwisefee_term_spinner);
        spStndrd = view.findViewById(R.id.headwisefee_stndrd_spinner);
        btnBack = view.findViewById(R.id.headwisefee_btnBack);
        btnMenu = view.findViewById(R.id.headwisefee_btnmenu);
        llHeader = view.findViewById(R.id.llHeader);
        tvNorecord = view.findViewById(R.id.headwisefee_txtNoRecords);
        tvPhone = view.findViewById(R.id.head_frg_tvphone);
        ivPhone = view.findViewById(R.id.head_frg_ivphone);
        ivSMS = view.findViewById(R.id.head_frg_sms);
        tvTitle.setText(title);

        if (requestType.equalsIgnoreCase("DueTerm1") || requestType.equalsIgnoreCase("DueTerm2")) {
            tvPhone.setVisibility(View.GONE);
            ivPhone.setVisibility(View.VISIBLE);
            ivSMS.setVisibility(View.VISIBLE);
        } else {
            tvPhone.setVisibility(View.VISIBLE);
            ivPhone.setVisibility(View.GONE);
            ivSMS.setVisibility(View.GONE);
        }

        linearlayoutmanager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(linearlayoutmanager);

//        rvList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
//                String studentId = String.valueOf(finalArrayAnnouncementFinal.get(i).getStudentID());
//                callFinanceMISDataApi(studentId, termID);
//                if(expandableListView.isGroupExpanded(i)){
//                    expandableListView.collapseGroup(i);
//                }else {
//                    expandableListView.expandGroup(i);
//                }
//
//                return true;
//            }
//        });
//
//        rvList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
//
//                    rvList.collapseGroup(lastExpandedPosition);
//                }
//                lastExpandedPosition = groupPosition;
//            }
//
//        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AppConfiguration.ReverseTermDetailId = "";

//                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                AppConfiguration.position = 58;
                AppConfiguration.firsttimeback = true;

                getActivity().onBackPressed();

//                fragment = new MISFragment();
//                fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        spAcademic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = spAcademic.getSelectedItem().toString();
                String getid = spinnerTermMap2.get(spAcademic.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                termID = getid;
                AppConfiguration.financeTermId = termID;

                Log.d("FinalFinanaceTermId", termID);
                //AppConfiguration.TermName = name;

//                fragmentMisBinding.LLFinance.setVisibility(View.GONE);
//                fragmentMisBinding.progressFinance.setVisibility(View.VISIBLE);

                try {
                    if (isAdded()) {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                callFinanceMISDataApi(FinalFinanaceTermId);

                                //fillFinanceTermSpinner();
                            }
                        }, 2000);

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                progressBar.setVisibility(View.VISIBLE);
                tvNorecord.setVisibility(View.GONE);

                callTotalFeesTerm1(termID, requestType, stndrdID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spStndrd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = spStndrd.getSelectedItem().toString();
                String getid = String.valueOf(spinnerStandardMap.get(spStndrd.getSelectedItemPosition()));

                Log.d("value", name + " " + getid);
                stndrdID = getid;
                Log.d("FinalStandardIdStr", stndrdID);
                StandardName = name;
                if (name.equalsIgnoreCase("All")) {
                    stndrdID = "0";
                } else {
                    stndrdID = getid;
                }
//                Log.d("StandardName", FinalStandardStr);
//                fillSection();
                progressBar.setVisibility(View.VISIBLE);
                tvNorecord.setVisibility(View.GONE);

                callTotalFeesTerm1(termID, requestType, stndrdID);
                //  Log.d("FinalTermDetailIdStr", FinalTermDetailIdStr);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        callTermApi();
        callGradeApi();
        callTotalFeesTerm1(termID, requestType, stndrdID);

    }


    private void callTermApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Map<String, String> map = new HashMap<>();

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTerm(map, PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<TermModel>() {
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
//                        fillTermSpinner();
                        fillFinanceTermSpinner();
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

    private void callGradeApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }
        Map<String, String> map = new HashMap<>();

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStandardDetail(map, PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<GetStandardModel>() {
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
                        fillGradeSpinner();
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

    private void callTotalFeesTerm1(final String term, final String request, String stndrd) {
        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        Call<MISHeadwiseFee> call = apiService.getHeadwiseFeeDetail(AppConfiguration.BASEURL + "HeadWiseFeesCollectionDetail?TermID=" + term + "&RequestType=" + request + "&StandardID=" + stndrd);
        call.enqueue(new Callback<MISHeadwiseFee>() {

            @Override
            public void onResponse(Call<MISHeadwiseFee> call, retrofit2.Response<MISHeadwiseFee> response) {
                Utils.dismissDialog();
                progressBar.setVisibility(View.GONE);
                if (response.body() == null) {
//                    progressBar.setVisibility(View.GONE);
                    llHeader.setVisibility(View.GONE);
                    tvNorecord.setVisibility(View.VISIBLE);
                    return;
                }

                if (response.body().getSuccess().equalsIgnoreCase("false")) {
//                    progressBar.setVisibility(View.GONE);
                    llHeader.setVisibility(View.GONE);
                    tvNorecord.setVisibility(View.VISIBLE);
                    tvNorecord.setVisibility(View.VISIBLE);
                    return;
                }
                if (response.body().getSuccess().equalsIgnoreCase("True")) {

                    if (response.body().getFinalArray().size() > 0) {

//                        progressBar.setVisibility(View.GONE);
                        llHeader.setVisibility(View.VISIBLE);

//                        finalArrayAnnouncementFinal = response.body().getFinalArray();

//                        if (finalArrayAnnouncementFinal != null) {
//                            fragmentClasswiseResultBinding.tvNoRecords.setVisibility(View.GONE);
////                            fragmentClasswiseResultBinding.lvExpHeader.setVisibility(View.VISIBLE);
////                            fragmentClasswiseResultBinding.listHeader.setVisibility(View.VISIBLE);
////                            fragmentClasswiseResultBinding.studentlistProgress.setVisibility(View.VISIBLE);
//                            fillExpLV();
//                            headwiseFeeAdapter = new HeadwiseFeesAdapter(getActivity(),listDataHeader,listDataChild,responseCallBack);
//                            rvList.setAdapter(headwiseFeeAdapter);
////                            fragmentClasswiseResultBinding.studentlistProgress.setVisibility(View.GONE);
//                        } else {
////                            fragmentClasswiseResultBinding.tvNoRecords.setVisibility(View.VISIBLE);
////                            fragmentClasswiseResultBinding.lvExpHeader.setVisibility(View.GONE);
////                            fragmentClasswiseResultBinding.listHeader.setVisibility(View.GONE);
////                            fragmentClasswiseResultBinding.studentlistProgress.setVisibility(View.GONE);
//                        }

                        finalArrayGetFeeModels = response.body().getFinalArray();

                        if (finalArrayGetFeeModels != null) {
//                        fillExpLV();
                            llHeader.setVisibility(View.VISIBLE);
                            tvNorecord.setVisibility(View.GONE);
                            headwiseFeeAdapter = new HeadwiseFeeAdapter(getActivity(), finalArrayGetFeeModels, term, request, linearlayoutmanager, recyclerItemClick);
//                            headwiseFeeAdapter = new HeadwiseFeeAdapter(getActivity(), listDataHeader, listDataChild, responseCallBack);
                            rvList.setAdapter(headwiseFeeAdapter);
                        }
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<MISHeadwiseFee> call, Throwable t) {
                // Log error here since request failed
                Log.e("headfee", t.toString());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void fillExpLV() {

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, ArrayList<MISHeadwiseFee.TermDatum>>();
        for (int i = 0; i < finalArrayAnnouncementFinal.size(); i++) {
            listDataHeader.add(finalArrayAnnouncementFinal.get(i).getClassName() + "|" + finalArrayAnnouncementFinal.get(i).getStandard() + "|" + finalArrayAnnouncementFinal.get(i).getMobileNo());
            Log.d("header", "" + listDataHeader);
//            ArrayList<MISHeadwiseFee.TermDatum> row = new ArrayList<MISHeadwiseFee.TermDatum>();
//            row.addAll(finalArrayAnnouncementFinal.get(i).getTermData());
//            Log.d("row", "" + row);
//            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }
    }


    public void fillFinanceTermSpinner() {

        ArrayList<Integer> TermId = new ArrayList<Integer>();
        for (int i = 0; i < finalArrayGetTermModels.size(); i++) {
            TermId.add(finalArrayGetTermModels.get(i).getTermId());
        }
        ArrayList<String> Term = new ArrayList<String>();
        for (int j = 0; j < finalArrayGetTermModels.size(); j++) {
            Term.add(finalArrayGetTermModels.get(j).getTerm());
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerTermMap2 = new HashMap<Integer, String>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerTermMap2.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }


//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spAcademic);
//
//            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray) {

            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                //((TextView) v).setTextSize(16);
                ((TextView) v).setGravity(Gravity.CENTER_HORIZONTAL);

                return v;

            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getDropDownView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.LEFT);

                return v;

            }

        };


        spAcademic.setAdapter(adapter);
        FinalFinanaceTermId = spinnerTermMap2.get(1);
        AppConfiguration.financeTermId = FinalFinanaceTermId;
        spAcademic.setSelection(1, false);

    }

    public void fillGradeSpinner() {
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("All");

        ArrayList<String> standardname = new ArrayList<>();
        for (int z = 0; z < firstValue.size(); z++) {
            standardname.add(firstValue.get(z));
            for (int i = 0; i < finalArrayStandardsList.size(); i++) {
                standardname.add(finalArrayStandardsList.get(i).getStandard());
            }
        }
        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        ArrayList<Integer> standardId = new ArrayList<>();
        for (int m = 0; m < firstValueId.size(); m++) {
            standardId.add(firstValueId.get(m));
            for (int j = 0; j < finalArrayStandardsList.size(); j++) {
                standardId.add(finalArrayStandardsList.get(j).getStandardID());
            }
        }
        String[] spinnerstandardIdArray = new String[standardId.size()];

        spinnerStandardMap = new HashMap<Integer, String>();
        for (int i = 0; i < standardId.size(); i++) {
            spinnerStandardMap.put(i, String.valueOf(standardId.get(i)));
            spinnerstandardIdArray[i] = standardname.get(i).trim();
        }

//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spStndrd);
//
//            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 500 : spinnerstandardIdArray.length * 100);
////            popupWindow1.setHeght(200);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }

        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        spStndrd.setAdapter(adapterstandard);
//        spStndrd.setSelection(1);
        FinalStandardIdStr = spinnerStandardMap.get(0);


    }

    @Override
    public void onItemClick(View view, int position) {
        ((LinearLayoutManager) rvList.getLayoutManager()).scrollToPositionWithOffset(position, 0);

    }
}
