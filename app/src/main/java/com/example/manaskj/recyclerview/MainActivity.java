package com.example.manaskj.recyclerview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RetrofitAdapter retrofitAdapter;
    private RecyclerView recyclerView;
    private static String Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar(getString(R.string.app_name));
        initView();

        fetchJSON();


    }


    public void setToolbar(@NonNull String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryLight));
        setSupportActionBar(toolbar);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void fetchJSON() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RecyclerInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        RecyclerInterface api = retrofit.create(RecyclerInterface.class);

        Call<String> call = api.getString();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Responsestring", response.body().toString());

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        String jsonresponse = response.body().toString();
                        writeRecycler(jsonresponse);

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void writeRecycler(String response) {

        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(response);

            ArrayList<ModelRecycler> modelRecyclerArrayList = new ArrayList<>();
            JSONArray dataArray = obj.getJSONArray("rows");
            Title = obj.getString("title");
            setToolbar(Title);
            for (int i = 0; i < dataArray.length(); i++) {

                ModelRecycler modelRecycler = new ModelRecycler();
                JSONObject dataobj = dataArray.getJSONObject(i);

                modelRecycler.setImgURL(dataobj.getString("imageHref"));
                modelRecycler.setName(dataobj.getString("title"));
                modelRecycler.setCountry(dataobj.getString("description"));
                modelRecycler.setCity(dataobj.getString("title"));

                modelRecyclerArrayList.add(modelRecycler);

            }

            retrofitAdapter = new RetrofitAdapter(MainActivity.this, modelRecyclerArrayList);
            recyclerView.setAdapter(retrofitAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

            Toast.makeText(MainActivity.this, obj.optString("message") + "", Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
