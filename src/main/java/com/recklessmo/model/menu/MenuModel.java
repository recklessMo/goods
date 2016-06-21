package com.recklessmo.model.menu;

import java.util.List;

/**
 * Created by hpf on 4/13/16.
 */
public class MenuModel {
    private String text;
    private String sref;
    private String icon;
    private int weight;

    private List<MenuModel> submenu;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSref() {
        return sref;
    }

    public void setSref(String sref) {
        this.sref = sref;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuModel> getSubmenu() {
        return submenu;
    }

    public void setSubmenu(List<MenuModel> submenu) {
        this.submenu = submenu;
    }
}
