package com.marciorr.concretesolutionsproject.network;

import com.marciorr.concretesolutionsproject.model.RetroUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

//Define o EndPoint
public interface GetDataService {

    @GET("pulls")
    Call<List<RetroUser>> getAllUsers();
}
