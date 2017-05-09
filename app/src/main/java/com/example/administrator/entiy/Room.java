package com.example.administrator.entiy;

/**
 * Created by Administrator on 2017/2/27.
 */

public class Room {
    private String name;
    private String username;
    private String id;
    private String roomface_url;
    private String userface_url;
    private String text;
    private String onLine;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomface_url() {
        return roomface_url;
    }

    public void setRoomface_url(String roomface_url) {
        this.roomface_url = roomface_url;
    }

    public String getUserface_url() {
        return userface_url;
    }

    public void setUserface_url(String userface_url) {
        this.userface_url = userface_url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOnLine() {
        return onLine;
    }

    public void setOnLine(String onLine) {
        this.onLine = onLine;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
