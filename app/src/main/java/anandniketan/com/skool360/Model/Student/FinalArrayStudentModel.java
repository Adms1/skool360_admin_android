package anandniketan.com.skool360.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/17/2017.
 */

public class FinalArrayStudentModel {

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

        @SerializedName("TotalStudent")
        @Expose
        private String totalStudent;
        @SerializedName("StudentPresent")
        @Expose
        private String studentPresent;
        @SerializedName("StudentLeave")
        @Expose
        private String studentLeave;
        @SerializedName("StudentAbsent")
        @Expose
        private String studentAbsent;
        @SerializedName("StandardWiseAttendance")
        @Expose
        private List<StandardWiseAttendanceModel> standardWiseAttendance = null;
        @SerializedName("Test ID")
        @Expose
        private Integer testID;
        @SerializedName("Test Name")
        @Expose
        private String testName;
        @SerializedName("CheckedStatus")
        @Expose
        private String checkedStatus;
        @SerializedName("URL")
        @Expose
        private String uRL;
        @SerializedName("Cir_Date")
        @Expose
        private String cirDate;
        @SerializedName("Cir_Subject")
        @Expose
        private String cirSubject;
        @SerializedName("Cir_Description")
        @Expose
        private String cirDescription;
        @SerializedName("Cir_Order")
        @Expose
        private Integer cirOrder;
        @SerializedName("Cir_Status")
        @Expose
        private String cirStatus;
        @SerializedName("PK_CircularID")
        @Expose
        private Integer pKCircularID;
        @SerializedName("Total")
        @Expose
        private String total;
        @SerializedName("Current Status")
        @Expose
        private String currentStatus;
        @SerializedName("Staus Detail")
        @Expose
        private List<StandardWiseAttendanceModel> stausDetail = new ArrayList<StandardWiseAttendanceModel>();
        @SerializedName("StandardID")
        @Expose
        private Integer standardID;
        @SerializedName("ClassID")
        @Expose
        private Integer classID;
        @SerializedName("TotalPresent")
        @Expose
        private Integer totalPresent;
        @SerializedName("TotalAbsent")
        @Expose
        private Integer totalAbsent;
        @SerializedName("TotalLeave")
        @Expose
        private Integer totalLeave;
        @SerializedName("TotalOnDuty")
        @Expose
        private Integer totalOnDuty;
        @SerializedName("StudentDetail")
        @Expose
        private List<StandardWiseAttendanceModel> studentDetail = new ArrayList<StandardWiseAttendanceModel>();
        @SerializedName("classname")
        @Expose
        private String classname;
        @SerializedName("Subject")
        @Expose
        private String subject;
        @SerializedName("SubjectID")
        @Expose
        private Integer subjectID;
        @SerializedName("StudentData")
        @Expose
        private List<StandardWiseAttendanceModel> studentData = new ArrayList<StandardWiseAttendanceModel>();
        @SerializedName("Tag")
        @Expose
        private String tag;
        @SerializedName("Student ID")
        @Expose
        private String studentId;
        @SerializedName("StudentImage")
        @Expose
        private String studentImage;
        @SerializedName("First Name")
        @Expose
        private String firstName;
        @SerializedName("Last Name")
        @Expose
        private String lastName;
        @SerializedName("Standard")
        @Expose
        private String standard;
        @SerializedName("Class")
        @Expose
        private String classes;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Date Of Birth")
        @Expose
        private String dateOfBirth;
        @SerializedName("Birth Place")
        @Expose
        private String birthPlace;
        @SerializedName("Age")
        @Expose
        private String age;
        @SerializedName("Gender")
        @Expose
        private String gender;
        @SerializedName("Aadhaar Card No.")
        @Expose
        private String aadhaarCardNo;
        @SerializedName("Acedamic Year")
        @Expose
        private String acedamicYear;
        @SerializedName("Grade")
        @Expose
        private String grade;
        @SerializedName("Section")
        @Expose
        private String section;
        @SerializedName("Last School")
        @Expose
        private String lastSchool;
        @SerializedName("Date Of Admission")
        @Expose
        private String dateOfAdmission;
        @SerializedName("Admission Taken")
        @Expose
        private String admissionTaken;

