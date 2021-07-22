package com.yaga909.contestyard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Pdf_Show_Activity extends AppCompatActivity {
    String comp_code, comp_id;
    PDFView pdfView;
    String pdf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_show);
        Intent intent = getIntent();
        comp_code = intent.getStringExtra("comp_code");
        comp_id = intent.getStringExtra("comp_id");
        pdfView = findViewById(R.id.pdfView);

        Log.d("TAG", "pdf name: " + pdf);


        String url = "http://projecttech.xyz/contest_yard/pdf/"+pdf;


        new RetrivePdf().execute(url);
        loadPdf(comp_id, comp_code);
    }

    public void loadPdf(String comp_id, String comp_code) {

        String url = "http://projecttech.xyz/contest_yard/getPdf.php?comp_code=" + comp_code + "&comp_id=" + comp_id;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    JSONObject data = jsonArray.getJSONObject(0);

                    pdf = data.getString("pdf_name");

                    Toast.makeText(Pdf_Show_Activity.this, "Please Wait", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Pdf_Show_Activity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(stringRequest);


    }

    class RetrivePdf extends AsyncTask<String, Void, InputStream> {


        @Override
        protected InputStream doInBackground(String... strings) {

            InputStream inputStream = null;

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }


            } catch (IOException e) {
                e.printStackTrace();

            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
        }
    }

}