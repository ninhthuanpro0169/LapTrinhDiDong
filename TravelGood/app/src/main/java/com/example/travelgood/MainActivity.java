package com.example.travelgood;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.travelgood.activity.HotroActivity;
import com.example.travelgood.activity.KhamphaActivity;
import com.example.travelgood.activity.SearchActivity;
import com.example.travelgood.activity.SinhthaiActivity;
import com.example.travelgood.activity.ThamquanActivity;
import com.example.travelgood.adapter.DulichAdapter;
import com.example.travelgood.adapter.LoaidlAdapter;
import com.example.travelgood.adapter.SearchAdapter;
import com.example.travelgood.model.Dulich;
import com.example.travelgood.model.Loaidl;
import com.example.travelgood.ultil.CheckConnection;
import com.example.travelgood.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.travelgood.R.id.actionsearch;
import static com.example.travelgood.R.id.drawerlayout;
import static com.example.travelgood.R.id.search_view;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbarmanhinhchinh;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DulichAdapter dulichAdapter;
    DrawerLayout drawerLayout;
    ArrayList<Loaidl> mangloaidl;
    LoaidlAdapter loaidlAdapter;
    SearchAdapter searchAdapter;
    int id = 0;
    String tenloaidulich = "";
    String hinhanhloaidulich = "";
    ArrayList<Dulich> mangdulich;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menutoolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case actionsearch:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionViewFipper();
            ActionBar();
            GetDuLieuLoaidl();
            GetDuLieuDLHot();
            CatchonItemListView();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Kiểm tra lại kết nối");
            finish();
        }

    }


    private void CatchonItemListView() {

        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                 case 0:
                     if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                         Intent intent = new Intent(MainActivity.this, MainActivity.class);

                     }else {
                         CheckConnection.ShowToast_Short(getApplicationContext(), "Hãy kiểm tra lại kết nối");
                     }
                         drawerLayout.closeDrawer(GravityCompat.START);
                         break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, SinhthaiActivity.class);
                            intent.putExtra("idloaidulich",mangloaidl.get(i).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, KhamphaActivity.class);
                            intent.putExtra("idloaidulich",mangloaidl.get(i).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, ThamquanActivity.class);
                            intent.putExtra("idloaidulich",mangloaidl.get(i).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, HotroActivity.class);
                            intent.putExtra("idloaidulich",mangloaidl.get(i).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }
    private void GetDuLieuLoaidl() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongdanLoaidl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response !=null){
                    for (int i=0 ; i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id= jsonObject.getInt("id");
                            tenloaidulich=jsonObject.getString("tenloaidulich");
                            hinhanhloaidulich=jsonObject.getString("hinhanhloaidulich");
                            mangloaidl.add(new Loaidl(id,tenloaidulich,hinhanhloaidulich));
                            loaidlAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                    mangloaidl.add(4,new Loaidl(0,"Hỗ Trợ","https://www.kindpng.com/picc/m/134-1341998_contact-icon-images-hd-hd-png-download.png"));

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @SuppressLint("RestrictedApi")
    private void ActionBar(){
        setSupportActionBar(toolbarmanhinhchinh);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setLogo(R.drawable.titletoolbar);
        actionBar.setDisplayUseLogoEnabled(true);
        toolbarmanhinhchinh.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbarmanhinhchinh.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });
    }

    private void GetDuLieuDLHot() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdandulichhot, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                 if (response != null ){
                     int ID = 0;
                     String Tendiadiem="";
                     String Diachi ="";
                     String Motadiadiem ="";
                     String Hinhanhdiadiem ="";
                     int IDDulich =0;
                     double Lat;
                     double Lng;
                     for ( int i = 0 ; i < response.length(); i++){
                         try{
                             JSONObject jsonObject = response.getJSONObject(i);
                             ID = jsonObject.getInt("id");
                             Tendiadiem = jsonObject.getString("tendiadiem");
                             Diachi = jsonObject.getString("diachi");
                             Motadiadiem=jsonObject.getString("motadiadiem");
                             Hinhanhdiadiem=jsonObject.getString("hinhanhdiadiemdulich");
                             IDDulich = jsonObject.getInt("iddulich");
                             Lat = jsonObject.getDouble("lat");
                             Lng = jsonObject.getDouble("lng");
                             mangdulich.add(new Dulich(ID,Tendiadiem,Diachi,Hinhanhdiadiem,Motadiadiem,IDDulich,Lat,Lng));
                             dulichAdapter.notifyDataSetChanged();

                         } catch (JSONException e) {
                             e.printStackTrace();
                         }
                     }
                 }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);

    }


    private void ActionViewFipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://divui.com/blog/wp-content/uploads/2018/10/111111.jpg");
        mangquangcao.add("https://vcdn-dulich.vnecdn.net/2020/01/08/sac-mau-cua-bien-vnexpress-1-6641-1578454676.jpg");
        mangquangcao.add("https://i1.wp.com/du-lich.net/wp-content/uploads/2016/05/mui-ong-doi-phu-quoc-3.jpg?resize=810%2C540&ssl=1");
        mangquangcao.add("https://tailieufree.net/wp-content/uploads/2018/04/hinh-nen-vinh-ha-long-quang-ninh-dep.jpg");
        mangquangcao.add("https://anhnendep.net/wp-content/uploads/2015/06/hinh-du-lich-italy-1.jpg");
        for (int i =0 ; i< mangquangcao.size() ; i++ ) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void Anhxa(){
        toolbarmanhinhchinh = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewFlipper);
        recyclerViewmanhinhchinh = findViewById(R.id.recyclerview);
        navigationView = findViewById(R.id.navigationview);
        listViewmanhinhchinh = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout=findViewById(drawerlayout);
        mangloaidl = new ArrayList<>();
        mangloaidl.add(0,new Loaidl(0,"Trang Chủ","https://laptopgiasi.vn/wp-content/uploads/2017/09/icon-trang-chu-laptopgiasi.vn_.png"));
        loaidlAdapter = new LoaidlAdapter(mangloaidl,getApplicationContext());
        listViewmanhinhchinh.setAdapter(loaidlAdapter);
        mangdulich = new ArrayList<>();
        dulichAdapter = new DulichAdapter(getApplicationContext(), mangdulich);
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
        recyclerViewmanhinhchinh.setAdapter(dulichAdapter);


    }
}
