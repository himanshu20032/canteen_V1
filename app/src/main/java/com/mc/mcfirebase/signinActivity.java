package com.mc.mcfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mc.mcfirebase.Model.currentUser;
import com.mc.mcfirebase.Model.userData;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Currency;

public class signinActivity extends AppCompatActivity {
    TextInputLayout editphone,editpassword;
    Button signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        editphone =  findViewById(R.id.editPhone);
        editpassword =  findViewById(R.id.editPassword);
        signin = (Button) findViewById(R.id.signIn);

        //firebase database

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference user_table = database.getReference("User");

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(signinActivity.this,"INSIDE SIGNIN",Toast.LENGTH_LONG);
                user_table.addValueEventListener(new ValueEventListener() {

                    //
                    //ProgressDialog dialog = new ProgressDialog(signinActivity.this);
                    //dialog.setMessage("WAIT...");
                    //dialog.show();
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //userData user = snapshot.child(editphone.getText().toString()).getValue(userData.class);
                        //userData user = snapshot.child(editphone.getText().toString()).getValue(userData.class);


                        if(snapshot.child(editphone.getEditText().getText().toString()).exists()){
                            userData user = snapshot.child(editphone.getEditText().getText().toString()).getValue(userData.class);
                            Log.e("data received",snapshot.child(editphone.getEditText().getText().toString()).getValue().toString());

                            if (user.getPassword().equals(editpassword.getEditText().getText().toString())) {
                                //Toast.makeText(signinActivity.this, "SIGN IN SUCCESS", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(signinActivity.this,MenuActivity.class);
                                currentUser.currentUser = user;

                                startActivity(intent);
                                finish();
                            } else {
                            Toast.makeText(signinActivity.this, "UNSUCCESSFUL", Toast.LENGTH_LONG).show();
                        }
                        }
                        else{
                            Toast.makeText(signinActivity.this,"User not exist",Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(signinActivity.this,"ERROR",Toast.LENGTH_LONG);
                    }
                });
            }
        });


        //
        Button signup = (Button) findViewById(R.id.signUp);
        signup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signinActivity.this,signupActivity.class);
                startActivity(intent);
            }
        });
     }
}