package com.skool360admin.Model.Notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotificationModel {

    @SerializedName("Success")
    @Expose
    private String success;

    @SerializedName("DOB")
    @Expose
    private String dob;

    @SerializedName("FinalArray")
    @Expose
    private ArrayList<FinalArray> finalarray;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public ArrayList<FinalArray> getFinalarray() {
        return finalarray;
    }

    public void setFinalarray(ArrayList<FinalArray> finalarray) {
        this.finalarray = finalarray;
    }

    public class FinalArray {

        @SerializedName("NotificationType")
        @Expose
        private String notificationtype;

        @SerializedName("Name")
        @Expose
        private String name;

        @SerializedName("Department")
        @Expose
        private String department;

        @SerializedName("Type")
        @Expose
        private String type;

        @SerializedName("ID")
        @Expose
        private String id;

        @SerializedName("Flag")
        @Expose
        private String flag;

        @SerializedName("Message")
        @Expose
        private String message;

        @SerializedName("PKID")
        @Expose
        private String pkid;

        @SerializedName("Date")
        @Expose
        private String date;

        public String getNotificationtype() {
            return notificationtype;
        }

        public void setNotificationtype(String notificationtype) {
            this.notificationtype = notificationtype;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPkid() {
            return pkid;
        }

        public void setPkid(String pkid) {
            this.pkid = pkid;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

}
