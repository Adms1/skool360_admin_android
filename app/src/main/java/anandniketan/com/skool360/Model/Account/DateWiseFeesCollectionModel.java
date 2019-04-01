package anandniketan.com.skool360.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DateWiseFeesCollectionModel {

    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("OpeningBalance")
    @Expose
    private Double openingBalance;
    @SerializedName("ClosingBalance")
    @Expose
    private Double closingBalance;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArray> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public Double getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(Double closingBalance) {
        this.closingBalance = closingBalance;
    }

    public List<FinalArray> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArray> finalArray) {
        this.finalArray = finalArray;
    }



    public class FeeReceiptDetail {

        @SerializedName("CreateDate")
        @Expose
        private String createDate;
        @SerializedName("Term")
        @Expose
        private String term;
        @SerializedName("Amount")
        @Expose
        private Double amount;
        @SerializedName("PaymentMode")
        @Expose
        private String paymentMode;
        @SerializedName("EncryptedTransID")
        @Expose
        private String encryptedTransID;
        @SerializedName("STudentId")
        @Expose
        private Integer sTudentId;
        @SerializedName("IsCanceled")
        @Expose
        private String isCanceled;
        @SerializedName("URL")
        @Expose
        private String uRL;

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getTerm() {
            return term;
        }

        public void setTerm(String term) {
            this.term = term;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public String getPaymentMode() {
            return paymentMode;
        }

        public void setPaymentMode(String paymentMode) {
            this.paymentMode = paymentMode;
        }

        public String getEncryptedTransID() {
            return encryptedTransID;
        }

        public void setEncryptedTransID(String encryptedTransID) {
            this.encryptedTransID = encryptedTransID;
        }

        public Integer getSTudentId() {
            return sTudentId;
        }

        public void setSTudentId(Integer sTudentId) {
            this.sTudentId = sTudentId;
        }

        public String getIsCanceled() {
            return isCanceled;
        }

        public void setIsCanceled(String isCanceled) {
            this.isCanceled = isCanceled;
        }

        public String getURL() {
            return uRL;
        }

        public void setURL(String uRL) {
            this.uRL = uRL;
        }

    }

    public class FinalArray {

        @SerializedName("Amount")
        @Expose
        private String amount;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("GRNO")
        @Expose
        private String gRNO;
        @SerializedName("Standard")
        @Expose
        private String standard;
        @SerializedName("Class")
        @Expose
        private String _class;
        @SerializedName("TotalAmt")
        @Expose
        private String totalAmt;
        @SerializedName("StudentID")
        @Expose
        private Integer studentID;
        @SerializedName("OpeningBalance")
        @Expose
        private String openingBalance;
        @SerializedName("FeeReceiptDetail")
        @Expose
        private List<FeeReceiptDetail> feeReceiptDetail = null;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getClass_() {
            return _class;
        }

        public void setClass_(String _class) {
            this._class = _class;
        }

        public String getTotalAmt() {
            return totalAmt;
        }

        public void setTotalAmt(String totalAmt) {
            this.totalAmt = totalAmt;
        }

        public Integer getStudentID() {
            return studentID;
        }

        public void setStudentID(Integer studentID) {
            this.studentID = studentID;
        }

        public String getOpeningBalance() {
            return openingBalance;
        }

        public void setOpeningBalance(String openingBalance) {
            this.openingBalance = openingBalance;
        }

        public List<FeeReceiptDetail> getFeeReceiptDetail() {
            return feeReceiptDetail;
        }

        public void setFeeReceiptDetail(List<FeeReceiptDetail> feeReceiptDetail) {
            this.feeReceiptDetail = feeReceiptDetail;
        }

    }
}
