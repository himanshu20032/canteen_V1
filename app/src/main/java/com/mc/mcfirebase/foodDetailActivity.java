package com.mc.mcfirebase;
import com.mc.mcfirebase.Model.currentUser;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mc.mcfirebase.Model.foodData;
import com.squareup.picasso.Picasso;

import static java.lang.Integer.parseInt;

public class foodDetailActivity extends AppCompatActivity {
    String foodID,image,title,desc,price;
    TextView foodtitle,fooddesc;
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
        fooddesc = findViewById(R.id.desc);
        String foodName,foodImage,foodDescription;
        foodTable.child(foodID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodData data = snapshot.getValue(foodData.class);
                image = data.getImage();
                title = data.getName();
                desc = data.getDesc();
                price = data.getPrice();
                Log.e("AWWWW",image);
                foodtitle.setText(title);
                fooddesc.setText(desc);
                Picasso.with(foodDetailActivity.this).load(image).into(imageview);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(foodDetailActivity.this,"SOME ERROR OCCURED",Toast.LENGTH_LONG).show();
            }
        });

        Button add = (Button) findViewById(R.id.add);
        Button delete = (Button) findViewById(R.id.delete);
        Button addtocart = (Button) findViewById(R.id.addToCart);
        TextView quantity = (TextView) findViewById(R.id.quant);
        Database DB =  new Database(this,currentUser.currentUser.getPhone());

        Log.e("add",currentUser.currentUser.getPhone());
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int val = Integer.parseInt(quantity.getText().toString()) + 1;
                quantity.setText(String.valueOf(val));
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int val = Integer.parseInt(quantity.getText().toString()) - 1;
                if(val < 0) {
                    val = 0;
                }
                quantity.setText(String.valueOf(val));
            }
        });

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodData temp = DB.getOne(foodID);
                if(temp == null)
                    DB.addToCart(new foodData(image,title,desc,foodID,price), Integer.parseInt(quantity.getText().toString()));
                else
                    DB.update(new foodData(image,title,desc,foodID,price), Integer.parseInt(temp.getPrice()) + Integer.parseInt(quantity.getText().toString()));
            }
        });






    }
}