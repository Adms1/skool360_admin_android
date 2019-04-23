package anandniketan.com.skool360.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 11/17/2017.
 */

public class StudentAttendanceModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("Year")
    @Expose
    private String year;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Term")
    @Expose
    private String term;
    @SerializedName("FinalArray")
    @Expose
    private List<StudentAttendanceFinalArray> finalArray = null;
    @SerializedName("StandardWiseAttendance")
    @Expose
    private List<StandardWiseAttendanceModel> standardWiseAttendance = null;
    @SerializedName("ConsistentAbsent")
    @Expose
    private List<ConsistentAbsentStudentModel> consistentAbsent = null;

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

    public List<StudentAttendanceFinalArray> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<StudentAttendanceFinalArray> finalArray) {
        this.finalArray = finalArray;
    }

    public List<StandardWiseAttendanceModel> getStandardWiseAttendance() {
        return standardWiseAttendance;
    }

    public void setStandardWiseAttendance(List<StandardWiseAttendanceModel> standardWiseAttendance) {
        this.standardWiseAttendance = standardWiseAttendance;
    }

    public List<ConsistentAbsentStudentModel> getConsistentAbsent() {
        return consistentAbsent;
    }

    public void setConsistentAbsent(List<ConsistentAbsentStudentModel> consistentAbsent) {
        this.consistentAbsent = consistentAbsent;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
    //=========== sms report=============

    @SerializedName("Total")
    @Expose
    private String total;
    @SerializedName("Delivered")
    @Expose
    private String delivered;
    @SerializedName("Other")
    @Expose
    private String other;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

}
