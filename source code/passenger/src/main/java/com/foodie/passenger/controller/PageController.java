package com.foodie.passenger.controller;
import com.foodie.common.dto.*;
import com.foodie.common.pojo.Site;
import java.util.Date;

import com.foodie.common.pojo.*;
import com.foodie.passenger.feignapi.OrderController;
import com.sun.istack.internal.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class PageController {

    OrderController orderController;

    private User p_user = new User();
    {
        p_user.setId(1);
        p_user.setPassword("123");
        p_user.setName("a");
        p_user.setPhone("12312312312");
    }

    private ArrayList<OrderRes2> orderRes2s = new ArrayList<>();
    private ArrayList<OrderRes3> orderRes3s = new ArrayList<>();
    {
        for(int i = 1;i < 10;i++) {
            User user1 = new User();
            user1.setId(2*i+1);
            user1.setPassword("123");
            user1.setName("李");
            user1.setPhone("12312312312");

            User user2 = new User();
            user2.setId(2*i);
            user2.setPassword("123");
            user2.setName("刘");
            user2.setPhone("12312312312");

            Car car = new Car();
            car.setId(i);
            car.setUserid(2*i);
            car.setBrand("a牌");
            car.setNumber("琼A12345");

            Site site1 = new Site();
            site1.setId(i);
            site1.setName("海大北门");
            site1.setLongitude(120.0F);
            site1.setLatitude(20.0F);

            Site site2 = new Site();
            site2.setId(2*i);
            site2.setName("海大南门");
            site2.setLongitude(120.0F);
            site2.setLatitude(20.0F);

            Route1 route1 = new Route1();
            route1.setDeparture(site1);
            route1.setDestination(site2);
            route1.setId(i);
            route1.setDepartureid(1);
            route1.setDestinationid(2);
            route1.setDistance(2);
            route1.setTimecost(60);

            Orderstate orderstate = new Orderstate();
            orderstate.setId(i);
            orderstate.setFinishstate((byte) 1);
            orderstate.setItinerarystate(2);
            orderstate.setCancelstate((byte) 0);
            orderstate.setPickupstate((byte) 1);

            Ordertime ordertime = new Ordertime();
            ordertime.setId(i);
            ordertime.setStarttime(new Date());
            ordertime.setEndtime(new Date());
            ordertime.setDeparttime(new Date());
            ordertime.setArrivetime(new Date());

            Orderpay orderpay = new Orderpay();
            orderpay.setId(i);
            orderpay.setUserid(i);
            orderpay.setState(1);
            orderpay.setPaytime(new Date());
            orderpay.setFare((long) 50);
            orderpay.setPaytype("0");

            Ordercomment ordercomment = new Ordercomment();
            ordercomment.setId(i);
            ordercomment.setContent("comment");
            ordercomment.setRate("5");
            ordercomment.setTime(new Date());

            //OrderRes2 orderRes2 = new OrderRes2(i, route, orderstate, ordertime);
            OrderRes3 orderRes3 = new OrderRes3(i, route1, user1, user2, car, orderpay, orderstate, ordertime, ordercomment);

            //orderRes2s.add(orderRes2);
            orderRes3s.add(orderRes3);
        }
    }

    private Wallet wallet = new Wallet();
    {
        wallet.setId(1);
        wallet.setBalance(100L);
        wallet.setState((byte)0);
        wallet.setUserid(1);
    }

    private Site site1 = new Site();
    private Site site2 = new Site();
    {
        site1.setId(1);
        site1.setName("海大北门");
        site1.setLongitude(120.0F);
        site1.setLatitude(20.0F);

        site2.setId(2);
        site2.setName("海大南门");
        site2.setLongitude(120.0F);
        site2.setLatitude(20.0F);
    }

    Route1 route1 = new Route1();
    {
        route1.setDeparture(site1);
        route1.setDestination(site2);
        route1.setId(1);
        route1.setDepartureid(1);
        route1.setDestinationid(2);
        route1.setDistance(2);
        route1.setTimecost(60);
    }

    private User d_user = new User();
    {
        d_user.setId(2);
        d_user.setPassword("123");
        d_user.setName("a");
        d_user.setPhone("12312312312");
    }
    Car car = new Car();
    {
        car.setId(1);
        car.setUserid(2);
        car.setBrand("a牌");
        car.setNumber("琼A12345");
    }
    OrderRes1 orderRes1 = new OrderRes1(1, 15D, p_user, d_user, route1, car);

    //乘客主页
    @GetMapping("/passenger/goindex")
    public ModelAndView passengergoindex() {    //传入id
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pasindex");
        //调用服务端，根据id找user
        modelAndView.addObject("p_user", p_user);
        return modelAndView;
    }

    //订单列表页
    @GetMapping("/passenger/goorderlist")
    public ModelAndView passengergoorderlist() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pasorderlist");
        /*
        //订单列表
        ServerResponse orderuserlist = orderController.orderuserlist(p_user.getId());
        ArrayList<OrderRes2> orderRes2s = (ArrayList<OrderRes2>) orderuserlist.getData();
        for(int i = 0;i < orderRes2s.size();i++)
        {
            int orderid = orderRes2s.get(i).getOrderid();
            //订单列表中某个订单的详细信息
            ServerResponse orderdetail = orderController.orderuserdetail(orderid);
            OrderRes3 orderRes3 = (OrderRes3) orderdetail.getData();
            //订单列表中各个订单的详细信息列表
            ArrayList<OrderRes3> orderRes3s = new ArrayList<>();
            orderRes3s.add(orderRes3);
        }
        */
        //orderRes3s包含了orderRes2s和orderRes的信息
        modelAndView.addObject("orderRes3s", orderRes3s);
        return modelAndView;
    }

    //钱包页
    @GetMapping("/passenger/gowallet")
    public ModelAndView passengergowallet() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("paswallet");
        modelAndView.addObject("p_user",p_user);
        modelAndView.addObject("wallet", wallet);
        return modelAndView;
    }

    //钱包充值
    @PostMapping("/wallet/recharge")
    public ServerResponse walletrecharge(int userid, int amount) {
        return null;
    }

    //钱包提现
    @PostMapping("/wallet/withdrawal")
    public ServerResponse walletwithdrawal(int useri, int amount) {
        return null;
    }

    //检索页
    @GetMapping("/passenger/gomap")
    public ModelAndView passengergomap() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pasmap");
        modelAndView.addObject("p_user", p_user);
        return modelAndView;
    }

    //乘客发起订单
    @PostMapping("/passenger/submitorder")
    public ServerResponse passengersubmitorder() {    //从检索页传入两个site，出发地和目的地
        OrderReq1 orderReq1 = new OrderReq1(p_user.getId(), site1, site2);
        return orderController.orderpassengersubmit(orderReq1);
    }

    //乘客取消订单
    @GetMapping("/passenger/cancel")
    public ModelAndView passengercancel() {   //从检索页传入orderid
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pasmap");
        /*
        ServerResponse orderpassengercancel = orderController.orderpassengercancel(1);
        if (orderpassengercancel == null)
            return null;
         */
        return modelAndView;
    }

    //订单详情页(显示当前订单的状况)
    @GetMapping("/passenger/goorderdetail")
    public ModelAndView passengergoorderdetail() {  //传入orderRes1
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("orderRes1", orderRes1);
        //司机接单后
        modelAndView.setViewName("passub");
        /*
        //司机接到乘客后
        modelAndView.setViewName("pasdes");
        //送达目的地后
        modelAndView.setViewName("paspay");
         */
        return modelAndView;
    }

    //支付订单
    @PostMapping("/passenger/pay")
    public ServerResponse passengerpay(int orderid, String paytype) {
        ServerResponse orderpassengerpay = orderController.orderpassengerpay(new OrderReq3(orderid, paytype));
        return orderpassengerpay;
    }

}
