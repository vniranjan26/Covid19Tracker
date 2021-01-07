package com.example.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    //@Override
    int minus =1;
    boolean doubleBackToExitPressedOnce=false;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String date;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView confirmedTv,confirminc,recoveredTv,confirmrec,deceasedTv,confirmdead,activeTv,lastUpdatedTv;
    Button button3,button4,button5;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);
        button5=findViewById(R.id.button5);
        confirmedTv=findViewById(R.id.confirmedTv);confirminc=findViewById(R.id.confirminc);recoveredTv=findViewById(R.id.recoveredTv);confirmrec=findViewById(R.id.confirmrec);deceasedTv=findViewById(R.id.deceasedTv);confirmdead=findViewById(R.id.confirmdead);activeTv=findViewById(R.id.activeTv);lastUpdatedTv=findViewById(R.id.lastUpdatedTv);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        if(isConnected()) {
            fetch(minus);
            lastUpdatedTv.setText("Last Update on\n" + refreshtime());}
        else
        {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if(isConnected()) {
                    fetch(minus);
                    lastUpdatedTv.setText("Last Update on\n" + refreshtime());}
                   else{ Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();}
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });
    }
    public void fetch(final int minus)
    {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://api.covid19india.org/data.json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("cases_time_series");
                    int jsonArraySize = jsonArray.length();
                    JSONObject dayAfter_yesterday = jsonArray.getJSONObject(jsonArraySize-2);
                    String date = dayAfter_yesterday.getString("date");
                    button5.setText(date);
                    JSONObject cases_time_series = jsonArray.getJSONObject(jsonArraySize-minus);
                    Integer dailyconfirmed = Integer.parseInt(cases_time_series.getString("dailyconfirmed"));
                    confirminc.setText(""+dailyconfirmed);
                    Integer dailydeceased = Integer.parseInt(cases_time_series.getString("dailydeceased"));
                    confirmdead.setText(""+dailydeceased);
                    Integer dailyrecovered = Integer.parseInt(cases_time_series.getString("dailyrecovered"));
                    confirmrec.setText(""+dailyrecovered);
                    Integer totalconfirmed = Integer.parseInt(cases_time_series.getString("totalconfirmed"));
                    confirmedTv.setText(""+totalconfirmed);
                    Integer totaldeceased = Integer.parseInt(cases_time_series.getString("totaldeceased"));
                    deceasedTv.setText(""+totaldeceased);
                    Integer totalrecovered = Integer.parseInt(cases_time_series.getString("totalrecovered"));
                    recoveredTv.setText(""+totalrecovered);
                    activeTv.setText(""+(totalconfirmed-(totaldeceased+totalrecovered)));
                    Log.d("myapp", "The response is" + dailyconfirmed);
                } catch (JSONException e) {
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
    public void  showPopup(View view)
    {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.pop_menu);
        popup.show();
    }
    public String refreshtime()
    {
        calendar=Calendar.getInstance();
        simpleDateFormat= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        date=simpleDateFormat.format(calendar.getTime());
        return date;

    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.item1:
                return  true;
            case R.id.item2:
                Intent i= new Intent(MainActivity.this,Faq.class);
                startActivity(i);
                return  true;
            case R.id.item3:
                Intent email = new Intent(Intent.ACTION_SENDTO);
                email.setData(Uri.parse("mailto:vpatel2600@gmail.com"));
                email.putExtra(Intent.EXTRA_SUBJECT, "Report Issue");
                email.putExtra(Intent.EXTRA_TEXT, "Write your issue Here.");
                if (email.resolveActivity(getPackageManager()) != null) {
                    startActivity(email);
                }
                return  true;
            case R.id.item4:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:vpatel2600@gmail.com")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, "Write Your Message Here");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                return  true;
        }
        return false;
    }
    public  void onclickstatewise(View view)
    {
        Intent i= new Intent(MainActivity.this,Statewise.class);
        startActivity(i);
    }
    public void confirmed(View view)
    {
        Intent i= new Intent(MainActivity.this,Confirmdetails.class);
        startActivity(i);
    }
    public void deceased(View view)
    {
        Intent i= new Intent(MainActivity.this,Deceaseddetails.class);
        startActivity(i);
    }
    public void recovery(View view)
    {
        Intent i= new Intent(MainActivity.this,Recovery.class);
        startActivity(i);
    }
    public void active(View view)
    {
        Intent i= new Intent(MainActivity.this,Active.class);
        startActivity(i);
    }
    public void testing(View view)
    {
        Uri gmmIntentUri = Uri.parse("geo:0,0?z=1&q=covid 19 test");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void day(View view)
    {
        switch (view.getId())
        {
            case R.id.button3:
            { button3.setBackground(ContextCompat.getDrawable(this, R.drawable.spinner_bg));
                button3.setTextColor(Color.parseColor("#000000"));
                button4.setTextColor(Color.parseColor("#FFFFFF"));
                button4.setBackground(ContextCompat.getDrawable(this, R.drawable.statistic_button));
                button5.setTextColor(Color.parseColor("#FFFFFF"));
                button5.setBackground(ContextCompat.getDrawable(this, R.drawable.statistic_button));
                minus=1;
                fetch(minus);
                break;}
            case R.id.button4:
            { button4.setBackground(ContextCompat.getDrawable(this, R.drawable.spinner_bg));
                button4.setTextColor(Color.parseColor("#000000"));
                button3.setTextColor(Color.parseColor("#FFFFFF"));
                button3.setBackground(ContextCompat.getDrawable(this, R.drawable.statistic_button));
                button5.setTextColor(Color.parseColor("#FFFFFF"));
                button5.setBackground(ContextCompat.getDrawable(this, R.drawable.statistic_button));
                minus=2;
                fetch(minus);
                break;}
            case R.id.button5:
            { button5.setBackground(ContextCompat.getDrawable(this, R.drawable.spinner_bg));
                button5.setTextColor(Color.parseColor("#000000"));
                button4.setTextColor(Color.parseColor("#FFFFFF"));
                button4.setBackground(ContextCompat.getDrawable(this, R.drawable.statistic_button));
                button3.setTextColor(Color.parseColor("#FFFFFF"));
                button3.setBackground(ContextCompat.getDrawable(this, R.drawable.statistic_button));
                minus=3;
                fetch(minus);
                break;}
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    public boolean isConnected()
    {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if(null!=activeNetwork)
        {
           return true;
        }
        return false;
    }

}