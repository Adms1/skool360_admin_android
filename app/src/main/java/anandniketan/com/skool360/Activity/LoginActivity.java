package anandniketan.com.skool360.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Model.MISModel;
import anandniketan.com.skool360.Model.login.LogInModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends Activity {

    //Use for dialog
    Dialog forgotDialog, idpasswordDialog;
    String communicationnoStr;
    //ExamModel forgotModelResponse, standardsectionResponse, studentListResponse;
    HashMap<Integer, String> spinnerStandardMap;
    HashMap<Integer, String> spinnerSectionMap;
    HashMap<Integer, String> studentMap;
    //  private ForgotpasswordAsyncTask forgotpasswordAsyncTask = null;
//    private GetStandardSectionAsyncTask getStandardSectionAsyncTask = null;
//    private GetStudentListAsyncTask getStudentListAsyncTask = null;
    private EditText edtUserName, edtPassword, edtmobileno;
    private TextView forgot_title_txt;
    private Button btnLogin, cancel_btn, submit_btn;
    private Spinner standard_spinner, section_spinner, student_spinner;
    private CheckBox chkRemember;
    private Context mContext;
    private ProgressDialog progressDialog;
    private HashMap<String, String> result = new HashMap<>();
    private HashMap<String, String> param = new HashMap<>();
    private String putExtras = "0";
    private String putExtrasData = "0";
    private String FinalStandardIdStr, FinalStandardStr, FinalSectionIdStr, FinalSectionStr, FinalStudentIdStr, FinalStudentNameStr;
    private List<LogInModel.FinalArray> mData = new ArrayList<LogInModel.FinalArray>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;

        putExtrasData = getIntent().getStringExtra("message");
        putExtras = getIntent().getStringExtra("fromNotification");//getAction();

        System.out.println("Login Extra : " + putExtrasData);
//        Log.d("Data", Utility.getPref(mContext, "data"));
//        Log.d("message", Utility.getPref(mContext, "message"));
        checkUnmPwd();
        initViews();
        setListners();
    }

    public void initViews() {
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        chkRemember = findViewById(R.id.chkRemember);
        forgot_title_txt = findViewById(R.id.forgot_title_txt);
    }

    public void setListners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnected(mContext)) {
                    if (!edtUserName.getText().toString().equalsIgnoreCase("")) {
                        if (!edtPassword.getText().toString().equalsIgnoreCase("")) {
//                            if(edtUserName.getText().toString().equalsIgnoreCase("bhadaj") && edtPassword.getText().toString().equalsIgnoreCase("bhadaj@1234")){
                            login(edtUserName.getText().toString(), edtPassword.getText().toString());

//                                callPermissionDetail("5");
//
//                                Intent intentDashboard = new Intent(LoginActivity.this, DashboardActivity.class);//SplashScreenActivity
//                                PrefUtils.getInstance(LoginActivity.this).setValue("Loginwithother", "false");
//
//                                String pwd = edtPassword.getText().toString();
//
////                        saveDetails(staffId, empCode, empName, deptId, desgId, empDept, empDesg, deviceId, pwd);
//                                saveDetails("5", "24", "Bhadaj", "1", "3", "Admin", "Admin", "", pwd);
//
//                                intentDashboard.putExtra("message", putExtrasData);
//                                intentDashboard.putExtra("fromNotification", putExtras);
//                                System.out.println("messageLogin: " + putExtrasData);
//
//                                startActivity(intentDashboard);
//                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//                                finish();

//                            }else{
//                                Utils.ping(LoginActivity.this, "Username or Password Not Match");
//                            }
                        } else {
                            Utils.ping(mContext, "Please Enter Password");
                            edtPassword.requestFocus();
                        }
                    } else {
                        Utils.ping(mContext, "Please Enter User Name");
                        edtUserName.requestFocus();
                    }
                } else {
                    Utils.ping(mContext, "Network not available");
                }
            }
        });

        forgot_title_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // forgotPasswordDialog();
            }
        });

        edtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (Utils.isNetworkConnected(mContext)) {
                        if (!edtUserName.getText().toString().equalsIgnoreCase("")) {
                            if (!edtPassword.getText().toString().equalsIgnoreCase("")) {
//                                if(edtUserName.getText().toString().equalsIgnoreCase("bhadaj") && edtPassword.getText().toString().equalsIgnoreCase("bhadaj@1234")){
                                login(edtUserName.getText().toString(), edtPassword.getText().toString());

//                                    callPermissionDetail("5");
//
//                                    Intent intentDashboard = new Intent(LoginActivity.this, DashboardActivity.class);//SplashScreenActivity
//                                    PrefUtils.getInstance(LoginActivity.this).setValue("Loginwithother", "false");
//
//                                    String pwd = edtPassword.getText().toString();
//
////                        saveDetails(staffId, empCode, empName, deptId, desgId, empDept, empDesg, deviceId, pwd);
//                                    saveDetails("5", "24", "Bhadaj", "1", "3", "Admin", "Admin", "", pwd);
//
//                                    intentDashboard.putExtra("message", putExtrasData);
//                                    intentDashboard.putExtra("fromNotification", putExtras);
//                                    System.out.println("messageLogin: " + putExtrasData);
//
//                                    startActivity(intentDashboard);
//                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//                                    finish();
//
//                                }else{
//                                    Utils.ping(LoginActivity.this, "Username or Password Not Match");
//                                }
                            } else {
                                Utils.pong(mContext, "Please Enter Password");
                                edtPassword.requestFocus();
                            }
                        } else {
                            Utils.pong(mContext, "Please Enter User Name");
                            edtUserName.requestFocus();
                        }
                    } else {
                        Utils.ping(mContext, "Network not available");
                    }
                }
                return false;
            }
        });
    }

    public void login(String UserId, String pwd) {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), LoginActivity.this);
            return;
        }

        Utils.showDialog(LoginActivity.this);
        ApiHandler.getApiService().login(getLoginDetail(UserId, pwd), new retrofit.Callback<LogInModel>() {
            @Override
            public void success(LogInModel termModel, Response response) {
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
                    Utils.ping(mContext, "UserName or Password Not Match");
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
                    mData = termModel.getFinalArray();
                    if (mData != null) {

                        Utils.callPermissionDetail(LoginActivity.this, mData.get(0).getStaffID(), mData.get(0).getLocationid());

                        Intent intentDashboard = new Intent(LoginActivity.this, DashboardActivity.class);//SplashScreenActivity
                        PrefUtils.getInstance(LoginActivity.this).setValue("Loginwithother", "false");

                        String staffId = mData.get(0).getStaffID();
                        String empCode = mData.get(0).getEmpCode();
                        String empDesg = mData.get(0).getDesignationName();
                        String empName = mData.get(0).getEmpName();
                        String deptId = mData.get(0).getDepratmentID();
                        String desgId = mData.get(0).getDesignationID();
                        String empDept = mData.get(0).getDepratmentName();
                        String pwd = edtPassword.getText().toString();
                        String deviceId = mData.get(0).getDeviceId();
                        String locId = mData.get(0).getLocationid();

//                        AppConfiguration.DEVICE_ID = mData.get(0).getDeviceId();

                        saveDetails(staffId, empCode, empName, deptId, desgId, empDept, empDesg, deviceId, pwd, locId);
//                        saveDetails("5", empCode, empName, deptId, desgId, empDept, empDesg, deviceId, pwd);

                        intentDashboard.putExtra("message", putExtrasData);
                        intentDashboard.putExtra("fromNotification", putExtras);
                        System.out.println("messageLogin: " + putExtrasData);

                        startActivity(intentDashboard);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        finish();
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

    private Map<String, String> getLoginDetail(String userId, String pwd) {
        Map<String, String> map = new HashMap<>();
        map.put("UserID", userId);
        map.put("Password", pwd);
        return map;
    }

    private Map<String, String> getAdminDeviceDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("StaffID", "");
        map.put("DeviceID", "");
        map.put("TokenID", "");
        map.put("DeviceType", "");
        map.put("LocationID", PrefUtils.getInstance(LoginActivity.this).getStringValue("LocationID", "0"));
        return map;
    }

    public void addDeviceDetail() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), LoginActivity.this);
            return;
        }
        Utils.showDialog(LoginActivity.this);
        ApiHandler.getApiService().addDeviceDetailAdmin(getAdminDeviceDetail(), PrefUtils.getInstance(LoginActivity.this).getStringValue("LocationID", "0"), new retrofit.Callback<MISModel>() {
            @Override
            public void success(MISModel termModel, Response response) {
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
                    Utils.ping(mContext, "UserName or Password Not Match");
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
//                    mData = termModel.getFinalArray();
//                    if (mData != null) {
//                        Intent intentDashboard = new Intent(LoginActivity.this, DashboardActivity.class);//SplashScreenActivity
//                        PrefUtils.getInstance(LoginActivity.this).setValue("Loginwithother", "false");
//
//                        String staffId = mData.get(0).getStaffID();
//                        String empCode = mData.get(0).getEmpCode();
//                        String empDesg = mData.get(0).getDesignationName();
//                        String empName  = mData.get(0).getEmpName();
//                        String deptId = mData.get(0).getDepratmentID();
//                        String desgId = mData.get(0).getDesignationID();
//                        String empDept = mData.get(0).getDepratmentName();
//                        String pwd = edtPassword.getText().toString();
//                        String deviceId = mData.get(0).getDeviceId();
//
//                        saveDetails(staffId,empCode,empName,deptId,desgId,empDept,empDesg,deviceId,pwd);
//
//                        intentDashboard.putExtra("message", putExtrasData);
//                        intentDashboard.putExtra("fromNotification", putExtras);
//                        System.out.println("messageLogin: " + putExtrasData);
//
//                        startActivity(intentDashboard);
//                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
//                        finish();
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

    public void checkUnmPwd() {
        if (!PrefUtils.getInstance(LoginActivity.this).getStringValue("Emp_Name", "").equalsIgnoreCase("")) {

            Intent intentDashboard = new Intent(LoginActivity.this, DashboardActivity.class);
            // Utility.setPref(mContext, "Loginwithother", "false");
            intentDashboard.putExtra("message", putExtrasData);
            intentDashboard.putExtra("fromNotification", putExtras);
            startActivity(intentDashboard);
            finish();
        }
    }

    public void saveDetails(String staffId, String empCode, String empName, String deptId, String desgId, String depName, String desgName, String deviceId, String pwd, String locId) {
        PrefUtils prefUtils = PrefUtils.getInstance(LoginActivity.this);
        prefUtils.setValue("StaffID", staffId);
        prefUtils.setValue("Emp_Code", empCode);
        prefUtils.setValue("Emp_Name", empName);
        prefUtils.setValue("DepratmentID", deptId);
        prefUtils.setValue("DesignationID", desgId);
        prefUtils.setValue("DepratmentName", depName);
        prefUtils.setValue("DesignationName", desgName);
        prefUtils.setValue("DeviceId", deviceId);
        prefUtils.setValue("pwd", pwd);
        prefUtils.setValue("LocatiobID", locId);

    }

//    public void forgotPasswordDialog() {
//        forgotDialog = new Dialog(mContext, R.style.Theme_Dialog);
//        Window window = forgotDialog.getWindow();
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        forgotDialog.getWindow().getAttributes().verticalMargin = 0.0f;
//        wlp.gravity = Gravity.CENTER;
//        window.setAttributes(wlp);
//
////        forgotDialog.getWindow().setBackgroundDrawableResource(R.drawable.session_confirm);
//        forgotDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        forgotDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        forgotDialog.setCancelable(false);
//        forgotDialog.setContentView(R.layout.forgot_password);
//
//        cancel_btn = (Button) forgotDialog.findViewById(R.id.cancel_btn);
//        submit_btn = (Button) forgotDialog.findViewById(R.id.submit_btn);
//        edtmobileno = (EditText) forgotDialog.findViewById(R.id.edtmobileno);
//
//        cancel_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                forgotDialog.dismiss();
//            }
//        });
//
//        edtmobileno.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                if (i == EditorInfo.IME_ACTION_DONE) {
//                    communicationnoStr = edtmobileno.getText().toString();
//                    if (!communicationnoStr.equalsIgnoreCase("")) {
//                        if (communicationnoStr.length() >= 10) {
//                            getForgotData();
//                        } else {
//                            edtmobileno.setError("Please enter valid no");
//                        }
//                    } else {
//                        edtmobileno.setError("Please enter communication no");
//                    }
//                }
//                return false;
//            }
//        });
//        submit_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                communicationnoStr = edtmobileno.getText().toString();
//                if (!communicationnoStr.equalsIgnoreCase("")) {
//                    if (communicationnoStr.length() >= 10) {
//                        getForgotData();
//                    } else {
//                        edtmobileno.setError("Please enter valid no");
//                    }
//                } else {
//                    edtmobileno.setError("Please enter communication no");
//                }
//            }
//        });
//
//        forgotDialog.show();
//
//    }
//
//    public void getForgotData() {
//        if (Utils.isNetworkConnected(mContext)) {
//            progressDialog = new ProgressDialog(mContext);
//            progressDialog.setMessage("Please Wait...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        HashMap<String, String> params = new HashMap<String, String>();
//                        params.put("MobileNo", communicationnoStr);
//                        forgotpasswordAsyncTask = new ForgotpasswordAsyncTask(params);
//                        forgotModelResponse = forgotpasswordAsyncTask.execute().get();
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                progressDialog.dismiss();
//                                if (forgotModelResponse != null) {
//                                    if (forgotModelResponse.getSuccess().equalsIgnoreCase("True")) {
//                                        Utils.ping(mContext, "Please check your inbox for id or password");
//                                        forgotDialog.dismiss();
//                                    } else {
//                                        progressDialog.dismiss();
//                                        Utils.ping(mContext, forgotModelResponse.getMessage().toString());
//                                    }
//                                } else {
//                                    Intent serverintent = new Intent(mContext, Server_Error.class);
//                                    startActivity(serverintent);
//                                }
//                            }
//                        });
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        } else {
//            Utils.ping(mContext, "Network not available");
//        }
//    }


//    public void IdPasswordDialog() {
//        idpasswordDialog = new Dialog(mContext, R.style.Theme_Dialog);
//        Window window = idpasswordDialog.getWindow();
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        idpasswordDialog.getWindow().getAttributes().verticalMargin = 0.0f;
//        wlp.gravity = Gravity.CENTER;
//        window.setAttributes(wlp);
//
////        forgotDialog.getWindow().setBackgroundDrawableResource(R.drawable.session_confirm);
//        idpasswordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        idpasswordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        idpasswordDialog.setCancelable(false);
//        idpasswordDialog.setContentView(R.layout.idpassworddialog);
//        getStandardSectionData();
//        cancel_btn = (Button) idpasswordDialog.findViewById(R.id.cancel_btn);
//        submit_btn = (Button) idpasswordDialog.findViewById(R.id.submit_btn);
//        standard_spinner = (Spinner) idpasswordDialog.findViewById(R.id.standard_spinner);
//        section_spinner = (Spinner) idpasswordDialog.findViewById(R.id.section_spinner);
//        student_spinner = (Spinner) idpasswordDialog.findViewById(R.id.student_spinner);
//        cancel_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                idpasswordDialog.dismiss();
//            }
//        });
//
//
//        standard_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String name = standard_spinner.getSelectedItem().toString();
//                String getid = spinnerStandardMap.get(standard_spinner.getSelectedItemPosition());
//
//                Log.d("value", name + " " + getid);
//                FinalStandardIdStr = getid.toString();
//                Log.d("FinalStandardIdStr", FinalStandardIdStr);
//                FinalStandardStr = name;
//                Log.d("StandardName", FinalStandardStr);
//                fillSectionSpinner();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        section_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String name = section_spinner.getSelectedItem().toString();
//                String getid = spinnerSectionMap.get(section_spinner.getSelectedItemPosition());
//
//                Log.d("value", name + " " + getid);
//                FinalSectionIdStr = getid.toString();
//                Log.d("FinalStandardIdStr", FinalStandardIdStr);
//                FinalSectionStr = name;
//                Log.d("SectionName", FinalSectionStr);
//                getStudentListData();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        student_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String name = student_spinner.getSelectedItem().toString();
//                String getid = studentMap.get(student_spinner.getSelectedItemPosition());
//
//                Log.d("value", name + " " + getid);
//                FinalStudentIdStr = getid.toString();
//                Log.d("FinalStudentIdStr", FinalStudentIdStr);
//                FinalStudentNameStr = name;
//                Log.d("StudentName", FinalStudentNameStr);
//                for (int j = 0; j < studentListResponse.getFinalArray().size(); j++) {
//                    if (FinalStudentNameStr.equalsIgnoreCase(studentListResponse.getFinalArray().get(j).getStudentName())) {
//                        Utility.setPref(mContext, "studid", studentListResponse.getFinalArray().get(j).getStudentID());//
//                        Utility.setPref(mContext, "FamilyID", studentListResponse.getFinalArray().get(j).getFamilyID());
//                        Utility.setPref(mContext, "standardID", String.valueOf(studentListResponse.getFinalArray().get(j).getStandardID()));
//                        Utility.setPref(mContext, "ClassID", studentListResponse.getFinalArray().get(j).getClassID());
//                        Utility.setPref(mContext, "TermID", String.valueOf(studentListResponse.getFinalArray().get(j).getTermID()));//result.get("TermID"));
//                        Utility.setPref(mContext, "RegisterStatus", String.valueOf(studentListResponse.getFinalArray().get(j).getRegisterStatus()));
//                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        submit_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Utils.setPref(mContext, "studid", FinalStudentIdStr);//
//                Intent intentDashboard = new Intent(LoginActivity.this, DashBoardActivity.class);
//                Utility.setPref(mContext, "Loginwithother", "True");
//                intentDashboard.putExtra("message", putExtrasData);
//                intentDashboard.putExtra("fromNotification", putExtras);
//                startActivity(intentDashboard);
//                finish();
//            }
//        });
//
//        idpasswordDialog.show();
//    }

//    public void getStandardSectionData() {
//        if (Utils.isNetworkConnected(mContext)) {
//            progressDialog = new ProgressDialog(mContext);
//            progressDialog.setMessage("Please Wait...");
//            progressDialog.setCancelable(false);
//            //    progressDialog.show();
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        HashMap<String, String> params = new HashMap<String, String>();
//
//                        getStandardSectionAsyncTask = new GetStandardSectionAsyncTask(params);
//                        standardsectionResponse = getStandardSectionAsyncTask.execute().get();
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                progressDialog.dismiss();
//                                if (standardsectionResponse!=null){
//                                    if (standardsectionResponse.getSuccess().equalsIgnoreCase("True")) {
//                                        fillStandardSpinner();
//                                    } else {
//                                        progressDialog.dismiss();
//                                    }
//                                }else{
//                                    Intent serverintent=new Intent(mContext,Server_Error.class);
//                                    startActivity(serverintent);
//                                }
//                            }
//                        });
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        } else {
//            Utils.ping(mContext, "Network not available");
//        }
//    }

//    public void fillStandardSpinner() {
//        ArrayList<String> firstValue = new ArrayList<>();
//        firstValue.add("All");
//
//        ArrayList<String> standardname = new ArrayList<>();
//        for (int z = 0; z < firstValue.size(); z++) {
//            standardname.add(firstValue.get(z));
//            for (int i = 0; i < standardsectionResponse.getFinalArray().size(); i++) {
//                standardname.add(standardsectionResponse.getFinalArray().get(i).getStandard());
//            }
//        }
//        ArrayList<Integer> firstValueId = new ArrayList<>();
//        firstValueId.add(0);
//        ArrayList<Integer> standardId = new ArrayList<>();
//        for (int m = 0; m < firstValueId.size(); m++) {
//            standardId.add(firstValueId.get(m));
//            for (int j = 0; j < standardsectionResponse.getFinalArray().size(); j++) {
//                standardId.add(standardsectionResponse.getFinalArray().get(j).getStandardID());
//            }
//        }
//        String[] spinnerstandardIdArray = new String[standardId.size()];
//
//        spinnerStandardMap = new HashMap<Integer, String>();
//        for (int i = 0; i < standardId.size(); i++) {
//            spinnerStandardMap.put(i, String.valueOf(standardId.get(i)));
//            spinnerstandardIdArray[i] = standardname.get(i).trim();
//        }
//
//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(standard_spinner);
//
//            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 300 : spinnerstandardIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }
//
//
//        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
//        standard_spinner.setAdapter(adapterstandard);
//
//        FinalStandardIdStr = spinnerStandardMap.get(0);
//    }
//
//    public void fillSectionSpinner() {
//        ArrayList<String> firstValue = new ArrayList<>();
//        firstValue.add("All");
//
//        ArrayList<String> sectionName = new ArrayList<>();
//        for (int z = 0; z < firstValue.size(); z++) {
//            sectionName.add(firstValue.get(z));
//            for (int i = 0; i < standardsectionResponse.getFinalArray().size(); i++) {
//                if (FinalStandardStr.equalsIgnoreCase(standardsectionResponse.getFinalArray().get(i).getStandard())) {
//                    for (int j = 0; j < standardsectionResponse.getFinalArray().get(i).getSectionDetail().size(); j++) {
//                        sectionName.add(standardsectionResponse.getFinalArray().get(i).getSectionDetail().get(j).getSection());
//                    }
//                }
//            }
//        }
//        ArrayList<Integer> firstValueId = new ArrayList<>();
//        firstValueId.add(0);
//        ArrayList<Integer> sectionId = new ArrayList<>();
//        for (int m = 0; m < firstValueId.size(); m++) {
//            sectionId.add(firstValueId.get(m));
//            for (int j = 0; j < standardsectionResponse.getFinalArray().size(); j++) {
//                if (FinalStandardStr.equalsIgnoreCase(standardsectionResponse.getFinalArray().get(j).getStandard())) {
//                    for (int i = 0; i < standardsectionResponse.getFinalArray().get(j).getSectionDetail().size(); i++) {
//                        sectionId.add(standardsectionResponse.getFinalArray().get(j).getSectionDetail().get(i).getSectionID());
//                    }
//                }
//            }
//        }
//        String[] spinnerstandardIdArray = new String[sectionId.size()];
//
//        spinnerSectionMap = new HashMap<Integer, String>();
//        for (int i = 0; i < sectionId.size(); i++) {
//            spinnerSectionMap.put(i, String.valueOf(sectionId.get(i)));
//            spinnerstandardIdArray[i] = sectionName.get(i).trim();
//        }
//
//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(section_spinner);
//
//            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 300 : spinnerstandardIdArray.length * 100);
////            popupWindow1.setHeght(200);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }
//
//
//        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
//        section_spinner.setAdapter(adapterstandard);
//
//        FinalSectionIdStr = spinnerSectionMap.get(0);
//    }

//    public void getStudentListData() {
//        if (Utils.isNetworkConnected(mContext)) {
//            progressDialog = new ProgressDialog(mContext);
//            progressDialog.setMessage("Please Wait...");
//            progressDialog.setCancelable(false);
//            //    progressDialog.show();
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        HashMap<String, String> params = new HashMap<String, String>();
//
//                        getStudentListAsyncTask = new GetStudentListAsyncTask(params);
//                        params.put("StandrdID", FinalStandardIdStr);
//                        params.put("ClassID", FinalSectionIdStr);
//                        studentListResponse = getStudentListAsyncTask.execute().get();
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                progressDialog.dismiss();
//                                if (studentListResponse!=null) {
//                                    if (studentListResponse.getSuccess().equalsIgnoreCase("True")) {
//                                        fillStudentSpinner();
//                                    } else {
//                                        progressDialog.dismiss();
//
//                                    }
//                                }else{
//                                    Intent serverintent=new Intent(mContext,Server_Error.class);
//                                    startActivity(serverintent);
//                                }
//                            }
//                        });
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        } else {
//            Utility.ping(mContext, "Network not available");
//        }
//    }
//
//    public void fillStudentSpinner() {
//        ArrayList<String> firstValue = new ArrayList<>();
//        firstValue.add("All");
//
//        ArrayList<String> studentName = new ArrayList<>();
//        for (int z = 0; z < firstValue.size(); z++) {
//            studentName.add(firstValue.get(z));
//            for (int i = 0; i < studentListResponse.getFinalArray().size(); i++) {
//                studentName.add(studentListResponse.getFinalArray().get(i).getStudentName());
//            }
//        }
//        ArrayList<Integer> firstValueId = new ArrayList<>();
//        firstValueId.add(0);
//        ArrayList<Integer> studentId = new ArrayList<>();
//        for (int m = 0; m < firstValueId.size(); m++) {
//            studentId.add(firstValueId.get(m));
//            for (int j = 0; j < studentListResponse.getFinalArray().size(); j++) {
//                studentId.add(Integer.valueOf(studentListResponse.getFinalArray().get(j).getStudentID()));
//
//            }
//        }
//        String[] spinnerstandardIdArray = new String[studentId.size()];
//
//        studentMap = new HashMap<Integer, String>();
//        for (int i = 0; i < studentId.size(); i++) {
//            studentMap.put(i, String.valueOf(studentId.get(i)));
//            spinnerstandardIdArray[i] = studentName.get(i).trim();
//        }
//
//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(student_spinner);
//
//            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 300 : spinnerstandardIdArray.length * 100);
////            popupWindow1.setHeght(200);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }
//
//
//        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
//        student_spinner.setAdapter(adapterstandard);
//
//        FinalStudentIdStr = studentMap.get(0);
//    }
}
