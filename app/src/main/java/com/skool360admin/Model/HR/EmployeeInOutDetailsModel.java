package com.skool360admin.Model.HR;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmployeeInOutDetailsModel {

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

        @SerializedName("EmployeeCode")
        @Expose
        private String employeeCode;
        @SerializedName("EmployeeName")
        @Expose
        private String employeeName;
        @SerializedName("OnDate")
        @Expose
        private String onDate;
        @SerializedName("InOutDetails")
        @Expose
        private String inOutDetails;
        @SerializedName("WorkingHours")
        @Expose
        private String workingHours;

        public String getEmployeeCode() {
            return employeeCode;
        }

        public void setEmployeeCode(String employeeCode) {
            this.employeeCode = employeeCode;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }

        public String getOnDate() {
            return onDate;
        }

        public void setOnDate(String onDate) {
            this.onDate = onDate;
        }

        public String getInOutDetails() {
            return inOutDetails;
        }

        public void setInOutDetails(String inOutDetails) {
            this.inOutDetails = inOutDetails;
        }

        public String getWorkingHours() {
            return workingHours;
        }

        public void setWorkingHours(String workingHours) {
            this.workingHours = workingHours;
        }

    }


}
