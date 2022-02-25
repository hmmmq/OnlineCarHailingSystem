package com.foodie.orderservice.feign;
import com.foodie.common.dto.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

//微服务api
@FeignClient(name = "dispatchservice",fallback = ServiceFallBack.class)
@Component
public interface DispatchService {
    @PostMapping("/dispatch/order")
    public ServerResponse<List<Integer>> dispatchorder(DispatchReq1 dispatchReq1);
}
