package com.example.fuelqueueapplication.api.interfaces;

import com.example.fuelqueueapplication.api.response.FuelStationDetailsResponse;
import com.example.fuelqueueapplication.api.response.FuelStationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FuelStationInterface {
    @GET("/api/FuelStation/getFuelStations")
    Call<List<FuelStationResponse>> getAllFuelStations();

    @GET("/api/FuelStation/get/{id}")
    Call<FuelStationDetailsResponse> getFuelStationDetails(@Path("id") String id);
}


// TODO: Interface define #1
// TODO: request / response define
