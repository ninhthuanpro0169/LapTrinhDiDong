package com.example.travelgood.adapter;

import android.content.Context;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelgood.R;
import com.example.travelgood.model.Loaidl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaidlAdapter extends BaseAdapter {
    ArrayList<Loaidl> arraylistloaidl;
    Context context;

    public LoaidlAdapter(ArrayList<Loaidl> arraylistloaidl, Context context) {
        this.arraylistloaidl = arraylistloaidl;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arraylistloaidl.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylistloaidl.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public  class ViewHolder{
        TextView txttenloaidl;
        ImageView imgloaidl;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_loaidl, null);
            viewHolder.txttenloaidl = view.findViewById(R.id.textviewloaidl);
            viewHolder.imgloaidl = view.findViewById(R.id.imageviewloaidl);
            view.setTag(viewHolder);
        } else {
            viewHolder  = (ViewHolder) view.getTag();
        }
        Loaidl loaidl = (Loaidl) getItem(i);
        viewHolder.txttenloaidl.setText(loaidl.getTenLoaidl());
        Picasso.with(context).load(loaidl.getHinhanhloaidl())
                .placeholder(R.drawable.load)
                .error(R.drawable.loi)
                .into(viewHolder.imgloaidl);
        return view;
    }
}
