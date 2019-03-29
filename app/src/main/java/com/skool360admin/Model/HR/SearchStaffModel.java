package com.skool360admin.Model.HR;


import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressLint("ParcelCreator")
public class SearchStaffModel  {

    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArray> finalArray = null;

    protected SearchStaffModel(Parcel in) {
        success = in.readString();
        finalArray = in.createTypedArrayList(FinalArray.CREATOR);
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArray> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArray> finalArray) {
        this.finalArray = finalArray;
    }




@SuppressLint("ParcelCreator")
public static class FinalArray  implements Parcelable {

    @SerializedName("EmployeeID")
    @Expose
    private Integer employeeID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Emp Code")
    @Expose
    private String empCode;
    @SerializedName("Date Of Birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("Department")
    @Expose
    private String department;
    @SerializedName("Designation")
    @Expose
    private String designation;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Marital Status")
    @Expose
    private String maritalStatus;
    @SerializedName("Blood Group")
    @Expose
    private String bloodGroup;
    @SerializedName("Mobile No")
    @Expose
    private String mobileNo;
    @SerializedName("Official Email ID")
    @Expose
    private String officialEmailID;
    @SerializedName("Religion")
    @Expose
    private String religion;
    @SerializedName("Father/HusbandName")
    @Expose
    private String fatherHusbandName;
    @SerializedName("Bank Name")
    @Expose
    private String bankName;
    @SerializedName("Bank Account No")
    @Expose
    private String bankAccountNo;
    @SerializedName("PF Account No")
    @Expose
    private String pFAccountNo;
    @SerializedName("PAN")
    @Expose
    private String pAN;
    @SerializedName("Emergency No")
    @Expose
    private String emergencyNo;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("PresentAddress")
    @Expose
    private String presentAddress;
    @SerializedName("PresentCity")
    @Expose
    private String presentCity;
    @SerializedName("PresentPhone")
    @Expose
    private String presentPhone;
    @SerializedName("PresentPinCode")
    @Expose
    private String presentPinCode;
    @SerializedName("PresentState")
    @Expose
    private String presentState;
    @SerializedName("Extra Curricular Actitvities")
    @Expose
    private String extraCurricularActitvities;
    @SerializedName("Association MemberShip")
    @Expose
    private String associationMemberShip;
    @SerializedName("Rank/Merits/Certificates")
    @Expose
    private String rankMeritsCertificates;
    @SerializedName("Comments")
    @Expose
    private String comments;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("Appointment Order")
    @Expose
    private String appointmentOrder;
    @SerializedName("Date Of Join")
    @Expose
    private String dateOfJoin;
    @SerializedName("Facilities Offered")
    @Expose
    private String facilitiesOffered;
    @SerializedName("Board")
    @Expose
    private String board;
    @SerializedName("Responsibilities")
    @Expose
    private String responsibilities;
    @SerializedName("Shift")
    @Expose
    private String shift;
    @SerializedName("Date Of Left")
    @Expose
    private String dateOfLeft;
    @SerializedName("Term")
    @Expose
    private String term;

    protected FinalArray(Parcel in) {
        if (in.readByte() == 0) {
            employeeID = null;
        } else {
            employeeID = in.readInt();
        }
        name = in.readString();
        empCode = in.readString();
        dateOfBirth = in.readString();
        department = in.readString();
        designation = in.readString();
        gender = in.readString();
        maritalStatus = in.readString();
        bloodGroup = in.readString();
        mobileNo = in.readString();
        officialEmailID = in.readString();
        religion = in.readString();
        fatherHusbandName = in.readString();
        bankName = in.readString();
        bankAccountNo = in.readString();
        pFAccountNo = in.readString();
        pAN = in.readString();
        emergencyNo = in.readString();
        userName = in.readString();
        password = in.readString();
        presentAddress = in.readString();
        presentCity = in.readString();
        presentPhone = in.readString();
        presentPinCode = in.readString();
        presentState = in.readString();
        extraCurricularActitvities = in.readString();
        associationMemberShip = in.readString();
        rankMeritsCertificates = in.readString();
        comments = in.readString();
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
        appointmentOrder = in.readString();
        dateOfJoin = in.readString();
        facilitiesOffered = in.readString();
        board = in.readString();
        responsibilities = in.readString();
        shift = in.readString();
        dateOfLeft = in.readString();
        term = in.readString();
    }

    public static final Creator<FinalArray> CREATOR = new Creator<FinalArray>() {
        @Override
        public FinalArray createFromParcel(Parcel in) {
            return new FinalArray(in);
        }

        @Override
        public FinalArray[] newArray(int size) {
            return new FinalArray[size];
        }
    };

    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOfficialEmailID() {
        return officialEmailID;
    }

    public void setOfficialEmailID(String officialEmailID) {
        this.officialEmailID = officialEmailID;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getFatherHusbandName() {
        return fatherHusbandName;
    }

    public void setFatherHusbandName(String fatherHusbandName) {
        this.fatherHusbandName = fatherHusbandName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getPFAccountNo() {
        return pFAccountNo;
    }

    public void setPFAccountNo(String pFAccountNo) {
        this.pFAccountNo = pFAccountNo;
    }

    public String getPAN() {
        return pAN;
    }

    public void setPAN(String pAN) {
        this.pAN = pAN;
    }

    public String getEmergencyNo() {
        return emergencyNo;
    }

    public void setEmergencyNo(String emergencyNo) {
        this.emergencyNo = emergencyNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getPresentCity() {
        return presentCity;
    }

    public void setPresentCity(String presentCity) {
        this.presentCity = presentCity;
    }

    public String getPresentPhone() {
        return presentPhone;
    }

    public void setPresentPhone(String presentPhone) {
        this.presentPhone = presentPhone;
    }

    public String getPresentPinCode() {
        return presentPinCode;
    }

    public void setPresentPinCode(String presentPinCode) {
        this.presentPinCode = presentPinCode;
    }

    public String getPresentState() {
        return presentState;
    }

    public void setPresentState(String presentState) {
        this.presentState = presentState;
    }

    public String getExtraCurricularActitvities() {
        return extraCurricularActitvities;
    }

    public void setExtraCurricularActitvities(String extraCurricularActitvities) {
        this.extraCurricularActitvities = extraCurricularActitvities;
    }

    public String getAssociationMemberShip() {
        return associationMemberShip;
    }

    public void setAssociationMemberShip(String associationMemberShip) {
        this.associationMemberShip = associationMemberShip;
    }

    public String getRankMeritsCertificates() {
        return rankMeritsCertificates;
    }

    public void setRankMeritsCertificates(String rankMeritsCertificates) {
        this.rankMeritsCertificates = rankMeritsCertificates;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAppointmentOrder() {
        return appointmentOrder;
    }

    public void setAppointmentOrder(String appointmentOrder) {
        this.appointmentOrder = appointmentOrder;
    }

    public String getDateOfJoin() {
        return dateOfJoin;
    }

    public void setDateOfJoin(String dateOfJoin) {
        this.dateOfJoin = dateOfJoin;
    }

    public String getFacilitiesOffered() {
        return facilitiesOffered;
    }

    public void setFacilitiesOffered(String facilitiesOffered) {
        this.facilitiesOffered = facilitiesOffered;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getDateOfLeft() {
        return dateOfLeft;
    }

    public void setDateOfLeft(String dateOfLeft) {
        this.dateOfLeft = dateOfLeft;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (employeeID == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(employeeID);
        }
        parcel.writeString(name);
        parcel.writeString(empCode);
        parcel.writeString(dateOfBirth);
        parcel.writeString(department);
        parcel.writeString(designation);
        parcel.writeString(gender);
        parcel.writeString(maritalStatus);
        parcel.writeString(bloodGroup);
        parcel.writeString(mobileNo);
        parcel.writeString(officialEmailID);
        parcel.writeString(religion);
        parcel.writeString(fatherHusbandName);
        parcel.writeString(bankName);
        parcel.writeString(bankAccountNo);
        parcel.writeString(pFAccountNo);
        parcel.writeString(pAN);
        parcel.writeString(emergencyNo);
        parcel.writeString(userName);
        parcel.writeString(password);
        parcel.writeString(presentAddress);
        parcel.writeString(presentCity);
        parcel.writeString(presentPhone);
        parcel.writeString(presentPinCode);
        parcel.writeString(presentState);
        parcel.writeString(extraCurricularActitvities);
        parcel.writeString(associationMemberShip);
        parcel.writeString(rankMeritsCertificates);
        parcel.writeString(comments);
        if (status == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(status);
        }
        parcel.writeString(appointmentOrder);
        parcel.writeString(dateOfJoin);
        parcel.writeString(facilitiesOffered);
        parcel.writeString(board);
        parcel.writeString(responsibilities);
        parcel.writeString(shift);
        parcel.writeString(dateOfLeft);
        parcel.writeString(term);
    }


}
}