package com.learn.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class AccoountMap implements Serializable {
    private String username;
    private String password;
    private Double money;
    private List<User> userList;
    private Map<String, User> map;

    @Override
    public String toString() {
        return "AccoountMap{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", money=" + money +
                ", userList=" + userList +
                ", map=" + map +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Map<String, User> getMap() {
        return map;
    }

    public void setMap(Map<String, User> map) {
        this.map = map;
    }
}
