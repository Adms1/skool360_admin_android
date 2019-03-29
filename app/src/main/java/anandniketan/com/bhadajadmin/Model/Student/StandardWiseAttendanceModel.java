package anandniketan.com.bhadajadmin.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/17/2017.
 */

public class StandardWiseAttendanceModel {
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("Class")
    @Expose
    private String _class;
    @SerializedName("TotalStudent")
    @Expose
    private String totalStudent;
    @SerializedName("Present")
    @Expose
    private String present;
    @SerializedName("Absent")
    @Expose
    private String absent;
    @SerializedName("Leave")
    @Expose
    private String leave;

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

    public String getTotalStudent() {
        return totalStudent;
    }

    public void setTotalStudent(String totalStudent) {
        this.totalStudent = totalStudent;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }


    //=======status detail ========
    @SerializedName("Status")
    @Expose
    private Object status;
    @SerializedName("Date")
    @Expose
    private String date;

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //======Attendance student detail============
    @SerializedName("StudentID")
    @Expose
    private Integer studentID;
    @SerializedName("StudentName")
    @Expose
    private String studentName;
    @SerializedName("StudentImage")
    @Expose
    private String studentImage;
    @SerializedName("AttendanceID")
    @Expose
    private Integer attendanceID;
    @SerializedName("AttendenceStatus")
    @Expose
    private String attendenceStatus;
    @SerializedName("Comment")
    @Expose
    private String comment;

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentImage() {
        return studentImage;
    }

    public void setStudentImage(String studentImage) {
        this.studentImage = studentImage;
    }

    public Integer getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(Integer attendanceID) {
        this.attendanceID = attendanceID;
    }

    public String getAttendenceStatus() {
        return attendenceStatus;
    }

    public void setAttendenceStatus(String attendenceStatus) {
        this.attendenceStatus = attendenceStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
