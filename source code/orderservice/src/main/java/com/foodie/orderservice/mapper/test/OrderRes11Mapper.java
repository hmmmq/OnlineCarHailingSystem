package com.foodie.orderservice.mapper.test;

import com.foodie.orderservice.dto.OrderReq1;
import com.foodie.orderservice.dto.OrderRes1;
import com.foodie.orderservice.dto.OrderRes11;
import feign.Param;

import java.util.List;

public interface OrderRes11Mapper {

    OrderRes11 select(@Param("orderid")Integer orderid,@Param("passengerid")Integer passengerid);

}