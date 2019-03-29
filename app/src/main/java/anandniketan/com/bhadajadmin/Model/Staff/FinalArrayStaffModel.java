package anandniketan.com.bhadajadmin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/20/2017.
 */

public class FinalArrayStaffModel {
    @SerializedName("TotalStaff")
    @Expose
    private String totalStaff;
    @SerializedName("StaffPresent")
    @Expose
    private String staffPresent;
    @SerializedName("StaffLeave")
    @Expose
    private String staffLeave;
    @SerializedName("StaffAbsent")
    @Expose
    private String staffAbsent;
    @SerializedName("ConsistenceAbsent")
    @Expose
    private List<Datum> consistenceAbsent = new ArrayList<Datum>();
    @SerializedName("Teacher")
    @Expose
    private String teacher;
    @SerializedName("Pk_EmployeeID")
    @Expose
    private Integer pkEmployeeID;
    @SerializedName("Emp_Code")
    @Expose
    private String empCode;
    @SerializedName("Pk_ClsTeacherID")
    @Expose
    private Integer pkClsTeacherID;
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName(value = "stdid", alternate = {"StandardId", "StandardID"})
    @Expose
    private Integer standardId;
    @SerializedName("SubjectId")
    @Expose
    private Integer subjectId;
    @SerializedName("ClassID")
    @Expose
    private String classid;
    @SerializedName("SubjectID")
    @Expose
    private Integer subjectID;
    @SerializedName("EmployeeId")
    @Expose
    private String employeeId;
    @SerializedName("Employee")
    @Expose
    private String employee;
    @SerializedName("Pk_SubjectID")
    @Expose
    private Integer pkSubjectID;
    @SerializedName("Pk_AssignID")
    @Expose
    private Integer pkAssignID;
    @SerializedName("TeacherName")
    @Expose
    private String teacherName;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("Chapter_Name")
    @Expose
    private String chapterName;
    @SerializedName("Objective")
    @Expose
    private String objective;
    @SerializedName("Key_Point")
    @Expose
    private String keyPoint;
    @SerializedName("Assessment_Question")
    @Expose
    private String assessmentQuestion;
    @SerializedName("Employee_Name")
    @Expose
    private String employeeName;
    @SerializedName("ChapterNo")
    @Expose
    private Integer chapterNo;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("TestName")
    @Expose
    private String testName;
    @SerializedName("Grade")
    @Expose
    private String grade;
    @SerializedName("TestDate")
    @Expose
    private String testDate;
    @SerializedName("Day")
    @Expose
    private String day;

    @SerializedName("EmployeeName")
    @Expose
    private String empName;

    @SerializedName("Status")
    @Expose
    private String status;

    @SerializedName("EmployeeID")
    @Expose
    private Integer empId;

    @SerializedName("Data")
    @Expose
    private List<Datum> data = new ArrayList<Datum>();

    public String getTotalStaff() {
        return totalStaff;
    }

    public void setTotalStaff(String totalStaff) {
        this.totalStaff = totalStaff;
    }

    public String getStaffPresent() {
        return staffPresent;
    }

    public void setStaffPresent(String staffPresent) {
        this.staffPresent = staffPresent;
    }

    public String getStaffLeave() {
        return staffLeave;
    }

    public void setStaffLeave(String staffLeave) {
        this.staffLeave = staffLeave;
    }

    public String getStaffAbsent() {
        return staffAbsent;
    }

    public void setStaffAbsent(String staffAbsent) {
        this.staffAbsent = staffAbsent;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Integer getPkEmployeeID() {
        return pkEmployeeID;
    }

    public void setPkEmployeeID(Integer pkEmployeeID) {
        this.pkEmployeeID = pkEmployeeID;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public Integer getPkClsTeacherID() {
        return pkClsTeacherID;
    }

    public void setPkClsTeacherID(Integer pkClsTeacherID) {
        this.pkClsTeacherID = pkClsTeacherID;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStandardId() {
        return standardId;
    }

    public void setStandardId(Integer standardId) {
        this.standardId = standardId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(Integer subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public Integer getPkSubjectID() {
        return pkSubjectID;
    }

    public void setPkSubjectID(Integer pkSubjectID) {
        this.pkSubjectID = pkSubjectID;
    }

    public Integer getPkAssignID() {
        return pkAssignID;
    }

    public void setPkAssignID(Integer pkAssignID) {
        this.pkAssignID = pkAssignID;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getKeyPoint() {
        return keyPoint;
    }

    public void setKeyPoint(String keyPoint) {
        this.keyPoint = keyPoint;
    }

    public String getAssessmentQuestion() {
        return assessmentQuestion;
    }

    public void setAssessmentQuestion(String assessmentQuestion) {
        this.assessmentQuestion = assessmentQuestion;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(Integer chapterNo) {
        this.chapterNo = chapterNo;
    }

    public Integer getiD() {
        return iD;
    }

    public void setiD(Integer iD) {
        this.iD = iD;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<Datum> getConsistenceAbsent() {
        return consistenceAbsent;
    }

    public void setConsistenceAbsent(List<Datum> consistenceAbsent) {
        this.consistenceAbsent = consistenceAbsent;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
}
