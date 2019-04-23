package anandniketan.com.skool360.Fragment.fragment.student;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import anandniketan.com.skool360.Fragment.fragment.common.NotificationFragment;
import anandniketan.com.skool360.Interface.SuggestionReplyCallback;
import anandniketan.com.skool360.Model.Student.SuggestionDataModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiClient;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.Utility.WebServices;
import anandniketan.com.skool360.activity.DashboardActivity;
import anandniketan.com.skool360.adapter.ExpandableSuggestion;
import anandniketan.com.skool360.databinding.FragmentSuggestionBinding;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */

public class SuggestionFragment extends Fragment implements DatePickerDialog.OnDateSetListener, SuggestionReplyCallback {

    private static String dateFinal = "", FinalStatusIdStr;
    private FragmentSuggestionBinding fragmentSuggestionBinding;
    private TextView tvHeader;
    private Button btnBack, btnMenu;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private DatePickerDialog datePickerDialog;
    private int whichDateClicked = 1;
    private int lastExpandedPosition = -1;
    private int Year, Month, Day;
    private Calendar calendar;
    private View rootView;
    private Context mContext;
    private SuggestionReplyCallback suggestionReplyCallback;
    private ArrayList<SuggestionDataModel.FinalArray> finalArraySuggestionFinal;
    private List<String> listDataHeader;
    private HashMap<String, ArrayList<SuggestionDataModel.FinalArray>> listDataChild;
    private ExpandableSuggestion expandableSuggestion;
    private String type, ndate = "", nid = "", nstatus = "";

