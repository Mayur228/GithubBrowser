package com.mk.githubbrowser.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mk.githubbrowser.Activity.CommitActivity;
import com.mk.githubbrowser.Model.BranchDatum;
import com.mk.githubbrowser.Model.RepoData;
import com.mk.githubbrowser.R;

import java.util.List;

public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.BranchViewHolder> {

    Context context;
    List<BranchDatum> list;
    onItemCLick itemCLick;

    public BranchAdapter(Context context, List<BranchDatum> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public BranchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BranchViewHolder(LayoutInflater.from(context).inflate(R.layout.branch_item, parent, false));
    }

    @Override
    public void onBindViewHolder(BranchAdapter.BranchViewHolder holder, int position) {

        BranchDatum datum=list.get(position);

        holder.branch_name.setText(datum.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(context, CommitActivity.class);
                intent1.putExtra("BRANCHNAME",datum.getName());
                intent1.putExtra("REPOCOMMIT",datum.getCommit().getUrl());
                context.startActivity(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BranchViewHolder extends RecyclerView.ViewHolder {
        TextView branch_name;

        public BranchViewHolder(View itemView) {
            super(itemView);
            branch_name = itemView.findViewById(R.id.branch_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemCLick.onItemCLick(v,getAdapterPosition());
                }
            });
        }
    }


    public void setItemCLick(onItemCLick itemCLick) {
        this.itemCLick = itemCLick;
    }

    public interface onItemCLick {
        void onItemCLick(View view,int pos);
    }
}
