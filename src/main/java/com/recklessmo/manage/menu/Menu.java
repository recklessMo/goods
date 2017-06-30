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
            new Menu(200, null, "单次考试分析", "#score", null),
            new Menu(300, null, "成绩进退步分析", "#score", null),
            new Menu(400, null, "成绩趋势分析", "#score", null),
            new Menu(500, null, "成绩对比分析", "#score", null),
//            new Menu(600, null, "统计站", "icon-book-open", null),
            new Menu(700, null, "物资管理", "#stock", null),
            new Menu(800, null, "微信工具", "#wechat", null),
//            new Menu(900, null, "财务站", "icon-settings", null),
//            new Menu(1000, null, "个人中心", "icon-info", null),
            new Menu(1100, null, "学校设置", "#admin", null),
            new Menu(1200, null, "习题库", "#stock", null),
            new Menu(10000, null, "超管站", "#admin", null),


            new Menu(110, "教务管理", "学生查询", "#searchStudent", "app.student-view-worktable"),
            //选择考试, 然后进行成绩趋势分析
//            new Menu(120, "教务管理", "个人成绩趋势", "icon-user-follow", "app.student-score-trend"),
            new Menu(130, "教务管理", "录入导出", "#importExport", "app.student-add-worktable"),
//            new Menu(140, "教务管理", "任课设置", "icon-user-follow", "app.student-teacher"),
//            new Menu(150, "教务管理", "课表编排", "icon-user-follow", "app.class-schedule"),


            new Menu(210, "单次考试分析", "考试设置", "#score", "app.exam"),//done
            //成绩单点击可以写评语. 评语跟随成绩， 评语同步显示微信端
            new Menu(220, "单次考试分析", "成绩单生成", "#score", "app.result-list"),//half-done
            new Menu(230, "单次考试分析", "整体情况分析", "#score", "app.result-total"),//done
            new Menu(240, "单次考试分析", "分数点阵图", "#score", "app.result-score-point"),//done
            new Menu(250, "单次考试分析", "名次点阵图", "#score", "app.result-rank-point"),//done
            new Menu(260, "单次考试分析", "分数段分析", "#score", "app.result-gap"),//done
            new Menu(270, "单次考试分析", "名次构成分析", "#score", "app.result-rank"),//done
            new Menu(280, "单次考试分析", "缺考人员统计", "#score", "app.result-absense"),


            new Menu(310, "成绩进退步分析", "个人进退步分析", "#score", "app.result-rankchange"),
            new Menu(320, "成绩进退步分析", "班级进退步分析", "#score", "app.result-rankchange"),
            new Menu(330, "成绩进退步分析", "年级进退步分析", "#score", "app.result-rankchange"),

            new Menu(410, "成绩趋势分析", "个人趋势分析", "#score", "app.result-trend"),
            new Menu(420, "成绩趋势分析", "班级趋势分析", "#score", "app.result-trend"),
            new Menu(430, "成绩趋势分析", "年级趋势分析", "#score", "app.result-trend"),

            new Menu(510, "成绩对比分析", "个人单次对比", "#score", "app.result-self"),
            new Menu(520, "成绩对比分析", "班级单次对比", "#score", "app.result-self"),
            new Menu(530, "成绩对比分析", "个人综合对比", "#score", "app.result-contrast"),
            new Menu(540, "成绩对比分析", "班级综合对比", "#score", "app.result-contrast"),


//            new Menu(301, "统计站", "财务统计", "icon-wallet", "app.payStat"),
//            new Menu(302, "统计站", "学生统计", "icon-wallet", "app.payStat"),
//            new Menu(303, "统计站", "微信统计", "icon-wallet", "app.wechatStat"),
//            new Menu(304, "统计站", "成本统计", "icon-wallet", "app.wechatStat"),

            new Menu(710, "物资管理", "物资", "#goods", "app.item"),
            new Menu(720, "物资管理", "出入库", "#stockList", "app.stock-worktable"),

            new Menu(810, "微信工具", "微信咨询", "#talk", "app.wechat-talk"),
            new Menu(820, "微信工具", "微信通知", "#notice", "app.wechat-notice"),
            new Menu(830, "微信工具", "作业布置", "#assignment", "app.assignment"),
            new Menu(840, "微信工具", "在线作业", "#assignment", "app.online-assignment"),


//            new Menu(601, "财务站", "学生财务", "icon-info", "app.todo"),

//            new Menu(701, "个人中心", "我的消息", "icon-info", "app.todo"),
//            new Menu(702, "个人中心", "我的课表", "icon-info", "app.account"),

            new Menu(1110, "学校设置", "帐号管理", "#account", "app.account"),
            new Menu(1120, "学校设置", "教务设置", "#eduSetting", "app.edu-setting"),
            new Menu(1130, "学校设置", "账号导入", "#account", "app.account-import"),
            new Menu(1140, "学校设置", "角色管理", "#account", "app.role"),
//            new Menu(803, "管理站", "学校设置", "icon-wrench", "app.edu-setting"),
//            new Menu(804, "管理站", "校内广播", "icon-wrench", "app.edu-setting"),
//            new Menu(805, "管理站", "家长群发", "icon-wrench", "app.parents-wechat")

            new Menu(1210, "习题库", "创建习题", "#assignment", "app.create-question"),

            new Menu(10010, "超管站", "机构管理", "#account", "app.org-list")

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
