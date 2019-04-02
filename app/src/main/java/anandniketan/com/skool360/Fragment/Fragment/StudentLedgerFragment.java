package anandniketan.com.skool360.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Activity.Helper;
import anandniketan.com.skool360.Adapter.ExpandableListAdapterAccountSummary;
import anandniketan.com.skool360.Adapter.ExpandableListAdapterReceipt;
import anandniketan.com.skool360.Model.Account.AccountFeesCollectionModel;
import anandniketan.com.skool360.Model.Account.AccountFeesStatusModel;
import anandniketan.com.skool360.Model.Account.FinalArrayAccountFeesModel;
import anandniketan.com.skool360.Model.Student.FinalArrayStudentModel;
import anandniketan.com.skool360.Model.Student.StudentAttendanceModel;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentStudentLedgerBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class StudentLedgerFragment extends Fragment {

    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    HashMap<Integer, String> spinnerTermMap;
    HashMap<Integer, String> spinnerStudentMap;
    HashMap<Integer, String> spinnerGrnoMap;
    String FinalTermIdStr, searchtypeStr = "Current Student", studentNameStr = "", FinalStudentIdStr, Selection;
    List<FinalArrayStudentModel> finalArrayStudentNameModelList;
    List<FinalArrayAccountFeesModel> finalArrayPaymentLedgerModelList;
    ExpandableListAdapterAccountSummary expandableListAdapterAccountSummary;
    List<String> listDataHeader;
    HashMap<String, ArrayList<FinalArrayAccountFeesModel>> listDataChild;
    List<FinalArrayAccountFeesModel> finalArrayAllPaymentLedgerModelList;
    List<String> listDataHeaderreceipt;
    HashMap<String, ArrayList<AccountFeesCollectionModel>> listDataChildreceipt;
    ExpandableListAdapterReceipt expandableListAdapterReceipt;
    private FragmentStudentLedgerBinding fragmentStudentLedgerBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private int lastExpandedPosition = -1;
    private ArrayList studentName = new ArrayList();

    public StudentLedgerFragment() {
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentStudentLedgerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_ledger, container, false);

        rootView = fragmentStudentLedgerBinding.getRoot();
        mContext = getActivity().getApplicationContext();


        setListner();
        scolling();
        callTermApi();
        callStudentNameApi();
        return rootView;
    }


    public void setListner() {
        fragmentStudentLedgerBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentStudentLedgerBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new AccountFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentStudentLedgerBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentStudentLedgerBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentStudentLedgerBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid;
                Log.d("FinalTermIdStr", FinalTermIdStr);
                callPaymentLedgerApi();
                callAllPaymentLedgerApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentStudentLedgerBinding.lvExpaccountsummary.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    fragmentStudentLedgerBinding.lvExpaccountsummary.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }

        });
        fragmentStudentLedgerBinding.lvExpreceipt.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    fragmentStudentLedgerBinding.lvExpreceipt.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        fragmentStudentLedgerBinding.searchTypeRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                fragmentStudentLedgerBinding.studentnameTxt.setText("");
                if (null != rb && checkedId > -1) {
                    switch (checkedId) {
                        case R.id.student_rdb:
                            searchtypeStr = fragmentStudentLedgerBinding.studentRdb.getTag().toString();
                            fillStudentName();
                            break;
                        case R.id.grno_rdb:
                            searchtypeStr = fragmentStudentLedgerBinding.grnoRdb.getTag().toString();
                            fillStudentName();
                            break;
                    }
                }
                Log.d("searchtype", searchtypeStr);
            }
        });
        fragmentStudentLedgerBinding.studentnameTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Selection = fragmentStudentLedgerBinding.studentnameTxt.getText().toString();

