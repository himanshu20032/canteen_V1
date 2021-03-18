package com.mc.mcfirebase;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;


public class orderViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public orderViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.orderStatus);
    }
}
