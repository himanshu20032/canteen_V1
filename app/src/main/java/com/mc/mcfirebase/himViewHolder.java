package com.mc.mcfirebase;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class himViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
    public  ImageView imageView;
    public  TextView textView;
    public himViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.foodImage);
        textView = itemView.findViewById(R.id.foodName);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}
