package anandniketan.com.skool360.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.skool360.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.databinding.GrStudentListDetailBinding;
import anandniketan.com.skool360.databinding.StudentListItemCommunicationDetailBinding;
import anandniketan.com.skool360.databinding.StudentListItemFatherDetailBinding;
import anandniketan.com.skool360.databinding.StudentListItemMotherDetailBinding;
import anandniketan.com.skool360.databinding.StudetnListItemTransportDetailBinding;

/**
 * Created by admsandroid on 1/29/2018.
 */

public class ExpandableListAdapterGRstudentdetail extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<StudentAttendanceFinalArray>> _listDataChild;

    public ExpandableListAdapterGRstudentdetail(Context context, List<String> listDataHeader, HashMap<String,
            ArrayList<StudentAttendanceFinalArray>> listDataChild) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listDataChild;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final ArrayList<StudentAttendanceFinalArray> childData = getChild(groupPosition, 0);
        GrStudentListDetailBinding binding;
        if (convertView == null) {

//            LayoutInflater layoutInflater = (LayoutInflater) this._context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = layoutInflater.inflate(R.layout.student_list_item_student_full_detail, null);

            Log.d("groupposition", "" + groupPosition);
        }

        String headerTitle = (String) getGroup(groupPosition);
        if (headerTitle.equalsIgnoreCase("Student Details")) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.gr_student_list_detail, parent, false);
            convertView = binding.getRoot();

            LinearLayout llBoard = convertView.findViewById(R.id.llBoard);
            LinearLayout llUsername = convertView.findViewById(R.id.llUsername);
            LinearLayout llPassword = convertView.findViewById(R.id.llPassword);
            TextView middleTxt = convertView.findViewById(R.id.middle_txt);
            TextView boardTxt = convertView.findViewById(R.id.board_txt);
            TextView aadharTxt = convertView.findViewById(R.id.aadhar_txt);
            TextView acedamicTxt = convertView.findViewById(R.id.acedamic_txt);
            TextView doaTxt = convertView.findViewById(R.id.doa_txt);
            TextView doatakenTxt = convertView.findViewById(R.id.doataken_txt);
            TextView houseTxt = convertView.findViewById(R.id.house_txt);
            TextView uniqueno = convertView.findViewById(R.id.unq_txt);
            TextView location = convertView.findViewById(R.id.location_txt);
            TextView oldgrTxt = convertView.findViewById(R.id.oldgr_txt);

//            TextView tagTxt = convertView.findViewById(R.id.tag_txt);
//            TextView firstnameTxt = convertView.findViewById(R.id.firstname_txt);
//            TextView lastnameTxt = convertView.findViewById(R.id.lastname_txt);
//            TextView dobTxt = convertView.findViewById(R.id.dob_txt);
//            TextView birthplaceTxt = convertView.findViewById(R.id.birthplace_txt);
//            TextView geTxt = convertView.findViewById(R.id.age_txt);
//            TextView genderTxt = convertView.findViewById(R.id.gender_txt);
//            TextView gradeTxt = convertView.findViewById(R.id.grade_txt);
//            TextView sectionTxt = convertView.findViewById(R.id.section_txt);
//            TextView lastschoolTxt = convertView.findViewById(R.id.lastschool_txt);
//
//            TextView bloodgroupTxt = convertView.findViewById(R.id.bloodgroup_txt);
//            TextView religionTxt = convertView.findViewById(R.id.religion_txt);
//            TextView nationalityTxt = convertView.findViewById(R.id.nationality_txt);
//            TextView statusTxt = convertView.findViewById(R.id.status_txt);
//            TextView grnoTxt = convertView.findViewById(R.id.grnno_txt);

//            llBoard.setVisibility(View.VISIBLE);

//            String[] name = childData.get(childPosition).getName().split("\\ ");
            binding.tagTxt.setText(childData.get(childPosition).getTag());
