package com.recklessmo.web.score;

import com.alibaba.fastjson.JSONObject;
import com.recklessmo.model.dynamicTable.DynamicTable;
import com.recklessmo.model.dynamicTable.TableColumn;
import com.recklessmo.model.score.CourseScore;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.ScoreListPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.naming.NameParser;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by hpf on 8/29/16.
 */
@Controller
@RequestMapping("/v1/score")
public class ScoreController {

    @Resource
    private ScoreService scoreService;

    /**
     * 查询成绩单 done
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse list(@RequestBody ScoreListPage scoreListPage) {
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        scoreListPage.setOrgId(userDetails.getOrgId());
        DynamicTable result = new DynamicTable();
        List<Score> data = scoreService.loadScoreList(scoreListPage);
        Map<String, String> nameMap = new HashMap<>();

        //data
        List<Map<String, Object>> dataList = new LinkedList<>();
        List<String> labelList = new LinkedList<>();
        for(int pos = 0; pos < data.size(); pos++){
            Score item = data.get(pos);
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("type", item.getClassType());
            dataMap.put("level", item.getClassLevel());
            dataMap.put("classname", item.getClassName());
            dataMap.put("classid", item.getClassId());
            dataMap.put("name", item.getName());
            if(pos == 0) {
                nameMap.put("type", "类型");//文科理科全科
                nameMap.put("level", "类别");//重点 普通 平行
                nameMap.put("classname", "名称");
                nameMap.put("name", "姓名");
                labelList.add("type");
                labelList.add("level");
                labelList.add("classname");
                labelList.add("name");
            }
            for (int i = 0; i < item.getCourseScoreList().size(); i++) {
                CourseScore courseScore = item.getCourseScoreList().get(i);
                String temp = "course" + i;
                dataMap.put(temp, courseScore.getScore());
                dataMap.put(temp + "rank", courseScore.getRank());
                if(pos == 0) {
                    nameMap.put(temp, courseScore.getCourseName());
                    nameMap.put(temp + "rank", "排名");
                    labelList.add(temp);
                    labelList.add(temp + "rank");
                }
            }
            dataList.add(dataMap);
        }
        result.setDataList(dataList);

        //label
        List<TableColumn> tableColumnList = new LinkedList<>();
        labelList.stream().forEach(label -> {
            TableColumn tableColumn = new TableColumn();
            tableColumn.setField(label);
            tableColumn.setTitle(nameMap.get(label));
            tableColumn.setSortable(label);
            JSONObject obj = new JSONObject();
            obj.put(label, "text");
            tableColumn.setFilter(obj);
            tableColumnList.add(tableColumn);
        });
        result.setLabelList(tableColumnList);
        return new JsonResponse(200, result, null);
    }


}
