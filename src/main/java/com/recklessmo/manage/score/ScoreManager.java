package com.recklessmo.manage.score;

import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.recklessmo.model.score.Score;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by hpf on 10/28/16.
 */
@Component
public class ScoreManager {

    private Comparator<Score> comparatorTotal = new Comparator<Score>() {
        @Override
        public int compare(Score o1, Score o2) {
            return o1.getTotal() > o2.getTotal() ? 1 : -1;
        }
    };

    private Comparator<Score> comparatorChinese = new Comparator<Score>() {
        @Override
        public int compare(Score o1, Score o2) {
            return o1.getChinese() > o2.getChinese() ? 1 : -1;
        }
    };


//    private Comparator<Score> comparatorMath = new Comparator



    public List<Score> get(long id){
        List<Score> temp = new LinkedList<>();
        return null;
    }

}
