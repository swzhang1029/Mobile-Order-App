package org.orderapp.entity;

import java.util.HashMap;
import java.util.Map;

public class Order {
    /**
     * 对应服务器的key
     */
    private String key;
    /**
     * 订单编号
     */
    private String ordreNumber;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 商品详细
     */
    private String content;
    /**
     * 总价
     */
    private double totalPrice;
    /**
     * 下单时间
     */
    private long orderTime;

    private String userUUid;

    /**
     * 桌号
     */
    private String tableNum;

    private int comment = 0;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOrdreNumber() {
        return ordreNumber;
    }

    public void setOrdreNumber(String ordreNumber) {
        this.ordreNumber = ordreNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public String getUserUUid() {
        return userUUid;
    }

    public void setUserUUid(String userUUid) {
        this.userUUid = userUUid;
    }

    public String getTableNum() {
        return tableNum;
    }

    public void setTableNum(String tableNum) {
        this.tableNum = tableNum;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public  Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("ordreNumber",getOrdreNumber());
        map.put("shopName",getShopName());
        map.put("content",getContent());
        map.put("totalPrice",getTotalPrice());
        map.put("orderTime",getOrderTime());
        map.put("userUUid",getUserUUid());
        map.put("tableNum",getTableNum());
        map.put("comment",getComment());

        return map;
    }
}
