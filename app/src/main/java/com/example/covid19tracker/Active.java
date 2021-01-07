package com.example.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Active extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView button4;
    RecyclerView.Adapter adapter;
    List<Activelist> activelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active);
        button4=findViewById(R.id.button4);
        totalconfirmed();
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        activelist= new ArrayList<>();
        loadrecyleview();
    }
    public void back(View view) {
        onBackPressed();
    }

    public void loadrecyleview()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

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
                        Integer totalconfirmed = Integer.parseInt(statewise.getString("totalconfirmed"));
                        Integer totaldeceased = Integer.parseInt(statewise.getString("totaldeceased"));
                        Integer totalrecovered = Integer.parseInt(statewise.getString("totalrecovered"));
                        Activelist obj = new Activelist( date, totalconfirmed, totaldeceased, totalrecovered);
                        activelist.add(obj);
                        adapter=new ActiveAdapter(activelist,getApplicationContext());
                        recyclerView.setAdapter(adapter);
                        progressDialog.dismiss();
                        Log.d("Ex", "The response is : "+date+"  :  "+totalconfirmed+"  :  "+totalrecovered);
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
                    Integer totalconfirmed = Integer.parseInt(cases_time_series.getString("totalconfirmed"));
                    Integer totaldeceased = Integer.parseInt(cases_time_series.getString("totaldeceased"));
                    Integer totalrecovered = Integer.parseInt(cases_time_series.getString("totalrecovered"));
                    int total=totaldeceased+totalrecovered;
                    int today = (totalconfirmed-total);
                    button4.setText("Active Today:\n"+today+"\n"+"Active Percent:\n"+(new DecimalFormat("#.##").format((double) (today*100)/totalconfirmed)+" %"));
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

}