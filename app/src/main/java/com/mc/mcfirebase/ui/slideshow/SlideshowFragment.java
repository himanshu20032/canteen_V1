package com.mc.mcfirebase.ui.slideshow;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mc.mcfirebase.Database;
import com.mc.mcfirebase.Model.currentUser;
import com.mc.mcfirebase.Model.foodData;
import com.mc.mcfirebase.R;
import com.mc.mcfirebase.cartViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.cart_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartAdapter adapter = new cartAdapter();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return root;
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