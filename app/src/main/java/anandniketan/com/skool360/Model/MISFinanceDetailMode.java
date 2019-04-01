package anandniketan.com.skool360.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MISFinanceDetailMode {


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

        @SerializedName("StudentName")
        @Expose
        private String studentName;
        @SerializedName("Standard")
        @Expose
        private String standard;
        @SerializedName("ClassName")
        @Expose
        private String className;
        @SerializedName("GRNO")
        @Expose
        private String gRNO;
        @SerializedName("MobileNo")
        @Expose
        private String mobileNo;
        @SerializedName("EmailID")
        @Expose
        private String emailID;

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getStandard() {
            return standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getGRNO() {
            return gRNO;
        }

        public void setGRNO(String gRNO) {
            this.gRNO = gRNO;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getEmailID() {
            return emailID;
        }

        public void setEmailID(String emailID) {
            this.emailID = emailID;
        }

    }
}
