package com.ecobenchmark.controllers.getstats;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.mutiny.sqlclient.Row;

import java.util.UUID;

public class StatsResponse {

    @JsonProperty("account_id")
    private UUID accountId;

    @JsonProperty("account_login")
    private String accountLogin;

    @JsonProperty("list_count")
    private Integer listCount;

    @JsonProperty("task_avg")
    private Float taskAvg;

    public StatsResponse(UUID accountId, String accountLogin, Integer listCount, Float taskAvg) {
        this.accountId = accountId;
        this.accountLogin = accountLogin;
        this.listCount = listCount;
        this.taskAvg = taskAvg;
    }

    public static StatsResponse from(Row row){
        return new StatsResponse(
                row.get(UUID.class, "id"),
                row.get(String.class, "login"),
                row.get(Integer.class, "nb_list"),
                row.get(Float.class, "avg_tasks"));
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }

    public Integer getListCount() {
        return listCount;
    }

    public void setListCount(Integer listCount) {
        this.listCount = listCount;
    }

    public Float getTaskAvg() {
        return taskAvg;
    }

    public void setTaskAvg(Float taskAvg) {
        this.taskAvg = taskAvg;
    }
}
