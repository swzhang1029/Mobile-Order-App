package org.orderapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DataSnapshot;

import java.util.List;

/**
 * 店铺信息
 */
public class Shop implements Parcelable {
    /**
     * 对应服务器的key
     */
    private String key;
    private String name;
    private List<Goods> goods;
    /**
     * 评分
     */
    private double star=0;
    /**
     * 销量
     */
    private int sale=0;
    private double lat=0.0;
    private double lng = 0.0;

    private double distance = 0.0;

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Shop
     *
     * @param snapshot 服务下发的数据
     */
    public static Shop snapShotToBean(DataSnapshot snapshot) {
        Shop shop = snapshot.getValue(Shop.class);
        shop.setKey(snapshot.getKey());
        return shop;
    }

    /**
     * 复制data
     *
     * @param oldData 旧数据
     * @param newData 新数据
     * @return
     */
    public static Shop copy(Shop oldData, Shop newData) {
        oldData.setName(newData.getName());
        oldData.setGoods(newData.getGoods());

        return oldData;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeTypedList(this.goods);
        dest.writeDouble(this.star);
        dest.writeInt(this.sale);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
        dest.writeDouble(this.distance);
    }

    public void readFromParcel(Parcel source) {
        this.key = source.readString();
        this.name = source.readString();
        this.goods = source.createTypedArrayList(Goods.CREATOR);
        this.star = source.readDouble();
        this.sale = source.readInt();
        this.lat = source.readDouble();
        this.lng = source.readDouble();
        this.distance = source.readDouble();
    }

    public Shop() {
    }

    protected Shop(Parcel in) {
        this.key = in.readString();
        this.name = in.readString();
        this.goods = in.createTypedArrayList(Goods.CREATOR);
        this.star = in.readDouble();
        this.sale = in.readInt();
        this.lat = in.readDouble();
        this.lng = in.readDouble();
        this.distance = in.readDouble();
    }

    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel source) {
            return new Shop(source);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };
}
