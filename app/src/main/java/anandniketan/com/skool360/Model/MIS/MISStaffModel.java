package anandniketan.com.skool360.Model.MIS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MISStaffModel {


    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArray> finalArray = null;

    private String deptName;
    private String total;
    private String absent;
    private String leave;




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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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


    public class FinalArray {

        @SerializedName("StaffData")
        @Expose
        private List<StaffDatum> staffData = null;

        public List<StaffDatum> getStaffData() {
            return staffData;
        }

        public void setStaffData(List<StaffDatum> staffData) {
            this.staffData = staffData;
        }

        @SerializedName("ANT")
        @Expose
        private List<ANT> aNT = null;

        public List<ANT> getANT() {
            return aNT;
        }

        public void setANT(List<ANT> aNT) {
            this.aNT = aNT;
        }

        @SerializedName("Index")
        @Expose
        private String index;
        @SerializedName("TeacherName")
        @Expose
        private String teacherName;
        @SerializedName("Standard")
        @Expose
        private String standard;
        @SerializedName("Class")
        @Expose
        private String _class;
        @SerializedName("Subject")
        @Expose
        private String subject;

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

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

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

    }


    public class ANT {

        @SerializedName("ClassTeacher")
        @Expose
        private String classTeacher;
        @SerializedName("TeacherCode")
        @Expose
        private String teacherCode;
        @SerializedName("Grade")
        @Expose
        private String grade;
        @SerializedName("Section")
        @Expose
        private String section;

        public String getClassTeacher() {
            return classTeacher;
        }

        public void setClassTeacher(String classTeacher) {
            this.classTeacher = classTeacher;
        }

        public String getTeacherCode() {
            return teacherCode;
        }

        public void setTeacherCode(String teacherCode) {
            this.teacherCode = teacherCode;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

    }


    public class StaffDatum {

        @SerializedName("StaffName")
        @Expose
        private String staffName;
        @SerializedName("StaffCode")
        @Expose
        private String staffCode;
        @SerializedName("Department")
        @Expose
        private String department;
        @SerializedName("AttendanceStatus")
        @Expose
        private String attendanceStatus;

        public String getStaffName() {
            return staffName;
        }

        public void setStaffName(String staffName) {
            this.staffName = staffName;
        }

        public String getStaffCode() {
            return staffCode;
        }

        public void setStaffCode(String staffCode) {
            this.staffCode = staffCode;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getAttendanceStatus() {
            return attendanceStatus;
        }

        public void setAttendanceStatus(String attendanceStatus) {
            this.attendanceStatus = attendanceStatus;
        }

    }
}
