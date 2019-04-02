package anandniketan.com.skool360.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;

import anandniketan.com.skool360.Model.HR.SearchStaffModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.databinding.StaffDetailsListItemBinding;
import anandniketan.com.skool360.databinding.StaffOfficeDetailItemBinding;


public class ExpandableStaffInquiryProfileAdapter extends BaseExpandableListAdapter {

    ImageLoader imageLoader;
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, List<SearchStaffModel.FinalArray>> _listDataChild;


    public ExpandableStaffInquiryProfileAdapter(Context context, List<String> listDataHeader, HashMap<String, List<SearchStaffModel.FinalArray>> listDataChild) {
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
        final List<SearchStaffModel.FinalArray> childData = getChild(groupPosition, 0);

        StaffDetailsListItemBinding binding;

        if (convertView == null) {
            Log.d("groupposition", "" + groupPosition);
        }
        String headerTitle = (String) getGroup(groupPosition);
        try {
            if (headerTitle.equalsIgnoreCase("Personal Details")) {

                binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.staff_details_list_item, parent, false);
                convertView = binding.getRoot();

                //binding.tagTxt.setVisibility(View.GONE);
                binding.nameTxt.setText(childData.get(childPosition).getName());
                binding.ecodeTxt.setText(childData.get(childPosition).getEmpCode());
                binding.genderTxt.setText(childData.get(childPosition).getGender());
                binding.dobTxt.setText(childData.get(childPosition).getDateOfBirth());
                binding.bloodgroupTxt.setText(childData.get(childPosition).getBloodGroup());
                binding.mstatusTxt.setText(childData.get(childPosition).getMaritalStatus());
                binding.mnoTxt.setText(childData.get(childPosition).getMobileNo());
                binding.offemailTxt.setText(childData.get(childPosition).getOfficialEmailID());
                binding.fatherTxt1.setText(childData.get(childPosition).getFatherHusbandName());
                binding.religionTxt.setText(childData.get(childPosition).getReligion());
                // binding.ta.setText(childData.get(childPosition).getTransportRequired());
                binding.bankTxt.setText(childData.get(childPosition).getBankName());
                // binding.siblingInAnandNiketanTxt.setText(childData.get(childPosition).getSiblingInAnandNiketanSchool());

                binding.statusTxt.setText(childData.get(childPosition).getStatus());
                binding.bankAcTxt.setText(childData.get(childPosition).getBankAccountNo());
                binding.pfAcNoTxt.setText(childData.get(childPosition).getPFAccountNo());
                binding.panTxt.setText(childData.get(childPosition).getPAN());
                binding.excircularTxt.setText(childData.get(childPosition).getExtraCurricularActitvities());
                binding.assocationMembarshipTxt.setText(childData.get(childPosition).getAssociationMemberShip());
                binding.rankTxt.setText(childData.get(childPosition).getRankMeritsCertificates());
                binding.commentsTxt.setText(childData.get(childPosition).getComments());
                binding.enoTxt.setText(childData.get(childPosition).getEmergencyNo());
                binding.termTxt.setText(childData.get(childPosition).getTerm());
                binding.shiftTxt.setText(childData.get(childPosition).getShift());
                binding.usernameTxt.setText(childData.get(childPosition).getUserName());
                binding.passwordTxt.setText(childData.get(childPosition).getPassword());


            } else if (headerTitle.equalsIgnoreCase("Office Details")) {

                StaffOfficeDetailItemBinding communicationDetailBinding;
                communicationDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.staff_office_detail_item, parent, false);
                convertView = communicationDetailBinding.getRoot();

                communicationDetailBinding.deptTxt.setText(childData.get(childPosition).getDepartment());
                communicationDetailBinding.desgTxt.setText(childData.get(childPosition).getDesignation());
                communicationDetailBinding.appointmentOrderTxt.setText(childData.get(childPosition).getAppointmentOrder());
                communicationDetailBinding.dateOfJoinTxt.setText(childData.get(childPosition).getDateOfJoin());
                communicationDetailBinding.facilitiesTxt.setText(childData.get(childPosition).getFacilitiesOffered());
                communicationDetailBinding.responsblityTxt.setText(childData.get(childPosition).getResponsibilities());
                communicationDetailBinding.boardTxt.setText(childData.get(childPosition).getBoard());


            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
    public List<SearchStaffModel.FinalArray> getChild(int groupPosition, int childPosititon) {
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

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_student_full_detail, null);
        }
        TextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        LinearLayout linear_group = convertView.findViewById(R.id.linear_group);
        if (headerTitle.equalsIgnoreCase("Personal Details")) {
            linear_group.setBackgroundColor(Color.parseColor("#3597D3"));
        } else if (headerTitle.equalsIgnoreCase("Transport Details")) {
            linear_group.setBackgroundColor(Color.parseColor("#FF6BAE18"));
        } else if (headerTitle.equalsIgnoreCase("Office Details")) {
            linear_group.setBackgroundColor(ContextCompat.getColor(_context, R.color.present));
        } else if (headerTitle.equalsIgnoreCase("Mother Details")) {
            linear_group.setBackgroundColor(ContextCompat.getColor(_context, R.color.yellow));
        } else if (headerTitle.equalsIgnoreCase("Communication Details")) {
            linear_group.setBackgroundColor(Color.parseColor("#FF607D8B"));
        } else if (headerTitle.equalsIgnoreCase("For Office Use")) {
            linear_group.setBackgroundColor(ContextCompat.getColor(_context, R.color.light_sky));
        }


//        ListGroupStudentFullDetailBinding groupbinding;
//        if (convertView == null) {
//            groupbinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_group_student_full_detail, parent, false);
//            convertView = groupbinding.getRoot();
//        }
//        groupbinding.lblListHeader.setTypeface(null, Typeface.BOLD);
//        groupbinding.lblListHeader.setText(headerTitle);
//
//        if (headerTitle.equalsIgnoreCase("Student Detail")) {
//            groupbinding.linearGroup.setBackgroundColor(Color.parseColor("#3597D3"));
//        } else if (headerTitle.equalsIgnoreCase("Transport Detail")) {
//            groupbinding.linearGroup.setBackgroundColor(Color.parseColor("#FF6BAE18"));
//        } else if (headerTitle.equalsIgnoreCase("Father Detail")) {
//            groupbinding.linearGroup.setBackgroundColor(Color.parseColor("#FFE6B12E"));
//        } else if (headerTitle.equalsIgnoreCase("Mother Detail")) {
//            groupbinding.linearGroup.setBackgroundColor(Color.parseColor("#FF48ADDE"));
//        } else if (headerTitle.equalsIgnoreCase("Communication Detail")) {
//            groupbinding.linearGroup.setBackgroundColor(Color.parseColor("#FF607D8B"));
//        }

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



