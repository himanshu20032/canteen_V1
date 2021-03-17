package com.mc.mcfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mc.mcfirebase.Model.currentUser;
import com.mc.mcfirebase.Model.foodData;

import java.util.ArrayList;
import java.util.List;

public class placeOrderActivity extends AppCompatActivity {
    private String amount;
    private DatabaseReference ref;
    Database DB ;
    String allFood = " ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        Intent intent = getIntent();
        //List<foodData> allFood = new ArrayList<foodData>();
        foodData temp;

        Database DB = new Database(placeOrderActivity.this, currentUser.currentUser.getPhone());
        List<Pair<foodData, Integer>> foodList = DB.getCart();
        int i=0;
        foodData food;
        for(i=0;i<foodList.size();i++){
            temp = foodList.get(i).first;
            allFood = allFood.concat(temp.getId()+" ");

        }

        amount = String.valueOf(intent.getIntExtra("amount",0));
        TextView textView = (TextView) findViewById(R.id.amount);
        textView.setText(amount);
        ExtendedFloatingActionButton myFab = (ExtendedFloatingActionButton) findViewById(R.id.placeOrder);
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Request req = new Request();
                req.setAmount(amount);
                req.setFood(allFood);
                req.setStatus("pending");
                req.setRequestID(currentUser.currentUser.getTokenid());
                intent.putExtra("status","orderPlaced");
                ref = FirebaseDatabase.getInstance().getReference("Requests");
                ref.child(currentUser.currentUser.getTokenid()).setValue(req);
                setResult(10,intent);
                finish();
            }
        });
    }
}