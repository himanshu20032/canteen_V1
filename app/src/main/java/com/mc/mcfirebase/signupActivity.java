package com.mc.mcfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mc.mcfirebase.Model.userData;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class signupActivity extends AppCompatActivity {
    Button buttonsignup;
    TextInputLayout editphone,editpassword,editroll,editname,editemail;
    public FirebaseAuth firebaseAuth;

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
        firebaseAuth = FirebaseAuth.getInstance();

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
                Log.e("error1",newuser.getEmail());
                if(firebaseAuth == null)
                Log.e("error2",newuser.getPassword());



                createnewuser(newuser.getEmail(),newuser.getPassword(),phone,newuser.getName());


                /*
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
                });*/

            }
        });

    }

    private void createnewuser(String email1, String pass1,String phone,String Name) {


        firebaseAuth.createUserWithEmailAndPassword(email1,pass1).addOnCompleteListener(signupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {

                    Toast.makeText(signupActivity.this,"register success",Toast.LENGTH_SHORT).show();
                    HashMap<String,Object> data = new HashMap<>();
                    data.put("Name",Name);
                    data.put("Email",email1);
                    data.put("Phone Number",phone);
                    data.put("Password",pass1);
                    FirebaseDatabase.getInstance().getReference().child("SignUp Database").child(task.getResult().getUser().getUid()).updateChildren(data);
                    startActivity(new Intent(signupActivity.this,signinActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(signupActivity.this,"register unsuccessful",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}