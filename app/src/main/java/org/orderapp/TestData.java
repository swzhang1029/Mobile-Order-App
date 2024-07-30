package org.orderapp;

import org.orderapp.entity.Goods;
import org.orderapp.entity.Shop;
import org.orderapp.firebase.FirebaseUtil;
import org.orderapp.util.L;
import org.orderapp.util.ToastUtil;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 生成测试数据
 */
public class TestData {
    private static final String[] FOOD_NAMES = {
            "汉堡包", "披萨", "寿司", "炸鸡", "沙拉", "意大利面", "牛排", "饺子", "烤鱼", "炒饭"
    };
    private static final String[] CHINESE_FOOD_NAMES = {
            "炒鸡蛋", "宫保鸡丁", "鱼香肉丝", "红烧肉", "北京炸酱面", "麻辣香锅", "酸辣汤", "葱爆羊肉", "回锅肉", "麻婆豆腐"
    };
    private static Random random = new Random();
    private static NumberFormat format = NumberFormat.getInstance();

    /**
     * 生成商品数据
     */
    public static void genGoodsData() {
        format.setMaximumFractionDigits(2);

        addShop("Fast food", FOOD_NAMES);
        addShop("中餐馆", CHINESE_FOOD_NAMES);
    }

    private static void addShop(String shopName, String[] FOOD_NAMES) {
        Shop shop = new Shop();
        shop.setName(shopName);
        List<Goods> list = new ArrayList<>();
        for (int i = 0; i < FOOD_NAMES.length; i++) {
            Goods food = new Goods();
            food.setName(FOOD_NAMES[random.nextInt(FOOD_NAMES.length)]);
            // 设置价格在5到55之间
            food.setPrice(Double.parseDouble(format.format(random.nextDouble() * 50 + 5)));
            food.setSale(random.nextInt(5000));
            food.setStar(Double.parseDouble(format.format(random.nextDouble() + 3)));
            list.add(food);
        }
        shop.setGoods(list);
        FirebaseUtil.getShopRef().push()
                .setValue(shop)
                .addOnSuccessListener(unused -> L.d("添加成功"))
                .addOnFailureListener(e -> L.d("添加失败"));
    }
}
