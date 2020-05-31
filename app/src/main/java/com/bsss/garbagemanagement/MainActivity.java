package com.bsss.garbagemanagement;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    EditText Add1, Add2, city, pin, dis, descr, Land;
      Button up, sub;
    com.bsss.garbagemanagement.PrefManager prefManager;
    String getId;
    private int PICK_IMAGE_REQUEST = 2;
    private int REQUEST_IMAGE_CAPTURE = 1;
    private int RequestPermissionCode=3;
    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    String image;
    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;
    private ImageView imageView;
    TextView Tvpath;
    String HttpUrl = "http://bhanusevenhillssoftwareservices.com/bnglr/insert_prblm.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        prefManager = new PrefManager(MainActivity.this);
        HashMap<String, String> user = prefManager.getUserDetail();
        com.bsss.garbagemanagement.PrefManager prefManager = new PrefManager(this);
        prefManager.checkLogin();
        Add1 = findViewById(R.id.Add1);
        Add2 = findViewById(R.id.Add2);
        city = findViewById(R.id.city);
        pin = findViewById(R.id.Pin);
        dis = findViewById(R.id.Dis);
        Land = findViewById(R.id.land);
        descr = findViewById(R.id.Desc);
        imageView = findViewById(R.id.imageView);
        up = findViewById(R.id.btnUpload);
        sub = findViewById(R.id.btnsub);
        getId = user.get(prefManager.ID);
        EnableRuntimePermission();
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }

        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prblmsub();

            }
        });


    }

    private void EnableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.CAMERA))
        {

            Toast.makeText(MainActivity.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }
    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Bitmap lastBitmap = null;
            lastBitmap = bitmap;
            //encoding image to string
            image = getStringImage(lastBitmap);
            Log.d("image", image);
            imageView.setImageBitmap(bitmap);


        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri filePath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Bitmap lastBitmap = null;
                lastBitmap = bitmap;
                //encoding image to string
                image = getStringImage(lastBitmap);
                Log.d("image", image);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void prblmsub() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final String Address1 = Add1.getText().toString().trim();
        final String Address2 = Add2.getText().toString().trim();
        final String cit = city.getText().toString().trim();
        final String Landmark = Land.getText().toString().trim();
        final String code = pin.getText().toString().trim();
        final String Dis = dis.getText().toString().trim();
               final String Description = descr.getText().toString().trim();

        final String id = getId;
        final String images = image;


        if (Address1.length() == 0 ||  Landmark.length() == 0 || Address2.length() == 0 || cit.length() == 0 || code.length() == 0 || Dis.length() == 0 || Description.length() == 0 ) {
            Toast.makeText(getApplicationContext(), "Please enter your details", Toast.LENGTH_SHORT).show();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("1")) {
                        Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                        prefManager.InsertData(Address1, Address2, id, cit, code, Dis, Description);
                        Intent intent = new Intent(MainActivity.this, Allcomplaint.class);
                        startActivity(intent);

                        Add1.setText("");
                        Add2.setText("");
                        city.setText("");
                        pin.setText("");
                        dis.setText("");
                        descr.setText("");
                        Land.setText("");
                        imageView.setImageBitmap(null);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();

            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());

                if (images != null) {
                    params.put("Add1", Address1);
                    params.put("Add2", Address2);
                    params.put("id", id);
                    params.put("Land", Landmark);
                    params.put("City", cit);
                    params.put("Code", code);
                    params.put("District", Dis);
                    params.put("descri", Description);
                    params.put("photo", images);
                    //params.put("Date", timeStamp);

                }


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:

                return true;
            case R.id.nav_gallery:

                return true;
            case R.id.nav_slideshow:

                return true;
            case R.id.nav_tools:

                return true;
            case R.id.nav_share:

                return true;

            case R.id.nav_send:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        int id = item.getItemId();
        if (id == R.id.nav_home) {

            startActivity(new Intent(MainActivity.this, MainActivity.class));

        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(MainActivity.this, Allcomplaint.class));
        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(MainActivity.this, MyComplaint.class));

        } else if (id == R.id.nav_tools) {
            startActivity(new Intent(MainActivity.this, MyProfile.class));
        } else if (id == R.id.nav_share) {
            Intent shareintent = new Intent();
            shareintent.setAction(Intent.ACTION_SEND);
            shareintent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=\" + \"com.bsss.helpinghands");
            shareintent.setType("text/plain");
            startActivity(Intent.createChooser(shareintent, "share via"));
        } else if (id == R.id.nav_send) {
            logout();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        prefManager.logout();
    }
}
