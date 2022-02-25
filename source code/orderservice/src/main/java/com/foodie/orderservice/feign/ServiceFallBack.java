package com.foodie.orderservice.feign;

import com.foodie.common.dto.ServerResponse;

import java.util.List;

public class ServiceFallBack implements DispatchService,InformService{
    @Override
    public ServerResponse<List<Integer>> dispatchorder(DispatchReq1 dispatchReq1) {
        return null;
    }

    @Override
    public ServerResponse informneworder(List<Integer> driverlist) {
        return null;
    }
}
