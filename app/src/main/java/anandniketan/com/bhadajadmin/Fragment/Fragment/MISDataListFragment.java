package anandniketan.com.bhadajadmin.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.ExapandableListAdapterSMSRepoetData;
import anandniketan.com.bhadajadmin.Adapter.MISAccountHeaderAdapter;
import anandniketan.com.bhadajadmin.Adapter.MISDetailListAdapter;
import anandniketan.com.bhadajadmin.Adapter.MISStudentAdapter;
import anandniketan.com.bhadajadmin.Model.Account.FinalArrayStandard;
import anandniketan.com.bhadajadmin.Model.Account.GetStandardModel;
import anandniketan.com.bhadajadmin.Model.MIS.MISAccountModel;
import anandniketan.com.bhadajadmin.Model.MIS.MISNewAdmissionModel;
import anandniketan.com.bhadajadmin.Model.MIS.MISStaffModel;
import anandniketan.com.bhadajadmin.Model.MIS.MISStaffNewDetailModel;
import anandniketan.com.bhadajadmin.Model.MIS.MISStudentModel;
import anandniketan.com.bhadajadmin.Model.MIS.MISTaskReportDetailModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentMisData2Binding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MISDataListFragment extends Fragment {

    private FragmentMisData2Binding fragmentMisDataBinding;
    private View rootView;
    private Context mContext;
    private String title = "", requestType = "", date = "", termID = "", deptId = "";
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private List<MISStudentModel.FinalArray> misStudentFinalDataList;

    private List<MISStudentModel.StandardDatum> misStudentStandardDataList;
    private List<MISStudentModel.StudentDatum> misStudentDataList;
    private List<MISStudentModel.ANT> misStudentANTDataList;
    private List<MISStaffModel.FinalArray> misStaffFinalDataList;

    private List<MISStaffModel.StaffDatum> misStaffDataList;
    private List<MISStaffModel.ANT> misStaffANTDataList;
    private List<MISAccountModel.Datum> misAccountHeaderDataList;
    private List<MISAccountModel.ClassDatum> misAccountDataList;
    private List<MISNewAdmissionModel.FinalArray> misNADataList;
    private List<MISNewAdmissionModel.FinalArray> searchResults = new ArrayList<>();
    private ExapandableListAdapterSMSRepoetData exapandableListAdapterSMSRepoetData;
    private List<StudentAttendanceFinalArray> finalArrayinquiryCountList;

    private List<MISStaffNewDetailModel.FinalArray> staffNewDetailModelList;
    private List<MISTaskReportDetailModel.FinalArray> taskReportModelList;

    private MISStudentAdapter misStudentAdapter;
    private MISDetailListAdapter misDetailListAdapter;
    private TextView mTvInnerTitle, mTvInnerAttendanceStatus, mTvinnerGrno, mTvStudent, mTvDept, mTvCode, mTvName, mTvPhone, mTvGRNO, mTvClassTeacher, mTVGrade, mTvSection, mTvLeaveDay, mTvReason, mTvAbsentFrom, mTvInnerAttendanceAge, mTvInnerAttendanceSince;
    private View innerTitleView, innerListHeaderView;
    private String countdata = "", requestTitle = "";
    private MISAccountHeaderAdapter misAccountHeaderAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<StudentAttendanceFinalArray>> listDataChild;
    private Handler handler;
    private ProgressBar progressBar;

    private ArrayList<String> stdArr = new ArrayList<>();

    //standard spinner
    private List<FinalArrayStandard> finalArrayStandardsList;
    private HashMap<Integer, String> spinnerStandardMap;
    private String FinalStandardStr, FinalStandardIdStr, StandardName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentMisDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mis_data2, container, false);

        AppConfiguration.position = 66;
        AppConfiguration.firsttimeback = true;

        rootView = fragmentMisDataBinding.getRoot();
        progressBar = rootView.findViewById(R.id.loader);
        mContext = getActivity();


        try {
            Bundle bundle = this.getArguments();
            title = bundle.getString("title");
            requestType = bundle.getString("requestType");
            termID = bundle.getString("TermID");
            date = bundle.getString("Date");

            try {
                countdata = bundle.getString("countdata");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                requestTitle = bundle.getString("requestTitle");
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            if (title != null && !TextUtils.isEmpty(title)) {
                fragmentMisDataBinding.textView3.setText(title);
            }


            try {
                deptId = bundle.getString("deptID");
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            if (title.equalsIgnoreCase("Student")) {
                //set header view by requestType.

                if (requestType.equalsIgnoreCase("Total")) {
                    fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.layout_mis_student_detail_header);
                    innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();

                    fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                    innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();

                    mTvInnerTitle = innerTitleView.findViewById(R.id.title_txt);
                    mTvInnerTitle.setText("Total Students");

                } else if (requestType.equalsIgnoreCase("Present")) {

                    fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.layout_mis_student_detail_header);
                    innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();

                    fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                    innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();

                    mTvInnerTitle = innerTitleView.findViewById(R.id.title_txt);
                    mTvInnerTitle.setText(requestType);

                    mTvInnerAttendanceStatus = innerListHeaderView.findViewById(R.id.status_txt);
                    mTvInnerAttendanceStatus.setVisibility(View.GONE);

                } else if (requestType.equalsIgnoreCase("Absent") || requestType.equalsIgnoreCase("Leave")) {

                    fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.layout_mis_student_detail_header);
                    innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();

                    fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                    innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();

                    mTvInnerTitle = innerTitleView.findViewById(R.id.title_txt);
                    mTvInnerTitle.setText(requestType);

                    mTvGRNO = innerListHeaderView.findViewById(R.id.grno_txt);
                    mTvGRNO.setVisibility(View.VISIBLE);
                    mTvGRNO.setText("Phone No.");
                    mTvGRNO.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                    mTvInnerAttendanceStatus = innerListHeaderView.findViewById(R.id.status_txt);
                    mTvInnerAttendanceStatus.setVisibility(View.VISIBLE);
                    mTvInnerAttendanceStatus.setText("SMS");

                    mTvInnerAttendanceStatus.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));


                } else if (requestType.equalsIgnoreCase("ANT")) {

                    fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.layout_mis_student_detail_header);
                    innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();

                    fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                    innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();

                    mTvInnerTitle = innerTitleView.findViewById(R.id.title_txt);
                    mTvInnerTitle.setText("Attendance Not Taken");


