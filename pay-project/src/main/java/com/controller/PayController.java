package com.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.client.ScenicSpotClient;
import com.pojo.Customer;
import com.pojo.OrderRedis;
import com.pojo.ResultList;
import com.pojo.ScenicSpot;
import com.sun.deploy.net.HttpResponse;
import com.util.ErweimaUtils;
import com.util.TokenUtils;
import org.apache.http.protocol.RequestUserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = {"http://localhost:8080", "http://3b3663384p.qicp.vip"}, allowCredentials = "true")
@RestController
@RequestMapping("/pay")
public class PayController {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ScenicSpotClient scenicSpotClient;

    //    @RequestMapping("/appoin{cert_id}")
//    public List<AppPatiDoc> appoin(@PathVariable String cert_id){
//        return iapds.getAppPatiDocByCert_id(cert_id);
//    }
    @RequestMapping("/map")
    public String map(@RequestBody Map map) {
        double lat = Double.parseDouble( (String) map.get("lat"));
        double lnt = Double.parseDouble((String)map.get("lnt"));
        String s = UUID.randomUUID().toString();
        String s1 = null;
        try {
            s1 = ErweimaUtils.writeToLocal(s, lat, lnt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s1;
    }

    @RequestMapping("/test")
    public ResultList test(HttpServletRequest request) {
//      Customer customerByToken = TokenUtils.findCustomerByToken(request);
//      System.out.println(customerByToken);
//        String date = ((String) map.get("date")).substring(0, 10);
//        System.out.println(date);
//        System.out.println(map.get("ticknum"));
//        System.out.println(map.get("scenicSpotId"));
        //-----------token---
        String cookie = request.getHeader("cookie");
        Cookie[] cookies = request.getCookies();
        System.out.println(cookies);
        String token = TokenUtils.getToken(cookies);
        System.out.println(token);
        ResultList resultList = new ResultList();
        resultList.setMessage(token);
        return resultList;
    }

    @RequestMapping("/Alipay")
    public String pay(@RequestBody Map map, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
        //应用私钥（支付助手）  支付宝公钥


        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", "2021000118658603", "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCply5jey5HHQJb21JCuINUHNJs9a9e7j3i867o5Ip01SXxPaGuv5ZWsGJkcuIrRbInY/YlORSX7pTUPkycM9nhYD84R+lH17UgTgCTTsGlQmowAmfW2AuT9HqGirti3AjcrFYnl3M3rgtJrlivXVeME6MKLMTKtMBJZbxwwWA+WUe33olpf44NFi4mpsQjxB7/OMc1aMPU7Nvb4czqrdz3O2+01JJJ62mrak5fE04gS0AcStTcNd+iFOIwntdMWTjIhifW+3VfC+04tgRFDIxaPHVKYfikBGFjM29g4eIDIYBwzr/mo9CAqKXxeaQD3LfOh2BwL7fwjHh4yslruHVfAgMBAAECggEAawuxTTvYhyypa3+xmcnLo5EZxYsCqiIpUBLOqdRwDLTp4S8s2he2dnuZb5wQZI32mOSA3xf7hrcinCHCy6ny8k6FOSoy9pUSkBSMSm8gzgZw7mLmVndCP57GpBv3kbwfn+Lr8sum/1NNbrGs6uw5MYLHm8mMYgLbiLi9zFJTRKaeQ8dw0JWKIhXKE/O6nUyWfAbwNN5er4c6JT3Q1yI0Y1r6pE8W9JoVYMUq6PPv8csLjV/8xw7lOjR5GjNQRij4zBHvhj8ZwAWsH8YuSEKnvGfZzFEPC0uBnTkyBlCLmANSUXt3THSWErgR7eh3Y2CELW0KTC066euKUk9/FGs7OQKBgQDxHQAsGDtGVWgw1RJr0DASyLXs9UYufTKa7B4DAdJ8EBaZP50UksXFd5Rpga/ft2KfvW3bzIB1NOjUu2v2gE3ElxuOANuqUf5S+vlerI9BdGHYqyqFfzJGgkWgFunK0tuJeGKJjQOkPBwBRKGzVXgRjASne3niUI2ZCqhiFDhl5QKBgQC0D7QJouQU4AK4uH1G5kNk6+AIDYjG5qB+MAIIPyr0DL41IL018WleWowlxOUuIBBXl1LjRXbMc6jUXVoSvEGfRV6sB43bFavN+wKxuO+n/cAWK1zIZpUkf0tF8blAxy/QYuKLS0Z2BtYyI25y80WXkyhtfFcuQYAeRoKwMYT58wKBgQC3vRfigly5TmBlxhmRm0bnKZipiIgA6Vtk/8YnGH5kGIaAJh/4C5k2z9eDR1bVLxSzElHji8Xgi39ajbDKWh/pThWrcy/ybSVX6vWZlfpdMOlXiaiPrsyLOr8ALjXfYCv4aIr+sz0xLLVSqhBnbxxekssBLnFFa4lcNOj4RNxtmQKBgDa5ZxBer08g3fLiL0DzDpyHi6km4+D/ituPH67988IEdXKUJq1UV5/TiTCZbMXd/NmCJjDolbiBllgknxF+obsUTDegfB6PsY2CskjtWfkGh/C08Rf/BWj4Pxpc4t6rKv78brnDAQEyBrtqRVEuWoI8uVa9KYnnYlbROzrtceq9AoGARMgULw7gTWPn3uRAOVrmHuxofrFnhNJp7onTiazSgtEhyKicCByLJAcZ2txboSTxlMy4eErfzo/I0HyVGCdR/UdreCx1CxwYAQYLoitxWTavmHFHMZ0HDVwdVpEbWj2YfOzcnSxCW/hYiWpG2wjabp0pSvoLvRYsd3MKfYS8LvA=", "json", "utf-8", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA7kjpHb86UHmhj5u5UYctoR172ljs5X85mXYv3Ae2jegmnX6MPUsGEMO7JU4YLjX0igLwI1jdH3gBuzcPhOB3xq8J98D/5EonybnOYh1/2N+KIpbv+GudnrUQ6Yfw+wc8lyjqv41m1K63vrVN4l8uAiRny9SKxgN/Vscavb7haimEloey5k0RtCY5HTKiKWdSfSRCuvGqek+O7zLKALhuiuDSPIoSbdCQNWdj81dWMBdye+EssM/MPooeapvWWSotB3PBzG6zWqLTtMvAriOYvZDxuTf+mJgqAqM9+AyQI+ulPi3OaJ4OVjOlWUKEGhMTaAamlM0CC21VxGOo7HCMqwIDAQAB", "RSA2");   //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest(); //创建API对应的request
        alipayRequest.setReturnUrl("http://localhost:8080/index");
        //支付宝回调接口
        alipayRequest.setNotifyUrl("http://3b3663384p.qicp.vip/pay-project/pay/payReturn/"+6657); //在公共参数中设置回跳和通知地址
        // alipayRequest.putOtherTextParam("app_auth_token", "201611BB8xxxxxxxxxxxxxxxxxxxedcecde6");//如果 ISV 代商家接入电脑网站支付能力，则需要传入 app_auth_token，使用第三方应用授权；自研开发模式请忽略
        // \"out_trade_no\":\"123456789009876542222222222\","  +
        //产生订单并且放入redis中
        //Customer customerByToken = TokenUtils.findCustomerByToken(httpRequest);
        Customer customerByToken = new Customer();
        customerByToken.setCustomerId("6657");

        String scenicSpotId = (String) map.get("scenicSpotId");
        ScenicSpot scenicSpot = scenicSpotClient.findByScenicSpotId(scenicSpotId);
        OrderRedis orderRedis = new OrderRedis();
        orderRedis.setOrderOccurrenceTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        orderRedis.setOrderBookingTime(((String) map.get("date")).substring(0, 10));
        String oid = UUID.randomUUID().toString();
        orderRedis.setOrderId(oid);
        orderRedis.setOrderState(0);
        orderRedis.setScenicSpotName(scenicSpot.getScenicSpotName());
        orderRedis.setScenicSpotAddr(scenicSpot.getScenicSpotAddr());
        orderRedis.setScenicSpotPhone(scenicSpot.getScenicSpotPhone());
        orderRedis.setScenicSqotPhoto(scenicSpot.getScenicSqotPhoto());
        int ticknum = (Integer) map.get("ticknum");
        Double totalPrice = ticknum * (Double.parseDouble(scenicSpot.getScenicSqotPrice()));
        orderRedis.setTotalPrice(totalPrice);
        //订单绑定用户id 存入redis中
        redisTemplate.opsForHash().put(customerByToken.getCustomerId().toString(), oid.toString(), orderRedis);
        //五分钟内未支付将会过期
//        System.out.println(redisTemplate.opsForHash().entries(oid.toString()));
//        System.out.println(redisTemplate.keys(oid));
        redisTemplate.expire(customerByToken.getCustomerId().toString(), 5, TimeUnit.MINUTES);
//        int appointment_id = (int)(Math.random() * 100000000);
//        int pay = (int) (Math.random()*100);

        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + oid + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + totalPrice + "," +
                "    \"subject\":\"请支付\"," +
                "    \"body\":\"总票价\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }" +
                "  }"); //填充业务参数
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();  //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
//        httpResponse.setContentType( "text/html;charset="  + "utf-8");
//        httpResponse.getWriter().write(form); //直接将完整的表单html输出到页面
//        httpResponse.getWriter().flush();
//        httpResponse.getWriter().close();
        //  返回前端
        return form;
    }

