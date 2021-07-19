package com.mk.githubbrowser.Model;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FavRepoData extends RealmObject {
    String repo_name;
    String repo_des;
    String repo_url;
    String repo_branch;
    String repo_issue;
    String repo_commit;

    public FavRepoData() {
    }

    public FavRepoData(String id,String repo_name, String repo_des, String repo_url, String repo_branch, String repo_issue, String repo_commit) {
        this.repo_name = repo_name;
        this.repo_des = repo_des;
        this.repo_url = repo_url;
        this.repo_branch = repo_branch;
        this.repo_issue = repo_issue;
        this.repo_commit = repo_commit;
    }



    public String getRepo_name() {
        return repo_name;
    }

    public void setRepo_name(String repo_name) {
        this.repo_name = repo_name;
    }

    public String getRepo_des() {
        return repo_des;
    }

    public void setRepo_des(String repo_des) {
        this.repo_des = repo_des;
    }

    public String getRepo_url() {
        return repo_url;
    }

    public void setRepo_url(String repo_url) {
        this.repo_url = repo_url;
    }

    public String getRepo_branch() {
        return repo_branch;
    }

    public void setRepo_branch(String repo_branch) {
        this.repo_branch = repo_branch;
    }

    public String getRepo_issue() {
        return repo_issue;
    }

    public void setRepo_issue(String repo_issue) {
        this.repo_issue = repo_issue;
    }

    public String getRepo_commit() {
        return repo_commit;
    }

    public void setRepo_commit(String repo_commit) {
        this.repo_commit = repo_commit;
    }
}
