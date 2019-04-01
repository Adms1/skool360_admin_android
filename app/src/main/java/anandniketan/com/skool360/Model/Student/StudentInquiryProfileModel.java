package anandniketan.com.skool360.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentInquiryProfileModel {


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

        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("First Name")
        @Expose
        private String firstName;
        @SerializedName("Last Name")
        @Expose
        private String lastName;
        @SerializedName("Middle Name")
        @Expose
        private String middleName;
        @SerializedName("Date Of Birth")
        @Expose
        private String dateOfBirth;
        @SerializedName("Gender")
        @Expose
        private String gender;
        @SerializedName("Birth Place")
        @Expose
        private String birthPlace;
        @SerializedName("State")
        @Expose
        private String state;
        @SerializedName("Mother Tongue")
        @Expose
        private String motherTongue;
        @SerializedName("Transport Required")
        @Expose
        private String transportRequired;
        @SerializedName("Board")
        @Expose
        private String board;
        @SerializedName("Name of Sibling 1")
        @Expose
        private String nameOfSibling1;
        @SerializedName("Sibling 1 Grade")
        @Expose
        private String sibling1Grade;
        @SerializedName("Name of Sibling 2")
        @Expose
        private String nameOfSibling2;
        @SerializedName("Sibling 2 Grade")
        @Expose
        private String sibling2Grade;
        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("Distance from School in Kms")
        @Expose
        private String distanceFromSchoolInKms;
        @SerializedName("Father Name")
        @Expose
        private String fatherName;
        @SerializedName("Father Qualification")
        @Expose
        private String fatherQualification;
        @SerializedName("Father Occupation")
        @Expose
        private String fatherOccupation;
        @SerializedName("Father Organization")
        @Expose
        private String fatherOrganization;
        @SerializedName("Father Mobile No.")
        @Expose
        private String fatherMobileNo;
        @SerializedName("Father Email Address")
        @Expose
        private String fatherEmailAddress;
        @SerializedName("Mother Name")
        @Expose
        private String motherName;
        @SerializedName("Mother Qualification")
        @Expose
        private String motherQualification;
        @SerializedName("Mother Occupation")
        @Expose
        private String motherOccupation;
        @SerializedName("Mother Organization")
        @Expose
        private String motherOrganization;
        @SerializedName("Mother Mobile No.")
        @Expose
        private String motherMobileNo;
        @SerializedName("Mother Email Address")
        @Expose
        private String motherEmailAddress;
        @SerializedName("Nationality")
        @Expose
        private String nationality;
        @SerializedName("Seeking Admission for Grade")
        @Expose
        private String seekingAdmissionForGrade;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("GRNO")
        @Expose
        private String gRNO;
        @SerializedName("Grade/Section")
        @Expose
        private String gradeSection;
        @SerializedName("Grade")
        @Expose
        private String grade;
        @SerializedName("Section")
        @Expose
        private String section;
        @SerializedName("Session")
        @Expose
        private String session;
        @SerializedName("Form No.")
        @Expose
        private String formNo;
        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("Blood Group")
        @Expose
        private String bloodGroup;
        @SerializedName("Hometown")
        @Expose
        private String hometown;
        @SerializedName("Currently Residing with")
        @Expose
        private String currentlyResidingWith;
        @SerializedName("Previous School Attended")
        @Expose
        private String previousSchoolAttended;
        @SerializedName("Festivals Celebrated at home")
        @Expose
        private String festivalsCelebratedAtHome;
        @SerializedName("Sibling 1 Brother/Sister")
        @Expose
        private String sibling1BrotherSister;
        @SerializedName("Sibling 1 School")
        @Expose
        private String sibling1School;
        @SerializedName("Sibling 2 Brother/Sister")
        @Expose
        private String sibling2BrotherSister;
        @SerializedName("Sibling 2 School")
        @Expose
        private String sibling2School;
        @SerializedName("Name of Local Guardian")
        @Expose
        private String nameOfLocalGuardian;
        @SerializedName("Contact No. of Local Guardian")
        @Expose
        private String contactNoOfLocalGuardian;
        @SerializedName("Emergency Contact Nos.")
        @Expose
        private String emergencyContactNos;
        @SerializedName("Would you like to avail the school transport facility")
        @Expose
        private String wouldYouLikeToAvailTheSchoolTransportFacility;
        @SerializedName("Father First Name")
        @Expose
        private String fatherFirstName;
        @SerializedName("Father Last Name")
        @Expose
        private String fatherLastName;
        @SerializedName("Father Middle Name")
        @Expose
        private String fatherMiddleName;
        @SerializedName("Father Date of Birth")
        @Expose
        private String fatherDateOfBirth;
        @SerializedName("Father Blood Group")
        @Expose
        private String fatherBloodGroup;
        @SerializedName("Father Name of Company")
        @Expose
        private String fatherNameOfCompany;
        @SerializedName("Father Academic Qualification")
        @Expose
        private String fatherAcademicQualification;
        @SerializedName("Father Nationality")
        @Expose
        private String fatherNationality;
        @SerializedName("Father Contact Home")
        @Expose
        private String fatherContactHome;
        @SerializedName("Father Designation")
        @Expose
        private String fatherDesignation;
        @SerializedName("Father Languages Known")
        @Expose
        private String fatherLanguagesKnown;
        @SerializedName("Father Office Address")
        @Expose
        private String fatherOfficeAddress;
        @SerializedName("Father Home Address")
        @Expose
        private String fatherHomeAddress;
        @SerializedName("Father Status")
        @Expose
        private String fatherStatus;
        @SerializedName("Father Annual Income")
        @Expose
        private String fatherAnnualIncome;
        @SerializedName("Father Contact Office")
        @Expose
        private String fatherContactOffice;
        @SerializedName("Mother First Name")
        @Expose
        private String motherFirstName;
        @SerializedName("Mother Last Name")
        @Expose
        private String motherLastName;
        @SerializedName("Mother Middle Name")
        @Expose
        private String motherMiddleName;
        @SerializedName("Mother Date of Birth")
        @Expose
        private String motherDateOfBirth;
        @SerializedName("Mother Blood Group")
        @Expose
        private String motherBloodGroup;
        @SerializedName("Mother Name of Company")
        @Expose
        private String motherNameOfCompany;
        @SerializedName("Mother Academic Qualification")
        @Expose
        private String motherAcademicQualification;
        @SerializedName("Mother Nationality")
        @Expose
        private String motherNationality;
        @SerializedName("Mother Contact Home")
        @Expose
        private String motherContactHome;
        @SerializedName("Mother Designation")
        @Expose
        private String motherDesignation;
        @SerializedName("Mother Languages Known")
        @Expose
        private String motherLanguagesKnown;
        @SerializedName("Mother Office Address")
        @Expose
        private String motherOfficeAddress;
        @SerializedName("Mother Home Address")
        @Expose
        private String motherHomeAddress;
        @SerializedName("Mother Status")
        @Expose
        private String motherStatus;
        @SerializedName("Mother Annual Income")
        @Expose
        private String motherAnnualIncome;
        @SerializedName("Mother Contact Office")
        @Expose
        private String motherContactOffice;
        @SerializedName("Registration No.")
        @Expose
        private String registrationNo;
        @SerializedName("Registration Date")
        @Expose
        private String registrationDate;
        @SerializedName("Document Birth Certificate")
        @Expose
        private String documentBirthCertificate;
        @SerializedName("Document Photographs")
        @Expose
        private String documentPhotographs;
        @SerializedName("Document Original TC")
        @Expose
        private String documentOriginalTC;
        @SerializedName("I.D. Proof of Father")
        @Expose
        private String iDProofOfFather;
        @SerializedName("I.D. Proof of Mother")
        @Expose
        private String iDProofOfMother;
        @SerializedName("Wait listed")
        @Expose
        private String waitListed;
        @SerializedName("Father Passport Copy")
        @Expose
        private String fatherPassportCopy;
        @SerializedName("Father License Copy")
        @Expose
        private String fatherLicenseCopy;
        @SerializedName("Father Aadhar-Card Copy")
        @Expose
        private String fatherAadharCardCopy;
        @SerializedName("Mother Passport Copy")
        @Expose
        private String motherPassportCopy;
        @SerializedName("Mother License Copy")
        @Expose
        private String motherLicenseCopy;
        @SerializedName("Mother Aadhar-Card Copy")
        @Expose
        private String motherAadharCardCopy;
        @SerializedName("Admitted to Grade")
        @Expose
        private String admittedToGrade;
        @SerializedName("Remarks")
        @Expose
        private String remarks;
        @SerializedName("Admission Manager")
        @Expose
        private String admissionManager;
        @SerializedName("1 Interaction Date")
        @Expose
        private String _1InteractionDate;
        @SerializedName("1 Interaction Time")
        @Expose
        private String _1InteractionTime;
        @SerializedName("1 Interaction With")
        @Expose
        private String _1InteractionWith;
        @SerializedName("1 Interaction Remarks")
        @Expose
        private String _1InteractionRemarks;
        @SerializedName("2 Interaction Date")
        @Expose
        private String _2InteractionDate;
        @SerializedName("2 Interaction Time")
        @Expose
        private String _2InteractionTime;
        @SerializedName("2 Interaction With")
        @Expose
        private String _2InteractionWith;
        @SerializedName("2 Interaction Remarks")
        @Expose
        private String _2InteractionRemarks;
        @SerializedName("Student Image")
        @Expose
        private String studentImage;
        @SerializedName("Father Image")
        @Expose
        private String fatherImage;
        @SerializedName("Mother Image")
        @Expose
        private String motherImage;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getBirthPlace() {
            return birthPlace;
        }

        public void setBirthPlace(String birthPlace) {
            this.birthPlace = birthPlace;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getMotherTongue() {
            return motherTongue;
        }

        public void setMotherTongue(String motherTongue) {
            this.motherTongue = motherTongue;
        }

        public String getTransportRequired() {
            return transportRequired;
        }

        public void setTransportRequired(String transportRequired) {
            this.transportRequired = transportRequired;
        }

        public String getBoard() {
            return board;
        }

        public void setBoard(String board) {
            this.board = board;
        }

        public String getNameOfSibling1() {
            return nameOfSibling1;
        }

        public void setNameOfSibling1(String nameOfSibling1) {
            this.nameOfSibling1 = nameOfSibling1;
        }

        public String getSibling1Grade() {
            return sibling1Grade;
        }

        public void setSibling1Grade(String sibling1Grade) {
            this.sibling1Grade = sibling1Grade;
        }

        public String getNameOfSibling2() {
            return nameOfSibling2;
        }

        public void setNameOfSibling2(String nameOfSibling2) {
            this.nameOfSibling2 = nameOfSibling2;
        }

        public String getSibling2Grade() {
            return sibling2Grade;
        }

        public void setSibling2Grade(String sibling2Grade) {
            this.sibling2Grade = sibling2Grade;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDistanceFromSchoolInKms() {
            return distanceFromSchoolInKms;
        }

        public void setDistanceFromSchoolInKms(String distanceFromSchoolInKms) {
            this.distanceFromSchoolInKms = distanceFromSchoolInKms;
        }

        public String getFatherName() {
            return fatherName;
        }

        public void setFatherName(String fatherName) {
            this.fatherName = fatherName;
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

        public String getFatherOrganization() {
            return fatherOrganization;
        }

        public void setFatherOrganization(String fatherOrganization) {
            this.fatherOrganization = fatherOrganization;
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

        public String getMotherName() {
            return motherName;
        }

        public void setMotherName(String motherName) {
            this.motherName = motherName;
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

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }

        public String getSeekingAdmissionForGrade() {
            return seekingAdmissionForGrade;
        }

        public void setSeekingAdmissionForGrade(String seekingAdmissionForGrade) {
            this.seekingAdmissionForGrade = seekingAdmissionForGrade;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGRNO() {
            return gRNO;
        }

        public void setGRNO(String gRNO) {
            this.gRNO = gRNO;
        }

        public String getGradeSection() {
            return gradeSection;
        }

        public void setGradeSection(String gradeSection) {
            this.gradeSection = gradeSection;
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

        public String getSession() {
            return session;
        }

        public void setSession(String session) {
            this.session = session;
        }

        public String getFormNo() {
            return formNo;
        }

        public void setFormNo(String formNo) {
            this.formNo = formNo;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getBloodGroup() {
            return bloodGroup;
        }

        public void setBloodGroup(String bloodGroup) {
            this.bloodGroup = bloodGroup;
        }

        public String getHometown() {
            return hometown;
        }

        public void setHometown(String hometown) {
            this.hometown = hometown;
        }

        public String getCurrentlyResidingWith() {
            return currentlyResidingWith;
        }

        public void setCurrentlyResidingWith(String currentlyResidingWith) {
            this.currentlyResidingWith = currentlyResidingWith;
        }

        public String getPreviousSchoolAttended() {
            return previousSchoolAttended;
        }

        public void setPreviousSchoolAttended(String previousSchoolAttended) {
            this.previousSchoolAttended = previousSchoolAttended;
        }

        public String getFestivalsCelebratedAtHome() {
            return festivalsCelebratedAtHome;
        }

        public void setFestivalsCelebratedAtHome(String festivalsCelebratedAtHome) {
            this.festivalsCelebratedAtHome = festivalsCelebratedAtHome;
        }

        public String getSibling1BrotherSister() {
            return sibling1BrotherSister;
        }

        public void setSibling1BrotherSister(String sibling1BrotherSister) {
            this.sibling1BrotherSister = sibling1BrotherSister;
        }

        public String getSibling1School() {
            return sibling1School;
        }

        public void setSibling1School(String sibling1School) {
            this.sibling1School = sibling1School;
        }

        public String getSibling2BrotherSister() {
            return sibling2BrotherSister;
        }

        public void setSibling2BrotherSister(String sibling2BrotherSister) {
            this.sibling2BrotherSister = sibling2BrotherSister;
        }

        public String getSibling2School() {
            return sibling2School;
        }

        public void setSibling2School(String sibling2School) {
            this.sibling2School = sibling2School;
        }

        public String getNameOfLocalGuardian() {
            return nameOfLocalGuardian;
        }

        public void setNameOfLocalGuardian(String nameOfLocalGuardian) {
            this.nameOfLocalGuardian = nameOfLocalGuardian;
        }

        public String getContactNoOfLocalGuardian() {
            return contactNoOfLocalGuardian;
        }

        public void setContactNoOfLocalGuardian(String contactNoOfLocalGuardian) {
            this.contactNoOfLocalGuardian = contactNoOfLocalGuardian;
        }

        public String getEmergencyContactNos() {
            return emergencyContactNos;
        }

        public void setEmergencyContactNos(String emergencyContactNos) {
            this.emergencyContactNos = emergencyContactNos;
        }

        public String getWouldYouLikeToAvailTheSchoolTransportFacility() {
            return wouldYouLikeToAvailTheSchoolTransportFacility;
        }

        public void setWouldYouLikeToAvailTheSchoolTransportFacility(String wouldYouLikeToAvailTheSchoolTransportFacility) {
            this.wouldYouLikeToAvailTheSchoolTransportFacility = wouldYouLikeToAvailTheSchoolTransportFacility;
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

        public String getFatherMiddleName() {
            return fatherMiddleName;
        }

        public void setFatherMiddleName(String fatherMiddleName) {
            this.fatherMiddleName = fatherMiddleName;
        }

        public String getFatherDateOfBirth() {
            return fatherDateOfBirth;
        }

        public void setFatherDateOfBirth(String fatherDateOfBirth) {
            this.fatherDateOfBirth = fatherDateOfBirth;
        }

        public String getFatherBloodGroup() {
            return fatherBloodGroup;
        }

        public void setFatherBloodGroup(String fatherBloodGroup) {
            this.fatherBloodGroup = fatherBloodGroup;
        }

        public String getFatherNameOfCompany() {
            return fatherNameOfCompany;
        }

        public void setFatherNameOfCompany(String fatherNameOfCompany) {
            this.fatherNameOfCompany = fatherNameOfCompany;
        }

        public String getFatherAcademicQualification() {
            return fatherAcademicQualification;
        }

        public void setFatherAcademicQualification(String fatherAcademicQualification) {
            this.fatherAcademicQualification = fatherAcademicQualification;
        }

        public String getFatherNationality() {
            return fatherNationality;
        }

        public void setFatherNationality(String fatherNationality) {
            this.fatherNationality = fatherNationality;
        }

        public String getFatherContactHome() {
            return fatherContactHome;
        }

        public void setFatherContactHome(String fatherContactHome) {
            this.fatherContactHome = fatherContactHome;
        }

        public String getFatherDesignation() {
            return fatherDesignation;
        }

        public void setFatherDesignation(String fatherDesignation) {
            this.fatherDesignation = fatherDesignation;
        }

        public String getFatherLanguagesKnown() {
            return fatherLanguagesKnown;
        }

        public void setFatherLanguagesKnown(String fatherLanguagesKnown) {
            this.fatherLanguagesKnown = fatherLanguagesKnown;
        }

        public String getFatherOfficeAddress() {
            return fatherOfficeAddress;
        }

        public void setFatherOfficeAddress(String fatherOfficeAddress) {
            this.fatherOfficeAddress = fatherOfficeAddress;
        }

        public String getFatherHomeAddress() {
            return fatherHomeAddress;
        }

        public void setFatherHomeAddress(String fatherHomeAddress) {
            this.fatherHomeAddress = fatherHomeAddress;
        }

        public String getFatherStatus() {
            return fatherStatus;
        }

        public void setFatherStatus(String fatherStatus) {
            this.fatherStatus = fatherStatus;
        }

        public String getFatherAnnualIncome() {
            return fatherAnnualIncome;
        }

        public void setFatherAnnualIncome(String fatherAnnualIncome) {
            this.fatherAnnualIncome = fatherAnnualIncome;
        }

        public String getFatherContactOffice() {
            return fatherContactOffice;
        }

        public void setFatherContactOffice(String fatherContactOffice) {
            this.fatherContactOffice = fatherContactOffice;
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

        public String getMotherMiddleName() {
            return motherMiddleName;
        }

        public void setMotherMiddleName(String motherMiddleName) {
            this.motherMiddleName = motherMiddleName;
        }

        public String getMotherDateOfBirth() {
            return motherDateOfBirth;
        }

        public void setMotherDateOfBirth(String motherDateOfBirth) {
            this.motherDateOfBirth = motherDateOfBirth;
        }

        public String getMotherBloodGroup() {
            return motherBloodGroup;
        }

        public void setMotherBloodGroup(String motherBloodGroup) {
            this.motherBloodGroup = motherBloodGroup;
        }

        public String getMotherNameOfCompany() {
            return motherNameOfCompany;
        }

        public void setMotherNameOfCompany(String motherNameOfCompany) {
            this.motherNameOfCompany = motherNameOfCompany;
        }

        public String getMotherAcademicQualification() {
            return motherAcademicQualification;
        }

        public void setMotherAcademicQualification(String motherAcademicQualification) {
            this.motherAcademicQualification = motherAcademicQualification;
        }

        public String getMotherNationality() {
            return motherNationality;
        }

        public void setMotherNationality(String motherNationality) {
            this.motherNationality = motherNationality;
        }

        public String getMotherContactHome() {
            return motherContactHome;
        }

        public void setMotherContactHome(String motherContactHome) {
            this.motherContactHome = motherContactHome;
        }

        public String getMotherDesignation() {
            return motherDesignation;
        }

        public void setMotherDesignation(String motherDesignation) {
            this.motherDesignation = motherDesignation;
        }

        public String getMotherLanguagesKnown() {
            return motherLanguagesKnown;
        }

        public void setMotherLanguagesKnown(String motherLanguagesKnown) {
            this.motherLanguagesKnown = motherLanguagesKnown;
        }

        public String getMotherOfficeAddress() {
            return motherOfficeAddress;
        }

        public void setMotherOfficeAddress(String motherOfficeAddress) {
            this.motherOfficeAddress = motherOfficeAddress;
        }

        public String getMotherHomeAddress() {
            return motherHomeAddress;
        }

        public void setMotherHomeAddress(String motherHomeAddress) {
            this.motherHomeAddress = motherHomeAddress;
        }

        public String getMotherStatus() {
            return motherStatus;
        }

        public void setMotherStatus(String motherStatus) {
            this.motherStatus = motherStatus;
        }

        public String getMotherAnnualIncome() {
            return motherAnnualIncome;
        }

        public void setMotherAnnualIncome(String motherAnnualIncome) {
            this.motherAnnualIncome = motherAnnualIncome;
        }

        public String getMotherContactOffice() {
            return motherContactOffice;
        }

        public void setMotherContactOffice(String motherContactOffice) {
            this.motherContactOffice = motherContactOffice;
        }

        public String getRegistrationNo() {
            return registrationNo;
        }

        public void setRegistrationNo(String registrationNo) {
            this.registrationNo = registrationNo;
        }

        public String getRegistrationDate() {
            return registrationDate;
        }

        public void setRegistrationDate(String registrationDate) {
            this.registrationDate = registrationDate;
        }

        public String getDocumentBirthCertificate() {
            return documentBirthCertificate;
        }

        public void setDocumentBirthCertificate(String documentBirthCertificate) {
            this.documentBirthCertificate = documentBirthCertificate;
        }

        public String getDocumentPhotographs() {
            return documentPhotographs;
        }

        public void setDocumentPhotographs(String documentPhotographs) {
            this.documentPhotographs = documentPhotographs;
        }

        public String getDocumentOriginalTC() {
            return documentOriginalTC;
        }

        public void setDocumentOriginalTC(String documentOriginalTC) {
            this.documentOriginalTC = documentOriginalTC;
        }

        public String getIDProofOfFather() {
            return iDProofOfFather;
        }

        public void setIDProofOfFather(String iDProofOfFather) {
            this.iDProofOfFather = iDProofOfFather;
        }

        public String getIDProofOfMother() {
            return iDProofOfMother;
        }

        public void setIDProofOfMother(String iDProofOfMother) {
            this.iDProofOfMother = iDProofOfMother;
        }

        public String getWaitListed() {
            return waitListed;
        }

        public void setWaitListed(String waitListed) {
            this.waitListed = waitListed;
        }

        public String getFatherPassportCopy() {
            return fatherPassportCopy;
        }

        public void setFatherPassportCopy(String fatherPassportCopy) {
            this.fatherPassportCopy = fatherPassportCopy;
        }

        public String getFatherLicenseCopy() {
            return fatherLicenseCopy;
        }

        public void setFatherLicenseCopy(String fatherLicenseCopy) {
            this.fatherLicenseCopy = fatherLicenseCopy;
        }

        public String getFatherAadharCardCopy() {
            return fatherAadharCardCopy;
        }

        public void setFatherAadharCardCopy(String fatherAadharCardCopy) {
            this.fatherAadharCardCopy = fatherAadharCardCopy;
        }

        public String getMotherPassportCopy() {
            return motherPassportCopy;
        }

        public void setMotherPassportCopy(String motherPassportCopy) {
            this.motherPassportCopy = motherPassportCopy;
        }

        public String getMotherLicenseCopy() {
            return motherLicenseCopy;
        }

        public void setMotherLicenseCopy(String motherLicenseCopy) {
            this.motherLicenseCopy = motherLicenseCopy;
        }

        public String getMotherAadharCardCopy() {
            return motherAadharCardCopy;
        }

        public void setMotherAadharCardCopy(String motherAadharCardCopy) {
            this.motherAadharCardCopy = motherAadharCardCopy;
        }

        public String getAdmittedToGrade() {
            return admittedToGrade;
        }

        public void setAdmittedToGrade(String admittedToGrade) {
            this.admittedToGrade = admittedToGrade;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getAdmissionManager() {
            return admissionManager;
        }

        public void setAdmissionManager(String admissionManager) {
            this.admissionManager = admissionManager;
        }

        public String get1InteractionDate() {
            return _1InteractionDate;
        }

        public void set1InteractionDate(String _1InteractionDate) {
            this._1InteractionDate = _1InteractionDate;
        }

        public String get1InteractionTime() {
            return _1InteractionTime;
        }

        public void set1InteractionTime(String _1InteractionTime) {
            this._1InteractionTime = _1InteractionTime;
        }

        public String get1InteractionWith() {
            return _1InteractionWith;
        }

        public void set1InteractionWith(String _1InteractionWith) {
            this._1InteractionWith = _1InteractionWith;
        }

        public String get1InteractionRemarks() {
            return _1InteractionRemarks;
        }

        public void set1InteractionRemarks(String _1InteractionRemarks) {
            this._1InteractionRemarks = _1InteractionRemarks;
        }

        public String get2InteractionDate() {
            return _2InteractionDate;
        }

        public void set2InteractionDate(String _2InteractionDate) {
            this._2InteractionDate = _2InteractionDate;
        }

        public String get2InteractionTime() {
            return _2InteractionTime;
        }

        public void set2InteractionTime(String _2InteractionTime) {
            this._2InteractionTime = _2InteractionTime;
        }

        public String get2InteractionWith() {
            return _2InteractionWith;
        }

        public void set2InteractionWith(String _2InteractionWith) {
            this._2InteractionWith = _2InteractionWith;
        }

        public String get2InteractionRemarks() {
            return _2InteractionRemarks;
        }

        public void set2InteractionRemarks(String _2InteractionRemarks) {
            this._2InteractionRemarks = _2InteractionRemarks;
        }

        public String getStudentImage() {
            return studentImage;
        }

        public void setStudentImage(String studentImage) {
            this.studentImage = studentImage;
        }

        public String getFatherImage() {
            return fatherImage;
        }

        public void setFatherImage(String fatherImage) {
            this.fatherImage = fatherImage;
        }

        public String getMotherImage() {
            return motherImage;
        }

        public void setMotherImage(String motherImage) {
            this.motherImage = motherImage;
        }

    }
}
