package anandniketan.com.skool360.Fragment.Fragment;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
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

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.DateWiseFeesCollectionAdapter;
import anandniketan.com.skool360.Adapter.ExpandbleListAdapterDailyCollection;
import anandniketan.com.skool360.Model.Account.DateWiseFeesCollectionModel;
import anandniketan.com.skool360.Model.Account.FinalArrayStandard;
import anandniketan.com.skool360.Model.Account.GetStandardModel;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentDailyFeesCollectionBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DailyFeesCollectionFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static String dateFinal;
    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    List<FinalArrayStandard> finalArrayStandardsList;
    HashMap<Integer, String> spinnerTermMap;
    HashMap<Integer, String> spinnerStandardMap;
    HashMap<Integer, String> spinnerTermDetailIdMap;
    HashMap<Integer, String> spinnerPaymentModeMap;
    String FinalTermIdStr, FinalstandardIdStr = "", FinalTermDetailIdStr = "", FinalPaymentmodeStr = "";
    List<DateWiseFeesCollectionModel.FinalArray> dailyCollectionsList;
    List<String> listDataHeader;
    HashMap<String, ArrayList<DateWiseFeesCollectionModel.FinalArray>> listDataChild;
    ExpandbleListAdapterDailyCollection expandbleListAdapterDailyCollection;
    int Year, Month, Day;
    int mYear, mMonth, mDay;
    Calendar calendar;
    private FragmentDailyFeesCollectionBinding fragmentDailyFeesCollectionBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private int lastExpandedPosition = -1;
    private DateWiseFeesCollectionAdapter dateWiseFeesCollectionAdapter;
    private DatePickerDialog datePickerDialog;
    private String fromDate = "", toDate = "";
    private int whichClick = 1;
    private String viewstatus;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public DailyFeesCollectionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentDailyFeesCollectionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_daily_fees_collection, container, false);

        rootView = fragmentDailyFeesCollectionBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        Bundle bundle = this.getArguments();
        viewstatus = bundle.getString("viewstatus");

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.dailyfeescollection);

        setListners();
        callTermApi();
        callStandardApi();

    }

    public void setListners() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        // fragmentDailyFeesCollectionBinding.dateButton.setText(Utils.getTodaysDate());

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentDailyFeesCollectionBinding.fromDateButton.setText(Utils.getTodaysDate());
        fragmentDailyFeesCollectionBinding.todateButton.setText(Utils.getTodaysDate());

        fragmentDailyFeesCollectionBinding.fromDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichClick = 1;
                datePickerDialog = DatePickerDialog.newInstance(DailyFeesCollectionFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");

            }
        });

        fragmentDailyFeesCollectionBinding.todateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichClick = 2;
                datePickerDialog = DatePickerDialog.newInstance(DailyFeesCollectionFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new AccountFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
//        fragmentDailyFeesCollectionBinding.lvExpstudentfeescollection.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                if (lastExpandedPosition != -1
//                        && groupPosition != lastExpandedPosition) {
//                    fragmentDailyFeesCollectionBinding.lvExpstudentfeescollection.collapseGroup(lastExpandedPosition);
//                }
//                lastExpandedPosition = groupPosition;
//            }
//        });
        fragmentDailyFeesCollectionBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentDailyFeesCollectionBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentDailyFeesCollectionBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid;
                Log.d("FinalTermIdStr", FinalTermIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        fragmentDailyFeesCollectionBinding.standardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentDailyFeesCollectionBinding.standardSpinner.getSelectedItem().toString();
                String getid = spinnerStandardMap.get(fragmentDailyFeesCollectionBinding.standardSpinner.getSelectedItemPosition());
                FinalstandardIdStr = getid;
                Log.d("FinalstandardIdStr", FinalstandardIdStr);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentDailyFeesCollectionBinding.termDetailSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentDailyFeesCollectionBinding.termDetailSpinner.getSelectedItem().toString();
                String getid = spinnerTermDetailIdMap.get(fragmentDailyFeesCollectionBinding.termDetailSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermDetailIdStr = getid;
                Log.d("FinalTermDetailIdStr", FinalTermDetailIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentDailyFeesCollectionBinding.paymentModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentDailyFeesCollectionBinding.paymentModeSpinner.getSelectedItem().toString();
                String getid = spinnerPaymentModeMap.get(fragmentDailyFeesCollectionBinding.paymentModeSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalPaymentmodeStr = name;
                Log.d("FinalTermDetailIdStr", FinalPaymentmodeStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentDailyFeesCollectionBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDailyCollectionApi();
            }
        });
//        fragmentDailyFeesCollectionBinding.dateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                datePickerDialog = DatePickerDialog.newInstance(DailyFeesCollectionFragment.this, Year, Month, Day);
//                datePickerDialog.setThemeDark(false);
//                datePickerDialog.setOkText("Done");
//                datePickerDialog.showYearPickerFirst(false);
//                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
//                datePickerDialog.setTitle("Select Date From DatePickerDialog");
//                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
//            }
//        });
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
                        fillPaymentModeDetailSpinner();
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
    private void callDailyCollectionApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getDatewiseFeesCollection(getFeesCollectionDetail(), new retrofit.Callback<DateWiseFeesCollectionModel>() {
            @Override
            public void success(DateWiseFeesCollectionModel dailyFeeCollectionModel, Response response) {
//                Utils.dismissDialog();
                if (dailyFeeCollectionModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (dailyFeeCollectionModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (dailyFeeCollectionModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.dismissDialog();
//                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentDailyFeesCollectionBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentDailyFeesCollectionBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentDailyFeesCollectionBinding.lvExpstudentfeescollection.setVisibility(View.GONE);
                    return;
                }
                if (dailyFeeCollectionModel.getSuccess().equalsIgnoreCase("True")) {

                    dailyCollectionsList = dailyFeeCollectionModel.getFinalArray();
                    if (dailyCollectionsList != null && dailyCollectionsList.size() > 0) {
                        fragmentDailyFeesCollectionBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentDailyFeesCollectionBinding.lvExpHeader.setVisibility(View.VISIBLE);
                        fragmentDailyFeesCollectionBinding.lvExpstudentfeescollection.setVisibility(View.VISIBLE);
                        //fillExpLV();
                        fragmentDailyFeesCollectionBinding.lvExpstudentfeescollection.setLayoutManager(new LinearLayoutManager(getActivity()));
                        dateWiseFeesCollectionAdapter = new DateWiseFeesCollectionAdapter(getActivity(), dailyCollectionsList, viewstatus);
                        fragmentDailyFeesCollectionBinding.lvExpstudentfeescollection.setAdapter(dateWiseFeesCollectionAdapter);
                        Utils.dismissDialog();
                    } else {
                        fragmentDailyFeesCollectionBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentDailyFeesCollectionBinding.lvExpHeader.setVisibility(View.GONE);
                        fragmentDailyFeesCollectionBinding.lvExpstudentfeescollection.setVisibility(View.GONE);
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

    private Map<String, String> getFeesCollectionDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("TermID", FinalTermIdStr);
        map.put("TermDetailID", FinalTermDetailIdStr);
        map.put("FromDate", fragmentDailyFeesCollectionBinding.fromDateButton.getText().toString());
        map.put("ToDate", fragmentDailyFeesCollectionBinding.todateButton.getText().toString());
        map.put("PaymentMode", FinalPaymentmodeStr);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));

        return map;
    }

    //Use for fill the Term Spinner
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentDailyFeesCollectionBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentDailyFeesCollectionBinding.termSpinner.setAdapter(adapterTerm);

        fragmentDailyFeesCollectionBinding.termSpinner.setSelection(1);

        FinalTermIdStr = spinnerTermMap.get(0);
    }

    //Use for fill the Standard Spinner
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentDailyFeesCollectionBinding.standardSpinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 500 : spinnerstandardIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        fragmentDailyFeesCollectionBinding.standardSpinner.setAdapter(adapterTerm);

        FinalstandardIdStr = spinnerStandardMap.get(0);
    }

    //Use for fill the TermDetail Spinner
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentDailyFeesCollectionBinding.termDetailSpinner);

            popupWindow.setHeight(spinnertermdetailIdArray.length > 4 ? 500 : spinnertermdetailIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermdetailIdArray);
        fragmentDailyFeesCollectionBinding.termDetailSpinner.setAdapter(adapterTerm);

        FinalTermDetailIdStr = spinnerTermDetailIdMap.get(0);
    }

    //Use for fill the paymentMode Spinner
    public void fillPaymentModeDetailSpinner() {
        ArrayList<Integer> paymentmodeId = new ArrayList<>();
        paymentmodeId.add(1);
        paymentmodeId.add(2);
        paymentmodeId.add(3);
        ArrayList<String> paymentmodedetail = new ArrayList<>();
        paymentmodedetail.add("All");
        paymentmodedetail.add("School");
        paymentmodedetail.add("Online");
        String[] spinnerpaymentIdArray = new String[paymentmodeId.size()];

        spinnerPaymentModeMap = new HashMap<>();
        for (int i = 0; i < paymentmodeId.size(); i++) {
            spinnerPaymentModeMap.put(i, String.valueOf(paymentmodeId.get(i)));
            spinnerpaymentIdArray[i] = paymentmodedetail.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentDailyFeesCollectionBinding.paymentModeSpinner);

            popupWindow.setHeight(spinnerpaymentIdArray.length > 4 ? 500 : spinnerpaymentIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinnerpaymentIdArray);
        fragmentDailyFeesCollectionBinding.paymentModeSpinner.setAdapter(adapterTerm);

        FinalPaymentmodeStr = spinnerPaymentModeMap.get(0);
        callDailyCollectionApi();
    }

    //Use for fill the Term Spinner
    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        for (int i = 0; i < dailyCollectionsList.size(); i++) {
            listDataHeader.add(dailyCollectionsList.get(i).getName() + "|" +
                    dailyCollectionsList.get(i).getGRNO() + "|" + dailyCollectionsList.get(i).getStandard() + "|" + dailyCollectionsList.get(i).getTotalAmt() + "|" + dailyCollectionsList.get(i).getStudentID());
            Log.d("header", "" + listDataHeader);
            ArrayList<DateWiseFeesCollectionModel.FinalArray> row = new ArrayList<>();
            row.add(dailyCollectionsList.get(i));
            Log.d("row", "" + row);
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "Selected Date : " + Day + "/" + Month + "/" + Year;
        String datestr = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

        mDay = dayOfMonth;
        mMonth = monthOfYear + 1;
        mYear = year;
        String d, m, y;
        d = Integer.toString(mDay);
        m = Integer.toString(mMonth);
        y = Integer.toString(mYear);

        if (mDay < 10) {
            d = "0" + d;
        }
        if (mMonth < 10) {
            m = "0" + m;
        }

        dateFinal = d + "/" + m + "/" + y;

        if (whichClick == 1) {
            fragmentDailyFeesCollectionBinding.fromDateButton.setText(dateFinal);
        } else {
            if (whichClick == 2) {
                fragmentDailyFeesCollectionBinding.todateButton.setText(dateFinal);

            }
        }


    }
}

