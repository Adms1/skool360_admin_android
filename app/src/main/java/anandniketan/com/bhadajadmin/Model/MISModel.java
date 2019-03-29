package anandniketan.com.bhadajadmin.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MISModel {

    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArray> finalArray = null;

    @SerializedName("DetailArray")
    @Expose
    private List<DetailArray> detailArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FinalArray> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArray> finalArray) {
        this.finalArray = finalArray;
    }

    public List<DetailArray> getDetailArray() {
        return detailArray;
    }

    public void setDetailArray(List<DetailArray> detailArray) {
        this.detailArray = detailArray;
    }

    public class DetailArray {
        @SerializedName("Count")
        @Expose
        private String count;

        @SerializedName("Data")
        @Expose
        private List<FinalArray> data = null;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<FinalArray> getData() {
            return data;
        }

        public void setData(List<FinalArray> data) {
            this.data = data;
        }
    }

    public class FinalArray {

        @SerializedName("Total")
        @Expose
        private Integer total;

        @SerializedName("TotalPresent")
        @Expose
        private Integer totalPresent;

        @SerializedName("TotalAbsent")
        @Expose
        private Integer totalAbsent;

        @SerializedName("TotalLeave")
        @Expose
        private Integer totalLeave;
        @SerializedName("TotalStudentANT")
        @Expose
        private Integer totalStudentANT;

        @SerializedName("TotalConsistanceAbsent")
        @Expose
        private String totalConsistanceAbsent;

        @SerializedName("Attendance less then 70%")
        @Expose
        private String attendance;

        @SerializedName("Between alumini left")
        @Expose
        private String betAlumini;

        @SerializedName("DailyEntryTotal")
        @Expose
        private Integer dailyEntryTotal;

        @SerializedName("DailyEntryDone")
        @Expose
        private Integer dailyEntryDone;
        @SerializedName("HWTotal")
        @Expose
        private Integer hWTotal;
        @SerializedName("HWDone")
        @Expose
        private Integer hWDone;
        @SerializedName("CWTotal")
        @Expose
        private Integer cWTotal;
        @SerializedName("CWDone")
        @Expose
        private Integer cWDone;
        @SerializedName("TotalToBeCollected")
        @Expose
        private String totalToBeCollected;
        @SerializedName("Term1Fees")
        @Expose
        private String term1Fees;
        @SerializedName("Term2Fees")
        @Expose
        private String term2Fees;
        @SerializedName("Term1FeesCollection")
        @Expose
        private String term1FeesCollection;
        @SerializedName("Term2FeesCollection")
        @Expose
        private String term2FeesCollection;
        @SerializedName("TotalOS")
        @Expose
        private String totalOS;
        @SerializedName("CashCollection")
        @Expose
        private String cashCollection;
        @SerializedName("ChequeDD")
        @Expose
        private String chequeDD;
        @SerializedName("Online")
        @Expose
        private String online;

        @SerializedName("TotalInquiry")
        @Expose
        private Integer totalInquiry;
        @SerializedName("IssueAddmissionForm")
        @Expose
        private Integer issueAddmissionForm;
        @SerializedName("RcvAddmissionForm")
        @Expose
        private Integer rcvAddmissionForm;
        @SerializedName("CallForInterview")
        @Expose
        private Integer callForInterview;
        @SerializedName("ComeForInterview")
        @Expose
        private Integer comeForInterview;
        @SerializedName("ConfirmAddmission")
        @Expose
        private Integer confirmAddmission;
        @SerializedName("RejectedInquiry")
        @Expose
        private Integer rejectedInquiry;
        @SerializedName("InquiryFeesRcvd")
        @Expose
        private Integer inquiryFeesRcvd;

        @SerializedName("FeesNotPaid")
        @Expose
        private Integer feesNotPaid;


        @SerializedName("SMSSent")
        @Expose
        private String sMSSent;
        @SerializedName("SMSDelivered")
        @Expose
        private String sMSDelivered;
        @SerializedName("SMSPending")
        @Expose
        private String sMSPending;

        public String getAttendance() {
            return attendance;
        }

        public void setAttendance(String attendance) {
            this.attendance = attendance;
        }
        public Integer getTotalInquiry() {
            return totalInquiry;
        }

        public void setTotalInquiry(Integer totalInquiry) {
            this.totalInquiry = totalInquiry;
        }

        public Integer getIssueAddmissionForm() {
            return issueAddmissionForm;
        }

        public void setIssueAddmissionForm(Integer issueAddmissionForm) {
            this.issueAddmissionForm = issueAddmissionForm;
        }

        public String getBetAlumini() {
            return betAlumini;
        }

        public void setBetAlumini(String betAlumini) {
            this.betAlumini = betAlumini;
        }

        public Integer getRcvAddmissionForm() {
            return rcvAddmissionForm;
        }

        public void setRcvAddmissionForm(Integer rcvAddmissionForm) {
            this.rcvAddmissionForm = rcvAddmissionForm;
        }

        public Integer getCallForInterview() {
            return callForInterview;
        }

        public void setCallForInterview(Integer callForInterview) {
            this.callForInterview = callForInterview;
        }

        public Integer getComeForInterview() {
            return comeForInterview;
        }

        public void setComeForInterview(Integer comeForInterview) {
            this.comeForInterview = comeForInterview;
        }

        public Integer getConfirmAddmission() {
            return confirmAddmission;
        }

        public void setConfirmAddmission(Integer confirmAddmission) {
            this.confirmAddmission = confirmAddmission;
        }

        public Integer getRejectedInquiry() {
            return rejectedInquiry;
        }

        public void setRejectedInquiry(Integer rejectedInquiry) {
            this.rejectedInquiry = rejectedInquiry;
        }

        public Integer getInquiryFeesRcvd() {
            return inquiryFeesRcvd;
        }

        public void setInquiryFeesRcvd(Integer inquiryFeesRcvd) {
            this.inquiryFeesRcvd = inquiryFeesRcvd;
        }


        public String getTotalToBeCollected() {
            return totalToBeCollected;
        }

        public void setTotalToBeCollected(String totalToBeCollected) {
            this.totalToBeCollected = totalToBeCollected;
        }

        public String getTerm1Fees() {
            return term1Fees;
        }

        public void setTerm1Fees(String term1Fees) {
            this.term1Fees = term1Fees;
        }

        public String getTerm2Fees() {
            return term2Fees;
        }

        public void setTerm2Fees(String term2Fees) {
            this.term2Fees = term2Fees;
        }

        public String getTerm1FeesCollection() {
            return term1FeesCollection;
        }

        public void setTerm1FeesCollection(String term1FeesCollection) {
            this.term1FeesCollection = term1FeesCollection;
        }

        public String getTerm2FeesCollection() {
            return term2FeesCollection;
        }

        public void setTerm2FeesCollection(String term2FeesCollection) {
            this.term2FeesCollection = term2FeesCollection;
        }

        public String getTotalOS() {
            return totalOS;
        }

        public void setTotalOS(String totalOS) {
            this.totalOS = totalOS;
        }

        public String getCashCollection() {
            return cashCollection;
        }

        public void setCashCollection(String cashCollection) {
            this.cashCollection = cashCollection;
        }

        public String getChequeDD() {
            return chequeDD;
        }

        public void setChequeDD(String chequeDD) {
            this.chequeDD = chequeDD;
        }

        public String getOnline() {
            return online;
        }

        public void setOnline(String online) {
            this.online = online;
        }



        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getTotalPresent() {
            return totalPresent;
        }

        public void setTotalPresent(Integer totalPresent) {
            this.totalPresent = totalPresent;
        }

        public Integer getTotalAbsent() {
            return totalAbsent;
        }

        public void setTotalAbsent(Integer totalAbsent) {
            this.totalAbsent = totalAbsent;
        }

        public Integer getTotalLeave() {
            return totalLeave;
        }

        public void setTotalLeave(Integer totalLeave) {
            this.totalLeave = totalLeave;
        }

        public Integer getTotalStudentANT() {
            return totalStudentANT;
        }

        public void setTotalStudentANT(Integer totalStudentANT) {
            this.totalStudentANT = totalStudentANT;
        }

        public Integer getDailyEntryTotal() {
            return dailyEntryTotal;
        }

        public void setDailyEntryTotal(Integer dailyEntryTotal) {
            this.dailyEntryTotal = dailyEntryTotal;
        }

        public Integer getDailyEntryDone() {
            return dailyEntryDone;
        }

        public void setDailyEntryDone(Integer dailyEntryDone) {
            this.dailyEntryDone = dailyEntryDone;
        }

        public Integer getHWTotal() {
            return hWTotal;
        }

        public void setHWTotal(Integer hWTotal) {
            this.hWTotal = hWTotal;
        }

        public Integer getHWDone() {
            return hWDone;
        }

        public void setHWDone(Integer hWDone) {
            this.hWDone = hWDone;
        }

        public Integer getCWTotal() {
            return cWTotal;
        }

        public void setCWTotal(Integer cWTotal) {
            this.cWTotal = cWTotal;
        }

        public Integer getCWDone() {
            return cWDone;
        }

        public void setCWDone(Integer cWDone) {
            this.cWDone = cWDone;
        }

        public String getTotalConsistanceAbsent() {
            return totalConsistanceAbsent;
        }

        public void setTotalConsistanceAbsent(String totalConsistanceAbsent) {
            this.totalConsistanceAbsent = totalConsistanceAbsent;
        }


        public String getSMSSent() {
            return sMSSent;
        }

        public void setSMSSent(String sMSSent) {
            this.sMSSent = sMSSent;
        }

        public String getSMSDelivered() {
            return sMSDelivered;
        }

        public void setSMSDelivered(String sMSDelivered) {
            this.sMSDelivered = sMSDelivered;
        }

        public String getSMSPending() {
            return sMSPending;
        }

        public void setSMSPending(String sMSPending) {
            this.sMSPending = sMSPending;
        }


        public Integer getFeesNotPaid() {
            return feesNotPaid;
        }

        public void setFeesNotPaid(Integer feesNotPaid) {
            this.feesNotPaid = feesNotPaid;
        }
    }

}