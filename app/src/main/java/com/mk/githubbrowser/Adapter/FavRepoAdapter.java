package com.mk.githubbrowser.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mk.githubbrowser.Model.FavRepoData;
import com.mk.githubbrowser.Model.RepoData;
import com.mk.githubbrowser.R;
import com.mk.githubbrowser.Utils.TinyDB;

import java.util.List;

public class FavRepoAdapter extends RecyclerView.Adapter<FavRepoAdapter.RepoViewHolder> {

    Context context;
    List<FavRepoData> list;
    onItemCLick itemCLick;
    TinyDB db;


    public FavRepoAdapter(Context context, List<FavRepoData> list, onItemCLick itemCLick) {
        this.context = context;
        this.list = list;
        this.itemCLick = itemCLick;
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepoViewHolder(LayoutInflater.from(context).inflate(R.layout.repo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(FavRepoAdapter.RepoViewHolder holder, int position) {

        FavRepoData datum=list.get(position);

        holder.repo_name.setText(datum.getRepo_name());
        holder.repo_des.setText(datum.getRepo_des());

        db = new TinyDB(context.getApplicationContext());
        db.putString("BROWSER",datum.getRepo_url());


        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String git_url = datum.getRepo_url();
                shareIntent.putExtra(Intent.EXTRA_TEXT,git_url);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(shareIntent, "Send to"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder {
        TextView repo_name, repo_des;
        ImageView send;

        public RepoViewHolder(View itemView) {
            super(itemView);
            repo_name = itemView.findViewById(R.id.repo_name);
            repo_des = itemView.findViewById(R.id.repo_des);
            send = itemView.findViewById(R.id.send_icon);

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
