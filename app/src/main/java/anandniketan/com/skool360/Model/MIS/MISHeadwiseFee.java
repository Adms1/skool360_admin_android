package anandniketan.com.skool360.Model.MIS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


// created by antra 7/1/2019
public class MISHeadwiseFee {

    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private ArrayList<FinalArray> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<FinalArray> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(ArrayList<FinalArray> finalArray) {
        this.finalArray = finalArray;
    }

    public class TermDatum {

        @SerializedName("Head")
        @Expose
        private String head;
        @SerializedName("Term1Amt")
        @Expose
        private String totalFeesTerm1;
        @SerializedName("Term1AmtRecieved")
        @Expose
        private String recievedFeesTerm1;
        @SerializedName("Term2Amt")
        @Expose
        private String totalFeesTerm2;
        @SerializedName("Term2AmtRecieved")
        @Expose
        private String recievedFeesTerm2;

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getTotalFeesTerm1() {
            return totalFeesTerm1;
        }

        public void setTotalFeesTerm1(String totalFeesTerm1) {
            this.totalFeesTerm1 = totalFeesTerm1;
        }

        public String getRecievedFeesTerm1() {
            return recievedFeesTerm1;
        }

        public void setRecievedFeesTerm1(String recievedFeesTerm1) {
            this.recievedFeesTerm1 = recievedFeesTerm1;
        }

        public String getTotalFeesTerm2() {
            return totalFeesTerm2;
        }

        public void setTotalFeesTerm2(String totalFeesTerm2) {
            this.totalFeesTerm2 = totalFeesTerm2;
        }

        public String getRecievedFeesTerm2() {
            return recievedFeesTerm2;
        }

        public void setRecievedFeesTerm2(String recievedFeesTerm2) {
            this.recievedFeesTerm2 = recievedFeesTerm2;
        }
    }


    public class FinalArray {

        @SerializedName("StudentID")
        @Expose
        private String StudentID;
        @SerializedName("StudentName")
        @Expose
        private String StudentName;
        @SerializedName("Standard")
        @Expose
        private String Standard;
        @SerializedName("ClassName")
        @Expose
        private String ClassName;
        @SerializedName("GRNO")
        @Expose
        private String GRNO;
        @SerializedName("MobileNo")
        @Expose
        private String MobileNo;

        @SerializedName("EmailID")
        @Expose
        private String EmailID;


        @SerializedName("TermData")
        @Expose
        private List<HeadwiseStudent.Finalarray> termData = null;
        private boolean isSelected = false;

        public List<HeadwiseStudent.Finalarray> getTermData() {
            return termData;
        }

        public void setTermData(List<HeadwiseStudent.Finalarray> termData) {
            this.termData = termData;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getStudentID() {
            return StudentID;
        }

        public void setStudentID(String studentID) {
            StudentID = studentID;
        }

        public String getStudentName() {
            return StudentName;
        }

        public void setStudentName(String studentName) {
            StudentName = studentName;
        }

        public String getStandard() {
            return Standard;
        }

        public void setStandard(String standard) {
            Standard = standard;
        }

        public String getClassName() {
            return ClassName;
        }

        public void setClassName(String className) {
            ClassName = className;
        }

        public String getGRNO() {
            return GRNO;
        }

        public void setGRNO(String GRNO) {
            this.GRNO = GRNO;
        }

        public String getMobileNo() {
            return MobileNo;
        }

        public void setMobileNo(String mobileNo) {
            MobileNo = mobileNo;
        }

        public String getEmailID() {
            return EmailID;
        }

        public void setEmailID(String emailID) {
            EmailID = emailID;
        }
    }


}
