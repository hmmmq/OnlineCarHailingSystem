package com.foodie.orderservice.dto;

import com.foodie.common.pojo.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRes3 {
   private Integer orderid;
   private Route  route;
   private User passenger;
   private User driver;
   private Car car;
   private Orderpay orderpay;
   private Orderstate orderstate;
   private Ordertime ordertime;
   private Ordercomment ordercomment;

}
