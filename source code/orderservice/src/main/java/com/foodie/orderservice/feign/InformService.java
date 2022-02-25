package com.foodie.orderservice.feign;

import com.foodie.common.dto.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import java.util.List;

//微服务api
@FeignClient(name = "informservice",fallback = ServiceFallBack.class)
@Component
public interface InformService {

    ServerResponse informneworder(List<Integer> driverlist);

}
