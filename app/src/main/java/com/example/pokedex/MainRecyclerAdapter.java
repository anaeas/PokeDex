package com.example.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainViewHolder>{
    List<String> dados;
    private Context context;


    public MainRecyclerAdapter(List<String> dados, Context context) {
        this.dados = dados;
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
        holder.setImage(R.mipmap.ic_launcher);
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
