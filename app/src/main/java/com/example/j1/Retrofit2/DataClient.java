package com.example.j1.Retrofit2;

import com.example.j1.CClient;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DataClient {
    @Multipart
    @POST("uploadimage.php")
    Call<String> UploadPhoto(@Part MultipartBody.Part photo);

    @FormUrlEncoded // send data string format
    @POST("insert.php")
    Call<String> InsertData(@Field("username") String username
                            ,@Field("password") String password
                            ,@Field("imagepath") String imagepath);

    @FormUrlEncoded
    @POST("login.php")
    Call<List<CClient>> Logindata(@Field("username") String username
                                    ,@Field("password") String password);

    @GET("delete.php")
    Call<String> DeleteData (@Query("id") String id,
                             @Query("imagepath") String imagepath);


}
