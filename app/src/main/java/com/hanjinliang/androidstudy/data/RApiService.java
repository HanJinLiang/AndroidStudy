package com.hanjinliang.androidstudy.data;




import okhttp3.MultipartBody;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface RApiService {

    @Multipart
    @POST("/user/uploadHeaderImg")
    Call<Result<String>> uploadPic(@Part MultipartBody.Part file);


    @GET("/user/selectUserCond")
    Call<Result<String>> selectUserCond();

}
