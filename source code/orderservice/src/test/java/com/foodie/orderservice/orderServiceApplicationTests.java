package com.foodie.orderservice;

import com.foodie.common.dto.ServerResponse;
import com.foodie.common.pojo.Route;
import com.foodie.common.pojo.Site;
import com.foodie.orderservice.dto.OrderReq1;
import com.foodie.orderservice.dto.OrderRes1;
import com.foodie.orderservice.dto.Route1;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class orderServiceApplicationTests {
    @Autowired
	OrderServiceImpl orderService;

	Site departure=new Site();
	Site destination=new Site();
	{
		departure.setType(true);
		departure.setName("a");
		departure.setLatitude(1281.1212);
		departure.setLongitude(121.1212);
		destination.setType(false);
		destination.setName("b");
		destination.setLatitude(121.1212);
		destination.setLongitude(1201.1212);
		}
	Route1 route=new Route1();
	{
		route.setId(1);
		route.setDestinationid(1);
		route.setDepartureid(2);
		route.setDistance(10);
		route.setToll(5);
		route.setType((byte) 1);
		route.setTimecost(10);
		route.setOrderid(1);
		route.setDestination(destination);
		route.setDeparture(departure);

	}
	OrderReq1 orderReq1=new OrderReq1();
	{
		orderReq1.setDeparture(departure);
		orderReq1.setDestination(destination);
		orderReq1.setUserid(1);
	}

	@Test
	void contextLoads() {
		ServerResponse<OrderRes1> orderpassengersubmit = orderService.orderpassengersubmit(orderReq1);
		OrderRes1 data = orderpassengersubmit.getData();
	}

	@Test
	void testinsertrouteandsite(){
		orderService.insertrouteandsite(departure,destination,1);

	}

	@Test
	void testupdateroute(){
		orderService.updateroute(departure,destination,route);
	}






}
