package com.recklessmo.manage.menu;

import java.util.List;

/**
 *
 * 返回menu菜单
 *
 * Created by hpf on 4/13/16.
 */
public class Menu{

    public static Menu[] menuList = {
            //定义一级菜单
            new Menu(null, "教务站", "", "", "app.study", 1),
            new Menu(null, "财务站", "", "", "app.finance", 2),
            new Menu(null, "库存站", "", "", "app.stock", 3),
            new Menu(null, "管理站", "", "", "app.manage", 4),
            new Menu(null, "消息站", "", "", "app.message", 5),
            new Menu(null, "后勤站", "", "", "app.rear", 6),

            //定义二级菜单
            new Menu("教务站", "学生管理", "", "", "app.student", 1),
            new Menu("教务站", "成绩管理", "", "", "app.grade", 2),
            new Menu("教务站", "教师管理", "", "", "app.teacher", 3),
            new Menu("教务站", "排班管理", "", "", "app.duty", 4),

            new Menu("财务站", "缴费", "", "", "app.pay", 1),
            new Menu("财务站", "收费统计", "", "", "app.payStat", 2),
            new Menu("财务站", "报销统计", "", "", "app.expense", 3),

            new Menu("库存站", "库存查看", "", "", "app.stock", 1),
            new Menu("库存站", "入库查看", "", "", "app.stockIn", 2),
            new Menu("库存站", "出库查看", "", "", "app.stockOut", 3),

            new Menu("管理站", "帐户管理", "", "", "app.account", 1),
            new Menu("管理站", "参数设置", "", "", "app.setting", 1)
    };

    public static Menu[] menuListTest = {
            //定义一级菜单
            new Menu(null, "jwz", null, "icon-speedometer", null, 1),
            new Menu(null, "cwz", null, null, null, 2),
            new Menu(null, "glz", null, "icon-map", null, 4),
            new Menu(null, "zsz", null, "icon-note", "app.navtree", 5),

            //定义二级菜单
            new Menu("jwz", "xsgl", "ROLE_USER", "icon-graph", "app.nestable", 1),
            new Menu("jwz", "cjgl", "", null, "app.teacher", 2),
            new Menu("jwz", "jsgl", "", null, "app.duty", 3),

            new Menu("cwz", "jf", "", null, "app.finance", 1),
            new Menu("cwz", "jftj", "", null, "app.payStat", 2),
            new Menu("cwz", "bxtj", "", null, "app.expense", 3),

            new Menu("glz", "zhgl", "ROLE_USER", "icon-grid", "app.account", 1),
    };



    public String father;
    public String child;
    private String roles;
    private String icon;
    private String href;
    private int weight;

    public Menu(){

    }

    public Menu(String father, String child, String roles, String icon, String href, int weight){
        this.father = father;
        this.child = child;
        this.roles = roles;
        this.icon = icon;
        this.href = href;
        this.weight = weight;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
