package com.mc.mcfirebase.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mc.mcfirebase.foodDetailActivity;
import com.mc.mcfirebase.himViewHolder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mc.mcfirebase.Model.foodData;
import com.mc.mcfirebase.R;
import com.mc.mcfirebase.himViewHolder;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FirebaseRecyclerAdapter<foodData, himViewHolder> adapter;
    private RecyclerView recyclerView;
    private DatabaseReference ref;
    FirebaseRecyclerOptions<foodData> options;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ref = FirebaseDatabase.getInstance().getReference("Category");

        options = new FirebaseRecyclerOptions.Builder<foodData>()
                .setQuery(ref, foodData.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<foodData, himViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull himViewHolder holder, int position, @NonNull foodData model) {
                holder.textView.setText(model.getName());
                Picasso.with(getContext()).load(model.getImage()).into(holder.imageView);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(),foodDetailActivity.class);
                        intent.putExtra("foodID",adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });


            }

            @NonNull
            @Override
            public himViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);

                return new himViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter) ;
        /*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }
}