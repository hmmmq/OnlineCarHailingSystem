package com.foodie.demoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapRes1 {
    private Integer distance;
    private Integer duration;
    private Integer toll;
}
