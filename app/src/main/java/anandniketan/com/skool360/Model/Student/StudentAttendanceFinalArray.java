package anandniketan.com.skool360.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentAttendanceFinalArray {
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
    @SerializedName("Name")
    @Expose
    private String Name;
    //=================== FilterStudentData=================
    @SerializedName("Father Name")
    @Expose
    private String fatherName;
    @SerializedName("StudentName")
    @Expose
    private String studentName;
    @SerializedName("Grade_Section")
    @Expose
    private String gradeSection;
    @SerializedName("GRNO")
    @Expose
    private String gRNO;
    @SerializedName("StudentID")
    @Expose
    private Integer studentID;
    //==========student full detail=========
    @SerializedName("Tag")
    @Expose
    private String tag;

    @SerializedName(value = "", alternate = {"StudentImage", "Student Image"})
    @Expose
    private String studentImage;
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
    @SerializedName(value = "AcademicYear", alternate = {"Acedamic Year", "Acedemic Year"})
    @Expose
    private String acedamicYear;
    @SerializedName("Grade")
    @Expose
    private String grade;
    @SerializedName("Section")
    @Expose
    private String section;
    @SerializedName("Board")
    @Expose
    private String board;
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
    @SerializedName("Student UniqueId No.")
    @Expose
    private String unique_no;
    @SerializedName("Religion")
    @Expose
    private String religion;
    @SerializedName("User Name")
    @Expose
    private String userName;
    @SerializedName("Password")
    @Expose
    private String password;
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
    @SerializedName("Father Organization")
    @Expose
    private String fatherOrganization;
    @SerializedName("Father Designation")
    @Expose
    private String fatherDesignation;
    @SerializedName("Father Office Address")
    @Expose
    private String fatherofficeaddress;
    @SerializedName("Mother Name")
    @Expose
    private String motherName;
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
    @SerializedName("Mother Organization")
    @Expose
    private String motherOrganization;
    @SerializedName("Mother Designation")
    @Expose
    private String motherDesignation;
    @SerializedName("Mother Office Address")
    @Expose
    private String motherofficeaddress;
    @SerializedName("SMS Communication No.")
    @Expose
    private String sMSCommunicationNo;
    @SerializedName("Comm. Email ID")
    @Expose
    private String sMSCommunicationemail;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Zip Code")
    @Expose
    private String zipCode;
    @SerializedName("Old GRNO")
    @Expose
    private String oldGRNO;
    @SerializedName("Registration Date")
    @Expose
    private String registrationDate;
    @SerializedName("Student Type")
    @Expose
    private String studentType;
    //  =====InquiryCount=====
    @SerializedName("Total")
    @Expose
    private String total;
    //=======Enquiry Data======
    @SerializedName("Current Status")
    @Expose
    private String currentStatus;
    @SerializedName("Staus Detail")
    @Expose
    private List<StandardWiseAttendanceModel> stausDetail = null;
    //========Transport data ===========
    @SerializedName("Term")
    @Expose
    private String term;
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("Class")
    @Expose
    private String _class;
    @SerializedName(value = "RouteNm", alternate = {"RouteName", "Route"})
    @Expose
    private String routeName;
    @SerializedName("PickupPointName")
    @Expose
    private String pickupPointName;
    @SerializedName("KM")
    @Expose
    private String kM;
    @SerializedName("SMSNo")
    @Expose
    private String smsno;
    //======result permission=====
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("TermDetail")
    @Expose
    private String termDetail;
    //=======Attendance detail==============
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
    private List<StandardWiseAttendanceModel> studentDetail = null;
    //======Left student detail =======
    @SerializedName("First Name")
    @Expose
    private String firstName;
    @SerializedName("Last Name")
    @Expose
    private String lastName;
    @SerializedName("Admission_In_Grade")
    @Expose
    private String admissionInGrade;
    @SerializedName("Admission_In_Section")
    @Expose
    private String admissionInSection;
    @SerializedName("Father First Name")
    @Expose
    private String fatherFirstName;
    @SerializedName("Father Last Name")
    @Expose
    private String fatherLastName;
    @SerializedName("Mother First Name")
    @Expose
    private String motherFirstName;
    @SerializedName("Mother Last Name")
    @Expose
    private String motherLastName;
    @SerializedName("Transport KMs")
    @Expose
    private String transportKMs;
    @SerializedName("Nationality")
    @Expose
    private String nationality;
    @SerializedName("Student ID")
    @Expose
    private Integer student_ID;
    //============== suggestion permission employee ==========
    @SerializedName("EmployeeName")
    @Expose
    private String employeeName;
    @SerializedName("EmployeeID")
    @Expose
    private Integer employeeID;
    @SerializedName("AssignPermissionID")
    @Expose
    private Integer assignPermissionID;
    @SerializedName("Type")
    @Expose
    private String type;
    // ======= sms message ======
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Sendtime")
    @Expose
    private String sendtime;
    @SerializedName("Rectime")
    @Expose
    private String rectime;
    @SerializedName("Deliverstatus")
    @Expose
    private String deliverstatus;
    @SerializedName("Location")
    @Expose
    private String location;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTotalStudent() {
        return totalStudent;
    }

    public String getgRNO() {
        return gRNO;
    }

    public void setgRNO(String gRNO) {
        this.gRNO = gRNO;
    }

    public String getsMSCommunicationNo() {
        return sMSCommunicationNo;
    }

    public void setsMSCommunicationNo(String sMSCommunicationNo) {
        this.sMSCommunicationNo = sMSCommunicationNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getsMSCommunicationemail() {
        return sMSCommunicationemail;
    }

    public void setsMSCommunicationemail(String sMSCommunicationemail) {
        this.sMSCommunicationemail = sMSCommunicationemail;
    }

    public String getFatherofficeaddress() {
        return fatherofficeaddress;
    }

    public void setFatherofficeaddress(String fatherofficeaddress) {
        this.fatherofficeaddress = fatherofficeaddress;
    }

    public String getMotherofficeaddress() {
        return motherofficeaddress;
    }

    public void setMotherofficeaddress(String motherofficeaddress) {
        this.motherofficeaddress = motherofficeaddress;
    }

    public String getkM() {
        return kM;
    }

    public void setkM(String kM) {
        this.kM = kM;
    }

    public String getSmsno() {
        return smsno;
    }

    public void setSmsno(String smsno) {
        this.smsno = smsno;
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

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
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

    public String getGRNO() {
        return gRNO;
    }

    public void setGRNO(String gRNO) {
        this.gRNO = gRNO;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public String getOldGRNO() {
        return oldGRNO;
    }

    public void setOldGRNO(String oldGRNO) {
        this.oldGRNO = oldGRNO;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getStudentType() {
        return studentType;
    }

    public void setStudentType(String studentType) {
        this.studentType = studentType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getStudentImage() {
        return studentImage;
    }

    public void setStudentImage(String studentImage) {
        this.studentImage = studentImage;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getFatherOccupation() {
        return fatherOccupation;
    }

    public void setFatherOccupation(String fatherOccupation) {
        this.fatherOccupation = fatherOccupation;
    }

    public String getFatherOrganization() {
        return fatherOrganization;
    }

    public void setFatherOrganization(String fatherOrganization) {
        this.fatherOrganization = fatherOrganization;
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

    public String getMotherOrganization() {
        return motherOrganization;
    }

    public void setMotherOrganization(String motherOrganization) {
        this.motherOrganization = motherOrganization;
    }

    public String getMotherDesignation() {
        return motherDesignation;
    }

    public void setMotherDesignation(String motherDesignation) {
        this.motherDesignation = motherDesignation;
    }

    public String getSMSCommunicationNo() {
        return sMSCommunicationNo;
    }

    public void setSMSCommunicationNo(String sMSCommunicationNo) {
        this.sMSCommunicationNo = sMSCommunicationNo;
    }

    public String getUnique_no() {
        return unique_no;
    }

    public void setUnique_no(String unique_no) {
        this.unique_no = unique_no;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public List<StandardWiseAttendanceModel> getStausDetail() {
        return stausDetail;
    }

    public void setStausDetail(List<StandardWiseAttendanceModel> stausDetail) {
        this.stausDetail = stausDetail;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
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

    public String getKM() {
        return kM;
    }

    public void setKM(String kM) {
        this.kM = kM;
    }

    public String getTermDetail() {
        return termDetail;
    }

    public void setTermDetail(String termDetail) {
        this.termDetail = termDetail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<StandardWiseAttendanceModel> getStudentDetail() {
        return studentDetail;
    }

    public void setStudentDetail(List<StandardWiseAttendanceModel> studentDetail) {
        this.studentDetail = studentDetail;
    }

    public Integer getStudent_ID() {
        return student_ID;
    }

    public void setStudent_ID(Integer student_ID) {
        this.student_ID = student_ID;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
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

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getAdmissionInGrade() {
        return admissionInGrade;
    }

    public void setAdmissionInGrade(String admissionInGrade) {
        this.admissionInGrade = admissionInGrade;
    }

    public String getAdmissionInSection() {
        return admissionInSection;
    }

    public void setAdmissionInSection(String admissionInSection) {
        this.admissionInSection = admissionInSection;
    }

    public String getFatherFirstName() {
        return fatherFirstName;
    }

    public void setFatherFirstName(String fatherFirstName) {
        this.fatherFirstName = fatherFirstName;
    }

    public String getFatherLastName() {
        return fatherLastName;
    }

    public void setFatherLastName(String fatherLastName) {
        this.fatherLastName = fatherLastName;
    }

    public String getMotherFirstName() {
        return motherFirstName;
    }

    public void setMotherFirstName(String motherFirstName) {
        this.motherFirstName = motherFirstName;
    }

    public String getMotherLastName() {
        return motherLastName;
    }

    public void setMotherLastName(String motherLastName) {
        this.motherLastName = motherLastName;
    }

    public String getTransportKMs() {
        return transportKMs;
    }

    public void setTransportKMs(String transportKMs) {
        this.transportKMs = transportKMs;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    public Integer getAssignPermissionID() {
        return assignPermissionID;
    }

    public void setAssignPermissionID(Integer assignPermissionID) {
        this.assignPermissionID = assignPermissionID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getRectime() {
        return rectime;
    }

    public void setRectime(String rectime) {
        this.rectime = rectime;
    }

    public String getDeliverstatus() {
        return deliverstatus;
    }

    public void setDeliverstatus(String deliverstatus) {
        this.deliverstatus = deliverstatus;
    }

}
