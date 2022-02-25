package com.foodie.orderservice.feign;

import com.foodie.common.pojo.Site;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DispatchReq1 {
    Integer orderid;
    Site departure;
}
