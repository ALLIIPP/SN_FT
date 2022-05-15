package com.ohnonono.solananftviewer.data.network.returntypes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SNFTCollectionInfo {
        @SerializedName("id")
        private String id;
        @SerializedName("image")
        private String image;
        @SerializedName("name")
        private String name;
        @SerializedName("description")
        private String description;
        @SerializedName("first_mint")
        private String first_mint;
        @SerializedName("last_mint")
        private String last_mint;
        @SerializedName("first_mint_ts")
        private long first_mint_ts;
        @SerializedName("last_mint_ts")
        private long last_mint_ts;
        @SerializedName("total_mints")
        private int total_mints;
        @SerializedName("attrib_count")
        private ArrayList<NFTAttribute> attrib_count;
        @SerializedName("attrib_list")
        private ArrayList<String> attrib_titles;

    public ArrayList<String> getAttrib_titles() {
        return attrib_titles;
    }

    public void setAttrib_titles(ArrayList<String> attrib_titles) {
        this.attrib_titles = attrib_titles;
    }

    public int getTotal_mints() {
        return total_mints;
    }

    public void setTotal_mints(int total_mints) {
        this.total_mints = total_mints;
    }

    public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFirst_mint() {
            return first_mint;
        }

        public void setFirst_mint(String first_mint) {
            this.first_mint = first_mint;
        }

        public String getLast_mint() {
            return last_mint;
        }

        public void setLast_mint(String last_mint) {
            this.last_mint = last_mint;
        }

        public long getFirst_mint_ts() {
            return first_mint_ts;
        }

        public void setFirst_mint_ts(long first_mint_ts) {
            this.first_mint_ts = first_mint_ts;
        }

        public long getLast_mint_ts() {
            return last_mint_ts;
        }

        public void setLast_mint_ts(long last_mint_ts) {
            this.last_mint_ts = last_mint_ts;
        }

    public ArrayList<NFTAttribute> getAttrib_count() {
        return attrib_count;
    }

    public void setAttrib_count(ArrayList<NFTAttribute> attrib_count) {
        this.attrib_count = attrib_count;
    }

    public static class NFTAttribute {
        @SerializedName("attribute")
        private String attribute;
        @SerializedName("type")
        private String type;
        @SerializedName("count")
        private int count;

        public String getAttribute() {

            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

}
