package com.skool360admin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccountFeesModel {


        @SerializedName("Success")
        @Expose
        private String success;
        @SerializedName("Term1")
        @Expose
        private List<Term1> term1 = null;
        @SerializedName("Term2")
        @Expose
        private List<Term2> term2 = null;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public List<Term1> getTerm1() {
            return term1;
        }

        public void setTerm1(List<Term1> term1) {
            this.term1 = term1;
        }

        public List<Term2> getTerm2() {
            return term2;
        }

        public void setTerm2(List<Term2> term2) {
            this.term2 = term2;
        }


    public class Term1 {

        @SerializedName("TotalAmount")
        @Expose
        private String totalAmount;
        @SerializedName("RecievedAmount")
        @Expose
        private String recievedAmount;
        @SerializedName("DueAmount")
        @Expose
        private String dueAmount;

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getRecievedAmount() {
            return recievedAmount;
        }

        public void setRecievedAmount(String recievedAmount) {
            this.recievedAmount = recievedAmount;
        }

        public String getDueAmount() {
            return dueAmount;
        }

        public void setDueAmount(String dueAmount) {
            this.dueAmount = dueAmount;
        }

    }


    public class Term2 {

        @SerializedName("TotalAmount")
        @Expose
        private String totalAmount;
        @SerializedName("RecievedAmount")
        @Expose
        private String recievedAmount;
        @SerializedName("DueAmount")
        @Expose
        private String dueAmount;

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getRecievedAmount() {
            return recievedAmount;
        }

        public void setRecievedAmount(String recievedAmount) {
            this.recievedAmount = recievedAmount;
        }

        public String getDueAmount() {
            return dueAmount;
        }

        public void setDueAmount(String dueAmount) {
            this.dueAmount = dueAmount;
        }

    }
}
