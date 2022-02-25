package com.foodie.orderservice.mapper.dto;
import com.foodie.common.pojo.Site;
import com.foodie.orderservice.dto.OrderRes4;

import java.util.List;

public interface OrderRes4Mapper {
    List<OrderRes4> select(Site site);
}