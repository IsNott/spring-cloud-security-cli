package org.nott.cli.api.controller;

import com.alibaba.fastjson.JSONObject;
import org.nott.cli.common.model.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/your-api/")
public class ExampleController {

    @RequestMapping("test")
    public Result test(@RequestBody JSONObject jsonObject){
        return Result.success();
    }
}
