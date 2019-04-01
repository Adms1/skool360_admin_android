package anandniketan.com.skool360.Model.Other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 12/21/2017.
 */

public class FinalArraySMSDataModel {
    @SerializedName("PK_EmployeeID")
    @Expose
    private Integer pKEmployeeID;
    @SerializedName("EmpName")
    @Expose
    private String empName;
    @SerializedName("Emp_MobileNo")
    @Expose
    private Object empMobileNo;
    @SerializedName("CheckboxStatus")
    @Expose
    private String check;
    @SerializedName("Count")
    @Expose
    private Integer count;
    @SerializedName("Month")
    @Expose
    private Integer month;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("StudentCount")
    @Expose
    private Integer studentCount;
    @SerializedName("TeacherCount")
    @Expose
    private Integer teacherCount;
    @SerializedName("AdminCount")
    @Expose
    private Integer adminCount;
    @SerializedName("Employee Code")
    @Expose
    private String employeeCode;
    @SerializedName("Employee Name")
    @Expose
    private String employeeName;
    @SerializedName("Login Details")
    @Expose
    private String loginDetails;
    @SerializedName("StudentName")
    @Expose
    private String studentName;
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("FamilyName")
    @Expose
    private String familyName;
    @SerializedName(value = "smsno", alternate = {"Sms_No", "SMSNo"})
    @Expose
    private String smsNo;
    @SerializedName(value = "student_id", alternate = {"Fk_StudentID", "StudentID"})
    @Expose
    private Integer fkStudentID;
    @SerializedName("Fk_StandardID")
    @Expose
    private Integer fkStandardID;
    @SerializedName("Fk_ClassID")
    @Expose
    private Integer fkClassID;
    @SerializedName("MessageID")
    @Expose
    private String messageID;
    @SerializedName("FromID")
    @Expose
    private String fromID;
    @SerializedName("ToID")
    @Expose
    private String toID;
    @SerializedName("MeetingDate")
    @Expose
    private String meetingDate;
    @SerializedName("SubjectLine")
    @Expose
    private String subjectLine;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("ReadStatus")
    @Expose
    private String readStatus;
    @SerializedName("StartDT")
    @Expose
    private String startDT;
    @SerializedName("EndDT")
    @Expose
    private String endDT;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("PK_HolidayID")
    @Expose
    private Integer pKHolidayID;
    @SerializedName("PK_CategoryID")
    @Expose
    private Integer pKCategoryID;
    @SerializedName("StandardID")
    @Expose
    private String standardID;
    @SerializedName("classname")
    @Expose
    private String classname;
    @SerializedName("ClassID")
    @Expose
    private Integer classID;
    @SerializedName(value = "subject", alternate = {"Subject", "SubjectName"})
    @Expose
    private String subject;
    //    @SerializedName("SubjectID")
//    @Expose
//    private Integer subjectID;
    @SerializedName("SubjectID")
    @Expose
    private String subjectid;
    @SerializedName(value = "grno", alternate = {"GR", "GRNO"})
    @Expose
    private String gR;

    @SerializedName("ClassName")
    @Expose
    private String className;
    @SerializedName("AppStatus")
    @Expose
    private String appStatus;
    @SerializedName("StudentData")
    @Expose
    private List<StudentDatum> studentData = new ArrayList<StudentDatum>();
    @SerializedName("Term")
    @Expose
    private String term;
    @SerializedName("RouteName")
    @Expose
    private String routename;
    @SerializedName("PickupPointName")
    @Expose
    private String pickuppoint;
    @SerializedName("KM")
    @Expose
    private String km;
    @SerializedName("Index")
    @Expose
    private String index;
    @SerializedName("MarkID")
    @Expose
    private String markid;
    @SerializedName("Mark")
    @Expose
    private String mark;

    public Integer getPKEmployeeID() {
        return pKEmployeeID;
    }

    public void setPKEmployeeID(Integer pKEmployeeID) {
        this.pKEmployeeID = pKEmployeeID;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Object getEmpMobileNo() {
        return empMobileNo;
    }

    public void setEmpMobileNo(Object empMobileNo) {
        this.empMobileNo = empMobileNo;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    public Integer getTeacherCount() {
        return teacherCount;
    }

    public void setTeacherCount(Integer teacherCount) {
        this.teacherCount = teacherCount;
    }

    public Integer getAdminCount() {
        return adminCount;
    }

    public void setAdminCount(Integer adminCount) {
        this.adminCount = adminCount;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getLoginDetails() {
        return loginDetails;
    }

    public void setLoginDetails(String loginDetails) {
        this.loginDetails = loginDetails;
    }

    public Integer getpKEmployeeID() {
        return pKEmployeeID;
    }

    public void setpKEmployeeID(Integer pKEmployeeID) {
        this.pKEmployeeID = pKEmployeeID;
    }

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

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getSmsNo() {
        return smsNo;
    }

    public void setSmsNo(String smsNo) {
        this.smsNo = smsNo;
    }

    public Integer getFkStudentID() {
        return fkStudentID;
    }

    public void setFkStudentID(Integer fkStudentID) {
        this.fkStudentID = fkStudentID;
    }

    public Integer getFkStandardID() {
        return fkStandardID;
    }

    public void setFkStandardID(Integer fkStandardID) {
        this.fkStandardID = fkStandardID;
    }

    public Integer getFkClassID() {
        return fkClassID;
    }

    public void setFkClassID(Integer fkClassID) {
        this.fkClassID = fkClassID;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public String getToID() {
        return toID;
    }

    public void setToID(String toID) {
        this.toID = toID;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getSubjectLine() {
        return subjectLine;
    }

    public void setSubjectLine(String subjectLine) {
        this.subjectLine = subjectLine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getStartDT() {
        return startDT;
    }

    public void setStartDT(String startDT) {
        this.startDT = startDT;
    }

    public String getEndDT() {
        return endDT;
    }

    public void setEndDT(String endDT) {
        this.endDT = endDT;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getpKHolidayID() {
        return pKHolidayID;
    }

    public void setpKHolidayID(Integer pKHolidayID) {
        this.pKHolidayID = pKHolidayID;
    }

    public Integer getpKCategoryID() {
        return pKCategoryID;
    }

    public void setpKCategoryID(Integer pKCategoryID) {
        this.pKCategoryID = pKCategoryID;
    }

    public String getStandardID() {
        return standardID;
    }

    public void setStandardID(String standardID) {
        this.standardID = standardID;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Integer getClassID() {
        return classID;
    }

    public void setClassID(Integer classID) {
        this.classID = classID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

//    public Integer getSubjectID() {
//        return subjectID;
//    }
//
//    public void setSubjectID(Integer subjectID) {
//        this.subjectID = subjectID;
//    }

    public List<StudentDatum> getStudentData() {
        return studentData;
    }

    public void setStudentData(List<StudentDatum> studentData) {
        this.studentData = studentData;
    }




    public String getGR() {
        return gR;
    }

    public void setGR(String gR) {
        this.gR = gR;
    }



    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getRoutename() {
        return routename;
    }

    public void setRoutename(String routename) {
        this.routename = routename;
    }

    public String getPickuppoint() {
        return pickuppoint;
    }

    public void setPickuppoint(String pickuppoint) {
        this.pickuppoint = pickuppoint;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getgR() {
        return gR;
    }

    public void setgR(String gR) {
        this.gR = gR;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getMarkid() {
        return markid;
    }

    public void setMarkid(String markid) {
        this.markid = markid;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }
}
