package com.example.fuelqueueapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fuelqueueapplication.api.ApiClient;
import com.example.fuelqueueapplication.api.interfaces.FuelStationInterface;
import com.example.fuelqueueapplication.api.response.FuelStationResponse;
import com.example.fuelqueueapplication.recyclerViewAdapters.FuelStationListRecyclerViewAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuelStationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FuelStationListRecyclerViewAdapter recyclerViewAdapter;
    FuelStationInterface fuelStationInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_station);
        getSupportActionBar().setTitle("All Fuel Stations");
        recyclerView = findViewById(R.id.FuelStationRecyclerView);

        fuelStationInterface =  ApiClient.getClient().create(FuelStationInterface.class);
        Call<List<FuelStationResponse>> listCall = fuelStationInterface.getAllFuelStations();

        listCall.enqueue(new Callback<List<FuelStationResponse>>() {
            @Override
            public void onResponse(Call<List<FuelStationResponse>> call, Response<List<FuelStationResponse>> response) {
                if (response.isSuccessful()) {
                    List<FuelStationResponse> stationResponseList = response.body();
                    System.out.println(stationResponseList);
                    int i;
                    for(i=0;i<stationResponseList.size(); i++){
                        System.out.println(stationResponseList.get(i).getLocation());
                    }
                    recyclerViewAdapter = new FuelStationListRecyclerViewAdapter(FuelStationActivity.this,stationResponseList);
                    recyclerView.setAdapter(recyclerViewAdapter);
                }else {
                    Toast.makeText(FuelStationActivity.this, "CAN'T_GET_THE_FUEL_STATIONS", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FuelStationResponse>> call, Throwable t) {
                Toast.makeText(FuelStationActivity.this, "INTERNAL_SERVER_ERROR", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.feul_station_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.locationSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                recyclerViewAdapter.getFilter().filter(text);
                return false;
            }
        });
        return true;
    }
}