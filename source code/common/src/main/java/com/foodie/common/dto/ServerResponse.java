package com.foodie.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.NoArgsConstructor;
import java.io.Serializable;
/**
 * @Author: czely
 * @Date: 2021/6/5 19:57
 * @Description:
 */
// 确保序列化JSON时，如果是null对象，其key也会消失。
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
// 生成无参构造，确保在RPC调用时，不会出现反序列失败
@NoArgsConstructor
public class ServerResponse<T> implements Serializable {
    private int status;
    private String msg;
    private T data;

    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }


    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ServerResponse<T> createByresponseCode(ResponseCode responseCode) {
        return new ServerResponse<T>(responseCode.getCode(), responseCode.getReply());
    }
    public static <T> ServerResponse<T> createByresponseCodeData(ResponseCode responseCode,T data) {
        return new ServerResponse<T>(responseCode.getCode(), responseCode.getReply(),data);
    }

    //添加该注解的方法不在json序列化结果当中
    @JsonIgnore
    //响应是否正确的判断,通过注解在序列化时，isSuccess方法就不会在序列化里
    public boolean isSuccess() {
        return this.status == ResponseCode.SuccessfullyGet.getCode();
    }

    @JsonIgnore
    // 可以快速进行成功与否的条件判断，判断false时，不用加!
    public boolean isFail() {
        return this.status != ResponseCode.SuccessfullyGet.getCode();
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    // 快速构建返回结果
    //    成功时的调用
    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<T>(ResponseCode.SuccessfullyGet.getCode());
    }
    //通过文本显示成功，创建这个类，作用是供前端提示使用
    public static <T> ServerResponse<T> createBySuccessMessage(String msg) {
        return new ServerResponse<T>(ResponseCode.SuccessfullyGet.getCode(), msg);
    }
    //响应成功然后给前台相应数据
    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new ServerResponse<T>(ResponseCode.SuccessfullyGet.getCode(), data);
    }
    //响应成功然后给前台相应数据和文本消息
    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<T>(ResponseCode.SuccessfullyGet.getCode(), msg, data);
    }

    //    失败时的调用 响应失败时，直接把错误返回
    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<T>(ResponseCode.FailGet.getCode(), ResponseCode.FailGet.getReply());
    }
    //直接返回错误信息
    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage) {
        return new ServerResponse<T>(ResponseCode.FailGet.getCode(), errorMessage);
    }
    //服务端响应，需要登录/参数错误
    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode, String errorMessage) {
        return new ServerResponse<T>(errorCode, errorMessage);
    }
}
