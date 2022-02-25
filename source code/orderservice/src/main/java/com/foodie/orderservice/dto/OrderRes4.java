package com.foodie.orderservice.dto;

import com.foodie.common.pojo.Orderpay;
import com.foodie.common.pojo.Orderstate;
import com.foodie.common.pojo.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRes4 {
   private Integer orderid;
   private Orderstate orderState;
   private Route pdroute;
   private Route proute;
   private Orderpay orderpay;

}