    public SuggestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        suggestionReplyCallback = this;

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSuggestionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_suggestion, container, false);

        rootView = fragmentSuggestionBinding.getRoot();
        mContext = Objects.requireNonNull(getActivity()).getApplicationContext();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            type = bundle.getString("ntype");
            ndate = bundle.getString("sdate");
            nid = bundle.getString("stuid");
            nstatus = bundle.getString("sstatus");
        }

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.suggestion);

        setListener();
        fillStatusSpinner();
    }

    private void setListener() {

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

//        hour = calendar.get(Calendar.HOUR_OF_DAY);
//        minute = calendar.get(Calendar.MINUTE);
//        second = calendar.get(Calendar.SECOND);

        if (type.equalsIgnoreCase("notification")) {
            fragmentSuggestionBinding.sugFromdateBtn.setText(ndate);
        } else {
            fragmentSuggestionBinding.sugFromdateBtn.setText(Utils.getTodaysDate());
        }

        fragmentSuggestionBinding.sugTodateBtn.setText(Utils.getTodaysDate());

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        fragmentSuggestionBinding.suggestionList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {


            @Override

            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    fragmentSuggestionBinding.suggestionList.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }

        });

        fragmentSuggestionBinding.assignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentSuggestionBinding.assignSpinner.getSelectedItem().toString();

                nstatus = name;
                callParentSuggestionApi();

                Log.d("FinalStatusIdStr", name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                nstatus = "";
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equalsIgnoreCase("notification")) {
                    fragment = new NotificationFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                } else {
                    fragment = new StudentFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                }
            }
        });

        fragmentSuggestionBinding.sugFromdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichDateClicked = 1;
                datePickerDialog = DatePickerDialog.newInstance(SuggestionFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "DatePickerDialog");
            }
        });
        fragmentSuggestionBinding.sugTodateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichDateClicked = 2;
                datePickerDialog = DatePickerDialog.newInstance(SuggestionFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "DatePickerDialog");
            }
        });

        fragmentSuggestionBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callParentSuggestionApi();
            }
        });

    }

    private void callParentSuggestionApi() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        Call<SuggestionDataModel> call = apiService.getSuggestion(getSuggestionDetail());

        call.enqueue(new Callback<SuggestionDataModel>() {

//        ApiHandler.getApiService().getAllStaffLeaveRequest(getDetail(), new retrofit.Callback<SuggestionDataModel>() {

            @Override
            public void onResponse(Call<SuggestionDataModel> call, retrofit2.Response<SuggestionDataModel> response) {
                Utils.dismissDialog();
                if (response.body() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentSuggestionBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentSuggestionBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentSuggestionBinding.suggestionList.setVisibility(View.GONE);
                    return;
                }
                if (response.body().getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentSuggestionBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentSuggestionBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentSuggestionBinding.suggestionList.setVisibility(View.GONE);
                    return;
                }
                if (response.body().getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));

                    fragmentSuggestionBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentSuggestionBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentSuggestionBinding.suggestionList.setVisibility(View.GONE);
                    return;
                }
                if (response.body().getSuccess().equalsIgnoreCase("True")) {
                    finalArraySuggestionFinal = response.body().getFinalArray();
                    if (finalArraySuggestionFinal != null) {
                        fragmentSuggestionBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentSuggestionBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        fragmentSuggestionBinding.suggestionList.setVisibility(View.VISIBLE);

                        fillExpLV();
                        expandableSuggestion = new ExpandableSuggestion(getActivity(), listDataHeader, listDataChild, "", suggestionReplyCallback);
                        fragmentSuggestionBinding.suggestionList.setAdapter(expandableSuggestion);

                        if (nid != null) {
                            fragmentSuggestionBinding.suggestionList.expandGroup(0);
                        }

                    } else {
                        fragmentSuggestionBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentSuggestionBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentSuggestionBinding.suggestionList.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<SuggestionDataModel> call, Throwable t) {
                Utils.dismissDialog();
                t.printStackTrace();
                t.getMessage();
                fragmentSuggestionBinding.txtNoRecords.setText(t.getMessage());
                fragmentSuggestionBinding.txtNoRecords.setVisibility(View.VISIBLE);
                fragmentSuggestionBinding.recyclerLinear.setVisibility(View.GONE);
                fragmentSuggestionBinding.suggestionList.setVisibility(View.GONE);

                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });
    }

    private HashMap<String, String> getSuggestionDetail() {
        HashMap<String, String> map = new HashMap<>();
        map.put("FromDate", fragmentSuggestionBinding.sugFromdateBtn.getText().toString());
        map.put("ToDate", fragmentSuggestionBinding.sugTodateBtn.getText().toString());
        map.put("Status", nstatus);
        map.put("UserID", PrefUtils.getInstance(getActivity()).getStringValue("StaffID", "0"));
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        for (int i = 0; i < finalArraySuggestionFinal.size(); i++) {

            if (nid != null) {
                if (finalArraySuggestionFinal.get(i).getPk_suggestionid().equalsIgnoreCase(nid)) {
                    listDataHeader.add(finalArraySuggestionFinal.get(i).getSubject() + "|" + finalArraySuggestionFinal.get(i).getReplydate() + "|" + finalArraySuggestionFinal.get(i).getReplydatetime());
                    Log.d("header", "" + listDataHeader);
                    ArrayList<SuggestionDataModel.FinalArray> row = new ArrayList<>();
                    row.add(finalArraySuggestionFinal.get(i));
                    Log.d("row", "" + row);
                    listDataChild.put(listDataHeader.get(0), row);
                    Log.d("child", "" + listDataChild);

                }
            } else {
                listDataHeader.add(finalArraySuggestionFinal.get(i).getSubject() + "|" + finalArraySuggestionFinal.get(i).getReplydate() + "|" + finalArraySuggestionFinal.get(i).getReplydatetime());
                Log.d("header", "" + listDataHeader);
                ArrayList<SuggestionDataModel.FinalArray> row = new ArrayList<>();
                row.add(finalArraySuggestionFinal.get(i));
                Log.d("row", "" + row);
                listDataChild.put(listDataHeader.get(i), row);
                Log.d("child", "" + listDataChild);

            }
        }

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        populateSetDate(year, monthOfYear, dayOfMonth);
    }

    public void populateSetDate(int year, int month, int day) {
        String d, m, y;
        d = Integer.toString(day);
        m = Integer.toString(month + 1);
        y = Integer.toString(year);
        if (day < 10) {
            d = "0" + d;
        }
        if (month < 10) {
            m = "0" + m;
        }

        dateFinal = d + "/" + m + "/" + y;

        if (whichDateClicked == 1) {
            fragmentSuggestionBinding.sugFromdateBtn.setText(dateFinal);

        } else if (whichDateClicked == 2) {
            fragmentSuggestionBinding.sugTodateBtn.setText(dateFinal);

        } else if (whichDateClicked == 3) {
            fragmentSuggestionBinding.sugFromdateBtn.setText(dateFinal);
        }
    }

    private void fillStatusSpinner() {

        ArrayList<String> status = new ArrayList<>();

        status.add("--Select--");
        status.add("Pending");
        status.add("Replying");

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout, status);
        fragmentSuggestionBinding.assignSpinner.setAdapter(adapterTerm);

        FinalStatusIdStr = status.get(0);
    }

    @Override
    public void onReply(int grpos, int chpos, String message) {
        callSuggestionReplyApi(grpos, chpos, message);
    }

    private void callSuggestionReplyApi(int gr, int ch, String msg) {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        Call<JsonObject> call = apiService.getReplyParentSuggestion(getSuggestionReplyDetail(gr, ch, msg));

        call.enqueue(new Callback<JsonObject>() {

//        ApiHandler.getApiService().getAllStaffLeaveRequest(getDetail(), new retrofit.Callback<SuggestionDataModel>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                Utils.dismissDialog();
                if (response.body() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
//                    fragmentSuggestionBinding.txtNoRecords.setVisibility(View.VISIBLE);
//                    fragmentSuggestionBinding.recyclerLinear.setVisibility(View.GONE);
//                    fragmentSuggestionBinding.suggestionList.setVisibility(View.GONE);
                    return;
                }
                if (response.body().get("Success") == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
//                    fragmentSuggestionBinding.txtNoRecords.setVisibility(View.VISIBLE);
//                    fragmentSuggestionBinding.recyclerLinear.setVisibility(View.GONE);
//                    fragmentSuggestionBinding.suggestionList.setVisibility(View.GONE);
                    return;
                }
                if (response.body().get("Success").getAsString().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));

//                    fragmentSuggestionBinding.txtNoRecords.setVisibility(View.VISIBLE);
//                    fragmentSuggestionBinding.recyclerLinear.setVisibility(View.GONE);
//                    fragmentSuggestionBinding.suggestionList.setVisibility(View.GONE);
                    return;
                }
                if (response.body().get("Success").getAsString().equalsIgnoreCase("True")) {
                    Utils.ping(mContext, "Reply Send");

                    callParentSuggestionApi();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Utils.dismissDialog();
                t.printStackTrace();
                t.getMessage();
                fragmentSuggestionBinding.txtNoRecords.setText(t.getMessage());
                fragmentSuggestionBinding.txtNoRecords.setVisibility(View.VISIBLE);
                fragmentSuggestionBinding.recyclerLinear.setVisibility(View.GONE);
                fragmentSuggestionBinding.suggestionList.setVisibility(View.GONE);

                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });
    }

    private HashMap<String, String> getSuggestionReplyDetail(int gr, int ch, String msg) {
        HashMap<String, String> map = new HashMap<>();
        map.put("PK_SuggestionID", listDataChild.get(listDataHeader.get(gr)).get(ch).getPk_suggestionid());
        map.put("Reply", msg);
        map.put("UserID", PrefUtils.getInstance(getActivity()).getStringValue("StaffID", "0"));
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

}
