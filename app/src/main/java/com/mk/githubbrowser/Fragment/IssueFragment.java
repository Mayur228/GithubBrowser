package com.mk.githubbrowser.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mk.githubbrowser.Activity.CommitActivity;
import com.mk.githubbrowser.Activity.Detail_Activity;
import com.mk.githubbrowser.Adapter.BranchAdapter;
import com.mk.githubbrowser.Adapter.IssueAdapter;
import com.mk.githubbrowser.Model.BranchDatum;
import com.mk.githubbrowser.Model.IssueDatum;
import com.mk.githubbrowser.R;

import java.util.ArrayList;
import java.util.List;

public class IssueFragment extends Fragment {

    View view;
    RecyclerView r_view;
    LinearLayoutManager manager;
    StringRequest request;
    RequestQueue requestQueue;
    String url;
    List<IssueDatum> list;
    Gson gson;
    IssueAdapter adapter;
    TextView issue;

    public static IssueFragment newInstance(){
        return new IssueFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.issuefragment,container,false);
        r_view=view.findViewById(R.id.issue_view);
        issue=view.findViewById(R.id.issue);
        manager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        r_view.setLayoutManager(manager);
        gson=new Gson();
        list=new ArrayList<>();
        Intent intent=getActivity().getIntent();
        url=intent.getStringExtra("REPOISSUE");
        String url2=url.substring(0,url.indexOf("{"));
        requestQueue= Volley.newRequestQueue(getContext());
        request=new StringRequest(url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                list=gson.fromJson(response,new TypeToken<List<IssueDatum>>(){}.getType());
                adapter=new IssueAdapter(getContext(), list);
                r_view.setAdapter(adapter);

                (Detail_Activity.tabLayout).getTabAt(1).setText("ISSUES"+"("+list.size()+")");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);


        if (list.isEmpty()){
            issue.setText("No Issues");
            issue.setVisibility(View.VISIBLE);
            (Detail_Activity.tabLayout).getTabAt(1).setText("ISSUES"+"("+list.size()+")");
        }else {
            issue.setVisibility(View.GONE);
            (Detail_Activity.tabLayout).getTabAt(1).setText("ISSUES"+"("+list.size()+")");

        }

        return view;
    }
}
