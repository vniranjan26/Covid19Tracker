package com.example.covid19tracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Recovery extends AppCompatActivity {

    TextView button4;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Detailedlist> rec_detailedlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);
        button4=findViewById(R.id.button4);
        totalconfirmed();
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        rec_detailedlist= new ArrayList<>();
        recoverdetails();
    }
    public void back(View view) {
        onBackPressed();
    }
    public void totalconfirmed()
    {
        RequestQueue requestQueue1;
        requestQueue1 = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://api.covid19india.org/data.json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("cases_time_series");
                    int jsonArraySize = jsonArray.length();
                    JSONObject cases_time_series = jsonArray.getJSONObject(jsonArraySize-1);
                    Integer  totalrecovered = Integer.parseInt(cases_time_series.getString("totalrecovered"));
                    Integer  dailyrecovered = Integer.parseInt(cases_time_series.getString("dailyrecovered"));
                    button4.setText("Recovered Today:\n"+dailyrecovered+"\n"+"Total Recovered:\n"+totalrecovered);
                    Log.d("Ex", "Exception5"+totalrecovered+"  :  "+dailyrecovered);
                } catch (JSONException e) {
                    Log.d("Ex", "Exception4");
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Ex", "Exception3");

            }
        });
        requestQueue1.add(jsonObjectRequest);
    }
   public void recoverdetails()
   {
       RequestQueue requestQueue;
       requestQueue = Volley.newRequestQueue(this);
       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
               "https://api.covid19india.org/data.json", null, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {

               try {
                   JSONArray jsonArray = response.getJSONArray("cases_time_series");
                   for (int i = (jsonArray.length()-1); i >= 0; i--){
                       JSONObject statewise = jsonArray.getJSONObject(i);
                       String date = statewise.getString("date");
                       Integer totalrecovered = Integer.parseInt(statewise.getString("totalrecovered"));
                       Integer dailyrecovered = Integer.parseInt(statewise.getString("dailyrecovered"));
                       Detailedlist obj1 = new Detailedlist(date,totalrecovered,dailyrecovered);
                       rec_detailedlist.add(obj1);
                       adapter=new DetailedAdapter(rec_detailedlist,getApplicationContext());
                       recyclerView.setAdapter(adapter);
                       Log.d("Ex", "Adapter added");
                   }

               } catch (JSONException e) {
                   Log.d("Ex", "Exception1");
                   e.printStackTrace();
               }

           }


       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Log.d("myapp", "Something went wrong");

           }
       });
       requestQueue.add(jsonObjectRequest);
   }

}