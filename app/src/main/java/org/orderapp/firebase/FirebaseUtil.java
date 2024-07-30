package org.orderapp.firebase;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.orderapp.entity.Goods;
import org.orderapp.entity.Shop;
import org.orderapp.util.L;

public class FirebaseUtil {
    /**
     * 店铺列表Key
     */
    public final static String KEY_SHOP = "KEY_SHOP";
    /**
     * 用户信息
     */
    public final static String KEY_USERINF = "KEY_USERINF";

    /**
     * 订单
     */
    public static final String KEY_ORDER = "KEY_ORDER";

    private static DatabaseReference databaseReference;

    /**
     * 店铺关联
     */
    private static DatabaseReference shopRef = null;
    /**
     * 用户关联
     */
    private static DatabaseReference userRef = null;
    /**
     * 订单关联
     */
    private static DatabaseReference orderRef = null;


    public static DatabaseReference getShopRef() {
        return shopRef;
    }

    public static DatabaseReference getUserRef() {
        return userRef;
    }

    public static DatabaseReference getOrderRef() {
        return orderRef;
    }

    /**
     * 初始化FirebaseSDK
     */
    public static void initFirebaseSdk() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        shopRef = databaseReference.child(KEY_SHOP);
        userRef = databaseReference.child(KEY_USERINF);
        orderRef = databaseReference.child(KEY_ORDER);
    }


}
