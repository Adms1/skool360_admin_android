package com.skool360admin.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.Model.Student.StudentInquiryProfileModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.databinding.LayoutInquiryProfileFatherListItemBinding;
import anandniketan.com.bhadajadmin.databinding.LayoutInquiryProfileMotherListItemBinding;
import anandniketan.com.bhadajadmin.databinding.OfficeListItemDetailBinding;
import anandniketan.com.bhadajadmin.databinding.StudentListItemCommunicationDetailBinding;
import anandniketan.com.bhadajadmin.databinding.StudentListItemInquiryProfileBinding;


public class ExpandableStudentInquiryProfileAdapter extends BaseExpandableListAdapter {


    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<StudentInquiryProfileModel.FinalArray>> _listDataChild;
    ImageLoader imageLoader;


    public ExpandableStudentInquiryProfileAdapter(Context context, List<String> listDataHeader, HashMap<String, ArrayList<StudentInquiryProfileModel.FinalArray>> listDataChild) {
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
            final ArrayList<StudentInquiryProfileModel.FinalArray> childData = getChild(groupPosition, 0);

            StudentListItemInquiryProfileBinding binding;

            if (convertView == null) {
                Log.d("groupposition", "" + groupPosition);
            }
            String headerTitle = (String) getGroup(groupPosition);
            try {
                if (headerTitle.equalsIgnoreCase("Student Details")) {

                    binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.student_list_item_inquiry_profile, parent, false);
                    convertView = binding.getRoot();

                    if(!TextUtils.isEmpty(childData.get(childPosition).getStudentImage())){
                        Glide.with(_context).load(AppConfiguration.LIVE_BASE_URL + childData.get(childPosition).getStudentImage()).into(binding.studentProfileImage);
                    }

                    binding.tagTxt.setVisibility(View.GONE);
                    binding.firstnameTxt.setText(childData.get(childPosition).getFirstName());
                    binding.middleTxt.setText(childData.get(childPosition).getMiddleName());
                    binding.lastnameTxt.setText(childData.get(childPosition).getLastName());
                    binding.dobTxt.setText(childData.get(childPosition).getDateOfBirth());
                    binding.birthplaceTxt.setText(childData.get(childPosition).getBirthPlace());
                    binding.sessionTxt.setText(childData.get(childPosition).getSession());
                    binding.stateTxt.setText(childData.get(childPosition).getState());
                    binding.motherToungueTxt.setText(childData.get(childPosition).getMotherTongue());
                    binding.nationalityTxt.setText(childData.get(childPosition).getNationality());
                    binding.seekingAdmissionForGradeTxt.setText(childData.get(childPosition).getSeekingAdmissionForGrade());
                   // binding.ta.setText(childData.get(childPosition).getTransportRequired());
                    binding.boardTxt.setText(childData.get(childPosition).getBoard());
                   // binding.siblingInAnandNiketanTxt.setText(childData.get(childPosition).getSiblingInAnandNiketanSchool());



                    binding.gradeTxt.setText(childData.get(childPosition).getGradeSection());
                    binding.formTxt.setText(childData.get(childPosition).getFormNo());
                    binding.dateTxt.setText(childData.get(childPosition).getDate());
                    binding.grnoTxt.setText(childData.get(childPosition).getGRNO());
                    binding.bloodgroupTxt.setText(childData.get(childPosition).getBloodGroup());
                    binding.dobTxt.setText(childData.get(childPosition).getDateOfBirth());
                    binding.genderTxt1.setText(childData.get(childPosition).getGender());
                    binding.birthplaceTxt.setText(childData.get(childPosition).getBirthPlace());
                    binding.nationalityTxt.setText(childData.get(childPosition).getNationality());
                    binding.hometownTxt.setText(childData.get(childPosition).getHometown());
                    binding.motherToungueTxt.setText(childData.get(childPosition).getMotherTongue());
                    binding.currentlyResidewithTxt.setText(childData.get(childPosition).getCurrentlyResidingWith());
                    binding.previousSchoolTxt.setText(childData.get(childPosition).getPreviousSchoolAttended());


                    binding.sibling1BrotherSisterTxt.setText(childData.get(childPosition).getSibling1BrotherSister());
                    binding.sibling1SchoolTxt.setText(childData.get(childPosition).getSibling1School());

                    binding.sibling2BrotherSisterTxt.setText(childData.get(childPosition).getSibling2BrotherSister());
                    binding.siblingSchool2Txt.setText(childData.get(childPosition).getSibling2School());
                    binding.guardianNameTxt.setText(childData.get(childPosition).getNameOfLocalGuardian());
                    binding.guardianContactTxt.setText(childData.get(childPosition).getContactNoOfLocalGuardian());
                    binding.ecnoTxt.setText(childData.get(childPosition).getEmergencyContactNos());
                    binding.availTransportTxt.setText(childData.get(childPosition).getWouldYouLikeToAvailTheSchoolTransportFacility());

                    binding.festivalsTxt1.setText(childData.get(childPosition).getFestivalsCelebratedAtHome());

                    binding.siblingName1Txt.setText(childData.get(childPosition).getNameOfSibling1());
                    binding.siblingName2Txt.setText(childData.get(childPosition).getNameOfSibling2());
                    binding.siblingOneGradeTxt.setText(childData.get(childPosition).getSibling1Grade());
                    binding.siblingGrade2Txt.setText(childData.get(childPosition).getSibling2Grade());
                    binding.addressTxt.setText(childData.get(childPosition).getAddress());
                    binding.distanceTxt.setText(childData.get(childPosition).getDistanceFromSchoolInKms());

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
//                    binding.statusTxt.setTextColor(Color.parseColor("#FF6BAE18"));
//                    binding.statusTxt.setText("Active");

//                    SimpleDateFormat inFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//                    Date date = null, date1 = null;
//                    try {
//                        if (!childData.get(childPosition).getRegistrationDate().equalsIgnoreCase("")) {
//                            date = inFormat.parse(childData.get(childPosition).getRegistrationDate());
//                            SimpleDateFormat outFormat = new SimpleDateFormat("dd/MM/yyyy");
//                            String formatted = outFormat.format(date);
//                            System.out.println(formatted);
//                            binding.doaTxt.setText(formatted);
//
//                        } else {
//                            binding.doaTxt.setText("");
//                        }
//                        if (!childData.get(childPosition).getDateOfAdmission().equalsIgnoreCase("")) {
//                            date1 = inFormat.parse(childData.get(childPosition).getDateOfAdmission());
//                            SimpleDateFormat outFormat = new SimpleDateFormat("dd/MM/yyyy");
//                            String formatted1 = outFormat.format(date1);
//                            System.out.println(formatted1);
//                            binding.registrationTxt.setText(formatted1);
//                        } else {
//                            binding.registrationTxt.setText("");
//                        }
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(date);


                } else if (headerTitle.equalsIgnoreCase("Transport Details")) {
//                    StudetnListItemTransportDetailBinding transportDetailBinding;
//                    transportDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.studetn_list_item_transport_detail, parent, false);
//                    convertView = transportDetailBinding.getRoot();
//
//                    transportDetailBinding.pickupTxt.setText(childData.get(childPosition).getPickupBus());
//                    transportDetailBinding.pickuppointTxt.setText(childData.get(childPosition).getPickupPoint());
//                    transportDetailBinding.pickuptimeTxt.setText(childData.get(childPosition).getPickupPointTime());
//                    transportDetailBinding.dropbusTxt.setText(childData.get(childPosition).getDropBus());
//                    transportDetailBinding.droppointTxt.setText(childData.get(childPosition).getDropPoint());
//                    transportDetailBinding.droptimeTxt.setText(childData.get(childPosition).getDropPointTime());

                } else if (headerTitle.equalsIgnoreCase("Father Details")) {
                    LayoutInquiryProfileFatherListItemBinding fatherDetailBinding;


                    fatherDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_inquiry_profile_father_list_item, parent, false);
                    convertView = fatherDetailBinding.getRoot();


                    if(!TextUtils.isEmpty(childData.get(childPosition).getFatherImage())) {
                        Glide.with(_context).load(AppConfiguration.LIVE_BASE_URL + childData.get(childPosition).getFatherImage()).apply(new RequestOptions().placeholder(R.drawable.person_placeholder).error(R.drawable.person_placeholder)).into(fatherDetailBinding.fatherProfileImage);
                    }else{
                        Glide.with(_context).load((R.drawable.person_placeholder)).into(fatherDetailBinding.fatherProfileImage);
                    }

                   // String fname[] = childData.get(childPosition).getFatherName().split("\\ ");
                    fatherDetailBinding.fatherfirstnameTxt.setText(childData.get(childPosition).getFatherFirstName());
                    fatherDetailBinding.fathermiddleTxt.setText(childData.get(childPosition).getFatherMiddleName());
                    fatherDetailBinding.fatherlastnameTxt.setText(childData.get(childPosition).getFatherLastName());
                    fatherDetailBinding.fatherdob1Txt.setText(childData.get(childPosition).getFatherDateOfBirth());
                    fatherDetailBinding.fbloodgroup1Txt.setText(childData.get(childPosition).getFatherBloodGroup());
                    fatherDetailBinding.foccupation1Txt.setText(childData.get(childPosition).getFatherOccupation());
                    fatherDetailBinding.fNameOfCompanyTxt1.setText(childData.get(childPosition).getFatherNameOfCompany());
                    fatherDetailBinding.fqualificationTxt1.setText(childData.get(childPosition).getFatherQualification());
                    fatherDetailBinding.fDesgTxt1.setText(childData.get(childPosition).getFatherDesignation());
                    fatherDetailBinding.fNationalityTxt1.setText(childData.get(childPosition).getFatherNationality());
                    fatherDetailBinding.fLangKnownTxt1.setText(childData.get(childPosition).getFatherLanguagesKnown());
                    fatherDetailBinding.fmobileTxt1.setText(childData.get(childPosition).getFatherMobileNo());
                    fatherDetailBinding.fContactOfficeTxt1.setText(childData.get(childPosition).getFatherContactOffice());
                    fatherDetailBinding.fContactHomeTxt1.setText(childData.get(childPosition).getFatherContactHome());
                    fatherDetailBinding.fEmailHomeTxt1.setText(childData.get(childPosition).getFatherEmailAddress());
                    fatherDetailBinding.fHomeHomeTxt1.setText(childData.get(childPosition).getFatherHomeAddress());
                    fatherDetailBinding.fOfficeTxt1.setText(childData.get(childPosition).getFatherOfficeAddress());
                    fatherDetailBinding.fStatusTxt1.setText(childData.get(childPosition).getFatherStatus());
                    fatherDetailBinding.fAnnunalIncomeTxt1.setText(childData.get(childPosition).getFatherAnnualIncome());


                } else if (headerTitle.equalsIgnoreCase("Mother Details")) {

                   LayoutInquiryProfileMotherListItemBinding motherDetailBinding;
                   motherDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_inquiry_profile_mother_list_item, parent, false);
                   convertView = motherDetailBinding.getRoot();


