package com.mc.mcfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    private static final int TEZ_REQUEST_CODE = 123;

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
                /*
                Intent intent = new Intent();
                Request req = new Request();
                req.setAmount(amount);
                req.setFood(allFood);
                req.setStatus("pending");
                req.setRequestID(currentUser.currentUser.getTokenid());
                intent.putExtra("status","orderPlaced");
                ref = FirebaseDatabase.getInstance().getReference("Requests");
                ref.child(currentUser.currentUser.getTokenid()).setValue(req);*/
//                setResult(10,intent);
//                finish();
                Uri uri =
                        new Uri.Builder()
                                .scheme("upi")
                                .authority("pay")
                                .appendQueryParameter("pa", "gpay-11172772413@okbizaxis")
                                .appendQueryParameter("pn", "Books Corner")
                                .appendQueryParameter("mc", "")
                                .appendQueryParameter("tr", "123456789")
                                .appendQueryParameter("tn", "test transaction note")
                                .appendQueryParameter("am", "1.01")
                                .appendQueryParameter("cu", "INR")

                                .build();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);

                Intent chooser = Intent.createChooser(intent, "Pay with");


                if(null != chooser.resolveActivity(getPackageManager())) {
                    startActivityForResult(chooser, TEZ_REQUEST_CODE);
                } else {
                    Toast.makeText(placeOrderActivity.this,"NO UPI APP FOUND",Toast.LENGTH_SHORT).show();
                }


                Intent myintent = new Intent();
                Request req = new Request();
                req.setAmount(amount);
                req.setFood(allFood);
                req.setStatus("pending");
                req.setRequestID(currentUser.currentUser.getTokenid());
                myintent.putExtra("status","orderPlaced");
                ref = FirebaseDatabase.getInstance().getReference("Requests");
                ref.child(currentUser.currentUser.getTokenid()).setValue(req);
                setResult(10,myintent);
                finish();

            }

        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ArrayList<String> List = new ArrayList<>();
        if (requestCode == TEZ_REQUEST_CODE) {

            if(resultCode == RESULT_OK || resultCode ==11)
            {
                if(data!=null)
                {
                    String result = data.getStringExtra("response");
                    List.add(result);
                }
                else
                {
                    List.add("Nothing");
                }

            }
            else
            {
                List.add("Nothing");
            }
            StartUpiPayment(List);


        }

    }

    private void StartUpiPayment(ArrayList<String> list) {
        if(CheckInternetConnection())
        {
            String data = list.get(0);
            String PaymentDetails = "";
            String status = "";
            String ApprovalRefNo = "";

            if(data == null)
                data = "discard";

            String response[] = data.split("&");

            for(int i=0 ; i<response.length;i++)
            {
                String seperation[] = response[i].split("=");
                if(seperation.length >= 2)
                {
                    if(seperation[0].toLowerCase().equals("Status".toLowerCase()))
                    {
                        status = seperation[1].toLowerCase();
                    }
                    else if(seperation[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || seperation[0].toLowerCase().equals("txnRef".toLowerCase()))
                    {
                        ApprovalRefNo = seperation[1].toLowerCase();
                    }
                }
                else
                {
                    PaymentDetails = "Payment Cancelled By User";
                }
            }

            if (status.equals("success"))
            {
                Toast.makeText(placeOrderActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
            }
            else if("Payment Cancelled By User".equals(PaymentDetails)) {
                Toast.makeText(placeOrderActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(placeOrderActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Internet Not Available",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean CheckInternetConnection()
    {

        Boolean result = false;
        ConnectivityManager connectivityManager =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            result = true;
        }
        return result;
    }
}