package org.nott.feign;

import com.alibaba.fastjson.JSONObject;
import org.nott.cli.common.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@FeignClient(path = "/your-api/")
public interface ExampleClient {

    @RequestMapping("test")
    Result<List<Map<String,Object>>> test(@RequestBody JSONObject jsonObject);
}
