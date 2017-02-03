package com.tek.ems.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by uyalanat on 24-10-2016.
 */
public class ServiceGenerator {

   // public static final String WEB_SERVICE_BASE_URL = "http://192.168.0.124:8084/EaSI/rest/";

    public static final String WEB_SERVICE_BASE_URL = "http://45ba4158.ngrok.io/EaSI/rest/";
/*public static final String WEB_SERVICE_BASE_URL = "http://103.245.20.163:8081/api/";*/

   // public static final String WEB_SERVICE_BASE_URL = "http://103.245.20.163:8081/api/";
    //http://103.245.20.163:8081/
   // public static final String WEB_SERVICE_BASE_URL = "http://www.csn.ems.com.iis3002.databasemart.net/api/";

    private static final String TAG = "ServiceGenerator";

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private static OkHttpClient.Builder httpClient = null;
    private static Retrofit.Builder builder = null;

    static {
        httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(1, TimeUnit.MINUTES); // connect timeout
        httpClient.readTimeout(1, TimeUnit.MINUTES);

//        Dispatcher dispatcher = new Dispatcher();
//        dispatcher.setMaxRequests(3);
//        httpClient.dispatcher(dispatcher);

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.interceptors().add(logging);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector(){
//            @Override
//            public Boolean isIgnorableType(AnnotatedClass ac) {
//                if(ac.getRawType().equals(RealmObject.class))
//                    return true;
//                return super.isIgnorableType(ac);
//            }
//        });

        builder = new Retrofit.Builder()
                .baseUrl(WEB_SERVICE_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper));
    }

    public static EMSService createService() {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(EMSService.class);
    }

//    public static <S> S createService(Class<S> serviceClass) {
//        Retrofit retrofit = builder.client(httpClient.build()).build();
//        return retrofit.create(serviceClass);
//    }
}