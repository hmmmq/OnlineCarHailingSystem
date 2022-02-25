package com.foodie.orderservice.dto;

import com.foodie.common.pojo.Orderstate;
import com.foodie.common.pojo.Ordertime;
import com.foodie.common.pojo.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRes2 {
   private Integer orderid;
   private Route route;
   private Orderstate orderstate;
   private Ordertime ordertime;
}
