package com.recklessmo.manage.menu;

/**
 *
 * 返回menu菜单
 *
 * Created by hpf on 4/13/16.
 */
public class Menu{

    public static Menu[] menuList = {
            new Menu(100, null, "教务管理", "#edu", null),
            new Menu(200, null, "成绩分析", "#score", null),
//            new Menu(300, null, "统计站", "icon-book-open", null),
            new Menu(400, null, "物资管理", "#stock", null),
            new Menu(500, null, "微信工具", "#wechat", null),
//            new Menu(600, null, "财务站", "icon-settings", null),
//            new Menu(700, null, "个人中心", "icon-info", null),
            new Menu(800, null, "学校设置", "#admin", null),
            new Menu(10000, null, "超管站", "#admin", null),


            new Menu(101, "教务管理", "学生查询", "#searchStudent", "app.student-view-worktable"),
            //选择考试, 然后进行成绩趋势分析
//            new Menu(102, "教务站", "个人成绩趋势", "icon-user-follow", "app.student-score-trend"),
            new Menu(103, "教务管理", "录入导出", "#importExport", "app.student-add-worktable"),
//            new Menu(103, "教务站", "奖惩录入", "icon-user-follow", "app.student-reward"),
//            new Menu(104, "教务站", "毕业去向", "icon-user-follow", "app.student-graduate"),
//            new Menu(105, "教务站", "任课设置", "icon-user-follow", "app.student-teacher"),
//            new Menu(106, "教务站", "课表编排", "icon-user-follow", "app.class-schedule"),


            new Menu(201, "成绩分析", "考试设置", "#score", "app.exam"),//done
//            new Menu(202, "成绩站", "分析模版", "icon-plus", "app.analyze-template"),
            //成绩单点击可以写评语. 评语跟随成绩
            new Menu(203, "成绩分析", "成绩单生成", "#score", "app.result-list"),//half-done
            //雷达图
            new Menu(204, "成绩分析", "整体情况分析", "#score", "app.result-total"),//done
            new Menu(205, "成绩分析", "分数点阵图", "#score", "app.result-score-point"),//done
            new Menu(206, "成绩分析", "名次点阵图", "#score", "app.result-rank-point"),//done
            new Menu(207, "成绩分析", "分数段分析", "#score", "app.result-gap"),//done
            new Menu(208, "成绩分析", "名次构成分析", "#score", "app.result-rank"),//done
            new Menu(209, "成绩分析", "进退步分析", "#score", "app.result-rankchange"),
            new Menu(210, "成绩分析", "成绩趋势分析", "#score", "app.result-trend"),
            new Menu(211, "成绩分析", "单次成绩对比", "#score", "app.result-self"),
            new Menu(212, "成绩分析", "综合成绩对比", "#score", "app.result-contrast"),
            new Menu(213, "成绩分析", "缺考人员统计", "#score", "app.result-absense"),
//            new Menu(301, "统计站", "财务统计", "icon-wallet", "app.payStat"),
//            new Menu(302, "统计站", "学生统计", "icon-wallet", "app.payStat"),
//            new Menu(303, "统计站", "微信统计", "icon-wallet", "app.wechatStat"),
//            new Menu(304, "统计站", "成本统计", "icon-wallet", "app.wechatStat"),

            new Menu(401, "物资管理", "物资", "#goods", "app.item"),
            new Menu(402, "物资管理", "出入库", "#stockList", "app.stock-worktable"),

            new Menu(501, "微信工具", "微信咨询", "#talk", "app.wechat-talk"),
            new Menu(502, "微信工具", "微信通知", "#notice", "app.wechat-notice"),
            new Menu(503, "微信工具", "作业布置", "#assignment", "app.assignment"),

//            new Menu(601, "财务站", "学生财务", "icon-info", "app.todo"),

//            new Menu(701, "个人中心", "我的消息", "icon-info", "app.todo"),
//            new Menu(702, "个人中心", "我的课表", "icon-info", "app.account"),

            new Menu(801, "学校设置", "帐号管理", "#account", "app.account"),
            new Menu(802, "学校设置", "教务设置", "#eduSetting", "app.edu-setting"),
//            new Menu(803, "管理站", "学校设置", "icon-wrench", "app.edu-setting"),
//            new Menu(804, "管理站", "校内广播", "icon-wrench", "app.edu-setting"),
//            new Menu(805, "管理站", "家长群发", "icon-wrench", "app.parents-wechat")

            new Menu(10001, "超管站", "机构管理", "#account", "app.org-list")

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
