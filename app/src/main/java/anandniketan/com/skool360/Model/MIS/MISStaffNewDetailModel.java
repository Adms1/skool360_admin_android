package anandniketan.com.skool360.Model.MIS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MISStaffNewDetailModel {


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

        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("PhoneNo")
        @Expose
        private String phoneNo;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("Code")
        @Expose
        private String code;
        @SerializedName("EmployeeID")
        @Expose
        private Integer employeeID;
        @SerializedName("Status")
        @Expose
        private String status;

        @SerializedName("LeaveReason")
        @Expose
        private String leaveReason;

        @SerializedName("LeaveDays")
        @Expose
        private String leaveDays;

        @SerializedName("Department")
        @Expose
        private String department;

        @SerializedName("Days")
        @Expose
        private String days;

        @SerializedName("Age")
        @Expose
        private String age;

        @SerializedName("Working From")
        @Expose
        private String since;


        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }


        public String getName() {
            return name;
        }


        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Integer getEmployeeID() {
            return employeeID;
        }

        public void setEmployeeID(Integer employeeID) {
            this.employeeID = employeeID;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLeaveReason() {
            return leaveReason;
        }

        public void setLeaveReason(String leaveReason) {
            this.leaveReason = leaveReason;
        }

        public String getLeaveDays() {
            return leaveDays;
        }

        public void setLeaveDays(String leaveDays) {
            this.leaveDays = leaveDays;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSince() {
            return since;
        }

        public void setSince(String since) {
            this.since = since;
        }
    }

}
