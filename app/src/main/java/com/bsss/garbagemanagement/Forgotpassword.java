package com.bsss.garbagemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Forgotpassword extends AppCompatActivity {
    EditText Mobile,pass,cpass;
    Button submit;
    TextView RegHere;

    String HttpUrl = "http://bhanusevenhillssoftwareservices.com/bnglr/frgt.php";
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        Mobile=findViewById(R.id.editText_Number);
        pass=findViewById(R.id.editText_Password);
        cpass=findViewById(R.id.editText_Password2);
        submit=findViewById(R.id.button_login);
        RegHere=findViewById(R.id.btnLoginRegister);
        progressDialog = new ProgressDialog(Forgotpassword.this);
        RegHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Forgotpassword.this, activity_register.class);
                startActivity(intent);


            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Forgot();

            }
        });

    }

    private void Forgot() {

        final String Namer = Mobile.getText().toString().trim();
        final String Password=pass.getText().toString().trim();
        String Cpassword=cpass.getText().toString().trim();
        if (Namer.length() == 0 || Password.length() == 0 || Cpassword.length() ==0) {
            Toast.makeText(getApplicationContext(), "Please enter your details", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!Password.equals(Cpassword)) {
            Toast.makeText(getApplicationContext(), "Password Not Matched", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, HttpUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("message");
                    if (success.equals("Password Updated Successfully!")) {

                        Toast.makeText(Forgotpassword.this, "Password Updated Successfully ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Forgotpassword.this, Login.class));


                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();


                }
            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(Forgotpassword.this, "Error " +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile",Namer);
                params.put("password",Password);


                return params;
            }
        };

        Volley.newRequestQueue(Forgotpassword.this).add(stringRequest);

    }


}
