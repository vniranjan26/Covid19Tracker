package com.example.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

public class Statewise extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Statelist> statelists;
    TextView confirmedTv,activeTv,recoveredTv,deceasedTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statewise);
        confirmedTv=findViewById(R.id.confirmedTv);recoveredTv=findViewById(R.id.recoveredTv);;deceasedTv=findViewById(R.id.deceasedTv);activeTv=findViewById(R.id.activeTv);
        topviewfetch();
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        statelists= new ArrayList<>();
        fetch();

    }
    public void fetch()
    {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://api.covid19india.org/data.json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("statewise");
                    for (int i = 1; i < jsonArray.length(); i++){
                        JSONObject statewise = jsonArray.getJSONObject(i);
                        String state = statewise.getString("state");
                        String lastupdatedtime = statewise.getString("lastupdatedtime");
                        String deltaconfirmed = statewise.getString("deltaconfirmed");
                        String deltarecovered = statewise.getString("deltarecovered");
                        String deltadeaths = statewise.getString("deltadeaths");
                        Integer confirmed = Integer.parseInt(statewise.getString("confirmed"));
                        Integer recovered = Integer.parseInt(statewise.getString("recovered"));
                        Integer deaths = Integer.parseInt(statewise.getString("deaths"));
                        Statelist objstate = new Statelist(state,lastupdatedtime,deltaconfirmed,deltarecovered,deltadeaths,confirmed,recovered,deaths);
                        statelists.add(objstate);
                        adapter=new StateAdapter(statelists,getApplicationContext());
                        recyclerView.setAdapter(adapter); Log.d("Ex123", "adpater added");
                    }

                } catch (JSONException e) {
                    Log.d("Exception", "Exception1");
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
    public void topviewfetch()
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
                   /* Integer dailyconfirmed = Integer.parseInt(cases_time_series.getString("dailyconfirmed"));
                    confirminc.setText(""+dailyconfirmed);
                    Integer dailydeceased = Integer.parseInt(cases_time_series.getString("dailydeceased"));
                    confirmdead.setText(""+dailydeceased);
                    Integer dailyrecovered = Integer.parseInt(cases_time_series.getString("dailyrecovered"));
                    confirmrec.setText(""+dailyrecovered);*/
                    Integer totalconfirmed = Integer.parseInt(cases_time_series.getString("totalconfirmed"));
                    confirmedTv.setText(""+totalconfirmed);
                    Integer totaldeceased = Integer.parseInt(cases_time_series.getString("totaldeceased"));
                    deceasedTv.setText(""+totaldeceased);
                    Integer totalrecovered = Integer.parseInt(cases_time_series.getString("totalrecovered"));
                    recoveredTv.setText(""+totalrecovered);
                    activeTv.setText(""+(totalconfirmed-(totaldeceased+totalrecovered)));
                    /*String dateymd = cases_time_series.getString("dateymd");
                    lastUpdatedTv.setText("Last Updated on\n"+dateymd+" 11:59:00");*/
                } catch (JSONException e) {
                    Log.d("Ex", "Exception");
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Ex", "Exception");

            }
        });
        requestQueue1.add(jsonObjectRequest);

    }
    @Override public void onBackPressed()
    {
        this.finish();
        return;
    }


    public void back(View view) {
        onBackPressed();
    }
}