package com.online.taxi.request;

import lombok.Data;

/**
 * @date 2018/8/16
 */
@Data
public class PayRequest {
    //用户id
    private Integer yid;
    //本金
    private Double capital;
    //赠费
    private Double giveFee;

    private String source;
    //充值类型
    private Integer rechargeType;
    private Integer orderId;
    private String createUser;

}
