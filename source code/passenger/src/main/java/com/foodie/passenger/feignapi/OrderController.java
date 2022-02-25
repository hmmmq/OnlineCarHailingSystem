package com.foodie.passenger.feignapi;


import com.foodie.common.dto.*;
import com.foodie.common.dto.ServerResponse;
import com.foodie.common.pojo.Route;
import com.foodie.common.pojo.Site;
import com.sun.istack.internal.NotNull;
import org.springframework.web.bind.annotation.*;

public interface OrderController {

    @PostMapping("/order/passenger/submit")
    ServerResponse<OrderRes1> orderpassengersubmit(@NotNull OrderReq1 orderReq1);

    @PutMapping("/order/passenger/cancel")
    ServerResponse orderpassengercancel(@NotNull Integer orderid);

    @PutMapping("/order/driver/take")
    ServerResponse<Route> orderdrivertake(@NotNull OrderReq4 orderReq4);

    @PatchMapping("/map/driver/startitinerary")
    ServerResponse<Route> mapdriverstartitinerary(@NotNull Integer orderid);

    @PostMapping("/order/driver/requestpay")
    ServerResponse orderdriverrequestpay(@NotNull OrderReq2 orderReq2);

    @PostMapping("/order/passenger/pay")
    ServerResponse orderpassengerpay(@NotNull OrderReq3 orderReq3);

    @GetMapping("/order/user/list")
    ServerResponse<OrderRes2> orderuserlist(@NotNull Integer userid);

    @GetMapping("/order/user/detail")
    ServerResponse<OrderRes3> orderuserdetail(@NotNull Integer orderid);

    @PostMapping("/order/driver/list")
    ServerResponse<OrderRes4> orderdriverlist(@NotNull Site site);

}

