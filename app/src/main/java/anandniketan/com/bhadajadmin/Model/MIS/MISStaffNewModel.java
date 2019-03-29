package anandniketan.com.bhadajadmin.Model.MIS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MISStaffNewModel {

    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("Detail")
    @Expose
    private List<Detail> detail = null;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArray> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }

    public List<FinalArray> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArray> finalArray) {
        this.finalArray = finalArray;
    }


    public class Detail {

        @SerializedName("Staff absent more then 10%")
        @Expose
        private String staffAbsentMoreThen10;
        @SerializedName("More then 3 days absent")
        @Expose
        private String moreThen3DaysAbsent;
        @SerializedName("More then 3 days leave")
        @Expose
        private String moreThen3DaysLeave;

        public String getStaffAbsentMoreThen10() {
            return staffAbsentMoreThen10;
        }

        public void setStaffAbsentMoreThen10(String staffAbsentMoreThen10) {
            this.staffAbsentMoreThen10 = staffAbsentMoreThen10;
        }

        public String getMoreThen3DaysAbsent() {
            return moreThen3DaysAbsent;
        }

        public void setMoreThen3DaysAbsent(String moreThen3DaysAbsent) {
            this.moreThen3DaysAbsent = moreThen3DaysAbsent;
        }

        public String getMoreThen3DaysLeave() {
            return moreThen3DaysLeave;
        }

        public void setMoreThen3DaysLeave(String moreThen3DaysLeave) {
            this.moreThen3DaysLeave = moreThen3DaysLeave;
        }

    }


    public class FinalArray {

        @SerializedName("Department")
        @Expose
        private String department;
        @SerializedName("DepartmentID")
        @Expose
        private Integer departmentID;
        @SerializedName("TotalPresent")
        @Expose
        private String totalPresent;
        @SerializedName("TotalLeave")
        @Expose
        private Integer totalLeave;
        @SerializedName("TotalAbsent")
        @Expose
        private String totalAbsent;
        @SerializedName("Total")
        @Expose
        private String total;


        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public Integer getDepartmentID() {
            return departmentID;
        }

        public void setDepartmentID(Integer departmentID) {
            this.departmentID = departmentID;
        }

        public String getTotalPresent() {
            return totalPresent;
        }

        public void setTotalPresent(String totalPresent) {
            this.totalPresent = totalPresent;
        }

        public Integer getTotalLeave() {
            return totalLeave;
        }

        public void setTotalLeave(Integer totalLeave) {
            this.totalLeave = totalLeave;
        }

        public String getTotalAbsent() {
            return totalAbsent;
        }

        public void setTotalAbsent(String totalAbsent) {
            this.totalAbsent = totalAbsent;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    }
}
