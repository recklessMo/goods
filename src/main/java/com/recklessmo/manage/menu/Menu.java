package com.recklessmo.manage.menu;

/**
 *
 * 返回menu菜单
 *
 * Created by hpf on 4/13/16.
 */
public class Menu{

    public static Menu[] menuList = {
            new Menu(100, null, "教务站", "icon-book-open", null),
            new Menu(200, null, "成绩站", "icon-book-open", null),
            new Menu(300, null, "统计站", "icon-book-open", null),
            new Menu(400, null, "库存站", "icon-basket-loaded", null),
            new Menu(500, null, "管理站", "icon-settings", null),
            new Menu(600, null, "个人中心", "icon-info", null),

            new Menu(101, "教务站", "学生查询", "icon-people", "app.student-view-worktable"),
            new Menu(102, "教务站", "学生录入", "icon-user-follow", "app.student-add-worktable"),

            new Menu(201, "成绩站", "考试设置", "icon-plus", "app.exam"),
//            new Menu(202, "成绩站", "分析模版", "icon-plus", "app.analyze-template"),
            new Menu(203, "成绩站", "成绩单", "icon-plus", "app.result-list"),
            new Menu(204, "成绩站", "整体分析", "icon-plus", "app.result-total"),
            new Menu(205, "成绩站", "分数段分析", "icon-plus", "app.result-gap"),
            new Menu(206, "成绩站", "名次分析", "icon-plus", "app.result-rank"),
            new Menu(207, "成绩站", "均分分析", "icon-plus", "app.result-avg"),


            new Menu(301, "统计站", "财务统计", "icon-wallet", "app.payStat"),
            new Menu(302, "统计站", "学生统计", "icon-wallet", "app.payStat"),

            new Menu(401, "库存站", "物资", "icon-plus", "app.item"),
            new Menu(402, "库存站", "出入库", "icon-check", "app.stock-worktable"),

            new Menu(501, "管理站", "帐号管理", "icon-user-female", "app.account"),
            new Menu(502, "管理站", "教务设置", "icon-wrench", "app.edu-setting"),

            new Menu(601, "个人中心", "我的课表", "icon-info", "app.todo"),
            new Menu(602, "个人中心", "日历提醒", "icon-info", "app.account"),
    };



    private long id;
    private String father;
    private String child;
    private String icon;
    private String href;

    public Menu(){

    }

    public Menu(long id, String father, String child, String icon, String href){
        this.id = id;
        this.father = father;
        this.child = child;
        this.icon = icon;
        this.href = href;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

}