//                for (int i = 0; i < finalArrayStudentNameModelList.size(); i++) {
//                    if (searchtypeStr.equalsIgnoreCase("Current Student")) {
//                        if (finalArrayStudentNameModelList.get(i).getName().equalsIgnoreCase(Selection)) {
//                            FinalStudentIdStr = String.valueOf(finalArrayStudentNameModelList.get(i).getStudentID());
//                            Log.d("FinalStudentIdStr", "" + FinalStudentIdStr);
//                        }
//                    } else {
//                        if (finalArrayStudentNameModelList.get(i).getgRNO().equalsIgnoreCase(Selection)) {
//                            FinalStudentIdStr = String.valueOf(finalArrayStudentNameModelList.get(i).getStudentID());
//                            Log.d("FinalGRNOIdStr", "" + FinalStudentIdStr);
//                        }
//                    }
//                }
                callPaymentLedgerApi();
                callAllPaymentLedgerApi();
            }
        });
    }

    // For the use of scroll the All Listview And Screen
    public void scolling() {

        fragmentStudentLedgerBinding.scrollView.setFillViewport(true);

        Helper.getListViewSize(fragmentStudentLedgerBinding.lvExpaccountsummary);
        Helper.getListViewSize(fragmentStudentLedgerBinding.lvExpreceipt);


        fragmentStudentLedgerBinding.scrollView.setVerticalScrollBarEnabled(true);

        fragmentStudentLedgerBinding.scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                fragmentStudentLedgerBinding.lvExpaccountsummary.getParent().requestDisallowInterceptTouchEvent(false);
                fragmentStudentLedgerBinding.lvExpreceipt.getParent().requestDisallowInterceptTouchEvent(false);

                return false;
            }
        });
        fragmentStudentLedgerBinding.lvExpaccountsummary.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                fragmentStudentLedgerBinding.lvExpaccountsummary.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        fragmentStudentLedgerBinding.lvExpreceipt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                fragmentStudentLedgerBinding.lvExpreceipt.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    }

    // CALL PaymentLedger API HERE
    private void callPaymentLedgerApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getPaymentLedger(getPaymentLedgerDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<AccountFeesStatusModel>() {
            @Override
            public void success(AccountFeesStatusModel paymentLedgerModel, Response response) {
                Utils.dismissDialog();
                if (paymentLedgerModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (paymentLedgerModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (paymentLedgerModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentStudentLedgerBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentStudentLedgerBinding.expHeader.setVisibility(View.GONE);
                    fragmentStudentLedgerBinding.detailStudent.setVisibility(View.GONE);
                    fragmentStudentLedgerBinding.accText.setVisibility(View.GONE);
                    return;
                }
                if (paymentLedgerModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayPaymentLedgerModelList = paymentLedgerModel.getFinalArray();
                    if (finalArrayPaymentLedgerModelList != null) {
                        fragmentStudentLedgerBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentStudentLedgerBinding.expHeader.setVisibility(View.VISIBLE);
                        fragmentStudentLedgerBinding.detailStudent.setVisibility(View.VISIBLE);
                        fragmentStudentLedgerBinding.accText.setVisibility(View.VISIBLE);
                        fillExpLV();
                        expandableListAdapterAccountSummary = new ExpandableListAdapterAccountSummary(getActivity(), listDataHeader, listDataChild);
                        fragmentStudentLedgerBinding.lvExpaccountsummary.setAdapter(expandableListAdapterAccountSummary);
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

    private Map<String, String> getAllPaymentLedgerDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("studentid", FinalStudentIdStr);
        map.put("TermID", FinalTermIdStr);
        return map;
    }

    // CALL PaymentLedger API HERE
    private void callAllPaymentLedgerApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getAllPaymentLedger(getAllPaymentLedgerDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<AccountFeesStatusModel>() {
            @Override
            public void success(AccountFeesStatusModel allPaymentLedgerModel, Response response) {
                Utils.dismissDialog();
                if (allPaymentLedgerModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (allPaymentLedgerModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (allPaymentLedgerModel.getSuccess().equalsIgnoreCase("false")) {
//                    Utils.ping(mContext, getString(R.string.false_msg));
//                    fragmentStudentLedgerBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentStudentLedgerBinding.linearRecipt.setVisibility(View.GONE);
                    return;
                }
                if (allPaymentLedgerModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayAllPaymentLedgerModelList = allPaymentLedgerModel.getFinalArray();
                    if (finalArrayAllPaymentLedgerModelList != null) {
//                        fragmentStudentLedgerBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentStudentLedgerBinding.linearRecipt.setVisibility(View.VISIBLE);
                        fillExpLVReceipt();
                        expandableListAdapterReceipt = new ExpandableListAdapterReceipt(getActivity(), listDataHeaderreceipt, listDataChildreceipt);
                        fragmentStudentLedgerBinding.lvExpreceipt.setAdapter(expandableListAdapterReceipt);
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

    private Map<String, String> getPaymentLedgerDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("studentid", FinalStudentIdStr);
        map.put("TermID", FinalTermIdStr);
        return map;
    }

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

        spinnerTermMap = new HashMap<Integer, String>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerTermMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentLedgerBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentStudentLedgerBinding.termSpinner.setAdapter(adapterTerm);

        FinalTermIdStr = spinnerTermMap.get(0);
//        callPaymentLedgerApi();
//        callAllPaymentLedgerApi();
    }

    // CALL Student Name API HERE
    private void callStudentNameApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStudentName(getStudentDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel studentNameModel, Response response) {
//                Utils.dismissDialog();
                if (studentNameModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentNameModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentNameModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));

                    return;
                }
                if (studentNameModel.getSuccess().equalsIgnoreCase("True")) {
//                    finalArrayStudentNameModelList = studentNameModel.getFinalArray();
                    fillStudentName();
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

    public void fillStudentName() {
        ArrayList<Integer> StudentId = new ArrayList<Integer>();
        for (int i = 0; i < finalArrayStudentNameModelList.size(); i++) {
//            StudentId.add(Integer.valueOf(finalArrayStudentNameModelList.get(i).getStudentID()));
        }
        ArrayList<String> Student = new ArrayList<String>();
        ArrayList<String> Grno = new ArrayList<String>();
//        for (int j = 0; j < finalArrayStudentNameModelList.size(); j++) {
//            Student.add(finalArrayStudentNameModelList.get(j).getName());
//            Grno.add(finalArrayStudentNameModelList.get(j).getgRNO());
//        }

        if (searchtypeStr.equalsIgnoreCase("Current Student")) {
            fragmentStudentLedgerBinding.testTxt.setText(R.string.searchstudent);
            fragmentStudentLedgerBinding.studentnameTxt.setHint("Student Name");
            String[] spinnerstudentIdArray = new String[StudentId.size()];

            spinnerStudentMap = new HashMap<Integer, String>();
            for (int i = 0; i < StudentId.size(); i++) {
                spinnerStudentMap.put(i, String.valueOf(StudentId.get(i)));
                spinnerstudentIdArray[i] = Student.get(i).trim();
            }
            ArrayAdapter adb = new ArrayAdapter(mContext, R.layout.spinner_layout, spinnerstudentIdArray);
            fragmentStudentLedgerBinding.studentnameTxt.setThreshold(1);
            fragmentStudentLedgerBinding.studentnameTxt.setAdapter(adb);
        } else {

            fragmentStudentLedgerBinding.testTxt.setText(R.string.grno);
            fragmentStudentLedgerBinding.studentnameTxt.setHint("GRNO");
            String[] spinnerstudentIdArray = new String[StudentId.size()];

            spinnerStudentMap = new HashMap<Integer, String>();
            for (int i = 0; i < StudentId.size(); i++) {
                spinnerStudentMap.put(i, String.valueOf(StudentId.get(i)));
                spinnerstudentIdArray[i] = Grno.get(i).trim();
            }
            ArrayAdapter adb = new ArrayAdapter(mContext, R.layout.spinner_layout, spinnerstudentIdArray);
            fragmentStudentLedgerBinding.studentnameTxt.setThreshold(1);
            fragmentStudentLedgerBinding.studentnameTxt.setAdapter(adb);
        }

        FinalStudentIdStr = spinnerStudentMap.get(0);
    }

    private Map<String, String> getStudentDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("SearchType", "Current Student");
        map.put("InputValue", studentNameStr);
        return map;
    }

    // CALL Term API HERE
    private void callTermApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTerm(getTermDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<TermModel>() {
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
        return map;
    }


    public void fillExpLV() {
        for (int m = 0; m < finalArrayPaymentLedgerModelList.size(); m++) {
            fragmentStudentLedgerBinding.termTxt.setText(finalArrayPaymentLedgerModelList.get(m).getTerm());
            fragmentStudentLedgerBinding.nameTxt.setText(finalArrayPaymentLedgerModelList.get(m).getStudentName());
            fragmentStudentLedgerBinding.gradetxt.setText(finalArrayPaymentLedgerModelList.get(m).getStandard() + "-" + finalArrayPaymentLedgerModelList.get(m).getClassName());
            fragmentStudentLedgerBinding.smsTxt.setText(finalArrayPaymentLedgerModelList.get(m).getsMSNo());
            fragmentStudentLedgerBinding.dojTxt.setText(finalArrayPaymentLedgerModelList.get(m).getDateOfJoining());
        }

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, ArrayList<FinalArrayAccountFeesModel>>();
        ArrayList<String> account = new ArrayList<>();
        account.add("Admission Fees");
        account.add("Tution Fees");
        account.add("Caution Fees");
        account.add("Transport Fees");
        account.add("Imprest Fees");
        account.add("Late Fees");
        account.add("Discount Fees");
        account.add("Fees");

        for (int i = 0; i < account.size(); i++) {
            Log.d("account", "" + account);
            listDataHeader.add(account.get(i));
            Log.d("header", "" + listDataHeader);
            ArrayList<FinalArrayAccountFeesModel> row = new ArrayList<FinalArrayAccountFeesModel>();
            for (int j = 0; j < finalArrayPaymentLedgerModelList.size(); j++) {
                row.add(finalArrayPaymentLedgerModelList.get(j));
            }
            Log.d("row", "" + row);
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }
    }

    public void fillExpLVReceipt() {

        listDataHeaderreceipt = new ArrayList<>();
        listDataChildreceipt = new HashMap<String, ArrayList<AccountFeesCollectionModel>>();

        for (int i = 0; i < finalArrayAllPaymentLedgerModelList.size(); i++) {
            listDataHeaderreceipt.add(finalArrayAllPaymentLedgerModelList.get(i).getPayDate() + "|" + finalArrayAllPaymentLedgerModelList.get(i).getPaid());
            Log.d("header", "" + listDataHeaderreceipt);
            ArrayList<AccountFeesCollectionModel> row = new ArrayList<AccountFeesCollectionModel>();
            for (int j = 0; j < finalArrayAllPaymentLedgerModelList.get(i).getData().size(); j++) {
                row.add(finalArrayAllPaymentLedgerModelList.get(i).getData().get(j));
                Log.d("row", "" + row);
            }
            listDataChildreceipt.put(listDataHeaderreceipt.get(i), row);
            Log.d("child", "" + listDataChildreceipt);
        }
    }
}

