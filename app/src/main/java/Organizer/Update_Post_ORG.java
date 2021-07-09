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
import com.bumptech.glide.Glide;
import com.yaga909.contestyard.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Update_Post_ORG extends AppCompatActivity {
    int id;
    String post_name, post_fees, post_date, post_details, post_banner;
    TextView datechooser;
    EditText name, fees, description;
    ImageView banner;
    Bitmap bitmap;
    int year, month, date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__post__o_r_g);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        
        post_name = intent.getStringExtra("post_name");
        post_fees = intent.getStringExtra("post_fees");
        post_date = intent.getStringExtra("post_date");
        post_details = intent.getStringExtra("post_details");
        post_banner = intent.getStringExtra("post_banner");

        datechooser = findViewById(R.id.dateChooser);
        name = findViewById(R.id.postNameET);
        fees = findViewById(R.id.postFeesET);
        description = findViewById(R.id.postDescriptionET);
        banner = findViewById(R.id.bannerViewIV);

        datechooser.setText(post_date);
        name.setText(post_name);
        fees.setText(post_fees);
        description.setText(post_details);

        String url = "http://projecttech.xyz/contest_yard/comp_banners/";
        Glide.with(this).load(url + post_banner).into(banner);




    }


    public void ChooseBanner(View view) {
        Intent intent = new Intent();
       intent.setType("image/*");
       intent.setAction(Intent.ACTION_GET_CONTENT);
       startActivityForResult(Intent.createChooser(intent,"Choose Image File"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData() !=null){
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                banner.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    protected String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageToByte  = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageToByte,Base64.DEFAULT);
    }


    public void DatePicker(View view) {
        Calendar calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Update_Post_ORG.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int date) {
                String dateformat = year + " " + month + " " + date;
                datechooser.setText(dateformat);

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);

                CharSequence dateCharSequence = DateFormat.format("EEEE, dd MMM yyyy", calendar1);
                datechooser.setText(dateCharSequence);

            }
        }, year, month, date);
        datePickerDialog.show();
    }


    public void Update(View view) {
        
        updateData(id);
        
    }

    private void updateData(int id) {

        Random random = new Random();
        int image_name = random.nextInt(1000);
        
        String url = "http://projecttech.xyz/contest_yard/update_comp_post.php?id="+id;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Update_Post_ORG.this, "Updated", Toast.LENGTH_SHORT).show();
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update_Post_ORG.this, "Failed", Toast.LENGTH_SHORT).show();
                
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> stringStringMap = new HashMap<>();
                stringStringMap.put("comp_name", name.getText().toString());
                stringStringMap.put("comp_date", datechooser.getText().toString());
                stringStringMap.put("comp_description", description.getText().toString());
                stringStringMap.put("comp_fees", fees.getText().toString() + " taka");
                stringStringMap.put("comp_image_name", "Updated Banner" + image_name + ".jpg");
                stringStringMap.put("dp", imageToString(bitmap));




                return stringStringMap;
            }
        };
        
        
        requestQueue.add(stringRequest);
        
    }
}