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
//            new Menu(300, null, "统计站", "icon-book-open", null),
            new Menu(400, null, "库存站", "icon-settings", null),
            new Menu(500, null, "微信站", "icon-settings", null),
//            new Menu(600, null, "财务站", "icon-settings", null),
            new Menu(700, null, "个人中心", "icon-info", null),
            new Menu(800, null, "管理站", "icon-settings", null),
            new Menu(10000, null, "超管站", "icon-settings", null),


            new Menu(101, "教务站", "学生查询", "icon-people", "app.student-view-worktable"),
            //选择考试, 然后进行成绩趋势分析
//            new Menu(102, "教务站", "个人成绩趋势", "icon-user-follow", "app.student-score-trend"),
            new Menu(103, "教务站", "录入导出", "icon-user-follow", "app.student-add-worktable"),
//            new Menu(103, "教务站", "奖惩录入", "icon-user-follow", "app.student-reward"),
//            new Menu(104, "教务站", "毕业去向", "icon-user-follow", "app.student-graduate"),
            new Menu(105, "教务站", "任课设置", "icon-user-follow", "app.student-teacher"),
            new Menu(106, "教务站", "课表编排", "icon-user-follow", "app.class-schedule"),


            new Menu(201, "成绩站", "考试设置", "icon-plus", "app.exam"),//done
//            new Menu(202, "成绩站", "分析模版", "icon-plus", "app.analyze-template"),
            //成绩单点击可以写评语. 评语跟随成绩
            new Menu(203, "成绩站", "成绩单", "icon-plus", "app.result-list"),//half-done
            //雷达图
            new Menu(204, "成绩站", "成绩趋势", "icon-user-follow", "app.result-trend"),
            new Menu(205, "成绩站", "个人综合", "icon-plus", "app.result-self"),
            new Menu(206, "成绩站", "整体分析", "icon-plus", "app.result-total"),//done
            new Menu(207, "成绩站", "分数段分析", "icon-plus", "app.result-gap"),//done
            new Menu(208, "成绩站", "名次分析", "icon-plus", "app.result-rank"),//done
//            new Menu(208, "成绩站", "均分分析", "icon-plus", "app.result-avg"),
            new Menu(210, "成绩站", "进退步分析", "icon-plus", "app.result-rankchange"),
            new Menu(211, "成绩站", "缺考统计", "icon-plus", "app.result-absense"),
//            new Menu(211, "成绩站", "不合格名单", "icon-plus", "app.result-unqualified"),
            //选择两次考试进行对比,选出进退步的前几名, 可以搜索, 点击某个学生名字进去,可以弹出他的成绩趋势

//            new Menu(301, "统计站", "财务统计", "icon-wallet", "app.payStat"),
//            new Menu(302, "统计站", "学生统计", "icon-wallet", "app.payStat"),
//            new Menu(303, "统计站", "微信统计", "icon-wallet", "app.wechatStat"),
//            new Menu(304, "统计站", "成本统计", "icon-wallet", "app.wechatStat"),

            new Menu(401, "库存站", "物资", "icon-plus", "app.item"),
            new Menu(402, "库存站", "出入库", "icon-check", "app.stock-worktable"),

            new Menu(501, "微信站", "微信咨询", "icon-wrench", "app.wechat-talk"),
            new Menu(502, "微信站", "微信通知", "icon-wrench", "app.wechat-notice"),

//            new Menu(601, "财务站", "学生财务", "icon-info", "app.todo"),

//            new Menu(701, "个人中心", "我的消息", "icon-info", "app.todo"),
//            new Menu(702, "个人中心", "我的课表", "icon-info", "app.account"),

            new Menu(801, "管理站", "帐号管理", "icon-user-female", "app.account"),
            new Menu(802, "管理站", "教务设置", "icon-wrench", "app.edu-setting"),
//            new Menu(803, "管理站", "学校设置", "icon-wrench", "app.edu-setting"),
//            new Menu(804, "管理站", "校内广播", "icon-wrench", "app.edu-setting"),
//            new Menu(805, "管理站", "家长群发", "icon-wrench", "app.parents-wechat")

            new Menu(10001, "超管站", "帐号管理", "icon-user-female", "app.super-account"),
            new Menu(10002, "超管站", "机构管理", "icon-user-female", "app.org-list")

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
