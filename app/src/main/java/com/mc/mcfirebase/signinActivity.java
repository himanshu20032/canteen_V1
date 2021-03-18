package com.mc.mcfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
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
    Button signin,signup;
    private SignInButton googleSignInButton;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private int RC_SIGN_IN =9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        editphone =  findViewById(R.id.editPhone);
        editpassword =  findViewById(R.id.editPassword);
        signin = (Button) findViewById(R.id.signIn);
        signup = (Button) findViewById(R.id.signUp);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signinActivity.this,signupActivity.class));
                finish();
            }
        });
        //firebase database

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference user_table = database.getReference("User");
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null)
        {
            Log.e("error------------------",firebaseAuth.getCurrentUser().getPhoneNumber());
            setCurrentUserInfo(user_table,firebaseAuth.getCurrentUser().getUid());
            // startActivity(new Intent(signinActivity.this,MenuActivity.class));
            // finish();
        }

        ///////////////////////////////////earlier////////////////////////////////////////////////
        /*
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

        */

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_text = editphone.getEditText().getText().toString();
                String password =editpassword.getEditText().getText().toString();


                if(TextUtils.isEmpty(email_text))
                {
                    editphone.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    editpassword.setError("Password is required");

                }
                else
                {/*
                    user_table.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.child(editphone.getEditText().getText().toString()).exists()){
                                userData user = snapshot.child(editphone.getEditText().getText().toString()).getValue(userData.class);
                                Log.e("data received",snapshot.child(editphone.getEditText().getText().toString()).getValue().toString());

                                if (user.getPassword().equals(editpassword.getEditText().getText().toString())) {
                                    //Toast.makeText(signinActivity.this, "SIGN IN SUCCESS", Toast.LENGTH_LONG).show();

                                    currentUser.currentUser = user;


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
                    });*/
                    login(email_text,password);
                }
            }
        });


        //Google sign IN Started

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this,gso);
    /*
        googleSignInButton = findViewById(R.id.gmailSignin);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIN();
                Log.d("HI","1");
            }
        });


        */



    }

    private void SignIN() {

        Intent intent = googleSignInClient.getSignInIntent();
        Log.d("HI","2");
        startActivityForResult(intent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("HI","hello");
        Log.d("HI",requestCode+"hello");

        Log.d("HI",data+"hello");
        if(requestCode == RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Log.d("HI","3");
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {

        Log.d("HI","hdjs");
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Log.d("HI","4");
//            Toast.makeText(signin.this,"Sign In Successfully",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(account);
        }
        catch (ApiException e)
        {
            Log.w("HI", "Google sign in failed", e);
//
//            Toast.makeText(signin.this,"Sign In Failed",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);

        }

    }

    private void FirebaseGoogleAuth(GoogleSignInAccount account) {

        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(signinActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(signinActivity.this,"Successful",Toast.LENGTH_SHORT).show();
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    updateUI(firebaseUser);
                }
                else
                {
//                    Toast.makeText(signin.this,"UnSuccessful",Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }

            }
        });

    }

    private void updateUI(FirebaseUser user) {

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            Toast.makeText(signinActivity.this,personName+""+personEmail,Toast.LENGTH_SHORT).show();

            Intent i =new Intent(signinActivity.this,MenuActivity.class);
            startActivity(i);
        }
    }

    private void login(String email_text, String password) {

        firebaseAuth.signInWithEmailAndPassword(email_text,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                Toast.makeText(signinActivity.this,"login success",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(signinActivity.this,MenuActivity.class));
            }
        });
    }

    private void setCurrentUserInfo(DatabaseReference user_table,String tokenID){
        user_table.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child(tokenID).exists()){
                    userData user = snapshot.child(tokenID).getValue(userData.class);
                    Log.e("data received",user.getEmail());
                    currentUser.currentUser = user;
                    startActivity(new Intent(signinActivity.this,MenuActivity.class));
                    finish();
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
}