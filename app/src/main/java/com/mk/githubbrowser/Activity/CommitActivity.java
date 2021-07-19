package com.mk.githubbrowser.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.mk.githubbrowser.Adapter.CommitAdapter;
import com.mk.githubbrowser.Adapter.RepoAdapter;
import com.mk.githubbrowser.Model.CommitDatum;
import com.mk.githubbrowser.Model.RepoData;
import com.mk.githubbrowser.R;

import java.util.ArrayList;
import java.util.List;

public class CommitActivity extends AppCompatActivity {

    RecyclerView r_view;
    String url;
    String branch_name;
    TextView branch;
    StringRequest request;
    RequestQueue requestQueue;
    Gson gson;
    LinearLayoutManager manager;
    CommitAdapter adapter;
    List<CommitDatum> list;
    ImageView back;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit);

        r_view=findViewById(R.id.commit_view);
        branch=findViewById(R.id.txt_branch);
        back=findViewById(R.id.back_btn);
        layout=findViewById(R.id.appbar_layout);

        layout.setBackgroundColor(Color.BLACK);


        manager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        r_view.setLayoutManager(manager);
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        gson=new Gson();
        list=new ArrayList<>();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent=getIntent();
        url = intent.getStringExtra("REPOCOMMIT");
        branch_name ="?sha="+ intent.getStringExtra("BRANCHNAME");

        branch.setText(intent.getStringExtra("BRANCHNAME"));

       String url2= url.substring(0,url.lastIndexOf("/"));

        request=new StringRequest(url2+branch_name, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

               list= gson.fromJson(response,new TypeToken<List<CommitDatum>>(){}.getType());
               adapter=new CommitAdapter(getApplicationContext(),list);
               r_view.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CommitActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);

    }
}