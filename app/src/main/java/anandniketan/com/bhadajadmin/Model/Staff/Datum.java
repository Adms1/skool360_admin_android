package anandniketan.com.bhadajadmin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/24/2017.
 */

public class Datum {

    @SerializedName("Lecture")
    @Expose
    private Integer lecture;

    @SerializedName("TimetableID")
    @Expose
    private String timetableID;

    @SerializedName("Subject")
    @Expose
    private String subject;

    @SerializedName("StandardClass")
    @Expose
    private String standardClass;

    @SerializedName("ProxyStatus")
    @Expose
    private String proxyStatus;

    @SerializedName("EmpName")
    @Expose
    private String empName;

    @SerializedName("TeacherName")
    @Expose
    private String teacherName;

    @SerializedName("Days")
    @Expose
    private Integer days;

    public Integer getLecture() {
        return lecture;
    }

    public void setLecture(Integer lecture) {
        this.lecture = lecture;
    }

    public String getTimetableID() {
        return timetableID;
    }

    public void setTimetableID(String timetableID) {
        this.timetableID = timetableID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStandardClass() {
        return standardClass;
    }

    public void setStandardClass(String standardClass) {
        this.standardClass = standardClass;
    }

    public String getProxyStatus() {
        return proxyStatus;
    }

    public void setProxyStatus(String proxyStatus) {
        this.proxyStatus = proxyStatus;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
