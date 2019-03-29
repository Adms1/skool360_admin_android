package anandniketan.com.bhadajadmin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/24/2017.
 */

public class FinalArrayAccountFeesModel {
    @SerializedName("Collection")
    @Expose
    private List<AccountFeesCollectionModel> collection = new ArrayList<AccountFeesCollectionModel>();
    @SerializedName("StandardCollection")
    @Expose
    private List<AccountFeesCollectionModel> standardCollection = new ArrayList<AccountFeesCollectionModel>();
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("StandardID")
    @Expose
    private String standardID;
    @SerializedName("Term 1 Admission Fee")
    @Expose
    private String term1AdmissionFee;
    @SerializedName("Term 1 Caution Money")
    @Expose
    private String term1CautionMoney;
    @SerializedName("Term 1 Tuition Fee")
    @Expose
    private String term1TuitionFee;
    @SerializedName("Term 1 Imprest")
    @Expose
    private String term1Imprest;
    @SerializedName("Term 2 Tuition Fee")
    @Expose
    private String term2TuitionFee;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("GRNO")
    @Expose
    private String gRNO;
    @SerializedName("TotalFees")
    @Expose
    private String totalFees;
    @SerializedName("WaveOffAmt")
    @Expose
    private String waveOffAmt;
    @SerializedName("Discount")
    @Expose
    private String discount;
    @SerializedName("PayableAmt")
    @Expose
    private String payableAmt;
    @SerializedName("StudentID")
    @Expose
    private String studentID;
    @SerializedName("ReceivedFees")
    @Expose
    private String receivedFees;
    @SerializedName("PendingFees")
    @Expose
    private String pendingFees;
    @SerializedName("TotalImprest")
    @Expose
    private Double totalImprest;
    @SerializedName("DeductImprest")
    @Expose
    private String deductImprest;
    @SerializedName("RemainImprest")
    @Expose
    private String remainImprest;
    @SerializedName("Term")
    @Expose
    private String term;
    @SerializedName("StudentName")
    @Expose
    private String studentName;
    @SerializedName("ClassName")
    @Expose
    private String className;
    @SerializedName("SMSNo")
    @Expose
    private String sMSNo;
    @SerializedName("Date of Joining")
    @Expose
    private String dateOfJoining;
    @SerializedName("TotalTermFees")
    @Expose
    private String totalTermFees;
    @SerializedName("ReceiptTermFees")
    @Expose
    private String receiptTermFees;
    @SerializedName("RemainingTermFees")
    @Expose
    private String remainingTermFees;
    @SerializedName("TermLateFees")
    @Expose
    private String termLateFees;
    @SerializedName("LateFees")
    @Expose
    private String lateFees;
    @SerializedName("PreviousBalance")
    @Expose
    private String previousBalance;
    @SerializedName("AdmissionFees")
    @Expose
    private String admissionFees;
    @SerializedName("CautionFees")
    @Expose
    private String cautionFees;
    @SerializedName("TutionFees")
    @Expose
    private String tutionFees;
    @SerializedName("TransportFees")
    @Expose
    private String transportFees;
    @SerializedName("Imprest")
    @Expose
    private String imprest;
    @SerializedName("ReceiptTutionFees")
    @Expose
    private String receiptTutionFees;
    @SerializedName("RemainingTutionFees")
    @Expose
    private String remainingTutionFees;
    @SerializedName("ReceiptDiscount")
    @Expose
    private String receiptDiscount;
    @SerializedName("RemainingDiscount")
    @Expose
    private String remainingDiscount;
    @SerializedName("ReceiptAdmissionFees")
    @Expose
    private String receiptAdmissionFees;
    @SerializedName("RemainingAdmissionFees")
    @Expose
    private String remainingAdmissionFees;
    @SerializedName("ReceiptCautionFees")
    @Expose
    private String receiptCautionFees;
    @SerializedName("RemainingCautionFees")
    @Expose
    private String remainingCautionFees;
    @SerializedName("ReceiptTransportFees")
    @Expose
    private String receiptTransportFees;
    @SerializedName("RemainingTransportFees")
    @Expose
    private String remainingTransportFees;
    @SerializedName("ReceiptLateFees")
    @Expose
    private String receiptLateFees;
    @SerializedName("RemainingLateFees")
    @Expose
    private String remainingLateFees;
    @SerializedName("ReceiptImprest")
    @Expose
    private String receiptImprest;
    @SerializedName("RemainingImprest")
    @Expose
    private String remainingImprest;
    @SerializedName("PayDate")
    @Expose
    private String payDate;
    @SerializedName("Paid")
    @Expose
    private String paid;
    @SerializedName("TermDetail")
    @Expose
    private String termDetail;
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
    @SerializedName("TutionFee")
    @Expose
    private String tutionFee;
    @SerializedName("ImprestFee")
    @Expose
    private String imprestFee;
    @SerializedName("TransportFee")
    @Expose
    private String transportFee;
    @SerializedName("DiscountFee")
    @Expose
    private String discountFee;
    @SerializedName("PayPaidFees")
    @Expose
    private String payPaidFees;
    @SerializedName("CurrentOutstandingFees")
    @Expose
    private String currentOutstandingFees;
    @SerializedName("ChequeNo")
    @Expose
    private String chequeNo;
    @SerializedName("ChequeStatus")
    @Expose
    private String chequeStatus;
    @SerializedName("Data")
    @Expose
    private List<AccountFeesCollectionModel> data = new ArrayList<AccountFeesCollectionModel>();

