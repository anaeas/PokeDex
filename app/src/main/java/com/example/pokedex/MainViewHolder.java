package com.example.pokedex;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MainViewHolder extends RecyclerView.ViewHolder {
    TextView textView;
    ImageView imageView;

    public MainViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView);
        imageView = itemView.findViewById(R.id.imageView);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setImage(int image) {
        imageView.setImageResource(image);
    }
}
