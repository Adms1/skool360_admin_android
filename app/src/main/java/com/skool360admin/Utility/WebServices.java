package com.skool360admin.Utility;

import com.google.gson.JsonObject;
import com.squareup.okhttp.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Model.Account.AccountFeesModel;
import anandniketan.com.bhadajadmin.Model.Account.AccountFeesStatusModel;
import anandniketan.com.bhadajadmin.Model.Account.DateWiseFeesCollectionModel;
import anandniketan.com.bhadajadmin.Model.Account.GetStandardModel;
import anandniketan.com.bhadajadmin.Model.Account.OnlineTransactionModel;
import anandniketan.com.bhadajadmin.Model.Account.TallyTranscationModel;
import anandniketan.com.bhadajadmin.Model.HR.DailyAccountModel;
import anandniketan.com.bhadajadmin.Model.HR.DailyHouseKeepingModel;
import anandniketan.com.bhadajadmin.Model.HR.DailyHrAdminModel;
import anandniketan.com.bhadajadmin.Model.HR.DailyInfoTechnology;
import anandniketan.com.bhadajadmin.Model.HR.DailyTransportationModel;
import anandniketan.com.bhadajadmin.Model.HR.DepartmentModel;
import anandniketan.com.bhadajadmin.Model.HR.DesignationModel;
import anandniketan.com.bhadajadmin.Model.HR.EmployeeInOutDetailsModel;
import anandniketan.com.bhadajadmin.Model.HR.EmployeeInOutSummaryModel;
import anandniketan.com.bhadajadmin.Model.HR.EmployeePresentDetailsModel;
import anandniketan.com.bhadajadmin.Model.HR.GetPageListModel;
import anandniketan.com.bhadajadmin.Model.HR.HrHeadModel;
import anandniketan.com.bhadajadmin.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.bhadajadmin.Model.HR.LeaveDayModel;
import anandniketan.com.bhadajadmin.Model.HR.LeaveRequestModel;
import anandniketan.com.bhadajadmin.Model.HR.LeaveStatusModel;
import anandniketan.com.bhadajadmin.Model.HR.SearchStaffModel;
import anandniketan.com.bhadajadmin.Model.LeaveModel;
import anandniketan.com.bhadajadmin.Model.MIS.HeadwiseStudent;
import anandniketan.com.bhadajadmin.Model.MIS.MISAccountModel;
import anandniketan.com.bhadajadmin.Model.MIS.MISClassWiseResultModel;
import anandniketan.com.bhadajadmin.Model.MIS.MISFinanaceModel;
import anandniketan.com.bhadajadmin.Model.MIS.MISHeadwiseFee;
import anandniketan.com.bhadajadmin.Model.MIS.MISNewAdmissionModel;
import anandniketan.com.bhadajadmin.Model.MIS.MISSchoolResultModel;
import anandniketan.com.bhadajadmin.Model.MIS.MISStaffModel;
import anandniketan.com.bhadajadmin.Model.MIS.MISStaffNewDetailModel;
import anandniketan.com.bhadajadmin.Model.MIS.MISStaffNewModel;
import anandniketan.com.bhadajadmin.Model.MIS.MISStudentModel;
import anandniketan.com.bhadajadmin.Model.MIS.MISStudentRange;
import anandniketan.com.bhadajadmin.Model.MIS.MISStudentResultDataModel;
import anandniketan.com.bhadajadmin.Model.MIS.MISTaskReportDetailModel;
import anandniketan.com.bhadajadmin.Model.MIS.MISTaskReportModel;
import anandniketan.com.bhadajadmin.Model.MIS.MIStudentWiseResultModel;
import anandniketan.com.bhadajadmin.Model.MIS.RangeChartModel;
import anandniketan.com.bhadajadmin.Model.MIS.TopperChartModel;
import anandniketan.com.bhadajadmin.Model.MIS.TransportMainModel;
import anandniketan.com.bhadajadmin.Model.MISModel;
import anandniketan.com.bhadajadmin.Model.MIStudentWiseCalendarModel;
import anandniketan.com.bhadajadmin.Model.Notification.NotificationModel;
import anandniketan.com.bhadajadmin.Model.Other.GetStaffSMSDataModel;
import anandniketan.com.bhadajadmin.Model.PermissionDataModel;
import anandniketan.com.bhadajadmin.Model.Staff.HomeWorkModel;
import anandniketan.com.bhadajadmin.Model.Staff.StaffAttendaceModel;
import anandniketan.com.bhadajadmin.Model.Staff.ViewMarksModel;
import anandniketan.com.bhadajadmin.Model.Student.AnnouncementModel;
import anandniketan.com.bhadajadmin.Model.Student.CircularModel;
import anandniketan.com.bhadajadmin.Model.Student.FinalArrayStudentModel;
import anandniketan.com.bhadajadmin.Model.Student.GalleryDataModel;
import anandniketan.com.bhadajadmin.Model.Student.MarkSyllabusModel;
import anandniketan.com.bhadajadmin.Model.Student.PlannerModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentInquiryModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentInquiryProfileModel;
import anandniketan.com.bhadajadmin.Model.Student.SuggestionDataModel;
import anandniketan.com.bhadajadmin.Model.Student.TestModel;
import anandniketan.com.bhadajadmin.Model.Transport.TermModel;
import anandniketan.com.bhadajadmin.Model.Transport.TransportChargesModel;
import anandniketan.com.bhadajadmin.Model.UploadObject;
import anandniketan.com.bhadajadmin.Model.login.LogInModel;
import okhttp3.MultipartBody;
import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Created by admsandroid on 11/20/2017.
 */

