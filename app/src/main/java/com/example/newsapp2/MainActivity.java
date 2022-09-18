package com.example.newsapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SelectListener {
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    AppCompatButton btn, entertainment_btn, sports_btn, top_btn, business_btn, allNews_btn, technology_btn;
    ArrayList<model> arr = new ArrayList<>();  // for getting all news
    String url = "https://newsdata.io/api/1/news?apikey=pub_1117393b8d60c57e35306149a0e8ba5d39284&country=in";
    LottieAnimationView lottieAnimationView;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);

        allNews_btn = findViewById(R.id.allNews_btn);
        entertainment_btn = findViewById(R.id.entertainment_btn);
        sports_btn = findViewById(R.id.sports_btn);
        top_btn = findViewById(R.id.top_btn);
        business_btn = findViewById(R.id.business_btn);
        technology_btn = findViewById(R.id.technology_btn);

        lottieAnimationView = findViewById(R.id.no_result_animation);

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //for all news --> oncreate automatic call
        extractallnews();


        //all news
        allNews_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extractallnews();
            }
        });

        // for entertainment
        entertainment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extractEntertainmentNews();
            }
        });

        sports_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExtractSportNews();
            }
        });

        top_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExtractTopNews();
            }
        });

        business_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExtractBusinessNews();
            }
        });

        technology_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExtractTechnologyNews();
            }
        });
    }

    private void extractallnews() {


        requestQueue = Volley.newRequestQueue(this);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                lottieAnimationView.setVisibility(View.GONE);

                    try {
                        Log.d("Gaurav", "Respones : " + response);
                        JSONArray jsonArray = response.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            if (obj.getString("language").equals("english")) {
                                String title = obj.getString("title");
                                String source_id = obj.getString("source_id");
                                String Image_url = obj.getString("image_url");
                                String content = obj.getString("content");
                                Log.d("Gaurav", "url : " + Image_url);
                                arr.add(new model(title, source_id, Image_url, content));
                            }

                        }
                    } catch (JSONException e) {
                        Log.d("Gaurav", "error is catched !");
                        e.printStackTrace();
                    }


                CustomAdapter adapter = new CustomAdapter(getApplicationContext(), arr, MainActivity.this);
                recyclerView.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lottieAnimationView.setVisibility(View.VISIBLE);

            }
        });

        Log.d("Gaurav", "size : " + arr.size());

        requestQueue.add(jsonObjectRequest);

    }

    //Entertainment
    private void extractEntertainmentNews() {

        ArrayList<model> enter_arr = new ArrayList<>();  // for entertainment category

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Gaurav", "clicked entertainment !");
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    int flag = 0;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        JSONArray category = obj.getJSONArray("category");
                        String cat = (String) category.get(0);

                        if (cat.equals("entertainment")) {
                            flag = 1;
                            lottieAnimationView.setVisibility(View.GONE);
                            String title = obj.getString("title");
                            String source_id = obj.getString("source_id");
                            String Image_url = obj.getString("image_url");
                            String content = obj.getString("content");
                            enter_arr.add(new model(title, source_id, Image_url, content));
                        }

                    }
                    if(flag == 0){
                        lottieAnimationView.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    Log.d("Gaurav", "error is catched !");
                    e.printStackTrace();
                }
                CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, enter_arr, MainActivity.this);
                recyclerView.setAdapter(customAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Gaurav", "Response Error  !");

            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    public void ExtractSportNews() {
        ArrayList<model> sports_arr = new ArrayList<>(); // for sports category

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                lottieAnimationView.setVisibility(View.GONE);
                if (response.length() != 0) {
                    Log.d("Gaurav", "clicked entertainment !");
                    try {
                        JSONArray jsonArray = response.getJSONArray("results");
                        int flag = 0;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            JSONArray category = obj.getJSONArray("category");
                            Log.d("Gaurav", "Index : " + jsonArray.get(i) + " has category : " + category.get(0));
                            String cat = (String) category.get(0);

                            if (cat.equals("sports")) {
                                flag = 1;
                                lottieAnimationView.setVisibility(View.GONE);
                                String title = obj.getString("title");
                                String source_id = obj.getString("source_id");
                                String Image_url = obj.getString("image_url");
                                String content = obj.getString("content");
                                sports_arr.add(new model(title, source_id, Image_url, content));
                            }

                        }
                        if(flag == 0){
                            lottieAnimationView.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        Log.d("Gaurav", "error is catched !");
                        e.printStackTrace();
                    }

                    CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, sports_arr, MainActivity.this);
                    recyclerView.setAdapter(customAdapter);

                } else {
                    Log.d("Gaurav", "Error occured");
                    lottieAnimationView.playAnimation();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Gaurav", "Response Error  !");
                lottieAnimationView.setVisibility(View.VISIBLE);
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    public void ExtractTopNews() {
        ArrayList<model> top_arr = new ArrayList<>(); // top category

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                lottieAnimationView.setVisibility(View.GONE);
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    int flag = 0;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        JSONArray category = obj.getJSONArray("category");
                        String cat = (String) category.get(0);

                        if (cat.equals("top")) {
                            flag = 1;
                            lottieAnimationView.setVisibility(View.GONE);
                            String title = obj.getString("title");
                            String source_id = obj.getString("source_id");
                            String Image_url = obj.getString("image_url");
                            String content = obj.getString("content");
                            top_arr.add(new model(title, source_id, Image_url, content));
                        }

                    }
                    if(flag == 0){
                        lottieAnimationView.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    Log.d("Gaurav", "error is catched !");
                    e.printStackTrace();
                }
                CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, top_arr, MainActivity.this);
                recyclerView.setAdapter(customAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Gaurav", "Response Error  !");
                lottieAnimationView.setVisibility(View.VISIBLE);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void ExtractBusinessNews() {
        ArrayList<model> business_arr = new ArrayList<>(); // business category
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Gaurav", "Clicked Business !");
                lottieAnimationView.setVisibility(View.GONE);
                try {

                    JSONArray jsonArray = response.getJSONArray("results");
                    int flag = 0;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        JSONArray category = obj.getJSONArray("category");
                        Log.d("Gaurav", "Index : " + jsonArray.get(i) + " has category : " + category.get(0));
                        String cat = (String) category.get(0);

                        if (cat.equals("business")) {
                            flag = 1;
                            lottieAnimationView.setVisibility(View.GONE);
                            String title = obj.getString("title");
                            String source_id = obj.getString("source_id");
                            String Image_url = obj.getString("image_url");
                            String content = obj.getString("content");
                            business_arr.add(new model(title, source_id, Image_url, content));
                        }
                    }
                    if(flag == 0){
                        lottieAnimationView.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    Log.d("Gaurav", "error is catched !");
                    e.printStackTrace();
                }
                CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, business_arr, MainActivity.this);
                recyclerView.setAdapter(customAdapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Gaurav", "Response Error  !");
                lottieAnimationView.setVisibility(View.VISIBLE);
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    public void ExtractTechnologyNews() {
        ArrayList<model> technology_arr = new ArrayList<>(); // top category

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("Gaurav", "Technology Department !");

                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    int flag = 0;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        JSONArray category = obj.getJSONArray("category");
                        String cat = (String) category.get(0);

                        if (cat.equals("technology")) {
                            flag = 1;
                            lottieAnimationView.setVisibility(View.GONE);
                            String title = obj.getString("title");
                            String source_id = obj.getString("source_id");
                            String Image_url = obj.getString("image_url");
                            String content = obj.getString("content");
                            technology_arr.add(new model(title, source_id, Image_url, content));
                        }
                        else{

                        }
                    }
                    if(flag == 0){
                        lottieAnimationView.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    Log.d("Gaurav", "error is catched !");
                    e.printStackTrace();
                }
                CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, technology_arr, MainActivity.this);
                recyclerView.setAdapter(customAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Gaurav", "Response Error  !");

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void OnItemClicked(model model1) {
        Intent intent = new Intent(this,InformationActivity.class);

        intent.putExtra("ContentTitle1",model1.title);
        intent.putExtra("ContentResponse",model1.content);
        intent.putExtra("ImageUrlContent",model1.Image_url);
        Log.d("Gaurav","url : "+model1.Image_url);


        startActivity(intent);

    }


}





