package com.example.travelgood.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.travelgood.R;
import com.example.travelgood.adapter.DulichkhamphaAdapter;
import com.example.travelgood.model.Dulich;
import com.example.travelgood.ultil.CheckConnection;
import com.example.travelgood.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KhamphaActivity extends AppCompatActivity {

    Toolbar toolbarkhampha;
    ListView listViewkhampha;
    DulichkhamphaAdapter dulichkhamphaAdapter;
    ArrayList<Dulich> mangkhampha;
    int idkhampha = 0;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    boolean limitadata = false;
    mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khampha);
        Anhxa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIdloaidl();
            ActionToolbar();
            GetData(page);
            LoadMoreData();
        }else
        {
            CheckConnection.ShowToast_Short(getApplicationContext(),"hãy kiểm tra internet");
            finish();
        }

    }

    private void LoadMoreData() {
        listViewkhampha.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChiTietdulich.class);
                intent.putExtra("thongtindulich",mangkhampha.get(i));
                startActivity(intent);
            }
        });
        listViewkhampha.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount==totalItemCount && totalItemCount != 0 && isLoading == false && limitadata == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();

                }

            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdandulichkhampha+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id;
                String Tenkhampha= "";
                String Diachikp ="";
                String Hinhanhkhampha ="";
                String Motakp = "";
                int Idkhampha ;
                if(response != null && response.length() != 2){
                listViewkhampha.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i =0; i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tenkhampha = jsonObject.getString("tendiadiem");
                            Diachikp = jsonObject.getString("diachi");
                            Hinhanhkhampha = jsonObject.getString("hinhanhdiadiemdulich");
                            Motakp = jsonObject.getString("motadiadiem");
                            Idkhampha = jsonObject.getInt("iddulich");
                            mangkhampha.add(new Dulich(id,Tenkhampha,Diachikp,Hinhanhkhampha,Motakp,Idkhampha));
                            dulichkhamphaAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitadata = true;
                    listViewkhampha.removeFooterView(footerview);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Đã hết dữ liệu");

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("iddulich",String.valueOf(idkhampha));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarkhampha);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarkhampha.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetIdloaidl() {
        idkhampha = getIntent().getIntExtra("idloaidulich",-1);
        Log.d("giatriloaidulich",idkhampha+"");
    }

    private void Anhxa() {
        toolbarkhampha = (Toolbar) findViewById(R.id.toolbarkhampha);
        listViewkhampha = (ListView) findViewById(R.id.listviewkhampha);
        mangkhampha = new ArrayList<>();
        dulichkhamphaAdapter = new DulichkhamphaAdapter(getApplicationContext(),mangkhampha);
        listViewkhampha.setAdapter(dulichkhamphaAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();

    }
    public class mHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listViewkhampha.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public  class ThreadData extends  Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
