package com.ohnonono.solananftviewer.misc;

public class FollowsPojo {
    private String link; //
    private String name; // twitter -> @xxxxxxx // disc -> XXXX XXXXX
    private String type;

    public FollowsPojo(String link, String name, String type) {
        this.link = link;
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
