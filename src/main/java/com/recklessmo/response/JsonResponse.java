package com.recklessmo.response;

/**
 * Created by hpf on 6/13/16.
 *
 *
 * 可以自定义一个json序列化方式,这样可以不对null值进行序列化
 *
 *
 */
public class JsonResponse {

    private int status;
    private Integer totalCount;
    private Object data;

    public JsonResponse(){

    }

    public JsonResponse(int status, Object data, Integer totalCount){
        this.status = status;
        this.data = data;
        this.totalCount = totalCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
