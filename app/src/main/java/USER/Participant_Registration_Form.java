package USER;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Participant_Registration_Form extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Bitmap bitmap;
    CircleImageView circleImageView;
    Spinner spinner;
    String gender;
    EditText nameET, emailET, phoneET, passwordET,confirmPasswordET , institutionET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_registration__form);
        circleImageView = findViewById(R.id.circleImageIV);

        nameET = findViewById(R.id.postNameET);
        emailET = findViewById(R.id.postDescriptionET);
        phoneET = findViewById(R.id.phoneNumberET);
        passwordET = findViewById(R.id.estdTV);
        confirmPasswordET = findViewById(R.id.locationET);
        institutionET = findViewById(R.id.descriptionET);


        spinner = findViewById(R.id.genderSelect);
        String[] gender = getResources().getStringArray(R.array.gender);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.dropdown_menu_layout, gender);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    }

    public void RegistrationBTN(View view) {


        String name = nameET.getText().toString();
        String email = emailET.getText().toString();
        String phone = phoneET.getText().toString();
        String password = passwordET.getText().toString();
        String confirmPassword = confirmPasswordET.getText().toString();
        String institution = institutionET.getText().toString();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || institution.isEmpty() || gender.isEmpty()
                || password.isEmpty() || confirmPassword.isEmpty()){
            Toast.makeText(this, "Please Fill Up All the Information Properly", Toast.LENGTH_SHORT).show();

        }
      else {
          insertInfo();
        }






    }




    private void insertInfo() {
        String url = "http://projecttech.xyz/contest_yard/student_registration.php";
        Random random = new Random();
        int dp_name =  random.nextInt(1000);


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Participant_Registration_Form.this, "Registration Complete!", Toast.LENGTH_SHORT).show();
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Participant_Registration_Form.this, "Failed!", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> stringStringMap = new HashMap<>();
                stringStringMap.put("student_name", nameET.getText().toString());
                stringStringMap.put("student_email", emailET.getText().toString());
                stringStringMap.put("phone_number", phoneET.getText().toString());
                stringStringMap.put("password", passwordET.getText().toString());
                stringStringMap.put("institution_name", institutionET.getText().toString());
                stringStringMap.put("gender", gender);
                stringStringMap.put("dp_name","Student - "+dp_name + ".jpg");
                stringStringMap.put("dp", imageToString(bitmap));
                stringStringMap.put("un_number", String.valueOf(dp_name));


                return stringStringMap;
            }
        };

        requestQueue.add(stringRequest);


    }


    //we use activity for result to take image from gallary.
    // it is a implecite but as it is bringing something from gallary,
    // That is why we are useing "StartActivityOnResult"


    public void profilePictureUpload(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), 1);

    }

//for profile picture upload====>
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

    //====>

//processing the chosing image to string to send on server.

    protected String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        byte[] imageToByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageToByte,Base64.DEFAULT);
    }

    //=====>

// for spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (adapterView.getId() == R.id.genderSelect) {
            gender = adapterView.getItemAtPosition(position).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //====
}