package com.marciorr.concretesolutionsproject.model;

import com.google.gson.annotations.SerializedName;

public class RetroUser {

    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String body;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("html_url")
    private String html_url;
    @SerializedName("user")
    private Owner user;

    //Inicializa as variáveis
    public RetroUser(String title, String body, String created_at, String html_url, Owner user) {
        this.title = title;
        this.body = body;
        this.created_at = created_at;
        this.html_url = html_url;
        this.user = user;
    }

    //Cria os getters e setters
   public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public Owner getUser() {
        return user;
    }

    public void setUser(Owner user) {
        this.user = user;
    }

    public class Owner {
        public String login;
        public String avatar_url;

        public String getLogin() {
            return login;
        }

        public String getAvatar_url() {
            return avatar_url;
        }
    }
}
