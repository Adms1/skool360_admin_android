package anandniketan.com.skool360.Model.HR;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyHouseKeepingModel {

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

        @SerializedName("Repairs&Maintenance")
        @Expose
        private String repairsmaintenance;
        @SerializedName("Toilets")
        @Expose
        private String toilets;
        @SerializedName("WashArea")
        @Expose
        private String washarea;
        @SerializedName("ClassRoom")
        @Expose
        private String classroom;
        @SerializedName("TeacherComplain")
        @Expose
        private String teachercomplain;

        @SerializedName("MaidsPresent/Absent")
        @Expose
        private String maidspresentabsent;

        @SerializedName("Gardner")
        @Expose
        private String gardner;

        @SerializedName("Suggestion")
        @Expose
        private String suggestion;

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

        public String getRepairsmaintenance() {
            return repairsmaintenance;
        }

        public void setRepairsmaintenance(String repairsmaintenance) {
            this.repairsmaintenance = repairsmaintenance;
        }

        public String getToilets() {
            return toilets;
        }

        public void setToilets(String toilets) {
            this.toilets = toilets;
        }

        public String getWasharea() {
            return washarea;
        }

        public void setWasharea(String washarea) {
            this.washarea = washarea;
        }

        public String getClassroom() {
            return classroom;
        }

        public void setClassroom(String classroom) {
            this.classroom = classroom;
        }

        public String getTeachercomplain() {
            return teachercomplain;
        }

        public void setTeachercomplain(String teachercomplain) {
            this.teachercomplain = teachercomplain;
        }

        public String getMaidspresentabsent() {
            return maidspresentabsent;
        }

        public void setMaidspresentabsent(String maidspresentabsent) {
            this.maidspresentabsent = maidspresentabsent;
        }

        public String getGardner() {
            return gardner;
        }

        public void setGardner(String gardner) {
            this.gardner = gardner;
        }

        public String getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(String suggestion) {
            this.suggestion = suggestion;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }
    }
}

