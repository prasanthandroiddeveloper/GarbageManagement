package com.bsss.garbagemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyComplaint extends AppCompatActivity implements ProductsAdapter.OnItemClickListener  {
    public static final String EXTRA_Image="Image";
    public static final String EXTRA_name="name";
    public static final String EXTRA_Description="Description";
    //public static final String EXTRA_Quantity="Quantity";
    public static final String EXTRA_mobile="mobile";
    public static final String EXTRA_email="email";
    public static final String EXTRA_Addr_one="Addr_one";
    public static final String EXTRA_Addr_two="Addr_two";
    public static final String EXTRA_Landmark="Landmark";
    public static final String EXTRA_City="City";
    public static final String EXTRA_Pin="Pin";
    public static final String EXTRA_District="District";
    //public static final String EXTRA_State="State";

    String getId;
    PrefManager prefManager;
    private static final String URL_PRODUCT = "http://bhanusevenhillssoftwareservices.com/bnglr/click_viewer.php";

    List<Product> productList;
    RecyclerView recyclerView;
    ImageView emptyData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_complaint);
        PrefManager prefManager = new PrefManager (this);
        prefManager.checkLogin();
        HashMap<String, String> user = prefManager.getUserDetail();
        getId = user.get(prefManager.ID);
        emptyData=findViewById(R.id.empty);
        recyclerView = findViewById(R.id.recyclerViewer);
        productList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadProducts();

    }

    private void loadProducts() {

        final String id = getId;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);
                            prefManager=new PrefManager(getApplicationContext());



                            for (int i = 0; i < array.length(); i++) {

                                JSONObject product = array.getJSONObject(i);

                                productList.add(new Product(
                                        product.getInt("id"),
                                        product.getString("Image"),
                                        product.getString("name"),
                                        product.getString("Description"),

                                        product.getString("mobile"),
                                        product.getString("email"),
                                        product.getString("Addr_one"),
                                        product.getString("Addr_two"),
                                        product.getString("Landmark"),
                                        product.getString("City"),
                                        product.getString("Pin"),
                                        product.getString("District")


                                ));
                            }


                            ProductsAdapter adapter1 = new ProductsAdapter(MyComplaint.this, productList);
                            recyclerView.setAdapter(adapter1);
                            adapter1.setOnItemClickListener(MyComplaint.this);
                            if (productList.isEmpty())
                            {
                                emptyData.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);

                            }
                            else {
                                recyclerView.setVisibility(View.VISIBLE);
                                emptyData.setVisibility(View.GONE);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);


                return params;
            }
        };








        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent=new Intent(this,Complete_details.class);
        Product clickedItem=productList.get(position);
        detailIntent.putExtra(EXTRA_Image,clickedItem.getImage());
        detailIntent.putExtra(EXTRA_name,clickedItem.getName());
        detailIntent.putExtra(EXTRA_Description,clickedItem.getDescription());
        //detailIntent.putExtra(EXTRA_Quantity,clickedItem.getQuantity());
        detailIntent.putExtra(EXTRA_mobile,clickedItem.getMobile());
        detailIntent.putExtra(EXTRA_email,clickedItem.getEmail());
        detailIntent.putExtra(EXTRA_Addr_one,clickedItem.getAddr_one());
        detailIntent.putExtra(EXTRA_Addr_two,clickedItem.getAddr_two());
        detailIntent.putExtra(EXTRA_Landmark,clickedItem.getLandmark());
        detailIntent.putExtra(EXTRA_City,clickedItem.getCity());
        detailIntent.putExtra(EXTRA_Pin,clickedItem.getPin());
        detailIntent.putExtra(EXTRA_District,clickedItem.getDistrict());
       // detailIntent.putExtra(EXTRA_State,clickedItem.getState());
        startActivity(detailIntent);



    }

    @Override
    public void success(String s) {
        loadProducts();


    }

}
