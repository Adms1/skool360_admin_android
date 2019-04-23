package anandniketan.com.skool360.Model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LogInModel {

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

        @SerializedName("LocationID")
        @Expose
        private String locationid;

        @SerializedName("StaffID")
        @Expose
        private String staffID;
        @SerializedName("Emp_Code")
        @Expose
        private String empCode;
        @SerializedName("Emp_Name")
        @Expose
        private String empName;
        @SerializedName("DepratmentID")
        @Expose
        private String depratmentID;
        @SerializedName("DesignationID")
        @Expose
        private String designationID;
        @SerializedName("DepratmentName")
        @Expose
        private String depratmentName;
        @SerializedName("DesignationName")
        @Expose
        private String designationName;
        @SerializedName("DOB")
        @Expose
        private String dob;
        @SerializedName("DeviceId")
        @Expose
        private String deviceId;
        @SerializedName("ClassDetail")
        @Expose
        private List<Object> classDetail = null;

        public String getLocationid() {
            return locationid;
        }

        public void setLocationid(String locationid) {
            this.locationid = locationid;
        }

        public String getStaffID() {
            return staffID;
        }

        public void setStaffID(String staffID) {
            this.staffID = staffID;
        }

        public String getEmpCode() {
            return empCode;
        }

        public void setEmpCode(String empCode) {
            this.empCode = empCode;
        }

        public String getEmpName() {
            return empName;
        }

        public void setEmpName(String empName) {
            this.empName = empName;
        }

        public String getDepratmentID() {
            return depratmentID;
        }

        public void setDepratmentID(String depratmentID) {
            this.depratmentID = depratmentID;
        }

        public String getDesignationID() {
            return designationID;
        }

        public void setDesignationID(String designationID) {
            this.designationID = designationID;
        }

        public String getDepratmentName() {
            return depratmentName;
        }

        public void setDepratmentName(String depratmentName) {
            this.depratmentName = depratmentName;
        }

        public String getDesignationName() {
            return designationName;
        }

        public void setDesignationName(String designationName) {
            this.designationName = designationName;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public List<Object> getClassDetail() {
            return classDetail;
        }

        public void setClassDetail(List<Object> classDetail) {
            this.classDetail = classDetail;
        }

    }
}
