package anandniketan.com.bhadajadmin.Utility;

/**
 * Created by admsandroid on 11/20/2017.
 */

public class AppConfiguration {

    // public static final String BASEURL = "http://192.168.1.7:8086/MobileApp_Service.asmx/";// use for office
    // public static final String BASEURL = "http://103.24.183.28:8085/MobileApp_Service.asmx/"; // use for client

    // URL From Image
    // public static final String BASEURL_IMAGES = "http://192.168.1.7:8086/skool360-Category-Images/Admin/";// use for office
    // public static final String BASEURL_IMAGES ="http://103.24.183.28:8085/skool360-Category-Images/Admin/";// use for client

    // URL From Icons
    // public static final String BASEURL_ICONS = "http://192.168.1.7:8086/skool360-Design-Icons/Admin/";// use for office
    // public static final String BASEURL_ICONS ="http://103.24.183.28:8085/skool360-Design-Icons/Admin/";// use for client

    public static String GET_API_URL = "http://anandniketanbhadaj.org/appService/5b9a72856992e144c74fc836ed6e76a2/appsUrl";

//    public static String GET_API_URL = "http://192.168.1.8:8086/";

    public static String BASE_API_CONTAINER = "MobileApp_Service.asmx/";
//                        public static String LIVE_BASE_URL = PrefUtils.getInstance(BaseApp.mAppcontext).getStringValue("live_base_url", "http://103.250.144.109:8085/");
    public static String LIVE_BASE_URL = "http://192.168.1.22:8086/";
//    public static String LIVE_BASE_URL_SKOOL = "http://192.168.1.22:8089/";
//    public static String LIVE_BASE_URL = "http://103.250.144.109:8085/";

    public static String BASEURL = LIVE_BASE_URL + BASE_API_CONTAINER;
//    public static String BASEURL2 = LIVE_BASE_URL_SKOOL + BASE_API_CONTAINER;

    public static String BASEURL_IMAGES = LIVE_BASE_URL + "skool360-Category-Images/Admin/";
    public static String BASEURL_ICONS = LIVE_BASE_URL + "skool360-Design-Icons/Admin/";

    public static String UPLOAD_PDF_URL = LIVE_BASE_URL + "uploadpdf.aspx";
    public static String UPLOAD_IMAGE_URL = LIVE_BASE_URL + "upload.aspx";

//    public static String DEVICE_ID = "";

    public static String StudentId = "", StandardId = "", Subtitle = "", Coundata = "", Grade = "", pageInnerTitle = "", CountdataAccount = "", acReqTitle = "";
    public static String TermId, schoolResultTermID = "", financeTermId = "", NA_TERM_ID = "", TAG = "Staff", calendarTermId = "";
    public static String DesgId;
    public static String DeptId;

    public static String TermName;
    public static String TermDetailId;
    public static String TermDetailName;
    public static String ReverseTermDetailId = "";
    public static String StudentStatus;

    public static String FinalTermIdStr = "";
    public static String FinalStandardIdStr = "";
    public static String FinalClassIdStr = "";
    public static String FinalStatusStr = "";
    public static String CheckStudentId = "";

    public static boolean firsttimeback;
    public static int position;
    public static String fromDate = "", toDate = "", month = "", year = "", staffDate = "", taskReportDate = "", inquiryviewstatus = "", HRstaffseachviewstatus = "";

}
