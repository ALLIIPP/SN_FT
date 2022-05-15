package com.ohnonono.solananftviewer.data.network.returntypes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SNFTCollectionMint implements Parcelable{

    @SerializedName("crawl_id")
    private String crawl_id;
    @SerializedName("mint")
    private String mint;
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;
    @SerializedName("created")
    private long created;
    @SerializedName("rank")
    private int rank;
    @SerializedName("rarity")
    private float rarity;
    @SerializedName("price")
    private double price;
    @SerializedName("price_USD")
    private String price_USD;
    @SerializedName("market_name")
    private String market_name;
    @SerializedName("rank_explain")
    private ArrayList<RankExplain> rank_explain;
    @SerializedName("lilinfo")
    private Lilinfo lilinfo;

    protected SNFTCollectionMint(Parcel in) {
        crawl_id = in.readString();
        mint = in.readString();
        name = in.readString();
        image = in.readString();
        created = in.readLong();
        rank = in.readInt();
        rarity = in.readFloat();
        price = in.readDouble();
        price_USD = in.readString();
        market_name = in.readString();
        rank_explain = in.createTypedArrayList(RankExplain.CREATOR);
        lilinfo = in.readParcelable(Lilinfo.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(crawl_id);
        dest.writeString(mint);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeLong(created);
        dest.writeInt(rank);
        dest.writeFloat(rarity);
        dest.writeDouble(price);
        dest.writeString(price_USD);
        dest.writeString(market_name);
        dest.writeTypedList(rank_explain);
        dest.writeParcelable(lilinfo, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SNFTCollectionMint> CREATOR = new Creator<SNFTCollectionMint>() {
        @Override
        public SNFTCollectionMint createFromParcel(Parcel in) {
            return new SNFTCollectionMint(in);
        }

        @Override
        public SNFTCollectionMint[] newArray(int size) {
            return new SNFTCollectionMint[size];
        }
    };

    public Lilinfo getLilinfo() {
        return lilinfo;
    }

    public String getPrice_USD() {
        return price_USD;
    }

    public void setPrice_USD(String price_USD) {
        this.price_USD = price_USD;
    }

    public void setLilinfo(Lilinfo lilinfo) {
        this.lilinfo = lilinfo;
    }

    public String getMarket_name() {
        return market_name;
    }

    public void setMarket_name(String market_name) {
        this.market_name = market_name;
    }

    public String getCrawl_id() {
        return crawl_id;
    }

    public void setCrawl_id(String crawl_id) {
        this.crawl_id = crawl_id;
    }

    public String getMint() {
        return mint;
    }

    public void setMint(String mint) {
        this.mint = mint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public float getRarity() {
        return rarity;
    }

    public void setRarity(float rarity) {
        this.rarity = rarity;
    }

    public ArrayList<RankExplain> getRank_explain() {
        return rank_explain;
    }

    public void setRank_explain(ArrayList<RankExplain> rank_explain) {
        this.rank_explain = rank_explain;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static class RankExplain implements Parcelable{
        @SerializedName("attribute")
        private String attribute;
        @SerializedName("value")
        private String value;
        @SerializedName("value_perc")
        private double value_perc;
        @SerializedName("times_seen")
        private int times_seen;
        @SerializedName("total_seen")
        private int total_seen;

        protected RankExplain(Parcel in) {
            attribute = in.readString();
            value = in.readString();
            value_perc = in.readDouble();
            times_seen = in.readInt();
            total_seen = in.readInt();
        }

        public static final Creator<RankExplain> CREATOR = new Creator<RankExplain>() {
            @Override
            public RankExplain createFromParcel(Parcel in) {
                return new RankExplain(in);
            }

            @Override
            public RankExplain[] newArray(int size) {
                return new RankExplain[size];
            }
        };

        public String getAttribute() {
            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public double getValue_perc() {
            return value_perc;
        }

        public void setValue_perc(double value_perc) {
            this.value_perc = value_perc;
        }

        public int getTimes_seen() {
            return times_seen;
        }

        public void setTimes_seen(int times_seen) {
            this.times_seen = times_seen;
        }

        public int getTotal_seen() {
            return total_seen;
        }

        public void setTotal_seen(int total_seen) {
            this.total_seen = total_seen;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(attribute);
            parcel.writeString(value);
            parcel.writeDouble(value_perc);
            parcel.writeInt(times_seen);
            parcel.writeInt(total_seen);
        }
    }

    public static class Lilinfo implements Parcelable {
        @SerializedName("name")
        private String name;
        @SerializedName("img")
        private String img;
        @SerializedName("description")
        private String description;

        protected Lilinfo(Parcel in) {
            name = in.readString();
            img = in.readString();
            description = in.readString();
        }

        public static final Creator<Lilinfo> CREATOR = new Creator<Lilinfo>() {
            @Override
            public Lilinfo createFromParcel(Parcel in) {
                return new Lilinfo(in);
            }

            @Override
            public Lilinfo[] newArray(int size) {
                return new Lilinfo[size];
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(name);
            parcel.writeString(img);
            parcel.writeString(description);
        }
    }

}
