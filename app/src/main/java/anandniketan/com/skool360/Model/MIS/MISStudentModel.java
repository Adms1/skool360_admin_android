package anandniketan.com.skool360.Model.MIS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MISStudentModel {


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

        @SerializedName("StandardData")
        @Expose
        private List<StandardDatum> standardData = null;
        @SerializedName("StudentData")
        @Expose
        private List<StudentDatum> studentData = null;

        @SerializedName("ANT")
        @Expose
        private List<ANT> aNT = null;

        @SerializedName("c")
        @Expose
        private Integer c;
        @SerializedName("Grade")
        @Expose
        private String grade;
        @SerializedName("Section")
        @Expose
        private String section;
        @SerializedName("StudentName")
        @Expose
        private String studentName;
        @SerializedName("GRNO")
        @Expose
        private String gRNO;
        @SerializedName("PhoneNo")
        @Expose
        private String phoneNo;
        @SerializedName("ClassTeacher")
        @Expose
        private String classTeacher;
        @SerializedName("Percentage")
        @Expose
        private String percentage;

        public Integer getC() {
            return c;
        }

        public void setC(Integer c) {
            this.c = c;
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

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getGRNO() {
            return gRNO;
        }

        public void setGRNO(String gRNO) {
            this.gRNO = gRNO;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getClassTeacher() {
            return classTeacher;
        }

        public void setClassTeacher(String classTeacher) {
            this.classTeacher = classTeacher;
        }

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }

        public List<ANT> getANT() {
            return aNT;
        }

        public void setANT(List<ANT> aNT) {
            this.aNT = aNT;
        }


        public List<StandardDatum> getStandardData() {
            return standardData;
        }

        public void setStandardData(List<StandardDatum> standardData) {
            this.standardData = standardData;
        }


        public List<StudentDatum> getStudentData() {
            return studentData;
        }

        public void setStudentData(List<StudentDatum> studentData) {
            this.studentData = studentData;
        }

    }


    public class StandardDatum {

        @SerializedName("Standard")
        @Expose
        private String standard;
        @SerializedName("Total Student")
        @Expose
        private String totalStudent;

        public String getStandard() {
            return standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public String getTotalStudent() {
            return totalStudent;
        }

        public void setTotalStudent(String totalStudent) {
            this.totalStudent = totalStudent;
        }

    }


    public class StudentDatum {

        @SerializedName("Grade")
        @Expose
        private String grade;
        @SerializedName("Section")
        @Expose
        private String section;
        @SerializedName("StudentName")
        @Expose
        private String studentName;
        @SerializedName("GRNO")
        @Expose
        private String gRNO;
        @SerializedName("AttendanceStatus")
        @Expose
        private String attendanceStatus;

        @SerializedName("ClassTeacher")
        @Expose
        private String classTeacher;

        @SerializedName("Absent From")
        @Expose
        private String absentFrom;

        @SerializedName("Class Teacher")
        @Expose
        private String classTeacher1;

        @SerializedName("PhoneNo")
        @Expose
        private String phoneNo;

        @SerializedName("Percentage")
        @Expose
        private String percentage;


        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }
        public String getClassTeacher() {
            return classTeacher;
        }

        public void setClassTeacher(String classTeacher) {
            this.classTeacher = classTeacher;
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

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getGRNO() {
            return gRNO;
        }

        public void setGRNO(String gRNO) {
            this.gRNO = gRNO;
        }

        public String getAttendanceStatus() {
            return attendanceStatus;
        }

        public void setAttendanceStatus(String attendanceStatus) {
            this.attendanceStatus = attendanceStatus;
        }

        public String getAbsentFrom() {
            return absentFrom;
        }

        public void setAbsentFrom(String absentFrom) {
            this.absentFrom = absentFrom;
        }

        public String getClassTeacher1() {
            return classTeacher1;
        }

        public void setClassTeacher1(String classTeacher1) {
            this.classTeacher1 = classTeacher1;
        }

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }
    }

    public class ANT {

        @SerializedName("Grade")
        @Expose
        private String grade;
        @SerializedName("Section")
        @Expose
        private String section;
        @SerializedName("TotalStudent")
        @Expose
        private Integer totalStudent;
        @SerializedName("ClassTeacher")
        @Expose
        private String classTeacher;

        @SerializedName("PhoneNo")
        @Expose
        private String phoneNo;

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
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

        public Integer getTotalStudent() {
            return totalStudent;
        }

        public void setTotalStudent(Integer totalStudent) {
            this.totalStudent = totalStudent;
        }

        public String getClassTeacher() {
            return classTeacher;
        }

        public void setClassTeacher(String classTeacher) {
            this.classTeacher = classTeacher;
        }
    }

}
