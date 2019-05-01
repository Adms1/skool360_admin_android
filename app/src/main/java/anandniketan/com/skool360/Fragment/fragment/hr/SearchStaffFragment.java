package anandniketan.com.skool360.Fragment.fragment.hr;

import android.content.Context;
import android.databinding.DataBindingUtil;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Model.HR.DepartmentModel;
import anandniketan.com.skool360.Model.HR.DesignationModel;
import anandniketan.com.skool360.Model.HR.SearchStaffModel;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.activity.DashboardActivity;
import anandniketan.com.skool360.adapter.StaffSearchListItemAdapter;
import anandniketan.com.skool360.databinding.FragmentSearchStaffBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SearchStaffFragment extends Fragment {

    private Context mcontext;
    private FragmentSearchStaffBinding fragmentStudentViewInquiryBinding;
    private View rootView;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private HashMap<Integer, String> spinnerTermMap;
    private HashMap<Integer, String> spinnerDesgmMap;
    private HashMap<Integer, String> spinnerDeptmMap;
    private HashMap<Integer, String> spinnerOrderMap;

    private String FinalTermIdStr = "", FinalDesgId = "0", FinalDeptId = "0", FinalStatus = "";
    private List<FinalArrayGetTermModel> finalArrayGetTermModels;
    private List<DesignationModel.FinalArray> finalArrayGetDesgModels;
    private List<DepartmentModel.FinalArray> finalArrayGetDeptModels;
    private StaffSearchListItemAdapter staffSearchListItemAdapter;
    private List<SearchStaffModel.FinalArray> finalDatalist;

    private String viewstatus;
    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public SearchStaffFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        AppConfiguration.firsttimeback = true;
//        AppConfiguration.position = 61;

//        viewstatus = AppConfiguration.HRstaffseachviewstatus;

        fragmentStudentViewInquiryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_staff, container, false);
        rootView = fragmentStudentViewInquiryBinding.getRoot();

