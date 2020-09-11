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

@CrossOrigin(origins = {"http://localhost:8080","http://3b3663384p.qicp.vip"}, allowCredentials = "true")
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
        //

        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", "2021000118658603", "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCCA/vL3n/csSK4L2uZe1ALmm2d4+G73lC/4DJn8wVmXEFCVs/nwU0H6+yCbi8PuzNfQMw0R7F6ccEb0ApW6C6A4xanOlEGLyAG1udzdVYUpKtLp+9/k8bzT6nfowD2NtJl3HcSZEq0nTq30pZ39MgcJY5DdGWGjHQgbUfHZOvAD4fUoQJAcAqqgwxo8ZX0oV4mRzR4fbZO7oNrAq863imfYD93hCCGxa2yJ57vH0uA8tHzRSqTh9CU1FYQAHY+Q7MG6Gy8uZrfPOI9RIOPNLtDiDmP3SPp8FPn+0s2NnMlHnSSd+U/XBHu/cxjyrX922PsrabTb6FPBZHQJsiFWlMFAgMBAAECggEAIDa4dqTkbVv+TECDmn2xMY7LxikmTxQgUcpM8vCFz/zftn4n+TYtzd6otRBz3KP3cIxvjCyPTtVMkYWUakAQ9aJOhJSwaqXWe5tXsYXLFEZQougzMRr+Wl7MqjqLDHY83zwmzInI8MNcPxuQaJxf0ImFnDUEyreWNkqdVEwvFWDpKn3SZGmEWDdmu/Vnm8EgGn9W4YQRt7YRW2xvmv6O/3oH0rqpKx3v9HgYL9KeL0qQBmjOxuS5EHc+iYpORIbYfPIM8roGiFPFKv9+9n4pWo5I6WTF5xAoTH61cJeuFZUy4Mcs7avLpklifibKIzzYeJb/o44HC3kjC8SRmIOsgQKBgQDBObgDiXyrNWNGA3fTNzwOjNfHEkkdg8mMEldNFwkyjRTU03hEIj+h+MjxuY4S+DMmgCF9KtBNMOwvAsdymjA3d7SsS8G4AErA33SzkVKKRkhdJWs00EodUZ5No/MdkVDKD6zKGo+uO7kATNWAsRxToQk0dFdqpw2JQSp5kK9yMQKBgQCsQTBd/2D2bpnpN3hBCN6Mvm4Mvkm8FIU2oxX5Lq0MCfxZEOpIM1lxsFnns1Tm5uNvxx/wIiB7BAw9XPuYS93NCemHjBHQ/Lzh6fzvFNoXH18l62lmcpDQUnYPBNxHx0XzEGXtgUyCsaT0VJy6TAmDTD/0NRGtWLqKMxxCY6sFFQKBgElAZWdJ3u5U/f6T4AA9p1HoyQCaCQfcZzLn3RrHieAVJfmM9cH2reLsmtkwvBCq8VE+L/PwoCkzS8aCqjPjCjZs9hx/V8A2tCAsVmSgygbLoDFXty29bOtyfq32YnsqFNOVX676pM+rqnXXqvTvr8pweTnehSlUjUgkYULWu/xxAoGADIF3F3vw+DMzbWEfk+km7yTsLHSsOcFronnYoIyo95TKA1VdDPzdY4mgHVhBs7nTjUvxn6d6oDO9B792bKolYjtz/ulGadvPgr9QI7Np2f/nEoOy1UNADD/QXmrF61gmVe082S9n9J0dErgJ4OSgVC32/q3TqLgmYt4vEQrXYbkCgYEAqUneoXzcBk8cmSRX6+YlnH54tesg3Xtg7moD73sRdPKq5mv5FKHHUyGnvds8oNtaCUuxdwJEZKRQBhGiFyOXbWBBlRefrMwdU8+UcLyzPDA45o3fFVwZYiL6el0Mb10GVEqreCfF5fBooHNRLmCVYsyN3YF7EwSfNlEdrGWvZo0=", "json", "utf-8", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAggP7y95/3LEiuC9rmXtQC5ptnePhu95Qv+AyZ/MFZlxBQlbP58FNB+vsgm4vD7szX0DMNEexenHBG9AKVugugOMWpzpRBi8gBtbnc3VWFKSrS6fvf5PG80+p36MA9jbSZdx3EmRKtJ06t9KWd/TIHCWOQ3Rlhox0IG1Hx2TrwA+H1KECQHAKqoMMaPGV9KFeJkc0eH22Tu6DawKvOt4pn2A/d4QghsWtsiee7x9LgPLR80Uqk4fQlNRWEAB2PkOzBuhsvLma3zziPUSDjzS7Q4g5j90j6fBT5/tLNjZzJR50knflP1wR7v3MY8q1/dtj7K2m02+hTwWR0CbIhVpTBQIDAQAB", "RSA2");  //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest(); //创建API对应的request
        //alipayRequest.setReturnUrl("http://localhost:8080/??");
        //支付宝回调接口
        alipayRequest.setNotifyUrl("http://3b3663384p.qicp.vip/pay-project/Alipay/payReturn"); //在公共参数中设置回跳和通知地址
        // alipayRequest.putOtherTextParam("app_auth_token", "201611BB8xxxxxxxxxxxxxxxxxxxedcecde6");//如果 ISV 代商家接入电脑网站支付能力，则需要传入 app_auth_token，使用第三方应用授权；自研开发模式请忽略
        // \"out_trade_no\":\"123456789009876542222222222\","  +
        //产生订单并且放入redis中
        Customer customerByToken = TokenUtils.findCustomerByToken(httpRequest);
        String scenicSpotId = (String)map.get("scenicSpotId");
        ScenicSpot scenicSpot = scenicSpotClient.findByScenicSpotId(scenicSpotId);
        OrderRedis orderRedis = new OrderRedis();
        orderRedis.setOrderOccurrenceTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        orderRedis.setOrderBookingTime(((String) map.get("date")).substring(0,10));
        String oid = UUID.randomUUID().toString();
        orderRedis.setOrderId(oid);
        orderRedis.setOrderState(0);
        orderRedis.setScenicSpotName(scenicSpot.getScenicSpotName());
        orderRedis.setScenicSpotAddr(scenicSpot.getScenicSpotAddr());
        orderRedis.setScenicSpotPhone(scenicSpot.getScenicSpotPhone());
        orderRedis.setScenicSqotPhoto(scenicSpot.getScenicSqotPhoto());
        int ticknum = (Integer) map.get("ticknum");
        Double totalPrice = ticknum*(Double.parseDouble(scenicSpot.getScenicSqotPrice()));
        //订单绑定用户id 存入redis中
