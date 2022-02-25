package com.foodie.orderservice.dto;

import com.foodie.common.pojo.Site;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderReq1 {
    Integer userid;
    Site departure;
    Site destination;
}
