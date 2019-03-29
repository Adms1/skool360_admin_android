package com.skool360admin.Model.HR;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HrHeadModel {


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

        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("EmployeeName")
        @Expose
        private String employeeName;
        @SerializedName("AdmissionInquiry")
        @Expose
        private Integer admissionInquiry;
        @SerializedName("TotalNewAdmission")
        @Expose
        private Integer totalNewAdmission;
        @SerializedName("EventOfTheDay")
        @Expose
        private String eventOfTheDay;
        @SerializedName("FBUpdate")
        @Expose
        private String fBUpdate;
        @SerializedName("MajorConcern")
        @Expose
        private String majorConcern;
        @SerializedName("Other")
        @Expose
        private String other;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }

        public Integer getAdmissionInquiry() {
            return admissionInquiry;
        }

        public void setAdmissionInquiry(Integer admissionInquiry) {
            this.admissionInquiry = admissionInquiry;
        }

        public Integer getTotalNewAdmission() {
            return totalNewAdmission;
        }

        public void setTotalNewAdmission(Integer totalNewAdmission) {
            this.totalNewAdmission = totalNewAdmission;
        }

        public String getEventOfTheDay() {
            return eventOfTheDay;
        }

        public void setEventOfTheDay(String eventOfTheDay) {
            this.eventOfTheDay = eventOfTheDay;
        }

        public String getFBUpdate() {
            return fBUpdate;
        }

        public void setFBUpdate(String fBUpdate) {
            this.fBUpdate = fBUpdate;
        }

        public String getMajorConcern() {
            return majorConcern;
        }

        public void setMajorConcern(String majorConcern) {
            this.majorConcern = majorConcern;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

    }
}
