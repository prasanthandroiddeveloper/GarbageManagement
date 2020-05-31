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

public class Login extends AppCompatActivity {
    EditText Email, Password;

    // Creating button;
    Button LoginButton;
    TextView Register,Forgot;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Create string variable to hold the EditText Value.
    String EmailHolder, PasswordHolder;

    // Creating Progress dialog.
    ProgressDialog progressDialog;
    PrefManager prefManager;

    // Storing server url into String variable.
    String HttpUrl = "http://bhanusevenhillssoftwareservices.com/bnglr/loginfor.php";

    Boolean CheckEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefManager = new PrefManager(Login.this);

        // Assigning ID's to EditText.
        Email = (EditText) findViewById(R.id.editTextEmail);

        Password = (EditText) findViewById(R.id.editTextPassword);

        // Assigning ID's to Button.
        LoginButton = (Button) findViewById(R.id.cirLoginButton);
        Register=findViewById(R.id.btnLoginRegister);
        Forgot=findViewById(R.id.frgt);
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(Login.this);

        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(Login.this);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, activity_register.class);
                startActivity(intent);
            }
        });

        Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Forgotpassword.class);
                startActivity(intent);


            }
        });

        // Adding click listener to button.
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if (CheckEditText) {

                    UserLogin();

                } else {

                    Toast.makeText(Login.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }

            }
        });
        if(Boolean.toString(prefManager.isLoggedIn()).equalsIgnoreCase("true")){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();


        }

    }

    // Creating user login function.
    public void UserLogin() {

        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(ServerResponse);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String name = object.getString("name").trim();
                                    String email = object.getString("email").trim();
                                    String id = object.getString("id").trim();
                                    String mobile=object.getString("mobile").trim();
//                                    String mobile=object.getString("mobile").trim();
//                                    String password=object.getString("password").trim();
                                    prefManager.createSession(name, email, id,mobile);
                                    Toast.makeText(Login.this, "Login Successful ", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("email", email);
                                    startActivity(intent);
                                    finish();


                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            LoginButton.setVisibility(View.VISIBLE);
                            Toast.makeText(Login.this, "Invalid Username and Password ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LoginButton.setVisibility(View.VISIBLE);
                        Toast.makeText(Login.this, "Error " +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("email", EmailHolder);
                params.put("password", PasswordHolder);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }


    public void CheckEditTextIsEmptyOrNot() {

        // Getting values from EditText.
        EmailHolder = Email.getText().toString().trim();
        PasswordHolder = Password.getText().toString().trim();

        // Checking whether EditText value is empty or not.
        if (TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)) {

            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;

        } else {

            // If any of EditText is filled then set variable value as True.
            CheckEditText = true;
        }
    }
}