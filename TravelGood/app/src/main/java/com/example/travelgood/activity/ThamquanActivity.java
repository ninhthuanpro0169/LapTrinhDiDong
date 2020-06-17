package com.example.travelgood.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.travelgood.R;
import com.example.travelgood.adapter.DulichthamquanAdapter;
import com.example.travelgood.model.Dulich;
import com.example.travelgood.ultil.CheckConnection;
import com.example.travelgood.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThamquanActivity extends AppCompatActivity {
    Toolbar toolbarthamquan;
    ListView listViewthamquan;
    DulichthamquanAdapter dulichthamquanAdapter;
    ArrayList<Dulich> mangthamquan;
    int idthamquan = 0;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    boolean limitdata = false;
    mHandler mHandler;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thamquan);
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
        listViewthamquan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> Adapterview, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChiTietdulich.class);
                intent.putExtra("thongtindulich",mangthamquan.get(i));
                startActivity(intent);

            }
        });
        listViewthamquan.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        String duongdan = Server.Duongdandulichthamquan+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String Tenthamquan= "";
                String Diachitq ="";
                String Hinhanhthamquan ="";
                String Motatq = "";
                int Idthamquan = 0;
                double Lat;
                double Lng;
                if(response != null && response.length() != 2) {
                    listViewthamquan.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i =0; i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tenthamquan = jsonObject.getString("tendiadiem");
                            Diachitq = jsonObject.getString("diachi");
                            Hinhanhthamquan = jsonObject.getString("hinhanhdiadiemdulich");
                            Motatq = jsonObject.getString("motadiadiem");
                            Idthamquan = jsonObject.getInt("iddulich");
                            Lat = jsonObject.getDouble("lat");
                            Lng = jsonObject.getDouble("lng");
                            mangthamquan.add(new Dulich(id,Tenthamquan,Diachitq,Hinhanhthamquan,Motatq,Idthamquan,Lat,Lng));
                            dulichthamquanAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitdata = true;
                    listViewthamquan.removeFooterView(footerview);
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
                param.put("iddulich",String.valueOf(idthamquan));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarthamquan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarthamquan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetIdloaidl() {
        idthamquan = getIntent().getIntExtra("idloaidulich",-1);
        Log.d("giatriloaidulich",idthamquan+"");
    }

    private void Anhxa() {
        toolbarthamquan = (Toolbar) findViewById(R.id.toolbarthamquan);
        listViewthamquan = (ListView) findViewById(R.id.listviewthamquan);
        mangthamquan = new ArrayList<>();
        dulichthamquanAdapter = new DulichthamquanAdapter(getApplicationContext(),mangthamquan);
        listViewthamquan.setAdapter(dulichthamquanAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mHandler = new ThamquanActivity.mHandler();

    }
    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listViewthamquan.addFooterView(footerview);
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

