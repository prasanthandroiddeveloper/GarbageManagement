package com.bsss.garbagemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class activity_register extends AppCompatActivity {

    // Creating EditText.
    EditText FullName, Email, Password,Mobile ;

    // Creating button;
    Button Register;
    TextView Login,Forgott;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Create string variable to hold the EditText Value.
    String NameHolder, EmailHolder, PasswordHolder,MobileHolder ;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    // Storing server url into String variable.
    String HttpUrl = "http://bhanusevenhillssoftwareservices.com/bnglr/register.php";

    Boolean CheckEditText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Assigning ID's to EditText.
        FullName = (EditText) findViewById(R.id.inputName);

        Email = (EditText) findViewById(R.id.inputEmail);

        Password = (EditText) findViewById(R.id.inputPass);
        Mobile=findViewById(R.id.inputMobile);
          Login=findViewById(R.id.btnLoginRegister1);
          Forgott=findViewById(R.id.frgt1);
        // Assigning ID's to Button.
        Register = (Button) findViewById(R.id.btn_request_sms);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(activity_register.this);

        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(activity_register.this);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_register.this,Login.class);
                startActivity(intent);
            }
        });
             Forgott.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent=new Intent(activity_register.this,Forgotpassword.class);
                     startActivity(intent);
                 }
             });
        // Adding click listener to button.
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    UserRegistration();

                }
                else {

                    Toast.makeText(activity_register.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    public void UserRegistration(){

        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, Registering..");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(activity_register.this, "User Registered Please Login To Confirm", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(activity_register.this,Login.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(activity_register.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("email", EmailHolder);
                params.put("password", PasswordHolder);
                params.put("name", NameHolder);
                params.put("mobile",MobileHolder);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity_register.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }


    public void CheckEditTextIsEmptyOrNot(){

        // Getting values from EditText.
        NameHolder = FullName.getText().toString().trim();
        EmailHolder = Email.getText().toString().trim();
        MobileHolder=Mobile.getText().toString().trim();
        PasswordHolder = Password.getText().toString().trim();

        // Checking whether EditText value is empty or not.
        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)|| TextUtils.isEmpty(MobileHolder))
        {

            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;

        }
        else {

            // If any of EditText is filled then set variable value as True.
            CheckEditText = true ;
        }
    }

}