package Organizer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yaga909.contestyard.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Create_Post_ORG extends AppCompatActivity {
    Bitmap bitmap;
    ImageView bannerViewIV;
    TextView lastDateET;
    int year, month, date;
    EditText postNameET, postDescriptionET, postFeesET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__post);
        bannerViewIV = findViewById(R.id.bannerViewIV);
        lastDateET = findViewById(R.id.dateChooser);
        postNameET = findViewById(R.id.postNameET);
        postDescriptionET = findViewById(R.id.postDescriptionET);
        postFeesET = findViewById(R.id.postFeesET);




    }

    public void ChooseBanner(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose Image File"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                bannerViewIV.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    protected String ImageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageToByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageToByte, Base64.DEFAULT);
    }


    public void DatePicker(View view) {
        Calendar calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Create_Post_ORG.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int date) {
                String dateformat = year + " " + month + " " + date;
                lastDateET.setText(dateformat);

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);

                CharSequence dateCharSequence = DateFormat.format("EEEE, dd MMM yyyy", calendar1);
                lastDateET.setText(dateCharSequence);

            }
        }, year, month, date);
        datePickerDialog.show();

    }

    public void CreatePost(View view) {


        String lastDate = lastDateET.getText().toString();
        String name = postNameET.getText().toString();
        String description = postDescriptionET.getText().toString();
        String fees = postFeesET.getText().toString();

        if (lastDate.isEmpty() || name.isEmpty() || description.isEmpty() || fees.isEmpty()) {
            Toast.makeText(this, "Please fill up all the content first", Toast.LENGTH_SHORT).show();
        } else {
            insertPostData();
        }


    }

    private void insertPostData() {

        Intent intent = getIntent();
        String username = intent.getStringExtra("posted_by");
        String code = intent.getStringExtra("code1");
        Log.d("TAG", "insertPostData: "+username);
        Log.d("TAG", "insertPostData: "+code);



        Random random = new Random();
        int image_name = random.nextInt(1000);

        String url = "http://projecttech.xyz/contest_yard/comp_post.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Create_Post_ORG.this, "Done", Toast.LENGTH_SHORT).show();
                postNameET.getText().clear();
                postFeesET.getText().clear();
                postDescriptionET.getText().clear();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Create_Post_ORG.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> stringStringMap = new HashMap<>();
                stringStringMap.put("comp_name", postNameET.getText().toString());
                stringStringMap.put("comp_date", lastDateET.getText().toString());
                stringStringMap.put("comp_description", postDescriptionET.getText().toString());
                stringStringMap.put("comp_fees", postFeesET.getText().toString() + " taka");
                stringStringMap.put("comp_image_name", "Banner"+image_name+".jpg");
                stringStringMap.put("comp_posted_by",username);
                stringStringMap.put("comp_code",code);
                stringStringMap.put("dp", ImageToString(bitmap));


                return stringStringMap;
            }


        };
        requestQueue.add(stringRequest);
    }
}