package com.skool360admin.Model.HR;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeaveRequestModel {



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

        @SerializedName(value = "name", alternate = {"EmployeeName", "StudentName"})
        @Expose
        private String employeeName;
        @SerializedName("EmployeeID")
        @Expose
        private Integer employeeID;
        @SerializedName("ApplicationDate")
        @Expose
        private String applicationDate;
        @SerializedName("ClassName")
        @Expose
        private String classname;
        @SerializedName("Standard")
        @Expose
        private String standard;
        @SerializedName(value = "leave dates", alternate = {"LeaveDates", "LeaveDate"})
        @Expose
        private String leaveDates;
        @SerializedName("LeaveDays")
        @Expose
        private String leaveDays;
        @SerializedName("Reason")
        @Expose
        private String reason;
        @SerializedName("Comment")
        @Expose
        private String comment;
        @SerializedName("ApproveByName")
        @Expose
        private String approveByName;
        @SerializedName("ApproveDays")
        @Expose
        private String approveDays;
        @SerializedName(value = "statusval", alternate = {"StatusName", "Status"})
        @Expose
        private String statusName;
        @SerializedName(value = "leaveid", alternate = {"PK_LeaveApproveID", "PK_LeaveID"})
        @Expose
        private Integer pKLeaveApproveID;

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }

        public Integer getEmployeeID() {
            return employeeID;
        }

        public void setEmployeeID(Integer employeeID) {
            this.employeeID = employeeID;
        }

        public String getApplicationDate() {
            return applicationDate;
        }

        public void setApplicationDate(String applicationDate) {
            this.applicationDate = applicationDate;
        }

        public String getLeaveDates() {
            return leaveDates;
        }

        public void setLeaveDates(String leaveDates) {
            this.leaveDates = leaveDates;
        }

        public String getLeaveDays() {
            return leaveDays;
        }

        public void setLeaveDays(String leaveDays) {
            this.leaveDays = leaveDays;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getApproveByName() {
            return approveByName;
        }

        public void setApproveByName(String approveByName) {
            this.approveByName = approveByName;
        }

        public String getApproveDays() {
            return approveDays;
        }

        public void setApproveDays(String approveDays) {
            this.approveDays = approveDays;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public Integer getPKLeaveApproveID() {
            return pKLeaveApproveID;
        }

        public void setPKLeaveApproveID(Integer pKLeaveApproveID) {
            this.pKLeaveApproveID = pKLeaveApproveID;
        }

        public String getClassname() {
            return classname;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }

        public String getStandard() {
            return standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public Integer getpKLeaveApproveID() {
            return pKLeaveApproveID;
        }

        public void setpKLeaveApproveID(Integer pKLeaveApproveID) {
            this.pKLeaveApproveID = pKLeaveApproveID;
        }
    }

}
