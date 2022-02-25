package com.foodie.driver.controller;
import com.foodie.common.pojo.Orderstate;
import com.foodie.common.pojo.Route;
import com.foodie.common.pojo.User;
import com.foodie.common.pojo.Orderpay;

import com.foodie.common.dto.*;
import com.foodie.common.pojo.*;
import com.foodie.driver.feignapi.OrderController;
import com.sun.istack.internal.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;

@Controller
public class PageController {

    OrderController orderController;

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


    private ArrayList<OrderRes2> orderRes2s = new ArrayList<>();
    private ArrayList<OrderRes3> orderRes3s = new ArrayList<>();
    private ArrayList<OrderRes41> orderRes41s = new ArrayList<>();
    private ArrayList<OrderReq4> orderReq4s = new ArrayList<>();
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

            Site site3 = new Site();
            site3.setId(2*i);
            site3.setName("海大东门");
            site3.setLongitude(120.0F);
            site3.setLatitude(20.0F);

            Route1 route1 = new Route1();
            route1.setDeparture(site1);
            route1.setDestination(site2);
            route1.setId(i);
            route1.setDepartureid(1);
            route1.setDestinationid(2);
            route1.setDistance(2);
            route1.setTimecost(60);

            Route1 route2 = new Route1();
            route2.setDeparture(site3);
            route2.setDestination(site1);
            route2.setId(i);
            route2.setDepartureid(1);
            route2.setDestinationid(2);
            route2.setDistance(2);
            route2.setTimecost(60);

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
            OrderRes41 orderRes41 = new OrderRes41();
            orderRes41.setPassenger(user1);
            orderRes41.setOrderid(i);
            orderRes41.setOrderState(orderstate);
            orderRes41.setPdroute(route2);
            orderRes41.setProute(route1);
            orderRes41.setOrderpay(orderpay);
            OrderReq4 orderReq4 = new OrderReq4(i, 2, i, site3, site1);


