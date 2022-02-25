package com.foodie.orderservice.feign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapReq1 {
    private Double longitude1;
    private Double latitude1;
    private Double longitude2;
    private Double latitude2;
}
