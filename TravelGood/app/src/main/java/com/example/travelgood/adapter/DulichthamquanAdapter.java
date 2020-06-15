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

public class DulichthamquanAdapter extends BaseAdapter {
    Context context;
    ArrayList<Dulich> arraythamquan;

    public DulichthamquanAdapter(Context context, ArrayList<Dulich> arraythamquan) {
        this.context = context;
        this.arraythamquan = arraythamquan;
    }

    @Override
    public int getCount() {
        return arraythamquan.size();
    }

    @Override
    public Object getItem(int i) {
        return arraythamquan.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public  class ViewHolder{
        public TextView txttenthamquan, txtdiachithamquan , txtmotathamquan;
        public ImageView imgthamquan;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DulichthamquanAdapter.ViewHolder viewHolder = null;
        if ( view == null){
            viewHolder = new DulichthamquanAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dong_thamquan,null);
            viewHolder.txttenthamquan = view.findViewById(R.id.textviewtenthamquan);
            viewHolder.txtdiachithamquan = view.findViewById(R.id.textviewdiachithamquan);
            viewHolder.txtmotathamquan = view.findViewById(R.id.textviewmotathamquan);
            viewHolder.imgthamquan = view.findViewById(R.id.imageviewthamquan);
            view.setTag(viewHolder);
        }else {
            viewHolder = (DulichthamquanAdapter.ViewHolder) view.getTag();
        }
        Dulich dulich = (Dulich) getItem(i);
        viewHolder.txttenthamquan.setText(dulich.getTendiadiem());
        viewHolder.txtdiachithamquan.setText(dulich.getDiachi());
        viewHolder.txtmotathamquan.setMaxLines(2);
        viewHolder.txtmotathamquan.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotathamquan.setText(dulich.getMotadiadiem());
        Picasso.with(context).load(dulich.getHinhanhdiadiemdulich())
                .placeholder(R.drawable.load)
                .error(R.drawable.loi)
                .into(viewHolder.imgthamquan);

        return view;
    }
}
