package com.foodie.orderservice.dto;

import com.foodie.common.pojo.Orderstate;
import com.foodie.common.pojo.Ordertime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRes21 {
    private Integer orderid;
    private Integer prouteid;
    private Integer departureid;
    private Integer destinationid;
    private String departurename;
    private Double departurelongitude;
    private Double departurelatitude;
    private String destinationname;
    private Double destinationlongitude;
    private Double destinationlatitude;
    private Integer distance;
    private Integer timecost;
    private Integer toll;
    private Byte type;
    private Orderstate orderstate;
    private Ordertime ordertime;
}
