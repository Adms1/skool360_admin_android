package anandniketan.com.skool360.Utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static anandniketan.com.skool360.Utility.AppConfiguration.LIVE_BASE_URL;


/**
 * Created by admsandroid on 11/20/2017.
 */

public class ApiHandler {

    private static final long HTTP_TIMEOUT = TimeUnit.SECONDS.toMillis(10000);
    // ;
    public static String BASE_URL = AppConfiguration.BASEURL;
    private static WebServices apiService, apiServiceForFileUpload;


    public static WebServices getApiService() {
        if (apiService == null) {
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(100 * 1000, TimeUnit.MILLISECONDS);
            okHttpClient.setWriteTimeout(100 * 1000, TimeUnit.MILLISECONDS);
            okHttpClient.setReadTimeout(100 * 1000, TimeUnit.MILLISECONDS);
//
//            try{
//                BASE_URL = PrefUtils.getInstance(mAppcontext).getStringValue("live_base_url","");
//                BASE_URL  = BASE_URL + AppConfiguration.BASE_API_CONTAINER;
//            }catch (Exception ex){
//                ex.printStackTrace();
//            }


//            okHttpClient.setSslSocketFactory(new NoSSLv3Factory());
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(BASE_URL)
                    .setClient(new OkClient(myOkHttpClient()))
                    .setConverter(new GsonConverter(new Gson()))
                    .build();

            apiService = restAdapter.create(WebServices.class);
            return apiService;
        } else {
//            try{
//                BASE_URL = PrefUtils.getInstance(mAppcontext).getStringValue("live_base_url","");
//                BASE_URL  = BASE_URL + AppConfiguration.BASE_API_CONTAINER;
//            }catch (Exception ex){
//                ex.printStackTrace();
//            }

            return apiService;
        }
    }

    public static WebServices getApiServiceForFileUplod() {
        //retrofit2
        if (apiServiceForFileUpload == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(LIVE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(new okhttp3.OkHttpClient().newBuilder()
                            .connectTimeout(2000, TimeUnit.SECONDS)
                            .readTimeout(1000, TimeUnit.SECONDS)
                            .writeTimeout(1000, TimeUnit.SECONDS)
                            .build())
                    .build();
            apiServiceForFileUpload = retrofit.create(WebServices.class);
            return apiServiceForFileUpload;

        } else {
//            try{
//                BASE_URL = PrefUtils.getInstance(mAppcontext).getStringValue("live_base_url","");
//                BASE_URL  = BASE_URL + AppConfiguration.BASE_API_CONTAINER;
//            }catch (Exception ex){
//                ex.printStackTrace();
//            }

            return apiServiceForFileUpload;
        }
    }

    protected static OkHttpClient myOkHttpClient() {

        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setReadTimeout(100 * 1000, TimeUnit.MILLISECONDS);
            okHttpClient.setConnectTimeout(100 * 1000, TimeUnit.MILLISECONDS);

            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }};


            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            okHttpClient.setSslSocketFactory(sslSocketFactory);

            return okHttpClient;

        } catch (Exception e) {
            return null;
        }
    }

}
