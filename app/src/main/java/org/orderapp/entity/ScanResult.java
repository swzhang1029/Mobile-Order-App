package org.orderapp.entity;

/**
 * 扫码结果
 */
public class ScanResult {
    /**
     * 商家id
     */
    private String shop;
    /**
     * 桌号
     */
    private int tableNum;

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }
}
