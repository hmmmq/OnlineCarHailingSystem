package com.foodie.demoservice;

import com.foodie.common.dto.ServerResponse;
import com.foodie.common.pojo.Route;
import com.foodie.demoservice.dto.MapReq1;

//微服务api
public interface MapService {
    ///map/route
    ServerResponse<Route> maproute(MapReq1 mapReq1);

}
