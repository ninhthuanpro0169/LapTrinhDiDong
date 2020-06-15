package com.example.travelgood.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelgood.R;
import com.example.travelgood.model.Dulich;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DulichkhamphaAdapter extends BaseAdapter {
    Context context;
    ArrayList<Dulich> arraykhampha;

    public DulichkhamphaAdapter(Context context, ArrayList<Dulich> arraykhampha) {
        this.context = context;
        this.arraykhampha = arraykhampha;
    }

    @Override
    public int getCount() {
        return arraykhampha.size();
    }

    @Override
    public Object getItem(int i) {
        return arraykhampha.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public  class ViewHolder{
        public TextView txttenkhampha, txtdiachikhampha , txtmotakhampha;
        public ImageView imgkhampha;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DulichkhamphaAdapter.ViewHolder viewHolder = null;
        if ( view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dong_khampha,null);
            viewHolder.txttenkhampha = view.findViewById(R.id.textviewtenkhampha);
            viewHolder.txtdiachikhampha = view.findViewById(R.id.textviewdiachikhampha);
            viewHolder.txtmotakhampha = view.findViewById(R.id.textviewmotakhampha);
            viewHolder.imgkhampha = view.findViewById(R.id.imageviewkhampha);
            view.setTag(viewHolder);
        }else {
            viewHolder = (DulichkhamphaAdapter.ViewHolder) view.getTag();
        }
        Dulich dulich = (Dulich) getItem(i);
        viewHolder.txttenkhampha.setText(dulich.getTendiadiem());
        viewHolder.txtdiachikhampha.setText(dulich.getDiachi());
        viewHolder.txtmotakhampha.setMaxLines(2);
        viewHolder.txtmotakhampha.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotakhampha.setText(dulich.getMotadiadiem());
        Picasso.with(context).load(dulich.getHinhanhdiadiemdulich())
                .placeholder(R.drawable.load)
                .error(R.drawable.loi)
                .into(viewHolder.imgkhampha);

        return view;
    }
}

