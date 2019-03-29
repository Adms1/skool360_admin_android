package anandniketan.com.bhadajadmin.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.HolidayAdapter;
import anandniketan.com.bhadajadmin.Interface.getEditpermission;
import anandniketan.com.bhadajadmin.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.bhadajadmin.Model.Other.FinalArraySMSDataModel;
import anandniketan.com.bhadajadmin.Model.Other.GetStaffSMSDataModel;
import anandniketan.com.bhadajadmin.Model.Student.FinalArrayStudentModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentHolidayBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HolidayFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private FragmentHolidayBinding fragmentHolidayBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    int Year, Month, Day;
    Calendar calendar;
    private static String dateFinal;
    private static boolean isFromDate = false;
    private DatePickerDialog datePickerDialog;
    HashMap<Integer, String> spinnerHolidayCategoryMap;
    String FinalCategoryIdStr, FinalHolidayStr, startDateArray = "", endDateArray = "", discriptionArray = "", FinalholidayId = "0", HolidayNameStr, categoryStr,categoryIdStr="";
    List<FinalArrayStudentModel> finalHolidaycategoryList;
    List<FinalArraySMSDataModel> finalArrayHolidayDetialsList;
    HolidayAdapter holidayAdapter;
    String[] spinnerholidaycategoryIdArray;

    public HolidayFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentHolidayBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_holiday, container, false);

        rootView = fragmentHolidayBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setListners();
        callHolidayCategoryDataApi();
        callHolidayDataApi();
        return rootView;
    }


    public void setListners() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        fragmentHolidayBinding.startdateButton.setText(Utils.getTodaysDate());
        fragmentHolidayBinding.endDateButton.setText(Utils.getTodaysDate());

        fragmentHolidayBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentHolidayBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new OtherFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentHolidayBinding.holidayCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentHolidayBinding.holidayCategorySpinner.getSelectedItem().toString();
                String getid = spinnerHolidayCategoryMap.get(fragmentHolidayBinding.holidayCategorySpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalCategoryIdStr = getid;
                FinalHolidayStr = name;
                Log.d("FinalCategoryIdStr", FinalCategoryIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentHolidayBinding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callInsertHolidayApi();
            }
        });
        fragmentHolidayBinding.startdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromDate = true;
                datePickerDialog = DatePickerDialog.newInstance(HolidayFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });
        fragmentHolidayBinding.endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromDate = false;
                datePickerDialog = DatePickerDialog.newInstance(HolidayFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });
    }

    // CALL HolidayCategoryData API HERE
    private void callHolidayCategoryDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getHolidayCategory(getHolidayCategoryDetail(), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel holidayModel, Response response) {
                if (holidayModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (holidayModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (holidayModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
                    return;
                }
                if (holidayModel.getSuccess().equalsIgnoreCase("True")) {

                    if (holidayModel.getFinalArray().size() > 0) {
//                        finalHolidaycategoryList = holidayModel.getFinalArray();
                        fillCatergorySpinner();
                        Utils.dismissDialog();
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

    private Map<String, String> getHolidayCategoryDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    // CALL HolidayData API HERE
    private void callHolidayDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getHoliday(getHolidayDetail(), new retrofit.Callback<GetStaffSMSDataModel>() {
            @Override
            public void success(GetStaffSMSDataModel holidaydataModel, Response response) {
                if (holidaydataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (holidaydataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (holidaydataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
                    fragmentHolidayBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentHolidayBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentHolidayBinding.recyclerLinear.setVisibility(View.GONE);
                    return;
                }
                if (holidaydataModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayHolidayDetialsList = holidaydataModel.getFinalArray();
                    if (holidaydataModel.getFinalArray().size() > 0) {
                        fragmentHolidayBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentHolidayBinding.lvExpHeader.setVisibility(View.VISIBLE);
                        fragmentHolidayBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        holidayAdapter = new HolidayAdapter(mContext, holidaydataModel, new getEditpermission() {
                            @Override
                            public void getEditpermission() {
                                UpdateHoliday();
                            }
                        });
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        fragmentHolidayBinding.holidayList.setLayoutManager(mLayoutManager);
                        fragmentHolidayBinding.holidayList.setItemAnimator(new DefaultItemAnimator());
                        fragmentHolidayBinding.holidayList.setAdapter(holidayAdapter);
                        Utils.dismissDialog();
                    } else {
                        fragmentHolidayBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentHolidayBinding.lvExpHeader.setVisibility(View.GONE);
                        fragmentHolidayBinding.recyclerLinear.setVisibility(View.GONE);
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

    private Map<String, String> getHolidayDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    // CALL InsertHoliday API HERE
    private void callInsertHolidayApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().InsertHoliday(getInsertHolidayDetail(), new retrofit.Callback<InsertMenuPermissionModel>() {
            @Override
            public void success(InsertMenuPermissionModel insertHoliday, Response response) {
                if (insertHoliday == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertHoliday.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertHoliday.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
                    return;
                }
                if (insertHoliday.getSuccess().equalsIgnoreCase("True")) {
                    callHolidayDataApi();
                        Utils.dismissDialog();
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

    private Map<String, String> getInsertHolidayDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("StDt", fragmentHolidayBinding.startdateButton.getText().toString());
        map.put("EndDT", fragmentHolidayBinding.endDateButton.getText().toString());
        map.put("Description", fragmentHolidayBinding.descriptionEdt.getText().toString());
        map.put("Category", FinalHolidayStr);
        map.put("PK_HolidayID", FinalholidayId);
        map.put("PK_CategoryId",FinalCategoryIdStr);


        return map;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        populateSetDate(year, monthOfYear + 1, dayOfMonth);
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
        if (isFromDate) {
            fragmentHolidayBinding.startdateButton.setText(dateFinal);
        } else {
            fragmentHolidayBinding.endDateButton.setText(dateFinal);
        }
    }

    public void fillCatergorySpinner() {
        ArrayList<Integer> HolidaycategoryId = new ArrayList<Integer>();
//        for (int i = 0; i < finalHolidaycategoryList.size(); i++) {
//            HolidaycategoryId.add(finalHolidaycategoryList.get(i).getPkCategoryId());
//        }
//        ArrayList<String> HolidayName = new ArrayList<String>();
//        for (int j = 0; j < finalHolidaycategoryList.size(); j++) {
//            HolidayName.add(finalHolidaycategoryList.get(j).getCategory());
//        }

        spinnerholidaycategoryIdArray = new String[HolidaycategoryId.size()];

        spinnerHolidayCategoryMap = new HashMap<Integer, String>();
        for (int i = 0; i < HolidaycategoryId.size(); i++) {
            spinnerHolidayCategoryMap.put(i, String.valueOf(HolidaycategoryId.get(i)));
//            spinnerholidaycategoryIdArray[i] = HolidayName.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentHolidayBinding.holidayCategorySpinner);

            popupWindow.setHeight(spinnerholidaycategoryIdArray.length > 4 ? 500 : spinnerholidaycategoryIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerholidaycategoryIdArray);
        fragmentHolidayBinding.holidayCategorySpinner.setAdapter(adapterTerm);

        FinalCategoryIdStr = spinnerHolidayCategoryMap.get(0);


    }

    public void UpdateHoliday() {
        fragmentHolidayBinding.updateBtn.setText("Update");

        for (int k = 0; k < holidayAdapter.getRowValue().size(); k++) {
            String rowValueStr = holidayAdapter.getRowValue().get(k);
            Log.d("rowValueStr", rowValueStr);
            String[] spiltString = rowValueStr.split("\\|");
            categoryStr = spiltString[0];
            FinalholidayId = spiltString[1];
            startDateArray = spiltString[2];
            endDateArray = spiltString[3];
            discriptionArray = spiltString[4];
            categoryIdStr=spiltString[5];

//            statusArray = statusArray.substring(0, statusArray.length() - 1);

            Log.d("startDateArray", startDateArray);
        }
        fragmentHolidayBinding.startdateButton.setText(startDateArray);
        fragmentHolidayBinding.endDateButton.setText(endDateArray);
        fragmentHolidayBinding.descriptionEdt.setText(discriptionArray);

        for (int i = 0; i < finalHolidaycategoryList.size(); i++) {
//            if (categoryIdStr.equalsIgnoreCase(finalHolidaycategoryList.get(i).getPkCategoryId().toString())) {
//                HolidayNameStr = finalHolidaycategoryList.get(i).getCategory();
            }
        }
//        if (!HolidayNameStr.equalsIgnoreCase("")) {
//            for (int m = 0; m < spinnerholidaycategoryIdArray.length; m++) {
//                if (HolidayNameStr.equalsIgnoreCase((spinnerholidaycategoryIdArray[m]))) {
//                    Log.d("spinnerValue", spinnerholidaycategoryIdArray[m]);
//                    int index = m;
//                    Log.d("indexOf", String.valueOf(index));
//                    fragmentHolidayBinding.holidayCategorySpinner.setSelection(m);
//                }
//            }
//        }
//    }
}

