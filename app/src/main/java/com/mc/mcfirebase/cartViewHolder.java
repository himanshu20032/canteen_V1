package com.mc.mcfirebase;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class cartViewHolder extends RecyclerView.ViewHolder {
    TextView t1,t2;
    public cartViewHolder(@NonNull View itemView) {
        super(itemView);
        t1 = itemView.findViewById(R.id.cartfood);
        t2 = itemView.findViewById(R.id.cartquant);
    }
}
