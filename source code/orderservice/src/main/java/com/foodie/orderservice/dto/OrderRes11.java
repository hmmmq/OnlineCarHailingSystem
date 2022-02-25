package com.foodie.orderservice.dto;

import com.foodie.common.pojo.Car;
import com.foodie.common.pojo.Route;
import com.foodie.common.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRes11 {
    private Integer orderid;
    private Integer passengerid;
    private Integer driverid;
    private Integer pdrouteid;
    private Integer departureid;
    private Integer destinationid;
    private Integer carid;
    private Double  fare;
    private String  passengername;
    private String  passengerphone;
    private String drivername;
    private String driverphone;
    private String departurename;
    private Double departurelongitude;
    private Double departurelatitude;
    private String destinationname;
    private Double destinationlongitude;
    private Double destinationlatitude;
    private Integer distance;
    private Integer timecost;
    private Integer toll;
    private String carbrand;
    private String carnumber;
}
