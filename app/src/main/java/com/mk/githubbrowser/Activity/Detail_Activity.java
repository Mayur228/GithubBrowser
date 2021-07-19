package com.mk.githubbrowser.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.mk.githubbrowser.Fragment.BranchesFragment;
import com.mk.githubbrowser.Fragment.IssueFragment;
import com.mk.githubbrowser.Model.FavRepoData;
import com.mk.githubbrowser.R;
import com.mk.githubbrowser.Utils.TinyDB;

import io.realm.Realm;
import io.realm.RealmResults;

public class Detail_Activity extends AppCompatActivity {

    public static TabLayout tabLayout;
    TextView repo_name, repo_des;
    ImageView back,delete,browser;
    TinyDB db;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();

        layout.setBackgroundColor(Color.BLACK);


        db=new TinyDB(getApplicationContext());

        Intent intent = getIntent();
        repo_name.setText(intent.getStringExtra("REPONAME"));
        repo_des.setText(intent.getStringExtra("REPODES"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        browser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse(db.getString("BROWSER")));
                startActivity(intent1);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm.init(getApplicationContext());
                Realm realm = Realm.getDefaultInstance();

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<FavRepoData> results = realm.where(FavRepoData.class).equalTo("repo_name",repo_name.getText().toString()).findAll();
                        results.deleteAllFromRealm();

                        Intent intent1=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                });
            }
        });

        changeFragment(BranchesFragment.newInstance(), "BRANCHESFRAGMENT");


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        changeFragment(BranchesFragment.newInstance(), "BRANCHESFRAGMENT");
                        break;
                    case 1:
                        changeFragment(IssueFragment.newInstance(), "ISSUESFRAGMENT");
                        break;
                    default:
                        Toast.makeText(Detail_Activity.this, "BREAK", Toast.LENGTH_SHORT).show();
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initView() {
        tabLayout = findViewById(R.id.tab_layout);
        repo_name = findViewById(R.id.repo_name);
        repo_des = findViewById(R.id.repo_des);
        back = findViewById(R.id.back_btn);
        delete = findViewById(R.id.delete);
        browser = findViewById(R.id.browser);
        layout=findViewById(R.id.appbar_layout);
    }

    public void changeFragment(final Fragment fragment, final String fragmenttag) {

        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
            fragmentTransaction.replace(R.id.frame, fragment, fragmenttag);
            fragmentTransaction.commit();
        } catch (Exception e) {
            Log.d("catch-Frag_Transaction", e.toString());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}