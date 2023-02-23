package com.example.pokedex;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView textView;
    ImageView imageView;

    public MainViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        textView = itemView.findViewById(R.id.textView);
        imageView = itemView.findViewById(R.id.imageView);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setImage(Bitmap image) {
        imageView.setImageBitmap(image);
    }

    public void setId(int id) {
        itemView.setId(id);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "position = " + getLayoutPosition() + "id:"+itemView.getId(), Toast.LENGTH_SHORT).show();

        Intent it = new Intent(view.getContext(), Detalhes.class);
        it.putExtra("id", ""+itemView.getId());
        view.getContext().startActivity(it);

        /** Go through each item if you have few items within RecyclerView. */
        if (getLayoutPosition() == 0) {
            // Do whatever you want here
        } else if(getLayoutPosition() == 1) {
            // Do whatever you want here
        } else if(getLayoutPosition() == 2) {
            // Do whatever you want here
        }

//        /** Or you can use For loop if you have long list of items. */
//        for (int i = 0; i < exampleList.size(); i++) {
//            // Do whatever you want here
//        }
    }

}
