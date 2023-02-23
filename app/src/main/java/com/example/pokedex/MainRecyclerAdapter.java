package com.example.pokedex;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainViewHolder>{
    List<String> dados;
    List<String> imagens;
    private Context context;


    public MainRecyclerAdapter(List<String> dados, List<String> imagens, Context context) {
        this.dados = dados;
        this.imagens = imagens;
        this.context = context;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_main_item, parent, false);
        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainViewHolder holder, int position) {

        holder.setText(dados.get(position));

        byte[] decodedString = Base64.decode(imagens.get(position), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.setImage(decodedByte);
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
