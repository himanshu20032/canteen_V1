package com.mc.mcfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mc.mcfirebase.Model.foodData;
import com.squareup.picasso.Picasso;

public class foodDetailActivity extends AppCompatActivity {
    String foodID,image,title;
    TextView foodtitle;
    ImageView imageview;
    FirebaseDatabase database;
    DatabaseReference foodTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        Intent intent = getIntent();
        foodID = intent.getStringExtra("foodID");
        database = FirebaseDatabase.getInstance();
        foodTable = database.getReference("Category");

        foodtitle = findViewById(R.id.foodTitle);
        imageview = (ImageView)findViewById(R.id.foodImage);

        String foodName,foodImage,foodDescription;
        foodTable.child(foodID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodData data = snapshot.getValue(foodData.class);
                image = data.getImage();
                title = data.getName();
                Log.e("AWWWW",image);
                foodtitle.setText(title);
                Picasso.with(foodDetailActivity.this).load(image).into(imageview);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}