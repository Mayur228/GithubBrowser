package com.mk.githubbrowser.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mk.githubbrowser.Adapter.RepoAdapter;
import com.mk.githubbrowser.Model.FavRepoData;
import com.mk.githubbrowser.Model.RepoData;
import com.mk.githubbrowser.R;
import com.mk.githubbrowser.Utils.TinyDB;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;


public class Add_repoActivity extends AppCompatActivity {

    Button add;
    EditText ext_owner,ext_repo;
    TinyDB tinyDB;
    String url = "https://api.github.com/repos/";
    StringRequest request;
    RequestQueue requestQueue;
    Gson gson;
    List<RepoData> list;
    FavRepoData data;
    List<FavRepoData>favRepoData;
    RelativeLayout layout;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repo);

        initView();

        layout.setBackgroundColor(Color.BLACK);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(ext_owner.getText().toString(),ext_repo.getText().toString());
            }
        });
    }

    private void initView() {
        add=findViewById(R.id.add_btn);
        ext_owner=findViewById(R.id.ext_owner);
        ext_repo=findViewById(R.id.ext_repo);
        layout=findViewById(R.id.appbar_layout);
        back=findViewById(R.id.back_btn);
        tinyDB=new TinyDB(getApplicationContext());
    }

    private void getData(String username, String reponame) {

        requestQueue = Volley.newRequestQueue(this);
        request = new StringRequest(url + username.trim() + "/" + reponame.trim(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RESPO", response);

                gson = new Gson();
                RepoData repoData = gson.fromJson(response, new TypeToken<RepoData>() {
                }.getType());

                list=new ArrayList<>();
//                favRepoData=new ArrayList<>();
                list.add(repoData);
                data=new FavRepoData();

                Realm.init(getApplicationContext());
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                favRepoData = new RealmList<FavRepoData>();

                for (int i=0;i<list.size();i++){
                    data.setRepo_name(list.get(i).getName());
                    data.setRepo_des(list.get(i).getDescription());
                    data.setRepo_branch(list.get(i).getBranchesUrl());
                    data.setRepo_issue(list.get(i).getIssuesUrl());
                    data.setRepo_commit(list.get(i).getCommitsUrl());
                    data.setRepo_url(list.get(i).getHtmlUrl());

                    favRepoData.add(data);

                }

                realm.copyToRealm(favRepoData);
                realm.commitTransaction();
                Gson gson = new Gson();
                String json = gson.toJson(favRepoData);

                tinyDB.putString("JSON",json);
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Add_repoActivity.this, "Please Enter Data or Valid Data", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);

    }

}