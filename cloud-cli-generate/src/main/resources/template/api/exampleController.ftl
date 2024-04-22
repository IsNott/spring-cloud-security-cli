package ${packageName}.api.controller;

import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import ${packageName}.common.model.Result;
import ${packageName}.service.mapper.CommonMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/your-api/")
public class ExampleController {

@Resource
private CommonMapper commonMapper;

@RequestMapping("test")
public Result<#noparse><List<Map<String,Object>>></#noparse> test(@RequestBody JSONObject jsonObject){
List<#noparse><Map<String, Object>></#noparse> result = commonMapper.executeSql("select * from test");
return Result.success(result);
}
}
