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

public class DulichsinhthaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<Dulich> arraysinhthai;

    public DulichsinhthaiAdapter(Context context, ArrayList<Dulich> arraysinhthai) {
        this.context = context;
        this.arraysinhthai = arraysinhthai;
    }

    @Override
    public int getCount() {
        return arraysinhthai.size();
    }

    @Override
    public Object getItem(int i) {
        return arraysinhthai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public  class ViewHolder{
        public TextView txttensinhthai, txtdiachisinhthai , txtmotasinhthai;
        public ImageView imgsinhthai;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DulichsinhthaiAdapter.ViewHolder viewHolder = null;
        if ( view == null){
            viewHolder = new DulichsinhthaiAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dong_sinhthai,null);
            viewHolder.txttensinhthai = view.findViewById(R.id.textviewtensinhthai);
            viewHolder.txtdiachisinhthai = view.findViewById(R.id.textviewdiachisinhthai);
            viewHolder.txtmotasinhthai = view.findViewById(R.id.textviewmotasinhthai);
            viewHolder.imgsinhthai = view.findViewById(R.id.imageviewsinhthai);
            view.setTag(viewHolder);
        }else {
            viewHolder = (DulichsinhthaiAdapter.ViewHolder) view.getTag();
        }
        Dulich dulich = (Dulich) getItem(i);
        viewHolder.txttensinhthai.setText(dulich.getTendiadiem());
        viewHolder.txtdiachisinhthai.setText(dulich.getDiachi());
        viewHolder.txtmotasinhthai.setMaxLines(2);
        viewHolder.txtmotasinhthai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotasinhthai.setText(dulich.getMotadiadiem());
        Picasso.with(context).load(dulich.getHinhanhdiadiemdulich())
                .placeholder(R.drawable.load)
                .error(R.drawable.loi)
                .into(viewHolder.imgsinhthai);

        return view;
    }
}