        @SerializedName("Blood Group")
        @Expose
        private String bloodGroup;
        @SerializedName("House")
        @Expose
        private String house;
        @SerializedName("Old GRNO")
        @Expose
        private String oldGRNO;
        @SerializedName("Religion")
        @Expose
        private String religion;
        @SerializedName("User Name")
        @Expose
        private String userName;
        @SerializedName("Password")
        @Expose
        private String password;
        @SerializedName("GRNO")
        @Expose
        private String gRNO;
        @SerializedName("Registration Date")
        @Expose
        private String registrationDate;
        @SerializedName("Pickup Bus")
        @Expose
        private String pickupBus;
        @SerializedName("Pickup Point")
        @Expose
        private String pickupPoint;
        @SerializedName("Pickup Point Time")
        @Expose
        private String pickupPointTime;
        @SerializedName("Drop Bus")
        @Expose
        private String dropBus;
        @SerializedName("Drop Point")
        @Expose
        private String dropPoint;
        @SerializedName("Drop Point Time")
        @Expose
        private String dropPointTime;
        @SerializedName("Father Name")
        @Expose
        private String fatherName;
        @SerializedName("Father First Name")
        @Expose
        private String fatherfirstName;
        @SerializedName("Father Last Name")
        @Expose
        private String fatherlastName;
        @SerializedName("Father Phone No.")
        @Expose
        private String fatherPhoneNo;
        @SerializedName("Father Mobile No.")
        @Expose
        private String fatherMobileNo;
        @SerializedName("Father Email Address")
        @Expose
        private String fatherEmailAddress;
        @SerializedName("Father Qualification")
        @Expose
        private String fatherQualification;
        @SerializedName("Father Occupation")
        @Expose
        private String fatherOccupation;
        @SerializedName("Father Organisation")
        @Expose
        private String fatherOrganisation;
        @SerializedName("Father Designation")
        @Expose
        private String fatherDesignation;
        @SerializedName("Mother Name")
        @Expose
        private String motherName;
        @SerializedName("Mother First Name")
        @Expose
        private String motherfirstName;
        @SerializedName("Mother Last Name")
        @Expose
        private String motherlastName;
        @SerializedName("Mother Phone No.")
        @Expose
        private String motherPhoneNo;
        @SerializedName("Mother Mobile No.")
        @Expose
        private String motherMobileNo;
        @SerializedName("Mother Email Address")
        @Expose
        private String motherEmailAddress;
        @SerializedName("Mother Qualification")
        @Expose
        private String motherQualification;
        @SerializedName("Mother Occupation")
        @Expose
        private String motherOccupation;
        @SerializedName("Mother Organisation")
        @Expose
        private String motherOrganisation;
        @SerializedName("Mother Designation")
        @Expose
        private String motherDesignation;
        @SerializedName("SMS Communication No.")
        @Expose
        private String sMSCommunicationNo;
        @SerializedName("City")
        @Expose
        private String city;
        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("Student Type")
        @Expose
        private String studentType;
        @SerializedName("Zip Code")
        @Expose
        private String zipCode;
        @SerializedName("Status")
        @Expose
        private String Status;
        @SerializedName("Nationality")
        @Expose
        private String nationality;
        @SerializedName("TermID")
        @Expose
        private String termId;
        @SerializedName("Transport KMs")
        @Expose
        private String transportKms;
        @SerializedName("StudentName")
        @Expose
        private String studentName;
        @SerializedName("Grade_Section")
        @Expose
        private String gradeSection;
        @SerializedName("StudentID")
        @Expose
        private Integer studentID;
        @SerializedName("Term")
        @Expose
        private String term;
        @SerializedName("RouteName")
        @Expose
        private String routeName;
        @SerializedName("PickupPointName")
        @Expose
        private String pickupPointName;
        @SerializedName("KM")
        @Expose
        private String kM;
        @SerializedName("PK_StatusID")
        @Expose
        private Integer pKStatusID;
        @SerializedName("Category")
        @Expose
        private String category;
        @SerializedName("Pk_CategoryId")
        @Expose
        private Integer pkCategoryId;

        public String getTotalStudent() {
            return totalStudent;
        }

        public void setTotalStudent(String totalStudent) {
            this.totalStudent = totalStudent;
        }

