package anandniketan.com.skool360.Fragment.fragment.student;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Model.Account.FinalArrayStandard;
import anandniketan.com.skool360.Model.Account.GetStandardModel;
import anandniketan.com.skool360.Model.Student.AnnouncementModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PermissionUtils;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.activity.DashboardActivity;
import anandniketan.com.skool360.adapter.AnnouncmentAdpater;
import anandniketan.com.skool360.adapter.StandardAdapter;
import anandniketan.com.skool360.databinding.FragmentAnnouncementBinding;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;

import static android.app.Activity.RESULT_OK;


public class AnnouncementFragment extends Fragment implements PermissionUtils.ReqPermissionCallback, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static final int CHOOSE_PDF_REQ_CODE = 101;
    private static String dateFinal;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private int Year, Month, Day, hour, minute, second;
    private int mYear, mMonth, mDay;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private HashMap<Integer, String> spinnerOrderMap;
    private AnnouncmentAdpater announcmentAdpater;
    private RadioGroup radioGroupUploadType, radioGroupStatus, radioGroupStandard;
    private EditText etSubject, etAnnsText;
    private GridView gridViewStandard;
    private Button btnChooseFile;
    private AppCompatButton btnAdd;
    private List<FinalArrayStandard> finalArrayStandardsList;
    private StandardAdapter standardAdapter;
    private RadioButton rbStatusActive, rbStatusInactive, rbEnterAnnoucement, rbUploadPdf, rbAll, rbIndividual;
    private String FinalDateStr, FinalSubjectStr, FinalDiscriptionStr, FinalOrderStr, FinalStatusStr = "1";
    private LinearLayout llPdfView, llAnnsView;
    private PermissionUtils.ReqPermissionCallback reqPermissionCallback;
    private String pdfFilePath = "";
    private Button btnDate;
    //    private AppCompatCheckBox cbScheduleDateTime;
    private LinearLayout llDateTimeView;
    //    private AppCompatCheckBox cbScheduledatetime;
    private String fileName = "", Pk_AnnouncementID = "";
    private FragmentAnnouncementBinding fragmentAnnouncementBinding;
    private List<AnnouncementModel.FinalArray> bundleData;
    private boolean isRecordInUpdate = false;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public AnnouncementFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        reqPermissionCallback = this;
        // onUpdateRecordRef = (OnUpdateRecord)this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentAnnouncementBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_announcement, container, false);

        rootView = fragmentAnnouncementBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        radioGroupUploadType = rootView.findViewById(R.id.upload_type_group);
        radioGroupStatus = rootView.findViewById(R.id.status_group);
        radioGroupStandard = rootView.findViewById(R.id.standard_group);
        etSubject = rootView.findViewById(R.id.subject_edt);
        etAnnsText = rootView.findViewById(R.id.anns_edt);
        gridViewStandard = rootView.findViewById(R.id.standard_grid_view);
        btnChooseFile = rootView.findViewById(R.id.btn_uploadPDF);
        btnAdd = rootView.findViewById(R.id.upload_btn);
        rbEnterAnnoucement = rootView.findViewById(R.id.rb_enterAnns);
        rbUploadPdf = rootView.findViewById(R.id.rb_uploadPdf);
        rbStatusActive = rootView.findViewById(R.id.active_chk);
        rbStatusInactive = rootView.findViewById(R.id.inactive_chk);
        rbAll = rootView.findViewById(R.id.rb_all);
        rbIndividual = rootView.findViewById(R.id.rb_individual);
        llAnnsView = rootView.findViewById(R.id.linear_announcment);
        llPdfView = rootView.findViewById(R.id.linear_pdf);
        btnDate = rootView.findViewById(R.id.date_btn);
//        cbScheduleDateTime = rootView.findViewById(R.id.cb_scheduledatetime);
        llDateTimeView = rootView.findViewById(R.id.linear_date);
