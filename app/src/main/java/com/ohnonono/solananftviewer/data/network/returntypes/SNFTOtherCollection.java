package com.ohnonono.solananftviewer.data.network.returntypes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SNFTOtherCollection {

    @SerializedName("title")
    private String title;
    @SerializedName("id")
    private String id;
    @SerializedName("description")
    private String description;
    @SerializedName("collections")
    private ArrayList<OtherCollection_Collection> collection;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<OtherCollection_Collection> getCollection() {
        return collection;
    }

    public void setCollection(ArrayList<OtherCollection_Collection> collection) {
        this.collection = collection;
    }

    public static class OtherCollection_Collection {
        @SerializedName("description")
        public String description;
        @SerializedName("id")
        public String id;
        @SerializedName("name")
        public String name;
        @SerializedName("img")
        public String img;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