//                    mTvInnerAttendanceStatus = (TextView) innerListHeaderView.findViewById(R.id.status_txt);
//                    mTvInnerAttendanceStatus.setText("Class Teacher");
//                    mTvInnerAttendanceStatus.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, 1.2f));

                    mTvGRNO = innerListHeaderView.findViewById(R.id.grno_txt);
                    mTvGRNO.setVisibility(View.VISIBLE);
                    mTvGRNO.setText("Phone No.");
                    mTvGRNO.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f));

                    mTvInnerAttendanceStatus = innerListHeaderView.findViewById(R.id.status_txt);
                    mTvInnerAttendanceStatus.setVisibility(View.VISIBLE);
                    mTvInnerAttendanceStatus.setText("SMS");
                    mTvInnerAttendanceStatus.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f));

                    mTvClassTeacher = innerListHeaderView.findViewById(R.id.teacher_name_txt);
                    mTvClassTeacher.setVisibility(View.VISIBLE);


                    mTvStudent = innerListHeaderView.findViewById(R.id.student_txt);
                    mTvStudent.setText("Total Students");


                } else if (requestType.equalsIgnoreCase("ConsistentAbsent")) {

                    fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.list_item_mis_absent3day_student_header);
                    innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();

                    fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                    innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();
                    innerTitleView.setVisibility(View.VISIBLE);

                    mTvInnerTitle = innerListHeaderView.findViewById(R.id.title_txt);
                    mTvInnerTitle.setText(requestType);


                    mTVGrade = innerListHeaderView.findViewById(R.id.grade_txt);
                    mTVGrade.setVisibility(View.VISIBLE);
                    mTVGrade.setText("Phone No.");
                    mTVGrade.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f));

                    mTvGRNO = innerListHeaderView.findViewById(R.id.grno_txt);
                    mTvGRNO.setVisibility(View.VISIBLE);
                    mTvGRNO.setText("SMS");
                    mTvGRNO.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f));

                    mTvSection = innerListHeaderView.findViewById(R.id.section_txt);
                    mTvSection.setVisibility(View.VISIBLE);
                    mTvSection.setText("Class");
                    mTvInnerAttendanceStatus.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.2f));


                } else if (requestType.equalsIgnoreCase("Attendance less then 70%")) {

                    fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.list_item_mis_absent3day_student_header);
                    innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();

                    fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                    innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();

                    mTvInnerTitle = innerTitleView.findViewById(R.id.title_txt);
                    mTvInnerTitle.setText(requestType);

                    mTvAbsentFrom = innerListHeaderView.findViewById(R.id.absentfrom_txt);
                    mTvAbsentFrom.setText("Absent(in %)");
                    mTvAbsentFrom.setVisibility(View.VISIBLE);

                    mTVGrade = innerListHeaderView.findViewById(R.id.grade_txt);
                    mTVGrade.setText("Phone No.");
                    mTVGrade.setVisibility(View.VISIBLE);

                    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f);
                    params1.gravity = Gravity.CENTER;
                    mTVGrade.setLayoutParams(params1);

                    mTvGRNO = innerListHeaderView.findViewById(R.id.grno_txt);
                    mTvGRNO.setVisibility(View.VISIBLE);
                    mTvGRNO.setText("SMS");
                    LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f);
                    params2.gravity = Gravity.CENTER;
                    mTvGRNO.setLayoutParams(params2);


                    mTvSection = innerListHeaderView.findViewById(R.id.section_txt);
                    mTvSection.setVisibility(View.VISIBLE);
                    mTvSection.setText("Class");
                    LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.2f);
                    params3.gravity = Gravity.CENTER;
                    mTvSection.setLayoutParams(params3);


                }


            } else if (title.equalsIgnoreCase("Staff")) {


                if (requestType.equalsIgnoreCase("Total")) {

                    fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.list_item_mis_staff_detail_header);
                    innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();

                    fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                    innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();


                    mTvInnerTitle = innerTitleView.findViewById(R.id.title_txt);
                    mTvInnerTitle.setText("Total Staff");

                    fragmentMisDataBinding.layoutStub1.getViewStub().setVisibility(View.GONE);

                } else if (requestType.equalsIgnoreCase("ANT")) {

                    fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.list_item_mis_staff_detail_header);
                    innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();

                    fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                    innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();

                    mTvInnerAttendanceStatus = innerListHeaderView.findViewById(R.id.status_txt);
                    mTvInnerAttendanceStatus.setVisibility(View.VISIBLE);
                    mTvInnerAttendanceStatus.setText(getString(R.string.section));
                    mTvInnerAttendanceStatus.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.2f));

                    mTvDept = innerListHeaderView.findViewById(R.id.dept_txt);
                    mTvDept.setText(getString(R.string.grade));
                    mTvDept.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.8f));

                    mTvCode = innerListHeaderView.findViewById(R.id.dept_txt);
                    mTvCode.setText("Teacher Code");
                    mTvCode.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.2f));


                    mTvInnerTitle = innerTitleView.findViewById(R.id.title_txt);
                    mTvInnerTitle.setText(requestType);


                } else if (requestType.equalsIgnoreCase("Work Plan") || requestType.equalsIgnoreCase("CW Submitted") || requestType.equalsIgnoreCase("HW Submitted")) {

                    fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.list_item_mis_staff_detail_header);
                    innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();

                    mTvName = innerListHeaderView.findViewById(R.id.name_txt);
                    mTvName.setText("TeacherName");
                    mTvName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.3f));

                    mTvCode = innerListHeaderView.findViewById(R.id.code_txt);
                    mTvCode.setText("Class");
                    mTvCode.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.5f));


                    mTvDept = innerListHeaderView.findViewById(R.id.dept_txt);
                    mTvDept.setText("Subject");
                    mTvDept.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.2f));


                    mTvInnerAttendanceStatus = innerListHeaderView.findViewById(R.id.status_txt);
                    mTvInnerAttendanceStatus.setVisibility(View.GONE);


                    fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                    innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();


                    mTvInnerTitle = innerTitleView.findViewById(R.id.title_txt);
                    mTvInnerTitle.setText(requestType);


                } else {
                    fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.list_item_mis_staff_detail_header);
                    innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();

                    fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                    innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();

                    mTvInnerAttendanceStatus = innerListHeaderView.findViewById(R.id.status_txt);
                    mTvInnerAttendanceStatus.setVisibility(View.GONE);

                    mTvInnerTitle = innerTitleView.findViewById(R.id.title_txt);
                    mTvInnerTitle.setText(requestType);
                }


            } else if (title.equalsIgnoreCase("Staff New")) {

                if (requestType.equalsIgnoreCase("Total")) {

                    fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.list_item_mis_staff_new_header);

                    innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();

                    fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                    innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();

                    mTvLeaveDay = innerListHeaderView.findViewById(R.id.leave_txt);
                    mTvLeaveDay.setVisibility(View.GONE);

                    mTvReason = innerListHeaderView.findViewById(R.id.reason_txt);
                    mTvReason.setVisibility(View.GONE);


                    mTvInnerTitle = innerTitleView.findViewById(R.id.title_txt);
                    mTvInnerTitle.setText(requestTitle);

                } else if (requestType.equalsIgnoreCase("Absent")) {


                    fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.list_item_mis_staff_new_header);

                    innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();

                    fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                    innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();

                    mTvLeaveDay = innerListHeaderView.findViewById(R.id.leave_txt);
                    mTvLeaveDay.setVisibility(View.VISIBLE);
                    mTvLeaveDay.setText("Employee Code");

                    mTvInnerAttendanceStatus = innerListHeaderView.findViewById(R.id.status_txt);
                    mTvInnerAttendanceStatus.setVisibility(View.GONE);

                    mTvReason = innerListHeaderView.findViewById(R.id.reason_txt);
                    mTvReason.setVisibility(View.GONE);


                    mTvInnerTitle = innerTitleView.findViewById(R.id.title_txt);
                    mTvInnerTitle.setText(requestTitle);


                } else if (requestType.equalsIgnoreCase("Leave")) {

                    fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.list_item_mis_staff_new_header);

                    innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();

                    fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                    innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();

                    mTvInnerAttendanceStatus = innerListHeaderView.findViewById(R.id.status_txt);
                    mTvInnerAttendanceStatus.setVisibility(View.GONE);

                    mTvLeaveDay = innerListHeaderView.findViewById(R.id.leave_txt);
                    mTvLeaveDay.setVisibility(View.VISIBLE);
                    mTvLeaveDay.setText("Leave Days");

                    mTvReason = innerListHeaderView.findViewById(R.id.reason_txt);
                    mTvReason.setVisibility(View.VISIBLE);

                    mTvInnerTitle = innerTitleView.findViewById(R.id.title_txt);
                    mTvInnerTitle.setText(requestTitle);

                } else if (requestType.equalsIgnoreCase("Staff absent more then 10%")) {

                    fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.list_item_mis_staff_new_header);

                    innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();

                    fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                    innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();

                    mTvInnerAttendanceStatus = innerListHeaderView.findViewById(R.id.status_txt);
                    mTvInnerAttendanceStatus.setVisibility(View.GONE);

                    mTvInnerAttendanceAge = innerListHeaderView.findViewById(R.id.age_txt);
                    mTvInnerAttendanceAge.setVisibility(View.GONE);

                    mTvInnerAttendanceSince = innerListHeaderView.findViewById(R.id.since_txt);
                    mTvInnerAttendanceSince.setVisibility(View.GONE);

                    mTvLeaveDay = innerListHeaderView.findViewById(R.id.leave_txt);
                    mTvLeaveDay.setVisibility(View.VISIBLE);

                    mTvReason = innerListHeaderView.findViewById(R.id.reason_txt);
                    mTvReason.setVisibility(View.VISIBLE);
                    mTvReason.setText("Department");

                    mTvInnerTitle = innerTitleView.findViewById(R.id.title_txt);
                    mTvInnerTitle.setText(requestTitle);

                } else if (requestType.equalsIgnoreCase("More then 3 days leave")) {

                    fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.list_item_mis_staff_new_header);

                    innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();

                    fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                    innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();

                    mTvInnerAttendanceStatus = innerListHeaderView.findViewById(R.id.status_txt);
                    mTvInnerAttendanceStatus.setVisibility(View.VISIBLE);
                    mTvInnerAttendanceStatus.setText("Department");

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.5f);
                    params.gravity = Gravity.CENTER;
                    mTvInnerAttendanceStatus.setLayoutParams(params);


                    mTvInnerAttendanceAge = innerListHeaderView.findViewById(R.id.age_txt);
                    mTvInnerAttendanceAge.setVisibility(View.GONE);

                    mTvInnerAttendanceSince = innerListHeaderView.findViewById(R.id.since_txt);
                    mTvInnerAttendanceSince.setVisibility(View.GONE);


                    mTvLeaveDay = innerListHeaderView.findViewById(R.id.leave_txt);
                    mTvLeaveDay.setVisibility(View.VISIBLE);
                    mTvLeaveDay.setText("Leave Days");

                    mTvReason = innerListHeaderView.findViewById(R.id.reason_txt);
                    mTvReason.setVisibility(View.GONE);

                    mTvInnerTitle = innerTitleView.findViewById(R.id.title_txt);
                    mTvInnerTitle.setText(requestTitle);

                } else if (requestType.equalsIgnoreCase("More then 3 days absent")) {

                    fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.list_item_mis_staff_new_header);

                    innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();

                    fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                    innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();


                    mTvInnerAttendanceAge = innerListHeaderView.findViewById(R.id.age_txt);
                    mTvInnerAttendanceAge.setVisibility(View.GONE);

                    mTvInnerAttendanceSince = innerListHeaderView.findViewById(R.id.since_txt);
                    mTvInnerAttendanceSince.setVisibility(View.GONE);

                    mTvInnerAttendanceStatus = innerListHeaderView.findViewById(R.id.status_txt);
                    mTvInnerAttendanceStatus.setVisibility(View.VISIBLE);
                    mTvInnerAttendanceStatus.setText("Department");

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.5f);
                    params.gravity = Gravity.CENTER;
                    mTvInnerAttendanceStatus.setLayoutParams(params);


                    mTvLeaveDay = innerListHeaderView.findViewById(R.id.leave_txt);
                    mTvLeaveDay.setVisibility(View.VISIBLE);
                    mTvLeaveDay.setText("Absent Days");

                    mTvReason = innerListHeaderView.findViewById(R.id.reason_txt);
                    mTvReason.setVisibility(View.GONE);

                    mTvInnerTitle = innerTitleView.findViewById(R.id.title_txt);
                    mTvInnerTitle.setText(requestTitle);

                }


            } else if (title.equalsIgnoreCase("Account")) {

                fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.list_item_mis_account_inner_header);

                innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();

                fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();


                mTvInnerTitle = innerTitleView.findViewById(R.id.title_txt);
                mTvInnerTitle.setText(requestTitle);

            } else if (title.equalsIgnoreCase("New Addmission")) {

                fragmentMisDataBinding.misdataLlSpinner.setVisibility(View.GONE);

                if (requestType.equalsIgnoreCase("FeesNotPaid")) {

                    fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.list_item_mis_new_addmission_header);

                    innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();
                    mTvPhone = innerListHeaderView.findViewById(R.id.phone_txt);

                    fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                    innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();

                    mTvInnerAttendanceStatus = innerListHeaderView.findViewById(R.id.current_status_txt);
                    mTvInnerAttendanceStatus.setVisibility(View.GONE);


                } else {
                    fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.list_item_mis_new_addmission_header);

                    innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();
                    mTvPhone = innerListHeaderView.findViewById(R.id.phone_txt);

                    fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                    innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();


                    if (requestType.equalsIgnoreCase("Rejected")) {
                        mTvInnerTitle = innerTitleView.findViewById(R.id.title_txt);
                        mTvInnerTitle.setText(requestTitle);

                        mTvPhone.setText("Reason");
                    }

                }

            } else if (title.equalsIgnoreCase("Message")) {

                fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.sms_report_header);

                innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();

                fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();


                mTvInnerTitle = innerTitleView.findViewById(R.id.title_txt);
                mTvInnerTitle.setText(requestTitle);

            } else if (title.equalsIgnoreCase("Task Report")) {

                fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.list_item_mis_task_report_innerlist_header);

                innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();

                fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
                innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();

                mTvInnerTitle = innerTitleView.findViewById(R.id.title_txt);
                mTvInnerTitle.setText(requestTitle);

            }


        } catch (Exception ex) {
            ex.printStackTrace();

        }

        setListners();
        progressBar.setVisibility(View.VISIBLE);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (title.equalsIgnoreCase("Student")) {
//                        callStudentMISDataApi();
                    } else if (title.equalsIgnoreCase("Staff")) {
                        callStaffMISDataApi();
                    } else if (title.equalsIgnoreCase("Staff New")) {
                        callStaffNewMISDataApi();
                    } else if (title.equalsIgnoreCase("Account")) {
                        callAccountMISDataApi();
                    } else if (title.equalsIgnoreCase("New Addmission")) {
                        callNewAddmissionMISDataApi();
                    } else if (title.equalsIgnoreCase("Message")) {
                        callMessageMISDataApi();
                    } else if (title.equalsIgnoreCase("Task Report")) {
                        callTaskReportMISDataApi();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, 2000);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void setListners() {
        fragmentMisDataBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AppConfiguration.ReverseTermDetailId = "";
//                fragment = new MISFragment();
//                fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();

                AppConfiguration.position = 58;
                AppConfiguration.firsttimeback = true;

                getActivity().onBackPressed();


//                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        fragmentMisDataBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        fragmentMisDataBinding.misdataGradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentMisDataBinding.misdataGradeSpinner.getSelectedItem().toString();
                String getid = spinnerStandardMap.get(fragmentMisDataBinding.misdataGradeSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalStandardIdStr = getid;
                Log.d("FinalStandardIdStr", FinalStandardIdStr);
                StandardName = name;
                FinalStandardStr = name;
                Log.d("StandardName", StandardName);

                //clear the initial data set
                searchResults = new ArrayList<>();

                if (name.equalsIgnoreCase("All")) {
                    searchResults.addAll(misNADataList);
                } else {
                    for (int i = 0; i < misNADataList.size(); i++) {
                        //compare the String in EditText with Names in the ArrayList
                        if (name.equalsIgnoreCase(misNADataList.get(i).getGrade())) {
                            searchResults.add(misNADataList.get(i));
                        }
                    }
                }

                Log.e("stdsize", "" + searchResults.size());

                if (searchResults.size() > 0) {

                    fragmentMisDataBinding.rvMisdataList.setVisibility(View.VISIBLE);
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.VISIBLE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.VISIBLE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.VISIBLE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.GONE);

                    fragmentMisDataBinding.tvTxt.setText(requestTitle + ": " + String.valueOf(searchResults.size()));

                    misDetailListAdapter = new MISDetailListAdapter(getActivity(), searchResults, 6, requestType);
                    fragmentMisDataBinding.rvMisdataList.setLayoutManager(new LinearLayoutManager(mContext));
                    fragmentMisDataBinding.rvMisdataList.setAdapter(misDetailListAdapter);
//                    misDetailListAdapter.notifyDataSetChanged();
                } else {

                    fragmentMisDataBinding.tvTxt.setText(requestTitle + ": " + "0");
                    fragmentMisDataBinding.rvMisdataList.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        AppConfiguration.position = 66;
        AppConfiguration.firsttimeback = true;
    }

//    private void callStudentMISDataApi() {
//
//        if (!Utils.checkNetwork(mContext)) {
//            Utils.showCustomDialog(getResources().getString(R.string.internet_error),getResources().getString(R.string.internet_connection_error), getActivity());
//            return;
//        }
//        progressBar.setVisibility(View.VISIBLE);
//
//        //Utils.showDialog(getActivity());
//        ApiHandler.getApiService().getMISStudentdata(getParams(), new retrofit.Callback<MISStudentModel>() {
//            @Override
//            public  void  success ( MISStudentModel  staffSMSDataModel , Response  response ) {
//                //Utils.dismissDialog();
//
//                if (staffSMSDataModel == null) {
//                    Utils.ping(mContext, getString(R.string.something_wrong));
//                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
//                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
//                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
//                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
//                    progressBar.setVisibility(View.GONE);
//
//                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
//                    return;
//                }
//                if (staffSMSDataModel.getSuccess() == null) {
//                    Utils.ping(mContext, getString(R.string.something_wrong));
//                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
//                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
//                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
//                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
//                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
//
//                    return;
//                }
//                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
//                    Utils.ping(mContext, getString(R.string.false_msg));
//                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
//                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
//                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
//                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
//                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
//
//                    return;
//                }
//                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {
//
//
//                    try {
//
//                        progressBar.setVisibility(View.GONE);
//
//                        misStudentStandardDataList = staffSMSDataModel.getFinalArray().get(0).getStandardData();
//                        misStudentDataList = staffSMSDataModel . getFinalArray () . get ( 0 ) . getStudentData ();
//                        misStudentANTDataList = staffSMSDataModel.getFinalArray().get(0).getANT();
//                        misStudentFinalDataList = staffSMSDataModel . getFinalArray ();
//
//
//                        fragmentMisDataBinding.lvHeader2.setVisibility(View.VISIBLE);
//                        fragmentMisDataBinding.lvHeader.setVisibility(View.VISIBLE);
//                        fragmentMisDataBinding.recyclerLinear.setVisibility(View.VISIBLE);
//                        fragmentMisDataBinding.recyclerLinear1.setVisibility(View.VISIBLE);
//                        fragmentMisDataBinding.txtNoRecords.setVisibility(View.GONE);
//
//
//                        if (!requestType.equalsIgnoreCase("ANT")) {
//
//                            int totalCount = 0;
//
//                            for (int count = 0; count < misStudentStandardDataList.size(); count++) {
//
//                                totalCount + =  Integer . parseInt (misStudentStandardDataList . get (count) . getTotalStudent ());
//                            }
//
//                            if (requestType.equalsIgnoreCase("Present") || requestType.equalsIgnoreCase("Absent") || requestType.equalsIgnoreCase("Leave") || requestType.equalsIgnoreCase("ConsistentAbsent") || requestType.equalsIgnoreCase("Attendance less then 70%")) {
//                                fragmentMisDataBinding.tvTxt.setText(requestType + ": " + totalCount);
//                            } else if (requestType.equalsIgnoreCase("Total")) {
//                                fragmentMisDataBinding.tvTxt.setText("Total Student: " + totalCount);
//                            }
//
//                            misStudentAdapter =  new  MISStudentAdapter (mContext, misStudentStandardDataList, 0 );
//                            fragmentMisDataBinding . rvMisdataList1 . setLayoutManager ( new  linearLayoutManager (mContext));
//                            fragmentMisDataBinding . rvMisdataList1 . setAdapter (lost dent adapter);
//
//                            if(requestType.equalsIgnoreCase("ConsistentAbsent") ) {
//                                misDetailListAdapter = new MISDetailListAdapter(getActivity(),misStudentDataList,1, requestType);
//                                fragmentMisDataBinding . rvMisdataList . setLayoutManager ( new  linearLayoutManager (mContext));
//                                fragmentMisDataBinding.rvMisdataList.setAdapter(misDetailListAdapter);
//
//                            }else if(requestType.equalsIgnoreCase("Attendance less then 70%")){
//                                misDetailListAdapter = new MISDetailListAdapter(getActivity(),misStudentDataList,1, requestType);
//                                fragmentMisDataBinding . rvMisdataList . setLayoutManager ( new  linearLayoutManager (mContext));
//                                fragmentMisDataBinding.rvMisdataList.setAdapter(misDetailListAdapter);
//                            }
//
//                            else{
//                                misDetailListAdapter = new MISDetailListAdapter(getActivity(),misStudentDataList, 0, requestType);
//                                fragmentMisDataBinding . rvMisdataList . setLayoutManager ( new  linearLayoutManager (mContext));
//                                fragmentMisDataBinding.rvMisdataList.setAdapter(misDetailListAdapter);
//                            }
//
//                        }else if(requestType.equalsIgnoreCase("ANT")){
//
//                            int totalCount = 0;
//
//                            for (int count = 0; count < misStudentANTDataList.size(); count++) {
//
//                                totalCount + = misStudentANTDataList . get (count) .
//                            }
//                            fragmentMisDataBinding.tvTxt.setText("Attendance Not Taken: " +totalCount);
//
//                            if(requestType.equalsIgnoreCase("ANT")){
//
//                                fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
//                                fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
//
//
//                                misDetailListAdapter = new MISDetailListAdapter(mContext, misStudentANTDataList,0,requestType);
//                                fragmentMisDataBinding . rvMisdataList . setLayoutManager ( new LinearLayoutManager (mContext));
//                                fragmentMisDataBinding.rvMisdataList.setAdapter(misDetailListAdapter);
//                            }
//                        }
//
//
//
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                // Utils.dismissDialog();
//                error.printStackTrace();
//                error.getMessage();
//                progressBar.setVisibility(View.GONE);
//                fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
//                fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
//                fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
//                fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
//                fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
//                fragmentMisDataBinding.txtNoRecords.setText(error.getMessage());
//                //Utils.ping(mContext, getString(R.string.something_wrong));
//            }
//        });
//
//    }


    private void callStaffMISDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        // Utils.showDialog(getActivity());
        progressBar.setVisibility(View.VISIBLE);

        ApiHandler.getApiService().getMISStaffdata(getParams(), new retrofit.Callback<MISStaffModel>() {
            @Override
            public void success(MISStaffModel staffSMSDataModel, Response response) {
                // Utils.dismissDialog();

                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);

                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {


                    try {

                        progressBar.setVisibility(View.GONE);

                        misStaffDataList = staffSMSDataModel.getFinalArray().get(0).getStaffData();
                        misStaffANTDataList = staffSMSDataModel.getFinalArray().get(0).getANT();
                        misStaffFinalDataList = staffSMSDataModel.getFinalArray();

                        fragmentMisDataBinding.lvHeader2.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.lvHeader.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.recyclerLinear1.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.txtNoRecords.setVisibility(View.GONE);


                        fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                        fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);


                        if (requestType.equalsIgnoreCase("Total")) {
                            int totalCount = 0;

                            totalCount += misStaffDataList.size();
                            fragmentMisDataBinding.tvTxt.setText("Total Staff: " + totalCount);
                        } else {

                            if (countdata != null) {
                                fragmentMisDataBinding.tvTxt.setText(requestTitle + ": " + countdata);
                            }
                        }

                        if (requestType.equalsIgnoreCase("ANT")) {
                            misDetailListAdapter = new MISDetailListAdapter(mContext, misStaffANTDataList, 2, requestType);
                            fragmentMisDataBinding.rvMisdataList.setLayoutManager(new LinearLayoutManager(mContext));
                            fragmentMisDataBinding.rvMisdataList.setAdapter(misDetailListAdapter);

                        } else if (requestType.equalsIgnoreCase("Work Plan") || requestType.equalsIgnoreCase("CW Submitted") || requestType.equalsIgnoreCase("HW Submitted")) {
                            misDetailListAdapter = new MISDetailListAdapter(mContext, misStaffFinalDataList, 2, requestType);
                            fragmentMisDataBinding.rvMisdataList.setLayoutManager(new LinearLayoutManager(mContext));
                            fragmentMisDataBinding.rvMisdataList.setAdapter(misDetailListAdapter);

                        } else {
                            misDetailListAdapter = new MISDetailListAdapter(mContext, misStaffDataList, 2, requestType);
                            fragmentMisDataBinding.rvMisdataList.setLayoutManager(new LinearLayoutManager(mContext));
                            fragmentMisDataBinding.rvMisdataList.setAdapter(misDetailListAdapter);
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                // Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                fragmentMisDataBinding.txtNoRecords.setText(error.getMessage());
                progressBar.setVisibility(View.GONE);

                //Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private void callStaffNewMISDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        //Utils.showDialog(getActivity());
        progressBar.setVisibility(View.VISIBLE);

        ApiHandler.getApiService().getMISStaffAttendanceDetail(getStaffNewParams(), new retrofit.Callback<MISStaffNewDetailModel>() {
            @Override
            public void success(MISStaffNewDetailModel staffSMSDataModel, Response response) {
                //Utils.dismissDialog();

                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);

                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {

                    try {

                        progressBar.setVisibility(View.GONE);

                        staffNewDetailModelList = staffSMSDataModel.getFinalArray();

                        fragmentMisDataBinding.lvHeader2.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.lvHeader.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.recyclerLinear1.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.txtNoRecords.setVisibility(View.GONE);

                        fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                        fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);

                        if (countdata != null) {
                            fragmentMisDataBinding.tvTxt.setText(requestTitle + ": " + countdata);
                        }

                        misDetailListAdapter = new MISDetailListAdapter(mContext, staffNewDetailModelList, 7, requestType);
                        fragmentMisDataBinding.rvMisdataList.setLayoutManager(new LinearLayoutManager(mContext));
                        fragmentMisDataBinding.rvMisdataList.setAdapter(misDetailListAdapter);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                //  Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                fragmentMisDataBinding.txtNoRecords.setText(error.getMessage());
                progressBar.setVisibility(View.GONE);

                //Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private void callTaskReportMISDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        //Utils.showDialog(getActivity());
        progressBar.setVisibility(View.VISIBLE);

        ApiHandler.getApiService().getMISTaskData(getTaskReportParams(), new retrofit.Callback<MISTaskReportDetailModel>() {
            @Override
            public void success(MISTaskReportDetailModel staffSMSDataModel, Response response) {
                //Utils.dismissDialog();

                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);

                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {


                    try {

                        progressBar.setVisibility(View.GONE);

                        taskReportModelList = staffSMSDataModel.getFinalArray();


                        fragmentMisDataBinding.lvHeader2.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.lvHeader.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.recyclerLinear1.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.txtNoRecords.setVisibility(View.GONE);


                        fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                        fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);


                        if (countdata != null) {
                            fragmentMisDataBinding.tvTxt.setText(requestTitle + ": " + countdata);
                        }

                        misDetailListAdapter = new MISDetailListAdapter(mContext, taskReportModelList, 8, requestType);
                        fragmentMisDataBinding.rvMisdataList.setLayoutManager(new LinearLayoutManager(mContext));
                        fragmentMisDataBinding.rvMisdataList.setAdapter(misDetailListAdapter);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                //  Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                fragmentMisDataBinding.txtNoRecords.setText(error.getMessage());
                progressBar.setVisibility(View.GONE);

                //Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }


    private void callAccountMISDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        progressBar.setVisibility(View.VISIBLE);

        ApiHandler.getApiService().getMISAccountByType(getAccountParams(), new retrofit.Callback<MISAccountModel>() {
            @Override
            public void success(MISAccountModel staffSMSDataModel, Response response) {
                //Utils.dismissDialog();

                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {
                    progressBar.setVisibility(View.GONE);


                    try {

                        misAccountHeaderDataList = staffSMSDataModel.getData();
                        misAccountDataList = staffSMSDataModel.getClassData();

                        fragmentMisDataBinding.lvHeader2.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.lvHeader.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.recyclerLinear1.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.txtNoRecords.setVisibility(View.GONE);


                        if (countdata != null) {
                            fragmentMisDataBinding.tvTxt.setText(requestTitle + ": " + countdata);
                        }


//                            misAccountHeaderAdapter = new MISAccountHeaderAdapter(getActivity(),misAccountHeaderDataList,0);
//                            fragmentMisDataBinding.rvMisdataList1.setLayoutManager(new LinearLayoutManager(mContext));
//                            fragmentMisDataBinding.rvMisdataList1.setAdapter(misAccountHeaderAdapter);


                        misAccountHeaderAdapter = new MISAccountHeaderAdapter(getActivity(), misAccountHeaderDataList, 0);
                        fragmentMisDataBinding.rvMisdataList1.setLayoutManager(new LinearLayoutManager(mContext));
                        fragmentMisDataBinding.rvMisdataList1.setAdapter(misAccountHeaderAdapter);


                        misDetailListAdapter = new MISDetailListAdapter(getActivity(), misAccountDataList, 3, requestType);
                        fragmentMisDataBinding.rvMisdataList.setLayoutManager(new LinearLayoutManager(mContext));
                        fragmentMisDataBinding.rvMisdataList.setAdapter(misDetailListAdapter);


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                /// Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                fragmentMisDataBinding.txtNoRecords.setText(error.getMessage());
                //Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }


    private Map<String, String> getAccountParams() {
        Map<String, String> map = new HashMap<>();
        // map.put("Date",date);
        map.put("TermID", termID);
        map.put("RequestType", requestType);
        return map;
    }


    private void callNewAddmissionMISDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        //Utils.showDialog(getActivity());
        progressBar.setVisibility(View.VISIBLE);

        ApiHandler.getApiService().getMISNewAddmission(getNewAdmissionParams(), new retrofit.Callback<MISNewAdmissionModel>() {
            @Override
            public void success(MISNewAdmissionModel staffSMSDataModel, Response response) {
                // Utils.dismissDialog();

                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    fragmentMisDataBinding.misdataLlSpinner.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    fragmentMisDataBinding.misdataLlSpinner.setVisibility(View.GONE);
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    fragmentMisDataBinding.misdataLlSpinner.setVisibility(View.GONE);
                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {

                    fragmentMisDataBinding.misdataLlSpinner.setVisibility(View.VISIBLE);

                    try {

                        misNADataList = staffSMSDataModel.getFinalArray();

                        fragmentMisDataBinding.lvHeader2.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.lvHeader.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.recyclerLinear1.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.txtNoRecords.setVisibility(View.GONE);

                        fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                        fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);

                        Log.e("nasize", "" + misNADataList.size());

                        for (int i = 0; i < misNADataList.size(); i++) {

                            if (stdArr.size() > 0) {
                                if (!stdArr.contains(misNADataList.get(i).getGrade())) {
                                    stdArr.add(misNADataList.get(i).getGrade());
                                }
                            } else {
                                stdArr.add(misNADataList.get(i).getGrade());
                            }

                        }
//                        misDetailListAdapter = new MISDetailListAdapter(getActivity(), misNADataList, 6, requestType);
//                        fragmentMisDataBinding.rvMisdataList.setLayoutManager(new LinearLayoutManager(mContext));
//                        fragmentMisDataBinding.rvMisdataList.setAdapter(misDetailListAdapter);

                        Log.e("nasize", "" + stdArr.size());

                        callStandardApi();
                        progressBar.setVisibility(View.GONE);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                //  Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                progressBar.setVisibility(View.GONE);
                fragmentMisDataBinding.misdataLlSpinner.setVisibility(View.GONE);
                fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                fragmentMisDataBinding.txtNoRecords.setText(error.getMessage());
                //Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }


    private void callMessageMISDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        progressBar.setVisibility(View.VISIBLE);

        ApiHandler.getApiService().getAllSMSDetail(getMessageReportDetail(), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel inquiryDataModel, Response response) {
                if (inquiryDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentMisDataBinding.listHeader.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);

                    return;
                }
                if (inquiryDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentMisDataBinding.listHeader.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);

                    return;
                }
                if (inquiryDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentMisDataBinding.listHeader.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);


                    return;
                }
                if (inquiryDataModel.getSuccess().equalsIgnoreCase("True")) {

                    if (requestType != null) {

                        if (!TextUtils.isEmpty(requestType)) {


                            finalArrayinquiryCountList = new ArrayList<>();

                            for (int count = 0; count < inquiryDataModel.getFinalArray().size(); count++) {

                                if (!requestType.equalsIgnoreCase("Sent")) {

                                    if (inquiryDataModel.getFinalArray().get(count).getStatus().equalsIgnoreCase(requestType)) {
                                        finalArrayinquiryCountList.add(inquiryDataModel.getFinalArray().get(count));
                                    }

                                } else {
                                    finalArrayinquiryCountList.add(inquiryDataModel.getFinalArray().get(count));

                                }
                            }
                        }
                    }

                    if (finalArrayinquiryCountList != null) {
                        fragmentMisDataBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                        fragmentMisDataBinding.lvHeader.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);

                        if (countdata != null) {
                            fragmentMisDataBinding.tvTxt.setText(requestTitle + ": " + countdata);
                        }
                        fragmentMisDataBinding.listHeader.setVisibility(View.VISIBLE);
                        fillExpLV();
                        exapandableListAdapterSMSRepoetData = new ExapandableListAdapterSMSRepoetData(getActivity(), listDataHeader, listDataChild, "");
                        fragmentMisDataBinding.lvExpviewsmsreport.setAdapter(exapandableListAdapterSMSRepoetData);
                        Utils.dismissDialog();
                    } else {
                        fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                        fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                        fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                        fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentMisDataBinding.listHeader.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
                progressBar.setVisibility(View.GONE);

                fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
                fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
                fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                fragmentMisDataBinding.listHeader.setVisibility(View.GONE);
            }
        });


    }

    private Map<String, String> getMessageReportDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("StartDate", Utils.getTodaysDate());
        map.put("EndDate", Utils.getTodaysDate());
        return map;
    }


    private Map<String, String> getNewAdmissionParams() {
        Map<String, String> map = new HashMap<>();
        // map.put("Date",date);
        map.put("TermID", termID);
        map.put("RequestType", requestType);
        return map;
    }

    private Map<String, String> getTaskReportParams() {
        Map<String, String> map = new HashMap<>();
        map.put("Date", date);
        map.put("TermID", termID);
        map.put("TaskType", requestType);
        return map;
    }

    private Map<String, String> getStaffNewParams() {
        Map<String, String> map = new HashMap<>();
        map.put("Date", date);
        map.put("TermID", termID);
        map.put("DepartmentID", deptId);
        map.put("RequestType", requestType);
        return map;
    }


    private Map<String, String> getParams() {
        Map<String, String> map = new HashMap<>();
        map.put("Date", date);
        map.put("TermID", termID);
        map.put("RequestType", requestType);
        return map;
    }


    public void fillExpLV() {
        try {


            listDataHeader = new ArrayList<>();
            listDataChild = new HashMap<String, List<StudentAttendanceFinalArray>>();

            for (int i = 0; i < finalArrayinquiryCountList.size(); i++) {

                listDataHeader.add(finalArrayinquiryCountList.get(i).getMobileNo() + "|" +
                        finalArrayinquiryCountList.get(i).getSendtime() + "|" +
                        finalArrayinquiryCountList.get(i).getRectime() + "|" +
                        finalArrayinquiryCountList.get(i).getDeliverstatus());
                Log.d("header", "" + listDataHeader);
                ArrayList<StudentAttendanceFinalArray> row = new ArrayList<StudentAttendanceFinalArray>();

                row.add(finalArrayinquiryCountList.get(i));
                Log.d("row", "" + row);

                listDataChild.put(listDataHeader.get(i), row);
                Log.d("child", "" + listDataChild);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Standard Filter
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
                        fillGradeSpinner();
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

    //Use for fill the Standard Spinner
    public void fillGradeSpinner() {
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("All");

        ArrayList<String> standardname = new ArrayList<>();

//        for (int z = 0; z < 2; z++) {
        standardname.add(firstValue.get(0));
//            for (int i = 0; i < finalArrayStandardsList.size(); i++) {
//                for (int j = 0; j < stdArr.size(); j++) {
//                    if(misNADataList.get(j).getGrade().equalsIgnoreCase(finalArrayStandardsList.get(i).getStandard()))
        standardname.addAll(stdArr);
//                }
//            }
//        }

        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        ArrayList<Integer> standardId = new ArrayList<>();
//        for (int m = 0; m < firstValueId.size(); m++) {
        standardId.add(firstValueId.get(0));
            for (int j = 0; j < finalArrayStandardsList.size(); j++) {
                if (stdArr.contains(finalArrayStandardsList.get(j).getStandard())) {
                    standardId.add(finalArrayStandardsList.get(j).getStandardID());
                }
            }
//        }
        String[] spinnerstandardIdArray = new String[standardId.size()];

        spinnerStandardMap = new HashMap<>();
        for (int i = 0; i < standardId.size(); i++) {
            spinnerStandardMap.put(i, String.valueOf(standardId.get(i)));
            spinnerstandardIdArray[i] = standardname.get(i).trim();
        }


        ArrayAdapter<String> adapterstandard = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        fragmentMisDataBinding.misdataGradeSpinner.setAdapter(adapterstandard);

        FinalStandardStr = spinnerStandardMap.get(0);
    }


}




