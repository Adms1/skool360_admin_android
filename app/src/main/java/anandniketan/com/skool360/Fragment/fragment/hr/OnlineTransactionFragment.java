package anandniketan.com.skool360.Fragment.fragment.hr;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Fragment.fragment.account.AccountFragment;
import anandniketan.com.skool360.Model.Account.FinalArrayStandard;
import anandniketan.com.skool360.Model.Account.GetStandardModel;
import anandniketan.com.skool360.Model.Account.OnlineStatusModel;
import anandniketan.com.skool360.Model.Account.OnlineTransactionModel;
import anandniketan.com.skool360.Model.Account.TallyTranscationModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.activity.DashboardActivity;
import anandniketan.com.skool360.adapter.ExpandableOnlineTransactionAdapter;
import anandniketan.com.skool360.adapter.TallyTransactionAdapter;
import anandniketan.com.skool360.databinding.FragmentOnlineTransactionBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OnlineTransactionFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static String dateFinal, finalStatus;
    ArrayList<OnlineStatusModel> firstValue;
    private FragmentOnlineTransactionBinding fragmentTallyTranscationBinding;
    private View rootView;
    private Context mContext;
    private List<FinalArrayStandard> finalArrayStandardsList;
    private HashMap<Integer, String> spinnerStandardMap;
    private String FinalClassIdStr = "", FinalStatusIdStr = "";
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private DatePickerDialog datePickerDialog;
    private int whichdateViewClick = 1;
    private Calendar calendar;
    private ExpandableOnlineTransactionAdapter onlineTransactionAdapter;
    private int Year, Month, Day;
    private HashMap<Integer, String> spinnerOrderMap;
    private List<TallyTranscationModel.FinalArray> dailyCollectionsList;
    private TallyTransactionAdapter tallyTransactionAdapter;
    private List<OnlineTransactionModel.FinalArray> finalArrayList;
    private ExpandableListView rvList;
    private List<String> listDataHeader;
    private TextView tvHeader;
    private Button btnBack, btnMenu;
    private HashMap<String, ArrayList<OnlineTransactionModel.FinalArray>> listDataChild;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AppConfiguration.position = 41;
        AppConfiguration.firsttimeback = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentTallyTranscationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_online_transaction, container, false);

        rootView = fragmentTallyTranscationBinding.getRoot();
        mContext = getActivity().getApplicationContext();


        //Set Thread Policy
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().build());

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);
        rvList = view.findViewById(R.id.lvExpstudentfeescollection);

        tvHeader.setText(R.string.online_transcation);

        setListner();
        fillGradeSpinner();

    }

    public void setListner() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        fragmentTallyTranscationBinding.fromDate1Edt.setText(Utils.getTodaysDate());
        fragmentTallyTranscationBinding.toDate2Edt.setText(Utils.getTodaysDate());

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new AccountFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentTallyTranscationBinding.fromDate1Edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichdateViewClick = 1;
                datePickerDialog = DatePickerDialog.newInstance(OnlineTransactionFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });

        fragmentTallyTranscationBinding.toDate2Edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichdateViewClick = 2;
                datePickerDialog = DatePickerDialog.newInstance(OnlineTransactionFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");

            }
        });

        fragmentTallyTranscationBinding.gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                finalStatus = firstValue.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentTallyTranscationBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callOnlineTransactionIDApi();
            }
        });

    }

    private void callStandardApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStandardSectionCombine(getStandardDetail(), new retrofit.Callback<GetStandardModel>() {
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
                        // fillStatusSpinner();

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

    private Map<String, String> getOnlineDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("StartDate", fragmentTallyTranscationBinding.fromDate1Edt.getText().toString());
        map.put("EndDate", fragmentTallyTranscationBinding.toDate2Edt.getText().toString());
        map.put("Status", finalStatus);
        map.put("TransactionID", fragmentTallyTranscationBinding.etTranscationid.getText().toString());
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    private void callOnlineTransactionIDApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getOnlineTransactionList(getOnlineDetail(), new retrofit.Callback<OnlineTransactionModel>() {
            @Override
            public void success(OnlineTransactionModel standardModel, Response response) {
                Utils.dismissDialog();
                if (standardModel == null) {
                    fragmentTallyTranscationBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentTallyTranscationBinding.lvExpHeader.setVisibility(View.GONE);
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (standardModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentTallyTranscationBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentTallyTranscationBinding.lvExpHeader.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentTallyTranscationBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentTallyTranscationBinding.lvExpHeader.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);

                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayList = standardModel.getFinalArray();
                    if (finalArrayList != null) {
                        fragmentTallyTranscationBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentTallyTranscationBinding.lvExpHeader.setVisibility(View.VISIBLE);
                        rvList.setVisibility(View.VISIBLE);
                        fillExpLV();
                        onlineTransactionAdapter = new ExpandableOnlineTransactionAdapter(getActivity(), listDataHeader, listDataChild);
                        rvList.setAdapter(onlineTransactionAdapter);
                    } else {
                        fragmentTallyTranscationBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentTallyTranscationBinding.lvExpHeader.setVisibility(View.GONE);
                        rvList.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
                rvList.setVisibility(View.GONE);
            }
        });

    }

    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        for (int i = 0; i < finalArrayList.size(); i++) {
            listDataHeader.add(finalArrayList.get(i).getTransactionid() + "|" + finalArrayList.get(i).getDate() + "|" + finalArrayList.get(i).getAmount()
                    + "|" + finalArrayList.get(i).getStatus());
            Log.d("header", "" + listDataHeader);
            ArrayList<OnlineTransactionModel.FinalArray> row = new ArrayList<>();
            row.add(finalArrayList.get(i));
            Log.d("row", "" + row);
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }

    }

    public void fillGradeSpinner() {
        firstValue = new ArrayList<>();

        OnlineStatusModel onlineStatusModel = new OnlineStatusModel();
        onlineStatusModel.setStatus("All");
        onlineStatusModel.setId("-1");
        firstValue.add(onlineStatusModel);

        OnlineStatusModel onlineStatusModel1 = new OnlineStatusModel();
        onlineStatusModel1.setStatus("Unsetteled");
        onlineStatusModel1.setId("0");
        firstValue.add(onlineStatusModel1);

        OnlineStatusModel onlineStatusModel2 = new OnlineStatusModel();
        onlineStatusModel2.setStatus("Setteled");
        onlineStatusModel2.setId("1");
        firstValue.add(onlineStatusModel2);

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentTallyTranscationBinding.gradeSpinner);

            popupWindow.setHeight(200);
//            popupWindow1.setHeght(200);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayList<String> newArr = new ArrayList<>();
        for (int i = 0; i < firstValue.size(); i++) {
            newArr.add(firstValue.get(i).getStatus());

        }

        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, newArr);
        fragmentTallyTranscationBinding.gradeSpinner.setAdapter(adapterstandard);

//        FinalClassIdStr = spinnerStandardMap.get(0);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        populateSetDate(year, monthOfYear, dayOfMonth);
    }

    public void populateSetDate(int year, int month, int day) {
        String d, m, y;
        d = Integer.toString(day);
        m = Integer.toString(month);
        y = Integer.toString(year);
        if (day < 10) {
            d = "0" + d;
        }
        if (month < 10) {
            m = "0" + m;
        }

        dateFinal = d + "/" + m + "/" + y;
        if (whichdateViewClick == 1) {
            fragmentTallyTranscationBinding.fromDate1Edt.setText(dateFinal);

        } else {
            fragmentTallyTranscationBinding.toDate2Edt.setText(dateFinal);
        }
    }
}
