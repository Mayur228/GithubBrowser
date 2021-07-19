package com.mk.githubbrowser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mk.githubbrowser.Model.IssueDatum;
import com.mk.githubbrowser.Model.RepoData;
import com.mk.githubbrowser.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.RepoViewHolder> {

    Context context;
    List<IssueDatum> list;

    public IssueAdapter(Context context, List<IssueDatum> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepoViewHolder(LayoutInflater.from(context).inflate(R.layout.issue_item, parent, false));
    }

    @Override
    public void onBindViewHolder(IssueAdapter.RepoViewHolder holder, int position) {

        IssueDatum datum=list.get(position);
        holder.issue_title.setText(datum.getTitle());
        holder.creator_name.setText(datum.getUser().getLogin());
        Glide.with(holder.profile_img).load(datum.getUser().getAvatarUrl()).into(holder.profile_img);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder {
        TextView issue_title,creator_name;
        CircleImageView profile_img;

        public RepoViewHolder(View itemView) {
            super(itemView);
            issue_title = itemView.findViewById(R.id.issue_title);
            creator_name = itemView.findViewById(R.id.creator_name);
            profile_img = itemView.findViewById(R.id.profile_image);


        }
    }

}
