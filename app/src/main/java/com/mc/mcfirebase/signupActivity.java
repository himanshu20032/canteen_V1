package com.mc.mcfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.mc.mcfirebase.Model.userData;
import com.rengwuxian.materialedittext.MaterialEditText;

public class signupActivity extends AppCompatActivity {
    Button buttonsignup;
    TextInputLayout editphone,editpassword,editroll,editname,editemail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference user_table = database.getReference("User");

        editphone = findViewById(R.id.editPhone);
        editpassword = findViewById(R.id.editPassword);
        editroll = findViewById(R.id.editRoll);
        editname = findViewById(R.id.editName);
        editemail = findViewById(R.id.editEmail);


        buttonsignup = (Button) findViewById(R.id.submit);
        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userData newuser = new userData();
                String phone = editphone.getEditText().getText().toString();
                newuser.setName(editname.getEditText().getText().toString());
                newuser.setPassword(editpassword.getEditText().getText().toString());
                newuser.setRoll(editpassword.getEditText().getText().toString());
                newuser.setEmail(editemail.getEditText().getText().toString());
                newuser.setPhone(editphone.getEditText().getText().toString());

                user_table.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(phone).exists()){
                            Log.e("error","ok");
                            Toast.makeText(signupActivity.this,"USER ALREADY EXIST",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            //snapshot not used while adding values
                            user_table.child(phone).setValue(newuser);
                            Toast.makeText(signupActivity.this,"USER REGISTERED",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(signupActivity.this,"SOME ERROR OCCURED",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }
}