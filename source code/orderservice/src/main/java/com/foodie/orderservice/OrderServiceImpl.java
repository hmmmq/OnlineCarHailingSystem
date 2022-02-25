package com.foodie.orderservice;
import com.foodie.common.dto.ResponseCode;
import com.foodie.common.dto.ServerResponse;
import com.foodie.common.pojo.*;
import com.foodie.orderservice.dto.*;
import com.foodie.orderservice.feign.*;
import com.foodie.orderservice.mapper.*;
import com.foodie.orderservice.mapper.dto.*;
import com.foodie.orderservice.mapper.test.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.attribute.standard.Destination;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrdercommentMapper ordercommentMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderpayMapper orderpayMapper;
    @Autowired
    OrderstateMapper  orderstateMapper;
    @Autowired
    OrdertimeMapper ordertimeMapper;
    @Autowired
    SiteMapper siteMapper;
    @Autowired
    RouteMapper routeMapper;
    @Autowired
    OrderRes11Mapper orderRes11Mapper;

    OrderRes2Mapper orderRes2Mapper;

    OrderRes3Mapper orderRes3Mapper;

    OrderRes4Mapper orderRes4Mapper;

    DispatchService dispatchService;

    InformService informService;

    MapService mapService;

    DriverorderMapper driverorderMapper;

    private RedisTemplate redisTemplate;
    Long fare=0l;


    @SneakyThrows
    @Override
    @Transactional
    public ServerResponse<OrderRes1> orderpassengersubmit(OrderReq1 orderReq1) {
        //生成订单
        Order order=new Order();
        order.setPassengerid(orderReq1.getUserid());
        int orderid = orderMapper.insert(order);
        insertordertime(orderid);
        insertorderpay(orderid,orderReq1.getUserid(),fare);
        insertordercomment(orderid);
        insertorderstate(orderid);
        Integer prouteid = insertrouteandsite(orderReq1.getDeparture(), orderReq1.getDestination(), orderid);
        Integer pdrouteid = insertrouteandsite(new Site(), orderReq1.getDeparture(), orderid);
        order.setOrdertimeid(orderid);
        order.setOrderpayid(orderid);
        order.setOrdercommentid(orderid);
        order.setOrderstateid(orderid);
        //wait for mapservice
        do {
            Thread.sleep(3000);
        }while (null==prouteid||null==pdrouteid||prouteid==pdrouteid);
        order.setProuteid(prouteid);
        order.setPdrouteid(pdrouteid);
        orderMapper.updateByPrimaryKey(order);
        //更新司机可接单列表, 通知有新的订单
        updatedriverorderlist(orderReq1.getDeparture(),orderid);
        // 阻塞至有司机接单
        waittakeorder(orderid);
        // 更新车费
        updateFare(prouteid,pdrouteid,orderid);
        // 返回前端数据
        OrderRes11 orderRes11 = orderRes11Mapper.select(orderid,orderReq1.getUserid());
        OrderRes1 orderRes1=transform(orderRes11);
        return ServerResponse.createBySuccess(orderRes1);
    }


    private OrderRes1 transform(OrderRes11 orderRes11) {
        OrderRes1 orderRes1=new OrderRes1();
        orderRes1.setOrderid(orderRes11.getOrderid());
        orderRes1.setFare(orderRes11.getFare());
        User passenger=new User();
        passenger.setId(orderRes11.getPassengerid());
        passenger.setName(orderRes11.getPassengerphone());
        passenger.setPhone(orderRes11.getPassengername());
        User driver=new User();
        driver.setId(orderRes11.getDriverid());
        driver.setName(orderRes11.getDriverphone());
        driver.setPhone(orderRes11.getDrivername());
        Route1 route1=new Route1();
        route1.setId(orderRes11.getPdrouteid());
        route1.setTimecost(orderRes11.getTimecost());
        route1.setDistance(orderRes11.getDistance());
        route1.setDestinationid(orderRes11.getDestinationid());
        route1.setDepartureid(orderRes11.getDepartureid());
        route1.setToll(orderRes11.getToll());
        route1.setType((byte) 1);
        Site departure=new Site();
        departure.setOrderid(orderRes11.getOrderid());
        departure.setRouteid(orderRes11.getPdrouteid());
        departure.setId(orderRes11.getDepartureid());
        departure.setName(orderRes11.getDeparturename());
        Site destination=new Site();
        destination.setOrderid(orderRes11.getOrderid());
        destination.setRouteid(orderRes11.getPdrouteid());
        destination.setId(orderRes11.getDestinationid());
        destination.setName(orderRes11.getDestinationname());
        route1.setDeparture(departure);
        route1.setDestination(destination);
        Car car=new Car();
        car.setId(orderRes11.getCarid());
        car.setUserid(orderRes11.getDriverid());
        car.setBrand(orderRes11.getCarbrand());
        car.setNumber(orderRes11.getCarnumber());
        orderRes1.setPassenger(passenger);
        orderRes1.setDriver(driver);
        orderRes1.setRoute(route1);
        orderRes1.setCar(car);
        return orderRes1;
    }

    @Override
    @Transactional
    public ServerResponse orderpassengercancel(Integer orderid) {
        //1、异步更新orderstate
        Order order = orderMapper.selectByPrimaryKey(orderid);
        Orderstate orderstate = getOrderstate(order.getOrderstateid());
        orderstate.setId(orderid);
        orderstate.setFinishstate((byte) 1);
        orderstate.setCancelstate((byte) 1);
        orderstateMapper.updateByPrimaryKey(orderstate);
        return ServerResponse.createByresponseCode(ResponseCode.SuccessfullyUpdate);
    }

    @Override
    @Transactional
    public ServerResponse<Route> orderdrivertake(OrderReq4 orderReq4) {
        //1、存储司机site,计算距离和费用
        Integer orderid = orderReq4.getOrderid();
        Site driversite = orderReq4.getDriversite();
        Site passengersite = orderReq4.getPassengersite();
        Integer driverid = orderReq4.getDriverid();
        MapReq1 mapReq1=new MapReq1();
        mapReq1.setLatitude1(driversite.getLatitude());
        mapReq1.setLongitude1(driversite.getLongitude());
        mapReq1.setLatitude2(passengersite.getLatitude());
        mapReq1.setLongitude1(passengersite.getLongitude());
        ServerResponse<Route> maproute = mapService.maproute(mapReq1);
        Route data = maproute.getData();
        Integer toll = data.getToll();
        Integer distance = data.getDistance();
        Integer timecost = data.getTimecost();
        siteMapper.insert(driversite);
        //2、插入pdroute至route表
        int pdrouteid = routeMapper.insert(data);
        //3、异步更新order表
        Order order = orderMapper.selectByPrimaryKey(orderid);
        order.setPdrouteid(pdrouteid);
        order.setDriverid(driverid);
        order.setCarid(orderReq4.getCarid());
        orderMapper.updateByPrimaryKey(order);
        //4、异步更新orderstate表
        Orderstate orderstate = orderstateMapper.selectByPrimaryKey(orderid);
        orderstate.setPickupstate((byte) 1);
        //5、异步发送消息给客户端，提示已有司机接单
        return ServerResponse.createBySuccess(new Route1());
    }

    @Override
    @Transactional
    public ServerResponse<Route> mapdriverstartitinerary(Integer orderid) {
        //1、异步更新orderstate
        Orderstate orderstate = getOrderstate(orderid);
        orderstate.setItinerarystate(1);
        //2、返回乘客路线图
        Order order = orderMapper.selectByPrimaryKey(orderid);
        Route route = getRoute(order);
        return ServerResponse.createBySuccess(route);
    }

    @Override
    @Transactional
    public ServerResponse orderdriverrequestpay(OrderReq2 orderReq2) {
        //1、异步更新orderstate
        Orderstate orderstate = getOrderstate(orderReq2.getOrderid());
        orderstate.setItinerarystate(2);
        orderstateMapper.updateByPrimaryKey(orderstate);
        //2、异步更新费用
        Order order = orderMapper.selectByPrimaryKey(orderReq2.getOrderid());
        Orderpay orderpay = orderpayMapper.selectByPrimaryKey(order.getOrderpayid());
        orderpay.setFare(orderReq2.getFare());
        orderpayMapper.updateByPrimaryKey(orderpay);
        //3、异步通知乘客支付
        return ServerResponse.createByresponseCode(ResponseCode.SuccessfullyUpdate);

    }

    @Override
    @Transactional
    public ServerResponse orderpassengerpay(OrderReq3 orderReq3) {
        //1、异步更新orderpay表
        Order order = orderMapper.selectByPrimaryKey(orderReq3.getOrderid());
        Orderpay orderpay = getOrderpay(order);
        orderpay.setPaytype(orderReq3.getPaytype());
        orderpayMapper.updateByPrimaryKey(orderpay);
        //2、返回支付页面
        return ServerResponse.createByresponseCode(ResponseCode.SuccessfullyUpdate);
    }

    @Override
    public ServerResponse<List<OrderRes2>> orderuserlist(Integer userid) {
//        //1、根据userid查询order类
//        Order order = orderMapper.selectByUserId(userid);
//        //2、查询proute,异步
//        Route route = getRoute(order);
//        //3、查询orderstate，异步
//        Orderstate orderstate = getOrderstate(order.getOrderstateid());
//        //4、查询ordertime，异步
//        Ordertime ordertime = getOrdertime(order);
//        //5、装配OrderRes2，异步
//        OrderRes2 orderRes2 = new OrderRes2();
//        orderRes2.setOrderid(order.getId());
//        orderRes2.setRoute(route);
//        orderRes2.setOrdertime(ordertime);
//        orderRes2.setOrderstate(orderstate);
        List<OrderRes2> orderRes2List = orderRes2Mapper.select(userid);
        return ServerResponse.createBySuccess(orderRes2List);
    }

    @Override
    public ServerResponse<OrderRes3> orderuserdetail(Integer orderid) {
        OrderRes3 orderRes3 = orderRes3Mapper.select(orderid);
        return ServerResponse.createBySuccess(orderRes3);
    }

    @Override
    public ServerResponse<List<OrderRes4>> orderdriverlist(Site site) {
        //根据司机id查找对应的可接单列表,redis里
        //返回List<OrderRes4>
        List<OrderRes4> orderRes4List = orderRes4Mapper.select(site);
        return ServerResponse.createBySuccess(orderRes4List);
    }

    // 私有方法
    private Orderstate getOrderstate(Integer orderstateid) {
        return orderstateMapper.selectByPrimaryKey(orderstateid);
    }

    private Route getRoute(Order order) {
        return routeMapper.selectByPrimaryKey(order.getProuteid());
    }

    private Orderpay getOrderpay(Order order) {
        return orderpayMapper.selectByPrimaryKey(order.getOrderpayid());
    }

    // insert方法
    @Transactional
    public Integer insertrouteandsite(Site departure, Site destination, Integer orderid){
        //0、初始化route
        Route route=new Route();
        route.setOrderid(orderid);
        Route route1=new Route();
        route.setOrderid(2);
        Route route2=new Route();
        route.setOrderid(3);
        try{
            //1、插入route
            int routeid = routeMapper.insert(route);
            int routeid1 = routeMapper.insert(route1);
            int routeid2 = routeMapper.insert(route2);
            //更新获取distance，timecost，toll,异步
            updateroute(departure,destination,route);
            //2、插入site
            departure.setRouteid(routeid);
            departure.setOrderid(orderid);
            destination.setRouteid(routeid);
            destination.setOrderid(orderid);
            int departureid = siteMapper.insert(departure);
            int destinationid = siteMapper.insert(destination);
            //3、更新route
            route.setDepartureid(departureid);
            route.setDestinationid(destinationid);
            routeMapper.updateByPrimaryKey(route);
            return routeid;
        }catch (Exception exception){
            log.print("insertrouteandsite错误"+exception);
        }
        return null;
    }

    @Transactional
    @Async
    private void insertorderstate(Integer orderid){
        Orderstate orderstate=new Orderstate();
        //与orderid相等
        orderstate.setId(orderid);
        //订单是否结束
        orderstate.setFinishstate((byte) 0);
        //0行程未开始,1行程进行中,2行程已结束
        orderstate.setItinerarystate(0);
        //0未取消,1已取消
        orderstate.setCancelstate((byte) 0);
        //0没有司机接单,1有司机接单
        orderstate.setPickupstate((byte) 0);
        int insert = orderstateMapper.insert(orderstate);
        log.print("insertorderstate："+insert);
    }

    @Transactional
    @Async
    private void insertordercomment(Integer orderid){
        Ordercomment ordercomment=new Ordercomment();
        ordercomment.setId(orderid);
        int insert = ordercommentMapper.insert(ordercomment);
        log.print("insertordercomment："+insert);
    }

    @Transactional
    @Async
    private void insertorderpay(Integer orderid,Integer userid,Long fare){
        Orderpay orderpay=new Orderpay();
        orderpay.setId(orderid);
        orderpay.setState(0);
        orderpay.setUserid(userid);
        orderpay.setFare(fare);
        int insert = orderpayMapper.insert(orderpay);
        log.print("insertorderpay："+insert);
    }

    @Transactional
    @Async
    private void insertordertime(Integer orderid){
        Ordertime ordertime=new Ordertime();
        ordertime.setId(orderid);
        ordertime.setStarttime(new Date());
        int insert = ordertimeMapper.insert(ordertime);
        log.print("insertordertime："+insert);
    }

    @Transactional
    @Async
    public void updateroute(Site departure,Site destination,Route route){
        if (departure!=null&&destination!=null){
            MapReq1 mapReq1=new MapReq1();
            mapReq1.setLatitude1(departure.getLatitude());
            mapReq1.setLongitude1(departure.getLongitude());
            mapReq1.setLatitude2(destination.getLatitude());
            mapReq1.setLongitude2(destination.getLongitude());
            try{
                route = mapService.maproute(mapReq1).getData();
            }catch (Exception e){
                log.print("can't retrieve the route");
            }
            routeMapper.updateByPrimaryKey(route);
        }

    }


    @Transactional
    private void updateFare(Integer prouteid, Integer pdrouteid,Integer orderid) {
        Route proute;
        Route pdroute;
        do{
            proute = routeMapper.selectByPrimaryKey(prouteid);
            pdroute  = routeMapper.selectByPrimaryKey(pdrouteid);
            Integer total=proute.getToll()+pdroute.getToll();
            fare=total.longValue();
        }
        while (null==proute.getToll()||null==pdroute.getToll());
        Orderpay orderpay = orderpayMapper.selectByPrimaryKey(orderid);
        orderpay.setFare(fare);
        orderpayMapper.updateByPrimaryKey(orderpay);
    }

    @SneakyThrows
    private void waittakeorder(Integer orderid){
        Integer driverid;
        do {
            Thread.sleep(10000);
            Order order1 = orderMapper.selectByPrimaryKey(orderid);
            driverid = order1.getDriverid();
        }while (driverid==null);

    }
    @Transactional
    @Async
    private void updatedriverorderlist(Site departure,Integer orderid){
        DispatchReq1 dispatchReq1=new DispatchReq1();
        dispatchReq1.setDeparture(departure);
        dispatchReq1.setOrderid(orderid);
        ServerResponse<List<Integer>> driverlist = dispatchService.dispatchorder(dispatchReq1);
        if (!driverlist.getData().isEmpty()){
         List<Driverorder> driverorderList= resembleList(driverlist,orderid);
            driverorderMapper.batchinsert(driverorderList);
            ServerResponse informneworder;
            do {
                informneworder = informService.informneworder(driverlist.getData());
            }while (!informneworder.getMsg().equals(ResponseCode.SuccessfullPublish));
        }
    }

    private List<Driverorder> resembleList(ServerResponse<List<Integer>> driverlist, Integer orderid) {
        List<Integer> data = driverlist.getData();
        List<Driverorder> driverorderList = new ArrayList<>();
        for (Integer driverid:data){
            Driverorder driverorder = new Driverorder();
            driverorder.setDriverid(driverid);
            driverorder.setOrderid(orderid);
            driverorderList.add(driverorder);
        }
        return driverorderList;
    }


}
