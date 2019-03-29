package com.skool360admin.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LeaveModel {


    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private ArrayList<FinalArray> finalArray = null;

    @SerializedName("LeaveDetails")
    @Expose
    private ArrayList<FinalArray> leavedetails = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<FinalArray> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(ArrayList<FinalArray> finalArray) {
        this.finalArray = finalArray;
    }

    public ArrayList<FinalArray> getLeavedetails() {
        return leavedetails;
    }

    public void setLeavedetails(ArrayList<FinalArray> leavedetails) {
        this.leavedetails = leavedetails;
    }

    public class Detail {
        @SerializedName("LeaveStartDate")
        @Expose
        private String leaveStartDate;
        @SerializedName("LeaveEndDate")
        @Expose
        private String leaveEndDate;
        @SerializedName("LeaveDays")
        @Expose
        private String leaveDays;
        @SerializedName(value = "approvestartdate", alternate = {"ApproveStartDate", "ARStartDate"})
        @Expose
        private String approveStartDate;
        @SerializedName(value = "approveednddate", alternate = {"ApproveEndDate", "AREndDate"})
        @Expose
        private String approveEndDate;
        @SerializedName(value = "approvedays", alternate = {"ApproveDays", "ARDays"})
        @Expose
        private String approveDays;
        @SerializedName("ApproveStatus")
        @Expose
        private String approveStatus;
        @SerializedName(value = "approveby", alternate = {"ApproveBy", "ARBy"})
        @Expose
        private String approveBy;
        @SerializedName("PL")
        @Expose
        private String pL;
        @SerializedName("CL")
        @Expose
        private String cL;

        @SerializedName("LeaveID")
        @Expose
        private String leaveid;

        @SerializedName("CreateDate")
        @Expose
        private String createdate;

        @SerializedName("Status")
        @Expose
        private String status;

        @SerializedName("Reason")
        @Expose
        private String reason;

        @SerializedName("HeadName")
        @Expose
        private String headname;

        public String getLeaveStartDate() {
            return leaveStartDate;
        }

        public void setLeaveStartDate(String leaveStartDate) {
            this.leaveStartDate = leaveStartDate;
        }

        public String getLeaveEndDate() {
            return leaveEndDate;
        }

        public void setLeaveEndDate(String leaveEndDate) {
            this.leaveEndDate = leaveEndDate;
        }

        public String getLeaveDays() {
            return leaveDays;
        }

        public void setLeaveDays(String leaveDays) {
            this.leaveDays = leaveDays;
        }

        public String getApproveStartDate() {
            return approveStartDate;
        }

        public void setApproveStartDate(String approveStartDate) {
            this.approveStartDate = approveStartDate;
        }

        public String getApproveEndDate() {
            return approveEndDate;
        }

        public void setApproveEndDate(String approveEndDate) {
            this.approveEndDate = approveEndDate;
        }

        public String getApproveDays() {
            return approveDays;
        }

        public void setApproveDays(String approveDays) {
            this.approveDays = approveDays;
        }

        public String getApproveStatus() {
            return approveStatus;
        }

        public void setApproveStatus(String approveStatus) {
            this.approveStatus = approveStatus;
        }

        public String getApproveBy() {
            return approveBy;
        }

        public void setApproveBy(String approveBy) {
            this.approveBy = approveBy;
        }

        public String getPL() {
            return pL;
        }

        public void setPL(String pL) {
            this.pL = pL;
        }

        public String getCL() {
            return cL;
        }

        public void setCL(String cL) {
            this.cL = cL;
        }

        public String getpL() {
            return pL;
        }

        public void setpL(String pL) {
            this.pL = pL;
        }

        public String getcL() {
            return cL;
        }

        public void setcL(String cL) {
            this.cL = cL;
        }

        public String getLeaveid() {
            return leaveid;
        }

        public void setLeaveid(String leaveid) {
            this.leaveid = leaveid;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getHeadname() {
            return headname;
        }

        public void setHeadname(String headname) {
            this.headname = headname;
        }
    }

    public static class FinalArray {

        @SerializedName("LeaveStartDate")
        @Expose
        private String leaveStartDate;
        @SerializedName("LeaveEndDate")
        @Expose
        private String leaveEndDate;
        @SerializedName("LeaveDays")
        @Expose
        private String leaveDays;
        @SerializedName(value = "approvestartdate", alternate = {"ApproveStartDate", "ARStartDate"})
        @Expose
        private String approveStartDate;
        @SerializedName(value = "approveednddate", alternate = {"ApproveEndDate", "AREndDate"})
        @Expose
        private String approveEndDate;
        @SerializedName(value = "approvedays", alternate = {"ApproveDays", "ARDays"})
        @Expose
        private String approveDays;
        @SerializedName("ApproveStatus")
        @Expose
        private String approveStatus;
        @SerializedName(value = "approveby", alternate = {"ApproveBy", "ARBy"})
        @Expose
        private String approveBy;
        @SerializedName("PL")
        @Expose
        private String pL;
        @SerializedName("CL")
        @Expose
        private String cL;

        @SerializedName("LeaveID")
        @Expose
        private String leaveid;

        @SerializedName("CreateDate")
        @Expose
        private String createdate;

        @SerializedName("Status")
        @Expose
        private String status;

        @SerializedName("Reason")
        @Expose
        private String reason;

        @SerializedName("HeadName")
        @Expose
        private String headname;
        @SerializedName("EmployeeName")
        @Expose
        private String employeeName;


        @SerializedName("Category")
        @Expose
        private String category;

        @SerializedName("Total")
        @Expose
        private String total;

        @SerializedName("Used")
        @Expose
        private String used;
        @SerializedName("Remaining")
        @Expose
        private String remaining;

        @SerializedName("EmployeeID")
        @Expose
        private Integer employeeID;
        @SerializedName("PLUSed")
        @Expose
        private Double pLUSed;
        @SerializedName("TotalPL")
        @Expose
        private Integer totalPL;
        @SerializedName("CLUsed")
        @Expose
        private Double cLUsed;
        @SerializedName("TotalCL")
        @Expose
        private Integer totalCL;

        @SerializedName("PayStatus")
        @Expose
        private String payStatus;
        @SerializedName("Detail")
        @Expose
        private List<Detail> detail = null;

        public String getLeaveStartDate() {
            return leaveStartDate;
        }

        public void setLeaveStartDate(String leaveStartDate) {
            this.leaveStartDate = leaveStartDate;
        }

        public String getLeaveEndDate() {
            return leaveEndDate;
        }

        public void setLeaveEndDate(String leaveEndDate) {
            this.leaveEndDate = leaveEndDate;
        }

        public String getLeaveDays() {
            return leaveDays;
        }

        public void setLeaveDays(String leaveDays) {
            this.leaveDays = leaveDays;
        }

        public String getApproveStartDate() {
            return approveStartDate;
        }

        public void setApproveStartDate(String approveStartDate) {
            this.approveStartDate = approveStartDate;
        }

        public String getApproveEndDate() {
            return approveEndDate;
        }

        public void setApproveEndDate(String approveEndDate) {
            this.approveEndDate = approveEndDate;
        }

        public String getApproveDays() {
            return approveDays;
        }

        public void setApproveDays(String approveDays) {
            this.approveDays = approveDays;
        }

        public String getApproveStatus() {
            return approveStatus;
        }

        public void setApproveStatus(String approveStatus) {
            this.approveStatus = approveStatus;
        }

        public String getApproveBy() {
            return approveBy;
        }

        public void setApproveBy(String approveBy) {
            this.approveBy = approveBy;
        }

        public String getPL() {
            return pL;
        }

        public void setPL(String pL) {
            this.pL = pL;
        }

        public String getCL() {
            return cL;
        }

        public void setCL(String cL) {
            this.cL = cL;
        }

        public String getpL() {
            return pL;
        }

        public void setpL(String pL) {
            this.pL = pL;
        }

        public String getcL() {
            return cL;
        }

        public void setcL(String cL) {
            this.cL = cL;
        }

        public String getLeaveid() {
            return leaveid;
        }

        public void setLeaveid(String leaveid) {
            this.leaveid = leaveid;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getHeadname() {
            return headname;
        }

        public void setHeadname(String headname) {
            this.headname = headname;
        }

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

        public Double getPLUSed() {
            return pLUSed;
        }

        public void setPLUSed(Double pLUSed) {
            this.pLUSed = pLUSed;
        }

        public Integer getTotalPL() {
            return totalPL;
        }

        public void setTotalPL(Integer totalPL) {
            this.totalPL = totalPL;
        }

        public Double getCLUsed() {
            return cLUsed;
        }

        public void setCLUsed(Double cLUsed) {
            this.cLUsed = cLUsed;
        }

        public Integer getTotalCL() {
            return totalCL;
        }

        public void setTotalCL(Integer totalCL) {
            this.totalCL = totalCL;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getUsed() {
            return used;
        }

        public void setUsed(String used) {
            this.used = used;
        }

        public String getRemaining() {
            return remaining;
        }

        public void setRemaining(String remaining) {
            this.remaining = remaining;
        }

        public Double getpLUSed() {
            return pLUSed;
        }

        public void setpLUSed(Double pLUSed) {
            this.pLUSed = pLUSed;
        }

        public Double getcLUsed() {
            return cLUsed;
        }

        public void setcLUsed(Double cLUsed) {
            this.cLUsed = cLUsed;
        }

        public String getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(String payStatus) {
            this.payStatus = payStatus;
        }

        public List<Detail> getDetail() {
            return detail;
        }

        public void setDetail(List<Detail> detail) {
            this.detail = detail;
        }

    }

}