                    if(!TextUtils.isEmpty(childData.get(childPosition).getMotherImage())) {
                        Glide.with(_context).load(AppConfiguration.LIVE_BASE_URL + childData.get(childPosition).getMotherImage()).apply(new RequestOptions().placeholder(R.drawable.person_placeholder).error(R.drawable.person_placeholder)).into(motherDetailBinding.motherProfileImage);
                    }else{
                        Glide.with(_context).load((R.drawable.person_placeholder)).into(motherDetailBinding.motherProfileImage);

                    }

                   // String Mname[] = childData.get(childPosition).getMotherName().split("\\ ");
                    motherDetailBinding.motherfirstnameTxt.setText(childData.get(childPosition).getMotherFirstName());
                    motherDetailBinding.mothermiddleTxt.setText(childData.get(childPosition).getMotherMiddleName());
                    motherDetailBinding.motherlastnameTxt.setText(childData.get(childPosition).getMotherLastName());
                    motherDetailBinding.motherdobTxt.setText(childData.get(childPosition).getMotherDateOfBirth());
                    motherDetailBinding.motherbloodgroupTxt.setText(childData.get(childPosition).getMotherBloodGroup());
                    motherDetailBinding.moccupationTxt.setText(childData.get(childPosition).getMotherOccupation());
                    motherDetailBinding.mNameOfCompTxt.setText(childData.get(childPosition).getMotherNameOfCompany());
                    motherDetailBinding.mqualificationTxt.setText(childData.get(childPosition).getMotherQualification());
                    motherDetailBinding.mdesgnTxt1.setText(childData.get(childPosition).getMotherDesignation());
                    motherDetailBinding.mnationalityTxt.setText(childData.get(childPosition).getMotherNationality());
                    motherDetailBinding.mLangKnownTxt.setText(childData.get(childPosition).getMotherLanguagesKnown());
                    motherDetailBinding.mmobileTxt.setText(childData.get(childPosition).getMotherMobileNo());
                    motherDetailBinding.mContactOfficeTxt.setText(childData.get(childPosition).getMotherContactOffice());
                    motherDetailBinding.mContactHomeTxt.setText(childData.get(childPosition).getMotherContactHome());
                    motherDetailBinding.mEmailTxt.setText(childData.get(childPosition).getMotherEmailAddress());
                    motherDetailBinding.mHomeTxt.setText(childData.get(childPosition).getMotherHomeAddress());
                    motherDetailBinding.mOfficeTxt.setText(childData.get(childPosition).getMotherOfficeAddress());
                    motherDetailBinding.mStatusTxt.setText(childData.get(childPosition).getMotherStatus());
                    motherDetailBinding.mAnnualIncomTxt.setText(childData.get(childPosition).getMotherAnnualIncome());


