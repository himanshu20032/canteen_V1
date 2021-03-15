package com.mc.mcfirebase;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.textview.MaterialTextView;
import com.mc.mcfirebase.Model.currentUser;


public class profileFragment extends Fragment {
    MaterialTextView username,email,phone,roll,pending;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        username = view.findViewById(R.id.username);
        email = view.findViewById(R.id._Email);
        phone = view.findViewById(R.id._Phonenumber);
        roll = view.findViewById(R.id._Rollnumber);

        if(currentUser.currentUser.getName()!=null)
            username.setText(currentUser.currentUser.getName());

        if(currentUser.currentUser.getRoll()!=null)
            roll.setText(currentUser.currentUser.getRoll());

        if(currentUser.currentUser.getEmail()!=null)
            email.setText(currentUser.currentUser.getEmail());

        if(currentUser.currentUser.getPhone()!=null)
            phone.setText(currentUser.currentUser.getPhone());

        return view;
    }
}