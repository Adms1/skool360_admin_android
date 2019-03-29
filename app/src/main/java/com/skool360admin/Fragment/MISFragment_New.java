package com.skool360admin.Fragment;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.MISListAdapter;
import anandniketan.com.bhadajadmin.Adapter.SimpleSectionedRecyclerViewAdapter;
import anandniketan.com.bhadajadmin.Model.MISModel;
import anandniketan.com.bhadajadmin.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.bhadajadmin.Model.Transport.TermModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentMisNewBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MISFragment_New extends Fragment {

    private Button btnBack,btnMenu;
    private View view;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    FragmentMisNewBinding fragmentMisBinding;
    //FragmentMisBinding fragmentMisBinding;
    private List<FinalArrayGetTermModel> finalArrayGetTermModels;
    HashMap<Integer, String> spinnerTermMap;
    private String FinalTermIdStr = "";
    private HashMap<String,String> studentDataList  = new HashMap<String, String>();
    private HashMap<String,String> staffDataList  = new HashMap<String, String>();
    private HashMap<String,String> accountDataList  = new HashMap<String, String>();
    private HashMap<String,String> newAdmissionDataList  = new HashMap<String, String>();
    private HashMap<String,String> messagetDataList  = new HashMap<String, String>();
    private MISListAdapter misListAdapter;
    private List<SimpleSectionedRecyclerViewAdapter.Section> sections;
    private SimpleSectionedRecyclerViewAdapter.Section[] dummy;
    private SimpleSectionedRecyclerViewAdapter mSectionedAdapter;
    private ArrayList<String> dataValues;
    private ArrayList<String> keyValues;
    public MISFragment_New() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMisBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_mis_new,container,false);
        rootView = fragmentMisBinding.getRoot();

        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 5;
        mContext = getActivity().getApplicationContext();


        fragmentMisBinding.rvMisdatalist.setHasFixedSize(true);
        fragmentMisBinding.rvMisdatalist.setLayoutManager(new LinearLayoutManager(getActivity()));
        //fragmentMisBinding.rvMisdatalist.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));

        dataValues = new ArrayList<String>();
        keyValues = new ArrayList<String>();


        dataValues.add("--");
        keyValues.add("--");




        setListners();
        try {
            callTermApi();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return rootView;
    }


    public void setListners() {
        fragmentMisBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfiguration.ReverseTermDetailId = "";
                fragment = new HomeFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentMisBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        fragmentMisBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentMisBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentMisBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid.toString();
                Log.d("FinalTermIdStr", FinalTermIdStr);
                AppConfiguration.TermName = name;

                sections = new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0,"Student"));
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(6,"Staff"));
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(16,"Account"));
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(25,"New Addmission"));
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(33,"Message"));

                dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];

                misListAdapter = new MISListAdapter(getActivity(),dataValues,keyValues);


                mSectionedAdapter =  new SimpleSectionedRecyclerViewAdapter(getActivity(),R.layout.list_item_mis_data_header,R.id.title_txt,misListAdapter);
                mSectionedAdapter.setSections(sections.toArray(dummy));


                fragmentMisBinding.rvMisdatalist.setAdapter(mSectionedAdapter);


                try {
                    callStudentMISDataApi("Student");
                }catch (Exception ex){
                    ex.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void callTermApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTerm(getTermDetail(),new retrofit.Callback<TermModel>() {
            @Override
            public void success(TermModel termModel, Response response) {
                Utils.dismissDialog();
                if (termModel == null) {
                    Utils.ping(mContext,getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess() == null) {
                    Utils.ping(mContext,getString(R.string.something_wrong));
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentMisBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,R.layout.spinner_layout,spinnertermIdArray) {

            public View getView(int position, View convertView,ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                //((TextView) v).setTextSize(16);
                ((TextView) v).setGravity(Gravity.CENTER_HORIZONTAL);

                return v;

            }

            public View getDropDownView(int position, View convertView,ViewGroup parent) {

                View v = super.getDropDownView(position, convertView,parent);

                ((TextView) v).setGravity(Gravity.LEFT);

                return v;

            }

        };



        fragmentMisBinding.termSpinner.setAdapter(adapter);
        FinalTermIdStr = spinnerTermMap.get(0);
    }


    private void callStudentMISDataApi(String requestType) {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error),getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getMISdata(getParams(requestType), new retrofit.Callback<MISModel>() {
            @Override
            public void success(MISModel staffSMSDataModel, Response response) {
                Utils.dismissDialog();
                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext,getString(R.string.false_msg));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {

                    Gson gsonData = new Gson();
                    String dataStudent = gsonData.toJson(staffSMSDataModel);

                    try {
                        studentDataList = Utils.getValuesFromJson(dataStudent,"Student");

                        ArrayList<String> keys  = new ArrayList<String>();

                        for (String key :studentDataList.keySet()) {
                            keys.add(key);

                        }
                        ArrayList<String> values  = new ArrayList<String>();

                        for(int count = 0;count<studentDataList.size();count++){
                            values.add(studentDataList.get(keys.get(count)));
                        }

                        dataValues.clear();
                        keyValues.clear();


                        dataValues.addAll(values);
                        keyValues.addAll(keys);

                        misListAdapter.notifyDataSetChanged();
                      //  mSectionedAdapter.notifyDataSetChanged();

                        //This is the code to provide a sectioned list

                        //Sections

                        //Add your adapter to the sectionAdapter

                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }



                    try {

                        callStafftMISDataApi("Staff");


                    }catch (Exception ex){
                        ex.printStackTrace();
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


    private void callStafftMISDataApi(String requestType) {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error),getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getMISdata(getParams(requestType), new retrofit.Callback<MISModel>() {
            @Override
            public void success(MISModel staffSMSDataModel, Response response) {
                Utils.dismissDialog();
                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext,getString(R.string.false_msg));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {

                    Gson gsonData = new Gson();
                    String dataStudent = gsonData.toJson(staffSMSDataModel);

                    try {
                        studentDataList = Utils.getValuesFromJson(dataStudent,"Staff");

                        ArrayList<String> keys  = new ArrayList<String>();

                        for (String key :studentDataList.keySet()) {
                            keys.add(key);

                        }
                        ArrayList<String> values  = new ArrayList<String>();

                        for(int count = 0;count<studentDataList.size();count++){
                            values.add(studentDataList.get(keys.get(count)));
                        }




                        dataValues.addAll(values);
                        keyValues.addAll(keys);

                        misListAdapter.notifyDataSetChanged();
                        //  mSectionedAdapter.notifyDataSetChanged();


                        //This is the code to provide a sectioned list

                        //Sections



                        //Add your adapter to the sectionAdapter


                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }


                    try {
                        callAccounttMISDataApi("Account");
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext,error.getMessage());
            }
        });

    }


    private void callAccounttMISDataApi(String requestType) {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error),getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getMISdata(getParams(requestType), new retrofit.Callback<MISModel>() {
            @Override
            public void success(MISModel staffSMSDataModel, Response response) {
                Utils.dismissDialog();
                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext,getString(R.string.false_msg));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {

                    Gson gsonData = new Gson();
                    String dataStudent = gsonData.toJson(staffSMSDataModel);

                    try {
                        studentDataList = Utils.getValuesFromJson(dataStudent,"Account");

                        ArrayList<String> keys  = new ArrayList<String>();

                        for (String key :studentDataList.keySet()) {
                            keys.add(key);

                        }
                        ArrayList<String> values  = new ArrayList<String>();

                        for(int count = 0;count<studentDataList.size();count++){
                            values.add(studentDataList.get(keys.get(count)));
                        }




                        dataValues.addAll(values);
                        keyValues.addAll(keys);

                        misListAdapter.notifyDataSetChanged();
                        //  mSectionedAdapter.notifyDataSetChanged();


                        //This is the code to provide a sectioned list

                        //Sections



                        //Add your adapter to the sectionAdapter


                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }



                    try {
                        callNAMISDataApi("New Addmission");

                    }catch (Exception ex){
                        ex.printStackTrace();
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


    private void callNAMISDataApi(String requestType) {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error),getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getMISdata(getParams(requestType), new retrofit.Callback<MISModel>() {
            @Override
            public void success(MISModel staffSMSDataModel, Response response) {
                Utils.dismissDialog();
                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext,getString(R.string.false_msg));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {


                    Gson gsonData = new Gson();
                    String dataStudent = gsonData.toJson(staffSMSDataModel);

                    try {
                        studentDataList = Utils.getValuesFromJson(dataStudent,"NA");

                        ArrayList<String> keys  = new ArrayList<String>();

                        for (String key :studentDataList.keySet()) {
                            keys.add(key);

                        }
                        ArrayList<String> values  = new ArrayList<String>();

                        for(int count = 0;count<studentDataList.size();count++){
                            values.add(studentDataList.get(keys.get(count)));
                        }




                        dataValues.addAll(values);
                        keyValues.addAll(keys);

                        misListAdapter.notifyDataSetChanged();
                        //  mSectionedAdapter.notifyDataSetChanged();


                        //This is the code to provide a sectioned list

                        //Sections



                        //Add your adapter to the sectionAdapter


                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }




                    try {
                        callMessgeMISDataApi("Message");

                    }catch (Exception ex){
                        ex.printStackTrace();
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



    private void callMessgeMISDataApi(String requestType) {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error),getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getMISdata(getParams(requestType), new retrofit.Callback<MISModel>() {
            @Override
            public void success(MISModel staffSMSDataModel, Response response) {
                Utils.dismissDialog();
                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext,getString(R.string.false_msg));
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {

                    Gson gsonData = new Gson();
                    String dataStudent = gsonData.toJson(staffSMSDataModel);

                    try {
                        studentDataList = Utils.getValuesFromJson(dataStudent,"Message");

                        ArrayList<String> keys  = new ArrayList<String>();

                        for (String key :studentDataList.keySet()) {
                            keys.add(key);

                        }
                        ArrayList<String> values  = new ArrayList<String>();

                        for(int count = 0;count<studentDataList.size();count++){
                            values.add(studentDataList.get(keys.get(count)));
                        }




                        dataValues.addAll(values);
                        keyValues.addAll(keys);

                        misListAdapter.notifyDataSetChanged();
                        //  mSectionedAdapter.notifyDataSetChanged();


                        //This is the code to provide a sectioned list

                        //Sections



                        //Add your adapter to the sectionAdapter


                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    try {

                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext,getString(R.string.something_wrong));
            }
        });

    }





    private Map<String,String> getParams(String params) {
        Map<String, String> map = new HashMap<>();
        map.put("Date",Utils.getTodaysDate());
        map.put("TermID",FinalTermIdStr);
        map.put("RequestType",params);
        return map;
    }


}
