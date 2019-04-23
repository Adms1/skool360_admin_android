package anandniketan.com.skool360.Fragment.fragment.other;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Interface.onCheckBoxChnage;
import anandniketan.com.skool360.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.skool360.Model.Other.FinalArraySMSDataModel;
import anandniketan.com.skool360.Model.Other.GetStaffSMSDataModel;
import anandniketan.com.skool360.Model.Other.StudentDatum;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.adapter.ListAdapterCreate;
import anandniketan.com.skool360.databinding.FragmentCreateBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class CreateFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static TextView insert_message_date_txt;
    GetStaffSMSDataModel responseArray;
    List<FinalArraySMSDataModel> finalArrayStudentNameModelsList;
    ListAdapterCreate listAdapterCreate;
    String spinnerSelectedValue, value;
    int Year, Month, Day;
    Calendar calendar;
    int mYear, mMonth, mDay;
    String finalStudentArray, finalmessageDate, finalmessageSubject, finalmessageMessageLine;
    String textIcon;
    private FragmentCreateBinding fragmentCreateBinding;
    private View rootView;
    private Context mContext;
    private ArrayList<StudentDatum> arrayList;
    //Use for Dialog
    private DatePickerDialog datePickerDialog;
    private AlertDialog alertDialogAndroid = null;
    private Button close_btn, send_btn;
    private EditText insert_message_subject_txt, insert_message_Message_txt;

    public CreateFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentCreateBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create, container, false);

        rootView = fragmentCreateBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        Glide.with(mContext)
                .load(AppConfiguration.BASEURL_ICONS + "Done.png")
                .into(fragmentCreateBinding.insertMessageImg);
        setUserVisibleHint(true);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && rootView != null) {
            callClassSubjectWiseStudentDataApi();
        }
        // execute your data loading logic.
    }

    public void setListners() {
        fragmentCreateBinding.createClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerSelectedValue = adapterView.getItemAtPosition(i).toString();
                Log.d("spinner", spinnerSelectedValue);
                String[] array = spinnerSelectedValue.split("\\-");//textIcon
                String value = array[2].replaceFirst(">", "");
                Log.d("Array", Arrays.toString(array));
//                Log.d("Array1", value);
                List<FinalArraySMSDataModel> filterFinalArray = new ArrayList<FinalArraySMSDataModel>();
                for (FinalArraySMSDataModel arrayObj : responseArray.getFinalArray()) {
                    if (arrayObj.getStandard().equalsIgnoreCase(array[0].trim()) &&
                            arrayObj.getClassname().equalsIgnoreCase(array[1].trim()) && arrayObj.getSubject().equalsIgnoreCase(value.trim())) {
                        filterFinalArray.add(arrayObj);
                    }
                }
                setExpandableListView(filterFinalArray);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        fragmentCreateBinding.insertMessageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessage();
            }
        });
    }


    // CALL GetClassSubjectWiseStudentData API HERE
    private void callClassSubjectWiseStudentDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTeacherGetClassSubjectWiseStudent(getInboxDataDetail(), new retrofit.Callback<GetStaffSMSDataModel>() {
            @Override
            public void success(GetStaffSMSDataModel displayStudentModel, Response response) {
                Utils.dismissDialog();
                if (displayStudentModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (displayStudentModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (displayStudentModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
                    return;
                }
                if (displayStudentModel.getSuccess().equalsIgnoreCase("True")) {
                    responseArray = displayStudentModel;
                    Log.d("responseArray", "" + responseArray);
                    finalArrayStudentNameModelsList = displayStudentModel.getFinalArray();
                    if (finalArrayStudentNameModelsList != null) {
                        fillspinner();
                        Utils.dismissDialog();
                    } else {
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

    private Map<String, String> getInboxDataDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("StaffID", "0");
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    public void fillspinner() {
        ArrayList<String> row = new ArrayList<String>();
        textIcon = getResources().getString(R.string.arrow);
        for (int z = 0; z < finalArrayStudentNameModelsList.size(); z++) {
            if (!finalArrayStudentNameModelsList.get(z).getSubject().equalsIgnoreCase("")) {
                row.add(finalArrayStudentNameModelsList.get(z).getStandard() + "-" +
                        finalArrayStudentNameModelsList.get(z).getClassname() + "->" +
                        finalArrayStudentNameModelsList.get(z).getSubject());
            }
        }
        HashSet hs = new HashSet();
        hs.addAll(row);
        row.clear();
        row.addAll(hs);
        Log.d("marks", "" + row);
        Collections.sort(row);
        System.out.println("Sorted ArrayList in Java - Ascending order : " + row);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentCreateBinding.createClassSpinner);

            popupWindow.setHeight(row.size() > 5 ? 500 : row.size() * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, row);
        fragmentCreateBinding.createClassSpinner.setAdapter(adapterYear);
    }

    private void setExpandableListView(List<FinalArraySMSDataModel> array) {
        arrayList = new ArrayList<>();
        arrayList.clear();

        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getStudentData().size() > 0) {
                fragmentCreateBinding.createHeader.setVisibility(View.VISIBLE);
                fragmentCreateBinding.txtNoRecordsCreate.setVisibility(View.GONE);
                fragmentCreateBinding.insertMessageImg.setVisibility(View.GONE);
                for (int j = 0; j < array.get(i).getStudentData().size(); j++) {
                    arrayList.add(array.get(i).getStudentData().get(j));
                }

                Log.d("arrayList", "" + arrayList.size());
            } else {
                fragmentCreateBinding.txtNoRecordsCreate.setVisibility(View.VISIBLE);
                fragmentCreateBinding.createHeader.setVisibility(View.GONE);
                fragmentCreateBinding.insertMessageImg.setVisibility(View.GONE);
            }
        }
        for (int k = 0; k < arrayList.size(); k++) {
            arrayList.get(k).setCheck("0");
        }
        listAdapterCreate = new ListAdapterCreate(getActivity(), arrayList, getActivity().getFragmentManager(), new onCheckBoxChnage() {
            @Override
            public void getChecked() {
                fragmentCreateBinding.insertMessageImg.setVisibility(View.GONE);
                ArrayList<StudentDatum> updatedData = listAdapterCreate.getDatas();
                Boolean data = false;
                for (int i = 0; i < updatedData.size(); i++) {
                    if (updatedData.get(i).getCheck().equalsIgnoreCase("1")) {
                        data = true;
                        Log.d("Position,Checked or not", "" + i + " : " + updatedData.get(i).getCheck());
                    }
                }
                if (data) {
                    fragmentCreateBinding.insertMessageImg.setVisibility(View.VISIBLE);
                } else {
                    fragmentCreateBinding.insertMessageImg.setVisibility(View.GONE);
                }
            }
        });
        fragmentCreateBinding.lvCreate.setAdapter(listAdapterCreate);
        fragmentCreateBinding.lvCreate.deferNotifyDataSetChanged();

    }

    public void SendMessage() {
        LayoutInflater lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = lInflater.inflate(R.layout.insert_message_item, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
        alertDialogBuilderUserInput.setView(layout);

        alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.setCancelable(false);
        alertDialogAndroid.show();
        Window window = alertDialogAndroid.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(wlp);
        alertDialogAndroid.show();

        insert_message_date_txt = layout.findViewById(R.id.insert_message_date_txt);
        insert_message_subject_txt = layout.findViewById(R.id.insert_message_subject_txt);
        insert_message_Message_txt = layout.findViewById(R.id.insert_message_Message_txt);
        send_btn = layout.findViewById(R.id.send_message_btn);
        close_btn = layout.findViewById(R.id.close_btn);

        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis() - 1000);

        insert_message_date_txt.setText(Utils.getTodaysDate());


        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogAndroid.dismiss();
            }
        });

        insert_message_date_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = DatePickerDialog.newInstance(CreateFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.setMinDate(calendar);
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> id = new ArrayList<>();

                ArrayList<StudentDatum> array = listAdapterCreate.getDatas();
                for (int j = 0; j < array.size(); j++) {
                    if (array.get(j).getCheck().equalsIgnoreCase("1")) {
                        id.add(array.get(j).getStudentID().toString());
                    }
                }
                finalStudentArray = String.valueOf(id);
                finalStudentArray = finalStudentArray.substring(1, finalStudentArray.length() - 1);
                finalmessageDate = insert_message_date_txt.getText().toString();
                finalmessageSubject = insert_message_subject_txt.getText().toString();
                finalmessageMessageLine = insert_message_Message_txt.getText().toString();
                Log.d("StudentArray", "" + id);
                if (!finalStudentArray.equalsIgnoreCase("") && !finalmessageDate.equalsIgnoreCase("") &&
                        !finalmessageSubject.equalsIgnoreCase("") && !finalmessageMessageLine.equalsIgnoreCase("")) {
                    callCreateMessageDetailApi();
                } else {
                    Utils.ping(mContext, "Blank field not allowed.");
                }
            }
        });

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
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

        insert_message_date_txt.setText(d + "/" + m + "/" + y);
    }

    // CALL CreateMessageDetail API HERE
    private void callCreateMessageDetailApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().PTMTeacherStudentInsertDetail(getCreateMessageDetail(), new retrofit.Callback<InsertMenuPermissionModel>() {
            @Override
            public void success(InsertMenuPermissionModel insertinboxModel, Response response) {
                Utils.dismissDialog();
                if (insertinboxModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertinboxModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertinboxModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (insertinboxModel.getSuccess().equalsIgnoreCase("True")) {
                    alertDialogAndroid.dismiss();
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

    private Map<String, String> getCreateMessageDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("MessageID", "0");
        map.put("FromID", "0");
        map.put("ToID", finalStudentArray);
        map.put("MeetingDate", finalmessageDate);
        map.put("SubjectLine", finalmessageSubject);
        map.put("Description", finalmessageMessageLine);
        map.put("Flag", "Staff");
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }
}

