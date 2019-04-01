package anandniketan.com.skool360.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OnlineTransactionModel {

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

        @SerializedName("TransactionID")
        @Expose
        private String transactionid;
        @SerializedName("PaymentID")
        @Expose
        private Double paymentid;
        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("StudentName")
        @Expose
        private String studentname;
        @SerializedName("Grade")
        @Expose
        private String grade;
        @SerializedName("Amount")
        @Expose
        private String amount;
        @SerializedName("Status")
        @Expose
        private String status;

        public String getTransactionid() {
            return transactionid;
        }

        public void setTransactionid(String transactionid) {
            this.transactionid = transactionid;
        }

        public Double getPaymentid() {
            return paymentid;
        }

        public void setPaymentid(Double paymentid) {
            this.paymentid = paymentid;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStudentname() {
            return studentname;
        }

        public void setStudentname(String studentname) {
            this.studentname = studentname;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
