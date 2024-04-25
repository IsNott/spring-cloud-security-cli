package org.nott.cli.api.controller;

import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import org.nott.cli.api.interfaces.ExampleApi;
import org.nott.cli.common.model.Result;
import org.nott.cli.service.mapper.CommonMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ExampleController implements ExampleApi {

    @Resource
    private CommonMapper commonMapper;

    @Override
    public Result<List<Map<String,Object>>> test(@RequestBody JSONObject jsonObject){
        List<Map<String, Object>> result = commonMapper.executeSql("select * from test");
        return Result.success(result);
    }
}
