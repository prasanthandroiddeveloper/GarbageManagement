package com.bsss.garbagemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static com.bsss.garbagemanagement.Allcomplaint.EXTRA_Addr_one;
import static com.bsss.garbagemanagement.Allcomplaint.EXTRA_Addr_two;
import static com.bsss.garbagemanagement.Allcomplaint.EXTRA_City;
import static com.bsss.garbagemanagement.Allcomplaint.EXTRA_Description;
import static com.bsss.garbagemanagement.Allcomplaint.EXTRA_District;
import static com.bsss.garbagemanagement.Allcomplaint.EXTRA_Image;
import static com.bsss.garbagemanagement.Allcomplaint.EXTRA_Landmark;
import static com.bsss.garbagemanagement.Allcomplaint.EXTRA_Pin;
import static com.bsss.garbagemanagement.Allcomplaint.EXTRA_email;
import static com.bsss.garbagemanagement.Allcomplaint.EXTRA_mobile;
import static com.bsss.garbagemanagement.Allcomplaint.EXTRA_name;

public class Complete_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_details);
        Intent intent=getIntent();
        String image=intent.getStringExtra(EXTRA_Image);
        String name=intent.getStringExtra(EXTRA_name);
        String description=intent.getStringExtra(EXTRA_Description);
        //String quantity=intent.getStringExtra(EXTRA_Quantity);
        String mobile=intent.getStringExtra(EXTRA_mobile);
        String email=intent.getStringExtra(EXTRA_email);
        String addr1=intent.getStringExtra(EXTRA_Addr_one);
        String addr2=intent.getStringExtra(EXTRA_Addr_two);
        String land=intent.getStringExtra(EXTRA_Landmark);
        String city=intent.getStringExtra(EXTRA_City);
        String pin=intent.getStringExtra(EXTRA_Pin);
        String dis=intent.getStringExtra(EXTRA_District);
       // String state=intent.getStringExtra(EXTRA_State);

        ImageView images=findViewById(R.id.fetch);
        TextView des=findViewById(R.id.wwh);
       // TextView quan=findViewById(R.id.Quanwe);
        TextView address1=findViewById(R.id.addr1);
        TextView address2=findViewById(R.id.str);
        TextView Landmark=findViewById(R.id.mark);
        TextView City=findViewById(R.id.tpt);
        TextView Pin=findViewById(R.id.code);
        TextView District=findViewById(R.id.distr);
       // TextView State=findViewById(R.id.ap);
        TextView Name=findViewById(R.id.namer);
        TextView Mobile=findViewById(R.id.phnnum);
        TextView Email=findViewById(R.id.emailat);

        Glide.with(this)
                .load(image)
                .into(images);
        des.setText(description);
        //quan.setText(quantity);
        address1.setText(addr1);
        address2.setText(addr2);
        Landmark.setText(land);
        City.setText(city);
        Pin.setText(pin);
        District.setText(dis);
        //State.setText(state);
        Name.setText(name);
        Mobile.setText(mobile);
        Email.setText(email);


    }
}
