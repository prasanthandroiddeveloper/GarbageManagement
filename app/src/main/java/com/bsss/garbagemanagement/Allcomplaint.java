package com.bsss.garbagemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Allcomplaint extends AppCompatActivity implements ProductAdapter.OnItemClickListener {
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
    private static final String URL_PRODUCTS = "http://bhanusevenhillssoftwareservices.com/bnglr/click_view.php";

    List<Product> productList;
    RecyclerView recyclerView;
    ImageView emptyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allcomplaint);
        recyclerView = findViewById(R.id.recyclerView);
        emptyData=findViewById(R.id.empty);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();

        loadProducts();
    }
    private void loadProducts() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);


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


                            ProductAdapter adapter = new ProductAdapter(Allcomplaint.this, productList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(Allcomplaint.this);
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
                });


        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent=new Intent(this,Complete_details.class);
        Product clickedItem=productList.get(position);
        detailIntent.putExtra(EXTRA_Image,clickedItem.getImage());
        detailIntent.putExtra(EXTRA_name,clickedItem.getName());
        detailIntent.putExtra(EXTRA_Description,clickedItem.getDescription());
       // detailIntent.putExtra(EXTRA_Quantity,clickedItem.getQuantity());
        detailIntent.putExtra(EXTRA_mobile,clickedItem.getMobile());
        detailIntent.putExtra(EXTRA_email,clickedItem.getEmail());
        detailIntent.putExtra(EXTRA_Addr_one,clickedItem.getAddr_one());
        detailIntent.putExtra(EXTRA_Addr_two,clickedItem.getAddr_two());
        detailIntent.putExtra(EXTRA_Landmark,clickedItem.getLandmark());
        detailIntent.putExtra(EXTRA_City,clickedItem.getCity());
        detailIntent.putExtra(EXTRA_Pin,clickedItem.getPin());
        detailIntent.putExtra(EXTRA_District,clickedItem.getDistrict());
        //detailIntent.putExtra(EXTRA_State,clickedItem.getState());
        startActivity(detailIntent);
    }
}