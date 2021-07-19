package com.mk.githubbrowser.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mk.githubbrowser.Adapter.FavRepoAdapter;
import com.mk.githubbrowser.Adapter.RepoAdapter;
import com.mk.githubbrowser.Model.FavRepoData;
import com.mk.githubbrowser.Model.RepoData;
import com.mk.githubbrowser.R;
import com.mk.githubbrowser.Utils.TinyDB;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;


public class MainActivity extends AppCompatActivity {

    Button add_btn;
    ImageView add;
    TextView txt;
    RecyclerView r_view;
    Gson gson;
    LinearLayoutManager manager;
    FavRepoAdapter adapter;
    List<FavRepoData> list;
    TinyDB tinyDB;
    RelativeLayout layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        layout.setBackgroundColor(Color.BLACK);

        manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        list = new ArrayList<>();
        r_view.setLayoutManager(manager);

        getData();


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Add_repoActivity.class);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Add_repoActivity.class);
                startActivity(intent);
            }
        });



    }

    private void getData() {

        Realm.init(getApplicationContext());

        Realm realm = Realm.getDefaultInstance();

        list=realm.where(FavRepoData.class).findAll();
        gson=new Gson();

        if (list.isEmpty()){
            txt.setVisibility(View.VISIBLE);
            add_btn.setVisibility(View.VISIBLE);
        }else {
            txt.setVisibility(View.GONE);
            add_btn.setVisibility(View.GONE);
            adapter=new FavRepoAdapter(MainActivity.this, list, new FavRepoAdapter.onItemCLick() {
                @Override
                public void onItemCLick(View view, int pos) {
                    Intent intent=new Intent(getApplicationContext(),Detail_Activity.class);
                    intent.putExtra("REPONAME",list.get(pos).getRepo_name());
                    intent.putExtra("REPODES",list.get(pos).getRepo_des());
                    intent.putExtra("REPOBRANCH",list.get(pos).getRepo_branch());
                    intent.putExtra("REPOISSUE",list.get(pos).getRepo_issue());
                    startActivity(intent);
                }
            });
            r_view.setAdapter(adapter);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initView() {
        add_btn = findViewById(R.id.add_btn);
        add = findViewById(R.id.add);
        r_view = findViewById(R.id.r_view);
        txt = findViewById(R.id.txt);
        layout=findViewById(R.id.appbar_layout);

        tinyDB = new TinyDB(getApplicationContext());
    }
}