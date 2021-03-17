package com.mc.mcfirebase.ui.slideshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mc.mcfirebase.Database;
import com.mc.mcfirebase.Model.currentUser;
import com.mc.mcfirebase.Model.foodData;
import com.mc.mcfirebase.R;
import com.mc.mcfirebase.cartViewHolder;
import com.mc.mcfirebase.placeOrderActivity;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.cart_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartAdapter adapter = new cartAdapter();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        ExtendedFloatingActionButton myFab = (ExtendedFloatingActionButton) root.findViewById(R.id.placeOrder);
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),placeOrderActivity.class);
                startActivityForResult(intent,10);
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            List<Pair<foodData, Integer>> foodList = new ArrayList<Pair<foodData, Integer>>();
            Database DB = new Database(getContext(), currentUser.currentUser.getPhone());
            foodList = DB.getCart();
            int i=0;
            foodData food;
            for(i=0;i<foodList.size();i++){
                food = foodList.get(i).first;
                DB.delete(food);
            }

            NavController navController = Navigation.findNavController((Activity) getContext(), R.id.nav_host_fragment);
            navController.navigate(R.id.nav_gallery);

        }
    }

    public class cartAdapter extends RecyclerView.Adapter<cartViewHolder>{
        foodData food;
        int quantity;
        List<Pair<foodData, Integer>> foodList = new ArrayList<Pair<foodData, Integer>>();
        Pair<foodData, Integer> pair ;
        Database DB ;
        TextView foodtext,quanttext;
        public cartAdapter() {
            DB = new Database(getContext(), currentUser.currentUser.getPhone());
            foodList = DB.getCart();
        }
        @NonNull
        @Override
        public cartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.cart_recycler_item,parent,false);

            return new cartViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull cartViewHolder holder, int position) {
            pair = foodList.get(position);
            food = pair.first;
            quantity = pair.second;
            foodtext = (TextView) holder.itemView.findViewById(R.id.cartfood);
            quanttext = (TextView)holder.itemView.findViewById(R.id.cartquant);
            foodtext.setText(food.getName());
            quanttext.setText(String.valueOf(quantity));

        }



        @Override
        public int getItemCount() {
            return foodList.size();
        }
    }
}