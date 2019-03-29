package anandniketan.com.bhadajadmin.Fragment.Fragment;

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
import android.widget.ExpandableListView;
import android.widget.Spinner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.ExpandableListAdapterStudentDiscount;
import anandniketan.com.bhadajadmin.Model.Account.AccountFeesStatusModel;
import anandniketan.com.bhadajadmin.Model.Account.FinalArrayAccountFeesModel;
import anandniketan.com.bhadajadmin.Model.Account.FinalArrayStandard;
import anandniketan.com.bhadajadmin.Model.Account.GetStandardModel;
import anandniketan.com.bhadajadmin.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.bhadajadmin.Model.Transport.TermModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentStudentDiscountBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class StudentDiscountFragment extends Fragment {

    private FragmentStudentDiscountBinding fragmentStudentDiscountBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private int lastExpandedPosition = -1;
    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    List<FinalArrayStandard> finalArrayStandardsList;
    HashMap<Integer, String> spinnerTermMap;
    HashMap<Integer, String> spinnerdiscountTypeMap;
    HashMap<Integer, String> spinnerStandardMap;
    String FinalTermIdStr, FinaldiscountypeIdStr = "", FinalstandardIdStr = "";
    List<FinalArrayAccountFeesModel> finalArrayDiscountModelList;
    List<String> listDataHeader;
    HashMap<String, ArrayList<FinalArrayAccountFeesModel>> listDataChild;
    ExpandableListAdapterStudentDiscount expandableListAdapterStudentDiscount;

    public StudentDiscountFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStudentDiscountBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_discount, container, false);

        rootView = fragmentStudentDiscountBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setListners();
        callTermApi();
        callStandardApi();

        return rootView;
    }


    public void setListners() {
        fragmentStudentDiscountBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentStudentDiscountBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new AccountFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentStudentDiscountBinding.lvExpstudentdiscount.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {


            @Override

            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    fragmentStudentDiscountBinding.lvExpstudentdiscount.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }

        });
        fragmentStudentDiscountBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentStudentDiscountBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentStudentDiscountBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid.toString();
                Log.d("FinalTermIdStr", FinalTermIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentStudentDiscountBinding.discountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentStudentDiscountBinding.discountSpinner.getSelectedItem().toString();
                String getid = spinnerdiscountTypeMap.get(fragmentStudentDiscountBinding.discountSpinner.getSelectedItemPosition());

                Log.d("routevalue", name + " " + getid);
                FinaldiscountypeIdStr = getid.toString();
                Log.d("FinaldiscountypeIdStr", FinaldiscountypeIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentStudentDiscountBinding.standardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentStudentDiscountBinding.standardSpinner.getSelectedItem().toString();
                String getid = spinnerStandardMap.get(fragmentStudentDiscountBinding.standardSpinner.getSelectedItemPosition());


                FinalstandardIdStr = getid.toString();
                Log.d("FinalstandardIdStr", FinalstandardIdStr);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentStudentDiscountBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callStudentDiscountDetailApi();
            }
        });
    }


    // CALL Term API HERE
    private void callTermApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTerm(getTermDetail(), new retrofit.Callback<TermModel>() {
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
                        fillDiscountSpinner();
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


    // CALL Standard API HERE
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
                        fillStandardSpinner();
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

    private Map<String, String> getStandardDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    // CALL Term API HERE
    private void callStudentDiscountDetailApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getDiscountDetail(getStudentgetDiscountDetail(), new retrofit.Callback<AccountFeesStatusModel>() {
            @Override
            public void success(AccountFeesStatusModel discountDetailsModel, Response response) {
//                Utils.dismissDialog();
                if (discountDetailsModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (discountDetailsModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (discountDetailsModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.dismissDialog();
//                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentStudentDiscountBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentStudentDiscountBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentStudentDiscountBinding.lvExpstudentdiscount.setVisibility(View.GONE);
                    return;
                }
                if (discountDetailsModel.getSuccess().equalsIgnoreCase("True")) {

                    finalArrayDiscountModelList = discountDetailsModel.getFinalArray();
                    if (finalArrayDiscountModelList != null) {
                        if (finalArrayDiscountModelList.size() > 0) {
                            fragmentStudentDiscountBinding.txtNoRecords.setVisibility(View.GONE);
                            fragmentStudentDiscountBinding.lvExpHeader.setVisibility(View.VISIBLE);
                            fragmentStudentDiscountBinding.lvExpstudentdiscount.setVisibility(View.VISIBLE);

                            fillExpLV();
                            expandableListAdapterStudentDiscount = new ExpandableListAdapterStudentDiscount(getActivity(), listDataHeader, listDataChild);
                            fragmentStudentDiscountBinding.lvExpstudentdiscount.setAdapter(expandableListAdapterStudentDiscount);
                            Utils.dismissDialog();
                        } else {
                            Utils.dismissDialog();
                            fragmentStudentDiscountBinding.txtNoRecords.setVisibility(View.VISIBLE);
                            fragmentStudentDiscountBinding.lvExpHeader.setVisibility(View.GONE);
                            fragmentStudentDiscountBinding.lvExpstudentdiscount.setVisibility(View.GONE);
                        }
                    } else {
                        fragmentStudentDiscountBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentStudentDiscountBinding.lvExpHeader.setVisibility(View.GONE);
                        fragmentStudentDiscountBinding.lvExpstudentdiscount.setVisibility(View.GONE);
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

    private Map<String, String> getStudentgetDiscountDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("TermID", FinalTermIdStr);
        map.put("Standard", FinalstandardIdStr);
        map.put("DiscType", FinaldiscountypeIdStr);

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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentDiscountBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentStudentDiscountBinding.termSpinner.setAdapter(adapterTerm);

        FinalTermIdStr=spinnerTermMap.get(0);
    }

    public void fillDiscountSpinner() {
        ArrayList<Integer> discounttypeId = new ArrayList<>();
        discounttypeId.add(1);
        discounttypeId.add(2);
        discounttypeId.add(3);

        ArrayList<String> discounttype = new ArrayList<>();
        discounttype.add("Individual");
        discounttype.add("All");
        discounttype.add("Waive Off On Previous Year");

        String[] spinnerdiscounttypeIdArray = new String[discounttypeId.size()];

        spinnerdiscountTypeMap = new HashMap<Integer, String>();
        for (int i = 0; i < discounttypeId.size(); i++) {
            spinnerdiscountTypeMap.put(i, String.valueOf(discounttypeId.get(i)));
            spinnerdiscounttypeIdArray[i] = discounttype.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentDiscountBinding.discountSpinner);

            popupWindow.setHeight(spinnerdiscounttypeIdArray.length > 4 ? 500 : spinnerdiscounttypeIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerdiscounttypeIdArray);
        fragmentStudentDiscountBinding.discountSpinner.setAdapter(adapterTerm);

        FinaldiscountypeIdStr = spinnerdiscountTypeMap.get(0) ;
    }

    public void fillStandardSpinner() {
        ArrayList<Integer> standardId = new ArrayList<Integer>();
        for (int i = 0; i < finalArrayStandardsList.size(); i++) {
            standardId.add(finalArrayStandardsList.get(i).getStandardID());
        }
        ArrayList<String> Standard = new ArrayList<String>();
        for (int j = 0; j < finalArrayStandardsList.size(); j++) {
            Standard.add(finalArrayStandardsList.get(j).getStandard());
        }

        String[] spinnerstandardIdArray = new String[standardId.size()];

        spinnerStandardMap = new HashMap<Integer, String>();
        for (int i = 0; i < standardId.size(); i++) {
            spinnerStandardMap.put(i, String.valueOf(standardId.get(i)));
            spinnerstandardIdArray[i] = Standard.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentDiscountBinding.standardSpinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 500 : spinnerstandardIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        fragmentStudentDiscountBinding.standardSpinner.setAdapter(adapterTerm);




        FinalstandardIdStr = spinnerStandardMap.get(0);
        callStudentDiscountDetailApi();
    }

    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, ArrayList<FinalArrayAccountFeesModel>>();

        for (int i = 0; i < finalArrayDiscountModelList.size(); i++) {
            listDataHeader.add(finalArrayDiscountModelList.get(i).getName() + "|" +
                    finalArrayDiscountModelList.get(i).getgRNO() + "|" + finalArrayDiscountModelList.get(i).getStandard());
            Log.d("header", "" + listDataHeader);
            ArrayList<FinalArrayAccountFeesModel> row = new ArrayList<FinalArrayAccountFeesModel>();
            row.add(finalArrayDiscountModelList.get(i));
            Log.d("row", "" + row);
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }

    }


}

