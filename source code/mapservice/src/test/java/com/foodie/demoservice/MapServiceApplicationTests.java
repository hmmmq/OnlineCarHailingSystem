package com.foodie.demoservice;

import com.foodie.demoservice.dto.MapReq1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MapServiceApplicationTests {

	@Autowired
	MapService mapService;


	@Test
	void contextLoads() {
		MapReq1 mapReq1=new MapReq1();
		mapReq1.setLongitude1(40.01116);
		mapReq1.setLatitude1(116.339303);
		mapReq1.setLongitude2(39.936404);
		mapReq1.setLatitude2(116.452562);
		mapService.maproute(mapReq1);

	}

}
