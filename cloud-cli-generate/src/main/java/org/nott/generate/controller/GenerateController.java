package org.nott.generate.controller;

import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import org.nott.generate.service.CodeGenerator;
import org.nott.cli.common.model.Result;
import org.nott.generate.model.ModuleInfo;
import org.nott.generate.model.ProjectInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Nott
 * @date 2024-4-22
 */
@RestController
@RequestMapping("/generate/")
public class GenerateController {

    @Resource
    private CodeGenerator codeGenerator;

    @RequestMapping("project")
    public Result<Void> project(@RequestBody JSONObject jsonObject) throws Exception{
        ProjectInfo projectInfo = jsonObject.getJSONObject("projectInfo").toJavaObject(ProjectInfo.class);
        codeGenerator.generator(projectInfo,projectInfo.getModuleInfos());
        return Result.success();
    }
}
