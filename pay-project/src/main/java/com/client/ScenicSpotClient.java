package com.client;

import com.pojo.ScenicSpot;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(serviceId = "scenicSpot-project")
public interface ScenicSpotClient {
    @RequestMapping("/scenicSpot/findByScenicSpotId/{scenicSpotId}")
    public ScenicSpot findByScenicSpotId(@PathVariable("scenicSpotId") String scenicSpotId);
}
