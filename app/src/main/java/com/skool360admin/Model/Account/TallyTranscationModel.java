package com.skool360admin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TallyTranscationModel {

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
        @SerializedName("VoucherNo")
        @Expose
        private String voucherNo;
        @SerializedName("StudentName")
        @Expose
        private String studentName;
        @SerializedName("GRNO")
        @Expose
        private String gRNO;
        @SerializedName("Standard")
        @Expose
        private String standard;
        @SerializedName("Narration")
        @Expose
        private String narration;
        @SerializedName("Amount")
        @Expose
        private Double amount;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("PaymentMode")
        @Expose
        private String paymentMode;
        @SerializedName("IsCanceled")
        @Expose
        private String isCanceled;
        @SerializedName("ViewBtnVisible")
        @Expose
        private Boolean viewBtnVisible;
        @SerializedName("URL")
        @Expose
        private String uRL;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getVoucherNo() {
            return voucherNo;
        }

        public void setVoucherNo(String voucherNo) {
            this.voucherNo = voucherNo;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getGRNO() {
            return gRNO;
        }

        public void setGRNO(String gRNO) {
            this.gRNO = gRNO;
        }

        public String getStandard() {
            return standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public String getNarration() {
            return narration;
        }

        public void setNarration(String narration) {
            this.narration = narration;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPaymentMode() {
            return paymentMode;
        }

        public void setPaymentMode(String paymentMode) {
            this.paymentMode = paymentMode;
        }

        public String getIsCanceled() {
            return isCanceled;
        }

        public void setIsCanceled(String isCanceled) {
            this.isCanceled = isCanceled;
        }

        public Boolean getViewBtnVisible() {
            return viewBtnVisible;
        }

        public void setViewBtnVisible(Boolean viewBtnVisible) {
            this.viewBtnVisible = viewBtnVisible;
        }

        public String getURL() {
            return uRL;
        }

        public void setURL(String uRL) {
            this.uRL = uRL;
        }

    }
}
