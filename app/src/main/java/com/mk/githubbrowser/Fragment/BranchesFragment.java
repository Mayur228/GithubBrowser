package com.mk.githubbrowser.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.mk.githubbrowser.Adapter.BranchAdapter;
import com.mk.githubbrowser.Model.BranchDatum;
import com.mk.githubbrowser.R;

import java.util.ArrayList;
import java.util.List;

public class BranchesFragment extends Fragment {

    View view;
    RecyclerView r_view;
    LinearLayoutManager manager;
    StringRequest request;
    RequestQueue requestQueue;
    String url;
    List<BranchDatum> list;
    Gson gson;
    BranchAdapter adapter;
    TextView branch;
    BranchDatum datum;

    public static BranchesFragment newInstance(){
        return new BranchesFragment();
    };
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.branchesfragment,container,false);
        r_view=view.findViewById(R.id.branch_view);
        branch=view.findViewById(R.id.branch);
        manager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        r_view.setLayoutManager(manager);
        gson=new Gson();
        list=new ArrayList<>();
        Intent intent=getActivity().getIntent();
        url=intent.getStringExtra("REPOBRANCH");
        String url2=url.substring(0,url.indexOf("{"));

        requestQueue= Volley.newRequestQueue(getContext());
        request=new StringRequest(url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                list=gson.fromJson(response,new TypeToken<List<BranchDatum>>(){}.getType());
//                datum=new BranchDatum();
//
//                for (int i=0;i<list.size();i++){
//                    datum=list.get(i);
//                }

                if (list.isEmpty()){
                    branch.setText("No Branches");
                    branch.setVisibility(View.VISIBLE);
                }else {
                    branch.setVisibility(View.GONE);
                }
                adapter=new BranchAdapter(getContext(), list);

                r_view.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);




        return view;
    }

}