    @RequestMapping("/payReturn/{customerId}")
    public String returnCallBack(@PathVariable("customerId")String customerId,HttpServletRequest request, HttpResponse httpResp) throws AlipayApiException {
        Map<String, String> paramsMap = convertRequestParamsToMap(request);
        //支付宝公钥
        boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA7kjpHb86UHmhj5u5UYctoR172ljs5X85mXYv3Ae2jegmnX6MPUsGEMO7JU4YLjX0igLwI1jdH3gBuzcPhOB3xq8J98D/5EonybnOYh1/2N+KIpbv+GudnrUQ6Yfw+wc8lyjqv41m1K63vrVN4l8uAiRny9SKxgN/Vscavb7haimEloey5k0RtCY5HTKiKWdSfSRCuvGqek+O7zLKALhuiuDSPIoSbdCQNWdj81dWMBdye+EssM/MPooeapvWWSotB3PBzG6zWqLTtMvAriOYvZDxuTf+mJgqAqM9+AyQI+ulPi3OaJ4OVjOlWUKEGhMTaAamlM0CC21VxGOo7HCMqwIDAQAB", "utf-8", "RSA2");//调用SDK验证签名
        System.out.println(customerId);
        if (signVerified) {
            //  验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            // 拿到返回的map数据 内部验证订单号和支付金额 然后让订单支付情况为已支付
            String orderId = (String) paramsMap.get("out_trade_no");
            List list = redisTemplate.opsForHash().values(customerId.toString());
            System.out.println(list);
            Map o = null;
            for (Object x:list) {
                o = (Map) x;
                 if(o.get("orderId").equals(orderId)){
                     o.put("orderState",1);
                     redisTemplate.opsForHash().delete(customerId.toString(), orderId.toString());
                     redisTemplate.opsForHash().put(customerId.toString(), orderId.toString(), o);
                 }
            }
            return "success";
        } else {
            //  验签失败则记录异常日志，并在response中返回failure.
            return "fail";
        }
    }

    // 将request中的参数转换成Map
    private static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<String, String>();
        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;
            //            爆裂吧 现实，粉碎吧 精神，放逐这个世界！
            //            邪王真眼！
            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.toString().substring(1));
            } else {
                retMap.put(name, "");
            }
        }
        return retMap;
    }


//丛雨:明后天也要加倍努力哦
    //丛雨:拿出精神！难过的时候更要保持笑容
    //唔！好！
    //明天也要加油哦--！哦--！
    //丛雨:哦--！
    //感觉精神又提起一点呢！


}