public interface WebServices {

    @retrofit2.http.GET()
    Call<JsonObject> getBaseUrl(@Url String url);

    @FormUrlEncoded
    @POST("/StaffLogin")
    void login(@FieldMap Map<String, String> map, Callback<LogInModel> callback);

    @FormUrlEncoded
    @POST("/GetTerm")
    void getTerm(@FieldMap Map<String, String> map, Callback<TermModel> callback);

    @FormUrlEncoded
    @POST("/GetPermissionData")
    void getPermissionData(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/Admin_StudentAttendence")
    void getStudentAttendace(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/Admin_StaffAttendence")
    void getStaffAttendace(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/Admin_StudentSearchByParentName")
    void getParentName(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/Admin_StudentSearchByStuName")
    void getStudentName(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/Admin_StudentShowFilteredData")
    void getAdmin_StudentFilterData(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/Admin_SearchStudent")
    void getAdmin_SearchStudent(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/GetStaffFullDetail")
    void getAdmin_SearchStaff(@FieldMap Map<String, String> map, Callback<SearchStaffModel> callback);

    @FormUrlEncoded
    @POST("/Admin_StudentFullDetail")
    void getStudentFullDetail(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/StudentTransportDetail")
    void getStudentTransportDetail(@FieldMap Map<String, String> map, Callback<GetStaffSMSDataModel> callback);

    @FormUrlEncoded
    @POST("/StudentTransportDetail")
    void getStudentTransportDetail1(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/SendSMS")
    void sendSMS(@FieldMap Map<String, String> map, Callback<GetStaffSMSDataModel> callback);

    @FormUrlEncoded
    @POST("/GetResultPermissionAdmin")
    void getResultPermission(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/InsertResultPermission")
    void InsertResultPermission(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/GetOnlinePaymentPermission")
    void getOnlinePaymentPermission(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/GetRoutePickUpPointDetail")
    void getRouteDetail(@FieldMap Map<String, String> map, Callback<TransportChargesModel> callback);


    @FormUrlEncoded
    @POST("/GetVehicleToRouteDetail")
    void getVehicleRouteDetail(@FieldMap Map<String, String> map, Callback<TransportChargesModel> callback);

    @FormUrlEncoded
    @POST("/Admin_AccountFeesStatus")
    void getAccountFeesStatusDetail(@FieldMap Map<String, String> map, Callback<AccountFeesStatusModel> callback);

    @FormUrlEncoded
    @POST("/Admin_AccountFeesStructure")
    void getAccountFeesStructureDetail(@FieldMap Map<String, String> map, Callback<AccountFeesStatusModel> callback);


    @FormUrlEncoded
    @POST("/DatewiseCollection")
    void getDatewiseFeesCollection(@FieldMap Map<String, String> map, Callback<DateWiseFeesCollectionModel> callback);

    @FormUrlEncoded
    @POST("/GetStandardSection")
    void getStandardDetail(@FieldMap Map<String, String> map, Callback<GetStandardModel> callback);

    @FormUrlEncoded
    @POST("/GetStandardSectionCombine")
    void getStandardSectionCombine(@FieldMap Map<String, String> map, Callback<GetStandardModel> callback);

    @FormUrlEncoded
    @POST("/GetDiscountDetail")
    void getDiscountDetail(@FieldMap Map<String, String> map, Callback<AccountFeesStatusModel> callback);

    @FormUrlEncoded
    @POST("/GetImprestDetail")
    void getImprestDetail(@FieldMap Map<String, String> map, Callback<AccountFeesStatusModel> callback);

    @FormUrlEncoded
    @POST("/DailyFeeColleCtion")
    void getDailyFeeColleCtionDetail(@FieldMap Map<String, String> map, Callback<AccountFeesStatusModel> callback);

//    @FormUrlEncoded
//    @POST("/TeacherGetTimetable")
//    void getTimeTable(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/AdminGetTimetable")
    void getTimeTable(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/GetPaymentLedger")
    void getPaymentLedger(@FieldMap Map<String, String> map, Callback<AccountFeesStatusModel> callback);

    @FormUrlEncoded
    @POST("/GetAllPaymentLedger")
    void getAllPaymentLedger(@FieldMap Map<String, String> map, Callback<AccountFeesStatusModel> callback);

    @FormUrlEncoded
    @POST("/GetExams")
    void getExams(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/GetTeachers")
    void getTeachers(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/GetTeacherbyTermID")
    void getTeachersbyTerm(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/GetSubject")
    void getSubject(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/GetSubjectAssgin")
    void getSubjectAssgin(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/InsertAssignSubject")
    void InsertAssignSubject(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/DeleteAssginSubject")
    void DeleteAssignSubject(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/GetClassTeacherDetail")
    void getClassTeacherDetail(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/InsertClassTeachers")
    void InsertClassTeachers(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/DeleteClassTeacher")
    void DeleteClassTeacher(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/InsertMenuPermission")
    void InsertMenuPermission(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/InsertSingleSMSData")
    void InsertSingleSMSData(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/GetStaffSMSData")
    void getStaffSMSData(@FieldMap Map<String, String> map, Callback<GetStaffSMSDataModel> callback);

    @FormUrlEncoded
    @POST("/InsertStaffSMSData")
    void InsertStaffSMSData(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/GetBulkSMSData")
    void getBulkSMSData(@FieldMap Map<String, String> map, Callback<GetStaffSMSDataModel> callback);

    @FormUrlEncoded
    @POST("/InsertBulkSMSData")
    void InsertBulkSMSData(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/GetAppSMSData")
    void getAppSMSData(@FieldMap Map<String, String> map, Callback<GetStaffSMSDataModel> callback);

    @FormUrlEncoded
    @POST("/SendAppSMS")
    void SendAppSMS(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/InsertAbsentTodaySMS")
    void InsertAbsentTodaySMS(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/GetStudentProfilePermission")
    void getStudentProfilePermission(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/InsertProfilePermission")
    void InsertProfilePermission(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/InsertOnlinePaymentPermission")
    void InsertOnlinePaymentPermission(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/GetEmployeeForSuggestionPermission")
    void getEmployeeForSuggestionPermission(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/GetSuggestionPermission")
    void getSuggestionPermission(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/InsertSuggestionPermission")
    void InsertSuggestionPermission(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/DeleteSuggestionPermission")
    void deleteSuggestionPermission(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/GetTestNameForMarksSyllabusPermission")
    void getTestNameForMarksSyllabusPermission(@FieldMap Map<String, String> map, Callback<TestModel> callback);

    @FormUrlEncoded
    @POST("/GetMarksSyllabusPermission")
    void getMarksSyllabusPermission(@FieldMap Map<String, String> map, Callback<MarkSyllabusModel> callback);

    @FormUrlEncoded
    @POST("/InsertMarksSyllabusPermission")
    void insertMarksSyllabusPermission(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/GetGRRegister")
    void getGRRegister(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/GetNewRegister")
    void getNewRegister(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/GetLeftDetainStudent")
    void getLeftDetainStudent(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/GetTestName")
    void getTestName(@FieldMap Map<String, String> map, Callback<FinalArrayStudentModel> callback);

    @FormUrlEncoded
    @POST("/GetStudentMarks")
    void getStudentMarks(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/ViewMarks")
    void getStudentViewMarks(@FieldMap Map<String, String> map, Callback<ViewMarksModel> callback);

    @FormUrlEncoded
    @POST("/GetPlanner")
    void getPlanner(@FieldMap Map<String, String> map, Callback<PlannerModel> callback);

    @FormUrlEncoded
    @POST("/InsertPlanner")
    void insertPlanner(@FieldMap Map<String, String> map, Callback<PlannerModel> callback);

    @FormUrlEncoded
    @POST("/DeletePlanner")
    void deletePlanner(@FieldMap Map<String, String> map, Callback<PlannerModel> callback);

    @FormUrlEncoded
    @POST("/GetAnnouncementData")
    void getAnnouncementData(@FieldMap Map<String, String> map, Callback<AnnouncementModel> callback);

    @FormUrlEncoded
    @POST("/InsertAnnouncement")
    void InsertAnnouncement(@FieldMap Map<String, String> map, Callback<AnnouncementModel> callback);

    @FormUrlEncoded
    @POST("/DeleteAnnouncement")
    void deleteAnnouncement(@FieldMap Map<String, String> map, Callback<AnnouncementModel> callback);

    @FormUrlEncoded
    @POST("/GetInquiryCount")
    void getInquiryCount(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/GetInquiryData")
    void getInquiryData(@FieldMap Map<String, String> map, Callback<StudentInquiryModel> callback);

    @FormUrlEncoded
    @POST("/InsertInquiry")
    void insertInquiryData(@FieldMap Map<String, String> map, Callback<StudentInquiryModel> callback);

    @FormUrlEncoded
    @POST("/GetInduiryDataByID")
    void getInquiryDataByID(@FieldMap Map<String, String> map, Callback<StudentInquiryProfileModel> callback);

    @FormUrlEncoded
    @POST("/RejectInquiry")
    void rejectInquiry(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/GetAllSMSDetail")
    void getAllSMSDetail(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/GetAttendence_Admin")
    void getAttendence_Admin(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/InsertAttendance")
    void insertAttendance(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/GetReceiptDetails")
    void getReceiptDetails(@FieldMap Map<String, String> map, Callback<AccountFeesStatusModel> callback);

    @FormUrlEncoded
    @POST("/PTMTeacherStudentGetDetail")
    void getPTMTeacherStudentGetDetail(@FieldMap Map<String, String> map, Callback<GetStaffSMSDataModel> callback);

    @FormUrlEncoded
    @POST("/PTMTeacherStudentInsertDetail")
    void PTMTeacherStudentInsertDetail(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/PTMDeleteMeeting")
    void PTMDeleteMeeting(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/TeacherGetClassSubjectWiseStudent")
    void getTeacherGetClassSubjectWiseStudent(@FieldMap Map<String, String> map, Callback<GetStaffSMSDataModel> callback);

    @FormUrlEncoded
    @POST("/GetMonthlyCount")
    void getMonthlyCount(@FieldMap Map<String, String> map, Callback<GetStaffSMSDataModel> callback);

    @FormUrlEncoded
    @POST("/GetDateCountPerMonth")
    void getDateCountPerMonth(@FieldMap Map<String, String> map, Callback<GetStaffSMSDataModel> callback);

    @FormUrlEncoded
    @POST("/GetLoginDetailsDatewise")
    void getLoginDetailsDatewise(@FieldMap Map<String, String> map, Callback<GetStaffSMSDataModel> callback);
    @FormUrlEncoded
    @POST("/GetHolidayCategory")
    void getHolidayCategory(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/GetHoliday")
    void getHoliday(@FieldMap Map<String, String> map, Callback<GetStaffSMSDataModel> callback);

    @FormUrlEncoded
    @POST("/InsertHoliday")
    void InsertHoliday(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/GetEmployeeBySubject")
    void getEmployeeBySubject(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/GetLessonPlanSubject")
    void getLessonPlanSubject(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/GetLessonPlan")
    void getLessonPlan(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/GetGalleryData")
    void getGalleryData(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/InsertGallery")
    void insertGalleryData(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/DeleteGallery")
    void deleteGalleryData(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/GetCircularDataAdmin")
    void getCircularDataAdmin(@FieldMap Map<String, String> map, Callback<CircularModel> callback);

    @FormUrlEncoded
    @POST("/InsertCircular")
    void insertCircular(@FieldMap Map<String, String> map, Callback<CircularModel> callback);

    @FormUrlEncoded
    @POST("/DeleteCircular")
    void deleteCircular(@FieldMap Map<String, String> map, Callback<CircularModel> callback);

    @FormUrlEncoded
    @POST("/GetStandardSection")
    void getStandardSection(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/GetTimetableByClass")
    void getTimetableByClass(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/AdminInsertTimetable")
    void adminInsertTimetable(@FieldMap Map<String, String> map, Callback<JsonObject> callback);

    @FormUrlEncoded
    @POST("/DeleteTimetable")
    void deleteTimetable(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/GetTeacherBySubject")
    void getTeacherBySubjecte(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/GetHead")
    void getHead(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/InsertStaffLeaveRequest")
    void insertStaffLeaveRequest(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/GetStaffLeaveRequest")
    void getStaffLeaveRequest(@FieldMap Map<String, String> map, Callback<LeaveModel> callback);

    @FormUrlEncoded
    @POST("/GetLeaveDays")
    void getleaveDays(@FieldMap Map<String, String> map, Callback<LeaveDayModel> callback);

    @retrofit2.http.FormUrlEncoded
    @retrofit2.http.POST("GetAllStaffLeaveRequest")
    Call<LeaveRequestModel> getAllStaffLeaveRequest(@retrofit2.http.FieldMap Map<String, String> map);

    @retrofit2.http.FormUrlEncoded
    @retrofit2.http.POST("GetAllStudentLeaveRequest")
    Call<LeaveRequestModel> getAllStudentLeaveRequest(@retrofit2.http.FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/DeleteStaffLeave")
    void deleteStaffLeave(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/GetTeacherlist")
    void getTeacherlist(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/GetStandardTeacher")
    void getStandardTeacher(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/GetSubjectByTeacher")
    void getSubjectByTeacher(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/TeacherStudentHomeworkStatus")
    void getHomeDetail(@FieldMap Map<String, String> map, Callback<HomeWorkModel> callback);

    @FormUrlEncoded
    @POST("/TeacherStudentHomeworkStatus")
    void teacherStudentHomeworkStatus(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @retrofit2.http.GET()
    Call<JsonObject> teacherStudentHomeworkStatusInsertUpdate(@Url String url);

    @FormUrlEncoded
    @POST("/TallyTransactionList")
    void getTallyTransactionList(@FieldMap Map<String, String> map, Callback<TallyTranscationModel> callback);

    @FormUrlEncoded
    @POST("/OnlineTransactionList")
    void getOnlineTransactionList(@FieldMap Map<String, String> map, Callback<OnlineTransactionModel> callback);

    @FormUrlEncoded
    @POST("/GetTrasportCharges")
    void getTransportChargesDetail(@FieldMap Map<String, String> map, Callback<TransportChargesModel> callback);

    @FormUrlEncoded
    @POST("/GetVehicleDetail")
    void getVehicleDetail(@FieldMap Map<String, String> map, Callback<TransportChargesModel> callback);

    @FormUrlEncoded
    @POST("/GetLeaveStatus")
    void getLeaveStatus(@FieldMap Map<String, String> map, Callback<LeaveStatusModel> callback);

    @FormUrlEncoded
    @POST("/UpdateLeaveStatus")
    void updateLeaveStatus(@FieldMap Map<String, String> map, Callback<LeaveRequestModel> callback);

    @FormUrlEncoded
    @POST("/LeaveBalance")
    void leaveBalance(@FieldMap Map<String, String> map, Callback<LeaveModel> callback);

    @FormUrlEncoded
    @POST("/GetPageList")
    void getPageList(@FieldMap Map<String, String> map, Callback<GetPageListModel> callback);

    @FormUrlEncoded
    @POST("/DailyTransportation")
    void getDailyTransportation(@FieldMap Map<String, String> map, Callback<DailyTransportationModel> callback);

    @FormUrlEncoded
    @POST("/DailyAccount")
    void getDailyAccount(@FieldMap Map<String, String> map, Callback<DailyAccountModel> callback);

    @FormUrlEncoded
    @POST("/DailyInformationTechnology")
    void getDailyInformationTechnology(@FieldMap Map<String, String> map, Callback<DailyInfoTechnology> callback);

    @FormUrlEncoded
    @POST("/DailyHRHead")
    void getDailyHRHead(@FieldMap Map<String, String> map, Callback<HrHeadModel> callback);

    @FormUrlEncoded
    @POST("/DailyAdmin")
    void getDailyAdmin(@FieldMap Map<String, String> map, Callback<DailyHrAdminModel> callback);

    @FormUrlEncoded
    @POST("/DailyHousekeeping")
    void getDailyHousekeeping(@FieldMap Map<String, String> map, Callback<DailyHouseKeepingModel> callback);

    @FormUrlEncoded
    @POST("/EmployeeInOutSummary")
    void getEmployeeInOutSummary(@FieldMap Map<String, String> map, Callback<EmployeeInOutSummaryModel> callback);

    @FormUrlEncoded
    @POST("/EmployeePresentDetails")
    void getEmployeePresentDetails(@FieldMap Map<String, String> map, Callback<EmployeePresentDetailsModel> callback);

    @FormUrlEncoded
    @POST("/EmployeeInOutDetails")
    void getEmployeeInOutDetails(@FieldMap Map<String, String> map, Callback<EmployeeInOutDetailsModel> callback);

    @FormUrlEncoded
    @POST("/GetDepartment")
    void getDepartment(@FieldMap Map<String, String> map, Callback<DepartmentModel> callback);

    @FormUrlEncoded
    @POST("/GetDesignation")
    void getDesignation(@FieldMap Map<String, String> map, Callback<DesignationModel> callback);

    @FormUrlEncoded
    @POST("/GetStaffFullDetail")
    void getStaffFullDetail(@FieldMap Map<String, String> map, Callback<SearchStaffModel> callback);

    @FormUrlEncoded
    @POST("/GetAbsentToday")
    void getAbsentToday(@FieldMap Map<String, String> map, Callback<GetStaffSMSDataModel> callback);

    @FormUrlEncoded
    @POST("/GetTestForMarks")
    void getTestForMarks(@FieldMap Map<String, String> map, Callback<GetStaffSMSDataModel> callback);

    @FormUrlEncoded
    @POST("/GetMarks")
    void getMarks(@FieldMap Map<String, String> map, Callback<GetStaffSMSDataModel> callback);

    @FormUrlEncoded
    @POST("/SendStudentMarksSMS")
    void sendStudentMarksSMS(@FieldMap Map<String, String> map, Callback<GetStaffSMSDataModel> callback);

    @Multipart
    @POST("uploadpdf.aspx")
    Call<ResponseBody> uploadPDF(@Part MultipartBody.Part file);

    @Multipart
    @POST("uploadpdf.aspx")
    Call<UploadObject> uploadMultiFile(@Part MultipartBody.Part file1, @Part MultipartBody.Part file2, @Part MultipartBody.Part file3);

    @Multipart
    @retrofit2.http.POST("uploadpdf.aspx")
    Call<okhttp3.ResponseBody> uploadSingleFile(@Part MultipartBody.Part file);

    @Multipart
    @retrofit2.http.POST("upload.aspx")
    Call<okhttp3.ResponseBody> uploadMultipleFile(@Part List<MultipartBody.Part> files);

    @FormUrlEncoded
    @POST("/TotalFeesCollectionByTerm")
    void totalFeesCollectionByTerm(@FieldMap Map<String, String> map, Callback<AccountFeesModel> callback);

    @FormUrlEncoded
    @POST("/GetMISData")
    void getMISdata(@FieldMap Map<String, String> map, Callback<MISModel> callback);

    @FormUrlEncoded
    @POST("/GetMISStudent")
    void getMISStudentdata(@FieldMap Map<String, String> map, Callback<MISStudentModel> callback);

    @FormUrlEncoded
    @POST("/GetMISStaff")
    void getMISStaffdata(@FieldMap Map<String, String> map, Callback<MISStaffModel> callback);

    @FormUrlEncoded
    @POST("/GetMISAccountByType")
    void getMISAccountByType(@FieldMap Map<String, String> map, Callback<MISAccountModel> callback);

    @FormUrlEncoded
    @POST("/GetMISAccountByStandard")
    void getMISAccountByStandard(@FieldMap Map<String, String> map, Callback<MISAccountModel> callback);

    @FormUrlEncoded
    @POST("/GetMISAccountByStudent")
    void getMISAccountByStudent(@FieldMap Map<String, String> map, Callback<MISAccountModel> callback);

    @FormUrlEncoded
    @POST("/GetMISNewAddmission")
    void getMISNewAddmission(@FieldMap Map<String, String> map, Callback<MISNewAdmissionModel> callback);

    @FormUrlEncoded
    @POST("/GetMISStaffAttendance")
    void getMISStaffAttendance(@FieldMap Map<String, String> map, Callback<MISStaffNewModel> callback);

    @FormUrlEncoded
    @POST("/GetMISStaffAttendanceDetail")
    void getMISStaffAttendanceDetail(@FieldMap Map<String, String> map, Callback<MISStaffNewDetailModel> callback);

    @FormUrlEncoded
    @POST("/GetMISTask")
    void getMISTask(@FieldMap Map<String, String> map, Callback<MISTaskReportModel> callback);

    @FormUrlEncoded
    @POST("/GetMISTaskData")
    void getMISTaskData(@FieldMap Map<String, String> map, Callback<MISTaskReportDetailModel> callback);

    @FormUrlEncoded
    @POST("/GetTopperList")
    void getTopperList(@FieldMap Map<String, String> map, Callback<MISSchoolResultModel> callback);

    @FormUrlEncoded
    @POST("/GetResultClassWise")
    void getResultClassWise(@FieldMap Map<String, String> map, Callback<MISClassWiseResultModel> callback);

    @FormUrlEncoded
    @POST("/GetResultStudentWise")
    void getResultStudentWise(@FieldMap Map<String, String> map, Callback<MIStudentWiseResultModel> callback);

    @FormUrlEncoded
    @POST("/GetResultData")
    void getResultData(@FieldMap Map<String, String> map, Callback<MISStudentResultDataModel> callback);

    @FormUrlEncoded
    @POST("/HeadWiseFeesCollection")
    void getHeadWiseFeesCollection(@FieldMap Map<String, String> map, Callback<MISFinanaceModel> callback);

    @FormUrlEncoded
    @POST("/AddDeviceDetailAdmin")
    void addDeviceDetailAdmin(@FieldMap Map<String, String> map, Callback<MISModel> callback);

//    static let baseUrl:String            = "http://192.168.1.11:8086/MobileApp_Service.asmx/"
//
//    static let loginApi:String                          = "\(baseUrl)InsertProfilePermission"
//    static let getTermApi:String                        = "\(baseUrl)GetTerm"
//    static let getPermissionDataApi:String              = "\(baseUrl)GetPermissionData"
//
//    // MARK: Student
//
//    static let admin_StudentAttendenceApi:String        = "\(baseUrl)Admin_StudentAttendence"
//    static let admin_StaffAttendenceApi:String          = "\(baseUrl)Admin_StaffAttendence"
//    static let totalFeesCollectionByTermApi:String      = "\(baseUrl)TotalFeesCollectionByTerm"
//
//    static let admin_StudentSearchByParentName:String   = "\(baseUrl)Admin_StudentSearchByParentName"
//    static let admin_StudentSearchByStuName:String      = "\(baseUrl)Admin_StudentSearchByStuName"
//    static let admin_StudentShowFilteredData:String     = "\(baseUrl)Admin_StudentShowFilteredData"
//    static let admin_SearchStudent:String               = "\(baseUrl)Admin_SearchStudent"
//    static let admin_StudentFullDetailApi:String        = "\(baseUrl)Admin_StudentFullDetail"
//
//    static let studentTransportDetailApi:String         = "\(baseUrl)StudentTransportDetail"
//    static let sendSMSApi:String                        = "\(baseUrl)SendSMS"
//
//    static let getResultPermissionAdminApi:String       = "\(baseUrl)GetResultPermissionAdmin"
//    static let insertResultPermissionApi:String         = "\(baseUrl)InsertResultPermission"
//
//    static let getOnlinePaymentPermissionApi:String     = "\(baseUrl)GetOnlinePaymentPermission"
//    static let insertOnlinePaymentPermissionApi:String  = "\(baseUrl)InsertOnlinePaymentPermission"
//
//    static let getStudentProfilePermissionApi:String    = "\(baseUrl)GetStudentProfilePermission"
//    static let insertProfilePermissionApi:String        = "\(baseUrl)InsertProfilePermission"
//
//    static let getGetEmployeeForSuggestionPermissionApi:String     = "\(baseUrl)GetEmployeeForSuggestionPermission"
//    static let getGetSuggestionPermissionApi:String     = "\(baseUrl)GetSuggestionPermission"
//    static let insertSuggestionPermissionApi:String     = "\(baseUrl)InsertSuggestionPermission"
//    static let deleteSuggestionPermissionApi:String     = "\(baseUrl)DeleteSuggestionPermission"
//
//    static let getGRRegisterApi:String                  = "\(baseUrl)GetGRRegister"
//    static let getNewRegisterApi:String                  = "\(baseUrl)GetNewRegister"
//    static let getLeftDetainStudentApi:String           = "\(baseUrl)GetLeftDetainStudent"
//
//    static let getEnquiryCountApi:String                = "\(baseUrl)GetInquiryCount"
//    static let getEnquiryDataApi:String                 = "\(baseUrl)GetInquiryData"
//    static let getEnduiryDataByIDApi:String             = "\(baseUrl)GetInduiryDataByID"
//    static let rejectEnquiryApi:String                  = "\(baseUrl)RejectInquiry"
//
//    static let getAttendenceAdminApi:String             = "\(baseUrl)GetAttendence_Admin"
//
//    static let getTestNameApi:String                    = "\(baseUrl)GetTestName"
//    //    static let getStudentMarksApi:String                = "\(baseUrl)GetStudentMarks"
//    static let viewMarksApi:String                      = "\(baseUrl)ViewMarks"
//
//    static let getPlannerApi:String                     = "\(baseUrl)GetPlanner"
//    static let insertPlannerApi:String                  = "\(baseUrl)InsertPlanner"
//    static let deletePlannerApi:String                  = "\(baseUrl)DeletePlanner"
//
//    static let getTestNameForMarksSyllabusPermissionApi:String = "\(baseUrl)GetTestNameForMarksSyllabusPermission"
//    static let getMarksSyllabusPermissionApi:String     = "\(baseUrl)GetMarksSyllabusPermission"
//    static let insertMarksSyllabusPermissionApi:String  = "\(baseUrl)InsertMarksSyllabusPermission"
//
//    static let getGalleryDataApi:String                 = "\(baseUrl)GetGalleryData"
//    static let insertGalleryApi:String                  = "\(baseUrl)InsertGallery"
//    static let deleteGalleryApi:String                  = "\(baseUrl)DeleteGallery"
//
//    static let getAnnouncementDataApi:String            = "\(baseUrl)GetAnnouncementData"
//    static let insertAnnouncementApi:String             = "\(baseUrl)InsertAnnouncement"
//    static let deleteAnnouncementApi:String             = "\(baseUrl)DeleteAnnouncement"
//
//    static let getCircularDataApi:String                = "\(baseUrl)GetCircularDataAdmin"
//    static let insertCircularApi:String                 = "\(baseUrl)InsertCircular"
//    static let deleteCircularApi:String                 = "\(baseUrl)DeleteAnnouncement"
//
//    // MARK: Staff
//
//    static let getTeachersApi:String                    = "\(baseUrl)GetTeachers"
//    static let getStandardSectionApi:String             = "\(baseUrl)GetStandardSection"
//    static let insertClassTeachersApi:String            = "\(baseUrl)InsertClassTeachers"
//    static let deleteClassTeacherApi:String             = "\(baseUrl)DeleteClassTeacher"
//    static let getClassTeacherDetailApi:String          = "\(baseUrl)GetClassTeacherDetail"
//
//    static let getSubjectApi:String                     = "\(baseUrl)GetSubject"
//    static let getSubjectAssginApi:String               = "\(baseUrl)GetSubjectAssgin"
//    static let insertAssignSubjectApi:String            = "\(baseUrl)InsertAssignSubject"
//
//    static let getLessonPlanSubjectApi:String           = "\(baseUrl)GetLessonPlanSubject"
//    static let getEmployeeBySubjectApi:String           = "\(baseUrl)GetEmployeeBySubject"
//    static let getLessonPlanApi:String                  = "\(baseUrl)GetLessonPlan"
//    static let wordApi:String                           = "\(downloadUrl)LessonPlanWord_Mobile.aspx?ID="
//
//    static let getExamsApi:String                       = "\(baseUrl)GetExams"
//
//    static let getTimetableByClassApi:String            = "\(baseUrl)GetTimetableByClass"
//    static let adminInsertTimetableApi:String           = "\(baseUrl)AdminInsertTimetable"
//    static let deleteTimetableApi:String                = "\(baseUrl)DeleteTimetable"
//    static let getTeacherBySubjectApi:String            = "\(baseUrl)GetTeacherBySubject"
//
//    static let getHeadApi:String                        = "\(baseUrl)GetHead"
//    static let insertStaffLeaveRequestApi:String        = "\(baseUrl)InsertStaffLeaveRequest"
//    static let getStaffLeaveRequestApi:String           = "\(baseUrl)GetStaffLeaveRequest"
//    static let deleteStaffLeaveApi:String               = "\(baseUrl)DeleteStaffLeave"
//
//    static let getTeacherlistApi:String                 = "\(baseUrl)GetTeacherlist"
//    static let getStandardTeacherApi:String             = "\(baseUrl)GetStandardTeacher"
//    static let getSubjectByTeacherApi:String            = "\(baseUrl)GetSubjectByTeacher"
//    static let teacherStudentHomeworkStatusApi:String   = "\(baseUrl)TeacherStudentHomeworkStatus"
//    static let teacherStudentHomeworkStatusInsertUpdateApi:String  = "\(baseUrl)TeacherStudentHomeworkStatusInsertUpdate"
//
//
//    // MARK: Account
//
//    static let admin_AccountFeesStructureApi:String     = "\(baseUrl)Admin_AccountFeesStructure"
//    static let dailyFeeCollectionApi:String             = "\(baseUrl)DatewiseCollection"
//    static let getImprestDetailApi:String               = "\(baseUrl)GetImprestDetail"
//    static let getDiscountDetailApi:String              = "\(baseUrl)GetDiscountDetail"
//
//    static let getPaymentLedger:String                  = "\(baseUrl)GetPaymentLedger"
//    static let getAllPaymentLedger:String               = "\(baseUrl)GetAllPaymentLedger"
//
//    static let getReceiptDetailsApi:String              = "\(baseUrl)GetReceiptDetails"
//
//    static let tallyTransactionListApi:String           = "\(baseUrl)TallyTransactionList"
//    static let getOnlineTransactionIDApi:String         = "\(baseUrl)GetOnlineTransactionID"
//    static let onlineTransactionListApi:String          = "\(baseUrl)OnlineTransactionList"
//
//    // MARK: Transport
//
//    static let getTrasportChargesApi:String             = "\(baseUrl)GetTrasportCharges"
//    static let getRoutePickUpPointDetail:String         = "\(baseUrl)GetRoutePickUpPointDetail"
//    static let getVehicleDetail:String                  = "\(baseUrl)GetVehicleDetail"
//    static let getVehicleToRouteDetail:String           = "\(baseUrl)GetVehicleToRouteDetail"
//
//    // MARK: HR
//
//    static let getLeaveStatusApi:String                 = "\(baseUrl)GetLeaveStatus"
//    static let getAllStaffLeaveRequestApi:String        = "\(baseUrl)GetAllStaffLeaveRequest"
//    static let updateLeaveStatusApi:String              = "\(baseUrl)UpdateLeaveStatus"
//
//    static let leaveBalanceApi:String                   = "\(baseUrl)LeaveBalance"
//
//    static let getPageListApi:String                    = "\(baseUrl)GetPageList"
//    static let insertMenuPermissionApi:String           = "\(baseUrl)InsertMenuPermission"
//
//    static let dailyTransportationApi:String            = "\(baseUrl)DailyTransportation"
//    static let dailyAccountApi:String                   = "\(baseUrl)DailyAccount"
//    static let dailyITApi:String                        = "\(baseUrl)DailyInformationTechnology"
//    static let dailyHRHeadApi:String                    = "\(baseUrl)DailyHRHead"
//    static let dailyAdminApi:String                     = "\(baseUrl)DailyAdmin"
//    static let dailyHousekeepingApi:String              = "\(baseUrl)DailyHousekeeping"
//
//    static let employeeInOutSummaryApi:String           = "\(baseUrl)EmployeeInOutSummary"
//    static let employeePresentDetailsApi:String         = "\(baseUrl)EmployeePresentDetails"
//    static let employeeInOutDetailsApi:String           = "\(baseUrl)EmployeeInOutDetails"
//
//    static let getDepartmentApi:String                  = "\(baseUrl)GetDepartment"
//    static let getDesignationApi:String                 = "\(baseUrl)GetDesignation"
//    static let getStaffFullDetailApi:String             = "\(baseUrl)GetStaffFullDetail"
//
//    // MARK: SMS
//
//    static let getAbsentTodayApi:String                 = "\(baseUrl)GetAbsentToday"
//    static let insertAbsentTodaySMSApi:String           = "\(baseUrl)InsertAbsentTodaySMS"
//
//    static let getBulkSMSDataApi:String                 = "\(baseUrl)GetBulkSMSData"
//    static let insertBulkSMSDataApi:String              = "\(baseUrl)InsertBulkSMSData"
//
//    static let getStaffSMSDataApi:String                = "\(baseUrl)GetStaffSMSData"
//    static let insertStaffSMSDataApi:String             = "\(baseUrl)InsertStaffSMSData"
//
//    static let insertSingleSMSDataApi:String            = "\(baseUrl)InsertSingleSMSData"
//
//    static let getAllSMSDetailApi:String                = "\(baseUrl)GetAllSMSDetail"
//    static let getAppSMSDataApi:String                  = "\(baseUrl)GetAppSMSData"
//    static let sendAppSMSApi:String                     = "\(baseUrl)SendAppSMS"
//
//    static let getStandardSectionCombineApi:String      = "\(baseUrl)GetStandardSectionCombine"
//    static let getTestForMarksApi:String                = "\(baseUrl)GetTestForMarks"
//    static let getMarksApi:String                       = "\(baseUrl)GetMarks"
//    static let sendStudentMarksSMSApi:String            = "\(baseUrl)SendStudentMarksSMS"
//
//    //    static let getAnnouncementDataApi:String            = "\(baseUrl)GetAnnouncementData"
//    //static let insertAnnouncementApi:String             = "\(baseUrl)InsertAnnouncement"
//
//    static let teacherGetClassSubjectWiseStudentApi:String         = "\(baseUrl)TeacherGetClassSubjectWiseStudent"
//    static let ptmTeacherStudentGetDetailApi:String                = "\(baseUrl)PTMTeacherStudentGetDetail"
//    static let ptmTeacherStudentInsertDetailApi:String             = "\(baseUrl)PTMTeacherStudentInsertDetail"
//    static let ptmDeleteMeeting:String                             = "\(baseUrl)PTMDeleteMeeting"
//
//    static let getMonthlyCountApi:String                           = "\(baseUrl)GetMonthlyCount"
//    static let getDateCountPerMonthApi:String                      = "\(baseUrl)GetDateCountPerMonth"
//    static let getLoginDetailsDatewiseApi:String                   = "\(baseUrl)GetLoginDetailsDatewise"
//
//    static let getHolidayApi:String                                = "\(baseUrl)GetHoliday"
//    static let getHolidayCategoryApi:String                        = "\(baseUrl)GetHolidayCategory"
//    static let insertHolidayApi:String                             = "\(baseUrl)InsertHoliday"

    //Antra from 7/1/2018

    @FormUrlEncoded
    @retrofit2.http.GET
    Call<HeadwiseStudent> getHeadWiseFeesCollectionStudent(@Url String url);

    @retrofit2.http.GET
    Call<MISHeadwiseFee> getHeadwiseFeeDetail(@Url String url);

    @retrofit2.http.GET
    Call<MISStudentRange> getStudentRange(@Url String url);

    @retrofit2.http.GET
    Call<TransportMainModel> getTransportDetail(@Url String url);

    @retrofit2.http.GET
    Call<MIStudentWiseCalendarModel> getSchoolCalendarDetail(@Url String url);

    @retrofit2.http.GET
    Call<PermissionDataModel> getPermissionData(@Url String url);

    @retrofit2.http.GET
    Call<GalleryDataModel> getGalleryData(@Url String url);

    @retrofit2.http.GET
    Call<JsonObject> deleteGalleryData(@Url String url);

    @retrofit2.http.GET
    Call<GalleryDataModel> insertGalleryData(@Url String url);

    @retrofit2.http.FormUrlEncoded
    @retrofit2.http.POST("CheckStaffActive")
    Call<JsonObject> getUserStatus(@Field("UserID") String userid);

    @retrofit2.http.FormUrlEncoded
    @retrofit2.http.POST("AdminMessage")
    Call<NotificationModel> getNotification(@Field("StaffID") String StaffID);

    @retrofit2.http.FormUrlEncoded
    @retrofit2.http.POST("GetTopperWithComparison")
    Call<TopperChartModel> getTopperChart(@Field("TermDetailID") String TermDetailID);

    @retrofit2.http.FormUrlEncoded
    @retrofit2.http.POST("GetCountRangeWise")
    Call<RangeChartModel> getRangeChart(@Field("TermID") String TermID);

    @retrofit2.http.GET("GetTerm")
    Call<TermModel> getTerm();

    @retrofit2.http.FormUrlEncoded
    @retrofit2.http.POST("GetParentSuggestion")
    Call<SuggestionDataModel> getSuggestion(@retrofit2.http.FieldMap HashMap<String, String> map);

    @retrofit2.http.FormUrlEncoded
    @retrofit2.http.POST("ReplyToParentSuggestion")
    Call<JsonObject> getReplyParentSuggestion(@retrofit2.http.FieldMap HashMap<String, String> map);

    @retrofit2.http.GET("GetInquiryStatus")
    Call<TermModel> getInquiryStatus();

    @retrofit2.http.FormUrlEncoded
    @retrofit2.http.POST("BirthdayWish")
    Call<JsonObject> getBirthdaywish(@Field("StaffID") String StaffID, @Field("Type") String Type, @Field("ReceiverID") String ReceiverID);

}
