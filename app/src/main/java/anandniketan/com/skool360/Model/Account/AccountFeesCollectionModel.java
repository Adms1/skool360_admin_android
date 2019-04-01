package anandniketan.com.skool360.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/27/2017.
 */

public class AccountFeesCollectionModel {
    @SerializedName("TermID")
    @Expose
    private Integer termID;
    @SerializedName("TotalAmt")
    @Expose
    private Double totalAmt;
    @SerializedName("TotalAmtStudent")
    @Expose
    private String totalAmtStudent;
    @SerializedName("TotalRcv")
    @Expose
    private Double totalRcv;
    @SerializedName("TotalRcvStudent")
    @Expose
    private String totalRcvStudent;
    @SerializedName("TotalDue")
    @Expose
    private Double totalDue;
    @SerializedName("TotalDueStudent")
    @Expose
    private String totalDueStudent;
    @SerializedName("TotalDuePer")
    @Expose
    private String totalDuePer;
    @SerializedName("DueStudentPer")
    @Expose
    private String dueStudentPer;
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("StdTotalFees")
    @Expose
    private Double stdTotalFees;
    @SerializedName("StdStudent")
    @Expose
    private String stdStudent;
    @SerializedName("StdTotalRcv")
    @Expose
    private Double stdTotalRcv;
    @SerializedName("StdStudentRcv")
    @Expose
    private String stdStudentRcv;
    @SerializedName("StdTotalDues")
    @Expose
    private Double stdTotalDues;
    @SerializedName("StdStudentDues")
    @Expose
    private String stdStudentDues;
    @SerializedName("Term")
    @Expose
    private String term;
    @SerializedName("TermDetail")
    @Expose
    private String termDetail;
    @SerializedName("GRNO")
    @Expose
    private String gRNO;
    @SerializedName("PayMode")
    @Expose
    private String payMode;
    @SerializedName("PaidFee")
    @Expose
    private String paidFee;
    @SerializedName("ReceiptNo")
    @Expose
    private String receiptNo;
    @SerializedName("AdmissionFee")
    @Expose
    private String admissionFee;
    @SerializedName("CautionFee")
    @Expose
    private String cautionFee;
    @SerializedName("PreviousFees")
    @Expose
    private String previousFees;
    @SerializedName("TuitionFee")
    @Expose
    private String tuitionFee;
    @SerializedName("Transport")
    @Expose
    private String transport;
    @SerializedName("ImprestFee")
    @Expose
    private String imprestFee;
    @SerializedName("LatesFee")
    @Expose
    private String latesFee;
    @SerializedName("DiscountFee")
    @Expose
    private String discountFee;
    @SerializedName("PayPaidFees")
    @Expose
    private String payPaidFees;
    @SerializedName("CurrentOutstandingFees")
    @Expose
    private String currentOutstandingFees;
    public Integer getTermID() {
        return termID;
    }

    public void setTermID(Integer termID) {
        this.termID = termID;
    }

    public Double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getTotalAmtStudent() {
        return totalAmtStudent;
    }

    public void setTotalAmtStudent(String totalAmtStudent) {
        this.totalAmtStudent = totalAmtStudent;
    }

    public Double getTotalRcv() {
        return totalRcv;
    }

    public void setTotalRcv(Double totalRcv) {
        this.totalRcv = totalRcv;
    }

    public String getTotalRcvStudent() {
        return totalRcvStudent;
    }

    public void setTotalRcvStudent(String totalRcvStudent) {
        this.totalRcvStudent = totalRcvStudent;
    }

    public Double getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(Double totalDue) {
        this.totalDue = totalDue;
    }

    public String getTotalDueStudent() {
        return totalDueStudent;
    }

    public void setTotalDueStudent(String totalDueStudent) {
        this.totalDueStudent = totalDueStudent;
    }

    public String getTotalDuePer() {
        return totalDuePer;
    }

    public void setTotalDuePer(String totalDuePer) {
        this.totalDuePer = totalDuePer;
    }

    public String getDueStudentPer() {
        return dueStudentPer;
    }

    public void setDueStudentPer(String dueStudentPer) {
        this.dueStudentPer = dueStudentPer;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public Double getStdTotalFees() {
        return stdTotalFees;
    }

    public void setStdTotalFees(Double stdTotalFees) {
        this.stdTotalFees = stdTotalFees;
    }

    public String getStdStudent() {
        return stdStudent;
    }

    public void setStdStudent(String stdStudent) {
        this.stdStudent = stdStudent;
    }

    public Double getStdTotalRcv() {
        return stdTotalRcv;
    }

    public void setStdTotalRcv(Double stdTotalRcv) {
        this.stdTotalRcv = stdTotalRcv;
    }

    public String getStdStudentRcv() {
        return stdStudentRcv;
    }

    public void setStdStudentRcv(String stdStudentRcv) {
        this.stdStudentRcv = stdStudentRcv;
    }

    public Double getStdTotalDues() {
        return stdTotalDues;
    }

    public void setStdTotalDues(Double stdTotalDues) {
        this.stdTotalDues = stdTotalDues;
    }

    public String getStdStudentDues() {
        return stdStudentDues;
    }

    public void setStdStudentDues(String stdStudentDues) {
        this.stdStudentDues = stdStudentDues;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTermDetail() {
        return termDetail;
    }

    public void setTermDetail(String termDetail) {
        this.termDetail = termDetail;
    }

    public String getgRNO() {
        return gRNO;
    }

    public void setgRNO(String gRNO) {
        this.gRNO = gRNO;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(String paidFee) {
        this.paidFee = paidFee;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getAdmissionFee() {
        return admissionFee;
    }

    public void setAdmissionFee(String admissionFee) {
        this.admissionFee = admissionFee;
    }

    public String getCautionFee() {
        return cautionFee;
    }

    public void setCautionFee(String cautionFee) {
        this.cautionFee = cautionFee;
    }

    public String getPreviousFees() {
        return previousFees;
    }

    public void setPreviousFees(String previousFees) {
        this.previousFees = previousFees;
    }

    public String getTuitionFee() {
        return tuitionFee;
    }

    public void setTuitionFee(String tuitionFee) {
        this.tuitionFee = tuitionFee;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getImprestFee() {
        return imprestFee;
    }

    public void setImprestFee(String imprestFee) {
        this.imprestFee = imprestFee;
    }

    public String getLatesFee() {
        return latesFee;
    }

    public void setLatesFee(String latesFee) {
        this.latesFee = latesFee;
    }

    public String getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(String discountFee) {
        this.discountFee = discountFee;
    }

    public String getPayPaidFees() {
        return payPaidFees;
    }

    public void setPayPaidFees(String payPaidFees) {
        this.payPaidFees = payPaidFees;
    }

    public String getCurrentOutstandingFees() {
        return currentOutstandingFees;
    }

    public void setCurrentOutstandingFees(String currentOutstandingFees) {
        this.currentOutstandingFees = currentOutstandingFees;
    }
}
