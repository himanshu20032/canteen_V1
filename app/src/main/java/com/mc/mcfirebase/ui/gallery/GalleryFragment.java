package com.mc.mcfirebase.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mc.mcfirebase.Request;
import com.mc.mcfirebase.Model.currentUser;
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
import com.mc.mcfirebase.Request;
import com.mc.mcfirebase.foodDetailActivity;
import com.mc.mcfirebase.himViewHolder;
import com.mc.mcfirebase.orderViewHolder;
import com.squareup.picasso.Picasso;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FirebaseRecyclerAdapter<Request, orderViewHolder> adapter;
    private RecyclerView recyclerView;
    private DatabaseReference ref;
    FirebaseRecyclerOptions<Request> options;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.orderRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ref = FirebaseDatabase.getInstance().getReference("Requests");
        options = new FirebaseRecyclerOptions.Builder<Request>()
                .setQuery(ref, Request.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Request, orderViewHolder>(options){
            public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
                return new orderViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull orderViewHolder holder, int position, @NonNull Request model) {
                holder.textView.setText(model.getRequestID());
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter) ;
        adapter.notifyDataSetChanged();
        return root;
    }
}