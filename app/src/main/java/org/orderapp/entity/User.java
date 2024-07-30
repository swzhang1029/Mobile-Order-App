package org.orderapp.entity;

/**
 * 用户信息
 */
public class User {
    /**
     * 对应服务器的key
     */
    private String uuid;
    private String account;
    private String password;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
