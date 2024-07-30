package org.orderapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 商品
 */
public class Goods implements Parcelable {
    /**
     * 商品名称
     */
    private String name;
    /**
     * 价格
     */
    private double price;
    /**
     * 数量
     */
    private int num = 0;
    /**
     * 评分
     */
    private double star;
    /**
     * 销量
     */
    private int sale;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeDouble(this.price);
        dest.writeInt(this.num);
        dest.writeDouble(this.star);
        dest.writeInt(this.sale);
    }

    public void readFromParcel(Parcel source) {
        this.name = source.readString();
        this.price = source.readDouble();
        this.num = source.readInt();
        this.star = source.readDouble();
        this.sale = source.readInt();
    }

    public Goods() {
    }

    protected Goods(Parcel in) {
        this.name = in.readString();
        this.price = in.readDouble();
        this.num = in.readInt();
        this.star = in.readDouble();
        this.sale = in.readInt();
    }

    public static final Creator<Goods> CREATOR = new Creator<Goods>() {
        @Override
        public Goods createFromParcel(Parcel source) {
            return new Goods(source);
        }

        @Override
        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };
}
