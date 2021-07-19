package com.mk.githubbrowser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mk.githubbrowser.Model.Commit;
import com.mk.githubbrowser.Model.CommitDatum;
import com.mk.githubbrowser.Model.RepoData;
import com.mk.githubbrowser.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommitAdapter extends RecyclerView.Adapter<CommitAdapter.RepoViewHolder> {

    Context context;
    List<CommitDatum> list;

    public CommitAdapter(Context context, List<CommitDatum> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepoViewHolder(LayoutInflater.from(context).inflate(R.layout.commit_items, parent, false));
    }

    @Override
    public void onBindViewHolder(CommitAdapter.RepoViewHolder holder, int position) {

        CommitDatum datum=list.get(position);

        holder.date.setText(datum.getCommit().getAuthor().getDate().substring(0,datum.getCommit().getAuthor().getDate().lastIndexOf("T")));
        holder.sha.setText(datum.getSha());
        holder.username.setText(datum.getCommit().getAuthor().getName());
        holder.mess.setText(datum.getCommit().getMessage());
        Glide.with(context).load(datum.getAuthor().getAvatarUrl()).into(holder.profile_img);

//        if (datum.getAuthor().getAvatarUrl().isEmpty()){
//            Glide.with(holder.profile_img).load(R.drawable.profile).into(holder.profile_img);
//
//        }else {
//
//        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder {
        TextView date, sha,username,mess;
        CircleImageView profile_img;

        public RepoViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.txt_date);
            sha = itemView.findViewById(R.id.txt_id);
            mess = itemView.findViewById(R.id.mess);
            username = itemView.findViewById(R.id.user_name);
            profile_img = itemView.findViewById(R.id.profile_image);

        }
    }

}
