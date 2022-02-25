package com.foodie.common.dto;

/**
 * @Author: czely
 * @Date: 2021/6/5 17:20
 * @Description: 状态码
 */
public enum ResponseCode {
    SuccessfullyGet(200,"successfully get data!"),
    SuccessfullyUpdate(201,"successfully update!"),
    SuccessfullyAdd(202,"successfully add!"),
    SuccessfullyDelete(203,"successfully delete!"),
    SuccessfullPublish(204,"successfully publish!"),
    FailGet(400,"fail to get data!"),
    FailUpdate(401,"fail to update"),
    FailAdd(402,"fail to add!"),
    FailDelete(403,"fail to delete"),
    FailPublish(404,"fail to publish");


    private final int code;
    private final String reply;


    private ResponseCode(int code, String reply) {
        this.code=code;
        this.reply=reply;
    }

    public int getCode() {
        return code;
    }

    public String getReply() {
        return reply;
    }
}
