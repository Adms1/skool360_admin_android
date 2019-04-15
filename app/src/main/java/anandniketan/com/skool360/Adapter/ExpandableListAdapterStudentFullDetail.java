package anandniketan.com.skool360.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.skool360.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.databinding.StudentListItemCommunicationDetailBinding;
import anandniketan.com.skool360.databinding.StudentListItemFatherDetailBinding;
import anandniketan.com.skool360.databinding.StudentListItemMotherDetailBinding;
import anandniketan.com.skool360.databinding.StudentListItemStudentFullDetailBinding;
import anandniketan.com.skool360.databinding.StudetnListItemTransportDetailBinding;

import static anandniketan.com.skool360.Utility.AppConfiguration.LIVE_BASE_URL;


/**
 * Created by admsandroid on 11/22/2017.
 */

public class ExpandableListAdapterStudentFullDetail extends BaseExpandableListAdapter {

    ImageLoader imageLoader;
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<StudentAttendanceFinalArray>> _listDataChild;


    public ExpandableListAdapterStudentFullDetail(Context context, List<String> listDataHeader, HashMap<String, ArrayList<StudentAttendanceFinalArray>> listDataChild) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listDataChild;
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final ArrayList<StudentAttendanceFinalArray> childData = getChild(groupPosition, 0);
        StudentListItemStudentFullDetailBinding binding;
        if (convertView == null) {
            Log.d("groupposition", "" + groupPosition);
        }
        String headerTitle = (String) getGroup(groupPosition);
        try {
            if (headerTitle.equalsIgnoreCase("Student Details")) {

                binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.student_list_item_student_full_detail, parent, false);
                convertView = binding.getRoot();

                TextView nationality = convertView.findViewById(R.id.nationality_txt);
                TextView location = convertView.findViewById(R.id.location_txt);
                TextView uniqueno = convertView.findViewById(R.id.unq_txt);

//                imageLoader = ImageLoader.getInstance();
//                DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//                        .cacheInMemory(true)
//                        .cacheOnDisk(true)
//                        .imageScaleType(ImageScaleType.EXACTLY)
//                        .displayer(new FadeInBitmapDisplayer(300))
//                        .build();
//                ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//                        _context)
//                        .threadPriority(Thread.MAX_PRIORITY)
//                        .defaultDisplayImageOptions(defaultOptions)
//                        .memoryCache(new WeakMemoryCache())
//                        .denyCacheImageMultipleSizesInMemory()
//                        .tasksProcessingOrder(QueueProcessingType.LIFO)// .enableLogging()
//                        .build();
//                imageLoader.init(config.createDefault(_context));
//                imageLoader.displayImage(childData.get(childPosition).getStudentImage(),binding.profileImage);

                if (!TextUtils.isEmpty(LIVE_BASE_URL + childData.get(childPosition).getStudentImage())) {
                    Glide.with(_context).load(LIVE_BASE_URL + childData.get(childPosition).getStudentImage()).apply(new RequestOptions().placeholder(R.drawable.person_placeholder).error(R.drawable.person_placeholder)).into(binding.profileImage);
                } else {
                    Glide.with(_context).load(R.drawable.person_placeholder).into(binding.profileImage);
                }

                String[] name = childData.get(childPosition).getName().split("\\ ");
                binding.tagTxt.setText(childData.get(childPosition).getTag());
                binding.firstnameTxt.setText(name[0]);
                binding.middleTxt.setText(name[1]);
                binding.lastnameTxt.setText(name[2]);
                binding.dobTxt.setText(childData.get(childPosition).getDateOfBirth());
                binding.birthplaceTxt.setText(childData.get(childPosition).getBirthPlace());
                binding.ageTxt.setText(childData.get(childPosition).getAge());
                binding.genderTxt.setText(childData.get(childPosition).getGender());
                binding.aadharTxt.setText(childData.get(childPosition).getAadhaarCardNo());
                uniqueno.setText(childData.get(childPosition).getUnique_no());
//                binding.studentTypeTxt.setText(childData.get(childPosition).getStudentType());
                nationality.setText(childData.get(childPosition).getNationality());
                location.setText(childData.get(childPosition).getLocation());
                binding.acedamicTxt.setText(childData.get(childPosition).getAcedamicYear());
                binding.gradeTxt.setText(childData.get(childPosition).getGrade());
                binding.sectionTxt.setText(childData.get(childPosition).getSection());
                binding.lastschoolTxt.setText(childData.get(childPosition).getLastSchool());
                binding.doatakenTxt.setText(childData.get(childPosition).getAdmissionTaken());
                binding.bloodgroupTxt.setText(childData.get(childPosition).getBloodGroup());
                binding.houseTxt.setText(childData.get(childPosition).getHouse());
                binding.oldgrTxt.setText(childData.get(childPosition).getOldGRNO());
                binding.religionTxt.setText(childData.get(childPosition).getReligion());
                binding.usernameTxt.setText(childData.get(childPosition).getUserName());
                binding.passwordTxt.setText(childData.get(childPosition).getPassword());
                binding.grnoTxt.setText(childData.get(childPosition).getGRNO());

//                if (AppConfiguration.StudentStatus.equalsIgnoreCase("Current Student")) {
//                    binding.statusTxt.setTextColor(Color.parseColor("#FF6BAE18"));
//                    binding.statusTxt.setText("Active");
//
//                } else {
//                    binding.statusTxt.setTextColor(Color.parseColor("#ff0000"));
//                    binding.statusTxt.setText("InActive");
//                }


//                if (childData.get(childPosition).getStatus().equalsIgnoreCase("Current Student")) {
//                    binding.statusTxt.setTextColor(Color.parseColor("#FF6BAE18"));
//                    binding.statusTxt.setText("Active");
//
//                } else {
//                    binding.statusTxt.setTextColor(Color.parseColor("#ff0000"));
//                    binding.statusTxt.setText("InActive");
//                }
                binding.statusTxt.setTextColor(Color.parseColor("#FF6BAE18"));
                binding.statusTxt.setText("Active");

                SimpleDateFormat inFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = null, date1 = null;
                try {
                    if (!childData.get(childPosition).getRegistrationDate().equalsIgnoreCase("")) {
                        date = inFormat.parse(childData.get(childPosition).getRegistrationDate());
                        SimpleDateFormat outFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String formatted = outFormat.format(date);
                        System.out.println(formatted);
                        binding.doaTxt.setText(formatted);

                    } else {
                        binding.doaTxt.setText("");
                    }
                    if (!childData.get(childPosition).getDateOfAdmission().equalsIgnoreCase("")) {
                        date1 = inFormat.parse(childData.get(childPosition).getDateOfAdmission());
                        SimpleDateFormat outFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String formatted1 = outFormat.format(date1);
                        System.out.println(formatted1);
                        binding.registrationTxt.setText(formatted1);
                    } else {
                        binding.registrationTxt.setText("");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println(date);


            } else if (headerTitle.equalsIgnoreCase("Transport Details")) {
                StudetnListItemTransportDetailBinding transportDetailBinding;
                transportDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.studetn_list_item_transport_detail, parent, false);
                convertView = transportDetailBinding.getRoot();

                transportDetailBinding.kmsTxt.setVisibility(View.VISIBLE);
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
                String fname[] = childData.get(childPosition).getFatherName().split("\\ ");
                fatherDetailBinding.fatherfirstnameTxt.setText(fname[0]);
                fatherDetailBinding.fatherlastnameTxt.setText(fname[1]);
                fatherDetailBinding.fphoneTxt.setText(childData.get(childPosition).getFatherPhoneNo());
                fatherDetailBinding.fmobileTxt.setText(childData.get(childPosition).getFatherMobileNo());
                fatherDetailBinding.fEmailTxt.setText(childData.get(childPosition).getFatherEmailAddress());
                fatherDetailBinding.fQualificationTxt.setText(childData.get(childPosition).getFatherQualification());
                fatherDetailBinding.foccupationTxt.setText(childData.get(childPosition).getFatherOccupation());
                fatherDetailBinding.forganisationTxt.setText(childData.get(childPosition).getFatherOrganization());
                fatherDetailBinding.fdesignationTxt.setText(childData.get(childPosition).getFatherDesignation());

            } else if (headerTitle.equalsIgnoreCase("Mother Details")) {
                StudentListItemMotherDetailBinding motherDetailBinding;
                motherDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.student_list_item_mother_detail, parent, false);
                convertView = motherDetailBinding.getRoot();

                String Mname[] = childData.get(childPosition).getMotherName().split("\\ ");
                motherDetailBinding.motherfirstnameTxt.setText(Mname[0]);
                motherDetailBinding.motherlastnameTxt.setText(Mname[1]);
                motherDetailBinding.mphoneTxt.setText(childData.get(childPosition).getMotherPhoneNo());
                motherDetailBinding.mmobileTxt.setText(childData.get(childPosition).getMotherMobileNo());
                motherDetailBinding.memailTxt.setText(childData.get(childPosition).getMotherEmailAddress());
                motherDetailBinding.mqualificationTxt.setText(childData.get(childPosition).getMotherQualification());
                motherDetailBinding.moccupationTxt.setText(childData.get(childPosition).getMotherOccupation());
                motherDetailBinding.morganisationTxt.setText(childData.get(childPosition).getMotherOrganization());
                motherDetailBinding.mdesignationTxt.setText(childData.get(childPosition).getMotherDesignation());
            } else if (headerTitle.equalsIgnoreCase("Communication Details")) {
                StudentListItemCommunicationDetailBinding communicationDetailBinding;
                communicationDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.student_list_item_communication_detail, parent, false);
                convertView = communicationDetailBinding.getRoot();

                communicationDetailBinding.smsEmailTxt.setText(childData.get(childPosition).getsMSCommunicationemail());
                communicationDetailBinding.smsTxt.setText(childData.get(childPosition).getSMSCommunicationNo());
                communicationDetailBinding.cityTxt.setText(childData.get(childPosition).getCity());
                communicationDetailBinding.addressTxt.setText(childData.get(childPosition).getAddress());
                communicationDetailBinding.zipTxt.setText(childData.get(childPosition).getZipCode());
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

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
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
