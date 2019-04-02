package anandniketan.com.skool360.Fragment.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.TestNameAdapter;
import anandniketan.com.skool360.Model.Account.FinalArrayStandard;
import anandniketan.com.skool360.Model.Account.GetStandardModel;
import anandniketan.com.skool360.Model.Staff.ViewMarksModel;
import anandniketan.com.skool360.Model.Student.FinalArrayStudentModel;
import anandniketan.com.skool360.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.skool360.Model.Transport.TermModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.DialogUtils;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ViewMarksFragment extends Fragment {

    private Spinner termSpinner, gradeSpinner, sectionSpinner;
    private Button btnView, btnBack, btnMenu;
    private TextView tvHeader;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private HashMap<Integer, String> spinnerTermMap, spinnerStandardMap, spinnerSectionMap;
    private String FinalTermIdStr, FinalClassIdStr, FinalStandardIdStr, StandardName, FinalStandardStr, FinalTermName, FinalStandardectionStr, FinalSectionIdStr, FinalTestIDs;
    private List<FinalArrayGetTermModel> finalArrayGetTermModels;
    private TestNameAdapter testNameAdapter;
    private ArrayList<String> testids = new ArrayList<>();
    private List<FinalArrayStandard> finalArrayStandardsList;
    private List<FinalArrayStudentModel.FinalArray> finalArrayStudentNameModelList;
    private GridView gradeGridView;

    public ViewMarksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_marks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        termSpinner = view.findViewById(R.id.viewmarks_term_spinner);
        gradeSpinner = view.findViewById(R.id.viewmarks_grade_spinner);
        sectionSpinner = view.findViewById(R.id.viewmarks_section_sp);
        btnView = view.findViewById(R.id.viewmarks_view_btn);
        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);
        gradeGridView = view.findViewById(R.id.viewmarks_grade_grid_view);

        setListener();
        callTermApi();
        callStandardApi();
    }

    private void setListener() {

        tvHeader.setText(R.string.view_marks);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StaffFragment();
                fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callViewMaks();
            }
        });

        termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid;
                FinalTermName = name;
                Log.d("FinalTermIdStr", FinalTermIdStr);
                callTestNameApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = gradeSpinner.getSelectedItem().toString();
                String getid = spinnerStandardMap.get(gradeSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalStandardIdStr = getid;
                Log.d("FinalStandardIdStr", FinalStandardIdStr);
                StandardName = name;
                FinalStandardStr = name;
                Log.d("StandardName", StandardName);
                fillSection();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = sectionSpinner.getSelectedItem().toString();
                String getid = spinnerSectionMap.get(sectionSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalSectionIdStr = getid;
                Log.d("FinalStandardIdStr", FinalSectionIdStr);
                StandardName = name;
                FinalStandardectionStr = name;
                Log.d("StandardName", StandardName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private HashMap<String, String> getURLDetail() {

        testids = testNameAdapter.getDatas();

        FinalTestIDs = "";

        StringBuilder sb = new StringBuilder();
        for (String s : testids) {
            FinalTestIDs = TextUtils.join("/", testids);
            sb.append(s);
            sb.append("/");
//            FinalTestIDs += s + "/";
        }

        Log.d("testidsssssss", FinalTermIdStr + "," + FinalTermName + "\n" + FinalTestIDs + "\n" +
                "\n" + FinalStandardIdStr + "," + FinalStandardStr + "\n" + FinalSectionIdStr + "," + FinalStandardectionStr);

        HashMap<String, String> map = new HashMap<>();

        if (!FinalTestIDs.equalsIgnoreCase("")) {

            map.put("TermID", FinalTermIdStr + "," + FinalTermName);
            map.put("TestIDs", FinalTestIDs);
            map.put("GradeID", FinalStandardIdStr + "," + FinalStandardStr);
            map.put("SectionID", FinalSectionIdStr + "," + FinalStandardectionStr);

        } else {

            DialogUtils.createConfirmDialog(getActivity(), "Please select any one test", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();

        }

        return map;
    }

    private void callViewMaks() {
        if (!Utils.checkNetwork(getActivity())) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStudentViewMarks(getURLDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<ViewMarksModel>() {
            @Override
            public void success(ViewMarksModel termModel, Response response) {
                Utils.dismissDialog();
                if (termModel == null) {
//                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess() == null) {
//                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(getActivity(), getString(R.string.false_msg));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
                    DialogUtils.showWebviewDialog(getActivity(), AppConfiguration.LIVE_BASE_URL + termModel.getFinalArray().get(0).getUrl());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
//                Utils.ping(getActivity(), getString(R.string.something_wrong));
            }
        });
    }

    // CALL Term API HERE
    private void callTermApi() {

        if (!Utils.checkNetwork(getActivity())) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTerm(getTermDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<TermModel>() {
            @Override
            public void success(TermModel termModel, Response response) {
                Utils.dismissDialog();
                if (termModel == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess() == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(getActivity(), getString(R.string.false_msg));
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
                Utils.ping(getActivity(), getString(R.string.something_wrong));
            }
        });
    }

    private Map<String, String> getTermDetail() {
        return new HashMap<>();
    }

    public void fillTermSpinner() {
        ArrayList<Integer> TermId = new ArrayList<>();
        for (int i = 0; i < finalArrayGetTermModels.size(); i++) {
            TermId.add(finalArrayGetTermModels.get(i).getTermId());
        }
        ArrayList<String> Term = new ArrayList<>();
        for (int j = 0; j < finalArrayGetTermModels.size(); j++) {
            Term.add(finalArrayGetTermModels.get(j).getTerm());
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerTermMap = new HashMap<>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerTermMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }

//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentSearchStudentBinding.termSpinner);
//
//            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout, spinnertermIdArray);
        termSpinner.setAdapter(adapterTerm);
        termSpinner.setSelection(1);

    }

    // CALL Standard API HERE
    private void callStandardApi() {

        if (!Utils.checkNetwork(getActivity())) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStandardDetail(getTermDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<GetStandardModel>() {
            @Override
            public void success(GetStandardModel standardModel, Response response) {
                Utils.dismissDialog();
                if (standardModel == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (standardModel.getSuccess() == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(getActivity(), getString(R.string.false_msg));
                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayStandardsList = standardModel.getFinalArray();
                    if (finalArrayStandardsList != null) {
                        fillGradeSpinner();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(getActivity(), getString(R.string.something_wrong));
            }
        });
    }

    public void fillGradeSpinner() {
//        ArrayList<String> firstValue = new ArrayList<>();
//        firstValue.add("All");
//
        ArrayList<String> standardname = new ArrayList<>();
//        for (int z = 0; z < firstValue.size(); z++) {
//            standardname.add(firstValue.get(z));
        for (int i = 0; i < finalArrayStandardsList.size(); i++) {
            standardname.add(finalArrayStandardsList.get(i).getStandard());
        }
//        }
//        ArrayList<Integer> firstValueId = new ArrayList<>();
//        firstValueId.add(0);
        ArrayList<Integer> standardId = new ArrayList<>();
//        for (int m = 0; m < firstValueId.size(); m++) {
//            standardId.add(firstValueId.get(m));
        for (int j = 0; j < finalArrayStandardsList.size(); j++) {
            standardId.add(finalArrayStandardsList.get(j).getStandardID());
        }
//        }
        String[] spinnerstandardIdArray = new String[standardId.size()];

        spinnerStandardMap = new HashMap<Integer, String>();
        for (int i = 0; i < standardId.size(); i++) {
            spinnerStandardMap.put(i, String.valueOf(standardId.get(i)));
            spinnerstandardIdArray[i] = standardname.get(i).trim();
        }

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(gradeSpinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 500 : spinnerstandardIdArray.length * 100);
//            popupWindow1.setHeght(200);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }


        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, spinnerstandardIdArray);
        gradeSpinner.setAdapter(adapterstandard);

        FinalStandardIdStr = spinnerStandardMap.get(0);
    }

    public void fillSection() {
        ArrayList<String> sectionname = new ArrayList<>();
        ArrayList<Integer> sectionId = new ArrayList<>();
        ArrayList<String> firstSectionValue = new ArrayList<String>();
        firstSectionValue.add("All");
        ArrayList<Integer> firstSectionId = new ArrayList<>();
//        firstSectionId.add(0);

//        if (StandardName.equalsIgnoreCase("All")) {
//            for (int j = 0; j < firstSectionValue.size(); j++) {
//                sectionname.add(firstSectionValue.get(j));
//            }
//            for (int i = 0; i < firstSectionId.size(); i++) {
//                sectionId.add(firstSectionId.get(i));
//            }
//
//        }
        for (int z = 0; z < finalArrayStandardsList.size(); z++) {
            if (StandardName.equalsIgnoreCase(finalArrayStandardsList.get(z).getStandard())) {
//                for (int j = 0; j < firstSectionValue.size(); j++) {
//                    sectionname.add(firstSectionValue.get(j));
                for (int i = 0; i < finalArrayStandardsList.get(z).getSectionDetail().size(); i++) {
                    sectionname.add(finalArrayStandardsList.get(z).getSectionDetail().get(i).getSection());
//                    }
                }
//                for (int j = 0; j < firstSectionId.size(); j++) {
//                    sectionId.add(firstSectionId.get(j));
                for (int m = 0; m < finalArrayStandardsList.get(z).getSectionDetail().size(); m++) {
                    sectionId.add(finalArrayStandardsList.get(z).getSectionDetail().get(m).getSectionID());
                }
//                }
            }
        }

        String[] spinnersectionIdArray = new String[sectionId.size()];

        spinnerSectionMap = new HashMap<>();
        for (int i = 0; i < sectionId.size(); i++) {
            spinnerSectionMap.put(i, String.valueOf(sectionId.get(i)));
            spinnersectionIdArray[i] = sectionname.get(i).trim();
        }
//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentAttendaneBinding.sectionSpinner);
//
//            popupWindow.setHeight(spinnersectionIdArray.length > 4 ? 500 : spinnersectionIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }
        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, spinnersectionIdArray);
        sectionSpinner.setAdapter(adapterstandard);

        FinalClassIdStr = spinnerSectionMap.get(0);

    }

    // CALL TestName API HERE
    private void callTestNameApi() {

        if (!Utils.checkNetwork(getActivity())) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTestName(getTestNameDetail(), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"), new retrofit.Callback<FinalArrayStudentModel>() {
            @Override
            public void success(FinalArrayStudentModel testNameModel, Response response) {
                Utils.dismissDialog();
                if (testNameModel == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (testNameModel.getSuccess() == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (testNameModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(getActivity(), getString(R.string.false_msg));
                    return;
                }
                if (testNameModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayStudentNameModelList = testNameModel.getFinalArray();
                    if (finalArrayStudentNameModelList != null) {
                        for (int i = 0; i < finalArrayStudentNameModelList.size(); i++) {
                            finalArrayStudentNameModelList.get(i).setCheckedStatus("0");
                        }
                        testNameAdapter = new TestNameAdapter(getActivity(), finalArrayStudentNameModelList);
                        gradeGridView.setAdapter(testNameAdapter);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(getActivity(), getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getTestNameDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("TermID", FinalTermIdStr);
        return map;
    }


}