                   // motherDetailBinding.mdesignationTxt.setText(childData.get(childPosition).getMotherDesignation());
                } else if (headerTitle.equalsIgnoreCase("Communication Details")) {
                    StudentListItemCommunicationDetailBinding communicationDetailBinding;
//                    communicationDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.student_list_item_communication_detail, parent, false);
//                    convertView = communicationDetailBinding.getRoot();
//                    communicationDetailBinding.smsTxt.setText(childData.get(childPosition).getSMSCommunicationNo());
//                    communicationDetailBinding.cityTxt.setText(childData.get(childPosition).getCity());
//                    communicationDetailBinding.addressTxt.setText(childData.get(childPosition).getAddress());
//                    communicationDetailBinding.zipTxt.setText(childData.get(childPosition).getZipCode());
                }else if (headerTitle.equalsIgnoreCase("For Office Use")) {

                    OfficeListItemDetailBinding  communicationDetailBinding;
                    communicationDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.office_list_item_detail, parent, false);
                    convertView = communicationDetailBinding.getRoot();

                    communicationDetailBinding.regTxt.setText(childData.get(childPosition).getRegistrationNo());
                    communicationDetailBinding.regDateTxt.setText(childData.get(childPosition).getRegistrationDate());
                    communicationDetailBinding.docBcertTxt.setText(childData.get(childPosition).getDocumentBirthCertificate());
                    communicationDetailBinding.docPhotoTxt.setText(childData.get(childPosition).getDocumentPhotographs());
                    communicationDetailBinding.docOrigTcTxt.setText(childData.get(childPosition).getDocumentOriginalTC());
                    communicationDetailBinding.idProofFatherTxt11.setText(childData.get(childPosition).getIDProofOfFather());
                    communicationDetailBinding.fPassportTxt.setText(childData.get(childPosition).getFatherPassportCopy());
                    communicationDetailBinding.fLicenseTxt.setText(childData.get(childPosition).getFatherLicenseCopy());
                    communicationDetailBinding.fAdharCardTxt.setText(childData.get(childPosition).getFatherAadharCardCopy());

                    communicationDetailBinding.idOfMotherTxt11.setText(childData.get(childPosition).getIDProofOfMother());
                    communicationDetailBinding.mPassportTxt.setText(childData.get(childPosition).getMotherPassportCopy());
                    communicationDetailBinding.mLicenseTxt.setText(childData.get(childPosition).getMotherLicenseCopy());
                    communicationDetailBinding.mAdharTxt.setText(childData.get(childPosition).getMotherAadharCardCopy());
                    communicationDetailBinding.admiitedGradeTxt.setText(childData.get(childPosition).getAdmittedToGrade());
                    communicationDetailBinding.wlistTxt.setText(childData.get(childPosition).getWaitListed());
                    communicationDetailBinding.remarksTxt.setText(childData.get(childPosition).getRemarks());
                    communicationDetailBinding.addManagerTxt.setText(childData.get(childPosition).getAdmissionManager());

                    communicationDetailBinding.firstInterDateTxt.setText(childData.get(childPosition).get1InteractionDate());
                    communicationDetailBinding.firstInterTimeTxt.setText(childData.get(childPosition).get1InteractionTime());
                    communicationDetailBinding.firstInterWithTxt.setText(childData.get(childPosition).get1InteractionWith());
                    communicationDetailBinding.remarksInterWithTxt.setText(childData.get(childPosition).get1InteractionRemarks());

                    communicationDetailBinding.secInterDateTxt.setText(childData.get(childPosition).get2InteractionDate());
                    communicationDetailBinding.secInterTimeTxt.setText(childData.get(childPosition).get2InteractionTime());
                    communicationDetailBinding.secInterWithTxt.setText(childData.get(childPosition).get2InteractionWith());
                    communicationDetailBinding.remarksSecInterWithTxt.setText(childData.get(childPosition).get2InteractionRemarks());

                }
            }catch (Exception ex){
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
        public ArrayList<StudentInquiryProfileModel.FinalArray> getChild(int groupPosition, int childPosititon) {
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
            if (headerTitle.equalsIgnoreCase("Student Details")) {
                linear_group.setBackgroundColor(Color.parseColor("#3597D3"));
            } else if (headerTitle.equalsIgnoreCase("Transport Details")) {
                linear_group.setBackgroundColor(Color.parseColor("#FF6BAE18"));
            } else if (headerTitle.equalsIgnoreCase("Father Details")) {
                linear_group.setBackgroundColor(ContextCompat.getColor(_context,R.color.present));
            } else if (headerTitle.equalsIgnoreCase("Mother Details")) {
                linear_group.setBackgroundColor(ContextCompat.getColor(_context,R.color.yellow));
            } else if (headerTitle.equalsIgnoreCase("Communication Details")) {
                linear_group.setBackgroundColor(Color.parseColor("#FF607D8B"));
            }else if (headerTitle.equalsIgnoreCase("For Office Use")) {
                linear_group.setBackgroundColor(ContextCompat.getColor(_context,R.color.light_sky));
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



