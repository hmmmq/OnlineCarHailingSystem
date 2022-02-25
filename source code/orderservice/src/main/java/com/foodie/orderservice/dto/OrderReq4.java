package com.foodie.orderservice.dto;

import com.foodie.common.pojo.Site;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderReq4 {
   private Integer carid;
   private Integer driverid;
   private Integer orderid;
   private Site driversite;
   private Site passengersite;
}