//        redisTemplate.opsForHash().put(oid.toString(),orderRedis);
        //五分钟内未支付将会过期
        redisTemplate.expire("order_"+customerByToken.getCustomerId().toString(),5,TimeUnit.MINUTES);
//        int appointment_id = (int)(Math.random() * 100000000);
//        int pay = (int) (Math.random()*100);

        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + oid + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + totalPrice + "," +
                "    \"subject\":\"挂号费\"," +
                "    \"body\":\"挂号费\"," +
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

    @RequestMapping("/payReturn")
    public String returnCallBack(HttpServletRequest request, HttpResponse httpResp) throws AlipayApiException {
        Map<String, String> paramsMap = convertRequestParamsToMap(request);
        //支付宝公钥
        boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA7kjpHb86UHmhj5u5UYctoR172ljs5X85mXYv3Ae2jegmnX6MPUsGEMO7JU4YLjX0igLwI1jdH3gBuzcPhOB3xq8J98D/5EonybnOYh1/2N+KIpbv+GudnrUQ6Yfw+wc8lyjqv41m1K63vrVN4l8uAiRny9SKxgN/Vscavb7haimEloey5k0RtCY5HTKiKWdSfSRCuvGqek+O7zLKALhuiuDSPIoSbdCQNWdj81dWMBdye+EssM/MPooeapvWWSotB3PBzG6zWqLTtMvAriOYvZDxuTf+mJgqAqM9+AyQI+ulPi3OaJ4OVjOlWUKEGhMTaAamlM0CC21VxGOo7HCMqwIDAQAB", "utf-8", "RSA2"); //调用SDK验证签名
        if (signVerified) {
            //  验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            System.out.println(paramsMap);
            // 拿到返回的map数据 内部验证订单号和支付金额 然后让订单支付情况为已支付
            String orderId = (String)paramsMap.get("out_trade_no");
            Customer customerByToken = TokenUtils.findCustomerByToken(request);
            List values = redisTemplate.opsForHash().values("order_" + customerByToken.getCustomerId().toString());
            for (Object x:values) {
                OrderRedis o = (OrderRedis)x;
                if(o.getOrderId().equals(orderId)){
                    o.setOrderState(1);
                    redisTemplate.opsForHash().delete("order_"+customerByToken.getCustomerId().toString(),o.getOrderId());
                    redisTemplate.opsForHash().put("order_"+customerByToken.getCustomerId().toString(),o.getOrderId(),o);

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
        //            人这种东西啊，实在是太有趣了，让我欲罢不能呢
        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();

        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            //            今天的风儿甚为喧嚣啊

            int valLen = values.length;
            //            爆裂吧 现实，粉碎吧 精神，放逐这个世界！
            //            邪王真眼！
            if (valLen == 1) {
                //  错的不是我，是这个世界！
                retMap.put(name, values[0]);
                //这是命运石之门的选择
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                //我是新世界的神
                for (String val : values) {
                    sb.append(",").append(val);
                    //能打败我的只有我自己
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
