package com.foodie.demoservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.foodie.common.dto.ServerResponse;
import com.foodie.common.pojo.Route;
import com.foodie.demoservice.dto.MapReq1;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MapServiceImpl implements MapService {

    @Override
    public ServerResponse<Route> maproute(MapReq1 mapReq1) {
        Double latitude1 = mapReq1.getLatitude1();
        Double latitude2 = mapReq1.getLatitude2();
        Double longitude1 = mapReq1.getLongitude1();
        Double longitude2 = mapReq1.getLongitude2();
        try {
            //创建http工具（理解成:浏览器） 发起请求，解析响应
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //创建HttpPOST请求对象
            HttpGet get = new HttpGet("https://api.map.baidu.com/directionlite/v1/driving?origin="+longitude1+","+latitude1+"&destination="+longitude2+","+latitude2+"&ak=GdUcRBRPxzyignf3Rdxjkgv0EOlIzDSd");
            //所有请求参数
            List<NameValuePair> params = new ArrayList<>();
            //创建响应对象
            CloseableHttpResponse response = httpClient.execute(get);
            //由于响应体是字符串，因此把HttpEntity类型转换为字符串类型
            String resultstr = EntityUtils.toString(response.getEntity());
            //输出结果
            System.out.println(resultstr);
            //释放资源
            response.close();
            httpClient.close();
            //装配
            HashMap map = JSON.parseObject(resultstr, HashMap.class);
            JSONObject result =(JSONObject) map.get("result");
            JSONArray routes =(JSONArray) result.get("routes");
            JSONObject routejson = (JSONObject)routes.get(0);
            Route route=new Route();
            route.setToll((Integer) routejson.get("toll"));
            route.setDistance((Integer) routejson.get("distance"));
            route.setTimecost((Integer) routejson.get("duration"));
            return ServerResponse.createBySuccess(route);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
