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
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.ExapndableInOutSummaryAdapter;
import anandniketan.com.skool360.Model.HR.EmployeeInOutSummaryModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.DateUtils;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentInOutSummaryBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class InOutSummaryFragment extends Fragment {

    private FragmentInOutSummaryBinding fragmentInOutSummaryBinding;
    private View rootView;
    private List<String> monthNames;
    private List<String> years;
    private HashMap<Integer, String> spinnerMonthMap;
    private HashMap<Integer, String> spinnerYearMap;
    private String monthId = "", yearId = "";
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private Context mContext;
    private List<EmployeeInOutSummaryModel.FinalArray> finalArrayInOut;
    private ExapndableInOutSummaryAdapter exapndableInOutSummaryAdapter;
    private List<String> listDataHeader;
    private HashMap<String, ArrayList<EmployeeInOutSummaryModel.FinalArray>> listDataChild;
    private String viewstatus;
    private int lastExpandedPosition = -1;
    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public InOutSummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentInOutSummaryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_in_out_summary, container, false);

        mContext = getActivity().getApplicationContext();

        Bundle bundle = this.getArguments();
        viewstatus = bundle.getString("inoutviewstatus");

        return fragmentInOutSummaryBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inflate the layout for this fragment
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 51;

        viewstatus = AppConfiguration.HRstaffseachviewstatus;

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.inoutsummary);

        fillMonthSpinner();
        fillYearSpinner();

        setListners();
    }

    private void setListners() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new AttendenceReportFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentInOutSummaryBinding.monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentInOutSummaryBinding.monthSpinner.getSelectedItem().toString();
                String getid = spinnerMonthMap.get(fragmentInOutSummaryBinding.monthSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                monthId = getid;
                Log.d("monthId", monthId);

                AppConfiguration.month = monthId;

//                callParentNameApi();
//                callStudentNameApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentInOutSummaryBinding.yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentInOutSummaryBinding.yearSpinner.getSelectedItem().toString();
                String getid = spinnerYearMap.get(fragmentInOutSummaryBinding.yearSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                yearId = getid;
                AppConfiguration.year = yearId;
                Log.d("yearId", yearId);

                monthId = AppConfiguration.month;

                //callInOutSummaryListDataApi();
                callEmployeeInOutListApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentInOutSummaryBinding.inoutlistList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    fragmentInOutSummaryBinding.inoutlistList.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });


        fragmentInOutSummaryBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                monthId = AppConfiguration.month;

                callEmployeeInOutListApi();
            }
        });
    }


    private void callEmployeeInOutListApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getEmployeeInOutSummary(getInOutParams(), new retrofit.Callback<EmployeeInOutSummaryModel>() {
            @Override
            public void success(EmployeeInOutSummaryModel announcementModel, Response response) {

                monthId = "";

                Utils.dismissDialog();
                if (announcementModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentInOutSummaryBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentInOutSummaryBinding.expHeader.setVisibility(View.GONE);
                    return;
                }
                if (announcementModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentInOutSummaryBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentInOutSummaryBinding.expHeader.setVisibility(View.GONE);
                    return;
                }
                if (announcementModel.getSuccess().equalsIgnoreCase("false")) {

                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentInOutSummaryBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentInOutSummaryBinding.expHeader.setVisibility(View.GONE);
                    return;

                }
                if (announcementModel.getSuccess().equalsIgnoreCase("True")) {

                    finalArrayInOut = announcementModel.getFinalArray();
                    if (finalArrayInOut != null) {
                        fragmentInOutSummaryBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentInOutSummaryBinding.expHeader.setVisibility(View.VISIBLE);
                        fillExpLV();
                        exapndableInOutSummaryAdapter = new ExapndableInOutSummaryAdapter(getActivity(), listDataHeader, listDataChild, viewstatus);
                        fragmentInOutSummaryBinding.inoutlistList.setAdapter(exapndableInOutSummaryAdapter);
                    } else {
                        fragmentInOutSummaryBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentInOutSummaryBinding.expHeader.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

                monthId = "";

                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                fragmentInOutSummaryBinding.txtNoRecords.setVisibility(View.VISIBLE);
                fragmentInOutSummaryBinding.expHeader.setVisibility(View.GONE);
                fragmentInOutSummaryBinding.txtNoRecords.setText(error.getMessage());
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getInOutParams() {
        Map<String, String> map = new HashMap<>();
        int monthIdInt;
        try {

            monthIdInt = Integer.parseInt(monthId);

            if (monthIdInt <= 9) {
                monthId = "0" + monthId;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        map.put("Month", monthId);
        map.put("Year", yearId);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, ArrayList<EmployeeInOutSummaryModel.FinalArray>>();
        for (int i = 0; i < finalArrayInOut.size(); i++) {
            listDataHeader.add(finalArrayInOut.get(i).getName() + "|" + finalArrayInOut.get(i).getDepartment());
            Log.d("header", "" + listDataHeader);
            ArrayList<EmployeeInOutSummaryModel.FinalArray> row = new ArrayList<EmployeeInOutSummaryModel.FinalArray>();
            row.add(finalArrayInOut.get(i));
            Log.d("row", "" + row);
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }

    }


    public void fillMonthSpinner() {
        monthNames = DateUtils.getMonthNames();

        ArrayList<Integer> TermId = new ArrayList<Integer>();
        for (int i = 1; i <= monthNames.size(); i++) {
            TermId.add(i);
        }
        ArrayList<String> Term = new ArrayList<String>();
        for (int j = 0; j < monthNames.size(); j++) {
            Term.add(monthNames.get(j));
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerMonthMap = new HashMap<Integer, String>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerMonthMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentInOutSummaryBinding.monthSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentInOutSummaryBinding.monthSpinner.setAdapter(adapterTerm);

        String currentMonthName = DateUtils.getCurrentMonthName();

        for (int count = 0; count < monthNames.size(); count++) {
            if (currentMonthName.equalsIgnoreCase(monthNames.get(count))) {
                fragmentInOutSummaryBinding.monthSpinner.setSelection(count);
            }
        }
        monthId = spinnerMonthMap.get(fragmentInOutSummaryBinding.monthSpinner.getSelectedItemPosition());
    }


    public void fillYearSpinner() {
        years = new ArrayList<String>();
        years.add(String.valueOf(DateUtils.getPreviousYear()));
        years.add(String.valueOf(DateUtils.getCurrentYear()));
        years.add(String.valueOf(DateUtils.getFutureYear()));

        ArrayList<Integer> TermId = new ArrayList<Integer>();
        for (int i = 0; i < years.size(); i++) {
            TermId.add(Integer.parseInt(years.get(i)));
        }
        ArrayList<String> Term = new ArrayList<String>();
        for (int j = 0; j < years.size(); j++) {
            Term.add(years.get(j));
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerYearMap = new HashMap<Integer, String>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerYearMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentInOutSummaryBinding.yearSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentInOutSummaryBinding.yearSpinner.setAdapter(adapterTerm);
        fragmentInOutSummaryBinding.yearSpinner.setSelection(1);

        yearId = spinnerYearMap.get(1);

    }


}
