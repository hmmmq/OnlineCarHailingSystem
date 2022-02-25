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
public class OrderRes1 {
    private Integer orderid;
    private Double fare;
    private User passenger;
    private User driver;
    private Route route;
    private Car car;
}
