package com.skool360admin.Model.MIS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MISNewAdmissionModel {

    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArray> finalArray = null;

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


    public class FinalArray {

        @SerializedName("InqDate")
        @Expose
        private String inqDate;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("StudentID")
        @Expose
        private Integer studentID;
        @SerializedName("Grade")
        @Expose
        private String grade;
        @SerializedName("Gender")
        @Expose
        private String gender;
        @SerializedName("Current Status")
        @Expose
        private String currentStatus;

        @SerializedName("MobileNo")
        @Expose
        private String MobileNo;

        @SerializedName("Reason")
        @Expose
        private String reason;

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getMobileNo() {
            return MobileNo;
        }

        public void setMobileNo(String mobileNo) {
            MobileNo = mobileNo;
        }

        public String getInqDate() {
            return inqDate;
        }

        public void setInqDate(String inqDate) {
            this.inqDate = inqDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getStudentID() {
            return studentID;
        }

        public void setStudentID(Integer studentID) {
            this.studentID = studentID;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCurrentStatus() {
            return currentStatus;
        }

        public void setCurrentStatus(String currentStatus) {
            this.currentStatus = currentStatus;
        }

    }

}
