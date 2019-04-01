package anandniketan.com.skool360.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SuggestionDataModel {

    @SerializedName("Success")
    @Expose
    private String success;

    @SerializedName("FinalArray")
    @Expose
    private ArrayList<FinalArray> finalArray;

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

    public class FinalArray {

        @SerializedName("Date")
        @Expose
        private String date;

        @SerializedName("StudentName")
        @Expose
        private String stu_name;

        @SerializedName("Standard")
        @Expose
        private String standard;

        @SerializedName("ClassName")
        @Expose
        private String classname;

        @SerializedName("Subject")
        @Expose
        private String subject;

        @SerializedName("Comment")
        @Expose
        private String comment;

        @SerializedName("EmployeeName")
        @Expose
        private String employeename;

        @SerializedName("Reply")
        @Expose
        private String reply;

        @SerializedName("Status")
        @Expose
        private String status;

        @SerializedName("PK_SuggestionID")
        @Expose
        private String pk_suggestionid;

        @SerializedName("ReplyDateTime")
        @Expose
        private String replydatetime;

        @SerializedName("SuggestionDateTime")
        @Expose
        private String suggestiondatetime;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStu_name() {
            return stu_name;
        }

        public void setStu_name(String stu_name) {
            this.stu_name = stu_name;
        }

        public String getStandard() {
            return standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public String getClassname() {
            return classname;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getEmployeename() {
            return employeename;
        }

        public void setEmployeename(String employeename) {
            this.employeename = employeename;
        }

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPk_suggestionid() {
            return pk_suggestionid;
        }

        public void setPk_suggestionid(String pk_suggestionid) {
            this.pk_suggestionid = pk_suggestionid;
        }

        public String getReplydatetime() {
            return replydatetime;
        }

        public void setReplydatetime(String replydatetime) {
            this.replydatetime = replydatetime;
        }

        public String getSuggestiondatetime() {
            return suggestiondatetime;
        }

        public void setSuggestiondatetime(String suggestiondatetime) {
            this.suggestiondatetime = suggestiondatetime;
        }
    }
}
