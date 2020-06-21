package com.example.travelgood.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.travelgood.MainActivity;
import com.example.travelgood.R;
import com.example.travelgood.adapter.SearchAdapter;
import com.example.travelgood.model.Dulich;
import com.example.travelgood.ultil.CheckConnection;
import com.example.travelgood.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class SearchActivity extends AppCompatActivity {
    Toolbar toolbarsearch;
    ListView listViewsearch;
    SearchAdapter searchAdapter;
    List<Dulich> mangsearch;
    int idsearch = 0;
    View footerview;
    boolean isLoading = false;
    boolean limitdata = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Anhxa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIdloaidl();
            ActionToolbar();
            GetData();
            LoadMoreData();
        }else
        {
            CheckConnection.ShowToast_Short(getApplicationContext(),"hãy kiểm tra internet");
            finish();
        }

    }

    private void LoadMoreData() {
        listViewsearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> Adapterview, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChiTietdulich.class);
                intent.putExtra("thongtindulich",mangsearch.get(i));
                startActivity(intent);

            }
        });

    }

    private void GetData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.DuongdanSearch;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String Tensearch= "";
                String Diachisearch ="";
                String Hinhanhsearch ="";
                String Motasearch = "";
                int Idsearch = 0;
                double Lat;
                double Lng;
                if(response != null && response.length() != 2) {
                    listViewsearch.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i =0; i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tensearch = jsonObject.getString("tendiadiem");
                            Diachisearch = jsonObject.getString("diachi");
                            Hinhanhsearch = jsonObject.getString("hinhanhdiadiemdulich");
                            Motasearch = jsonObject.getString("motadiadiem");
                            Idsearch = jsonObject.getInt("iddulich");
                            Lat = jsonObject.getDouble("lat");
                            Lng = jsonObject.getDouble("lng");
                            mangsearch.add(new Dulich(id,Tensearch,Diachisearch,Hinhanhsearch,Motasearch,Idsearch,Lat,Lng));
                            //searchAdapter.notifyDataSetChanged();
                            searchAdapter = new SearchAdapter(SearchActivity.this,R.layout.dong_search, (ArrayList<Dulich>) mangsearch);
                            listViewsearch.setAdapter(searchAdapter);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitdata = true;
                    listViewsearch.removeFooterView(footerview);
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
                param.put("iddulich",String.valueOf(idsearch));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarsearch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarsearch.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetIdloaidl() {
        idsearch = getIntent().getIntExtra("idloaidulich",-1);
        Log.d("giatriloaidulich",idsearch+"");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menusearch , menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_view).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(SearchActivity.this,query,Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.filter(newText.trim());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void Anhxa() {
        toolbarsearch = (Toolbar) findViewById(R.id.toolbarsearch);
        listViewsearch = (ListView) findViewById(R.id.listviewsearch);
        mangsearch = new ArrayList<>();
        searchAdapter = new SearchAdapter(getApplicationContext(),R.layout.dong_search, (ArrayList<Dulich>) mangsearch);
        listViewsearch.setAdapter(searchAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);


    }

}
