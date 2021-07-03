package Organizer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class Organizer_Registration_Form extends AppCompatActivity {
    Bitmap bitmap;
    CircleImageView circleImageView;
    EditText orgNameET, emailET, phoneET, descriptionET, userNameET, locationET;
    TextView estdTV;
    int date, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer__registration__form);
        circleImageView = findViewById(R.id.profilePicture);

        orgNameET = findViewById(R.id.postNameET);
        emailET = findViewById(R.id.postDescriptionET);
        phoneET = findViewById(R.id.phoneNumberET);
        estdTV = findViewById(R.id.estdTV);
        locationET = findViewById(R.id.locationET);
        descriptionET = findViewById(R.id.descriptionET);
        userNameET = findViewById(R.id.postFeesET);
    }

    public void uploadPicture(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                circleImageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    protected String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte[] imageToByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageToByte, Base64.DEFAULT);

    }

    public void sendrequest(View view) {
        String name = orgNameET.getText().toString();
        String email = emailET.getText().toString();
        String phone = phoneET.getText().toString();
        String location = locationET.getText().toString();
        String description = descriptionET.getText().toString();
        String estd = estdTV.getText().toString();
        String userName = userNameET.getText().toString();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || location.isEmpty() || description.isEmpty()
                || estd.isEmpty() || userName.isEmpty()) {
            Toast.makeText(this, "Please Fill Up All the Information Properly", Toast.LENGTH_SHORT).show();

        } else {
            insertData();
        }


    }

    private void insertData() {
        Random random = new Random();
        int request_code = random.nextInt(1000);
        int image_name = random.nextInt(1000);

        String url = "http://projecttech.xyz/contest_yard/org_registration.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Organizer_Registration_Form.this, "Done", Toast.LENGTH_SHORT).show();
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Organizer_Registration_Form.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> stringStringMap = new HashMap<>();
                stringStringMap.put("name", orgNameET.getText().toString());
                stringStringMap.put("email", emailET.getText().toString());
                stringStringMap.put("phone", phoneET.getText().toString());
                stringStringMap.put("established", estdTV.getText().toString());
                stringStringMap.put("location", locationET.getText().toString());
                stringStringMap.put("description", descriptionET.getText().toString());
                stringStringMap.put("user_name", userNameET.getText().toString());
                stringStringMap.put("logo_name", "Org_Logo" + image_name + ".jpg");
                stringStringMap.put("request_code", "0" + request_code);

                stringStringMap.put("picture", imageToString(bitmap));


                return stringStringMap;
            }


        };
        requestQueue.add(stringRequest);
    }

    public void DatePicker(View view) {
        Calendar calendar =     Calendar.getInstance();

        date = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Organizer_Registration_Form.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateFormat = year+ " "+ month + " "+ date;
                estdTV.setText(dateFormat);

                Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(Calendar.YEAR, year);
                    calendar1.set(Calendar.MONTH, month);
                    calendar1.set(Calendar.DATE, date);

                    CharSequence dateCharSequence = DateFormat.format("EEEE, dd MM yyyy", calendar1);
                    estdTV.setText(dateCharSequence);
            }
        }, year, month,date);

        datePickerDialog.show();

    }
}