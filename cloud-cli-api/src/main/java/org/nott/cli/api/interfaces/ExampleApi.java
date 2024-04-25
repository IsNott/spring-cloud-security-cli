package org.nott.cli.api.interfaces;

import com.alibaba.fastjson.JSONObject;
import org.nott.cli.common.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Component
@FeignClient(value = "cloud-cli",path = "/your-api")
@RequestMapping("/your-api/")
public interface ExampleApi {

    @RequestMapping("test")
    Result<List<Map<String,Object>>> test(@RequestBody JSONObject jsonObject);
}
