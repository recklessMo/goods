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
            new Menu("教务站", "学生管理", "ROLE_USER", "", "app.student", 1),
            new Menu("教务站", "成绩管理", "ROLE_USER", "", "app.grade", 2),
            new Menu("教务站", "教师管理", "ROLE_USER", "", "app.teacher", 3),
            new Menu("教务站", "排班管理", "ROLE_USER", "", "app.duty", 4),


            new Menu("财务站", "缴费", "", "ROLE_USER", "app.pay", 1),
            new Menu("财务站", "收费统计", "ROLE_USER", "", "app.payStat", 2),
            new Menu("财务站", "报销统计", "ROLE_USER", "", "app.expense", 3),

            new Menu("库存站", "库存查看", "ROLE_USER", "", "app.stock", 1),
            new Menu("库存站", "入库查看", "ROLE_USER", "", "app.stockIn", 2),
            new Menu("库存站", "出库查看", "ROLE_USER", "", "app.stockOut", 3),

            new Menu("管理站", "帐户设置", "ROLE_USER", "", "app.account", 1),
            new Menu("管理站", "教务参数", "ROLE_USER", "", "app.setting", 1)
    };

    public static Menu[] menuListTest = {
            //定义一级菜单
            new Menu(null, "教务站", null, "icon-book-open", null, 1),
            new Menu(null, "成绩站", null, "icon-book-open", null, 2),
            new Menu(null, "财务站", null, "icon-notebook", null, 3),
            new Menu(null, "库存站", "ROLE_USER", "icon-basket-loaded", "app.stock-worktable", 4),
            new Menu(null, "管理站", null, "icon-settings", null, 5),
            new Menu(null, "个人中心", null, "icon-info", null, 6),

            //定义二级菜单
            new Menu("教务站", "学生查询", "ROLE_USER", "icon-people", "app.student-view-worktable", 3),
            new Menu("教务站", "学生录入", "ROLE_USER", "icon-user-follow", "app.student-add-worktable", 4),
            new Menu("教务站", "学生修改", "ROLE_USER", "icon-user-follow", "app.student-view-worktable", 5),
            //用于分班,升级,以及毕业等,可以批量管理,快速的进行分班,可以设定排序规则进行成绩打印,期中期末的时候需要成绩打印
            new Menu("教务站", "批量修改", "ROLE_USER", "icon-user-follow", "app.student-edit-worktable", 6),

            new Menu("财务站", "缴费", "ROLE_USER", "icon-plus", "app.finance", 1),
            new Menu("财务站", "报销", "ROLE_USER", "icon-check", "app.finance", 2),
            new Menu("财务站", "财物统计", "ROLE_USER", "icon-wallet", "app.payStat", 3),

            new Menu("管理站", "帐号管理", "ROLE_USER", "icon-user-female", "app.account", 1),
            new Menu("管理站", "常规设置", "ROLE_USER", "icon-wrench", "app.account", 2),
            new Menu("管理站", "教务设置", "ROLE_USER", "icon-wrench", "app.edu-setting", 3),

            new Menu("个人中心", "我的课表", "ROLE_USER", "icon-info", "app.todo", 1),
            new Menu("个人中心", "日历提醒", "ROLE_USER", "icon-info", "app.account", 1),
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
