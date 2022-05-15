package com.ohnonono.solananftviewer.data.network.returntypes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SNFTHomepage implements Parcelable{

    @SerializedName("top_collections")
    public ArrayList<Collection> top_collections;
    @SerializedName("hot_collections")
    public ArrayList<Collection> hot_collections;
    @SerializedName("movers_collections")
    public ArrayList<Collection> movers_collections;
    @SerializedName("other_collections")
    public ArrayList<OtherCollections> other_collections;
    @SerializedName("cool_items")
    public ArrayList<SNFTCollectionMint> cool_collections;


    protected SNFTHomepage(Parcel in) {
        top_collections = in.createTypedArrayList(Collection.CREATOR);
        hot_collections = in.createTypedArrayList(Collection.CREATOR);
        movers_collections = in.createTypedArrayList(Collection.CREATOR);
        other_collections = in.createTypedArrayList(OtherCollections.CREATOR);
        cool_collections = in.createTypedArrayList(SNFTCollectionMint.CREATOR);
    }

    public static final Creator<SNFTHomepage> CREATOR = new Creator<SNFTHomepage>() {
        @Override
        public SNFTHomepage createFromParcel(Parcel in) {
            return new SNFTHomepage(in);
        }

        @Override
        public SNFTHomepage[] newArray(int size) {
            return new SNFTHomepage[size];
        }
    };

    public ArrayList<SNFTCollectionMint> getCool_collection() {
        return cool_collections;
    }

    public void setCool_collection(ArrayList<SNFTCollectionMint> cool_collections) {
        this.cool_collections = cool_collections;
    }

    public ArrayList<Collection> getTop_collections() {
        return top_collections;
    }

    public void setTop_collections(ArrayList<Collection> top_collections) {
        this.top_collections = top_collections;
    }

    public ArrayList<Collection> getHot_collections() {
        return hot_collections;
    }

    public void setHot_collections(ArrayList<Collection> hot_collections) {
        this.hot_collections = hot_collections;
    }

    public ArrayList<Collection> getMovers_collections() {
        return movers_collections;
    }

    public void setMovers_collections(ArrayList<Collection> movers_collections) {
        this.movers_collections = movers_collections;
    }

    public ArrayList<OtherCollections> getOther_collections() {
        return other_collections;
    }

    public void setOther_collections(ArrayList<OtherCollections> other_collections) {
        this.other_collections = other_collections;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(top_collections);
        parcel.writeTypedList(hot_collections);
        parcel.writeTypedList(movers_collections);
        parcel.writeTypedList(other_collections);
        parcel.writeTypedList(cool_collections);
    }

    public static class Collection implements Parcelable {
        @SerializedName("project_id")
        private String project_id;
        @SerializedName("market_cap")
        private long market_cap;
        @SerializedName("volume_7day")
        private long volume_7day;
        @SerializedName("volume_1day_change")
        private double volume_1day_change;
        @SerializedName("floor_price")
        private double floor_price;
        @SerializedName("average_price")
        private double average_price;
        @SerializedName("average_price_1day_change")
        private double average_price_1day_change;
        @SerializedName("max_price")
        private double max_price;
        @SerializedName("twitter_followers")
        private int twitter_followers;
        @SerializedName("supply")
        private int supply;
        @SerializedName("name")
        private String name;
        @SerializedName("img")
        private String img;

        protected Collection(Parcel in) {
            project_id = in.readString();
            market_cap = in.readLong();
            volume_7day = in.readLong();
            volume_1day_change = in.readDouble();
            floor_price = in.readDouble();
            average_price = in.readDouble();
            average_price_1day_change = in.readDouble();
            max_price = in.readDouble();
            twitter_followers = in.readInt();
            supply = in.readInt();
            name = in.readString();
            img = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(project_id);
            dest.writeLong(market_cap);
            dest.writeLong(volume_7day);
            dest.writeDouble(volume_1day_change);
            dest.writeDouble(floor_price);
            dest.writeDouble(average_price);
            dest.writeDouble(average_price_1day_change);
            dest.writeDouble(max_price);
            dest.writeInt(twitter_followers);
            dest.writeInt(supply);
            dest.writeString(name);
            dest.writeString(img);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Collection> CREATOR = new Creator<Collection>() {
            @Override
            public Collection createFromParcel(Parcel in) {
                return new Collection(in);
            }

            @Override
            public Collection[] newArray(int size) {
                return new Collection[size];
            }
        };

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

        public String getProject_id() {
            return project_id;
        }

        public void setProject_id(String project_id) {
            this.project_id = project_id;
        }

        public long getMarket_cap() {
            return market_cap;
        }

        public void setMarket_cap(long market_cap) {
            this.market_cap = market_cap;
        }

        public long getVolume_7day() {
            return volume_7day;
        }

        public void setVolume_7day(long volume_7day) {
            this.volume_7day = volume_7day;
        }

        public double getVolume_1day_change() {
            return volume_1day_change;
        }

        public void setVolume_1day_change(double volume_1day_change) {
            this.volume_1day_change = volume_1day_change;
        }

        public double getFloor_price() {
            return floor_price;
        }

        public void setFloor_price(double floor_price) {
            this.floor_price = floor_price;
        }

        public double getAverage_price() {
            return average_price;
        }

        public void setAverage_price(double average_price) {
            this.average_price = average_price;
        }

        public double getAverage_price_1day_change() {
            return average_price_1day_change;
        }

        public void setAverage_price_1day_change(double average_price_1day_change) {
            this.average_price_1day_change = average_price_1day_change;
        }

        public double getMax_price() {
            return max_price;
        }

        public void setMax_price(double max_price) {
            this.max_price = max_price;
        }

        public int getTwitter_followers() {
            return twitter_followers;
        }

        public void setTwitter_followers(int twitter_followers) {
            this.twitter_followers = twitter_followers;
        }

        public int getSupply() {
            return supply;
        }

        public void setSupply(int supply) {
            this.supply = supply;
        }


    }
    public static class OtherCollections implements Parcelable {
        @SerializedName("title")
        public String title;
        @SerializedName("id")
        public String id;
        @SerializedName("collections")
        public ArrayList<DescriptiveCollection> collections;

        protected OtherCollections(Parcel in) {
            title = in.readString();
            id = in.readString();
            collections = in.createTypedArrayList(DescriptiveCollection.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeString(id);
            dest.writeTypedList(collections);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<OtherCollections> CREATOR = new Creator<OtherCollections>() {
            @Override
            public OtherCollections createFromParcel(Parcel in) {
                return new OtherCollections(in);
            }

            @Override
            public OtherCollections[] newArray(int size) {
                return new OtherCollections[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public ArrayList<DescriptiveCollection> getCollections() {
            return collections;
        }

        public void setCollections(ArrayList<DescriptiveCollection> collections) {
            this.collections = collections;
        }

    }
    public static class DescriptiveCollection implements Parcelable {
        @SerializedName("description")
        private String description;
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("img")
        private String img;

        protected DescriptiveCollection(Parcel in) {
            description = in.readString();
            id = in.readString();
            name = in.readString();
            img = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(description);
            dest.writeString(id);
            dest.writeString(name);
            dest.writeString(img);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<DescriptiveCollection> CREATOR = new Creator<DescriptiveCollection>() {
            @Override
            public DescriptiveCollection createFromParcel(Parcel in) {
                return new DescriptiveCollection(in);
            }

            @Override
            public DescriptiveCollection[] newArray(int size) {
                return new DescriptiveCollection[size];
            }
        };

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
