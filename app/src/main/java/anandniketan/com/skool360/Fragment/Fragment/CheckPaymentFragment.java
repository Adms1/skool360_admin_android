package anandniketan.com.skool360.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.CheckPaymentAdapter;
import anandniketan.com.skool360.Model.Account.AccountFeesStatusModel;
import anandniketan.com.skool360.Model.Account.FinalArrayAccountFeesModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentCheckPaymentBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class CheckPaymentFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static String dateFinal;
    private static boolean isFromDate = false;
    String FinalChequeNoStr, FinalStartDateStr, FinalEndDateStr;
    //Use for fill ReceiptList
    CheckPaymentAdapter checkPaymentAdapter;
    List<String> listDataHeader;
    HashMap<String, ArrayList<FinalArrayAccountFeesModel>> listDataChild;
    List<FinalArrayAccountFeesModel> finalArrayAllPaymentLedgerModelList;
    int Year, Month, Day;
    int mYear, mMonth, mDay;
    Calendar calendar;
    private FragmentCheckPaymentBinding fragmentCheckPaymentBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private int lastExpandedPosition = -1;
    private DatePickerDialog datePickerDialog;

    public CheckPaymentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentCheckPaymentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_check_payment, container, false);

        rootView = fragmentCheckPaymentBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setListners();


        return rootView;
    }


    public void setListners() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        fragmentCheckPaymentBinding.startdateButton.setText(Utils.getTodaysDate());
        fragmentCheckPaymentBinding.enddateButton.setText(Utils.getTodaysDate());

        fragmentCheckPaymentBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentCheckPaymentBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new AccountFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentCheckPaymentBinding.lvExpcheckpayment.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    fragmentCheckPaymentBinding.lvExpcheckpayment.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        fragmentCheckPaymentBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCheckPaymentApi();
            }
        });
        fragmentCheckPaymentBinding.startdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromDate = true;
                datePickerDialog = DatePickerDialog.newInstance(CheckPaymentFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });
        fragmentCheckPaymentBinding.enddateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromDate = false;
                datePickerDialog = DatePickerDialog.newInstance(CheckPaymentFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });
    }

    // CALL CheckPayment API HERE
    private void callCheckPaymentApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getReceiptDetails(getCheckPaymentDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<AccountFeesStatusModel>() {
            @Override
            public void success(AccountFeesStatusModel checkReceiptModel, Response response) {
//                Utils.dismissDialog();
                if (checkReceiptModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (checkReceiptModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (checkReceiptModel.getSuccess().equalsIgnoreCase("false")) {
                    fragmentCheckPaymentBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentCheckPaymentBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentCheckPaymentBinding.listHeader.setVisibility(View.GONE);
                    Utils.dismissDialog();
                    return;
                }
                if (checkReceiptModel.getSuccess().equalsIgnoreCase("True")) {

                    finalArrayAllPaymentLedgerModelList = checkReceiptModel.getFinalArray();
                    if (finalArrayAllPaymentLedgerModelList != null) {
                        fragmentCheckPaymentBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentCheckPaymentBinding.lvExpHeader.setVisibility(View.VISIBLE);
                        fragmentCheckPaymentBinding.listHeader.setVisibility(View.VISIBLE);
                        fillExpLV();
                        checkPaymentAdapter = new CheckPaymentAdapter(getActivity(), listDataHeader, listDataChild);
                        fragmentCheckPaymentBinding.lvExpcheckpayment.setAdapter(checkPaymentAdapter);
                        Utils.dismissDialog();
                    } else {
                        fragmentCheckPaymentBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentCheckPaymentBinding.lvExpHeader.setVisibility(View.GONE);
                        fragmentCheckPaymentBinding.listHeader.setVisibility(View.GONE);
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

    private Map<String, String> getCheckPaymentDetail() {

        FinalChequeNoStr = fragmentCheckPaymentBinding.checkNoEdt.getText().toString();
        FinalStartDateStr = fragmentCheckPaymentBinding.startdateButton.getText().toString();
        FinalEndDateStr = fragmentCheckPaymentBinding.enddateButton.getText().toString();

        Map<String, String> map = new HashMap<>();
        map.put("Chequeno", FinalChequeNoStr);
        map.put("FromDate", FinalStartDateStr);
        map.put("ToDate", FinalEndDateStr);


        return map;
    }

    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, ArrayList<FinalArrayAccountFeesModel>>();

        for (int i = 0; i < finalArrayAllPaymentLedgerModelList.size(); i++) {
            listDataHeader.add(finalArrayAllPaymentLedgerModelList.get(i).getgRNO() + "|"
                    + finalArrayAllPaymentLedgerModelList.get(i).getPayDate() + "|" +
                    finalArrayAllPaymentLedgerModelList.get(i).getChequeStatus() + "|" +
                    finalArrayAllPaymentLedgerModelList.get(i).getPayPaidFees() + "|" +
                    finalArrayAllPaymentLedgerModelList.get(i).getChequeNo());

            ArrayList<FinalArrayAccountFeesModel> row = new ArrayList<FinalArrayAccountFeesModel>();
            row.add(finalArrayAllPaymentLedgerModelList.get(i));

            listDataChild.put(listDataHeader.get(i), row);
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

        if (isFromDate) {
            fragmentCheckPaymentBinding.startdateButton.setText(dateFinal);
        } else {
            fragmentCheckPaymentBinding.enddateButton.setText(dateFinal);
        }
    }
}

