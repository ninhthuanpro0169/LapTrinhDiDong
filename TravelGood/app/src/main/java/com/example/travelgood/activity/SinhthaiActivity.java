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
import com.example.travelgood.adapter.DulichsinhthaiAdapter;
import com.example.travelgood.model.Dulich;
import com.example.travelgood.ultil.CheckConnection;
import com.example.travelgood.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SinhthaiActivity extends AppCompatActivity {
    Toolbar toolbarsinhthai;
    ListView listViewsinhthai;
    DulichsinhthaiAdapter dulichsinhthaiAdapter;
    ArrayList<Dulich> mangsinhthai;
    int idsinhthai = 0;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    boolean limitdata = false;
    mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinhthai);
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
        listViewsinhthai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> Adapterview, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChiTietdulich.class);
                intent.putExtra("thongtindulich",mangsinhthai.get(i));
                startActivity(intent);

            }
        });
        listViewsinhthai.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotalItem) {
                if(FirstItem + VisibleItem == TotalItem && TotalItem !=0 && isLoading== false && limitdata==false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();

                }

            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdandulichsinhthai+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String Tensinhthai= "";
                String Diachist ="";
                String Hinhanhsinhthai ="";
                String Motast = "";
                int Idsinhthai = 0;
                if(response != null && response.length() != 2) {
                    listViewsinhthai.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i =0; i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tensinhthai = jsonObject.getString("tendiadiem");
                            Diachist = jsonObject.getString("diachi");
                            Hinhanhsinhthai = jsonObject.getString("hinhanhdiadiemdulich");
                            Motast = jsonObject.getString("motadiadiem");
                            Idsinhthai = jsonObject.getInt("iddulich");
                            mangsinhthai.add(new Dulich(id,Tensinhthai,Diachist,Hinhanhsinhthai,Motast,Idsinhthai));
                            dulichsinhthaiAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitdata = true;
                    listViewsinhthai.removeFooterView(footerview);
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
                param.put("iddulich",String.valueOf(idsinhthai));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarsinhthai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarsinhthai.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetIdloaidl() {
        idsinhthai = getIntent().getIntExtra("idloaidulich",-1);
        Log.d("giatriloaidulich",idsinhthai+"");
    }

    private void Anhxa() {
        toolbarsinhthai = (Toolbar) findViewById(R.id.toolbarsinhthai);
        listViewsinhthai = (ListView) findViewById(R.id.listviewsinhthai);
        mangsinhthai = new ArrayList<>();
        dulichsinhthaiAdapter = new DulichsinhthaiAdapter(getApplicationContext(),mangsinhthai);
        listViewsinhthai.setAdapter(dulichsinhthaiAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();

    }
    public class mHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listViewsinhthai.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends  Thread{
        public  void run() {
            mHandler.sendEmptyMessage(0);
            try{
                Thread.sleep(3000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
