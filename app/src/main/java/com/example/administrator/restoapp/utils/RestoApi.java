package com.example.administrator.restoapp.utils;

import com.example.administrator.restoapp.models.ProductModel;
import com.example.administrator.restoapp.models.SignupLoginModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Administrator on 11/16/2017.
 */

public interface RestoApi {

    public static final String BASE_URL = "https://restappnick.herokuapp.com/api/";

//
//
//    @FormUrlEncoded
//    @POST()
//    Call<SignupLoginModel> signUpLogin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("users/facebooklogin/")
    Call<SignupLoginModel> loginWithFacebook(@Field("fullname") String fullname, @Field("email") String email, @Field("fbId") String fbId, @Field("fbtoken") String fbtoken, @Field("profile_pic") String profile_pic);

    @FormUrlEncoded
    @POST("users/googlelogin/")
    Call<SignupLoginModel> loginWithGoogle(@Field("fullname") String fullname, @Field("email") String email, @Field("gId") String gId, @Field("gToken") String gToken, @Field("profile_pic") String profile_pic);

    @GET("products/categories/{id}/")
    Call<ProductModel> getProducts(@Header("Authorization") String authorization, @Path("id") int id);


}
