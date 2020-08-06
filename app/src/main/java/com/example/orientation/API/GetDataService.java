package com.example.orientation.API;


import com.example.orientation.model.DetailResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("getdetails/")
    Call<DetailResponse> listdetail();
}
