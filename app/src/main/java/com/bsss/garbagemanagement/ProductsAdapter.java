package com.bsss.garbagemanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


    public class ProductsAdapter  extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>  {
        private Context mCtx;
        private List<Product> productList;
        private ProductsAdapter.OnItemClickListener mListener;
        ProgressDialog progressDialog;
        public ProductsAdapter(Context mCtx, List<Product> productList) {
            this.mCtx = mCtx;
            this.productList = productList;

        }

        public void setOnItemClickListener(ProductsAdapter.OnItemClickListener listener) {

            mListener=listener;

        }



        public interface OnItemClickListener{
            void onItemClick(int position);

            void success(String s);
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.lists_posts,null);
            return new ProductViewHolder(view);

        }
        @Override
        public void onBindViewHolder(@NonNull final ProductViewHolder holder,final int position) {
            final Product product = productList.get(position);

            //loading the image
            Glide.with(mCtx)
                    .load(product.getImage())
                    .into(holder.imageView);

            holder.textViewTitle.setText(product.getName());
            holder.textViewShortDesc.setText(product.getDescription());
            holder.textViewPrice.setText(product.getAddr_two());
            holder.Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    delete(product.getId());

                }
            });

        }

        private void delete(final int id) {

            progressDialog=new ProgressDialog(mCtx);
            progressDialog.setMessage("Loading");
            progressDialog.show();

            final String URL_DELETE = "http://bhanusevenhillssoftwareservices.com/bnglr/Api.php";
            StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_DELETE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    try
                    {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("message");
                        if (success.equals("Post Deleted Successfully!")) {

                            Toast.makeText(mCtx, "Deleted Successfully ", Toast.LENGTH_SHORT).show();
                            mCtx.startActivity(new Intent(mCtx,MainActivity.class));

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

                            Log.d("Error", String.valueOf(error));

                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", String.valueOf(id));

                    return params;
                }
            };

            Volley.newRequestQueue(mCtx).add(stringRequest);

        }



        @Override
        public int getItemCount() {
            return productList.size();
        }


        class ProductViewHolder extends RecyclerView.ViewHolder {

            TextView textViewTitle,textViewShortDesc,textViewPrice;
            ImageView imageView,Delete;

            public ProductViewHolder(@NonNull final View itemView) {
                super(itemView);

                textViewTitle = itemView.findViewById(R.id.textViewTitle1);
                textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc1);
                textViewPrice = itemView.findViewById(R.id.textViewPrice1);
                imageView = itemView.findViewById(R.id.imageView1);
                Delete=itemView.findViewById(R.id.delete);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null){
                            int position=getAdapterPosition();
                            if (position !=RecyclerView.NO_POSITION){
                                mListener.onItemClick(position);
                            }
                        }

                    }
                });


            }
        }

    }
