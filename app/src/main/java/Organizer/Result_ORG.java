package Organizer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yaga909.contestyard.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Result_ORG extends AppCompatActivity {
    TextView msgTV;
    String encodedPDF;
    String name, code;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result__o_r_g);
        msgTV = findViewById(R.id.msgTV);
        Intent intent = getIntent();
       id = intent.getIntExtra("id", 0);
        Log.d("ID", "onCreate: "+id);
       name = intent.getStringExtra("post_name");
       code = intent.getStringExtra("org_code");

    }

    public void ChoosePdf(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent = intent.createChooser(intent,"Choose a file");
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data !=null){
            msgTV.setText("File Selected");
            Uri Path = data.getData();
            try {
                InputStream inputStream = Result_ORG.this.getContentResolver().openInputStream(Path);
                byte[] pdfToString = new byte[inputStream.available()];
                inputStream.read(pdfToString);
           encodedPDF = Base64.encodeToString(pdfToString,Base64.DEFAULT);
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public void UploadResult(View view) {
        uploadPDF();

    }

    private void uploadPDF() {
        Random random =new Random();
        int PdfCount = random.nextInt(1000);
        String url = "http://projecttech.xyz/contest_yard/comp_result.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Result_ORG.this, "Upload Successfull", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Result_ORG.this, "Upload Failed", Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> stringStringMap = new HashMap<>();
                stringStringMap.put("comp_name", name);
                stringStringMap.put("comp_code", code);
                stringStringMap.put("comp_id", String.valueOf(id));
                stringStringMap.put("pdf", encodedPDF);
                stringStringMap.put("pdf_name",name+PdfCount+".pdf");

                return stringStringMap;

            }
        };

        requestQueue.add(stringRequest);



    }
}