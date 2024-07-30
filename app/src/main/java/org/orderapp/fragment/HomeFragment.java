package org.orderapp.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.orderapp.APP;
import org.orderapp.R;
import org.orderapp.activity.OrderActivity;
import org.orderapp.activity.ShopActivity;
import org.orderapp.adapter.ShopAdapter;
import org.orderapp.entity.ScanResult;
import org.orderapp.entity.Shop;
import org.orderapp.firebase.FirebaseUtil;
import org.orderapp.fragment.base.BaseFragment;
import org.orderapp.util.JsonUtil;
import org.orderapp.util.L;
import org.orderapp.util.LocationUtil;
import org.orderapp.util.ToastUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment {


    private String key = "";

    private EditText et_search;

    /**
     * 店铺列表UI
     */
    private RecyclerView recyclerView;
    /**
     * 店铺列表数据
     */
    private List<Shop> allShopList;
    /**
     * 店铺列表适配器
     */
    private ShopAdapter shopAdapter;

    private TextView tv_sale;
    private TextView tv_distance;
    private int sort = 1;

    private Typeface boldTypeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
    private Typeface normalTypeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, null);

            tv_sale = view.findViewById(R.id.tv_sale);
            tv_distance = view.findViewById(R.id.tv_distance);

            tv_sale.setOnClickListener(this::onClick);
            tv_distance.setOnClickListener(this::onClick);

            view.findViewById(R.id.tv_title).setOnClickListener(v -> getData());

            et_search = view.findViewById(R.id.et_search);
            et_search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    key = s.toString().trim();
                    notifyList();
                }
            });

            initList();
            getData();

            setTitle("Recommend");
            setRightAction(R.drawable.baseline_qr_code_scanner_2);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_right:
                if (isLogin()) {
                    barcodeLauncher.launch(new ScanOptions());
                }
                break;
            case R.id.tv_sale:
                sort(1);
                break;
            case R.id.tv_distance:
                sort(2);
                break;
            default:
                break;
        }
    }


    /**
     * 初始化列表适配器
     */
    private void initList() {
        recyclerView = view.findViewById(R.id.rv_list);
        allShopList = new ArrayList<>();

        shopAdapter = new ShopAdapter();
        shopAdapter.setListener(new ShopAdapter.Listener() {
            @Override
            public void itemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", shopAdapter.getData(position));
                gotoActivity(ShopActivity.class, bundle);
            }

            @Override
            public void navigation(int position) {
                Shop shop = shopAdapter.getData(position);
                LocationUtil.loadNavigationView(getActivity(), shop.getLat(), shop.getLng());
            }
        });
        //配置适配器
        recyclerView.setAdapter(shopAdapter);
        //配置线性布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getData() {
        shopAdapter.clearData();
        FirebaseUtil.getShopRef()
                .get()
                .addOnCompleteListener(task -> {
                    allShopList.clear();
                    for (DataSnapshot child : task.getResult().getChildren()) {
                        Shop shop = child.getValue(Shop.class);
                        shop.setKey(child.getKey());
                        allShopList.add(shop);
                    }
                    notifyList();
                });
//        //添加数据监听
//        FirebaseUtil.getShopRef().addChildEventListener(new ChildEventListener() {
//            /**
//             * 监听是否有新添加数据
//             * @param snapshot An immutable snapshot of the data at the new child location
//             * @param previousChildName The key name of sibling location ordered before the new child. This
//             *     will be null for the first child node of a location.
//             */
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                if (snapshot.getValue() != null) {
//                    allShopList.add(Shop.snapShotToBean(snapshot));
//                    notifyList();
//                }
//            }
//
//            /**
//             * 监听是否有数据变动
//             * @param snapshot An immutable snapshot of the data at the new data at the child location
//             * @param previousChildName The key name of sibling location ordered before the child. This will
//             *     be null for the first child node of a location.
//             */
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                if (snapshot.getValue() != null) {
//                    //这里处理服务器修改了数据，自动更新APP列表
//                    Shop shop = Shop.snapShotToBean(snapshot);
//                    for (Shop oldData : allShopList) {
//                        if (shop.getKey().equals(oldData.getKey())) {
//                            Shop.copy(oldData, shop);
//                            notifyList();
//                            return;
//                        }
//                    }
//                }
//            }
//
//            /**
//             * 监听是否有数据删除
//             * @param snapshot An immutable snapshot of the data at the child that was removed.
//             */
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                if (snapshot.getValue() != null) {
//                    //这里处理服务器删除了数据，自动更新APP列表
//                    Shop shop = Shop.snapShotToBean(snapshot);
//                    //列表当前的数据，找出服务器删除的联系人
//                    for (int i = 0; i < allShopList.size(); i++) {
//                        Shop oldData = allShopList.get(i);
//                        if (shop.getKey().equals(oldData.getKey())) {
//                            allShopList.remove(i);
//                            notifyList();
//                            return;
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                L.d(error.getMessage());
//            }
//        });
    }


    /**
     * 刷新列表
     */
    private void notifyList() {
        if (!isAdded()) {
            return;
        }

        List<Shop> shops = new ArrayList<>();
        //过滤关键词
        for (Shop shop : allShopList) {
            if (contains(shop.getName(), key)) {
                shops.add(shop);
            }
        }
        //计算距离
        if (APP.lat != 0 && APP.lng != 0) {
            for (Shop shop : shops) {
                shop.setDistance(LocationUtil.getDistance(APP.lat, APP.lng, shop.getLat(), shop.getLng()) / 1000.0);
            }
        }
        //排序
        switch (sort) {
            case 2:
                shops = shops.stream().sorted(Comparator.comparing(Shop::getDistance)).collect(Collectors.toList());
                break;
            default:
                shops = shops.stream().sorted(Comparator.comparing(Shop::getSale).reversed()).collect(Collectors.toList());
                break;
        }

        shopAdapter.clearData();
        shopAdapter.insertAll(shops);
    }

    private boolean contains(String str, String key) {
        if (TextUtils.isEmpty(key)) {
            return true;
        }
        return str.toLowerCase().contains(key.toLowerCase());
    }

    // Register the launcher and result handler
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() == null) {
                    ToastUtil.show("Cancelled");
                } else {
                    L.d(result.getContents());
                    //解释识别的字符串
                    ScanResult scanResult = JsonUtil.fromJson(result.getContents(), ScanResult.class);

                    //匹配扫码的店铺
                    List<Shop> shops = shopAdapter.getDatas();
                    for (Shop shop : shops) {
                        if (shop.getKey().equals(scanResult.getShop())) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("data", shop);
                            bundle.putInt(OrderActivity.KEY_TABLE_NUM, scanResult.getTableNum());
                            gotoActivity(OrderActivity.class, bundle);
                            return;
                        }
                    }
                }
            });

    /**
     * @param type 1.销量优先 2.距离优先
     */
    private void sort(int type) {
        this.sort = type;
        tv_sale.setTypeface(normalTypeface);
        tv_distance.setTypeface(normalTypeface);

        switch (sort) {
            case 2:
                tv_distance.setTypeface(boldTypeface);
                break;
            default:
                tv_sale.setTypeface(boldTypeface);
                break;
        }

        notifyList();
    }
}
