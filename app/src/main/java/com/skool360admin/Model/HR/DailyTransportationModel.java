package com.skool360admin.Model.HR;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyTransportationModel {

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
        @SerializedName("RouteProblem")
        @Expose
        private String routeProblem;
        @SerializedName("DriverComplaint")
        @Expose
        private String driverComplaint;
        @SerializedName("ParentsComplaint")
        @Expose
        private String parentsComplaint;
        @SerializedName("TimingProblem")
        @Expose
        private String timingProblem;
        @SerializedName("VehicleProblem")
        @Expose
        private String vehicleProblem;
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

        public String getRouteProblem() {
            return routeProblem;
        }

        public void setRouteProblem(String routeProblem) {
            this.routeProblem = routeProblem;
        }

        public String getDriverComplaint() {
            return driverComplaint;
        }

        public void setDriverComplaint(String driverComplaint) {
            this.driverComplaint = driverComplaint;
        }

        public String getParentsComplaint() {
            return parentsComplaint;
        }

        public void setParentsComplaint(String parentsComplaint) {
            this.parentsComplaint = parentsComplaint;
        }

        public String getTimingProblem() {
            return timingProblem;
        }

        public void setTimingProblem(String timingProblem) {
            this.timingProblem = timingProblem;
        }

        public String getVehicleProblem() {
            return vehicleProblem;
        }

        public void setVehicleProblem(String vehicleProblem) {
            this.vehicleProblem = vehicleProblem;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

    }
}