    public List<AccountFeesCollectionModel> getCollection() {
        return collection;
    }

    public void setCollection(List<AccountFeesCollectionModel> collection) {
        this.collection = collection;
    }
    public List<AccountFeesCollectionModel> getStandardCollection() {
        return standardCollection;
    }
    public void setStandardCollection(List<AccountFeesCollectionModel> standardCollection) {
        this.standardCollection = standardCollection;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getStandardID() {
        return standardID;
    }

    public void setStandardID(String standardID) {
        this.standardID = standardID;
    }

    public String getTerm1AdmissionFee() {
        return term1AdmissionFee;
    }

    public void setTerm1AdmissionFee(String term1AdmissionFee) {
        this.term1AdmissionFee = term1AdmissionFee;
    }

    public String getTerm1CautionMoney() {
        return term1CautionMoney;
    }

    public void setTerm1CautionMoney(String term1CautionMoney) {
        this.term1CautionMoney = term1CautionMoney;
    }

    public String getTerm1TuitionFee() {
        return term1TuitionFee;
    }

    public void setTerm1TuitionFee(String term1TuitionFee) {
        this.term1TuitionFee = term1TuitionFee;
    }

    public String getTerm1Imprest() {
        return term1Imprest;
    }

    public void setTerm1Imprest(String term1Imprest) {
        this.term1Imprest = term1Imprest;
    }

    public String getTerm2TuitionFee() {
        return term2TuitionFee;
    }

    public void setTerm2TuitionFee(String term2TuitionFee) {
        this.term2TuitionFee = term2TuitionFee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getgRNO() {
        return gRNO;
    }

    public void setgRNO(String gRNO) {
        this.gRNO = gRNO;
    }

    public String getTotalFees() {
        return totalFees;
    }

    public void setTotalFees(String totalFees) {
        this.totalFees = totalFees;
    }

    public String getWaveOffAmt() {
        return waveOffAmt;
    }

    public void setWaveOffAmt(String waveOffAmt) {
        this.waveOffAmt = waveOffAmt;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPayableAmt() {
        return payableAmt;
    }

    public void setPayableAmt(String payableAmt) {
        this.payableAmt = payableAmt;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getReceivedFees() {
        return receivedFees;
    }

    public void setReceivedFees(String receivedFees) {
        this.receivedFees = receivedFees;
    }

    public String getPendingFees() {
        return pendingFees;
    }

    public void setPendingFees(String pendingFees) {
        this.pendingFees = pendingFees;
    }

    public Double getTotalImprest() {
        return totalImprest;
    }

    public void setTotalImprest(Double totalImprest) {
        this.totalImprest = totalImprest;
    }

    public String getDeductImprest() {
        return deductImprest;
    }

    public void setDeductImprest(String deductImprest) {
        this.deductImprest = deductImprest;
    }

    public String getRemainImprest() {
        return remainImprest;
    }

    public void setRemainImprest(String remainImprest) {
        this.remainImprest = remainImprest;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getsMSNo() {
        return sMSNo;
    }

    public void setsMSNo(String sMSNo) {
        this.sMSNo = sMSNo;
    }

    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getTotalTermFees() {
        return totalTermFees;
    }

    public void setTotalTermFees(String totalTermFees) {
        this.totalTermFees = totalTermFees;
    }

    public String getReceiptTermFees() {
        return receiptTermFees;
    }

    public void setReceiptTermFees(String receiptTermFees) {
        this.receiptTermFees = receiptTermFees;
    }

    public String getRemainingTermFees() {
        return remainingTermFees;
    }

    public void setRemainingTermFees(String remainingTermFees) {
        this.remainingTermFees = remainingTermFees;
    }

    public String getTermLateFees() {
        return termLateFees;
    }

    public void setTermLateFees(String termLateFees) {
        this.termLateFees = termLateFees;
    }

    public String getLateFees() {
        return lateFees;
    }

    public void setLateFees(String lateFees) {
        this.lateFees = lateFees;
    }

    public String getPreviousBalance() {
        return previousBalance;
    }

    public void setPreviousBalance(String previousBalance) {
        this.previousBalance = previousBalance;
    }

    public String getAdmissionFees() {
        return admissionFees;
    }

    public void setAdmissionFees(String admissionFees) {
        this.admissionFees = admissionFees;
    }

    public String getCautionFees() {
        return cautionFees;
    }

    public void setCautionFees(String cautionFees) {
        this.cautionFees = cautionFees;
    }

    public String getTutionFees() {
        return tutionFees;
    }

    public void setTutionFees(String tutionFees) {
        this.tutionFees = tutionFees;
    }

    public String getTransportFees() {
        return transportFees;
    }

    public void setTransportFees(String transportFees) {
        this.transportFees = transportFees;
    }

    public String getImprest() {
        return imprest;
    }

    public void setImprest(String imprest) {
        this.imprest = imprest;
    }

    public String getReceiptTutionFees() {
        return receiptTutionFees;
    }

    public void setReceiptTutionFees(String receiptTutionFees) {
        this.receiptTutionFees = receiptTutionFees;
    }

    public String getRemainingTutionFees() {
        return remainingTutionFees;
    }

    public void setRemainingTutionFees(String remainingTutionFees) {
        this.remainingTutionFees = remainingTutionFees;
    }

    public String getReceiptDiscount() {
        return receiptDiscount;
    }

    public void setReceiptDiscount(String receiptDiscount) {
        this.receiptDiscount = receiptDiscount;
    }

    public String getRemainingDiscount() {
        return remainingDiscount;
    }

    public void setRemainingDiscount(String remainingDiscount) {
        this.remainingDiscount = remainingDiscount;
    }

    public String getReceiptAdmissionFees() {
        return receiptAdmissionFees;
    }

    public void setReceiptAdmissionFees(String receiptAdmissionFees) {
        this.receiptAdmissionFees = receiptAdmissionFees;
    }

    public String getRemainingAdmissionFees() {
        return remainingAdmissionFees;
    }

    public void setRemainingAdmissionFees(String remainingAdmissionFees) {
        this.remainingAdmissionFees = remainingAdmissionFees;
    }

    public String getReceiptCautionFees() {
        return receiptCautionFees;
    }

    public void setReceiptCautionFees(String receiptCautionFees) {
        this.receiptCautionFees = receiptCautionFees;
    }

    public String getRemainingCautionFees() {
        return remainingCautionFees;
    }

    public void setRemainingCautionFees(String remainingCautionFees) {
        this.remainingCautionFees = remainingCautionFees;
    }

    public String getReceiptTransportFees() {
        return receiptTransportFees;
    }

    public void setReceiptTransportFees(String receiptTransportFees) {
        this.receiptTransportFees = receiptTransportFees;
    }

    public String getRemainingTransportFees() {
        return remainingTransportFees;
    }

    public void setRemainingTransportFees(String remainingTransportFees) {
        this.remainingTransportFees = remainingTransportFees;
    }

    public String getReceiptLateFees() {
        return receiptLateFees;
    }

    public void setReceiptLateFees(String receiptLateFees) {
        this.receiptLateFees = receiptLateFees;
    }

    public String getRemainingLateFees() {
        return remainingLateFees;
    }

    public void setRemainingLateFees(String remainingLateFees) {
        this.remainingLateFees = remainingLateFees;
    }

    public String getReceiptImprest() {
        return receiptImprest;
    }

    public void setReceiptImprest(String receiptImprest) {
        this.receiptImprest = receiptImprest;
    }

    public String getRemainingImprest() {
        return remainingImprest;
    }

    public void setRemainingImprest(String remainingImprest) {
        this.remainingImprest = remainingImprest;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getTermDetail() {
        return termDetail;
    }

    public void setTermDetail(String termDetail) {
        this.termDetail = termDetail;
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

    public String getTutionFee() {
        return tutionFee;
    }

    public void setTutionFee(String tutionFee) {
        this.tutionFee = tutionFee;
    }

    public String getImprestFee() {
        return imprestFee;
    }

    public void setImprestFee(String imprestFee) {
        this.imprestFee = imprestFee;
    }

    public String getTransportFee() {
        return transportFee;
    }

    public void setTransportFee(String transportFee) {
        this.transportFee = transportFee;
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

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getChequeStatus() {
        return chequeStatus;
    }

    public void setChequeStatus(String chequeStatus) {
        this.chequeStatus = chequeStatus;
    }

    public List<AccountFeesCollectionModel> getData() {
        return data;
    }

    public void setData(List<AccountFeesCollectionModel> data) {
        this.data = data;
    }
}
