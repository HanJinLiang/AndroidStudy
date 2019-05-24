package com.hanjinliang.androidstudy.data;


import com.hanjinliang.androidstudy.BuildConfig;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Callback;
import retrofit2.Retrofit;


public class RDataRepository {
  //  public static String APP_DOMAIN ="http://132.232.84.18:8080/selectUserCond";
    public static String APP_DOMAIN ="http://localhost:8080";
    private static final String TAG = RDataRepository.class.getSimpleName();
    private static RDataRepository INSTANCE;

    private RApiService service;
    private String baseUrl;

    private RDataRepository() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl.Builder builder1 = request.url().newBuilder();

                Request.Builder requestBuilder = request.newBuilder()
                        .url(builder1.build());
                request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        if ("debug".equals(BuildConfig.BUILD_TYPE)) {//测试 测试log拦截器
            builder.addInterceptor(new LoggingInterceptor());
        }
        //设置超时
        builder.readTimeout(30, TimeUnit.SECONDS).connectTimeout(10, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS);
        baseUrl = APP_DOMAIN;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(new FastJsonConverterFactory())
                .callFactory(builder.build())
                .build();
        service = retrofit.create(RApiService.class);
    }

    /**
     * 重置
     */
    public static void reset() {
        INSTANCE = null;
    }


    public static RDataRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RDataRepository();
        }

        return INSTANCE;
    }


    /**
     * 上传照片
     * @param file
     * @param cb
     */
    public void uploadPic(   File file, Callback<Result<String>> cb){
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        service.uploadPic( body).enqueue(cb);
    }

    /**
     * 上传照片
     * @param file
     * @param cb
     */
    public void uploadPic(  byte[] file, Callback<Result<String>> cb){
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", "test.jpg", requestFile);
        service.uploadPic( body).enqueue(cb);
    }

    public void selectUserCond( Callback<Result<String>> cb){
        service.selectUserCond( ).enqueue(cb);
    }

}
