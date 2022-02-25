package com.foodie.orderservice;
import com.foodie.common.dto.ServerResponse;
import com.foodie.common.pojo.Route;
import com.foodie.common.pojo.Site;
import com.foodie.orderservice.dto.*;
import java.util.List;

//微服务api
public interface OrderService {

    public ServerResponse<OrderRes1> orderpassengersubmit(OrderReq1 orderReq1);
    public ServerResponse orderpassengercancel(Integer orderid);
    public ServerResponse<Route> orderdrivertake(OrderReq4 orderReq4);
    public ServerResponse<Route> mapdriverstartitinerary(Integer orderid);
    public ServerResponse orderdriverrequestpay(OrderReq2 orderReq2);
    public ServerResponse orderpassengerpay(OrderReq3 orderReq3);
    public ServerResponse<List<OrderRes2>> orderuserlist(Integer userid);
    public ServerResponse<OrderRes3> orderuserdetail(Integer orderid);
    public ServerResponse<List<OrderRes4>> orderdriverlist(Site site);

}
