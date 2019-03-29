package anandniketan.com.bhadajadmin.Model.HR;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmployeePresentDetailsModel {


    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("PresentEmployee")
    @Expose
    private Integer presentEmployee;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArray> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Integer getPresentEmployee() {
        return presentEmployee;
    }

    public void setPresentEmployee(Integer presentEmployee) {
        this.presentEmployee = presentEmployee;
    }

    public List<FinalArray> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArray> finalArray) {
        this.finalArray = finalArray; }



    public class FinalArray {

        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("EmployeeName")
        @Expose
        private String employeeName;
        @SerializedName("Shift")
        @Expose
        private String shift;
        @SerializedName("Department")
        @Expose
        private String department;
        @SerializedName("Designation")
        @Expose
        private String designation;

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

        public String getShift() {
            return shift;
        }

        public void setShift(String shift) {
            this.shift = shift;
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

    }
}