//        cbScheduledatetime = rootView.findViewById(R.id.cb_scheduledatetime);
        llAnnsView.setVisibility(View.VISIBLE);
        llPdfView.setVisibility(View.GONE);

        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 55;
        //Set Thread Policy
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            gridViewStandard.setNestedScrollingEnabled(false);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view1 = view.findViewById(R.id.header);
        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.announcment);

        setListners();
        callStandardApi();
    }


    public void setListners() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (PermissionUtils.hasPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) && PermissionUtils.hasPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("application/pdf");
                    startActivityForResult(intent, CHOOSE_PDF_REQ_CODE);
                } else {
                    PermissionUtils.checkPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE, 102, "Please allow extrnal storage permission to continue", "You can't upload pdf without giving permission", reqPermissionCallback);
                }
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    callInsertUpdateAnnouncement();
                }
            }
        });
        rbEnterAnnoucement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    llAnnsView.setVisibility(View.VISIBLE);
                    llPdfView.setVisibility(View.GONE);
                }
            }
        });
        rbUploadPdf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    llAnnsView.setVisibility(View.GONE);
                    llPdfView.setVisibility(View.VISIBLE);
                }
            }
        });

//        cbScheduleDateTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    fragmentAnnouncementBinding.linearDate.setVisibility(View.VISIBLE);
//                } else {
//                    try {
//                        String currentDateTime = Utils.getCurrentDateTime("dd/MM/yyyy");
//                        String currentDateTime1 = Utils.getCurrentDateTime("hh:mm");
//                        fragmentAnnouncementBinding.dateBtn.setText(currentDateTime);
//                        fragmentAnnouncementBinding.timeBtn.setText(currentDateTime1);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                    fragmentAnnouncementBinding.linearDate.setVisibility(View.GONE);
//                }
//            }
//        });
        rbStatusInactive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    FinalStatusStr = "0";
                }
            }
        });

        rbStatusActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    FinalStatusStr = "1";
                }
            }
        });
        rbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!isRecordInUpdate) {
                    if (b) {
                        if (finalArrayStandardsList != null) {
                            if (finalArrayStandardsList.size() > 0) {

                                for (int listsize = 0; listsize < finalArrayStandardsList.size(); listsize++) {
                                    finalArrayStandardsList.get(listsize).setCheckedStatus("1");
                                }
                                if (standardAdapter != null) {
                                    standardAdapter.notifyDataSetChanged();
                                    //standardAdapter.disableSelection();
                                }
                            }
                        }
                    } else {
                        if (finalArrayStandardsList != null) {
                            if (finalArrayStandardsList.size() > 0) {

                                for (int listsize = 0; listsize < finalArrayStandardsList.size(); listsize++) {
                                    finalArrayStandardsList.get(listsize).setCheckedStatus("0");
                                }
                                if (standardAdapter != null) {
                                    standardAdapter.notifyDataSetChanged();
                                    //  standardAdapter.enableSelection();
                                }
                            }
                        }
                    }
                }
            }
        });

        try {
            String currentDateTime = Utils.getCurrentDateTime("dd/MM/yyyy");
            String currentDateTime1 = Utils.getCurrentDateTime("hh:mm");
            fragmentAnnouncementBinding.dateBtn.setText(currentDateTime);
            fragmentAnnouncementBinding.timeBtn.setText(currentDateTime1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        fragmentAnnouncementBinding.dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = DatePickerDialog.newInstance(AnnouncementFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });

        fragmentAnnouncementBinding.timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog = TimePickerDialog.newInstance(AnnouncementFragment.this, hour, minute, second, true);
                timePickerDialog.setThemeDark(false);
                timePickerDialog.setOkText("Done");
                timePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                timePickerDialog.setTitle("Select Time From TimePickerDialog");
                timePickerDialog.show(getActivity().getFragmentManager(), "TimePickerDialog");

            }
        });

        rbIndividual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (!isRecordInUpdate) {
                    if (b) {

                        if (finalArrayStandardsList != null) {
                            if (finalArrayStandardsList.size() > 0) {

                                for (int listsize = 0; listsize < finalArrayStandardsList.size(); listsize++) {
                                    finalArrayStandardsList.get(listsize).setCheckedStatus("0");
                                }
                                if (standardAdapter != null) {
                                    standardAdapter.notifyDataSetChanged();
                                    //  standardAdapter.enableSelection();
                                }
                            }
                        }
                    } else {
                        if (finalArrayStandardsList != null) {
                            if (finalArrayStandardsList.size() > 0) {

                                for (int listsize = 0; listsize < finalArrayStandardsList.size(); listsize++) {
                                    finalArrayStandardsList.get(listsize).setCheckedStatus("1");
                                }
                                if (standardAdapter != null) {
                                    standardAdapter.notifyDataSetChanged();
                                    //standardAdapter.disableSelection();
                                }
                            }
                        }
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppConfiguration.position = 55;
                fragment = new AnnoucementListFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                AppConfiguration.firsttimeback = true;

            }
        });
    }

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
                        for (int i = 0; i < finalArrayStandardsList.size(); i++) {
                            finalArrayStandardsList.get(i).setCheckedStatus("1");
                        }
                        standardAdapter = new StandardAdapter(mContext, finalArrayStandardsList);
                        gridViewStandard.setAdapter(standardAdapter);
                    }
                    try {
                        Bundle bundle = AnnouncementFragment.this.getArguments();
                        if (bundle != null) {
                            bundleData = new ArrayList<>();
                            bundleData = bundle.getParcelableArrayList("data");
                            if (bundleData != null) {
                                setData(bundleData);
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    //onUpdateRecordRef.onUpdateRecord();
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
        HashMap<String, String> map = new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }


    // CALL InsertAnnouncement
    public void callInsertUpdateAnnouncement() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().InsertAnnouncement(getInsertAnnouncement(), new retrofit.Callback<AnnouncementModel>() {
            @Override
            public void success(AnnouncementModel permissionModel, Response response) {
                Utils.dismissDialog();
                if (permissionModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (permissionModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (permissionModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (permissionModel.getSuccess().equalsIgnoreCase("True")) {
                    if (rbUploadPdf.isChecked()) {
                        if (pdfFilePath != null) {
                            if (!TextUtils.isEmpty(pdfFilePath)) {
                                uploadPdf(pdfFilePath);
                            } else {
                                //record updated.
                                if (!isRecordInUpdate) {
                                    Utils.ping(mContext, getString(R.string.anns_success_msg));
                                } else {
                                    Utils.ping(mContext, "Announcement updated successfully");
                                }

                                AppConfiguration.position = 11;
                                fragment = new AnnoucementListFragment();
                                fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                                AppConfiguration.firsttimeback = true;

                            }
                        }
                    } else {
                        if (!isRecordInUpdate) {
                            Utils.ping(mContext, getString(R.string.anns_success_msg));
                        } else {
                            Utils.ping(mContext, "Announcement updated successfully");

                        }
                        AppConfiguration.position = 11;
                        fragment = new AnnoucementListFragment();
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                .replace(R.id.frame_container, fragment).commit();
                        AppConfiguration.firsttimeback = true;


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

    private Map<String, String> getInsertAnnouncement() {
        Map<String, String> map = new HashMap<>();
        map.put("Subject", FinalSubjectStr);
        if (FinalDiscriptionStr == null) {
            FinalDiscriptionStr = "";
        }
        map.put("Description", FinalDiscriptionStr);
        map.put("Status", FinalStatusStr);
        if (Pk_AnnouncementID != null) {
            if (TextUtils.isEmpty(Pk_AnnouncementID)) {
                map.put("PK_AnnouncmentID", "0");
            } else {
                map.put("PK_AnnouncmentID", Pk_AnnouncementID);
            }
        }

        if (TextUtils.isEmpty(fileName)) {
            Long tsLong = System.currentTimeMillis() / 1000;
            fileName = "Pdf_" + tsLong + ".pdf";
        }
//        if(pdfFilePath != null && !TextUtils.isEmpty(pdfFilePath)) {
//            fileName = pdfFilePath.substring(pdfFilePath.lastIndexOf("/") + 1);
//        }

        if (rbEnterAnnoucement.isChecked()) {
            map.put("FileName", "");
        } else {
            if (rbUploadPdf.isChecked()) {
                map.put("FileName", fileName);
            }
        }
        if (rbAll.isChecked()) {
            map.put("GradeAll", "Y");
        } else {
            map.put("GradeAll", "N");
        }

        if (rbAll.isChecked()) {
            // map.put("GradeID", "0");
            if (standardAdapter != null) {
                String selectedGradeIds = TextUtils.join(",", standardAdapter.getCheckedStandards());
                map.put("GradeID", selectedGradeIds);
            }

        } else {
            if (standardAdapter != null) {
                String selectedGradeIds = TextUtils.join(",", standardAdapter.getCheckedStandards());
                map.put("GradeID", selectedGradeIds);
            }
        }


        try {
//            if (cbScheduleDateTime.isChecked()) {
////                if (fragmentAnnouncementBinding.dateTimeBtn.getText().toString().length() > 0) {
////                    String[] dateTime = fragmentAnnouncementBinding.dateTimeBtn.getText().toString().split(" ");
////                    String date = dateTime[0];
////                    String time = dateTime[1];
//                map.put("ScheduleDate", fragmentAnnouncementBinding.dateBtn.getText().toString());
//                map.put("ScheduleTime", fragmentAnnouncementBinding.timeBtn.getText().toString());
////                }
//            } else {
            map.put("ScheduleDate", "");
            map.put("ScheduleTime", "");
//            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));

        return map;
    }

    private void uploadPdf(String filePath) {
        //upload file using retrofit 2
        try {
            File file = null;
            try {
                file = new File(filePath);

            } catch (Exception e) {
                e.printStackTrace();
            }

            String path = file.getAbsolutePath();

            if (file != null) {
                if (file.exists()) {

                    Utils.showDialog(getActivity());

                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    MultipartBody.Part body = MultipartBody.Part.createFormData("Pdf", fileName, requestFile);

                    Call<ResponseBody> responseBodyCall = ApiHandler.getApiServiceForFileUplod().uploadSingleFile(body);

                    responseBodyCall.enqueue(new retrofit2.Callback<ResponseBody>() {
                        @Override
                        public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Utils.dismissDialog();
                                if (!isRecordInUpdate) {
                                    Utils.ping(getActivity(), getString(R.string.anns_success_msg));
                                } else {
                                    Utils.ping(getActivity(), "Announcement updated successfully");
                                }
                                AppConfiguration.position = 11;
                                fragment = new AnnoucementListFragment();
                                fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                                AppConfiguration.firsttimeback = true;
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                            Utils.ping(getActivity(), t.getMessage());
                            Utils.dismissDialog();
                        }
                    });
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PDF_REQ_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();


                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                //btnChooseFile.setText(displayName);

                                String filePath = Utils.getFilePathFromUri(getActivity(), uri);
                                File file = null;
                                try {
                                    file = new File(filePath);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                String path = file.getAbsolutePath();

                                if (file != null) {
                                    if (file.exists()) {
                                        fileName = "";
                                        pdfFilePath = file.getAbsolutePath();

                                        btnChooseFile.setText("File Selected");
                                        //upload file using retrofit 2
//                                        Utils.showDialog(getActivity());
//                                        //   Call<UploadObject> fileUpload = ApiHandler.getApiService().uploadSingleFile(fileToUpload, filename);
//
//                                        RequestBody mFile = RequestBody.create(MediaType.parse("application/pdf"), file);
//                                        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
//
//                                        ApiHandler.getApiServiceForFileUplod().uploadSingleFile(fileToUpload).enqueue(new retrofit2.Callback<String>() {
//                                            @Override
//                                            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
//                                                Utils.dismissDialog();
//                                                //Toast.makeText(getActivity(), "Response " + response.body(),Toast.LENGTH_LONG).show();
//
//                                            }
//
//                                            @Override
//                                            public void onFailure(Call<String> call, Throwable t) {
//                                                Utils.dismissDialog();
//                                                Toast.makeText(getActivity(), t.getMessage(),Toast.LENGTH_LONG).show();
//                                            }
//                                        });
                                    }
                                }


                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {

                        String filePath = Utils.getFilePathFromUri(getActivity(), uri);
                        File file = null;
                        try {
                            file = new File(filePath);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        String path = file.getAbsolutePath();

                        if (file != null) {
                            if (file.exists()) {
                                pdfFilePath = file.getAbsolutePath();
                                btnChooseFile.setText("File Selected");
                                fileName = "";
                            }
                        }
                    }

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onResult(boolean success) {
        if (success) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            startActivityForResult(intent, CHOOSE_PDF_REQ_CODE);
        }
    }

    private boolean validate() {
        try {
            FinalSubjectStr = etSubject.getText().toString();

            if (TextUtils.isEmpty(etSubject.getText().toString().trim())) {
                Utils.ping(getActivity(), "Please enter subject");
                return false;
            }
            if (rbEnterAnnoucement.isChecked()) {
                FinalDiscriptionStr = etAnnsText.getText().toString();
                if (TextUtils.isEmpty(etAnnsText.getText().toString().trim())) {
                    Utils.ping(getActivity(), "Please enter announcement text");
                    return false;
                }
            }
            if (rbUploadPdf.isChecked()) {
                if (!isRecordInUpdate) {
                    if (TextUtils.isEmpty(pdfFilePath)) {
                        Utils.ping(getActivity(), "Please attach pdf file");
                        return false;
                    }
                }
            }
            if (rbIndividual.isChecked()) {
                if (standardAdapter != null) {
                    if (standardAdapter.getCheckedStandards() != null) {
                        if (standardAdapter.getCheckedStandards().size() <= 0) {
                            Utils.ping(getActivity(), "Please select standard");
                            return false;
                        }
                    }
                }
            }
//            if (cbScheduleDateTime.isChecked()) {
//                if (TextUtils.isEmpty(fragmentAnnouncementBinding.dateBtn.getText().toString()) || fragmentAnnouncementBinding.dateBtn.getText().length() <= 0 || TextUtils.isEmpty(fragmentAnnouncementBinding.timeBtn.getText().toString()) || fragmentAnnouncementBinding.timeBtn.getText().length() <= 0) {
//                    Utils.ping(getActivity(), "Please select schedule date time");
//                    return false;
//                }
//            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;

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

        fragmentAnnouncementBinding.dateBtn.setText(dateFinal);

        calendar = Calendar.getInstance();

//        timePickerDialog = TimePickerDialog.newInstance(AnnouncementFragment.this, hour, minute, second, true);
//        timePickerDialog.setThemeDark(false);
//        timePickerDialog.setOkText("Done");
//        timePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
//        timePickerDialog.setTitle("Select Time From TimePickerDialog");
//        timePickerDialog.show(getActivity().getFragmentManager(), "TimePickerDialog");

    }

//    @Override
//    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
//        populateSetTime(hourOfDay,minute,second);
//    }

    public void populateSetTime(int hour, int min, int sec) {
        String d, m, y;
        d = String.valueOf(hour);
        m = String.valueOf(min);
        y = String.valueOf(sec);
        if (hour < 10) {
            d = "0" + d;
        }
        if (min < 10) {
            m = "0" + m;
        }

        dateFinal = d + ":" + m;

        fragmentAnnouncementBinding.timeBtn.setText(dateFinal);

    }


    private void setData(List<AnnouncementModel.FinalArray> dataList) {
        try {
            if (dataList != null) {
                if (dataList.size() > 0) {
                    for (int count = 0; count < dataList.size(); count++) {

                        pdfFilePath = "";
                        btnAdd.setText("Update");
                        isRecordInUpdate = true;
                        Pk_AnnouncementID = String.valueOf(dataList.get(count).getPKAnnouncmentID());

                        if (!TextUtils.isEmpty(dataList.get(count).getAnnoucementPDF())) {
                            rbUploadPdf.setChecked(true);
                            fileName = dataList.get(count).getAnnoucementPDF().substring(dataList.get(count).getAnnoucementPDF().lastIndexOf("/") + 1);
                            btnChooseFile.setText("File Selected");
                            rbEnterAnnoucement.setChecked(false);
                        } else {
                            rbUploadPdf.setChecked(false);
                            rbEnterAnnoucement.setChecked(true);
                        }

                        fragmentAnnouncementBinding.subjectEdt.setText(dataList.get(count).getSubjectName());
                        FinalSubjectStr = fragmentAnnouncementBinding.subjectEdt.getText().toString();

                        if (standardAdapter != null) {


                            String[] standards = dataList.get(count).getStandard().split(",");

                            // standardAdapter.setCheckedStandards(items);

                            if (finalArrayStandardsList != null) {
                                if (finalArrayStandardsList.size() > 0) {

                                    for (int count2 = 0; count2 < finalArrayStandardsList.size(); count2++) {
                                        finalArrayStandardsList.get(count2).setCheckedStatus("0");
                                    }

                                    for (int count3 = 0; count3 < finalArrayStandardsList.size(); count3++) {
                                        for (int count1 = 0; count1 < standards.length; count1++) {
                                            if (standards[count1].equalsIgnoreCase(finalArrayStandardsList.get(count3).getStandard())) {
                                                finalArrayStandardsList.get(count3).setCheckedStatus("1");
                                            }
                                        }
                                    }

                                    standardAdapter = new StandardAdapter(mContext, finalArrayStandardsList);
                                    gridViewStandard.setAdapter(standardAdapter);
//                                    standardAdapter.notifyDataSetChanged();

                                }
                            }
                            //  standardAdapter.notifyDataSetChanged();

                        }

                        if (!TextUtils.isEmpty(dataList.get(count).getScheduleDate()) && !TextUtils.isEmpty(dataList.get(count).getScheduleTime())) {
//                            fragmentAnnouncementBinding.cbScheduledatetime.setChecked(true);
                            fragmentAnnouncementBinding.dateBtn.setText(dataList.get(count).getScheduleDate());
                            fragmentAnnouncementBinding.timeBtn.setText(dataList.get(count).getScheduleTime());
                        }

                        if (!TextUtils.isEmpty(dataList.get(count).getAnnDesc())) {
                            fragmentAnnouncementBinding.annsEdt.setText(dataList.get(count).getAnnDesc());
                            FinalDiscriptionStr = fragmentAnnouncementBinding.annsEdt.getText().toString();
                        }

                        if (!TextUtils.isEmpty(dataList.get(count).getAnnStatus())) {
                            if (dataList.get(count).getAnnStatus().equalsIgnoreCase("Yes")) {
                                rbStatusActive.setChecked(true);
                                rbStatusInactive.setChecked(false);
                                FinalStatusStr = "1";

                            } else {
                                rbStatusActive.setChecked(false);
                                rbStatusInactive.setChecked(true);
                                FinalStatusStr = "0";
                            }
                        }

                        if (!TextUtils.isEmpty(dataList.get(count).getGradeStatus())) {
                            if (dataList.get(count).getGradeStatus().equalsIgnoreCase("Individual")) {
                                fragmentAnnouncementBinding.rbAll.setChecked(false);
                                fragmentAnnouncementBinding.rbIndividual.setChecked(true);

                            } else {
                                if (dataList.get(count).getGradeStatus().equalsIgnoreCase("All")) {
                                    fragmentAnnouncementBinding.rbAll.setChecked(true);
                                    fragmentAnnouncementBinding.rbIndividual.setChecked(false);
                                }
                            }
                        }
                    }
                }
            }
            isRecordInUpdate = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        populateSetTime(hourOfDay, minute, second);
    }
}