            //orderRes2s.add(orderRes2);
            orderRes3s.add(orderRes3);
            orderRes41s.add(orderRes41);
            orderReq4s.add(orderReq4);
        }
    }

    private int ordernum = 10;
    private long orderfaretoday = (long) 199;
    private long orderfarepresent = (long) 9.00;

    private Wallet wallet = new Wallet();
    {
        wallet.setId(1);
        wallet.setBalance(100L);
        wallet.setState((byte)0);
        wallet.setUserid(2);
    }

    //司机位置
    Site site3 = new Site();
    {
        site3.setId(1);
        site3.setName("海大东门");
        site3.setLongitude(120.0F);
        site3.setLatitude(20.0F);
    }
    Site site1 = new Site();
    {
        site1.setId(2);
        site1.setName("海大北门");
        site1.setLongitude(120.0F);
        site1.setLatitude(20.0F);
    }

    Route1 routepd = new Route1();
    {
        routepd.setDeparture(site1);
        routepd.setDestination(site3);
        routepd.setId(1);
        routepd.setDepartureid(1);
        routepd.setDestinationid(2);
        routepd.setDistance(2);
        routepd.setTimecost(60);
    }

    OrderRes3 orderRes3 = orderRes3s.get(1);

    //司机主页
    @GetMapping("/driver/goindex")
    public ModelAndView drivergoindex() {    //传入id
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("driverindex");
        //调用服务端，得到用户和车
        /*
        ServerResponse orderuserlist = orderController.orderuserlist(d_user.getId());
        ArrayList<OrderRes2> orderRes2s = (ArrayList<OrderRes2>) orderuserlist.getData();
        int ordernum = 0;
        long orderfaretoday = 0;
        long orderfarepresent = 0;
        for(int i = 0;i < orderRes2s.size();i++)
        {
            ServerResponse orderdetail = orderController.orderuserdetail(orderRes2s.get(i).getOrderid());
            OrderRes3 orderRes3 = (OrderRes3) orderdetail.getData();
            Date date = new Date();
            //得到今日订单数
            if (orderRes2s.get(i).getOrdertime().getEndtime().getDate() == date.getDate())
            {
                ordernum++;
                orderfaretoday += orderRes3.getOrderpay().getFare();
            }
            //得到当前订单价钱
            if (orderRes2s.get(i).getOrderstate().getItinerarystate() == 1)
                orderfarepresent = orderRes3.getOrderpay().getFare();

        }
         */
        modelAndView.addObject("d_user", d_user);
        modelAndView.addObject("car", car);
        modelAndView.addObject("ordernum", ordernum);
        modelAndView.addObject("orderfaretoday", orderfaretoday);
        modelAndView.addObject("orderfarepresent", orderfarepresent);
        return modelAndView;
    }

    //订单列表页
    @GetMapping("/driver/goorderlist")
    public ModelAndView drivergoorderlist() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("driverorderlist");
        /*
        //订单列表
        ServerResponse orderuserlist = orderController.orderuserlist(p_user.getId());
        ArrayList<OrderRes2> orderRes2s = (ArrayList<OrderRes2>) orderuserlist.getData();
        //详细列表
        ArrayList<OrderRes3> orderRes3s = new ArrayList<>();
        for(int i = 0;i < orderRes2s.size();i++)
        {
            int orderid = orderRes2s.get(i).getOrderid();
            //订单列表中某个订单的详细信息
            ServerResponse orderdetail = orderController.orderuserdetail(orderid);
            OrderRes3 orderRes3 = (OrderRes3) orderdetail.getData();
            //订单列表中各个订单的详细信息列表
            orderRes3s.add(orderRes3);
        }
        */
        //orderRes3s包含了orderRes2s和orderRes的信息
        modelAndView.addObject("orderRes3s", orderRes3s);
        return modelAndView;
    }

    //钱包页
    @GetMapping("/driver/gowallet")
    public ModelAndView drivergowallet() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("driverwallet");
        modelAndView.addObject("d_user",d_user);
        modelAndView.addObject("wallet", wallet);
        return modelAndView;
    }

    //司机选择接单页
    @GetMapping("/driver/gotakeorder")
    public ModelAndView drivergotakeorder(Car car, User d_user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("drivertakeorder");
        //得到司机位置site
        /*
        //得到可接单列表orderRes41s
        ServerResponse orderlist = orderController.orderdriverlist(site);
        ArrayList<OrderRes41> orderRes41s = (ArrayList<OrderRes41>) orderlist.getData();
         */
        modelAndView.addObject("orderRes41s", orderRes41s);
        modelAndView.addObject("car", car);
        modelAndView.addObject("d_user", d_user);
        return modelAndView;
    }

    //订单详情页（显示当前订单的状况）
    @GetMapping("/driver/goorderdetail")
    public ModelAndView drivergoorderdetail(Car car, User d_user, OrderRes41 orderRes41) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("drivingtake");
        /*
        //服务端获得司机地点site3
        //乘客地点
        ServerResponse orderdetail = orderController.orderuserdetail(orderRes41.getOrderid());
        OrderRes3 orderRes3 = (OrderRes3) orderdetail.getData();
        OrderReq4 orderReq4 = new OrderReq4(car.getId(), d_user.getId(), orderRes41.getOrderid(), site3, orderRes3.getRoute().getDeparture());
        //司机接单后，前往乘客所在地
        ServerResponse orderdrivertake = orderController.orderdrivertake(orderReq4);
        Route routepd = (Route) orderdrivertake.getData();
         */
        modelAndView.addObject("orderRes3", orderRes3);
        modelAndView.addObject("routepd", routepd);
        return modelAndView;
    }

    //接到乘客去往目的地
    @GetMapping("/driver/godestination")
    public ModelAndView drivergodestination() {//传入订单信息
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("drivingstartitinerary");
        //
        modelAndView.addObject("orderRes3", orderRes3);
        return modelAndView;
    }
}
