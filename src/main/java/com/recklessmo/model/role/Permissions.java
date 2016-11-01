package com.recklessmo.model.role;

/**
 *
 * 权限, 目前暂定权限对应着二级小菜单
 *
 * Created by hpf on 10/21/16.
 */
public class Permissions {

    private long id;
    private String name;

    public Permissions(){

    }

    public Permissions(long id, String name){
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
