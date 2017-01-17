package com.recklessmo.model.user;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 4/18/16.
 */
public class UserVO {

    public static int MALE = 0;
    public static int FEMALE = 1;

    private long id;
    private String userName;
    private String pwd;
    private String name;
    private String phone;
    private String email = "";
    private int gender;
    private String job = "";
    private List<Integer> authorities = new LinkedList<>();
    private Date created;


    public static int getMALE() {
        return MALE;
    }

    public static void setMALE(int MALE) {
        UserVO.MALE = MALE;
    }

    public static int getFEMALE() {
        return FEMALE;
    }

    public static void setFEMALE(int FEMALE) {
        UserVO.FEMALE = FEMALE;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public List<Integer> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Integer> authorities) {
        this.authorities = authorities;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
