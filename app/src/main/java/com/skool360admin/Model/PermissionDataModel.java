package com.skool360admin.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PermissionDataModel {

    @SerializedName("Success")
    @Expose
    private String success;

    @SerializedName("FinalArray")
    @Expose
    private ArrayList<FinalArray> finalarray;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<FinalArray> getFinalarray() {
        return finalarray;
    }

    public void setFinalarray(ArrayList<FinalArray> finalarray) {
        this.finalarray = finalarray;
    }

    public class FinalArray {
        @SerializedName("Name")
        @Expose
        private String name;

        @SerializedName("Detail")
        @Expose
        private ArrayList<Detaill> detail;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<Detaill> getDetail() {
            return detail;
        }

        public void setDetail(ArrayList<Detaill> detail) {
            this.detail = detail;
        }
    }

    public class Detaill {
        @SerializedName("PageName")
        @Expose
        private String pagename;

        @SerializedName("Status")
        @Expose
        private String status;

        @SerializedName("IsUserUpdate")
        @Expose
        private String isuserupdate;

        @SerializedName("IsUserDelete")
        @Expose
        private String isuserdelete;

        @SerializedName("IsUserView")
        @Expose
        private String isuserview;

        public String getPagename() {
            return pagename;
        }

        public void setPagename(String pagename) {
            this.pagename = pagename;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIsuserupdate() {
            return isuserupdate;
        }

        public void setIsuserupdate(String isuserupdate) {
            this.isuserupdate = isuserupdate;
        }

        public String getIsuserdelete() {
            return isuserdelete;
        }

        public void setIsuserdelete(String isuserdelete) {
            this.isuserdelete = isuserdelete;
        }

        public String getIsuserview() {
            return isuserview;
        }

        public void setIsuserview(String isuserview) {
            this.isuserview = isuserview;
        }
    }

}
