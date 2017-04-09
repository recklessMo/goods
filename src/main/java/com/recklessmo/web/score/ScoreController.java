package com.recklessmo.web.score;

import com.alibaba.fastjson.JSONObject;
import com.recklessmo.model.dynamicTable.DynamicTable;
import com.recklessmo.model.dynamicTable.TableColumn;
import com.recklessmo.model.score.CourseScore;
import com.recklessmo.model.score.Score;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.util.score.ScoreUtils;
import com.recklessmo.web.webmodel.page.ScoreListPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.recklessmo.response.JsonResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
     * 查询成绩单
     * @param
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse list(@RequestBody ScoreListPage scoreListPage){
        DynamicTable result = new DynamicTable();
        List<Score> data = scoreService.loadScoreList(scoreListPage);

        //
        data.stream().forEach(item -> {
            List<CourseScore> courseScoreList = item.getCourseScoreList();
            for(int i = 0; i < courseScoreList.size(); i++){
                courseScoreList.get(i).setCourseName("course" + i);
            }
        });
        //



        List<Map<String, Object>> dataList = new LinkedList<>();
        data.stream().forEach(item -> {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("班级类型", "");
            dataMap.put("班级类别", "");
            dataMap.put("班级名称", "");
            item.getCourseScoreList().stream().forEach(courseScore -> {
                dataMap.put(courseScore.getCourseName(), courseScore.getScore());
            });
            dataList.add(dataMap);
        });
        result.setDataList(dataList);
        if(dataList.size() > 0) {
            Map<String, Object> temp = dataList.get(0);
            List<TableColumn> tableColumnList = new LinkedList<>();
            List<String> labelList = new LinkedList<>(temp.keySet());
            for(int i = 0; i < labelList.size(); i++) {
                String label = labelList.get(i);
                TableColumn tableColumn = new TableColumn();
                tableColumn.setId(i);
                tableColumn.setField(label);
                tableColumn.setTitle(label);
                tableColumn.setSortable(label);
//                tableColumn.setFilter("{'" + label + "':'text'}");
                JSONObject obj = new JSONObject();
                obj.put(label, "text");
                tableColumn.setFilter(obj);
                tableColumnList.add(tableColumn);
            }
            result.setLabelList(tableColumnList);
        }
        return new JsonResponse(200, result, null);
    }


}