//            binding.firstnameTxt.setText(name[0]);
//            middleTxt.setText(name[1]);
//            binding.lastnameTxt.setText(name[2]);
            binding.firstnameTxt.setText(childData.get(childPosition).getFirstName());
            binding.lastnameTxt.setText(childData.get(childPosition).getLastName());
            binding.dobTxt.setText(childData.get(childPosition).getDateOfBirth());
            binding.birthplaceTxt.setText(childData.get(childPosition).getBirthPlace());
            binding.ageTxt.setText(childData.get(childPosition).getAge());
            binding.genderTxt.setText(childData.get(childPosition).getGender());
            binding.studentTypeTxt.setText(childData.get(childPosition).getBoard());
            binding.aadharTxt.setText(childData.get(childPosition).getAadhaarCardNo());
            binding.acedamicTxt.setText(childData.get(childPosition).getAcedamicYear());
            binding.gradeTxt.setText(childData.get(childPosition).getGrade());
            binding.sectionTxt.setText(childData.get(childPosition).getSection());
            binding.lastschoolTxt.setText(childData.get(childPosition).getLastSchool());
            binding.doaTxt.setText(childData.get(childPosition).getDateOfAdmission());
            binding.doatakenTxt.setText(childData.get(childPosition).getAdmissionTaken());
            binding.bloodgroupTxt.setText(childData.get(childPosition).getBloodGroup());
            binding.houseTxt.setText(childData.get(childPosition).getHouse());
            binding.unqTxt.setText(childData.get(childPosition).getUnique_no());
            binding.religionTxt.setText(childData.get(childPosition).getReligion());
            binding.nationalityTxt.setText(childData.get(childPosition).getNationality());
            binding.locationTxt.setText(childData.get(childPosition).getLocation());
            if (childData.get(childPosition).getStatus().equalsIgnoreCase("1")) {
                binding.statusTxt.setTextColor(Color.parseColor("#FF6BAE18"));
                binding.statusTxt.setText(R.string.active);

            } else {
                binding.statusTxt.setTextColor(Color.parseColor("#ff0000"));
                binding.statusTxt.setText(R.string.inactive);

            }

            binding.grnoTxt.setText(childData.get(childPosition).getGRNO());
            binding.oldgrTxt.setText(childData.get(childPosition).getOldGRNO());

        } else if (headerTitle.equalsIgnoreCase("Transport Details")) {

            StudetnListItemTransportDetailBinding transportDetailBinding;
            transportDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.studetn_list_item_transport_detail, parent, false);
            convertView = transportDetailBinding.getRoot();

            transportDetailBinding.llKm.setVisibility(View.VISIBLE);
            transportDetailBinding.kmTxt.setText(childData.get(childPosition).getTransportKMs());
            transportDetailBinding.kmsTxt.setText(childData.get(childPosition).getRouteName());
            transportDetailBinding.pickupTxt.setText(childData.get(childPosition).getPickupBus());
            transportDetailBinding.pickuppointTxt.setText(childData.get(childPosition).getPickupPoint());
            transportDetailBinding.pickuptimeTxt.setText(childData.get(childPosition).getPickupPointTime());
            transportDetailBinding.dropbusTxt.setText(childData.get(childPosition).getDropBus());
            transportDetailBinding.droppointTxt.setText(childData.get(childPosition).getDropPoint());
            transportDetailBinding.droptimeTxt.setText(childData.get(childPosition).getDropPointTime());

        } else if (headerTitle.equalsIgnoreCase("Father Details")) {

            StudentListItemFatherDetailBinding fatherDetailBinding;
            fatherDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.student_list_item_father_detail, parent, false);
            convertView = fatherDetailBinding.getRoot();
            fatherDetailBinding.fatherfirstnameTxt.setText(childData.get(childPosition).getFatherFirstName());
            fatherDetailBinding.fatherlastnameTxt.setText(childData.get(childPosition).getFatherLastName());
            fatherDetailBinding.fphoneTxt.setText(childData.get(childPosition).getFatherPhoneNo());
            fatherDetailBinding.fmobileTxt.setText(childData.get(childPosition).getFatherMobileNo());
            fatherDetailBinding.fEmailTxt.setText(childData.get(childPosition).getFatherEmailAddress());
            fatherDetailBinding.fQualificationTxt.setText(childData.get(childPosition).getFatherQualification());
            fatherDetailBinding.foccupationTxt.setText(childData.get(childPosition).getFatherOccupation());
            fatherDetailBinding.forganisationTxt.setText(childData.get(childPosition).getFatherOrganization());
            fatherDetailBinding.fdesignationTxt.setText(childData.get(childPosition).getFatherDesignation());
            fatherDetailBinding.officeTxt.setText(childData.get(childPosition).getFatherofficeaddress());

        } else if (headerTitle.equalsIgnoreCase("Mother Details")) {

            StudentListItemMotherDetailBinding motherDetailBinding;
            motherDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.student_list_item_mother_detail, parent, false);
            convertView = motherDetailBinding.getRoot();

            motherDetailBinding.motherfirstnameTxt.setText(childData.get(childPosition).getMotherFirstName());
            motherDetailBinding.motherlastnameTxt.setText(childData.get(childPosition).getMotherLastName());
            motherDetailBinding.mphoneTxt.setText(childData.get(childPosition).getMotherPhoneNo());
            motherDetailBinding.mmobileTxt.setText(childData.get(childPosition).getMotherMobileNo());
            motherDetailBinding.memailTxt.setText(childData.get(childPosition).getMotherEmailAddress());
            motherDetailBinding.mqualificationTxt.setText(childData.get(childPosition).getMotherQualification());
            motherDetailBinding.moccupationTxt.setText(childData.get(childPosition).getMotherOccupation());
            motherDetailBinding.morganisationTxt.setText(childData.get(childPosition).getMotherOrganization());
            motherDetailBinding.mdesignationTxt.setText(childData.get(childPosition).getMotherDesignation());
            motherDetailBinding.officeTxt.setText(childData.get(childPosition).getMotherofficeaddress());

        } else if (headerTitle.equalsIgnoreCase("Communication Details")) {

            StudentListItemCommunicationDetailBinding communicationDetailBinding;
            communicationDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.student_list_item_communication_detail, parent, false);
            convertView = communicationDetailBinding.getRoot();

            communicationDetailBinding.smsTxt.setText(childData.get(childPosition).getSMSCommunicationNo());
            communicationDetailBinding.cityTxt.setText(childData.get(childPosition).getCity());
            communicationDetailBinding.addressTxt.setText(childData.get(childPosition).getAddress());
            communicationDetailBinding.zipTxt.setText(childData.get(childPosition).getZipCode());
            communicationDetailBinding.smsEmailTxt.setText(childData.get(childPosition).getsMSCommunicationemail());
            communicationDetailBinding.addressTxt.setText(childData.get(childPosition).getAddress());
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public ArrayList<StudentAttendanceFinalArray> getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (infalInflater != null) {
                convertView = infalInflater.inflate(R.layout.list_group_student_full_detail, null);
            }
        }

        TextView lblListHeader = null;
        if (convertView != null) {

            lblListHeader = convertView.findViewById(R.id.lblListHeader);

            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);

            LinearLayout linear_group = convertView.findViewById(R.id.linear_group);

            if (headerTitle.equalsIgnoreCase("Student Details")) {
                linear_group.setBackgroundColor(Color.parseColor("#3597D3"));
            } else if (headerTitle.equalsIgnoreCase("Transport Details")) {
                linear_group.setBackgroundColor(Color.parseColor("#FF6BAE18"));
            } else if (headerTitle.equalsIgnoreCase("Father Details")) {
                linear_group.setBackgroundColor(Color.parseColor("#FFE6B12E"));
            } else if (headerTitle.equalsIgnoreCase("Mother Details")) {
                linear_group.setBackgroundColor(Color.parseColor("#FF48ADDE"));
            } else if (headerTitle.equalsIgnoreCase("Communication Details")) {
                linear_group.setBackgroundColor(Color.parseColor("#FF607D8B"));
            }
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}

