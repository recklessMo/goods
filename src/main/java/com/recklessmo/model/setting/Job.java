package com.recklessmo.model.setting;

import java.util.Date;

/**
 * Created by hpf on 8/23/16.
 */
public class Job {

    private long jobId;
    private String jobName;

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
