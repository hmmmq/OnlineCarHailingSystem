package com.foodie.orderservice.feign;

import com.foodie.common.dto.ServerResponse;
import com.foodie.common.pojo.Route;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

//微服务api
@FeignClient(name = "mapservice",fallback = ServiceFallBack.class)
@Component
public interface MapService {
    @PostMapping("/map/route")
    ServerResponse<Route> maproute(MapReq1 mapReq1);

}
