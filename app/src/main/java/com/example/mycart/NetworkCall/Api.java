package com.example.mycart.NetworkCall;

import com.example.mycart.Model.Addresses;
import com.example.mycart.Model.AllItems;
import com.example.mycart.Model.Items;
import com.example.mycart.Model.Users;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface Api {

    @POST
    Call<Users> getLogin(
            @Url String url,
            @Body RequestBody body
    );


    @POST
    Call<Addresses> getAddress(
            @Url String url,
            @Body RequestBody body
    );

    @GET
    Call<AllItems> getAllItems(
          @Url String url
    );
}