        public String getStudentPresent() {
            return studentPresent;
        }

        public void setStudentPresent(String studentPresent) {
            this.studentPresent = studentPresent;
        }

        public String getStudentLeave() {
            return studentLeave;
        }

        public void setStudentLeave(String studentLeave) {
            this.studentLeave = studentLeave;
        }

        public String getStudentAbsent() {
            return studentAbsent;
        }

        public void setStudentAbsent(String studentAbsent) {
            this.studentAbsent = studentAbsent;
        }

        public List<StandardWiseAttendanceModel> getStandardWiseAttendance() {
            return standardWiseAttendance;
        }

        public void setStandardWiseAttendance(List<StandardWiseAttendanceModel> standardWiseAttendance) {
            this.standardWiseAttendance = standardWiseAttendance;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getgRNO() {
            return gRNO;
        }

        public void setgRNO(String gRNO) {
            this.gRNO = gRNO;
        }

        public Integer getTestID() {
            return testID;
        }

        public void setTestID(Integer testID) {
            this.testID = testID;
        }

        public String getTestName() {
            return testName;
        }

        public void setTestName(String testName) {
            this.testName = testName;
        }

        public String getCheckedStatus() {
            return checkedStatus;
        }

        public void setCheckedStatus(String checkedStatus) {
            this.checkedStatus = checkedStatus;
        }

        public String getuRL() {
            return uRL;
        }

        public void setuRL(String uRL) {
            this.uRL = uRL;
        }

        public String getCirDate() {
            return cirDate;
        }

        public void setCirDate(String cirDate) {
            this.cirDate = cirDate;
        }

        public String getCirSubject() {
            return cirSubject;
        }

        public void setCirSubject(String cirSubject) {
            this.cirSubject = cirSubject;
        }

        public String getCirDescription() {
            return cirDescription;
        }

        public void setCirDescription(String cirDescription) {
            this.cirDescription = cirDescription;
        }

        public Integer getCirOrder() {
            return cirOrder;
        }

        public void setCirOrder(Integer cirOrder) {
            this.cirOrder = cirOrder;
        }

        public String getCirStatus() {
            return cirStatus;
        }

        public void setCirStatus(String cirStatus) {
            this.cirStatus = cirStatus;
        }

        public Integer getpKCircularID() {
            return pKCircularID;
        }

        public void setpKCircularID(Integer pKCircularID) {
            this.pKCircularID = pKCircularID;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCurrentStatus() {
            return currentStatus;
        }

        public void setCurrentStatus(String currentStatus) {
            this.currentStatus = currentStatus;
        }

        public Integer getStandardID() {
            return standardID;
        }

        public void setStandardID(Integer standardID) {
            this.standardID = standardID;
        }

        public Integer getClassID() {
            return classID;
        }

        public void setClassID(Integer classID) {
            this.classID = classID;
        }

        public Integer getTotalPresent() {
            return totalPresent;
        }

        public void setTotalPresent(Integer totalPresent) {
            this.totalPresent = totalPresent;
        }

        public Integer getTotalAbsent() {
            return totalAbsent;
        }

        public void setTotalAbsent(Integer totalAbsent) {
            this.totalAbsent = totalAbsent;
        }

        public Integer getTotalLeave() {
            return totalLeave;
        }

        public void setTotalLeave(Integer totalLeave) {
            this.totalLeave = totalLeave;
        }

        public Integer getTotalOnDuty() {
            return totalOnDuty;
        }

        public void setTotalOnDuty(Integer totalOnDuty) {
            this.totalOnDuty = totalOnDuty;
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

        public Integer getSubjectID() {
            return subjectID;
        }

        public void setSubjectID(Integer subjectID) {
            this.subjectID = subjectID;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getStudentImage() {
            return studentImage;
        }

        public void setStudentImage(String studentImage) {
            this.studentImage = studentImage;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getClasses() {
            return classes;
        }

        public void setClasses(String classes) {
            this.classes = classes;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getBirthPlace() {
            return birthPlace;
        }

        public void setBirthPlace(String birthPlace) {
            this.birthPlace = birthPlace;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getAadhaarCardNo() {
            return aadhaarCardNo;
        }

        public void setAadhaarCardNo(String aadhaarCardNo) {
            this.aadhaarCardNo = aadhaarCardNo;
        }

        public String getAcedamicYear() {
            return acedamicYear;
        }

        public void setAcedamicYear(String acedamicYear) {
            this.acedamicYear = acedamicYear;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getLastSchool() {
            return lastSchool;
        }

        public void setLastSchool(String lastSchool) {
            this.lastSchool = lastSchool;
        }

        public String getDateOfAdmission() {
            return dateOfAdmission;
        }

        public void setDateOfAdmission(String dateOfAdmission) {
            this.dateOfAdmission = dateOfAdmission;
        }

        public String getAdmissionTaken() {
            return admissionTaken;
        }

        public void setAdmissionTaken(String admissionTaken) {
            this.admissionTaken = admissionTaken;
        }

        public String getBloodGroup() {
            return bloodGroup;
        }

        public void setBloodGroup(String bloodGroup) {
            this.bloodGroup = bloodGroup;
        }

        public String getHouse() {
            return house;
        }

        public void setHouse(String house) {
            this.house = house;
        }

        public String getOldGRNO() {
            return oldGRNO;
        }

        public void setOldGRNO(String oldGRNO) {
            this.oldGRNO = oldGRNO;
        }

        public String getReligion() {
            return religion;
        }

        public void setReligion(String religion) {
            this.religion = religion;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRegistrationDate() {
            return registrationDate;
        }

        public void setRegistrationDate(String registrationDate) {
            this.registrationDate = registrationDate;
        }

        public String getPickupBus() {
            return pickupBus;
        }

        public void setPickupBus(String pickupBus) {
            this.pickupBus = pickupBus;
        }

        public String getPickupPoint() {
            return pickupPoint;
        }

        public void setPickupPoint(String pickupPoint) {
            this.pickupPoint = pickupPoint;
        }

        public String getPickupPointTime() {
            return pickupPointTime;
        }

        public void setPickupPointTime(String pickupPointTime) {
            this.pickupPointTime = pickupPointTime;
        }

        public String getDropBus() {
            return dropBus;
        }

        public void setDropBus(String dropBus) {
            this.dropBus = dropBus;
        }

        public String getDropPoint() {
            return dropPoint;
        }

        public void setDropPoint(String dropPoint) {
            this.dropPoint = dropPoint;
        }

        public String getDropPointTime() {
            return dropPointTime;
        }

        public void setDropPointTime(String dropPointTime) {
            this.dropPointTime = dropPointTime;
        }

        public String getFatherName() {
            return fatherName;
        }

        public void setFatherName(String fatherName) {
            this.fatherName = fatherName;
        }

        public String getFatherfirstName() {
            return fatherfirstName;
        }

        public void setFatherfirstName(String fatherfirstName) {
            this.fatherfirstName = fatherfirstName;
        }

        public String getFatherlastName() {
            return fatherlastName;
        }

        public void setFatherlastName(String fatherlastName) {
            this.fatherlastName = fatherlastName;
        }

        public String getFatherPhoneNo() {
            return fatherPhoneNo;
        }

        public void setFatherPhoneNo(String fatherPhoneNo) {
            this.fatherPhoneNo = fatherPhoneNo;
        }

        public String getFatherMobileNo() {
            return fatherMobileNo;
        }

        public void setFatherMobileNo(String fatherMobileNo) {
            this.fatherMobileNo = fatherMobileNo;
        }

        public String getFatherEmailAddress() {
            return fatherEmailAddress;
        }

        public void setFatherEmailAddress(String fatherEmailAddress) {
            this.fatherEmailAddress = fatherEmailAddress;
        }

        public String getFatherQualification() {
            return fatherQualification;
        }

        public void setFatherQualification(String fatherQualification) {
            this.fatherQualification = fatherQualification;
        }

        public String getFatherOccupation() {
            return fatherOccupation;
        }

        public void setFatherOccupation(String fatherOccupation) {
            this.fatherOccupation = fatherOccupation;
        }

        public String getFatherOrganisation() {
            return fatherOrganisation;
        }

        public void setFatherOrganisation(String fatherOrganisation) {
            this.fatherOrganisation = fatherOrganisation;
        }

        public String getFatherDesignation() {
            return fatherDesignation;
        }

        public void setFatherDesignation(String fatherDesignation) {
            this.fatherDesignation = fatherDesignation;
        }

        public String getMotherName() {
            return motherName;
        }

        public void setMotherName(String motherName) {
            this.motherName = motherName;
        }

        public String getMotherfirstName() {
            return motherfirstName;
        }

        public void setMotherfirstName(String motherfirstName) {
            this.motherfirstName = motherfirstName;
        }

        public String getMotherlastName() {
            return motherlastName;
        }

        public void setMotherlastName(String motherlastName) {
            this.motherlastName = motherlastName;
        }

        public String getMotherPhoneNo() {
            return motherPhoneNo;
        }

        public void setMotherPhoneNo(String motherPhoneNo) {
            this.motherPhoneNo = motherPhoneNo;
        }

        public String getMotherMobileNo() {
            return motherMobileNo;
        }

        public void setMotherMobileNo(String motherMobileNo) {
            this.motherMobileNo = motherMobileNo;
        }

        public String getMotherEmailAddress() {
            return motherEmailAddress;
        }

        public void setMotherEmailAddress(String motherEmailAddress) {
            this.motherEmailAddress = motherEmailAddress;
        }

        public String getMotherQualification() {
            return motherQualification;
        }

        public void setMotherQualification(String motherQualification) {
            this.motherQualification = motherQualification;
        }

        public String getMotherOccupation() {
            return motherOccupation;
        }

        public void setMotherOccupation(String motherOccupation) {
            this.motherOccupation = motherOccupation;
        }

        public String getMotherOrganisation() {
            return motherOrganisation;
        }

        public void setMotherOrganisation(String motherOrganisation) {
            this.motherOrganisation = motherOrganisation;
        }

        public String getMotherDesignation() {
            return motherDesignation;
        }

        public void setMotherDesignation(String motherDesignation) {
            this.motherDesignation = motherDesignation;
        }

        public String getsMSCommunicationNo() {
            return sMSCommunicationNo;
        }

        public void setsMSCommunicationNo(String sMSCommunicationNo) {
            this.sMSCommunicationNo = sMSCommunicationNo;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStudentType() {
            return studentType;
        }

        public void setStudentType(String studentType) {
            this.studentType = studentType;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }

        public String getTermId() {
            return termId;
        }

        public void setTermId(String termId) {
            this.termId = termId;
        }

        public String getTransportKms() {
            return transportKms;
        }

        public void setTransportKms(String transportKms) {
            this.transportKms = transportKms;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getGradeSection() {
            return gradeSection;
        }

        public void setGradeSection(String gradeSection) {
            this.gradeSection = gradeSection;
        }

        public Integer getStudentID() {
            return studentID;
        }

        public void setStudentID(Integer studentID) {
            this.studentID = studentID;
        }

        public String getTerm() {
            return term;
        }

        public void setTerm(String term) {
            this.term = term;
        }

        public String getRouteName() {
            return routeName;
        }

        public void setRouteName(String routeName) {
            this.routeName = routeName;
        }

        public String getPickupPointName() {
            return pickupPointName;
        }

        public void setPickupPointName(String pickupPointName) {
            this.pickupPointName = pickupPointName;
        }

        public String getkM() {
            return kM;
        }

        public void setkM(String kM) {
            this.kM = kM;
        }

        public Integer getpKStatusID() {
            return pKStatusID;
        }

        public void setpKStatusID(Integer pKStatusID) {
            this.pKStatusID = pKStatusID;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Integer getPkCategoryId() {
            return pkCategoryId;
        }

        public void setPkCategoryId(Integer pkCategoryId) {
            this.pkCategoryId = pkCategoryId;
        }

        public List<StandardWiseAttendanceModel> getStausDetail() {
            return stausDetail;
        }

        public void setStausDetail(List<StandardWiseAttendanceModel> stausDetail) {
            this.stausDetail = stausDetail;
        }

        public List<StandardWiseAttendanceModel> getStudentDetail() {
            return studentDetail;
        }

        public void setStudentDetail(List<StandardWiseAttendanceModel> studentDetail) {
            this.studentDetail = studentDetail;
        }
    }
}
