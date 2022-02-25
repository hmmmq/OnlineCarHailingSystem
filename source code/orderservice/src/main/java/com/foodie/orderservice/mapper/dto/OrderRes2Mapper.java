package com.foodie.orderservice.mapper.dto;

import com.foodie.orderservice.dto.OrderRes1;
import com.foodie.orderservice.dto.OrderRes2;

import java.util.List;

public interface OrderRes2Mapper {

    List<OrderRes2> select(Integer userid);

}