//        setListners();
//        callTermApi();
        return rootView;

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

        tvHeader.setText(R.string.searchstaff);

        setListners();
        callTermApi();

    }

    public void setListners() {

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new HRFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
            }
        });


        fragmentStudentViewInquiryBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentStudentViewInquiryBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentStudentViewInquiryBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid;
                AppConfiguration.TermId = FinalTermIdStr;
                Log.d("FinalTermIdStr", FinalTermIdStr);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        fragmentStudentViewInquiryBinding.desgSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentStudentViewInquiryBinding.desgSpinner.getSelectedItem().toString();
                String getid = spinnerDesgmMap.get(fragmentStudentViewInquiryBinding.desgSpinner.getSelectedItemPosition());
                // String getid = finalArrayGetDesgModels.get(fragmentStudentViewInquiryBinding.desgSpinner.getSelectedItemPosition()).getDesignationId();

                Log.d("value", name + " " + getid);
                FinalDesgId = getid;
                AppConfiguration.DesgId = FinalDesgId;

                //Log.d("FinalDesgId", FinalDesgId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        fragmentStudentViewInquiryBinding.deptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentStudentViewInquiryBinding.deptSpinner.getSelectedItem().toString();
                String getid = spinnerDeptmMap.get(fragmentStudentViewInquiryBinding.deptSpinner.getSelectedItemPosition());
                //String getid = finalArrayGetDeptModels.get(fragmentStudentViewInquiryBinding.desgSpinner.getSelectedItemPosition()).getDepartmentCode();

                Log.d("value", name + " " + getid);
                FinalDeptId = getid;
                AppConfiguration.DeptId = FinalDeptId;

                Log.d("FinalDeptIdstr", FinalDeptId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        fragmentStudentViewInquiryBinding.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentStudentViewInquiryBinding.statusSpinner.getSelectedItem().toString();
                // String getid = sp.get(fragmentStudentViewInquiryBinding.deptSpinner.getSelectedItemPosition());

                FinalStatus = name;
                Log.d("FinalStatus", FinalStatus);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        fragmentStudentViewInquiryBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callStaffListApi();
            }
        });

    }


    private void callTermApi() {

        if (!Utils.checkNetwork(mcontext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(mcontext);
        ApiHandler.getApiService().getTerm(getTermDetail(), new retrofit.Callback<TermModel>() {
            @Override
            public void success(TermModel termModel, Response response) {
                Utils.dismissDialog();
                if (termModel == null) {
                    Utils.ping(mcontext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess() == null) {
                    Utils.ping(mcontext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mcontext, getString(R.string.false_msg));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayGetTermModels = termModel.getFinalArray();
                    if (finalArrayGetTermModels != null) {
                        fillTermSpinner();
                        fillStatusSpinner();
                        callDesignationApi();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mcontext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getTermDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }


    private void callDesignationApi() {

        if (!Utils.checkNetwork(mcontext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(mcontext);
        ApiHandler.getApiService().getDesignation(getDesDetail(), new retrofit.Callback<DesignationModel>() {
            @Override
            public void success(DesignationModel termModel, Response response) {
                Utils.dismissDialog();
                if (termModel == null) {
                    Utils.ping(mcontext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess() == null) {
                    Utils.ping(mcontext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mcontext, getString(R.string.false_msg));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayGetDesgModels = termModel.getFinalArray();
                    if (finalArrayGetDesgModels != null) {
                        fillDesignationSpinner();
                        callDepartmentApi();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mcontext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getDesDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }


    private void callDepartmentApi() {

        if (!Utils.checkNetwork(mcontext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(mcontext);
        ApiHandler.getApiService().getDepartment(getDeptDetail(), new retrofit.Callback<DepartmentModel>() {
            @Override
            public void success(DepartmentModel termModel, Response response) {
                Utils.dismissDialog();
                if (termModel == null) {
                    Utils.ping(mcontext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess() == null) {
                    Utils.ping(mcontext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mcontext, getString(R.string.false_msg));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayGetDeptModels = termModel.getFinalArray();
                    if (finalArrayGetDesgModels != null) {
                        fillDepartmentnSpinner();
                        callStaffListApi();

                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mcontext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getDeptDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    private void callStaffListApi() {

        if (!Utils.checkNetwork(mcontext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(mcontext);
        ApiHandler.getApiService().getAdmin_SearchStaff(getStaffParams(), new retrofit.Callback<SearchStaffModel>() {
            @Override
            public void success(SearchStaffModel termModel, Response response) {
                Utils.dismissDialog();
                if (termModel == null) {
                    Utils.ping(mcontext, getString(R.string.something_wrong));
                    fragmentStudentViewInquiryBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentStudentViewInquiryBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentStudentViewInquiryBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (termModel.getSuccess() == null) {
                    Utils.ping(mcontext, getString(R.string.something_wrong));
                    fragmentStudentViewInquiryBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentStudentViewInquiryBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentStudentViewInquiryBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("false")) {
//                    Utils.ping(getActivity(), getString(R.string.false_msg));
                    fragmentStudentViewInquiryBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentStudentViewInquiryBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentStudentViewInquiryBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
                    finalDatalist = new ArrayList<>();
                    finalDatalist = termModel.getFinalArray();
                    fragmentStudentViewInquiryBinding.recyclerLinear.setVisibility(View.VISIBLE);
                    fragmentStudentViewInquiryBinding.lvExpHeader.setVisibility(View.VISIBLE);
                    fragmentStudentViewInquiryBinding.txtNoRecords.setVisibility(View.GONE);

                    staffSearchListItemAdapter = new StaffSearchListItemAdapter(mcontext, finalDatalist, viewstatus);
                    fragmentStudentViewInquiryBinding.staffList.setLayoutManager(new LinearLayoutManager(mcontext));
                    fragmentStudentViewInquiryBinding.staffList.setAdapter(staffSearchListItemAdapter);

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mcontext, getString(R.string.something_wrong));
                fragmentStudentViewInquiryBinding.recyclerLinear.setVisibility(View.GONE);
                fragmentStudentViewInquiryBinding.lvExpHeader.setVisibility(View.GONE);
                fragmentStudentViewInquiryBinding.txtNoRecords.setVisibility(View.VISIBLE);
                fragmentStudentViewInquiryBinding.txtNoRecords.setText(error.getMessage());
            }
        });

    }

    private Map<String, String> getStaffParams() {
        Map<String, String> map = new HashMap<>();
        map.put("TermID", FinalTermIdStr);
        map.put("DepartmentID", FinalDeptId);
        map.put("DesignationID", FinalDesgId);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        //map.put("Status",FinalStatus);
        return map;
    }

    public void fillStatusSpinner() {
        ArrayList<String> statusIdArray = new ArrayList<String>();
        statusIdArray.add("Active");
        statusIdArray.add("Inactive");


        ArrayList<String> statusdetail = new ArrayList<>();
        statusdetail.add("Active");
        statusdetail.add("Inactive");


        String[] spinnerstatusIdArray = new String[statusIdArray.size()];

        spinnerOrderMap = new HashMap<Integer, String>();
        for (int i = 0; i < statusIdArray.size(); i++) {
            spinnerOrderMap.put(i, String.valueOf(statusIdArray.get(i)));
            spinnerstatusIdArray[i] = statusdetail.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentViewInquiryBinding.statusSpinner);

            popupWindow.setHeight(spinnerstatusIdArray.length > 4 ? 500 : spinnerstatusIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mcontext, R.layout.spinner_layout, spinnerstatusIdArray);
        fragmentStudentViewInquiryBinding.statusSpinner.setAdapter(adapterTerm);

        FinalStatus = spinnerOrderMap.get(0);

    }

    public void fillTermSpinner() {
        ArrayList<Integer> TermId = new ArrayList<Integer>();


//
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentViewInquiryBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mcontext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentStudentViewInquiryBinding.termSpinner.setAdapter(adapterTerm);

        FinalTermIdStr = spinnerTermMap.get(0);
        AppConfiguration.TermId = FinalTermIdStr;
        fragmentStudentViewInquiryBinding.termSpinner.setSelection(0);

    }


    public void fillDesignationSpinner() {
        ArrayList<String> TermId = new ArrayList<String>();
        TermId.add("0");
        for (int i = 0; i < finalArrayGetDesgModels.size(); i++) {
            TermId.add(finalArrayGetDesgModels.get(i).getDesignationId());
        }
        ArrayList<String> Term = new ArrayList<String>();
        Term.add("-Select-");
        for (int j = 0; j < finalArrayGetDesgModels.size(); j++) {
            Term.add(finalArrayGetDesgModels.get(j).getDesignationName());
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerDesgmMap = new HashMap<Integer, String>();
        spinnerDesgmMap.put(0, "0");
        for (int i = 0; i < TermId.size(); i++) {
            spinnerDesgmMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentViewInquiryBinding.desgSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mcontext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentStudentViewInquiryBinding.desgSpinner.setAdapter(adapterTerm);

        FinalDesgId = spinnerDesgmMap.get(0);

        AppConfiguration.DesgId = FinalDesgId;


    }


    public void fillDepartmentnSpinner() {
        ArrayList<String> TermId = new ArrayList<String>();
        TermId.add("0");
        for (int i = 0; i < finalArrayGetDeptModels.size(); i++) {
            TermId.add(finalArrayGetDeptModels.get(i).getDepartmentId());
        }
        ArrayList<String> Term = new ArrayList<String>();
        Term.add("-Select-");
        for (int j = 0; j < finalArrayGetDeptModels.size(); j++) {
            Term.add(finalArrayGetDeptModels.get(j).getDepartmentName());
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerDeptmMap = new HashMap<Integer, String>();
        spinnerDeptmMap.put(0, "0");
        for (int i = 0; i < TermId.size(); i++) {
            spinnerDeptmMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }
//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentViewInquiryBinding.deptSpinner);
//
//            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(mcontext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentStudentViewInquiryBinding.deptSpinner.setAdapter(adapterTerm);

        FinalDeptId = spinnerDeptmMap.get(0);

        AppConfiguration.DeptId = FinalDeptId;

    }

}
