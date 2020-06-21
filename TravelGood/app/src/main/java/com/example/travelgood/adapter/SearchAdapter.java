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

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class SearchAdapter extends BaseAdapter {
    Context context;
    List<Dulich> arraysearch;
    int layout;
    ArrayList<Dulich> arrayList;

    public SearchAdapter(Context context,int layout, ArrayList<Dulich> arraysearch) {
        this.context = context;
        this.layout = layout;
        this.arraysearch = arraysearch;

        this.arrayList = new ArrayList<Dulich>();
        this.arrayList.addAll(arraysearch);
    }

    @Override
    public int getCount() {
        return arraysearch.size();
    }

    @Override
    public Object getItem(int i) {
        return arraysearch.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public  class ViewHolder{
        public TextView txttensearch, txtdiachisearch , txtmotasearch;
        public ImageView imgsearch;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SearchAdapter.ViewHolder viewHolder = null;
        if ( view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dong_search,null);
            viewHolder.txttensearch = view.findViewById(R.id.textviewtensearch);
            viewHolder.txtdiachisearch = view.findViewById(R.id.textviewdiachisearch);
            viewHolder.txtmotasearch = view.findViewById(R.id.textviewmotasearch);
            viewHolder.imgsearch = view.findViewById(R.id.imageviewsearch);
            view.setTag(viewHolder);
        }else {
            viewHolder = (SearchAdapter.ViewHolder) view.getTag();
        }
        Dulich dulich = (Dulich) getItem(i);
        viewHolder.txttensearch.setText(dulich.getTendiadiem());
        viewHolder.txtdiachisearch.setText(dulich.getDiachi());
        viewHolder.txtmotasearch.setMaxLines(2);
        viewHolder.txtmotasearch.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotasearch.setText(dulich.getMotadiadiem());
        Picasso.with(context).load(dulich.getHinhanhdiadiemdulich())
                .placeholder(R.drawable.load)
                .error(R.drawable.loi)
                .into(viewHolder.imgsearch);

        return view;
    }

    public static String  removeAccent(String s){
        String temp = Normalizer.normalize(s,Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCOMBINING_DIACRITICAL_MARKS}+");
        return pattern.matcher(temp).replaceAll("");
    }
    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());
        arraysearch.clear();
        if (charText.length() == 0) {
            arraysearch.addAll(arrayList);
        } else {
            for (Dulich dulich : arrayList) {
                String ten = removeAccent(dulich.Tendiadiem);
                String diadiem = removeAccent(dulich.Diachi);
                if (ten.toLowerCase(Locale.getDefault()).contains(charText)||diadiem.toLowerCase(Locale.getDefault()).contains(charText)) {
                    arraysearch.add(dulich);
                }
            }
        }
        notifyDataSetChanged();
    }
}

