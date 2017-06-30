package com.recklessmo.constant;


/**
 *
 */
public class ScoreAnalyseConstants {


    public int findRotateSteps(String ring, String key) {
        int step = 0, pos = 0, len = ring.length();
        for(int i = 0; i < key.length(); i++){
            char temp = key.charAt(i);
            if(ring.charAt(pos) != temp){
                int k = pos;
                int clockCount = 0;
                while(ring.charAt(pos) != temp){
                    pos++;
                    if(pos == len){
                        pos = 0;
                    }
                    clockCount++;
                }
                int first = pos;

                pos = k;
                int antiClockCount = 0;
                while(ring.charAt(pos) != temp){
                    pos--;
                    if(pos == -1){
                        pos = len - 1;
                    }
                    antiClockCount++;
                }
                int second = pos;

                if(clockCount < antiClockCount){
                    pos = first;
                    step += clockCount;
                }else{
                    pos = second;
                    step += antiClockCount;
                }

            }
        }
        return step + key.length();
    }

    public static void main(String[] args){
        new ScoreAnalyseConstants().findRotateSteps("iotfo", "fioot");
    }
}
