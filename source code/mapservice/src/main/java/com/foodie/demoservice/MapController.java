package com.foodie.demoservice;
import com.foodie.common.dto.ServerResponse;
import com.foodie.common.pojo.Route;
import com.foodie.demoservice.dto.MapReq1;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
//微服务对外提供的接口.
@RestController
@RefreshScope
public class MapController {
    @Autowired
    MapService mapService;
    @PostMapping("/map/route")
    public ServerResponse<Route> maproute(@NotNull @RequestBody MapReq1 mapReq1) {
        return mapService.maproute(mapReq1);
    }

}
