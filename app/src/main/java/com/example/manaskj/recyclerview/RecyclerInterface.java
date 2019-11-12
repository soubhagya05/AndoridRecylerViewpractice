package com.example.manaskj.recyclerview;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecyclerInterface {
//    String JSONURL ="https://demonuts.com/Demonuts/JsonTest/Tennis/";
    String JSONURL ="https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/";

//    @GET("json_parsing.php")
    @GET("facts.json")
    Call<String> getString();

}
