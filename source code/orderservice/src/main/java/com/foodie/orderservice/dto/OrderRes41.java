package com.foodie.orderservice.dto;

import com.foodie.common.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRes41 extends OrderRes4{
    private User passenger;
}
