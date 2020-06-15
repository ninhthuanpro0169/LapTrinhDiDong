package com.example.travelgood.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelgood.R;
import com.example.travelgood.activity.ChiTietdulich;
import com.example.travelgood.model.Dulich;
import com.example.travelgood.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DulichAdapter extends RecyclerView.Adapter<DulichAdapter.ItemHolder> {
    Context context;
    ArrayList<Dulich> arraydulich;

    public DulichAdapter(Context context, ArrayList<Dulich> arraydulich) {
        this.context = context;
        this.arraydulich = arraydulich;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_diadiemhot,null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Dulich dulich = arraydulich.get(position);
        holder.txttendulich.setText(dulich.getTendiadiem());
        holder.txtdiachi.setText(dulich.getDiachi());
        Picasso.with(context).load(dulich.getHinhanhdiadiemdulich())
                .placeholder(R.drawable.load)
                .error(R.drawable.loi)
                .into(holder.imghinhdulich);

    }

    @Override
    public int getItemCount() {

        return arraydulich.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhdulich;
        public TextView txttendulich , txtdiachi;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imghinhdulich = itemView.findViewById(R.id.imageviewdulich);
            txttendulich = itemView.findViewById(R.id.textviewtendiadiem);
            txtdiachi = itemView.findViewById(R.id.textviewdiachi);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietdulich.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("thongtindulich",arraydulich.get(getAdapterPosition()));
                    CheckConnection.ShowToast_Short(context,arraydulich.get(getAdapterPosition()).getTendiadiem());
                    context.startActivity(intent);
                }
            });
        }
    }
}
