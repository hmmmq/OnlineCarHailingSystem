package com.foodie.orderservice;


import com.foodie.common.dto.ServerResponse;
import com.foodie.common.pojo.Route;
import com.foodie.common.pojo.Site;
import com.foodie.orderservice.dto.*;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//微服务对外提供的接口.
@RestController
@RefreshScope
public class OrderController  {
    @Autowired
    private OrderService orderService;

    @PostMapping("/order/passenger/submit")

    public ServerResponse<OrderRes1> orderpassengersubmit(@NotNull OrderReq1 orderReq1) {
        return orderService.orderpassengersubmit(orderReq1);
    }

    @PutMapping("/order/passenger/cancel")

    public ServerResponse orderpassengercancel(@NotNull Integer orderid) {
        return orderService.orderpassengercancel(orderid);
    }

    @PutMapping("/order/driver/take")

    public ServerResponse<Route> orderdrivertake(@NotNull OrderReq4 orderReq4) {
        return orderService.orderdrivertake(orderReq4);
    }

    @PatchMapping("/map/driver/startitinerary")

    public ServerResponse<Route> mapdriverstartitinerary(@NotNull Integer orderid) {
        return orderService.mapdriverstartitinerary(orderid);
    }

    @PostMapping("/order/driver/requestpay")

    public ServerResponse orderdriverrequestpay(@NotNull OrderReq2 orderReq2) {
        return orderService.orderdriverrequestpay(orderReq2);
    }

    @PostMapping("/order/passenger/pay")

    public ServerResponse orderpassengerpay(@NotNull OrderReq3 orderReq3) {
        return orderService.orderpassengerpay(orderReq3);
    }

    @GetMapping("/order/user/list")

    public ServerResponse<List<OrderRes2>> orderuserlist(@NotNull Integer userid) {
        return orderService.orderuserlist(userid);
    }

    @GetMapping("/order/user/detail")

    public ServerResponse<OrderRes3> orderuserdetail(@NotNull Integer orderid) {
        return orderService.orderuserdetail(orderid);
    }

    @PostMapping("/order/driver/list")

    public ServerResponse<List<OrderRes4>> orderdriverlist(@NotNull Site site) {
        return orderService.orderdriverlist(site);
    }

}
