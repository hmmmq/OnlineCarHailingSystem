package com.foodie.orderservice.dto;

import com.foodie.common.pojo.Route;
import com.foodie.common.pojo.Site;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route1 extends Route {
    private Site departure;
    private Site destination;
}
